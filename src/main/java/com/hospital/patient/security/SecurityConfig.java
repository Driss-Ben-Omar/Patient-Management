package com.hospital.patient.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager=new JdbcUserDetailsManager(dataSource);
		return jdbcUserDetailsManager;
	}

//	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		String password = passwordEncoder.encode("1234");

		InMemoryUserDetailsManager detailsManager = new InMemoryUserDetailsManager(
				User.withUsername("driss").password(password).roles("USER").build(),
				User.withUsername("omar").password(passwordEncoder.encode("0000")).roles("USER", "ADMIN").build(),
				User.withUsername("mouad").password(passwordEncoder.encode("test")).roles("USER").build());
		return detailsManager;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin();
//		httpSecurity.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN");
//		httpSecurity.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/user/**")).hasRole("USER");
		httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
		httpSecurity.exceptionHandling().accessDeniedPage("/notAuthrized");
		return httpSecurity.build();
	}

}
