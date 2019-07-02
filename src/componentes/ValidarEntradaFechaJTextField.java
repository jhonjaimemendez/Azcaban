/**
 * Clase: ValidarEntradaFechaJTextField
 * 
 * @version  1.0
 * 
 * @since 118-10-2005
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
 * Esta clase valida la entrada de datos de tipo fecha en el formato aaaa-mm-dd, es decir,
 * solo valida que la fecha digitada se encuentre en el formato anterior, mas no si los
 * valores de la fecha son correctos
 */
 
 final public class ValidarEntradaFechaJTextField extends KeyAdapter {

        public void  keyTyped (KeyEvent k) {
 	        
 	        JTextField jtextfield = (JTextField)k.getComponent();
        
         
            if (((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE)  && (!Character.isDigit(k.getKeyChar())) && (k.getKeyChar()!='-')) || 
                  (jtextfield.getText().length()>=10 && (!jtextfield.getText().equals("aaaa-mm-dd")) && !jtextfield.getText().equals(jtextfield.getName()))) {
                
                // Se consume o borra el caracter digitado
                k.consume();
        
        } else
                 if ((k.getKeyChar()=='-') && (jtextfield.getText().length()!=4) && (jtextfield.getText().length()!=7) )
                 	
                    k.consume();
  	     }   
  }