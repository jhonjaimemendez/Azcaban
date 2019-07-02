/**
 * Clase: OperacionesBasesDeDatos
 * 
 * @version  1.0
 * 
 * @since 16-04-2007
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
 
package com.JASoft.componentes;

import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JTextField;


/**
 * Esta clase define un conjunto de operaciones DML y recuperacion de datos sobre la base de datos
 */

public abstract class OperacionesBasesDeDatos  extends Validador{
	
	
  /**
	* Actualiza registro(s) en la Base de datos
	*
	*@param nombreTabla Nombre de la tabla a actualizar
	*@param campos  Array de los nombres de campos y sus valores a actualizar
	*@param condicion Condición de filtro para actualizar
	*@param conMySQL Conexion de la Base de datos
	*@param escribir true si desea mostrar mensajes desde el metodo; false en caso contrario
	*/
  final public boolean actualizar(String nombreTabla,String[] campos,String condicion, ConectarMySQL conMySQL,boolean escribir) throws Exception{ 
  	   
  	   boolean resultado = false;
  	   
  	   // Se actualiza en la tabla que llega por prametro    
       String sql = "Update "+ nombreTabla + " Set ";

             
       for (int i=0;i<campos.length;i++) {
           sql = sql + campos[i];
           
           if (i<(campos.length-1))
             sql = sql + ",";   

        }
       
        
        sql = sql + "  Where "+ condicion;
  	      
  	     System.out.println(sql); 	
  	   // Se actualiza el registro en la base de datos y se registra en un log para auditorias
       conMySQL.ejecutaSentencia(sql,getObtenerFechaCompletaServidor(conMySQL),conMySQL);
      
       if (escribir)
        // Se muestra el mensaje de insecin de exitosa
	    Mensaje("Registro Actualizado","Información ",JOptionPane.INFORMATION_MESSAGE);                           
	
	    resultado = true;
			  
	   
        return resultado;	  
  	}
  	
  	
  /**
	* Busca registro(s) en la Base de datos
	*
	* @param sentenciaSQL sentencia SQL a ejecutar contra la Base de datos
	* @param conMySQL Conexion de la Base de datos
	* @return Conjunto de resultados
	*
	*/

  	final public ResultSet buscarRegistro(String sentenciaSQL,ConectarMySQL conMySQL) {
 		
 		ResultSet resultado = null;
 		 	
 		
 		 try {
 	
 		    // Se realiza la consulta en la base de datos
 		    resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		   
 		 } catch (Exception e) {
 		    
 		     System.out.println("Error" +e);
 		       	
 		 }     
  
 	   
 	     return resultado;
 	  
 	}
 	
  	/**
	 * Elimina registro(s) en la Base de datos
	 *
	 * @param nombreTabla Nombre de la tabla a actualizar
	 * @param conMySQL Conexion de la Base de datos
	 * @param condicion Condición de filtro para actualizar
	 * @param escribir true si desea mostrar mensajes desde el metodo; false en caso contrario
     *
	 */

    final public boolean eliminarRegistro(String nombreTabla,String condicion,ConectarMySQL conMySQL,boolean escribir) throws Exception {
        
        
        boolean resultado = false;  		
  	
  		    // Se crea la sentencia SQL
  			String sql = "Delete From "+nombreTabla+" Where  "+condicion;
  		System.out.println(sql);
  			//Se borra el registro
  			conMySQL.ejecutaSentencia(sql,getObtenerFechaCompletaServidor(conMySQL),conMySQL);
  			
  			if (escribir)
  			  // Se muestra el mensaje de eliminacin de exitosa
		      Mensaje("Registro eliminado","Información ",JOptionPane.INFORMATION_MESSAGE);                           
		
		    resultado = true;
	
	
            return resultado;	  
  	
  	}
    
    
   /**
	 * Guarda registro(s) en la Base de datos
	 *
	 * @param nombreTabla Nombre de la tabla a actualizar
	 * @param campos  Array de los nombres de los valores (datos) a almecenar en un tabla en la BD
	 * @param conMySQL Conexion de la Base de datos
	 * @param escribir true si desea mostrar mensajes desde el metodo; false en caso contrario
     *
	 */
 	
  	 final public boolean guardar(String nombreTabla,String[] campos,ConectarMySQL conMySQL,boolean escribir) throws  Exception { 
  	  
  	    boolean resultado = false;
  	
  	    String sql = generarSentenciaInsert(nombreTabla,campos);
  	  
  	    System.out.println(sql);
  	    
  	  	// Se inserta el registro en la base de datos
		conMySQL.ejecutaSentencia(sql,getObtenerFechaCompletaServidor(conMySQL),conMySQL);
		 
		 
		     
		// Se muestra el mensaje de insecin de exitosa
	    if (escribir) 
	      Mensaje("Registro Insertado","Información ",JOptionPane.INFORMATION_MESSAGE);                           
		
		resultado = true;
		
       
       
       return resultado; 	  
  	
  	}
  	
  	
  	
  	/**
	 * Guarda registro(s) en la Base de datos
	 *
	 * @param nombreTabla Nombre de la tabla a actualizar
	 * @param array  Array de los nombres de los valores (datos) a almecenar en un tabla en la BD
	 * @param conMySQL Conexion de la Base de datos
	 * @param escribir true si desea mostrar mensajes desde el metodo; false en caso contrario
     *
	 */
 	 final public boolean guardar(String nombreTabla,String[][] array,ConectarMySQL conMySQL,int numColumnas,boolean escribir) throws  Exception{ 
  	     
  	   boolean resultado = true;
  	
  	   for (int i=0; i<array.length;i++) {
  	  	 
  	  	   String campos[] = new String[numColumnas];
  	  	    
  	  	   // Se sacan las filas que se desean insertar
  	  	   for (int j=0;j<numColumnas;j++)
  	  	    
  	  	     campos[j] = array[i][j];
  	  	      
  	  	     // Se crea la expresion para insertar
  	  	     String sql = generarSentenciaInsert(nombreTabla,campos);
  	  	     
  	  	     System.out.println(sql);
  	         
  	    	     // Se inserta el registro en la base de datos
	         conMySQL.ejecutaSentencia(sql,getObtenerFechaCompletaServidor(conMySQL),conMySQL);

  	   }
		    // Se muestra el mensaje de inserción de exitosa
		    if (escribir) 
		       Mensaje("Registro Insertado","Información ",JOptionPane.INFORMATION_MESSAGE);                           
		
		     return resultado;
	 
  	}
  	
  	
  	
  	/**
	 * Genera una sentencia DML de insercion de datos a partir de un array
	 *
	 * @param nombreTabla Nombre de la tabla a actualizar
	 * @param campos  Array de los nombres de los valores (datos) a almecenar en un tabla en la BD
	 * return String senetencia de insercion de datos
	 *
	 */
 	
  	final public String generarSentenciaInsert(String nombreTabla,String[] campos) {
  		 
  		 
	  	  String sql = "Insert Into "+nombreTabla+" Values(";
	  	  
	  	  // Se especifican los campos que llegan como parametro
	  	  for (int i=0;i<campos.length;i++) {
	  	    
	  	    sql = sql+campos[i];
	  	  
	  	     if (i < (campos.length-1))
	  	       sql += ",";
	  	     else 
	  	       sql += ")";
	  	     
	  	  }	 
	  	  
	  	  return sql;
	  	  
  	}
  	
  	
  	/**
	 * Muestra una casilla de mensajes con el un titulo, mensaje y tipo de mensaje
	 *
	 * @param mensaje Mensaje a mostrar
	 * @param titulo  Titulo del mensaje
	 * @param tipo  Tipo de mensaje: 0 error, 1 Informacion o confirmacion, 2 Precaucion
	 *
	 */
 	 
    final public void Mensaje(String mensaje,String titulo, int tipo) {
    	
    	JOptionPane.showMessageDialog(null,mensaje,titulo,tipo);
    	
    }
	
}