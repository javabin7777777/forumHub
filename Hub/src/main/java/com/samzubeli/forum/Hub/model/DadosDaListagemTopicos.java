package com.samzubeli.forum.Hub.model;

import com.samzubeli.forum.Hub.DB.TopicoDB;

public record DadosDaListagemTopicos(
		Long id,
		String titulo,
		String mensagem,		
		String dataCriacao,	
		String dataAtual,
		Boolean estado,		
		String autor,
		String curso) {
	
	public DadosDaListagemTopicos(TopicoDB topico) {
		this(topico.getId(), topico.getTitulo(), topico.getMensagem(),topico.getDataCriacao(),
				topico.getDataAtual(), topico.getEstado(), topico.getAutor(),topico.getCurso());
		
	}
}
