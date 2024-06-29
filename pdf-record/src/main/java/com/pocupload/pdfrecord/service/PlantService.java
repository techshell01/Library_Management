package com.pocupload.pdfrecord.service;

import java.util.List;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.PlantsDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;

public interface PlantService {

	ResponseDto<List<PlantsDto>> getAllAreaInfo();

	ResponseDto<List<DocumentInfoDto>> getDocumentInfoByArea(PlantsDto areaDto);

}
