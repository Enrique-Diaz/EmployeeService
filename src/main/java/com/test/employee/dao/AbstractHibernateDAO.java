package com.test.employee.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
 
public abstract class AbstractHibernateDAO<T> implements AbstractDAO<T>{
 
    @Autowired
    private SessionFactory sessionFactory;
    
    private Class<T> domainClass;
 
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }
 
    public T findById(String id) {
    	return getSession().get(this.getDomainClass(), id);
    }
    
    public T findById(Integer id) {
    	return getSession().get(this.getDomainClass(), id);
    }
    
    public void save(T entity) {
    	getSession().save(entity);
	}
    
    public void update(T entity) {
    	getSession().merge(entity);
    }
 
    public void delete(T entity) {
        getSession().delete(entity);
    }
    
    public abstract List<T> findAll();

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private Class<T> getDomainClass() {
        if (this.domainClass == null) {
            ParameterizedType thisType = (ParameterizedType)this.getClass().getGenericSuperclass();
            this.domainClass = (Class)thisType.getActualTypeArguments()[0];
        }

        return this.domainClass;
    }

    @SuppressWarnings("unused")
	private String getDomainClassName() {
        return this.getDomainClass().getName();
    }
    
    //Get employees by an ID
    //Create new employees
    //Update existing employees
    //Delete employees
}