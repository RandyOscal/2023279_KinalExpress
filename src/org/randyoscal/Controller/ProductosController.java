
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
import org.randyoscal.bean.Productos;
import org.randyoscal.system.Principal;

public class ProductosController implements Initializable{
   private Principal escenarioPrincipal;

    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Productos> listaClientes;
    @FXML private Button btnRegresar;
  //  @FXML MenuItem btnMenuClientes
    
    @FXML private TextField txtidTipoPro;
    @FXML private TextField txtdescripcion;
    
    
    @FXML private TableView tblProductos;
    
    @FXML private TableColumn colidTipoPro;
    @FXML private TableColumn coldescripcion;
    
        
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
        tblProductos.setItems(getProductos());
        colidTipoPro.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("idTipoProducto"));
        coldescripcion.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcion"));
        
    }
    
    public void selccionarDatos(){
    txtidTipoPro.setText(String.valueOf(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getIdTipoProducto()));
    txtdescripcion.setText(((Productos)tblProductos.getSelectionModel().getSelectedItem()).getDescripcion());
    
        
}
    
    public ObservableList<Productos> getProductos(){
        ArrayList<Productos> lista = new ArrayList<>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_ListarTipoProducto()");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Productos (resultado.getInt("idTipoProducto"),
                                        resultado.getString("descripcion")                                       
                
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
        Productos registro = new Productos();
        registro.setIdTipoProducto(Integer.parseInt(txtidTipoPro.getText()));
        registro.setDescripcion(txtdescripcion.getText());
        
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarTipoProducto(?, ?)");
            procedimiento.setInt(1, registro.getIdTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());            
                        
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
                if(tblProductos.getSelectionModel().getSelectedItem() != null){
                    int respuesta  = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarTipoProducto(?)");
                            procedimiento.setInt(1, ((Productos)tblProductos.getSelectionModel().getSelectedItem()).getIdTipoProducto());
                            procedimiento.execute();
                            listaClientes.remove(tblProductos.getSelectionModel().getSelectedItem());
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtidTipoPro.setDisable(true);
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
                    txtidTipoPro.setDisable(false);
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
                txtidTipoPro.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }
    
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarTipoProducto(?,?)");
            Productos registro = (Productos)tblProductos.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtdescripcion.getText());
            procedimiento.setInt(1, registro.getIdTipoProducto());
            procedimiento.setString(2, registro.getDescripcion());
            
            
            procedimiento.execute();
            listaClientes.add(registro);

            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void desactivarControles(){
        txtidTipoPro.setEditable(false);
        txtdescripcion.setEditable(false);
        
    }
    
    
    public void activarControles(){
        txtidTipoPro.setEditable(true);
        txtdescripcion.setEditable(true);
      
    }
    
    public void limpiarControles(){
        txtidTipoPro.clear();
        txtdescripcion.clear();
        
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

