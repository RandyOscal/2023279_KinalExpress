package org.randyoscal.bean;

public class Factura {
    private int numeroFactura;
    private String estado;
    private Double totalFactura;
    private String fechaFactura;
    private int codigoCliente;
    private int codigoEmpleados;

    public Factura() {
    }

    public Factura(int numeroFactura, String estado, Double totalFactura, String fechaFactura, int codigoCliente, int codigoEmpleados) {
        this.numeroFactura = numeroFactura;
        this.estado = estado;
        this.totalFactura = totalFactura;
        this.fechaFactura = fechaFactura;
        this.codigoCliente = codigoCliente;
        this.codigoEmpleados = codigoEmpleados;
    }

    public int getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getEstado() {
        return estado;
    }
  
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(Double totalFactura) {
        this.totalFactura = totalFactura;
    }

    public String getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(String fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public int getCodigoEmpleados() {
        return codigoEmpleados;
    }

    public void setCodigoEmpleados(int codigoEmpleados) {
        this.codigoEmpleados = codigoEmpleados;
    }

    @Override
    public String toString() {
        return "Factura{" + "numeroFactura=" + numeroFactura + 
                ", estado=" + estado + ", totalFactura=" + totalFactura + 
                ", fechaFactura=" + fechaFactura + ", codigoCliente=" 
                + codigoCliente + ", codigoEmpleados=" + codigoEmpleados + '}';
    }
}
