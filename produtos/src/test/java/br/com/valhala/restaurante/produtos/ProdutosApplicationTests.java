package br.com.valhala.restaurante.produtos;

import br.com.valhala.restaurante.produtos.infra.test_containers.PostgresExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PostgresExtension.class)
class ProdutosApplicationTests {

    @Test
    void contextLoads() {
    }

}
