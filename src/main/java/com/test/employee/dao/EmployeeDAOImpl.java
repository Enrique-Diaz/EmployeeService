package com.test.employee.dao;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.test.employee.model.Employee;

@Repository
public class EmployeeDAOImpl extends AbstractHibernateDAO<Employee> implements EmployeeDAO{

	@Autowired
	private Logger logger;

	@Override
	public List<Employee> findAllActiveEmployees() {
		logger.info("Entering DAO layer at findAllActiveEmployees");
		// Create CriteriaBuilder
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        // Create CriteriaQuery
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        
        // Specify criteria root
        Root<Employee> employee = criteriaQuery.from(Employee.class);
        
        Predicate status = builder.equal(employee.get("status"), true);
        
        criteriaQuery.select(employee).where(status);
        
        Query<Employee> q = getSession().createQuery(criteriaQuery);
        
        logger.info("Leaving DAO layer at findAllActiveEmployees");
		return q.getResultList();
	}

	@Override
	public List<Employee> findAll() {
		logger.info("Entering DAO layer at findAll");
		// Create CriteriaBuilder
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        // Create CriteriaQuery
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        
        // Specify criteria root
        Root<Employee> employee = criteriaQuery.from(Employee.class);
        
        criteriaQuery.select(employee);
        
        Query<Employee> q = getSession().createQuery(criteriaQuery);
        
        logger.info("Leaving DAO layer at findAll");
		return q.getResultList();
	}

	@Override
	public void logicalDeleteEmployee(Integer employeeId) {
		logger.info("Entering DAO layer at logicalDeleteEmployee");
		// Create CriteriaBuilder
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        // Create CriteriaQuery
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        
        // Specify criteria root
        Root<Employee> employee = criteriaQuery.from(Employee.class);
        
        Predicate status = builder.equal(employee.get("id"), employeeId);
        
        criteriaQuery.select(employee).where(status);
        
        Query<Employee> q = getSession().createQuery(criteriaQuery);
        
        Employee employeeToDelete = q.uniqueResult();
        
        logger.info("Employee found:{}", employeeToDelete.toString());
        
        employeeToDelete.setStatus(false);
        
        getSession().merge(employeeToDelete);
        
        logger.info("Leaving DAO layer at logicalDeleteEmployee");
	}

	@Override
	public Employee getActiveEmployee(Integer employeeId) {
		logger.info("Entering DAO layer at getActiveEmployee");
		// Create CriteriaBuilder
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        
        // Create CriteriaQuery
        CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);
        
        // Specify criteria root
        Root<Employee> employee = criteriaQuery.from(Employee.class);
        
        Predicate id = builder.equal(employee.get("id"), employeeId);
        Predicate status = builder.equal(employee.get("status"), true);
        
        criteriaQuery.select(employee).where(id, status);
        
        Query<Employee> q = getSession().createQuery(criteriaQuery);
        
        logger.info("Leaving DAO layer at getActiveEmployee");
		return q.uniqueResult();
	}
}