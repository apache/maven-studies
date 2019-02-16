package org.apache.maven.filesystem;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Iterator;

class BasedirPath implements Path
{
    private final Path basedir;
    
    private Path path;
    
    BasedirPath( Path path, Path basedir ) {
        this.path = path;
        this.basedir = basedir;
    }
    
    public FileSystem getFileSystem()
    {
        return path.getFileSystem();
    }

    public boolean isAbsolute()
    {
        return path.isAbsolute();
    }

    public Path getRoot()
    {
        return path.getRoot();
    }

    public Path getFileName()
    {
        return path.getFileName();
    }

    public Path getParent()
    {
        return path.getParent();
    }

    public int getNameCount()
    {
        return path.getNameCount();
    }

    public Path getName( int index )
    {
        return path.getName( index );
    }

    public Path subpath( int beginIndex, int endIndex )
    {
        return path.subpath( beginIndex, endIndex );
    }

    public boolean startsWith( Path other )
    {
        return path.startsWith( other );
    }

    public boolean startsWith( String other )
    {
        return path.startsWith( other );
    }

    public boolean endsWith( Path other )
    {
        return path.endsWith( other );
    }

    public boolean endsWith( String other )
    {
        return path.endsWith( other );
    }

    public Path normalize()
    {
        return path.normalize();
    }

    public Path resolve( Path other )
    {
        return path.resolve( other );
    }

    public Path resolve( String other )
    {
        return path.resolve( other );
    }

    public Path resolveSibling( Path other )
    {
        return path.resolveSibling( other );
    }

    public Path resolveSibling( String other )
    {
        return path.resolveSibling( other );
    }

    public Path relativize( Path other )
    {
        return path.relativize( other );
    }

    public URI toUri()
    {
        return path.toUri();
    }

    public Path toAbsolutePath()
    {
        if ( !path.isAbsolute() )
        {
            path = basedir.resolve( path );
        }
        return path.toAbsolutePath();
    }

    public Path toRealPath( LinkOption... options )
        throws IOException
    {
        return path.toRealPath( options );
    }

    public File toFile()
    {
        return path.toFile();
    }

    public WatchKey register( WatchService watcher, Kind<?>[] events, Modifier... modifiers )
        throws IOException
    {
        return path.register( watcher, events, modifiers );
    }

    public WatchKey register( WatchService watcher, Kind<?>... events )
        throws IOException
    {
        return path.register( watcher, events );
    }

    public Iterator<Path> iterator()
    {
        return path.iterator();
    }

    public int compareTo( Path other )
    {
        return path.compareTo( other );
    }

    public boolean equals( Object other )
    {
        return path.equals( other );
    }

    public int hashCode()
    {
        return path.hashCode();
    }

    public String toString()
    {
        return path.toString();
    }
}
