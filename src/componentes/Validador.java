/**
 * Clase: Validador
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

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

import java.text.SimpleDateFormat;



/**
 *Esta clase instancia un conjunto de clases que validan la entrada de informacion
 *en las interfaces graficas, tales como: Que solo se digiten numeros en un JTextField,
 *validacion de fecha y caracteres etc.
 */
  
  public abstract class Validador extends AtributoVisual {
  	
  	//Declaracion de Variables
  
  	private  static ValidarEntradaNumeroJTextField getValidarEntradaNumeroJTextField;
    
    private  static ValidarEntradaLetraJTextField  getValidarEntradaLetraJTextField;  
  	
  	private static  ConvetirMayCaracteres getConvertirMayuscula ;
  	
  	private static SimpleDateFormat formatoFecha;
  	
  	private ValidarFecha valFecha ;
  	
   
   //Metodos analizadores
   
	/**
	 * Retorna un objeto que permite la entrada de solo numero en un JTextField o JPasswordField se debe
	 * agregar como un evento de tipo addKeyListener
	 */
	
   final public ValidarEntradaNumeroJTextField getValidarEntradaNumeroJTextField() {
   	  
   	   
   	   if  (getValidarEntradaNumeroJTextField == null)
   	   
   	       getValidarEntradaNumeroJTextField = new ValidarEntradaNumeroJTextField();
   	   
   	   
   	   return getValidarEntradaNumeroJTextField;
   	 
   }
   
   
   /**
	 * Retorna un objeto que permite la entrada de solo letra en un JTextField o JPasswordField se debe
	 * agregar como un evento de tipo addKeyListener
	 */
	 
	final public ValidarEntradaLetraJTextField getValidarEntradaLetraJTextField() {
   	 
   	 
   	  if  (getValidarEntradaLetraJTextField == null)
   	   
   	       getValidarEntradaLetraJTextField =  new ValidarEntradaLetraJTextField();
   	   
   	   
   	  return getValidarEntradaLetraJTextField;
   	 
   }
   
   
   
  	
  /**
   * Retorna un objeto que permite validar la entrada de caracteres siempre en mayuscula
   *
   */
 
   final public ConvetirMayCaracteres getConvertirMayuscula() {
   	 
   	 if (getConvertirMayuscula == null)
   	 
   	   getConvertirMayuscula = new ConvetirMayCaracteres();
   	 
   	 return getConvertirMayuscula;
   	 
   }
    
   
  /**
   * Retorna la fecha completa del servidor 
   * @param conMySQL Conexion de la Base de datos
   * @return String Fecha Completa	
   */
  
   final public String getObtenerFechaCompletaServidor(ConectarMySQL conMySQL) {
   	 
   	   return ObtenerFechaServidor.getObtenerFechaCompleta(conMySQL);
   	 
   }
   
   
  /**
   * Retorna la fecha del servidor  en formato aaaa-mm-dd
   * @param conMySQL Conexion de la Base de datos
   * @return String Fecha en fotmato aaaa-mm-dd
   *	
   */
  
   final public String getObtenerFecha(ConectarMySQL conMySQL) {
   	 
   	   return ObtenerFechaServidor.getObtenerFecha(conMySQL);
   	 
   }
   
   
   
  /**
   *  Retorna el año de la fecha actual
   *  @param conMySQL Conexion de la Base de datos
   *  @return String Año de la fecha del servidor
   */
  
   final public String getObtenerAño(ConectarMySQL conMySQL) {
   	 
   	   return ObtenerFechaServidor.getObtenerAño(conMySQL);
   	 
   }
   
   
   /**
   *  Retorna el mes de la fecha actual
   *  @param conMySQL Conexion de la Base de datos
   *  @return String Año de la fecha del servidor
   */
  
   final public String getObtenerMes(ConectarMySQL conMySQL) {
   	 
   	   return ObtenerFechaServidor.getObtenerMes(conMySQL);
   	 
   }
   
   
  /**
   *  Retorna el dia de la fecha actual
   *  @param conMySQL Conexion de la Base de datos
   *  @return String Dia de la fecha del servidor
   */
  
  final  public String getObtenerDia(ConectarMySQL conMySQL) {
   	 
   	   return ObtenerFechaServidor.getObtenerDia(conMySQL);
   	 
   }
   
   
  /**
   * Retorna true si la fecha es correcta false si no
   * @param fechaValidar Fecha a validar
   * @return boolean true: es correcta false: es incorrecta 
   */

   final public boolean esFecha(String fechaValidar) {
   	 
   	    boolean resultadoBoolean = false;
   	 
   	 	//metodo para validar si la fecha es correcta
        try {
        	
            if (formatoFecha == null) {
              
              
              formatoFecha = new SimpleDateFormat("yyyy/MM/dd");
              formatoFecha.setLenient(false);
            
            }
            
              
            Date fecha = formatoFecha.parse(fechaValidar);
            
            resultadoBoolean = true;
            
            
        } catch (Exception e) {}
        
        return resultadoBoolean;
    }
   	 
   
  /**
   * Retorna true si el correo es correcto false si no
   * @param correo Correo a validar
   * @return boolean true: es correcta false: es incorrecta 
   */
   
  final public boolean esEmail(String correo) {
  	
  	
  	    boolean resultado = false; //Se valida que no tenga caracteres especiales
  	
        Pattern pat = null;
        
        Matcher mat = null;   
             
        pat = Pattern.compile("^([0-9a-zA-Z]([_.w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        
        mat = pat.matcher(correo);
        
        if (mat.find())
            
            resultado =  true;
            
        return  resultado;  
            
    }
    
   
   
   /**
   * Retorna true si la pagina Web es correcta false si no
   * @param paginaWeb Pagina a validar
   * @return boolean true: es correcta false: es incorrecta 
   */
   
  final public boolean esPaginaWeb(String paginaWeb) {
  	
  	
  	    boolean resultado = false; //Se valida que no tenga caracteres especiales
  	
        Pattern pat = null;
        
        Matcher mat = null;   
             
        pat = Pattern.compile("^(www.*([0-9a-zA-Z][-w]*[0-9a-zA-Z].)+([a-zA-Z]{2,9}.)+[a-zA-Z]{2,3})$");
        
        mat = pat.matcher(paginaWeb);
        
        if (mat.find())
            
            resultado =  true;
            
        return  resultado;  
            
    }
  
  
   
   /**
 	* Valida la fecha digitada en un JTextField
 	*
 	* @param fechaValidar Fecha a validar
 	* @param comodin true: Valida que la fecha a validar sea menor a la actual del sistema
 	* @return boolean true: es correcta false: es incorrecta 
 	*/
   
   final public boolean getValidarFecha(String fechaValidar,boolean comodin) {
   	 
   	 return valFecha.validarFecha(fechaValidar);
   	 
   }
   
   //Otros Metodos
   

   /**
     * Valida los campos (JTextField) obligatorios que se encuentran en una interfaz grafica
     *
     *@param campos Array de JTextField que son obligatorios
     *@return int Indice del Array que falta digitar informacion, si todo esta bien devuelve una posicion mayor al tamaño del array
     */
    
    final public static int getValidarJTextFieldObligatorios(JTextField[] campos) {
 		
	 		int indice = 0;
	 		
	 		boolean sw = true;
	 		
	 		// Se recorre el array de JTextField y se verifica si tiene por lo menos un carecter
	 		// o si el textField tiene el formato  String "aaaa-mm-dd"
	 		while ((indice < campos.length) &&  sw) {

	 			if (campos[indice].getText().length() == 0 || (campos[indice].getText().equals("aaaa-mm-dd")))
	 			
	 			  sw = false;
	 			
	 			else 
	 			
	 			 indice++;  
	 		
	 		}
	 		
	 		return indice;		
 	 }
  	 
  	 
  	 
  	 
  	 final public static int getValidarJTextFieldObligatorios(JTextField[] campos,int limite) {
 		
	 		int indice = 0;
	 		
	 		boolean sw = true;
	 		
	 		// Se recorre el array de JTextField y se verifica si tiene por lo menos un carecter
	 		// o si el textField tiene el formato  String "aaaa-mm-dd"
	 		while ((indice < limite) &&  sw) {

	 			if (campos[indice].getText().length() == 0 || (campos[indice].getText().equals("aaaa-mm-dd")))
	 			
	 			  sw = false;
	 			
	 			else 
	 			
	 			 indice++;  
	 		
	 		}
	 		
	 		return indice;		
 	 }
  	 
  	/**
     * Determina a traves de una fecha si esta es o no feriada
     *
     * @param conMySQL conexion a la base de datos
     * @param fecha Fecha a saber si es o no feriada
     * @return boolean true: Feriado falso : No es Feriado
     */
  	 final public static boolean esFeriado(ConectarMySQL conMySQL,String fecha) {
 		    
 		    boolean respuestaBoolean = false;
 		    
	 		try {
	 			
	 			String sentencia = "Select 'x' From Festivos Where FechaFes ='"+fecha+"'";
	 			
	 			//Se busca si se encuentra configurado en la base de datos
	 			ResultSet resultado = conMySQL.buscarRegistro(sentencia);
	 			
	 			if (resultado != null)
	 				
	 			  if (resultado.next())
	 			    
	 			     respuestaBoolean = true;
	 			
	 		} catch (Exception e) {
	 			System.out.println("Error"+e);
	 		}
	 		
	 		
	 		return respuestaBoolean;
 	}
 	
 	
 	/**
 	 * Inicia la clase para validar fecha
 	 */
 	
 	final public void iniciarValidarFecha(ConectarMySQL conMySQL) {
 		
 		 valFecha  = new ValidarFecha(ObtenerFechaServidor.getObtenerFechaCompleta(conMySQL));
 		
 	}
 	
 	
 	/**
 	 * Devuelve el numero de filas con datos en un JTable a partir de una columna pivote
 	 * @param jTable Tabla
     * @param columna columna pivote
     * @return numero de filas con datos
     */
     
 	final public static int obtenerFilas(JTable jTable, int columna) {
		
		
		int fila = 0;
		
		
		while (fila < jTable.getRowCount()  && jTable.getValueAt(fila,columna)!=null &&  String.valueOf(jTable.getValueAt(fila,columna)).length()>0)
		  
		  fila++;
 	

       return fila;
        
 		
	}
	
	/**
 	 * Devuelve si el componente es nulo o no
 	 * @param jComponente Componente a verificar si o no nulo
     * @return true si es nulo, false diferente a nulo
     */
    
    
	final public boolean esNulo(JComponent jComponente) {
		
		boolean resultado = false;
		
		if (jComponente == null)
		   
		    resultado = true;
		    
		return  resultado;   
		
	}
	
	
	/**
 	 * Devuelve si un JTextField esta vacio o no
 	 * @param jTextField JTextField a verificar si o no esta vacio
     * @return true si esta vacio, false diferente a vacio
     */
    
	final public boolean estaVacio(JTextField jTextField) {
		
		boolean resultado = false;
		
		if (jTextField.getText().equals(""))
		   
		    resultado = true;
		 
		    
		return  resultado;   
		
	}
	
	
	
  	 
 }