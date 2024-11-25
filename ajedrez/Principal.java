package ajedrez;

import ajedrez.controlador.LectorPGN;
import ajedrez.vista.InterfazGraficaAjedrez;

import javax.swing.SwingUtilities;
import java.io.IOException;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        // Aseguramos que la interfaz gráfica se cree en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            InterfazGraficaAjedrez ventana = new InterfazGraficaAjedrez();
            ventana.setVisible(true);

            // Si quieres cargar un archivo PGN al inicio, puedes hacerlo aquí
            try {
                // Ejemplo de carga de archivo PGN
                String rutaArchivo = "partida.pgn"; // Ajusta la ruta según tu archivo
                List<String> movimientos = LectorPGN.leerMovimientos(rutaArchivo);
                System.out.println("Movimientos cargados: " + movimientos);
                // Aquí puedes procesar los movimientos...
            } catch (IOException e) {
                System.err.println("Error al cargar el archivo PGN: " + e.getMessage());
            }
        });
    }
}