package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
abstract class ProdutoJsonInput implements Serializable {

    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String fabricante;

}
