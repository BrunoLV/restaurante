package br.com.valhala.restaurante.produtos.dominio.produto.validacao.regras;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = ValidadorProdutoNomeUnico.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProdutoNomeUnico {

    String message() default "Produto com nome duplicado.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
