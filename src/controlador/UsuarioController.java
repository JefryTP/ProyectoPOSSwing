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
                System.out.println("Debe ingresar dni y clave.");
                return null;
            }

            Usuario u = dao.buscarPorDni(dni.trim());
            if (u == null) {
                System.out.println("Usuario no encontrado o inactivo.");
                return null;
            }

            if (!u.getClave().equals(clave)) {
                System.out.println("Clave incorrecta.");
                return null;
            }

            return u;

        } catch (Exception e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
            return null;
        }
    }

    // Acción para buscar un usuario por su id (ej. para mostrar el nombre del cajero en la boleta)
    public Usuario buscarPorId(int id) {
        try {
            if (id <= 0) {
                System.out.println("ID no válido.");
                return null;
            }
            return dao.buscarPorId(id);
        } catch (Exception e) {
            System.out.println("Error al buscar el usuario: " + e.getMessage());
            return null;
        }
    }
}
