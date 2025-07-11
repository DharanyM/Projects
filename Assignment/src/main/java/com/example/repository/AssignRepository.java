package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.model.Employee;

public interface AssignRepository extends JpaRepository<Employee, Long> {
		  List<Employee> findBysalary(long salary);

		  List<Employee> findBynameContainingIgnoreCase(String name);
		  
		  @Query("SELECT MAX(e.salary) FROM Employee e WHERE e.department = :department")
		    Double findMaxSalaryByDepartment(String department);

		    @Query("SELECT AVG(e.salary) FROM Employee e WHERE e.department = :department")
		    Double findAverageSalaryByDepartment(String department);
		}



