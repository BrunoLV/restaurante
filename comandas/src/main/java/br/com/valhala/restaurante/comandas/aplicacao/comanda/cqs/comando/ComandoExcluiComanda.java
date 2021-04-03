package br.com.valhala.restaurante.comandas.aplicacao.comanda.cqs.comando;

import br.com.valhala.restaurante.aplicacao.cqs.Comando;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class ComandoExcluiComanda implements Comando {

    private String guid;

}
