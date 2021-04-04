package br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ItemJsonPostInput extends ItemJsonInput {
}
