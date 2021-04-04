package br.com.valhala.restaurante.comandas.infra.comanda.rest.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoEditaComanda;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ComandaJsonPutInput;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConversorComandaPutInputParaComandaEditaComanda implements Conversor<ComandaJsonPutInput, ComandoEditaComanda> {

    @Override
    public ComandoEditaComanda converte(ComandaJsonPutInput source) {

        List<ComandoEditaComanda.Item> itens = source.getItens()
                .stream()
                .map(i -> ComandoEditaComanda.Item
                        .builder()
                        .guid(i.getGuid())
                        .guidProduto(i.getGuidProduto())
                        .valorUnitario(i.getValorUnitario())
                        .quantidade(i.getQuantidade())
                        .build())
                .collect(Collectors.toList());

        ComandoEditaComanda comando = ComandoEditaComanda
                .builder()
                .guid(source.getGuid())
                .numeroMesa(source.getNumeroMesa())
                .itens(itens)
                .build();

        return comando;
    }

}
