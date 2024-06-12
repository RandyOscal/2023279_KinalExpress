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
import org.randyoscal.bean.DetalleFactura;
import org.randyoscal.system.Principal;

public class DetalleFacturaController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones {AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleFactura> listaDetallesCompra;
    @FXML private Button btnRegresar;

    @FXML private TextField txtCodDetalleFactura;
    @FXML private TextField txtprecioU;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtnumFactura;
    @FXML private TextField txtcodProducto;

    @FXML private TableView tblDetallesFactura;

    @FXML private TableColumn colCodigoDetalleFactura;
    @FXML private TableColumn colPrecioU;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colnumFactura;
    @FXML private TableColumn colcodProducto;

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
        if (tblDetallesFactura != null) {
            tblDetallesFactura.setItems(getDetallesFactura());
            colCodigoDetalleFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
            colPrecioU.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
            colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
            colnumFactura.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("numeroFactura"));
            colcodProducto.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("codigoProducto"));
        } else {
            System.out.println("tblDetallesFactura es nulo");
        }
    }

    public ObservableList<DetalleFactura> getDetallesFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<DetalleFactura>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarDetallesFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()) {
                lista.add(new DetalleFactura (
                        resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroFactura"),
                        resultado.getString("codigoProducto")
                ));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return listaDetallesCompra = FXCollections.observableArrayList(lista);
    }

    public void seleccionarDatos() {
        if (tblDetallesFactura.getSelectionModel().getSelectedItem() != null) {
            DetalleFactura detalleSeleccionado = (DetalleFactura) tblDetallesFactura.getSelectionModel().getSelectedItem();
            txtCodDetalleFactura.setText(String.valueOf(detalleSeleccionado.getCodigoDetalleFactura()));
            txtprecioU.setText(String.valueOf(detalleSeleccionado.getPrecioUnitario()));
            txtCantidad.setText(String.valueOf(detalleSeleccionado.getCantidad()));
            txtnumFactura.setText(String.valueOf(detalleSeleccionado.getNumeroFactura()));
            txtcodProducto.setText(String.valueOf(detalleSeleccionado.getCodigoProducto()));
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
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodDetalleFactura.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtprecioU.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
        registro.setNumeroFactura(Integer.parseInt(txtnumFactura.getText()));
        registro.setCodigoProducto(txtcodProducto.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarDetalleFactura(?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
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
                if(tblDetallesFactura.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Detalle de Compra", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarDetalleCompraPorCodigo(?)");
                            procedimiento.setInt(1, ((DetalleFactura)tblDetallesFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura());
                            procedimiento.execute();
                            listaDetallesCompra.remove(tblDetallesFactura.getSelectionModel().getSelectedItem());
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
                if (tblDetallesFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtCodDetalleFactura.setDisable(true);
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
                    txtCodDetalleFactura.setDisable(false);
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
                txtCodDetalleFactura.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarDetalleCompra(?, ?, ?, ?, ?)");
            DetalleFactura registro = (DetalleFactura)tblDetallesFactura.getSelectionModel().getSelectedItem();
            registro.setPrecioUnitario(Double.parseDouble(txtprecioU.getText()));
            registro.setCantidad(Integer.parseInt(txtCantidad.getText()));
            registro.setNumeroFactura(Integer.parseInt(txtnumFactura.getText()));
            registro.setCodigoProducto(txtcodProducto.getText());
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();
            listaDetallesCompra.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void desactivarControles(){
        txtCodDetalleFactura.setEditable(false);
        txtprecioU.setEditable(false);
        txtCantidad.setEditable(false);
        txtnumFactura.setEditable(false);
        txtcodProducto.setEditable(false);
    }

    public void activarControles(){
        txtCodDetalleFactura.setEditable(true);
        txtprecioU.setEditable(true);
        txtCantidad.setEditable(true);
        txtnumFactura.setEditable(true);
        txtcodProducto.setEditable(true);
    }

    public void limpiarControles(){
        txtCodDetalleFactura.clear();
        txtprecioU.clear();
        txtCantidad.clear();
        txtnumFactura.clear();
        txtcodProducto.clear();
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