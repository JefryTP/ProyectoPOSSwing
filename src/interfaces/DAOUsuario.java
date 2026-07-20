package interfaces;

import modelo.Usuario;

public interface DAOUsuario {

    public Usuario buscarPorDni(String dni) throws Exception;

    public Usuario buscarPorId(int id) throws Exception;
}