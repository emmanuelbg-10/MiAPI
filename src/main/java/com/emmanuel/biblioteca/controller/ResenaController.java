package com.emmanuel.biblioteca.controller;

import com.emmanuel.biblioteca.entity.Resena;
import com.emmanuel.biblioteca.exception.ErrorResponse;
import com.emmanuel.biblioteca.service.ResenaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/resenas")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(ResenaService resenaService) {
        this.resenaService = resenaService;
    }

    @GetMapping
    public ResponseEntity<List<Resena>> getAll() {
        List<Resena> resenas = resenaService.getResenas();
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/{resenaId}")
    public ResponseEntity<?> getById(@PathVariable("resenaId") Integer resenaId) {
        try {
            Resena resena = resenaService.getResenaById(resenaId);
            return ResponseEntity.ok(resena);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                            "Reseña con ID " + resenaId + " no encontrada", "/api/v1/resenas/" + resenaId)
            );
        }
    }
}
