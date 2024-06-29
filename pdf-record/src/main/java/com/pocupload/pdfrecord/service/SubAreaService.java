package com.pocupload.pdfrecord.service;

import java.util.List;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.dto.SubAreaDto;

public interface SubAreaService {

	public ResponseDto<List<SubAreaDto>> getAllInstrumentInfo();

	public ResponseDto<List<DocumentInfoDto>> getAllDocInfoByInstId(SubAreaDto instrumentDto);

}
