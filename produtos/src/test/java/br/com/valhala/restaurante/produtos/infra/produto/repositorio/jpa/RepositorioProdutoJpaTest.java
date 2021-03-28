package br.com.valhala.restaurante.produtos.infra.produto.repositorio.jpa;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles("test")
@DBRider
class RepositorioProdutoJpaTest {

    @Autowired
    RepositorioProdutoJpa repositorioProdutoJpa;

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    @ExpectedDataSet(value = "datasets/produto/produto-pos-inclusao.yml", ignoreCols = {"data_cadastro"})
    void deveIncluirProdutoComSucesso() {
        Produto produto = Produto
                .builder()
                .guid("7de0b077875c45bcb4b6fd19f7d46f31")
                .nome("Teste-2")
                .descricao("Teste-2")
                .valor(new BigDecimal("100.00"))
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .fabricante("Teste-2")
                .build();
        repositorioProdutoJpa.cria(produto);
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    @ExpectedDataSet(value = "datasets/produto/produto-pos-edicao.yml", ignoreCols = {"data_cadastro"})
    void deveEditarProdutoComSucesso() {
        Produto produto = Produto
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .fabricante("Teste-edicao")
                .build();
        repositorioProdutoJpa.edita(produto);
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    void deveDispararModeloNaoEncontradoExceptionQuandoInformadoGuidNaoCadastradoNaEdicao() {
        Produto produto = Produto
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8999")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .fabricante("Teste-edicao")
                .build();

        assertThatExceptionOfType(ModeloNaoEncontradoException.class)
                .isThrownBy(() -> repositorioProdutoJpa.edita(produto))
                .withMessage("Produto com guid [8fa845b01c134bcf9de0de1ec2ff8999] não encontrado.");
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    void deveExcluirProdutoComSucesso() {
        Produto produto = repositorioProdutoJpa.buscaPorGuid("8fa845b01c134bcf9de0de1ec2ff8765");
        assertThat(produto).isNotNull();
        repositorioProdutoJpa.exclui(produto);
        assertThatExceptionOfType(ModeloNaoEncontradoException.class)
                .isThrownBy(() -> repositorioProdutoJpa.exclui(produto))
                .withMessage("Produto com guid [8fa845b01c134bcf9de0de1ec2ff8765] não encontrado.");
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", transactional = true, cleanAfter = true)
    void deveDispararModeloNaoEncontradoExceptionQuandoInformadoGuidNaoCadastradoNaExclusao() {
        Produto produto = Produto.builder().guid("8fa845b01c134bcf9de0de1ec2ff8999").build();
        assertThatExceptionOfType(ModeloNaoEncontradoException.class)
                .isThrownBy(() -> repositorioProdutoJpa.exclui(produto))
                .withMessage("Produto com guid [8fa845b01c134bcf9de0de1ec2ff8999] não encontrado.");
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveRetornaProdutoCadastradoComGuidInformado() {
        String guid = "8fa845b01c134bcf9de0de1ec2ff8765";
        Produto produto = repositorioProdutoJpa.buscaPorGuid(guid);
        assertThat(produto).isNotNull();
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveDispararModeloNaoEncontradoExceptionQuandoInformadoGuidNaoCadastrado() {
        String guid = "8fa845b01c134bcf9de0de1ec2ff8784";
        assertThatExceptionOfType(ModeloNaoEncontradoException.class)
                .isThrownBy(() -> repositorioProdutoJpa.buscaPorGuid(guid))
                .withMessage("Produto com guid [8fa845b01c134bcf9de0de1ec2ff8784] não encontrado.");
    }

    @Test
    @DataSet(value = "datasets/produto/produto-lista.yml", cleanAfter = true)
    void deveListarTodosProdutosCadastrados() {
        Collection<Produto> produtos = repositorioProdutoJpa.lista();
        assertThat(produtos)
                .isNotEmpty()
                .hasSize(2)
                .extracting("guid").contains("8fa845b01c134bcf9de0de1ec2ff8765", "7de0b077875c45bcb4b6fd19f7d46f31");
    }

}