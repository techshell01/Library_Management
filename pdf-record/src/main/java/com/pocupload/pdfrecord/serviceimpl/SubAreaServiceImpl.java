package com.pocupload.pdfrecord.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.dto.SubAreaDto;
import com.pocupload.pdfrecord.mapper.DocumentMapper;
import com.pocupload.pdfrecord.mapper.SubAreaMapper;
import com.pocupload.pdfrecord.model.DocumentModel;
import com.pocupload.pdfrecord.model.SubArea;
import com.pocupload.pdfrecord.repository.DocumentRepository;
import com.pocupload.pdfrecord.repository.SubAreaRepository;
import com.pocupload.pdfrecord.service.SubAreaService;

@Service
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	SubAreaRepository instrumentRepository;

	@Autowired
	DocumentRepository documentRepository;

	@Autowired
	SubAreaMapper instrumentMapper;

	@Autowired
	DocumentMapper documentMapper;

	@Override
	public ResponseDto<List<SubAreaDto>> getAllInstrumentInfo() {

		ResponseDto<List<SubAreaDto>> response = new ResponseDto<List<SubAreaDto>>();

		List<SubAreaDto> listOfInstrumentDtos = instrumentMapper
				.mapSubAreaListToSubAreaDtoList(instrumentRepository.findAll());

		Integer count = listOfInstrumentDtos.size();

		for (SubAreaDto instrumentDto : listOfInstrumentDtos) {
			instrumentDto.setCount(count);
			instrumentDto.setFileCount(documentRepository.existBySubAreaId(instrumentDto.getSubAreaId()));
		}

		if (listOfInstrumentDtos != null && !listOfInstrumentDtos.isEmpty()) {
			response.setMessage("Success!!");
			response.setResponse(listOfInstrumentDtos);
			response.setStatus(HttpStatus.OK.value());
		}

		return response;
	}

	@Override
	public ResponseDto<List<DocumentInfoDto>> getAllDocInfoByInstId(SubAreaDto instrumentDto) {
		// TODO Auto-generated method stub

		ResponseDto<List<DocumentInfoDto>> response = new ResponseDto<List<DocumentInfoDto>>();

		SubArea instrument = instrumentRepository.findById(instrumentDto.getSubAreaId()).orElse(null);

		List<DocumentInfoDto> listOfDocumentDtos = null;

		if (instrument != null) {
			List<DocumentModel> listOfDocumentModels = documentRepository.findAllBySubArea(instrument);
			listOfDocumentDtos = documentMapper.mapDocumentInfoModelListToDocumentDtoList(listOfDocumentModels);
		}

		if (listOfDocumentDtos != null && !listOfDocumentDtos.isEmpty()) {
			response.setMessage("Success!!");
			response.setResponse(listOfDocumentDtos);
			response.setStatus(HttpStatus.OK.value());
		} else {
			response.setMessage("Error!!");
			response.setResponse(null);
			response.setStatus(HttpStatus.OK.value());
		}

		return response;
	}

}
