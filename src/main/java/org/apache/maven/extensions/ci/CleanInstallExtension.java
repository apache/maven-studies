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

import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;

/**
 * Extension that verifies if "clean install" was useful
 * 
 * @author Robert Scholte
 * @since 1.0
 */
@Named
@Singleton
public class CleanInstallExtension extends AbstractMavenLifecycleParticipant implements LogEnabled
{
    private Path ciLog = Paths.get( System.getProperty( "user.home" ), ".m2/extensions/ci.ser" );
    
    private Logger logger;
    
    @Override
    public void enableLogging( Logger logger )
    {
        this.logger = logger;
    }
    
    @SuppressWarnings( "unchecked" )
    @Override
    public void afterSessionEnd( MavenSession session )
        throws MavenExecutionException
    {
        if ( session.getResult().hasExceptions() )
        {
            return;
        }
        
        final List<MavenProject> projects = session.getProjects(); 
        final List<String> projectKeys = projects
                        .stream()
                        .map( p -> p.getGroupId() + ':' + p.getArtifactId() + ':' + p.getVersion() )
                        .collect( Collectors.toList() );
        
        try
        {
            List<Data> originalLines;
            if ( Files.exists( ciLog ) )
            {
                try ( FileInputStream fis = new FileInputStream( ciLog.toFile() );
                      ObjectInputStream ois = new ObjectInputStream( fis ) ) 
                {
                    originalLines = (List<Data>) ois.readObject();
                }
                catch ( ClassNotFoundException e )
                {
                    throw new MavenExecutionException( e.getMessage(), e );
                }
            }
            else
            {
                originalLines = Collections.emptyList();
            }

            Set<String> dependencyKeys = projects
                            .parallelStream()
                            .map( p -> p.getDependencies() )
                            .flatMap( d -> d.stream() )
                            .map( d -> d.getGroupId() + ':' + d.getArtifactId() + ':' + d.getVersion() )
                            .collect( Collectors.toSet() );
            
            Function<Data, State> state = l -> 
            {
                List<String> keys = l.projectKeys;
                if ( !Collections.disjoint( keys, projectKeys ) )
                {
                    return State.PROJECTKEY;
                }
                else if ( !Collections.disjoint( keys, dependencyKeys ) )
                {
                    return State.DEPENDENCYKEY;
                }
                else
                {
                    return State.NONE;
                }
            };
            
            Map<State, List<Data>> groupedLines = originalLines.stream()
                .collect( Collectors.groupingBy( state ) );
            
            AtomicInteger counter = new AtomicInteger();
            
            List<Data> disjoint = groupedLines.get( State.PROJECTKEY );
            if ( !Optional.ofNullable( disjoint ).map( List::isEmpty ).orElse( true ) )
            {
                if ( session.getGoals().contains( "install" ) )
                {
                    disjoint.stream().forEach( d -> 
                    {
                        logger.warn( "Previous 'install' on this project was unnecessary "
                                        + "and polluted your local repository" );
                        logger.warn( "A good Maven citizen uses 'verify' instead" );
                        
                        counter.set( d.counter );
                    } );

                    if ( counter.get() > 2 )
                    {
                        logger.warn( "I warned you...." );
                        throw new MavenExecutionException( "Stubborn developer spotted",
                                                           session.getTopLevelProject().getFile() );
                    }
                }
                else
                {
                    // learning...
                    disjoint.stream().forEach( d -> logger.warn( "Previous 'install' on this project was unnecessary, "
                        + "but you're learning. Well done!" ) );
                }
            }
            else if ( session.getGoals().contains( "install" ) ) 
            {
                logger.info( "Registrating " + session.getGoals() );
            }

            List<Data> newLines = groupedLines.getOrDefault( State.NONE, new ArrayList<>( 1 ) );
            if ( session.getGoals().contains( "install" ) )
            {
                Data data = new Data();
                data.startTime = session.getStartTime().getTime();
                data.executionRootDirectory = session.getExecutionRootDirectory();
                data.goals = session.getGoals();
                data.projectKeys = projectKeys;
                data.counter = counter.incrementAndGet();
                newLines.add( data );
            }

            if ( !( newLines.isEmpty() && originalLines.isEmpty() ) )
            {
                if ( !Files.exists( ciLog ) )
                {
                    Files.createDirectories( ciLog.getParent() );
                }
                
                try ( FileOutputStream fos = new FileOutputStream( ciLog.toFile() );
                      ObjectOutputStream oos = new ObjectOutputStream( fos ) ) 
                {
                    oos.writeObject( newLines );
                }
            }
        }
        catch ( IOException e )
        {
            throw new MavenExecutionException( e.getMessage(), e );
        }
    }
    
    // for testing purpose
    void setCiLog( Path ciLog )
    {
        this.ciLog = ciLog;
    }
    
    private enum State 
    {
       PROJECTKEY, DEPENDENCYKEY, NONE    
    }
    
    private static class Data implements Serializable
    {
        private static final long serialVersionUID = -6588609095599417153L;

        private int counter;
        
        private String executionRootDirectory;
        
        private List<String> goals;

        private long startTime;
        
        private List<String> projectKeys;
    }
}
