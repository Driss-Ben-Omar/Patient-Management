package com.hospital.patient;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.hospital.patient.entities.Patient;
import com.hospital.patient.entities.repository.PatientRepository;

@SpringBootApplication
public class HospitalManagementApplication implements CommandLineRunner{
	
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(HospitalManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		 patientRepository.save(new Patient(null,"driss benomar", new Date(), false, 101));
		 patientRepository.save(new Patient(null,"mouad benthami", new Date(), false, 580));
		 patientRepository.save(new Patient(null,"yousra el fraani", new Date(), false, 600));
		
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//@Bean
	CommandLineRunner commandLineRunnerJdbcUsers(JdbcUserDetailsManager jdbcUserDetailsManager) {
		PasswordEncoder passwordEncoder=passwordEncoder();
		return args->{
			jdbcUserDetailsManager.createUser(
					User.withUsername("driss").password(passwordEncoder.encode("1234")).roles("USER").build()
					);
			jdbcUserDetailsManager.createUser(
					User.withUsername("omar").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()
					);
			jdbcUserDetailsManager.createUser(
					User.withUsername("mouad").password(passwordEncoder.encode("1234")).roles("USER").build()
					);
		};
	}

}
