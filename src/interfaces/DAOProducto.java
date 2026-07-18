package interfaces;

import modelo.Producto;

public interface DAOProducto {
    public Producto buscarPorCodigo(String codigo) throws Exception;
}
