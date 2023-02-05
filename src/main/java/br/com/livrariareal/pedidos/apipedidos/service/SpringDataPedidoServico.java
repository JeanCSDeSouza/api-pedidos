package br.com.livrariareal.pedidos.apipedidos.service;

import br.com.livrariareal.pedidos.apipedidos.model.LinhaItemPedido;
import br.com.livrariareal.pedidos.apipedidos.model.Pedido;
import br.com.livrariareal.pedidos.apipedidos.model.dto.LinhaItemPedidoRepresentationAssembler;
import br.com.livrariareal.pedidos.apipedidos.model.dto.PedidoData;
import br.com.livrariareal.pedidos.apipedidos.model.dto.PedidoRepresentationAssembler;
import br.com.livrariareal.pedidos.apipedidos.repository.PedidoRepositorio;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
public class SpringDataPedidoServico implements PedidoServico {

    private PedidoRepositorio pedidoRepositorio;
    private PedidoRepresentationAssembler pedidoRepresentationAssembler;
    private LinhaItemPedidoRepresentationAssembler itemPedidoRepresentationAssembler;
    private WebClient.Builder webClientBuilder;

    private static final String SALVAR_PEDIDO_CB_NOME = "pedidos";

    @Autowired
    public SpringDataPedidoServico(PedidoRepositorio pedidoRepositorio, PedidoRepresentationAssembler pedidoRepresentationAssembler,
                         LinhaItemPedidoRepresentationAssembler itemPedidoRepresentationAssembler, WebClient.Builder webClientBuilder) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.pedidoRepresentationAssembler = pedidoRepresentationAssembler;
        this.itemPedidoRepresentationAssembler = itemPedidoRepresentationAssembler;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    @CircuitBreaker(name = SALVAR_PEDIDO_CB_NOME, fallbackMethod = "pedidosFallBack")
    @TimeLimiter( name = SALVAR_PEDIDO_CB_NOME )
    //@Retry( name = SALVAR_PEDIDO_CB_NOME )
    public CompletableFuture<PedidoData> salvarPedido(PedidoData pedidoData){
        //Transforma pedidoData em classe anotada para persistencia pedido
        Pedido pedido = pedidoRepresentationAssembler.mapToPersistence(pedidoData);
        pedido.setLinhaItemPedidoList( itemPedidoRepresentationAssembler.mapToPersistenseCollection( pedidoData.getLinhaItemPedidoDataList(), pedido ) );
        //Cria lista de sku's para chamada a inventario
        List<String> codigosSku = pedido.getLinhaItemPedidoList().stream()
                .map(LinhaItemPedido::getCodigoSku)
                .toList();
        //Chama api de inventario que retorna um long que representa a quantidade de produtos em estoque
        Long resultado = webClientBuilder.build().get()
                .uri( "http://servico-inventario/api/inventario",
                        uriBuilder -> uriBuilder.queryParam( "skuCode", codigosSku ).build() )
                .retrieve()
                .bodyToMono( Long.class )
                .block();
        //Salva o pedido caso o Long retornado da chamada à inventário seja igual ao tamanho da lista de produtos
        if( resultado == codigosSku.size() ){
            pedido = pedidoRepositorio.save(pedido);
            //todo chamada assincrona a inventário pra recurerar qual item do pedido está fora de estoque
        }else{
            throw new IllegalArgumentException("Foram pedidos " + codigosSku.size() + " produtos e " + ( codigosSku.size() - resultado ) + " deles não estão em estoque");
        }
        final PedidoData pedidoDataCF = pedidoRepresentationAssembler.toModel(pedido);
        return CompletableFuture.supplyAsync( () -> pedidoDataCF) ;
    }
    @Override
    public CompletableFuture<PedidoData> pedidosFallBack(PedidoData pedidoData, RuntimeException runtimeException){
        pedidoData.setPedidoNumero("Erro, não foi possível registrar o pedido, tente novamente.");
        final PedidoData pedidoDataCF = pedidoData;
        return  CompletableFuture.supplyAsync(( ) -> pedidoDataCF);
    }
}
