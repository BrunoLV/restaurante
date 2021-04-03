package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando;

import br.com.valhala.restaurante.aplicacao.cqs.Comando;
import lombok.*;

import java.math.BigDecimal;
import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ComandoCriaComanda implements Comando {

    private Integer numeroMesa;
    private Collection<Item> itens;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @Builder
    public static class Item {
        private BigDecimal valorUnitario;
        private Integer quantidade;
        private String guidProduto;
    }

}
