package br.com.valhala.restaurante.comandas.dominio.comanda.modelo;

import br.com.valhala.restaurante.dominio.Entidade;
import br.com.valhala.restaurante.infra.geradores.GeradorGuid;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Item implements Entidade, Cloneable {

    @NotBlank(message = "{item.guid.obrigatorio}")
    @EqualsAndHashCode.Include
    private String guid;

    @NotNull
    @Valid
    private Produto produto;

    @NotNull
    private BigDecimal valorUnitario;

    @NotNull(message = "{item.quantidade.obrigadorio}")
    @Min(value = 1, message = "{item.quantidade.minimo}")
    private Integer quantidade;

    public static Item novo(Produto produto, BigDecimal valorUnitario, Integer quantidade) {
        return Item.of(GeradorGuid.geraGuid(), produto, valorUnitario, quantidade);
    }

    public BigDecimal calculaValorTotalItem() {
        return this.valorUnitario.multiply(new BigDecimal(quantidade));
    }

    public Item edita(final Item dadosEdicao) {
        return Item.of(this.guid, dadosEdicao.produto, dadosEdicao.getValorUnitario(), dadosEdicao.getQuantidade());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
