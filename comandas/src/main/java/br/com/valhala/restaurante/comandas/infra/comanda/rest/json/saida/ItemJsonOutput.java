package br.com.valhala.restaurante.comandas.infra.comanda.rest.json.saida;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ItemJsonOutput implements Serializable {

    private String guid;
    private String guidProduto;
    private String nome;
    private String fabricante;
    private BigDecimal valorUnitario;
    private Integer quantidade;

}
