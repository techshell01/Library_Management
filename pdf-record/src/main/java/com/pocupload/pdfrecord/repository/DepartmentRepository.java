package com.pocupload.pdfrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	@Query("SELECT d FROM Department d JOIN d.plant p WHERE d.departmentName = :departmentName AND p.plantName = :plantName")
	Department existByDepartName(@Param("departmentName") String departmentName, @Param("plantName") String plantName);

}
