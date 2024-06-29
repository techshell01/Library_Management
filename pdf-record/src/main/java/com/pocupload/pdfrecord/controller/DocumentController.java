package com.pocupload.pdfrecord.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.service.DocumentService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/document")
public class DocumentController {

	@Autowired
	DocumentService documentService;

	@GetMapping("/get-info")
	public ResponseEntity<ResponseDto<List<DocumentInfoDto>>> getAllDocInfo(@RequestParam("fileName") String fileName,
			Pageable pageable) {

		ResponseDto<List<DocumentInfoDto>> response = new ResponseDto<List<DocumentInfoDto>>();
		List<DocumentInfoDto> listOfDocumentInfoDtos = documentService.getAllFileInfo(fileName, pageable);

		if (!listOfDocumentInfoDtos.isEmpty()) {

			response.setStatus(HttpStatus.OK.value());
			response.setResponse(listOfDocumentInfoDtos);
			response.setMessage("success!!");

			return new ResponseEntity<ResponseDto<List<DocumentInfoDto>>>(response,
					HttpStatusCode.valueOf(HttpStatus.OK.value()));

		} else {

			response.setStatus(HttpStatus.OK.value());
			response.setResponse(listOfDocumentInfoDtos);
			response.setMessage("No Data Present!!");

			return new ResponseEntity<ResponseDto<List<DocumentInfoDto>>>(response,
					HttpStatusCode.valueOf(HttpStatus.OK.value()));

		}

	}

	@GetMapping(value = "/download/{name}", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<Resource> downloadFile(@PathVariable(value = "name") String fileName) {
		ResponseEntity<Resource> fileResponse = documentService.downloadFile(fileName);

		if (fileResponse.getStatusCode().is4xxClientError() || fileResponse.getStatusCode().is5xxServerError()) {
			return ResponseEntity.status(fileResponse.getStatusCode()).build();
		}

		return fileResponse;
	}

	@GetMapping("/show-file/{name}")
	public void showFile(@PathVariable(value = "name") String fileName, HttpServletResponse response) {
		ResponseEntity<Resource> fileResponse = documentService.showFile(fileName);

		if (fileResponse == null || fileResponse.getStatusCode().is4xxClientError()
				|| fileResponse.getStatusCode().is5xxServerError()) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return;
		}

		Resource resource = fileResponse.getBody();

		// Set response headers
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + fileName);
		response.setContentType(fileResponse.getHeaders().getContentType().toString());

		try {
			// Copy file content to response output stream
			FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
			response.flushBuffer();
		} catch (IOException e) {
			// Handle IOException
			e.printStackTrace();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}

//	@GetMapping(value = "/show-pdf/{name}")
//	public ResponseEntity<Resource> showPdf(@PathVariable(value = "name") String fileName) {
//		ResponseEntity<Resource> fileResponse = documentService.showFile(fileName);
//		
//		if (fileResponse.getStatusCode().is4xxClientError() || fileResponse.getStatusCode().is5xxServerError()) {
//			return ResponseEntity.status(fileResponse.getStatusCode()).build();
//		}
//		
//		return fileResponse;
//	}

}
