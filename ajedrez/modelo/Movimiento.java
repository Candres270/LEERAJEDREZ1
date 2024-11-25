package ajedrez.modelo;

public class Movimiento {
    private Posicion origen;
    private Posicion destino;
    private Pieza pieza;
    private Pieza piezaCapturada;

    public Movimiento(Posicion origen, Posicion destino, Pieza pieza, Pieza piezaCapturada) {
        this.origen = origen;
        this.destino = destino;
        this.pieza = pieza;
        this.piezaCapturada = piezaCapturada;
    }

    public Posicion getOrigen() { return origen; }
    public Posicion getDestino() { return destino; }
    public Pieza getPieza() { return pieza; }
    public Pieza getPiezaCapturada() { return piezaCapturada; }
}

