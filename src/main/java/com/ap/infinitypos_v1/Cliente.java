package com.ap.infinitypos_v1;

public class Cliente {
    //NOMBRE, RIF y DIRECCION
    public String nombre;
    public String rif;
    public String direccion;

    public Cliente(String nombre, String rif, String direccion) {
        this.nombre = nombre;
        this.rif = rif;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRif() {
        return rif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
