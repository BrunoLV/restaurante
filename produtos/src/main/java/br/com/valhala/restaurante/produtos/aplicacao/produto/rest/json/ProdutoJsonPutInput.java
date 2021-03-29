package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProdutoJsonPutInput extends ProdutoJsonInput {

    private String guid;

}
