package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.consulta;

import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;

import java.util.Collection;

public interface ConsultaProdutoService {

    Produto buscaPorGuid(String guid);

    Collection<Produto> lista();

}
