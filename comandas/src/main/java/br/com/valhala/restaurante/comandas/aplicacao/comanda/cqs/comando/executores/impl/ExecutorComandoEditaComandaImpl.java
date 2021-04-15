package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.impl;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloInvalidoException;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.ComandoEditaComanda;
import br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando.executores.ExecutorComandoEditaComanda;
import br.com.valhala.restaurante.comandas.aplicacao.produto.anti_corrupcao.servico.ServicoProduto;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Item;
import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Produto;
import br.com.valhala.restaurante.comandas.dominio.comanda.servico.ServicoComanda;
import br.com.valhala.restaurante.dominio.validacao.resultado.Erro;
import br.com.valhala.restaurante.infra.rest.exceptions.RecursoInexistenteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
        Produto produto;

        try {
            produto = servicoProdutoOutbound.buscaPorGuid(i.getGuidProduto());
        } catch (RecursoInexistenteException e) {
            Erro erro = new Erro("guidProduto", String.format("Produto identificado com guid: %s não localizado.", i.getGuidProduto()));
            throw new ModeloInvalidoException("Item inválido", Collections.singletonList(erro));
        }

        return Item
                .builder()
                .produto(produto)
                .valorUnitario(i.getValorUnitario())
                .quantidade(i.getQuantidade())
                .build();
    }
}
