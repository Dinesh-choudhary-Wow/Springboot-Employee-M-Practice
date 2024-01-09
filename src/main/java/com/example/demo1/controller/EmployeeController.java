package com.example.demo1.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.dto.EmployeeWithDepartmentDTO;
import com.example.demo1.service.EmployeeService;
import com.example.demo1.service.exceptions.NoMatchingDataException;
import com.example.demo1.vo.Employee;
import com.example.demo1.vo.EmployeeRequest;
import com.example.demo1.vo.SearchRequest;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private static final Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public List<Employee> getAllEmployees() {
		logger.log(Level.INFO, "API hitting from controller to getAllEmployees");
		return employeeService.getAllEmployees();
	}

	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable Long id) {
		logger.log(Level.INFO, "API hitting from controller to getEmployeeById");
		return employeeService.getEmployeeById(id);
	}

	@PostMapping("/addEmployee")
    public Employee addEmployee(@RequestBody EmployeeRequest employeeRequest) {
		logger.log(Level.INFO, "API hitting from Department controller to Add Employees");
		return employeeService.addEmployee(employeeRequest);
	}

	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		logger.log(Level.INFO, "API hitting from controller to deleteEmployee");
		employeeService.deleteEmployee(id);
	}

//    @GetMapping("/search")
//    public List<Employee> getEmployeesByFirstName(@RequestParam String firstName) {
//        return employeeService.getEmployeesByFirstName(firstName);
//    }
//    
//    @GetMapping("/searchByLastName")
//    public List<Employee> getEmployeesByLastName(@RequestParam String lastName) {
//        return employeeService.getEmployeesByLastName(lastName);
//    }
//    
//    @GetMapping("/searchByThreeLetters")
//    public List<Employee> getEmployeesWithThreeLetterName(@RequestParam String letters) {
//        return employeeService.getEmployeesWithThreeLetterName(letters);
//    }

	// using single post request
	/*
	@PostMapping("/search")
	public ResponseEntity<Object> searchEmployees ( @RequestBody SearchRequest searchRequest,
													@RequestParam(required = false) String sortField,
													@RequestParam(required = false, defaultValue = "asc") String sortOrder,
													@RequestParam(required = false, defaultValue = "10") int limit) {
		logger.log(Level.INFO, "API hitting from controller to searchEmployees");

		try {
			Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
	        
	        // Create a Sort object based on the user's input
	        Sort sort = Sort.by(direction, sortField);

			List<Employee> result = employeeService.searchEmployees(searchRequest,sort, limit);

			if (result.isEmpty()) {
				// Return a specific response for not found with a custom message
				String message = "No matching data found for the provided criteria.";
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
			}

			return ResponseEntity.ok(result);
		} catch (NoMatchingDataException e) {
			// Log the exception
			logger.error("NoMatchingDataException: " + e.getMessage());

			// Return a specific response for not found with a custom message
			String message = "No matching data found for the provided criteria.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
		} catch (Exception e) {
			// Log unexpected exceptions
			logger.error("Unexpected exception: " + e.getMessage(), e);

			// Return a specific response for other exceptions with a custom message
			String message = "An unexpected error occurred.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", message));
		}
	}
*/	
	// search enhance -2
	@PostMapping("/search")
	public ResponseEntity<Object> searchEmployees ( @RequestBody SearchRequest searchRequest) {
		logger.log(Level.INFO, "API hitting from controller to searchEmployees");

		try {
			
			List<EmployeeWithDepartmentDTO> result = employeeService.searchEmployees(searchRequest);

			if (result.isEmpty()) {
				String message = "No matching data found for the provided criteria.";
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
			}

			return ResponseEntity.ok(result);
		} catch (NoMatchingDataException e) {
			logger.error("NoMatchingDataException: " + e.getMessage());

			String message = "No matching data found for the provided criteria.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", message));
			
		} catch (Exception e) {
			logger.error("Unexpected exception: " + e.getMessage(), e);

			String message = "An unexpected error occurred.";
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("message", message));
		}
	}
	

}
