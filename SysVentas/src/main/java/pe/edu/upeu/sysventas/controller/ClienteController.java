package com.sysventas.controller;

import com.sysventas.model.Cliente;
import com.sysventas.util.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.Optional;

/**
 * ClienteController - CRUD para la entidad Cliente.
 *
 * Esta clase sigue el mismo estilo que ProductoController: maneja la tabla,
 * los campos del formulario y las operaciones contra la base de datos upeu_cliente.
 *
 * Requisitos:
 * - Tener la clase Conexion en com.sysventas.util que devuelva Connection a la BD upeu_cliente.
 * - Tener la clase modelo com.sysventas.model.Cliente con campos id, nombre, dni, email, telefono (o los que uses).
 *
 * Nota: Ajusta nombres de columnas y de la tabla (clientes) según tu esquema en la BD.
 */
public class ClienteController {

    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDni;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;

    @FXML private Button btnNuevo;
    @FXML private Button btnGuardar;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnCancelar;

    @FXML private TableView<Cliente> tblClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, String> colDni;
    @FXML private TableColumn<Cliente, String> colEmail;
    @FXML private TableColumn<Cliente, String> colTelefono;

    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();

    // Inicialización (similar a initialize en ProductoController)
    @FXML
    public void initialize() {
        configurarTabla();
        cargarClientes();
        habilitarFormulario(false);
        txtId.setEditable(false);
    }

    private void configurarTabla() {
        // Asigna las propiedades del modelo a las columnas.
        // Ajusta los nombres de propiedad según tu clase Cliente (getId, getNombre, getDni, ...)
        colId.setCellValueFactory(cell -> cell.getValue().idProperty().asObject());
        colNombre.setCellValueFactory(cell -> cell.getValue().nombreProperty());
        colDni.setCellValueFactory(cell -> cell.getValue().dniProperty());
        colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
        colTelefono.setCellValueFactory(cell -> cell.getValue().telefonoProperty());

        tblClientes.setItems(listaClientes);
    }

    private void cargarClientes() {
        listaClientes.clear();
        String sql = "SELECT id, nombre, dni, email, telefono FROM clientes";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("dni"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
                listaClientes.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlert(Alert.AlertType.ERROR, "Error", "No se pudo cargar la lista de clientes.");
        }
    }

    @FXML
    private void onNuevo(ActionEvent event) {
        limpiarFormulario();
        habilitarFormulario(true);
        txtId.setText("0");
        txtNombre.requestFocus();
    }

    @FXML
    private void onGuardar(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText().isEmpty() ? "0" : txtId.getText());
            String nombre = txtNombre.getText().trim();
            String dni = txtDni.getText().trim();
            String email = txtEmail.getText().trim();
            String telefono = txtTelefono.getText().trim();

            if (nombre.isEmpty()) {
                mostrarAlert(Alert.AlertType.WARNING, "Validación", "Ingrese el nombre del cliente.");
                return;
            }

            if (id == 0) {
                crearCliente(nombre, dni, email, telefono);
                mostrarAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente creado correctamente.");
            } else {
                actualizarCliente(id, nombre, dni, email, telefono);
                mostrarAlert(Alert.AlertType.INFORMATION, "Éxito", "Cliente actualizado correctamente.");
            }

            cargarClientes();
            limpiarFormulario();
            habilitarFormulario(false);

        } catch (NumberFormatException ex) {
            mostrarAlert(Alert.AlertType.ERROR, "Error", "Id inválido.");
        }
    }

    @FXML
    private void onEditar(ActionEvent event) {
        Cliente seleccionado = tblClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlert(Alert.AlertType.WARNING, "Atención", "Seleccione un cliente para editar.");
            return;
        }
        llenarFormulario(seleccionado);
        habilitarFormulario(true);
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        Cliente seleccionado = tblClientes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlert(Alert.AlertType.WARNING, "Atención", "Seleccione un cliente para eliminar.");
            return;
        }
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText("¿Eliminar cliente?");
        confirm.setContentText("¿Está seguro que desea eliminar al cliente: " + seleccionado.getNombre() + "?");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            eliminarCliente(seleccionado.getId());
            cargarClientes();
            mostrarAlert(Alert.AlertType.INFORMATION, "Eliminado", "Cliente eliminado correctamente.");
        }
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        limpiarFormulario();
        habilitarFormulario(false);
    }

    @FXML
    private void onSeleccionarFila(MouseEvent event) {
        if (event.getClickCount() == 2) { // doble clic para editar rápido
            Cliente c = tblClientes.getSelectionModel().getSelectedItem();
            if (c != null) {
                llenarFormulario(c);
                habilitarFormulario(true);
            }
        }
    }

    private void llenarFormulario(Cliente c) {
        txtId.setText(String.valueOf(c.getId()));
        txtNombre.setText(c.getNombre());
        txtDni.setText(c.getDni());
        txtEmail.setText(c.getEmail());
        txtTelefono.setText(c.getTelefono());
    }

    private void limpiarFormulario() {
        txtId.clear();
        txtNombre.clear();
        txtDni.clear();
        txtEmail.clear();
        txtTelefono.clear();
    }

    private void habilitarFormulario(boolean habilitar) {
        txtNombre.setDisable(!habilitar);
        txtDni.setDisable(!habilitar);
        txtEmail.setDisable(!habilitar);
        txtTelefono.setDisable(!habilitar);

        btnGuardar.setDisable(!habilitar);
        btnCancelar.setDisable(!habilitar);

        btnNuevo.setDisable(habilitar);
        btnEditar.setDisable(habilitar);
        btnEliminar.setDisable(habilitar);
    }

    /* Operaciones CRUD contra la BD */

    private void crearCliente(String nombre, String dni, String email, String telefono) {
        String sql = "INSERT INTO clientes (nombre, dni, email, telefono) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, dni);
            ps.setString(3, email);
            ps.setString(4, telefono);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlert(Alert.AlertType.ERROR, "Error", "No se pudo crear el cliente.");
        }
    }

    private void actualizarCliente(int id, String nombre, String dni, String email, String telefono) {
        String sql = "UPDATE clientes SET nombre = ?, dni = ?, email = ?, telefono = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, dni);
            ps.setString(3, email);
            ps.setString(4, telefono);
            ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el cliente.");
        }
    }

    private void eliminarCliente(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el cliente.");
        }
    }

    private void mostrarAlert(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(mensaje);
        a.showAndWait();
    }
}

