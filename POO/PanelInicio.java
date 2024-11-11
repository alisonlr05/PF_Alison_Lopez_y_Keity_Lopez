import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PanelInicio extends JFrame {
    private final JTextField txtUsuario;
    private final JPasswordField txtPassword;
    private final JButton btnIngresar;

    public PanelInicio() {
        setTitle("Autenticación de Usuarios");
        setSize(600, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel con fondo de imagen
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = new ImageIcon("imagenes/Fondo.jpg");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new GridBagLayout());

        // Crear etiquetas
        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblPassword = new JLabel("Contraseña:");
        lblUsuario.setForeground(Color.WHITE);
        lblPassword.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 16));  // Tamaño más grande
        lblPassword.setFont(new Font("Arial", Font.BOLD, 16));

        // Crear los campos de texto 
        txtUsuario = new JTextField(20);
        txtPassword = new JPasswordField(20);
        txtUsuario.setPreferredSize(new Dimension(200, 30));
        txtPassword.setPreferredSize(new Dimension(200, 30));

        // Crear el botón con un icono redimensionado
        btnIngresar = new JButton("Ingresar", new ImageIcon(new ImageIcon("imagenes/ingresar.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        btnIngresar.setPreferredSize(new Dimension(200, 50));
        btnIngresar.setFont(new Font("Arial", Font.BOLD, 14));  // Tamaño de letra más grande

        // Layout para los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFondo.add(lblUsuario, gbc);

        gbc.gridx = 1;
        panelFondo.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFondo.add(lblPassword, gbc);

        gbc.gridx = 1;
        panelFondo.add(txtPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panelFondo.add(btnIngresar, gbc);

        add(panelFondo);

        // Acción de autenticación al presionar "Ingresar"
        btnIngresar.addActionListener((ActionEvent e) -> {
            autenticarUsuario();
        });
    }

   private void autenticarUsuario() {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());
    
        // Usar la clase ConexionDB para establecer la conexión
        try (Connection conexion = ConexionDB.conectar()) {
            if (conexion != null) {
                String sql = "SELECT COUNT(*) FROM Usuarios WHERE usuario = ? AND contraseña = ?";
                PreparedStatement stmt = conexion.prepareStatement(sql);
                stmt.setString(1, usuario);
                stmt.setString(2, password);
    
                ResultSet rs = stmt.executeQuery();
                rs.next();
                int count = rs.getInt(1);
    
                if (count == 1) {
                    JOptionPane.showMessageDialog(this, "Bienvenido, " + usuario);
                    // Abre el menú principal aquí
                    new MenuPrincipal().setVisible(true);
                    dispose(); // Cierra la ventana de PanelInicio
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de conexión con la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelInicio().setVisible(true);
        });
    }

}//Fin de la clase PanelInicio
