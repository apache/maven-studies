package org.apache.maven.filesystem;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Enumeration;

public class BasedirClassLoader extends ClassLoader
{
    private final ClassLoader classLoader;
    
    private final Path basedir;
    
    public BasedirClassLoader( ClassLoader classLoader , Path basedir )
    {
        this.classLoader = classLoader;
        this.basedir = basedir;
    }
    
    public final Path getBasedir()
    {
        return basedir;
    }
    
    public int hashCode()
    {
        return classLoader.hashCode();
    }

    public boolean equals( Object obj )
    {
        return classLoader.equals( obj );
    }

    public String toString()
    {
        return classLoader.toString();
    }

    public Class<?> loadClass( String name )
        throws ClassNotFoundException
    {
        return classLoader.loadClass( name );
    }

    public Enumeration<URL> getResources( String name )
        throws IOException
    {
        return classLoader.getResources( name );
    }

    public InputStream getResourceAsStream( String name )
    {
        return classLoader.getResourceAsStream( name );
    }

    public void setDefaultAssertionStatus( boolean enabled )
    {
        classLoader.setDefaultAssertionStatus( enabled );
    }

    public void setPackageAssertionStatus( String packageName, boolean enabled )
    {
        classLoader.setPackageAssertionStatus( packageName, enabled );
    }

    public void setClassAssertionStatus( String className, boolean enabled )
    {
        classLoader.setClassAssertionStatus( className, enabled );
    }

    public void clearAssertionStatus()
    {
        classLoader.clearAssertionStatus();
    }
}
