package org.assetmanagement.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.assetmanagement.util.*;
import org.assetmanagement.entity.Assets;
import org.assetmanagement.exception.*;

public class AssetManagementServiceImpl implements AssetManagementService{
	
	public static Connection con = null;
	
	public void getCon () {
		if (con == null) {
			con = DBConnUtil.getConnection();
		}
	}
	
	public void endCon () {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	@Override
	public Assets getAsset(int assetId) {
		Assets asset = null;
		PreparedStatement psmt = null;
		ResultSet rs  = null;
		getCon();
		try{
			
			psmt = con.prepareStatement("Select * from assets where asset_id=?");
			psmt.setInt(1, assetId);
			
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				System.out.println("Asset found successfully");
				asset = new Assets(
						rs.getInt("asset_id"),
						rs.getString("name"),
						rs.getString("type"),
						rs.getString("serial_number"),
						rs.getString("purchase_date"),
						rs.getString("location"),
						rs.getString("status"),
						rs.getInt("owner_id"));
				
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			
			//Close ResultSet
			if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		    // Close PreparedStatements
		    if (psmt != null) {
		        try {
		            psmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		}
		return asset;
	}
	
	@Override
	public boolean addAsset(Assets asset) {
		
		boolean added=false;
		PreparedStatement psmt = null;
		getCon();
		String INSERT_ASSET_QUERY="insert into assets(name,type,serial_number,purchase_date,location,status,owner_id) values(?,?,?,?,?,?,?)";
		try{
				psmt = con.prepareStatement(INSERT_ASSET_QUERY);
				
				psmt.setString(1,asset.getName());
				psmt.setString(2, asset.getType());
				psmt.setString(3, asset.getSerialNumber());
				psmt.setString(4, asset.getPurchaseDate());
				psmt.setString(5, asset.getLocation());
				psmt.setString(6, asset.getStatus());
				psmt.setInt(7, asset.getOwnerId());
				
				
				int rowsEffected = psmt.executeUpdate();
				if(rowsEffected>0) {
					added=true;
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		finally{
			
			// Close PreparedStatements
		    if (psmt != null) {
		        try {
		            psmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }

		}
		return added;
		
	}

	@Override
	public boolean updateAsset(Assets asset) throws AssetNotFoundException{
		
		boolean updated=false;
		PreparedStatement psmt = null;
		getCon();
		String UPDATE_ASSET_QUERY="update assets set name=?,type=?,serial_number=?,purchase_date=?,location=?,status=?,owner_id=? where asset_id=?";
			try{
				checkAsset(asset.getAssetId());
				psmt = con.prepareStatement(UPDATE_ASSET_QUERY);

				psmt.setString(1, asset.getName());
				psmt.setString(2, asset.getType());
				psmt.setString(3, asset.getSerialNumber());
				psmt.setString(4, asset.getPurchaseDate());
				psmt.setString(5, asset.getLocation());
				psmt.setString(6, asset.getStatus());
				psmt.setInt(7, asset.getOwnerId());
				psmt.setInt(8, asset.getAssetId());
				
				int rowsEffected = psmt.executeUpdate();
				if(rowsEffected>0) {
					updated=true;
				}
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			finally{
				
				// Close PreparedStatements
			    if (psmt != null) {
			        try {
			            psmt.close();
			        } catch (SQLException e) {
			            System.out.println(e.getMessage());
			        }
			    }
			}
		
		return updated;
	}

	@Override
	public boolean deleteAsset(int assetId) throws AssetNotFoundException{
		boolean deleted = false;
		int maxAssetId = 0;
		PreparedStatement psmt = null;
		PreparedStatement maxIdPsmt = null;
		PreparedStatement resetPsmt = null;
		ResultSet rs = null;
		getCon();
		String DELETE_ASSET_QUERY="delete from assets where asset_id=?";
		String RESET_AUTOINCREMENT_QUERY = "alter table assets auto_increment = ?";
			try{
//				if(!checkAsset(assetId) ) {
//	    			throw new AssetNotFoundException("Asset with ID "+assetId+" not found.");
//				}
				checkAsset(assetId);
				psmt = con.prepareStatement(DELETE_ASSET_QUERY);
				psmt.setInt(1, assetId);
				
				int rowsEffected = psmt.executeUpdate();
				
				if(rowsEffected>0) {
					deleted=true;
				}
				
				if(deleted) {
					
					String GET_MAX_ASSET_ID_QUERY = "select max(asset_id) from assets";
			        maxIdPsmt = con.prepareStatement(GET_MAX_ASSET_ID_QUERY);
			        rs = maxIdPsmt.executeQuery();
			        if (rs.next()) {
			        	maxAssetId = rs.getInt(1);
			        }

			        // Reset auto_increment to max(asset_id) + 1
			        resetPsmt = con.prepareStatement(RESET_AUTOINCREMENT_QUERY); 
			        resetPsmt.setInt(1, maxAssetId + 1);  // Set to max + 1
			        resetPsmt.executeUpdate();
					
				}
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			finally {
				
				try {
					if (rs != null) rs.close();
					if (psmt != null) psmt.close();
					if (maxIdPsmt != null) maxIdPsmt.close();
					if (resetPsmt != null) resetPsmt.close();
				}
				catch (SQLException e) {
			            System.out.println(e.getMessage());
			    }
			}
		return deleted;
	}

	@Override
	public boolean allocateAsset(int assetId, int employeeId, String allocationDate) throws AssetNotFoundException, EmployeeNotFoundException{
	    boolean allocated = false;
	    boolean assetAvailable = false;

	    
	    PreparedStatement psmt = null;
	    PreparedStatement assetSearch = null;
	    PreparedStatement assetUpdate = null;

	    ResultSet assetList = null;
	    String ALLOCATE_ASSET_QUERY="insert into asset_allocations (asset_id, employee_id, allocation_date) values(?,?,?)";
	    getCon();
	    	try {
	    		checkAsset(assetId);
	    		checkEmployee(employeeId);
	    		// Check if the asset exists and is in use
		        assetSearch = con.prepareStatement("SELECT * FROM assets WHERE asset_id=? AND status='in use'");
		        assetSearch.setInt(1, assetId);
		        assetList = assetSearch.executeQuery();
		        if (assetList.next()) {
		            assetAvailable = true;
		        }
		        if(assetAvailable) {
		        	psmt = con.prepareStatement(ALLOCATE_ASSET_QUERY);
		            psmt.setInt(1, assetId);
		            psmt.setInt(2, employeeId);
		            psmt.setString(3, allocationDate);
		            int rowsAffected = psmt.executeUpdate();
		            
		            if (rowsAffected > 0) {
		                allocated = true;
		                
		                assetUpdate = con.prepareStatement("update assets set status='allocated' where asset_id=?");
		                assetUpdate.setInt(1,assetId);
		                assetUpdate.executeUpdate();
		            }
		        }
		        else {
		        	System.out.println("Asset is not available to allocate.");
		        }
	    	}
	    	catch (SQLException e) {
		        System.out.println(e.getMessage());
		    } 
	    	finally {
		        // Close resources in the reverse order of their opening
		        try {
		            if (assetList != null) assetList.close();
		            if(assetUpdate !=null) assetUpdate.close();
		            if (assetSearch != null) assetSearch.close();
		            if (psmt != null) psmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
	    return allocated;
	}

	@Override
	public boolean deallocateAsset(int assetId, int employeeId, String returnDate) throws AssetNotFoundException, EmployeeNotFoundException {
		String allocId = "SELECT allocation_id FROM asset_allocations WHERE asset_id = " + assetId + " AND employee_id = " + employeeId + " ORDER BY allocation_date DESC LIMIT 1";
		
		getCon();
		int r = 0;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement assetUpdate = null;
		try {
			checkAsset(assetId);
			checkEmployee(employeeId);
			
			stmt = con.createStatement();
			rs = stmt.executeQuery(allocId);
			
			int id = 0;
			if (rs.next()) {
				id = rs.getInt(1);
			}
			
			String sql = "UPDATE asset_allocations SET return_date = \"" + returnDate + "\" WHERE allocation_id = " + String.valueOf(id);
			r = stmt.executeUpdate(sql);
			
			if(r > 0) {
				assetUpdate = con.prepareStatement("update assets set status='in use' where asset_id=?");
                assetUpdate.setInt(1,assetId);
                assetUpdate.executeUpdate();
			}
		}
		catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		    
		    if (rs != null) {
		        try {
		        	rs.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		}
		
		if (r > 0)
			return true;
		return false;
	}
	
	@Override
	public boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost) throws AssetNotFoundException {
		String sql = "INSERT INTO maintenance_records(asset_id, maintenance_date, description, cost) VALUES (?, ?, ?, ?)";
				
		getCon();
		int r = 0;
		PreparedStatement stmt = null;
		try {
			checkAsset(assetId);			
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, assetId);
			stmt.setString(2, maintenanceDate);
			stmt.setString(3, description);
			stmt.setDouble(4, cost);
			
			r = stmt.executeUpdate();
			
		}  catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		}
		
		if (r > 0)
			return true;
		return false;
	}

	@Override
	public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) throws AssetNotFoundException, EmployeeNotFoundException, AssetNotMaintainException {
		String sql = "INSERT INTO reservations(asset_id, employee_id, reservation_date, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
		
		getCon();
		int r = 0;
		PreparedStatement stmt = null;
		try {
			checkAsset(assetId);	
			checkEmployee(employeeId);
			checkAssetMaintenance(assetId);
			
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, assetId);
			stmt.setInt(2, employeeId);
			stmt.setString(3, reservationDate);
			stmt.setString(4, startDate);
			stmt.setString(5, endDate);
			stmt.setString(6, "pending");
			
			r = stmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		}
		
		if (r > 0)
			return true;
		return false;
	}

	@Override
	public boolean withdrawReservation(int reservationId) throws ReservationNotFoundException {
		String sql = "DELETE FROM reservations WHERE reservation_id = " + reservationId;

		getCon();
		int r = 0;
		PreparedStatement resetPsmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			checkReservation(reservationId);
			stmt = con.createStatement();			
			r = stmt.executeUpdate(sql);
			
			rs = stmt.executeQuery("SELECT max(reservation_id) from reservations");
			
			if (r > 0 && rs.next()) {
				resetPsmt = con.prepareStatement("ALTER TABLE reservations AUTO_INCREMENT = " + rs.getInt(1)); 
		        resetPsmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		    
		    if (resetPsmt != null) {
		        try {
		        	resetPsmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		}
		
		if (r > 0)
			return true;
		return false;
	}
	
	public boolean checkAsset(int assetId) throws AssetNotFoundException  {
		String sql = "Select COUNT(*) from assets where asset_id=?";
		getCon();
		
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Boolean res = false;
		try{
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, assetId);
			
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt(1);
	            if (count > 0) {
	                res = true;  // Asset exists
	            } else {
	                throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
	            }
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
						
			//Close ResultSet
			if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
			
		    // Close PreparedStatements
		    if (psmt != null) {
		        try {
		            psmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }		    
		}
		
		return res;
	}

	public boolean checkEmployee(int employeeId) throws EmployeeNotFoundException {
		String sql = "Select COUNT(*) from employees where employee_id=?";
		getCon();
		
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Boolean res = false;
		try{
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, employeeId);
			
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count > 0) {
	                res = true;  // Asset exists
	            } else {
	                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
	            }			
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
						
			//Close ResultSet
			if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
			
		    // Close PreparedStatements
		    if (psmt != null) {
		        try {
		            psmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }		    
		}
		
		return res;
	}

	public boolean checkReservation(int reservationId) throws ReservationNotFoundException {
		String sql = "Select COUNT(*) from reservations where reservation_id=?";
		getCon();
		
		PreparedStatement psmt = null;
		ResultSet rs = null;
		Boolean res = false;
		try{
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, reservationId);
			
			rs = psmt.executeQuery();
			
			if (rs.next()) {
				res = true;	
				int count = rs.getInt(1);
				if (count > 0) {
	                res = true;  // Asset exists
	            } else {
	                throw new ReservationNotFoundException("Reservation with ID " + reservationId + " not found.");
	            }	
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		finally {
						
			//Close ResultSet
			if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
			
		    // Close PreparedStatements
		    if (psmt != null) {
		        try {
		            psmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }		    
		}
		
		return res;
	}

	public boolean checkAssetMaintenance(int assetId) throws AssetNotMaintainException, AssetNotFoundException  {
		String sql = "select DATEDIFF(CURDATE(), (select maintenance_date from maintenance_records where asset_id =" + assetId + " order by maintenance_date desc limit 1)) from dual";
	
		Statement stmt = null;
		ResultSet rs = null, rs1 = null, rs2 = null;
		Boolean res = false;
		try {
			checkAsset(assetId);	
			
			stmt = con.createStatement();
			rs2 = stmt.executeQuery("SELECT COUNT(*) FROM maintenance_records WHERE asset_id = " + assetId);
			int c = 0;
			if (rs2.next())
				c = rs2.getInt(1);
			
			if (c > 0) {
				rs = stmt.executeQuery(sql);
				
				if (rs.next()) {
					int d = rs.getInt(1);
					if (d < 730)
						res = true;
					else 
						throw new AssetNotMaintainException ("Asset needs Maintenance before Allocation");
				}
			}
			else {
				rs1 = stmt.executeQuery("select DATEDIFF(CURDATE(), (SELECT purchase_date FROM assets WHERE asset_id = " + assetId + "))");
				if (rs1.next()) {
					int d = rs1.getInt(1);
					if (d < 730)
						res = true;
					else 
						throw new AssetNotMaintainException ("Asset needs Maintenance before Allocation");
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
		    if (stmt != null) {
		        try {
		        	stmt.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		    
			if (rs != null) {
		        try {
		            rs.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
			
			if (rs1 != null) {
		        try {
		            rs1.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
			
			if (rs2 != null) {
		        try {
		            rs2.close();
		        } catch (SQLException e) {
		            System.out.println(e.getMessage());
		        }
		    }
		}
		
		return res;
	}
}