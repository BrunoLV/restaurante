package br.com.valhala.restaurante.comandas.infra.comanda.orm.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Item;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Produto;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.ItemORM;
import org.springframework.stereotype.Component;

@Component
public class ConversorItemORMParaModelo implements Conversor<ItemORM, Item> {

    @Override
    public Item converte(ItemORM source) {
        return Item
                .builder()
                .guid(source.getGuid())
                .produto(Produto.of(source.getGuidProduto(), source.getNome(), source.getFabricante()))
                .valorUnitario(source.getValorUnitario())
                .quantidade(source.getQuantidade())
                .build();
    }

}
