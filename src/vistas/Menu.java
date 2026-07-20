package vistas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Menu extends JFrame {

    private static final Color BG_COLOR = new Color(30, 80, 106); 
    private static final Color BTN_BG_COLOR = new Color(0, 56, 83); 
    private static final Color TEXT_COLOR = Color.BLACK; 
    private static final Font BTN_FONT = new Font("Arial", Font.BOLD, 22);

    private final Map<String, ImageIcon> iconMap;

    public Menu() {
        setTitle("Proyecto POS - Menú Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // 1. Cargar iconos
        iconMap = loadIcons();

        // 2. Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(50, 0, 50, 0)); 
        setContentPane(mainPanel);

        // 3. Logo
        JLabel logoLabel = createLogoLabel();
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(60)); 

        // 4. Contenedor de botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(buttonPanel);

        // 5. Botones (Si el icono es null, el método addMenuButton dibuja un recuadro)
        addMenuButton(buttonPanel, "Caja", iconMap.get("caja")); 
        buttonPanel.add(Box.createVerticalStrut(25)); 

        addMenuButton(buttonPanel, "Usuarios", iconMap.get("usuario"));
        buttonPanel.add(Box.createVerticalStrut(25));

        addMenuButton(buttonPanel, "Productos", iconMap.get("productos"));
        buttonPanel.add(Box.createVerticalStrut(60)); 

        addMenuButton(buttonPanel, "Salir", null); 
    }

    private JLabel createLogoLabel() {
        ImageIcon logoIcon = iconMap.get("logo");
        if (logoIcon != null) {
            // Escalamos el logo a un tamaño fijo para evitar errores de proporción
            logoIcon = scaleIcon(logoIcon, 250, 100); 
        }
        JLabel label = new JLabel(logoIcon != null ? logoIcon : createGenericIcon(250, 100, Color.RED));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void addMenuButton(JPanel container, String text, ImageIcon icon) {
        JButton button = new JButton(text);
        
        button.setFont(BTN_FONT);
        button.setForeground(TEXT_COLOR);
        button.setBackground(BTN_BG_COLOR);
        button.setFocusPainted(false); 
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BTN_BG_COLOR, 1), 
            BorderFactory.createEmptyBorder(15, 30, 15, 20) 
        ));

        // Solo agregamos el icono y su separación si realmente existe uno
        if (icon != null) {
            ImageIcon scaledIcon = scaleIcon(icon, 30, 30);
            if (scaledIcon != null) {
                button.setIcon(scaledIcon);
                button.setHorizontalTextPosition(SwingConstants.LEFT); // Texto a la izquierda del icono
                button.setIconTextGap(20); // Espacio entre texto e icono
            }
        }
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        Dimension btnSize = new Dimension(450, 70); 
        button.setPreferredSize(btnSize);
        button.setMinimumSize(btnSize);
        button.setMaximumSize(btnSize);

        // Lógica de los botones
        if (text.equals("Salir")) {
            button.addActionListener(e -> {dispose();
                    vistas.LoginElaborado ventanaLogin = new vistas.LoginElaborado();
                    ventanaLogin.setVisible(true);});
        }if (text.equals("Caja")) {
            button.addActionListener(e -> {dispose();
                    vistas.VistaCaja ventanaCaja = new vistas.VistaCaja();
                    ventanaCaja.setVisible(true);});
        }if (text.equals("Usuarios")) {
            button.addActionListener(e -> {dispose();
                    vistas.VistaAdminUsu ventanaUsu = new vistas.VistaAdminUsu();
                    ventanaUsu.setVisible(true);});
        }if (text.equals("Productos")) {
            button.addActionListener(e -> {dispose();
                    vistas.VistaAdminProduc ventanaProduct = new vistas.VistaAdminProduc();
                    ventanaProduct.setVisible(true);});
        }

        container.add(button);
    }

    private Map<String, ImageIcon> loadIcons() {
        Map<String, ImageIcon> icons = new HashMap<>();
        String[] fileKeys = {"logo","caja", "usuario", "productos"};
        String[] extensions = {".png",".png", ".png", ".png"};

        for (int i = 0; i < fileKeys.length; i++) {
            String key = fileKeys[i];
            // Ahora usa la misma estructura que tu LoginElaborado
            String path = "src/img/" + key + extensions[i];
            File file = new File(path);
            
            if (file.exists()) {
                icons.put(key, new ImageIcon(path));
            } else {
                System.err.println("ADVERTENCIA: No se encontró la imagen en: " + path);
                icons.put(key, null); // Se evita que crashee
            }
        }
        return icons;
    }

    private ImageIcon scaleIcon(ImageIcon icon, int width, int height) {
        // Segunda barrera anti-crasheo
        if (icon == null || icon.getImage() == null) {
            return null;
        }
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    private ImageIcon createGenericIcon(int width, int height, Color color) {
        return new ImageIcon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                g.setColor(color);
                g.fillRect(x, y, getIconWidth(), getIconHeight());
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }
}