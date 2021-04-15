package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.impl;

import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoEditaProduto;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores.ExecutorComandoEditaProduto;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.servico.ServicoProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExecutorComandoEditaProdutoImpl implements ExecutorComandoEditaProduto {

    private final ServicoProduto servico;

    @Transactional
    @Override
    public void executa(ComandoEditaProduto comandoEditaProduto) {
        Produto dadosOperacao = geraDadosOperacao(comandoEditaProduto);
        servico.edita(comandoEditaProduto.getGuid(), dadosOperacao);
    }

    private Produto geraDadosOperacao(ComandoEditaProduto comando) {
        return Produto
                .builder()
                .guid(comando.getGuid())
                .nome(comando.getNome())
                .descricao(comando.getDescricao())
                .valor(comando.getValor())
                .fabricante(comando.getFabricante())
                .build();
    }
}
