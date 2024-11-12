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

        // Etiquetas y campos de texto para 'id_cargo' y 'cargo'
        JLabel lblIdCargo = new JLabel("ID Cargo:");
        JTextField txtIdCargo = new JTextField(20);
        
        JLabel lblCargo = new JLabel("Cargo:");
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
}
