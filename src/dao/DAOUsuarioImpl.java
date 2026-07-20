package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import interfaces.DAOUsuario;
import modelo.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DAOUsuarioImpl extends Conexion implements DAOUsuario {

    @Override
public ResultSet buscar(String dato) throws Exception {

    String sql = "SELECT u.dni, u.nombre, u.apellido, "
            + "u.telefono, u.correo, u.clave, "
            + "r.cargo AS rol, u.activo "
            + "FROM usuario u "
            + "INNER JOIN rol r ON u.id_rol = r.id "
            + "WHERE u.dni LIKE ? "
            + "OR u.nombre LIKE ? "
            + "OR r.cargo LIKE ?";

    conectar();

    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setString(1, "%" + dato + "%");
    ps.setString(2, "%" + dato + "%");
    ps.setString(3, "%" + dato + "%");

    return ps.executeQuery();
}

    @Override
    public void registrar(Usuario usu) throws Exception {

        String sql = "INSERT INTO usuario(dni, nombre, apellido, telefono, correo, clave, id_rol, activo) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

        conectar();

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, usu.getDni());
        ps.setString(2, usu.getNombre());
        ps.setString(3, usu.getApellido());
        ps.setString(4, usu.getTelefono());
        ps.setString(5, usu.getCorreo());
        ps.setString(6, usu.getClave());
        ps.setInt(7, usu.getIdRol());
        ps.setInt(8, usu.getActivo());

        ps.executeUpdate();

        cerrar();
    }

   @Override
public void modificar(Usuario usu) throws Exception {

    String sql = "UPDATE usuario SET "
            + "dni = ?, "
            + "nombre = ?, "
            + "apellido = ?, "
            + "telefono = ?, "
            + "correo = ?, "
            + "clave = ?, "
            + "id_rol = ?, "
            + "activo = ? "
            + "WHERE dni = ?";

    conectar();

    PreparedStatement ps = conn.prepareStatement(sql);

    ps.setString(1, usu.getDni());        // DNI nuevo
    ps.setString(2, usu.getNombre());
    ps.setString(3, usu.getApellido());
    ps.setString(4, usu.getTelefono());
    ps.setString(5, usu.getCorreo());
    ps.setString(6, usu.getClave());
    ps.setInt(7, usu.getIdRol());
    ps.setInt(8, usu.getActivo());
    ps.setString(9, usu.getDni());        // DNI para el WHERE

    ps.executeUpdate();

    cerrar();
}
public void eliminar(Usuario usu) throws Exception {

    String sql = "DELETE FROM usuario WHERE dni = ?";

    conectar();

    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, usu.getDni());

    ps.executeUpdate();

    cerrar();
}

    @Override
    public List<Usuario> listar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Map<Integer, String> listarRoles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
