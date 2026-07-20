package controlador;

import dao.DAOUsuarioImpl;
import interfaces.DAOUsuario;
import java.sql.ResultSet;
import java.util.List;
import modelo.Usuario;

public class UsuarioController {

    private final DAOUsuario dao;

    public UsuarioController() {
        this.dao = new DAOUsuarioImpl();
    }

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
    
    public void registrar(Usuario usu) throws Exception {
        dao.registrar(usu);
    }

    public void modificar(Usuario usu) throws Exception {
        dao.modificar(usu);
    }

    public void eliminar(Usuario usu) throws Exception {
        dao.eliminar(usu);
    }

    public List<Usuario> listar() throws Exception {
        return dao.listar();
    }

    public ResultSet buscar(String dato) throws Exception {
        return dao.buscar(dato);
    }
}