package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import org.apache.log4j.Logger;



public class Conexion {
    
    private final static Logger log = Logger.getLogger(Conexion.class);
    
        static Connection conn=null;
        static String bd=null;
        static String login=null;
        static String password=null;
        static String url=null; 
    
    public static Connection Enlace()throws SQLException    {
        
        bd="OFS1";
        login="sgt";
        password="sgt";
        url="jdbc:oracle:thin:@81.207.121.113:1521:OFS1"; 
        
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
    
    public static Connection EnlaceSGTCONF()throws SQLException    {
        
        bd="ITS";
        login="SGTCONF";
        password="SGTCONFSICE2013";
        url="jdbc:oracle:thin:@81.207.121.243:1521:ITS";
        
        try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection(url, login, password);
        }
        catch(ClassNotFoundException e )
        {
            log.fatal("Fallo en EnlaceSGTCONF " + e);
        }
        return conn;
    }
}
