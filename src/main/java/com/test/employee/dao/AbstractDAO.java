package com.test.employee.dao;

public interface AbstractDAO<T> {

	T findById(String var1);

    T findById(Integer var1);
    
    void save(T var1);

    void update(T var1);

    void delete(T var1);
}