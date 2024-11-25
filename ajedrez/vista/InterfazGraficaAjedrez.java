package ajedrez.vista;

import ajedrez.controlador.InterpretadorPGN;
import ajedrez.controlador.LectorPGN;
import ajedrez.modelo.Posicion;
import ajedrez.modelo.Tablero;
import ajedrez.modelo.Pieza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class InterfazGraficaAjedrez extends JFrame {
    private Tablero tablero;
    private JPanel panelTablero;
    private JButton botonSiguiente;
    private JButton botonAnterior;
    private JButton botonReiniciar;
    private JLabel labelEstado;
    private List<String> movimientos;
    private int movimientoActual;
    private InterpretadorPGN interpretador;
    private static final String RUTA_PARTIDA = "partida.pgn"; // Ruta al archivo de la partida

    public InterfazGraficaAjedrez() {
        tablero = new Tablero();
        interpretador = new InterpretadorPGN(tablero);
        movimientoActual = 0;
        cargarPartida();
        inicializarInterfaz();
    }

    private void cargarPartida() {
        try {
            movimientos = LectorPGN.leerMovimientos(RUTA_PARTIDA);
            if (movimientos.isEmpty()) {
                labelEstado.setText("Error: No se encontraron movimientos en el archivo");
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la partida: " + e.getMessage());
            movimientos = null;
        }
    }

    private void inicializarInterfaz() {
        setTitle("Visualizador de Partidas de Ajedrez");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de estado
        labelEstado = new JLabel("Movimiento 0", SwingConstants.CENTER);
        add(labelEstado, BorderLayout.NORTH);

        // Crear panel del tablero
        panelTablero = new JPanel(new GridLayout(8, 8));
        actualizarVistaTablero();
        add(panelTablero, BorderLayout.CENTER);

        // Crear panel de control
        JPanel panelControl = new JPanel();
        botonAnterior = new JButton("Anterior");
        botonSiguiente = new JButton("Siguiente");
        botonReiniciar = new JButton("Reiniciar");

        botonAnterior.addActionListener(this::onAnterior);
        botonSiguiente.addActionListener(this::onSiguiente);
        botonReiniciar.addActionListener(this::onReiniciar);

        panelControl.add(botonAnterior);
        panelControl.add(botonSiguiente);
        panelControl.add(botonReiniciar);
        add(panelControl, BorderLayout.SOUTH);

        setSize(600, 650);
        setLocationRelativeTo(null);
        actualizarBotones();
    }

    private void actualizarVistaTablero() {
        panelTablero.removeAll();

        for (int fila = 0; fila < 8; fila++) {
            for (int columna = 0; columna < 8; columna++) {
                JPanel casilla = crearCasilla(fila, columna);
                panelTablero.add(casilla);
            }
        }

        if (movimientos != null) {
            labelEstado.setText("Movimiento " + movimientoActual + " de " + movimientos.size());
        }

        panelTablero.revalidate();
        panelTablero.repaint();
    }

    private JPanel crearCasilla(int fila, int columna) {
        JPanel casilla = new JPanel(new BorderLayout());
        casilla.setBackground((fila + columna) % 2 == 0 ? new Color(240, 217, 181) : new Color(181, 136, 99));

        Posicion pos = new Posicion(fila, columna);
        Pieza pieza = tablero.getPieza(pos);

        if (pieza != null) {
            JLabel labelPieza = new JLabel(pieza.toString(), SwingConstants.CENTER);
            labelPieza.setFont(new Font("Arial", Font.BOLD, 40));
            labelPieza.setForeground(pieza.esBlanca() ? Color.WHITE : Color.BLACK);
            casilla.add(labelPieza, BorderLayout.CENTER);
        }

        return casilla;
    }

    private void onAnterior(ActionEvent e) {
        if (movimientoActual > 0) {
            tablero.deshacerUltimoMovimiento();
            movimientoActual--;
            actualizarVistaTablero();
            actualizarBotones();
        }
    }

    private void onSiguiente(ActionEvent e) {
        if (movimientos != null && movimientoActual < movimientos.size()) {
            String movimiento = movimientos.get(movimientoActual);
            if (interpretador.interpretarMovimiento(movimiento)) {
                movimientoActual++;
                actualizarVistaTablero();
                actualizarBotones();
            }
        }
    }

    private void onReiniciar(ActionEvent e) {
        tablero = new Tablero();
        interpretador = new InterpretadorPGN(tablero);
        movimientoActual = 0;
        actualizarVistaTablero();
        actualizarBotones();
    }

    private void actualizarBotones() {
        botonAnterior.setEnabled(movimientoActual > 0);
        botonSiguiente.setEnabled(movimientos != null && movimientoActual < movimientos.size());
    }
}