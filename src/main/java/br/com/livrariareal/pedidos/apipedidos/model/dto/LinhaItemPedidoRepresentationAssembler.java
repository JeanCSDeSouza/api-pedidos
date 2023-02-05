package br.com.livrariareal.pedidos.apipedidos.model.dto;

import br.com.livrariareal.pedidos.apipedidos.model.LinhaItemPedido;
import br.com.livrariareal.pedidos.apipedidos.model.Pedido;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
@Component
public class LinhaItemPedidoRepresentationAssembler implements RepresentationModelAssembler<LinhaItemPedido, LinhaItemPedidoData> {
    @Override
    public LinhaItemPedidoData toModel(LinhaItemPedido entity) {
        return LinhaItemPedidoData.builder()
                .id( entity.getId() )
                .codigoSku( entity.getCodigoSku() )
                .quantidade( entity.getQuantidade() )
                .preco( entity.getPreco() )
                .build();
                //.add( linkTo( methodOn( LinhaItemPedidoControlador.class ).linhaItemPedido( entity.getId() ) ).withSelfRel() );

    }

    @Override
    public CollectionModel<LinhaItemPedidoData> toCollectionModel(Iterable<? extends LinhaItemPedido> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
    public LinhaItemPedido mapToPersistense( LinhaItemPedidoData linhaItemPedidoData, Pedido pedido){
        return LinhaItemPedido.builder()
                .id(linhaItemPedidoData.getId())
                .codigoSku(linhaItemPedidoData.getCodigoSku())
                .quantidade(linhaItemPedidoData.getQuantidade())
                .preco(linhaItemPedidoData.getPreco())
                .pedido( pedido )
                .build();
    }

    public List<LinhaItemPedido> mapToPersistenseCollection(CollectionModel<LinhaItemPedidoData> linhaItemPedidoDataList, Pedido pedido) {
        return linhaItemPedidoDataList.getContent()
                .stream()
                .map( linhaItemPedidoData ->  mapToPersistense(linhaItemPedidoData, pedido ) )
                .toList();
    }
}
