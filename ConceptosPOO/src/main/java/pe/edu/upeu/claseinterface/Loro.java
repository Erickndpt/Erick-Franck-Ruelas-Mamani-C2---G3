package pe.edu.upeu.claseinterface;

public class Loro implements Animal {
    @Override
    public void emitirSonido() {
        System.out.println("Hola amigo, no seas flojo");
    }

    @Override
    public void dormir() {
        System.out.println("No molestar que me da Zzzz....Zzz..");
    }
}
