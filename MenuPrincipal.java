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
             // Crear un menú con opciones
    String[] opciones = {"Insertar", "Actualizar", "Eliminar", "Consultar"};
    
    // Mostrar un diálogo con las opciones
    String seleccion = (String) JOptionPane.showInputDialog(
        MenuPrincipal.this,
        "Seleccione una opción:",
        "Gestión de Tabla 1",
        JOptionPane.QUESTION_MESSAGE,
        null, // Icono por defecto
        opciones, // Opciones del menú
        opciones[0] // Opción por defecto
    );

    // Verificar la opción seleccionada
    if (seleccion != null) {
        switch (seleccion) {
            case "Insertar":
                JOptionPane.showMessageDialog(MenuPrincipal.this, "Funcionalidad de Insertar en Tabla 1");
                // Aquí iría el código para insertar registros
                break;
            case "Actualizar":
                JOptionPane.showMessageDialog(MenuPrincipal.this, "Funcionalidad de Actualizar en Tabla 1");
                // Aquí iría el código para actualizar registros
                break;
            case "Eliminar":
                JOptionPane.showMessageDialog(MenuPrincipal.this, "Funcionalidad de Eliminar en Tabla 1");
                // Aquí iría el código para eliminar registros
                break;
            case "Consultar":
                JOptionPane.showMessageDialog(MenuPrincipal.this, "Funcionalidad de Consultar en Tabla 1");
                // Aquí iría el código para consultar registros
                break;
            default:
                JOptionPane.showMessageDialog(MenuPrincipal.this, "Opción no válida");
        }
    }
        });

        btnTabla2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí se abriría la ventana o panel para gestionar la Tabla 2
                JOptionPane.showMessageDialog(MenuPrincipal.this, "Funcionalidad de Tabla 2");
            }
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
