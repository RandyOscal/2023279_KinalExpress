package org.randyoscal.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.randyoscal.system.Principal;

public class LoginController implements Initializable{
    private Principal escenarioPrincipal;
    @FXML MenuItem btnIngresar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void clicIngresar (ActionEvent event){
        if (event.getSource() == btnIngresar){
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
