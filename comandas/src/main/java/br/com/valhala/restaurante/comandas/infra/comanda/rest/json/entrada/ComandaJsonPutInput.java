package br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComandaJsonPutInput extends ComandaJsonInput implements Serializable {

    private String guid;
    private Collection<ItemJsonPutInput> itens;

}
