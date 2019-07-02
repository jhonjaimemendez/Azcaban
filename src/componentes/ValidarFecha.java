/**
 * Clase: ValidarFecha
 * 
 * @version  1.0
 * 
 * @since 18-10-2005
 * 
 * @autor Ing.  Jhon J. Mendez
 *
 * Copyrigth: JASoft
 */
 

 package com.JASoft.componentes;

import java.util.Calendar;
import java.util.Date;


/**
 * Esta clase permite validar Fechas en el formato yyyy-mm-dd, ademas de tener funciones que permitan devolver
 * el numero de dias de un mes, comparar fechas, etc.
 */
 
final public class ValidarFecha {
 	
 	// Declaración de variables
 	private int añoActual;
 	
 	private String fechaActual;
 	
 	private boolean sw = true;
 	
 	
 	
 	/**
 	 * Constructor general que inicia los valores de las variables
 	 *
 	 *@param fechaServidor fecha/hora del servidor
 	 */
 	
 	 public ValidarFecha(String  fechaServidor) {
 		
 		//Se obtiene el año y la fecha actual
 		añoActual = Integer.parseInt(fechaServidor.substring(0,4));
 		
 		
 		fechaActual = fechaServidor.substring(0,11);
 		
 		
 	}
 	
 	
 	//Otros metodos
 	
 
 	/**
 	 * Valida la fecha digitada en un JTextField
 	 *
 	 *@param fecha Fecha a validar
 	 *@param sw true: Valida que la fecha a validar sea menor a la actual del sistema
 	 *@return boolean true: es correcta false: es incorrecta 
 	 */
 
 	public boolean validarFecha(String fecha,boolean sw) {
 		 
 		 this.sw = sw;
 		 
 		 return validarFecha(fecha);
 	}	
 	
 	
 	/**
 	 * Valida la fecha digitada en un JTextField mutación del metodo de arriba
 	 *
 	 *@param fecha Fecha a validar
 	 *@return boolean true: es correcta false: es incorrecta 
 	 */
 	public boolean validarFecha(String fecha) {
 		
 		//Declaración de variables
 		
 		String str = "";
 		
 		boolean resultado = false;
 		
 		int añoValidar = 0;
 		
 		int mesValidar = 0;
 		
 		int dia = 0;
 		
 		
 		// Se remplazan los carcteres en blanco
 		for (int i=0; i<fecha.length(); i++) {
 		
 		  if (fecha.charAt(i) != ' ')
 		    
 		      str = str + fecha.charAt(i);
 		    
 		}
 		
 		
 	   
 	   try {
 		
	 		// Se verifica la cantidad de valores en la fecha y se valida las posiciones de los caracteres
	 		if (str.length() == 10) {
	 			
	 		 if (str.indexOf('-') == 4) {
	 		 	
	 		   if (str.indexOf('-',5) == 7) {
	 		
	 		    if (str.indexOf('-',8) <0) {	
	 		      
	 		      añoValidar = Integer.parseInt(str.substring(0,4));
	 		       
	 		      // Se valida el ao
	 		      if (añoValidar >1900 &&  añoValidar <= añoActual) {
	                  
	                  mesValidar = Integer.parseInt(str.substring(5,7));
	                  
	                  if (mesValidar>0 && mesValidar<=12) {
	                  
	                    dia = Integer.parseInt(str.substring(8,10));  
	                   
	                     if (dia <= getDias(mesValidar,añoValidar)) {
		                   
		                   	 if (sw)
		                   	 
		                   	   resultado  = compararFechas(fecha,fechaActual);
		                   	 
		                   	 else {
		                   	 	
		                   	 	resultado = true;
		                   	 	
		                   	 	sw = true;
		                   	 }  
	                    } 
	                  } 
	               
	                } 
	               
	               } 
	 		      
	 		      } 
	 		     
	 		    } 
	 		    
	 		   }
	 		  
	 	   
 	   }catch (Exception e) {
 	       
 	       	resultado = false;
 	   }
 		  
 		  return resultado; 		   
 	}
 	
 	
 	
 	/**
 	 * Devuelve el numero de dias del numero de mes que recibe como parametro
 	 *
 	 *@param numeroMes  Numero del mes a validar
 	 *@param añoValidar Año a validar
 	 *@return int Numero de dias del mes 
 	 */
 	
 	public static int getDias(int numeroMes, int añoValidar) {
 	   
 	    int resultadoNumeroDias = 0;
 	   
 	    switch (numeroMes) {
 	    	
 	    	case 1: resultadoNumeroDias = 31; break;
 	    	
 	    	case 2:
 	    	 
 	    	 if (((añoValidar % 4 == 0)  && (añoValidar % 100 != 0)) || (añoValidar % 400 == 0))
 	    	   
 	    	   resultadoNumeroDias = 29;
 	    	 
 	    	 else
 	    	   resultadoNumeroDias = 28; 
 	    	   
 	    	  
 	    	break;   	
 	    	
 	    	
 	    	case 3: resultadoNumeroDias = 31; break;
 	    	case 4: resultadoNumeroDias = 30;break;
 	    	case 5: resultadoNumeroDias = 31; break;
 	    	case 6: resultadoNumeroDias = 30;break;
 	    	case 7: resultadoNumeroDias = 31; break;
 	    	case 8: resultadoNumeroDias = 31; break;
 	    	case 9: resultadoNumeroDias = 30;break;
 	    	case 10:resultadoNumeroDias = 31; break;
 	    	case 11:resultadoNumeroDias = 30;break;
 	    	case 12:resultadoNumeroDias = 31; break;
 	    	  
 	    	
 	    
 	    	
 	    	
 	    	
 	    	  
 	    	
 	     }	 
 	    	   
 	    return resultadoNumeroDias;	  
 	      
 	}
 	
 	
 	
 	/**
 	 * Compara dos fecha para comprabar si la primera es menor o mayor
 	 *
 	 *@param fechaInicial  Fecha Inicial
 	 *@param fechaFinal fechaFinal
 	 *
 	 *@return booelan true: es menor la fechaInicial a la fechaFinal false :  es mayor la fechaInicial a la fechaFinal
 	 */
 	
    public static boolean compararFechas(String fechaInicial, String fechaFinal) {
  		
  		boolean resultado = false;
  		
  		Calendar calendar = Calendar.getInstance();
  		
  		// Se convierten las fecha inicial en String a tipo Date
  		calendar.set(Integer.parseInt(fechaInicial.substring(0,4)),Integer.parseInt(fechaInicial.substring(5,7))-1,Integer.parseInt(fechaInicial.substring(8,10)));
  		Date fecInicial = calendar.getTime();
  		
  		// Se convierten las fecha final en String a tipo Date
  		calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)));
   		Date fecFinal = calendar.getTime();
   		
   		
   		//Se comparan las fechas
   		if (fecFinal.compareTo(fecInicial) >= 0) 
  		    resultado =  true;
  		
  		
  		return resultado;
  		
  		
  	}
		 
  
 }