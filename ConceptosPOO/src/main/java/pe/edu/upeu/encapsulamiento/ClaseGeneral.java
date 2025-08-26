package pe.edu.upeu.encapsulamiento;

public class ClaseGeneral {

    public static void main(String[] args) {
        Persona p= new Persona();// p=objeto
        //p.nombre = "Erick";
        //p.edad = 25;
        p.setNombre("Erick"); // encapsulamiento
        p.setEdad(20); // encapsulamiento
        p.apellido = ("Ruelas"); // No se esta aplicando encapsulamiento
        p.saludo();

        Persona p2= new Persona();

        Trabajador t= new Trabajador(); //T=objeto
        t.setNombre("Erick");
        t.setApellido("Ruelas");
        t.setEdad(20);
        t.setArea("Sistemas");
        t.setGenero('M');
        System.out.println(t);


    }

}
