
package org.randyoscal.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import org.randyoscal.system.Principal;



public class ProgramadorController implements Initializable {
    private Principal escenarioPrincipal;
    
  //  @FXML MenuItem btnMenuClientes;
    @FXML MenuItem btnProgramador;
    @FXML private Button btnRegresar;
    
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
    public void clicProgramador (ActionEvent event){
        if (event.getSource() == btnProgramador){
            escenarioPrincipal.ProgramadorView();
        }
    }
    
    @FXML 
    public void regresar (ActionEvent event){
        if (event.getSource() == btnRegresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
    
    @FXML
    public void handleButtonAction (ActionEvent event){
        if (event.getSource() == btnProgramador){
        escenarioPrincipal.ProgramadorView();
        }
    }
   
}

    






