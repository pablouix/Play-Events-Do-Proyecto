package pag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RemZonaDetallePage extends JPanel {
    private JTextField idTextField;
    private JButton removerButton;

    public RemZonaDetallePage() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("ID de Zona Detalle:");
        idTextField = new JTextField(10);
        removerButton = new JButton("Eliminar");

        formPanel.add(idLabel);
        formPanel.add(idTextField);
        formPanel.add(removerButton);

        add(formPanel, BorderLayout.CENTER);

    
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTextField.getText();
               
                try {
                    String apiUrl = "http://eventos.somee.com/api/ZonasDetalles/" + id;
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(apiUrl))
                            .DELETE()
                            .build();

                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    if (response.statusCode() == 200) {
                        JOptionPane.showMessageDialog(RemZonaDetallePage.this, "Zona Detalle eliminada con éxito.");
                        idTextField.setText(""); 
                    } else {
                        JOptionPane.showMessageDialog(RemZonaDetallePage.this, "Error al eliminar la Zona Detalle: " + response.body());
                    }
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RemZonaDetallePage.this, "Error al eliminar la Zona Detalle: " + ex.getMessage());
                }
            }
        });
    }
}
