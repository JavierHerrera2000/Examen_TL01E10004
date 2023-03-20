package com.example.tl01e10004.tablas;

public class Contactos
{
    public int id;

    private String Pais;
    private String NombreContacto;
    private String NumeroContacto;
    private String NotaContacto;
    private byte[] imagen;

    public Contactos(int id, String pais, String nombreContacto, String numeroContacto, String notaContacto, byte[] imagen) {
        this.id = id;
        Pais = pais;
        NombreContacto = nombreContacto;
        NumeroContacto = numeroContacto;
        NotaContacto = notaContacto;
        this.imagen = imagen;
    }

    public Contactos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return Pais;
    }

    public void setPais(String pais) {
        Pais = pais;
    }

    public String getNombreContacto() {
        return NombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        NombreContacto = nombreContacto;
    }

    public String getNumeroContacto() {
        return NumeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        NumeroContacto = numeroContacto;
    }

    public String getNotaContacto() {
        return NotaContacto;
    }

    public void setNotaContacto(String notaContacto) {
        NotaContacto = notaContacto;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}
