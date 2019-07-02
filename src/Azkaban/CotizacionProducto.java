package com.JASoft.azkaban;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.KeyboardFocusManager;


import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JCheckBox;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.JFormattedTextField;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;


import java.text.NumberFormat;
import java.text.DecimalFormat;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.util.Vector;

import java.sql.ResultSet;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.Calendario;

final public class CotizacionProducto extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpTodos;
    private  JPanel jpProductos;
    private  JPanel jpVentas;
    
    //Se delcaran los JTextField
    private  JTextField jtCodigo;
    private  JTextField jtDescripcion;
    private  JTextField jtTotalUnidades;
    private  JTextField jtTotalVentas;
   
    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
    //** Se declaran los JTable
    private  JTable tablaVentasProducto;
    
    //** Se declaran los JLabel
    private JLabel jlCodigo;
    private JLabel jlDescripcion;
    private JLabel jlTotalVentas;
    private JLabel jlTotalUnidades;
    
    
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
    
    //** NumberFormat
    private NumberFormat formatter;
    
    //JTabbedPane
    private JTabbedPane pestaña;
    
     
   //JDialog
   private JDialog dialogCotizacionesDetalle;
   
   //JButton
   private JButton jbCerrar;
  
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Numero Cotización","Fecha","Código","Producto","Cantidad","Total"};
    private Object [][] datos = new Object[16][6];
    
    //Vectores
    private Vector columnas;
    
    //Modelo de datos 
    private SortableTableModel dm;
   
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
     
    //** Constructor General 
    public CotizacionProducto(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Cotizaciones Por Producto","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;

     //** Se agregan los JPanel

      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(10,45,780,500);
      
      jpTodos = getJPanel(0,0,100,100);
      pestaña.addTab("Todos   ",jpTodos);
      
      jpProductos = getJPanel(0,0,100,100);
      pestaña.addTab("Productos  ",jpProductos);

      jpVentas = getJPanel("Ventas Realizadas",40, 60, 700, 350,14);
      jpTodos.add(jpVentas);
      
       pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 0) {
      		  	
      		  
      		   jpTodos.add(jpVentas); 
      		   jlTotalUnidades.setVisible(false);
               jtTotalUnidades.setVisible(false);
         			
      		   
      		   traerInformacion();
      			
      			tablaVentasProducto.getColumnModel().getColumn(2).setPreferredWidth(75);
	            tablaVentasProducto.getColumnModel().getColumn(2).setMinWidth(15);
	            tablaVentasProducto.getColumnModel().getColumn(2).setMaxWidth(2147483647);
	        
	            tablaVentasProducto.getColumnModel().getColumn(3).setPreferredWidth(75);
	            tablaVentasProducto.getColumnModel().getColumn(3).setMinWidth(15);
	            tablaVentasProducto.getColumnModel().getColumn(3).setMaxWidth(2147483647);
	        
                   
		        ocultarColumnas(tablaVentasProducto,0);
		        ocultarColumnas(tablaVentasProducto,1);
		        
		        jpTodos.add(jpVentas);
		        
		        configurarColumnas();
      			
     
      			
      		} else {
      		
      		 
               jtTotalUnidades.setText("");
	           jtTotalVentas.setText("");
	           jlTotalUnidades.setVisible(true);
               jtTotalUnidades.setVisible(true);
    
	           
	           	tablaVentasProducto.getColumnModel().getColumn(0).setPreferredWidth(75);
	            tablaVentasProducto.getColumnModel().getColumn(0).setMinWidth(15);
	            tablaVentasProducto.getColumnModel().getColumn(0).setMaxWidth(2147483647);
	        
	            tablaVentasProducto.getColumnModel().getColumn(1).setPreferredWidth(75);
	            tablaVentasProducto.getColumnModel().getColumn(1).setMinWidth(15);
	            tablaVentasProducto.getColumnModel().getColumn(1).setMaxWidth(2147483647);
	        
	           ocultarColumnas(tablaVentasProducto,2);
		       ocultarColumnas(tablaVentasProducto,3);
	           
	           jpProductos.add(jpVentas);
		       
               dm.setDataVector(datos,nombresColumnas);
                
      				
      		} 
      		
  		
            
      			
      	}
      	
      	
      });
      
      getJFrame().add(pestaña); 
	  
    
      //** Se agregan los JTable

      // se configura el modelo para la tabla
      dm = new SortableTableModel() {

          //Se especifica el serial para la serializacion
          static final long serialVersionUID = 19781212;

          public Class getColumnClass(int col) {

              return String.class;

          }

         public boolean isCellEditable(int row, int col) {

           
               return false;  

         }

      };

     dm.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla

     tablaVentasProducto = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaVentasProducto.addFocusListener(this);
  
    
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaVentasProducto);
      scrollPane.setBounds(25, 40, 655, 276);
      jpVentas.add(scrollPane);
       
       
     
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
    
      columnas = new Vector();
      columnas.add("NumeroCotizacion");
      columnas.add("Fecha");
      columnas.add("Codigo");
      columnas.add("Productos");
      columnas.add("Cantidad");
      columnas.add("Total");
      
     
      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
         
      
   
       //Se agregan los JLabel
      jlCodigo = getJLabel("Código:",280,20, 50, 20);
      jpProductos.add(jlCodigo);
      
      jlDescripcion = getJLabel("Descripción:",430, 20, 80, 20);
      jpProductos.add(jlDescripcion);
      
      jlTotalUnidades  = getJLabel("Unidades:",30, 320, 80, 20);
      jpVentas.add(jlTotalUnidades);
      
      jlTotalVentas  = getJLabel("Total:",230, 320, 80, 20);
      jpVentas.add(jlTotalVentas);
      
      
      
      jtCodigo = getJTextField("",320, 20, 105, 20,"Digíte el Número de Identificación del Cliente","12");
      jtCodigo.setHorizontalAlignment(JTextField.RIGHT);
      jtCodigo.addKeyListener(getValidarEntradaNumeroJTextField());
      jtCodigo.addFocusListener(this);
      jpProductos.add(jtCodigo);
      
      jtTotalUnidades  = getJTextField("Total Unidades Vendidas",100, 320, 80, 20,"",false);
      jtTotalUnidades.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalUnidades);
      
      jtTotalVentas  = getJTextField("Total Vendidas",290, 320, 80, 20,"",false);
      jtTotalVentas.setHorizontalAlignment(JTextField.RIGHT);
      jtTotalVentas.setVisible(false);
      jpVentas.add(jtTotalVentas);
     
      
   
      

      jtDescripcion = getJTextField("",560, 20, 200, 20,"Digite el Nombre del Cliente o Presione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtDescripcion,conectarMySQL);
      
      
      //jtRazonSocial.addKeyListener(listaValor);
      jtDescripcion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtDescripcion.addKeyListener(listaValor);
      jtDescripcion.addFocusListener(this);
      jtDescripcion.addActionListener(this);
      jpProductos.add(jtDescripcion);
      
      jcPeriodos =  getJComboBox(20,20,120,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Dia");
      jcPeriodos.addItem("Quincena");
      jcPeriodos.addItem("Mes");
      jcPeriodos.addItem("Trimestre");
      jpProductos.add(jcPeriodos);
     
      formatter = new DecimalFormat("#,###,###");

      traerInformacion();

     
    
    
    }
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaVentasProducto.getColumnModel().getColumn(0).setPreferredWidth(5);
		tablaVentasProducto.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaVentasProducto.getColumnModel().getColumn(1).setPreferredWidth(60);
		tablaVentasProducto.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		tablaVentasProducto.getColumnModel().getColumn(2).setPreferredWidth(70);
		tablaVentasProducto.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
		tablaVentasProducto.getColumnModel().getColumn(3).setCellRenderer(getAlinearIzquierda());
		tablaVentasProducto.getColumnModel().getColumn(3).setPreferredWidth(250);
		tablaVentasProducto.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());	
		tablaVentasProducto.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());	
		

	}
    //******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		String sentencia = "Select IdProducto,Descripcion "+
		                   "From   Productos "+
		                   "Where Descripcion like '";

		return sentencia;


	}
	
	

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[1][5];
		
		objetosRetorno[0][0] = jtDescripcion;
        objetosRetorno[0][1] = tablaVentasProducto; 
       	objetosRetorno[0][2] = "1";
       	objetosRetorno[0][3] = jtCodigo;
       	objetosRetorno[0][4] = "0";


		return objetosRetorno;

	}
	
	
    //*********************** Metodo limpiar ************************

    private void limpiar() { 

        if (pestaña.getSelectedIndex() == 0) //Comodin para que se dispare el stateChange
	       
	         pestaña.setSelectedIndex(1);
	       
	         pestaña.setSelectedIndex(0);
      
      
      
      
    }
    
   
    
     
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        String sentenciaSQL = "";
       
            if (pestaña.getSelectedIndex() == 0)
         
         
         
	            sentenciaSQL    =   "Select  P.IdProducto,P.Descripcion,Sum(V.Cantidad),Sum(V.subtotal) "+
	                                "From CotizacionesEncabezado VE,CotizacionesDetalle V, Productos P "+ 
	                                "Where VE.NumeroCotizacion = V.NumeroCotizacion  and V.IdProducto = P.IdProducto "+
	                                "Group by P.IdProducto, P.Descripcion"; 
                              
         	else
               
               if (jcPeriodos.getSelectedIndex() == 0)
                
	                 sentenciaSQL    =   "Select  V.NumeroCotizacion,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal "+
		                                 "From CotizacionesEncabezado VE,CotizacionesDetalle V, Productos P "+ 
		                                 "Where VE.NumeroCotizacion = V.NumeroCotizacion  and"+
		                                 "                        V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'";
		                                 
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                
	                 sentenciaSQL    =   "Select  V.NumeroCotizacion,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal "+
		                                 "From CotizacionesEncabezado VE,CotizacionesDetalle V, Productos P "+ 
		                                 "Where VE.NumeroCotizacion = V.NumeroCotizacion  and"+
		                                 "                       V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'"+
		                                 "                       and Substr(VE.Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'";
		                                 
	                
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
        	                  
		                       
				                 sentenciaSQL    =  "Select  V.NumeroCotizacion,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal "+
					                                "From CotizacionesEncabezado VE,CotizacionesDetalle V, Productos P "+ 
					                                "Where VE.NumeroCotizacion = V.NumeroCotizacion  and"+
					                                "                       V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'"+
					                                "                       and substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
							                        "                       and Substr(VE.Fecha,1,10) " + condicion;
				     } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                
					                 sentenciaSQL    =  "Select  V.NumeroCotizacion,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal "+
						                                "From CotizacionesEncabezado VE,CotizacionesDetalle V, Productos P "+ 
						                                "Where VE.NumeroCotizacion = V.NumeroCotizacion  and"+
						                                "                       V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'"+
						                                "                       and substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'";
						                                
						       else                         
					                
					                 if (jcPeriodos.getSelectedIndex() == 4) {
					                 
			                                   String condicion = "";
			                                   int numeroMes =  Integer.parseInt(getObtenerMes(conectarMySQL));
				        	                    
				        	                   if (numeroMes >= 1 && numeroMes <=3)
				        	                    
				        	                        condicion =  "'" + getObtenerAño(conectarMySQL) + "-01' and '" + getObtenerAño(conectarMySQL) +"-03'";  //se obtiene la fecha hasta el valor del trimeste
				        	                   
				        	                   else
				        	                     
				        	                     if (numeroMes >= 4 && numeroMes <= 6)
				        	                                
				        	                         condicion =  "'" + getObtenerAño(conectarMySQL) + "-04' and '" + getObtenerAño(conectarMySQL) +"-06'";  //se obtiene la fecha hasta el valor del trimeste
				        	         
				        	                      else
				        	                     
					        	                     if (numeroMes >= 7 && numeroMes <= 9)
					        	                                
					        	                         condicion =  "'" + getObtenerAño(conectarMySQL) + "-07' and '" + getObtenerAño(conectarMySQL) +"-09'";  //se obtiene la fecha hasta el valor del trimeste
					        	                      
					        	                      else   
					        	                        
					        	                         condicion =   "'" +getObtenerAño(conectarMySQL) + "-10' and '" + getObtenerAño(conectarMySQL) +"-12'";  //se obtiene la fecha hasta el valor del trimeste
					        	                      
			        	                      
						                                 sentenciaSQL    =  	"Select  V.NumeroCotizacion,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal "+
												                                "From CotizacionesEncabezado VE,CotizacionesDetalle V, Productos P "+ 
												                                "Where VE.NumeroCotizacion = V.NumeroCotizacion  and"+
												                                "                       V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'"+
												                                "                       and substr(VE.Fecha,1,7) between " + condicion;
													          
		                             }
		                             
		                         
	   
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	{ 

              
              Vector <Vector > filas = new Vector <Vector>();
              long cantidades = 0;
              long totales = 0;
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
			     if (pestaña.getSelectedIndex() == 0) {
			     
			        columnas.add("");
			        columnas.add("");
			        columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	
			    	Long subTotal = resultado.getLong(4);
			    	
	                columnas.add(formatter.format(subTotal));
	                
	                totales += subTotal;
			    
			     } else {
			    	
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	
			    	Long cantidad = resultado.getLong(5);
			    	Long subTotal = resultado.getLong(6);
			    	
	                columnas.add(formatter.format(cantidad));
	                columnas.add(formatter.format(subTotal));
                    
                    cantidades += cantidad;
                    totales += subTotal;

			    	
			    }
			    	
			     filas.add(columnas);	
		    				
			  }
	           
	           
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 
			 	 if (pestaña.getSelectedIndex() == 1) {
				 	 
				 	 jtTotalUnidades.setText(String.valueOf(cantidades));
				 	 jtTotalVentas.setText(formatter.format(totales));
			 	 }
			 	 
			 	 resultadoBoolean = true;
		 	 
			 
			 } else {
			 	
			     if (pestaña.getSelectedIndex() == 0)
			 	
			 	 
			 	 	Mensaje(" No existen facturas asociadas ","Información",2);
			 
			 
			 	else {
			 	     
			 	    jtCodigo.setText("");
			 	    jtDescripcion.setText("");
			 	  	Mensaje(" Este Productos no tiene facturas asociadas ","Información",2);
			 	  	jtCodigo.grabFocus();
			    }  
			 	
			 } 
		 	      
		 	   	
          } 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultadoBoolean;
    }
	
		//*****************************Metodo traerInformacionProducto() ******************************************
    
    private boolean traerInformacionProducto() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select Descripcion "+
                              "From   Productos "+
                              "Where  IdProducto = '" + jtCodigo.getText() + "'";
       
        	   
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 


	             if (resultado.next()) { 
	
	                  jtDescripcion.setText(resultado.getString(1));
	                
		 	   	      resultadoBoolean = true;
		 	   	}   
		 	   	
          } 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultadoBoolean;
    }
	
      
      //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

         // Se verifica el componente que genero la acccion

         if (fuente == Blimpiar) {

            limpiar();

         } else 

            if (fuente == Bbuscar) { 

               traerInformacion();

            } else 

               if (fuente == Bsalir) {

                 ocultarJFrame(); 

               }  else 
			 		   	if (fuente == jbCerrar) {
                         
                               dialogCotizacionesDetalle.setVisible(false);
                             
                          } else
                          
                             if (fuente == jtDescripcion)  {
				 	             	     			
	 	     			    	 if (getJTableListaValores().isVisible()) {
	 	     			    	 						 		   	  	     
				 		   	  	      if (getJTableListaValores().getSelectedRow() > -1)
				 		   	  	  	     
				 		   	  	  	      getJTableListaValores().setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
				 		   	  	  	  
				 		   	  	  	  else
				 		   	  	  	  
				 		   	  	  	     getJTableListaValores().setRowSelectionInterval(0,0);
				 		   	  	  	     
				 		   	  	  	     
				 		   	  	  	 getJTableListaValores().grabFocus();
				 		   	  	  
				 		   	  	  }
				 		   	  } else
				 		   	     
				 		   	     if (fuente == jcPeriodos) {
				 		   	     	 
				 		   	     	 dm.setDataVector(datos,nombresColumnas);
				 		   	     	 jtCodigo.setText("");
				 		   	     	 jtDescripcion.setText("");
				 		   	     	 
				 		   	     }
      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
        
         if (f.getComponent() == jtDescripcion && (getDialogoListaValores() == null || !getDialogoListaValores().isVisible()) && jtDescripcion.isFocusable() &&   mostrarListaAutomatica) {
            	
             listaValor.mostrarListaValores();
	
		} else
		
			 if (f.getComponent() != jtDescripcion  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
                     
                  jtCodigo.setText(getJTableListaValores().getValueAt(0,0).toString());
	              jtDescripcion.setText(getJTableListaValores().getValueAt(0,1).toString());
	             
	              getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
                  jtDescripcion.selectAll();
		
			 } else
		   
				   if (f.getComponent() == tablaVentasProducto && !jtDescripcion.getText().isEmpty())
				      
				       traerInformacion();
				       
		        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
        if (f.getComponent() == jtCodigo && !jtCodigo.getText().isEmpty()) {
        	
        	if (!traerInformacionProducto()) {
        		
        		mostrarListaAutomatica = false;
        	    Mensaje("Identificación " +  jtCodigo.getText( ) + " no registrada","Información",2);
        	    mostrarListaAutomatica = true;
        		jtCodigo.setText("");
        		
        	} else {
                
                mostrarListaAutomatica = false;
        	    tablaVentasProducto.grabFocus();
        	    tablaVentasProducto.setRowSelectionInterval(0,0);
        	    traerInformacion();
        		
        	    
        	}    
        	
        }
        
        
        

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

}