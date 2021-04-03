package br.com.valhala.restaurante.comandas.infra.comanda.orm;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tb_item")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemORM implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "guid", length = 100, unique = true, nullable = false)
    private String guid;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "fabricante", length = 100, nullable = false)
    private String fabricante;

    @Column(name = "valor_unitario", precision = 17, scale = 2, nullable = false)
    private BigDecimal valorUnitario;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "guid_produto", length = 100, nullable = false)
    private String guidProduto;

    @ManyToOne
    @JoinColumn(name = "id_comanda", nullable = false)
    private ComandaORM comanda;

}
