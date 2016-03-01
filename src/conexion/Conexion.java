package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import org.apache.log4j.Logger;



public class Conexion {
    
    private final static Logger log = Logger.getLogger(Conexion.class);
    
    static Connection conn=null;
    static String bd="OFS1";
    static String login="sgt";
    static String password="sgt";
    static String url="jdbc:oracle:thin:@81.207.121.113:1521:OFS1";
    
    public static Connection Enlace()throws SQLException    {
        try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection(url, login, password);
        }
        catch(ClassNotFoundException e )
        {
            log.fatal("Fallo en Enlace " + e);
        }
        return conn;
    }
    
    
    public static Connection EnlaceSGTHIST()throws SQLException    {
        
        bd="ITS";
        login="SGTHIST";
        password="SGTHIST2013";
        url="jdbc:oracle:thin:@81.207.121.243:1521:ITS";
        
        try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection(url, login, password);
        }
        catch(ClassNotFoundException e )
        {
            log.fatal("Fallo en EnlaceSGTHIST " + e);
        }
        return conn;
    }
}
