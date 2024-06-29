package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.model.Plants;
import com.pocupload.pdfrecord.model.Department;
import com.pocupload.pdfrecord.model.DocumentModel;
import com.pocupload.pdfrecord.model.MainHead;
import com.pocupload.pdfrecord.model.SubArea;
import com.pocupload.pdfrecord.repository.PlantRepository;
import com.pocupload.pdfrecord.repository.DepartmentRepository;
import com.pocupload.pdfrecord.repository.MainHeadRepository;
import com.pocupload.pdfrecord.repository.SubAreaRepository;

@Mapper(componentModel = "spring")
public abstract class DocumentMapper {

	@Value("${base.file.upload.dir}")
	private String baseDir;

	@Autowired
	SubAreaRepository subAreaRepository;

	@Autowired
	PlantRepository plantRepository;

	@Autowired
	MainHeadRepository mainHeadRepository;

	@Autowired
	DepartmentRepository departmentRepository;

	@Mapping(target = "subArea", source = "documentInfoDto", qualifiedByName = "getBySubAreaName")
	@Mapping(target = "plant", source = "documentInfoDto.plant", qualifiedByName = "getPlantName")
	@Mapping(target = "department", source = "documentInfoDto", qualifiedByName = "getByDepartment")
	@Mapping(target = "mainHead", source = "documentInfoDto.mainHead", qualifiedByName = "getByMainHeadName")
	public abstract DocumentModel convertingDocumentInfoDtoToDocumentEntity(DocumentInfoDto documentInfoDto);

	public abstract List<DocumentInfoDto> mapDocumentInfoModelListToDocumentDtoList(
			List<DocumentModel> listDocumentModels);

	public abstract List<DocumentModel> mapDocumentInfoDtoListToDocumentModelList(
			List<DocumentInfoDto> listDocumentModelsInfoDtos);

	@Mapping(target = "directory", source = "listDocumentModels", qualifiedByName = "getDirectoryInfo")
	@Mapping(target = "subArea", source = "listDocumentModels", qualifiedByName = "getSubAreaNameByDocument")
	@Mapping(target = "plant", source = "listDocumentModels", qualifiedByName = "getPlantDetails")
	@Mapping(target = "department", source = "listDocumentModels", qualifiedByName = "getDepartmentDetails")
	@Mapping(target = "mainHead", source = "listDocumentModels", qualifiedByName = "getByMainHeadDetails")
	public abstract DocumentInfoDto mapDocumentInfoToDocumentDto(DocumentModel listDocumentModels);

	@Named("getDirectoryInfo")
	protected String getDirectoryInfo(DocumentModel documentModel) {
		return documentModel.getDirectory() != null ? baseDir.concat(documentModel.getDirectory()) : baseDir;
	}

	@Named("getBySubAreaName")
	protected SubArea getBySubAreaName(DocumentInfoDto documentInfoDto) {
		return documentInfoDto != null
				? subAreaRepository.existBySubAreaNameByAllFields(documentInfoDto.getSubArea(),
						documentInfoDto.getDepartment(), documentInfoDto.getPlant(), documentInfoDto.getMainHead())
				: null;
	}

	@Named("getByMainHeadName")
	protected MainHead getByMainHeadName(String mainHeadName) {
		return mainHeadName != null ? mainHeadRepository.existByMainHeadName(mainHeadName) : null;
	}

	@Named("getPlantName")
	protected Plants getPlantName(String plant) {
		return plant != null ? plantRepository.existByPlantName(plant) : null;
	}

	@Named("getByDepartment")
	protected Department getByDepartment(DocumentInfoDto documentInfoDto) {
		return documentInfoDto != null
				? departmentRepository.existByDepartName(documentInfoDto.getDepartment(), documentInfoDto.getPlant())
				: null;
	}

	@Named("getSubAreaNameByDocument")
	protected String getSubAreaNameByDocument(DocumentModel documentModel) {
		return documentModel != null ? documentModel.getSubArea().getSubAreaName() : "";
	}

	@Named("getPlantDetails")
	protected String getPlantDetails(DocumentModel documentModel) {
		return documentModel != null ? documentModel.getPlant().getPlantName() : "";
	}

	@Named("getDepartmentDetails")
	protected String getDepartmentDetails(DocumentModel documentModel) {
		return documentModel != null ? documentModel.getDepartment().getDepartmentName() : "";
	}

	@Named("getByMainHeadDetails")
	protected String getByMainHeadDetails(DocumentModel documentModel) {
		return documentModel != null ? documentModel.getMainHead().getHeadName() : "";
	}

}
