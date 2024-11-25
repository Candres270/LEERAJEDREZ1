package ajedrez.controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LectorPGN {
    private static final Pattern PATRON_MOVIMIENTO = Pattern.compile("\\d+\\.\\s*([^\\s]+)(?:\\s+([^\\s]+))?");

    public static List<String> leerMovimientos(String nombreArchivo) throws IOException {
        List<String> movimientos = new ArrayList<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                if (linea.trim().startsWith("[")) continue; // Saltar metadatos

                Matcher matcher = PATRON_MOVIMIENTO.matcher(linea);
                while (matcher.find()) {
                    movimientos.add(matcher.group(1)); // Movimiento de las blancas
                    if (matcher.group(2) != null) {
                        movimientos.add(matcher.group(2)); // Movimiento de las negras
                    }
                }
            }
        }
        return movimientos;
    }
}
