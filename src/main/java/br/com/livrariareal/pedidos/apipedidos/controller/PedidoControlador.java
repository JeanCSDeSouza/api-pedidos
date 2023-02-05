package br.com.livrariareal.pedidos.apipedidos.controller;

import br.com.livrariareal.pedidos.apipedidos.model.Pedido;
import br.com.livrariareal.pedidos.apipedidos.model.dto.PedidoData;
import br.com.livrariareal.pedidos.apipedidos.model.dto.PedidoRepresentationAssembler;
import br.com.livrariareal.pedidos.apipedidos.repository.PedidoRepositorio;
import br.com.livrariareal.pedidos.apipedidos.service.PedidoServico;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoControlador {

    private PedidoRepositorio pedidoRepositorio;
    private  PedidoRepresentationAssembler pedidoRepresentationAssembler;
    private PedidoServico pedidoServico;

    @Autowired
    public PedidoControlador(PedidoRepositorio pedidoRepositorio,
                             PedidoRepresentationAssembler pedidoRepresentationAssembler, PedidoServico pedidoServico) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.pedidoRepresentationAssembler = pedidoRepresentationAssembler;
        this.pedidoServico = pedidoServico;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<PedidoData> buscaPedido(Long id) {
        List<Pedido> pedidos = pedidoRepositorio.findAll();
        return pedidoRepresentationAssembler.toCollectionModel(pedidos);
    }

    @PostMapping
    public ResponseEntity<CompletableFuture<PedidoData>> geraPedido(@RequestBody PedidoData pedidoData){
        return ResponseEntity.status(HttpStatus.CREATED).body( pedidoServico.salvarPedido(pedidoData) );
    }

}
