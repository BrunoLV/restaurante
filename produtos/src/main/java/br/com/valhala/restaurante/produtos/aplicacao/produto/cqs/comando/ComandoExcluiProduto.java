package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando;

import br.com.valhala.restaurante.aplicacao.cqs.Comando;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ComandoExcluiProduto implements Comando {

    private String guid;

}
