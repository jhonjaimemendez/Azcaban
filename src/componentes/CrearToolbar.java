/**
 * Clase: CrearToolbar
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

import java.awt.Dimension;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToolBar;


/**
 * Esta clase permite configurar una JToolBar (Barra De Herramientas) con sus Botones
 */
  
  public class CrearToolbar extends OperacionesBasesDeDatos {
  	
  	public JToolBar toolBar;
  	public JButton Blimpiar;
  	public JButton Bguardar;
  	public JButton Beliminar;
  	public JButton Bbuscar;
  	public JButton Bimprimir;
  	public JButton Bsalir;
  	
  	//Atributos estaticos
  	private static JLabel lTituloJToolBar;
  	private final static  Dimension DIMENSION =  new Dimension(150,0);
  	private final static  Dimension DIMENSION1 =new Dimension(140,0);
  	private final static  Dimension DIMENSION2 =  new Dimension(6,0);
	  	
  	
    
    /**
     * Constructor vacio
     */
  	public CrearToolbar() {
  	}
  	
	/**
	 * Constructor por defecto que permite configurar la JToolBar, se especifica un parametro
	 * como comodin solo para que no se ejecute automaticamente
	 *
	 * @param comodin Comodin utilizada para que no se ejecute por defecto
	 */
	public CrearToolbar(String comodin) {
  		

  		// Se instancia y configura la toolbar
  		toolBar = new JToolBar();
  		toolBar.setFloatable(false);
  		toolBar.setBorder(new javax.swing.border.EtchedBorder());
  		toolBar.setBorderPainted(true);
  		toolBar.setBounds(1,0,789,40);
  		
  	   // Se define un objeto de tipo dimension para posicionar el JLabel
  		toolBar.addSeparator(DIMENSION);
  	 	
  	 	
  	 	 //Se instancia el titulo si no ha sido creado
  	 	if (lTituloJToolBar == null)	
  	 	
  	 	  lTituloJToolBar = new JLabel(new ImageIcon(getClass().getResource("/Imagenes/MOTORBIKE.gif")));
  	 	  
  	 	  
  	 	//Se adiona el titulo al JToolbar   
	  	toolBar.add(lTituloJToolBar);
 	  		
  	
  		// Se configura la dimension
        toolBar.addSeparator(DIMENSION1);
        
        
       
  		// Se configuran los botones
  	    Blimpiar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Limpiar.gif")));
  	    Blimpiar.setToolTipText("Limpiar Alt + L");
  	    Blimpiar.setMnemonic('L');
  	    Blimpiar.setBorder(getBordeBotonesJToolbar());
  	    toolBar.add(Blimpiar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bguardar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Guardar.gif")));
  		Bguardar.setToolTipText("Guardar Alt + G");
  		Bguardar.setMnemonic('G');
  		Bguardar.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bguardar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Beliminar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Eliminar.gif")));
  		Beliminar.setToolTipText("Eliminar Alt + E");
  		Beliminar.setMnemonic('E');
  		Beliminar.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Beliminar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bbuscar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Buscar.gif")));
  		Bbuscar.setToolTipText("Buscar Alt + B");
  		Bbuscar.setMnemonic('B');
  	    Bbuscar.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bbuscar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bimprimir = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Imprimir.gif")));
  		Bimprimir.setToolTipText("Imprimir Alt + I");
  		Bimprimir.setMnemonic('I');
  		Bimprimir.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bimprimir);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bsalir = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Salir.gif")));
  		Bsalir.setToolTipText("Salir Alt + S");
  		Bsalir.setMnemonic('S');
  	    Bsalir.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bsalir);
  		
		  	
  		
  	}
  	
  		/**
	 * Constructor por defecto que permite configurar la JToolBar, se especifica un parametro
	 * como comodin solo para que no se ejecute automaticamente
	 *
	 * @param comodin Comodin utilizada para que no se ejecute por defecto
	 */
	public CrearToolbar(String comodin,String comodin1) {
  	
  		// Se instancia y configura la toolbar
  		toolBar = new JToolBar();
  		toolBar.setFloatable(false);
  		toolBar.setBorder(new javax.swing.border.EtchedBorder());
  		toolBar.setBorderPainted(true);
  		toolBar.setBounds(1,0,789,40);
  		
  	   // Se define un objeto de tipo dimension para posicionar el JLabel
  		toolBar.addSeparator(DIMENSION);
  	 	
  	 	
  	 	 //Se instancia el titulo si no ha sido creado
  	 		
  	 	 lTituloJToolBar = new JLabel(new ImageIcon(getClass().getResource("/Imagenes/MOTORBIKE.gif")));
  	 	  
  	 	  
  	 	//Se adiona el titulo al JToolbar   
	  	toolBar.add(lTituloJToolBar);
 	  		
  	
  		// Se configura la dimension
        toolBar.addSeparator(DIMENSION1);
        
        
       
  		// Se configuran los botones
  	    Blimpiar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Limpiar.gif")));
  	    Blimpiar.setToolTipText("Limpiar Alt + L");
  	    Blimpiar.setMnemonic('L');
  	    Blimpiar.setBorder(getBordeBotonesJToolbar());
  	    toolBar.add(Blimpiar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bguardar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Guardar.gif")));
  		Bguardar.setToolTipText("Guardar Alt + G");
  		Bguardar.setMnemonic('G');
  		Bguardar.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bguardar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Beliminar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Eliminar.gif")));
  		Beliminar.setToolTipText("Eliminar Alt + E");
  		Beliminar.setMnemonic('E');
  		Beliminar.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Beliminar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bbuscar = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Buscar.gif")));
  		Bbuscar.setToolTipText("Buscar Alt + B");
  		Bbuscar.setMnemonic('B');
  	    Bbuscar.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bbuscar);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bimprimir = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Imprimir.gif")));
  		Bimprimir.setToolTipText("Imprimir Alt + I");
  		Bimprimir.setMnemonic('I');
  		Bimprimir.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bimprimir);
  		toolBar.addSeparator(DIMENSION2);
  		
  		Bsalir = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Salir.gif")));
  		Bsalir.setToolTipText("Salir Alt + S");
  		Bsalir.setMnemonic('S');
  	    Bsalir.setBorder(getBordeBotonesJToolbar());
  		toolBar.add(Bsalir);
  		
		  	
  		
  	}
  }