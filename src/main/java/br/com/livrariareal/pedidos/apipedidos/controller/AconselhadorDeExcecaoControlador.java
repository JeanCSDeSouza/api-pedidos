package br.com.livrariareal.pedidos.apipedidos.controller;

import br.com.livrariareal.pedidos.apipedidos.service.ExcecaoResposta;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

public class AconselhadorDeExcecaoControlador {
   //excecãoIllegalArgumentException
    @ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
    @ExceptionHandler( IllegalArgumentException.class )
    public final ExcecaoResposta handleIllegalArgumentException(IllegalArgumentException illegalArgumentException, WebRequest webRequenst ){
        return ExcecaoResposta.builder()
                .message("Alguns dos produtos não estão em estoque, pedimos descupas pelo inconveniente")
                .timestamp(LocalDateTime.now())
                .details("Place holder: deve listar os produtos em falta.")
                .build();
    }

}
