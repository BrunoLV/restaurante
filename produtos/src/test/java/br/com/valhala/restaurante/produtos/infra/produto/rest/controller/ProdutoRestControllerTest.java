package br.com.valhala.restaurante.produtos.infra.produto.rest.controller;

import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoDadosJsonOutput;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoJsonOutput;
import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ListaProdutosDataSetProvider;
import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ProdutoDataSetProvider;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPostInput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPutInput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.saida.ProdutoJsonOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@DBRider
class ProdutoRestControllerTest {

    private static final String SERVLET_CONTEXT = "/api-produto";
    private static final String RESOURCE_PATH = "/api-produto/produto";

    private static final FieldDescriptor[] PRODUTO_POST_FIELD_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("nome").description("Nome do Produto."),
            fieldWithPath("descricao").description("Descrição do Produto."),
            fieldWithPath("valor").description("Valor do Produto."),
            fieldWithPath("fabricante").description("Fabricante do Produto.")
    };

    private static final FieldDescriptor[] PRODUTO_PUT_FIELD_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("guid").description("Identificador único do Produto."),
            fieldWithPath("nome").description("Nome do Produto."),
            fieldWithPath("descricao").description("Descrição do Produto."),
            fieldWithPath("valor").description("Valor do Produto."),
            fieldWithPath("fabricante").description("Fabricante do Produto.")
    };

    private static final FieldDescriptor[] PRODUTO_FIELD_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("guid").description("Identificador único do Produto."),
            fieldWithPath("nome").description("Nome do Produto."),
            fieldWithPath("descricao").description("Descrição do Produto."),
            fieldWithPath("dataCadastro").description("Data do Cadastro do Produto."),
            fieldWithPath("valor").description("Valor do Produto."),
            fieldWithPath("fabricante").description("Fabricante do Produto.")
    };

    private static final FieldDescriptor[] ERRO_VALIDACAO_DADOS = new FieldDescriptor[]{
            fieldWithPath("path").description("Path do recurso acessado."),
            fieldWithPath("mensagem").description("Mensagem da ocorrência."),
            fieldWithPath("erros[].campo").description("Campo validado.").optional(),
            fieldWithPath("erros[].mensagem").description("Mensagem de validação.")
    };

    private static final ParameterDescriptor[] PRODUTO_GUID_PARAM_DESCRIPTOR = new ParameterDescriptor[]{
            parameterWithName("guid").description("Identificador único do Produto.")
    };

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(MockMvcResultHandlers.print())
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveDeletarProdutoERetornar204() throws Exception {
        this.mockMvc
                .perform(delete(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff8765").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isNoContent())
                .andDo(document("produto/exclui",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveRetornar404QuandoInformadoGuidInexistenteParaExclusao() throws Exception {
        this.mockMvc
                .perform(delete(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff9999").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isNotFound())
                .andDo(document("produto/exclui-404",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveBuscarProdutoERetornar200() throws Exception {

        ProdutoJsonOutput outputEsperado = ProdutoJsonOutput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-1")
                .descricao("Teste-1")
                .dataCadastro(LocalDate.of(2021, 03, 26))
                .valor(new BigDecimal("100.00"))
                .fabricante("Teste-1")
                .build();

        MvcResult mvcResult = this.mockMvc
                .perform(get(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff8765").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isOk())
                .andDo(document("produto/obtem-por-guid",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR),
                        responseFields(PRODUTO_FIELD_DESCRIPTOR)
                )).andReturn();

        ProdutoJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProdutoJsonOutput.class);
        assertThat(output, equalTo(outputEsperado));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveRetornar404QuandoInformadoGuidInexistenteParaConsulta() throws Exception {

        this.mockMvc
                .perform(get(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff9999").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isNotFound())
                .andDo(document("produto/obtem-por-guid-404",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    void deveBuscarProdutosERetornar200() throws Exception {

        ProdutoJsonOutput[] outputsEsperados = new ProdutoJsonOutput[]{
                ProdutoJsonOutput
                        .builder()
                        .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                        .nome("Teste-1")
                        .descricao("Teste-1")
                        .dataCadastro(LocalDate.of(2021, 03, 26))
                        .valor(new BigDecimal("100.00"))
                        .fabricante("Teste-1")
                        .build(),
                ProdutoJsonOutput
                        .builder()
                        .guid("7de0b077875c45bcb4b6fd19f7d46f31")
                        .nome("Teste-2")
                        .descricao("Teste-2")
                        .dataCadastro(LocalDate.of(2021, 03, 26))
                        .valor(new BigDecimal("100.00"))
                        .fabricante("Teste-2")
                        .build()
        };

        MvcResult mvcResult = this.mockMvc
                .perform(get(RESOURCE_PATH + "/lista").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isOk())
                .andDo(document("produto/lista",
                        responseFields(fieldWithPath("[]").description("Lista de Produtos")).andWithPrefix("[].", PRODUTO_FIELD_DESCRIPTOR)
                )).andReturn();

        ProdutoJsonOutput[] outputs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProdutoJsonOutput[].class);

        assertThat(outputs, not(emptyArray()));
        assertThat(outputs, equalTo(outputsEsperados));
    }

    @Test
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    void deveRetornar422QuandoInformadoProdutoInvalidoNaEdicao() throws Exception {

        ProdutoJsonPutInput postInput = ProdutoJsonPutInput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-2")
                .descricao("Teste-criacao")
                .valor(new BigDecimal("101.00"))
                .fabricante(null)
                .build();

        String input = objectMapper.writeValueAsString(postInput);

        MvcResult mvcResult = this.mockMvc
                .perform(put(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff8765")
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isUnprocessableEntity())
                .andDo(document("produto/edicao-422",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR),
                        requestFields(PRODUTO_PUT_FIELD_DESCRIPTOR),
                        responseFields(ERRO_VALIDACAO_DADOS)
                )).andReturn();

        ErroValidacaoDadosJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ErroValidacaoDadosJsonOutput.class);

        assertThat(output.getPath(), equalTo(mvcResult.getRequest().getRequestURL().toString()));
        assertThat(output.getErros(), not(empty()));
        assertThat(output.getErros(), hasSize(2));
        assertThat(output.getErros(), hasItems(
                ErroValidacaoJsonOutput.builder().campo(null).mensagem("O [nome] do Produto já esta em uso.").build(),
                ErroValidacaoJsonOutput.builder().campo("fabricante").mensagem("O [fabricante] do Produto é obrigatório.").build()
        ));
    }


    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveRetornar404QuandoInformadoGuidInexistenteParaEdicao() throws Exception {

        ProdutoJsonPutInput putInput = ProdutoJsonPutInput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff9999")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .fabricante("Teste-edicao")
                .build();

        String input = objectMapper.writeValueAsString(putInput);

        this.mockMvc
                .perform(put(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff9999")
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isNotFound())
                .andDo(document("produto/edita-404",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR),
                        requestFields(PRODUTO_PUT_FIELD_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveEditarProdutoERetorna204() throws Exception {

        ProdutoJsonPutInput putInput = ProdutoJsonPutInput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .fabricante("Teste-edicao")
                .build();

        String json = objectMapper.writeValueAsString(putInput);

        this.mockMvc
                .perform(put(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff8765")
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNoContent())
                .andDo(document("produto/edita",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR),
                        requestFields(PRODUTO_PUT_FIELD_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveRetornar422QuandoInformadoProdutoInvalidoNaCriacaoDeNovo() throws Exception {

        ProdutoJsonPostInput postInput = ProdutoJsonPostInput
                .builder()
                .nome("Teste-1")
                .descricao("Teste-criacao")
                .valor(new BigDecimal("101.00"))
                .fabricante(null)
                .build();

        String input = objectMapper.writeValueAsString(postInput);

        MvcResult mvcResult = this.mockMvc
                .perform(post(RESOURCE_PATH)
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isUnprocessableEntity())
                .andDo(document("produto/cria-422",
                        requestFields(PRODUTO_POST_FIELD_DESCRIPTOR),
                        responseFields(ERRO_VALIDACAO_DADOS)
                )).andReturn();

        ErroValidacaoDadosJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ErroValidacaoDadosJsonOutput.class);

        assertThat(output.getPath(), equalTo(mvcResult.getRequest().getRequestURL().toString()));
        assertThat(output.getErros(), not(empty()));
        assertThat(output.getErros(), hasSize(2));
        assertThat(output.getErros(), hasItems(
                ErroValidacaoJsonOutput.builder().campo(null).mensagem("O [nome] do Produto já esta em uso.").build(),
                ErroValidacaoJsonOutput.builder().campo("fabricante").mensagem("O [fabricante] do Produto é obrigatório.").build()
        ));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void deveCriarOProdutoRetornar201EOEnderecoDoRecursoNoLocation() throws Exception {

        ProdutoJsonPostInput postInput = ProdutoJsonPostInput
                .builder()
                .nome("Teste-criacao")
                .descricao("Teste-criacao")
                .valor(new BigDecimal("101.00"))
                .fabricante("Teste-criacao")
                .build();

        String input = objectMapper.writeValueAsString(postInput);

        MvcResult mvcResult = this.mockMvc
                .perform(post(RESOURCE_PATH)
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("produto/cria",
                        requestFields(PRODUTO_POST_FIELD_DESCRIPTOR),
                        responseFields(PRODUTO_FIELD_DESCRIPTOR)
                )).andReturn();

        ProdutoJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ProdutoJsonOutput.class);
        assertThat(output.getGuid(), not(emptyString()));

        String guid = output.getGuid();
        String location = mvcResult.getResponse().getHeader("Location");
        assertThat(location, not(emptyString()));
        assertThat(location, containsString(String.format("%s/%s", RESOURCE_PATH, guid)));
    }

}
