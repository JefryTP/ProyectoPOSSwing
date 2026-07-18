package dao;

import interfaces.DAOProducto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Producto;

public class DAOProductoImpl extends Conexion implements DAOProducto{
     
    @Override
    public Producto buscarPorCodigo(String codigo) throws Exception {
        Producto p = null;
        try {
            this.conectar();
            String sql = "SELECT id, codigo, descripcion, id_categoria, precio, activo "
                    + "FROM producto WHERE codigo = ? AND activo = 1";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, codigo);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                p = new Producto();
                p.setId(rs.getInt("id"));
                p.setCodigo(rs.getString("codigo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                p.setPrecio(rs.getDouble("precio"));
                p.setActivo(rs.getBoolean("activo"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return p;
    }
}
