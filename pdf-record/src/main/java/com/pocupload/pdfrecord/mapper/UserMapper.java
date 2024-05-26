package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.pocupload.pdfrecord.dto.UserDto;
import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.repository.UserRoleRepository;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	UserRoleRepository userRoleRepository;

	public abstract List<UserDto> mapUserListToUserDtoList(List<UserDetailsModel> listUserDetailsModels);

	@Mapping(target = "role", expression = "java(userRoleRepository.findById(userDetailsModel.getRole().getRoleId()).get().getRoleType())")
	public abstract UserDto mapUserToUserDto(UserDetailsModel userDetailsModel);

	@Mapping(target = "role", expression = "java(userRoleRepository.findByRoleType(userDto.getRole()).get())")
	public abstract UserDetailsModel mapUserDtoToUserModel(UserDto userDto);

}
