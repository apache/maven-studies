package org.apache.maven.extensions.eventsound;

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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.maven.eventspy.AbstractEventSpy;
import org.apache.maven.execution.ExecutionEvent;
import org.codehaus.plexus.logging.LogEnabled;
import org.codehaus.plexus.logging.Logger;

@Named
@Singleton
public class SoundPlayingListener extends AbstractEventSpy implements LogEnabled
{
    private Logger logger;
    
    private Properties properties = new Properties();

    private Path config;

    @Override
    public void enableLogging( Logger logger )
    {
        this.logger = logger;
    }
    
    @Override
    public void init( Context context )
        throws Exception
    {
        String mavenHome = System.getProperty( "maven.home" );
        
        config = Paths.get( mavenHome, "conf/ext/eventsound.properties").normalize();
        
        if ( !Files.isRegularFile( config ) )
        {
            // TODO write properties file template with all available properties
            logger.error( "MISSING " + config.toString() );
        }
        
        try ( InputStream is = new FileInputStream( config.toFile() ) )
        {
            properties.load( is );
        }
        
    }

    @Override
    public void onEvent( Object event )
        throws Exception
    {
        if ( event instanceof ExecutionEvent )
        {
            ExecutionEvent executionEvent = (ExecutionEvent) event;
            
            if ( executionEvent.getType() == ExecutionEvent.Type.SessionEnded )
            {
                
                String key;
                if ( executionEvent.getSession().getResult().hasExceptions() )
                {
                    key = "executionEvent.session.failure.audiofile";
                }
                else
                {
                    key = "executionEvent.session.success.audiofile";
                }
                
                String value = properties.getProperty( key );
                if ( value != null )
                {
                    Path mediafile = config.getParent().resolve( value );
                    
                    Thread t = new Thread( new Player( mediafile ) );
                    t.run();
                }
            }
        }
    }
    
    private class Player
        implements Runnable
    {
        private final Path mediafile;

        Player( Path mediafile )
        {
            this.mediafile = mediafile;
        }

        public void run()
        {
            final AudioListener listener = new AudioListener();
            try ( Clip clip = AudioSystem.getClip();
                  AudioInputStream ais = AudioSystem.getAudioInputStream( mediafile.toFile() ) )
            {
                clip.addLineListener( listener );
                clip.open( ais );
                clip.start();

                listener.waitUntilDone();
            }
            catch ( IOException | UnsupportedAudioFileException | LineUnavailableException e )
            {
                e.printStackTrace();
            }
            catch ( InterruptedException e )
            {
                // noop
            }
        }
    }
    
    /**
     * Ensure files is being played until the end
     * 
     * @author Robert Scholte
     * @since 1.0.0
     */
    static class AudioListener
        implements LineListener
    {
        private boolean done = false;

        @Override
        public synchronized void update( LineEvent event )
        {
            Type eventType = event.getType();
            if ( eventType == Type.STOP || eventType == Type.CLOSE )
            {
                done = true;
                notifyAll();
            }
        }

        public synchronized void waitUntilDone()
            throws InterruptedException
        {
            while ( !done )
            {
                wait();
            }
        }
    }
}
