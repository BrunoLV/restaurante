package br.com.valhala.restaurante.produtos.infra.produto.orm;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "tb_produto")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProdutoORM implements Serializable {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "guid")
    private String guid;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "valor", precision = 17, scale = 2)
    private BigDecimal valor;

    @Column(name = "fabricante")
    private String fabricante;

    @Version
    @Column(name = "versao")
    private Long versao;

}
