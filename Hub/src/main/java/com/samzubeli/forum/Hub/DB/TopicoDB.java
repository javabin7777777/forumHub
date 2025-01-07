package com.samzubeli.forum.Hub.DB;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.samzubeli.forum.Hub.model.DadosAtualizarTopicos;
import com.samzubeli.forum.Hub.model.Topico;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name="topicos")
@EqualsAndHashCode(of ="id")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TopicoDB {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;	
	private String dataCriacao;
	@Setter
	private String dataAtual;
	@Setter
	private Boolean estado;
	@Column(unique = true)
	private String autor;
	private String curso;
	
	DateFormatSymbols Simbolo = new DateFormatSymbols(Locale.ENGLISH);
	SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd 'at' HH:mm", Simbolo);
	
	public TopicoDB(Topico topico) {
		this.titulo = topico.titulo();
		this.mensagem = topico.mensagem();
		this.autor = topico.autor();
		this.curso = topico.curso();
		Date d = new Date();
		dataCriacao = dataAtual= f.format(d);
		estado = true;
	}

	public void atualizarTopico(@Valid DadosAtualizarTopicos dados) {
		if(dados.mensagem() != null) this.mensagem = dados.mensagem();
		if(dados.estado() != null) setEstado(dados.estado());
		Date d = new Date();
		setDataAtual(f.format(d));
	}
	
}
