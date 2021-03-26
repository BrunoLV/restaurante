package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando;

import br.com.valhala.restaurante.aplicacao.cqs.Comando;
import lombok.*;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ComandoEditaProduto implements Comando {

    private String guid;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String fabricante;

}
