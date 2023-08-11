package wms.user.services.userservice.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import wms.user.services.userservice.utils.RoleEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ROLE", uniqueConstraints = { @UniqueConstraint(name = "unique_role_key", columnNames = "role") })
public class Role {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Enumerated(EnumType.STRING)
	protected RoleEnum role;

	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<CustomUser> users = new HashSet<>();

	public Role(RoleEnum role) {
		this.role = role;
	}
}
