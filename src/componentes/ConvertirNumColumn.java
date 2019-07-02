/**
 * Clase: ConvertirNumColumn
 * 
 * @version  1.0
 * 
 * @since 07-11-2005
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.componentes;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.awt.event.*;

import java.awt.Color;


/**
 * Esta clase permite validar que solo se digiten numeros en un JTable,
 * cuando se permite escribir en una columna
 */

final public class ConvertirNumColumn extends JTextField {

    ConvertirNumColumn(Color color) {
    	
    	setBackground(color);
    	selectAll();
    	
    	
    addFocusListener(new FocusAdapter() {
      	
      	public void  focusGained (FocusEvent k) {
   	    
   	      System.out.println("122");
   	       
   	    }   
      	
      });
    }
    
    public String getValue() {
        return getText();
    }

    protected Document createDefaultModel() {
        return new SoloNumColumn();
    }

    
   static final protected class SoloNumColumn extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a) 
            throws BadLocationException {
           
           if (str.length()>0) {
           
              char[] source = str.toCharArray();
              char[] result = new char[source.length];
              int j = 0;

	            for (int i = 0; i < result.length; i++) 
	                if (Character.isDigit(source[i]))
	                    result[j++] = source[i];
	            
	            // Se inserta el string en el JTextField
	            super.insertString(offs, new String(result, 0, j), a);
	        }
	   }     
    }

}

