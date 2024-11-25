package ajedrez.modelo;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private Pieza[][] casillas;
    private List<Movimiento> movimientos;
    private int movimientoActual;
    private boolean turnoBlancas;

    public Tablero() {
        casillas = new Pieza[8][8];
        movimientos = new ArrayList<>();
        movimientoActual = -1;
        turnoBlancas = true;
        inicializarTablero();
    }

    private void inicializarTablero() {
        // Inicializar piezas negras
        casillas[0][0] = new Torre(false, new Posicion(0, 0));
        casillas[0][1] = new Caballo(false, new Posicion(0, 1));
        casillas[0][2] = new Alfil(false, new Posicion(0, 2));
        casillas[0][3] = new Dama(false, new Posicion(0, 3));
        casillas[0][4] = new Rey(false, new Posicion(0, 4));
        casillas[0][5] = new Alfil(false, new Posicion(0, 5));
        casillas[0][6] = new Caballo(false, new Posicion(0, 6));
        casillas[0][7] = new Torre(false, new Posicion(0, 7));

        // Peones negros
        for (int i = 0; i < 8; i++) {
            casillas[1][i] = new Peon(false, new Posicion(1, i));
        }

        // Peones blancos
        for (int i = 0; i < 8; i++) {
            casillas[6][i] = new Peon(true, new Posicion(6, i));
        }

        // Inicializar piezas blancas
        casillas[7][0] = new Torre(true, new Posicion(7, 0));
        casillas[7][1] = new Caballo(true, new Posicion(7, 1));
        casillas[7][2] = new Alfil(true, new Posicion(7, 2));
        casillas[7][3] = new Dama(true, new Posicion(7, 3));
        casillas[7][4] = new Rey(true, new Posicion(7, 4));
        casillas[7][5] = new Alfil(true, new Posicion(7, 5));
        casillas[7][6] = new Caballo(true, new Posicion(7, 6));
        casillas[7][7] = new Torre(true, new Posicion(7, 7));
    }

    public boolean esTurnoBlancas() {
        return turnoBlancas;
    }

    public Pieza getPieza(Posicion posicion) {
        if (!posicionValida(posicion)) return null;
        return casillas[posicion.getFila()][posicion.getColumna()];
    }

    public boolean posicionValida(Posicion pos) {
        return pos != null && pos.getFila() >= 0 && pos.getFila() < 8 &&
                pos.getColumna() >= 0 && pos.getColumna() < 8;
    }

    public boolean hayPiezasEntre(Posicion origen, Posicion destino) {
        if (!posicionValida(origen) || !posicionValida(destino)) {
            return true;
        }

        int difFila = destino.getFila() - origen.getFila();
        int difColumna = destino.getColumna() - origen.getColumna();

        // Verificar si el movimiento es válido (recto o diagonal)
        boolean movimientoRecto = difFila == 0 || difColumna == 0;
        boolean movimientoDiagonal = Math.abs(difFila) == Math.abs(difColumna);

        if (!movimientoRecto && !movimientoDiagonal) {
            return true;
        }

        int pasoFila = Integer.compare(difFila, 0);
        int pasoColumna = Integer.compare(difColumna, 0);

        int fila = origen.getFila() + pasoFila;
        int columna = origen.getColumna() + pasoColumna;

        // Verificar cada casilla en el camino
        while (fila != destino.getFila() || columna != destino.getColumna()) {
            if (casillas[fila][columna] != null) {
                return true;
            }
            fila += pasoFila;
            columna += pasoColumna;
        }

        return false;
    }

    public boolean esMovimientoLegal(Movimiento movimiento) {
        if (!posicionValida(movimiento.getOrigen()) || !posicionValida(movimiento.getDestino())) {
            return false;
        }

        Pieza pieza = getPieza(movimiento.getOrigen());
        Pieza piezaDestino = getPieza(movimiento.getDestino());

        // Verificaciones básicas
        if (pieza == null) return false;
        if (pieza.esBlanca() != turnoBlancas) return false;
        if (piezaDestino != null && piezaDestino.esBlanca() == pieza.esBlanca()) return false;

        // Verificar si el movimiento es válido para la pieza
        if (!pieza.esMovimientoValido(movimiento.getDestino(), this)) return false;

        // Verificar si hay piezas en el camino (excepto para el caballo)
        if (!(pieza instanceof Caballo) && hayPiezasEntre(movimiento.getOrigen(), movimiento.getDestino())) {
            return false;
        }

        return true;
    }

    public boolean moverPieza(Movimiento movimiento) {
        if (!esMovimientoLegal(movimiento)) {
            return false;
        }

        Pieza pieza = getPieza(movimiento.getOrigen());
        Pieza piezaCapturada = getPieza(movimiento.getDestino());

        // Realizar el movimiento
        casillas[movimiento.getDestino().getFila()][movimiento.getDestino().getColumna()] = pieza;
        casillas[movimiento.getOrigen().getFila()][movimiento.getOrigen().getColumna()] = null;
        pieza.setPosicion(movimiento.getDestino());

        // Si es un peón, marcar que ya no es su primer movimiento
        if (pieza instanceof Peon) {
            ((Peon) pieza).setPrimerMovimiento(false);
        }

        // Guardar el movimiento en el historial
        movimientos.add(movimiento);
        movimientoActual++;

        // Cambiar el turno
        turnoBlancas = !turnoBlancas;

        return true;
    }

    public boolean deshacerUltimoMovimiento() {
        if (movimientoActual < 0) return false;

        Movimiento movimiento = movimientos.get(movimientoActual);

        // Restaurar las piezas a su posición original
        casillas[movimiento.getOrigen().getFila()][movimiento.getOrigen().getColumna()] = movimiento.getPieza();
        casillas[movimiento.getDestino().getFila()][movimiento.getDestino().getColumna()] = movimiento.getPiezaCapturada();
        movimiento.getPieza().setPosicion(movimiento.getOrigen());

        // Si es un peón que se movió desde su posición inicial, restaurar primerMovimiento
        if (movimiento.getPieza() instanceof Peon) {
            Peon peon = (Peon) movimiento.getPieza();
            if (movimiento.getOrigen().getFila() == (peon.esBlanca() ? 6 : 1)) {
                peon.setPrimerMovimiento(true);
            }
        }

        movimientoActual--;
        turnoBlancas = !turnoBlancas;
        return true;
    }
}


