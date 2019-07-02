/**
 * Clase: EditorJTextFieldTabla
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
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import java.awt.Component;



final public class EditorJTable extends DefaultCellEditor {
   
     JTextField jTextField;
     
    public EditorJTable(JTextField jTextField) {

	     super(jTextField);
	     
	     setClickCountToStart(1); // change # of mouse clicks to begin editing
	    

    }
   
 

    
}    


