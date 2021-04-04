package br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
abstract class ItemJsonInput {

    private String guidProduto;
    private BigDecimal valorUnitario;
    private Integer quantidade;

}
