package modelo;

public class Usuario {
    private int id;
    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String clave;
    private int idRol;
    private String cargoRol; 
    private int activo;

    public Usuario(int id, String dni, String nombre, String apellido, String telefono, String correo, String clave, int idRol, String cargoRol, int activo) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.clave = clave;
        this.idRol = idRol;
        this.cargoRol = cargoRol;
        this.activo = activo;
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

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getActivo() {
        return activo;
    }

    public void setActivo(int activo) {
        this.activo = activo;
    }
   
    
}
