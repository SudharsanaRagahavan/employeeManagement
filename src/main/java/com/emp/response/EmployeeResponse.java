package com.emp.response;

import java.util.List;

import com.emp.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class EmployeeResponse {

	Enum<StatusEnum> status;

	String id;

	List<Schedule> schedule;

	public enum StatusEnum {
		SUCCESS, FAILURE;
	}

}
