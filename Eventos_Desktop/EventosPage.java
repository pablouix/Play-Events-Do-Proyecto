import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import pag.AddEventosPage;
import pag.AddZonaDetallePage;
import pag.RemZonaDetallePage;
import pag.RemEventosPage;
import pag.EstadisticasPage;

public class EventosPage {
    private static JTextArea eventosTextArea;

    public EventosPage(){
        JFrame ventana = new JFrame("Play Events Do Adm");
        ventana.setSize(600, 400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLayout(new BorderLayout());

        eventosTextArea = new JTextArea();
        eventosTextArea.setEditable(false);
        eventosTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(eventosTextArea);

        ventana.add(scrollPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu agregarMenu = new JMenu("Agregar");
        JMenu borrarMenu = new JMenu("Borrar");
        JMenu estadisticasMenu = new JMenu("Estadísticas");

        JMenuItem agregarEventoItem = new JMenuItem("Agregar Evento");
        agregarEventoItem.addActionListener(e -> abrirVentana("Agregar Evento"));
        agregarMenu.add(agregarEventoItem);

        JMenuItem agregarDetalleItem = new JMenuItem("Agregar Detalle");
        agregarDetalleItem.addActionListener(e -> abrirVentana("Agregar Detalle"));
        agregarMenu.add(agregarDetalleItem);

        JMenuItem borrarEventoItem = new JMenuItem("Borrar Evento");
        borrarEventoItem.addActionListener(e -> abrirVentana("Borrar Evento"));
        borrarMenu.add(borrarEventoItem);

        JMenuItem borrarZonaDetalleItem = new JMenuItem("Borrar Zona Detalle");
        borrarZonaDetalleItem.addActionListener(e -> abrirVentana("Borrar Zona Detalle"));
        borrarMenu.add(borrarZonaDetalleItem);

        JMenuItem estadisticasItem = new JMenuItem("Estadísticas de Eventos");
        estadisticasItem.addActionListener(e -> abrirVentana("Estadísticas"));
        estadisticasMenu.add(estadisticasItem);

        JMenuItem actualizarListaItem = new JMenuItem("Actualizar");
        actualizarListaItem.addActionListener(e -> actualizarListaEventos());
        menuBar.add(actualizarListaItem);

        menuBar.add(agregarMenu);
        menuBar.add(borrarMenu);
        menuBar.add(estadisticasMenu);
        ventana.setJMenuBar(menuBar);

        actualizarListaEventos();

        ventana.setVisible(true);
    }

    private static void actualizarListaEventos() {
        eventosTextArea.setText("");

        try {
            String apiUrl = "http://eventos.somee.com/api/eventos";
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONArray eventosArray = new JSONArray(response.body());

            for (int i = 0; i < eventosArray.length(); i++) {
                JSONObject eventoObj = eventosArray.getJSONObject(i);
                StringBuilder eventoData = new StringBuilder();

                int eventoId = eventoObj.getInt("id");
                eventoData.append("Evento ID: ").append(eventoId).append("\n");
                eventoData.append("Nombre: ").append(eventoObj.getString("nombre")).append("\n");
                eventoData.append("Descripción: ").append(eventoObj.getString("descripcion")).append("\n");
                eventoData.append("Lugar: ").append(eventoObj.getString("lugar")).append("\n");
                eventoData.append("Organizador: ").append(eventoObj.getString("organizador")).append("\n");
                eventoData.append("Es Público: ").append(eventoObj.getBoolean("esPublico")).append("\n");
                eventoData.append("Fecha: ").append(eventoObj.getString("fecha")).append("\n");
                eventoData.append("Hora: ").append(eventoObj.getString("hora")).append("\n\n");

                JSONArray zonaDetallesArray = eventoObj.getJSONArray("zonaDetalles");
                eventoData.append(" Zonas Detalle:\n");
                for (int j = 0; j < zonaDetallesArray.length(); j++) {
                    JSONObject zonaDetalleObj = zonaDetallesArray.getJSONObject(j);
                    int zonaDetalleId = zonaDetalleObj.getInt("id");
                    eventoData.append("  - Zona Detalle ID: ").append(zonaDetalleId).append("\n");
                    eventoData.append("    Zona: ").append(zonaDetalleObj.getString("nombre")).append("\n");
                    eventoData.append("    Capacidad: ").append(zonaDetalleObj.getInt("capacidad")).append(" Personas\n");
                    eventoData.append("    Precio: $").append(zonaDetalleObj.getDouble("precio")).append("\n");
                    eventoData.append("    Compradas: ").append(zonaDetalleObj.getInt("compradas")).append("\n");
                    eventoData.append("    Disponibles: ").append(zonaDetalleObj.getInt("disponibles")).append("\n");
                    eventoData.append("    Total Comprado: $").append(zonaDetalleObj.getDouble("totalComprado")).append("\n\n");
                }

                eventosTextArea.append(eventoData.toString());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void abrirVentana(String titulo) {
        JFrame ventana = new JFrame(titulo);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JPanel panel = new JPanel();

        Dimension tamanoPanel;
        switch (titulo) {
            case "Agregar Evento":
                tamanoPanel = new Dimension(400, 440);
                panel.add(new AddEventosPage());
                break;
            case "Agregar Detalle":
                tamanoPanel = new Dimension(400, 500);
                panel.add(new AddZonaDetallePage());
                break;
            case "Borrar Evento":
                tamanoPanel = new Dimension(400, 100);
                panel.add(new RemEventosPage());
                break;
            case "Borrar Zona Detalle":
                tamanoPanel = new Dimension(400, 100);
                panel.add(new RemZonaDetallePage());
                break;
            case "Estadísticas":
                tamanoPanel = new Dimension(400, 200);
                panel.add(new EstadisticasPage());
                break;
            default:
                tamanoPanel = new Dimension(400, 100);
                break;
        }
        panel.setPreferredSize(tamanoPanel);

        ventana.getContentPane().add(panel);
        ventana.pack();
        ventana.setVisible(true);
    }
}
