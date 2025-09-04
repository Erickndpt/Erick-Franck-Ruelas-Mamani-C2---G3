package pe.edu.upeu.asistencia.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum Carrera {
    SISTEMAS(Facultad.FIA, "Ing. Sistemas"),
    CIVIL(Facultad.FIA, "Ing. Civil"),
    AMBIENTAL(Facultad.FIA, "Ing. Ambiental"),

    ADMINISTRACION(Facultad.FCE, "Administración"),
    NUTRICION(Facultad.FCS, "Nutrición"),
    EDUCACION(Facultad.FACIHED, "Educación"),
    ;

    private Facultad facultad;
    private String descripcion;
}
