package com.emp.domain;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;

@Data
public class EmployeeGetSchedule {

	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyy-MM-dd", iso = ISO.DATE)
	private Timestamp date;

}
