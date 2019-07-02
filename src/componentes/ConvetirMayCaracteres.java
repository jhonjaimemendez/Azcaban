/*
 * Clase: ConvetirMayCaracteres
 * 
 * @version  1.0
 * 
 * @since 18-10-2005
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

 
package com.JASoft.componentes;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;


/*
 * Esta clase permite configurar un evento para validar que solo se digiten mayusculas
 */

final public class ConvetirMayCaracteres extends KeyAdapter {
   
   private JTextField jtextField;

   public void  keyTyped (KeyEvent k) {
   	   
   	   jtextField = (JTextField) k.getComponent();
   	 
   	   if (k.getComponent().getName()!= null && jtextField.getText().length() >= Integer.parseInt(k.getComponent().getName()) || k.getKeyChar() == '\'')
	  
	     k.consume();
	  
	   else  
	     k.setKeyChar(String.valueOf(k.getKeyChar()).toUpperCase().charAt(0));
   }
}