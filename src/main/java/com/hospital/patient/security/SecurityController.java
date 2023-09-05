package com.hospital.patient.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

	@GetMapping("/notAuthrized")
	public String notAutorized() {
		return "403";
	}
	
}
