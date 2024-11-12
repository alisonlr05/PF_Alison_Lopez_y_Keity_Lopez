import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertarEmpleado extends JFrame {

    // Campos de texto para capturar datos del empleado
    private JTextField txtCedula, txtNombre1, txtNombre2, txtApellido1, txtApellido2, txtFechaNacimiento, txtIdCargo, txtSalario, txtCargo;
    
    public InsertarEmpleado() {
        setTitle("Insertar Empleado");
        setSize(600, 400); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
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
        JLabel title = new JLabel("Insertar Empleado");
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

        // Crear campos de texto y etiquetas con estilo solicitado
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        panel.add(createLabel("Cédula:", labelFont, labelColor), setPosition(gbc, 0, 1));
        txtCedula = new JTextField(15);
        panel.add(txtCedula, setPosition(gbc, 1, 1));

        panel.add(createLabel("Nombre 1:", labelFont, labelColor), setPosition(gbc, 0, 2));
        txtNombre1 = new JTextField(15);
        panel.add(txtNombre1, setPosition(gbc, 1, 2));

        panel.add(createLabel("Nombre 2:", labelFont, labelColor), setPosition(gbc, 0, 3));
        txtNombre2 = new JTextField(15);
        panel.add(txtNombre2, setPosition(gbc, 1, 3));

        panel.add(createLabel("Apellido 1:", labelFont, labelColor), setPosition(gbc, 0, 4));
        txtApellido1 = new JTextField(15);
        panel.add(txtApellido1, setPosition(gbc, 1, 4));

        panel.add(createLabel("Apellido 2:", labelFont, labelColor), setPosition(gbc, 0, 5));
        txtApellido2 = new JTextField(15);
        panel.add(txtApellido2, setPosition(gbc, 1, 5));

        panel.add(createLabel("Fecha Nacimiento:", labelFont, labelColor), setPosition(gbc, 0, 6));
        txtFechaNacimiento = new JTextField(15);
        panel.add(txtFechaNacimiento, setPosition(gbc, 1, 6));

        panel.add(createLabel("ID Cargo:", labelFont, labelColor), setPosition(gbc, 0, 7));
        txtIdCargo = new JTextField(15);
        panel.add(txtIdCargo, setPosition(gbc, 1, 7));

        panel.add(createLabel("Salario:", labelFont, labelColor), setPosition(gbc, 0, 8));
        txtSalario = new JTextField(15);
        panel.add(txtSalario, setPosition(gbc, 1, 8));

        panel.add(createLabel("Cargo:", labelFont, labelColor), setPosition(gbc, 0, 9));
        txtCargo = new JTextField(15);
        panel.add(txtCargo, setPosition(gbc, 1, 9));

        // Botón para guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener((ActionEvent e) -> guardarEmpleado());
        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(btnGuardar, gbc);

        // Añadir el panel al centro del frame
        add(panel, BorderLayout.CENTER);

        // Botón para regresar al menú
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajusta el tamaño de la fuente
        btnRegresar.addActionListener((ActionEvent e) -> {
            new GestionTabla1().setVisible(true);  // Llama a la clase para regresar al menú
            dispose();  // Cierra la ventana actual
        });
        
        // Añadir el botón en la parte inferior del frame
        add(btnRegresar, BorderLayout.SOUTH);
    }

    // Método para obtener el GridBagConstraints de cada componente
    private GridBagConstraints setPosition(GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        return gbc;
    }

    // Método para crear etiquetas con fuente y color personalizados
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

   // Método para guardar el empleado en la base de datos llamando al SP
private void guardarEmpleado() {
    try (Connection conn = ConexionDB.conectar()) {
        // Llamar al procedimiento almacenado con 8 parámetros
        String sql = "{ CALL InsertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?) }";
        PreparedStatement ps = conn.prepareStatement(sql);

        // Establecer los 8 parámetros requeridos por el SP
        ps.setString(1, txtCedula.getText());
        ps.setString(2, txtNombre1.getText());
        ps.setString(3, txtNombre2.getText());
        ps.setString(4, txtApellido1.getText());
        ps.setString(5, txtApellido2.getText());
        ps.setString(6, txtFechaNacimiento.getText());
        ps.setInt(7, Integer.parseInt(txtIdCargo.getText()));
        ps.setDouble(8, Double.parseDouble(txtSalario.getText()));

        // Ejecutar la actualización
        ps.executeUpdate();
        
        // Mostrar mensaje de éxito
        JOptionPane.showMessageDialog(this, "Empleado insertado exitosamente.");

    } catch (SQLException ex) {
        // Mostrar mensaje de error en caso de fallo
        JOptionPane.showMessageDialog(this, "Error al insertar empleado: " + ex.getMessage());
    } catch (NumberFormatException ex) {
        // Manejo de excepciones para conversiones incorrectas (ej: ID Cargo y Salario)
        JOptionPane.showMessageDialog(this, "Error en los datos numéricos: " + ex.getMessage());
    }
}

}

