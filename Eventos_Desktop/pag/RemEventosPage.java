package pag;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemEventosPage extends JPanel {
    private JTextField idTextField;
    private JButton removerButton;

    public RemEventosPage() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new FlowLayout());

        JLabel idLabel = new JLabel("ID del Evento:");
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
                    URL url = new URL("http://eventos.somee.com/api/Eventos/" + id);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("DELETE");

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        JOptionPane.showMessageDialog(RemEventosPage.this, "Evento eliminado con éxito.");
                        idTextField.setText("");
                    } else {
                        if (connection.getErrorStream() != null) {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            reader.close();
                            JOptionPane.showMessageDialog(RemEventosPage.this, "Error al eliminar el evento: " + response.toString());
                        } else {
                            JOptionPane.showMessageDialog(RemEventosPage.this, "Error al eliminar el evento: Respuesta nula del servidor");
                        }
                    }
                    connection.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RemEventosPage.this, "Error al eliminar el evento: " + ex.getMessage());
                }
            }
        });
    }
}
