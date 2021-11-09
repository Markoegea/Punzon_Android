package co.edu.unab.punzon.data.model;

import java.util.ArrayList;
import java.util.List;

import co.edu.unab.punzon.R;

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

    public static List<Producto> buldierProductos(){
        List<Producto> productos = new ArrayList<>(1);
        Producto p1= new Producto("Prensa para soldar", 0001,70000,
                "Mordazas en U que proporciona mejor visibilidad y mayor superficie de " +
                        "trabajo", R.drawable.prensaparasoldarirwin9r);
        Producto p2 = new Producto("Probador de corriente- Tester", 0002,15000,
                "Punta fabricada en acero al carbono",R.drawable.probadordecorreintetester20cmccaimantruperproco2ox);

        productos.add(p1);
        productos.add(p2);

        return productos;
    }


}
