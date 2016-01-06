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
import java.util.ArrayList;
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
                        + "'VIERNES', 5, 'SÁBADO', 6, 'DOMINGO',7) BETWEEN 2 AND 4 "
                        + "AND "
                        + "to_date(to_char(fecha, 'hh24:mi'), 'hh24:mi') "
                        + "BETWEEN to_date('" + dto.getHoraIni() + "', 'hh24:mi') and to_date('" + dto.getHoraFin() + "', 'hh24:mi') "
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
                ResultSet rs = informe.executeQuery();
                while (rs.next()) 
                {
                    InformeRow Informedto = new InformeRow();
                    Informedto.setFec_medida(rs.getString("fec_medida"));
                    Informedto.setDes_sector(rs.getString("des_sector"));
                    Informedto.setCod_tramo(rs.getInt("cod_tramo"));
                    Informedto.setNombre(rs.getString("nombre"));
                    Informedto.setInd_tipo(rs.getString("ind_tipo"));
                    Informedto.setNom_sentido(rs.getString("nom_sentido"));
                    Informedto.setFecha(rs.getString("fecha_"));
                    Informedto.setVelocidad(rs.getInt("velocidad"));
                    Informedto.setDatosVelocidad60(rs.getInt("datos.intensidad/60"));
                    Informedto.setNum_velocidad(rs.getDouble("num_velocidad"));
                    rows.add(Informedto);
                    Informedto = null;
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
