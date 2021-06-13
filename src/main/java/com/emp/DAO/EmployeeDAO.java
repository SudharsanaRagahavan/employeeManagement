package com.emp.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emp.entity.EmployeeEnitity;
import com.emp.repo.EmployeeRepo;

@Component
public class EmployeeDAO {

	@Autowired(required = true)
	private EmployeeRepo employeeRepo;

	public EmployeeEnitity createEmployee(EmployeeEnitity employeeEnitity) {
		return employeeRepo.save(employeeEnitity);
	}

	public EmployeeEnitity findByID(String employeeId) {
		return employeeRepo.findEmployeeById(employeeId);
	}
	
	public List<EmployeeEnitity> findAllEmployees() {
		return employeeRepo.findAll();
	}
	
	public void updateEmployee(String schedule, String employeeId) {
		employeeRepo.updateSchedule(schedule, employeeId);
	}
}
