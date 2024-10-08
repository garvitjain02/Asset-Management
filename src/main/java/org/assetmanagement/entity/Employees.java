package org.assetmanagement.entity;

public class Employees {
	protected int employeeId;
	private String name;
	private String department;
	private String email;
	private String password;
	
	public Employees() {
		employeeId=0;
		name="";
		department="";
		email="";
		password="";
	}
	
	public Employees(int employeeId, String name, String department, String email, String password) {
		this.employeeId=employeeId;
		this.name=name;
		this.department=department;
		this.email=email;
		this.password=password;
	}
	
	//	getter methods
	public int getEmployeeId() {
		return this.employeeId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDepartment() {
		return this.department;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	//	setter methods
	public void setEmployeeId(int employeeId) {
		this.employeeId=employeeId;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	
	public void setDepartment(String department) {
		this.department=department;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	@Override
	public String toString() {
		return String.format(
				"\nEmployeeID : %d\nName : %s\nDepartment : %s\nEmail : %s\nPassword : %s",
				this.employeeId,
				this.name,this.department,
				this.email,
				this.password
				);
	}
}

//employees table:
//• employee_id (Primary Key)
//• name
//• department.
//• email.
//• password.
