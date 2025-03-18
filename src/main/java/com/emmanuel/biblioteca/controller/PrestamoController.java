package com.emmanuel.biblioteca.controller;

import com.emmanuel.biblioteca.entity.Prestamo;
import com.emmanuel.biblioteca.exception.ErrorResponse;
import com.emmanuel.biblioteca.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> getAll() {
        List<Prestamo> prestamos = prestamoService.getPrestamos();
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/{prestamoId}")
    public ResponseEntity<?> getById(@PathVariable("prestamoId") Integer prestamoId) {
        try {
            Prestamo prestamo = prestamoService.getPrestamoById(prestamoId);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                            "Préstamo con ID " + prestamoId + " no encontrado", "/api/v1/prestamos/" + prestamoId));
        }
    }
}