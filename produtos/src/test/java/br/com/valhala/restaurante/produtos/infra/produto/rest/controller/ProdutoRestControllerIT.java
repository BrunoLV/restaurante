package br.com.valhala.restaurante.produtos.infra.produto.rest.controller;

import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoDadosJsonOutput;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoJsonOutput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.saida.ProdutoJsonOutput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPostInput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPutInput;
import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ListaProdutosDataSetProvider;
import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ProdutoDataSetProvider;
import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ProdutoPosEdicaoDataSetProvider;
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
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

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
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    void deveDevolverListaNoCorpoERetornar200() {

        ProdutoJsonOutput outputExperado1 = ProdutoJsonOutput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-1")
                .descricao("Teste-1")
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .valor(new BigDecimal("100.00"))
                .fabricante("Teste-1")
                .build();

        ProdutoJsonOutput outputExperado2 = ProdutoJsonOutput
                .builder()
                .guid("7de0b077875c45bcb4b6fd19f7d46f31")
                .nome("Teste-2")
                .descricao("Teste-2")
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .valor(new BigDecimal("100.00"))
                .fabricante("Teste-2")
                .build();

        ProdutoJsonOutput[] listaEsperada = {outputExperado1, outputExperado2};

        ProdutoJsonOutput[] output = given()
                .accept(ContentType.JSON)
                .log().all()
                .when()
                .get(urlRecurso + "/lista")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response().as(ProdutoJsonOutput[].class);

        assertThat(output, is(notNullValue()));

        List<ProdutoJsonOutput> outputs = Arrays.asList(output);
        assertThat(outputs, hasSize(2));
        assertThat(outputs, hasItems(listaEsperada));

    }

    @Test
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    @ExpectedDataSet(provider = ProdutoDataSetProvider.class)
    void deveExcluirERetornar204() {

        given()
                .pathParam("guid", "7de0b077875c45bcb4b6fd19f7d46f31")
                .log().all()
                .when()
                .delete(urlRecurso + "/{guid}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .log().all();
    }

    @Test
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    void deveRetornar404QuandoGuidInformadoExclusaoNaoExistir() {

        given()
                .pathParam("guid", "7de0b077875c45bcb4b6fd19f7d46999")
                .log().all()
                .when()
                .delete(urlRecurso + "/{guid}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .log().all();
    }

    @Test
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    void deveDevolverProdutoNoCorpoERetornar200() {

        ProdutoJsonOutput outputExperado = ProdutoJsonOutput
                .builder()
                .guid("7de0b077875c45bcb4b6fd19f7d46f31")
                .nome("Teste-2")
                .descricao("Teste-2")
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .valor(new BigDecimal("100.00"))
                .fabricante("Teste-2")
                .build();

        ProdutoJsonOutput output = given()
                .pathParam("guid", "7de0b077875c45bcb4b6fd19f7d46f31")
                .accept(ContentType.JSON)
                .when()
                .get(urlRecurso + "/{guid}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .log().all()
                .extract().response().as(ProdutoJsonOutput.class);

        assertThat(output, is(notNullValue()));
        assertThat(output, equalTo(outputExperado));

    }

    @Test
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    void deveRetornar404QuandoGuidInformadoConsultaNaoExistir() {

        given()
                .pathParam("guid", "7de0b077875c45bcb4b6fd19f7d46999")
                .when()
                .get(urlRecurso + "/{guid}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .log().all();

    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    @ExpectedDataSet(provider = ProdutoPosEdicaoDataSetProvider.class)
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
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
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
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveCadastrarERetornar201ComLocationDoRecursoCriado() {

        ProdutoJsonPostInput input = ProdutoJsonPostInput
                .builder()
                .nome("Teste-2")
                .descricao("Teste-2")
                .valor(new BigDecimal("100.00"))
                .fabricante("Teste-2")
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
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
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
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
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