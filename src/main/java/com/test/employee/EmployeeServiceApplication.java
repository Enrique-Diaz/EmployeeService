package com.test.employee;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude=HibernateJpaAutoConfiguration.class)
public class EmployeeServiceApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EmployeeServiceApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "30335"));
        app.run(args);
	}
}