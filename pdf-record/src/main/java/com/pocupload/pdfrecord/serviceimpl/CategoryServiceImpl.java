package com.pocupload.pdfrecord.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.pocupload.pdfrecord.common.ResponseDtoForCategory;
import com.pocupload.pdfrecord.common.ResponseDtoForDocuments;
import com.pocupload.pdfrecord.dto.CategoryInfoDto;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.mapper.CategoryMapperService;
import com.pocupload.pdfrecord.mapper.DocumentMapper;
import com.pocupload.pdfrecord.mapper.PlantMapperService;
import com.pocupload.pdfrecord.model.Department;
import com.pocupload.pdfrecord.model.DocumentModel;
import com.pocupload.pdfrecord.model.MainHead;
import com.pocupload.pdfrecord.model.Plants;
import com.pocupload.pdfrecord.model.SubArea;
import com.pocupload.pdfrecord.repository.PlantRepository;
import com.pocupload.pdfrecord.repository.DepartmentRepository;
import com.pocupload.pdfrecord.repository.DocumentRepository;
import com.pocupload.pdfrecord.repository.MainHeadRepository;
import com.pocupload.pdfrecord.repository.SubAreaRepository;
import com.pocupload.pdfrecord.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	PlantRepository plantRepository;

	@Autowired
	SubAreaRepository subAreaRepository;

	@Autowired
	CategoryMapperService categoryMapperService;

	@Autowired
	DocumentMapper documentMapper;

	@Autowired
	DepartmentRepository departmentRepository;

	@Autowired
	PlantMapperService plantMapperService;

	@Autowired
	DocumentRepository documentRepository;

	@Autowired
	MainHeadRepository mainHeadRepository;

	@Override
	public ResponseDtoForCategory<List<CategoryInfoDto>> getAllDocumentInfo(String category, Integer id) {

		ResponseDtoForCategory<List<CategoryInfoDto>> response = new ResponseDtoForCategory<List<CategoryInfoDto>>();

		if (category.equalsIgnoreCase("plants")) {

			List<Plants> listOfPlants = plantRepository.findAll();

			List<CategoryInfoDto> listOfCategoryInfoDto = categoryMapperService.mapPlantListToCatDtoList(listOfPlants);

			for (CategoryInfoDto categoryDto : listOfCategoryInfoDto) {
				categoryDto.setFileCount(documentRepository.existByPlantId(categoryDto.getId()));
				categoryDto.setCount(listOfPlants.size());
			}

			if (listOfCategoryInfoDto != null && !listOfCategoryInfoDto.isEmpty()) {
				response.setMassage("success!!");
				response.setCategoryList(listOfCategoryInfoDto);
				response.setStatus(HttpStatus.OK.value());
			}

			return response;

		} else if (category.equalsIgnoreCase("sub-area")) {

			List<SubArea> listOfSubArea = subAreaRepository.findAll();

			List<CategoryInfoDto> listOfCategoryInfoDto = categoryMapperService
					.mapSubAreaListToCatInfoDtoList(listOfSubArea);

			for (CategoryInfoDto categoryDto : listOfCategoryInfoDto) {
				categoryDto.setFileCount(documentRepository.existBySubAreaId(categoryDto.getId()));
				categoryDto.setCount(listOfSubArea.size());
			}

			if (listOfCategoryInfoDto != null && !listOfCategoryInfoDto.isEmpty()) {
				response.setMassage("success!!");
				response.setCategoryList(listOfCategoryInfoDto);
				response.setStatus(HttpStatus.OK.value());
			}

			return response;

		} else if (category.equalsIgnoreCase("department")) {

			List<Department> listOfDepartment = departmentRepository.findAll();

			List<CategoryInfoDto> listOfCategoryInfoDto = categoryMapperService
					.mapDepartmentListToCatInfoDtoList(listOfDepartment);

			for (CategoryInfoDto categoryDto : listOfCategoryInfoDto) {
				categoryDto.setFileCount(documentRepository.existBySubAreaId(categoryDto.getId()));
				categoryDto.setCount(listOfDepartment.size());
			}

			if (listOfCategoryInfoDto != null && !listOfCategoryInfoDto.isEmpty()) {
				response.setMassage("success!!");
				response.setCategoryList(listOfCategoryInfoDto);
				response.setStatus(HttpStatus.OK.value());
			}

			return response;

		} else if (category.equalsIgnoreCase("main-head")) {

			List<MainHead> listOfMainHead = mainHeadRepository.findAll();

			List<CategoryInfoDto> listOfCategoryInfoDto = categoryMapperService
					.mapMainHeadListToCatInfoDtoList(listOfMainHead);

			for (CategoryInfoDto categoryDto : listOfCategoryInfoDto) {
				categoryDto.setFileCount(documentRepository.existBySubAreaId(categoryDto.getId()));
				categoryDto.setCount(listOfMainHead.size());
			}

			if (listOfCategoryInfoDto != null && !listOfCategoryInfoDto.isEmpty()) {
				response.setMassage("success!!");
				response.setCategoryList(listOfCategoryInfoDto);
				response.setStatus(HttpStatus.OK.value());
			}

			return response;

		} else {

			response.setMassage("No Record Get!!");
			response.setCategoryList(new ArrayList<CategoryInfoDto>());
			response.setStatus(HttpStatus.OK.value());

			return response;

		}

	}

	@Override
	public ResponseDtoForDocuments<List<DocumentInfoDto>> getAllDocumentInfoById(String category, Integer id) {

		ResponseDtoForDocuments<List<DocumentInfoDto>> response = new ResponseDtoForDocuments<List<DocumentInfoDto>>();

		List<DocumentModel> listDocumentModels = new ArrayList<>();

		if (category.equalsIgnoreCase("sub-area")) {
			SubArea subArea = subAreaRepository.findById(id).orElse(null);
			listDocumentModels = documentRepository.findAllBySubArea(subArea);
		} else if (category.equalsIgnoreCase("plants")) {
			Plants plants = plantRepository.findById(id).orElse(null);
			listDocumentModels = documentRepository.findAllByplant(plants);
		} else if (category.equalsIgnoreCase("department")) {
			Department department = departmentRepository.findById(id).orElse(null);
			listDocumentModels = documentRepository.findAllByDepartment(department);
		} else if (category.equalsIgnoreCase("main-head")) {
			MainHead mainHead = mainHeadRepository.findById(id).orElse(null);
			listDocumentModels = documentRepository.findAllByMainHead(mainHead);
		}

		if (listDocumentModels != null && !listDocumentModels.isEmpty()) {
			List<DocumentInfoDto> listDocumentInfoDtos = documentMapper
					.mapDocumentInfoModelListToDocumentDtoList(listDocumentModels);

			if (listDocumentInfoDtos != null && !listDocumentInfoDtos.isEmpty()) {
				response.setDocumentLists(listDocumentInfoDtos);
				response.setMessage("Success!!");
				response.setStatus(HttpStatus.OK.value());
			} else {
				response.setDocumentLists(new ArrayList<>());
				response.setMessage("Error!!");
				response.setStatus(HttpStatus.OK.value());
			}

		} else {
			response.setDocumentLists(new ArrayList<>());
			response.setMessage("No Data Found!!");
			response.setStatus(HttpStatus.OK.value());
		}
		return response;
	}

}
