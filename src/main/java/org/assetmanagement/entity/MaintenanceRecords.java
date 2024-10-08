package org.assetmanagement.entity;

public class MaintenanceRecords {
	private int maintenanceId;
	private int assetId;
	private String maintenanceDate;
	private String description;
	private double cost;
	
	public MaintenanceRecords() {
		maintenanceId=0;
		assetId=0;
		maintenanceDate="";
		description="";
		cost=0.0;
	}
	
	public MaintenanceRecords(int maintenanceId,int assetId, String maintenanceDate, String description, double cost) {
		this.maintenanceId=maintenanceId;
		this.assetId=assetId;
		this.maintenanceDate=maintenanceDate;
		this.description=description;
		this.cost=cost;		
	}
	
	public String getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(String maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public int getMaintenanceId() {
		return maintenanceId;
	}
	public void setMaintenanceId(int maintenanceId) {
		this.maintenanceId = maintenanceId;
	}
	
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	
	@Override
	public String toString() {
		return String.format(
				"Maintenance ID : %d\nAsset ID : %d\nMaintenance Date : %s\nDescription : %s\nCost : %s\n",
				this.maintenanceId,
				this.assetId,
				this.maintenanceDate,
				this.description,
				this.cost
				);
	}
	
}