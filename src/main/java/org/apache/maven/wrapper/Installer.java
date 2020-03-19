package org.apache.maven.wrapper;

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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @author Hans Dockter
 */
public class Installer
{
    public static final String DEFAULT_DISTRIBUTION_PATH = "wrapper/dists";

    private final Downloader download;

    private final PathAssembler pathAssembler;

    public Installer( Downloader download, PathAssembler pathAssembler )
    {
        this.download = download;
        this.pathAssembler = pathAssembler;
    }

    public Path createDist( WrapperConfiguration configuration )
        throws Exception
    {
        URI distributionUrl;
        String mvnwRepoUrl = System.getenv( MavenWrapperMain.MVNW_REPOURL );
        if ( mvnwRepoUrl != null && !mvnwRepoUrl.isEmpty() )
        {
            distributionUrl = new URI( mvnwRepoUrl + "/" + MavenWrapperMain.MVN_PATH );
            Logger.info( "Detected MVNW_REPOURL environment variable " + mvnwRepoUrl );
        }
        else
        {
            distributionUrl = configuration.getDistribution();
        }
        Logger.info( "Downloading Maven binary from " + distributionUrl );
        boolean alwaysDownload = configuration.isAlwaysDownload();
        boolean alwaysUnpack = configuration.isAlwaysUnpack();

        PathAssembler.LocalDistribution localDistribution = pathAssembler.getDistribution( configuration );

        Path localZipFile = localDistribution.getZipFile();
        boolean downloaded = false;
        if ( alwaysDownload || !Files.exists( localZipFile ) )
        {
            Path tmpZipFile = localZipFile.resolveSibling( localZipFile.getFileName() + ".part" );
            Files.deleteIfExists( tmpZipFile );
            Logger.info( "Downloading " + distributionUrl );
            download.download( distributionUrl, tmpZipFile );
            Files.move( tmpZipFile, localZipFile );
            downloaded = true;
        }

        Path distDir = localDistribution.getDistributionDir();
        List<File> dirs = listDirs( distDir.toFile() );

        if ( downloaded || alwaysUnpack || dirs.isEmpty() )
        {
            Files.walkFileTree( distDir.toAbsolutePath(), new SimpleFileVisitor<Path>()
            {
                @Override
                public FileVisitResult postVisitDirectory( Path dir, IOException exc )
                    throws IOException
                {
                    if ( dir.getParent().equals( distDir ) )
                    {
                        Logger.info( "Deleting directory " + distDir.toAbsolutePath() );
                        Files.delete( dir );
                    }
                    return FileVisitResult.CONTINUE;
                }
                
                public FileVisitResult visitFile( Path file, BasicFileAttributes attrs )
                    throws IOException
                {
                    if ( !file.getParent().equals( distDir ) )
                    {
                        Files.delete( file );
                    }
                    return FileVisitResult.CONTINUE;
                };
            } );
            
            Logger.info( "Unzipping " + localZipFile.toAbsolutePath() + " to " + distDir.toAbsolutePath() );
            unzip( localZipFile, distDir );
            
            dirs = listDirs( distDir.toFile() );
            if ( dirs.isEmpty() )
            {
                throw new RuntimeException( String.format( 
                   "Maven distribution '%s' does not contain any directories. Expected to find exactly 1 directory.",
                   distributionUrl ) );
            }
            setExecutablePermissions( dirs.get( 0 ) );
        }
        if ( dirs.size() != 1 )
        {
            throw new RuntimeException( String.format( 
                   "Maven distribution '%s' contains too many directories. Expected to find exactly 1 directory.",
                   distributionUrl ) );
        }
        return dirs.get( 0 ).toPath();
    }

    private List<File> listDirs( File distDir )
    {
        List<File> dirs = new ArrayList<File>();
        if ( distDir.exists() )
        {
            for ( File file : distDir.listFiles() )
            {
                if ( file.isDirectory() )
                {
                    dirs.add( file );
                }
            }
        }
        return dirs;
    }

    private void setExecutablePermissions( File mavenHome )
    {
        if ( isWindows() )
        {
            return;
        }
        File mavenCommand = new File( mavenHome, "bin/mvn" );
        String errorMessage = null;
        try
        {
            ProcessBuilder pb = new ProcessBuilder( "chmod", "755", mavenCommand.getCanonicalPath() );
            Process p = pb.start();
            if ( p.waitFor() == 0 )
            {
                Logger.info( "Set executable permissions for: " + mavenCommand.getAbsolutePath() );
            }
            else
            {
                BufferedReader is = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
                try ( Formatter stdout = new Formatter() )
                {
                    String line;
                    while ( ( line = is.readLine() ) != null )
                    {
                        stdout.format( "%s%n", line );
                    }
                    errorMessage = stdout.toString();
                }
            }
        }
        catch ( IOException | InterruptedException e )
        {
            errorMessage = e.getMessage();
        }
        if ( errorMessage != null )
        {
            Logger.warn( "Could not set executable permissions for: " + mavenCommand.getAbsolutePath() );
            Logger.warn( "Please do this manually if you want to use maven." );
        }
    }

    private boolean isWindows()
    {
        String osName = System.getProperty( "os.name" ).toLowerCase( Locale.US );
        if ( osName.indexOf( "windows" ) > -1 )
        {
            return true;
        }
        return false;
    }

    private Path deleteDir( Path dir ) throws IOException
    {
        return Files.walkFileTree( dir, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult postVisitDirectory( Path dir, IOException exc )
                throws IOException
            {
                Files.delete( dir );
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile( Path file, BasicFileAttributes attrs )
                throws IOException
            {
                Files.delete( file );
                return FileVisitResult.CONTINUE;
            }
        } );
    }

    private void unzip( Path zip, Path dest )
        throws IOException
    {
        try ( ZipFile zipFile = new ZipFile( zip.toFile() ) )
        {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while ( entries.hasMoreElements() )
            {
                ZipEntry entry = entries.nextElement();

                if ( entry.isDirectory() )
                {
                    Files.createDirectories( dest.resolve( entry.getName() ) );
                    continue;
                }

                Path targetFile = dest.resolve( entry.getName() );
                Files.createDirectories( targetFile.getParent() );

                Files.copy( zipFile.getInputStream( entry ), targetFile );
            }
        }
    }
}
