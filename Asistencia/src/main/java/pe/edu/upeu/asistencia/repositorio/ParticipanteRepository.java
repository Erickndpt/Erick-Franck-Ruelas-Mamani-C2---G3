package pe.edu.upeu.asistencia.repositorio;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import pe.edu.upeu.asistencia.conexion.Conn;
import pe.edu.upeu.asistencia.enums.Carrera;
import pe.edu.upeu.asistencia.enums.TipoParticipante;
import pe.edu.upeu.asistencia.modelo.Participante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ParticipanteRepository {

    Connection conn = Conn.getConexion();
    PreparedStatement pst;
    ResultSet rs;

    public List<Participante> participantes = null;

   public List<Participante> findAll(){
       participantes=new ArrayList<>();
       try{
           pst = conn.prepareStatement("SELECT * FROM participante");
           rs = pst.executeQuery();
           while(rs.next()){
               Participante p = new Participante();
               p.setDni(new SimpleStringProperty(rs.getString("dni")));
               p.setNombre(new SimpleStringProperty(rs.getString("nombre")));
               p.setApellidos(new SimpleStringProperty(rs.getString("apellidos")));
               p.setCarrera(Carrera.valueOf(rs.getString("carrera")));
               p.setTipoParticipante(TipoParticipante.valueOf(rs.getString("tipo_Participante")));
               participantes.add(p);
           }

       }catch(SQLException e){
           System.out.println(e.getMessage());
       }
        return participantes;
    }

    public void save(Participante p){
        String sql="INSERT INTO participante \n" +
                "(dni, nombre, apellidos, carrera, tipo_participante, estado)\n" +
                "VALUES('"+p.getDni().getValue()+"', ?, ?, ?, ?, 1);";
        try {
            pst= conn.prepareStatement(sql);
            pst.setString(1, p.getNombre().getValue());
            pst.setString(2, p.getApellidos().getValue());
            pst.setString(3, p.getCarrera().name());
            pst.setString(4, p.getTipoParticipante().name());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Participante update(Participante p){

        String sql="UPDATE participante\n" +
                "SET nombre=?, apellidos=?, carrera=?, tipo_participante=?\n" +
                "WHERE dni=?;";
        try {
            pst= conn.prepareStatement(sql);
            pst.setString(1, p.getNombre().getValue());
            pst.setString(2, p.getApellidos().getValue());
            pst.setString(3, p.getCarrera().name());
            pst.setString(4, p.getTipoParticipante().name());
            pst.setString(5, p.getDni().getValue());
            pst.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

       return p;
    }

    public void delete(String dni){
        String sql="DELETE FROM participante WHERE dni=?;";
        try {
            pst= conn.prepareStatement(sql);
            pst.setString(1, dni);
            pst.executeUpdate();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public Participante findById(String dni){
        Participante p=null;
        try {
            pst=conn.prepareStatement("SELECT * FROM participante where dni=?");
            pst.setString(1,dni);
            rs=pst.executeQuery();
            if(rs.next()){
                p=new Participante();
                p.setDni(new  SimpleStringProperty(rs.getString("dni")));
                p.setNombre(new  SimpleStringProperty(rs.getString("nombre")));
                p.setApellidos(new  SimpleStringProperty(rs.getString("apellidos")));
                p.setCarrera(Carrera.valueOf( rs.getString("carrera")));
                p.setTipoParticipante(TipoParticipante.valueOf( rs.getString("tipo_participante")));
                participantes.add(p);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return p;
    }

}
