package com.emmanuel.biblioteca.controller;

import com.emmanuel.biblioteca.entity.Prestamo;
import com.emmanuel.biblioteca.entity.Resena;
import com.emmanuel.biblioteca.entity.Usuario;
import com.emmanuel.biblioteca.exception.ErrorResponse;
import com.emmanuel.biblioteca.service.PrestamoService;
import com.emmanuel.biblioteca.service.ResenaService;
import com.emmanuel.biblioteca.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ResenaService resenaService;
    private final PrestamoService prestamoService;

    public UsuarioController(UsuarioService usuarioService, ResenaService resenaService, PrestamoService prestamoService) {
        this.usuarioService = usuarioService;
        this.resenaService = resenaService;
        this.prestamoService = prestamoService;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getAll() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<?> getById(@PathVariable("usuarioId") Integer usuarioId) {
        try {
            Usuario usuario = usuarioService.getUsuarioById(usuarioId);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), "/api/v1/usuarios/" + usuarioId));
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> saveUpdate(@RequestBody Usuario usuario) {
        Usuario savedUsuario = usuarioService.saveOrUpdate(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        try {
            usuarioService.deleteUsuarioById(usuarioId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), "/api/v1/usuarios/" + usuarioId));
        }
    }

    @GetMapping("/{usuarioId}/resenas")
    public ResponseEntity<List<Resena>> getResenasByUsuario(@PathVariable("usuarioId") Integer usuarioId) {
        List<Resena> resenas = resenaService.getResenasByUsuario(usuarioId);
        return ResponseEntity.ok(resenas);
    }

    @GetMapping("/{usuarioId}/libros/{libroId}/resenas/{resenaId}")
    public ResponseEntity<?> getResenaUsuario(@PathVariable Integer usuarioId,
                                              @PathVariable Integer libroId,
                                              @PathVariable Integer resenaId) {
        try {
            Resena resena = resenaService.getResenaByUsuarioYLibro(resenaId, usuarioId, libroId);
            return ResponseEntity.ok(resena);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), "/api/v1/usuarios/" + usuarioId + "/libros/" + libroId + "/resenas/" + resenaId));
        }
    }

    @PostMapping("/{usuarioId}/libros/{libroId}/resenas")
    public ResponseEntity<Resena> saveOrUpdateResena(@PathVariable Integer usuarioId,
                                                     @PathVariable Integer libroId,
                                                     @RequestBody Resena resena) {
        Resena savedResena = resenaService.saveOrUpdateResena(usuarioId, libroId, resena);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResena);
    }

    @DeleteMapping("/{usuarioId}/libros/{libroId}/resenas/{resenaId}")
    public ResponseEntity<Void> deleteResena(@PathVariable Integer usuarioId,
                                             @PathVariable Integer libroId,
                                             @PathVariable Integer resenaId) {
        resenaService.deleteResena(usuarioId, libroId, resenaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{usuarioId}/prestamos/{copiaLibroId}")
    public ResponseEntity<?> createPrestamo(@PathVariable Integer usuarioId,
                                            @PathVariable Integer copiaLibroId) {
        try {
            Prestamo prestamo = prestamoService.createPrestamo(usuarioId, copiaLibroId);
            return ResponseEntity.status(HttpStatus.CREATED).body(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), "/api/v1/usuarios/" + usuarioId + "/prestamos/" + copiaLibroId));
        }
    }

    @DeleteMapping("/{usuarioId}/prestamos/{prestamoId}")
    public ResponseEntity<?> deletePrestamo(@PathVariable Integer usuarioId,
                                            @PathVariable Integer prestamoId) {
        try {
            prestamoService.deletePrestamo(usuarioId, prestamoId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), "/api/v1/usuarios/" + usuarioId + "/prestamos/" + prestamoId));
        }
    }

    @GetMapping("/{usuarioId}/prestamos")
    public ResponseEntity<List<Prestamo>> getPrestamosByUsuario(@PathVariable Integer usuarioId) {
        List<Prestamo> prestamos = prestamoService.getPrestamosByUsuario(usuarioId);
        return ResponseEntity.ok(prestamos);
    }

    @GetMapping("/{usuarioId}/prestamos/{prestamoId}")
    public ResponseEntity<?> getPrestamoByUsuario(@PathVariable Integer usuarioId,
                                                  @PathVariable Integer prestamoId) {
        try {
            Prestamo prestamo = prestamoService.getPrestamoByUsuario(usuarioId, prestamoId);
            return ResponseEntity.ok(prestamo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", e.getMessage(), "/api/v1/usuarios/" + usuarioId + "/prestamos/" + prestamoId));
        }
    }
}
