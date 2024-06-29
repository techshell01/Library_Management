package com.pocupload.pdfrecord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.DepartmentDto;
import com.pocupload.pdfrecord.dto.DepartmentWiseDocumentDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.service.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	@Autowired
	DepartmentService departmentService;

	@GetMapping("/get-department-info")
	public ResponseEntity<ResponseDto<List<DepartmentDto>>> getDepartmentDetails() {
		return new ResponseEntity<ResponseDto<List<DepartmentDto>>>(departmentService.getAllResponseDto(),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@PostMapping("/get-department-info")
	public ResponseEntity<ResponseDto<List<DocumentInfoDto>>> getDocumentUnderDepartment(
			@RequestBody DepartmentDto departmentDto) {
		return new ResponseEntity<ResponseDto<List<DocumentInfoDto>>>(
				departmentService.getAllDocumentInfoForDepId(departmentDto),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@GetMapping("/get-department-wise-all-document")
	public ResponseEntity<ResponseDto<List<DepartmentWiseDocumentDto>>> getDepartwiseAllDocument() {
		return new ResponseEntity<ResponseDto<List<DepartmentWiseDocumentDto>>>(
				departmentService.getAllDocInfoUnderSpecDeprt(), HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

}
