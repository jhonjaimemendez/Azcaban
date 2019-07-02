/**
 * Clase: GenerarArchivosPlanos
 * 
 * @version  1.0
 * 
 * @since 23-11-2005
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */


package com.JASoft.componentes;

import java.io.File;
import java.io.FileOutputStream;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;


/**
 * Esta clase permite crear archivos planos a partir de una consulta SQL
 */

final public class GenerarArchivosPlanos {
	
  ConectarMySQL conMySQL;
  
  /**
   * Constructor  
   * @param p_conMySQL Conexion a la BD
   */
   
  public GenerarArchivosPlanos(ConectarMySQL p_conMySQL) {
  	
  	conMySQL = p_conMySQL;
  	
  }	
	
	
  /**
   * Genera el archivo plano en base una sentencia de consulta SQL y un nombre de archivo
   *
   * @param sentenciaSQL Sentencia de consulta SQL
   * @param nombreArchivo nombre del archivo a generar
   */
  	
  public void generaArcPlanos(String sentenciaSQL,String nombreArchivo) {
	    
	    String datosPlanos ="";
	    
	   try {
	     
	       // Se llama el metodo buscarRegistro de la clase conMySQL
		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
	      
	                                      
 	       // Se verifica si tiene datos
 	       if (resultado!=null)	{
 	   	    
 	   	    
 	   	     // Se crea una Metadatos del resultado
 	   	     ResultSetMetaData rsmd = resultado.getMetaData();
 	   	     
 	   	     while (resultado.next()) {
 	   	     
 	   	      for (int i = 1; i <= rsmd.getColumnCount(); i++) {
 	   	          
 	   	          //Se hace una validacion sobre la direccion que sea de 20
 	   	          datosPlanos = datosPlanos + camposArchivosPlanos(rsmd.getColumnName(i).equals("RESCOMDI") ? 20 : rsmd.getColumnDisplaySize(i),resultado.getString(i),rsmd.getColumnTypeName(i));
 	   	          
 	   	           
 	   	      }
 	   	           datosPlanos = datosPlanos + System.getProperty ("line.separator");
 	   	       
 	   	      }
 	   	      
 	   	      // Se crea el archivo plano 
 	   	      File file = new File(nombreArchivo);
             
              // Se crea un canal de salida para escribir en el archivo
              FileOutputStream canalSalida = new FileOutputStream(file);
              canalSalida.write(datosPlanos.getBytes());
              canalSalida.close();
            
            
 	   	   }  
 	   	     
 		 } catch (Exception e) {
 		   	  System.out.println("Error"+e);
 		 }
  }
 	   
 	   
 /**
   * Genera los campos del archivo plano en base al tamaño de cada columna en la tabla de consulta
   *
   * @param tamaño Tamaño de la columna
   * @param valor Valor columna
   * @param tipoColumna Tipo de columna
   * @return Columna configurada
   */
  	   
  static private String camposArchivosPlanos(int tamaño, String valor,String tipoColumna) {
 	   	
 	   	String columnaValor = "";
 	   
 	   if (valor!=null) 
 	   	    columnaValor = valor;
 	  
 	   int espacios = tamaño - columnaValor.length();
 	   	
	   if (!(tipoColumna.equals("DATETIME")||tipoColumna.equals("DATE"))) {
 	   
 	   	 
 	   	 if (tipoColumna.equals("VARCHAR") || tipoColumna.equals("CHAR")) {
 	   	   
 	   	    //Se adiciona espacios en blancos en la cadena espacios
 	   	    for (int i=0;i<espacios;i++)
 	   	       columnaValor = columnaValor.concat(" ");
 	   	 
 	   	 } else
 	   	    
 	   	    //Se adiciona ceros
 	   	    for (int i=0; i<espacios; i++)
 	   	       columnaValor = "0" + columnaValor;
 	   	 
 	   } else
 	      if (valor!=null) 
 	         columnaValor = valor.substring(0,4)+valor.substring(5,7)+valor.substring(8,10);
 	      else 
 	   	     for (int i=0;i<8;i++)
 	   	       columnaValor = columnaValor.concat(" ");
 	   	       
 	   	  // Se retorna la cadena con los caracteres en blanco
 	   	  return columnaValor;
  }
 	   
} 
	    
	    	
	    	
            
        