package br.com.valhala.restaurante.comandas.infra.comanda.orm.conversores;

import br.com.valhala.restaurante.aplicacao.conversao.Conversor;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.infra.comanda.orm.ComandaORM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversorComandaModeloParaORM implements Conversor<Comanda, ComandaORM> {

    private final ConversorItemModeloParaORM conversorItem;

    @Override
    public ComandaORM converte(Comanda source) {
        ComandaORM orm = ComandaORM
                .builder()
                .guid(source.getGuid())
                .data(source.getData())
                .numeroMesa(source.getNumeroMesa())
                .build();

        if (source.getItens() != null) {
            source.getItens().stream().map(conversorItem::converte).forEach(orm::adicionaItem);
        }

        return orm;
    }

}
