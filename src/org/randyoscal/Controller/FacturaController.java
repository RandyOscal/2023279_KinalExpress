package org.randyoscal.Controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.randyoscal.DB.Conexion;
import org.randyoscal.bean.Factura;
import org.randyoscal.system.Principal;

public class FacturaController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Factura> listaFacturas;
    
    @FXML private Button btnRegresar;
    

    @FXML private TextField txtnumFactura;
    @FXML private TextField txtEstado;
    @FXML private TextField txtTotFactura;
    @FXML private TextField txtFeFactura;
    @FXML private TextField txtCodCliente;
    @FXML private TextField txtCodEmpleados;

    @FXML private TableView tblFactura;

    @FXML private TableColumn colnumFactura;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colTotFactura;
    @FXML private TableColumn colFeFactura;
    @FXML private TableColumn colCodCliente;
    @FXML private TableColumn colCodEmpleados;
    

    @FXML private Button btnAgregar;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;

    @FXML private ImageView imgAgregar;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        desactivarControles();
    }

    public void cargarDatos() {
            tblFactura.setItems(getFactura());
            colnumFactura.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroFactura"));
            colEstado.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
            colTotFactura.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
            colFeFactura.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
            colCodCliente.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
            colCodEmpleados.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleados"));
    }

    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()) {
                lista.add(new Factura (
                        resultado.getInt("numeroFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleados")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listaFacturas = FXCollections.observableArrayList(lista);
    }

    public void seleccionarDatos() {
        if (tblFactura.getSelectionModel().getSelectedItem() != null) {
            Factura detalleSeleccionado = (Factura) tblFactura.getSelectionModel().getSelectedItem();
            txtnumFactura.setText(String.valueOf(detalleSeleccionado.getNumeroFactura()));
            txtEstado.setText(String.valueOf(detalleSeleccionado.getEstado()));
            txtTotFactura.setText(String.valueOf(detalleSeleccionado.getTotalFactura()));
            txtFeFactura.setText(String.valueOf(detalleSeleccionado.getFechaFactura()));
            txtCodCliente.setText(String.valueOf(detalleSeleccionado.getCodigoCliente()));
            txtCodEmpleados.setText(String.valueOf(detalleSeleccionado.getCodigoEmpleados()));
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento de la tabla.");
        }
    }

    public void agregar(){
        switch (tipoDeOperaciones){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                imgEliminar.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/randyoscal/Image/Agregar.png"));
                imgEliminar.setImage(new Image("/org/randyoscal/Image/Eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    public void guardar(){
        Factura registro = new Factura();
        registro.setNumeroFactura(Integer.parseInt(txtnumFactura.getText()));
        registro.setEstado(txtEstado.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotFactura.getText()));
        registro.setFechaFactura(txtFeFactura.getText());
        registro.setCodigoCliente(Integer.parseInt(txtCodCliente.getText()));
        registro.setCodigoEmpleados(Integer.parseInt(txtCodEmpleados.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarFactura(?, ?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleados());
            procedimiento.execute();
            listaFacturas.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void eliminar(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/randyoscal/Image/Agregar.png"));
                imgEliminar.setImage(new Image("/org/randyoscal/Image/Eliminar.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if(tblFactura.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Detalle de Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarDetalleCompraPorCodigo(?)");
                            procedimiento.setInt(1, ((Factura)tblFactura.getSelectionModel().getSelectedItem()).getNumeroFactura());
                            procedimiento.execute();
                            listaFacturas.remove(tblFactura.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un elemento.");
        }
    }

    public void editar(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtnumFactura.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algún elemento");
                break;
            case ACTUALIZAR:
                actualizar();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnAgregar.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                    desactivarControles();
                    txtnumFactura.setDisable(false);
                    tipoDeOperaciones = operaciones.NINGUNO;
                    limpiarControles();
                    cargarDatos();
        }
    }

    public void reporte(){
        switch(tipoDeOperaciones){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                txtnumFactura.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarDetalleCompra(?, ?, ?, ?, ?, ?)");
            Factura registro = (Factura)tblFactura.getSelectionModel().getSelectedItem();
            registro.setEstado(txtEstado.getText());
            registro.setTotalFactura(Double.parseDouble(txtTotFactura.getText()));
            registro.setFechaFactura(txtFeFactura.getText());
            registro.setCodigoCliente(Integer.parseInt(txtCodCliente.getText()));
            registro.setCodigoEmpleados(Integer.parseInt(txtCodEmpleados.getText()));
            procedimiento.setInt(1, registro.getNumeroFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleados());
            procedimiento.execute();
            listaFacturas.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtnumFactura.setEditable(false);
        txtEstado.setEditable(false);
        txtTotFactura.setEditable(false);
        txtFeFactura.setEditable(false);
        txtCodCliente.setEditable(false);
        txtCodEmpleados.setEditable(false);
    }

    public void activarControles(){
        txtnumFactura.setEditable(true);
        txtEstado.setEditable(true);
        txtTotFactura.setEditable(true);
        txtFeFactura.setEditable(true);
        txtCodCliente.setEditable(true);
        txtCodEmpleados.setEditable(true);
    }

    public void limpiarControles(){
        txtnumFactura.clear();
        txtEstado.clear();
        txtTotFactura.clear();
        txtFeFactura.clear();
        txtCodCliente.clear();
        txtCodEmpleados.clear();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML 
    public void regresar (ActionEvent event){
        if (event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
