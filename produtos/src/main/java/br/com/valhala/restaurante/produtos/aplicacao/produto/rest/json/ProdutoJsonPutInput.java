package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProdutoJsonPutInput extends ProdutoJsonInput {

    private String guid;

}
