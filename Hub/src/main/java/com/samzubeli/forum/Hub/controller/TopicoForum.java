package com.samzubeli.forum.Hub.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import com.samzubeli.forum.Hub.DB.ITopicoForum;
import com.samzubeli.forum.Hub.DB.TopicoDB;
import com.samzubeli.forum.Hub.model.DadosAtualizarTopicos;
import com.samzubeli.forum.Hub.model.DadosDaListagemTopicos;
import com.samzubeli.forum.Hub.model.Topico;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;

@Controller
@RequestMapping("/topicos")
@Getter
@SecurityRequirement(name = "bearer-key")  // documentação springdoc.
public class TopicoForum {
	
	@Autowired
	private ITopicoForum repository;
	
	private List<DadosDaListagemTopicos> listaForum = new ArrayList<>();

	@PostMapping
	@Transactional         // dadosRecebidos deverão estar no formato Json.
	public ResponseEntity<DadosDaListagemTopicos> InserirDados(@RequestBody @Valid Topico dadosRecebidos, 
			UriComponentsBuilder uriBuilder) {
		
		var topico = new TopicoDB(dadosRecebidos);	// recursos criados de acordo com os dados recebidos.
		repository.save(topico); // recursos salvos.
		// url para acessar os recursos recém salvos no DB.
		var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); 
				
		return ResponseEntity.created(uri).body(new DadosDaListagemTopicos(topico)); 
		// Responde com código 201 http(informar body e header na resposta).
		// body é onde está os dados do recurso que foi criado e salvo no DB.
		// header é onde está a url(location),que é a localização deste recurso recém criado e salvo no DB.
	}

	// listar o que está salvo no DB.
	// traz tudo o que está no DB ou um registro específico.
	@GetMapping("/listagem")
	public ResponseEntity<List<DadosDaListagemTopicos>> listar(@RequestParam(value = "/id",defaultValue = "0") Long id) {
		if(id != 0) {
			TopicoDB topicoPorId = repository.getReferenceById(id);
			listaForum.clear();
			listaForum.add(new DadosDaListagemTopicos(topicoPorId));			
			return ResponseEntity.ok(listaForum);
			// Responde com código 200 http e mostra o body,que traz detalhes de um tópico com id fornecido.
		}
		listaForum = repository.findAll().stream().map(e -> new DadosDaListagemTopicos(e)).toList();
		return ResponseEntity.ok(listaForum); 
		// Responde com código 200 htpp e mostra o body,que traz a listagem de tudo que foi encontrado no DB.
		
	}

	// atualizar um registro do DB ou recuperar um registro do DB,caso tenha sido apagado.
	@PutMapping(value = "/atualizar")
	@Transactional
	public ResponseEntity<DadosDaListagemTopicos>  atualizar(@RequestBody @Valid DadosAtualizarTopicos dados,
			@RequestParam(value = "recuperar/id", defaultValue = "0") Long id) {
		
		TopicoDB topico;
		
		if (id == 0) {
			topico = repository.getReferenceById(dados.id());
			topico.atualizarTopico(dados);
		} else {
			topico = repository.getReferenceById(id);
			topico.setEstado(true);
		}
		
		return ResponseEntity.ok(new DadosDaListagemTopicos(topico)); 
		// Responde com código 200 http e o body traz o que foi atualizado ou recuperado.
	}

	// apagar um registro do DB,o mesmo será sinalizado com 'false'.
	@DeleteMapping("/apagar/{id}")
	@Transactional
	public ResponseEntity apagar(@PathVariable Long id) {
		TopicoDB topico = repository.getReferenceById(id);
		topico.setEstado(false);

		return ResponseEntity.noContent().build(); 
		// Responde com código 204 htpp.
		// sem contéudo no body da resposta.
	}
}
