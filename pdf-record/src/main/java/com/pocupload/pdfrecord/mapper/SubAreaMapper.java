package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.pocupload.pdfrecord.dto.SubAreaDto;
import com.pocupload.pdfrecord.model.SubArea;

@Mapper(componentModel = "spring")
public abstract class SubAreaMapper {

	public abstract List<SubAreaDto> mapSubAreaListToSubAreaDtoList(List<SubArea> listOfInstrument);

	@Mapping(target = "count", ignore = true)
	@Mapping(target = "fileCount", ignore = true)
	public abstract SubAreaDto mapSubAreaToSubAreaDto(SubArea instrument);

}
