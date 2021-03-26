package br.com.valhala.restaurante.produtos.dominio.produto.modelo;

import br.com.valhala.restaurante.dominio.RaizAgregado;
import br.com.valhala.restaurante.infra.geradores.GeradorGuid;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto implements RaizAgregado<Produto> {

    @EqualsAndHashCode.Include
    private String guid;
    private String nome;
    private String descricao;
    private LocalDate dataCadastro;
    private BigDecimal valor;
    private String fabricante;

    public static Produto novo(String nome, String descricao, BigDecimal valor, String fabricante) {
        return Produto.of(GeradorGuid.geraGuid(), nome, descricao, LocalDate.now(), valor, fabricante);
    }

    public Produto edita(Produto produto) {
        return Produto.of(this.guid, produto.nome, produto.descricao, this.dataCadastro, produto.valor, produto.fabricante);
    }

}
