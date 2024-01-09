package com.example.demo1.dto;

public class DepartmentDTO {
	private Long id;
    private String departmentName;
    private String creationDate;
    private String departmentIsActive;
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
    
    
}
