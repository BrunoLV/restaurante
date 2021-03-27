package br.com.valhala.restaurante.produtos.aplicacao.rest.tratamento_exception;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloInvalidoException;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoDadosJsonOutput;
import br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json.ErroValidacaoJsonOutput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorHandlingRestController {

    @ExceptionHandler(value = {ModeloInvalidoException.class})
    public ResponseEntity<ErroValidacaoDadosJsonOutput> trataErroValidacao(ModeloInvalidoException ex, HttpServletRequest request) {
        ErroValidacaoDadosJsonOutput output = new ErroValidacaoDadosJsonOutput();
        output.setPath(request.getRequestURL().toString());
        output.setMensagem(ex.getMessage());
        ex.getErros().stream().map(e -> new ErroValidacaoJsonOutput(e.getCampo(), e.getMensagem())).forEach(e -> output.adicionaErro(e));
        return ResponseEntity.unprocessableEntity().body(output);
    }

}
