package ajedrez.modelo;

public abstract class Pieza {
    protected boolean esBlanca;
    protected Posicion posicion;

    public Pieza(boolean esBlanca, Posicion posicion) {
        this.esBlanca = esBlanca;
        this.posicion = posicion;
    }

    public abstract boolean esMovimientoValido(Posicion nuevaPosicion, Tablero tablero);

    public boolean esBlanca() {
        return esBlanca;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
}
