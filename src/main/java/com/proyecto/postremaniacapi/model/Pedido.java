package com.proyecto.postremaniacapi.model;

import java.util.Date;
import java.util.List;

public class Pedido {

    private String id;
    private String userId;
    private String username;
    private Date fecha;
    private String estado;
    private double total;
    private List<DetallePedido> detalles;

    public Pedido() {}


    public Pedido(String id, String userId, String username, Date fecha, String estado, double total) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; } // 🔥 NUEVO
    public void setUsername(String username) { this.username = username; } // 🔥 NUEVO

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}