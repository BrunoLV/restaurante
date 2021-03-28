package br.com.valhala.restaurante.produtos.dominio.produto.validacao.regras;

import br.com.valhala.restaurante.produtos.aplicacao.produto.dao.ProdutoDAO;
import br.com.valhala.restaurante.produtos.dominio.produto.modelo.Produto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorProdutoNomeUnico implements ConstraintValidator<ProdutoNomeUnico, Produto> {

    @Autowired
    private ProdutoDAO dao;

    @Override
    public boolean isValid(Produto produto, ConstraintValidatorContext context) {
        boolean existe;
        if (produto.getGuid() != null) {
            existe = dao.existeProdutoComNomeIgualComGuidDiferente(produto.getNome(), produto.getGuid());
        } else {
            existe = dao.existeProdutoComNomeIgual(produto.getNome());
        }
        return !existe;
    }
}
