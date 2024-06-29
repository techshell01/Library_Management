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
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.dto.SubAreaDto;
import com.pocupload.pdfrecord.service.SubAreaService;

@RestController
@RequestMapping("/instrument")
public class SubAreaController {

	@Autowired
	SubAreaService instrumentService;

	@GetMapping("/get-instrument-info")
	public ResponseEntity<ResponseDto<List<SubAreaDto>>> getAllInstrumentInfo() {
		return new ResponseEntity<ResponseDto<List<SubAreaDto>>>(instrumentService.getAllInstrumentInfo(),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@PostMapping("/get-document-by-instid")
	public ResponseEntity<ResponseDto<List<DocumentInfoDto>>> getDocumentByInstId(
			@RequestBody SubAreaDto instrumentDto) {
		return new ResponseEntity<ResponseDto<List<DocumentInfoDto>>>(
				instrumentService.getAllDocInfoByInstId(instrumentDto), HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

}
