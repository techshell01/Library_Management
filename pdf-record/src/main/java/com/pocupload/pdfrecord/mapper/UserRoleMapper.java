package com.pocupload.pdfrecord.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.pocupload.pdfrecord.dto.UserRoleDto;
import com.pocupload.pdfrecord.model.UserRole;

@Mapper(componentModel = "spring")
public abstract class UserRoleMapper {

	public abstract List<UserRoleDto> mapRoleModelListToRoleDtoList(List<UserRole> listUserRoles);

	public abstract UserRoleDto mapRoleModelToRoleDto(UserRole userRole);

	public abstract UserRole mapRoleUserDtoToRoleUser(UserRoleDto userRoleDto);

}
