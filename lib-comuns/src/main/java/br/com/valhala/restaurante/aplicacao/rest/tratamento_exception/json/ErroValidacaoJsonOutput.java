package br.com.valhala.restaurante.aplicacao.rest.tratamento_exception.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErroValidacaoJsonOutput implements Serializable {

    private String campo;
    private String mensagem;

}
