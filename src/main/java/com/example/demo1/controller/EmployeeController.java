package com.example.demo1.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo1.service.EmployeeService;
import com.example.demo1.service.exceptions.NoMatchingDataException;
import com.example.demo1.vo.Employee;
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
	public Employee saveEmployee(@RequestBody Employee employee) {
		logger.log(Level.INFO, "API hitting from controller to saveEmployee");
		return employeeService.saveEmployee(employee);
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
	@PostMapping("/search")
	public ResponseEntity<Object> searchEmployees(@RequestBody SearchRequest searchRequest) {
		logger.log(Level.INFO, "API hitting from controller to searchEmployees");

		try {
			List<Employee> result = employeeService.searchEmployees(searchRequest);

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

}
