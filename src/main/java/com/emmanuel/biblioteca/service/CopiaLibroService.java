package com.emmanuel.biblioteca.service;

import com.emmanuel.biblioteca.entity.CopiaLibro;
import com.emmanuel.biblioteca.entity.Libro;
import com.emmanuel.biblioteca.repository.CopiaLibroRepository;
import com.emmanuel.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CopiaLibroService {
    private final CopiaLibroRepository copiaLibroRepository;
    private final LibroRepository libroRepository;

    public CopiaLibroService(CopiaLibroRepository copiaLibroRepository, LibroRepository libroRepository) {
        this.copiaLibroRepository = copiaLibroRepository;
        this.libroRepository = libroRepository;
    }

    public List<CopiaLibro> getCopiaLibros(){
        return copiaLibroRepository.findAll();
    }

    public CopiaLibro getCopiaLibroById(Integer id) {
        return copiaLibroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Copia de libro no encontrada con ID: " + id));
    }

    public void deleteCopiaLibroById(Integer id) {
        CopiaLibro copiaLibro = getCopiaLibroById(id); // Lanza excepción si no existe
        copiaLibroRepository.delete(copiaLibro);
    }

    public CopiaLibro createCopiaLibro(Integer libroId) {
        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + libroId));

        CopiaLibro nuevaCopia = new CopiaLibro(libro);
        return copiaLibroRepository.save(nuevaCopia);
    }
}
