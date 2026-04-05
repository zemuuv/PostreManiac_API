package com.proyecto.postremaniacapi.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.proyecto.postremaniacapi.model.Pedido;
import com.proyecto.postremaniacapi.model.DetallePedido;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PedidoRepository {

    private final String COLECCION = "pedidos";

    // 🔥 GUARDAR PEDIDO
    public void guardarPedido(Pedido pedido) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        // guardar pedido principal
        db.collection(COLECCION)
                .document(pedido.getId())
                .set(pedido);

        // guardar detalles
        for (DetallePedido d : pedido.getDetalles()) {

            Map<String, Object> detalleMap = new HashMap<>();
            detalleMap.put("id", d.getId());
            detalleMap.put("productoId", d.getProductoId());
            detalleMap.put("nombreProducto", d.getNombreProducto());
            detalleMap.put("cantidad", d.getCantidad());
            detalleMap.put("precioUnitario", d.getPrecioUnitario());

            db.collection(COLECCION)
                    .document(pedido.getId())
                    .collection("detalles")
                    .document(d.getId())
                    .set(detalleMap);
        }
    }

    // 🔥 OBTENER TODOS (ADMIN)
    public List<Pedido> obtenerPedidos() throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLECCION).get();

        return mapearPedidosConDetalles(future.get().getDocuments());
    }

    // 🔥 FILTRAR POR ESTADO (ADMIN)
    public List<Pedido> obtenerPedidosPorEstado(String estado) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLECCION)
                .whereEqualTo("estado", estado)
                .get();

        return mapearPedidosConDetalles(future.get().getDocuments());
    }

    // 🔥 🔥 NUEVO: MIS PEDIDOS (CLIENTE)
    public List<Pedido> obtenerPedidosPorUsuario(String userId) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLECCION)
                .whereEqualTo("userId", userId)
                .get();

        return mapearPedidosConDetalles(future.get().getDocuments());
    }

    // 🔥 MÉTODO CENTRALIZADO (EVITA REPETICIÓN)
    private List<Pedido> mapearPedidosConDetalles(List<QueryDocumentSnapshot> documentos) throws Exception {

        List<Pedido> pedidos = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documentos) {

            Pedido pedido = doc.toObject(Pedido.class);
            pedido.setId(doc.getId());

            List<DetallePedido> detalles = obtenerDetallesPedido(doc.getId());
            pedido.setDetalles(detalles);

            pedidos.add(pedido);
        }

        return pedidos;
    }

    // 🔥 OBTENER DETALLES
    private List<DetallePedido> obtenerDetallesPedido(String pedidoId) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLECCION)
                .document(pedidoId)
                .collection("detalles")
                .get();

        List<DetallePedido> detalles = new ArrayList<>();

        for (QueryDocumentSnapshot doc : future.get().getDocuments()) {

            DetallePedido detalle = doc.toObject(DetallePedido.class);
            detalle.setId(doc.getId());

            detalles.add(detalle);
        }

        return detalles;
    }

    // 🔥 ACTUALIZAR ESTADO
    public void actualizarEstado(String pedidoId, String estado) {

        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLECCION)
                .document(pedidoId)
                .update("estado", estado);
    }

    // 🔥 CONTAR PEDIDOS
    public Map<String, Long> contarPedidosPorEstado() throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        Map<String, Long> conteo = new HashMap<>();

        String[] estados = {"PENDIENTE", "EN_PROCESO", "EN_CAMINO", "ENTREGADO"};

        for (String estado : estados) {

            ApiFuture<QuerySnapshot> future = db.collection(COLECCION)
                    .whereEqualTo("estado", estado)
                    .get();

            conteo.put(estado, (long) future.get().size());
        }

        return conteo;
    }
}