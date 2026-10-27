package com.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employee.entity.Employee;

@Service
public interface EmployeeService {
	
	public List<Employee> getAllEmployee(Employee employee);
	
	public Optional<Employee> getEmployeeById(Long id);
	
	public Employee addEmployee(Employee employee);
	
	public Employee updateEmployee(Long id, Employee employee);
	
	public String deleteEmployee(Long id);

}
