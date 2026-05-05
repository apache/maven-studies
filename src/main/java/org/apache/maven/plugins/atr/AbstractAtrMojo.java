/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.plugins.atr;

import java.io.File;
import java.net.URL;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.settings.Server;
import org.apache.maven.settings.crypto.DefaultSettingsDecryptionRequest;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.apache.maven.settings.crypto.SettingsDecryptionResult;

/**
 * Abstract base class for ATR Mojos.
 *
 * @author Maven Team
 */
public abstract class AbstractAtrMojo extends AbstractMojo {

    /**
     * The project id for ATR upload.
     */
    @Parameter(property = "atr.project", required = true)
    protected String project;

    /**
     * The version for ATR upload.
     */
    @Parameter(property = "atr.version", required = true)
    protected String version;

    /**
     * The ATR server URL.
     */
    @Parameter(property = "atr.url", defaultValue = "https://release-test.apache.org/")
    protected URL url;

    /**
     * Skip plugin execution.
     */
    @Parameter(property = "atr.skip", defaultValue = "false")
    protected boolean skip;

    /**
     * Dry run mode. When enabled, the plugin will simulate execution without performing actual operations.
     */
    @Parameter(property = "atr.dryRun", defaultValue = "false")
    protected boolean dryRun;

    /**
     * Server ID from settings.xml containing ATR credentials.
     * The server's username should be the ASF user ID, and the password should be the Personal Access Token (PAT).
     */
    @Parameter(property = "atr.serverId", defaultValue = "apache.atr")
    protected String serverId;

    /**
     * If set to true, the plugin will only execute in the execution root directory (typically the top-level
     * directory of a multi-module build). This prevents the plugin from running multiple times in child modules.
     */
    @Parameter(property = "atr.runOnlyAtExecutionRoot", defaultValue = "true")
    protected boolean runOnlyAtExecutionRoot;

    @Component
    protected MavenProject mavenProject;

    @Component
    protected MavenSession session;

    /**
     * Settings decrypter component.
     */
    @Component
    protected SettingsDecrypter settingsDecrypter;

    /**
     * Get and decrypt the server configuration.
     *
     * @return the decrypted server
     * @throws MojoExecutionException if server cannot be found or decrypted
     */
    protected Server getServer() throws MojoExecutionException {
        Server server = session.getSettings().getServer(serverId);
        if (server == null) {
            getLog().error("Missing permissions for '" + serverId + "' server in ~/.m2/settings.xml");
            throw new MojoExecutionException(
                    "<server><id>" + serverId + "</id> not found in ~/.m2/settings.xml. "
                            + "Please configure it with your ASF user ID as <username> and ATR Personal Access Token as <password> (encrypted if enabled).");
        }

        DefaultSettingsDecryptionRequest request = new DefaultSettingsDecryptionRequest(server);
        SettingsDecryptionResult result = settingsDecrypter.decrypt(request);

        if (!result.getProblems().isEmpty()) {
            getLog().warn("Problems decrypting server credentials: " + result.getProblems());
        }

        server = result.getServer();
        if (server.getUsername() == null || server.getPassword() == null) {
            throw new MojoExecutionException("Server '" + serverId
                    + "' must have username (ASF user ID) and password (ATR PAT) configured in settings.xml.");
        }

        return server;
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        if (skip) {
            getLog().info("Skipping ATR plugin execution");
            return;
        }

        if (runOnlyAtExecutionRoot && !isExecutionRoot()) {
            getLog().info("Skipping ATR plugin execution (not execution root)");
            return;
        }

        atrExecute();
    }

    /**
     * Check if the current project is the execution root.
     *
     * @return true if this is the execution root
     */
    private boolean isExecutionRoot() {
        File executionRootDirectory = new File(session.getExecutionRootDirectory());
        File projectBaseDir = mavenProject.getBasedir();
        return executionRootDirectory.equals(projectBaseDir);
    }

    /**
     * Create an ATR client with JWT caching support.
     *
     * @return the ATR client
     * @throws MojoExecutionException if client creation fails
     */
    protected AtrClient createAtrClient() throws MojoExecutionException {
        return new AtrClient(url, getServer(), getPluginContext(), getLog());
    }

    /**
     * Execute the ATR-specific logic.
     *
     * @throws MojoExecutionException if an error occurs during execution
     * @throws MojoFailureException if a failure occurs during execution
     */
    protected abstract void atrExecute() throws MojoExecutionException, MojoFailureException;
}
