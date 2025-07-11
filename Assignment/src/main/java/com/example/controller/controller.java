package com.example.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Employee;
import com.example.repository.AssignRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")

public class controller {

	@Autowired
	AssignRepository assignRepository;

	@GetMapping("/employee")
	public ResponseEntity<List<Employee>> getAllEmployees(@RequestParam(required = false) String name,
			@RequestParam(required = false) String department) {
		try {
			List<Employee> employees = new ArrayList<Employee>();

			if (name == null)
				assignRepository.findAll().forEach(employees::add);
			else
				assignRepository.findBynameContainingIgnoreCase(name).forEach(employees::add);

			if (employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getemployeeById(@PathVariable("id") long id) {
		Optional<Employee> employeeData = assignRepository.findById(id);

		if (employeeData.isPresent()) {
			return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/max-salary")
	public Double getMaxSalaryByDepartment(@RequestParam String department) {
		return assignRepository.findMaxSalaryByDepartment(department);
	}

	@GetMapping("/avg-salary")
	public Double getAverageSalaryByDepartment(@RequestParam String department) {
		return assignRepository.findAverageSalaryByDepartment(department);
	}

	@PostMapping("/employees")
	public ResponseEntity<Employee> createTutorial(@RequestBody Employee tutorial) {
		try {
			Employee _emp = assignRepository
					.save(new Employee(tutorial.getName(), tutorial.getDepartment(), tutorial.getSalary()));
			return new ResponseEntity<>(_emp, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee emp) {
		Optional<Employee> employeeData = assignRepository.findById(id);

		if (employeeData.isPresent()) {
			Employee _emp = employeeData.get();
			_emp.setName(emp.getName());
			_emp.setDepartment(emp.getDepartment());
			_emp.setSalary(emp.getSalary());
			return new ResponseEntity<>(assignRepository.save(_emp), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/employee/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			assignRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}