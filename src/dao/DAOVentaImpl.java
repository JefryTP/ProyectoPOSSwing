package dao;
 
import interfaces.DAOVenta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import modelo.Comprobante;
import modelo.ItemVenta;
import modelo.Venta;
 
public class DAOVentaImpl extends Conexion implements DAOVenta {
 
    @Override
    public long registrar(Venta venta, List<ItemVenta> items, Comprobante comprobante) throws Exception {
        long idVentaGenerada = -1;
 
        try {
            this.conectar();
            this.conn.setAutoCommit(false); // iniciamos la transacción
 
            // 1) Insertar la cabecera de la venta y recuperar su id generado
            String sqlVenta = "INSERT INTO venta (id_usuario, fecha, total) VALUES (?, ?, ?)";
            PreparedStatement stVenta = this.conn.prepareStatement(sqlVenta, Statement.RETURN_GENERATED_KEYS);
            stVenta.setInt(1, venta.getIdUsuario());
            stVenta.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            stVenta.setDouble(3, venta.getTotal());
            stVenta.executeUpdate();
 
            ResultSet rsKeys = stVenta.getGeneratedKeys();
            if (rsKeys.next()) {
                idVentaGenerada = rsKeys.getLong(1);
            }
            rsKeys.close();
            stVenta.close();
 
            // 2) Insertar cada línea del ticket en detalle_venta
            // (subconsulta por código evita tener que buscar antes el id_producto)
            String sqlDetalle = "INSERT INTO detalle_venta (id_venta, id_producto, cantidad, precio_unitario, subtotal) "
                    + "VALUES (?, (SELECT id FROM producto WHERE codigo = ?), ?, ?, ?)";
            PreparedStatement stDetalle = this.conn.prepareStatement(sqlDetalle);
            for (ItemVenta item : items) {
                stDetalle.setLong(1, idVentaGenerada);
                stDetalle.setString(2, item.getCodigo());
                stDetalle.setInt(3, item.getCantidad());
                stDetalle.setDouble(4, item.getPrecioUnitario());
                stDetalle.setDouble(5, item.getSubtotal());
                stDetalle.addBatch();
            }
            stDetalle.executeBatch();
            stDetalle.close();
 
            // 3) Insertar el comprobante
            String sqlComprobante = "INSERT INTO comprobante "
                    + "(id_venta, id_tipo_doc, num_doc, nombre_cliente, tipo_pago, monto_pagado, vuelto, fecha) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stComprobante = this.conn.prepareStatement(sqlComprobante);
            stComprobante.setLong(1, idVentaGenerada);
            stComprobante.setInt(2, comprobante.getIdTipoDoc());
            stComprobante.setString(3, comprobante.getNumDoc());
            stComprobante.setString(4, comprobante.getNombreCliente());
            stComprobante.setString(5, comprobante.getTipoPago());
            stComprobante.setDouble(6, comprobante.getMontoPagado());
            stComprobante.setDouble(7, comprobante.getVuelto());
            stComprobante.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
            stComprobante.executeUpdate();
            stComprobante.close();
 
            this.conn.commit(); // todo salió bien, confirmamos los 3 inserts juntos
 
        } catch (Exception e) {
            if (this.conn != null) {
                this.conn.rollback(); // algo falló, deshacemos todo (no queda venta a medias)
            }
            throw e;
        } finally {
            if (this.conn != null) {
                this.conn.setAutoCommit(true);
            }
            this.cerrar();
        }
 
        return idVentaGenerada;
    }
}
