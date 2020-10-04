package org.apache.maven.plugins.sign.pgp;

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

import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.bcpg.BCPGOutputStream;
import org.bouncycastle.bcpg.HashAlgorithmTags;
import org.bouncycastle.openpgp.PGPException;
import org.bouncycastle.openpgp.PGPPrivateKey;
import org.bouncycastle.openpgp.PGPSecretKey;
import org.bouncycastle.openpgp.PGPSecretKeyRing;
import org.bouncycastle.openpgp.PGPSecretKeyRingCollection;
import org.bouncycastle.openpgp.PGPSignature;
import org.bouncycastle.openpgp.PGPSignatureGenerator;
import org.bouncycastle.openpgp.PGPUtil;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.bouncycastle.openpgp.operator.jcajce.JcaPGPContentSignerBuilder;
import org.bouncycastle.openpgp.operator.jcajce.JcePBESecretKeyDecryptorBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Signig data by PGP.
 *
 * @author Slawomir Jaranowski
 */
@Slf4j
public class PGPSigner
{

    private final PGPSecretKeyInfo keyInfo;

    private PGPSecretKey secretKey;
    private PGPPrivateKey pgpPrivateKey;

    public PGPSigner( PGPSecretKeyInfo keyInfo ) throws PGPSignerException
    {

        this.keyInfo = keyInfo;
        try
        {
            loadKey();
        }
        catch ( IOException | PGPException e )
        {
            throw new PGPSignerException( e );
        }

        List<String> uIds = new ArrayList<>();
        secretKey.getUserIDs().forEachRemaining( uIds::add );

        LOGGER.info( "Loaded keyId: {}, uIds: {}", String.format( "%16X", secretKey.getKeyID() ), uIds );
    }

    /**
     * Find and load private key form file.
     */
    private void loadKey() throws IOException, PGPException, PGPSignerException
    {
        InputStream inputStream = PGPUtil.getDecoderStream( new FileInputStream( keyInfo.getFile() ) );
        PGPSecretKeyRingCollection pgpSecretKeyRingCollection = new PGPSecretKeyRingCollection( inputStream,
                new JcaKeyFingerprintCalculator() );

        Long keyId = keyInfo.getKeyId();
        if ( keyId != null )
        {
            secretKey = pgpSecretKeyRingCollection.getSecretKey( keyId );
        }
        else
        {
            // retrieve first master key
            Iterator<PGPSecretKeyRing> keyRings = pgpSecretKeyRingCollection.getKeyRings();
            if ( keyRings.hasNext() )
            {
                PGPSecretKeyRing secretKeys = keyRings.next();
                secretKey = secretKeys.getSecretKey();
            }
        }

        if ( secretKey == null )
        {
            throw new PGPSignerException( "Secret key not found" );
        }

        pgpPrivateKey = secretKey
                .extractPrivateKey( new JcePBESecretKeyDecryptorBuilder().build( keyInfo.getPassphrase() ) );
    }

    /**
     * Generate PGP signature for a given input stream.
     *
     * @param inputStream stream with data to calculate signature
     * @param outputPath a destination of signature
     * @throws PGPSignerException if some IO problems
     */
    public void sign( InputStream inputStream, Path outputPath ) throws PGPSignerException
    {

        PGPSignatureGenerator sGen = new PGPSignatureGenerator(
                new JcaPGPContentSignerBuilder( secretKey.getPublicKey().getAlgorithm(), HashAlgorithmTags.SHA256 ) );

        try
        {
            sGen.init( PGPSignature.BINARY_DOCUMENT, pgpPrivateKey );

            int i;
            while ( ( i = inputStream.read() ) >= 0 )
            {
                sGen.update( (byte) i );
            }

            Files.createDirectories( outputPath.getParent() );

            try ( OutputStream out = Files.newOutputStream( outputPath );
                  BCPGOutputStream bcpgOutputStream = new BCPGOutputStream( new ArmoredOutputStream( out ) ) )
            {
                sGen.generate().encode( bcpgOutputStream );
            }
        }
        catch ( PGPException | IOException e )
        {
            throw new PGPSignerException( e );
        }
    }
}
