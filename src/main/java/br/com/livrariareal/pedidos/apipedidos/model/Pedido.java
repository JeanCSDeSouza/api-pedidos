package br.com.livrariareal.pedidos.apipedidos.model;

import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="pedido_tabela")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    @Getter private Long id;
    @Column(name="pedido_numero", unique = true, nullable = false)
    @Getter private String pedidoNumero;
    //pedido não faz senido sem as linhas, FetchType.EAGER
    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(name = "linha_item_pedido")
    @Getter @Setter private List<LinhaItemPedido> linhaItemPedidoList;
}
