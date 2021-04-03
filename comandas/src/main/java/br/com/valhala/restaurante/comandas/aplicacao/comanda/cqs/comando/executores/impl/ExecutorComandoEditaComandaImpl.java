package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.impl;

import br.com.valhala.restaurante.comandas.aplicacao.produto.anti_corrupcao.servico.ServicoProduto;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoEditaComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.ExecutorComandoEditaComanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Item;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Produto;
import br.com.valhala.restaurante.comandas.dominio.comanda.servico.ServicoComanda;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExecutorComandoEditaComandaImpl implements ExecutorComandoEditaComanda {

    private final ServicoComanda servico;
    private final ServicoProduto servicoProdutoOutbound;

    @Transactional
    @Override
    public void executa(ComandoEditaComanda comando) {
        Comanda comanda = geraDadosOperacao(comando);
        servico.edita(comando.getGuid(), comanda);
    }

    private Comanda geraDadosOperacao(ComandoEditaComanda comando) {

        List<Item> itens = comando.getItens()
                .stream()
                .map(this::geraDadosItem)
                .collect(Collectors.toList());

        return Comanda.builder().numeroMesa(comando.getNumeroMesa()).itens(itens).build();
    }

    private Item geraDadosItem(ComandoEditaComanda.Item i) {
        Produto produto = servicoProdutoOutbound.buscaPorGuid(i.getGuidProduto());
        return Item
                .builder()
                .produto(produto)
                .valorUnitario(i.getValorUnitario())
                .quantidade(i.getQuantidade())
                .build();
    }
}
