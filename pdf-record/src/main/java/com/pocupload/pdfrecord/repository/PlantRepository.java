package com.pocupload.pdfrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.Plants;

@Repository
public interface PlantRepository extends JpaRepository<Plants, Integer> {

	@Query("SELECT p FROM Plants p WHERE p.plantName = :plantName")
	Plants existByPlantName(@Param("plantName") String plantName);

}
