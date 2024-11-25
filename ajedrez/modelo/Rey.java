package ajedrez.modelo;

public class Rey extends Pieza {
    public Rey(boolean esBlanca, Posicion posicion) {
        super(esBlanca, posicion);
    }

    @Override
    public boolean esMovimientoValido(Posicion nuevaPosicion, Tablero tablero) {
        int difFila = Math.abs(nuevaPosicion.getFila() - posicion.getFila());
        int difColumna = Math.abs(nuevaPosicion.getColumna() - posicion.getColumna());

        // El rey se puede mover una casilla en cualquier dirección
        return difFila <= 1 && difColumna <= 1 && (difFila != 0 || difColumna != 0);
    }

    @Override
    public String toString() {
        return esBlanca ? "♔" : "♚";
    }
}
