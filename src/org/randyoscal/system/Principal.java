package org.randyoscal.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.randyoscal.Controller.CompraVentaController;
import org.randyoscal.Controller.MenuClientesController;
import org.randyoscal.Controller.MenuPrincipalController;
import org.randyoscal.Controller.TipoProductosController;
import org.randyoscal.Controller.ProgramadorController;
import org.randyoscal.Controller.ProveedoresController;
import org.randyoscal.Controller.CargoEmpleadoController;
import org.randyoscal.Controller.DetalleCompraController;
import org.randyoscal.Controller.EmailProveedorController;
import org.randyoscal.Controller.EmpleadoController;
import org.randyoscal.Controller.ProductoController;
import org.randyoscal.Controller.TelefonoProveedorController;

/*
* Documentacion:
* NOmbre Randy Oscal
* Fecha Creacion: 11/04/2024
 */
public class Principal extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;
    private final String URLVIEW = "/org/randyoscal/View/";

    /* FXML, Parent*/
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Express");
        menuPrincipalView();

        /*Parent root = FXMLLoader.load(getClass().getResource("/org/randyoscal/View/MenuPrincipalView.fxml"));
       Scene escena = new Scene(root);
       escenarioPrincipal.setScene(escena);*/
        escenarioPrincipal.show();

    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception {
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();

        InputStream file = Principal.class.getResourceAsStream(URLVIEW + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Principal.class.getResource(URLVIEW + fxmlName));

        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();

        resultado = (Initializable) loader.getController();

        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 628, 475);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClienteView = (MenuClientesController)cambiarEscena("MenuClientesView.fxml", 749,421);
            menuClienteView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ProgramadorView(){
        try{
            ProgramadorController ProgramadorView = (ProgramadorController)cambiarEscena("Programador.fxml", 409,402);
            ProgramadorView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void TipoProductosView(){
        try{
            TipoProductosController ProductosView = (TipoProductosController)cambiarEscena("MenuTipoProductos.fxml", 749,422);
            ProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void ProveedoresView(){
        try{
            ProveedoresController ProveedoresView = (ProveedoresController)cambiarEscena("MenuProveedores.fxml", 749,422);
            ProveedoresView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }    
    
    public void CompraVentaView(){
        try{
            CompraVentaController CompraVentaView = (CompraVentaController)cambiarEscena("MenuCompras.fxml", 749,422);
            CompraVentaView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    } 
    
    
    
    public void CargoView(){
        try{
            CargoEmpleadoController SalarioView = (CargoEmpleadoController)cambiarEscena("MenuCargoEmpeadosView.fxml", 749,422);
            SalarioView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    public void TipoProductoView(){
        try{
            TipoProductosController ProductosView = (TipoProductosController)cambiarEscena("MenuTipoProductos.fxml", 749,422);
            ProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ProductoView(){
        try{
            ProductoController ProductosView = (ProductoController)cambiarEscena("MenuProductos.fxml", 749,422);
            ProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void EmpleadoView(){
        try{
            EmpleadoController ProductosView = (EmpleadoController)cambiarEscena("MenuEmpleadoView.fxml", 749,422);
            ProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void TelefonoProveedor(){
        try{
            TelefonoProveedorController ProductosView = (TelefonoProveedorController)cambiarEscena("MenuTelefonoProveedor.fxml", 749,422);
            ProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void EmailProveedor(){
        try{
            EmailProveedorController ProductosView = (EmailProveedorController)cambiarEscena("MenuEmailProveedor.fxml", 749,422);
            ProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void DetalleCompra(){
        try{
            DetalleCompraController ProductosView = (DetalleCompraController)cambiarEscena("MenuDetalleCompra.fxml", 749,422);
            ProductosView.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
