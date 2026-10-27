package com.employee.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private final EmployeeRepository employeeRepository;

	EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public List<Employee> getAllEmployee(Employee employee) {
		return employeeRepository.findAll();
		
	}

	@Override
	public Optional<Employee> getEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	@Override
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		Employee existing =  employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

		existing.setName(employee.getName());
		existing.setAddress(employee.getAddress());
		
		return employeeRepository.save(existing);
	}

	@Override
	public String deleteEmployee(Long id) {
		employeeRepository.deleteById(id);
		return "Employee deleted successfully";
	}

}
