import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ConsultarCargosFrame extends JFrame {
    private final JTextField txtIdCargo;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public ConsultarCargosFrame() {
        setTitle("Consultar Cargos");
        setSize(650, 450); 
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

        // Título
        JLabel lblTitulo = new JLabel("Consultar Cargos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        // Etiqueta para ID Cargo
        JLabel lblIdCargo = new JLabel("ID Cargo:");
        lblIdCargo.setForeground(Color.WHITE);
        lblIdCargo.setFont(new Font("Arial", Font.BOLD, 16));

        txtIdCargo = new JTextField(10); 

        // Botón Buscar con ícono
        ImageIcon iconBuscar = new ImageIcon(new ImageIcon("imagenes/Buscar.png").getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH));
        JButton btnBuscar = new JButton("Buscar", iconBuscar);
        btnBuscar.setPreferredSize(new Dimension(120, 30)); // Ajustar tamaño del botón
        btnBuscar.addActionListener((ActionEvent e) -> buscarCargos());

        // Configuración de la tabla
        String[] columnNames = {"ID Cargo", "Cargo"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        table.setRowHeight(24); 
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(table);

        // Botón para regresar al menú principal
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14));
        btnRegresar.addActionListener(e -> {
            new GestionTabla2().setVisible(true);
            dispose();
        });

        // Usar GridBagLayout para organizar los componentes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Añadir el título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lblTitulo, gbc);

        // Configuración para el label de ID Cargo
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblIdCargo, gbc);

        // Configuración para el campo de texto (JTextField)
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtIdCargo, gbc);

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

        // Añadir el botón regresar
        add(panel, BorderLayout.CENTER);
        add(btnRegresar, BorderLayout.SOUTH);

        setResizable(false);
    }

    /**
     * Método para buscar cargos según el ID ingresado.
     */
    private void buscarCargos() {
        tableModel.setRowCount(0); // Limpiar la tabla
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
        try (Connection conn = ConexionDB.conectar();
             CallableStatement stmt = conn.prepareCall("{CALL ConsultarCargos(?)}")) {

            // Establecer el parámetro de entrada
            stmt.setInt(1, idCargo);
            ResultSet rs = stmt.executeQuery();

            // Mostrar los resultados en la tabla
            while (rs.next()) {
                int id = rs.getInt("id_Cargo");
                String nombre = rs.getString("Cargo");
                tableModel.addRow(new Object[]{id, nombre});
            }

            // Verificar si no se encontraron resultados
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron cargos para el ID proporcionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar cargos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}