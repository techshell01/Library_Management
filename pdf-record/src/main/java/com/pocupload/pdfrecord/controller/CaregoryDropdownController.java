package com.pocupload.pdfrecord.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocupload.pdfrecord.common.ResponseDtoForCategory;
import com.pocupload.pdfrecord.common.ResponseDtoForDocuments;
import com.pocupload.pdfrecord.dto.CategoryInfoDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CaregoryDropdownController {

	@Autowired
	CategoryService categoryService;

	@GetMapping("/get-catwise-info/{category}/{id}")
	public ResponseEntity<ResponseDtoForCategory<List<CategoryInfoDto>>> getCategoryWiseInfo(
			@PathVariable String category, @PathVariable Integer id) {
		return new ResponseEntity<>(categoryService.getAllDocumentInfo(category, id),
				HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

	@GetMapping("/get-catwise-document/{category}/{id}")
	public ResponseEntity<ResponseDtoForDocuments<List<DocumentInfoDto>>> getDocumentsInfoById(
			@PathVariable("category") String category, @PathVariable("id") Integer id) {
		return new ResponseEntity<ResponseDtoForDocuments<List<DocumentInfoDto>>>(
				categoryService.getAllDocumentInfoById(category, id), HttpStatusCode.valueOf(HttpStatus.OK.value()));
	}

}
