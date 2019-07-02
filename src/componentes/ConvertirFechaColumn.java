/**
 * Clase: ConvetirFechaColumn
 * 
 * @version  1.0
 * 
 * @since 26-12-2005
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



/**
 * Esta clase permite validar que solo se especifique el formato de fecha aaaa-mm-dd
 * en las columnas de un JTable cuando se permite digitar en la columnas valores
 */


final public class ConvertirFechaColumn extends JTextField {

   
    public String getValue() {
        return getText();
    }

    protected Document createDefaultModel() {
        return new SoloFecColumn();
    }

    
    static final protected class SoloFecColumn extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a) 
            throws BadLocationException {
           
           if (str.length()>0) {
           
              char[] source = str.toCharArray();
              char[] result = new char[source.length];
              int j = 0;

	            for (int i = 0; i < result.length; i++) 
	                if (Character.isDigit(source[i]) || source[i] == '-')
	                    result[j++] = source[i];
	            
	            // Se inserta el string en el JTextField
	            super.insertString(offs, new String(result, 0, j), a);
	        }
	   }     
    }

}

