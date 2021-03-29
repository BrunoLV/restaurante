package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProdutoJsonPostInput extends ProdutoJsonInput {
}
