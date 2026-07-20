package controlador;
 
import dao.DAOUsuarioImpl;
import interfaces.DAOUsuario;
import modelo.Usuario;
 
public class UsuarioController {
 
    private final DAOUsuario dao;
 
    public UsuarioController() {
        this.dao = new DAOUsuarioImpl();
    }
 
    // Acción para autenticar un usuario contra la base de datos (dni + clave)
    public Usuario login(String dni, String clave) {
        try {
            if (dni == null || dni.trim().isEmpty() || clave == null || clave.isEmpty()) {
                System.out.println("Debe ingresar usuario y contraseña.");
                return null;
            }
 
            Usuario u = dao.buscarPorDni(dni.trim());
            if (u == null) {
                System.out.println("Usuario no encontrado o inactivo.");
                return null;
            }
 
            // NOTA: comparación en texto plano, válida solo para este proyecto de
            // curso. En un sistema real la clave debe guardarse con hash (BCrypt)
            // y compararse con BCrypt.checkpw(), nunca con equals().
            if (!u.getClave().equals(clave)) {
                System.out.println("Contraseña incorrecta.");
                return null;
            }
 
            return u;
 
        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return null;
        }
    }
}
