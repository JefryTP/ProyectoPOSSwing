package modelo;

public class Venta {
     
    private long id;
    private int idUsuario;
    private double total;
 
    public Venta() {
    }
 
    public Venta(long id, int idUsuario, double total) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.total = total;
    }
 
    public long getId() {
        return id;
    }
 
    public void setId(long id) {
        this.id = id;
    }
 
    public int getIdUsuario() {
        return idUsuario;
    }
 
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
 
    public double getTotal() {
        return total;
    }
 
    public void setTotal(double total) {
        this.total = total;
    }
}
