package com.samzubeli.forum.Hub.autenticacao.DB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUsuario extends JpaRepository<AutenticacaoUsuarioDB, Long> {

	UserDetails findByLogin(String login); 
	// Faz a consulta na tabela AutenticacaoUsuarioDB durante o processo de autenticação.

}
