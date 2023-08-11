package wms.user.services.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import wms.user.services.userservice.entity.Role;
import wms.user.services.userservice.model.ApiResponse;
import wms.user.services.userservice.service.RoleService;
import wms.user.services.userservice.utils.ApiUtils;
import wms.user.services.userservice.utils.RoleEnum;

@CrossOrigin
@RestController
@RequestMapping(RoleController.BASE_URL)
public class RoleController {
	public final static String BASE_URL = "/api/role";

	@Autowired
	private RoleService roleService;

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<Role>> create(@RequestBody Role role) {
		Role result = roleService.save(role);
		return ApiUtils.success(result, "Created", HttpStatus.CREATED);
	}

	@GetMapping("/get")
	public ResponseEntity<ApiResponse<List<RoleEnum>>> findAll() {
		List<RoleEnum> result = roleService.findAll();
		return ApiUtils.success(result, "Success", HttpStatus.OK);
	}
	
	@GetMapping("/get/{roleId}")
	public ResponseEntity<ApiResponse<Role>> findById(@PathVariable Long roleId) {
		Role result = roleService.findById(roleId).get();
		return ApiUtils.success(result, "Success", HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ApiResponse<Role>> update(@RequestBody Role role) {
		Role result = roleService.update(role);
		return ApiUtils.success(result, "Success", HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{roleId}")
	public ResponseEntity<ApiResponse<Role>> delete(@PathVariable Long roleId) {
		roleService.delete(roleId);
		return ApiUtils.success(null, "Deleted", HttpStatus.OK);
	}
}
