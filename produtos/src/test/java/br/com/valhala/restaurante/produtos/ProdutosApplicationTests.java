package br.com.valhala.restaurante.produtos;

import br.com.valhala.restaurante.produtos.infra.test_containers.MySQLExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith({MySQLExtension.class})
class ProdutosApplicationTests {

    @Test
    void contextLoads() {
    }

}
