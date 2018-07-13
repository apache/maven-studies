package org.apache.maven.demo.extension;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.AbstractMavenLifecycleParticipant;
import org.apache.maven.MavenExecutionException;
import org.apache.maven.execution.MavenSession;

/**
 * Lifecycle Participant demo (feature added in Maven 3.0-alpha-3 with
 * <a href="https://issues.apache.org/jira/browse/MNG-4224">MNG-4224</a>.
 */
@Named( "demo" )
@Singleton
public class LifecycleParticipantDemo
    extends AbstractMavenLifecycleParticipant
{
    public void afterProjectsRead( MavenSession session )
        throws MavenExecutionException
    {
        System.err.println( "LifecycleParticipantDemo afterProjectsRead" );
    }

    public void afterSessionStart( MavenSession session )
        throws MavenExecutionException
    {
        System.err.println( "LifecycleParticipantDemo afterSessionStart" );
    }

    /*
    * @since 3.2.1, MNG-5389
    */
   public void afterSessionEnd( MavenSession session )
       throws MavenExecutionException
   {
       System.err.println( "LifecycleParticipantDemo afterSessionEnd" );
   }
}
