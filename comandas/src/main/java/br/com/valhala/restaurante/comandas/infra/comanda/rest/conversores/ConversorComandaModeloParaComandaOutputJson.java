package br.com.valhala.restaurante.comandas.infra.comanda.rest.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.saida.ComandaJsonOutput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.saida.ItemJsonOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConversorComandaModeloParaComandaOutputJson implements Conversor<Comanda, ComandaJsonOutput> {

    private final ConversorItemModeloParaItemOutputJson conversorItem;

    @Override
    public ComandaJsonOutput converte(Comanda source) {

        List<ItemJsonOutput> itens = source.getItens()
                .stream()
                .map(conversorItem::converte)
                .collect(Collectors.toList());

        return ComandaJsonOutput
                .builder()
                .guid(source.getGuid())
                .data(source.getData())
                .numeroMesa(source.getNumeroMesa())
                .itens(itens)
                .build();

    }

}
