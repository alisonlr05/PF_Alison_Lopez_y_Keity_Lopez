import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PanelInicio extends JFrame {
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnIngresar;

    public PanelInicio() {
        setTitle("Autenticación de Usuarios");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear el panel y configurar la imagen de fondo
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = new ImageIcon("Fondo.jpg");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new GridBagLayout());

        // Crear y agregar componentes de autenticación
        JLabel lblUsuario = new JLabel("Usuario:");
        JLabel lblPassword = new JLabel("Contraseña:");
        txtUsuario = new JTextField(15);
        txtPassword = new JPasswordField(15);
        btnIngresar = new JButton("Ingresar", new ImageIcon("icono_ingresar.png"));

        // Layout para el formulario de autenticación
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
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
        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });
    }

   private void autenticarUsuario() {
    String usuario = txtUsuario.getText();
    String password = new String(txtPassword.getPassword());

    try (Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tu_base_de_datos", "usuario", "contraseña")) {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE nombreUsuario = ? AND password = ?";
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
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error de conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }

     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PanelInicio().setVisible(true);
        });
    }

}//Fin de la clase PanelInicio
