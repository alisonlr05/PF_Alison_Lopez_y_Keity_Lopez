import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EliminarEmpleado extends JFrame {
    private JTextField txtCedula;

    public EliminarEmpleado() {
        setTitle("Eliminar Empleado");
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
    
        // Titulo
        JLabel lblTitulo = new JLabel("Eliminar Empleado");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        // Etiqueta para la cédula
        JLabel lblCedula = new JLabel("Cédula a eliminar:");
        lblCedula.setFont(new Font("Arial", Font.BOLD, 16)); // Aumentar tamaño de la etiqueta
        lblCedula.setForeground(Color.WHITE);

        // Campo de texto para la cédula
        txtCedula = new JTextField(15);
        txtCedula.setFont(new Font("Arial", Font.PLAIN, 16)); // Aumentar tamaño de fuente en el campo de texto

        //boton Eliminar
        ImageIcon iconEliminar = new ImageIcon(new ImageIcon("imagenes/borrar.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        JButton btnEliminar = new JButton("Eliminar", iconEliminar);
        btnEliminar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEliminar.addActionListener(e -> eliminarEmpleado());

        // Botón para regresar al menú
        JButton btnRegresar = new JButton("Regresar al Menú");
        btnRegresar.setFont(new Font("Arial", Font.PLAIN, 14)); // Ajusta el tamaño de la fuente
        btnRegresar.addActionListener((ActionEvent e) -> {
            new GestionTabla1().setVisible(true);  // Llama a la clase para regresar al menú
            dispose();  // Cierra la ventana actual
        });
        
        // Añadir el botón en la parte inferior del frame
        add(btnRegresar, BorderLayout.SOUTH);

        // Layout settings
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(lblCedula, gbc);
        
        gbc.gridx = 1;
        panel.add(txtCedula, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(btnEliminar, gbc);

        add(panel, BorderLayout.CENTER);

        add(btnRegresar, BorderLayout.SOUTH);

        pack(); 
    }

//eliminar empleado según la cédula ingresada.   
    private void eliminarEmpleado() {
        String cedula = txtCedula.getText().trim();

        // Validación de entrada
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese una cédula válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Conexión a la base de datos y eliminación del empleado
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Empleado WHERE cedula = ?")) {

            stmt.setString(1, cedula);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado exitosamente.");
                txtCedula.setText(""); // Limpiar campo de cédula
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró un empleado con esa cédula.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
