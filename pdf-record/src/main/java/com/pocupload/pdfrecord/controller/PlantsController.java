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
import com.pocupload.pdfrecord.dto.PlantsDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.service.PlantService;

@RestController
@RequestMapping("/area")
public class PlantsController {

	@Autowired
	PlantService areaService;

	@GetMapping("/get-all-areas")
	public ResponseEntity<ResponseDto<List<PlantsDto>>> getAllAreas() {
		return new ResponseEntity<ResponseDto<List<PlantsDto>>>(areaService.getAllAreaInfo(),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@PostMapping("/document-under-area")
	public ResponseEntity<ResponseDto<List<DocumentInfoDto>>> getDocumentUnderArea(@RequestBody PlantsDto areaDto) {
		return new ResponseEntity<ResponseDto<List<DocumentInfoDto>>>(areaService.getDocumentInfoByArea(areaDto),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

}
