package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    protected Connection conn;
    private final String DB_URL = "jdbc:mysql://localhost:3306/bdahorramax";
    private final String USUAR = "root";
    private final String CONTR = "admin123";

    public void conectar() throws Exception {
        try {
            conn = DriverManager.getConnection(DB_URL, USUAR, CONTR);
        } catch (SQLException e) {
            System.out.println("Error de conexión"+ e.getMessage());
    e.printStackTrace();
        }
    }

    public void cerrar() throws Exception {
        if (conn != null) {
            if (!conn.isClosed()) {
                conn.close();
            }
        }
    }
}
