package org.assetmanagement.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

class MaintenanceTest {
	
	static AssetManagementService obj;
	
	@BeforeAll
	static void connectionEstablishment() {
		obj = new AssetManagementServiceImpl();
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Close Connection");
		obj.endCon();
	}
	
	@Test
	void testPerformMaintenance() throws Exception {
		assertEquals(true,obj.performMaintenance(3, "2024-10-11", "Some Issue", 2000));
	}
	
	@Test
	void testPerformMaintenance2() throws Exception {
		assertEquals(false,obj.performMaintenance(5,"2024-20-10","Motor Issue",20000));
	}
	@Test
	void testPerformMaintenance3() throws Exception {
		assertEquals(false,obj.performMaintenance(7, null, "Screen Replacement", 1000));
	}
	
	@Test
	void testPerformMaintenance4() throws Exception {
		assertEquals(false,obj.performMaintenance(4, "2024-10-03", null, 1000));
	}
	
	
}