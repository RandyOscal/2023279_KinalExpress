package org.randyoscal.bean;

public class Empleado {
    private int codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private double sueldo;
    private String direccion;
    private String turno;
    private int idCargoEmpleado;

    public Empleado() {
    }

    public Empleado(int codigoEmpleado, String nombreEmpleado, String apellidoEmpleado, double sueldo, String direccion, String turno, int idCargoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.sueldo = sueldo;
        this.direccion = direccion;
        this.turno = turno;
        this.idCargoEmpleado = idCargoEmpleado;
    }

    // Getters y Setters

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public int getIdCargoEmpleado() {
        return idCargoEmpleado;
    }

    public void setIdCargoEmpleado(int idCargoEmpleado) {
        this.idCargoEmpleado = idCargoEmpleado;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "codigoEmpleado=" + codigoEmpleado +
                ", nombreEmpleado='" + nombreEmpleado + '\'' +
                ", apellidoEmpleado='" + apellidoEmpleado + '\'' +
                ", sueldo=" + sueldo +
                ", direccion='" + direccion + '\'' +
                ", turno='" + turno + '\'' +
                ", idCargoEmpleado=" + idCargoEmpleado +
                '}';
    }
}
