package com.pocupload.pdfrecord.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDtoForCategory<T> {

	private String massage;

	private T categoryList;

	private Integer status;

}
