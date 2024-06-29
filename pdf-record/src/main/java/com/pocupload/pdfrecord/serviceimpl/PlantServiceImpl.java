package com.pocupload.pdfrecord.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.PlantsDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.mapper.PlantMapperService;
import com.pocupload.pdfrecord.mapper.DocumentMapper;
import com.pocupload.pdfrecord.model.Plants;
import com.pocupload.pdfrecord.model.DocumentModel;
import com.pocupload.pdfrecord.repository.PlantRepository;
import com.pocupload.pdfrecord.repository.DocumentRepository;
import com.pocupload.pdfrecord.service.PlantService;

@Service
public class PlantServiceImpl implements PlantService {

	@Autowired
	PlantRepository plantRepository;

	@Autowired
	PlantMapperService plantMapperService;

	@Autowired
	DocumentMapper documentMapper;

	@Autowired
	DocumentRepository documentRepository;

	@Override
	public ResponseDto<List<PlantsDto>> getAllAreaInfo() {

		ResponseDto<List<PlantsDto>> response = new ResponseDto<List<PlantsDto>>();

		List<PlantsDto> listOfAreaDtos = plantMapperService.mapAreaModelToAreaDtoList(plantRepository.findAll());

		for (PlantsDto areaDto : listOfAreaDtos) {
			areaDto.setFileCount(documentRepository.existByPlantId(areaDto.getPlantId()));
			areaDto.setCount(listOfAreaDtos.size());
		}

		if (listOfAreaDtos != null && !listOfAreaDtos.isEmpty()) {
			response.setMessage("Success!!");
			response.setResponse(listOfAreaDtos);
			response.setStatus(HttpStatus.OK.value());
		}

		return response;
	}

	@Override
	public ResponseDto<List<DocumentInfoDto>> getDocumentInfoByArea(PlantsDto areaDto) {

		ResponseDto<List<DocumentInfoDto>> response = new ResponseDto<List<DocumentInfoDto>>();

		List<DocumentInfoDto> listOfDocuemntInfoDto = null;

		Plants area = plantRepository.findById(areaDto.getPlantId()).orElse(null);

		List<DocumentModel> listOfDocuments = documentRepository.findAllByplant(area);

		if (listOfDocuments != null && !listOfDocuments.isEmpty()) {
			listOfDocuemntInfoDto = documentMapper.mapDocumentInfoModelListToDocumentDtoList(listOfDocuments);
		}

		if (listOfDocuemntInfoDto != null && !listOfDocuemntInfoDto.isEmpty()) {

			response.setMessage("Success!!");
			response.setResponse(listOfDocuemntInfoDto);
			response.setStatus(HttpStatus.OK.value());

		} else {

			response.setMessage("Some Error Happends...");
			response.setStatus(HttpStatus.OK.value());

		}

		return response;
	}
}
