package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentInfoDto {

	private Integer refernceId;

	private String fileSize;

	private String fileName;

	private String extension;

	private Integer startIndex;

	private Integer endIndex;

	private Boolean isActive;

	private String directory;

	private String fileUrl;

}
