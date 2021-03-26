package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoJsonPutInput extends ProdutoJsonInput {

    private String guid;

}
