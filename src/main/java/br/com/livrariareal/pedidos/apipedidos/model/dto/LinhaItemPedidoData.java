package br.com.livrariareal.pedidos.apipedidos.model.dto;

import br.com.livrariareal.pedidos.apipedidos.model.Pedido;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class LinhaItemPedidoData extends RepresentationModel<LinhaItemPedidoData> {

    @Getter private Long id;
    @Getter private String codigoSku;
    @Getter private BigDecimal preco;
    @Getter private Integer quantidade;

    @Getter private PedidoData pedidoData;
}
