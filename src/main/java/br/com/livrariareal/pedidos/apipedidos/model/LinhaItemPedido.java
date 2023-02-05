package br.com.livrariareal.pedidos.apipedidos.model;

import lombok.*;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="linha_item_pedido_tabela")
public class LinhaItemPedido {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "linha_item_pedido_id")
    @Getter private Long id;
    @Column(name = "codigo_sku", nullable = false)
    @Getter private String codigoSku;
    @Column(name = "preco")
    @Getter private BigDecimal preco;
    @Column(name = "quantidade")
    @Getter private Integer quantidade;
    @ManyToOne
    @JoinColumn(name="pedido_id", nullable = false)
    @Getter @Setter private Pedido pedido;
}
