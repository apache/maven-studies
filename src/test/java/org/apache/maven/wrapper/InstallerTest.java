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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Hans Dockter
 */
public class InstallerTest {
    
    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();
    

  private Installer install;

  private File distributionDir;

  private File zipStore;

  private File mavenHomeDir;

  private File zipDestination;

  private WrapperConfiguration configuration = new WrapperConfiguration();

  private Downloader download;

  private PathAssembler pathAssembler;

  private PathAssembler.LocalDistribution localDistribution;

  @Before
  public void setup() throws Exception {
    configuration.setZipBase(PathAssembler.PROJECT_STRING);
    configuration.setZipPath("someZipPath");
    configuration.setDistributionBase(PathAssembler.MAVEN_USER_HOME_STRING);
    configuration.setDistributionPath("someDistPath");
    configuration.setDistribution(new URI("http://server/maven-0.9.zip"));
    configuration.setAlwaysDownload(false);
    configuration.setAlwaysUnpack(false);
    distributionDir = temporaryFolder.newFolder( "someDistPath" );
    mavenHomeDir = new File(distributionDir, "maven-0.9");
    zipStore = temporaryFolder.newFolder( "zips" );
    zipDestination = new File(zipStore, "maven-0.9.zip");

    download = mock(Downloader.class);
    pathAssembler = mock(PathAssembler.class);
    localDistribution = mock(PathAssembler.LocalDistribution.class);

    when(localDistribution.getZipFile()).thenReturn(zipDestination);
    when(localDistribution.getDistributionDir()).thenReturn(distributionDir);
    when(pathAssembler.getDistribution(configuration)).thenReturn(localDistribution);

    install = new Installer(download, pathAssembler);

  }

  private void createTestZip(File zipDestination) throws Exception {
    File explodedZipDir = temporaryFolder.newFolder( "explodedZip");

    zipDestination.getParentFile().mkdirs();
    File mavenScript = new File(explodedZipDir, "maven-0.9/bin/mvn");
    mavenScript.getParentFile().mkdirs();
    Files.write( mavenScript.toPath(), Arrays.asList( "something" ) );

    zipTo(explodedZipDir, zipDestination);
  }

  public void testCreateDist() throws Exception {
    File homeDir = install.createDist(configuration);

    Assert.assertEquals(mavenHomeDir, homeDir);
    Assert.assertTrue(homeDir.isDirectory());
    Assert.assertTrue(new File(homeDir, "bin/mvn").exists());
    Assert.assertTrue(zipDestination.exists());

    Assert.assertEquals(localDistribution, pathAssembler.getDistribution(configuration));
    Assert.assertEquals(distributionDir, localDistribution.getDistributionDir());
    Assert.assertEquals(zipDestination, localDistribution.getZipFile());

    // download.download(new URI("http://some/test"), distributionDir);
    // verify(download).download(new URI("http://some/test"), distributionDir);
  }

  @Test
  public void testCreateDistWithExistingDistribution() throws Exception {
      Files.createFile( zipDestination.toPath() );

    mavenHomeDir.mkdirs();
    File someFile = new File(mavenHomeDir, "some-file");
    Files.createFile( someFile.toPath() );

    File homeDir = install.createDist(configuration);

    Assert.assertEquals(mavenHomeDir, homeDir);
    Assert.assertTrue(mavenHomeDir.isDirectory());
    Assert.assertTrue(new File(homeDir, "some-file").exists());
    Assert.assertTrue(zipDestination.exists());

    Assert.assertEquals(localDistribution, pathAssembler.getDistribution(configuration));
    Assert.assertEquals(distributionDir, localDistribution.getDistributionDir());
    Assert.assertEquals(zipDestination, localDistribution.getZipFile());
  }

  @Test
  public void testCreateDistWithExistingDistAndZipAndAlwaysUnpackTrue() throws Exception {

    createTestZip(zipDestination);
    mavenHomeDir.mkdirs();
    File garbage = new File(mavenHomeDir, "garbage");
    Files.createFile( garbage.toPath() );

    configuration.setAlwaysUnpack(true);

    File homeDir = install.createDist(configuration);

    Assert.assertEquals(mavenHomeDir, homeDir);
    Assert.assertTrue(mavenHomeDir.isDirectory());
    Assert.assertFalse(new File(homeDir, "garbage").exists());
    Assert.assertTrue(zipDestination.exists());

    Assert.assertEquals(localDistribution, pathAssembler.getDistribution(configuration));
    Assert.assertEquals(distributionDir, localDistribution.getDistributionDir());
    Assert.assertEquals(zipDestination, localDistribution.getZipFile());
  }

  @Test
  public void testCreateDistWithExistingZipAndDistAndAlwaysDownloadTrue() throws Exception {

    createTestZip(zipDestination);
    mavenHomeDir.mkdirs();
    File garbage = new File(mavenHomeDir, "garbage");
    Files.createFile( garbage.toPath() );
    
    configuration.setAlwaysUnpack(true);

    File homeDir = install.createDist(configuration);

    Assert.assertEquals(mavenHomeDir, homeDir);
    Assert.assertTrue(mavenHomeDir.isDirectory());
    Assert.assertTrue(new File(homeDir, "bin/mvn").exists());
    Assert.assertFalse(new File(homeDir, "garbage").exists());
    Assert.assertTrue(zipDestination.exists());

    Assert.assertEquals(localDistribution, pathAssembler.getDistribution(configuration));
    Assert.assertEquals(distributionDir, localDistribution.getDistributionDir());
    Assert.assertEquals(zipDestination, localDistribution.getZipFile());

    // download.download(new URI("http://some/test"), distributionDir);
    // verify(download).download(new URI("http://some/test"), distributionDir);
  }

  public void zipTo(File directoryToZip, File zipFile) {
    Zip zip = new Zip();
    zip.setBasedir(directoryToZip);
    zip.setDestFile(zipFile);
    zip.setProject(new Project());

    Zip.WhenEmpty whenEmpty = new Zip.WhenEmpty();
    whenEmpty.setValue("create");
    zip.setWhenempty(whenEmpty);
    zip.execute();
  }

}
