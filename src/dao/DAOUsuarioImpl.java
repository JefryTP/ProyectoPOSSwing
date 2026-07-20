package dao;

import interfaces.DAOUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.Usuario;

public class DAOUsuarioImpl extends Conexion implements DAOUsuario {

    @Override
    public Usuario buscarPorDni(String dni) throws Exception {
        Usuario u = null;
        try {
            this.conectar();
            String sql = "SELECT id, dni, nombre, apellido, telefono, correo, clave, id_rol, activo "
                    + "FROM usuario WHERE dni = ? AND activo = 1";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, dni);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setDni(rs.getString("dni"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
                u.setCorreo(rs.getString("correo"));
                u.setClave(rs.getString("clave"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setActivo(rs.getBoolean("activo"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return u;
    }

    @Override
    public Usuario buscarPorId(int id) throws Exception {
        Usuario u = null;
        try {
            this.conectar();
            String sql = "SELECT id, dni, nombre, apellido, telefono, correo, clave, id_rol, activo "
                    + "FROM usuario WHERE id = ?";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setDni(rs.getString("dni"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                u.setTelefono(rs.getString("telefono"));
                u.setCorreo(rs.getString("correo"));
                u.setClave(rs.getString("clave"));
                u.setIdRol(rs.getInt("id_rol"));
                u.setActivo(rs.getBoolean("activo"));
            }
            rs.close();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
        return u;
    }
}