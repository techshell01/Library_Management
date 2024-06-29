package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlantsDto {

	private Integer plantId;

	private String plantName;

	private Integer count;

	private Integer fileCount;

}
