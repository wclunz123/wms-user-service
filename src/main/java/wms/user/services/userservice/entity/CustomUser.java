package wms.user.services.userservice.entity;

import lombok.*;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "USERS", uniqueConstraints = { @UniqueConstraint(name = "unique_email", columnNames = "email") })
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

	@ManyToMany
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	protected Set<Role> roles;

}
