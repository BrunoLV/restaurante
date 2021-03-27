package br.com.valhala.restaurante.aplicacao.rest.tratamento_exception;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloInvalidoException;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoDadosJsonOutput;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoJsonOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ErrorHandlingRestController {

    @ExceptionHandler(value = {ModeloInvalidoException.class})
    public ResponseEntity<ErroValidacaoDadosJsonOutput> trataErroValidacao(ModeloInvalidoException ex, WebRequest request) {
        ErroValidacaoDadosJsonOutput output = new ErroValidacaoDadosJsonOutput();
        output.setPath(request.getContextPath());
        output.setMensagem(ex.getMessage());
        ex.getErros().stream().map(e -> new ErroValidacaoJsonOutput(e.getCampo(), e.getMensagem())).forEach(e -> output.adicionaErro(e));
        return ResponseEntity.unprocessableEntity().body(output);
    }

}
