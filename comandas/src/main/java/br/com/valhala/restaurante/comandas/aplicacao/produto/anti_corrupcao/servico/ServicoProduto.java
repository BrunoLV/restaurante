package br.com.valhala.restaurante.comandas.aplicacao.produto.anti_corrupcao.servico;

import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Produto;

public interface ServicoProduto {

    Produto buscaPorGuid(String guid);

}
