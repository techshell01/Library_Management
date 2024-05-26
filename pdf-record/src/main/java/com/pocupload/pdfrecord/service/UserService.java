package com.pocupload.pdfrecord.service;

import java.util.List;

import com.pocupload.pdfrecord.common.ResponseDto;
import com.pocupload.pdfrecord.dto.UserDto;

public interface UserService {

	public ResponseDto<UserDto> getUserByGivenId(Integer userId);

	public ResponseDto<List<UserDto>> getAllUsers();

	public ResponseDto<UserDto> saveUser(UserDto userDto);

	public ResponseDto<UserDto> updateUserDetails(UserDto userDto);

	public ResponseDto<Boolean> deleteUserById(Integer userId);

}
