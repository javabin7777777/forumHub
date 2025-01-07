package com.samzubeli.forum.Hub.autenticacao.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//Configurar(customizar) o processo de autenticação fornecido pelo Spring-Boot.

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private FiltrarAutenticado filtrarAutenticado;
	
	
	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {
		return http.csrf(crsf -> crsf.disable()) // desabilitar o cross-origin-site do spring-boot
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))	// configurar para stateless.			
				.authorizeHttpRequests( req -> {
					req.requestMatchers("/login").permitAll();// permitir a requisição de login e negar todas outras.
					req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll(); // permitir acessar a documentação da API,criada usando o  springdoc.
					req.anyRequest().authenticated(); // qualquer outra requisição,exceto login e documentação,deverá estar autenticada.
					})		 
				.addFilterBefore(filtrarAutenticado, UsernamePasswordAuthenticationFilter.class) // o filtro da aplicação é aplicado antes do filtro do Spring Boot.
				.build();		
	}
	
	
	// Fazer o Spring-Boot Configurar - AuthenticationManager - da classe AutenticarUsuario.
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		
		return configuration.getAuthenticationManager();
	}
	
	// Cifrar a senha conforme algoritmo BCRYPT.
	@Bean
	public PasswordEncoder passwordCifrado() {
		return new BCryptPasswordEncoder();
		
	}
}
