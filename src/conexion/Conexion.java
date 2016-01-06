package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import org.apache.log4j.Logger;



public class Conexion {
    
    private final static Logger log = Logger.getLogger(Conexion.class);
    
    static Connection conn=null;
    static Statement st=null;
    static ResultSet rs=null;
    
 
    
    static String bd="OFS1";
    static String login="sgt";
    static String password="sgt";
    static String url="jdbc:oracle:thin:@81.207.121.113:1521:OFS1";
    
    public static Connection Enlace(Connection conn)throws SQLException    {
        try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn=DriverManager.getConnection(url, login, password);
        }
        catch(ClassNotFoundException e )
        {
            log.fatal("Clase no encontrada");
        }
        return conn;
    }
}
