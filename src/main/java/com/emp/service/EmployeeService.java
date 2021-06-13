package com.emp.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.DAO.EmployeeDAO;
import com.emp.domain.Employee;
import com.emp.domain.Schedule;
import com.emp.domain.UpdateEmployee;
import com.emp.entity.EmployeeEnitity;
import com.emp.exception.EmployeeManagementValidationException;
import com.emp.response.EmployeeGetResponse;
import com.emp.response.EmployeeGetResponse.EmpRes;
import com.emp.response.EmployeeResponse;
import com.emp.response.UpdateEmployeeResponse;
import com.emp.util.EmployeeUtil;
import com.emp.util.ErrorEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmployeeService {

	@Autowired
	EmployeeDAO employeeDAO;

	public EmployeeResponse createEmployee(Employee employee) throws JsonProcessingException {
		EmployeeEnitity entity = new EmployeeEnitity();
		employee.setSchedule(EmployeeUtil.updateSchedule(employee.getSchedule()));
		entity.setEmployeeId(employee.getEmployeeId());
		entity.setSchedule(new ObjectMapper().writeValueAsString(employee.getSchedule()));
		entity = employeeDAO.createEmployee(entity);
		return setEmployeeResponse(entity);
	}

	private EmployeeResponse setEmployeeResponse(EmployeeEnitity employeeEnitity)
			throws JsonMappingException, JsonProcessingException {
		EmployeeResponse employeeResponse = new EmployeeResponse();
		employeeResponse.setStatus(EmployeeResponse.StatusEnum.SUCCESS);
		employeeResponse.setId(employeeEnitity.getEmployeeId());
		if (employeeEnitity.getSchedule() != null) {
			employeeResponse.setSchedule(
					new ObjectMapper().readValue(employeeEnitity.getSchedule(), new TypeReference<List<Schedule>>() {
					}));
		}
		return employeeResponse;
	}

	public EmployeeGetResponse getEmployee(String employeeId) throws JsonMappingException, JsonProcessingException {
		EmployeeEnitity employeeEnitity = employeeDAO.findByID(employeeId);
		EmployeeGetResponse employeeGetResponse = new EmployeeGetResponse();
		EmployeeGetResponse.EmpRes empRes = new EmpRes();
		List<EmpRes> employees = new ArrayList<EmpRes>();
		employeeGetResponse.setNoOfEmp(1);
		employeeGetResponse.setStatus(EmployeeGetResponse.StatusEnum.SUCCESS);
		empRes.setEmployeeId(employeeEnitity.getEmployeeId());
		empRes.setSchedule(
				new ObjectMapper().readValue(employeeEnitity.getSchedule(), new TypeReference<List<Schedule>>() {
				}));
		employees.add(empRes);
		employeeGetResponse.setEmployees(employees);
		return employeeGetResponse;
	}

	public EmployeeGetResponse getAllEmployee() throws JsonMappingException, JsonProcessingException {
		List<EmployeeEnitity> employeeEnitities = employeeDAO.findAllEmployees();
		EmployeeGetResponse employeeGetResponse = new EmployeeGetResponse();
		employeeGetResponse.setNoOfEmp(employeeEnitities.size());
		employeeGetResponse.setStatus(EmployeeGetResponse.StatusEnum.SUCCESS);
		EmployeeGetResponse.EmpRes empRes;
		List<EmpRes> employees = new ArrayList<EmpRes>();
		for (EmployeeEnitity employeeEnitity : employeeEnitities) {
			empRes = new EmpRes();
			empRes.setEmployeeId(employeeEnitity.getEmployeeId());
			empRes.setSchedule(
					new ObjectMapper().readValue(employeeEnitity.getSchedule(), new TypeReference<List<Schedule>>() {
					}));
			employees.add(empRes);
		}
		employeeGetResponse.setEmployees(employees);
		return employeeGetResponse;
	}

	public UpdateEmployeeResponse updateEmployee(UpdateEmployee employee, String employeeId)
			throws JsonProcessingException {
		EmployeeEnitity employeeEnitity = employeeDAO.findByID(employeeId);
		if (employeeEnitity != null) {
			employee.setSchedule(EmployeeUtil.updateSchedule(employee.getSchedule()));
			employeeDAO.updateEmployee(new ObjectMapper().writeValueAsString(employee.getSchedule()), employeeId);
		} else {
			throw new EmployeeManagementValidationException(null, ErrorEnum.EMPLOYEE_NOT_FOUND.toString());
		}
		return setUpdateEmployeeResponse(employeeId);
	}

	private UpdateEmployeeResponse setUpdateEmployeeResponse(String employeeId)
			throws JsonMappingException, JsonProcessingException {
		EmployeeEnitity employeeEnitity = employeeDAO.findByID(employeeId);
		UpdateEmployeeResponse employeeResponse = new UpdateEmployeeResponse();
		employeeResponse.setStatus(UpdateEmployeeResponse.StatusEnum.SUCCESS);
		employeeResponse.setId(employeeEnitity.getEmployeeId());
		employeeResponse.setSchedule(
				new ObjectMapper().readValue(employeeEnitity.getSchedule(), new TypeReference<List<Schedule>>() {
				}));
		return employeeResponse;
	}

	public EmployeeGetResponse GetAllEmployeeScheduleByDate(Date date)
			throws JsonMappingException, JsonProcessingException, ParseException {
		EmployeeGetResponse employeeGetResponse = new EmployeeGetResponse();
		List<EmployeeEnitity> employeeEnitity = employeeDAO.findAllEmployees();
		List<EmpRes> employeeList = new ArrayList<EmpRes>();
		EmpRes empRes;
		for (EmployeeEnitity enitity : employeeEnitity) {
			empRes = new EmpRes();
			empRes.setEmployeeId(enitity.getEmployeeId());
			empRes.setSchedule(new ObjectMapper().readValue(enitity.getSchedule(), new TypeReference<List<Schedule>>() {
			}));
			employeeList.add(empRes);
		}
		List<Schedule> schedules;
		List<EmpRes> employeesRes = new ArrayList<EmpRes>();
		;
		for (EmpRes employeeRes : employeeList) {
			EmpRes res = new EmpRes();
			for (Schedule schedule : employeeRes.getSchedule()) {
				schedules = new ArrayList<Schedule>();

				if ((EmployeeUtil.convertDate(schedule.getStartDate().toString()).before(date)
						&& EmployeeUtil.convertDate(schedule.getEndDate().toString()).after(date))
						|| (DateUtils.isSameDay(EmployeeUtil.convertDate(schedule.getStartDate().toString()), date))
						|| (DateUtils.isSameDay(EmployeeUtil.convertDate(schedule.getEndDate().toString()), date))) {
					schedules.add(schedule);
					res.setSchedule(schedules);
				}
			}
			if (res.getSchedule() != null && res.getSchedule().size() > 0) {
				res.setEmployeeId(employeeRes.getEmployeeId());
				employeesRes.add(res);
			}

		}
		employeeGetResponse.setEmployees(employeesRes);
		employeeGetResponse.setNoOfEmp(employeesRes.size());
		employeeGetResponse.setStatus(EmployeeGetResponse.StatusEnum.SUCCESS);
		return employeeGetResponse;
	}

	public EmployeeResponse removeAllSchedulesByEmployeeId(String employeeId) {
		EmployeeEnitity employeeEnitity = employeeDAO.findByID(employeeId);
		if (employeeEnitity == null) {
			throw new EmployeeManagementValidationException(null, ErrorEnum.EMPLOYEE_NOT_FOUND.toString());
		}
		EmployeeResponse employeeResponse = new EmployeeResponse();
		EmployeeEnitity enitity = new EmployeeEnitity();
		enitity.setEmployeeId(employeeId);
		employeeDAO.createEmployee(enitity);
		employeeResponse.setId(employeeId);
		employeeResponse.setStatus(EmployeeResponse.StatusEnum.SUCCESS);
		return employeeResponse;

	}

	public EmployeeResponse removeScheduleByGivenDate(String employeeId, String startDate, String endDate)
			throws JsonMappingException, JsonProcessingException, ParseException {
		List<Schedule> schedules = null;
		EmployeeEnitity employeeEnitity = employeeDAO.findByID(employeeId);
		EmployeeEnitity enitity = new EmployeeEnitity();
		EmpRes empRes = new EmpRes();
		empRes.setEmployeeId(employeeEnitity.getEmployeeId());
		empRes.setSchedule(
				new ObjectMapper().readValue(employeeEnitity.getSchedule(), new TypeReference<List<Schedule>>() {
				}));
		for (Schedule schedule : empRes.getSchedule()) {
			schedules = new ArrayList<Schedule>();
			if (!((EmployeeUtil.convertDate(schedule.getStartDate().toString())
					.after(EmployeeUtil.convertDate(startDate))
					&& EmployeeUtil.convertDate(schedule.getEndDate().toString())
							.before(EmployeeUtil.convertDate(endDate)))
					|| (DateUtils.isSameDay(EmployeeUtil.convertDate(schedule.getStartDate().toString()),
							EmployeeUtil.convertDate(startDate)))
					|| (DateUtils.isSameDay(EmployeeUtil.convertDate(schedule.getEndDate().toString()),
							EmployeeUtil.convertDate(endDate))))) {
				schedules.add(schedule);
			}
		}
		enitity.setEmployeeId(employeeId);
		enitity.setSchedule(new ObjectMapper().writeValueAsString(schedules));
		employeeDAO.createEmployee(enitity);
		return setEmployeeResponse(enitity);
	}

	public EmployeeResponse removeScheduleOnGivenDate(String employeeId, String date)
			throws JsonMappingException, JsonProcessingException, ParseException {
		List<Schedule> schedules = new ArrayList<Schedule>();
		EmployeeEnitity employeeEnitity = employeeDAO.findByID(employeeId);
		EmployeeEnitity enitity = new EmployeeEnitity();
		EmpRes empRes = new EmpRes();
		empRes.setEmployeeId(employeeEnitity.getEmployeeId());
		empRes.setSchedule(
				new ObjectMapper().readValue(employeeEnitity.getSchedule(), new TypeReference<List<Schedule>>() {
				}));
		for (Schedule schedule : empRes.getSchedule()) {
			if (DateUtils.isSameDay(schedule.getStartDate(), EmployeeUtil.convertDate(date))) {
				schedule.setStartDate(Timestamp.valueOf(schedule.getStartDate().toLocalDateTime().plusDays(1)));
			}
			schedules.add(schedule);
		}
		enitity.setEmployeeId(employeeId);
		enitity.setSchedule(new ObjectMapper().writeValueAsString(schedules));
		employeeDAO.createEmployee(enitity);
		return setEmployeeResponse(enitity);
	}
}
