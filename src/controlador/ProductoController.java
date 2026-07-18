package controlador;

import dao.DAOProductoImpl;
import interfaces.DAOProducto;
import modelo.Producto;

public class ProductoController {

    private final DAOProducto dao;

    public ProductoController() {
        this.dao = new DAOProductoImpl();
    }

    public Producto buscarPorCodigo(String codigo) {
        try {
            if (codigo == null || codigo.trim().isEmpty()) {
                System.out.println("Código no válido.");
                return null;
            }
            return dao.buscarPorCodigo(codigo.trim());
        } catch (Exception e) {
            System.out.println("Error al buscar el producto: " + e.getMessage());
            return null;
        }
    }
}
