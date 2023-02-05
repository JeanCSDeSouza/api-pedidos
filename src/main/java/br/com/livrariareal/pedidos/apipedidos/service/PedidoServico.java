package br.com.livrariareal.pedidos.apipedidos.service;

import br.com.livrariareal.pedidos.apipedidos.model.dto.PedidoData;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import java.util.concurrent.CompletableFuture;

public interface PedidoServico {

    CompletableFuture<PedidoData> salvarPedido(PedidoData pedidoData);
    CompletableFuture<PedidoData> pedidosFallBack(PedidoData pedidoData, RuntimeException runtimeException);
}
