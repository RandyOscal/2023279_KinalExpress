package org.randyoscal.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.randyoscal.system.Principal;


public class MenuPrincipalController implements Initializable {
    private Principal escenarioPrincipal;
    
  //  @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador;
    @FXML MenuItem btnProductos;
    @FXML MenuItem btnProveedores;
    @FXML MenuItem btnCompraVenta;
    @FXML MenuItem btnCargo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicClientes (ActionEvent event){
        if (event.getSource() == btnMenuClientes){
            escenarioPrincipal.menuClientesView();
        }
    }
    
    
    
    @FXML
    public void clicProgramador (ActionEvent event){
        if (event.getSource() == btnProgramador){
        escenarioPrincipal.ProgramadorView();
        }
    }
    
    
    @FXML
    public void clicProductos (ActionEvent event){
        if (event.getSource() == btnProductos){
            escenarioPrincipal.ProductosView();
        }
    }
    
    @FXML
    public void clicProveedores (ActionEvent event){
        if (event.getSource() == btnProveedores){
            escenarioPrincipal.ProveedoresView();
        }
    }
    
     @FXML
    public void clicCompraVenta (ActionEvent event){
        if (event.getSource() == btnCompraVenta){
            escenarioPrincipal.CompraVentaView();
        }
    }
    
     @FXML
    public void clicCargo (ActionEvent event){
        if (event.getSource() == btnCargo){
            escenarioPrincipal.CargoView();
        }
    }
       
}

