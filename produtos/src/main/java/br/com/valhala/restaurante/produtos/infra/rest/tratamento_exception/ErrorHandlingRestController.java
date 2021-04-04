package br.com.valhala.restaurante.produtos.infra.rest.tratamento_exception;

import br.com.valhala.restaurante.aplicacao.exceptions.ModeloInvalidoException;
import br.com.valhala.restaurante.aplicacao.exceptions.ModeloNaoEncontradoException;
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
        ex.getErros().stream().map(e -> new ErroValidacaoJsonOutput(e.getCampo() == null || e.getCampo().isBlank() ? null : e.getCampo(), e.getMensagem())).forEach(e -> output.adicionaErro(e));
        return ResponseEntity.unprocessableEntity().body(output);
    }

    @ExceptionHandler(value = {ModeloNaoEncontradoException.class})
    public ResponseEntity<Void> trataModeloNaoEncontrado(ModeloNaoEncontradoException ex, HttpServletRequest request) {
        return ResponseEntity.notFound().build();
    }

}
