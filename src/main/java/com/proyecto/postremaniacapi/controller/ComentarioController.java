package com.proyecto.postremaniacapi.controller;

import com.proyecto.postremaniacapi.dto.ComentarioRequest;
import com.proyecto.postremaniacapi.model.Comentario;
import com.proyecto.postremaniacapi.service.ComentarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    // 🔥 CREAR COMENTARIO (CON JWT REAL)
    @PostMapping
    public String crearComentario(
            @RequestBody ComentarioRequest request,
            HttpServletRequest httpRequest
    ) throws Exception {

        String userId = (String) httpRequest.getAttribute("userId");
        String username = (String) httpRequest.getAttribute("username");

        if (userId == null || username == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        return comentarioService.crearComentario(
                request.getProductoId(),
                request.getContenido(),
                userId,
                username
        );
    }

    // 📥 OBTENER TODOS
    @GetMapping
    public List<Comentario> obtenerComentarios(
            @RequestParam(required = false) String productoId
    ) throws Exception {

        // 🔥 FILTRO OPCIONAL
        if (productoId != null) {
            return comentarioService.obtenerComentariosPorProducto(productoId);
        }

        return comentarioService.obtenerComentarios();
    }

    // ❌ ELIMINAR (VALIDANDO CON userId)
    @DeleteMapping("/{id}")
    public String eliminarComentario(
            @PathVariable String id,
            HttpServletRequest httpRequest
    ) throws Exception {

        String userId = (String) httpRequest.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        return comentarioService.eliminarComentario(id, userId);
    }
}