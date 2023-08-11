package wms.user.services.userservice.model;

import java.io.Serializable;
import java.util.Set;

import lombok.*;
import wms.user.services.userservice.entity.Role;

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
	private Set<Role> roles;
}
