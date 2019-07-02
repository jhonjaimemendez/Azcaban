/**
 * Clase: Cursores
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

import java.awt.Cursor;

/**
 * Esta clase configura los iconos para los accesos directos
 */

public class Cursores {
	
	
	  /**
       * Cursos para los accesos directos selecionados
       */
       private  static Cursor cursorMano;
 	  
 	  /**
       * Cursos para los accesos directos que no se selecionan
       */
       private  static Cursor cursorDefecto;
      
      
  	   
  	   /**
	  	 * Obtiene el cursor que se muestra cuando se seleccionan los accesos directos
	  	 * 
	  	 *
	  	 * @return Cursor
	  	 */
  	   final public Cursor getCursorMano() {
  	   	
  	   	   if (cursorMano == null)
  	      	
  	      	 cursorMano = new Cursor(Cursor.HAND_CURSOR);
 	  
  	      
  	      return cursorMano;	
  	   	
  	   }
  	   
  	   
  	   /**
	  	 * Obtiene el cursor que se muestra cuando se desseleccionan los accesos directos
	  	 * 
	  	 *
	  	 * @return Cursor
	  	 */
  	   final public Cursor getCursorDefecto() {
  	      
  	      if (cursorMano == null)
  	      
  	        cursorDefecto = new Cursor(Cursor.DEFAULT_CURSOR); 
      
  	      return cursorDefecto;	
  	   	
  	   }
 } 	   