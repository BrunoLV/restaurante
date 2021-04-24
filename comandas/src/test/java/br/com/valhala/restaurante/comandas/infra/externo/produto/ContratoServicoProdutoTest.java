package br.com.valhala.restaurante.comandas.infra.externo.produto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ContratoServicoProdutoTest {

    @RegisterExtension
    public StubRunnerExtension stubRunner = new StubRunnerExtension().downloadStub("br.com.valhala.restaurante.produtos", "produtos", "1.0-SNAPSHOT", "stubs")
            .withPort(8080)
            .stubsMode(StubRunnerProperties.StubsMode.LOCAL);

    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    @Test
    void deveDevolverProdutoERetornar200() throws IOException, InterruptedException {

        HttpRequest httpRequest = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/produto/1"))
                .setHeader("Accept", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        assertThat(response.statusCode(), equalTo(HttpStatus.NO_CONTENT.value()));
    }

}
