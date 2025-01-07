package com.samzubeli.forum.Hub.model;

import jakarta.validation.constraints.NotBlank;

public record Topico(
		@NotBlank
		String titulo,
		@NotBlank
		String mensagem,
		@NotBlank
		String autor,
		@NotBlank
		String curso) {}
