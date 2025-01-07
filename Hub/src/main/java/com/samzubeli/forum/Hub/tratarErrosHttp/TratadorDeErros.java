package com.samzubeli.forum.Hub.tratarErrosHttp;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TratadorDeErros {
	// Tratar erros para quando o recurso não for encontrado,como por exemplo,o id não encontrado.
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> tratarErro404() {
		return ResponseEntity.notFound().build();
	}

	// Tratar erros para campos de uma requisição,que não passaram na validação,e filtrar estes erros com DadosRespostaErros.
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> tratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
		List<DadosRespostaErros> listaDeErros = erros.stream().map(DadosRespostaErros::new).toList();
		return ResponseEntity.badRequest().body(listaDeErros); 
		// Somente o campo que não passou na validação e a mensagemm referente,constarão no body de resposta. 
	}
	
	

	private record DadosRespostaErros(String campo, String mensagen) {

		public DadosRespostaErros(FieldError error) {
			this(error.getField(), error.getDefaultMessage());
		}

	}
}
/*
 * Quando não encontrado o recurso,então spring-boot responde por default,com
 * erro 500 http(erro interno do servidor).
 * 
 */