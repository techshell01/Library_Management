package com.pocupload.pdfrecord.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.service.DocumentService;

@RestController
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	DocumentService documentService;

	@PostMapping("/doc-upload")
	public ResponseEntity<ResponseDto<Boolean>> uploadDocument(@RequestParam("file") MultipartFile multipartFile,
			@RequestParam("directory") String directory) {

		ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
		Boolean response = documentService.uploadDocument(multipartFile, directory);

		if (response) {
			responseDto.setStatus(HttpStatus.OK.value());
			responseDto.setResponse(response);
			responseDto.setMessage("Document Uploaded Successfully!!");
		} else {
			responseDto.setStatus(HttpStatus.OK.value());
			responseDto.setResponse(response);
			responseDto.setMessage("Please upload valid file!!");
		}

		return ResponseEntity.ok(responseDto);

	}

	@PostMapping("/multiple-doc-upload")
	public ResponseEntity<ResponseDto<Boolean>> uploadMultipleDocuments(
			@RequestParam("file") MultipartFile[] multipartFile, @RequestParam("directory") String directory) {

		ResponseDto<Boolean> responseDto = new ResponseDto<Boolean>();
		Boolean response = documentService.uploadMultipleDocuments(multipartFile, directory);

		if (response) {
			responseDto.setStatus(HttpStatus.OK.value());
			responseDto.setResponse(response);
			responseDto.setMessage("Document Uploaded Successfully!!");
		} else {
			responseDto.setStatus(HttpStatus.OK.value());
			responseDto.setResponse(response);
			responseDto.setMessage("Please upload valid file!!");
		}

		return ResponseEntity.ok(responseDto);

	}

}
