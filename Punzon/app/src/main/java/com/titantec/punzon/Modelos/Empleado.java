package com.titantec.punzon.Modelos;

import java.util.ArrayList;

public class Empleado {
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private String tipoDocumento;
    private String documento;
    private String tipoEmpleado;
    private String cargo;
    private String especialidad;
    private String numero;
    private String email;
    private String contraseña;

    public Empleado() {
    }

    public Empleado(String tipoEmpleado, String tipoDocumento, String documento, String cargo, String especialidad, String numero, String email, String contraseña) {
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.tipoEmpleado = tipoEmpleado;
        this.cargo = cargo;
        this.especialidad = especialidad;
        this.numero = numero;
        this.email = email;
        this.contraseña = contraseña;
    }

    public Empleado(String nombre1, String nombre2, String apellido1, String apellido2, String tipoDocumento, String documento, String tipoEmpleado, String cargo, String especialidad, String numero, String email, String contraseña) {
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.tipoEmpleado = tipoEmpleado;
        this.cargo = cargo;
        this.especialidad = especialidad;
        this.numero = numero;
        this.email = email;
        this.contraseña = contraseña;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
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

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
}
