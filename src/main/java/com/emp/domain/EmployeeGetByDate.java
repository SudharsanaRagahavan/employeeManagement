package com.emp.domain;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class EmployeeGetByDate {

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Timestamp date;

}
