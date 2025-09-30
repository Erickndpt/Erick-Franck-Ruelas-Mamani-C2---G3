package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "participante")
public class Participante {
    @Id
    private String dni;

    private String nombre;
    private String apellidos;
    @Enumerated(EnumType.STRING)
    private Carrera carrera;
    @Enumerated(EnumType.STRING)
    private TipoParticipante tipoParticipante;

    private Boolean estado;

}
