package com.venturini.usuario.business.converter;

import com.venturini.usuario.business.dto.EnderecoDTO;
import com.venturini.usuario.business.dto.TelefoneDTO;
import com.venturini.usuario.business.dto.UsuarioDTO;
import com.venturini.usuario.infrastructure.entity.Endereco;
import com.venturini.usuario.infrastructure.entity.Telefone;
import com.venturini.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // ______________________________________________________________________
    // DTO para Entity
    // ______________________________________________________________________

    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        // Uma das formas para converter DTO -> ENTITY
//        Usuario usuario = new Usuario();
//        usuario.setNome(usuarioDTO.getNome());
//        usuario.setEmail(usuarioDTO.getEmail());

        // DTO -> ENTITY : Builder
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(usuarioDTO.getEnderecos() != null ?
                        paraListaEndereco(usuarioDTO.getEnderecos()) : null)
                .telefones(usuarioDTO.getTelefones() != null ?
                        paraListaTelefone(usuarioDTO.getTelefones()) : null)
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS) {
        // Transforma endereco em uma lista de enderecos
        return enderecoDTOS.stream().map(this::paraEndereco).toList();

        // Outra forma de conversao
//        List<Endereco> enderecos = new ArrayList<>();
//        for(EnderecoDTO enderecoDTO : enderecoDTOS) {
//            enderecos.add(paraEndereco(enderecoDTO));
//        }
//
//        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    public Telefone paraTelefone(TelefoneDTO telefoneDTO) {
        return  Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    // ______________________________________________________________________
    // Entity para DTO
    // ______________________________________________________________________

    public UsuarioDTO paraUsuarioDTO(Usuario usuarioEntity) {
        // Uma das formas para converter DTO -> ENTITY
//        UsuarioDTO usuarioDto = new UsuarioDTO();
//        usuarioDto.setNome(usuarioEntity.getNome());
//        usuarioDto.setEmail(usuarioEntity.getEmail());

        // DTO -> ENTITY : Builder
        return UsuarioDTO.builder()
                .nome(usuarioEntity.getNome())
                .email(usuarioEntity.getEmail())
                .senha(usuarioEntity.getSenha())
                .enderecos(usuarioEntity.getEnderecos() != null ?
                        paraListaEnderecoDTO(usuarioEntity.getEnderecos()) : null)
                .telefones(usuarioEntity.getTelefones() != null ?
                        paraListaTelefoneDTO(usuarioEntity.getTelefones()) : null)
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoEntitys) {
        // Transforma endereco em uma lista de enderecos
        return enderecoEntitys.stream().map(this::paraEnderecoDTO).toList();

        // Outra forma de conversao
//        List<Endereco> enderecos = new ArrayList<>();
//        for(EnderecoDTO enderecoDTO : enderecoDTOS) {
//            enderecos.add(paraEndereco(enderecoDTO));
//        }
//
//        return enderecos;
    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoEntity) {
        return EnderecoDTO.builder()
                .id(enderecoEntity.getId())
                .rua(enderecoEntity.getRua())
                .numero(enderecoEntity.getNumero())
                .complemento(enderecoEntity.getComplemento())
                .cidade(enderecoEntity.getCidade())
                .estado(enderecoEntity.getEstado())
                .cep(enderecoEntity.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefoneEntitys) {
        return telefoneEntitys.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefoneEntity) {
        return  TelefoneDTO.builder()
                .id(telefoneEntity.getId())
                .numero(telefoneEntity.getNumero())
                .ddd(telefoneEntity.getDdd())
                .build();
    }

   public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario usuarioEntity) {
        return Usuario.builder()
                .id(usuarioEntity.getId())
                // Mudou o nome grava no DTO, senão pega do banco de dados(entity)
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuarioEntity.getNome())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuarioEntity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuarioEntity.getEmail())
                .enderecos(usuarioEntity.getEnderecos()) // aqui não muda o endereços
                .telefones(usuarioEntity.getTelefones()) // aqui não muda o telefones
                .build();
   }
    public Endereco updateEndereco(EnderecoDTO enderecoDTO, Endereco enderecoEntity) {
        return Endereco.builder()
                .id(enderecoEntity.getId())
                // Mudou o nome grava no DTO, senão pega do banco de dados(entity)
                .rua(enderecoDTO.getRua() != null ? enderecoDTO.getRua() : enderecoEntity.getRua())
                .numero(enderecoDTO.getNumero() != null ? enderecoDTO.getNumero() : enderecoEntity.getNumero())
                .complemento(enderecoDTO.getComplemento() != null ? enderecoDTO.getComplemento() : enderecoEntity.getComplemento())
                .cidade(enderecoDTO.getCidade() != null ? enderecoDTO.getCidade() : enderecoEntity.getCidade())
                .estado(enderecoDTO.getEstado() != null ? enderecoDTO.getEstado() : enderecoEntity.getEstado())
                .cep(enderecoDTO.getCep() != null ? enderecoDTO.getCep() : enderecoEntity.getCep())
                .build();
    }

    public Telefone updateTelefone(TelefoneDTO telefoneDTO, Telefone telefoneEntity) {
        return Telefone.builder()
                .id(telefoneEntity.getId())
                // Mudou o nome grava no DTO, senão pega do banco de dados(entity)
                .numero(telefoneDTO.getNumero() != null ? telefoneDTO.getNumero() : telefoneEntity.getNumero())
                .ddd(telefoneDTO.getDdd() != null ? telefoneDTO.getDdd() : telefoneEntity.getDdd())
                .build();
    }

    public Endereco paraEnderecoEntity(EnderecoDTO enderecoDTO, Long idUsuario) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .usuario_id(idUsuario)
                .build();
    }

    public Telefone paraTelefoneEntity(TelefoneDTO telefoneDTO, Long idUsuario) {
        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .usuario_id(idUsuario)
                .build();
    }
}
