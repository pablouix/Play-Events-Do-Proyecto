package pag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class AddZonaDetallePage extends JPanel {
    private JTextField idEventoField;
    private JTextField nombreField;
    private JTextField capacidadField;
    private JTextField precioField;
    private JButton submitButton;

    public AddZonaDetallePage() {
        setLayout(new GridLayout(5, 2, 10, 10));

        JLabel idEventoLabel = new JLabel("ID del Evento:");
        idEventoLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        idEventoField = new JTextField(15);
        JLabel nombreLabel = new JLabel("Nombre:");
        nombreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nombreField = new JTextField(15);
        JLabel capacidadLabel = new JLabel("Capacidad:");
        capacidadLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        capacidadField = new JTextField(15);
        JLabel precioLabel = new JLabel("Precio:");
        precioLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        precioField = new JTextField(15);

        add(idEventoLabel);
        add(idEventoField);
        add(nombreLabel);
        add(nombreField);
        add(capacidadLabel);
        add(capacidadField);
        add(precioLabel);
        add(precioField);

        submitButton = new JButton("Agregar");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                String idEventoText = idEventoField.getText();
                String nombre = nombreField.getText();
                String capacidadText = capacidadField.getText();
                String precioText = precioField.getText();

               
                if (idEventoText.isEmpty() || nombre.isEmpty() || capacidadText.isEmpty() || precioText.isEmpty()) {
                   
                    JOptionPane.showMessageDialog(AddZonaDetallePage.this, "Por favor, completa todos los campos");
                    return;
                }

               
                int idEvento = Integer.parseInt(idEventoText);
                int capacidad = Integer.parseInt(capacidadText);
                double precio = Double.parseDouble(precioText);

                JSONObject zonaDetalleJson = new JSONObject();
                zonaDetalleJson.put("idEvento", idEvento);
                zonaDetalleJson.put("nombre", nombre);
                zonaDetalleJson.put("capacidad", capacidad);
                zonaDetalleJson.put("precio", precio);

               
                String json = zonaDetalleJson.toString();

               
                try {
                    URL url = new URL("http://eventos.somee.com/api/ZonasDetalles");
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
                        System.out.println("Zona detalle guardado exitosamente en la API. Respuesta: " + response.toString());
                    } else {
                        System.out.println("Error al guardar la zona detalle en la API. Código de respuesta: " + responseCode);
                    }
                    connection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

               
                idEventoField.setText("");
                nombreField.setText("");
                capacidadField.setText("");
                precioField.setText("");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        add(buttonPanel);
    }
}
