package br.com.valhala.restaurante.comandas.infra.comanda.orm.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Item;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.ItemORM;

public class ConversorItemModeloParaORM implements Conversor<Item, ItemORM> {

    @Override
    public ItemORM converte(Item source) {
        return ItemORM
                .builder()
                .guid(source.getGuid())
                .nome(source.getProduto().getNome())
                .fabricante(source.getProduto().getFabricante())
                .valorUnitario(source.getValorUnitario())
                .quantidade(source.getQuantidade())
                .guidProduto(source.getProduto().getGuid())
                .build();
    }

}
