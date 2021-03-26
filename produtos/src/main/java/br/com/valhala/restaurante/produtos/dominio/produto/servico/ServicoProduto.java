package br.com.valhala.restaurante.produtos.dominio.produto.servico;

import br.com.valhala.restaurante.dominio.servico.Servico;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;

public interface ServicoProduto extends Servico {

    Produto cria(Produto produto);

    void edita(String guid, Produto produto);

    void exclui(String guid);

}
