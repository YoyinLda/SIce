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
                Connection conexion = Conexion.Enlace();
                String query = "SELECT * FROM I_VELOCIDADES ORDER BY ID";
                
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
        log.trace("----------Entrando a buscar Hojas ----------------");
        ArrayList<HojaDto> hojas = new ArrayList<>();
        try{
                Connection conexion = Conexion.Enlace();
                String query = "SELECT * FROM I_VELOCIDADES "
                        + "WHERE ID BETWEEN "
                        + ini
                        + " AND "
                        + fin
                        + " ORDER BY ID";
                
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
                    hojas.add(h);
                    h = null;
                }
                hojasq.close();
                conexion.close();
                
        } catch(SQLException s){
            log.fatal("Error SQL al ObtenerHojas: "+s.getMessage());
        } catch(Exception | OutOfMemoryError e){
            log.fatal(e);
        }
        log.trace("---------------- Saliendo de Buscar Hojas -----------------");
        log.trace("---------------- Son " + hojas.size() + " Hojas -------------");
        
        return hojas;
    }
    
}
