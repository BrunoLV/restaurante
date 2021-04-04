package br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada;

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
