package com.example.demo1.vo;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="departments")
public class Department {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    private String departmentName;
    private String creationDate;
    private String departmentIsActive;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Employee> employees;

    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getDepartmentIsActive() {
		return departmentIsActive;
	}
	public void setDepartmentIsActive(String departmentIsActive) {
		this.departmentIsActive = departmentIsActive;
	}
	public Department(Long id, String departmentName, List<Employee> employees, String creationDate,
			String departmentIsActive) {
		super();
		this.id = id;
		this.departmentName = departmentName;
		this.employees = employees;
		this.creationDate = creationDate;
		this.departmentIsActive = departmentIsActive;
	}
	public Department() {
		super();
	}
    
	
	
    
    
}
