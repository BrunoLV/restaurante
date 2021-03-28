package br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa;

import br.com.valhala.restaurante.produtos.infra.produto.orm.ProdutoORM;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProdutoSpringData extends JpaRepository<ProdutoORM, Long> {

    ProdutoORM findByGuid(String guid);

    boolean existsByNome(String nome);

    boolean existsByNomeEqualsAndGuidNot(String nome, String guid);

}
