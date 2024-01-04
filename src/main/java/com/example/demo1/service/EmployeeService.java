package com.example.demo1.service;


import java.util.List;

//import org.springframework.data.domain.Sort;

import com.example.demo1.vo.Employee;
import com.example.demo1.vo.EmployeeRequest;
import com.example.demo1.vo.SearchRequest;

public interface EmployeeService {
	
	Employee addEmployee(EmployeeRequest employeeRequest);
	
    List<Employee> getAllEmployees();
    
    Employee getEmployeeById(Long id); 
    
    Employee saveEmployee(Employee employee);
    
    void deleteEmployee(Long id);
    
    List<Employee> getEmployeesByFirstName(String firstName);
    
    List<Employee> getEmployeesByLastName(String lastName);
    
    List<Employee> getEmployeesWithThreeLetterName(String letters);
    
//    List<Employee> searchEmployees(SearchRequest searchRequest, Sort sort, int limit);
    List<Employee> searchEmployees(SearchRequest searchRequest);

    
}
