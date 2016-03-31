/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

/**
 *
 * @author Yoyin
 */
public class HojaDto {
    
    private int id;
    private String nombre;
    private String tabla;
    private int ano;
    private int mes;
    private String horaIni;
    private String horaFin;
    private int tramo;
    private String sentido;
    private int sector;
    private int ubicacionPM;
    private int dia_ini;
    private int dia_fin;

    public HojaDto() {
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getHoraIni() {
        return horaIni;
    }

    public void setHoraIni(String horaIni) {
        this.horaIni = horaIni;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public int getTramo() {
        return tramo;
    }

    public void setTramo(int tramo) {
        this.tramo = tramo;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public int getSector() {
        return sector;
    }

    public void setSector(int sector) {
        this.sector = sector;
    }

    public int getUbicacionPM() {
        return ubicacionPM;
    }

    public void setUbicacionPM(int ubicacionPM) {
        this.ubicacionPM = ubicacionPM;
    }

    public int getDia_ini() {
        return dia_ini;
    }

    public void setDia_ini(int dia_ini) {
        this.dia_ini = dia_ini;
    }

    public int getDia_fin() {
        return dia_fin;
    }

    public void setDia_fin(int dia_fin) {
        this.dia_fin = dia_fin;
    }

    @Override
    public String toString() {
        return "HojaDto{" + "id=" + id + ", nombre=" + nombre + ", tabla=" + tabla + ", ano=" + ano + ", mes=" + mes + ", horaIni=" + horaIni + ", horaFin=" + horaFin + ", tramo=" + tramo + ", sentido=" + sentido + ", sector=" + sector + ", ubicacionPM=" + ubicacionPM + ", dia_ini=" + dia_ini + ", dia_fin=" + dia_fin + '}';
    }

    
    

    
    

    

    
    
    
    
    
}
