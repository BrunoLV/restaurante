package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.controller;

import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoDadosJsonOutput;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoJsonOutput;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonOutput;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonPostInput;
import br.com.valhala.restaurante.produtos.aplicacao.produto.rest.json.ProdutoJsonPutInput;
import br.com.valhala.restaurante.produtos.infra.test_containers.PostgresExtension;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(PostgresExtension.class)
@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
class ProdutoRestControllerIT {

    @LocalServerPort
    private int porta;

    private String urlRecurso;

    @BeforeEach
    void setup() {
        urlRecurso = String.format("http://localhost:%d/api/produto", porta);
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    @ExpectedDataSet(value = "datasets/produto/produto-pos-edicao.yml", ignoreCols = {"data_cadastro"})
    void deveAlterarERetornar204() {

        ProdutoJsonPutInput input = ProdutoJsonPutInput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .fabricante("Teste-edicao")
                .build();

        given()
            .contentType(ContentType.JSON)
            .body(input)
            .pathParam("guid", "8fa845b01c134bcf9de0de1ec2ff8765")
            .log().all()
        .when()
            .put(urlRecurso + "/{guid}")
        .then()
            .statusCode(HttpStatus.NO_CONTENT.value())
            .log().all();
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveRetornar404QuandoGuidInformadoNaoExistir() {

        ProdutoJsonPutInput input = ProdutoJsonPutInput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8999")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .fabricante("Teste-edicao")
                .build();

        given()
            .contentType(ContentType.JSON)
            .body(input)
            .pathParam("guid", "8fa845b01c134bcf9de0de1ec2ff8999")
            .log().all()
        .when()
            .put(urlRecurso + "/{guid}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value())
            .log().all();
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveCadastrarERetornar201ComLocationDoRecursoCriado() {

        ProdutoJsonPostInput input = ProdutoJsonPostInput
                .builder()
                .nome("Teste-IT")
                .descricao("Teste-IT")
                .valor(new BigDecimal("100.00"))
                .fabricante("Teste-IT")
                .build();

        Response response = given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(input)
                    .log().all()
                .when()
                    .post(urlRecurso)
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .contentType(ContentType.JSON)
                    .log().all()
                    .extract().response();

        ProdutoJsonOutput output = response.body().as(ProdutoJsonOutput.class);

        assertThat(output, is(notNullValue()));
        assertThat(output.getGuid(), is(notNullValue()));

        String locationEsperado = "http://localhost:" + porta + "/api/produto/" + output.getGuid();

        assertThat(response.getHeaders().hasHeaderWithName("Location"), is(true));
        assertThat(response.getHeader("Location"), equalTo(locationEsperado));
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveRetornar422EValidacoesNoCorpoDaRespostaQuandoNomeDuplicado() {

        ProdutoJsonPostInput input = ProdutoJsonPostInput
                .builder()
                .nome("Teste-1")
                .descricao("Teste-IT")
                .valor(new BigDecimal("100.00"))
                .fabricante("Teste-IT")
                .build();

        ErroValidacaoDadosJsonOutput output = given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(input)
                    .log().all()
                .when()
                    .post(urlRecurso)
                .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .contentType(ContentType.JSON)
                    .log().all()
                    .extract().response().body().as(ErroValidacaoDadosJsonOutput.class);

        assertThat(output, is(notNullValue()));
        assertThat(output.getPath(), equalTo(urlRecurso));
        assertThat(output.getMensagem(), equalTo("Produto inválido."));

        ErroValidacaoJsonOutput[] errosEsperados = {
                new ErroValidacaoJsonOutput(null, "O [nome] do Produto já esta em uso.")
        };

        assertThat(output.getErros(), hasSize(1));
        assertThat(output.getErros(), hasItems(errosEsperados));
    }

    @Test
    @DataSet(value = "datasets/produto/produto.yml", cleanAfter = true)
    void deveRetornar422EValidacoesNoCorpoDaRespostaQuandoInvalido() {

        ProdutoJsonPostInput input = ProdutoJsonPostInput
                .builder()
                .build();

        ErroValidacaoDadosJsonOutput output = given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(input)
                    .log().all()
                .when()
                    .post(urlRecurso)
                .then()
                    .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                    .contentType(ContentType.JSON)
                    .log().all()
                    .extract().response().body().as(ErroValidacaoDadosJsonOutput.class);

        assertThat(output, is(notNullValue()));
        assertThat(output.getPath(), equalTo(urlRecurso));
        assertThat(output.getMensagem(), equalTo("Produto inválido."));

        ErroValidacaoJsonOutput[] errosEsperados = {
                new ErroValidacaoJsonOutput("nome", "O [nome] do Produto é obrigatório."),
                new ErroValidacaoJsonOutput("descricao", "A [descricao] do Produto é obrigatória."),
                new ErroValidacaoJsonOutput("valor", "O [valor] do Produto é obrigatório."),
                new ErroValidacaoJsonOutput("fabricante", "O [fabricante] do Produto é obrigatório.")
        };

        assertThat(output.getErros(), hasSize(4));
        assertThat(output.getErros(), hasItems(errosEsperados));
    }

}