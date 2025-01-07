package com.samzubeli.forum.Hub.autenticacao.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.samzubeli.forum.Hub.autenticacao.DB.IUsuario;

// Aqui é implementado o processo para buscar o login no DB.

@Service
public class AutenticationService implements UserDetailsService {
	
	@Autowired
	private IUsuario repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return repository.findByLogin(username);
	}

}
