package interfaces;

import java.util.List;
import modelo.Comprobante;
import modelo.ItemVenta;
import modelo.Venta;
 
public interface DAOVenta {
    public long registrar(Venta venta, List<ItemVenta> items, Comprobante comprobante) throws Exception;
}
