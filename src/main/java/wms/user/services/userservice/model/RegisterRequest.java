package wms.user.services.userservice.model;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.*;
import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.utils.RoleEnum;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String email;
	private String password;
	private Set<RoleEnum> roles;

	public Set<Role> getRoles() {
		return roles.stream().map(Role::new).collect(Collectors.toSet());
	}
}
