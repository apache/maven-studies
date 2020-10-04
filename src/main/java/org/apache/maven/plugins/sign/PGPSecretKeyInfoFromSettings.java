package org.apache.maven.plugins.sign;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.plugins.sign.pgp.PGPSecretKeyInfo;
import org.apache.maven.settings.Server;

import java.io.File;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Information about pgp key from settings server
 *
 * @author Slawomir Jaranowski
 */
public class PGPSecretKeyInfoFromSettings implements PGPSecretKeyInfo
{

    private final Server settingsServer;

    PGPSecretKeyInfoFromSettings( Server settingsServer )
    {
        this.settingsServer = Objects.requireNonNull( settingsServer );
    }

    @Override
    public Long getKeyId()
    {
        try
        {
            return new BigInteger( settingsServer.getUsername(), 16 ).longValue();
        }
        catch ( NumberFormatException e )
        {
            return null;
        }
    }

    @Override
    public char[] getPassphrase()
    {
        return settingsServer.getPassphrase().toCharArray();
    }

    @Override
    public File getFile()
    {
        return new File( settingsServer.getPrivateKey() );
    }
}
