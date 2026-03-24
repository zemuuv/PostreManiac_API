package com.proyecto.postremaniacapi.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.proyecto.postremaniacapi.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    public void guardarUsuario(Usuario usuario) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        db.collection("usuarios").add(usuario);
    }

    public Usuario buscarPorUsername(String username) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        QuerySnapshot query = db.collection("usuarios")
                .whereEqualTo("username", username)
                .get()
                .get();

        if(query.isEmpty()){
            return null;
        }

        return query.getDocuments().get(0).toObject(Usuario.class);
    }
}
