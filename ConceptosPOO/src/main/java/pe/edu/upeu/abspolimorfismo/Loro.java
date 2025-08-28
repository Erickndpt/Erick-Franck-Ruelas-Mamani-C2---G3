package pe.edu.upeu.abspolimorfismo;

public class Loro extends Animal{
    @Override
    void emitirSonido() {
        System.out.println("Hey...no te distraigas!");
    }

    @Override
    void dormir() {
        super.dormir();
        System.out.println("Dejame dormir"+
                " Zzzz...zzz...");
    }
}
