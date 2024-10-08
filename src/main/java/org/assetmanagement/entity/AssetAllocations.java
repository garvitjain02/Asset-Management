package org.assetmanagement.entity;

public class AssetAllocations {
	
	private int allocationId;
	private int assetId;
	private int employeeId;
	private String allocationDate;
	private String returnDate;
	
	public AssetAllocations() {
		allocationId=0;
		assetId=0;
		employeeId=0;
		allocationDate="";
		returnDate="";
	}
	
	public AssetAllocations(int allocationId, int assetId, int employeeId, String allocationDate, String returnDate) {
		this.allocationId=allocationId;
		this.assetId=assetId;
		this.employeeId=employeeId;
		this.allocationDate=allocationDate;
		this.returnDate=returnDate;
	}
	
	public int getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(int allocationId) {
		this.allocationId = allocationId;
	}
	
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	public String getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(String allocationDate) {
		this.allocationDate = allocationDate;
	}
	
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public String toString() {
		return String.format(
				"Allocation ID : %d\nAsset ID : %d\nEmployee ID : %d\nAllocation Date : %s\nReturn Date : %s",
				this.allocationId,
				this.assetId,
				this.employeeId,
				this.allocationDate,
				this.returnDate
				);
	}
}
