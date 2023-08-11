package wms.user.services.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import wms.user.services.userservice.entity.CustomUser;

@Repository
public interface UserRepository extends JpaRepository<CustomUser, Long> {
	CustomUser findByEmail(String email); 
}
