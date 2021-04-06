package com.unipampa.rp6.web;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.tomcat.util.http.HeaderUtil;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unipampa.rp6.backend.domain.Usuario;
import com.unipampa.rp6.backend.repository.UsuarioRepository;

@RestController
@RequestMapping("/api")
@Transactional
public class UsuarioResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    private static final String ENTITY_NAME = "usuario";

    private final UsuarioRepository usuarioRepository;

    public UsuarioResource(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/usuarios")
    public Usuario createUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
        log.debug("REST request to save Usuario : {}", usuario);
        Usuario result = usuarioRepository.save(usuario);
        return result;
    }

    @PutMapping("/usuarios/{id}")
    public Usuario updateUsuario(@PathVariable(value = "id", required = false) final Long id, @RequestBody Usuario usuario)
        throws URISyntaxException {
        log.debug("REST request to update Usuario : {}, {}", id, usuario);

        Usuario result = usuarioRepository.save(usuario);
        return result;
    }

    @PatchMapping(value = "/usuarios/{id}", consumes = "application/merge-patch+json")
    public Usuario partialUpdateUsuario(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Usuario usuario
    ) throws URISyntaxException {
        log.debug("REST request to partial update Usuario partially : {}, {}", id, usuario);
        
        Optional<Usuario> result = usuarioRepository
            .findById(usuario.getId())
            .map(
                existingUsuario -> {
                    if (usuario.getNome() != null) {
                        existingUsuario.setNome(usuario.getNome());
                    }
                    if (usuario.getEmail() != null) {
                        existingUsuario.setEmail(usuario.getEmail());
                    }
                    if (usuario.getSenha() != null) {
                        existingUsuario.setSenha(usuario.getSenha());
                    }
                    if (usuario.getUsuario() != null) {
                        existingUsuario.setUsuario(usuario.getUsuario());
                    }

                    return existingUsuario;
                }
            )
            .map(usuarioRepository::save);

        return null;
    }

    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        log.debug("REST request to get all Usuarios");
        return usuarioRepository.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Optional<Usuario> getUsuario(@PathVariable Long id) {
        log.debug("REST request to get Usuario : {}", id);
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario;
    }

    @DeleteMapping("/usuarios/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        log.debug("REST request to delete Usuario : {}", id);
        usuarioRepository.deleteById(id);
    }
}
