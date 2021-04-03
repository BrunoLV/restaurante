package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.impl;

import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoCriaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoCriaProduto;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.servico.ServicoProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExecutorComandoCriaProdutoImpl implements ExecutorComandoCriaProduto {

    private final ServicoProduto servico;

    @Transactional
    @Override
    public void executa(ComandoCriaProduto comando) {
        servico.cria(geraDadosOperacao(comando));
    }

    @Transactional
    @Override
    public Produto executaRetornandoProdutoCriado(ComandoCriaProduto comando) {
        Produto produto = servico.cria(geraDadosOperacao(comando));
        return produto;
    }

    private Produto geraDadosOperacao(ComandoCriaProduto comando) {
        return Produto.of(null, comando.getNome(), comando.getDescricao(), null, comando.getValor(), comando.getFabricante());
    }

}
