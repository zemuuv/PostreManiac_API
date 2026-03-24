package com.proyecto.postremaniacapi.service;

import com.proyecto.postremaniacapi.model.Producto;
import com.proyecto.postremaniacapi.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public String crearProducto(Producto producto) throws ExecutionException, InterruptedException {

        return productoRepository.crearProducto(producto);
    }

    public List<Producto> obtenerProductos() throws ExecutionException, InterruptedException {

        return productoRepository.obtenerProductos();
    }

    public String actualizarProducto(String id, Producto producto) {

        productoRepository.actualizarProducto(id, producto);

        return "Producto actualizado correctamente";
    }

    public String eliminarProducto(String id) {

        productoRepository.eliminarProducto(id);

        return "Producto eliminado correctamente";
    }
}