package com.venturini.usuario.infrastructure.clients;

import com.venturini.usuario.infrastructure.clients.dto.ViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


// nome da API          // url da porta
@FeignClient(name = "via-cep", url = "${viacep.url}")
public interface ViaCepClient {

    @GetMapping("/ws/{cep}/json/")
    ViaCepDTO buscarDadosEndereco(@PathVariable("cep") String cep);
}


