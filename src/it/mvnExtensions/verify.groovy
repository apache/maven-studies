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

assert content.contains( 'LifecycleParticipantDemoPlexus afterSessionStart' );
assert content.contains( 'LifecycleParticipantDemoPlexus afterProjectsRead' );
// do not test since valid only since Maven 3.2.1: previous tests are sufficient
//assert content.contains( 'LifecycleParticipantDemoPlexus afterSessionEnd' );

assert content.contains( 'LifecycleParticipantDemoJsr330 afterSessionStart' );
assert content.contains( 'LifecycleParticipantDemoJsr330 afterProjectsRead' );
// do not test since valid only since Maven 3.2.1: previous tests are sufficient
//assert content.contains( 'LifecycleParticipantDemoJsr330 afterSessionEnd' );

assert content.contains( 'EventSpyDemo init:' );
assert content.contains( 'EventSpyDemo close' );

return true;