package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "deve retornar o produto e status 200"
    request {
        method 'GET'
        url '/produto/ba206deca62811ebbcbc0242ac130002'
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                guid: "ba206deca62811ebbcbc0242ac130002",
                nome: "Heineken 0 Alcool",
                descricao: "Cerveja Puro Malte sem Alcool",
                dataCadastro: "01/01/2021",
                valor: new BigDecimal("5.90"),
                fabricante: "Heineken"
        )
    }
}
