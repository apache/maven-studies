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

import java.io.File;
import java.math.BigInteger;

/**
 * Information about pgp key from settings server
 *
 * @author Slawomir Jaranowski
 */
public class PGPSecretKeyInfoFromParams implements PGPSecretKeyInfo
{

    private final Long keyId;
    private final char[] passphrase;
    private final File keyFile;

    PGPSecretKeyInfoFromParams( String keyIdStr, String passphraseStr, File keyFile )
    {


        if ( keyIdStr != null )
        {
            try
            {
                this.keyId = new BigInteger( keyIdStr, 16 ).longValue();
            }
            catch ( NumberFormatException e )
            {
                throw new SignMojoException( e );
            }
        }
        else
        {
            this.keyId = null;
        }

        this.passphrase = passphraseStr.toCharArray();
        this.keyFile = keyFile;
    }

    @Override
    public Long getKeyId()
    {
        return keyId;
    }

    @Override
    public char[] getPassphrase()
    {
        return passphrase;
    }

    @Override
    public File getFile()
    {
        return keyFile;
    }
}
