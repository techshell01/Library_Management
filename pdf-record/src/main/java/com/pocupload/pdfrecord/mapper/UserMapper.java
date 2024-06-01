package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.pocupload.pdfrecord.dto.UserDto;
import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.repository.UserRepository;
import com.pocupload.pdfrecord.repository.UserRoleRepository;
import com.pocupload.pdfrecord.utils.PasswordUtils;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

	@Autowired
	UserRoleRepository userRoleRepository;

	@Autowired
	UserRepository userRepository;

	public abstract List<UserDto> mapUserListToUserDtoList(List<UserDetailsModel> listUserDetailsModels);

	@Mapping(target = "role", expression = "java(userRoleRepository.findById(userDetailsModel.getRole().getRoleId()).get().getRoleType())")
	@Mapping(target = "password", ignore = true)
	public abstract UserDto mapUserToUserDto(UserDetailsModel userDetailsModel);

	@Mapping(target = "role", expression = "java(userRoleRepository.findByRoleType(userDto.getRole()).get())")
	@Mapping(target = "password", source = "userDto", qualifiedByName = "hashingThePassword")
	@Mapping(target = "confirmPassword", source = "userDto", qualifiedByName = "setBackupPassword")
	@Mapping(target = "isActive", source = "isActive", qualifiedByName = "checkActiveStatus")
	public abstract UserDetailsModel mapUserDtoToUserModel(UserDto userDto);

	@Named("hashingThePassword")
	protected String hashingThePassword(UserDto userDto) {
		UserDetailsModel userDetailsModel = userRepository
				.findById(userDto.getUserId() == null ? 0 : userDto.getUserId()).orElse(null);
		return userDetailsModel == null ? PasswordUtils.hashPassword(userDto.getPassword())
				: userDetailsModel.getPassword();
	}

	@Named("checkActiveStatus")
	protected Boolean checkActiveStatus(Boolean isActive) {
		return isActive == null || isActive == true ? true : false;
	}

	@Named("setBackupPassword")
	protected String setBackupPassword(UserDto userDto) {
		return userDto != null ? userDto.getPassword() : "";
	}

}
