/**
 * Clase: AcercaDe
 *
 * @version  1.0
 *
 * @since 19-10-2005
 *
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */


import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


 /**
  * Esta clase permite configurar el Jframe  para el AcercaDe
  */
 
 final public class AcercaDe extends JInternalFrame {
 	
 	 //Referencia al MenuPrincipal
    private MenuPrincipal menu;
   
 
 	public AcercaDe(MenuPrincipal p_menu) {
  	
  	  super("Acerca De", 
	      true, //resizable
	      false, //closable
	      false, //maximizable
	      false);//iconifiable
       
      menu = p_menu;
    	 
       
       // se configura el JInternalFrame    
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
	  this.setLayout(null);
 	 
  	  // Se especifica el tamaño y posicion
  	  setBounds((p_menu.getWidth() - 440)/2,(p_menu.getHeight() - 300)/2,440,300);
    
	  // se crea un border
  	  Border etched = BorderFactory.createEtchedBorder();
  	  
  	  // se crea un panel para obtener un borde
  	  JPanel panel = new JPanel();
  	  panel.setBounds(10,50,420,150);
  	  panel.setBorder(etched);
  	  
  	  
  	  
  	  JLabel LEtiq = new JLabel("<html><center>" + "<br>Copyright ©  2005-2008 Ing. Jhon Jaime Méndez "+
  	                            "<br> <br> Este programa esta protegido contra copia bajo las leyes <br> colombianas <br><br> Todos los Derechos reservados"+"</center></html>");
  	  LEtiq.setBounds(10,10,300,60);
  	  panel.add(LEtiq);
  	  
  	  JLabel LVersion = new JLabel("Versión 1.0");
  	  LVersion.setBounds(360,210,100,20);
  	  this.add(LVersion);
  	  
  	  
  	  //Se adiciona un boton
  	  JButton BAceptar = new JButton("Aceptar");
  	  BAceptar.setBounds(160,240,100,20);
  	  BAceptar.setMnemonic('A');
  	  BAceptar.addActionListener( new ActionListener() {
  	  	
  	  	public void actionPerformed(ActionEvent a) {
  	  		
  	  		menu.habilitarMenu(true);
  	  		dispose();
  	  		
  	  	}
  	  });
  	  this.add(BAceptar);
  	  this.add(panel);
  	  
  	  
  	  // Se visualiza el JFrame
  	  this.setVisible(true);
  	  
  	  
  	}
  	
  	
  	
  }	