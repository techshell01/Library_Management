package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.pocupload.pdfrecord.dto.DepartmentDto;
import com.pocupload.pdfrecord.model.Department;

@Mapper(componentModel = "spring")
public abstract class DepartmentMapper {

	public abstract List<DepartmentDto> mapDepartmentToDepartDtoList(List<Department> listOfDepartMent);

	@Mapping(target = "count", ignore = true)
	@Mapping(target = "fileCount", ignore = true)
	public abstract DepartmentDto mapDepartmentToDepartmentDto(Department department);

}
