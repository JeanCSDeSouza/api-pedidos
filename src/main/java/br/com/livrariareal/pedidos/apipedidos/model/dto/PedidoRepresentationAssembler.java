package br.com.livrariareal.pedidos.apipedidos.model.dto;

import br.com.livrariareal.pedidos.apipedidos.controller.PedidoControlador;
import br.com.livrariareal.pedidos.apipedidos.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class PedidoRepresentationAssembler implements RepresentationModelAssembler<Pedido,PedidoData> {
    @Autowired
    private LinhaItemPedidoRepresentationAssembler linhaItemPedidoRepresentationAssembler;

    @Override
    public PedidoData toModel(Pedido entity) {
        return PedidoData.builder()
                .id( entity.getId() )
                .pedidoNumero(entity.getPedidoNumero() )
                .linhaItemPedidoDataList( linhaItemPedidoRepresentationAssembler.toCollectionModel(entity.getLinhaItemPedidoList() ) )
                .build()
                .add( linkTo( methodOn( PedidoControlador.class ).buscaPedido(entity.getId()) ).withSelfRel() );
    }

    @Override
    public CollectionModel<PedidoData> toCollectionModel(Iterable<? extends Pedido> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public Pedido mapToPersistence(PedidoData pedidoData){
        Pedido pedido = Pedido.builder()
                            .id( pedidoData.getId() )
                            .pedidoNumero(pedidoData.getPedidoNumero() )
                            //.linhaItemPedidoList( linhaItemPedidoRepresentationAssembler.mapToPersistenseCollection( pedidoData.getLinhaItemPedidoDataList() ) )
                            .build();
        //pedido.setLinhaItemPedidoList( linhaItemPedidoRepresentationAssembler.mapToPersistenseCollection( pedidoData.getLinhaItemPedidoDataList(), pedido ) );
        return pedido;
    }
}
