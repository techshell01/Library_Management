package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfoDto {

	private Integer id;

	private String name;

	private Integer count;

	private Integer fileCount;

}
