package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;

import com.pocupload.pdfrecord.dto.DocumentInfoDto;
import com.pocupload.pdfrecord.model.DocumentModel;

@Mapper(componentModel = "spring")
public abstract class DocumentMapper {

	@Value("${base.file.upload.dir}")
	private String baseDir;

	public abstract DocumentModel convertingDocumentInfoDtoToDocumentEntity(DocumentInfoDto documentInfoDto);

	public abstract List<DocumentInfoDto> mapDocumentInfoModelListToDocumentDtoList(
			List<DocumentModel> listDocumentModels);

	public abstract List<DocumentModel> mapDocumentInfoDtoListToDocumentModelList(
			List<DocumentInfoDto> listDocumentModelsInfoDtos);

	@Mapping(target = "directory", source = "listDocumentModels", qualifiedByName = "getDirectoryInfo")
	public abstract DocumentInfoDto mapDocumentInfoToDocumentDto(DocumentModel listDocumentModels);

	@Named("getDirectoryInfo")
	protected String getDirectoryInfo(DocumentModel documentModel) {
		return documentModel.getDirectory() != null ? baseDir.concat(documentModel.getDirectory()) : baseDir;
	}

}
