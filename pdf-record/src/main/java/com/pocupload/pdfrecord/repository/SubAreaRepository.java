package com.pocupload.pdfrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.SubArea;

@Repository
public interface SubAreaRepository extends JpaRepository<SubArea, Integer> {

	@Query("SELECT sa FROM SubArea sa WHERE sa.subAreaName = :subAreaName")
	SubArea existBySubAreaName(@Param("subAreaName") String subAreaName);

	@Query("SELECT sa FROM SubArea sa JOIN sa.department d JOIN d.plant p JOIN p.mainHead mh WHERE sa.subAreaName = :subAreaName AND d.departmentName = :departmentName AND p.plantName = :plantName AND mh.headName = :headName")
	SubArea existBySubAreaNameByAllFields(@Param("subAreaName") String subAreaName,
			@Param("departmentName") String departmentName, @Param("plantName") String plantName,
			@Param("headName") String headName);

}
