/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.sql.Date;

/**
 *
 * @author Yoyin
 */
public class InformeRow {
    
    private String fec_medida;
    private String des_sector;
    private int cod_tramo;
    private String nombre;
    private String ind_tipo;
    private String nom_sentido;
    private String fecha;
    private int velocidad;
    private int datosVelocidad60;
    private double num_velocidad;

    public InformeRow() {
    }

    public InformeRow(String fec_medida, String des_sector, int cod_tramo, String nombre, String ind_tipo, String nom_sentido, String fecha, int velocidad, int datosVelocidad60, double num_velocidad) {
        this.fec_medida = fec_medida;
        this.des_sector = des_sector;
        this.cod_tramo = cod_tramo;
        this.nombre = nombre;
        this.ind_tipo = ind_tipo;
        this.nom_sentido = nom_sentido;
        this.fecha = fecha;
        this.velocidad = velocidad;
        this.datosVelocidad60 = datosVelocidad60;
        this.num_velocidad = num_velocidad;
    }

    public String getFec_medida() {
        return fec_medida;
    }

    public void setFec_medida(String fec_medida) {
        this.fec_medida = fec_medida;
    }

    public String getDes_sector() {
        return des_sector;
    }

    public void setDes_sector(String des_sector) {
        this.des_sector = des_sector;
    }

    public int getCod_tramo() {
        return cod_tramo;
    }

    public void setCod_tramo(int cod_tramo) {
        this.cod_tramo = cod_tramo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInd_tipo() {
        return ind_tipo;
    }

    public void setInd_tipo(String ind_tipo) {
        this.ind_tipo = ind_tipo;
    }

    public String getNom_sentido() {
        return nom_sentido;
    }

    public void setNom_sentido(String nom_sentido) {
        this.nom_sentido = nom_sentido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getDatosVelocidad60() {
        return datosVelocidad60;
    }

    public void setDatosVelocidad60(int datosVelocidad60) {
        this.datosVelocidad60 = datosVelocidad60;
    }

    public double getNum_velocidad() {
        return num_velocidad;
    }

    public void setNum_velocidad(double num_velocidad) {
        this.num_velocidad = num_velocidad;
    }

    @Override
    public String toString() {
        return "InformeRow{" + "fec_medida=" + fec_medida + ", des_sector=" + des_sector + ", cod_tramo=" + cod_tramo + ", nombre=" + nombre + ", ind_tipo=" + ind_tipo + ", nom_sector=" + nom_sentido + ", fecha=" + fecha + ", velocidad=" + velocidad + ", datosVelocidad60=" + datosVelocidad60 + ", num_velocidad=" + num_velocidad + '}';
    }
    
    
    
}
