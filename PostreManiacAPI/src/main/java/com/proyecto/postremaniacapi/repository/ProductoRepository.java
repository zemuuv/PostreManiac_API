package com.proyecto.postremaniacapi.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.proyecto.postremaniacapi.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class ProductoRepository {

    private static final String COLECCION = "productos";

    public String crearProducto(Producto producto) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<DocumentReference> future = db.collection(COLECCION).add(producto);

        return future.get().getId();
    }

    public List<Producto> obtenerProductos() throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();

        ApiFuture<QuerySnapshot> future = db.collection(COLECCION).get();

        List<QueryDocumentSnapshot> documentos = future.get().getDocuments();

        List<Producto> productos = new ArrayList<>();

        for (QueryDocumentSnapshot doc : documentos) {

            Producto producto = doc.toObject(Producto.class);
            producto.setImagen(doc.getId());

            productos.add(producto);
        }

        return productos;
    }

    public void actualizarProducto(String id, Producto producto) {

        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLECCION).document(id).set(producto);
    }

    public void eliminarProducto(String id) {

        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLECCION).document(id).delete();
    }
}