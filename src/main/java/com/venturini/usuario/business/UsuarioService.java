package com.venturini.usuario.business;

import com.venturini.usuario.business.converter.UsuarioConverter;
import com.venturini.usuario.business.dto.UsuarioDTO;
import com.venturini.usuario.infrastructure.entity.Usuario;
import com.venturini.usuario.infrastructure.exceptions.ConflictException;
import com.venturini.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.venturini.usuario.infrastructure.repository.UsuarioRepository;
import com.venturini.usuario.infrastructure.security.JwtUtil;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Salvar Usuário
    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        // Setar a senha criptografada
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        usuario = usuarioRepository.save(usuario);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    // Verifica se o email existe
    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado. " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado. ", e.getCause());
        }
    }

    // Chamar a função existsByEmail(email)
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Buscar usuario por email
    public Usuario buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não foi encontrado. " + email));
    }

    // Deleta usuário por email
    public void deletaUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        // Aqui buscamos o email do usuário através do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extractUsername(token.substring(7));

        // Busca os dados do usuário no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Email não localizado. " + email));

        // Mesclou os dados que recebemos da requisição DTO com os dados  do banco de dados
        Usuario usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        // Colocou criptografia na nossa senha
        usuarioDTO.setSenha(usuarioDTO.getEmail() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        // Salvou os dados do usuário convertido e depois pegou o retorno e converter para UsuarioDTO
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));

    }

}
