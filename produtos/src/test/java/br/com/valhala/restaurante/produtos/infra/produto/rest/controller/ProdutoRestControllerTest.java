package br.com.valhala.restaurante.produtos.infra.produto.rest.controller;

import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPostInput;
import br.com.valhala.restaurante.produtos.infra.produto.rest.json.entrada.ProdutoJsonPutInput;
import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ListaProdutosDataSetProvider;
import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ProdutoDataSetProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

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

    private static final FieldDescriptor[] ARRAY_PRODUTO_FIELD_DESCRIPTOR = new FieldDescriptor[]{
            fieldWithPath("[].guid").description("Identificador único do Produto."),
            fieldWithPath("[].nome").description("Nome do Produto."),
            fieldWithPath("[].descricao").description("Descrição do Produto."),
            fieldWithPath("[].dataCadastro").description("Data do Cadastro do Produto."),
            fieldWithPath("[].valor").description("Valor do Produto."),
            fieldWithPath("[].fabricante").description("Fabricante do Produto.")
    };

    private static final ParameterDescriptor[] PRODUTO_GUID_PARAM_DESCRIPTOR = new ParameterDescriptor[]{
            parameterWithName("guid").description("Identificador único do Produto.")
    };

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setup(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void excluiProduto() throws Exception {
        this.mockMvc
                .perform(delete(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff8765").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isNoContent())
                .andDo(document("produto/exclui",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void obtemPorGuid() throws Exception {
        this.mockMvc
                .perform(get(RESOURCE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff8765").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isOk())
                .andDo(document("produto/obtem-por-guid",
                        pathParameters(PRODUTO_GUID_PARAM_DESCRIPTOR),
                        responseFields(PRODUTO_FIELD_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ListaProdutosDataSetProvider.class, cleanAfter = true)
    void obtemListaProdutos() throws Exception {
        this.mockMvc
                .perform(get(RESOURCE_PATH + "/lista").contextPath(SERVLET_CONTEXT))
                .andExpect(status().isOk())
                .andDo(document("produto/lista",
                        responseFields(ARRAY_PRODUTO_FIELD_DESCRIPTOR)
                ));
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    void editaProduto() throws Exception {

        ProdutoJsonPutInput putInput = ProdutoJsonPutInput
                .builder()
                .guid("8fa845b01c134bcf9de0de1ec2ff8765")
                .nome("Teste-edicao")
                .descricao("Teste-edicao")
                .valor(new BigDecimal("101.00"))
                .fabricante("Teste-edicao")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
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
    void criaProduto() throws Exception {

        ProdutoJsonPostInput putInput = ProdutoJsonPostInput
                .builder()
                .nome("Teste-criacao")
                .descricao("Teste-criacao")
                .valor(new BigDecimal("101.00"))
                .fabricante("Teste-criacao")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(putInput);

        this.mockMvc
                .perform(post(RESOURCE_PATH)
                        .contextPath(SERVLET_CONTEXT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andDo(document("produto/cria",
                        requestFields(PRODUTO_POST_FIELD_DESCRIPTOR),
                        responseFields(PRODUTO_FIELD_DESCRIPTOR)
                ));
    }

}
