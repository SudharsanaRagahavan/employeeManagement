package com.emp.response;

import java.util.List;

import com.emp.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude
public class UpdateEmployeeResponse {

	Enum<StatusEnum> status;

	String id;

	List<Schedule> schedule;

	public enum StatusEnum {
		SUCCESS, FAILURE;
	}

}
