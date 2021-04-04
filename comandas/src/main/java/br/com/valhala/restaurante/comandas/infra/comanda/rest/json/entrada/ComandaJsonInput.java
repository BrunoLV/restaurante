package br.com.valhala.restaurante.comandas.infra.comanda.rest.json.entrada;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
abstract class ComandaJsonInput implements Serializable {

    private Integer numeroMesa;

}
