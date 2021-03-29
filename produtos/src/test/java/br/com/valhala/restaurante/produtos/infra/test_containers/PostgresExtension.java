package br.com.valhala.restaurante.produtos.infra.test_containers;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer<?> postgresql;

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        postgresql.stop();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        postgresql = new PostgreSQLContainer<>("postgres:alpine").withEnv("TZ", "GMT").withEnv("PGTZ", "GMT");
        postgresql.start();
        System.setProperty("spring.datasource.url", postgresql.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresql.getUsername());
        System.setProperty("spring.datasource.password", postgresql.getPassword());
        System.setProperty("spring.datasource.driver-class-name", "org.postgresql.Driver");
        System.setProperty("spring.jpa.database", "postgresql");
        System.setProperty("spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQLDialect");

    }

}
