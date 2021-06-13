package com.emp.response;

import java.util.List;

import com.emp.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class EmployeeGetResponse {

	Enum<StatusEnum> status;

	Integer noOfEmp;

	List<EmpRes> employees;

	@Data
	public static class EmpRes {
		String employeeId;

		List<Schedule> schedule;
	}

	public enum StatusEnum {
		SUCCESS, FAILURE;
	}

}
