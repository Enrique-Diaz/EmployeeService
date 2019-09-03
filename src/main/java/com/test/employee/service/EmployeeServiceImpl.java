package com.test.employee.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.test.employee.dao.EmployeeDAO;
import com.test.employee.exception.APIException;
import com.test.employee.model.Employee;
import com.test.employee.util.EmployeeConstant;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
    private EmployeeDAO dao;
	
	@Autowired
	private Logger logger;

	@Override
	public Employee getEmployeeById(Integer employeeId) throws APIException {
		logger.info("Entering business layer at getEmployeeById");
		
		Employee employeeFound = dao.getActiveEmployee(employeeId);
		if (employeeFound == null) {
			throw new APIException(EmployeeConstant.EMPLOYEE_NOT_FOUND.getMessage(), EmployeeConstant.EMPLOYEE_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		
		logger.info("Leaving business layer at getEmployeeById");
		return employeeFound;
	}

	@Override
	public void saveEmployee(Employee employee) {
		logger.info("Entering business layer at saveEmployee");
		
		dao.save(employee);
		
		logger.info("Leaving business layer at saveEmployee");
	}

	@Override
	public void updateEmployee(Employee employee) {
		logger.info("Entering business layer at updateEmployee");
		
		dao.update(employee);
		
		logger.info("Leaving business layer at updateEmployee");
	}

	@Override
	public void logicalDeleteEmployee(Integer employeeId) {
		logger.info("Entering business layer at logicalDeleteEmployee");
		
		dao.logicalDeleteEmployee(employeeId);
		
		logger.info("Leaving business layer at logicalDeleteEmployee");
	}

	@Override
	public List<Employee> getActiveEmployees() throws APIException {
		logger.info("Entering business layer at getActiveEmployees");
		
		List<Employee> employeeList = dao.findAllActiveEmployees();
		
		if (CollectionUtils.isEmpty(employeeList)) {
			throw new APIException(EmployeeConstant.EMPLOYEES_NOT_FOUND.getMessage(), EmployeeConstant.EMPLOYEES_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		
		logger.info("Leaving business layer at getActiveEmployees");
		return employeeList;
	}
	
	@Override
	public List<Employee> getEmployees() throws APIException {
		logger.info("Entering business layer at getEmployees");
		
		List<Employee> employeeList = dao.findAll();
		
		if (CollectionUtils.isEmpty(employeeList)) {
			throw new APIException(EmployeeConstant.EMPLOYEES_NOT_FOUND.getMessage(), EmployeeConstant.EMPLOYEES_NOT_FOUND.getCode(), HttpStatus.NOT_FOUND);
		}
		
		//List<Employee> filteredEmployees = employeeList.stream()
		//	.filter(Employee::isStatus)
		//	.collect(Collectors.toList());
		
		List<Employee> filteredEmployees = employeeList.stream()
				.filter(e -> e.isStatus())
				.collect(Collectors.toList());
		
		
		logger.info("Leaving business layer at getEmployees");
		return filteredEmployees;
	}
}