package com.proyecto.postremaniacapi.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.proyecto.postremaniacapi.model.Comentario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ComentarioRepository {

    private static final String COLLECTION = "comentarios";

    // 🔥 GUARDAR
    public void guardarComentario(Comentario comentario) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        String id = UUID.randomUUID().toString();
        comentario.setId(id);

        db.collection(COLLECTION).document(id).set(comentario);
    }

    // 📥 OBTENER TODOS
    public List<Comentario> obtenerTodos() throws Exception {

        Firestore db = FirestoreClient.getFirestore();
        List<Comentario> lista = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot doc : documents) {
            Comentario c = doc.toObject(Comentario.class);
            lista.add(c);
        }

        return lista;
    }

    // 🍰 🔥 NUEVO → FILTRAR POR PRODUCTO
    public List<Comentario> obtenerPorProducto(String productoId) throws Exception {

        Firestore db = FirestoreClient.getFirestore();
        List<Comentario> lista = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION)
                .whereEqualTo("productoId", productoId)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        for (QueryDocumentSnapshot doc : documents) {
            Comentario c = doc.toObject(Comentario.class);
            lista.add(c);
        }

        return lista;
    }

    // 🔍 BUSCAR POR ID
    public Comentario buscarPorId(String id) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        DocumentSnapshot doc = db.collection(COLLECTION).document(id).get().get();

        if (doc.exists()) {
            return doc.toObject(Comentario.class);
        }

        return null;
    }

    // ❌ ELIMINAR
    public void eliminarComentario(String id) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        db.collection(COLLECTION).document(id).delete();
    }
}