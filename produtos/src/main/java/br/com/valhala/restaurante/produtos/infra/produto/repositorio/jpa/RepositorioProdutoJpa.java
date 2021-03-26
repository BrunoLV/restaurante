package br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.repositorio.RepositorioProduto;
import br.com.valhala.restaurante.produtos.infra.produto.orm.ProdutoORM;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RepositorioProdutoJpa implements RepositorioProduto {

    private final RepositorioProdutoSpringData repositorio;

    @Override
    public void cria(Produto produto) {
        ProdutoORM orm = new ProdutoORM();
        orm.setGuid(produto.getGuid());
        orm.setNome(produto.getNome());
        orm.setDescricao(produto.getDescricao());
        orm.setDataCadastro(produto.getDataCadastro());
        orm.setValor(produto.getValor());
        orm.setFabricante(produto.getFabricante());
        repositorio.save(orm);
    }

    @Override
    public void edita(String guid, Produto produto) {
        ProdutoORM orm = repositorio.findByGuid(guid);
        if (orm == null) {
            throw new ModeloNaoEncontradoException();
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
            throw new ModeloNaoEncontradoException();
        }
        repositorio.delete(orm);
    }

    @Override
    public Optional<Produto> buscaPorGuid(String guid) {
        ProdutoORM orm = repositorio.findByGuid(guid);

        Produto produto = orm == null ?
                null :
                Produto.builder()
                        .guid(orm.getGuid())
                        .descricao(orm.getDescricao())
                        .valor(orm.getValor())
                        .fabricante(orm.getFabricante())
                        .build();

        return Optional.ofNullable(produto);
    }

    @Override
    public Collection<Produto> lista() {
        return repositorio.findAll()
                .stream()
                .map(o -> Produto.builder()
                        .guid(o.getGuid())
                        .descricao(o.getDescricao())
                        .valor(o.getValor())
                        .fabricante(o.getFabricante())
                        .build())
                .collect(Collectors.toList());
    }

}
