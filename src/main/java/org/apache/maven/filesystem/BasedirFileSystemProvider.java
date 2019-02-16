package org.apache.maven.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class BasedirFileSystemProvider extends FileSystemProvider
{
    private final FileSystemProvider defaultFileSystemProvider;
    
    public BasedirFileSystemProvider()
    {
        defaultFileSystemProvider = null;
    }
    
    public BasedirFileSystemProvider( FileSystemProvider fileSystemProvider )
    {
        this.defaultFileSystemProvider = fileSystemProvider;
    }
    
    @Override
    public void checkAccess( Path path, AccessMode... modes )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void copy( Path source, Path target, CopyOption... options )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createDirectory( Path dir, FileAttribute<?>... attrs )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createLink( Path link, Path existing )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void createSymbolicLink( Path link, Path target, FileAttribute<?>... attrs )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete( Path path )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteIfExists( Path path )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals( Object obj )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public <V extends FileAttributeView> V getFileAttributeView( Path path, Class<V> type, LinkOption... options )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileStore getFileStore( Path path )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileSystem getFileSystem( URI uri )
    {
        return new BasedirFileSystem( defaultFileSystemProvider.getFileSystem( uri ) );
    }

    @Override
    public Path getPath( URI uri )
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getScheme()
    {
        return defaultFileSystemProvider.getScheme();
    }

    @Override
    public int hashCode()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isHidden( Path path )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isSameFile( Path path, Path path2 )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void move( Path source, Path target, CopyOption... options )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsynchronousFileChannel newAsynchronousFileChannel( Path path, Set<? extends OpenOption> options,
                                                               ExecutorService executor, FileAttribute<?>... attrs )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public SeekableByteChannel newByteChannel( Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public DirectoryStream<Path> newDirectoryStream( Path dir, Filter<? super Path> filter )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileChannel newFileChannel( Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileSystem newFileSystem( Path path, Map<String, ?> env )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public FileSystem newFileSystem( URI uri, Map<String, ?> env )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream newInputStream( Path path, OpenOption... options )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public OutputStream newOutputStream( Path path, OpenOption... options )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A extends BasicFileAttributes> A readAttributes( Path path, Class<A> type, LinkOption... options )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Map<String, Object> readAttributes( Path path, String attributes, LinkOption... options )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public Path readSymbolicLink( Path link )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setAttribute( Path path, String attribute, Object value, LinkOption... options )
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString()
    {
        throw new UnsupportedOperationException();
    }
}
