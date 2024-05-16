
package org.randyoscal.Controller;

import java.net.URL;
import java.sql.Date;
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
import org.randyoscal.bean.CompraVentas;
import org.randyoscal.system.Principal;

public class CompraVentaController implements Initializable{
 private Principal escenarioPrincipal;

    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<CompraVentas> listaClientes;
    @FXML private Button btnRegresar;
  //  @FXML MenuItem btnMenuClientes
    
    @FXML private TextField txtnumeroDoc;
    @FXML private TextField txtfechaDoc;
    @FXML private TextField txtdescripcion;
    @FXML private TextField txttotalDoc;
    
    
    @FXML private TableView tblComprasV;
    
    @FXML private TableColumn colnumeroDoc;
    @FXML private TableColumn colfechaDoc;
    @FXML private TableColumn coldescripcion;
    @FXML private TableColumn coltotalDoc;
    
        
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
        tblComprasV.setItems(getCompraVentas());
        colnumeroDoc.setCellValueFactory(new PropertyValueFactory<CompraVentas, Integer>("numeroDocumento"));
        colfechaDoc.setCellValueFactory(new PropertyValueFactory<CompraVentas, String>("fechaDocumento"));
        coldescripcion.setCellValueFactory(new PropertyValueFactory<CompraVentas, String>("descripcion"));
        coltotalDoc.setCellValueFactory(new PropertyValueFactory<CompraVentas, String>("totalDocumento"));
       
    }
    
    public void selccionarDatos(){
    txtnumeroDoc.setText(String.valueOf(((CompraVentas)tblComprasV.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
    txtfechaDoc.setText(((CompraVentas)tblComprasV.getSelectionModel().getSelectedItem()).getFechaDocumento());
    txtdescripcion.setText(((CompraVentas)tblComprasV.getSelectionModel().getSelectedItem()).getDescripcion());
    txttotalDoc.setText(((CompraVentas)tblComprasV.getSelectionModel().getSelectedItem()).getTotalDocumento());
    
        
}
    
    public ObservableList<CompraVentas> getCompraVentas(){
        ArrayList<CompraVentas> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CompraVentas (resultado.getInt("numeroDocumento"),
                                        resultado.getString("fechaDocumento"),
                                        resultado.getString("descripcion"),
                                        resultado.getString("totalDocumento")
                                                                               
                
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
        CompraVentas registro = new CompraVentas();
        registro.setNumeroDocumento(Integer.parseInt(txtnumeroDoc.getText()));
        registro.setFechaDocumento(txtfechaDoc.getText());
        registro.setDescripcion(txtdescripcion.getText());
        registro.setTotalDocumento(txttotalDoc.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarCompras(?, ?, ?, ?)");
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());            
            procedimiento.setString(3, registro.getDescripcion());            
            procedimiento.setString(4, registro.getTotalDocumento());            
            
            
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
                if(tblComprasV.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarCompras(?)");
                            procedimiento.setInt(1, ((CompraVentas)tblComprasV.getSelectionModel().getSelectedItem()).getNumeroDocumento());
                            procedimiento.execute();
                            listaClientes.remove(tblComprasV.getSelectionModel().getSelectedItem());
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
                if (tblComprasV.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtnumeroDoc.setDisable(true);
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
                    txtnumeroDoc.setDisable(false);
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
                txtnumeroDoc.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarCompras(?, ?, ?, ?)");
            CompraVentas registro = (CompraVentas)tblComprasV.getSelectionModel().getSelectedItem();
            registro.setFechaDocumento(txtfechaDoc.getText());
            registro.setDescripcion(txtdescripcion.getText());
            registro.setTotalDocumento(txttotalDoc.getText());
            procedimiento.setInt(1, registro.getNumeroDocumento());
            procedimiento.setString(2, registro.getFechaDocumento());
            procedimiento.setString(3, registro.getDescripcion());
            procedimiento.setString(4, registro.getTotalDocumento());
            
            
            procedimiento.execute();
            listaClientes.add(registro);

            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void desactivarControles(){
        txtnumeroDoc.setEditable(false);
        txtfechaDoc.setEditable(false);
        txtdescripcion.setEditable(false);
        txttotalDoc.setEditable(false);
        
    }
    
    
    public void activarControles(){
        txtnumeroDoc.setEditable(true);
        txtfechaDoc.setEditable(true);
        txtdescripcion.setEditable(true);
        txttotalDoc.setEditable(true);
        
    }
    
    public void limpiarControles(){
        txtnumeroDoc.clear();
        txtfechaDoc.clear();
        txtdescripcion.clear();
        txttotalDoc.clear();
        
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