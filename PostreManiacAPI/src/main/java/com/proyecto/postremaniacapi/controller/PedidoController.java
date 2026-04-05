package com.proyecto.postremaniacapi.controller;

import com.proyecto.postremaniacapi.dto.PedidoDTO;
import com.proyecto.postremaniacapi.model.Pedido;
import com.proyecto.postremaniacapi.service.PedidoService;
import com.proyecto.postremaniacapi.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin("*")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private JwtUtil     jwtUtil;

    // 🔥 helper para token
    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token no enviado");
        }

        return authHeader.substring(7);
    }

    // 🛒 CREAR PEDIDO (cliente)
    @PostMapping
    public String crearPedido(
            @RequestBody PedidoDTO pedidoDTO,
            HttpServletRequest request
    ) throws Exception {

        String token = getToken(request);

        pedidoDTO.userId = jwtUtil.extractUserId(token);
        pedidoDTO.username = jwtUtil.extractUsername(token);

        return pedidoService.crearPedido(pedidoDTO);
    }

    // 👤 MIS PEDIDOS
    @GetMapping("/mis-pedidos")
    public List<Pedido> obtenerMisPedidos(HttpServletRequest request) throws Exception {

        String token = getToken(request);
        String userId = jwtUtil.extractUserId(token);

        return pedidoService.obtenerPedidosPorUsuario(userId);
    }

    // 👑 ADMIN - TODOS
    @GetMapping
    public List<Pedido> obtenerPedidos() throws Exception {
        return pedidoService.obtenerPedidos();
    }

    // 👑 ADMIN - FILTRAR
    @GetMapping("/estado/{estado}")
    public List<Pedido> obtenerPorEstado(@PathVariable String estado) throws Exception {
        return pedidoService.obtenerPedidosPorEstado(estado);
    }

    // 👑 ADMIN - ACTUALIZAR ESTADO
    @PutMapping("/{id}/estado")
    public String actualizarEstado(
            @PathVariable String id,
            @RequestParam String estado
    ) throws Exception {
        return pedidoService.actualizarEstado(id, estado);
    }

    // 👑 ADMIN - CONTEO
    @GetMapping("/conteo")
    public Map<String, Long> obtenerConteo() throws Exception {
        return pedidoService.contarPedidosPorEstado();
    }
}