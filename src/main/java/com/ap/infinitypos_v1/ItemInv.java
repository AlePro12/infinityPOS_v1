package com.ap.infinitypos_v1;

import static com.ap.infinitypos_v1.HelloApplication.alertError;

public class ItemInv {
    public String Codigo;
    public String Descrip;
    public Double Costo;
    public Double Precio;
    public Double Util;
    public Double Stock;
    public ItemInv(String Codigo, String Descrip, Double Costo, Double Precio, Double Util, Double Stock) {
        this.Codigo = Codigo;
        this.Descrip = Descrip;
        this.Costo = Costo;
        this.Precio = Precio;
        this.Util = Util;
        this.Stock = Stock;
    }
    //gets
    public String getCodigo() {
        return Codigo;
    }
    public String getDescrip() {
        return Descrip;
    }
    public Double getCosto() {
        return Costo;
    }
    public Double getPrecio() {
        return Precio;
    }
    public Double getUtil() {
        return Util;
    }
    public Double getStock() {
        return Stock;
    }
    //sets
    public void setCodigo(String Codigo) {
        this.Codigo = this.LimpiarString(Codigo);
    }
    public void setDescrip(String Descrip) {
        this.Descrip = this.LimpiarString(Descrip);
    }
    public void setCosto(Double Costo) {
        this.Costo = Costo;
    }
    public void setPrecio(Double Precio) {
        this.Precio = Precio;
    }
    public void setUtil(Double Util) {
        this.Util = Util;
    }
    public void setStock(Double Stock) {
        this.Stock = Stock;
    }
    //get con convert double to string
    public String getCostoStr() {
        return Double.toString(Costo);
    }
    public String getPrecioStr() {
        return Double.toString(Precio);
    }
    public String getUtilStr() {
        return Double.toString(Util);
    }
    public String getStockStr() {
        return Double.toString(Stock);
    }
    //set con convert
    public boolean validarDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public void setCostoStr(String Costo) {
        if (validarDouble(Costo)) {
            this.Costo = Double.parseDouble(Costo);
        }else{
            alertError("ERROR", "Costo no es un numero", "Verifique el costo");
        }
    }
    public void setPrecioStr(String Precio) {
        if (validarDouble(Precio)) {
            this.Precio = Double.parseDouble(Precio);
        }else{
            alertError("ERROR", "Precio no es un numero", "Verifique el precio");
        }
    }
    public void setUtilStr(String Util) {
        if (validarDouble(Util)) {
            this.Util = Double.parseDouble(Util);
        }else{
            alertError("ERROR", "Util no es un numero", "Verifique el util");
        }
    }
    public void setStockStr(String Stock) {
        if (validarDouble(Stock)) {
            this.Stock = Double.parseDouble(Stock);
        }else{
            alertError("ERROR", "Stock no es un numero", "Verifique el stock");
        }
    }

    public String LimpiarString(String str) {
        return str.replaceAll("[^a-zA-Z0-9]", "");
    }

}
