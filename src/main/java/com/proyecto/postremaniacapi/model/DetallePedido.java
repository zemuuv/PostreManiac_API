package com.proyecto.postremaniacapi.model;

public class DetallePedido {

    private String id;
    private String productoId;
    private String nombreProducto; // 🔥 NUEVO
    private int cantidad;
    private double precioUnitario;

    public DetallePedido() {}


    public DetallePedido(String id, String productoId, String nombreProducto, int cantidad, double precioUnitario) {
        this.id = id;
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductoId() { return productoId; }
    public void setProductoId(String productoId) { this.productoId = productoId; }

    public String getNombreProducto() { return nombreProducto; } // 🔥 NUEVO
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; } // 🔥 NUEVO

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}