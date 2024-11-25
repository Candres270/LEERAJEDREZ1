package ajedrez.modelo;

public class Peon extends Pieza {
    private boolean primerMovimiento = true;

    public Peon(boolean esBlanca, Posicion posicion) {
        super(esBlanca, posicion);
    }

    public void setPrimerMovimiento(boolean primerMovimiento) {
        this.primerMovimiento = primerMovimiento;
    }

    @Override
    public boolean esMovimientoValido(Posicion nuevaPosicion, Tablero tablero) {
        int difFila = nuevaPosicion.getFila() - posicion.getFila();
        int difColumna = Math.abs(nuevaPosicion.getColumna() - posicion.getColumna());

        // Dirección del movimiento según el color
        int direccion = esBlanca ? -1 : 1;

        // Movimiento básico hacia adelante
        if (difColumna == 0) {
            // Verificar que no haya pieza en el destino para movimiento hacia adelante
            if (tablero.getPieza(nuevaPosicion) != null) {
                return false;
            }

            if (difFila == direccion) {
                return true;
            }

            // Primer movimiento puede ser doble
            if (primerMovimiento && difFila == 2 * direccion) {
                // Verificar que no haya piezas en el camino
                Posicion intermedia = new Posicion(
                        posicion.getFila() + direccion,
                        posicion.getColumna()
                );
                return tablero.getPieza(intermedia) == null;
            }
        }

        // Captura en diagonal
        if (difColumna == 1 && difFila == direccion) {
            Pieza piezaDestino = tablero.getPieza(nuevaPosicion);
            return piezaDestino != null && piezaDestino.esBlanca() != this.esBlanca;
        }

        return false;
    }

    @Override
    public String toString() {
        return esBlanca ? "♙" : "♟";
    }
}
