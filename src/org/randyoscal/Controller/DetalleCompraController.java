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
import org.randyoscal.bean.DetalleCompra;
import org.randyoscal.system.Principal;

public class DetalleCompraController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listaDetallesCompra;
    @FXML private Button btnRegresar;

    @FXML private TextField txtCodigoDetalleCompra;
    @FXML private TextField txtCostoUnitario;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtNumeroDocumento;

    @FXML private TableView tblDetallesCompra;

    @FXML private TableColumn colCodigoDetalleCompra;
    @FXML private TableColumn colCostoUnitario;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colNumeroDocumento;

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
        if (tblDetallesCompra != null) {
            tblDetallesCompra.setItems(getDetallesCompra());
            colCodigoDetalleCompra.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
            colCostoUnitario.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
            colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
            colCodigoProducto.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
            colNumeroDocumento.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
        } else {
            System.out.println("tblDetallesCompra es nulo");
        }
    }

    public ObservableList<DetalleCompra> getDetallesCompra() {
        ArrayList<DetalleCompra> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetallesCompra()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()) {
                lista.add(new DetalleCompra (
                        resultado.getInt("codigoDetalleCompra"),
                        resultado.getDouble("costoUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getString("codigoProducto"),
                        resultado.getInt("numeroDocumento")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listaDetallesCompra = FXCollections.observableArrayList(lista);
    }

    public void seleccionarDatos() {
        if (tblDetallesCompra.getSelectionModel().getSelectedItem() != null) {
            DetalleCompra detalleSeleccionado = (DetalleCompra) tblDetallesCompra.getSelectionModel().getSelectedItem();
            txtCodigoDetalleCompra.setText(String.valueOf(detalleSeleccionado.getCodigoDetalleCompra()));
            txtCostoUnitario.setText(String.valueOf(detalleSeleccionado.getCostoUnitario()));
            txtCantidad.setText(String.valueOf(detalleSeleccionado.getCantidad()));
            txtCodigoProducto.setText(detalleSeleccionado.getCodigoProducto());
            txtNumeroDocumento.setText(String.valueOf(detalleSeleccionado.getNumeroDocumento()));
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
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDetalleCompra.getText()));
        registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setCodigoProducto(txtCodigoProducto.getText());
        registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarDetalleCompra(?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
            listaDetallesCompra.add(registro);
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
                if(tblDetallesCompra.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Detalle de Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarDetalleCompraPorCodigo(?)");
                            procedimiento.setInt(1, ((DetalleCompra)tblDetallesCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra());
                            procedimiento.execute();
                            listaDetallesCompra.remove(tblDetallesCompra.getSelectionModel().getSelectedItem());
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
                if (tblDetallesCompra.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtCodigoDetalleCompra.setDisable(true);
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
                    txtCodigoDetalleCompra.setDisable(false);
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
                txtCodigoDetalleCompra.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarDetalleCompra(?, ?, ?, ?, ?)");
            DetalleCompra registro = (DetalleCompra)tblDetallesCompra.getSelectionModel().getSelectedItem();
            registro.setCostoUnitario(Double.parseDouble(txtCostoUnitario.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setCodigoProducto(txtCodigoProducto.getText());
            registro.setNumeroDocumento(Integer.parseInt(txtNumeroDocumento.getText()));
            procedimiento.setInt(1, registro.getCodigoDetalleCompra());
            procedimiento.setDouble(2, registro.getCostoUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setString(4, registro.getCodigoProducto());
            procedimiento.setInt(5, registro.getNumeroDocumento());
            procedimiento.execute();
            listaDetallesCompra.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void desactivarControles(){
        txtCodigoDetalleCompra.setEditable(false);
        txtCostoUnitario.setEditable(false);
        txtCantidad.setEditable(false);
        txtCodigoProducto.setEditable(false);
        txtNumeroDocumento.setEditable(false);
    }

    public void activarControles(){
        txtCodigoDetalleCompra.setEditable(true);
        txtCostoUnitario.setEditable(true);
        txtCantidad.setEditable(true);
        txtCodigoProducto.setEditable(true);
        txtNumeroDocumento.setEditable(true);
    }

    public void limpiarControles(){
        txtCodigoDetalleCompra.clear();
        txtCostoUnitario.clear();
        txtCantidad.clear();
        txtCodigoProducto.clear();
        txtNumeroDocumento.clear();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML 
    public void regresar (ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
