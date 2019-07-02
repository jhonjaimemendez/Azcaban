/**
 * Clase: Calendario
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

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JDialog;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.BorderLayout;

import javax.swing.border.EtchedBorder;

import javax.swing.JFormattedTextField;

import java.text.SimpleDateFormat;

/**
 * Clase que configura un calendario gregoniano para mostrar un InternalFrame con el calendario
 */

final public class Calendario extends JDialog  implements ActionListener {
	
	private JCalendar jCalendar;
	private JTitlePanel componentTitlePanel;
	private JPanel componentPanel;
	private JButton jbCerrar;
	
	
	public Calendario(JFrame frame, int posicionX, int posicionY) {
       
       this(null,frame,posicionX,posicionY);
      
     } 
	
	
	public Calendario(JFormattedTextField jFormattedTextField,JFrame frame, int posicionX, int posicionY) {
       
      super(frame,true);
      setLayout(null);
      setBounds(posicionX,posicionY,245,240);
      getRootPane().setBorder(new EtchedBorder());
      getRootPane().setWindowDecorationStyle(JRootPane.NONE);
       
      jbCerrar = new JButton("Cerrar");
      jbCerrar.setMnemonic('C');
      jbCerrar.addActionListener(this);
      jbCerrar.setBounds(90,210,80,20);
      add(jbCerrar); 
       
       
      ImageIcon icon = new ImageIcon("/Imagenes/JDayChooserColor.gif");
       
      componentPanel = new JPanel();
      
       	
   	  componentTitlePanel = new JTitlePanel("Calendario", icon, componentPanel, BorderFactory
			                                 .createEmptyBorder(4, 4, 0, 4));
	  componentTitlePanel.setSize(240,200);
	   
      
      jCalendar = new JCalendar(jFormattedTextField,jbCerrar);
      componentTitlePanel.add(jCalendar);
      add(componentTitlePanel);
       
     
       
	  setVisible(true);
	}
	
	public JCalendar getJCalendar() {
		
		return jCalendar;
		
	}
	
	public void mostrarCalendario(boolean visible) {
		
		setVisible(visible);
		
	}
	
	
	public void actionPerformed(ActionEvent act) {
		
		if (act.getSource() == jbCerrar) {
			
			mostrarCalendario(false);
			jCalendar.jTextFormattedField.setText(new SimpleDateFormat("yyyy/MM/dd").format(jCalendar.getDate()));
	   }				
		
	}
	
	
	
}	
  