package controlador;

import dao.DAOProductoImpl;
import interfaces.DAOProducto;
import modelo.Producto;

public class ProductoController {
 
    // El controlador depende de la interfaz, no de la implementación (desacoplamiento)
    private final DAOProducto dao;
 
    public ProductoController() {
        this.dao = new DAOProductoImpl();
    }
 
    /**
     * Uso: Caja. Solo devuelve el producto si existe Y está activo, para
     * evitar vender algo que el admin desactivó.
     */
    public Producto buscarPorCodigo(String codigo) {
        try {
            if (codigo == null || codigo.trim().isEmpty()) {
                System.out.println("Código no válido.");
                return null;
            }
            Producto p = dao.buscarPorCodigo(codigo.trim());
            if (p == null || !p.isActivo()) {
                return null;
            }
            return p;
        } catch (Exception e) {
            System.out.println("Error al buscar el producto: " + e.getMessage());
            return null;
        }
    }
 
    /**
     * Uso: Administración de productos. Devuelve el producto exista o no
     * esté activo, porque desde aquí también se debe poder reactivarlo.
     */
    public Producto buscarPorCodigoAdmin(String codigo) {
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
 
    // Acción para registrar un producto nuevo (valida que el código no exista)
    public String registrar(Producto producto) {
        try {
            if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
                return "Error: el código no puede estar vacío.";
            }
            if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
                return "Error: la descripción no puede estar vacía.";
            }
            if (producto.getPrecio() <= 0) {
                return "Error: el precio debe ser mayor a 0.";
            }
 
            Producto existente = dao.buscarPorCodigo(producto.getCodigo().trim());
            if (existente != null) {
                return "Error: ya existe un producto con el código " + producto.getCodigo()
                        + ". Use Guardar/Modificar en su lugar.";
            }
 
            dao.registrar(producto);
            return "Producto registrado con éxito.";
 
        } catch (Exception e) {
            return "Error al registrar el producto: " + e.getMessage();
        }
    }
 
    // Acción para modificar un producto existente (valida que el código sí exista)
    public String modificar(Producto producto) {
        try {
            if (producto.getDescripcion() == null || producto.getDescripcion().trim().isEmpty()) {
                return "Error: la descripción no puede estar vacía.";
            }
            if (producto.getPrecio() <= 0) {
                return "Error: el precio debe ser mayor a 0.";
            }
 
            Producto existente = dao.buscarPorCodigo(producto.getCodigo().trim());
            if (existente == null) {
                return "Error: no existe ningún producto con el código " + producto.getCodigo()
                        + ". Use Agregar en su lugar.";
            }
 
            // Aseguramos que se actualice el registro correcto (por id real de la BD)
            producto.setId(existente.getId());
            dao.modificar(producto);
            return "Producto modificado con éxito.";
 
        } catch (Exception e) {
            return "Error al modificar el producto: " + e.getMessage();
        }
    }
 
    // Acción para eliminar físicamente un producto por su código
    public String eliminar(String codigo) {
        try {
            Producto existente = dao.buscarPorCodigo(codigo.trim());
            if (existente == null) {
                return "Error: no existe ningún producto con el código " + codigo;
            }
 
            dao.eliminar(existente.getId());
            return "Producto eliminado con éxito.";
 
        } catch (Exception e) {
            // Ocurre cuando el producto ya tiene ventas registradas (llave foránea)
            return "Error: no se puede eliminar. El producto tiene ventas registradas; "
                    + "puede desactivarlo en su lugar.";
        }
    }
}
