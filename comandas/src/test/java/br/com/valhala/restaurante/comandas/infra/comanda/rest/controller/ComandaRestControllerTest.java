package br.com.valhala.restaurante.comandas.infra.comanda.rest.controller;

import br.com.valhala.restaurante.comandas.infra.dbrider.provedores.ProvedorDatasetComanda;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@DBRider
public class ComandaRestControllerTest {

    private static final String SERVLET_CONTEXT = "api-comanda";
    private static final String RESOURCE_PATH = "/api-comanda/comanda";

    private static final ParameterDescriptor[] COMANDA_GUID_PARAM_DESCRIPTOR = new ParameterDescriptor[]{
            parameterWithName("guid").description("Identificador único da Comanda.")
    };

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
                .andDo(document("produto/exclui",
                        pathParameters(COMANDA_GUID_PARAM_DESCRIPTOR)
                ));
    }

}
