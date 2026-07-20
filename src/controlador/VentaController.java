package controlador;

import dao.DAOVentaImpl;
import interfaces.DAOVenta;
import java.util.List;
import modelo.Comprobante;
import modelo.ItemVenta;
import modelo.Venta;

public class VentaController {

    private final DAOVenta dao;

    public VentaController() {
        this.dao = new DAOVentaImpl();
    }

    public String registrarVenta(Venta venta, List<ItemVenta> items, Comprobante comprobante) {
        try {
            if (items == null || items.isEmpty()) {
                return "Error: el ticket no tiene productos agregados.";
            }
            if (comprobante.getNumDoc() == null || comprobante.getNumDoc().trim().isEmpty()) {
                return "Error: debe ingresar el número de documento del cliente.";
            }
            if (comprobante.getMontoPagado() < venta.getTotal()) {
                return "Error: el monto pagado es menor al total a pagar.";
            }

            long idVenta = dao.registrar(venta, items, comprobante);
            venta.setId(idVenta);
            return "Venta registrada con éxito. N° de venta: " + idVenta
                    + " | Vuelto: S/. " + String.format("%.2f", comprobante.getVuelto());

        } catch (Exception e) {
            System.out.println("Error al registrar la venta: " + e.getMessage());
            return "Error al registrar la venta: " + e.getMessage();
        }
    }
}
