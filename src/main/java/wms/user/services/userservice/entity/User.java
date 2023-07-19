package wms.user.services.userservice.entity;

import wms.user.services.userservice.utils.UserRole;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Column(nullable = false)
	protected String username;

	@Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) CHECK (email ~* '^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$')")
	@Email(message = "Invalid email format.")
	protected String email;

	@JsonIgnore
	@Column(nullable = false)
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
	protected String password;

	@Enumerated(EnumType.STRING)
	protected UserRole role;

}
