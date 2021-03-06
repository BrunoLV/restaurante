package br.com.valhala.restaurante.produtos.dominio.produto.modelo;

import br.com.valhala.restaurante.dominio.RaizAgregado;
import br.com.valhala.restaurante.infra.geradores.GeradorGuid;
import br.com.valhala.restaurante.produtos.dominio.produto.validacao.regras.ProdutoNomeUnico;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ProdutoNomeUnico(message = "{produto.nome.duplicado}")
public class Produto implements RaizAgregado<Produto> {

    @EqualsAndHashCode.Include
    @NotBlank(message = "{produto.guid.obrigatorio}")
    private String guid;

    @NotBlank(message = "{produto.nome.obrigatorio}")
    private String nome;

    @NotBlank(message = "{produto.descricao.obrigatorio}")
    private String descricao;

    @NotNull(message = "{produto.data_cadastro.obrigatorio}")
    private LocalDate dataCadastro;

    @NotNull(message = "{produto.valor.obrigatorio}")
    @DecimalMin(value = "0.01", inclusive = true, message = "{produto.valor.minimo}")
    private BigDecimal valor;

    @NotBlank(message = "{produto.fabricante.obrigatorio}")
    private String fabricante;

    public static Produto novo(String nome, String descricao, BigDecimal valor, String fabricante) {
        return Produto.of(GeradorGuid.geraGuid(), nome, descricao, LocalDate.now(), valor, fabricante);
    }

    public Produto edita(Produto produto) {
        return Produto.of(this.guid, produto.nome, produto.descricao, this.dataCadastro, produto.valor, produto.fabricante);
    }

}
