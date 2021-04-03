package br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.repositorio.RepositorioProduto;
import br.com.valhala.restaurante.produtos.infra.produto.orm.ProdutoORM;
import br.com.valhala.restaurante.produtos.infra.produto.orm.conversores.ConversorProdutoModeloParaORM;
import br.com.valhala.restaurante.produtos.infra.produto.orm.conversores.ConversorProdutoORMParaModelo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RepositorioProdutoJPA implements RepositorioProduto {

    private final RepositorioProdutoSpringData repositorio;
    private final ConversorProdutoModeloParaORM conversorProdutoModeloParaORM;
    private final ConversorProdutoORMParaModelo conversorProdutoORMParaModelo;

    @Override
    public void cria(Produto produto) {
        ProdutoORM orm = conversorProdutoModeloParaORM.converte(produto);
        repositorio.save(orm);
    }

    @Override
    public void edita(Produto produto) {
        ProdutoORM orm = repositorio.findByGuid(produto.getGuid());
        if (orm == null) {
            throw new ModeloNaoEncontradoException(String.format("Produto com guid [%s] não encontrado.", produto.getGuid()));
        }
        orm.setNome(produto.getNome());
        orm.setDescricao(produto.getDescricao());
        orm.setFabricante(produto.getFabricante());
        orm.setValor(produto.getValor());
        repositorio.save(orm);
    }

    @Override
    public void exclui(Produto produto) {
        ProdutoORM orm = repositorio.findByGuid(produto.getGuid());
        if (orm == null) {
            throw new ModeloNaoEncontradoException(String.format("Produto com guid [%s] não encontrado.", produto.getGuid()));
        }
        repositorio.delete(orm);
    }

    @Override
    public Produto buscaPorGuid(String guid) {
        ProdutoORM orm = repositorio.findByGuid(guid);
        if (orm == null) {
            throw new ModeloNaoEncontradoException(String.format("Produto com guid [%s] não encontrado.", guid));
        }
        Produto produto = conversorProdutoORMParaModelo.converte(orm);
        return produto;
    }

    @Override
    public Collection<Produto> lista() {
        return repositorio.findAll()
                .stream()
                .map(o -> conversorProdutoORMParaModelo.converte(o))
                .collect(Collectors.toList());
    }

}
