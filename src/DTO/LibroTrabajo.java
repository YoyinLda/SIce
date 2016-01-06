/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Yoyin
 */
public class LibroTrabajo {
    
    private XSSFWorkbook wb;
    private String Path;

    public LibroTrabajo() {
    }

    public XSSFWorkbook getWb() {
        return wb;
    }

    public void setWb(XSSFWorkbook wb) {
        this.wb = wb;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    @Override
    public String toString() {
        return "LibroTrabajo{" + "wb=" + wb + ", Path=" + Path + '}';
    }
    
    
    
}
