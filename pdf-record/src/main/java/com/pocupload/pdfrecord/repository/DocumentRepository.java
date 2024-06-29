package com.pocupload.pdfrecord.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.Plants;
import com.pocupload.pdfrecord.model.Department;
import com.pocupload.pdfrecord.model.DocumentModel;
import com.pocupload.pdfrecord.model.MainHead;
import com.pocupload.pdfrecord.model.SubArea;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentModel, Integer> {

	Optional<DocumentModel> findByFileName(String fileName);

	@Query("SELECT dm FROM DocumentModel dm WHERE dm.fileName LIKE %:fileName%")
	Page<DocumentModel> findAll(@Param("fileName") String fileName, Pageable pageable);

	@Query("SELECT count(dm) FROM DocumentModel dm JOIN dm.plant p WHERE p.plantId = :plantId")
	Integer existByPlantId(@Param("plantId") Integer plantId);

	@Query("SELECT count(dm) FROM DocumentModel dm JOIN dm.department d WHERE d.departmentId = :departmentId")
	Integer existByDepartmentId(@Param("departmentId") Integer departmentId);

	@Query("SELECT count(dm) FROM DocumentModel dm JOIN dm.subArea sa WHERE sa.subAreaId = :subAreaId")
	Integer existBySubAreaId(@Param("subAreaId") Integer subAreaId);

	List<DocumentModel> findAllByplant(Plants plants);

	List<DocumentModel> findAllByDepartment(Department department);

	List<DocumentModel> findAllBySubArea(SubArea subArea);

	List<DocumentModel> findAllByMainHead(MainHead mainHead);

}
