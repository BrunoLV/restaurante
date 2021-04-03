package br.com.valhala.restaurante.comandas.dominio.comanda.modelo;

import br.com.valhala.restaurante.dominio.ObjetoValor;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(staticName = "of")
@ToString
@EqualsAndHashCode
public class Produto implements ObjetoValor, Cloneable {

    @NotBlank(message = "{item.guid_produto.obrigatorio}")
    private String guid;
    @NotBlank(message = "{item.nome.obrigatorio}")
    private String nome;
    @NotNull(message = "{item.fabricante.obrigatorio}")
    private String fabricante;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
