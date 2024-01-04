package com.example.demo1.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo1.repository.DepartmentRepository;
import com.example.demo1.repository.EmployeeRepository;
import com.example.demo1.service.exceptions.NoMatchingDataException;
import com.example.demo1.vo.Department;
import com.example.demo1.vo.Employee;
import com.example.demo1.vo.EmployeeRequest;
import com.example.demo1.vo.SearchRequest;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private DepartmentRepository departmentRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
    public Employee addEmployee(EmployeeRequest employeeRequest) {
		logger.log(Level.INFO, "from Employee Service (Add Employee) -- Adding Employee");
		
        Department department = departmentRepository.findById(employeeRequest.getDepartmentId())
        		.orElseThrow(() -> new IllegalArgumentException("Department not found with ID: " + employeeRequest.getDepartmentId()));
        
        //creating a new employee
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(employeeRequest.getFirstName());
        newEmployee.setLastName(employeeRequest.getLastName());
        newEmployee.setEmail(employeeRequest.getEmail());
        newEmployee.setPhoneNumber(employeeRequest.getPhoneNumber());
        newEmployee.setBirthDate(employeeRequest.getBirthDate());
        newEmployee.setJobTitle(employeeRequest.getJobTitle());
        newEmployee.setDepartment(department);
        
        // saving the employee to db
        return employeeRepository.save(newEmployee);
    }
	
	
	@Override
	public List<Employee> getAllEmployees() {
		logger.log(Level.INFO, "Fetching all employees");
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(Long id) {
		logger.log(Level.INFO, "Fetching employee by ID: {0}", id);
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		return optionalEmployee.orElse(null);
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		logger.log(Level.INFO, "Saving employee: ", employee);
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(Long id) {
		logger.log(Level.INFO, "Deleting employee by ID: ", id);
		employeeRepository.deleteById(id);
	}

	@Override
	public List<Employee> getEmployeesByFirstName(String firstName) {
		logger.log(Level.INFO, "From Employee Service get Employees By FirstName: ");
		return employeeRepository.findByFirstName(firstName);
	}

	@Override
	public List<Employee> getEmployeesByLastName(String lastName) {
		logger.log(Level.INFO, "From Employee Service get Employees By LastName: ");
		return employeeRepository.findByLastName(lastName);
	}

	@Override
	public List<Employee> getEmployeesWithThreeLetterName(String letters) {
		logger.log(Level.INFO, "From Employee Service get Employees By three Letters: ");
		// Assuming the 'letters' parameter is a 3-letter string
		return employeeRepository.findByFirstNameIsStartingWithOrLastNameIsStartingWith(letters, letters);
	}

	// merge all three in 1 request
	/*
	 * public List<Employee> searchEmployees(SearchRequest searchRequest) {
	 * logger.log(Level.INFO, "Performing employee search: {0}", searchRequest);
	 * 
	 * if (searchRequest.getId() != null) { Optional<Employee> employee =
	 * employeeRepository.findById(searchRequest.getId()); return
	 * employee.map(Collections::singletonList).orElse(Collections.emptyList()); }
	 * else if (searchRequest.getFirstName() != null) { return
	 * employeeRepository.findByFirstName(searchRequest.getFirstName()); } else if
	 * (searchRequest.getLastName() != null) { return
	 * employeeRepository.findByLastName(searchRequest.getLastName()); } else if
	 * (searchRequest.getLetters() != null) { return
	 * employeeRepository.findByFirstNameIsStartingWithOrLastNameIsStartingWith(
	 * searchRequest.getLetters(), searchRequest.getLetters()); } else if
	 * (searchRequest.getEmail() != null) { return
	 * employeeRepository.findByEmail(searchRequest.getEmail()); } else { throw new
	 * IllegalArgumentException("Invalid search criteria"); } }
	 */
//-----   

	// enhancing the search operations

	public List<Employee> searchEmployees(SearchRequest searchRequest) {
		logger.log(Level.INFO, "From Employee Service Search Operations Start: ");

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
		Root<Employee> root = criteriaQuery.from(Employee.class);

		List<Predicate> predicates = new ArrayList<>();
		
		logger.log(Level.INFO, "From Employee Service Search Operations (Predicate--Appending) ");

		if (searchRequest.getId() != null) {
			predicates.add(criteriaBuilder.equal(root.get("id"), searchRequest.getId()));
		}

		if (!StringUtils.isEmpty(searchRequest.getFirstName())) {
			predicates.add(criteriaBuilder.equal(root.get("firstName"), searchRequest.getFirstName()));
		}

		if (!StringUtils.isEmpty(searchRequest.getLastName())) {
			predicates.add(criteriaBuilder.equal(root.get("lastName"), searchRequest.getLastName()));
		}

		if (!StringUtils.isEmpty(searchRequest.getEmail())) {
			predicates.add(criteriaBuilder.equal(root.get("email"), searchRequest.getEmail()));
		}
		if (!StringUtils.isEmpty(searchRequest.getJobTitle())) {
			predicates.add(criteriaBuilder.equal(root.get("jobTitle"), searchRequest.getJobTitle()));
		}
		
		// New logic for startId and endId
	    if (searchRequest.getStartId() != null) {
	        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("id"), searchRequest.getStartId()));
	    }

	    if (searchRequest.getEndId() != null) {
	        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("id"), searchRequest.getEndId()));
	    }
	    
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		/*
      	Sort sort = Sort.by(
      	new Sort.Order(Sort.Direction.ASC, searchRequest.getSortField()));
      	
  		Add sorting based on user preferences (ID, firstName, lastName)
  		if (sort != null) {
  			List<Order> orders = new ArrayList<>();
  			sort.forEach(order -> {
      		switch (order.getProperty()) {
	          	case "id":
	              	orders.add(order.isAscending() ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
	              	break;
	          	case "firstName":
	              // Use a default value for null entries
	              	orders.add(order.isAscending() ? criteriaBuilder.asc(criteriaBuilder.coalesce(root.get("firstName"), criteriaBuilder.literal("DefaultFirstName"))): criteriaBuilder.desc(criteriaBuilder.coalesce(root.get("firstName"), criteriaBuilder.literal("DefaultFirstName"))));
	              	break;
	          	case "lastName":
	              	orders.add(order.isAscending() ? criteriaBuilder.asc(root.get("lastName")) : criteriaBuilder.desc(root.get("lastName")));
	              	break;
	          // Add more cases for additional properties if needed
      			}
  			});
  		criteriaQuery.orderBy(orders);
  		}
		*/
		logger.log(Level.INFO, "From Employee Service Search Operations Perforiming sort operations ");

		
		// Sort Logic
		if (!StringUtils.isEmpty(searchRequest.getSortField())) {
			List<Order> orders = new ArrayList<>();
			if ("asc".equalsIgnoreCase(searchRequest.getSortOrder())) {
				orders.add(criteriaBuilder.asc(root.get(searchRequest.getSortField())));
			} else {
				orders.add(criteriaBuilder.desc(root.get(searchRequest.getSortField())));
			}
			criteriaQuery.orderBy(orders);
		}

		TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);

		List<Employee> result = query.getResultList();

		if (result.isEmpty()) {
			throw new NoMatchingDataException("Matching data not found");
		}
		logger.log(Level.INFO, "From Employee Service Search Operations End: ");

		return result;
	}
}
