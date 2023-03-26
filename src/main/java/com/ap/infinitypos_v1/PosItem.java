package com.ap.infinitypos_v1;

public class PosItem {
    public  String codigo;
    public  String descrip;
    public  double precio;
    public  int cantidad;
    public double total;


    public PosItem(String codigo, String descrip, double precio, int cantidad) {
        this.codigo = codigo;
        this.descrip = descrip;
        this.precio = precio;
        this.cantidad = cantidad;

        this.total = precio * cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescrip() {
        return descrip;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return precio * cantidad;
    }


}
