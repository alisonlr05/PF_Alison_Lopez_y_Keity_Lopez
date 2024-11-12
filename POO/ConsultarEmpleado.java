import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ConsultarEmpleado extends JFrame {
    private final JTextField txtCedula;
    private final DefaultTableModel tableModel;

    public ConsultarEmpleado() {
        setTitle("Consultar Empleado");
        setSize(650, 450); // Aumenta un poco el tamaño de la ventana
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
        JLabel lblTitulo = new JLabel("Consultar Empleado", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        // Crear panel para buscar por cédula
        JLabel lblCedula = new JLabel("Cédula:");
        lblCedula.setForeground(Color.WHITE);
        lblCedula.setFont(new Font("Arial", Font.BOLD, 16)); // Aumentar el tamaño de la fuente

        // Ajustar el tamaño del JTextField
        txtCedula = new JTextField(10);

        // Botón Buscar con ícono
        ImageIcon iconBuscar = new ImageIcon(new ImageIcon("imagenes/Buscar.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JButton btnBuscar = new JButton("Buscar", iconBuscar);
        btnBuscar.addActionListener((ActionEvent e) -> buscarEmpleado());

        // Configurar tabla
        String[] columnNames = {"Cédula", "Nombre 1", "Nombre 2", "Apellido 1", "Apellido 2", "Cargo", "Salario"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón para regresar al menú principal
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegresar.addActionListener((ActionEvent e) -> {
            new GestionTabla1().setVisible(true);
            dispose();
        });

        // Usar GridBagLayout para organizar componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Añadir el título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        // Configuración para el label de cédula
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblCedula, gbc);

        // Configuración para el campo de texto (JTextField) y botón Buscar en la misma fila
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtCedula, gbc);

        // Configuración para el botón Buscar
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(btnBuscar, gbc);

        // Añadir la tabla
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        add(panel, BorderLayout.CENTER);
        add(btnRegresar, BorderLayout.SOUTH);

        setResizable(false);
    }

    // Método para buscar un empleado según la cédula ingresada 
    private void buscarEmpleado() {
        String cedula = txtCedula.getText().trim();

        // Validar que se ha ingresado una cédula
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una cédula para buscar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        tableModel.setRowCount(0);
        cargarEmpleados(cedula);
    }

    // Cargar los datos de un empleado usando el procedimiento almacenado.
    private void cargarEmpleados(String cedula) {
        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall("{CALL ConsultarEmpleados(?)}")) {

            // Configurar el parámetro de cédula
            stmt.setString(1, cedula);
            ResultSet rs = stmt.executeQuery();

            // Recorrer resultados y agregarlos a la tabla
            while (rs.next()) {
                String ced = rs.getString("cedula");
                String nombre1 = rs.getString("nombre1");
                String nombre2 = rs.getString("nombre2");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                String cargo = rs.getString("cargo");
                double salario = rs.getDouble("salario");

                tableModel.addRow(new Object[]{ced, nombre1, nombre2, apellido1, apellido2, cargo, salario});
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontró un empleado con la cédula proporcionada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar empleados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
