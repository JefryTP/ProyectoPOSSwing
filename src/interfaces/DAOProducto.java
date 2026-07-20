package interfaces;

import modelo.Producto;

public interface DAOProducto {
    
    public Producto buscarPorCodigo(String codigo) throws Exception;
 
    public void registrar(Producto producto) throws Exception;
 
    public void modificar(Producto producto) throws Exception;
 
    public void eliminar(int id) throws Exception;
}
