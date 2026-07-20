package controlador;

import dao.Conexion;
import interfaces.DAOUsuario;
import dao.DAOUsuarioImpl;
import modelo.Usuario;

import java.util.List;
import java.util.Map;

public class UsuarioController {

    private final DAOUsuarioImpl dao = new DAOUsuarioImpl();

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

    public Map<Integer, String> listarRoles() throws Exception {
        return dao.listarRoles();
    }
    public void buscar(String dato){



}
}