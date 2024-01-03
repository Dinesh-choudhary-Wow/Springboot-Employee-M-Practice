package com.example.demo1.vo;

public class SearchRequest {
    private String firstName;
    private String lastName;
    private String letters;
    private String email;
    private Long id;
    
    private String sortField;
    private String sortOrder;
    private int limit;
    
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
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SearchRequest(String firstName, String lastName, String letters, String email, Long id) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.letters = letters;
		this.email = email;
		this.id = id;
	}
	public SearchRequest() {
		super();
	}

    
}
