import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class ActualizarCargoFrame extends JFrame {
    public ActualizarCargoFrame() {
        setTitle("Actualizar Cargo");
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

        // Título
        JLabel lblTitulo = new JLabel("Actualizar Cargo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        // Componentes de entrada
        JLabel lblIdCargo = new JLabel("ID Cargo:");
        lblIdCargo.setFont(new Font("Arial", Font.BOLD, 14));
        lblIdCargo.setForeground(Color.WHITE);
        txtIdCargo = new JTextField(20);

        JLabel lblCargo = new JLabel("Nuevo Cargo:");
        lblCargo.setFont(new Font("Arial", Font.BOLD, 14));
        lblCargo.setForeground(Color.WHITE);
        txtCargo = new JTextField(20);

        // Botón Actualizar
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 14));
        btnActualizar.addActionListener((ActionEvent e) -> actualizarCargo());

        // Botón para regresar al menú
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegresar.addActionListener((ActionEvent e) -> {
            new GestionTabla2().setVisible(true);
            dispose();
        });

        // Configuración del Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(lblIdCargo, gbc);

        gbc.gridx = 1;
        panel.add(txtIdCargo, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(lblCargo, gbc);

        gbc.gridx = 1;
        panel.add(txtCargo, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnActualizar, gbc);

        add(panel, BorderLayout.CENTER);
        add(btnRegresar, BorderLayout.SOUTH);
    }

    private void actualizarCargo() {
        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall("{CALL ActualizarCargo(?, ?)}")) {
            int idCargo = Integer.parseInt(txtIdCargo.getText());
            String cargo = txtCargo.getText();
            stmt.setInt(1, idCargo);
            stmt.setString(2, cargo);
            stmt.execute();
            JOptionPane.showMessageDialog(this, "Cargo actualizado correctamente.");
            
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
