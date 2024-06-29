package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.pocupload.pdfrecord.dto.CategoryInfoDto;
import com.pocupload.pdfrecord.model.Department;
import com.pocupload.pdfrecord.model.MainHead;
import com.pocupload.pdfrecord.model.Plants;
import com.pocupload.pdfrecord.model.SubArea;

@Mapper(componentModel = "spring")
public abstract class CategoryMapperService {

	public abstract List<CategoryInfoDto> mapPlantListToCatDtoList(List<Plants> listOfPlantDto);

	@Mapping(target = "id", source = "plants.plantId")
	@Mapping(target = "name", source = "plants.plantName")
	@Mapping(target = "count", ignore = true)
	@Mapping(target = "fileCount", ignore = true)
	public abstract CategoryInfoDto mapPlantDtoToCategortDto(Plants plants);

	public abstract List<CategoryInfoDto> mapSubAreaListToCatInfoDtoList(List<SubArea> listOfSubArea);

	@Mapping(target = "id", source = "subArea.subAreaId")
	@Mapping(target = "name", source = "subArea.subAreaName")
	@Mapping(target = "count", ignore = true)
	@Mapping(target = "fileCount", ignore = true)
	public abstract CategoryInfoDto mapSubAreaToCatInfoDto(SubArea subArea);

	public abstract List<CategoryInfoDto> mapDepartmentListToCatInfoDtoList(List<Department> listOfDepartment);

	@Mapping(target = "id", source = "department.departmentId")
	@Mapping(target = "name", source = "department.departmentName")
	@Mapping(target = "count", ignore = true)
	@Mapping(target = "fileCount", ignore = true)
	public abstract CategoryInfoDto mapDepartmentToCatInfoDto(Department department);

	public abstract List<CategoryInfoDto> mapMainHeadListToCatInfoDtoList(List<MainHead> listOfMainHead);

	@Mapping(target = "id", source = "mainHead.headId")
	@Mapping(target = "name", source = "mainHead.headName")
	@Mapping(target = "count", ignore = true)
	@Mapping(target = "fileCount", ignore = true)
	public abstract CategoryInfoDto mapMainHeadToCatInfoDto(MainHead mainHead);

}
