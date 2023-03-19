package com.example.tl01e10004.configuracion;

public class Transacciones
{
    //Nombre Base de datos
    public static final String NameDatabase="TL01E1";
    //Creacion de tabla y objetos
    public static final String tablacontact="contactos";

    /* Campos de la tabla contactos */
    public static String id="id";
    public static String Pais="Pais";
    public static String NombreContacto="NombreContacto";
    public static String NumeroContacto="NumeroContacto";
    public static String NotaContacto="NotaContacto";
    public static String imagen="imagen";

    //Consulta SQL DDL
    public static String CreateTBContactos="CREATE TABLE contactos (id INTEGER PRIMARY KEY AUTOINCREMENT,"+"Pais TEXT, NombreContacto TEXT, NumeroContacto TEXT, NotaContacto TEXT ,imagen BLOB)";
    public static String DropTBContactos="DROP TABLE IF EXIST contactos";
}
