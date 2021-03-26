package br.com.valhala.restaurante.produtos.infra.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class WebConfig {

    private static String[] BASE_NAMES = {
            "br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto"
    };

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(BASE_NAMES);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

}
