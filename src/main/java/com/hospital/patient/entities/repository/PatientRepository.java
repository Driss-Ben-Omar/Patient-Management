package com.hospital.patient.entities.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hospital.patient.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	Page<Patient> findByNomContains(String keyword,Pageable pageable);
	
	@Query("select p from Patient p where p.nom like :name")
	Page<Patient> searche(@Param("name") String keyword,Pageable pageable);
	
}
