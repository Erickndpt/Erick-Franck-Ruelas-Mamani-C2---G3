package pe.edu.upeu.asistencia.control;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;
import pe.edu.upeu.asistencia.modelo.Participante;
import pe.edu.upeu.asistencia.servicio.ParticipanteServicioI;

@Controller
public class ParticipanteController {

    @FXML
    private ComboBox<Carrera> cbxCarrera;

    @FXML
    private ComboBox<TipoParticipante> cbxTipoParticipante;

    @FXML TableView<Participante> tableView;
    ObservableList<Participante> participantes;
    TableColumn<Participante, String> dniCol, nombreCol, apellidoCol, carreraCol, tipoPartCol;
    TableColumn<Participante, Void> opcionCol;
    @Autowired
    ParticipanteServicioI ps;
    @FXML TextField txtDni, txtNombres, txtApellidos;
    int indexE=-1;
    @FXML
    public void initialize() {
        cbxCarrera.getItems().setAll(Carrera.values());
        cbxTipoParticipante.getItems().setAll(TipoParticipante.values());
        definirNombresColumnas();
        listarParticipantes();
    }
    public void definirNombresColumnas(){
        dniCol = new TableColumn("DNI");
        nombreCol = new TableColumn("Nombre");
        apellidoCol = new TableColumn("Apellido");
        apellidoCol.setMinWidth(180);
        carreraCol = new TableColumn("Carrera");
        tipoPartCol = new TableColumn("Tipo Participante");
        tipoPartCol.setMinWidth(160);
        opcionCol = new TableColumn("Opciones");
        opcionCol.setMinWidth(200);
        tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol, carreraCol, tipoPartCol, opcionCol);
    }

    public void agregarAccionBotones(){
        Callback<TableColumn<Participante, Void>, TableCell<Participante, Void>> cellFactory =
                param-> new  TableCell<>() {
                Button btnEditar = new Button("Editar");
                Button btnEliminar = new Button("Eliminar");
                    {
                        btnEditar.setOnAction((event) -> {
                            Participante participante =  getTableView().getItems().get(getIndex());
                            editarParticipante(participante, getIndex());
                        });
                        btnEliminar.setOnAction((event) -> {
                            eliminarParticipante(getIndex());
                        });
                    }
                @Override
                protected void updateItem(Void item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setGraphic(null);
                        }else{
                            HBox hBox = new HBox(btnEditar, btnEliminar);
                            hBox.setSpacing(10);
                            setGraphic(hBox);
                        }
                }
                };
        opcionCol.setCellFactory(cellFactory);
    }
    public void editarParticipante(Participante p, int index){
        txtDni.setText(p.getDni().getValue());
        txtNombres.setText(p.getNombre().getValue());
        txtApellidos.setText(p.getApellidos().getValue());
        cbxCarrera.getSelectionModel().select(p.getCarrera());
        cbxTipoParticipante.getSelectionModel().select(p.getTipoParticipante());
        indexE=index;
    }
    public void listarParticipantes(){
        dniCol.setCellValueFactory(cellData ->
                cellData.getValue().getDni());
        nombreCol.setCellValueFactory(cellData ->
                cellData.getValue().getNombre());
        apellidoCol.setCellValueFactory(cellData ->
                cellData.getValue().getApellidos());
        carreraCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCarrera().toString()));
        tipoPartCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoParticipante().toString()));
        agregarAccionBotones();

        participantes=FXCollections.observableArrayList(ps.findAll());
        tableView.setItems(participantes);
    }
    @FXML
    public void crearParticipante(){
        Participante participante = new Participante();
        participante.setDni(new SimpleStringProperty(txtDni.getText()));
        participante.setNombre(new SimpleStringProperty(txtNombres.getText()));
        participante.setApellidos(new SimpleStringProperty(txtApellidos.getText()));
        participante.setCarrera(cbxCarrera.getValue());
        participante.setTipoParticipante(cbxTipoParticipante.getValue());
        if(indexE==-1){
            ps.save(participante);
        }else{
            ps.update(participante, indexE);
            indexE=-1;
        }
        limpiarFormulario();
        listarParticipantes();
    }

    public void limpiarFormulario(){
        txtDni.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        cbxCarrera.getSelectionModel().clearSelection();
        cbxTipoParticipante.getSelectionModel().clearSelection();
    }



    public void eliminarParticipante(int index){
        ps.delete(index);
        listarParticipantes();
    }
}
