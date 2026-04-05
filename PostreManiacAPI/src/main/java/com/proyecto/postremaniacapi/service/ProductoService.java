package com.proyecto.postremaniacapi.service;

import com.proyecto.postremaniacapi.model.Producto;
import com.proyecto.postremaniacapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // 🔥 CREAR PRODUCTO CON ID
    public String crearProducto(Producto producto) throws ExecutionException, InterruptedException {

        // ✅ Generar ID único
        String id = UUID.randomUUID().toString();

        // ✅ Asignar ID al producto
        producto.setId(id);

        // 🔥 Guardar en repository
        return productoRepository.crearProducto(producto);
    }

    // 📦 OBTENER PRODUCTOS
    public List<Producto> obtenerProductos() throws ExecutionException, InterruptedException {
        return productoRepository.obtenerProductos();
    }

    // ✏️ ACTUALIZAR
    public String actualizarProducto(String id, Producto producto) {

        // 🔥 aseguramos que el objeto tenga el mismo ID
        producto.setId(id);

        productoRepository.actualizarProducto(id, producto);

        return "Producto actualizado correctamente";
    }

    // ❌ ELIMINAR
    public String eliminarProducto(String id) {

        productoRepository.eliminarProducto(id);

        return "Producto eliminado correctamente";
    }
}