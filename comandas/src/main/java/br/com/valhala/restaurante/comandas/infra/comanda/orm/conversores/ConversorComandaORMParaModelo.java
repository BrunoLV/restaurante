package br.com.valhala.restaurante.comandas.infra.comanda.orm.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Item;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.ComandaORM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ConversorComandaORMParaModelo implements Conversor<ComandaORM, Comanda> {

    private final ConversorItemORMParaModelo conversorItemORMParaModelo;

    @Override
    public Comanda converte(ComandaORM source) {
        List<Item> itens = source.getItens().stream().map(conversorItemORMParaModelo::converte).collect(Collectors.toList());
        return Comanda
                .builder()
                .guid(source.getGuid())
                .data(source.getData())
                .numeroMesa(source.getNumeroMesa())
                .itens(itens)
                .build();
    }

}
