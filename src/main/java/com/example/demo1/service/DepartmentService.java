package com.example.demo1.service;

import java.util.List;

import com.example.demo1.vo.Department;
import com.example.demo1.vo.DepartmentRequest;

public interface DepartmentService {
    Department addDepartment(DepartmentRequest departmentRequest);

    List<Department> getAllDepartments();
    
}
