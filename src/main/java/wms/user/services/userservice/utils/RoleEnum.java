package wms.user.services.userservice.utils;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RoleEnum implements Serializable {

	SUPER_ADMIN, ADMIN, MANAGER, FINANCE, HUMAN_RESOURCE, MECHANIC, OPERATION, VIEWER;

	@JsonCreator
	public static RoleEnum fromString(String value) {
		return RoleEnum.valueOf(value.toUpperCase());
	}

}
