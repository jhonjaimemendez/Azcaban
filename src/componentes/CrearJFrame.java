/*
 * Clase: CrearJFrame
 * 
 * Version : 1.0
 * 
 * Fecha: 18-10-2005
 * 
 * Copyright: Ing.  Jhon Mendez
 */
 
 package com.JASoft.componentes;

import java.awt.AWTKeyStroke;
import java.awt.Font;

import java.awt.KeyboardFocusManager;
import java.awt.Color;
import java.awt.Image;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.text.NumberFormat;
import java.text.DecimalFormat;


import java.sql.ResultSet;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.SizeRequirements;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

 /**
  * Esta clase permite definir parametros  comunes  para  la  creacion de  un  JFrame
  * a partir de ella, asi como metodos para la configuracion de componentes graficos entre otros
  */
  
  public class CrearJFrame extends CrearToolbar implements FocusListener{
  	
  	
  	private JFrame frame;
    private JFrame framePadre; 
    
    private static Set<AWTKeyStroke> upKeys;
    private static Set<AWTKeyStroke> upKeys1;
   
    
    private static SizeRequirements size;
   
   
    private static String dptoDivisionPolitica;
    private static String codigoDptoDivisionPolitica;
    private static String municipioDivisionPolitica;
    private static String codigoDivisionPolitica;
    
    private String sentenciaSQLListaValores;
    
    private JDialog dialogoListaValores; // Dialogo para mostrar la lista valores
    
    private JDialog dialogoArrayListaValores[]; // Array Dialogo para mostrar varias listas valores en una forma 
     
    
    private JTable tablaListaValores;  //Tabla para listar los valores
    private JTable tablaArrayListaValores[];  //Tabla para listar los valores para varias listas
    
    
  	public  static String departamentos[][];  // Almacena los codigos de departamento, 
                                              // los nombres, los codigos de las capitales
                                             //  y los nombres
  	
  	private int filaSeleccionada; //Comodin
  	
  	 
   //** NumberFormat
   private NumberFormat formatter;
   
   //Modelo de tabla
   private EditableCellTableModel modelo = new EditableCellTableModel();
   
   
  	
    /**
     * Constructor vacio
     */
    public CrearJFrame(){}
  	
  	
  	/**
     * Constructor que crea un jFrame a partir del titulo, sin colocarle una Toolbar
     * @param titulo del JFrame
     */
  
  	public CrearJFrame (String titulo) {
  	 
  	   // Se configura el marco
  	   configurarJFrame(titulo);
  	   configurarCloseOperation();
  	}
  	
  	 
  	 /**
  	  * Constructor utilizado para instanciar la clase que permite validar fecha
  	  * @param conMySQL Conexion a la BD
  	  */
  	 
  	 public CrearJFrame(ConectarMySQL conMySQL) {
  	 	 
  	 	 //Se inicia la clase validadora
  	     iniciarValidarFecha(conMySQL);
  	
  	 }
  	
  	
	 /**
	  * Constructor utilizado para instanciar un JFrame con una ToolBar
	  * @param titulo del JFrame
	  * @param toolBar Parametro comodin, se puede enviar cualquier cosa pero por estandar enviar ToolBar
      */
	
  	  public CrearJFrame (String titulo, String toolBar) {
  	       
  	       super("Comodin");
  	       
  	       
	  	   // Se configura el marco
	  	   configurarJFrame(titulo);
	  	   configurarJToolBar();
	  	   configurarCloseOperation(toolBar);
	  	 
  	  }
  	
  	
  	/**
	  * Constructor utilizado para instanciar un JFrame con una ToolBar y un evento de cierre de ventana que devuelve a la GUI padre
	  * @param titulo del JFrame
	  * @param toolBar Parametro comodin, se puede enviar cualquier cosa pero por estandar enviar ToolBar
	  * @param pframePadre JFrame padre
	  *
      */
	
  	public CrearJFrame (String titulo, String toolBar,JFrame pframePadre) {
  	       
	  	  //Se llama al contructor de arriba
	  	  this(titulo,toolBar);
	  	
	  	  this.framePadre  =  pframePadre;
	  	  
	  	   // Se configura el marco
	  	   configurarJFrame(titulo);
	  	   configurarJToolBar();
	  	   configurarCloseOperation(toolBar);
	  	   
	  	  
	  	   //Se agrega un evento de ventana  al frame para que muestre a su padre
	  	   frame.addWindowListener(new WindowAdapter() {
	  	      
	  	      public void windowClosing(WindowEvent w) {
	  	
	  	      	   frame.dispose();
	  	     	   framePadre.setVisible(true);
		           
	         }
	  	   });
	  	   
  	}
  	
  	
  		public CrearJFrame (String titulo, String toolBar,JFrame pframePadre,String comodin) {
  	       
	  	  //Se llama al contructor de arriba
	      super("Comodin",comodin);
  	       
  	       
	  	   // Se configura el marco
	  	   configurarJFrame(titulo);
	  	   configurarJToolBar();
	  	   configurarCloseOperation(toolBar);
	  	
	  	  this.framePadre  =  pframePadre;
	  	  
	  	   // Se configura el marco
	  	   configurarJFrame(titulo);
	  	   configurarJToolBar();
	  	   configurarCloseOperation(toolBar);
	  	   
	  	  
	  	   //Se agrega un evento de ventana  al frame para que muestre a su padre
	  	   frame.addWindowListener(new WindowAdapter() {
	  	      
	  	      public void windowClosing(WindowEvent w) {
	  	
	  	      	   frame.dispose();
	  	     	   framePadre.setVisible(true);
		           
	         }
	  	   });
	  	   
  	}
  	
  	
  	/**
	  * Constructor utilizado para instanciar un JFrame con una ToolBar e instancia la clase que valida la fecha
	  * @param titulo del JFrame
	  * @param toolBar Parametro comodin, se puede enviar cualquier cosa pero por estandar enviar ToolBar
	  * @param conMySQL Conexion a la BD
  	  *
      */
	
 	public CrearJFrame (String titulo, String toolBar,ConectarMySQL conMySQL) {
  	    
  	    this(titulo,toolBar);
  	  
  	    //Se inicia la clase validadora
  	    iniciarValidarFecha(conMySQL);
  	  
  	 
  	}
    
    /**
	  * Constructor utilizado para instanciar un JFrame con una ToolBar y un evento de cierre de ventana que devuelve a la GUI padre, ademas
	  * instancia la clase que valida la fecha
	  * @param titulo del JFrame
	  * @param toolBar Parametro comodin, se puede enviar cualquier cosa pero por estandar enviar ToolBar
	  * @param conMySQL Conexion a la BD
  	  * @param framePadre JFrame padre
	 */
	
  	public CrearJFrame (String titulo, String toolBar,JFrame framePadre,ConectarMySQL conMySQL) {
  	    
  	    this(titulo,toolBar,framePadre);
  	  
  	   //Se inicia la clase validadora
  	    iniciarValidarFecha(conMySQL);
  	 
  	}
 	
    
    /**
	  * Configura parametros generales para un JFrame
	  * @param titulo del JFrame
	  */
	
  	final private void configurarJFrame(String titulo) {
  	  
	  	  
	  	   // Se instancia y configura el JFrame
	  	  frame = new JFrame(titulo);
	  	  frame.setSize(800,570);
	  	  frame.setLocationRelativeTo(null);
	  	  frame.setResizable(false);
	  	  frame.getContentPane().setLayout(null);
	  	  
	  	  frame.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeysFrame());
	  	  
	  	  // Se configura la presentacion de los botones de la Toolbar
	  	  Blimpiar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/LimpiarS.gif")));
	  	  Bguardar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/GuardarS.gif")));
	  	  Beliminar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/EliminarS.gif")));
	  	  Bbuscar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/BuscarS.gif")));
	  	  Bimprimir.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/ImprimirS.gif")));
	  	  Bsalir.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/SalirS.gif"))); 
	  	  
  	  
  	}
  	
    
    /**
	  * Configura el JFrame para que baje de memoria el JFrame
	  */
	
  	final private void configurarCloseOperation() {
  	
  	   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	}
  	
    
    /**
	  * Configura el JFrame para que oculte el JFrame
	  * @param cadena parametro comodin
	  */
	
  	final private void configurarCloseOperation(String cadena) {
  
  	   frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  
  	}
  	
 
    /**
	  * Agrega la Toolbar al JFrame
	  */
	
  	final private void configurarJToolBar() {
  
  		 frame.getContentPane().add(toolBar);
  
  	}
  	
  	
  	/**
	  *  Valida un array de JTextField obligatorios contienen datos
	  *  @param campos array con los campos que se deben validar como obligatorios
	  *  @return true Campos obligatorios todos completos, false falta por lo menos uno sin datos
	  */
	
  	public boolean validarRegistro(JTextField campos[]) {
 	
 	    boolean resultado = false;
 	       
 		int opcionEsc = getValidarJTextFieldObligatorios(campos);
 		
 		// Se valida los campos requeridos
 	    if (opcionEsc >= campos.length) {
 	     
 	     	resultado = true;
 	    
 	    } else {
	    
	       Mensaje(campos[opcionEsc].getToolTipText(),"Reporte de Error ",JOptionPane.ERROR_MESSAGE);	
	       campos[opcionEsc].grabFocus();
	      	
	    } 
	    
	    return resultado; 
 	}
  	
  	
  		public boolean validarRegistro(JTextField campos[], int limite) {
 	
 	    boolean resultado = false;
 	       
 		int opcionEsc = getValidarJTextFieldObligatorios(campos,limite);
 		
 		// Se valida los campos requeridos
 	    if (opcionEsc >= campos.length) {
 	     
 	     	resultado = true;
 	    
 	    } else {
	    
	       Mensaje(campos[opcionEsc].getToolTipText(),"Reporte de Error ",JOptionPane.ERROR_MESSAGE);	
	       campos[opcionEsc].grabFocus();
	      	
	    } 
	    
	    return resultado; 
 	}
  	
  	/**
	  *  Metodo utilizado para crear varias listas de valores mostrada en un JTable
	  *  @param sentenciaSQL sentencia SQL de donde se sacaran los valores
	  *  @param conMySQL Conexion de BD
	  *  @param camposRetorno Array de los componentes a retornar valores
	  *  @param posicionX Posicion en X de la lista de valores
	  *  @param campoLista JTextField donde se muestra la lista de valores
	  *  @param indiceArrayDialogo Indice que referencia la posicion del JDialog en el array	  
	  *  @param tamañoArrayDialogo Tamaño del array de JDialog
	  *  
	  */
	
	 public void listarValores(String sentenciaSQL,ConectarMySQL conMySQL,final Object[][] camposRetorno,
	                           int posicionX,int posicionY, JTextField campoLista,final int indiceArrayDialogo,
	                           int tamañoArrayDialogo, int[] columnasOcultar) {
  		   
  		  
 		try {
 		
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 	
 		   if (resultado != null) {
 		   	
 		   	    ResultSetSerializable resulSet = new ResultSetSerializable(resultado);
 		   	    modelo.setDatos(resulSet.getDatosColumnas(),resulSet.getNombresColumnas());
 		
 		        if (dialogoArrayListaValores == null) {
 		
 		           dialogoArrayListaValores = new JDialog[tamañoArrayDialogo];
 		           tablaArrayListaValores  = new JTable[tamañoArrayDialogo];
 		
 		        }
 		        
 		        
 		        
	 		    if (dialogoArrayListaValores[indiceArrayDialogo] == null) {
	 		 
	 		 	           //Dialogo para especificar la lista de valores
				            dialogoArrayListaValores[indiceArrayDialogo] = new JDialog(getJFrame(),false);
				            dialogoArrayListaValores[indiceArrayDialogo].setLayout(null);
					  		dialogoArrayListaValores[indiceArrayDialogo].getRootPane().setWindowDecorationStyle(JRootPane.NONE);
					  		dialogoArrayListaValores[indiceArrayDialogo].setSize(380,177);
				      
				            //Se configura la tabla de lista de valores
			                tablaArrayListaValores[indiceArrayDialogo] = getJTable(modelo,true);
			            
			                 //Se agrega eventos a la tabla
					 		tablaArrayListaValores[indiceArrayDialogo].addMouseListener(new MouseAdapter() {
					 		   
						 		    public void mouseClicked(MouseEvent m){
						 		    
							 		     if (m.getClickCount() == 2) { //Se escoge la columna con doble Click
							 		       
							 		        JTextField jTextFieldRetorno = null;
							 		        JComponent jTextFieldSiguiente = null;
							 		        int posicionTablaElementoRetorno = 0;
							 		        
							 		      
							 		        for (int i = 0; i < camposRetorno.length ; i++) {
							 		        
							 		      	
							 		        	if ( i == 0) {
							 		          
									 		        	
							 		        	   jTextFieldRetorno = (JTextField) camposRetorno[i][0];
							 		        	   jTextFieldSiguiente = (JComponent) camposRetorno[i][1];
							 		        	   posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
							 		        	   jTextFieldRetorno.setFocusable(false);
							 		        	   jTextFieldRetorno.setText(tablaArrayListaValores[indiceArrayDialogo].getValueAt(tablaArrayListaValores[indiceArrayDialogo].getSelectedRow(),posicionTablaElementoRetorno).toString());
							 		        
									 		        	
									 		        
									 		    }   	 
							 		        	
							 		        	//Se verifica si la lista de valores treae un variabla de referencia
							 		        	if (camposRetorno[i][3] != null) {
							 		        	
							 		        	     if (camposRetorno[i][4] != null) 
							 		        	             
							 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString());
							 		        	             JTextField valorRetorno = ((JTextField)camposRetorno[i][3]);
							 		        	             valorRetorno.setText(tablaArrayListaValores[indiceArrayDialogo].getValueAt(tablaArrayListaValores[indiceArrayDialogo].getSelectedRow(),posicionTablaElementoRetorno).toString());
							 	
							 		        	   
							 		        	}
							 		        	    
							 		        	if (i == (camposRetorno.length - 1))    
							 		        	   
							 		        	   	 	
									 		        	if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
									 		        	
									 		        	      jTextFieldSiguiente.grabFocus();
									 		        	      
									 		     
									 		      
									 		        	      
									 		    }    	        
							 		        	
										  
											   jTextFieldRetorno.setFocusable(true);
											   dialogoArrayListaValores[indiceArrayDialogo].setVisible(false);
											
								 		     	 
									     }
							           
							        }       
						            
					        }); 
					         
					        
					        tablaArrayListaValores[indiceArrayDialogo].addKeyListener(new KeyAdapter() {
			    	
					        	public void keyPressed(KeyEvent k) {
					        		
					        		if (k.getKeyCode()==KeyEvent.VK_ESCAPE) {
					        		   
					        		   dialogoListaValores.setVisible(false);
					        		   
					        		} else   
					        		
					        		   if (k.getKeyCode()==KeyEvent.VK_ENTER) { // Evento para escoger las filas con enter
					        		 
							        		
						        		     
						 		            JTextField jTextFieldRetorno = null;
							 		        JComponent jTextFieldSiguiente = null;
							 		        int posicionTablaElementoRetorno = 0;
							 		        int filaSeleccionada = tablaArrayListaValores[indiceArrayDialogo].getSelectedRow() == -1 ? 0 : tablaArrayListaValores[indiceArrayDialogo].getSelectedRow();
						        		    
							 		        boolean esJTextField = false;
							 		       
							 		      
							 		        for (int i = 0; i < camposRetorno.length ; i++) {
							 		        
							 		      	
							 		        	if ( i == 0) {
							 		          
									 		        	   
							 		        	   jTextFieldRetorno = (JTextField) camposRetorno[i][0];
							 		        	   jTextFieldSiguiente = (JComponent) camposRetorno[i][1];
							 		        	   posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
							 		        	   jTextFieldRetorno.setFocusable(false);
							 		        	   jTextFieldRetorno.setText(tablaArrayListaValores[indiceArrayDialogo].getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
							 		        
									 		        	
									 		        	
									 		    }   	 
							 		        	
							 		      
							 		        	//Se verifica si la lista de valores treae un variabla de referencia
							 		        	if (camposRetorno[i][3] != null) {
							 		        	
							 		        	     if (camposRetorno[i][4] != null)  {
									 		          
							 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString());
							 		        	             JTextField valorRetorno = ((JTextField)camposRetorno[i][3]);
							 		        	             valorRetorno.setText(tablaArrayListaValores[indiceArrayDialogo].getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
							 		         
							 		        	             
							 		        	      }      
							 		        	   
							 		        	}
							 		        	    
							 		        	if (i == (camposRetorno.length - 1))    
							 		        	   
						 		        	   	 	
								 		        	if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
								 		        	
								 		        	      jTextFieldSiguiente.grabFocus();
								 		        	      
								 		     
									 		        	      
									 		    }    	        
							 		        	
										
											
											  
											jTextFieldRetorno.setFocusable(true);
											dialogoArrayListaValores[indiceArrayDialogo].setVisible(false);
											
									  }
					            }
					            	
					        });
								        
					        JScrollPane scrollListaValores = new JScrollPane(tablaArrayListaValores[indiceArrayDialogo]);
			                scrollListaValores.setBounds(0,0,380,177);
			                dialogoArrayListaValores[indiceArrayDialogo].add(scrollListaValores);
			                
			              
	 		       }
	 		       
	 		       dialogoArrayListaValores[indiceArrayDialogo].setLocation(getJFrame().getX()  + posicionX + 62, getJFrame().getY() + posicionY + 92);
					  	
	 		       
	 		        //Se ocultan las columnas si se especifico
	 		   	    if (columnasOcultar != null)
 		   	        
	  	               for (int i = 0; i <columnasOcultar.length ; i++) {
						    
						    ocultarColumnas(tablaArrayListaValores[indiceArrayDialogo], columnasOcultar[i]);
						
						}
						
						 
	 		      
	 		       if (tablaArrayListaValores[indiceArrayDialogo].getRowCount() > 0) { // Se verifica que la tabla contenga datos
	 		   	      
	 		   	      if (!dialogoArrayListaValores[indiceArrayDialogo].isVisible())
	 		   	      
	 		   	          dialogoArrayListaValores[indiceArrayDialogo].setVisible(true);  //Se muestra el dialogo de los tramites
	 		   	       
	 		   	      campoLista.grabFocus(); //Se devuelve el foco al componente que llamo la lista 
	 		   	      
	 		   	   } 
	 		   	   
	 		   	    
	 		   	   
	 		   	   
	 		   	       
	 		   }
	 		   
	 		     
  	   } catch (Exception e)	{
  		 	
  		    e.printStackTrace();
  		    	
  	   }
  		 
  	}
  	
  	//********** El de JTable
  	 public void listarValores(String sentenciaSQL,ConectarMySQL conMySQL,final Object[][] camposRetorno,
	                           int posicionX,int posicionY, DefaultCellEditor editor,
	                           final DefaultCellEditor editorRetorno,final int indiceArrayDialogo,
	                           int tamañoArrayDialogo, int p_filaSeleccionada, int[] columnasOcultar) {
  		   
  	    this.filaSeleccionada = p_filaSeleccionada;
  					
  	    
  	    JTable tabla = (JTable)camposRetorno[0][0]; //Se restringe la lista de valores sacando los valores que se hallan digitado
  	    
  	    int numFilas = obtenerFilas(tabla,9);
  	   
  	    String condicion = " and P.idProducto Not in (";
  	    
  	    for (int i = 0; i < numFilas; i++) {
  	       
  	       if (i > 0 && i != numFilas) 
  	       
  	         condicion += ",";
  	        
  	         condicion += "'" + tabla.getValueAt(i,1) + "'";
  	    
  	    
  	    }
  	    
  	    condicion += ")";
  	   
  	    
  	    if (numFilas > 0)
  	       
  	        sentenciaSQL += condicion;
  	        
  	          System.out.println(sentenciaSQL);  
  		try {
 		    
 		   
 		  
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 	
 		   if (resultado != null) {
 		   	
 		   	    ResultSetSerializable resulSet = new ResultSetSerializable(resultado);
 		   	    modelo.setDatos(resulSet.getDatosColumnas(),resulSet.getNombresColumnas());
 		
 		        if (dialogoArrayListaValores == null) {
 		
 		           dialogoArrayListaValores = new JDialog[tamañoArrayDialogo];
 		           tablaArrayListaValores  = new JTable[tamañoArrayDialogo];
 		
 		        }
 		        
 		        
 		        
	 		    if (dialogoArrayListaValores[indiceArrayDialogo] == null) {
	 		 
	 		 	           //Dialogo para especificar la lista de valores
				            dialogoArrayListaValores[indiceArrayDialogo] = new JDialog(getJFrame(),false);
				            dialogoArrayListaValores[indiceArrayDialogo].setLayout(null);
					  		dialogoArrayListaValores[indiceArrayDialogo].getRootPane().setWindowDecorationStyle(JRootPane.NONE);
					  		dialogoArrayListaValores[indiceArrayDialogo].setSize(380,177);
				        
				            //Se configura la tabla de lista de valores
			                tablaArrayListaValores[indiceArrayDialogo] = getJTable(modelo,true);
			        
			                 //Se agrega eventos a la tabla
					 		tablaArrayListaValores[indiceArrayDialogo].addMouseListener(new MouseAdapter() {
					 		   
						 		    public void mouseClicked(MouseEvent m){
						 		    
							 		     if (m.getClickCount() == 2) { //Se escoge la columna con doble Click
							 		       
							 		        JTable jTableRetorno = null;
							 		        int posicionTablaElementoRetorno = 0;
							 		        int posicionFilaTabla = 0;
							 		        int posicionColumnaTabla = 0;
							 		          
							 		      
							 		        for (int i = 0; i < camposRetorno.length ; i++) {
							 		        
							 		      	
							 		        	if ( i == 0) {
							 		            
							 		            	//Se asignan los valores que se configuran
								 		        	jTableRetorno = (JTable) camposRetorno[i][0]; //JTable
								 		            posicionFilaTabla = filaSeleccionada; //Posicion Fila donde se devuelve
						 		        	        posicionColumnaTabla = Integer.valueOf(camposRetorno[i][1].toString()); //Posicion Columna donde se devuelve
						 		        	        posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][3].toString()); //Posicion de la lista de valores que se debe devolver
						 		        	        jTableRetorno.getColumnModel().getColumn(2).getCellEditor().stopCellEditing();
						 		      	  	        jTableRetorno.setValueAt(tablaArrayListaValores[indiceArrayDialogo].getValueAt(tablaArrayListaValores[indiceArrayDialogo].getSelectedRow(),posicionTablaElementoRetorno).toString(),posicionFilaTabla,posicionColumnaTabla); //Se configura el valor
						 		        	        posicionColumnaTabla = Integer.valueOf(camposRetorno[i][2].toString()); //Posicion columna elemento siguiente
						 		        	        dialogoArrayListaValores[indiceArrayDialogo].setName("dummy");   
						 		        	        
									 		    }   	 
							 		        	
							 		        	if (camposRetorno[i][4] != null)  {
							 		        	             
								 		        	      	 
						 		        	                posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
						 		        	                int columnaRetornoAdicion = Integer.valueOf(camposRetorno[i][5].toString());
						 		        	                jTableRetorno.setValueAt(tablaArrayListaValores[indiceArrayDialogo].getValueAt(tablaArrayListaValores[indiceArrayDialogo].getSelectedRow(),posicionTablaElementoRetorno).toString(),posicionFilaTabla,columnaRetornoAdicion); //Se configura el valor 
					 		        	                
							 		        	              
							 		        	}        
							 		        	   
							 		        	if (i == (camposRetorno.length - 1)) {
							 		        	         
							 		        	        jTableRetorno.grabFocus();
									 		      	    jTableRetorno.editCellAt(posicionFilaTabla,posicionColumnaTabla);
									 		      	    editorRetorno.getComponent().requestFocus();
									 		      	    
									 		      	
									 		    }
									 		     
									 		   
											  
											     dialogoArrayListaValores[indiceArrayDialogo].setVisible(false);
											     
								 		     	 
									     }
							           
							        }
							               
						         }   
					        }); 
					         
					      
					        tablaArrayListaValores[indiceArrayDialogo].addKeyListener(new KeyAdapter() {
			    	
					        	public void keyPressed(KeyEvent k) {
					        		
					        		   if (k.getKeyCode()==KeyEvent.VK_ENTER) { // Evento para escoger las filas con enter
					        		 
						        		     
						 		            JTable jTableRetorno = null;
							 		        int posicionTablaElementoRetorno = 0;
							 		        int posicionFilaTabla = 0;
							 		        int posicionColumnaTabla = 0;
							 		          
							 		        
							 		        for (int i = 0; i < camposRetorno.length ; i++) {
							 		        
							 		      	 
							 		        	if ( i == 0) {
							 		          	       
							 		          	      
						 		        	        jTableRetorno = (JTable) camposRetorno[i][0]; //JTable
						 		        	        posicionFilaTabla = filaSeleccionada; //Posicion Fila donde se devuelve
						 		        	        posicionColumnaTabla = Integer.valueOf(camposRetorno[i][1].toString()); //Posicion Columna donde se devuelve
						 		        	        posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][3].toString()); //Posicion de la lista de valores que se debe devolver
						 		        	        jTableRetorno.getColumnModel().getColumn(2).getCellEditor().stopCellEditing();
						 		      	  
						 		        	        jTableRetorno.setValueAt(tablaArrayListaValores[indiceArrayDialogo].getValueAt(tablaArrayListaValores[indiceArrayDialogo].getSelectedRow(),posicionTablaElementoRetorno).toString(),posicionFilaTabla,posicionColumnaTabla); //Se configura el valor
						 		        	       
						 		        	        posicionColumnaTabla = Integer.valueOf(camposRetorno[i][2].toString()); //Posicion columna elemento siguiente
						 		        	        dialogoArrayListaValores[indiceArrayDialogo].setName("dummy");  
						 		        	        
						 		             
									 		    }   	 
							 		        	
						 		   
						 		        	   	if (camposRetorno[i][4] != null)  {
							 		        	             
								 		        	      	 
						 		        	                posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
						 		        	                int columnaRetornoAdicion = Integer.valueOf(camposRetorno[i][5].toString());
						 		        	                jTableRetorno.setValueAt(tablaArrayListaValores[indiceArrayDialogo].getValueAt(tablaArrayListaValores[indiceArrayDialogo].getSelectedRow(),posicionTablaElementoRetorno).toString(),posicionFilaTabla,columnaRetornoAdicion); //Se configura el valor 
					 		        	                
							 		        	              
							 		        	}        
							 		        	   
							 		        	if (i == (camposRetorno.length - 1)) {
							 		        	         
							 		        	        jTableRetorno.grabFocus();
									 		      	    jTableRetorno.editCellAt(posicionFilaTabla,posicionColumnaTabla);
									 		      	    editorRetorno.getComponent().requestFocus();
									 		      	    
									 		      	
									 		    }
									 		     
									 		   
											  
											     dialogoArrayListaValores[indiceArrayDialogo].setVisible(false);
											     
								
									  }
					               }
					            
					            }
					            	
					        });
								        
					        JScrollPane scrollListaValores = new JScrollPane(tablaArrayListaValores[indiceArrayDialogo]);
			                scrollListaValores.setBounds(0,0,380,177);
			                dialogoArrayListaValores[indiceArrayDialogo].add(scrollListaValores);
			                
			              
	 		       }
	 		       
	 		    
	 		       dialogoArrayListaValores[indiceArrayDialogo].setLocation(getJFrame().getX()  + posicionX + 62, getJFrame().getY() + posicionY + 92); 
				
				   
				   //Se ocultan las columnas si se especifico
	 		   	    if (columnasOcultar != null)
 		   	        
	  	               for (int i = 0; i <columnasOcultar.length ; i++) {
						    
						    ocultarColumnas(tablaArrayListaValores[indiceArrayDialogo], columnasOcultar[i]);
						
						}
						
	 		      
	 		       if (tablaArrayListaValores[indiceArrayDialogo].getRowCount() > 0) { // Se verifica que la tabla contenga datos
	 		       
	 		         if (!dialogoArrayListaValores[indiceArrayDialogo].isVisible())
	 		   	      
	 		   	        dialogoArrayListaValores[indiceArrayDialogo].setVisible(true);  //Se muestra el dialogo de los tramites  
	 		   	        
	 		   	     editor.getComponent().requestFocus(); //Se devuelve el foco al componente que llamo la lista 
	 		   	      
	 		   	   } 
	 		   	   
	 		   	   
	 		   	       
	 		   }
	 		
	 		  
	 		   	       
	 		
  	   } catch (Exception e)	{
  		 	
  		    e.printStackTrace();
  		    	
  	   }
  		 
  	}
  	
  	
  
  
  	
  	/**
	  *  Metodo utilizado para crear una lista de valores mostrada en un JTable
	  *  @param tabla tabla donde se listarn los valores
	  *  @param sentenciaSQL sentencia SQL de donde se sacaran los valores
	  *  @param conMySQL Conexion de BD
	  *  @return true se puede mostrar la lista sin problemas false Existe algun problema para mostrar la lista como por ejemplo:
	  *  error en la sentencia SQL
	  *  
	  */
	
	 public void listarValores(String sentenciaSQL,ConectarMySQL conMySQL,final Object[][] camposRetorno,
	                           int posicionX,int posicionY, JTextField campoLista) {
  		   
  		try {
 		 	
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		   
 		   if (resultado != null) {
 		   	
 		   	   ResultSetSerializable resulSet = new ResultSetSerializable(resultado);
 		   	   modelo.setDatos(resulSet.getDatosColumnas(),resulSet.getNombresColumnas());
 		      
 		      if (dialogoArrayListaValores == null) {
 		       
		           //Dialogo para especificar la lista de valores
		            dialogoListaValores = new JDialog(getJFrame(),false);
		            dialogoListaValores.setName(""+posicionY);
		            dialogoListaValores.setLayout(null);
			  		dialogoListaValores.setBounds(getJFrame().getX()  + posicionX, posicionY + 187,380,177);
			  		dialogoListaValores.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
	                
		            //Se configura la tabla de lista de valores
	                tablaListaValores = getJTable(modelo,true);
	            
	                 //Se agrega eventos a la tabla
			 		tablaListaValores.addMouseListener(new MouseAdapter() {
			 		   
				 		    public void mouseClicked(MouseEvent m){
				 		    
					 		     if (m.getClickCount() == 2) { //Se escoge la columna con doble Click
					 		       
						 		        JTextField jTextFieldRetorno = null;
						 		        JComponent jTextFieldSiguiente= null;
						 		        int posicionTablaElementoRetorno = 0;
						 		        
						 		
						 		        for (int i = 0; i < camposRetorno.length ; i++) {
						 		           
						 		           
						 		          if (i == 0) {
							 		         	
							 		        	//Se asignan los valores que se configuran
							 		        	jTextFieldRetorno = (JTextField) camposRetorno[i][0];
							 		        	jTextFieldSiguiente = (JComponent) camposRetorno[i][1];
							 		        	posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
							 		        	jTextFieldRetorno.setFocusable(false);
							 		        	jTextFieldRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
							 		        	
						 		        	
						 		          } 
						 		        
						 		        	      
						 		        	//Se verifica si la lista de valores treae un variabla de referencia
						 		        	if (camposRetorno[i][3] != null) {
						 		        	
						 		        	     if (camposRetorno[i][4] != null) 
						 		        	             
						 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
						 		        	     
						 		        	     
						 		        	     if (camposRetorno[i][3].getClass().getSuperclass().getName().equals("javax.swing.JTextField")) {
						 		        	       
							 		        	       JTextField valorRetorno = ((JTextField)camposRetorno[i][3]);
							 		        	       valorRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
							 		                 
						 		                 } else {
							 		                 	
							 		                 	JComboBox jcomboBox = ((JComboBox)camposRetorno[i][3]);
							 		                 	jcomboBox.setSelectedItem(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
							 		             }
						 		        	}
						 		        
										
									    
									    
									    if (i == (camposRetorno.length - 1)) 
											
											if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
						 		        	
						 		        	      jTextFieldSiguiente.grabFocus();
						 		        	 
					 		        	
									}
									
									
									jTextFieldRetorno.setFocusable(true);
									dialogoListaValores.setVisible(false);
									
						 		     	 
							     }
					           
					        }       
				            
			        }); 
			        
			        
			        tablaListaValores.addKeyListener(new KeyAdapter() {
	    	
			        	public void keyPressed(KeyEvent k) {
			        		
			        		if (k.getKeyCode()==KeyEvent.VK_ESCAPE) {
			        		   
			        		   dialogoListaValores.setVisible(false);
			        		   
			        		} else   
			        		
			        		   if (k.getKeyCode()==KeyEvent.VK_ENTER) { // Evento para escoger las filas con enter
			        		 
					        		     int filaSeleccionada = tablaListaValores.getSelectedRow() == -1 ? 0 : tablaListaValores.getSelectedRow();
					        		     JTextField jTextFieldRetorno = null;
						 		         JComponent jTextFieldSiguiente= null;
						 		          int posicionTablaElementoRetorno = 0;
					        	       
					        	          for (int i = 0; i < camposRetorno.length ; i++) {
								 		           
								 		           
								 		          if (i == 0) {
									 		         	
									 		        	//Se asignan los valores que se configuran
									 		        	jTextFieldRetorno = (JTextField) camposRetorno[i][0];
									 		        	jTextFieldSiguiente = (JComponent) camposRetorno[i][1];
									 		        	posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
									 		        	jTextFieldRetorno.setFocusable(false);
									 		        	jTextFieldRetorno.setText(tablaListaValores.getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
									 		        	
								 		        	
								 		          } 
								 		        
								 		        	      
								 		        	//Se verifica si la lista de valores treae un variabla de referencia
								 		        	if (camposRetorno[i][3] != null) {
								 		        	
								 		        	     if (camposRetorno[i][4] != null) 
								 		        	             
								 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
								 		        	     
								 		        	     
								 		        	      
								 		        	     if (camposRetorno[i][3].getClass().getSuperclass().getName().equals("javax.swing.JTextField")) {
								 		        	       
								 		        	       JTextField valorRetorno = ((JTextField)camposRetorno[i][3]);
								 		        	       valorRetorno.setText(tablaListaValores.getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
								 		                 
								 		                 } else {
								 		                 	
								 		                 	JComboBox jcomboBox = ((JComboBox)camposRetorno[i][3]);
								 		                 	jcomboBox.setSelectedItem(tablaListaValores.getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
								 		                 }	     
								 		        	  
								 		        	}
								 		        
												
											    
											    
											    if (i == (camposRetorno.length - 1)) 
													
													if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
								 		        	
								 		        	      jTextFieldSiguiente.grabFocus();
								 		        	 
							 		        	
											}
									
									
									        jTextFieldRetorno.setFocusable(true);
									        dialogoListaValores.setVisible(false);
									
			        	      
			        	        }
			            }
			            	
			        });
						        
			        
	                JScrollPane scrollListaValores = new JScrollPane(tablaListaValores);
	                scrollListaValores.setBounds(0,0,380,177);
	                dialogoListaValores.add(scrollListaValores);
	      
 		       }
 		          
 		   	   if (tablaListaValores.getRowCount() > 0) { // Se verifica que la tabla contenga datos
 		   	      
 		   	       dialogoListaValores.setVisible(true);  //Se muestra el dialogo de los tramites   
 		   	      
 		   	   } 
 		   	   
 		   	   campoLista.grabFocus();	 //Se especifica el foco en el campo que contiene la lista
 		   
 		   	       
 		   }
 		   
 		   
 		   
 		     
  	   } catch (Exception e)	{
  		 	
  		    e.printStackTrace();
  		    	
  	   }
  		 
  	}
  	
  	
  	
  	 public void listarValores(String sentenciaSQL,ConectarMySQL conMySQL,final Object[][] camposRetorno,
	                           int posicionX,int posicionY, JTextField campoLista, int[] columnasOcultar) {
  		   
									 
  	    
  
  		try {
 		 
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		   
 		   if (resultado != null) {
 		   	
 		   	   ResultSetSerializable resulSet = new ResultSetSerializable(resultado);
 		   	   modelo.setDatos(resulSet.getDatosColumnas(),resulSet.getNombresColumnas());
 		   
 		       if (dialogoListaValores == null) {	//Se verifica si ya se configuro el dialogo
 		           
 		           //Dialogo para especificar la lista de valores
  		            dialogoListaValores = new JDialog(getJFrame(),false);
  		            dialogoListaValores.setLayout(null);
			  		dialogoListaValores.setBounds(getJFrame().getX()  + posicionX, getJFrame().getY() + posicionY + 187,380,177);
			  		dialogoListaValores.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

 		      		 		        	
 		            //Se configura la tabla de lista de valores
  	                tablaListaValores = getJTable(modelo,true);
  	            
  	                 //Se agrega eventos a la tabla
			 		tablaListaValores.addMouseListener(new MouseAdapter() {
			 		   
				 		    public void mouseClicked(MouseEvent m){
				 		    
					 		     if (m.getClickCount() == 2) { //Se escoge la columna con doble Click
					 		       
					 		        for (int i = 0; i < camposRetorno.length ; i++) {
					 		        	
					 		        	//Se asignan los valores que se configuran
					 		        	JTextField jTextFieldRetorno = (JTextField) camposRetorno[i][0];
					 		        	JTextField jTextFieldSiguiente = (JTextField) camposRetorno[i][1];
					 		        	int posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
					 		        	jTextFieldRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
					 		        	
					 		        	
					 		        	if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
					 		        	    
					 		        	      jTextFieldSiguiente.grabFocus();
					 		        	      
					 		        	//Se verifica si la lista de valores treae un variabla de referencia
					 		        	if (camposRetorno[i][3] != null) {
					 		        	
					 		        	     if (camposRetorno[i][4] != null) 
					 		        	             
					 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
					 		        	     
					 		        	     
					 		        	     	     
					 		        	     JLabel valorRetorno = ((JLabel)camposRetorno[i][3]);
					 		        	     valorRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
					 		        
					 		        	}
					 		        	      
					 		        	
									}
									
								//	dialogoListaValores.setVisible(false);
									
						 		     	 
							     }
					           
					        }       
				            
			        }); 
			        
			        
			        tablaListaValores.addKeyListener(new KeyAdapter() {
        	
			        	public void keyPressed(KeyEvent k) {
			        		
			        		if (k.getKeyCode()==KeyEvent.VK_ESCAPE) {
			        		   
			        		   dialogoListaValores.setVisible(false);
			        		   
			        		} else   
			        		
			        		   if (k.getKeyCode()==KeyEvent.VK_ENTER) { // Evento para escoger las filas con enter
			        		 
					        		     int filaSeleccionada = tablaListaValores.getSelectedRow() == -1 ? 0 : tablaListaValores.getSelectedRow();
					        		    
					        	       
					        	         for (int i = 0; i < camposRetorno.length ; i++) {
					 		        	
							 		        	//Se asignan los valores que se configuran
							 		        	JTextField jTextFieldRetorno = (JTextField) camposRetorno[i][0];
							 		        	JTextField jTextFieldSiguiente = (JTextField) camposRetorno[i][1];
							 		        	int posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
							 		        	camposRetorno[i][3] = jTextFieldRetorno.getText();
							 		        	jTextFieldRetorno.setText(tablaListaValores.getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
							 		        	
							 		        	
							 		        	if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
							 		        	    
							 		        	      jTextFieldSiguiente.grabFocus();
							 		    
							 		    
							 		        	//Se verifica si la lista de valores treae un variabla de referencia
							 		        	if (camposRetorno[i][3] != null) {
							 		        	
							 		        	
							 		        	     if (camposRetorno[i][4] != null) 
							 		        	             
							 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
							 		        	     
							 		        	     
							 		        	     	     
							 		        	     JLabel valorRetorno = ((JLabel)camposRetorno[i][3]);
							 		        	     valorRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
							 		        
							 		        	     
							 		        	}
							 		        	      
					 		        	
								  	  }
									
								//	dialogoListaValores.setVisible(false);
			        	      
			        	        }
			            }
			            	
			        });
						        
			        
  	                JScrollPane scrollListaValores = new JScrollPane(tablaListaValores);
                    scrollListaValores.setBounds(0,0,380,177);
                    dialogoListaValores.add(scrollListaValores);
      
  	                
  	   
 		       }
 		       
 		          
 		   	   if (tablaListaValores.getRowCount()>0) { // Se verifica que la tabla contenga datos
 		   	      
 		   	       dialogoListaValores.setVisible(true);  //Se muestra el dialogo de los tramites   
 		   	      
 		   	              
  	                //Se ocultan las columnas si se especifico
  	               for (int i = 0; i <columnasOcultar.length ; i++) {
					    
					    ocultarColumnas(tablaListaValores, columnasOcultar[i]);
					
					}
  	            
 		   	        
 		   	        campoLista.grabFocus();
 		   	   
 		   	   } 
 		   	       
 		   }
 		   
 		   
 		     
  	   } catch (Exception e)	{
  		 	
  		    e.printStackTrace();
  		    	
  	   }
  		 
  	}
  	
  	
  	
  	
  	public void listarValores(String sentenciaSQL,ConectarMySQL conMySQL,final Object[][] camposRetorno,
	                           int posicionX,int posicionY, JTextField campoLista,int ancho) {
  		   
  		
  		try {
 		 
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		   
 		   if (resultado != null) {
 		   	
 		   	   ResultSetSerializable resulSet = new ResultSetSerializable(resultado);
 		   	   modelo.setDatos(resulSet.getDatosColumnas(),resulSet.getNombresColumnas());
 		   
 		       if (dialogoListaValores == null) {	//Se verifica si ya se configuro el dialogo
 		           
 		           //Dialogo para especificar la lista de valores
  		            dialogoListaValores = new JDialog(getJFrame(),false);
  		            dialogoListaValores.setLayout(null);
			  		dialogoListaValores.setBounds(getJFrame().getX()  + posicionX, getJFrame().getY() + posicionY + 187,ancho,177);
			  		dialogoListaValores.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

 		      
 		            //Se configura la tabla de lista de valores
  	                tablaListaValores = getJTable(modelo,true);
  	            
  	                 //Se agrega eventos a la tabla
			 		tablaListaValores.addMouseListener(new MouseAdapter() {
			 		   
				 		    public void mouseClicked(MouseEvent m){
				 		    
					 		     if (m.getClickCount() == 2) { //Se escoge la columna con doble Click
					 		       
					 		        for (int i = 0; i < camposRetorno.length ; i++) {
					 		        	
					 		        	//Se asignan los valores que se configuran
					 		        	JTextField jTextFieldRetorno = (JTextField) camposRetorno[i][0];
					 		        	JTextField jTextFieldSiguiente = (JTextField) camposRetorno[i][1];
					 		        	int posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
					 		        	jTextFieldRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
					 		        	
					 		        	//Se verifica si la lista de valores treae un variabla de referencia
					 		        	if (camposRetorno[i][3] != null) {
					 		        	
					 		        	
					 		        	     if (camposRetorno[i][4] != null) 
					 		        	             
					 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
					 		        	     
					 		        	     
					 		        	     JLabel valorRetorno = ((JLabel)camposRetorno[i][3]);
					 		        	     valorRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
					 		        	     
					 		        	     
					 		        	}
					 		        	
					 		        	
					 		        	
					 		        	if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
					 		        	    
					 		        	      jTextFieldSiguiente.grabFocus();
					 		        	
									}
									
								//	dialogoListaValores.setVisible(false);
									
						 		     	 
							     }
					           
					        }       
				            
			        }); 
			        
			        
			        tablaListaValores.addKeyListener(new KeyAdapter() {
        	
			        	public void keyPressed(KeyEvent k) {
			        		
			        		if (k.getKeyCode()==KeyEvent.VK_ESCAPE) {
			        		   
			        		   dialogoListaValores.setVisible(false);
			        		   
			        		} else   
			        		
			        		   if (k.getKeyCode()==KeyEvent.VK_ENTER) { // Evento para escoger las filas con enter
			        		 
					        		     int filaSeleccionada = tablaListaValores.getSelectedRow() == -1 ? 0 : tablaListaValores.getSelectedRow();
					        		    
					        	       
					        	         for (int i = 0; i < camposRetorno.length ; i++) {
					 		        	
							 		        	//Se asignan los valores que se configuran
							 		        	JTextField jTextFieldRetorno = (JTextField) camposRetorno[i][0];
							 		        	JTextField jTextFieldSiguiente = (JTextField) camposRetorno[i][1];
							 		        	int posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
							 		            camposRetorno[i][3] = jTextFieldRetorno.getText();
							 		        	jTextFieldRetorno.setText(tablaListaValores.getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
							 		        	
							 		        	
							 		        	if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
							 		        	    
							 		        	      jTextFieldSiguiente.grabFocus();
							 		        	      
							 		        	//Se verifica si la lista de valores treae un variabla de referencia
							 		        	if (camposRetorno[i][3] != null) {
							 		        	
							 		        	
							 		        	     if (camposRetorno[i][4] != null) 
							 		        	             
							 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
							 		        	     
							 		        	     
							 		        	    	     
							 		        	     JLabel valorRetorno = ((JLabel)camposRetorno[i][3]);
							 		        	     valorRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
					 		        
					 		        	     
					 		        	}
					 		        	      
					 		        	
								  	  }
								
								//	dialogoListaValores.setVisible(false);
			        	      
			        	        }
			            }
			            	
			        });
						        
			        
  	                JScrollPane scrollListaValores = new JScrollPane(tablaListaValores);
                    scrollListaValores.setBounds(0,0,ancho,177);
                    dialogoListaValores.add(scrollListaValores);
      
  	                
  	   
 		       }
 		       
 		           
 		   	   if (tablaListaValores.getRowCount()>0) { // Se verifica que la tabla contenga datos
 		   	      
 		   	       dialogoListaValores.setVisible(true);  //Se muestra el dialogo de los tramites   
 		   	      
  	            
 		   	       //campoLista.grabFocus();
 		   	   
 		   	   } 
 		   	       
 		   }
 		   
 		   
 		   //campoLista.grabFocus();	 //Se especifica el foco en el campo que contiene la lista
 		   
 		     
  	   } catch (Exception e)	{
  		 	
  		    e.printStackTrace();
  		    	
  	   }
  		 
  	}
  	
  	
  	
  	 public void listarValores(String sentenciaSQL,ConectarMySQL conMySQL,final Object[][] camposRetorno,
	                           int posicionX,int posicionY, JTextField campoLista, int[] columnasOcultar, int ancho) {
  		   
  		try {
 		 
 	
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		   
 		   if (resultado != null) {
 		   	
 		   	   ResultSetSerializable resulSet = new ResultSetSerializable(resultado);
 		   	   modelo.setDatos(resulSet.getDatosColumnas(),resulSet.getNombresColumnas());
 		   
 		       if (dialogoListaValores == null) {	//Se verifica si ya se configuro el dialogo
 		   
 		            //Dialogo para especificar la lista de valores
  		            dialogoListaValores = new JDialog(getJFrame(),false);
  		            dialogoListaValores.setLayout(null);
			  		dialogoListaValores.setSize(ancho,177);
			  		dialogoListaValores.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
			  		dialogoListaValores.setLocation(getJFrame().getX()  + posicionX, getJFrame().getY() + posicionY + 187);

 		      		 		        	
 		            //Se configura la tabla de lista de valores
  	                tablaListaValores = getJTable(modelo);
  	            
  	                 //Se agrega eventos a la tabla
			 		tablaListaValores.addMouseListener(new MouseAdapter() {
			 		   
				 		    public void mouseClicked(MouseEvent m){
				 		    
					 		     if (m.getClickCount() == 2) { //Se escoge la columna con doble Click
					 		        
					 		       
					 		        JTextField jTextFieldRetorno = null;
					 		        
					 		        
					 		        for (int i = 0; i < camposRetorno.length ; i++) {
					 		        	
					 		        	//Se asignan los valores que se configuran
					 		        	jTextFieldRetorno = (JTextField) camposRetorno[i][0];
					 		        	JTextField jTextFieldSiguiente = (JTextField) camposRetorno[i][1];
					 		        	int posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
					 		        	jTextFieldRetorno.setFocusable(false);
					 		        	jTextFieldRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
					 		        	
					 		        	if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
					 		        	    
					 		        	      jTextFieldSiguiente.grabFocus();
					 		        	      
					 		        	//Se verifica si la lista de valores treae un variabla de referencia
					 		        	if (camposRetorno[i][3] != null) {
					 		        	
					 		        	     if (camposRetorno[i][4] != null) 
					 		        	             
					 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
					 		        	     
					 		        	     
					 		        	     	     
					 		        	     JLabel valorRetorno = ((JLabel)camposRetorno[i][3]);
					 		        	     valorRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
					 		        
					 		        	}
					 		        	      
					 		        	
									}
									jTextFieldRetorno.setFocusable(true);
									dialogoListaValores.setVisible(false);
									
						 		     	 
							     }
					           
					        }       
				            
			        }); 
			        
			        
			        tablaListaValores.addKeyListener(new KeyAdapter() {
        	
			        	public void keyPressed(KeyEvent k) {
			        		
			        		if (k.getKeyCode()==KeyEvent.VK_ESCAPE) {
			        		   
			        		   dialogoListaValores.setVisible(false);
			        		   
			        		} else   
			        		
			        		   if (k.getKeyCode()==KeyEvent.VK_ENTER) { // Evento para escoger las filas con enter
			        		 
					        		     int filaSeleccionada = tablaListaValores.getSelectedRow() == -1 ? 0 : tablaListaValores.getSelectedRow();
					        		     
					        		     JTextField jTextFieldRetorno = null;
					        	       
					        	         for (int i = 0; i < camposRetorno.length ; i++) {
					 		        	
							 		        	
							 		        	//Se asignan los valores que se configuran
							 		        	jTextFieldRetorno = (JTextField) camposRetorno[i][0];
							 		        	JTextField jTextFieldSiguiente = (JTextField) camposRetorno[i][1];
							 		        	int posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][2].toString());
							 		            jTextFieldRetorno.setFocusable(false);
							 		            jTextFieldRetorno.setText(tablaListaValores.getValueAt(filaSeleccionada,posicionTablaElementoRetorno).toString());
							 		        	
					 		        	 
							 		        		if (jTextFieldSiguiente != null) //Se pasa el foco al siguiente JTextField de la forma
							 		        	    
							 		        	      jTextFieldSiguiente.grabFocus();
							 		        	
							 		        
							 		        	//Se verifica si la lista de valores treae un variabla de referencia
							 		        	if (camposRetorno[i][3] != null) {
							 		        	
							 		        	
							 		        	     if (camposRetorno[i][4] != null) 
							 		        	             
							 		        	             posicionTablaElementoRetorno = Integer.valueOf(camposRetorno[i][4].toString()); 
							 		        	     
							 		        	     
							 		        	     	     
							 		        	     JLabel valorRetorno = ((JLabel)camposRetorno[i][3]);
							 		        	     valorRetorno.setText(tablaListaValores.getValueAt(tablaListaValores.getSelectedRow(),posicionTablaElementoRetorno).toString());
							 		        
							 		        	     
							 		        	}
							 		        	
							  	          }
									      
									      jTextFieldRetorno.setFocusable(true);
									      dialogoListaValores.setVisible(false);
			        	      
			        	        }
			            }
			            	
			        });
						        
			        
  	                JScrollPane scrollListaValores = new JScrollPane(tablaListaValores);
                    scrollListaValores.setBounds(0,0,ancho,177);
                    dialogoListaValores.add(scrollListaValores);
      
  	                
  	   
 		       }
 		      	
 		          
 		   	   if (tablaListaValores.getRowCount()>0) { // Se verifica que la tabla contenga datos
 		   	     
 		   	     if (!dialogoListaValores.isVisible()) 
 		   	     
	 		   	   
 		   	        dialogoListaValores.setVisible(true);  //Se muestra el dialogo de los tramites   
 		   	       
	   	                
  	                 //Se ocultan las columnas si se especifico
  	                for (int i = 0; i <columnasOcultar.length ; i++) {
					    
					    ocultarColumnas(tablaListaValores, columnasOcultar[i]);
					
					}
	  	        
	  	        
	  	           
 		   	       campoLista.grabFocus();
 		   	   
 		   	   } 
 		   	       
 		   }
 		   
 		   
 		   campoLista.grabFocus();	 //Se especifica el foco en el campo que contiene la lista
 		   
 		     
  	   } catch (Exception e)	{
  		 	
  		    e.printStackTrace();
  		    	
  	   }
  		 
  	}
  	
  	
  	
  	
  	/**
	  *  Metodo para comparar dos fechas
	  *  @param fechaInicial Fecha inicial
	  *  @param fechaFinal Fecha Final
	  *  @param escribir Si se desea o no escribir el mensaje de error
	  *  @return true la fecha inicial es menor a la primera, false la fecha inicial es mayor a la final
	  *  
	  */
	
  	public boolean compararFechas(String fechaInicial, String fechaFinal,boolean escribir) {
        
        boolean resultado = false;
  	    
  		Calendar calendar = Calendar.getInstance();
  		
  		// Se validan las fechas
  		if (escribir)
  		  calendar.set(Integer.parseInt(fechaInicial.substring(0,4)),Integer.parseInt(fechaInicial.substring(5,7))-1,Integer.parseInt(fechaInicial.substring(8,10)));
  		else  
  		  calendar.set(Integer.parseInt(fechaInicial.substring(0,4)),Integer.parseInt(fechaInicial.substring(5,7))-1,Integer.parseInt(fechaInicial.substring(8,10)),Integer.parseInt(fechaInicial.substring(11,13)),Integer.parseInt(fechaInicial.substring(14,16)),Integer.parseInt(fechaInicial.substring(17,19)));
  		
  		 
  		Date fecInicial = calendar.getTime();
  			
  		if (escribir)
  		   calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)));
  		else
  		   calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)),Integer.parseInt(fechaFinal.substring(11,13)),Integer.parseInt(fechaFinal.substring(14,16)),Integer.parseInt(fechaFinal.substring(17,19)));
  		
  		
  		Date fecFinal = calendar.getTime();

  		if (escribir) {
  		
  			if (fecFinal.compareTo(fecInicial) >= 0) 
	  		   
	  		    resultado = true;
	  	
	  		else {
	  		     Mensaje("La fecha final debe ser mayor o igual a la inicial","Error",JOptionPane.ERROR_MESSAGE);
	  		}    
  		
  		}else 
  		   if (fecFinal.compareTo(fecInicial) > 0) {


	  		    resultado = true;
	  		}
	  	
	  	return 	resultado;
  		
  	}
  	
  	
  	/**
	  *  Metodo para configurar un objeto de tipo JLabel y se agrega por defecto en el JFrame
	  *  @param texto Texto que se desea agregar al JLabel
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @return JLabel configurado
	  *  
	  */
	
  	public JLabel getJLabel(String texto,int posX, int posY, int ancho, int alto) {
  		
  		
  		//Se configura el JLabel
  		JLabel jLabel = new JLabel(texto);
 		jLabel.setBounds(posX,posY,ancho,alto);
 	
 		
 		if (frame != null)
 		   
 		   frame.add(jLabel); //Se agrega al JFrame
 		  
 	    return jLabel;
 	    
  	}
  	
  	
  	
  	/**
	  *  Metodo para configurar un objeto de tipo JTextField y se agrega por defecto en el JFrame
	  *  @param texto Texto que se desea agregar al JLabel
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param toolTipText Texto informativo que se visualiza cuando se coloca el mouse sobre el componente
	  *  @param setName Parametro utilizado para validar la maxima cantidad de caracteres
	  *  @return JTextField configurado
	  *  
	  */
	  
  	  public JTextField getJTextField(String texto,int posX, int posY, int ancho, int alto, String toolTipText,String setName) {
	  		
	  		JTextField jTextfield = new JTextField(texto) {
	  			
	  			//static final long serialVersionUID = 19781204;
	  	
	  			public void paste(){};  //Se especifica el metodo paste de esta manera para que no se pueda copiar en el JTextFied
	  		};	
	  		
	  		
	 		jTextfield.setBounds(posX,posY,ancho,alto);
	 	    jTextfield.setToolTipText(toolTipText);
	 	   
	 	    if (texto.indexOf('-') > 0)  {
	 	    
	 	        jTextfield.setName(texto);
	 	        jTextfield.setHorizontalAlignment(0);
	 	    
	 	    }else	
	 	       
	 	        jTextfield.setName(setName);
	 	    
	 	    
	 	    if (texto.equals("aaaa-mm-dd")) {
	 	    
	 	      jTextfield.selectAll();
	 	      jTextfield.setHorizontalAlignment(0);
	 		
	 	    }
	 	    
	 	    if (frame != null)
	 	    
	 	       frame.add(jTextfield); 	    
	 	    
	 	    return jTextfield;
  	}
  	
  	/**
	  *  Metodo para configurar un objeto de tipo JTextField y se agrega por defecto en el JFrame
	  *  @param toolTipText Texto informativo que se visualiza cuando se coloca el mouse sobre el componente
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param setName Parametro utilizado para validar la maxima cantidad de caracteres
	  *  @return JTextField configurado
	  *  
	  */
	
    public JTextField getJTextField(String toolTipText,int posX, int posY, int ancho, int alto,String setName) {
  		
  		JTextField jTextfield = new JTextField(){
  		
  			static final long serialVersionUID = 19781205;
  	
  			
  			public void paste(){};  //Se especifica el metodo paste de esta manera para que no se pueda copiar en el JTextFied
  			
  		};	
  		
 		jTextfield.setBounds(posX,posY,ancho,alto);
 	    jTextfield.setToolTipText(toolTipText);
 	    jTextfield.setName(setName);
 	    
 	    if (frame != null)
 	       
 	       frame.add(jTextfield); 	    
 	    
 	    return jTextfield;
  	}
  	
  	/**
	  *  Metodo para configurar un objeto de tipo JTextField y se agrega por defecto en el JFrame
	  *  @param toolTipText Texto informativo que se visualiza cuando se coloca el mouse sobre el componente
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param setName Parametro utilizado para validar la maxima cantidad de caracteres
	  *  @param editable false si es el JTextField no es editable y no focusable true si es el JTextField  es editable y focusable
	  *  @return JTextField configurado
	  *  
	  */
	
  	public JTextField getJTextField(String toolTipText,int posX, int posY, int ancho, int alto,String setName,boolean editable) {
  		
  		JTextField jTextfield = new JTextField(){
  		
  			static final long serialVersionUID = 19781206;
  	
  			public void paste(){};  //Se especifica el metodo paste de esta manera para que no se pueda copiar en el JTextFied
  		};	
  		
  		
 		jTextfield.setBounds(posX,posY,ancho,alto);
 	    jTextfield.setToolTipText(toolTipText);
 	    jTextfield.setName(setName);
 	    jTextfield.setEditable(editable);
 	    jTextfield.setFocusable(editable);
 	   
 	    
 	    if (frame != null)
 	
 	       frame.add(jTextfield); 	    
 	     
 	    return jTextfield;
  	}
  	
  	
  	public JFormattedTextField getJFormattedTextField(String texto,int posX, int posY, int ancho, int alto, String toolTipText,String setName) {
  		
  		JFormattedTextField jFormattedTextField = null;
  		
  		try {
  		
		  		MaskFormatter  maskFormatter = new MaskFormatter("####/##/##");
		  		
		  		jFormattedTextField = new JFormattedTextField(maskFormatter){
		  		
		  			static final long serialVersionUID = 19781206;
		  	
		  			public void paste(){};  //Se especifica el metodo paste de esta manera para que no se pueda copiar en el JTextFied
		  		};	
		  		 
		  		
	  			jFormattedTextField.setBounds(posX,posY,ancho,alto);
 	            jFormattedTextField.setToolTipText(toolTipText);
 	            jFormattedTextField.setText(texto.replace('-','/'));
 	            jFormattedTextField.setHorizontalAlignment(0);
 	            jFormattedTextField.setName(setName);
 	    
		 	    if (frame != null)
		 	
		 	       frame.add(jFormattedTextField);
		 	       
		 	       
		 } catch (Exception e) {
		 	
		 	 e.printStackTrace();
		 }	        	    
		 	     
 	    return jFormattedTextField;
  	}
  	
  	
  	//***************** getFormatoNumero **********************************
  	
  	public String getFormatoNumero(String numero) {
  		
  		if (formatter == null)
  		
  		  formatter = new DecimalFormat("#,###,###"); 
  		
  		
  		return formatter.format(new Long(numero));
  		
  	}
  	
  	
  	//***************** getFormatoNumero **********************************
  	
  	public String getFormatoNumero(Long numero) {
  		
  		if (formatter == null)
  		
  		  formatter = new DecimalFormat("#,###,###"); 
  		
  		
  		return formatter.format(numero);
  		
  	}
  	
  	
  	/**
	  *  Metodo para configurar un objeto de tipo JPasswordField y se agrega por defecto en el JFrame
	  *  @param toolTipText Texto informativo que se visualiza cuando se coloca el mouse sobre el componente
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param setName Parametro utilizado para validar la maxima cantidad de caracteres
	  *  @return JPasswordField configurado
	  *  
	  */
	
  	public JPasswordField getJPasswordField(String toolTipText,int posX, int posY, int ancho, int alto,String setName) {
  		
  		
  		JPasswordField jPasswordField = new JPasswordField();
 		jPasswordField.setBounds(posX,posY,ancho,alto);
 		jPasswordField.setToolTipText(toolTipText);
 	    jPasswordField.setFont(getLetraJPassword());
 	   
  		
 	    if (frame != null)
 	       
 	       frame.add(jPasswordField); 	    
 	     
 	    return jPasswordField;
  	}
  	
  	
  	
  	/**
	  *  Metodo para configurar un objeto de tipo JComboBox y se agrega por defecto en el JFrame
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param toolTipText Texto informativo que se visualiza cuando se coloca el mouse sobre el componente
	  *  @return JComboBox configurado
	  *  
	  */
  	public JComboBox getJComboBox(int posX, int posY, int ancho, int alto, String toolTipText){
  	 	
  	 	JComboBox jCombobox = new JComboBox();
 		
 		jCombobox.setBounds(posX,posY,ancho,alto);
 		jCombobox.setToolTipText(toolTipText);
 		
 		if (frame != null)
 		  
 		   frame.add(jCombobox);
 	  	
 	  	return jCombobox;
 	}
 	
 	/**
	  *  Metodo para configurar un objeto de tipo JCheckBox y se agrega por defecto en el JFrame
	  *  @param texto Texto para el JCheckBox
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param pathSelectIcon ruta del icono para el JCheckBox
	  *  @return JCheckBox configurado
	  *  
	  */
  	
 	public JCheckBox getJCheckBox(String texto, int posX, int posY, int ancho, int alto, String tooltipText,String pathSelectIcon) {
 		
 		JCheckBox jcheckbox = new JCheckBox(texto);
 		jcheckbox.setBounds(posX,posY,ancho,alto);
 		jcheckbox.setMnemonic(texto.charAt(0));
 		jcheckbox.setSelectedIcon(new ImageIcon(getClass().getResource(pathSelectIcon)));
 		
 		if (frame != null)
 		  
 		  frame.add(jcheckbox);
 		  
 	  	return jcheckbox;	
	}
	
	
	
	public JCheckBox getJCheckBox(String texto, int posX, int posY, int ancho, int alto, String tooltipText) {
 		
 		JCheckBox jcheckbox = new JCheckBox(texto);
 		jcheckbox.setBounds(posX,posY,ancho,alto);
 		jcheckbox.setMnemonic(texto.charAt(0));
 		jcheckbox.setToolTipText(tooltipText);
 		
 		if (frame != null)
 		  
 		  frame.add(jcheckbox);
 		  
 	  	return jcheckbox;	
	}
	
	/**
	  *  Metodo para configurar un objeto de tipo JCheckBox y se agrega por defecto en el JFrame
	  *  @param texto Texto para el JCheckBox
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @return JCheckBox configurado
	  *  
	  */
  	
	public JCheckBox getJCheckBox(String texto, int posX, int posY, int ancho, int alto) {
 		
 		JCheckBox jcheckbox = new JCheckBox(texto);
 		jcheckbox.setBounds(posX,posY,ancho,alto);
 	    jcheckbox.setMnemonic(texto.charAt(0));
 		
 		if (frame != null)
 		
 		  frame.add(jcheckbox);
 		  
 	  	return jcheckbox;	
	}

	
    
	/**
	  *  Metodo para configurar un objeto de tipo JButton y se agrega por defecto en el JFrame
	  *  @param texto Texto para el JButton
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param pathIcon ruta del icono para el jButton
	  *  @param activo true activo, false desactivado
	  *  @return JButton configurado
	  *  
	  */
	  	
	public JButton getJButton(String texto, int posX, int posY, int ancho, int alto, String pathIcon, boolean activo) {	
		
		JButton jButton = new JButton(texto,new ImageIcon(getClass().getResource("/"+pathIcon)));
        jButton.setBounds(posX,posY,ancho,alto);
        jButton.setEnabled(activo);
        
        if (frame != null)
          
          frame.add(jButton);
          
        return jButton;
	}

    /**
	  *  Metodo para configurar un objeto de tipo JButton y se agrega por defecto en el JFrame
	  *  @param texto Texto para el JButton
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param pathIcon ruta del icono para el jButton
	  *  @param toolTipText Texto informativo que se visualiza cuando se coloca el mouse sobre el componente
	  *  @return JButton configurado
	  *  
	  */
		
	public JButton getJButton(String texto, int posX, int posY, int ancho, int alto, String pathIcon, String toolTipText) {	
		
		JButton jButton;
		
		//Se verifica si se le debe agregar icono
		if (pathIcon.length() > 0)
		   
		    jButton = new JButton(texto,new ImageIcon(getClass().getResource("/"+pathIcon)));
		   
		else
		  
		     jButton = new JButton(texto);	   
		
		
		if (!texto.isEmpty())
		
		   jButton.setMnemonic(texto.charAt(0));
		
        jButton.setBounds(posX,posY,ancho,alto);
        jButton.setToolTipText(toolTipText);
        
        
        if (frame != null)
          
          frame.add(jButton);
        
        return jButton;
	}
    
    /**
	  *  Metodo para configurar un objeto de tipo JRadioButton y se agrega por defecto en el JFrame
	  *  @param texto Texto para el JButton
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param activo true activo, false desactivado
	  *  @return JRadioButton configurado
	  *  
	  */
	
    public JRadioButton getJRadioButton(String texto, int posX, int posY, int ancho, int alto, boolean activo) {
    	
    	JRadioButton jRadioButton = new JRadioButton(texto);
        jRadioButton.setBounds(posX,posY,ancho,alto);
        jRadioButton.setSelected(activo);
        
        if (frame != null)
        
          frame.add(jRadioButton);
          
        return jRadioButton;
        
	}
    
        /**
	  *  Metodo para configurar un objeto de tipo JRadioButton y se agrega por defecto en el JFrame
	  *  @param texto Texto para el JButton
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param activo true activo, false desactivado
	  *  @return JRadioButton configurado
	  *  
	  */
	
    public JRadioButton getJRadioButton(String texto, int posX, int posY, int ancho, int alto, ButtonGroup grupo,boolean activo) {
    	
    	JRadioButton jRadioButton = new JRadioButton(texto);
        jRadioButton.setBounds(posX,posY,ancho,alto);
        jRadioButton.setSelected(activo);
        grupo.add(jRadioButton);
        
        if (frame != null)
        
          frame.add(jRadioButton);
          
        return jRadioButton;
        
	}
	
	
     /**
	  *  Metodo para configurar un objeto de tipo JRadioButton y se agrega por defecto en el JFrame
	  *  @param texto Texto para el JButton
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param grupo grupo al cual se agrega el JRadioButton
	  *  @return JRadioButton configurado
	  *  
	  */
	
	public JRadioButton getJRadioButton(String texto, int posX, int posY, int ancho, int alto, ButtonGroup grupo) {
    	
    	JRadioButton jRadioButton = new JRadioButton(texto);
        jRadioButton.setBounds(posX,posY,ancho,alto);
        grupo.add(jRadioButton);
        
        if (frame != null)
        
           frame.add(jRadioButton);
           
        return jRadioButton;
        
	}
	
	/**
	  *  Metodo para configurar un objeto de tipo JPanel 
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @return JPanel configurado
	  *  
	  */
	
     public JPanel getJPanel( int posX, int posY, int ancho, int alto) {
	    
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setDoubleBuffered(true);
 		panel.setBounds(posX,posY,ancho,alto);
 		panel.setBorder(getEtchedBorder());
 		
 		if (frame != null)
 		
 	       frame.add(panel);
 	       
 	       
 		return panel;
	
	}
	

	/**
	  *  Metodo para configurar un objeto de tipo JPanel con un borde con titulo
	  *  @param titulo Titulo del JPanel
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @return JPanel configurado
	  *  
	  */
	  
	public JPanel getJPanel(String titulo, int posX, int posY, int ancho, int alto) {
		
		JPanel panel = new JPanel();
 		panel.setLayout(null);
 		panel.setDoubleBuffered(true);
 		panel.setBounds(posX,posY,ancho,alto);
 		panel.setBorder(new javax.swing.border.TitledBorder(titulo));
 		
 		if (frame != null)
 		
 	       frame.add(panel);
 		
 		
 		return panel;
	
	}
	
	
	/**
	  *  Metodo para configurar un objeto de tipo JPanel con un borde con titulo especificando el tamaño la letra
	  *  @param titulo Titulo del JPanel
	  *  @param posX Posicion en X
	  *  @param posY Posicion en Y
	  *  @param ancho Ancho 
	  *  @param alto alto
	  *  @param tamañoLetra Tamaño de la letra del titulo
	  *  @return JPanel configurado
	  *  
	  */
	
	public JPanel getJPanel(String titulo, int posX, int posY, int ancho, int alto,int tamañoLetra) {
	   
	   Font letra = new Font("Arial",Font.PLAIN,tamañoLetra);
	   
    	Border etched = BorderFactory.createEtchedBorder();
  	    TitledBorder titled =  BorderFactory.createTitledBorder(etched,titulo,TitledBorder.CENTER,TitledBorder.TOP,letra);
    	
		JPanel panel = getJPanel(titulo,posX,posY,ancho,alto);
		panel.setBorder(titled);
		
		if (frame != null)
		
		  frame.add(panel);
 		
 		return panel;
	
	}
	 
    
    /**
	  *  Metodo para configurar un objeto de tipo JTable con un modelo de datos y la presentacion de tipo iTunes
	  *  @param modelo Modelo de datos
	  *  @return JTable configurado
	  *  
	  */
	
	public JTable getJTable(DefaultTableModel modelo) {
		
		JTable tabla = new JTable(modelo);
 		tabla.setAutoCreateColumnsFromModel(false);
 		tabla.getTableHeader().setReorderingAllowed(false);
 		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		getJTunesTableHeader().borrarIconoSeleccion();
 		tabla.setTableHeader(getJTunesTableHeader());
 	
 		
 		TableColumnModel colModel = tabla.getColumnModel();
		tabla.getTableHeader().setColumnModel(colModel);
        
        for (int i = 0; i < tabla.getColumnCount(); i++) {
			
			TableColumn column = colModel.getColumn(i);
			column.setHeaderRenderer(getJTunesHeaderRenderer());
		
			if (!tabla.getColumnClass(i).toString().endsWith("Boolean"))
			   column.setCellRenderer(getAlinearIzquierda());
			
 		
        }
        
        return tabla;  		
  	    
	}
	
	
    /**
	  *  Metodo para configurar un objeto de tipo JTable con un modelo de datos y la presentacion de tipo iTunes, pero se debe
	  *  utilizar cuando en la misma GUI se crea mas un objeto JTable con la presentacion iTunes
	  *  @param modelo Modelo de datos
	  *  @param comodin comodin
	  *  @return JTable configurado
	  *  
	  */
	
	public JTable getJTable(DefaultTableModel modelo,boolean comodin) {
		
		
		JTable tabla = new JTable(modelo);
 		tabla.setAutoCreateColumnsFromModel(false);
 		tabla.getTableHeader().setReorderingAllowed(false);
 		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		tabla.setTableHeader(new JTunesTableHeader(true));
 		
 		TableColumnModel colModel = tabla.getColumnModel();
		tabla.getTableHeader().setColumnModel(colModel);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
			
			TableColumn column = colModel.getColumn(i);
			column.setHeaderRenderer(getJTunesHeaderRenderer());
		
			if (!tabla.getColumnClass(i).toString().endsWith("Boolean"))
			   column.setCellRenderer(getAlinearIzquierda());
 		
        }
  
        return tabla;  		
  	    
	}
	
	
	/**
	  *  Metodo para configurar un objeto de tipo JTable 
	  *  @return JTable configurado
	  *  
	  */
	
	public JTable getJTable() {
		
		 JTable tabla = new JTable();
		
		//Coloca en negrilla los encabezados
        tabla.getTableHeader().setFont(getLetraListaDeValores());
        tabla.getTableHeader().setBackground(Color.green);
        tabla.getTableHeader().setForeground(Color.white);
 		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		tabla.getTableHeader().setReorderingAllowed(false);
 	
 	    return tabla;  		
  	    
	}
	
	/**
	  *  Metodo para devolver un objeto de tipo JTable configurado en la lista de valores
	  *  @return JTable configurado
	  *  
	  */
	
	public JTable getJTableListaValores() {
		
		 return tablaListaValores;
  	    
	}


    /**
	  *  Metodo para devolver un objeto de tipo Array JTable configurado para varias lista de valores
	  *  @return JTable configurado
	  *  
	  */
	
	public JTable[] getJTableArrayListaValores() {
		
		 return tablaArrayListaValores;
  	    
	}
	
	/**
	  *  Metodo que muestra el JFrame
	  *  
	  */
	
	public void mostrarJFrame() {
		
		frame.setVisible(true);
		
		
	}
	
	
	
    /**
	  *  Metodo que oculta el JFrame
	  *  
	  */
	
	public void ocultarJFrame() {
		
	    framePadre.setVisible(true);
	    frame.dispose();
	    
	  	
	}

  	/**
	  *  Metodo que oculta las columnas de una tabla en un rango de columnas
	  *  @param tabla Tabla sobre la cual se ocultaran las columnas
	  *  @param columnaOcultar Columna ha ocultar
	  *
	  */

    public void ocultarColumnas(JTable tabla,int columnaOcultar) {
   	
   	   if (size == null) //Se instancia una sola ves
   	 	 
   	 	 size = new SizeRequirements();
   	   	 	
   	 	tabla.getColumnModel().getColumn(columnaOcultar).setMinWidth(size.minimum);
	    tabla.getColumnModel().getColumn(columnaOcultar).setMaxWidth(size.maximum);
		tabla.getColumnModel().getColumn(columnaOcultar).setPreferredWidth(size.preferred);
	
		
   	 
    }
  
  
  
	/**
	  *  Metodo que oculta las columnas de una tabla en un rango de columnas
	  *  @param tabla Tabla sobre la cual se ocultaran las columnas
	  *  @param tabla Tabla sobre la cual se ocultaran las columnas
	  *  @param columnaInicial Columna desde donde se comenzaran a ocultar las filas
	  *  @param columnaFinal Columna hasta donde se  ocultaran las filas
	  */

    public void ocultarColumnas(JTable tabla,int columnaInicial,int columnaFinal) {
   	
   	   if (size == null) //Se instancia una sola ves
   	 	 
   	 	 size = new SizeRequirements();
   	 	
   	 	for (int i = columnaInicial; i <= columnaFinal; i++) {
	   	 	
	   	 	tabla.getColumnModel().getColumn(i).setMinWidth(size.minimum);
		    tabla.getColumnModel().getColumn(i).setMaxWidth(size.maximum);
			tabla.getColumnModel().getColumn(i).setPreferredWidth(size.preferred);
		
		}	
   	 
    }
  
  
  	/**
	  *  Metodo para configurar un objeto de tipo DefaultCellEditor para que escriba siempre en mayuscula en una columna
	  *  de una tabla 
	  *  @param tabla Tabla
	  *  @return JTable configurado
	  *  
	  */
	
/*  	public  DefaultCellEditor getMayusculaEditor(JTable tabla) {
  	       
         //Se configura el editor para mayusculas
        
        final ConvertirMayColumn stringField = new ConvertirMayColumn();
        
        DefaultCellEditor stringEditor = 
            new DefaultCellEditor(stringField) {
                
                static final long serialVersionUID = 19781207;
  	
                // Se retorna el String en mayuscula
                public Object getCellEditorValue() {
                	
                    return stringField.getValue();
                }
            };
        
        return stringEditor;
     } 	
     
        
     /**
	  *  Metodo para configurar un objeto de tipo DefaultCellEditor para que acepte solo numeros
	  *  @param tabla Tabla
	  *  @return JTable configurado
	  *  
	  */

  /*   public DefaultCellEditor getNumerosEditor(JTable tabla) {
   
        final ConvertirNumColumn integerField = new ConvertirNumColumn();
         
         
          DefaultCellEditor integerEditor = 
            new DefaultCellEditor(integerField) {
            	
            static final long serialVersionUID = 19781208;

                
                // Se retorna el numero
                public Object getCellEditorValue() {
                    return integerField.getValue();
                }
            };
        
           
           return integerEditor;
        
 	}
 	
 	/**
	  *  Metodo para configurar un objeto de tipo DefaultCellEditor para que acepte solo formatos de tipo de fecha
	  *  @param tabla Tabla
	  *  @return JTable configurado
	  *  
	  */

/* 	public DefaultCellEditor getFechaEditor(JTable tabla) {
   
       final  ConvertirFechaColumn fechaField = new ConvertirFechaColumn();
         
         
       DefaultCellEditor fechaEditor = 
            new DefaultCellEditor(fechaField) {
            	
             static final long serialVersionUID = 19781209;
   
                // Se retorna el numero
                public Object getCellEditorValue() {
                    return fechaField.getValue();
                }
        };
        
        return fechaEditor;
        
        
 	}
 	
    
    /**
	  *  Metodo para configurar un objeto de tipo Set que permite que en una GUI que permita utilizar TAB o enter para los forward
	  *  @return Set configurado
	  *  
	  */

 	public Set<AWTKeyStroke> getUpKeysFrame() {
 		
 		if (upKeys == null) {
            
            // Se configura para que se pueda navegar con Enter o Tab
           upKeys = new HashSet<AWTKeyStroke>(1);
           upKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER,0));
           upKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB,0));
           
        }
        
        
        return upKeys;
      
  
 	}
 	
 	   
    /**
	  *  Metodo para configurar un objeto de tipo Set que permite que en una GUI que permita utilizar TAB 
	  *  @return Set configurado
	  *  
	  */
	
 	public Set<AWTKeyStroke> getUpKeys() {
 		
 		if (upKeys1 == null) {
            
            // Se configura para que se pueda navegar con Enter o Tab
           upKeys1 = new HashSet<AWTKeyStroke>(1);
           upKeys1.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB,0));
           
        }
        
        
        return upKeys1;
      
  
 	}
 	
 	/**
 	  * Metodo para redondear valores en Java
 	  * @param numeroRedondear : Numero a redondear
 	  * @param numeroDecimales : Numero decimales a redondear
 	  * @return numero redondeado
 	  */
 	
 	
 	 public float redondearNumero(float numeroRedondear, int numeroDecimales) {
	      
	      float potenciaDiez = (float)Math.pow(10,numeroDecimales);
	     
	       numeroRedondear = numeroRedondear * potenciaDiez;
	       
	      float numeroRedondeado = Math.round(numeroRedondear);
	      
	      return (float)numeroRedondeado/potenciaDiez;
	      
     }
     
     
 	 	 
    /**
	  *  Metodo que permite adicionar elementos en un JComboBox y retornar los codigos de esos elementos
	  *  consultando desde la BD
	  *  @param jComboBox JComboBox al que se le adicionan los elementos
	  *  @param sentenciaSQL consulta hacia la BD desde donde se extraen los datos
	  *  @return String[] Array con los codigos
	  *  
	  */
	
 	public String[] traerDatos(JComboBox jComboBox,String sentenciaSQL,ConectarMySQL conMySQL)  {
		 
		  String resultadoString[] = null;
		 
		  try {
 		 
 		       // Se realiza la consulta en la base de datos
 		        ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		    	
 		    	if (resultado!=null) 
 		        
	 		         resultado.last();
	 		        
	 		         int numFilas = resultado.getRow();
	 		        
	 		       
	 		         if (numFilas > 0) { //Se verifica que haya datos
	 		         
			 		         resultadoString = new String[numFilas];
			 		         
			 		         resultado.beforeFirst();
			 		         
			 		         int i=0;
			 		        
			 		         // Se extraen los registros
			 		         while (resultado.next()) {
			 		         	 
			 		         	 jComboBox.addItem(resultado.getString(2));
			 		         	 
			 		             resultadoString[i] = resultado.getString(1);
			 	
			 		              
			 		             i++;
			 		              
			 		         }    
			 		        
	 	       
	 		         }     
	 		        
 		   } catch (Exception e) {
 		 	  
 		 	  Mensaje("Error al adicionar elementos al JComboBox","Reporte de Error ",0);
	       
	       }
	       
	       return resultadoString;
 	}
 	
 		   
    
    /**
	  *  Metodo que deveulve adiciona en un JComBoBox los departamentos de colombia
	  *  @param CDptos JComboBox que se le adicionan los departamentos
	  *  @param conMySQL Conexion a la base de datos
	  */

    final static public void traerDepatamentos(JComboBox CDptos,ConectarMySQL conMySQL)  {
		
  	  if (departamentos == null) {
	  
	      departamentos = new String[33][4];
	      
	      
	      departamentos[0][0] = "28";
	      departamentos[0][1] = "AMAZONAS";
	      departamentos[0][2] = "91001000";
	      departamentos[0][3] = "LETICIA";
	      
	      departamentos[1][0] = "1";
	      departamentos[1][1] = "ANTIOQUIA";
	      departamentos[1][2] = "5001000";
	      departamentos[1][3] =	"MEDELLIN";	
  	      
  	      departamentos[2][0] = "24";
	      departamentos[2][1] = "ARAUCA";
	      departamentos[2][2] = "81001000";
	      departamentos[2][3] = "ARAUCA";
  	      
  	      departamentos[3][0] = "2";
	      departamentos[3][1] = "ATLANTICO";
	      departamentos[3][2] = "8001000";
	      departamentos[3][3] =	"BARRANQUILLA";
	      
	      
	      departamentos[4][0] = "3";
	      departamentos[4][1] = "BOLIVAR";
	      departamentos[4][2] = "13001000";
	      departamentos[4][3] =	"CARTAGENA";
	      
	      
	      departamentos[5][0] = "4";
	      departamentos[5][1] = "BOYACA";
	      departamentos[5][2] = "15001000";
	      departamentos[5][3] =	"TUNJA";
	      
	      
	      departamentos[6][0] = "5";
	      departamentos[6][1] = "CALDAS";
	      departamentos[6][2] = "17001000";
	      departamentos[6][3] =	"MANIZALES";
	      
	      
	      departamentos[7][0] = "6";
	      departamentos[7][1] = "CAQUETA";
	      departamentos[7][2] = "18001000";
	      departamentos[7][3] =	"FLORENCIA";
	      
	      departamentos[8][0] = "25";
	      departamentos[8][1] = "CASANARE";
	      departamentos[8][2] = "85001000";
	      departamentos[8][3] = "YOPAL";
	      
	      
	      departamentos[9][0] = "7";
	      departamentos[9][1] = "CAUCA";
	      departamentos[9][2] = "19001000";
	      departamentos[9][3] =	"POPAYAN";
	      
	      
	      departamentos[10][0] = "8";
	      departamentos[10][1] = "CESAR";
	      departamentos[10][2] = "20001000";
	      departamentos[10][3] = "VALLEDUPAR";
	      
	      departamentos[11][0] = "11";
	      departamentos[11][1] = "CHOCO";
	      departamentos[11][2] = "27001000";
	      departamentos[11][3] = "QUIBDO";
	      	
	      departamentos[12][0] = "9";
	      departamentos[12][1] = "CORDOBA";
	      departamentos[12][2] = "23001000";
	      departamentos[12][3] = "MONTERIA";
	      
	      departamentos[13][0] = "10";
	      departamentos[13][1] = "CUNDINAMARCA";
	      departamentos[13][2] = "11001000";
	      departamentos[13][3] = "BOGOTA";
	      
	      
	      departamentos[14][0] = "29";
	      departamentos[14][1] = "GUAINIA";
	      departamentos[14][2] = "94001000";
	      departamentos[14][3] = "INIRIDA";
	      
	      
	      departamentos[15][0] = "13";
	      departamentos[15][1] = "GUAJIRA";
	      departamentos[15][2] = "44001000";
	      departamentos[15][3] = "RIOHACHA";
	      
	      
	      departamentos[16][0] = "30";
	      departamentos[16][1] = "GUAVIARE";
	      departamentos[16][2] = "95001000";
	      departamentos[16][3] = "SAN JOSE DEL GUAVIARE";
	      
	      
	      departamentos[17][0] = "12";
	      departamentos[17][1] = "HUILA";
	      departamentos[17][2] = "41001000";
	      departamentos[17][3] = "NEIVA";
	      
	      
	      departamentos[18][0] = "14";
	      departamentos[18][1] = "MAGDALENA";
	      departamentos[18][2] = "47001000";
	      departamentos[18][3] = "SANTA MARTA";
	      
	      
	      departamentos[19][0] = "15";
	      departamentos[19][1] = "META";
	      departamentos[19][2] = "50001000";
	      departamentos[19][3] = "VILLAVICENCIO";
	      
	      
	      departamentos[20][0] = "16";
	      departamentos[20][1] = "NARIÑO";
	      departamentos[20][2] = "52001000";
	      departamentos[20][3] = "PASTO";
	      
	      
	      departamentos[21][0] = "17";
	      departamentos[21][1] = "NORTE DE SANTANDER";
	      departamentos[21][2] = "54001000";
	      departamentos[21][3] = "CUCUTA";
	      
	      
	      departamentos[22][0] = "26";
	      departamentos[22][1] = "PUTUMAYO";
	      departamentos[22][2] = "86001000";
	      departamentos[22][3] = "MOCOA";
	      
	      
	      departamentos[23][0] = "18";
	      departamentos[23][1] = "QUINDIO";
	      departamentos[23][2] = "63001000";
	      departamentos[23][3] = "ARMENIA";
	     
	      
	      departamentos[24][0] = "19";
	      departamentos[24][1] = "RISARALDA";
	      departamentos[24][2] = "66001000";
	      departamentos[24][3] = "PEREIRA";
	      
	      
	      departamentos[25][0] = "27";
	      departamentos[25][1] = "SAN ANDRES";
	      departamentos[25][2] = "88001000";
	      departamentos[25][3] = "SAN ANDRES";
	      
	      
	      departamentos[26][0] = "20";
	      departamentos[26][1] = "SANTANDER";
	      departamentos[26][2] = "68001000";
	      departamentos[26][3] = "BUCARAMANGA";
	      
	      
	      departamentos[27][0] = "21";
	      departamentos[27][1] = "SUCRE";
	      departamentos[27][2] = "70001000";
	      departamentos[27][3] = "SINCELEJO";
	      
	      
	      departamentos[28][0] = "22";
	      departamentos[28][1] = "TOLIMA";
	      departamentos[28][2] = "73001000";
	      departamentos[28][3] = "IBAGUE";
	      
	      
	      departamentos[29][0] = "23";
	      departamentos[29][1] = "VALLE";
	      departamentos[29][2] = "76001000";
	      departamentos[29][3] = "CALI";
	      
	      
	      departamentos[30][0] = "31";
	      departamentos[30][1] = "VAUPES";
	      departamentos[30][2] = "97001000";
	      departamentos[30][3] = "MITU";
	      
	      
	      departamentos[31][0] = "32";
	      departamentos[31][1] = "VICHADA";
	      departamentos[31][2] = "99001000";
	      departamentos[31][3] = "PUERTO CARRENO";
	      
	      departamentos[32][0] = "0";
	      departamentos[32][1] = "NO ESPECIFICADA";
	      departamentos[32][2] = "0";
	      departamentos[32][3] = "NO ESPECIFICADO";
	      
	      
  	  }  
 	  
 	  for (int i = 0 ; i < departamentos.length; i++)
 	      CDptos.addItem(departamentos[i][1]);
 	      
 	
 	}
    //*************************************************************************************************************
    
 	public final Vector buscarMunicipio(ConectarMySQL conMySQL,String codMunicipio)  {
		 
		  Vector < String > resultadoVector = null;

		  String  sentenciaSQL =  "Select D.NombreDpto,M.Municipio "+
 		                          "From Departamentos D , MunicipiosCorregimientos M "+
 		                          "Where D.CodigoDpto = M.CodigoDpto and M.DivisionPolitica = '"+ codMunicipio  + "'" +
 		                          " Order by Municipio" ;  
 		
 		
 		   
 		 try {
 		 	
 		 	     // Se realiza la consulta en la base de datos
 		     ResultSet resultadoMunicipio = conMySQL.buscarRegistro(sentenciaSQL);
 		    
 		 
 	
 		     // Se extraen los registros
 		     if  (resultadoMunicipio.next()) {
 		     
 		             resultadoVector = new Vector < String >();
 		             resultadoVector.addElement(resultadoMunicipio.getString(1));
 		             resultadoVector.addElement(resultadoMunicipio.getString(2));
 		                	
 		             
 		     }       
 		        
 		   } catch (Exception e) {
 		 	
 		 	  Mensaje("Error al buscar municipio"+e,"Error ",JOptionPane.ERROR_MESSAGE);	       
	       }
	       
	       return resultadoVector;
 	}
  	
  	
   /**
	  *  Metodo que deveulve un array de los diferentes corregimientos
	  *  @return JFrame configurado
	  *  
	  */

       
   final public String[][] buscarCorregimiento(ConectarMySQL conMySQL,String departamento, String municipio,String corregimiento)  {
		 
	 String[][] resultado = null;

	 String  sentenciaSQL = "Select DIVCODIGMUNICIPIO,DIVIPOLI,Municipio "+
 		                    "From TTAMDIVI "+
 		                    "Where DICCODIG = "+departamento +
 		                    " and DIVCODIGMUNICIPIO like '%" + municipio +"%' "+ 
 		                    "and DIVIPOLI = " + corregimiento;
 		                    
 		
 		   
 		 try {
 		 	
 		 	     // Se realiza la consulta en la base de datos
 		     ResultSet resultadoCorregimiento = conMySQL.buscarRegistro(sentenciaSQL);
 		    
 		 
 	
 		     // Se extraen los registros
 		     if  (resultadoCorregimiento.next()) {
 		             
 		             resultado = new String[1][3];
 		             	
 		             resultado[0][0] = resultadoCorregimiento.getString(1);
 		             resultado[0][1] = resultadoCorregimiento.getString(2);
 		             resultado[0][2] = resultadoCorregimiento.getString(3);
 		            
 		     }   
 		     	
 		     	
 		   } catch (Exception e) {
 		 	
 		 	  Mensaje("Error al buscar corregimiento" ,"Error ",JOptionPane.ERROR_MESSAGE);	       
	       }
	       
	       return resultado;
 	}
 	
 	
 	public String getCodigoDivisionPolitica(ConectarMySQL conMySQL) {
 		
 		if (codigoDptoDivisionPolitica == null)
 	
 	        codigoDivisionPolitica = "70713000";
 		     
 		return  codigoDivisionPolitica;    
 		
 	}
    
    
    
    public String getDptoDivisionPolitica(ConectarMySQL conMySQL) {
 		
 		if (dptoDivisionPolitica == null)
 		  
 		    dptoDivisionPolitica = "SUCRE";
 		     
 		return  dptoDivisionPolitica;    
 		
 	}
    
    
    
    public String getCodigoDptoDivisionPolitica(ConectarMySQL conMySQL) {
 		
 		if (dptoDivisionPolitica == null)
 	      
 	      codigoDptoDivisionPolitica = "21";
 		     
 		return  codigoDptoDivisionPolitica;    
 		
 	}
    
    
    
    public String getMunicipioDivisionPolitica(ConectarMySQL conMySQL) {
 		
 		if (municipioDivisionPolitica == null)
 		    
 		     municipioDivisionPolitica = "SAN ONOFRE";
 		     
 		return  municipioDivisionPolitica;    
 		
 	}
   
   
   
    public JDialog getDialogoListaValores() {
 		
 		return  dialogoListaValores;
 	}
 	
 	public JDialog[] getDialogoArrayListaValores() {
 		
 		return  dialogoArrayListaValores;
 	}
 	
 	
 	public ListaValor getListaValores(String sentencia,Object [][] camposRetornos,
 	                                  CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL,
 	                                  int indiceArrayDialogo,int tamañoArrayDialogo,int[] columnasOcultar) {
 		
 		//Se instancia la clase que valida los eventos en el campo de la lista
 		ListaValor listaValores = new ListaValor(sentencia,camposRetornos,crearJFrame,campoLista,conectarMySQL,0,
 		                                         indiceArrayDialogo,tamañoArrayDialogo,columnasOcultar); 
 		                                        
 		
 		return listaValores;
 	}
 	
 	
 	
 	public ListaValor getListaValores(String sentencia,Object [][] camposRetornos,
 	                                  CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL) {
 		
 		//Se instancia la clase que valida los eventos en el campo de la lista
 		ListaValor listaValores = new ListaValor(sentencia,camposRetornos,crearJFrame,campoLista,conectarMySQL,0); 
 		                                        
 		
 		return listaValores;
 	}
 	
 	
 	public ListaValor getListaValores(String sentencia,Object [][] camposRetornos,
 	                                  CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL, int ancho) {
 		
 		//Se instancia la clase que valida los eventos en el campo de la lista
 		ListaValor listaValores = new ListaValor(sentencia,camposRetornos,crearJFrame,campoLista,conectarMySQL,ancho); 
 		                                        
 		
 		return listaValores;
 	}
 	
 	public ListaValor getListaValores(String sentencia,Object [][] camposRetornos,
 	                                  CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL, int[] ocultarColumnas) {
 		
 		//Se instancia la clase que valida los eventos en el campo de la lista
 		ListaValor listaValores = new ListaValor(sentencia,camposRetornos,crearJFrame,campoLista,conectarMySQL,ocultarColumnas,0); 
 		                                        
 		
 		return listaValores;
 	}
 	
 	
 	public ListaValor getListaValores(String sentencia,Object [][] camposRetornos,
 	                                  CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL, int[] ocultarColumnas,int ancho) {
 		
 		//Se instancia la clase que valida los eventos en el campo de la lista
 		ListaValor listaValores = new ListaValor(sentencia,camposRetornos,crearJFrame,campoLista,conectarMySQL,ocultarColumnas,ancho); 
 		                                        
 		
 		return listaValores;
 	}
 	
 	
 	
 	public ListaValor getListaValores(String sentencia,Object [][] camposRetornos,
 	                                  CrearJFrame crearJFrame,JTextField campoLista,JComboBox jComboBox,ConectarMySQL conectarMySQL, int[] ocultarColumnas,int ancho) {
 		
 		//Se instancia la clase que valida los eventos en el campo de la lista
 		ListaValor listaValores = new ListaValor(sentencia,jComboBox,camposRetornos,crearJFrame,campoLista,conectarMySQL,ocultarColumnas,ancho); 
 		                                        
 		
 		return listaValores;
 	}
    /**
	  *  Metodo que deveulve el JFrame configurado
	  *  @return JFrame configurado
	  *  
	  */

 	public JFrame getJFrame() {
 		
 		return frame;
 	}
 	
 	
 	public ImageIcon getImagenEscalada(int ancho,int alto,String path) {
 		
		ImageIcon imagen = new ImageIcon(path);
	    Image escala = imagen.getImage(); //Se adecua la imagen a la escala del JLabel
	    escala = escala.getScaledInstance(ancho,alto,Image.SCALE_AREA_AVERAGING);
	    imagen.setImage(escala);
	    
	    return imagen;
    }
 	
 	//*************************************************************************
 	
    public DefaultCellEditor getNumerosEditor(JTable tabla) {
   
        final ConvertirNumColumn  integerField = new ConvertirNumColumn(getVisualAtributoGanaFocoComponentes());
       
         
          DefaultCellEditor integerEditor = 
            new DefaultCellEditor(integerField) {
            	
            static final long serialVersionUID = 19781208;

                
                // Se retorna el numero
                public Object getCellEditorValue() {
                	
                    return integerField.getText();
                }
            };
        
           
           return integerEditor;
        
 	}
     
    
    //*************************************************************************
    
    public  DefaultCellEditor getEditor(JTextField jTextField) {
  	       
         //Se configura el editor para mayusculas
        
        final EditorJTable editorJTable = new EditorJTable(jTextField);
        
        return editorJTable;
     } 	  
 
    //**********************************************************************************
  
    public void focusGained(FocusEvent F) {
 	
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
    }
 	
  
  //**********************************************************************************
  
  public void focusLost(FocusEvent F) {
 	    // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());
 		
  }
  
    
}