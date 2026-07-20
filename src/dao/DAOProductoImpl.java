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
                    + "FROM producto WHERE codigo = ?";
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
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return p;
    }
 
    @Override
    public void registrar(Producto producto) throws Exception {
        try {
            this.conectar();
            String sql = "INSERT INTO producto (codigo, descripcion, id_categoria, precio, activo) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, producto.getCodigo());
            st.setString(2, producto.getDescripcion());
            st.setInt(3, producto.getIdCategoria());
            st.setDouble(4, producto.getPrecio());
            st.setBoolean(5, producto.isActivo());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
 
    @Override
    public void modificar(Producto producto) throws Exception {
        try {
            this.conectar();
            String sql = "UPDATE producto SET descripcion = ?, id_categoria = ?, precio = ?, activo = ? "
                    + "WHERE id = ?";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, producto.getDescripcion());
            st.setInt(2, producto.getIdCategoria());
            st.setDouble(3, producto.getPrecio());
            st.setBoolean(4, producto.isActivo());
            st.setInt(5, producto.getId());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
 
    @Override
    public void eliminar(int id) throws Exception {
        try {
            this.conectar();
            String sql = "DELETE FROM producto WHERE id = ?";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }
}
