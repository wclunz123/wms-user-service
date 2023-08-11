package wms.user.services.userservice.service;

import java.util.List;
import java.util.Optional;

import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.utils.RoleEnum;

public interface RoleService {

	public Role save(final Role role);
	
	public Role update(final Role role);
	
	public Optional<Role> findById(final Long roleId);
	
	public List<RoleEnum> findAll();
	
	public void delete(final Long roleId);
}
