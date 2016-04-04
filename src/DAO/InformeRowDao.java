/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;
import conexion.Conexion;
import DTO.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;


/**
 *
 * @author Yoyin
 */
public class InformeRowDao {
    
    
    
    private final static Logger log = Logger.getLogger(HojaDao.class);
    
    public static ArrayList<InformeRow> ObtenerInfome(HojaDto dto)
    {
        
        log.trace("--------- Realizando consulta para informe --------------------");
        log.trace("------------- Hoja " + dto.getNombre() + " --------------------");
        ArrayList<InformeRow> rows = new ArrayList<>();
        try{
            Connection conn = Conexion.Enlace();
                String query = "select distinct (ci.fec_medida)"
                        +",inf.des_sector"
                        + ",ci.cod_tramo"
                        + ",pms.nombre"
                        + ",ci.ind_tipo"
                        + ",DECODE(INF.SENTIDO, '-','Norte-Sur', '+','Sur-Norte') NOM_Sentido"
                        + ",to_char(datos.fecha, 'dd-mm-yyyy hh24:mi:ss') as fecha_"
                        + ",DATOS.VELOCIDAD"
                        + ",DATOS.INTENSIDAD/60"
                        + ",ci.num_velocidad "
                        + "FROM ci_velocidades_medias ci"
                        + ",inf_autopista_temp inf"
                        + "," + dto.getTabla() + " datos"
                        + ",pms"
                        + " WHERE to_date(to_char(fec_medida, 'yyyy'), 'yyyy')=to_date('" + dto.getAno() + "', 'yyyy') "
                        + "AND "
                        + "DECODE(RTRIM(LTRIM(to_char(fec_medida, 'MONTH'))), "
                        + "'ENERO', 1,'FEBRERO', 2,'MARZO', 3, 'ABRIL', 4, 'MAYO' "
                        + ", 5, 'JUNIO', 6,'JULIO',7,'AGOSTO',8, 'SEPTIEMBRE',9"
                        + ", 'OCTUBRE',10,'NOVIEMBRE',11, 'DICIEMBRE',12) = " + dto.getMes()
                        + " AND "
                        + "DECODE(RTRIM(LTRIM(to_char(fec_medida, 'DAY'))),"
                        + "'LUNES', 1,'MARTES', 2,'MIÉRCOLES', 3, 'JUEVES', 4, "
                        + "'VIERNES', 5, 'SÁBADO', 6, 'DOMINGO',7) BETWEEN 2 AND 4 "
                        + "AND"
                        + " to_date(to_char(fec_medida, 'hh24:mi'), 'hh24:mi')=to_date('"+ dto.getHoraFin() +"', 'hh24:mi') "
                        + "AND ci.cod_tramo = ? "
                        + "AND inf.cod_tramo = ? "
                        + "AND ci.ind_sentido = ? "
                        + "AND inf.sentido = ? "
                        + "AND inf.cod_sector = ? "
                        + "AND INF.UBICACION_PM = ? "
                        + "AND INF.NOMBRE_PM = PMS.NOMBRE "
                        + "AND PMS.ID = DATOS.IDENTIF "
                        + "AND "
                        + "DECODE(RTRIM(LTRIM(to_char(fecha, 'DAY'))),"
                        + "'LUNES', 1,'MARTES', 2,'MIÉRCOLES', 3, 'JUEVES', 4, "
                        + "'VIERNES', 5, 'SÁBADO', 6, 'DOMINGO',7) BETWEEN ? AND ? "
                        + "AND to_date(to_char(DATOS.FECHA, 'hh24:mi'), 'hh24:mi') > to_date('" + dto.getHoraIni() + "', 'hh24:mi') "
                        + "AND to_date(to_char(DATOS.FECHA, 'hh24:mi'), 'hh24:mi') <= to_date('" + dto.getHoraFin() + "', 'hh24:mi') "
                        + "AND "
                        + "to_date(to_char(fecha, 'dd/mm/yyyy'), 'dd/mm/yyyy') "
                        + "= to_date(to_char(fec_medida, 'dd/mm/yyyy'), 'dd/mm/yyyy') "
                        + "order by fec_medida"
                        ;
                PreparedStatement informe = conn.prepareStatement(query);
                informe.setQueryTimeout(10);
                informe.setInt(1, dto.getTramo());
                informe.setInt(2, dto.getTramo());
                informe.setString(3, dto.getSentido());
                informe.setString(4, dto.getSentido());
                informe.setInt(5, dto.getSector());
                informe.setInt(6, dto.getUbicacionPM());
                informe.setInt(7, dto.getDia_ini());
                informe.setInt(8, dto.getDia_fin());
                ResultSet rs = informe.executeQuery();
                while (rs.next()) 
                {
                    InformeRow informedto = new InformeRow();
                    informedto.setFec_medida(rs.getString("fec_medida"));
                    informedto.setDes_sector(rs.getString("des_sector"));
                    informedto.setCod_tramo(rs.getInt("cod_tramo"));
                    informedto.setNombre(rs.getString("nombre"));
                    informedto.setInd_tipo(rs.getString("ind_tipo"));
                    informedto.setNom_sentido(rs.getString("nom_sentido"));
                    informedto.setFecha(rs.getString("fecha_"));
                    informedto.setVelocidad(rs.getInt("velocidad"));
                    informedto.setDatosVelocidad60(rs.getInt("datos.intensidad/60"));
                    informedto.setNum_velocidad(rs.getDouble("num_velocidad"));
                    
                    rows.add(informedto);
                    informedto = null;
                    }
                rs = null;
                informe.close();
                conn.close();
        }catch(SQLException s){
            log.fatal("Error SQL al ObtenerInfome: "+s.getMessage());
            log.info(dto.getTabla());
            
        } catch(Exception | OutOfMemoryError e){
            log.fatal(e);
        }
        
        log.trace("--------------- Listo Hoja " + dto.getNombre() 
                + " Con " + rows.size() + " Filas -----------------------");
        return rows;
    }
    
    private static InformeRow ArreglaDato(HojaDto hoja, InformeRow informedto)
    {
        System.out.println("Entrando a arreglar dato");
        InformeRow dto = new InformeRow();
        try{
            Connection conn = Conexion.Enlace();
            
            String newTable = hoja.getTabla().substring(0, 12) + '5' + hoja.getTabla().substring(13, 19);
            System.out.println("nueva tabla " + newTable);
            
            System.out.println("fecha string " + informedto.getFecha());
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = sdf.parse(informedto.getFecha());
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            
            String dateStr = sdfDate.format(date);
            String hora = sdfTime.format(date);
            
            System.out.println("date " + date);
            System.out.println("date str " + dateStr);
            
            
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek( Calendar.MONDAY);
            calendar.setMinimalDaysInFirstWeek( 4 );
            calendar.setTime(date);
            int dia = calendar.get(Calendar.DAY_OF_WEEK);
            System.out.println("dia " + dia);
            int semana = calendar.get(Calendar.WEEK_OF_MONTH);
            System.out.println("semana " + semana);

            
            
            String query = "select distinct (ci.fec_medida)"
                        +",inf.des_sector"
                        + ",ci.cod_tramo"
                        + ",pms.nombre"
                        + ",ci.ind_tipo"
                        + ",DECODE(INF.SENTIDO, '-','Norte-Sur', '+','Sur-Norte') NOM_Sentido"
                        + ",to_char(datos.fecha, 'dd-mm-yyyy hh24:mi:ss') as fecha_"
                        + ",DATOS.VELOCIDAD"
                        + ",DATOS.INTENSIDAD/60"
                        + ",ci.num_velocidad "
                        + "FROM ci_velocidades_medias ci"
                        + ",inf_autopista_temp inf"
                        + "," + newTable + " datos"
                        + ",pms"
                        + " WHERE to_date(to_char(fec_medida, 'yyyy'), 'yyyy')=to_date('" + (hoja.getAno() - 1) + "', 'yyyy') "
                        + "AND "
                        + "DECODE(RTRIM(LTRIM(to_char(fec_medida, 'MONTH'))), "
                        + "'ENERO', 1,'FEBRERO', 2,'MARZO', 3, 'ABRIL', 4, 'MAYO' "
                        + ", 5, 'JUNIO', 6,'JULIO',7,'AGOSTO',8, 'SEPTIEMBRE',9"
                        + ", 'OCTUBRE',10,'NOVIEMBRE',11, 'DICIEMBRE',12) = " + hoja.getMes()
                        + " AND "
                        + "DECODE(RTRIM(LTRIM(to_char(fec_medida, 'DAY'))),"
                        + "'LUNES', 1,'MARTES', 2,'MIÉRCOLES', 3, 'JUEVES', 4, "
                        + "'VIERNES', 5, 'SÁBADO', 6, 'DOMINGO',7) BETWEEN 2 AND 4 "
                        + "AND"
                        + " to_date(to_char(fec_medida, 'hh24:mi'), 'hh24:mi')=to_date('"+ hoja.getHoraFin() +"', 'hh24:mi') "
                        + "AND ci.cod_tramo = " + hoja.getTramo() + " "
                        + "AND inf.cod_tramo = " + hoja.getTramo() + " "
                        + "AND ci.ind_sentido = '" + hoja.getSentido() + "' "
                        + "AND inf.sentido = '" + hoja.getSentido() + "' "
                        + "AND inf.cod_sector = " + hoja.getSector() + " "
                        + "AND INF.UBICACION_PM = " + hoja.getUbicacionPM() + " "
                        + "AND INF.NOMBRE_PM = PMS.NOMBRE "
                        + "AND PMS.ID = DATOS.IDENTIF "
                        + "AND "
                        + "to_char(fec_medida, 'D') = " + dia
                        + " AND "
                        + "to_char(to_date(fec_medida, 'dd/mm/yyyy'), 'W') = " + semana
                        + " AND "
                        + "to_date(to_char(fecha, 'hh24:mi:ss'), 'hh24:mi:ss') = to_date('" + hora + "', 'hh24:mi:ss') "
                        + "AND "
                        + "to_date(to_char(fecha, 'dd/mm/yyyy'), 'dd/mm/yyyy') "
                        + "= to_date(to_char(fec_medida, 'dd/mm/yyyy'), 'dd/mm/yyyy') "
                          + " AND "
                          + "nombre = '" + informedto.getNombre() + "' "
                        + "order by fec_medida"
                        ;
            PreparedStatement informe = conn.prepareStatement(query);
            informe.setQueryTimeout(10);
            
            log.trace("query: " + query);
            
            ResultSet rs = informe.executeQuery();
            
            while(rs.next()) 
                {
                    System.out.println("hay resultado");
                    dto.setFec_medida(rs.getString("fec_medida"));
                    dto.setDes_sector(rs.getString("des_sector"));
                    dto.setCod_tramo(rs.getInt("cod_tramo"));
                    dto.setNombre(rs.getString("nombre"));
                    dto.setInd_tipo(rs.getString("ind_tipo"));
                    dto.setNom_sentido(rs.getString("nom_sentido"));
                    dto.setFecha(rs.getString("fecha_"));
                    dto.setVelocidad(rs.getInt("velocidad"));
                    dto.setDatosVelocidad60(rs.getInt("datos.intensidad/60"));
                    dto.setNum_velocidad(rs.getDouble("num_velocidad"));
                    
                    System.out.println("objeto row" + dto.toString());
                }
            informe.close();
            conn.close();
        }catch(SQLException s){
            log.fatal("Error SQL al corregir informe: "+s.getMessage());
            log.fatal((s));
            
        } catch (ParseException ex) {
            log.fatal("Error parse exception " + ex);
        }
        System.out.println("Saliendo Arreglar dato");
        
        return dto;
    }
    
    
    public static ArrayList<InformeRow> ObtenerInfomeSGTHIST(HojaDto dto)
    {
        
        log.trace("--------- Realizando consulta para informe con base de datos nueva --------------------");
        log.trace("------------- Hoja " + dto.getNombre() + " --------------------");
        ArrayList<InformeRow> rows = new ArrayList<>();
        try{
            Connection conn = Conexion.EnlaceSGTHIST();
                String query = "SELECT DISTINCT(CI.FEC_MEDIDA)"
                        +",S.DESC_SECTOR"
                        + ",CI.COD_TRAMO"
                        + ",TE.IDENT"
                        + ",CI.IND_TIPO"
                        + ",DECODE(CI.IND_SENTIDO, '-','Norte-Sur', '+','Sur-Norte') NOM_Sentido"
                        + ",to_char(DATOS.FECHA, 'dd-mm-yyyy hh24:mi:ss') as fecha_"
                        + ",DATOS.VELOCIDAD"
                        + ",DATOS.INTENSIDAD/60"
                        + ",CI.NUM_VELOCIDAD "
                        + "FROM CI_VELOCIDADES_MEDIAS CI"
                        + ",SGTCONF.ITS_SECTORES S"
                        + ",SGTCONF.ITS_TRAMOS T"
                        + ",SGTCONF.ITS_TRAMOS_EQUIPOS TE"
                        + "," + dto.getTabla() + " datos"
                        + ",SGTCONF.CONF_EQUIPOS CE"
                        + ",SGTCONF.CONF_EQUIPOS_POSICIONES CEP"
                        
                        + " WHERE "
                        + "CI.COD_TRAMO = T.NOMBRE_TRAMO "
                        + "AND T.COD_SECTOR = S.COD_SECTOR "
                        + "AND TE.NOMBRE_TRAMO = T.NOMBRE_TRAMO "
                        + "AND DATOS.IDENTIF = TE.IDENT "
                        + "AND CI.IND_SENTIDO = TE.SENTIDO "
                        + "AND TE.IDENT = CE.IDENT "
                        + "AND CE.IDENT = CEP.IDENT "
                        + "AND CE.DESCRIPCION <> 'NO EXISTE' "
                        
                        + "AND to_date(to_char(CI.FEC_MEDIDA, 'yyyy'), 'yyyy')=to_date('" + dto.getAno() + "', 'yyyy') "
                        + "AND DECODE(RTRIM(LTRIM(to_char(CI.FEC_MEDIDA, 'MONTH'))), "
                        + "'ENERO', 1,'FEBRERO', 2,'MARZO', 3, 'ABRIL', 4, 'MAYO' "
                        + ", 5, 'JUNIO', 6,'JULIO',7,'AGOSTO',8, 'SEPTIEMBRE',9"
                        + ", 'OCTUBRE',10,'NOVIEMBRE',11, 'DICIEMBRE',12) = " + dto.getMes()
                        + " AND DECODE(RTRIM(LTRIM(to_char(CI.FEC_MEDIDA, 'DAY'))),"
                        + "'LUNES', 1,'MARTES', 2,'MIÉRCOLES', 3, 'JUEVES', 4, "
                        + "'VIERNES', 5, 'SÁBADO', 6, 'DOMINGO',7) BETWEEN " + dto.getDia_ini() + " AND " + dto.getDia_fin() + " "
                        + "AND to_date(to_char(CI.FEC_MEDIDA, 'hh24:mi'), 'hh24:mi')=to_date('"+ dto.getHoraFin() +"', 'hh24:mi') "
                        + "AND ci.cod_tramo = " + dto.getTramo() + " "
                        + "AND S.COD_SECTOR = " + dto.getSector() + " "
                        + "AND CI.IND_SENTIDO = '" + dto.getSentido() + "' "
                        + "AND CEP.ID_UBICACION = " + dto.getUbicacionPM() + " "
                        + "AND to_date(to_char(DATOS.FECHA, 'hh24:mi'), 'hh24:mi') > to_date('" + dto.getHoraIni() + "', 'hh24:mi') "
                        + "AND to_date(to_char(DATOS.FECHA, 'hh24:mi'), 'hh24:mi') <= to_date('" + dto.getHoraFin() + "', 'hh24:mi') "
                        + "AND TO_DATE(TO_CHAR(DATOS.FECHA, 'dd/mm/yyyy'), 'dd/mm/yyyy') = TO_DATE(TO_CHAR(CI.FEC_MEDIDA, 'dd/mm/yyyy'), 'dd/mm/yyyy') "
                        + "ORDER BY CI.FEC_MEDIDA, TE.IDENT,to_char(DATOS.FECHA, 'dd-mm-yyyy hh24:mi:ss')"
                        ;
                log.trace("query nueva " + query);
                PreparedStatement informe = conn.prepareStatement(query);
                //informe.setQueryTimeout(10);
                ResultSet rs = informe.executeQuery();
                while (rs.next()) 
                {
                    InformeRow informedto = new InformeRow();
                    informedto.setFec_medida(rs.getString("fec_medida"));
                    informedto.setDes_sector(rs.getString("desc_sector"));
                    informedto.setCod_tramo(rs.getInt("cod_tramo"));
                    informedto.setNombre(rs.getString("ident"));
                    informedto.setInd_tipo(rs.getString("ind_tipo"));
                    informedto.setNom_sentido(rs.getString("nom_sentido"));
                    informedto.setFecha(rs.getString("fecha_"));
                    informedto.setVelocidad(rs.getInt("velocidad"));
                    informedto.setDatosVelocidad60(rs.getInt("datos.intensidad/60"));
                    informedto.setNum_velocidad(rs.getDouble("num_velocidad"));
                    
                    rows.add(informedto);
                    informedto = null;
                    }
                rs = null;
                informe.close();
                conn.close();
        }catch(SQLException s){
            log.fatal("Error SQL al ObtenerInfome: "+s.getMessage());
            log.info(dto.getTabla());
            
        } catch(Exception | OutOfMemoryError e){
            log.fatal(e);
        }
        
        log.trace("--------------- Listo Hoja " + dto.getNombre() 
                + " Con " + rows.size() + " Filas -----------------------");
        return rows;
    }
    
}
