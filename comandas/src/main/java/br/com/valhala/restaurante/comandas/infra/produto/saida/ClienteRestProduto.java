package br.com.valhala.restaurante.comandas.infra.produto.saida;

import br.com.valhala.restaurante.comandas.infra.produto.saida.dto.ProdutoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("produtos")
public interface ClienteRestProduto {

    @RequestMapping(value = "/produto/{guid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    ProdutoDTO getByGuid(@PathVariable("guid") String guid);

}
