package com.venturini.usuario.business;

import com.venturini.usuario.infrastructure.clients.ViaCepClient;
import com.venturini.usuario.infrastructure.clients.dto.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {
    // Injeção de dependência
    private final ViaCepClient viaCepClient;

    public ViaCepDTO buscaDadosEndereco(String cep) {
        return viaCepClient.buscarDadosEndereco(processaViaCep(cep));
    }

    private String processaViaCep(String cep) {
        String cepFormatado = cep.replace(" ", "").
                replace("-", "");

        // \d+ -> mais de um números ou [0-9]
        if(!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)) {
            throw new IllegalArgumentException("CEP: " + cep + " é invalido! Favor digitar novamente");
        }
        return cepFormatado;
    }
}
