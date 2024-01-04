package com.example.demo1.vo;


public class SearchRequest {
	private Long id;
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	private String firstName;
    private String lastName;
    private String letters;
    private String email;
    private String jobTitle;
    private String departmentName;
    private String departmentIsActive;
    

    private String sortField;
    private String sortOrder;
    
    private Long startId;
    private Long endId;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLetters() {
		return letters;
	}
	public void setLetters(String letters) {
		this.letters = letters;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getDepartmentIsActive() {
		return departmentIsActive;
	}
	public void setDepartmentIsActive(String departmentIsActive) {
		this.departmentIsActive = departmentIsActive;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Long getStartId() {
		return startId;
	}
	public void setStartId(Long startId) {
		this.startId = startId;
	}
	public Long getEndId() {
		return endId;
	}
	public void setEndId(Long endId) {
		this.endId = endId;
	}
    
    

}
