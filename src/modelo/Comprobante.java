package modelo;

public class Comprobante {
 
    private long id;
    private long idVenta;
    private int idTipoDoc;
    private String numDoc;
    private String nombreCliente;
    private String tipoPago;
    private double montoPagado;
    private double vuelto;
 
    public Comprobante() {
    }
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public long getIdVenta() {
        return idVenta;
    }
 
    public void setIdVenta(long idVenta) {
        this.idVenta = idVenta;
    }
 
    public int getIdTipoDoc() {
        return idTipoDoc;
    }
 
    public void setIdTipoDoc(int idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }
 
    public String getNumDoc() {
        return numDoc;
    }
 
    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }
 
    public String getNombreCliente() {
        return nombreCliente;
    }
 
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
 
    public String getTipoPago() {
        return tipoPago;
    }
 
    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
 
    public double getMontoPagado() {
        return montoPagado;
    }
 
    public void setMontoPagado(double montoPagado) {
        this.montoPagado = montoPagado;
    }
 
    public double getVuelto() {
        return vuelto;
    }
 
    public void setVuelto(double vuelto) {
        this.vuelto = vuelto;
    }
}
