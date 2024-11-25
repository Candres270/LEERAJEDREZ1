package ajedrez.modelo;

public class Posicion {
    private int fila;
    private int columna;

    public Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public static Posicion desdeNotacionAlgebraica(String algebraica) {
        char columnaChar = algebraica.charAt(0);
        char filaChar = algebraica.charAt(1);

        int columna = columnaChar - 'a';
        int fila = 8 - (filaChar - '0');

        return new Posicion(fila, columna);
    }
}
