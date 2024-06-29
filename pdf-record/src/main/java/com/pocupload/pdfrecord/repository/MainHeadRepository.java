package com.pocupload.pdfrecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.MainHead;

@Repository
public interface MainHeadRepository extends JpaRepository<MainHead, Integer> {

	@Query("SELECT mh FROM MainHead mh WHERE mh.headName = :headName")
	MainHead existByMainHeadName(@Param("headName") String headName);

}
