package pe.edu.upeu.asistencia.control;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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

    @Autowired
    ParticipanteServicioI ps;
    @Autowired
    private MessageSource messageSource;

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
        apellidoCol.setMinWidth(200);
        carreraCol = new TableColumn("Carrera");
        tipoParCol = new TableColumn("Tipo Participante");
        tipoParCol.setMinWidth(160);
        tableView.getColumns().addAll(dniCol, nombreCol, apellidoCol, carreraCol, tipoParCol);
    }

    public void listarParticipantes(){
        dniCol.setCellValueFactory(cellData ->
                cellData.getValue().getDni());
        participantes = FXCollections.observableArrayList(ps.findAll());
        tableView.setItems(participantes);
    }

}
