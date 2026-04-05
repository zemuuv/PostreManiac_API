package com.proyecto.postremaniacapi.dto;

import java.util.List;

public class PedidoDTO {

    public String id;
    public String userId;
    public String username;
    public double total;
    public String estado;
    public String fecha;
    public List<DetalleDTO> detalles;
}