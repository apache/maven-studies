package org.apache.maven.filesystem;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.WatchService;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.nio.file.spi.FileSystemProvider;
import java.util.Set;

public class BasedirFileSystem extends FileSystem
{
    private final FileSystem fileSystem;
    
    public BasedirFileSystem( FileSystem fileSystem )
    {
        this.fileSystem = fileSystem;
    }
    
    @Override
    public int hashCode()
    {
        return fileSystem.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        return fileSystem.equals( obj );
    }

    @Override
    public FileSystemProvider provider()
    {
        return fileSystem.provider();
    }

    @Override
    public void close()
        throws IOException
    {
        fileSystem.close();
    }

    @Override
    public boolean isOpen()
    {
        return fileSystem.isOpen();
    }

    @Override
    public boolean isReadOnly()
    {
        return fileSystem.isReadOnly();
    }

    @Override
    public String getSeparator()
    {
        return fileSystem.getSeparator();
    }

    @Override
    public Iterable<Path> getRootDirectories()
    {
        return fileSystem.getRootDirectories();
    }

    @Override
    public Iterable<FileStore> getFileStores()
    {
        return fileSystem.getFileStores();
    }

    @Override
    public String toString()
    {
        return fileSystem.toString();
    }

    @Override
    public Set<String> supportedFileAttributeViews()
    {
        return fileSystem.supportedFileAttributeViews();
    }

    @Override
    public Path getPath( String first, String... more )
    {
        Path p = fileSystem.getPath( first, more );

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if ( cl instanceof BasedirClassLoader )
        {
            p = new BasedirPath( p, ( (BasedirClassLoader) cl ).getBasedir() );
        }
        return p;
    }

    @Override
    public PathMatcher getPathMatcher( String syntaxAndPattern )
    {
        return fileSystem.getPathMatcher( syntaxAndPattern );
    }

    @Override
    public UserPrincipalLookupService getUserPrincipalLookupService()
    {
        return fileSystem.getUserPrincipalLookupService();
    }

    @Override
    public WatchService newWatchService()
        throws IOException
    {
        return fileSystem.newWatchService();
    }
}
