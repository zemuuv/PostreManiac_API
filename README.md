
# 🍰 PostreManiac API

Backend de la aplicación **PostreManiac**, encargado de la gestión de pedidos de usuarios y administración de estados.

---

## 🚀 Tecnologías

* Spring Boot (Java)
* Firebase Firestore
* JWT (Autenticación)

---

## 📌 Descripción

Esta API funciona como capa lógica del sistema, permitiendo:

* Crear pedidos desde la aplicación móvil
* Gestionar estados de pedidos (admin)
* Consultar pedidos según el rol del usuario
* Mantener la seguridad mediante autenticación con JWT

---

## 🔐 Seguridad

* Todas las operaciones requieren autenticación
* El usuario se identifica mediante un token JWT
* La información sensible (userId, username) se obtiene desde el token, no desde el cliente

---

## 🧁 Gestión de pedidos

El sistema maneja:

* Creación de pedidos con múltiples productos
* Cálculo automático del total
* Estados del pedido:

  * PENDIENTE
  * EN_PROCESO
  * EN_CAMINO
  * ENTREGADO

---

## 👥 Roles

* **Usuario**

  * Crear pedidos
  * Ver sus pedidos y estado

* **Administrador**

  * Ver todos los pedidos
  * Cambiar estados
  * Consultar métricas (conteo de pedidos)

---

## 🧱 Estructura del proyecto

```
controller/
service/
repository/
model/
dto/
config/
```

---

## ⚙️ Configuración

* Uso de Firebase Firestore como base de datos
* Configuración mediante archivo de credenciales (no incluido en el repositorio)
* Manejo de seguridad con JWT

---

## 🧠 Notas

* La API está diseñada para ser consumida únicamente por la aplicación del proyecto
* No está pensada como servicio público
* Se prioriza la seguridad y control de acceso por roles

---

## 👨‍💻 Autor

Samuel Vargas 🚀
