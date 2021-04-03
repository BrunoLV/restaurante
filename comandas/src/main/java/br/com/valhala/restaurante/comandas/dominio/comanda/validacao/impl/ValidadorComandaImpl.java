package br.com.valhala.restaurante.comandas.dominio.comanda.validacao.impl;

import br.com.valhala.restaurante.comandas.dominio.comanda.modelo.Comanda;
import br.com.valhala.restaurante.comandas.dominio.comanda.validacao.ValidadorComanda;
import br.com.valhala.restaurante.dominio.validacao.resultado.Erro;
import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ValidadorComandaImpl implements ValidadorComanda {

    private final Validator validator;

    @Override
    public ResultadoValidacao valida(Comanda modelo) {
        ResultadoValidacao resultado = new ResultadoValidacao();
        Set<ConstraintViolation<Comanda>> violacoes = validator.validate(modelo);
        violacoes.stream().map(v -> new Erro(v.getPropertyPath().toString(), v.getMessage())).forEach(resultado::adicionaErro);
        return resultado;
    }

}
