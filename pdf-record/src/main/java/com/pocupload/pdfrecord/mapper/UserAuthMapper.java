package com.pocupload.pdfrecord.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import com.pocupload.pdfrecord.dto.LoginCredResponseDto;
import com.pocupload.pdfrecord.model.UserDetailsModel;
import com.pocupload.pdfrecord.model.UserRole;
import com.pocupload.pdfrecord.repository.UserRoleRepository;

@Mapper(componentModel = "spring")
public abstract class UserAuthMapper {

	@Autowired
	UserRoleRepository userRoleRepository;

	@Mapping(target = "role", source = "role", qualifiedByName = "getRole")
	public abstract LoginCredResponseDto mapUserCredToUserDetailsModel(UserDetailsModel userDetailsModel);

	@Named("getRole")
	protected String getRole(UserRole role) {
		return role != null ? role.getRoleType() : "";
	}
}
