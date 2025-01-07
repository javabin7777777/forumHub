package com.samzubeli.forum.Hub.autenticacao.servico;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.samzubeli.forum.Hub.autenticacao.DB.AutenticacaoUsuarioDB;

// Classe para gerar o token de autenticação,como resposta do login e senha enviado pelo usuário.

@Service
public class GeradoraDeToken {

	@Value("${forum.token.secret}")
	private String secret; // obtido de application.properties .

	public String gerarToken(AutenticacaoUsuarioDB user) {
		try {
			var algorithm = Algorithm.HMAC256(secret);
			return JWT.create().withIssuer("API Forum-Hub")
					.withSubject(user.getLogin()) // usuário armazenado no token.
					.withClaim("id", user.getId()) // id do usuário(referente ao DB) será armazenado no token.
					.withExpiresAt(tempoDeDuracao()).sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("erro ao gerar token".toUpperCase(), exception);
		}
	}

	/*
	 * Obtém data e hora atual e soma duas horas,na hora obtida,e, converte para
	 * segundos de acordo com o fuso-horário Brasil.
	 */

	private Instant tempoDeDuracao() {

		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
