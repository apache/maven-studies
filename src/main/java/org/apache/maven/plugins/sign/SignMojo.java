package org.apache.maven.plugins.sign;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.transform.FileTransformer;
import org.eclipse.aether.transform.FileTransformerManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Sign project artifacts.
 */
@Slf4j
@Mojo( name = "sign", defaultPhase = LifecyclePhase.VERIFY )
public class SignMojo extends AbstractMojo
{

    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    @Parameter( defaultValue = "${session}", required = true, readonly = true )
    protected MavenSession session;

    @Parameter( defaultValue = "${repositorySystemSession}", readonly = true )
    private RepositorySystemSession repoSession;

    @Parameter
    private boolean useReflection;

    @Override
    public void execute() throws MojoExecutionException
    {


        LOGGER.info( "session: {}", session );
        LOGGER.info( "repoSession: {}", repoSession );
        LOGGER.info( "Artifact : {}", project.getArtifact() );
        LOGGER.info( "AttachedArtifacts: {}", project.getAttachedArtifacts() );
        LOGGER.info( "project.getFile: {}", project.getFile() );

        Artifact pomArtifact = new DefaultArtifact( project.getArtifact().getGroupId(),
                project.getArtifact().getArtifactId(), "", // classifier
                "pom", project.getArtifact().getVersion() );
        pomArtifact = pomArtifact.setFile( project.getFile() );


        if ( useReflection )
        {
            try
            {
                getFileTransformerFromReflection( repoSession, pomArtifact );
            }
            catch ( NoSuchMethodException nme )
            {
                LOGGER.info( " noSuchMethod: {}", nme.getMessage(), nme );
            }
            catch ( InvocationTargetException | IllegalAccessException e )
            {
                throw new MojoExecutionException( e.getMessage(), e );
            }
        }
        else
        {
            getFileTransformerFromMethodCall( repoSession, pomArtifact );
        }
    }

    private void getFileTransformerFromMethodCall( RepositorySystemSession repoSession, Artifact pomArtifact )
    {
        LOGGER.info( " -> getFileTransformerFromMethodCall" );
        FileTransformerManager fileTransformerManager = repoSession.getFileTransformerManager();

        Collection<FileTransformer> transformersForArtifact = fileTransformerManager
                .getTransformersForArtifact( pomArtifact );

        LOGGER.info( "    -> transformers: {}", transformersForArtifact );
    }

    private void getFileTransformerFromReflection( RepositorySystemSession repoSession, Artifact pomArtifact )
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        LOGGER.info( " -> getFileTransformerFromReflection" );

        Method getFileTransformerManagerMethod = repoSession.getClass().getMethod( "getFileTransformerManager" );
        LOGGER.info( "   -> getFileTransformerManagerMethod  : {}", getFileTransformerManagerMethod );

        Object fileTransformerManager = getFileTransformerManagerMethod.invoke( repoSession );
        LOGGER.info( "   -> fileTransformerManager: {}", fileTransformerManager );
        if ( fileTransformerManager == null )
        {
            return;
        }

        Method getTransformersForArtifactMethod = fileTransformerManager.getClass()
                .getMethod( "getTransformersForArtifact", Artifact.class );

        LOGGER.info( "   -> getTransformersForArtifactMethod  : {}", getTransformersForArtifactMethod );
        getTransformersForArtifactMethod.setAccessible( true );
        Collection<?> transformersForArtifact = (Collection<?>) getTransformersForArtifactMethod
                .invoke( fileTransformerManager, pomArtifact );
        LOGGER.info( "   -> transformersForArtifact: {}", transformersForArtifact );

        // what to do - if have more then one transformer ???
        for ( Object o : transformersForArtifact )
        {
            LOGGER.info( "     -> transformer: {}", o );
            Method transformDataMethod = o.getClass().getMethod( "transformData", File.class );
            transformDataMethod.setAccessible( true );
            InputStream inputStream = (InputStream) transformDataMethod.invoke( o, pomArtifact.getFile() );
            String outPom = new BufferedReader( new InputStreamReader( inputStream ) ).lines()
                    .collect( Collectors.joining( "\n" ) );
            LOGGER.info( "outPom:\n{}", outPom );
        }
    }
}
