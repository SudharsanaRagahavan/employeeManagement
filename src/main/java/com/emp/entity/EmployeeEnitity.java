package com.emp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

import lombok.Data;

@Data
@Entity
@Table(name = "employee_details")
public class EmployeeEnitity {

	@Id
	@NotNull
	private String employeeId;

	@Nullable
	@Column(columnDefinition = "longtext")
	private String schedule;
}
