package modelo;
 
public class Categoria {
 
    private int id;
    private String nombre;
 
    public Categoria() {
    }
 
    public Categoria(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    // Para que se vea el nombre (y no la referencia del objeto) dentro del JComboBox
    @Override
    public String toString() {
        return nombre;
    }
}
