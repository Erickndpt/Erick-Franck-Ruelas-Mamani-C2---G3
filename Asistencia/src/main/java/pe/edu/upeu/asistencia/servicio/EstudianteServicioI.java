package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Estudiante;

import java.util.List;

public interface EstudianteServicioI {

    void save(Estudiante estudiante); //C
    List<Estudiante> findAllEstusiante(); // R
    Estudiante updateEstudiante(Estudiante estudiante,int index); //U
    void delete(int index); //D

    Estudiante findEstudianteById(int index);//Buscar
}
