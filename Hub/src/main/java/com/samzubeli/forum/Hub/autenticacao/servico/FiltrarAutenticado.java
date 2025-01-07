package com.samzubeli.forum.Hub.autenticacao.servico;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.samzubeli.forum.Hub.autenticacao.DB.IUsuario;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// esta classe,filtra as requisiçoes do usuário,verificando o token enviado,e,assim permite ou nega
// o processamento das requições enviadas pelo mesmo para API.

@Component
public class FiltrarAutenticado extends OncePerRequestFilter {

		// request: são os dados da requisição. 
		// response: onde é inserido os dados de resposta. 
		// filterChain: envia os dados da requição e resposta para o próximo na cadeia de processamento. 
		// OncePerRequestFilter: classe do Spring Boot,que possui o método doFilterInternal.
	
	@Autowired
	private VerificarToken verificarToken;
	
	@Autowired
	IUsuario repository;
	
	@Override    
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
		
		String  tokenJWT = receberToken(request);
		
		if(tokenJWT != null) {
			String  user =  verificarToken.getUserAndVerifyToken(tokenJWT); // obter o 'subject(user) do token'.
			 var usuario = repository.findByLogin(user);// obter do DB os dados do usuário e devolve no formato objeto usuario.
			 var authentication = new UsernamePasswordAuthenticationToken(user,null,usuario. getAuthorities());
			 SecurityContextHolder.getContext().setAuthentication(authentication);// aqui o spring boot permite o Susuário.			
		}		
		
		filterChain.doFilter(request, response); // seguir para o próximo,na cadeia de processamento.		
	}
	
	// usuario. getAuthorities(): perfil do usuário,da classe AutenticacaoUsuarioDB.
	
	
	// Obter o token enviado pelo usuário,para API.
	
	private String receberToken(HttpServletRequest request) {
		
		String tokenDaRequisicao = request.getHeader("Authorization");		
		
		if(tokenDaRequisicao != null) {			
			return tokenDaRequisicao.substring(6); // A palavra Bearer vem junto ao token,logo no início.			
		}		
		return null;		
	}
	
	// Authorization é um dos campos de informação de overhead que faz parte do pacote enviado pela requisição.
	// é onde está o token tipo Bearer.
	
	// para requisição login,o campo Authorization(Auth) é enviado vazio para API.

}

/*throw new RuntimeException("Nenhum token foi enviado pelo usuário,"
+ "favor enviar seu token de autenticação.".toUpperCase());*/