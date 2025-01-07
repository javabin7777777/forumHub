package com.samzubeli.forum.Hub.autenticacao.model;

import jakarta.validation.constraints.NotBlank;

public record DadosDeLogin( @NotBlank String login, @NotBlank String password) {}
