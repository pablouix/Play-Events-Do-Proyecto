import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        setTitle("Play Events Do Adm");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Play Events Do Adm");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 5, 5));

        JLabel usernameLabel = new JLabel("Usuario:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Clave:");
        passwordField = new JPasswordField(15);

        centerPanel.add(usernameLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Iniciar sesión");
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals("User") && password.equals("1234")) {
                    dispose();
                    EventosPage eventosPage = new EventosPage();
                } else {
                    JOptionPane.showMessageDialog(null, "Datos incorrectos. Inténtelo de nuevo");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();
    }
}
