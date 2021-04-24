package contracts

import org.springframework.cloud.contract.spec.Contract

import java.time.LocalDate
import java.time.format.DateTimeFormatter

Contract.make {

    description "deve retornar um produto pelo GUID 1"

    request {
        method 'GET'
        url '/produto/1'
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                guid: "1",
                nome: "Heineken 0 Alcool",
                descricao: "Cerveja Puro Malte sem Alcool",
                dataCadastro: "01/01/2021",
                valor: new BigDecimal("5.90"),
                fabricante: "Heineken"
        )
    }

}
