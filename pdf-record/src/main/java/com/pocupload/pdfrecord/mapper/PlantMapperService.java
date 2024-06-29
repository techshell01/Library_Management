package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pocupload.pdfrecord.dto.PlantsDto;
import com.pocupload.pdfrecord.model.Plants;

@Mapper(componentModel = "spring")
public abstract class PlantMapperService {

	public abstract List<PlantsDto> mapAreaModelToAreaDtoList(List<Plants> listOfArea);

	public abstract PlantsDto mapAreaModelToDto(Plants area);

}
