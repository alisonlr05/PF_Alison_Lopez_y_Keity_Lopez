import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class EliminarCargoFrame extends JFrame {
    public EliminarCargoFrame() {
        setTitle("Eliminar Cargo");
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

        JLabel lblIdCargo = new JLabel("ID Cargo:");
        JTextField txtIdCargo = new JTextField(20);
        JButton btnEliminar = new JButton("Eliminar");

        btnEliminar.addActionListener((ActionEvent e) -> {
            try {
                int idCargo = Integer.parseInt(txtIdCargo.getText());
                if (eliminarCargo(idCargo)) {
                    JOptionPane.showMessageDialog(this, "Cargo eliminado correctamente");
                    txtIdCargo.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el cargo");
                }
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
        gbc.gridwidth = 2;
        panel.add(btnEliminar, gbc);

        add(panel);
    }

    private boolean eliminarCargo(int idCargo) {
        try (Connection conexion = ConexionDB.conectar()) {
            if (conexion != null) {
                String sql = "{CALL EliminarCargo(?)}";
                CallableStatement stmt = conexion.prepareCall(sql);
                stmt.setInt(1, idCargo);
                stmt.execute();
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el cargo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
