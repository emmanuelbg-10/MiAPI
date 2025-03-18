package com.emmanuel.biblioteca.controller;

import com.emmanuel.biblioteca.entity.Libro;
import com.emmanuel.biblioteca.entity.Resena;
import com.emmanuel.biblioteca.service.LibroService;
import com.emmanuel.biblioteca.service.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/libros")
public class LibroController {
    private final LibroService libroService;
    private final ResenaService resenaService;

    public LibroController(LibroService libroService, ResenaService resenaService) {
        this.libroService = libroService;
        this.resenaService = resenaService;
    }

    @GetMapping
    public ResponseEntity<List<Libro>> getAll() {
        List<Libro> libros = libroService.getLibros();
        return ResponseEntity.ok(libros);
    }

    @GetMapping("/{libroId}")
    public ResponseEntity<Libro> getById(@PathVariable("libroId") Integer libroId) {
        try {
            Libro libro = libroService.getLibroById(libroId).orElseThrow(() ->
                    new RuntimeException("Libro con ID " + libroId + " no encontrado"));
            return ResponseEntity.ok(libro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{libroId}/resenas")
    public ResponseEntity<List<Resena>> getResenasByLibro(@PathVariable("libroId") Integer libroId) {
        List<Resena> resenas = resenaService.getResenasByLibro(libroId);
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/{libroId}/resenas/{resenaId}")
    public ResponseEntity<Resena> getByIdLibroResena(
            @PathVariable("libroId") Integer libroId,
            @PathVariable("resenaId") Integer resenaId) {
        try {
            Resena resena = resenaService.getResenaLibroById(resenaId, libroId);
            return ResponseEntity.ok(resena);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}