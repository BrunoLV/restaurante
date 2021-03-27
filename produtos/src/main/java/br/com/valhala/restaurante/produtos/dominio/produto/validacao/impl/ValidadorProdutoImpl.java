package br.com.valhala.restaurante.produtos.dominio.produto.validacao.impl;

import br.com.valhala.restaurante.dominio.validacao.resultado.Erro;
import br.com.valhala.restaurante.dominio.validacao.resultado.ResultadoValidacao;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import br.com.valhala.restaurante.produtos.dominio.produto.validacao.ValidadorProduto;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class ValidadorProdutoImpl implements ValidadorProduto {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public ResultadoValidacao valida(Produto modelo) {
        ResultadoValidacao resultado = new ResultadoValidacao();
        Set<ConstraintViolation<Produto>> violacoes = validator.validate(modelo);
        violacoes.stream().map(v -> new Erro(v.getPropertyPath().toString(), v.getMessage())).forEach(e -> resultado.adicionaErro(e));
        return resultado;
    }

}