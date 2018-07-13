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

import org.apache.maven.execution.AbstractExecutionListener;
import org.apache.maven.execution.ExecutionEvent;

/**
 * Execution Listener demo.
 * <b>Question:</b> how to inject it to Maven runtime?
 */
@Named( "demo" )
@Singleton
public class ExecutionListenerDemo
    extends AbstractExecutionListener
{
    public void projectDiscoveryStarted( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo projectDiscoveryStarted" );
    }

    public void sessionStarted( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo sessionStarted" );
    }

    public void sessionEnded( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo sessionEnded" );
    }

    public void projectSkipped( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo projectSkipped" );
    }

    public void projectStarted( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo projectStarted" );
    }

    public void projectSucceeded( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo projectSucceeded" );
    }

    public void projectFailed( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo projectFailed" );
    }

    public void forkStarted( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo forkStarted" );
    }

    public void forkSucceeded( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo forkSucceeded" );
    }

    public void forkFailed( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo forkFailed" );
    }

    public void mojoSkipped( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo mojoSkipped" );
    }

    public void mojoStarted( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo mojoStarted" );
    }

    public void mojoSucceeded( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo mojoSucceeded" );
    }

    public void mojoFailed( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo mojoFailed" );
    }

    public void forkedProjectStarted( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo forkedProjectStarted" );
    }

    public void forkedProjectSucceeded( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo forkedProjectSucceeded" );
    }

    public void forkedProjectFailed( ExecutionEvent event )
    {
        System.err.println( "ExecutionListenerDemo forkedProjectFailed" );
    }
}
