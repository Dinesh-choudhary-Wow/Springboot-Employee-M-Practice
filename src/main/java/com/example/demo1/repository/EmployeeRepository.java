package com.example.demo1.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.vo.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    // Additional query methods can be added here if needed
	List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByFirstNameIsStartingWithOrLastNameIsStartingWith(String firstName, String lastName);
    List<Employee> findByEmail(String email);
}
