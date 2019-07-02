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


import java.text.NumberFormat;
import java.text.DecimalFormat;

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

final public class UtilidadPeriodo extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpCriterios;
    private  JPanel jpVentas;
    
    //Se declaran los JTextField
    private  JTextField jtTotalVentas;
    private  JTextField jtGanancias;
   
      //** Se declaran los JCheckBox
    private JRadioButton jrTodos;
    private JRadioButton jrDia;
    private JRadioButton jrQuincena;
    private JRadioButton jrMes;
    private JRadioButton jrTrimestre;
        
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
   
    //** Se declaran los JTable
    private  JTable tablaVentasProducto;
    
    
    //** NumberFormat
    private NumberFormat formatter;
    
   
   //JButton
   private JButton jbCerrar;
  
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Nro Venta","Fecha","Código","Producto","Cantidad","Precio Venta","Total","P. Compra","Ganancia"};
    private Object [][] datos = new Object[19][6];
    
    //Vectores
    private Vector columnas;
    
    //Modelo de datos 
    private SortableTableModel dm;
   
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
     
    //** Constructor General 
    public UtilidadPeriodo(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Utilidad Por Períodos","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;

      //** Se agregan los JPanel

      jpCriterios = getJPanel("Criterios De Busqueda",55, 45, 700, 65,14);
      
        //Se instancia un objeto de tipo ButtonGroup para agrupar los JRadioButton
      ButtonGroup grupo = new ButtonGroup();
     
      jrTodos = getJRadioButton("Todos",30, 25, 100, 20,grupo);
   	  jrTodos.setMnemonic('T');
   	  jrTodos.setSelected(true);
   	  jrTodos.addActionListener(this);
	  jpCriterios.add(jrTodos);
	  
	  jrDia = getJRadioButton("Dia",160, 25, 100, 20,grupo);
   	  jrDia.setMnemonic('D');
   	  jrDia.addActionListener(this);
	  jpCriterios.add(jrDia);
	  
	  jrQuincena = getJRadioButton("Quincena",290, 25, 100, 20,grupo);
   	  jrQuincena.setMnemonic('Q');
   	  jrQuincena.addActionListener(this);
	  jpCriterios.add(jrQuincena);
	  
	  jrMes = getJRadioButton("Mes",440, 25, 100, 20,grupo);
   	  jrMes.setMnemonic('M');
   	  jrMes.addActionListener(this);
	  jpCriterios.add(jrMes);
	  
	  
	  jrTrimestre = getJRadioButton("Trimestre",550, 25, 100, 20,grupo);
   	  jrTrimestre.setMnemonic('T');
   	  jrTrimestre.addActionListener(this);
	  jpCriterios.add(jrTrimestre);
	  
	  

      jpVentas = getJPanel("Ventas Realizadas",5, 120, 790, 420,14);
      
      
       //Se agregan los JLabel
     
      
      JLabel jlTotalVentas  = getJLabel("Total:",230, 380, 80, 20);
      jpVentas.add(jlTotalVentas);
      
      JLabel jlGanancia  = getJLabel("Ganancia:",400, 380, 80, 20);
      jpVentas.add(jlGanancia);
      
      
      
      
      jtTotalVentas  = getJTextField("Total Vendidas",290, 380, 80, 20,"",false);
      jtTotalVentas.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalVentas);
     
      jtGanancias = getJTextField("Total Ganancias",460, 380, 80, 20,"",false);
      jtGanancias.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtGanancias);
   
      
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
      scrollPane.setBounds(5, 40, 775, 323);
      jpVentas.add(scrollPane);
       
       
     
      //** Se configura el icono del frame

       getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/UtilidadesPeriodo.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame();
      
      
      
      columnas = new Vector();
      columnas.add("NumeroVenta");
      columnas.add("Fecha");
      columnas.add("Codigo");
      columnas.add("Productos");
      columnas.add("Cantidad");
      columnas.add("Precio Venta");
      columnas.add("Total");
      columnas.add("Precio Compra");
      columnas.add("Ganancia");
      
      formatter = new DecimalFormat("#,###,###");

      
      traerInformacion(); 
     
      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
     
    
    
    }
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaVentasProducto.getColumnModel().getColumn(0).setPreferredWidth(38);
		tablaVentasProducto.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
		
	    tablaVentasProducto.getColumnModel().getColumn(1).setPreferredWidth(46);
	    tablaVentasProducto.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		
		tablaVentasProducto.getColumnModel().getColumn(3).setCellRenderer(getAlinearIzquierda());
		tablaVentasProducto.getColumnModel().getColumn(3).setPreferredWidth(212);
	    tablaVentasProducto.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(4).setPreferredWidth(42);
	    tablaVentasProducto.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(5).setPreferredWidth(64);
	    tablaVentasProducto.getColumnModel().getColumn(6).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(6).setPreferredWidth(32);
	    tablaVentasProducto.getColumnModel().getColumn(7).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(7).setPreferredWidth(62);
	    tablaVentasProducto.getColumnModel().getColumn(7).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(7).setPreferredWidth(52);
	    tablaVentasProducto.getColumnModel().getColumn(8).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(8).setPreferredWidth(52);
	   
	      
	    ocultarColumnas(tablaVentasProducto,2);		
		

	}
    
    
	
    //*********************** Metodo limpiar ************************

    private void limpiar() { 

      dm.setDataVector(datos,nombresColumnas);
      
     
    }
    
   
    
     
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        limpiar();
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "";
       
            
               
               if (jrTodos.isSelected())
                
	                 sentenciaSQL    =   "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
		                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
		                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
		                                 "      V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto and VE.Fecha between P1.FechaDesde and ifnull(P1.FechaHasta,  P1.FechaDesde + INTERVAL 20 Year) ";
		                                
		                                 
		      
		      else                         
                 
                 if (jrDia.isSelected())
                 
                
                     sentenciaSQL    =   "Select  V.NumeroVenta,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
		                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
		                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
		                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
		                                 "                       and VE.Fecha between  P1.FechaDesde and ifnull(P1.FechaHasta,  P1.FechaDesde + INTERVAL 20 Year) and "+
		                                 "                       substr(VE.Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'";
		        
		                                 
	                
        	     else
        	     
        	         if (jrQuincena.isSelected()) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
			        	                  
					             sentenciaSQL    =   "Select  V.NumeroVenta,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
					                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
					                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
					                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
					                                 "                       and VE.Fecha between  P1.FechaDesde and ifnull(P1.FechaHasta,  P1.FechaDesde + INTERVAL 20 Year) and "+
					                                 "                       substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
										             "                       and Substr(VE.Fecha,1,10) " + condicion;
					          
				    } else    
        	     
		        	         if (jrMes.isSelected())
		                             
		                              
					                 sentenciaSQL    =   "Select  V.NumeroVenta,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
						                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
						                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
						                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
						                                 "                       and VE.Fecha between  P1.FechaDesde and ifnull(P1.FechaHasta,  P1.FechaDesde + INTERVAL 20 Year) and "+
						                                  "                       substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'";
						                         
						       
						       else                         
					                
					                 if (jrTrimestre.isSelected()) {
					                 
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
					        	                      
			        	                                
			        	                                    sentenciaSQL    =   "Select  V.NumeroVenta,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
												                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
												                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
												                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
												                                 "                       and VE.Fecha between  P1.FechaDesde and ifnull(P1.FechaHasta,  P1.FechaDesde + INTERVAL 20 Year) and "+
												                                 "                       substr(VE.Fecha,1,7) between " + condicion;
												       
												                                
													          
		                              } 
		                             
		                         
	 
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	{ 

              
              Vector <Vector > filas = new Vector <Vector>();
              long cantidades = 0;
              long totales = 0;
              long gananciaTotal = 0;
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
			    	
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	
			    	Long cantidad = resultado.getLong(5);
			    	columnas.add(getFormatoNumero(cantidad));
	                columnas.add(getFormatoNumero(resultado.getString(6)));
			    	Long subTotal = resultado.getLong(7);
			    	columnas.add(getFormatoNumero(subTotal));
                    columnas.add(getFormatoNumero(resultado.getString(8)));
			    	Long ganancia = resultado.getLong(9);
	                columnas.add(getFormatoNumero(ganancia));
			    	
                    cantidades += cantidad;
                    totales += subTotal;
                    gananciaTotal += ganancia;

			    	
			       filas.add(columnas);	
		    				
			  }
	           
	           
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 
		 	 	 
			 	 jtTotalVentas.setText(formatter.format(totales));
			 	 jtGanancias.setText(formatter.format(gananciaTotal));
		 	 
			 	 resultadoBoolean = true;
		 	 
			 
			 } else {
			 	     
			 	
			 	  	Mensaje(" Este período no tiene facturas asociadas ","Información",2);
			 	  
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

               } else
               
                 if (fuente == jrTodos || fuente == jrDia  || fuente == jrQuincena || fuente == jrMes || fuente == jrTrimestre)
                    
                    traerInformacion();
      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
                
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

}