package br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProdutoJsonPutInput extends ProdutoJsonInput {

    private String guid;

}
