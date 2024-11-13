import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class MenuPrincipal extends JFrame {
    
    public MenuPrincipal() {
        setTitle("Menú Principal");
        setSize(600, 400); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear el panel de fondo con la imagen
        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imagenFondo = new ImageIcon("imagenes/Fondo.jpg");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new GridBagLayout());

        // Crear botones para las opciones del menú
        JButton btnTabla1 = new JButton("Gestionar Tabla Empleado", resizeIcon(new ImageIcon("imagenes/btnTabla.png"), 30, 30));
        JButton btnTabla2 = new JButton("Gestionar Tabla Cargo", resizeIcon(new ImageIcon("imagenes/btnTabla.png"), 30, 30));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión", resizeIcon(new ImageIcon("imagenes/salida.png"), 30, 30));

        // Estilo de los botones
        Font fuenteBoton = new Font("Arial", Font.BOLD, 14);
        for (JButton btn : new JButton[] { btnTabla1, btnTabla2, btnCerrarSesion }) {
            btn.setFont(fuenteBoton);
            btn.setPreferredSize(new Dimension(280, 40)); // Tamaño de los botones
            btn.setHorizontalTextPosition(SwingConstants.RIGHT); // Pone el texto a la derecha del icono
            btn.setVerticalTextPosition(SwingConstants.CENTER); // Pone el texto centrado verticalmente
            btn.setIconTextGap(10); // Espacio entre el texto y el icono
        }

        // Agregar mensajes informativos (tooltips) a los botones
        btnTabla1.setToolTipText("Gestiona los datos de la Tabla 1.");
        btnTabla2.setToolTipText("Gestiona los datos de la Tabla 2.");
        btnCerrarSesion.setToolTipText("Cierra la sesión actual y regresa al inicio.");

        // Agregar acciones a los botones
        btnTabla1.addActionListener((ActionEvent e) -> {
            // Mostrar ventana de GestionTabla1
            new GestionTabla1().setVisible(true);
            dispose(); // Cerrar la ventana principal
        });

        btnTabla2.addActionListener((ActionEvent e) -> {
            // Mostrar ventana de GestionTabla2
            new GestionTabla2().setVisible(true);
            dispose(); // Cerrar la ventana principal
        });

        btnCerrarSesion.addActionListener((ActionEvent e) -> {
            int opcion = JOptionPane.showConfirmDialog(MenuPrincipal.this, "¿Está seguro de que desea cerrar sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                // Regresar al PanelInicio
                new PanelInicio().setVisible(true);
                dispose();
            }
        });

        // Añadir botones al panel usando GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFondo.add(btnTabla1, gbc);
        
        gbc.gridy = 1;
        panelFondo.add(btnTabla2, gbc);
        
        gbc.gridy = 2;
        panelFondo.add(btnCerrarSesion, gbc);

        add(panelFondo);
    }

    // Método para redimensionar el icono
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }

}//Fin de la clase Menu Principal
