package ajedrez.modelo;

public class Torre extends Pieza {
    public Torre(boolean esBlanca, Posicion posicion) {
        super(esBlanca, posicion);
    }

    @Override
    public boolean esMovimientoValido(Posicion nuevaPosicion, Tablero tablero) {
        int difFila = Math.abs(nuevaPosicion.getFila() - posicion.getFila());
        int difColumna = Math.abs(nuevaPosicion.getColumna() - posicion.getColumna());

        // La torre solo se mueve en línea recta
        return difFila == 0 || difColumna == 0;
    }

    @Override
    public String toString() {
        return esBlanca ? "♖" : "♜";
    }
}
