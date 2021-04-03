package br.com.valhala.restaurante.comandas.infra.produto.saida.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ProdutoDTO implements Serializable {

    private String guid;
    private String nome;
    private String descricao;
    private LocalDate dataCadastro;
    private BigDecimal valor;
    private String fabricante;

}
