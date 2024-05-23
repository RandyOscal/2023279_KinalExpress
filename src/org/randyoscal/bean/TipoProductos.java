package org.randyoscal.bean;

    

public class TipoProductos {
    
    private int idTipoProducto;
    private String descripcion;

    public TipoProductos() {
    }

    public TipoProductos(int idTipoProducto, String descripcion) {
        this.idTipoProducto = idTipoProducto;
        this.descripcion = descripcion;
    }
    
    
    public int getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(int idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
    
}
