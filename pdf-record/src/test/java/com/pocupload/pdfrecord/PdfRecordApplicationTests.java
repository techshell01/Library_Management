package com.pocupload.pdfrecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pocupload.pdfrecord.service.DocumentService;

@SpringBootTest
class PdfRecordApplicationTests {

	@Autowired
	DocumentService documentService;

//	@Test
//	void contextLoads() {
//	}

//	@Test
//	void upload() {
//		documentService.uploadDocument(MultipartFile multi);
//	}

}
