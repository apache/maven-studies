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
assert new File( basedir, 'build.log' ).exists();

content = new File( basedir, 'build.log' ).text;

boolean maven30 = content.contains( 'Apache Maven 3.0.' );
boolean maven31 = content.contains( 'Apache Maven 3.1.' );

assert !content.contains( 'LifecycleParticipantDemoPlexus afterSessionStart' ); // not yet activated when POM extension
assert content.contains( 'LifecycleParticipantDemoPlexus afterProjectsRead' );
if ( maven30 || maven31 ) {
  // afterSessionEnd API available only since Maven 3.2.1
  assert !content.contains( 'LifecycleParticipantDemoPlexus afterSessionEnd' );
} else { 
  assert content.contains( 'LifecycleParticipantDemoPlexus afterSessionEnd' );
}

if ( maven30 ) {
  assert !content.contains( 'LifecycleParticipantDemoJsr330 after' );
} else {
  assert !content.contains( 'LifecycleParticipantDemoJsr330 afterSessionStart' ); // not yet activated when POM extension
  assert content.contains( 'LifecycleParticipantDemoJsr330 afterProjectsRead' );
  if ( maven31 ) {
    // afterSessionEnd API available only since Maven 3.2.1
    assert !content.contains( 'LifecycleParticipantDemoJsr330 afterSessionEnd' );
  } else { 
    assert content.contains( 'LifecycleParticipantDemoJsr330 afterSessionEnd' );
  }
}

// EventSpy not activated when POM extension
assert !content.contains( 'EventSpyDemo init:' );
assert !content.contains( 'EventSpyDemo close' );

return true;