package ajedrez.modelo;

public class Alfil extends Pieza {
    public Alfil(boolean esBlanca, Posicion posicion) {
        super(esBlanca, posicion);
    }

    @Override
    public boolean esMovimientoValido(Posicion nuevaPosicion, Tablero tablero) {
        int difFila = Math.abs(nuevaPosicion.getFila() - posicion.getFila());
        int difColumna = Math.abs(nuevaPosicion.getColumna() - posicion.getColumna());

        // El alfil solo se mueve en diagonal
        return difFila == difColumna;
    }

    @Override
    public String toString() {
        return esBlanca ? "♗" : "♝";
    }
}
