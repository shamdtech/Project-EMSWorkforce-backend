package com.employee.department.service.constants;

public class DatabaseConstants {
	
	public static enum DepartmentStatus {
		ACTIVE(1),
		NOT_ACTIVE(0);
		
		private int status;

	    private DepartmentStatus(int status) {
	      this.status = status;
	    }

	    public int value() {
	      return this.status;
	    }
	     
	}

}
