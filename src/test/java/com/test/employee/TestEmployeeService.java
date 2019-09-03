package com.test.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.test.employee.dao.EmployeeDAO;
import com.test.employee.exception.APIException;
import com.test.employee.model.Employee;
import com.test.employee.service.EmployeeServiceImpl;

public class TestEmployeeService {

    @Mock
    private EmployeeDAO employeeDAO;

    @Mock
    private Logger logger;

    @InjectMocks
    private EmployeeServiceImpl testService;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployeeDAO_Found() throws APIException {
        Integer employeeId = 10;

        Employee employee = new Employee();
        employee.setId(10);
        employee.setFirstName("Pablo");
        employee.setLastName("Perez");
        employee.setDateOfBirth(new Date(1990, 01, 22));
        employee.setDateOfEmployment(new Date(2018, 10, 16));
        employee.setStatus(true);
        employee.setAdmin(false);

        when(employeeDAO.getActiveEmployee(anyInt())).thenReturn(employee);

        Employee employeeResult = testService.getEmployeeById(employeeId);

        verify(employeeDAO, times(1)).getActiveEmployee(anyInt());
        assertNotNull(employeeResult);
        assertEquals(employeeResult.getId(), employee.getId());
    }

    @Test(expected = APIException.class)
    public void testGetEmployeeDAO_NotFound() throws APIException {
        Integer employeeId = 10;

        when(employeeDAO.getActiveEmployee(anyInt())).thenReturn(null);

        testService.getEmployeeById(employeeId);
    }

    @Test
    public void testGetEmployeeDAO_NotFoundLambda() {
        Assertions.assertThrows(APIException.class, () -> {
            when(employeeDAO.getActiveEmployee(anyInt())).thenReturn(null);
            testService.getEmployeeById(anyInt());
        });
    }
}