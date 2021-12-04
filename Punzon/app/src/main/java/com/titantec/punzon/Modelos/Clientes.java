package com.titantec.punzon.Modelos;

import java.util.List;

public class Clientes extends com.titantec.punzon.Reportes.Clientes {

    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String documento;
    private String numero;
    private String email;
    private String contraseña;
    private String direccion;
    private String imagen;
    private List<String> carrito;

    public Clientes() {
    }

    public Clientes(String nombre, String apellido, String tipoDocumento, String documento, String numero, String email, String contraseña, String direccion, String imagen, List carrito) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.numero = numero;
        this.email = email;
        this.contraseña = contraseña;
        this.direccion = direccion;
        this.imagen = imagen;
        this.carrito = carrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<String> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<String> carrito) {
        this.carrito = carrito;
    }
}
