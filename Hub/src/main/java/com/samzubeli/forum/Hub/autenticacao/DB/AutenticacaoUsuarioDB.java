package com.samzubeli.forum.Hub.autenticacao.DB;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name="usuario")
@EqualsAndHashCode(of ="id")
@Entity(name="USUARIO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AutenticacaoUsuarioDB implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String login;
	private String password;
	
	// Métodos da interface UserDetails do Spring-Boot.
	
	// Controla o perfil de acesso do usuário.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority("ROLE_USER")); // ROLE_USER -> Perfil padrão do Spring-Boot.
	}
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return login;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}
}
