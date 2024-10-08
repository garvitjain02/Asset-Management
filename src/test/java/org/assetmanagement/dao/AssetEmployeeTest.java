package org.assetmanagement.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.assetmanagement.exception.AssetNotFoundException;
import org.assetmanagement.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AssetEmployeeTest {
	

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
	void testAssetId() {
		 int invalidAssetId = 999;  // Non-existing asset ID

	        // Assuming addAsset or another method will throw this exception
	        Exception exception = assertThrows(AssetNotFoundException.class, () -> {
	            obj.deleteAsset(invalidAssetId);  // or any method that checks asset existence
	        });

	        String expectedMessage = "Asset with ID " + invalidAssetId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	@Test
	void testAssetId1() {
		 int invalidAssetId = -999;  // Non-existing asset ID

	        // Assuming addAsset or another method will throw this exception
	        Exception exception = assertThrows(AssetNotFoundException.class, () -> {
	            obj.allocateAsset(invalidAssetId,2,"2024-10-10");  // or any method that checks asset existence
	        });

	        String expectedMessage = "Asset with ID " + invalidAssetId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testAssetId3() {
		 int invalidAssetId = -999;  // Non-existing asset ID

	        // Assuming addAsset or another method will throw this exception
	        Exception exception = assertThrows(AssetNotFoundException.class, () -> {
	            obj.deallocateAsset(invalidAssetId,2,"2024-10-10");  // or any method that checks asset existence
	        });

	        String expectedMessage = "Asset with ID " + invalidAssetId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testAssetId4() {
		 int invalidAssetId = -999;  // Non-existing asset ID

	        // Assuming addAsset or another method will throw this exception
	        Exception exception = assertThrows(AssetNotFoundException.class, () -> {
	            obj.performMaintenance(invalidAssetId, "2024-10-10", "description about issue", 300); // or any method that checks asset existence
	        });

	        String expectedMessage = "Asset with ID " + invalidAssetId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testAssetId5() {
		 int invalidAssetId = -999;  // Non-existing asset ID

	        // Assuming addAsset or another method will throw this exception
	        Exception exception = assertThrows(AssetNotFoundException.class, () -> {
	            obj.reserveAsset(invalidAssetId,2,"2024-10-10","2024-03-10","2024-04-10");  // or any method that checks asset existence
	        });

	        String expectedMessage = "Asset with ID " + invalidAssetId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testEmployeeId6() {
		 int invalidEmployeeId = -999;  // Non-existing Employee ID

	        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
	            obj.reserveAsset(2,invalidEmployeeId,"2024-10-10","2024-03-10","2024-04-10");  // or any method that checks asset existence
	        });

	        String expectedMessage = "Employee with ID " + invalidEmployeeId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testEmployeeId7() {
		 int invalidEmployeeId = -999;  

	        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
	            obj.deallocateAsset(2,invalidEmployeeId,"2024-10-10");  // or any method that checks asset existence
	        });

	        String expectedMessage = "Employee with ID " + invalidEmployeeId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testEmployeeId8() {
		 int invalidEmployeeId = -999;  

	        Exception exception = assertThrows(EmployeeNotFoundException.class, () -> {
	            obj.allocateAsset(2,invalidEmployeeId,"2024-10-10");  // or any method that checks asset existence
	        });

	        String expectedMessage = "Employee with ID " + invalidEmployeeId + " not found";
	        String actualMessage = exception.getMessage();

	        assertTrue(actualMessage.contains(expectedMessage));
	}
	
}