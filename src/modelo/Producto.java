package modelo;

public class Producto {
    private int id;
    private String codigo;
    private String descripcion;
    private int idCategoria;
    private double precio;
    private boolean activo;
 
    public Producto() {
    }
 
    public Producto(int id, String codigo, String descripcion, int idCategoria, double precio, boolean activo) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.precio = precio;
        this.activo = activo;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getCodigo() {
        return codigo;
    }
 
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
 
    public int getIdCategoria() {
        return idCategoria;
    }
 
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
 
    public double getPrecio() {
        return precio;
    }
 
    public void setPrecio(double precio) {
        this.precio = precio;
    }
 
    public boolean isActivo() {
        return activo;
    }
 
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
