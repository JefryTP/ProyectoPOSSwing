package proyectoposswing;

import vistas.LoginElaborado;
public class Main {

    public static void main(String[] args) {
       java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginElaborado().setVisible(true);
            }
        });
    }
    
}
