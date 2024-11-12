import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ConsultarCargosFrame extends JFrame {
    private final JTextArea textArea;
    private final JTextField txtIdCargo;

    public ConsultarCargosFrame() {
        setTitle("Consultar Cargos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Crear panel de entrada
        JPanel inputPanel = new JPanel(new FlowLayout());
        JLabel lblIdCargo = new JLabel("ID Cargo:");
        txtIdCargo = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");

        inputPanel.add(lblIdCargo);
        inputPanel.add(txtIdCargo);
        inputPanel.add(btnBuscar);
        add(inputPanel, BorderLayout.NORTH);

        // Crear área de texto para mostrar resultados
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Acción del botón Buscar
        btnBuscar.addActionListener((ActionEvent e) -> {
            buscarCargos();
        });
    }

    /**
     * Método para buscar cargos según el ID ingresado.
     */
    private void buscarCargos() {
        textArea.setText(""); // Limpiar el área de texto
        int idCargo;

        // Validar si el campo de ID está vacío o tiene un número válido
        if (txtIdCargo.getText().trim().isEmpty()) {
            idCargo = 0; // Mostrar todos los cargos si no se proporciona un ID
        } else {
            try {
                idCargo = Integer.parseInt(txtIdCargo.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese un número válido para el ID de cargo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Conectar a la base de datos y ejecutar el procedimiento almacenado
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tu_base_datos", "root", "password");
             CallableStatement stmt = conn.prepareCall("{CALL ConsultarCargos(?)}")) {

            // Establecer el parámetro de entrada
            stmt.setInt(1, idCargo);
            ResultSet rs = stmt.executeQuery();

            // Mostrar los resultados en el área de texto
            while (rs.next()) {
                textArea.append(rs.getInt("id_cargo") + " - " + rs.getString("nombre") + "\n");
            }

            // Verificar si no se encontraron resultados
            if (textArea.getText().isEmpty()) {
                textArea.setText("No se encontraron cargos para el ID proporcionado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar cargos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ConsultarCargosFrame().setVisible(true);
        });
    }
}
