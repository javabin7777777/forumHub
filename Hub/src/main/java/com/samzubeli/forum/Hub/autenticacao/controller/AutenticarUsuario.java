package com.samzubeli.forum.Hub.autenticacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samzubeli.forum.Hub.autenticacao.DB.AutenticacaoUsuarioDB;
import com.samzubeli.forum.Hub.autenticacao.model.DadosDeLogin;
import com.samzubeli.forum.Hub.autenticacao.model.DadosToken;
import com.samzubeli.forum.Hub.autenticacao.servico.GeradoraDeToken;

import jakarta.validation.Valid;

// Aqui é implementado o processo de autenticação do usuário na api.

@RestController
@RequestMapping("/login")
public class AutenticarUsuario {
	
	@Autowired
	private AuthenticationManager autenticador; // Autenticador do Spring-Boot.
	
	@Autowired
	private GeradoraDeToken geradorDeToken; // instância da classe GeradoraDeToken,que é a classe responsável por gerar o token.
	
	@PostMapping
	public ResponseEntity login(@RequestBody @Valid DadosDeLogin dados) {
		
		// Criar objeto de autenticação(login e password).
		var objDeAutenticacao = new UsernamePasswordAuthenticationToken(dados.login(), dados.password()); // DTO do Spring-Boot.
		
		// autenticar o usuário conforme login e senha fornecidos pelo mesmo.
		var autenticado = autenticador.authenticate(objDeAutenticacao);
		
		// gera o token conforme login senha do usuário.		
		var token = geradorDeToken.gerarToken( (AutenticacaoUsuarioDB) autenticado.getPrincipal()); 
		
		return ResponseEntity.ok(new DadosToken(token));
		// devolve como resposta o token dentro do dto DadosToken(formato Json),conforme autenticação do usuário com login e senha.
	}
	
}
