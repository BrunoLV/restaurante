package br.com.valhala.restaurante.produtos.infra.produto.dao.jpa;

import br.com.valhala.restaurante.produtos.aplicacao.produto.dao.ProdutoDAO;
import br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa.RepositorioProdutoSpringData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoDAOJPA implements ProdutoDAO {

    private final RepositorioProdutoSpringData repositorio;

    @Override
    public boolean existeProdutoComNomeIgual(String nome) {
        return repositorio.existsByNome(nome);
    }

    @Override
    public boolean existeProdutoComNomeIgualComGuidDiferente(String nome, String guid) {
        return repositorio.existsByNomeEqualsAndGuidNot(nome, guid);
    }

}
