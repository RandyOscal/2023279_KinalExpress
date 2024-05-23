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
import org.randyoscal.bean.EmailProveedor;
import org.randyoscal.system.Principal;

public class EmailProveedorController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<EmailProveedor> listaEmailsProveedor;
    @FXML private Button btnRegresar;
    
    @FXML private TextField txtCodigoEmailProveedor;
    @FXML private TextField txtEmailProveedor;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtCodigoProveedor;
    
    @FXML private TableView tblEmailsProveedor;
    
    @FXML private TableColumn colCodigoEmailProveedor;
    @FXML private TableColumn colEmailProveedor;
    @FXML private TableColumn colDescripcion;
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
    
    public void cargarDatos(){
    if (tblEmailsProveedor != null) {
        tblEmailsProveedor.setItems(getEmailsProveedor());
        colCodigoEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoEmailProveedor"));
        colEmailProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("emailProveedor"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<EmailProveedor, String>("descripcion"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<EmailProveedor, Integer>("codigoProveedor"));
    } else {
        System.out.println("tblEmailsProveedor es nulo");
    }
}

    
    public ObservableList<EmailProveedor> getEmailsProveedor(){
        ArrayList<EmailProveedor> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarEmailsProveedor()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new EmailProveedor (
                        resultado.getInt("codigoEmailProveedor"),
                        resultado.getString("emailProveedor"),
                        resultado.getString("descripcion"),
                        resultado.getInt("codigoProveedor")
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaEmailsProveedor = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarDatos(){
        if (tblEmailsProveedor.getSelectionModel().getSelectedItem() != null) {
            txtCodigoEmailProveedor.setText(String.valueOf(((EmailProveedor)tblEmailsProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor()));
            txtEmailProveedor.setText(((EmailProveedor)tblEmailsProveedor.getSelectionModel().getSelectedItem()).getEmailProveedor());
            txtDescripcion.setText(((EmailProveedor)tblEmailsProveedor.getSelectionModel().getSelectedItem()).getDescripcion());
            txtCodigoProveedor.setText(String.valueOf(((EmailProveedor)tblEmailsProveedor.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
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
        EmailProveedor registro = new EmailProveedor();
        registro.setCodigoEmailProveedor(Integer.parseInt(txtCodigoEmailProveedor.getText()));
        registro.setEmailProveedor(txtEmailProveedor.getText());
        registro.setDescripcion(txtDescripcion.getText());
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarEmailProveedor(?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            listaEmailsProveedor.add(registro);
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
                if(tblEmailsProveedor.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Correo de Proveedor", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarEmailProveedorPorCodigo(?)");
                            procedimiento.setInt(1, ((EmailProveedor)tblEmailsProveedor.getSelectionModel().getSelectedItem()).getCodigoEmailProveedor());
                            procedimiento.execute();
                            listaEmailsProveedor.remove(tblEmailsProveedor.getSelectionModel().getSelectedItem());
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
                if (tblEmailsProveedor.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtCodigoEmailProveedor.setDisable(true);
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
                    txtCodigoEmailProveedor.setDisable(false);
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
                txtCodigoEmailProveedor.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarEmailProveedor(?, ?, ?, ?)");
            EmailProveedor registro = (EmailProveedor)tblEmailsProveedor.getSelectionModel().getSelectedItem();
            registro.setEmailProveedor(txtEmailProveedor.getText());
            registro.setDescripcion(txtDescripcion.getText());
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
            procedimiento.setInt(1, registro.getCodigoEmailProveedor());
            procedimiento.setString(2, registro.getEmailProveedor());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setInt(4, registro.getCodigoProveedor());
            procedimiento.execute();
            listaEmailsProveedor.add(registro);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void desactivarControles(){
        txtCodigoEmailProveedor.setEditable(false);
        txtEmailProveedor.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCodigoProveedor.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoEmailProveedor.setEditable(true);
        txtEmailProveedor.setEditable(true);
        txtDescripcion.setEditable(true);
        txtCodigoProveedor.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoEmailProveedor.clear();
        txtEmailProveedor.clear();
        txtDescripcion.clear();
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
