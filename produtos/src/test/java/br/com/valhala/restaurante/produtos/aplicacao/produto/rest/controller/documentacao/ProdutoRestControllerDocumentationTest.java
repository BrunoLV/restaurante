package br.com.valhala.restaurante.produtos.aplicacao.produto.rest.controller.documentacao;

import br.com.valhala.restaurante.produtos.infra.dbrider.providers.ProdutoDataSetProvider;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@DBRider
public class ProdutoRestControllerDocumentationTest {

    private static final String BASE_PATH = "/produto";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;


    @BeforeEach
    public void setup(RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    @DataSet(provider = ProdutoDataSetProvider.class, cleanAfter = true)
    public void obtemPorGuidSucesso() throws Exception {
        this.mockMvc
                .perform(get(BASE_PATH + "/{guid}", "8fa845b01c134bcf9de0de1ec2ff8765"))
                .andExpect(status().isOk())
                .andDo(document("produto/obtem-por-guid",
                        pathParameters(
                            parameterWithName("guid").description("Identificador único do Produto a ser consultado.")
                        ),
                        responseFields(
                            fieldWithPath("guid").description("Identificador único do Produto."),
                            fieldWithPath("nome").description("Nome do Produto."),
                            fieldWithPath("descricao").description("Descrição do Produto."),
                            fieldWithPath("dataCadastro").description("Data do Cadastro do Produto."),
                            fieldWithPath("valor").description("Valor do Produto."),
                            fieldWithPath("fabricante").description("Fabricante do Produto."))
                       ));
    }

}
