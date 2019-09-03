package com.test.employee;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.test.employee.controller.EmployeeController;
import com.test.employee.exception.APIException;
import com.test.employee.model.Employee;
import com.test.employee.service.EmployeeService;

public class TestEmployeeController {

	@Mock
	private EmployeeService employeeService;

	@Mock
	private Logger logger;

	@InjectMocks
	private EmployeeController testController;

	@Before
	public void init(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetEmployee_Found() throws APIException {
		Integer employeeId = 10;

		Employee employee = new Employee();
		employee.setId(10);
		employee.setFirstName("Pablo");
		employee.setLastName("Perez");
		employee.setDateOfBirth(new Date(1990, 01, 22));
		employee.setDateOfEmployment(new Date(2018, 10, 16));
		employee.setStatus(true);
		employee.setAdmin(false);

		when(employeeService.getEmployeeById(anyInt())).thenReturn(employee);

		ResponseEntity<?> response = testController.getEmployee(employeeId);

		verify(employeeService, times(1)).getEmployeeById(anyInt());
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(((Employee)response.getBody()).getId(), employee.getId());
	}

	@Test(expected = APIException.class)
	public void testGetEmployee_IncorrectId() throws APIException {
		Integer employeeId = 0;

		testController.getEmployee(employeeId);
	}

	@Test
	public void testGetEmployee_IncorrectIdLambda() {
		Assertions.assertThrows(APIException.class, () -> {
			Integer employeeId = 0;
			testController.getEmployee(employeeId);
		});
	}
}