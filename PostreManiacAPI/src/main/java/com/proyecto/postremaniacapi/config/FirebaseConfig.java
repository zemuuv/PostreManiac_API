package com.proyecto.postremaniacapi.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @PostConstruct
    public void initialize() {

        try {


            String firebaseCredentials = System.getenv("FIREBASE_CREDENTIALS");

            if (firebaseCredentials == null || firebaseCredentials.isEmpty()) {
                throw new RuntimeException("FIREBASE_CREDENTIALS no está configurada");
            }


            InputStream serviceAccount = new ByteArrayInputStream(
                    firebaseCredentials.getBytes(StandardCharsets.UTF_8)
            );

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();


            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("🔥 Firebase inicializado correctamente");
            }

        } catch (Exception e) {
            System.err.println("❌ Error al inicializar Firebase:");
            e.printStackTrace();
        }
    }
}