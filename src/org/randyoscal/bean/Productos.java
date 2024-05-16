
package org.randyoscal.bean;

    

public class Productos {
    
    private int idTipoProducto;
    private String descripcion;

    public Productos() {
    }

    public Productos(int idTipoProducto, String descripcion) {
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
