package dao;
 
import interfaces.DAOCategoria;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;
 
public class DAOCategoriaImpl extends Conexion implements DAOCategoria {
 
    @Override
    public List<Categoria> listar() throws Exception {
        List<Categoria> lista = new ArrayList<>();
        try {
            this.conectar();
            String sql = "SELECT id, nombre FROM categoria ORDER BY nombre";
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setId(rs.getInt("id"));
                c.setNombre(rs.getString("nombre"));
                lista.add(c);
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return lista;
    }
}