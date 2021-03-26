package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.impl;

import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoExcluiProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoExcluiProduto;
import br.com.valhala.restaurante.produtos.dominio.produto.servico.ServicoProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExecutorComandoExcluiProdutoImpl implements ExecutorComandoExcluiProduto {

    private final ServicoProduto servico;

    @Transactional
    @Override
    public void executa(ComandoExcluiProduto comandoExcluiProduto) {
        servico.exclui(comandoExcluiProduto.getGuid());
    }
}
