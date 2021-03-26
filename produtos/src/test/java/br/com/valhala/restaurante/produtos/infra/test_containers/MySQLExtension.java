package br.com.valhala.restaurante.produtos.infra.test_containers;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;

public class MySQLExtension implements BeforeAllCallback, AfterAllCallback {

    private MySQLContainer<?> mysql;

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        mysql.stop();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        mysql = new MySQLContainer<>("mysql:8")
                .withDatabaseName("test-db")
                .withUsername("test-user")
                .withPassword("test-pass")
                .withExposedPorts(3603);

        mysql.start();

        System.setProperty("spring.datasource.url", mysql.getJdbcUrl());
        System.setProperty("spring.datasource.username", mysql.getUsername());
        System.setProperty("spring.datasource.password", mysql.getPassword());
    }

}
