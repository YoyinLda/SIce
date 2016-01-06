/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NEGOCIO;

import DTO.InformeRow;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;
import java.io.FileOutputStream;
import java.io.IOException ;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import org.apache.log4j.Logger;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.openxml4j.exceptions.OpenXML4JRuntimeException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Yoyin
 */
public class Excel {
    
    private final static Logger log = Logger.getLogger(Excel.class);
    
    
    public static XSSFWorkbook abrirXlsx()
    {
        XSSFWorkbook  wb = new XSSFWorkbook();
        System.gc();
        log.trace("------------------ abriendo Excel Base.xlsx ----------------------");
        
        
        try
        {
            InputStream ExcelFileToRead = new FileInputStream("Base.xlsx");
            wb = new XSSFWorkbook(ExcelFileToRead);
        } catch(FileNotFoundException e){
            log.fatal(e);
        } catch(IOException e){
            log.fatal(e);
        } catch(Exception | OutOfMemoryError e){
            log.fatal(e);
        }
        log.trace("---------------- Listo apertura Excel Base -----------------------");
        return wb;
        
    }
    
    public static String guardarXlsx(XSSFWorkbook wb) throws IOException
    {
        System.gc();
        log.trace("-------------------- Escribiendo y guardando excel final  -----------------------------");
        String strDate;
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            Date now = new Date();
            strDate = sdfDate.format(now);
            
            
            String doc = "C:\\Informes_Java\\Velocidades-" + strDate + "-" + now.getHours()
                    +now.getMinutes()
                    +now.getSeconds()
                    + ".xlsx";
            
            try
            {
                FileOutputStream fileOut = new FileOutputStream(doc);
                wb.write(fileOut);
                fileOut.flush();
		fileOut.close();
                log.trace("para la barra");
            }catch (FileNotFoundException ex) {
                log.fatal("FileNotFoundException " + ex + " mensage: " + ex.getMessage());
                
            }catch (OpenXML4JRuntimeException ex)
            {
                log.fatal("OpenXML4JRuntimeException " + ex);
                doc = null;
            } catch(Exception | OutOfMemoryError e){
                log.fatal(e);
                doc = null;
        }
            log.trace("------------------ Escritura y guardago OK ---------------------------------");
            
            return doc;
    }
    
    public static XSSFWorkbook escribirExcel(XSSFWorkbook wb, ArrayList<InformeRow> list, String h)
    {
        System.gc();
        log.trace("----------------------- Escribiendo WorkBook ----------------------------------");
        log.trace("----------------------- Hoja " + h + " -------------------------------------");
        XSSFWorkbook workBook = wb;
        int rowIni = 0;
        int rowFin = list.size();
        String hoja = h;
        XSSFSheet  sheet;
        
        try
        {
            sheet = workBook.getSheet(hoja);
            XSSFRow  row;
            XSSFCell  cell = null;

            CellStyle style;
            style = wb.createCellStyle();
            DataFormat format = wb.createDataFormat();
            style.setDataFormat(format.getFormat("0.00"));
            
            CellStyle dateStyle;
            dateStyle = wb.createCellStyle();
            DataFormat dateFormat = wb.createDataFormat();
            dateStyle.setDataFormat(dateFormat.getFormat("dd-mm-yyyy H:mm:ss"));

            for(int r = rowIni; r < rowFin; r++)
            {
                row = sheet.createRow(r + 10);
                InformeRow dto = list.get(r);
                //Date fecha = strToDate(dto.getFecha());

                cell = row.getCell(0, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getDes_sector());
                cell = row.getCell(1, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getCod_tramo());
                cell = row.getCell(2, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getNombre());
                cell = row.getCell(3, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getInd_tipo());
                cell = row.getCell(4, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getNom_sentido());
                cell = row.getCell(5, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getFecha());
                cell = row.getCell(6, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getVelocidad());
                cell = row.getCell(7, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getDatosVelocidad60());
                cell = row.getCell(8, Row.CREATE_NULL_AS_BLANK);
                cell.setCellValue(dto.getNum_velocidad());
                cell.setCellStyle(style);
            }
        } catch(OutOfMemoryError | Exception e){
            log.fatal(e);
        }
        
        log.trace("---------------------------- fin escritura WB. " + h + " ----------------------------");
        return workBook;
    }
    
    
 
    
    
    
}
