package br.com.livrariareal.pedidos.apipedidos.repository;

import br.com.livrariareal.pedidos.apipedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepositorio extends JpaRepository<Pedido, Long> {
}
