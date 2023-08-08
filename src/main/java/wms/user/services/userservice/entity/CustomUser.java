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
@ToString
@Entity
@Table(name = "USERS")
public class CustomUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Column(nullable = false)
	protected String username;

//	@Column(nullable = false, unique = true, columnDefinition = "VARCHAR(255) CHECK (email ~* '^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$')")
	@Column(nullable = false, unique = true)
	@Email(message = "Invalid email format.")
	protected String email;

	@JsonIgnore
	@Column(nullable = false)
	protected String password;

	@Enumerated(EnumType.STRING)
	protected UserRole role;

}
