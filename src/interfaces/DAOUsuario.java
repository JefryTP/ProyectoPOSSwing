package interfaces;

import java.sql.ResultSet;
import java.util.List;
import modelo.Usuario;

public interface DAOUsuario {

    public void registrar(Usuario usu) throws Exception;

    public void modificar(Usuario usu) throws Exception;

    public void eliminar(Usuario usu) throws Exception;

    public List<Usuario> listar() throws Exception;

    public ResultSet buscar(String dato) throws Exception;

}
