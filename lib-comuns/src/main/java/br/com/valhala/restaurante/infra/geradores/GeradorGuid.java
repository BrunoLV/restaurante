package br.com.valhala.restaurante.infra.geradores;

import java.util.UUID;

public class GeradorGuid {

    public static String geraGuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
