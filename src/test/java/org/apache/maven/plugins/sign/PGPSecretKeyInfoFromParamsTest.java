package org.apache.maven.plugins.sign;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

public class PGPSecretKeyInfoFromParamsTest
{

    private static final String[] KEY_ID_HEX = {"ABCDEF0123456789", "0000000000000000", "FFFFFFFFFFFFFFFF"};
    private static final long[] KEY_ID = {0xABCDEF0123456789L, 0L, 0xFFFFFFFFFFFFFFFFL};

    private static final File KEY_FILE = new File( "test.asc" );

    private static final String KEY_PASS_STR = "pass";
    private static final char[] KEY_PASS = KEY_PASS_STR.toCharArray();

    @Test
    public void allProperties()
    {
        // when
        PGPSecretKeyInfoFromParams info = new PGPSecretKeyInfoFromParams( KEY_ID_HEX[0], KEY_PASS_STR, KEY_FILE );

        // then
        assertThat( info.getKeyId(), is( KEY_ID[0] ) );
        assertThat( info.getFile(), is( KEY_FILE ) );
        assertThat( info.getPassphrase(), is( KEY_PASS ) );
    }

    @Test
    public void npeShouldBeThrowForNullPassphrase()
    {
        assertThrows( NullPointerException.class,
                () -> new PGPSecretKeyInfoFromParams( null, null, null ) );
    }

    @Test
    public void keyIdShouldBeParsedProperly()
    {
        for ( int i = 0; i < KEY_ID_HEX.length; i++ )
        {
            // when
            PGPSecretKeyInfoFromParams info = new PGPSecretKeyInfoFromParams( KEY_ID_HEX[i], KEY_PASS_STR, KEY_FILE );

            // then
            assertThat( info.getKeyId(), is( KEY_ID[i] ) );
        }
    }
}
