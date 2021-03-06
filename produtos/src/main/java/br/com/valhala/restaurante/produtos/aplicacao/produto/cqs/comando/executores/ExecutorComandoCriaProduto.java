package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.executores;

import br.com.valhala.restaurante.aplicacao.cqs.ExecutorComando;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.comando.ComandoCriaProduto;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;

public interface ExecutorComandoCriaProduto extends ExecutorComando<ComandoCriaProduto> {

    Produto executaRetornandoProdutoCriado(ComandoCriaProduto comando);

}
