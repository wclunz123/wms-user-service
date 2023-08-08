package wms.user.services.userservice.service;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.model.RegisterRequest;

public interface UserService {
	public CustomUser register(RegisterRequest request);
}
