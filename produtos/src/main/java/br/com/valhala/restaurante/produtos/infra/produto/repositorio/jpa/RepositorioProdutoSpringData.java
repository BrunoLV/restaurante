package br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa;

import br.com.valhala.restaurante.produtos.infra.produto.orm.ProdutoORM;
import org.springframework.data.jpa.repository.JpaRepository;

interface RepositorioProdutoSpringData extends JpaRepository<ProdutoORM, Long> {

    ProdutoORM findByGuid(String guid);

}
