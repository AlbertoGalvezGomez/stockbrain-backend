package com.stockbrain.modelo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private Double ventasHoy;
    private Long totalProductos;
    private Long stockBajo;
    private List<VentaDiaDTO> ventas7Dias;
    private List<ProductoVendidoDTO> top5Productos;
}
