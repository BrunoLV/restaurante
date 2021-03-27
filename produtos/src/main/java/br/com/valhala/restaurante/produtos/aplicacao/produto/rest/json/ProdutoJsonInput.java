package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
abstract class ProdutoJsonInput implements Serializable {

    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String fabricante;

}
