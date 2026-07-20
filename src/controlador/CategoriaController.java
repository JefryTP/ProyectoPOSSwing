package controlador;
 
import dao.DAOCategoriaImpl;
import interfaces.DAOCategoria;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;
 
public class CategoriaController {
 
    private final DAOCategoria dao;
 
    public CategoriaController() {
        this.dao = new DAOCategoriaImpl();
    }
    public List<Categoria> listar() {
        try {
            return dao.listar();
        } catch (Exception e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
