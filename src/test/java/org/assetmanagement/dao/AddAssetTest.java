package org.assetmanagement.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.assetmanagement.entity.*;

class AddAssetTest {

	static AssetManagementService obj = new AssetManagementServiceImpl();
	
	//Test Case to Insert new Asset
	@Test
	void testAddAsset() {
		Assets asset = new Assets(-1, "MacBook Pro", "Laptop", "LP3487", "2024-10-05", "Delhi", "in use", 1);
		assertEquals (true, obj.addAsset(asset));		
	}
	
	//Test Case to Add Asset with already existing Serial Number (i.e Asset already Exists)
	@Test
	void testAddAsset2() {
		Assets asset = new Assets(-1, "MacBook Pro", "Laptop", "LP3487", "2024-10-05", "Delhi", "in use", 1);
		assertEquals (false, obj.addAsset(asset));		
	}
	
	//Test Case with null date
	@Test
	void testAddAsset3() {
		Assets asset = new Assets(-1, "MacBook Pro", "Laptop", "LP3487", "", "Delhi", "in use", 1);
		assertEquals (false, obj.addAsset(asset));		
	}
	
	//Test Case with Invalid Date Format
	@Test
	void testAddAsset4() {
		Assets asset = new Assets(-1, "MacBook Pro", "Laptop", "LP3487", "10-10-2024", "Delhi", "in use", 1);
		assertEquals (false, obj.addAsset(asset));		
	}
	
	//Test Case with Name Null
	@Test
	void testAddAsset5() {
		Assets asset = new Assets(-1, "", "Laptop", "LP3487", "10-10-2024", "Delhi", "in use", 1);
		assertEquals (false, obj.addAsset(asset));		
	}
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		obj = new AssetManagementServiceImpl();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Close Connection");
		obj.endCon();
	}

}
