import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GestionTabla2 extends JFrame {

    public GestionTabla2() {
        setTitle("Gestión de Tabla 2");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        
        // Crear botones para las operaciones
        JButton btnInsertar = new JButton("Insertar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnConsultar = new JButton("Consultar");

        // Añadir acciones a los botones
        btnInsertar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Insertar en Tabla 2");
        });

        btnActualizar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Actualizar en Tabla 2");
        });

        btnEliminar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Eliminar en Tabla 2");
        });

        btnConsultar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Consultar en Tabla 2");
        });

        // Añadir botones al panel
        panel.add(btnInsertar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnConsultar);

        add(panel, BorderLayout.CENTER);

        // Botón para regresar al menú principal
        JButton btnRegresar = new JButton("Regresar al Menú Principal");
        btnRegresar.addActionListener((ActionEvent e) -> {
            new MenuPrincipal().setVisible(true);
            dispose();
        });

        add(btnRegresar, BorderLayout.SOUTH);
    }
}
