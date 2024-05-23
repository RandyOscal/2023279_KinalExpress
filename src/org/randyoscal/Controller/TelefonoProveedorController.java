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
import org.randyoscal.bean.TelefonoProveedor;
import org.randyoscal.system.Principal;

public class TelefonoProveedorController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<TelefonoProveedor> listaTelefonosProveedor;
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoTelefonoProveedor;
    @FXML private TextField txtNumeroPrincipal;
    @FXML private TextField txtNumeroSecundario;
    @FXML private TextField txtObservaciones;
    @FXML private TextField txtCodigoProveedor;
    
    @FXML private TableView tblTelefonosProveedor;
    
    @FXML private TableColumn colCodigoTelefonoProveedor;
    @FXML private TableColumn colNumeroPrincipal;
    @FXML private TableColumn colNumeroSecundario;
    @FXML private TableColumn colObservaciones;
    @FXML private TableColumn colCodigoProveedor;
    
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
    
    public  void cargarDatos(){
        tblTelefonosProveedor.setItems(getTelefonosProveedor());
        colCodigoTelefonoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoTelefonoProveedor"));
        colNumeroPrincipal.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroPrincipal"));
        colNumeroSecundario.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("numeroSecundario"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, String>("observaciones"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<TelefonoProveedor, Integer>("codigoProveedor"));
    }
    
    public ObservableList<TelefonoProveedor> getTelefonosProveedor(){
        ArrayList<TelefonoProveedor> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTelefonosProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TelefonoProveedor (
                        resultado.getInt("codigoTelefonoProveedor"),
                        resultado.getString("numeroPrincipal"),
                        resultado.getString("numeroSecundario"),
                        resultado.getString("observaciones"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTelefonosProveedor = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarDatos(){
        txtCodigoTelefonoProveedor.setText(String.valueOf(((TelefonoProveedor)tblTelefonosProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor()));
        txtNumeroPrincipal.setText(((TelefonoProveedor)tblTelefonosProveedor.getSelectionModel().getSelectedItem()).getNumeroPrincipal());
        txtNumeroSecundario.setText(((TelefonoProveedor)tblTelefonosProveedor.getSelectionModel().getSelectedItem()).getNumeroSecundario());
        txtObservaciones.setText(((TelefonoProveedor)tblTelefonosProveedor.getSelectionModel().getSelectedItem()).getObservaciones());
        txtCodigoProveedor.setText(String.valueOf(((TelefonoProveedor)tblTelefonosProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
        TelefonoProveedor registro = new TelefonoProveedor();
        registro.setCodigoTelefonoProveedor(Integer.parseInt(txtCodigoTelefonoProveedor.getText()));
        registro.setNumeroPrincipal(txtNumeroPrincipal.getText());
        registro.setNumeroSecundario(txtNumeroSecundario.getText());
        registro.setObservaciones(txtObservaciones.getText());
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarTelefonoProveedor(?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listaTelefonosProveedor.add(registro);
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
                if(tblTelefonosProveedor.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Teléfono de Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarTelefonoProveedorPorCodigo(?)");
                            procedimiento.setInt(1, ((TelefonoProveedor)tblTelefonosProveedor.getSelectionModel().getSelectedItem()).getCodigoTelefonoProveedor());
                            procedimiento.execute();
                            listaTelefonosProveedor.remove(tblTelefonosProveedor.getSelectionModel().getSelectedItem());
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
                if (tblTelefonosProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtCodigoTelefonoProveedor.setDisable(true);
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
                    txtCodigoTelefonoProveedor.setDisable(false);
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
                txtCodigoTelefonoProveedor.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarTelefonoProveedor(?, ?, ?, ?, ?)");
            TelefonoProveedor registro = (TelefonoProveedor)tblTelefonosProveedor.getSelectionModel().getSelectedItem();
            registro.setNumeroPrincipal(txtNumeroPrincipal.getText());
            registro.setNumeroSecundario(txtNumeroSecundario.getText());
            registro.setObservaciones(txtObservaciones.getText());
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
            procedimiento.setInt(1, registro.getCodigoTelefonoProveedor());
            procedimiento.setString(2, registro.getNumeroPrincipal());
            procedimiento.setString(3, registro.getNumeroSecundario());
            procedimiento.setString(4, registro.getObservaciones());
            procedimiento.setInt(5, registro.getCodigoProveedor());
            procedimiento.execute();
            listaTelefonosProveedor.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoTelefonoProveedor.setEditable(false);
        txtNumeroPrincipal.setEditable(false);
        txtNumeroSecundario.setEditable(false);
        txtObservaciones.setEditable(false);
        txtCodigoProveedor.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoTelefonoProveedor.setEditable(true);
        txtNumeroPrincipal.setEditable(true);
        txtNumeroSecundario.setEditable(true);
        txtObservaciones.setEditable(true);
        txtCodigoProveedor.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoTelefonoProveedor.clear();
        txtNumeroPrincipal.clear();
        txtNumeroSecundario.clear();
        txtObservaciones.clear();
        txtCodigoProveedor.clear();
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
