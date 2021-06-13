package com.emp.repo;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.emp.entity.EmployeeEnitity;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEnitity, String> {

	@Query("SELECT s FROM EmployeeEnitity s WHERE employeeId = :employeeId")
	public EmployeeEnitity findEmployeeById(@Param("employeeId") String employeeId);

	@Transactional
	@Modifying
	@Query("UPDATE EmployeeEnitity s SET schedule = :schedule WHERE s.employeeId = :employeeId")
	void updateSchedule(@Param("schedule") String schedule, @Param("employeeId") String employeeId);

}
