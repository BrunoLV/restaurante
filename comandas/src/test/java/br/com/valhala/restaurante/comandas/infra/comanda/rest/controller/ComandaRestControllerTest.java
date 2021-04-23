package br.com.valhala.restaurante.comandas.infra.comanda.rest.controller;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoDadosJsonOutput;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoJsonOutput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ComandaJsonPostInput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ComandaJsonPutInput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ItemJsonPostInput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada.ItemJsonPutInput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.saida.ComandaJsonOutput;
import br.com.valhala.restaurante.comandas.infra.comanda.rest.json.saida.ItemJsonOutput;
import br.com.valhala.restaurante.comandas.infra.dbrider.provedores.ProvedorDatasetComanda;
import br.com.valhala.restaurante.comandas.infra.dbrider.provedores.ProvedorDatasetListaComandas;
import br.com.valhala.restaurante.comandas.infra.externo.produto.ClienteRestProduto;
import br.com.valhala.restaurante.comandas.infra.externo.produto.dto.ProdutoDTO;
import br.com.valhala.restaurante.infra.rest.exceptions.RecursoInexistenteException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
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
import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
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
public class ComandaRestControllerTest {

    private static final String SERVLET_CONTEXT = "/api-comandas";
    private static final String RESOURCE_PATH = "/api-comandas/comanda";

    private static final ParameterDescriptor[] COMANDA_GUID_PARAM_DESCRIPTOR = new ParameterDescriptor[]{
            parameterWithName("guid").description("Identificador único da Comanda.")
    };

    private static final FieldDescriptor[] COMANDA_FIELD_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("guid").description("Identificador único da Comanda."),
            fieldWithPath("data").description("Data da abertura da comanda."),
            fieldWithPath("numeroMesa").description("Numero da mesa da comanda."),
            fieldWithPath("itens[].guid").description("Identificador único do Item."),
            fieldWithPath("itens[].guidProduto").description("Identificador do produto."),
            fieldWithPath("itens[].nome").description("Nome do produto que constitui o item."),
            fieldWithPath("itens[].fabricante").description("Fabricante do produto que constitui o item."),
            fieldWithPath("itens[].valorUnitario").description("Valor unitário do item."),
            fieldWithPath("itens[].quantidade").description("Quantidade do item.")
    };

    private static final FieldDescriptor[] COMANDA_POST_FIELD_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("numeroMesa").description("Número da mesa."),
            fieldWithPath("itens[].guidProduto").description("Identificador do Produto."),
            fieldWithPath("itens[].quantidade").description("Quantidade do item."),
            fieldWithPath("itens[].valorUnitario").description("Valor unitário do Item.")
    };

    private static final FieldDescriptor[] COMANDA_PUT_FIELD_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("guid").description("Identificador único da Comanda."),
            fieldWithPath("numeroMesa").description("Número da mesa."),
            fieldWithPath("itens[].guid").description("Identificador único do Item."),
            fieldWithPath("itens[].guidProduto").description("Identificador do Produto."),
            fieldWithPath("itens[].quantidade").description("Quantidade do item."),
            fieldWithPath("itens[].valorUnitario").description("Valor unitário do Item.")
    };

    private static final FieldDescriptor[] ERRO_VALIDACAO_DADOS = new FieldDescriptor[]{
            fieldWithPath("path").description("Path do recurso acessado."),
            fieldWithPath("mensagem").description("Mensagem da ocorrência."),
            fieldWithPath("erros[].campo").description("Campo validado.").optional(),
            fieldWithPath("erros[].mensagem").description("Mensagem de validação.")
    };

    @MockBean
    ClienteRestProduto clienteRestProduto;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDomentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .alwaysDo(MockMvcResultHandlers.print())
                .apply(documentationConfiguration(restDomentation))
                .build();
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveDeletarComandaERetornar204() throws Exception {
        this.mockMvc
                .perform(delete(RESOURCE_PATH + "/{guid}", "ce1f42c0a17b11ebbcbc0242ac130002").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isNoContent())
                .andDo(document("comanda/exclui",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveRetornar404QuandoInformadoGuidInexistenteParaExclusao() throws Exception {
        this.mockMvc
                .perform(delete(RESOURCE_PATH + "/{guid}", "ce1f42c0a17b11ebbcbc0242ac130002").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isNoContent())
                .andDo(document("comanda/exclui-404",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveBuscarComandaERetornar200() throws Exception {

        ComandaJsonOutput outputEsperado = ComandaJsonOutput
                .builder()
                .guid("ce1f42c0a17b11ebbcbc0242ac130002")
                .data(LocalDate.of(2021, 01, 01))
                .numeroMesa(1)
                .itens(Collections.singletonList(
                        ItemJsonOutput
                                .builder()
                                .guid("9498ed66a17c11ebbcbc0242ac130002")
                                .nome("Teste")
                                .fabricante("Teste")
                                .valorUnitario(new BigDecimal("100.00"))
                                .quantidade(2)
                                .guidProduto("d86f9b16a17c11ebbcbc0242ac130002")
                                .build()
                ))
                .build();

        MvcResult mvcResult = this.mockMvc
                .perform(get(RESOURCE_PATH + "/{guid}", "ce1f42c0a17b11ebbcbc0242ac130002").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isOk())
                .andDo(document("comanda/obtem-por-guid",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR),
                        responseFields(COMANDA_FIELD_DESCRIPTOR)
                )).andReturn();

        ComandaJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ComandaJsonOutput.class);
        assertThat(output, equalTo(outputEsperado));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveRetornar404QuandoInformadoGuidInexistenteParaConsulta() throws Exception {

        this.mockMvc
                .perform(get(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff9999").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isNotFound())
                .andDo(document("comanda/obtem-por-guid-404",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProvedorDatasetListaComandas.class, cleanAfter = true)
    void deveBuscarProdutosERetornar200() throws Exception {

        ComandaJsonOutput[] outputsEsperados = new ComandaJsonOutput[]{
                ComandaJsonOutput
                        .builder()
                        .guid("ce1f42c0a17b11ebbcbc0242ac130002")
                        .data(LocalDate.of(2021, 01, 01))
                        .numeroMesa(1)
                        .itens(Collections.singletonList(
                                ItemJsonOutput
                                        .builder()
                                        .guid("9498ed66a17c11ebbcbc0242ac130002")
                                        .nome("Teste")
                                        .fabricante("Teste")
                                        .valorUnitario(new BigDecimal("100.00"))
                                        .quantidade(2)
                                        .guidProduto("d86f9b16a17c11ebbcbc0242ac130002")
                                        .build()
                        ))
                        .build(),
                ComandaJsonOutput
                        .builder()
                        .guid("ce1f42c0a17b11ebbcbc0242ac130003")
                        .data(LocalDate.of(2021, 01, 01))
                        .numeroMesa(1)
                        .itens(Collections.singletonList(
                                ItemJsonOutput
                                        .builder()
                                        .guid("9498ed66a17c11ebbcbc0242ac130003")
                                        .nome("Teste-1")
                                        .fabricante("Teste-1")
                                        .valorUnitario(new BigDecimal("100.00"))
                                        .quantidade(2)
                                        .guidProduto("d86f9b16a17c11ebbcbc0242ac130003")
                                        .build()
                        ))
                        .build()
        };

        MvcResult mvcResult = this.mockMvc
                .perform(get(RESOURCE_PATH + "/lista").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isOk())
                .andDo(document("produto/lista",
                        responseFields(fieldWithPath("[]").description("Lista de Produtos")).andWithPrefix("[].", COMANDA_FIELD_DESCRIPTOR)
                )).andReturn();

        ComandaJsonOutput[] outputs = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ComandaJsonOutput[].class);

        assertThat(outputs, not(emptyArray()));
        assertThat(outputs, equalTo(outputsEsperados));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveCriarAComandaRetornar201EOEnderecoDoRecursoNoLocation() throws Exception {

        configuraChamadaSucessoConsultaProduto();

        ComandaJsonPostInput postInput = ComandaJsonPostInput
                .builder()
                .numeroMesa(2)
                .itens(Collections.singletonList(
                        ItemJsonPostInput
                                .builder()
                                .guidProduto("d86f9b16a17c11ebbcbc0242ac130003")
                                .quantidade(1)
                                .valorUnitario(new BigDecimal("50.00"))
                                .build()
                ))
                .build();

        String input = objectMapper.writeValueAsString(postInput);

        MvcResult mvcResult = this.mockMvc
                .perform(post(RESOURCE_PATH)
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("comanda/cria",
                        requestFields(COMANDA_POST_FIELD_DESCRIPTOR),
                        responseFields(COMANDA_FIELD_DESCRIPTOR)
                )).andReturn();

        verify(clienteRestProduto, times(1)).getByGuid(anyString());

        ComandaJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ComandaJsonOutput.class);
        assertThat(output.getGuid(), not(emptyString()));

        String guid = output.getGuid();
        String location = mvcResult.getResponse().getHeader("Location");
        assertThat(location, not(emptyString()));
        assertThat(location, containsString(String.format("%s/%s", RESOURCE_PATH, guid)));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveRetornar422QuandoInformadaComandaInvalidoNaCriacaoDeNovo() throws Exception {

        configuraChamadaSucessoConsultaProduto();

        ComandaJsonPostInput postInput = ComandaJsonPostInput
                .builder()
                .numeroMesa(null)
                .itens(Collections.singletonList(
                        ItemJsonPostInput
                                .builder()
                                .guidProduto("d86f9b16a17c11ebbcbc0242ac130003")
                                .quantidade(null)
                                .valorUnitario(new BigDecimal("50.00"))
                                .build()
                ))
                .build();

        String input = objectMapper.writeValueAsString(postInput);

        MvcResult mvcResult = this.mockMvc
                .perform(post(RESOURCE_PATH)
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isUnprocessableEntity())
                .andDo(document("comanda/cria-422",
                        requestFields(COMANDA_POST_FIELD_DESCRIPTOR),
                        responseFields(ERRO_VALIDACAO_DADOS)
                )).andReturn();

        ErroValidacaoDadosJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ErroValidacaoDadosJsonOutput.class);

        assertThat(output.getPath(), equalTo(mvcResult.getRequest().getRequestURL().toString()));
        assertThat(output.getErros(), not(empty()));
        assertThat(output.getErros(), hasSize(2));
        assertThat(output.getErros(), hasItems(
                ErroValidacaoJsonOutput.builder().campo("numeroMesa").mensagem("O [numeroMesa] da Comanda é obrigatório.").build(),
                ErroValidacaoJsonOutput.builder().campo("itens[0].quantidade").mensagem("A [quantidade] do Item é obrigatória.").build()
        ));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveRetornar422QuandoInformadoProdutoInexistenteNaCriacaoDeComanda() throws Exception {

        configuraChamadaProdutoNaoEncontrado();

        ComandaJsonPostInput postInput = ComandaJsonPostInput
                .builder()
                .numeroMesa(1)
                .itens(Collections.singletonList(
                        ItemJsonPostInput
                                .builder()
                                .guidProduto("d86f9b16a17c11ebbcbc0242ac139999")
                                .quantidade(1)
                                .valorUnitario(new BigDecimal("50.00"))
                                .build()
                ))
                .build();

        String input = objectMapper.writeValueAsString(postInput);

        MvcResult mvcResult = this.mockMvc
                .perform(post(RESOURCE_PATH)
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isUnprocessableEntity())
                .andDo(document("comanda/cria-422",
                        requestFields(COMANDA_POST_FIELD_DESCRIPTOR),
                        responseFields(ERRO_VALIDACAO_DADOS)
                )).andReturn();

        ErroValidacaoDadosJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ErroValidacaoDadosJsonOutput.class);

        assertThat(output.getPath(), equalTo(mvcResult.getRequest().getRequestURL().toString()));
        assertThat(output.getErros(), not(empty()));
        assertThat(output.getErros(), hasSize(1));
        assertThat(output.getErros(), hasItems(
                ErroValidacaoJsonOutput.builder().campo("guidProduto").mensagem(String.format("Produto identificado com guid: %s não localizado.", "d86f9b16a17c11ebbcbc0242ac139999")).build()
        ));
    }

    private void configuraChamadaProdutoNaoEncontrado() {
        when(clienteRestProduto.getByGuid(anyString())).thenThrow(RecursoInexistenteException.class);
    }

    private void configuraChamadaSucessoConsultaProduto() {
        ProdutoDTO produtoMock = ProdutoDTO
                .builder()
                .guid("d86f9b16a17c11ebbcbc0242ac130003")
                .nome("Teste-mock")
                .descricao("Teste-mock")
                .dataCadastro(LocalDate.of(2021, 1, 1))
                .valor(new BigDecimal(50))
                .fabricante("Teste-mock")
                .build();

        when(clienteRestProduto.getByGuid(anyString())).thenReturn(produtoMock);
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveEditarComandaERetorna204() throws Exception {

        configuraChamadaSucessoConsultaProduto();

        ComandaJsonPutInput putInput = ComandaJsonPutInput
                .builder()
                .guid("ce1f42c0a17b11ebbcbc0242ac130002")
                .numeroMesa(2)
                .itens(Collections.singletonList(
                        ItemJsonPutInput
                                .builder()
                                .guid("ce1f42c0a17b11ebbcbc0242ac130002")
                                .guidProduto("ce1f42c0a17b11ebbcbc0242ac130002")
                                .quantidade(3)
                                .valorUnitario(new BigDecimal("50.00"))
                                .build()
                ))
                .build();

        String input = objectMapper.writeValueAsString(putInput);

        this.mockMvc
                .perform(put(RESOURCE_PATH + "/{guid}", "ce1f42c0a17b11ebbcbc0242ac130002")
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isNoContent())
                .andDo(document("comanda/edita",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR),
                        requestFields(COMANDA_PUT_FIELD_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveRetornar404QuandoInformadoGuidInexistenteParaEdicao() throws Exception {

        configuraChamadaSucessoConsultaProduto();

        ComandaJsonPutInput putInput = ComandaJsonPutInput
                .builder()
                .guid("ce1f42c0a17b11ebbcbc0242ac139999")
                .numeroMesa(2)
                .itens(Collections.singletonList(
                        ItemJsonPutInput
                                .builder()
                                .guid("ce1f42c0a17b11ebbcbc0242ac139999")
                                .guidProduto("ce1f42c0a17b11ebbcbc0242ac139999")
                                .quantidade(3)
                                .valorUnitario(new BigDecimal("50.00"))
                                .build()
                ))
                .build();

        String input = objectMapper.writeValueAsString(putInput);

        this.mockMvc
                .perform(put(RESOURCE_PATH + "/{guid}", "ce1f42c0a17b11ebbcbc0242ac139999")
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isNotFound())
                .andDo(document("comanda/edita-404",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProvedorDatasetComanda.class, cleanAfter = true)
    void deveRetornar422QuandoInformadaComandaInvalidoNaEdicao() throws Exception {

        configuraChamadaSucessoConsultaProduto();

        ComandaJsonPutInput putInput = ComandaJsonPutInput
                .builder()
                .guid("ce1f42c0a17b11ebbcbc0242ac130002")
                .numeroMesa(null)
                .itens(Collections.singletonList(
                        ItemJsonPutInput
                                .builder()
                                .guid("ce1f42c0a17b11ebbcbc0242ac130002")
                                .guidProduto("ce1f42c0a17b11ebbcbc0242ac130002")
                                .quantidade(null)
                                .valorUnitario(new BigDecimal("50.00"))
                                .build()
                ))
                .build();

        String input = objectMapper.writeValueAsString(putInput);

        MvcResult mvcResult = this.mockMvc
                .perform(put(RESOURCE_PATH + "/{guid}", "ce1f42c0a17b11ebbcbc0242ac130002")
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input))
                .andExpect(status().isUnprocessableEntity())
                .andDo(document("comanda/edita-422",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR),
                        requestFields(COMANDA_PUT_FIELD_DESCRIPTOR),
                        responseFields(ERRO_VALIDACAO_DADOS)
                )).andReturn();

        ErroValidacaoDadosJsonOutput output = objectMapper.readValue(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), ErroValidacaoDadosJsonOutput.class);

        assertThat(output.getPath(), equalTo(mvcResult.getRequest().getRequestURL().toString()));
        assertThat(output.getErros(), not(empty()));
        assertThat(output.getErros(), hasSize(2));
        assertThat(output.getErros(), hasItems(
                ErroValidacaoJsonOutput.builder().campo("numeroMesa").mensagem("O [numeroMesa] da Comanda é obrigatório.").build(),
                ErroValidacaoJsonOutput.builder().campo("itens[0].quantidade").mensagem("A [quantidade] do Item é obrigatória.").build()
        ));
    }

}
