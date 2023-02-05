package br.com.livrariareal.pedidos.apipedidos.model.dto;


import lombok.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.Entity;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class PedidoData extends RepresentationModel<PedidoData> {
    private Long id;
    @Getter private String pedidoNumero;
    @Getter private CollectionModel<LinhaItemPedidoData> linhaItemPedidoDataList;
}
