package br.com.valhala.restaurante.produtos.modelo.produto.validacao.impl;

import br.com.valhala.restaurante.dominio.validacao.resultado.Erro;
import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;
import br.com.valhala.restaurante.infra.geradores.GeradorGuid;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.validacao.ValidadorProduto;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DBRider
class ValidadorProdutoImplTest {

    @Autowired
    ValidadorProduto validadorProduto;

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

        assertThat(resultadoValidacao.temErros()).isFalse();
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

        assertThat(resultadoValidacao.temErros()).isTrue();
        assertThat(resultadoValidacao.getErros()).isNotEmpty().hasSize(6).contains(errosEsperados);
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

        assertThat(resultadoValidacao.temErros()).isTrue();
        assertThat(resultadoValidacao.getErros()).isNotEmpty().hasSize(1).contains(errosEsperados);
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

        assertThat(resultadoValidacao.temErros()).isTrue();
        assertThat(resultadoValidacao.getErros()).isNotEmpty().hasSize(1).contains(errosEsperados);
    }

}