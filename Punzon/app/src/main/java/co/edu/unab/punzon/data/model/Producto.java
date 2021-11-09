package co.edu.unab.punzon.data.model;

public class Producto {
    private String nombre;
    private long id;
    private double precio;
    private String descripcion;
    private int imagen;

    public Producto(String nombre, long id, double precio, String descripcion, int imagen) {
        this.nombre = nombre;
        this.id = id;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public long getId() {
        return id;
    }

    public double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getImagen() {
        return imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }


}
