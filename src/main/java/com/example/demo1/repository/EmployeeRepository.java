package com.example.demo1.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo1.vo.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	List<Employee> findByFirstName(String firstName);
    List<Employee> findByLastName(String lastName);
    List<Employee> findByFirstNameIsStartingWithOrLastNameIsStartingWith(String firstName, String lastName);
    List<Employee> findByEmail(String email);
    List<Employee> findByJobTitle(String jobTitle);
    
    List<Employee> findByDepartmentDepartmentName(String departmentName);
    List<Employee> findByDepartmentDepartmentIsActive(String departmentIsActive);
}
