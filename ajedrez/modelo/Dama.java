    package ajedrez.modelo;

    public class Dama extends Pieza {
        public Dama(boolean esBlanca, Posicion posicion) {
            super(esBlanca, posicion);
        }

        @Override
        public boolean esMovimientoValido(Posicion nuevaPosicion, Tablero tablero) {
            int difFila = Math.abs(nuevaPosicion.getFila() - posicion.getFila());
            int difColumna = Math.abs(nuevaPosicion.getColumna() - posicion.getColumna());

            // La dama se puede mover en diagonal o en línea recta
            return difFila == difColumna || difFila == 0 || difColumna == 0;
        }

        @Override
        public String toString() {
            return esBlanca ? "♕" : "♛";
        }
    }