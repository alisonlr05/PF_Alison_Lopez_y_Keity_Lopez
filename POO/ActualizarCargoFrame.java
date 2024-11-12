import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class ActualizarCargoFrame extends JFrame {
    public ActualizarCargoFrame() {
        setTitle("Actualizar Cargo");
        setSize(400, 300);
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

        JLabel lblIdCargo = new JLabel("ID Cargo:");
        JTextField txtIdCargo = new JTextField(20);
        JLabel lblCargo = new JLabel("Nuevo Cargo:");
        JTextField txtCargo = new JTextField(20);
        JButton btnActualizar = new JButton("Actualizar");

        btnActualizar.addActionListener((ActionEvent e) -> {
            try {
                int idCargo = Integer.parseInt(txtIdCargo.getText());
                String cargo = txtCargo.getText();
                actualizarCargo(idCargo, cargo);
                JOptionPane.showMessageDialog(this, "Cargo actualizado correctamente");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido");
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblIdCargo, gbc);

        gbc.gridx = 1;
        panel.add(txtIdCargo, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(lblCargo, gbc);

        gbc.gridx = 1;
        panel.add(txtCargo, gbc);

        gbc.gridy = 2;
        panel.add(btnActualizar, gbc);

        add(panel);
    }

    private void actualizarCargo(int idCargo, String cargo) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tu_base_datos", "root", "password");
             CallableStatement stmt = conn.prepareCall("{CALL ActualizarCargo(?, ?)}")) {
            stmt.setInt(1, idCargo);
            stmt.setString(2, cargo);
            stmt.execute();
        } catch (SQLException ex) {
        }
    }
}
