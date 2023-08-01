package pag;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class EstadisticasPage extends JPanel {
    public EstadisticasPage() {
        setLayout(new BorderLayout());

        JTextArea estadisticasTextArea = new JTextArea();
        estadisticasTextArea.setEditable(false);
        estadisticasTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(estadisticasTextArea);
        add(scrollPane, BorderLayout.CENTER);

        try {
            String apiUrl = "http://eventos.somee.com/api/eventos";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONArray eventosArray = new JSONArray(response.body());
            int totalEventos = eventosArray.length();
            int eventosPublicos = 0;
            int eventosPrivados = 0;
            double totalComprado = 0.0;
            int totalDisponibles = 0;
            int totalCapacidad = 0;
            int totalBoletasVendidas = 0;

            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoObj = eventosArray.getJSONObject(i);
                boolean esPublico = eventoObj.getBoolean("esPublico");
                JSONArray zonaDetallesArray = eventoObj.getJSONArray("zonaDetalles");

                if (esPublico) {
                    eventosPublicos++;
                } else {
                    eventosPrivados++;
                }

                for (int j = 0; j < zonaDetallesArray.length(); j++) {
                    JSONObject zonaDetalleObj = zonaDetallesArray.getJSONObject(j);
                    int compradas = zonaDetalleObj.getInt("compradas");
                    int disponibles = zonaDetalleObj.getInt("disponibles");
                    int capacidad = zonaDetalleObj.getInt("capacidad");
                    double totalCompradoZona = zonaDetalleObj.getDouble("totalComprado");

                    totalComprado += totalCompradoZona;
                    totalDisponibles += disponibles;
                    totalCapacidad += capacidad;
                    totalBoletasVendidas += compradas;
                }
            }

            StringBuilder estadisticasData = new StringBuilder();
            estadisticasData.append("Cantidad de Eventos: ").append(totalEventos).append("\n");
            estadisticasData.append("Eventos Públicos: ").append(eventosPublicos).append("\n");
            estadisticasData.append("Eventos Privados: ").append(eventosPrivados).append("\n");
            estadisticasData.append("Total Vendidos: $").append(totalComprado).append("\n");
            estadisticasData.append("Total Disponibles: ").append(totalDisponibles).append("\n");
            estadisticasData.append("Total Capacidad de Todas las Zonas: ").append(totalCapacidad).append("\n");
            estadisticasData.append("Cantidad de Boletas Vendidas: ").append(totalBoletasVendidas).append("\n");

            estadisticasTextArea.setText(estadisticasData.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
