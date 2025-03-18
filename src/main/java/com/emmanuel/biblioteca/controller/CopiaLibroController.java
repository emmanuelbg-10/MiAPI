package com.emmanuel.biblioteca.controller;

import com.emmanuel.biblioteca.entity.CopiaLibro;
import com.emmanuel.biblioteca.exception.ErrorResponse;
import com.emmanuel.biblioteca.service.CopiaLibroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/copiaLibros")
public class CopiaLibroController {

    private final CopiaLibroService copiaLibroService;

    public CopiaLibroController(CopiaLibroService copiaLibroService) {
        this.copiaLibroService = copiaLibroService;
    }

    @GetMapping
    public ResponseEntity<List<CopiaLibro>> getAll() {
        List<CopiaLibro> copiaLibros = copiaLibroService.getCopiaLibros();
        return ResponseEntity.ok(copiaLibros);
    }

    @GetMapping("/{libroId}")
    public ResponseEntity<?> getById(@PathVariable("libroId") Integer libroId) {
        try {
            CopiaLibro copiaLibro = copiaLibroService.getCopiaLibroById(libroId);
            return ResponseEntity.ok(copiaLibro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                            "Copia de libro con ID " + libroId + " no encontrada", "/api/v1/copiaLibros/" + libroId));
        }
    }

    @PostMapping("/{libroId}")
    public ResponseEntity<?> saveUpdate(@PathVariable Integer libroId) {
        try {
            CopiaLibro copiaLibro = copiaLibroService.createCopiaLibro(libroId);
            return ResponseEntity.status(HttpStatus.CREATED).body(copiaLibro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                            e.getMessage(), "/api/v1/copiaLibros/" + libroId));
        }
    }

    @DeleteMapping("/{copiaLibroId}")
    public ResponseEntity<?> deleteLibro(@PathVariable("copiaLibroId") Integer copiaLibroId) {
        try {
            copiaLibroService.deleteCopiaLibroById(copiaLibroId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                            "Copia de libro con ID " + copiaLibroId + " no encontrada", "/api/v1/copiaLibros/" + copiaLibroId));
        }
    }
}
