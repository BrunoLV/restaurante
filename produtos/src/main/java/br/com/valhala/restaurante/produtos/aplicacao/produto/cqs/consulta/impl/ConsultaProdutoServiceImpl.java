package br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.consulta.impl;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.produtos.aplicacao.produto.cqs.consulta.ConsultaProdutoService;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.repositorio.RepositorioProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ConsultaProdutoServiceImpl implements ConsultaProdutoService {

    private final RepositorioProduto repositorio;

    @Override
    public Produto buscaPorGuid(String guid) {
        return repositorio.buscaPorGuid(guid).orElseThrow(() -> new ModeloNaoEncontradoException());
    }

    @Override
    public Collection<Produto> lista() {
        return repositorio.lista();
    }

}
