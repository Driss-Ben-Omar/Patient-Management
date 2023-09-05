package com.hospital.patient.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hospital.patient.entities.Patient;
import com.hospital.patient.entities.repository.PatientRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class PatientController {

	private PatientRepository patientRepository;
	
	@GetMapping("/user/index")
	@PreAuthorize("hasRole('USER')")
	public String index(Model model,
			@RequestParam(name="page",defaultValue = "0") int page,
			@RequestParam(name="size",defaultValue = "5")int size,
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		
		Page<Patient> patients=patientRepository.findByNomContains(keyword, PageRequest.of(page, size));
		
		model.addAttribute("patients", patients.getContent());
		model.addAttribute("pages", new int[patients.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		
		return "patients";
	}
	
	@GetMapping("/admin/delete")
	@PreAuthorize("hasRole('ADMIN')")
	public String delete(Long id,String keyword,int page) {
		
		patientRepository.deleteById(id);
		
		return "redirect:/user/index?page="+page+"&keyword="+keyword;
	}
	
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public String home() {
				
		return "redirect:/user/index";
	}
	
	@GetMapping("/admin/formPatients")
	@PreAuthorize("hasRole('ADMIN')")
	public String formPatient(Model model) {
		
		model.addAttribute("patient", new Patient());
		
		return "formPatients";
	}
	
	
	@PostMapping("/admin/savePatient")
	@PreAuthorize("hasRole('ADMIN')")
	public String savePatient(@Valid Patient patient,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "formPatients";
		}
		patientRepository.save(patient);
		return "redirect:/user/index?keyword="+patient.getNom();
	}
	
	@GetMapping("/admin/editPatient")
	@PreAuthorize("hasRole('ADMIN')")
	public String editPatient(Model model,@RequestParam(name="id") Long id) {
		Patient patient=patientRepository.findById(id).get();
		
		model.addAttribute("patient", patient);
		
		return "editPatient";
	}
	
}
