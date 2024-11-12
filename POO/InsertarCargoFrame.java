import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class InsertarCargoFrame extends JFrame {
    public InsertarCargoFrame() {
        setTitle("Insertar Cargo");
        setSize(600, 400); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("imagenes/Fondo.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());

        // Crear título
        JLabel title = new JLabel("Insertar Cargo");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        // Estilo para etiquetas y botones
        Font fuenteBoton = new Font("Arial", Font.BOLD, 14); 
        Color colorLetra = Color.WHITE;

        // Etiquetas y campos de texto para 'id_cargo' y 'cargo'
        JLabel lblIdCargo = createStyledLabel("ID Cargo:", fuenteBoton, colorLetra);
        JTextField txtIdCargo = new JTextField(20);

        JLabel lblCargo = createStyledLabel("Cargo:", fuenteBoton, colorLetra);
        JTextField txtCargo = new JTextField(20);
        
        JButton btnInsertar = new JButton("Insertar");

        btnInsertar.addActionListener((ActionEvent e) -> {
            String idCargo = txtIdCargo.getText();
            String cargo = txtCargo.getText();
            
            // Validación de campos no vacíos
            if (!idCargo.isEmpty() && !cargo.isEmpty()) {
                if (insertarCargo(idCargo, cargo)) {
                    JOptionPane.showMessageDialog(this, "Cargo insertado correctamente");
                    txtIdCargo.setText("");
                    txtCargo.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al insertar el cargo");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Ambos campos deben estar llenos");
            }
        });

        // Botón para regresar al menú
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajusta el tamaño de la fuente
        btnRegresar.addActionListener((ActionEvent e) -> {
            new GestionTabla2().setVisible(true);  // Llama a la clase para regresar al menú
            dispose();  // Cierra la ventana actual
        });
        
        // Añadir el botón en la parte inferior del frame
        add(btnRegresar, BorderLayout.SOUTH);

        // Configuración del diseño usando GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblIdCargo, gbc);

        gbc.gridx = 1;
        panel.add(txtIdCargo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblCargo, gbc);

        gbc.gridx = 1;
        panel.add(txtCargo, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnInsertar, gbc);

        add(panel);
    }

    // Modificar el método insertarCargo para aceptar dos parámetros
    private boolean insertarCargo(String idCargo, String cargo) {
        try (Connection conexion = ConexionDB.conectar()) {
            if (conexion != null) {
                String sql = "{CALL InsertarCargo(?, ?)}";
                CallableStatement stmt = conexion.prepareCall(sql);
                
                // Asignar parámetros al procedimiento almacenado
                stmt.setString(1, idCargo); // Primer parámetro para 'id_cargo'
                stmt.setString(2, cargo);   // Segundo parámetro para 'cargo'
                
                stmt.execute();
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar el cargo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Método para crear etiquetas con fuente y color personalizados
    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }
}
