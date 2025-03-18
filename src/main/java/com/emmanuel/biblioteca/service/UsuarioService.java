package com.emmanuel.biblioteca.service;

import com.emmanuel.biblioteca.entity.Resena;
import com.emmanuel.biblioteca.entity.Usuario;
import com.emmanuel.biblioteca.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario getUsuarioById(Integer id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró al usuario con ID "+ id));
    }

    public Usuario saveOrUpdate(Usuario usuario){
        usuarioRepository.save(usuario);
        return usuario;
    }

    public void deleteUsuarioById(Integer usuarioId){
        Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);

        if(usuario.isPresent()){
            usuarioRepository.deleteById(usuarioId);
        } else {
            throw new RuntimeException("El usuario con ID"+ usuarioId+" no existe.");
        }
    }
}
