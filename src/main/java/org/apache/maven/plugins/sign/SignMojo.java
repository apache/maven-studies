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

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.sign.pgp.PGPSigner;
import org.apache.maven.plugins.sign.pgp.PGPSignerException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectHelper;
import org.apache.maven.project.artifact.ProjectArtifact;
import org.eclipse.aether.transform.FileTransformer;
import org.eclipse.aether.transform.TransformException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Sign project artifacts.
 *
 * @author Slawomir Jaranowski
 */
@Mojo( name = "sign", defaultPhase = LifecyclePhase.VERIFY )
public class SignMojo extends AbstractMojo
{

    private static final Logger LOGGER = LoggerFactory.getLogger( SignMojo.class );

    @Parameter( defaultValue = "${project}", readonly = true, required = true )
    private MavenProject project;

    @Parameter( defaultValue = "${session}", required = true, readonly = true )
    protected MavenSession session;

    @Parameter( defaultValue = "pgpKey", required = true, property = "sign.serverId" )
    private String serverId;

    @Inject
    private MavenProjectHelper projectHelper;

    private PGPSigner pgpSigner;

    @Override
    public void execute() throws MojoExecutionException
    {
        try
        {
            pgpSigner = new PGPSigner(
                    new PGPSecretKeyInfoFromSettings( session.getSettings().getServer( serverId ) ) );
        }
        catch ( PGPSignerException e )
        {
            throw new MojoExecutionException( e.getMessage(), e );
        }

        // collect artifact to sign
        Set<Artifact> artifactsToSign = new HashSet<>();

        artifactsToSign.add( new ProjectArtifact( project ) );
        artifactsToSign.add( project.getArtifact() );
        artifactsToSign.addAll( project.getAttachedArtifacts() );

        // sign and attach signature to project
        artifactsToSign.stream()
                .map( this::signArtefact )
                .flatMap( List::stream )
                .forEach( this::attacheSignResult );
    }

    /**
     * Sign given artifact. In result we can have many signatures, transformers can produce multiple output for one
     * artifact.
     * <p>
     * This method ask transformers for inputStream for all artifact mutations, and sign each stream.
     *
     * @param artifact artifact to sign
     * @return sign result
     */
    private List<SignResult> signArtefact( Artifact artifact )
    {
        LOGGER.info( "Sign artifact: {}", artifact );

        org.eclipse.aether.artifact.Artifact srcArtifact = new org.eclipse.aether.artifact.DefaultArtifact(
                artifact.getGroupId(),
                artifact.getArtifactId(),
                artifact.getClassifier(),
                artifact.getArtifactHandler().getExtension(),
                artifact.getVersion(),
                null,
                artifact.getFile() );

        Collection<FileTransformer> transformersForArtifact = session.getRepositorySession().getFileTransformerManager()
                .getTransformersForArtifact( srcArtifact );

        List<SignResult> result = new ArrayList<>();

        try
        {
            if ( transformersForArtifact.isEmpty() )
            {
                try ( InputStream artifactInputStream = new BufferedInputStream(
                        new FileInputStream( srcArtifact.getFile() ) ) )
                {
                    result.add( makeSignature( artifactInputStream,
                            srcArtifact.getArtifactId(),
                            srcArtifact.getClassifier(),
                            srcArtifact.getExtension() ) );
                }
            }
            else
            {
                for ( FileTransformer fileTransformer : transformersForArtifact )
                {
                    org.eclipse.aether.artifact.Artifact dstArtifact = fileTransformer.transformArtifact( srcArtifact );
                    result.add( makeSignature( fileTransformer.transformData( srcArtifact.getFile() ),
                            dstArtifact.getArtifactId(),
                            dstArtifact.getClassifier(),
                            dstArtifact.getExtension() ) );
                }
            }
        }
        catch ( IOException | PGPSignerException | TransformException e )
        {
            throw new SignMojoException( e );
        }

        return result;
    }

    /**
     * Sign given input stream. In result we will have file with signature.
     *
     * @param inputStream data to sign
     * @param artifactId used for build filename
     * @param classifier used for build filename
     * @param extension used for build filename
     * @return result of signing
     * @throws PGPSignerException if some thing wrong
     */
    private SignResult makeSignature( InputStream inputStream, String artifactId, String classifier, String extension )
            throws PGPSignerException
    {

        String targetExt = extension + ".asc";

        String targetName = artifactId;
        if ( classifier != null && !classifier.isEmpty() )
        {
            targetName += "-" + classifier;
        }
        targetName += "." + targetExt;

        Path target = Paths.get( project.getBuild().getDirectory(), targetName );

        pgpSigner.sign( inputStream, target );

        return new SignResult( classifier, targetExt, target.toFile() );
    }

    /**
     * Attache sign result to project.
     */
    private void attacheSignResult( SignResult signResult )
    {
        LOGGER.info( "Attache signature: {}", signResult );

        projectHelper
                .attachArtifact( project, signResult.getExtension(), signResult.getClassifier(), signResult.getFile() );
    }
}
