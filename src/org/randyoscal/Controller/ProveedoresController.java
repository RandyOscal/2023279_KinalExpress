
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
import org.randyoscal.bean.Proveedores;
import org.randyoscal.system.Principal;

public class ProveedoresController implements Initializable{
    private Principal escenarioPrincipal;

    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Proveedores> listaClientes;
    @FXML private Button btnRegresar;
  //  @FXML MenuItem btnMenuClientes
    
    @FXML private TextField txtCodigoP;
    @FXML private TextField txtNitP;
    @FXML private TextField txtNombreP;
    @FXML private TextField txtApellidoP;
    @FXML private TextField txtDireccionP;
    @FXML private TextField txtRazonS;
    @FXML private TextField txtContactoP;
    @FXML private TextField txtpaginaW;
    
    @FXML private TableView tblProveedores;
    
    @FXML private TableColumn colCodigoP;
    @FXML private TableColumn colNitP;
    @FXML private TableColumn colNombreP;
    @FXML private TableColumn colApellidoP;
    @FXML private TableColumn colDireccionP;
    @FXML private TableColumn colRazonS;
    @FXML private TableColumn colContactoP;
    @FXML private TableColumn colpaginaW;
        
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
    }
    
    public  void cargarDatos(){
        tblProveedores.setItems(getProveedores());
        colCodigoP.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("NITproveedor"));
        colNombreP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidoProveedor"));
        colDireccionP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonS.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoP.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colpaginaW.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }
    
    public void selccionarDatos(){
    txtCodigoP.setText(String.valueOf(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
    txtNitP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNITproveedor());
    txtNombreP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor());
    txtApellidoP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getApellidoProveedor());
    txtDireccionP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor());
    txtRazonS.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial());
    txtContactoP.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal());
    txtpaginaW.setText(((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb());
        
}
    
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProveedores()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedores (resultado.getInt("codigoProveedor"),
                                        resultado.getString("NITproveedor"),
                                        resultado.getString("nombreProveedor"),
                                        resultado.getString("apellidoProveedor"),
                                        resultado.getString("direccionProveedor"),
                                        resultado.getString("razonSocial"),
                                        resultado.getString("contactoPrincipal"),                                       
                                        resultado.getString("paginaWeb")                                       
                
                ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
                
        return listaClientes = FXCollections.observableArrayList(lista);
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
                // Agregar imagenes nuevas para guardar y cancelar
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
        Proveedores registro = new Proveedores();
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoP.getText()));
        registro.setNITproveedor(txtNitP.getText());
        registro.setNombreProveedor(txtNombreP.getText());
        registro.setApellidoProveedor(txtApellidoP.getText());
        registro.setDireccionProveedor(txtDireccionP.getText());
        registro.setRazonSocial(txtRazonS.getText());
        registro.setContactoPrincipal(txtContactoP.getText());
        registro.setPaginaWeb(txtpaginaW.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarProveedores(?, ?, ?, ?, ?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());            
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            
            procedimiento.execute();
            listaClientes.add(registro);
            
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
                if(tblProveedores.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarProveedor(?)");
                            procedimiento.setInt(1, ((Proveedores)tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
                            procedimiento.execute();
                            listaClientes.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        
                    }
                }else
                    JOptionPane.showMessageDialog(null, "Debe de selccionar un elemento. ");
                
        }
        
        
    }
    
    public void editar(){
        
        switch(tipoDeOperaciones){
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtCodigoP.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                }else
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
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
                    txtCodigoP.setDisable(false);
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
                txtCodigoP.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarProveedores(?, ?, ?, ?, ?, ?, ?, ?)");
            Proveedores registro = (Proveedores)tblProveedores.getSelectionModel().getSelectedItem();
            registro.setNITproveedor(txtNitP.getText());
            registro.setNombreProveedor(txtNombreP.getText());
            registro.setApellidoProveedor(txtApellidoP.getText());
            registro.setDireccionProveedor(txtDireccionP.getText());
            registro.setRazonSocial(txtRazonS.getText());
            registro.setContactoPrincipal(txtContactoP.getText());
            registro.setPaginaWeb(txtpaginaW.getText());
            procedimiento.setInt(1, registro.getCodigoProveedor());
            procedimiento.setString(2, registro.getNITproveedor());
            procedimiento.setString(3, registro.getNombreProveedor());
            procedimiento.setString(4, registro.getApellidoProveedor());
            procedimiento.setString(5, registro.getDireccionProveedor());
            procedimiento.setString(6, registro.getRazonSocial());
            procedimiento.setString(7, registro.getContactoPrincipal());
            procedimiento.setString(8, registro.getPaginaWeb());
            
            procedimiento.execute();
            listaClientes.add(registro);

            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void desactivarControles(){
        txtCodigoP.setEditable(false);
        txtNitP.setEditable(false);
        txtNombreP.setEditable(false);
        txtApellidoP.setEditable(false);
        txtDireccionP.setEditable(false);
        txtRazonS.setEditable(false);
        txtContactoP.setEditable(false);
        txtpaginaW.setEditable(false);
    }
    
    
    public void activarControles(){
        txtCodigoP.setEditable(true);
        txtNitP.setEditable(true);
        txtNombreP.setEditable(true);
        txtApellidoP.setEditable(true);
        txtDireccionP.setEditable(true);
        txtRazonS.setEditable(true);
        txtContactoP.setEditable(true);
        txtpaginaW.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoP.clear();
        txtNitP.clear();
        txtNombreP.clear();
        txtApellidoP.clear();
        txtDireccionP.clear();
        txtRazonS.clear();
        txtContactoP.clear();
        txtpaginaW.clear();
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
