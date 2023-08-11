package wms.user.services.userservice.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.repository.RoleRepository;
import wms.user.services.userservice.service.RoleService;
import wms.user.services.userservice.utils.RoleEnum;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role save(final Role role) {
		return roleRepository.save(role);
	}

	public Role update(final Role role) {
		return roleRepository.save(role);
	}

	public Optional<Role> findById(final Long roleId) {
		return roleRepository.findById(roleId);
	}

	public List<RoleEnum> findAll() {
		return roleRepository.findAll().stream().map(Role::getRole).collect(Collectors.toList());
	}

	public void delete(final Long roleId) {
		roleRepository.deleteById(roleId);
	}
}
