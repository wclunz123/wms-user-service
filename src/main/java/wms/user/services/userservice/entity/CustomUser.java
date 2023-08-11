package wms.user.services.userservice.entity;

import lombok.*;
import wms.user.services.userservice.utils.RoleEnum;

import java.util.Set;
import java.util.stream.Collectors;

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
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Column(nullable = false)
	protected String username;

	@Column(nullable = false, unique = true)
	@Email(message = "Invalid email format.")
	protected String email;

	@JsonIgnore
	@Column(nullable = false)
	protected String password;

	@ManyToMany
	@JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	protected Set<Role> roles;
	
	public Set<RoleEnum> getRoles() {
		return roles.stream().map(Role::getRole).collect(Collectors.toSet());
	}
}
