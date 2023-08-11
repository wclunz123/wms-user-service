package wms.user.services.userservice.utils;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import wms.user.services.userservice.model.ApiResponse;

public class ApiUtils {
	public static <T> ApiResponse<T> successResponse(T data, String message) {
		return new ApiResponse<>(data, message, LocalDateTime.now());
	}

	public static <T> ResponseEntity<ApiResponse<T>> success(T data, String message, HttpStatus status) {
		return ResponseEntity.status(status).body(new ApiResponse<T>(data, message, LocalDateTime.now()));
	}
}
