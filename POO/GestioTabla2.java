import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class GestionTabla2 extends JFrame {

        public GestionTabla2() {
        setTitle("Gestión de Tabla 2");
        setSize(600, 400);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel con fondo de imagen
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

        // Crear iconos redimensionados desde la carpeta "imagenes" y aumentar tamaño
        ImageIcon iconInsertar = new ImageIcon(new ImageIcon("imagenes/senales.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        ImageIcon iconActualizar = new ImageIcon(new ImageIcon("imagenes/actualizar.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        ImageIcon iconEliminar = new ImageIcon(new ImageIcon("imagenes/borrar.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        ImageIcon iconConsultar = new ImageIcon(new ImageIcon("imagenes/busqueda.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));

        // Crear botones con iconos redimensionados y aumentar tamaño de fuente
        JButton btnInsertar = new JButton("Insertar", iconInsertar);
        JButton btnActualizar = new JButton("Actualizar", iconActualizar);
        JButton btnEliminar = new JButton("Eliminar", iconEliminar);
        JButton btnConsultar = new JButton("Consultar", iconConsultar);

        // Ajustar el tamaño de los botones y el tamaño de la fuente
        Dimension buttonSize = new Dimension(150, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);
        
        btnInsertar.setPreferredSize(buttonSize);
        btnInsertar.setFont(buttonFont);

        btnActualizar.setPreferredSize(buttonSize);
        btnActualizar.setFont(buttonFont);

        btnEliminar.setPreferredSize(buttonSize);
        btnEliminar.setFont(buttonFont);

        btnConsultar.setPreferredSize(buttonSize);
        btnConsultar.setFont(buttonFont);

       // Agregar mensajes informativos a los botones
       btnInsertar.setToolTipText("Inserta nuevos cargos a los empleados en tabla Cargo");
       btnActualizar.setToolTipText("Actualiza con nuevos datos la tabla Cargo");
       btnEliminar.setToolTipText("Elimina el Cargo de un empleado de la tabla Cargo");
       btnConsultar.setToolTipText("Consulta por el cargo de un empleado, mediante el ID cargo");
            
        // Añadir acciones a los botones
        btnInsertar.addActionListener((ActionEvent e) -> {
            new InsertarCargoFrame().setVisible(true);
            dispose();
        });

        btnActualizar.addActionListener((ActionEvent e) -> {
            new ActualizarCargoFrame().setVisible(true);
            dispose();
        });

        btnEliminar.addActionListener((ActionEvent e) -> {
            new EliminarCargoFrame().setVisible(true);
            dispose();
        });

        btnConsultar.addActionListener((ActionEvent e) -> {
            new ConsultarCargosFrame().setVisible(true);
            dispose();
        });

                // Agregar botones al panel en forma de cuadrícula centrada
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(btnInsertar, gbc);

        gbc.gridy = 1;
        panel.add(btnActualizar, gbc);

        gbc.gridy = 2;
        panel.add(btnEliminar, gbc);

        gbc.gridy = 3;
        panel.add(btnConsultar, gbc);

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
