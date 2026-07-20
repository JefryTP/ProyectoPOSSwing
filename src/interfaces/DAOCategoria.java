package interfaces;
 
import java.util.List;
import modelo.Categoria;
 
public interface DAOCategoria {
 
    public List<Categoria> listar() throws Exception;
}
