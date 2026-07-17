package modelo;

public class Usuario {
    private int id;
    private String dni;
    private String nombre;
    private String telefono;
    private String clave;
    private int idRol;
    private String cargoRol; 

    public Usuario(int id, String dni, String nombre, String telefono, String clave, int idRol, String cargoRol) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.telefono = telefono;
        this.clave = clave;
        this.idRol = idRol;
        this.cargoRol = cargoRol;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getCargoRol() {
        return cargoRol;
    }

    public void setCargoRol(String cargoRol) {
        this.cargoRol = cargoRol;
    }
    
}
