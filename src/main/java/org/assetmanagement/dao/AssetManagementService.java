package org.assetmanagement.dao;

import org.assetmanagement.entity.Assets;
import org.assetmanagement.exception.AssetNotFoundException;
import org.assetmanagement.exception.AssetNotMaintainException;
import org.assetmanagement.exception.EmployeeNotFoundException;
import org.assetmanagement.exception.ReservationNotFoundException;

public interface AssetManagementService {
	public boolean addAsset(Assets asset);
	public boolean updateAsset(Assets asset) throws AssetNotFoundException;
	public boolean deleteAsset(int assetId) throws AssetNotFoundException;
	public boolean allocateAsset(int assetId, int employeeId, String allocationDate) throws AssetNotFoundException, EmployeeNotFoundException;
	boolean deallocateAsset(int assetId, int employeeId, String returnDate) throws AssetNotFoundException, EmployeeNotFoundException;
	boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost) throws AssetNotFoundException ;
	boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) throws AssetNotFoundException, EmployeeNotFoundException, AssetNotMaintainException, AssetNotMaintainException ;
	boolean withdrawReservation(int reservationId) throws ReservationNotFoundException ;
	public void endCon ();
	public boolean checkAsset(int assetId) throws AssetNotFoundException;
	Assets getAsset(int assetId);
}