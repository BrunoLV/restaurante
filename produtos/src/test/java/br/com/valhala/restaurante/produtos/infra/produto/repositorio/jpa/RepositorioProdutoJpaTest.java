package br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.infra.test_containers.PostgresExtension;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@ExtendWith(PostgresExtension.class)
class RepositorioProdutoJpaTest {

    @Autowired
    RepositorioProdutoJpa repositorioProdutoJpa;

    @Test
    @DataSet(value = "datasets/produto/produto.yml")
    void deveRetornaProdutoCadastradoComGuidInformado() {
        String guid = "8fa845b01c134bcf9de0de1ec2ff8765";
        Produto produto = repositorioProdutoJpa.buscaPorGuid(guid);
        assertThat(produto, is(notNullValue()));
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml")
    void deveDispararModeloNaoEncontradoExceptionQuandoInformadoGuidNaoCadastrado() {
        String guid = "8fa845b01c134bcf9de0de1ec2ff8784";
        assertThrows(ModeloNaoEncontradoException.class, () -> repositorioProdutoJpa.buscaPorGuid(guid));
    }


}