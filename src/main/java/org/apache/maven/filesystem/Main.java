package org.apache.maven.filesystem;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Ensure to start java with {@code -Djava.nio.file.spi.DefaultFileSystemProvider=org.apache.maven.filesystem.BasedirFilesystemProvider}
 * 
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/index.html?java/nio/file/spi/FileSystemProvider.html">java.nio.file.spi.FileSystemProvider</a>
 */
public class Main
{

    public static void main( String[] args )
    {
        ClassLoader old = Thread.currentThread().getContextClassLoader();
        try
        {
            System.out.println( "--- ORIGINAL ---" );
            tests();
            System.out.println();

            Path basedir = Paths.get( "/a/b/c" ).toAbsolutePath();
            ClassLoader c1 = new BasedirClassLoader( old, basedir );
            Thread.currentThread().setContextClassLoader( c1 );
            System.out.println( "--- With BasedirClassloader ( " + basedir + " ) ---" );
            tests();
        }
        finally
        {
            Thread.currentThread().setContextClassLoader( old );
        }
    }

    public static void tests() {
        System.out.println( "Path: " + Paths.get( "src/main/java" ) ); // good
        System.out.println( "Path.toAbsolutePath(): " + Paths.get( "src/main/java" ).toAbsolutePath() ); // good
        System.out.println( "File.getAbsoluteFile(): " + new File( "src/main/java" ).getAbsoluteFile() ); // bad
    }


}
