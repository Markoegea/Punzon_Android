package com.titantec.punzon.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Productos implements Parcelable {

    private String nombre;
    private String id;
    private String precio;
    private String descripcion;
    private String imagen;
    private String cantidad;
    private String marca;

    public Productos() {
    }

    public Productos(String nombre, String id, String precio, String descripcion, String imagen, String cantidad, String marca) {
        this.nombre = nombre;
        this.id = id;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.cantidad = cantidad;
        this.marca = marca;
    }

    protected Productos(Parcel in) {
        nombre = in.readString();
        id = in.readString();
        precio = in.readString();
        descripcion = in.readString();
        imagen = in.readString();
        cantidad = in.readString();
        marca = in.readString();
    }

    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nombre);
        parcel.writeString(id);
        parcel.writeString(precio);
        parcel.writeString(descripcion);
        parcel.writeString(imagen);
        parcel.writeString(cantidad);
        parcel.writeString(marca);
    }
}
