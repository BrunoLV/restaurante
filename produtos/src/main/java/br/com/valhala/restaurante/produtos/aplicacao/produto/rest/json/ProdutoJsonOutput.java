package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoJsonOutput implements Serializable {

    private String guid;
    private String nome;
    private String descricao;
    private LocalDate dataCadastro;
    private BigDecimal valor;
    private String fabricante;

}
