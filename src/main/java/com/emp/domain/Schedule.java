package com.emp.domain;

import java.sql.Timestamp;

import com.emp.util.FrequencyEnum;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Schedule {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp startDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Timestamp endDate;

	private String time;

	private Long duration;

	private Boolean repeat;

	private FrequencyEnum frequency;
}
