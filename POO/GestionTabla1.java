import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GestionTabla1 extends JFrame {

    public GestionTabla1() {
        setTitle("Gestión de Tabla 1");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        // Crear panel con fondo de imagen
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("Fondo.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Crear botones con iconos
        JButton btnInsertar = new JButton("Insertar", new ImageIcon("senales.png"));
        JButton btnActualizar = new JButton("Actualizar", new ImageIcon("actualizar.png"));
        JButton btnEliminar = new JButton("Eliminar", new ImageIcon("borrar.png"));
        JButton btnConsultar = new JButton("Consultar", new ImageIcon("busqueda.png"));


        // Añadir acciones a los botones
        btnInsertar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Insertar en Tabla 1");
        });

        btnActualizar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Actualizar en Tabla 1");
        });

        btnEliminar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Eliminar en Tabla 1");
        });

        btnConsultar.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this, "Funcionalidad de Consultar en Tabla 1");
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
