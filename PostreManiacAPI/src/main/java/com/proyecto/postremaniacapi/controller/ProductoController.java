package com.proyecto.postremaniacapi.controller;

import com.proyecto.postremaniacapi.model.Producto;
import com.proyecto.postremaniacapi.service.ProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    public String crearProducto(@RequestBody Producto producto) throws ExecutionException, InterruptedException {
        return productoService.crearProducto(producto);
    }

    @GetMapping
    public List<Producto> obtenerProductos() throws ExecutionException, InterruptedException {
        return productoService.obtenerProductos();
    }

    @PutMapping("/{id}")
    public String actualizarProducto(@PathVariable String id, @RequestBody Producto producto) {
        return productoService.actualizarProducto(id, producto);
    }

    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable String id) {
        return productoService.eliminarProducto(id);
    }
}