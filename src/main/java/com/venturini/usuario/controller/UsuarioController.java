package com.venturini.usuario.controller;

import com.venturini.usuario.business.UsuarioService;
import com.venturini.usuario.business.ViaCepService;
import com.venturini.usuario.business.dto.EnderecoDTO;
import com.venturini.usuario.business.dto.TelefoneDTO;
import com.venturini.usuario.business.dto.UsuarioDTO;
import com.venturini.usuario.infrastructure.clients.dto.ViaCepDTO;
import com.venturini.usuario.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
// Swagger
// @Tag(name = "Tarefas", description = "Cadastro tarefas de usuários") // nome e descrição da controller
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ViaCepService viaCepService;

    // POST -> CREATE (Padrão HTTP <- Padrão Rest)
    @PostMapping
    // @Operation -> descrição do método
    @Operation(summary = "Salvar Usuários", description = "Cria um novo usuário")
    // @ApiResponse -> Status code
    @ApiResponse(responseCode = "200", description = "Usuário salvo com sucesso")
    @ApiResponse(responseCode = "409", description = "Usuário já cadastrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));
    }

    // Logar
    @PostMapping("/login")
    @Operation(summary = "Login Usuários", description = "Login de usuário")
    @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.autenticaLoginUsuario(usuarioDTO));
    }

    // @GET -> BUSCAR (Padrão HTTP -> Padrão Rest)
    @GetMapping
    @Operation(summary = "Buscar dados de Usuários por Email",
            description = "Buscar dados do usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<UsuarioDTO> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscaUsuarioPorEmail(email));
    }

    // Deleta usuario por email
    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar Usuários por Id", description = "Deleta usuário")
    @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<Void> deletarUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar Dados de Usuários",
            description = "Atualizar dados de usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                       @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualizar Endereço de Usuários",
            description = "Atualiza endereço de usuários")
    @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                         @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, enderecoDTO));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualizar Telefone de Usuários",
            description = "Atualiza telefone de usuários")
    @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                         @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, telefoneDTO));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Salvar Endereço de Usuários",
            description = "Salva endereço de usuários")
    @ApiResponse(responseCode = "200", description = "Endereço salvo com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<EnderecoDTO> cadastrarEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                        @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Salvar Telefone de Usuários",
            description = "Salva telefone de usuários")
    @ApiResponse(responseCode = "200", description = "Telefone salvo com sucesso")
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    @ApiResponse(responseCode = "403", description = "Usuário não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro de servidor")
    public ResponseEntity<TelefoneDTO> cadastrarTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                         @RequestHeader("Authorization") String  token) {
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, telefoneDTO));
    }

    @GetMapping("/endereco/{cep}")
    public ResponseEntity<ViaCepDTO> buscarDadosCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(viaCepService.buscaDadosEndereco(cep));
    }
  }
