/**
 * Clase: ConvertirMayColumn
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

import java.awt.Color;



/**
 * Esta clase permite validar que solo se digiten mayusculas en un JTable cuando se permite escribir
 * en las columnas
 */

final public class ConvertirMayColumn extends JTextField {

    ConvertirMayColumn(Color color) {
    	
    	setBackground(color);
    }
   
    public String getValue() {
        return getText();
    }

    protected Document createDefaultModel() {
        return new SoloMayColumn();
    }

    
    static final
    
    class SoloMayColumn extends PlainDocument {

        public void insertString(int offs, String str, AttributeSet a) 
            throws BadLocationException {
            
            // Se convierte la cadena a mayuscula
            str = str.toUpperCase();
            
            // Se inserta el string en el JTextField
            super.insertString(offs, str, a);
        }
    }

}

