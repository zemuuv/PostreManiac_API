package com.proyecto.postremaniacapi.dto;

public class ComentarioRequest {

    private String productoId;
    private String contenido;

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
}