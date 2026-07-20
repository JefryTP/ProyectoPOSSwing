package dao;

import interfaces.DAOUsuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
                u = mapearUsuario(rs);
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
                u = mapearUsuario(rs);
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
    public void registrar(Usuario usu) throws Exception {
        try {
            this.conectar();
            String sql = "INSERT INTO usuario(dni, nombre, apellido, telefono, correo, clave, id_rol, activo) "
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, usu.getDni());
            st.setString(2, usu.getNombre());
            st.setString(3, usu.getApellido());
            st.setString(4, usu.getTelefono());
            st.setString(5, usu.getCorreo());
            st.setString(6, usu.getClave());
            st.setInt(7, usu.getIdRol());
            st.setBoolean(8, usu.isActivo());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void modificar(Usuario usu) throws Exception {
        try {
            this.conectar();
            String sql = "UPDATE usuario SET dni = ?, nombre = ?, apellido = ?, telefono = ?, "
                    + "correo = ?, clave = ?, id_rol = ?, activo = ? WHERE id = ?";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, usu.getDni());
            st.setString(2, usu.getNombre());
            st.setString(3, usu.getApellido());
            st.setString(4, usu.getTelefono());
            st.setString(5, usu.getCorreo());
            st.setString(6, usu.getClave());
            st.setInt(7, usu.getIdRol());
            st.setBoolean(8, usu.isActivo());
            st.setInt(9, usu.getId());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    @Override
    public void eliminar(Usuario usu) throws Exception {
        try {
            this.conectar();
            String sql = "DELETE FROM usuario WHERE dni = ?";
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, usu.getDni());
            st.executeUpdate();
            st.close();
        } catch (Exception e) {
            throw e;
        } finally {
            this.cerrar();
        }
    }

    @Override
    public List<Usuario> listar() throws Exception {
        List<Usuario> lista = new ArrayList<>();
        try {
            this.conectar();
            String sql = "SELECT id, dni, nombre, apellido, telefono, correo, clave, id_rol, activo "
                    + "FROM usuario ORDER BY nombre";
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
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

    @Override
    public ResultSet buscar(String dato) throws Exception {
        // NOTA: a diferencia de los demás métodos, aquí NO se cierra la conexión
        // antes de devolver el ResultSet (si se cerrara, el ResultSet quedaría
        // inválido). Esto significa que la conexión queda abierta hasta que el
        // recolector de basura la reclame — no es ideal para un uso intensivo,
        // pero es aceptable para este proyecto de curso. Una alternativa más
        // robusta sería que este método arme y devuelva un List<Usuario> ya
        // materializado (cerrando la conexión aquí mismo), igual que listar().
        String sql = "SELECT u.dni, u.nombre, u.apellido, "
                + "u.telefono, u.correo, u.clave, "
                + "r.cargo AS rol, u.activo "
                + "FROM usuario u "
                + "INNER JOIN rol r ON u.id_rol = r.id "
                + "WHERE u.dni LIKE ? "
                + "OR u.nombre LIKE ? "
                + "OR r.cargo LIKE ?";

        this.conectar();
        PreparedStatement st = this.conn.prepareStatement(sql);
        st.setString(1, "%" + dato + "%");
        st.setString(2, "%" + dato + "%");
        st.setString(3, "%" + dato + "%");
        return st.executeQuery();
    }

    private Usuario mapearUsuario(ResultSet rs) throws Exception {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setDni(rs.getString("dni"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setTelefono(rs.getString("telefono"));
        u.setCorreo(rs.getString("correo"));
        u.setClave(rs.getString("clave"));
        u.setIdRol(rs.getInt("id_rol"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}
