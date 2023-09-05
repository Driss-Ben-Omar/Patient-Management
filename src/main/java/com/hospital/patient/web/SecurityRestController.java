package com.hospital.patient.web;


import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityRestController {

	@GetMapping("/profile")
	public Authentication authentication(Authentication authentication) {
		return authentication;
	}
	
	@GetMapping("/profile1")
	public Authentication authentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	@GetMapping("/principal")
	public Principal principal(Principal principal) {
		return principal;
	}
	
}
