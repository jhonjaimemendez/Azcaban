
/**
 * Clase: ValidarEntradaNumeroJTextField
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


/**
  * Esta clase valida la entrada de datos de tipo numerico en un JTextField que se agrega
  * como un evento de tipo addKeyListener
  */
 
 final public class ValidarEntradaNumeroJTextField extends KeyAdapter {

    public void  keyTyped (KeyEvent k) {
	
	  JTextField jtextField = (JTextField) k.getComponent();
	  
	  if ((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) || 
	     (k.getComponent().getName()!= null && jtextField.getText().length() >= Integer.parseInt(k.getComponent().getName())))
	           // Se consume o borra el caracter digitado
	           k.consume();
	  }	
 }	
 
 