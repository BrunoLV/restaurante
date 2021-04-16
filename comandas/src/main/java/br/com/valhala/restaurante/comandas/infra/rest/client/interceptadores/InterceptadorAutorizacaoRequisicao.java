package br.com.valhala.restaurante.comandas.infra.rest.client.interceptadores;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class InterceptadorAutorizacaoRequisicao implements RequestInterceptor {

    private static final String HEADER_AUTORIZACAO = "Authorization";
    private static final String TIPO_TOKEN = "Bearer";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) auth.getPrincipal();
        String token = jwt.getTokenValue();
        requestTemplate.header(HEADER_AUTORIZACAO, String.format("%s %s", TIPO_TOKEN, token));
    }

}