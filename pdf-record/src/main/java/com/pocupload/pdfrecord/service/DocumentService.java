package com.pocupload.pdfrecord.service;

import org.springframework.web.multipart.MultipartFile;

import com.pocupload.pdfrecord.dto.DocumentInfoDto;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

public interface DocumentService {

	Boolean uploadDocument(MultipartFile multipartFile, String directory);

	Boolean uploadMultipleDocuments(MultipartFile[] multipartFiles, String directory);

	ResponseEntity<Resource> downloadFile(String fileName);

	ResponseEntity<Resource> showFile(String fileName);

	List<DocumentInfoDto> getAllFileInfo();

}
