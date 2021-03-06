/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.*;
import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Yoyin
 */
public class HojaDao {
    
    private final static Logger log = Logger.getLogger(HojaDao.class);
    
    public static ArrayList<HojaDto> ObtenerHojas()
    {
        log.trace("----------Entrando a buscar Hojas ----------------");
        ArrayList<HojaDto> hojas = new ArrayList<>();
        try{
                Connection conexion = Conexion.EnlaceSGTCONF();
                String query = "SELECT * FROM SGTCONF.I_VELOCIDADES "
                        + "WHERE VIGENTE = 1 "
                        /*
                        + "AND ID IN (221,222,223,234,237,238,239) "
                        */
                        + "ORDER BY ID"
                        ;
                log.trace(query);
                PreparedStatement hojasq = conexion.prepareStatement(query);
                ResultSet rs = hojasq.executeQuery();
                while (rs.next()) 
                {
                    HojaDto h = new HojaDto();
                    
                    h.setId(rs.getInt("id"));
                    h.setNombre(rs.getString("nombre"));
                    h.setTabla(rs.getString("tabla"));
                    h.setHoraIni(rs.getString("horaini"));
                    h.setHoraFin(rs.getString("horafin"));
                    h.setTramo(rs.getInt("tramo"));
                    h.setSentido(rs.getString("sentido"));
                    h.setSector(rs.getInt("sector"));
                    h.setUbicacionPM(rs.getInt("ubicacionpm"));
                    h.setDia_ini(rs.getInt("dia_ini"));
                    h.setDia_fin(rs.getInt("dia_fin"));
                    hojas.add(h);
                    h = null;
                }
                hojasq.close();
                conexion.close();
                
        } catch(SQLException s){
            log.fatal("Error SQL al ObtenerHojas: " + s.getMessage());
        } catch(Exception | OutOfMemoryError e){
            log.fatal(e);
        }
        log.trace("---------------- Saliendo de Buscar Hojas -----------------");
        log.trace("---------------- Son " + hojas.size() + " Hojas -------------");
        
        return hojas;
    }
    
    public static ArrayList<HojaDto> ObtenerHojasRango(int ini, int fin)
    {
        log.trace("----------Entrando a buscar Hojas Rango ----------------");
        ArrayList<HojaDto> hojas = new ArrayList<>();
        try{
                Connection conexion = Conexion.EnlaceSGTCONF();
                String query = "SELECT * FROM SGTCONF.I_VELOCIDADES "
                        + "WHERE ID BETWEEN "
                        + ini
                        + " AND "
                        + fin
                        + " AND VIGENTE = 1"
                        + " ORDER BY ID";
                log.trace("query rango hojas " + query);
                PreparedStatement hojasq = conexion.prepareStatement(query);
                ResultSet rs = hojasq.executeQuery();
                while (rs.next()) 
                {
                    HojaDto h = new HojaDto();
                    
                    h.setId(rs.getInt("id"));
                    h.setNombre(rs.getString("nombre"));
                    h.setTabla(rs.getString("tabla"));
                    h.setHoraIni(rs.getString("horaini"));
                    h.setHoraFin(rs.getString("horafin"));
                    h.setTramo(rs.getInt("tramo"));
                    h.setSentido(rs.getString("sentido"));
                    h.setSector(rs.getInt("sector"));
                    h.setUbicacionPM(rs.getInt("ubicacionpm"));
                    h.setDia_ini(rs.getInt("dia_ini"));
                    h.setDia_fin(rs.getInt("dia_fin"));
                    hojas.add(h);
                    h = null;
                }
                hojasq.close();
                conexion.close();
                
        } catch(SQLException s){
            log.fatal("Error SQL al ObtenerHojasRango: "+s.getMessage());
        } catch(Exception | OutOfMemoryError e){
            log.fatal(e);
        }
        log.trace("---------------- Saliendo de Buscar Hojas -----------------");
        log.trace("---------------- Son " + hojas.size() + " Hojas -------------");
        
        return hojas;
    }
    
}
