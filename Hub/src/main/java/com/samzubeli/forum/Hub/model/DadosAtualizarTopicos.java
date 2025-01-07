package com.samzubeli.forum.Hub.model;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizarTopicos(
		@NotNull Long id, String mensagem, Boolean estado) {

}
