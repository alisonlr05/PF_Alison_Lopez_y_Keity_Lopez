import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultarEmpleado extends JFrame {

    public ConsultarEmpleado() {
        setTitle("Consultar Empleado");
        setSize(700, 500);
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
        panel.setLayout(new BorderLayout());

        // Título en la parte superior del panel
        JLabel lblTitulo = new JLabel("Consultar Empleado", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        panel.add(lblTitulo, BorderLayout.NORTH);

        // Tabla para mostrar los datos de los empleados
        String[] columnNames = {"Cédula", "Nombre 1", "Nombre 2", "Apellido 1", "Apellido 2", "Fecha Nacimiento", "Cargo", "Salario"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Botón para regresar al menú principal
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegresar.addActionListener((ActionEvent e) -> {
            new GestionTabla1().setVisible(true);  // Muestra el menú principal
            dispose();  // Cierra la ventana actual
        });

        // Agregar el botón en la parte inferior del panel
        panel.add(btnRegresar, BorderLayout.SOUTH);

        add(panel);

        // Cargar datos en la tabla desde la base de datos
        cargarEmpleados(tableModel);
    }

    // Método para cargar los datos de los empleados usando el procedimiento almacenado
    private void cargarEmpleados(DefaultTableModel tableModel) {
        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall("{CALL ConsultarEmpleados()}")) {

            ResultSet rs = stmt.executeQuery();

            // Recorrer resultados y agregarlos a la tabla
            while (rs.next()) {
                String cedula = rs.getString("cedula");
                String nombre1 = rs.getString("nombre1");
                String nombre2 = rs.getString("nombre2");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                String fechaNacimiento = rs.getString("fecha_nacimiento");
                String cargo = rs.getString("cargo");
                double salario = rs.getDouble("salario");

                tableModel.addRow(new Object[]{cedula, nombre1, nombre2, apellido1, apellido2, fechaNacimiento, cargo, salario});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar empleados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
