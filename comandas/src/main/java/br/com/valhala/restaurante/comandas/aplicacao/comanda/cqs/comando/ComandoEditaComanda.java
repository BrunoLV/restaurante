package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando;

import br.com.valhala.restaurante.aplicacao.cqs.Comando;
import lombok.*;

import java.math.BigDecimal;
import java.util.Collection;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ComandoEditaComanda implements Comando {

    private String guid;
    private Integer numeroMesa;
    private Collection<ComandoEditaComanda.Item> itens;

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    @Builder
    public static class Item {
        private String guid;
        private BigDecimal valorUnitario;
        private Integer quantidade;
        private String guidProduto;
    }

}
