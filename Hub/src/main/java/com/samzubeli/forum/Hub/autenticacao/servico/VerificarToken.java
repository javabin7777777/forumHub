package com.samzubeli.forum.Hub.autenticacao.servico;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

public class VerificarToken {
	
	@Value("${forum.token.secret}")
	private String secret; // obtido de application.properties .
	
	// verificar o token enviado pelo usuário e devolver o usuário.
		public String getUserAndVerifyToken(String tokenJWT) {
			
			try {
				var algoritmo = Algorithm.HMAC256(secret);
				return JWT.require(algoritmo)
						  .withIssuer("API Forum-Hub")
						  .build()
						  .verify(tokenJWT)
						  .getSubject();// obtém o 'user'.	
				
			} catch (JWTVerificationException exception) {
				throw new RuntimeException("token jwt inválido ou expirado.".toUpperCase());
			}
		}
}
