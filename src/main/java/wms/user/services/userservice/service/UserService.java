package wms.user.services.userservice.service;

import java.util.List;
import java.util.Optional;

import wms.user.services.userservice.entity.CustomUser;
import wms.user.services.userservice.model.RegisterRequest;

public interface UserService {
	public CustomUser register(RegisterRequest request);
	
	public Optional<CustomUser> findById(final Long userId);
	
	public List<CustomUser> findAll();
}
