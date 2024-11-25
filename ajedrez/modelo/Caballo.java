package ajedrez.modelo;

public class Caballo extends Pieza {
    public Caballo(boolean esBlanca, Posicion posicion) {
        super(esBlanca, posicion);
    }

    @Override
    public boolean esMovimientoValido(Posicion nuevaPosicion, Tablero tablero) {
        int difFila = Math.abs(nuevaPosicion.getFila() - posicion.getFila());
        int difColumna = Math.abs(nuevaPosicion.getColumna() - posicion.getColumna());

        // El caballo se mueve en forma de L
        return (difFila == 2 && difColumna == 1) || (difFila == 1 && difColumna == 2);
    }

    @Override
    public String toString() {
        return esBlanca ? "♘" : "♞";
    }
}
