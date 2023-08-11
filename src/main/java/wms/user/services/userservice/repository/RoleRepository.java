package wms.user.services.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.utils.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRole(RoleEnum role);

}
