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
import org.randyoscal.bean.CargoEmpleados;
import org.randyoscal.system.Principal;

public class CargoEmpleadoController implements Initializable {

    private Principal escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<CargoEmpleados> listaClientes;
    @FXML private Button btnRegresar;
    //  @FXML MenuItem btnMenuClientes

    @FXML private TextField txtidCargoEm;
    @FXML private TextField txtnombreCargo;
    @FXML private TextField txtdescripcionCargo;

    @FXML private TableView tblCargoEm;

    @FXML private TableColumn colidCargoEm;
    @FXML private TableColumn colnombreCargo;
    @FXML private TableColumn coldescripcionCargo;

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
        tblCargoEm.setItems(getCargoEmpleados());
        colidCargoEm.setCellValueFactory(new PropertyValueFactory<CargoEmpleados, Integer>("idCargoEmpleado"));
        colnombreCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleados, String>("nombreCargo"));
        coldescripcionCargo.setCellValueFactory(new PropertyValueFactory<CargoEmpleados, String>("descripcionCargo"));

    }

    public void selccionarDatos() {
        txtidCargoEm.setText(String.valueOf(((CargoEmpleados) tblCargoEm.getSelectionModel().getSelectedItem()).getIdCargoEmpleado()));
        txtnombreCargo.setText(((CargoEmpleados) tblCargoEm.getSelectionModel().getSelectedItem()).getNombreCargo());
        txtdescripcionCargo.setText(((CargoEmpleados) tblCargoEm.getSelectionModel().getSelectedItem()).getDescripcionCargo());

    }

    public ObservableList<CargoEmpleados> getCargoEmpleados() {
        ArrayList<CargoEmpleados> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargosEmpleado()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new CargoEmpleados(resultado.getInt("idCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaClientes = FXCollections.observableArrayList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
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

    public void guardar() {
        CargoEmpleados registro = new CargoEmpleados();
        registro.setIdCargoEmpleado(Integer.parseInt(txtidCargoEm.getText()));
        registro.setNombreCargo(txtnombreCargo.getText());
        registro.setDescripcionCargo(txtdescripcionCargo.getText());

        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_AgregarCargoEmpleado(?, ?, ?)");
            procedimiento.setInt(1, registro.getIdCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());

            procedimiento.execute();
            listaClientes.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
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
                if (tblCargoEm.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EliminarCargoEmpleado(?)");
                            procedimiento.setInt(1, ((CargoEmpleados) tblCargoEm.getSelectionModel().getSelectedItem()).getIdCargoEmpleado());
                            procedimiento.execute();
                            listaClientes.remove(tblCargoEm.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de selccionar un elemento. ");
                }

        }

    }

    public void editar() {

        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblCargoEm.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtidCargoEm.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar algun elemento");
                }
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
                txtidCargoEm.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                limpiarControles();
                cargarDatos();

        }

    }

    public void reporte() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                txtidCargoEm.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("call sp_EditarCargoEmpleado(?, ?, ?)");
            CargoEmpleados registro = (CargoEmpleados) tblCargoEm.getSelectionModel().getSelectedItem();
            registro.setNombreCargo(txtnombreCargo.getText());
            registro.setDescripcionCargo(txtdescripcionCargo.getText());
            procedimiento.setInt(1, registro.getIdCargoEmpleado());
            procedimiento.setString(2, registro.getNombreCargo());
            procedimiento.setString(3, registro.getDescripcionCargo());
            
            procedimiento.execute();
            listaClientes.add(registro);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void desactivarControles() {
        txtidCargoEm.setEditable(false);
        txtnombreCargo.setEditable(false);
        txtdescripcionCargo.setEditable(false);

    }

    public void activarControles() {
        txtidCargoEm.setEditable(true);
        txtnombreCargo.setEditable(true);
        txtdescripcionCargo.setEditable(true);

    }

    public void limpiarControles() {
        txtidCargoEm.clear();
        txtnombreCargo.clear();
        txtdescripcionCargo.clear();

    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void regresar(ActionEvent event) {
        if (event.getSource() == btnRegresar) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
