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
    TableColumn<Participante, String> dniCol, nombreCol, apellidoCol, carreraCol, tipoParCol;
    TableColumn<Participante, Void> opcionCol;

    @Autowired
    ParticipanteServicioI ps;

    @FXML TextField txtDni, txtNombres, txtApellidos;

    @FXML
    public void initialize(){
        cbxCarrera.getItems().addAll(Carrera.values());
        cbxTipoParticipante.getItems().addAll(TipoParticipante.values());

        definirNombresColumnas(); //Llamamos a la clase  ""definirNombresColumnas"" para que lo marque de azulito
        listarParticipantes();

    }

    public void definirNombresColumnas(){ //Para pintar los emcabezados osea para que se puede ingresar texto y de esa forma poner un nombre
        dniCol = new TableColumn("DNI");
        nombreCol = new TableColumn("Nombre");
        apellidoCol = new TableColumn("Apellido");
        apellidoCol.setMinWidth(180);
        carreraCol = new TableColumn("Carrera");
        tipoParCol = new TableColumn("Tipo Participante");
        tipoParCol.setMinWidth(160);
        opcionCol = new TableColumn("Opciones");
        tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol, carreraCol, tipoParCol, opcionCol);
    }

    public void agregarAccionBotones(){
        Callback<TableColumn<Participante, Void>, TableCell<Participante, Void>> cellFactory =
                param-> new  TableCell<>() {
                Button btnEditar = new Button("Editar");
                Button btnEliminar = new Button("Eliminar");
                    {
                        btnEditar.setOnAction((event)->{
                            System.out.println("Editando participante");
                        });
                        btnEliminar.setOnAction((event)->{
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

    public void listarParticipantes(){
        dniCol.setCellValueFactory(cellData ->
                cellData.getValue().getDni());
        nombreCol.setCellValueFactory(cellData ->
                cellData.getValue().getNombre());
        apellidoCol.setCellValueFactory(cellData ->
                cellData.getValue().getApellidos());
        carreraCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCarrera().toString()));
        tipoParCol.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTipoParticipante().toString()));
        agregarAccionBotones();

        participantes = FXCollections.observableArrayList(ps.findAll());
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
        ps.save(participante);
        listarParticipantes();
    }

    public void eliminarParticipante(int index){
        ps.delete(index);
        listarParticipantes();
    }

}
