package com.proyecto.postremaniacapi.service;

import com.proyecto.postremaniacapi.model.Comentario;
import com.proyecto.postremaniacapi.repository.ComentarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    // 🔥 CREAR COMENTARIO (CON userId y username)
    public String crearComentario(String productoId, String contenido, String userId, String username) throws Exception {

        if (contenido == null || contenido.trim().isEmpty()) {
            throw new RuntimeException("El comentario no puede estar vacío");
        }

        Comentario c = new Comentario();

        c.setProductoId(productoId);
        c.setContenido(contenido);

        // 🔥 IMPORTANTE
        c.setUserId(userId);
        c.setUsername(username);

        c.setFecha(LocalDateTime.now().toString());

        comentarioRepository.guardarComentario(c);

        return "Comentario creado";
    }

    // 📥 TODOS
    public List<Comentario> obtenerComentarios() throws Exception {
        return comentarioRepository.obtenerTodos();
    }

    // 🍰 🔥 POR PRODUCTO
    public List<Comentario> obtenerComentariosPorProducto(String productoId) throws Exception {
        return comentarioRepository.obtenerPorProducto(productoId);
    }

    // ❌ ELIMINAR (VALIDANDO POR userId)
    public String eliminarComentario(String id, String userId) throws Exception {

        Comentario c = comentarioRepository.buscarPorId(id);

        if (c == null) {
            throw new RuntimeException("Comentario no encontrado");
        }

        // 🔥 VALIDACIÓN REAL (NO username)
        if (!c.getUserId().equals(userId)) {
            throw new RuntimeException("No puedes eliminar este comentario");
        }

        comentarioRepository.eliminarComentario(id);

        return "Comentario eliminado";
    }
}