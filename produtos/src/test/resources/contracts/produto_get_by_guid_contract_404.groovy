package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("deve retornar status 404 para GUID inexistente")
    request {
        method 'GET'
        url '/produto/125a0f18a62911ebbcbc0242ac130002'
    }
    response {
        status NOT_FOUND()
    }
}
