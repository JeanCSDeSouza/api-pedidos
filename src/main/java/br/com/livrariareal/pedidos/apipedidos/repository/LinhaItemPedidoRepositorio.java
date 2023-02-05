package br.com.livrariareal.pedidos.apipedidos.repository;

import br.com.livrariareal.pedidos.apipedidos.model.LinhaItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinhaItemPedidoRepositorio  extends JpaRepository<LinhaItemPedido, Long>{
}
