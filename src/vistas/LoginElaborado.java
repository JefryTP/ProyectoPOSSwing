/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginElaborado extends JFrame {

    public LoginElaborado() {
        setUndecorated(true);
        setSize(800, 500);
        setLocationRelativeTo(null);
        // TRUCO 1: Quitamos el BorderLayout y usamos "null" para poder superponer capas
        setLayout(null);

        // ==========================================
        // CAPA SUPERIOR: PANEL IZQUIERDO (Oscuro con Curva)
        // ==========================================
        // Sobrescribimos el método paintComponent para dibujar nuestra propia forma
        JPanel panelIzquierdo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                
                // Dibujamos un rectángulo curvo en todo el panel (Radio de 60px)
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 60, 60);
                // Rellenamos solo la mitad izquierda con un rectángulo recto para tapar la curva de ese lado
                g2.fillRect(0, 0, getWidth() - 30, getHeight()); 
                g2.dispose();
            }
        };
        panelIzquierdo.setBackground(new Color(24, 24, 24)); 
        panelIzquierdo.setBounds(0, 0, 350, 500);
        panelIzquierdo.setOpaque(false); // Vital: Hace que las esquinas curvas sean invisibles y muestren la foto
        panelIzquierdo.setLayout(null);

        // Logo de la tienda
        // Logo de la tienda (40% más chico)
        ImageIcon iconoTiendaOriginal = new ImageIcon("src/img/icono_tienda.png");
        // Escalamiento reducido a 60x60
        Image imgTienda = iconoTiendaOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(imgTienda));
        // Ajustamos la posición X a 145 y la Y a 80 para mantener el centro exacto
        lblLogo.setBounds(145, 80, 60, 60); 
        panelIzquierdo.add(lblLogo);

        // Título principal
        JLabel lblTitulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setBounds(0, 180, 350, 40);
        panelIzquierdo.add(lblTitulo);

        // Campo de Texto: Código
        JTextField txtCodigo = new JTextField("Código");
        txtCodigo.setBounds(50, 250, 250, 40);
        txtCodigo.setBackground(new Color(200, 200, 200));
        txtCodigo.setForeground(Color.GRAY);
        txtCodigo.setBorder(new BordeRedondo(15));
        txtCodigo.setHorizontalAlignment(JTextField.CENTER);
        
        txtCodigo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (txtCodigo.getText().equals("Código")) {
                    txtCodigo.setText("");
                    txtCodigo.setForeground(Color.BLACK); 
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (txtCodigo.getText().isEmpty()) {
                    txtCodigo.setForeground(Color.GRAY);
                    txtCodigo.setText("Código");
                }
            }
        });
        panelIzquierdo.add(txtCodigo);

        // Campo de Texto: Contraseña
        JPasswordField txtContrasena = new JPasswordField("Contraseña");
        txtContrasena.setBounds(50, 310, 250, 40);
        txtContrasena.setBackground(new Color(200, 200, 200));
        txtContrasena.setForeground(Color.GRAY);
        txtContrasena.setEchoChar((char) 0); 
        txtContrasena.setBorder(new BordeRedondo(15));
        txtContrasena.setHorizontalAlignment(JTextField.CENTER);
        
        txtContrasena.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (String.valueOf(txtContrasena.getPassword()).equals("Contraseña")) {
                    txtContrasena.setText("");
                    txtContrasena.setForeground(Color.BLACK);
                    txtContrasena.setEchoChar('•'); 
                }
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (String.valueOf(txtContrasena.getPassword()).isEmpty()) {
                    txtContrasena.setForeground(Color.GRAY);
                    txtContrasena.setText("Contraseña");
                    txtContrasena.setEchoChar((char) 0); 
                }
            }
        });
        panelIzquierdo.add(txtContrasena);

        // Botón Ingresar
        JButton btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(100, 390, 150, 40);
        btnIngresar.setBackground(new Color(138, 201, 38)); 
        btnIngresar.setForeground(Color.BLACK);
        btnIngresar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnIngresar.setFocusPainted(false);
        btnIngresar.setBorder(new BordeRedondo(15)); 
        
        // --- LÓGICA DE LOGIN AGREGADA AQUÍ ---
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String usuario = txtCodigo.getText();
                String password = new String(txtContrasena.getPassword());

                // Validamos con las credenciales quemadas en el código
                if (usuario.equals("admin") && password.equals("123")) {
                    dispose(); // Cierra el login
                    vistas.Menu ventanaMenu = new vistas.Menu();
                    ventanaMenu.setVisible(true); // Abre el menú
                } else {
                    JOptionPane.showMessageDialog(LoginElaborado.this, 
                        "El código o la contraseña son incorrectos.", 
                        "Error de Autenticación", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // -------------------------------------

        panelIzquierdo.add(btnIngresar);

        // ==========================================
        // CAPA FONDO: PANEL DERECHO (Imagen del Señor)
        // ==========================================
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(null); 
        // TRUCO 2: Hacemos que este panel empiece desde X=300, 
        // metiéndose 50px por debajo del panel oscuro para que se asome en la curva.
        panelDerecho.setBounds(300, 0, 500, 500); 

        // Botón de Apagado (15% más grande y con esquinas redondeadas)
        ImageIcon iconoApagadoOriginal = new ImageIcon("src/img/icono_apagado.png");
        Image imgApagado = iconoApagadoOriginal.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        
        // CORRECCIÓN: Ahora sí le pasamos el ImageIcon al JLabel
        JLabel btnApagar = new JLabel(new ImageIcon(imgApagado)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Le ponemos el molde curvo
                g2.setClip(new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                // Dejamos que el JLabel dibuje su propia imagen, pero respetando el recorte
                super.paintComponent(g2); 
                g2.dispose();
            }
        };
        btnApagar.setBounds(445, 10, 35, 35); 
        btnApagar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnApagar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        panelDerecho.add(btnApagar);

        // Imagen de Fondo (El Señor) estirada y encuadrada
        // Imagen de Fondo (El Señor) - Proporción intermedia
        ImageIcon fotoFondo = new ImageIcon("src/img/fondo_senor.jpg");
        Image imagenEscalada = fotoFondo.getImage().getScaledInstance(625, 500, Image.SCALE_SMOOTH);
        JLabel lblFondoSenor = new JLabel(new ImageIcon(imagenEscalada));
        // Lo movemos solo -60 píxeles a la izquierda para centrar este nuevo tamaño
        lblFondoSenor.setBounds(-60, 0, 625, 500);
        panelDerecho.add(lblFondoSenor);

        add(panelIzquierdo); 
        add(panelDerecho);   
    }
}

class BordeRedondo implements Border {
    private int radio;

    BordeRedondo(int radio) {
        this.radio = radio;
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(10, 15, 10, 15);
    }

    public boolean isBorderOpaque() {
        return false; 
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.DARK_GRAY);
        g2.drawRoundRect(x, y, width - 1, height - 1, radio, radio);
    }
}