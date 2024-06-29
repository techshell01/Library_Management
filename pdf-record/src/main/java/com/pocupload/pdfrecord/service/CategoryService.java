package com.pocupload.pdfrecord.service;

import java.util.List;

import com.pocupload.pdfrecord.common.ResponseDtoForCategory;
import com.pocupload.pdfrecord.common.ResponseDtoForDocuments;
import com.pocupload.pdfrecord.dto.CategoryInfoDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;

public interface CategoryService {

	public ResponseDtoForCategory<List<CategoryInfoDto>> getAllDocumentInfo(String category, Integer id);

	public ResponseDtoForDocuments<List<DocumentInfoDto>> getAllDocumentInfoById(String category, Integer id);

}
