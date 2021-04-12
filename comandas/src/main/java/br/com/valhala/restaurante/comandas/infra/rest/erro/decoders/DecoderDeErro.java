package br.com.valhala.restaurante.comandas.infra.rest.erro.decoders;

import br.com.valhala.restaurante.infra.rest.exceptions.ErroRecursoExternoException;
import br.com.valhala.restaurante.infra.rest.exceptions.RecursoInexistenteException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class DecoderDeErro implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {

        final String recurso = response.request().url();

        Exception excecao;

        switch (response.status()) {
            case 404 -> excecao = new RecursoInexistenteException(recurso, "Recurso requisitado não encontrado.");
            default -> excecao = new ErroRecursoExternoException(recurso, "Erro na chamada do recurso.");
        }

        return excecao;

    }

}
