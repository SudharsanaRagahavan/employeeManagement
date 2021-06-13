package com.emp.controller;

import java.text.ParseException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emp.domain.Employee;
import com.emp.domain.UpdateEmployee;
import com.emp.response.EmployeeGetResponse;
import com.emp.response.EmployeeResponse;
import com.emp.response.UpdateEmployeeResponse;
import com.emp.service.EmployeeService;
import com.emp.util.EmployeeUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(value = "/api/v1")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@PostMapping(value = "/employee", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public EmployeeResponse create(@Valid @RequestBody Employee employee) throws JsonProcessingException {
		return employeeService.createEmployee(employee);
	}

	@GetMapping(value = "/employee")
	public EmployeeGetResponse getEmployee(@RequestParam(value = "employee_id", required = false) String employeeId)
			throws JsonMappingException, JsonProcessingException {
		EmployeeGetResponse employeeGetResponse = new EmployeeGetResponse();
		if (employeeId != null) {
			employeeGetResponse = employeeService.getEmployee(employeeId);
		} else if (employeeId == null) {
			employeeGetResponse = employeeService.getAllEmployee();
		}
		return employeeGetResponse;
	}

	@PutMapping(value = "/employee/{employeeId}")
	public UpdateEmployeeResponse updateEmployee(@PathVariable("employeeId") String employeeId,
			@Valid @RequestBody UpdateEmployee employee) throws JsonProcessingException {
		return employeeService.updateEmployee(employee, employeeId);

	}

	@GetMapping(value = "/employee/date")
	public EmployeeGetResponse getEmpployeeScheduleBydate(@Valid @RequestParam String employeeGetSchedule)
			throws JsonMappingException, JsonProcessingException, ParseException {

		return employeeService.GetAllEmployeeScheduleByDate(EmployeeUtil.convertDate(employeeGetSchedule));
	}

	@PostMapping(value = "/employee/remove/schedule")
	public EmployeeResponse removeAllScheduleByEmployeeId(@Valid @RequestParam String employeeId) {
		return employeeService.removeAllSchedulesByEmployeeId(employeeId);
	}

	@PostMapping(value = "/employee/remove/schedule/date")
	public EmployeeResponse removeSchdeuleByGivenDate(@RequestParam String employeeId, @RequestParam String startDate,
			@RequestParam(required = false) String endDate)
			throws JsonMappingException, JsonProcessingException, ParseException {
		if (endDate != null) {
			return employeeService.removeScheduleByGivenDate(employeeId, startDate, endDate);
		} else {
			return employeeService.removeScheduleOnGivenDate(employeeId, startDate);
		}
	}

}
