import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class InsertarCargoFrame extends JFrame {
    private final JTextField txtIdCargo;
    private final JTextField txtCargo;

    public InsertarCargoFrame() {
        setTitle("Insertar Cargo");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configurar fondo de imagen
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

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        // Configurar estilo de fuente y color para las etiquetas
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Color labelColor = Color.WHITE;

        // Campos de texto y etiquetas
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(createLabel("ID Cargo:", labelFont, labelColor), setPosition(gbc, 0, 1));
        txtIdCargo = new JTextField(20);
        panel.add(txtIdCargo, setPosition(gbc, 1, 1));

        panel.add(createLabel("Cargo:", labelFont, labelColor), setPosition(gbc, 0, 2));
        txtCargo = new JTextField(20);
        panel.add(txtCargo, setPosition(gbc, 1, 2));

        // Botón para guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setFont(labelFont);
        btnGuardar.addActionListener((ActionEvent e) -> insertarCargo());

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnGuardar, gbc);

        // Botón para regresar al menú
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegresar.addActionListener(e -> {
            new GestionTabla2().setVisible(true);
            dispose();
        });
        add(btnRegresar, BorderLayout.SOUTH);

        add(panel);
    }

    private void insertarCargo() {
        try (Connection conexion = ConexionDB.conectar()) {
            String sql = "{CALL InsertarCargo(?,?)}";
            CallableStatement stmt = conexion.prepareCall(sql);
            stmt.setString(1, txtIdCargo.getText());
            stmt.setString(2, txtCargo.getText());
            stmt.execute();
            JOptionPane.showMessageDialog(this, "Cargo insertado correctamente");
            txtIdCargo.setText("");
            txtCargo.setText("");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al insertar el cargo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private GridBagConstraints setPosition(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }
}

