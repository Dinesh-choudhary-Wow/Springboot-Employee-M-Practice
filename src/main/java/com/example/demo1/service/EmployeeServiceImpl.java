package com.example.demo1.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo1.repository.EmployeeRepository;
import com.example.demo1.service.exceptions.NoMatchingDataException;
import com.example.demo1.vo.Employee;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	private static final Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @PersistenceContext
    private EntityManager entityManager;
    
    
    
    
    
    
    
    
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
    	logger.log(Level.INFO, "Saving employee: {0}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
    	logger.log(Level.INFO, "Deleting employee by ID: {0}", id);
        employeeRepository.deleteById(id);
    }
    
    @Override
    public List<Employee> getEmployeesByFirstName(String firstName) {
        return employeeRepository.findByFirstName(firstName);
    }
    
    @Override
    public List<Employee> getEmployeesByLastName(String lastName) {
        return employeeRepository.findByLastName(lastName);
    }
    
    @Override
    public List<Employee> getEmployeesWithThreeLetterName(String letters) {
        // Assuming the 'letters' parameter is a 3-letter string
        return employeeRepository.findByFirstNameIsStartingWithOrLastNameIsStartingWith(letters, letters);
    }
    
    
    
    // merge all three in 1 request
    /*
    public List<Employee> searchEmployees(SearchRequest searchRequest) {
        logger.log(Level.INFO, "Performing employee search: {0}", searchRequest);

        if (searchRequest.getId() != null) {
            Optional<Employee> employee = employeeRepository.findById(searchRequest.getId());
            return employee.map(Collections::singletonList).orElse(Collections.emptyList());
        } else if (searchRequest.getFirstName() != null) {
            return employeeRepository.findByFirstName(searchRequest.getFirstName());
        } else if (searchRequest.getLastName() != null) {
            return employeeRepository.findByLastName(searchRequest.getLastName());
        } else if (searchRequest.getLetters() != null) {
            return employeeRepository.findByFirstNameIsStartingWithOrLastNameIsStartingWith(
                    searchRequest.getLetters(), searchRequest.getLetters());
        } else if (searchRequest.getEmail() != null) {
            return employeeRepository.findByEmail(searchRequest.getEmail());
        } else {
            throw new IllegalArgumentException("Invalid search criteria");
        }
    }
    */
    
    
    
    //enhancing the search operations
    
    public List<Employee> searchEmployees(SearchRequest searchRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);

        List<Predicate> predicates = new ArrayList<Predicate>();

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
        
        
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        
        
        // Add sorting based on user preferences (ID, firstName, lastName)
//        if (sort != null) {
//            List<Order> orders = new ArrayList<>();
//            sort.forEach(order -> {
//                switch (order.getProperty()) {
//                    case "id":
//                        orders.add(order.isAscending() ? criteriaBuilder.asc(root.get("id")) : criteriaBuilder.desc(root.get("id")));
//                        break;
//                    case "firstName":
//                        // Use a default value for null entries
//                        orders.add(order.isAscending() ? criteriaBuilder.asc(criteriaBuilder.coalesce(root.get("firstName"), criteriaBuilder.literal("DefaultFirstName"))): criteriaBuilder.desc(criteriaBuilder.coalesce(root.get("firstName"), criteriaBuilder.literal("DefaultFirstName"))));
//                        break;
//                    case "lastName":
//                        orders.add(order.isAscending() ? criteriaBuilder.asc(root.get("lastName")) : criteriaBuilder.desc(root.get("lastName")));
//                        break;
//                    // Add more cases for additional properties if needed
//                }
//            });
//            criteriaQuery.orderBy(orders);
//        }
        
        
        
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
        
     // Apply limit
        query.setMaxResults(searchRequest.getLimit());
        
        List<Employee> result = query.getResultList();

        if (result.isEmpty()) {
            // Return a custom message when no matching data is found
            throw new NoMatchingDataException("Matching data not found");
        }

        return result;
    }

	
    
    
    
}
