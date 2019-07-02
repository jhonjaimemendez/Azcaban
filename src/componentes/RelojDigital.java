/*
 * Clase: RelojDigital
 * 
 * Version : 1.0
 * 
 * Fecha: 30-01-2006
 * 
 * Copyright: Ing. Jhon Mendez
 */
 
package com.JASoft.componentes;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;



/**
 * Esta clase permite visualizar un reloj digital en el menu principal
 */


final public class RelojDigital extends JLabel implements ActionListener {
	
  private SimpleDateFormat formato = new SimpleDateFormat("yyyy - MMMM - dd   hh:mm a ");
 
  public RelojDigital() {
    	
    	setFont(new Font("Arial",Font.ITALIC + Font.BOLD,12));
    	setText(formato.format(new Date()));
    	setBounds(540 ,493,210,20);
    	setHorizontalAlignment(JLabel.RIGHT);
    	
    	
    	Timer t = new Timer(1000, this);
        t.start();
  }




  public void actionPerformed(ActionEvent ae) {

       setText(formato.format(new Date()));

  }

}  
   