package pag;

import javax.swing.*;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class AddEventosPage extends JPanel {
    private JLabel etiquetaNombre;
    private JTextField campoNombre;
    private JLabel etiquetaImagen;
    private JTextField campoImagen;
    private JLabel etiquetaFecha;
    private JTextField campoFecha;
    private JLabel etiquetaHora;
    private JTextField campoHora;
    private JLabel etiquetaDescripcion;
    private JTextField campoDescripcion;
    private JLabel etiquetaLugar;
    private JTextField campoLugar;
    private JLabel etiquetaOrganizador;
    private JTextField campoOrganizador;
    private JLabel etiquetaEsPublico;
    private JComboBox<String> comboEsPublico;
    private JButton botonAgregar;

    public AddEventosPage() {
        setLayout(new GridLayout(9, 2, 10, 10));

        etiquetaNombre = new JLabel("Nombre:");
        etiquetaNombre.setHorizontalAlignment(SwingConstants.RIGHT);
        campoNombre = new JTextField(15);

        etiquetaImagen = new JLabel("Imagen:");
        etiquetaImagen.setHorizontalAlignment(SwingConstants.RIGHT);
        campoImagen = new JTextField(15);

        etiquetaFecha = new JLabel("Fecha:");
        etiquetaFecha.setHorizontalAlignment(SwingConstants.RIGHT);
        campoFecha = new JTextField(15);

        etiquetaHora = new JLabel("Hora:");
        etiquetaHora.setHorizontalAlignment(SwingConstants.RIGHT);
        campoHora = new JTextField(15);
        
        etiquetaDescripcion = new JLabel("Descripción:");
        etiquetaDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
        campoDescripcion = new JTextField(15);
        
        etiquetaLugar = new JLabel("Lugar:");
        etiquetaLugar.setHorizontalAlignment(SwingConstants.RIGHT);
        campoLugar = new JTextField(15);
        
        etiquetaOrganizador = new JLabel("Organizador:");
        etiquetaOrganizador.setHorizontalAlignment(SwingConstants.RIGHT);
        campoOrganizador = new JTextField(15);

        etiquetaEsPublico = new JLabel("Es Público:");
        etiquetaEsPublico.setHorizontalAlignment(SwingConstants.RIGHT);
        comboEsPublico = new JComboBox<>(new String[]{"true", "false"});

        add(etiquetaNombre);
        add(campoNombre);
        add(etiquetaImagen);
        add(campoImagen);
        add(etiquetaFecha);
        add(campoFecha);
        add(etiquetaHora);
        add(campoHora);
        add(etiquetaDescripcion);
        add(campoDescripcion);
        add(etiquetaLugar);
        add(campoLugar);
        add(etiquetaOrganizador);
        add(campoOrganizador);
        add(etiquetaEsPublico);
        add(comboEsPublico);

        botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = campoNombre.getText();
                String urlImagen = campoImagen.getText();
                String fecha = campoFecha.getText();
                String hora = campoHora.getText();
                String descripcion = campoDescripcion.getText();
                String lugar = campoLugar.getText();
                String organizador = campoOrganizador.getText();
                boolean esPublico = comboEsPublico.getSelectedItem().equals("true");

                if (nombre.isEmpty() || urlImagen.isEmpty() || fecha.isEmpty() || hora.isEmpty() || descripcion.isEmpty() || lugar.isEmpty() || organizador.isEmpty()) {
                    JOptionPane.showMessageDialog(AddEventosPage.this, "Por favor, completa todos los campos");
                    return;
                }

                JSONObject eventoJson = new JSONObject();
                eventoJson.put("id", 0);
                eventoJson.put("imagen", urlImagen);
                eventoJson.put("nombre", nombre);
                eventoJson.put("fecha", fecha);
                eventoJson.put("hora", hora);
                eventoJson.put("descripcion", descripcion);
                eventoJson.put("lugar", lugar);
                eventoJson.put("organizador", organizador);
                eventoJson.put("esPublico", esPublico);

                String json = eventoJson.toString();

                try {
                    URL url = new URL("http://eventos.somee.com/api/eventos");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    outputStream.writeBytes(json);
                    outputStream.flush();
                    outputStream.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        System.out.println("Evento guardado exitosamente en la API. Respuesta: " + response.toString());
                    } else {
                        System.out.println("Error al guardar el evento en la API. Código de respuesta: " + responseCode);
                    }
                    connection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel panelBoton = new JPanel();
        panelBoton.add(botonAgregar);

        add(panelBoton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Agregar Eventos");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.getContentPane().add(new AddEventosPage());
                frame.setVisible(true);
            }
        });
    }
}
