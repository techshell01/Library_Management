package com.pocupload.pdfrecord.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDtoForDocuments<T> {

	private String message;

	private T documentLists;

	private Integer status;

}
