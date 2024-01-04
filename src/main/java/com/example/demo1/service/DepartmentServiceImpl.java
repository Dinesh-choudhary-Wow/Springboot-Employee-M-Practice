package com.example.demo1.service;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo1.controller.EmployeeController;
import com.example.demo1.repository.DepartmentRepository;
import com.example.demo1.vo.Department;
import com.example.demo1.vo.DepartmentRequest;

import jakarta.transaction.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
    private DepartmentRepository departmentRepository; 

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional
    public Department addDepartment(DepartmentRequest departmentRequest) {
		logger.log(Level.INFO, "From Department Service (addDepartment) -- Adding department");

        //create a new Department
    	Department newDepartment = new Department();
    	newDepartment.setDepartmentName(departmentRequest.getDepartmentName());
    	newDepartment.setCreationDate(departmentRequest.getCreationDate());
    	newDepartment.setDepartmentIsActive(departmentRequest.getDepartmentIsActive());
    	
    	//save the department to db
    	return departmentRepository.save(newDepartment);
    }

    @Override
    public List<Department> getAllDepartments() {
		logger.log(Level.INFO, "from Department service (GetAll EMployees) Getting all Departments");
        return departmentRepository.findAll();
    }

}
