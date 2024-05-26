package com.pocupload.pdfrecord.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pocupload.pdfrecord.model.DocumentModel;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentModel, Integer> {

	Optional<DocumentModel> findByFileName(String fileName);

}
