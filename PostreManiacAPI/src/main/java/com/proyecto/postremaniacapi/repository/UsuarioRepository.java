package com.proyecto.postremaniacapi.repository;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.proyecto.postremaniacapi.model.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    // 🔥 GUARDAR USUARIO CON ID PERSONALIZADO
    public void guardarUsuario(Usuario usuario) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        db.collection("usuarios")
                .document(usuario.getId()) // 🔥 usamos el ID generado
                .set(usuario);
    }

    // 🔍 BUSCAR POR USERNAME
    public Usuario buscarPorUsername(String username) throws Exception {

        Firestore db = FirestoreClient.getFirestore();

        QuerySnapshot query = db.collection("usuarios")
                .whereEqualTo("username", username)
                .get()
                .get();

        if(query.isEmpty()){
            return null;
        }

        Usuario user = query.getDocuments().get(0).toObject(Usuario.class);

        // 🔥 IMPORTANTE: recuperar el ID del documento
        user.setId(query.getDocuments().get(0).getId());

        return user;
    }
}