import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.*;
import javax.swing.*;

public class ActualizarEmpleado extends JFrame {
    private final JTextField txtCedula;
    private final JTextField txtNombre1;
    private final JTextField txtNombre2;
    private final JTextField txtApellido1;
    private final JTextField txtApellido2;
    private final JTextField txtFechaNacimiento;
    private final JTextField txtIdCargo;
    private final JTextField txtSalario;

    public ActualizarEmpleado() {
        setTitle("Actualizar Empleado");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Crear panel con imagen de fondo
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
        JLabel lblTitulo = new JLabel("Actualizar Empleado");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        // Campos de entrada
        Font labelFont = new Font("Arial", Font.BOLD, 14);

        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setFont(labelFont);
        lblCedula.setForeground(Color.WHITE);
        txtCedula = new JTextField(20);

        JLabel lblNombre1 = new JLabel("Primer Nombre:");
        lblNombre1.setFont(labelFont);
        lblNombre1.setForeground(Color.WHITE);
        txtNombre1 = new JTextField(20);

        JLabel lblNombre2 = new JLabel("Segundo Nombre:");
        lblNombre2.setFont(labelFont);
        lblNombre2.setForeground(Color.WHITE);
        txtNombre2 = new JTextField(20);

        JLabel lblApellido1 = new JLabel("Primer Apellido:");
        lblApellido1.setFont(labelFont);
        lblApellido1.setForeground(Color.WHITE);
        txtApellido1 = new JTextField(20);

        JLabel lblApellido2 = new JLabel("Segundo Apellido:");
        lblApellido2.setFont(labelFont);
        lblApellido2.setForeground(Color.WHITE);
        txtApellido2 = new JTextField(20);

        JLabel lblFechaNacimiento = new JLabel("Fecha de Nacimiento (YYYY-MM-DD):");
        lblFechaNacimiento.setFont(labelFont);
        lblFechaNacimiento.setForeground(Color.WHITE);
        txtFechaNacimiento = new JTextField(20);

        JLabel lblIdCargo = new JLabel("ID Cargo:");
        lblIdCargo.setFont(labelFont);
        lblIdCargo.setForeground(Color.WHITE);
        txtIdCargo = new JTextField(20);

        JLabel lblSalario = new JLabel("Salario:");
        lblSalario.setFont(labelFont);
        lblSalario.setForeground(Color.WHITE);
        txtSalario = new JTextField(20);

        // Botón para actualizar
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setFont(labelFont);
        ImageIcon iconActualizar = new ImageIcon(new ImageIcon("imagenes/actualizar.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        btnActualizar.setIcon(iconActualizar);
        btnActualizar.addActionListener((ActionEvent e) -> actualizarEmpleado());

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
        panel.add(lblCedula, gbc);
        gbc.gridx = 1;
        panel.add(txtCedula, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(lblNombre1, gbc);
        gbc.gridx = 1;
        panel.add(txtNombre1, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(lblNombre2, gbc);
        gbc.gridx = 1;
        panel.add(txtNombre2, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(lblApellido1, gbc);
        gbc.gridx = 1;
        panel.add(txtApellido1, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(lblApellido2, gbc);
        gbc.gridx = 1;
        panel.add(txtApellido2, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(lblFechaNacimiento, gbc);
        gbc.gridx = 1;
        panel.add(txtFechaNacimiento, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(lblIdCargo, gbc);
        gbc.gridx = 1;
        panel.add(txtIdCargo, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(lblSalario, gbc);
        gbc.gridx = 1;
        panel.add(txtSalario, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnActualizar, gbc);

        add(panel, BorderLayout.CENTER);
        add(btnRegresar, BorderLayout.SOUTH);
    }

    private void actualizarEmpleado() {
        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall("{CALL ActualizarEmpleado(?, ?, ?, ?, ?, ?, ?, ?)}")) {
            stmt.setString(1, txtCedula.getText().trim());
            stmt.setString(2, txtNombre1.getText().trim());
            stmt.setString(3, txtNombre2.getText().trim());
            stmt.setString(4, txtApellido1.getText().trim());
            stmt.setString(5, txtApellido2.getText().trim());
            stmt.setDate(6, Date.valueOf(txtFechaNacimiento.getText().trim()));
            stmt.setInt(7, Integer.parseInt(txtIdCargo.getText().trim()));
            stmt.setBigDecimal(8, new BigDecimal(txtSalario.getText().trim()));
            stmt.execute();

            JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el empleado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Fecha de nacimiento o salario inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
