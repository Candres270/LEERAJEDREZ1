// InterpretadorPGN.java
package ajedrez.controlador;

import ajedrez.modelo.Movimiento;
import ajedrez.modelo.Posicion;
import ajedrez.modelo.Tablero;
import ajedrez.modelo.*;

public class InterpretadorPGN {
    private Tablero tablero;

    public InterpretadorPGN(Tablero tablero) {
        this.tablero = tablero;
    }

    public boolean interpretarMovimiento(String movimiento) {
        // Ignorar resultado de la partida si está presente
        if (movimiento.equals("1-0") || movimiento.equals("0-1") ||
                movimiento.equals("1/2-1/2") || movimiento.equals("*")) {
            return false;
        }

        // Eliminar el símbolo de jaque o mate si existe
        movimiento = movimiento.replace("+", "").replace("#", "");

        try {
            // Casos especiales
            if (movimiento.equals("O-O")) {  // Enroque corto
                return realizarEnroqueCortoPGN();
            }
            if (movimiento.equals("O-O-O")) {  // Enroque largo
                return realizarEnroqueLargoPGN();
            }

            // Identificar los componentes del movimiento
            char tipoPieza = 'P';  // Por defecto es peón
            String destinoStr;
            boolean esCaptura = movimiento.contains("x");

            // Determinar tipo de pieza si no es peón
            if (Character.isUpperCase(movimiento.charAt(0))) {
                tipoPieza = movimiento.charAt(0);
                movimiento = movimiento.substring(1);
            }

            // Obtener casilla de destino (últimos 2 caracteres)
            destinoStr = movimiento.substring(movimiento.length() - 2);
            Posicion destino = Posicion.desdeNotacionAlgebraica(destinoStr);

            // Buscar la pieza que puede realizar este movimiento
            Pieza piezaAMover = encontrarPieza(tipoPieza, destino, movimiento);

            if (piezaAMover != null) {
                Movimiento mov = new Movimiento(
                        piezaAMover.getPosicion(),
                        destino,
                        piezaAMover,
                        tablero.getPieza(destino)
                );
                tablero.moverPieza(mov);
                return true;
            }

        } catch (Exception e) {
            System.err.println("Error al interpretar movimiento: " + movimiento);
            e.printStackTrace();
        }
        return false;
    }

    private Pieza encontrarPieza(char tipoPieza, Posicion destino, String movimiento) {
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                Pieza pieza = tablero.getPieza(new Posicion(fila, col));
                if (pieza != null && coincidePieza(pieza, tipoPieza) &&
                        pieza.esMovimientoValido(destino, tablero)) {
                    // Verificar información adicional (columna o fila específica)
                    if (coincideInformacionAdicional(pieza, movimiento)) {
                        return pieza;
                    }
                }
            }
        }
        return null;
    }

    private boolean coincidePieza(Pieza pieza, char tipoPieza) {
        return (tipoPieza == 'P' && pieza instanceof Peon) ||
                (tipoPieza == 'R' && pieza instanceof Torre) ||
                (tipoPieza == 'N' && pieza instanceof Caballo) ||
                (tipoPieza == 'B' && pieza instanceof Alfil) ||
                (tipoPieza == 'Q' && pieza instanceof Dama) ||
                (tipoPieza == 'K' && pieza instanceof Rey);
    }

    private boolean coincideInformacionAdicional(Pieza pieza, String movimiento) {
        // Si hay información específica de columna o fila en el movimiento
        if (movimiento.length() > 2) {
            char info = movimiento.charAt(0);
            if (Character.isLowerCase(info)) {
                // Es una columna específica
                return pieza.getPosicion().getColumna() == (info - 'a');
            } else if (Character.isDigit(info)) {
                // Es una fila específica
                return pieza.getPosicion().getFila() == (8 - (info - '0'));
            }
        }
        return true;
    }

    private boolean realizarEnroqueCortoPGN() {
        // Implementar lógica de enroque corto
        return false;
    }

    private boolean realizarEnroqueLargoPGN() {
        // Implementar lógica de enroque largo
        return false;
    }
}
