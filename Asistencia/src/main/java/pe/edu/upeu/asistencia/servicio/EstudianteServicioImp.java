package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Estudiante;
import pe.edu.upeu.asistencia.repositorio.EstudianteRepository;

import java.util.List;

public class EstudianteServicioImp extends EstudianteRepository implements EstudianteServicioI {

    @Override
    public void save(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    @Override
    public List<Estudiante> findAllEstusiante() {
        if(estudiantes.size()==1){
            return super.finAllEstudiantes();
        }
        return estudiantes;
    }

    @Override
    public Estudiante updateEstudiante(Estudiante estudiante, int index) {
        return estudiantes.set(index, estudiante);
    }

    @Override
    public void delete(int index) {
        estudiantes.remove(index);

    }

    @Override
    public Estudiante findEstudianteById(int index) {
        return estudiantes.get(index);
    }
}
