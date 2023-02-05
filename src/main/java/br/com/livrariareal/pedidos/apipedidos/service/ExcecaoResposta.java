package br.com.livrariareal.pedidos.apipedidos.service;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
public class ExcecaoResposta {
	@Getter private final LocalDateTime timestamp;
	@Getter private final String message;
	@Getter private final String details;
}