package com.pocupload.pdfrecord.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.DepartmentDto;
import com.pocupload.pdfrecord.dto.DepartmentWiseDocumentDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.mapper.DepartmentMapper;
import com.pocupload.pdfrecord.mapper.DocumentMapper;
import com.pocupload.pdfrecord.model.Department;
import com.pocupload.pdfrecord.model.DocumentModel;
import com.pocupload.pdfrecord.repository.DepartmentRepository;
import com.pocupload.pdfrecord.repository.DocumentRepository;
import com.pocupload.pdfrecord.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	DepartmentMapper departmentMapper;

	@Autowired
	DocumentMapper documentMapper;

	@Autowired
	DocumentRepository documentRepository;

	@Override
	public ResponseDto<List<DepartmentDto>> getAllResponseDto() {

		ResponseDto<List<DepartmentDto>> response = new ResponseDto<List<DepartmentDto>>();

		List<DepartmentDto> listOfDepartmentDtos = departmentMapper
				.mapDepartmentToDepartDtoList(departmentRepository.findAll());

		Integer count = listOfDepartmentDtos.size();

		for (DepartmentDto departmentDto : listOfDepartmentDtos) {

			departmentDto.setFileCount(documentRepository.existByDepartmentId(departmentDto.getDepartmentId()));
			departmentDto.setCount(count);

		}

		if (listOfDepartmentDtos != null && !listOfDepartmentDtos.isEmpty()) {
			response.setMessage("Success!!");
			response.setResponse(listOfDepartmentDtos);
			response.setStatus(HttpStatus.OK.value());
		}

		return response;
	}

	@Override
	public ResponseDto<List<DocumentInfoDto>> getAllDocumentInfoForDepId(DepartmentDto departmentDto) {

		ResponseDto<List<DocumentInfoDto>> response = new ResponseDto<List<DocumentInfoDto>>();

		Department department = departmentRepository.findById(departmentDto.getDepartmentId()).orElse(null);

		List<DocumentInfoDto> listOfDocumentInfoDtos = null;

		if (department != null) {
			List<DocumentModel> listDocumentModels = documentRepository.findAllByDepartment(department);
			listOfDocumentInfoDtos = documentMapper.mapDocumentInfoModelListToDocumentDtoList(listDocumentModels);
		}

		if (listOfDocumentInfoDtos != null && !listOfDocumentInfoDtos.isEmpty()) {
			response.setMessage("Success!!");
			response.setResponse(listOfDocumentInfoDtos);
			response.setStatus(HttpStatus.OK.value());
		} else {
			response.setMessage("Error!!");
			response.setResponse(null);
			response.setStatus(HttpStatus.OK.value());
		}

		return response;
	}

	@Override
	public ResponseDto<List<DepartmentWiseDocumentDto>> getAllDocInfoUnderSpecDeprt() {

		ResponseDto<List<DepartmentWiseDocumentDto>> respone = new ResponseDto<List<DepartmentWiseDocumentDto>>();

		DepartmentWiseDocumentDto departmentWiseDocumentDto = null;

		List<Department> listOfDepartList = departmentRepository.findAll();

		List<DepartmentWiseDocumentDto> listOfDepartmentWiseDocumentDto = new ArrayList<DepartmentWiseDocumentDto>();

		for (Department department : listOfDepartList) {

			departmentWiseDocumentDto = new DepartmentWiseDocumentDto();

			departmentWiseDocumentDto.setDepartmentName(department.getDepartmentName());
			departmentWiseDocumentDto
					.setDoucmentCount(documentRepository.existByDepartmentId(department.getDepartmentId()));

			listOfDepartmentWiseDocumentDto.add(departmentWiseDocumentDto);

		}

		if (listOfDepartmentWiseDocumentDto != null && !listOfDepartmentWiseDocumentDto.isEmpty()) {
			respone.setMessage("Success!!");
			respone.setResponse(listOfDepartmentWiseDocumentDto);
			respone.setStatus(HttpStatus.OK.value());
		} else {
			respone.setMessage("No Data Present");
			respone.setResponse(listOfDepartmentWiseDocumentDto);
			respone.setStatus(HttpStatus.OK.value());
		}

		return respone;
	}

}
