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

import java.util.HashMap;
import java.util.Map;

import org.apache.maven.eventspy.AbstractEventSpy;
import org.apache.maven.eventspy.EventSpy;
import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.classworlds.ClassWorld;
import org.codehaus.plexus.classworlds.realm.ClassRealm;
import org.codehaus.plexus.component.annotations.Component;

/**
 * Event Spy demo (since Maven 3.0.2, with <a href="https://issues.apache.org/jira/browse/MNG-4936">MNG-4936</a>).
 * <p>Notice: event spies are not activated from POM build extensions.
 */
//@Named( "demo" )
//@Singleton
@Component( role = EventSpy.class, hint = "demo" ) // using Plexus Component annotations for Maven 3.0.x
public class EventSpyDemo
    extends AbstractEventSpy
{
    private Map<String, Integer> events = new HashMap<String, Integer>();

    private DefaultPlexusContainer container;

    public void init( Context context )
        throws Exception
    {
        System.err.println( "EventSpyDemo init:" );
        for ( Map.Entry<String, Object> entry : context.getData().entrySet() )
        {
            System.err.println( "EventSpyDemo init context: - " + entry.getKey() + " = " + entry.getValue() );
        }
        container = (DefaultPlexusContainer) context.getData().get( "plexus" );
        dump( container.getClassWorld() );
    }

    public void onEvent( Object event )
        throws Exception
    {
        //System.err.println( "EventSpyDemo onEvent: " + event );
        Integer count = events.get( event.getClass().getName() );
        if ( count == null )
        {
            count = 1;
        }
        else
        {
            count++;
        }
        events.put( event.getClass().getName(), count );
    }

    public void close()
        throws Exception
    {
        System.err.println( "EventSpyDemo close" );
        for ( Map.Entry<String, Integer> entry : events.entrySet() )
        {
            System.err.println( "EventSpyDemo - " + entry.getValue() + " " + entry.getKey() );
        }
        dump( container.getClassWorld() );
    }

    private void dump( ClassWorld cw )
    {
        for ( ClassRealm cr : cw.getRealms() )
        {
            System.err.println( "EventSpyDemo - ClassRealm " + cr.getId() );
            cr.display( System.err );
        }
    }
}
