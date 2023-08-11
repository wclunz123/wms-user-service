package wms.user.services.userservice.utils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleEnum {

	SUPER_ADMIN, ADMIN, MANAGER, FINANCE, HUMAN_RESOURCE, OPERATION, VIEWER;

	@JsonCreator
	public static RoleEnum fromString(String value) {
		return RoleEnum.valueOf(value.toUpperCase());
	}

}
