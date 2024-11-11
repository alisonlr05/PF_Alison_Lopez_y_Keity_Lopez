import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                ImageIcon imagenFondo = new ImageIcon("Fondo.jpg");
                g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(new GridBagLayout());

        // Crear botones para las opciones del menú
        JButton btnTabla1 = new JButton("Gestionar Tabla 1", new ImageIcon("icono_tabla1.png"));
        JButton btnTabla2 = new JButton("Gestionar Tabla 2", new ImageIcon("icono_tabla2.png"));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión", new ImageIcon("icono_cerrar_sesion.png"));

        // Agregar acciones a los botones
        btnTabla1.addActionListener(new ActionListener() {
           new GestionTabla1().setVisible(true);
            dispose();
        });

        btnTabla2.addActionListener(new ActionListener() {
            new GestionTabla2().setVisible(true);
            dispose();
        });

        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(MenuPrincipal.this, "¿Está seguro de que desea cerrar sesión?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    // Regresar al PanelInicio
                    new PanelInicio().setVisible(true);
                    dispose();
                }
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
}//Fin de la clase MenuPrincipal
