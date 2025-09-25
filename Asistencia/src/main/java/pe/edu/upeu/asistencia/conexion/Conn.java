package pe.edu.upeu.asistencia.conexion;

import javax.swing.*;
import java.sql.*;

public class Conn {

    static Connection conexion=null;

    public static Connection getConexion(){
        try{
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:data/asistencia.db?foreign_keys=on;";
            if(conexion==null){
                conexion=DriverManager.getConnection(url);
            }
            System.out.println("Conexion establecida!");
        }catch(ClassNotFoundException | SQLException e){      //ClassNotFoundException=captura el error de esta parte ///Class.forName("org.sqlite.JDBC");
                JOptionPane.showMessageDialog(null,"Error:"+e.getMessage());

        }
        return conexion;
    }
    public static void closeConexion(){
        try {
            if(conexion!=null){conexion.close();}
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

   /* public static void main(String[] args) {
        Connection con= getConexion();
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            pst=con.prepareStatement("SELECT * FROM participante");
            rs=pst.executeQuery();
            while(rs.next()){
                System.out.println(rs.getString("dni"));
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }*/


}
