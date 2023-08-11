package wms.user.services.userservice.entity;

import javax.persistence.*;

import lombok.*;
import wms.user.services.userservice.utils.RoleEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ROLE", uniqueConstraints = { @UniqueConstraint(name = "unique_role", columnNames = "role") })
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long Id;

	@Enumerated(EnumType.STRING)
	protected RoleEnum role;
}
