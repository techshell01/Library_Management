package com.pocupload.pdfrecord.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubAreaDto {

	private Integer subAreaId;

	private String subAreaName;

	private Integer count;

	private Integer fileCount;

}
