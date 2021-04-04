package br.com.valhala.restaurante.comandas.infra.comanda.rest.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoCriaComanda;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ComandaJsonPostInput;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConversorComandaPostInputParaComandaCriaComanda implements Conversor<ComandaJsonPostInput, ComandoCriaComanda> {

    @Override
    public ComandoCriaComanda converte(ComandaJsonPostInput source) {

        List<ComandoCriaComanda.Item> itens = source.getItens()
                .stream()
                .map(i -> ComandoCriaComanda.Item
                        .builder()
                        .guidProduto(i.getGuidProduto())
                        .valorUnitario(i.getValorUnitario())
                        .quantidade(i.getQuantidade())
                        .build())
                .collect(Collectors.toList());

        ComandoCriaComanda comando = ComandoCriaComanda
                .builder()
                .numeroMesa(source.getNumeroMesa())
                .itens(itens)
                .build();

        return comando;
    }

}
