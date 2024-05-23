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

public class ProductoController implements Initializable {
    private Principal escenarioPrincipal;
    private enum operaciones{AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO}
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Productos> listaProductos;

    @FXML private Button btnRegresar;

    @FXML private TextField txtCodigoProducto;
    @FXML private TextField txtDescripcionProducto;
    @FXML private TextField txtPrecioUnitario;
    @FXML private TextField txtPrecioDocena;
    @FXML private TextField txtPrecioMayor;
    @FXML private TextField txtImagenProducto;
    @FXML private TextField txtExistencia;
    @FXML private TextField txtIdTipoProducto;
    @FXML private TextField txtCodigoProveedor;

    @FXML private TableView tblProductos;

    @FXML private TableColumn colCodigoProducto;
    @FXML private TableColumn colDescripcionProducto;
    @FXML private TableColumn colPrecioUnitario;
    @FXML private TableColumn colPrecioDocena;
    @FXML private TableColumn colPrecioMayor;
    @FXML private TableColumn colImagenProducto;
    @FXML private TableColumn colExistencia;
    @FXML private TableColumn colIdTipoProducto;
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

    public void cargarDatos() {
        tblProductos.setItems(getProductos());
        colCodigoProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescripcionProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioDocena.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colImagenProducto.setCellValueFactory(new PropertyValueFactory<Productos, String>("imagenProducto"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colIdTipoProducto.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("idTipoProducto"));
        colCodigoProveedor.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }

    public void seleccionarDatos() {
        Productos productoSeleccionado = (Productos) tblProductos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado != null) {
            txtCodigoProducto.setText(productoSeleccionado.getCodigoProducto());
            txtDescripcionProducto.setText(productoSeleccionado.getDescripcionProducto());
            txtPrecioUnitario.setText(String.valueOf(productoSeleccionado.getPrecioUnitario()));
            txtPrecioDocena.setText(String.valueOf(productoSeleccionado.getPrecioDocena()));
            txtPrecioMayor.setText(String.valueOf(productoSeleccionado.getPrecioMayor()));
            txtImagenProducto.setText(productoSeleccionado.getImagenProducto());
            txtExistencia.setText(String.valueOf(productoSeleccionado.getExistencia()));
            txtIdTipoProducto.setText(String.valueOf(productoSeleccionado.getIdTipoProducto()));
            txtCodigoProveedor.setText(String.valueOf(productoSeleccionado.getCodigoProveedor()));
        }
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarProductos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Productos(
                    resultado.getString("codigoProducto"),
                    resultado.getString("descripcionProducto"),
                    resultado.getDouble("precioUnitario"),
                    resultado.getDouble("precioDocena"),
                    resultado.getDouble("precioMayor"),
                    resultado.getString("imagenProducto"),
                    resultado.getInt("existencia"),
                    resultado.getInt("idTipoProducto"),
                    resultado.getInt("codigoProveedor")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos = FXCollections.observableArrayList(lista);
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
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodigoProducto.getText());
        registro.setDescripcionProducto(txtDescripcionProducto.getText());
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
        registro.setImagenProducto(txtImagenProducto.getText());
        registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
        registro.setIdTipoProducto(Integer.parseInt(txtIdTipoProducto.getText()));
        registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getIdTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();
            listaProductos.add(registro);
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar si elimina el registro", "Eliminar Productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarProductoPorCodigo(?)}");
                            procedimiento.setString(1, ((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
                            procedimiento.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un producto.");
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/randyoscal/Image/Aceptar.png"));
                    imgReporte.setImage(new Image("/org/randyoscal/Image/Cancelar.png"));
                    activarControles();
                    txtCodigoProducto.setDisable(true);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un producto.");
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
                txtCodigoProducto.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                limpiarControles();
                cargarDatos();
                break;
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
                txtCodigoProducto.setDisable(false);
                imgEditar.setImage(new Image("/org/randyoscal/Image/Editar.png"));
                imgReporte.setImage(new Image("/org/randyoscal/Image/Reportes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EditarProducto(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            Productos registro = (Productos) tblProductos.getSelectionModel().getSelectedItem();
            registro.setDescripcionProducto(txtDescripcionProducto.getText());
            registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitario.getText()));
            registro.setPrecioDocena(Double.parseDouble(txtPrecioDocena.getText()));
            registro.setPrecioMayor(Double.parseDouble(txtPrecioMayor.getText()));
            registro.setImagenProducto(txtImagenProducto.getText());
            registro.setExistencia(Integer.parseInt(txtExistencia.getText()));
            registro.setIdTipoProducto(Integer.parseInt(txtIdTipoProducto.getText()));
            registro.setCodigoProveedor(Integer.parseInt(txtCodigoProveedor.getText()));
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setString(6, registro.getImagenProducto());
            procedimiento.setInt(7, registro.getExistencia());
            procedimiento.setInt(8, registro.getIdTipoProducto());
            procedimiento.setInt(9, registro.getCodigoProveedor());
            procedimiento.execute();
            listaProductos.add(registro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void desactivarControles() {
        txtCodigoProducto.setEditable(false);
        txtDescripcionProducto.setEditable(false);
        txtPrecioUnitario.setEditable(false);
        txtPrecioDocena.setEditable(false);
        txtPrecioMayor.setEditable(false);
        txtImagenProducto.setEditable(false);
        txtExistencia.setEditable(false);
        txtIdTipoProducto.setEditable(false);
        txtCodigoProveedor.setEditable(false);
    }

    public void activarControles() {
        txtCodigoProducto.setEditable(true);
        txtDescripcionProducto.setEditable(true);
        txtPrecioUnitario.setEditable(true);
        txtPrecioDocena.setEditable(true);
        txtPrecioMayor.setEditable(true);
        txtImagenProducto.setEditable(true);
        txtExistencia.setEditable(true);
        txtIdTipoProducto.setEditable(true);
        txtCodigoProveedor.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoProducto.clear();
        txtDescripcionProducto.clear();
        txtPrecioUnitario.clear();
        txtPrecioDocena.clear();
        txtPrecioMayor.clear();
        txtImagenProducto.clear();
        txtExistencia.clear();
        txtIdTipoProducto.clear();
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
