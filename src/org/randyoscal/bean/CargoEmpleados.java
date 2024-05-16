
package org.randyoscal.bean;

public class CargoEmpleados {
    private int idCargoEmpleado;
    private String nombreCargo;
    private String descripcionCargo;

    public CargoEmpleados() {
    }

    public CargoEmpleados(int idCargoEmpleado, String nombreCargo, String descripcionCargo) {
        this.idCargoEmpleado = idCargoEmpleado;
        this.nombreCargo = nombreCargo;
        this.descripcionCargo = descripcionCargo;
    }

    public int getIdCargoEmpleado() {
        return idCargoEmpleado;
    }

    public void setIdCargoEmpleado(int idCargoEmpleado) {
        this.idCargoEmpleado = idCargoEmpleado;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }
    
    
}
