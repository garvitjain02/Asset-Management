package org.assetmanagement.main;
import org.assetmanagement.entity.*;
import org.assetmanagement.dao.*;
import java.util.Scanner;

public class MainModule {

	public static void main(String[] args){

		Scanner sc=new Scanner(System.in);
		AssetManagementService assetService = new AssetManagementServiceImpl();
		
		do {
			System.out.println("\n\n---Asset Management Menu---");
			System.out.println("1.Add Asset");
			System.out.println("2.Update Asset");
			System.out.println("3.DeleteAsset");
			System.out.println("4.Allocate Asset");
			System.out.println("5.Deallocate Asset");
			System.out.println("6.Perform Maintenance");
			System.out.println("7.Reserve Asset");
			System.out.println("8.Withdraw Asset");
			System.out.println("9.Exit");
			System.out.println("\nEnter the option(1-9):");
			int option = sc.nextInt();
			sc.nextLine();
			if (option==9) {
				try {
					assetService.endCon();
					System.out.println("Closing Connection String");
				}
				catch(Exception e) {
					e.printStackTrace();
				}
				break;
			}
			else {
				switch(option) {
					case 1->{
						System.out.println("<-Adding Asset->");
						System.out.println("Enter asset name:");
						String name = sc.nextLine();
						System.out.println("Enter asset type:");
						String type = sc.nextLine();
						System.out.println("Enter asset serial number:");
						String serialNumber = sc.nextLine();
						System.out.println("Enter asset purchase date(YYYY-MM-DD):");
						String purchaseDate = sc.nextLine();
						System.out.println("Enter asset location:");
						String location = sc.nextLine();
						System.out.println("Enter asset status:");
						String status = sc.nextLine();
						System.out.println("Enter asset owner id(if not -1)");
						int ownerId=sc.nextInt();
						
						Assets asset = new Assets(-1,name,type,serialNumber,purchaseDate,location,status,ownerId);
						if(assetService.addAsset(asset)) {
							System.out.println("Asset added successfully.");
						}
						else {
							System.out.println("\nOperation Failure");
						}
					}
				
					case 2->{
						System.out.println("<-Updating Asset->");
						System.out.println("Enter the AssetID:");
						int inputAssetId=sc.nextInt();
						Assets asset = assetService.getAsset(inputAssetId);
						while(asset!=null) 
						{
							System.out.println("1.Name");
							System.out.println("2.Type");
							System.out.println("3.Serial Number");
							System.out.println("4.Purchase Date");
							System.out.println("5.Location");
							System.out.println("6.Status");
							System.out.println("7.OwnerID");
							System.out.println("8.Exit");
							System.out.println("Select the fields of asset to update:");
							int updateOption = sc.nextInt();
							sc.nextLine();
							if(updateOption==8) {
								break;
							}
							else {
								switch(updateOption) {
									case 1->{
										System.out.println("Enter the new Name:");
										String name = sc.nextLine();
										asset.setName(name);
									}
									case 2->{
										System.out.println("Enter the new Type:");
										String type = sc.nextLine();
										asset.setType(type);
									}
									case 3->{
										System.out.println("Enter the new Serial Number:");
										String serialNumber=sc.nextLine();
										asset.setSerialNumber(serialNumber);
									}
									case 4->{
										System.out.println("Enter the new Purchase Date:");
										String purchaseDate = sc.nextLine();
										asset.setPurchaseDate(purchaseDate);
									}
									case 5->{
										System.out.println("Enter the new Location:");
										String location = sc.nextLine();
										asset.setLocation(location);
									}
									case 6->{
										System.out.println("Enter the new Status:");
										String status = sc.nextLine();
										asset.setStatus(status);
									}
									case 7->{
										System.out.println("Enter the new OwnerID:");
										int ownerId=sc.nextInt();
										sc.nextLine();
										asset.setOwnerId(ownerId);
										
									}
									default->{
										System.out.println("Invalid UpdateOption.");
									}
								}
							}
						}
						try {
							if(asset!=null && assetService.updateAsset(asset)) {
								System.out.println("Asset updated successfully.");
							}
							else {
								System.out.println("Operation Failure.");
							}
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
							System.out.println("\nOperation Failure.");
						}
						
					}
					case 3-> {
						System.out.println("<-Delete Asset->");
						System.out.println("Enter the AssetId:");
						int inputAsset = sc.nextInt();
						try {
							if(assetService.deleteAsset(inputAsset)) {
								System.out.println("Asset deleted Successfully");
							}
							else {
								System.out.println("\nOperation Failure.");
							}
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
							System.out.println("\nOperation Failure.");
						}
					}
					case 4-> {
						System.out.println("<-Allocate Asset->");
						System.out.println("Enter the AssetID:");
						int assetId=sc.nextInt();
						System.out.println("Enter the EmployeeID:");
						int employeeId=sc.nextInt();
						sc.nextLine();
						System.out.println("Enter the Allocation Date(YYYY-MM-DD):");
						String allocationDate = sc.nextLine();
						
						try {
							if(assetService.allocateAsset(assetId, employeeId, allocationDate)) {
								System.out.println("Asset Allocated Successfully.");
							}
							else {
								System.out.println("\nOperation Failure.");
							}
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
							System.out.println("\nOperation Failure.");
						}
						
					}
					case 5->{
						System.out.println("<-Deallocate Asset->");
						System.out.println("Enter the AssetID:");
						int assetId=sc.nextInt();
						System.out.println("Enter the EmployeeID:");
						int employeeId=sc.nextInt();
						sc.nextLine();
						System.out.println("Enter the Return Date(YYYY-MM-DD):");
						String returnDate = sc.nextLine();
						
						try {
							if(assetService.deallocateAsset(assetId, employeeId, returnDate)) {
								System.out.println("Asset Deallocated Successfully.");
							}
							else {
								System.out.println("\nOperation Failure.");
							}
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
							System.out.println("\nOperation Failure.");
						}
						
					}
					case 6->{
						System.out.println("<-Perform Maintenance->");
						System.out.println("Enter the AssetID:");
						int assetId=sc.nextInt();
						sc.nextLine();
						
						System.out.println("Enter the Maintenance Date(YYYY-MM-DD):");
						String maintenanceDate = sc.nextLine();
						
						System.out.println("Enter the Description:");
						String description = sc.nextLine();
						
						System.out.println("Enter the Cost:");
						Double cost = sc.nextDouble();
						
						try {
							if (assetService.performMaintenance(assetId, maintenanceDate, description, cost)) {
								System.out.println("Maintenance Recorded Successfully");
							}
							else {
								System.out.println("\nOperation Failure.");
							}
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
							System.out.println("\nOperation Failure.");
						}
						
					}
					case 7-> {
						System.out.println("<-Reserve Asset->");
						System.out.println("Enter the AssetID:");
						int assetId= sc.nextInt();
						System.out.println("Enter the EmployeeID:");
						int employeeId=sc.nextInt();
						sc.nextLine();
						System.out.println("Enter the Reservation Date(YYYY-MM-DD):");
						String reservationDate=sc.nextLine();
						System.out.println("Enter the Start Date(YYYY-MM-DD):");
						String startDate = sc.nextLine();
						System.out.println("Enter the End Date(YYYY-MM-DD):");
						String endDate=sc.nextLine();
						
						try {
							if(assetService.reserveAsset(assetId, employeeId, reservationDate, startDate, endDate)) {
								System.out.println("Asset Reserved Successfully.");
							}
							else {
								System.out.println("\nOperation Failure.");
							}
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
							System.out.println("\nOperation Failure.");
						}
						
						
					}
					case 8-> {
						System.out.println("<-Withdraw Asset->");
						System.out.println("Enter ReservationID:");
						int reservationId=sc.nextInt();
						
						try {
							if(assetService.withdrawReservation(reservationId)) {
								System.out.println("Asset Reservation Withdrawn Successfully.");
							}
							else {
								System.out.println("Operation Failure.");
							}
						}
						catch(Exception e) {
							System.out.println(e.getMessage());
							System.out.println("\nOperation Failure.");
						}
						
						
					}
					default-> System.out.println("Invalid Option.");
				}
			}
		}while(true);
		sc.close();
	}

}