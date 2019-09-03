package com.test.employee.util;

public enum EmployeeConstant {
	
	UNEXPECTED_ERROR(1000,"Something went wrong, Please contact support."),
	INVALID_EMPLOYEE_ID(1001, "Invalid employee Id (less or equal to 0)"),
	MISSING_EMPLOYEE_DATA_TO_SAVE(1002, "Missing data from the employee to save"),
	EMPLOYEE_WITH_NO_RIGHTS(1003, "Loged employee doesn't have rights to create/update/delete new employees"),
	MISSING_EMPLOYEE_DATA_TO_UPDATE(1004, "Missing data from the employee to update"),
	EMPLOYEE_NOT_FOUND(2000, "Employee ID not found!"),
	EMPLOYEES_NOT_FOUND(2001, "No active employees found!");
	
	private int code;
    private String message;
    
    EmployeeConstant(int code, String message) {
        this.code = code;
        this.message = message;
    }

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}