package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.pocupload.pdfrecord.dto.UserDto;
import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.repository.UserRoleRepository;
import com.pocupload.pdfrecord.utils.PasswordUtils;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	UserRoleRepository userRoleRepository;

	public abstract List<UserDto> mapUserListToUserDtoList(List<UserDetailsModel> listUserDetailsModels);

	@Mapping(target = "role", expression = "java(userRoleRepository.findById(userDetailsModel.getRole().getRoleId()).get().getRoleType())")
	public abstract UserDto mapUserToUserDto(UserDetailsModel userDetailsModel);

	@Mapping(target = "role", expression = "java(userRoleRepository.findByRoleType(userDto.getRole()).get())")
	@Mapping(target = "password", source = "password", qualifiedByName = "hashingThePassword")
	@Mapping(target = "isActive", source = "isActive", qualifiedByName = "checkActiveStatus")
	public abstract UserDetailsModel mapUserDtoToUserModel(UserDto userDto);

	@Named("hashingThePassword")
	protected String hashingThePassword(String password) {
		return !password.isEmpty() ? PasswordUtils.hashPassword(password) : "";
	}

	@Named("checkActiveStatus")
	protected Boolean checkActiveStatus(Boolean isActive) {
		return isActive == null || isActive == true ? true : false;
	}

}
