/**
 * Clase: CrearJFrameParaMenu
 * 
 * @version  1.0
 * 
 * @since 24-04-2006
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
package com.JASoft.componentes;
 
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;

/**
 * Clase que permite configurar el JFrame para un Menu
 */


public class CrearJFrameParaMenu extends AtributoVisual {
 
  private JFrame frame;
 
    /**
     * Constructor
     * @param titulo Titulo del menu
     */
     
    public  CrearJFrameParaMenu(String titulo) {
 	
 			// Se decora el marco
	  	  JFrame.setDefaultLookAndFeelDecorated(true);
	  	  
	  	  init(titulo);
	  	  
	}  	  
	
	public  CrearJFrameParaMenu(String titulo,boolean decorarMarco) {
 	
 		
 		if (!decorarMarco)
 		
 		   JFrame.setDefaultLookAndFeelDecorated(true); // Se decora el marco
	  	  
	  	  
	  	  init(titulo);
	  	  
	}  	  
	
	public void init(String titulo) {
		
		// Se instancia y configura el marco
	  	  frame = new JFrame(titulo);
	  	  frame.setSize(800,570);
	  	  frame.setLocationRelativeTo(null);
	  	  frame.setResizable(false);
	  	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	  
	  	  UIManager.put("TextField.font",  new Font("Arial",Font.BOLD,12));
  	      UIManager.put("TextField.foreground",new Color(51,51,51));
  	
	}
	
	/**
	 * Muestra una casilla de mensajes con el un titulo, mensaje y tipo de mensaje
	 *
	 * @param mensaje Mensaje a mostrar
	 * @param titulo  Titulo del mensaje
	 * @param tipo  Tipo de mensaje: 0 error, 1 Informacion o confirmacion, 2 Precaucion
	 *
	 */
 	
	final public void Mensaje(String mensaje,String titulo, int tipo) {
    	JOptionPane.showMessageDialog(null,mensaje,titulo,tipo);
    }

	
	/**
	 * Permite actualizar el tema para las diferentes interfaces graficas
	 * @param tema Tema a cambiar
	 */
	 
	  	  
    final public void actualizarTema(MetalTheme tema) {
   	 	
   	 	try {
   	 		    
   	 		if (tema.getName().equals("Steel"))
   	 		      frame.getContentPane().setBackground(new Color(153,153,204));
   	 		    
        		MetalLookAndFeel.setCurrentTheme(tema);
        		UIManager.setLookAndFeel(new MetalLookAndFeel());
                SwingUtilities.updateComponentTreeUI(frame);
                
                
            } catch (Exception e)  {}
  	
    }
    
    public void iniciarConfiguracionesInicialesPorDefecto() {
  	   	
  	   
       		  AtributoVisual.configuracionesInicialesPorDefecto(); //Se adicionan las cnfiguraciones iniciales
     
    }
     
     
    /**
	 * Devuelve el objeto JFrame
	 * @return retorna el JFrame
	 */

    public JFrame getJFrame() {
    	
    	return frame;
    }
    
    
   
    
 
}    


