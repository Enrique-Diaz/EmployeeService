package com.test.employee.dao;

import java.util.List;

import com.test.employee.model.Employee;

public interface EmployeeDAO extends AbstractDAO<Employee>{
	
	List<Employee> findAllActiveEmployees();
	
	List<Employee> findAll();
	
	void logicalDeleteEmployee(Integer employeeId);
	
	Employee getActiveEmployee(Integer employeeId);
}