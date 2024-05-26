package com.pocupload.pdfrecord.service;

import java.util.List;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserRoleDto;

public interface UserRoleService {

	public ResponseDto<List<UserRoleDto>> getAllUserRole();

	public ResponseDto<Boolean> saveRoleUsers(UserRoleDto userRoleDto);

}
