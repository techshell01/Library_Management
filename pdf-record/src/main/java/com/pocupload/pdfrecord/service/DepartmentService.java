package com.pocupload.pdfrecord.service;

import java.util.List;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.DepartmentDto;
import com.pocupload.pdfrecord.dto.DepartmentWiseDocumentDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;

public interface DepartmentService {

	public ResponseDto<List<DepartmentDto>> getAllResponseDto();

	public ResponseDto<List<DocumentInfoDto>> getAllDocumentInfoForDepId(DepartmentDto departmentDto);

	public ResponseDto<List<DepartmentWiseDocumentDto>> getAllDocInfoUnderSpecDeprt();

}
