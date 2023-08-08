package wms.user.services.userservice.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wms.user.services.userservice.utils.UserRole;

@Getter
@Setter
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
	private UserRole role;
}
