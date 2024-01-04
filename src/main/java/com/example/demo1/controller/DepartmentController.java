package com.example.demo1.controller;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.service.DepartmentService;
import com.example.demo1.vo.Department;
import com.example.demo1.vo.DepartmentRequest;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/addDepartment")
    public Department addDepartment(@RequestBody DepartmentRequest departmentRequest) {
		logger.log(Level.INFO, "API hitting from Department controller to Add Department");
    	return departmentService.addDepartment(departmentRequest);
    	
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
		logger.log(Level.INFO, "API hitting from Department controller to getAllDepartments");
        List<Department> departments = departmentService.getAllDepartments();
        return new ResponseEntity<>(departments, HttpStatus.OK);
    }
    
}
