/* 
 * Clase: ImprimirReporteJasper
 *
 * Version 1.0
 *
 * Fecha: 19-08-2008
 *
 * Autor: Jhon Jaime Mendez
 *
 * Copyright: JASoft
 */
 
 package com.JASoft.componentes;
 
import java.sql.*;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperPrintManager;

import java.util.Map;
import java.net.URL;
import java.io.OutputStream;
import java.io.InputStream;


/**
 * Esta clase permite imprimir un reporte JRXML de jasper
 */
 
 public class ImprimirReporteJasper {
 
 
   /**
     * Constructor por defecto
     */
    public ImprimirReporteJasper() {
        
    }
    
   /**
     * @param databaseName Base de datos,
     * @param userName Nombre de usuario
     * @param reportFile Nombre del reporte Jasper Report file (.jrxml)
     */
     
   public static void runReport(String databaseName, Connection conexion,InputStream reportFile) {
   
        try{
            
           
            
            JasperDesign jasperDesign = JRXmlLoader.load(reportFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conexion);
            JasperViewer.viewReport(jasperPrint,false);
        
        }catch(Exception ex) {
        	
            String connectMsg = "Error a crear reporte " + ex.getMessage() + " " + ex.getLocalizedMessage();
            javax.swing.JOptionPane.showMessageDialog(null,connectMsg);
            System.out.println(connectMsg);
        }
    }
    
    
  

    /**
     * @param databaseName Base de datos,
     * @param userName Nombre de usuario
     * @param parametros Parametros para el reporte
     * @param reportFile Nombre del reporte Jasper Report file (.jrxml)
     */    
    public static void runReport(String databaseName, Connection conexion,Map parametros,InputStream reportFile) {
   
        try{
        
            JasperDesign jasperDesign = JRXmlLoader.load(reportFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion);
            JasperPrintManager.printReport(jasperPrint, false);


        
        }catch(Exception ex) {
        	
            String connectMsg = "Error a crear reporte " + ex.getMessage() + " " + ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
    }

 
 }   