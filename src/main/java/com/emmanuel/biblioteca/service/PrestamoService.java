package com.emmanuel.biblioteca.service;

import com.emmanuel.biblioteca.entity.CopiaLibro;
import com.emmanuel.biblioteca.entity.Prestamo;
import com.emmanuel.biblioteca.entity.Resena;
import com.emmanuel.biblioteca.entity.Usuario;
import com.emmanuel.biblioteca.repository.PrestamoRepository;
import com.emmanuel.biblioteca.repository.CopiaLibroRepository;
import com.emmanuel.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoService {


    private final PrestamoRepository prestamoRepository;
    private final CopiaLibroRepository copiaLibroRepository;
    private final UsuarioRepository usuarioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, CopiaLibroRepository copiaLibroRepository, UsuarioRepository usuarioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.copiaLibroRepository = copiaLibroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Prestamo> getPrestamos(){
        return prestamoRepository.findAll();
    }

    public Prestamo getPrestamoById(Integer id){
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el prestamo con ID "+ id));
    }

    public Prestamo createPrestamo(Integer usuarioId, Integer copiaLibroId) {
        try {
            Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            CopiaLibro copiaLibro = copiaLibroRepository.findById(copiaLibroId).orElseThrow(() -> new RuntimeException("Copia del libro no encontrada"));

            LocalDate fechaInicio = LocalDate.now();
            LocalDate fechaVencimiento = fechaInicio.plusDays(14); // 2 semanas de préstamo

            Prestamo prestamo = new Prestamo(usuario, copiaLibro, fechaInicio, fechaVencimiento);

            return prestamoRepository.save(prestamo);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al crear el préstamo: " + e.getMessage());
        }
    }

    public void deletePrestamo(Integer usuarioId, Integer prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (!prestamo.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("El préstamo no pertenece a este usuario");
        }

        prestamoRepository.delete(prestamo);
    }

    public List<Prestamo> getPrestamosByUsuario(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return prestamoRepository.findByUsuario(usuario);
    }

    public Prestamo getPrestamoByUsuario(Integer usuarioId, Integer prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado"));

        if (!prestamo.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("El préstamo no pertenece a este usuario");
        }

        return prestamo;
    }

}