package com.emp.domain;

import java.util.List;

import lombok.Data;
import lombok.NonNull;

@Data
public class Employee {

	@NonNull
	private String employeeId;

	@NonNull
	private List<Schedule> schedule;

}
