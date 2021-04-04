package br.com.valhala.restaurante.comandas.infra.comanda.rest.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Item;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.saida.ItemJsonOutput;
import org.springframework.stereotype.Component;

@Component
class ConversorItemModeloParaItemOutputJson implements Conversor<Item, ItemJsonOutput> {

    @Override
    public ItemJsonOutput converte(Item source) {
        return ItemJsonOutput
                .builder()
                .guid(source.getGuid())
                .guidProduto(source.getProduto().getGuid())
                .nome(source.getProduto().getNome())
                .fabricante(source.getProduto().getFabricante())
                .valorUnitario(source.getValorUnitario())
                .quantidade(source.getQuantidade())
                .build();
    }

}
