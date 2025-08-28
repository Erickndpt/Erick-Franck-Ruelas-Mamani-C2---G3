package pe.edu.upeu.claseinterface;

public class ClaseGeneral {
    public static void main(String[] args) {
        Animal a = new Loro();
        a.emitirSonido();
        a.dormir();

        a = new Gato();
        a.emitirSonido();
        a.dormir();
    }
}
