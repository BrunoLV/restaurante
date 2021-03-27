package br.com.valhala.restaurante.produtos.dominio.produto.servico.impl;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloInvalidoException;
import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.repositorio.RepositorioProduto;
import br.com.valhala.restaurante.produtos.dominio.produto.servico.ServicoProduto;
import br.com.valhala.restaurante.produtos.dominio.produto.validacao.ValidadorProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicoProdutoImpl implements ServicoProduto {

    private final RepositorioProduto repositorio;
    private final ValidadorProduto validador;

    @Override
    public Produto cria(Produto produto) {
        Produto produtoInclusao = Produto.novo(produto.getNome(), produto.getDescricao(), produto.getValor(), produto.getFabricante());
        validaProduto(produtoInclusao);
        repositorio.cria(produtoInclusao);
        return repositorio.buscaPorGuid(produtoInclusao.getGuid());
    }

    private void validaProduto(Produto produto) {
        ResultadoValidacao resultadoValidacao = validador.valida(produto);
        if (resultadoValidacao.temErros()) {
            throw new ModeloInvalidoException("Produto inválido.", resultadoValidacao.getErros());
        }
    }

    @Override
    public void edita(String guid, Produto produto) {
        Produto produtoPersistente = repositorio.buscaPorGuid(guid);
        Produto produtoEditado = produtoPersistente.edita(produto);
        validaProduto(produtoEditado);
        repositorio.edita(produtoEditado);
    }

    @Override
    public void exclui(String guid) {
        Produto produtoPersistente = repositorio.buscaPorGuid(guid);
        repositorio.exclui(produtoPersistente);
    }

}
