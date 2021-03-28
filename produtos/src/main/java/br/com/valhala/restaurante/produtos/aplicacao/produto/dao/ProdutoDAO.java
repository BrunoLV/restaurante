package br.com.valhala.restaurante.produtos.aplicacao.produto.dao;

public interface ProdutoDAO {

    boolean existeProdutoComNomeIgual(String nome);

    boolean existeProdutoComNomeIgualComGuidDiferente(String nome, String guid);

}
