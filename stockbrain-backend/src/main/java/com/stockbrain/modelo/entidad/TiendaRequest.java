package com.stockbrain.modelo.entidad;

public class TiendaRequest {

    private String nombre;
    private String ubicacion;
    private Long administradorId;

    public TiendaRequest() {}

    public TiendaRequest(String nombre, String ubicacion, Long administradorId) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.administradorId = administradorId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Long getAdministradorId() {
        return administradorId;
    }

    public void setAdministradorId(Long administradorId) {
        this.administradorId = administradorId;
    }
}