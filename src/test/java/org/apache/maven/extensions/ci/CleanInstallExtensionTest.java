package org.apache.maven.extensions.ci;

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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.maven.execution.MavenExecutionRequest;
import org.apache.maven.execution.MavenExecutionResult;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Dependency;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith( MockitoExtension.class )
class CleanInstallExtensionTest
{
    private CleanInstallExtension extension;

    @Mock
    private MavenSession session;

    @Mock
    private MavenExecutionRequest request;

    @Mock
    private MavenExecutionResult result;
    
    @Mock
    private Logger logger;

    @BeforeEach
    public void setup()
    {
        extension = new CleanInstallExtension();
        extension.enableLogging( logger );
        when( session.getResult() ).thenReturn( result );
    }

    @Test
    void firstTimeNoInstall()
        throws Exception
    {
        Path ciLog = Paths.get( "target/tests/unit/start1.ser" );
        extension.setCiLog( ciLog );
        List<MavenProject> projects = Arrays.asList( newMavenProject( "G", "A", "V" ) );
        when( session.getProjects() ).thenReturn( projects );

        extension.afterSessionEnd( session );

        assertTrue( Files.notExists( ciLog ) );
        verifyZeroInteractions( logger );
    }

    @Test
    void firstTimeInstall()
        throws Exception
    {
        Path ciLog = Paths.get( "target/tests/unit/start2.ser" );
        Files.deleteIfExists( ciLog );
        
        extension.setCiLog( ciLog );
        List<MavenProject> projects = Arrays.asList( newMavenProject( "G", "A", "V" ) );
        when( session.getProjects() ).thenReturn( projects );
        when( session.getGoals() ).thenReturn( Arrays.asList( "clean", "install" ) );
        when( session.getStartTime() ).thenReturn( new Date() );

        extension.afterSessionEnd( session );

        assertTrue( Files.exists( ciLog ) );
        
        verifyZeroInteractions( logger );
    }

    @Test
    void classic()
        throws Exception
    {
        Path ciLog = Paths.get( "target/tests/unit/start2.ser" );
        Files.deleteIfExists( ciLog );
        
        extension.setCiLog( ciLog );
        List<MavenProject> projects = Arrays.asList( newMavenProject( "G", "A", "V" ) );
        when( session.getProjects() ).thenReturn( projects );
        when( session.getGoals() ).thenReturn( Arrays.asList( "clean", "install" ) );
        when( session.getStartTime() ).thenReturn( new Date() );

        extension.afterSessionEnd( session );
        extension.afterSessionEnd( session );

        assertTrue( Files.exists( ciLog ) );
        
        verify( logger, atLeastOnce() ).warn( anyString() );
    }

    @Test
    void localTest()
        throws Exception
    {
        Path ciLog = Paths.get( "target/tests/unit/localtest.ser" );
        Files.deleteIfExists( ciLog );
        
        extension.setCiLog( ciLog );
        MavenProject lib = newMavenProject( "G", "lib", "V" );
        List<MavenProject> libList = Arrays.asList( lib );
        
        MavenProject app = newMavenProject( "G", "app", "V" );
        List<Dependency> appDeps = Arrays.asList( newDependency( "G", "lib", "V" ) );
        when( app.getDependencies() ).thenReturn( appDeps );
        List<MavenProject> appList = Arrays.asList( app );
        
        when( session.getProjects() ).thenReturn( libList ).thenReturn( appList ).thenReturn( libList );
        when( session.getGoals() ).thenReturn( Arrays.asList( "clean", "install" ) );
        when( session.getStartTime() ).thenReturn( new Date() );

        extension.afterSessionEnd( session );
        extension.afterSessionEnd( session );
        extension.afterSessionEnd( session );

        assertTrue( Files.exists( ciLog ) );
        
        verifyZeroInteractions( logger );
    }

    private MavenProject newMavenProject( String groupId, String artifactId, String version )
    {
        MavenProject project = mock( MavenProject.class );
        when( project.getGroupId() ).thenReturn( groupId );
        when( project.getArtifactId() ).thenReturn( artifactId );
        when( project.getVersion() ).thenReturn( version );
        return project;
    }

    private Dependency newDependency( String groupId, String artifactId, String version )
    {
        Dependency dependency = new Dependency();
        dependency.setGroupId( groupId );
        dependency.setArtifactId( artifactId );
        dependency.setVersion( version );
        return dependency;
    }

    
    
}
