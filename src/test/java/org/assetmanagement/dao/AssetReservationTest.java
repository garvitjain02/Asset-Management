package org.assetmanagement.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AssetReservationTest {
	
	static AssetManagementService obj;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		obj = new AssetManagementServiceImpl();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("Close Connection");
		obj.endCon();
	}

	//Check Valid Entry
	@Test
	void testReserveAsset1() throws Exception{
		assertEquals (true, obj.reserveAsset(1, 10, "2024-10-05", "2024-10-10", "2024-10-15"));
	}
	
	//Check reservationDate is null
	@Test
	void testReserveAsset10() throws Exception{
		assertEquals (false, obj.reserveAsset(1, 10, "", "2024-10-01", "2024-10-05"));
	}
			
	//Check startDate is null
	@Test
	void testReserveAsset11() throws Exception{
		assertEquals (false, obj.reserveAsset(10, 1, "2024-10-10", "", "2024-10-05"));
	}
		
	//Check endDate is null
	@Test
	void testReserveAsset12() throws Exception{
		assertEquals (false, obj.reserveAsset(10, 10, "2024-10-10", "2024-10-01", ""));
	}
		
	//Check reservationDate with different Format
	@Test
	void testReserveAsset13() throws Exception{
		assertEquals (false, obj.reserveAsset(1, 9, "10-20-2013", "2024-10-01", "2024-10-05"));
	}
		
	//Check startDate with different Format
	@Test
	void testReserveAsset14() throws Exception{
		assertEquals (false, obj.reserveAsset(9, 5, "2024-10-10", "05-2024-10", "2024-10-05"));
	}
		
	//Check endDate with different Format
	@Test
	void testReserveAsset15() throws Exception{
		assertEquals (false, obj.reserveAsset(10, 5, "2024-10-10", "2024-10-01", "10-20-1234"));
	}
	
/*	//Check with Invalid assetId
	@Test
	void testReserveAsset2() throws Exception{
		assertEquals (false, obj.reserveAsset(100, 10, "2024-10-05", "2024-10-10", "2024-10-15"));
	}
	
	//Check with Invalid employeeId
	@Test
	void testReserveAsset3() throws Exception{
		assertEquals (false, obj.reserveAsset(1, 100, "2024-10-05", "2024-10-10", "2024-10-15"));
	}
	
	//Check with Invalid employeeId and assetId both
	@Test
	void testReserveAsset4() throws Exception{
		assertEquals (false, obj.reserveAsset(100, 100, "2024-10-05", "2024-10-01", "2024-10-15"));
	}
		
	//Check startDate is greater than reservationDate
	@Test
	void testReserveAsset5() throws Exception{
		assertEquals (false, obj.reserveAsset(100, 100, "2024-10-05", "2024-10-01", "2024-10-15"));
	}
	
	//Check endDate is greater than reservationDate
	@Test
	void testReserveAsset6() throws Exception{
		assertEquals (false, obj.reserveAsset(100, 100, "2024-10-10", "2024-10-10", "2024-10-05"));
	}
	
	//Check startDate and endDate is greater than reservation Date
	@Test
	void testReserveAsset7() throws Exception{
		assertEquals (false, obj.reserveAsset(100, 100, "2024-10-10", "2024-10-01", "2024-10-05"));
	}
	
	//Check endDate is greater than startDate
	@Test
	void testReserveAsset8() throws Exception{
		assertEquals (false, obj.reserveAsset(100, 100, "2024-10-10", "2024-10-01", "2024-09-05"));
	}
	
	//Check reservationDate is less than Current Date
	@Test
	void testReserveAsset9() throws Exception{
		assertEquals (false, obj.reserveAsset(100, 100, "2024-10-10", "2024-10-01", "2024-10-05"));
	}*/
}