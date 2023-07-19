package wms.user.services.userservice.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
	private HttpStatus code;
	private String message;
	private LocalDateTime timestamp;
}
