package br.com.valhala.restaurante.produtos.aplicacao.produto.validacao.impl;

import br.com.valhala.restaurante.dominio.validacao.resultado.Erro;
import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;
import br.com.valhala.restaurante.infra.geradores.GeradorGuid;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.validacao.ValidadorProduto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ValidadorProdutoImplTest {

    ValidadorProduto validadorProduto = new ValidadorProdutoImpl();

    @Test
    void naoDeveFalharQuandoPropriedadesEstiveremPreenchidasCorretamente() {
        Produto produto = Produto
                .builder()
                .guid(GeradorGuid.geraGuid())
                .nome("Teste-1")
                .descricao("Teste-1")
                .valor(BigDecimal.ONE)
                .dataCadastro(LocalDate.now())
                .fabricante("Teste-1")
                .build();

        ResultadoValidacao resultadoValidacao = validadorProduto.valida(produto);

        assertThat(resultadoValidacao.temErros(), is(false));
    }

    @Test
    void deveFalharQuandoTodasPropridadesObrigatoriasNaoForemPreenchidas() {
        Produto produto = Produto.builder().build();

        ResultadoValidacao resultadoValidacao = validadorProduto.valida(produto);

        Erro[] errosEsperados = {
                new Erro("guid", "O [guid] do Produto é obrigatório."),
                new Erro("nome", "O [nome] do Produto é obrigatório."),
                new Erro("descricao", "A [descricao] do Produto é obrigatória."),
                new Erro("dataCadastro", "A [dataCadastro] do Produto é obrigatória."),
                new Erro("valor", "O [valor] do Produto é obrigatório."),
                new Erro("fabricante", "O [fabricante] do Produto é obrigatório.")
        };

        assertThat(resultadoValidacao.temErros(), is(true));
        assertThat(resultadoValidacao.getErros(), is(not(empty())));
        assertThat(resultadoValidacao.getErros(), hasSize(6));
    }

    @Test
    void deveFalharQuandoValorForZero() {
        Produto produto = Produto
                .builder()
                .guid(GeradorGuid.geraGuid())
                .nome("Teste-1")
                .descricao("Teste-1")
                .valor(BigDecimal.ZERO)
                .dataCadastro(LocalDate.now())
                .fabricante("Teste-1")
                .build();

        ResultadoValidacao resultadoValidacao = validadorProduto.valida(produto);

        Erro[] errosEsperados = {new Erro("valor", "O [valor] do Produto deve ser maior ou igual à [0.01]")};

        assertThat(resultadoValidacao.temErros(), is(true));
        assertThat(resultadoValidacao.getErros(), is(not(empty())));
        assertThat(resultadoValidacao.getErros(), hasSize(1));
        assertThat(resultadoValidacao.getErros(), hasItems(errosEsperados));
    }

    @Test
    void deveFalharQuandoValorForMenorQueZero() {
        Produto produto = Produto
                .builder()
                .guid(GeradorGuid.geraGuid())
                .nome("Teste-1")
                .descricao("Teste-1")
                .valor(BigDecimal.ONE.negate())
                .dataCadastro(LocalDate.now())
                .fabricante("Teste-1")
                .build();

        ResultadoValidacao resultadoValidacao = validadorProduto.valida(produto);

        Erro[] errosEsperados = {new Erro("valor", "O [valor] do Produto deve ser maior ou igual à [0.01]")};

        assertThat(resultadoValidacao.temErros(), is(true));
        assertThat(resultadoValidacao.getErros(), is(not(empty())));
        assertThat(resultadoValidacao.getErros(), hasSize(1));
        assertThat(resultadoValidacao.getErros(), hasItems(errosEsperados));
    }

}