package com.test.employee.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.employee.exception.APIException;
import com.test.employee.model.Employee;
import com.test.employee.service.EmployeeService;
import com.test.employee.util.EmployeeConstant;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private Logger logger;
	
	@Autowired
	private EmployeeService employeeService;
	
	private static final int ZERO =0 ;
	
	/**
     * Get an active employee by id.
     *
     * @param employeeId
     * 
     * @return employee
     */
	@GetMapping("/{employeeId}")
	public ResponseEntity<?> getEmployee(@PathVariable(value="employeeId") Integer employeeId) throws APIException {
		logger.info("Entering Controller layer at getEmployee, employeeID to search:{}", employeeId);

		if( employeeId <= ZERO ) {
			throw new APIException(EmployeeConstant.INVALID_EMPLOYEE_ID.getMessage(), EmployeeConstant.INVALID_EMPLOYEE_ID.getCode(), HttpStatus.BAD_REQUEST);
		}
		
		Employee employee = employeeService.getEmployeeById(employeeId);
		
		logger.info("Leaving Controller layer at getEmployee");
		return new ResponseEntity<>(employee, HttpStatus.OK);
	}
	
	/**
     * Get a list of active employees.
     *
     * @return employeeList
     */
	@GetMapping("/active-employees")
	public ResponseEntity<?> getActiveEmployees() throws APIException {
		logger.info("Entering Controller layer at getActiveEmployees");
		
		List<Employee> employeeList = employeeService.getActiveEmployees();
		
		logger.info("Leaving Controller layer at getActiveEmployees");
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}
	
	/**
     * Get employees and filter active employees.
     *
     * @return employeeList
     */
	@GetMapping("/employees")
	public ResponseEntity<?> getEmployees() throws APIException {
		logger.info("Entering Controller layer at getEmployees");
		
		List<Employee> employeeList = employeeService.getEmployees();
		
		logger.info("Leaving Controller layer at getEmployees");
		return new ResponseEntity<>(employeeList, HttpStatus.OK);
	}
	
	/**
     * Save/Create a new employee if the loggedEmployee has rights.
     * 
     * @param loggedEmployeeId
     * @param employeeToSave
     *
     * @return 
     */
	@PostMapping("/create-employee/{loggedEmployeeId}")
	public ResponseEntity<?> saveEmployee(@PathVariable(value="loggedEmployeeId") Integer loggedEmployeeId,
			@RequestBody Employee employeeToSave) throws APIException {
		logger.info("Entering Controller layer at saveEmployee, loggedEmployeeId:{} has request to save the following employee:{}", loggedEmployeeId, employeeToSave.toString());
		
		if( loggedEmployeeId <= ZERO ) {
			throw new APIException(EmployeeConstant.INVALID_EMPLOYEE_ID.getMessage(), EmployeeConstant.INVALID_EMPLOYEE_ID.getCode(), HttpStatus.BAD_REQUEST);
		} else if (employeeToSave == null 
				|| StringUtils.isBlank(employeeToSave.getFirstName())
				|| StringUtils.isBlank(employeeToSave.getLastName())
				|| !Optional.ofNullable(employeeToSave.getDateOfEmployment()).isPresent()
				) {
			throw new APIException(EmployeeConstant.MISSING_EMPLOYEE_DATA_TO_SAVE.getMessage(), EmployeeConstant.MISSING_EMPLOYEE_DATA_TO_SAVE.getCode(), HttpStatus.BAD_REQUEST);
		}
		
		Employee loggedEmployee = employeeService.getEmployeeById(loggedEmployeeId);
		
		if (loggedEmployee.isAdmin()) {
			employeeService.saveEmployee(employeeToSave);
		} else {
			throw new APIException(EmployeeConstant.EMPLOYEE_WITH_NO_RIGHTS.getMessage(), EmployeeConstant.EMPLOYEE_WITH_NO_RIGHTS.getCode(), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Leaving Controller layer at saveEmployee");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	/**
     * Update existing employee if the loggedEmployee has rights.
     * 
     * @param loggedEmployeeId
     * @param employeeToUpdate
     *
     * @return 
     */
	@PutMapping("/update-employee/{loggedEmployeeId}")
	public ResponseEntity<?> updateEmployee(@PathVariable(value="loggedEmployeeId") Integer loggedEmployeeId,
			@RequestBody Employee employeeToUpdate) throws APIException {
		logger.info("Entering Controller layer at updateEmployee, loggedEmployeeId:{} has request to update the following employee:{}", loggedEmployeeId, employeeToUpdate.toString());
		
		if( loggedEmployeeId <= ZERO ) {
			throw new APIException(EmployeeConstant.INVALID_EMPLOYEE_ID.getMessage(), EmployeeConstant.INVALID_EMPLOYEE_ID.getCode(), HttpStatus.BAD_REQUEST);
		} else if (employeeToUpdate == null 
				|| employeeToUpdate.getId()==null || employeeToUpdate.getId()<=0
				|| StringUtils.isBlank(employeeToUpdate.getFirstName())
				|| StringUtils.isBlank(employeeToUpdate.getLastName())
				|| !Optional.ofNullable(employeeToUpdate.getDateOfEmployment()).isPresent()
				) {
			throw new APIException(EmployeeConstant.MISSING_EMPLOYEE_DATA_TO_UPDATE.getMessage(), EmployeeConstant.MISSING_EMPLOYEE_DATA_TO_UPDATE.getCode(), HttpStatus.BAD_REQUEST);
		}
		
		Employee loggedEmployee = employeeService.getEmployeeById(loggedEmployeeId);
		
		if (loggedEmployee.isAdmin()) {
			employeeService.updateEmployee(employeeToUpdate);
		} else {
			throw new APIException(EmployeeConstant.EMPLOYEE_WITH_NO_RIGHTS.getMessage(), EmployeeConstant.EMPLOYEE_WITH_NO_RIGHTS.getCode(), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Leaving Controller layer at updateEmployee");
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	/**
     * Logical delete an existing employee if the loggedEmployee has rights.
     * 
     * @param loggedEmployeeId
     * @param employeeIdToDelete
     *
     * @return 
     */
	@DeleteMapping("/delete-employee/{loggedEmployeeId}")
	public ResponseEntity<?> deleteEmployee(@PathVariable(value="loggedEmployeeId") Integer loggedEmployeeId,
			@RequestHeader(value = "employee-id-to-delete") Integer employeeIdToDelete) throws APIException {
		logger.info("Entering Controller layer at deleteEmployee, loggedEmployeeId:{} has request to delete the following employee:{}", loggedEmployeeId, employeeIdToDelete);
		
		if( loggedEmployeeId <= ZERO || (employeeIdToDelete == null || employeeIdToDelete <= ZERO)) {
			throw new APIException(EmployeeConstant.INVALID_EMPLOYEE_ID.getMessage(), EmployeeConstant.INVALID_EMPLOYEE_ID.getCode(), HttpStatus.BAD_REQUEST);
		}
		
		Employee loggedEmployee = employeeService.getEmployeeById(loggedEmployeeId);
		
		if (loggedEmployee.isAdmin()) {
			employeeService.logicalDeleteEmployee(employeeIdToDelete);
		} else {
			throw new APIException(EmployeeConstant.EMPLOYEE_WITH_NO_RIGHTS.getMessage(), EmployeeConstant.EMPLOYEE_WITH_NO_RIGHTS.getCode(), HttpStatus.BAD_REQUEST);
		}
		
		logger.info("Leaving Controller layer at updateEmployee");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}