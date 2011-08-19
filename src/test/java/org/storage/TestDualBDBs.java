/**
 * 
 * Copyright (c) 2005-2011, AtKaaZ
 * All rights reserved.
 * this file is part of DemLinks
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 
 * * Neither the name of 'DemLinks' nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.storage;



import static org.junit.Assert.*;

import org.JUnitCommons.*;
import org.dml.storage.berkeleydb.commons.*;
import org.dml.storage.berkeleydb.generics.*;
import org.dml.storage.commons.*;
import org.junit.*;



public class TestDualBDBs
		extends JUnitHooker
{
	
	
	private StorageGeneric	jeStorage;
	private StorageGeneric	jniStorage;
	
	
	
	@Before
	public void setUp() {
		jeStorage = BaseTest_for_Storage.setUpStorage( StorageType.BDB, BDBStorageSubType.JE );
		jniStorage = BaseTest_for_Storage.setUpStorage( StorageType.BDB, BDBStorageSubType.JNI );
	}
	
	
	@After
	public void tearDown() {
		if ( null != jeStorage ) {
			jeStorage.shutdown();
		}
		if ( null != jniStorage ) {
			jniStorage.shutdown();
		}
	}
	
	
	@Test
	public void nodeEqualsFromTwoDiffStorages() {
		final NodeGeneric jeNode = jeStorage.createNewUniqueNode();
		final NodeGeneric jniNode = jniStorage.createNewUniqueNode();
		assertTrue( jeNode.getId() == jniNode.getId() );
		assertFalse( jeNode.equals( jniNode ) );
		assertFalse( jniNode.equals( jeNode ) );
		
		final NodeBDB jeNodeSecondInst = NodeBDB.getBDBNodeInstance( (StorageBDBGeneric)jeStorage, jeNode.getId() );
		assertTrue( jeNode.getId() == jeNodeSecondInst.getId() );
		assertTrue( jeNode.equals( jeNodeSecondInst ) );
		assertTrue( jeNodeSecondInst.equals( jeNode ) );
		
		assertTrue( jeNodeSecondInst.getId() == jniNode.getId() );
		assertFalse( jeNodeSecondInst.equals( jniNode ) );
		assertFalse( jniNode.equals( jeNodeSecondInst ) );
		
		final NodeBDB jeNodeDifferent = NodeBDB.getBDBNodeInstance( (StorageBDBGeneric)jeStorage, jniNode.getId() + 1 );
		
		assertFalse( jeNode.getId() == jeNodeDifferent.getId() );
		assertFalse( jeNode.equals( jeNodeDifferent ) );
		assertFalse( jeNodeDifferent.equals( jeNode ) );
		
		assertFalse( jeNodeDifferent.getId() == jniNode.getId() );
		assertFalse( jeNodeDifferent.equals( jniNode ) );
		assertFalse( jniNode.equals( jeNodeDifferent ) );
		
	}
}
