package com.test.employee.service;

import java.util.List;

import com.test.employee.exception.APIException;
import com.test.employee.model.Employee;

public interface EmployeeService {

	public Employee getEmployeeById(Integer employeeId) throws APIException;
	
	public void saveEmployee(Employee employee);
	
	public void updateEmployee(Employee employee);
	
	public void logicalDeleteEmployee(Integer employeeId);
	
	public List<Employee> getActiveEmployees() throws APIException;
	
	public List<Employee> getEmployees() throws APIException;
}