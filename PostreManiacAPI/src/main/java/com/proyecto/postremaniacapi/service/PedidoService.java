package com.proyecto.postremaniacapi.service;

import com.proyecto.postremaniacapi.dto.PedidoDTO;
import com.proyecto.postremaniacapi.dto.DetalleDTO;
import com.proyecto.postremaniacapi.model.Pedido;
import com.proyecto.postremaniacapi.model.DetallePedido;
import com.proyecto.postremaniacapi.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // 🔥 CREAR PEDIDO
    public String crearPedido(PedidoDTO pedidoDTO) throws Exception {

        // 🔐 VALIDACIONES
        if (pedidoDTO.userId == null || pedidoDTO.userId.isEmpty()) {
            throw new RuntimeException("El userId es obligatorio");
        }

        if (pedidoDTO.username == null || pedidoDTO.username.isEmpty()) {
            throw new RuntimeException("El username es obligatorio");
        }

        if (pedidoDTO.detalles == null || pedidoDTO.detalles.isEmpty()) {
            throw new RuntimeException("El pedido debe tener productos");
        }

        double totalCalculado = 0;
        List<DetallePedido> detalles = new ArrayList<>();

        // 🔄 PROCESAR DETALLES
        for (DetalleDTO d : pedidoDTO.detalles) {

            if (d.cantidad <= 0) {
                throw new RuntimeException("Cantidad inválida");
            }

            if (d.nombreProducto == null || d.nombreProducto.isEmpty()) {
                throw new RuntimeException("El nombre del producto es obligatorio");
            }

            double subtotal = d.cantidad * d.precioUnitario;
            totalCalculado += subtotal;

            String detalleId = UUID.randomUUID().toString();

            DetallePedido detalle = new DetallePedido(
                    detalleId,
                    d.productoId,
                    d.nombreProducto,
                    d.cantidad,
                    d.precioUnitario
            );

            detalles.add(detalle);
        }

        // 🧾 CREAR PEDIDO
        String pedidoId = UUID.randomUUID().toString();

        Pedido pedido = new Pedido(
                pedidoId,
                pedidoDTO.userId,
                pedidoDTO.username,
                new Date(),
                "PENDIENTE",
                totalCalculado
        );

        pedido.setDetalles(detalles);

        // 💾 GUARDAR
        pedidoRepository.guardarPedido(pedido);

        return pedidoId;
    }

    // 👑 ADMIN - TODOS LOS PEDIDOS
    public List<Pedido> obtenerPedidos() throws Exception {
        return pedidoRepository.obtenerPedidos();
    }

    // 👑 ADMIN - FILTRAR POR ESTADO
    public List<Pedido> obtenerPedidosPorEstado(String estado) throws Exception {
        return pedidoRepository.obtenerPedidosPorEstado(estado);
    }

    // 👤 CLIENTE - MIS PEDIDOS
    public List<Pedido> obtenerPedidosPorUsuario(String userId) throws Exception {

        if (userId == null || userId.isEmpty()) {
            throw new RuntimeException("UserId inválido");
        }

        return pedidoRepository.obtenerPedidosPorUsuario(userId);
    }

    // 🔄 CAMBIAR ESTADO
    public String actualizarEstado(String id, String estado) {

        List<String> estadosValidos = Arrays.asList(
                "PENDIENTE",
                "EN_PROCESO",
                "EN_CAMINO",
                "ENTREGADO"
        );

        if (!estadosValidos.contains(estado)) {
            throw new RuntimeException("Estado inválido");
        }

        pedidoRepository.actualizarEstado(id, estado);

        return "Estado actualizado a " + estado;
    }

    // 📊 CONTADOR PARA DASHBOARD
    public Map<String, Long> contarPedidosPorEstado() throws Exception {
        return pedidoRepository.contarPedidosPorEstado();
    }
}