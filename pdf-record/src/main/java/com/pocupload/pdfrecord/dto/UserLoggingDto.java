package com.pocupload.pdfrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoggingDto {

    private String userName;

    private Date timeStamp;

    private String action;

    private Integer departmentId;

    private Integer roleId;
}
