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

final public class UtilidadProducto extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpCriterios;
    private  JPanel jpVentas;
    
    //Se delcaran los JTextField
    private  JTextField jtCodigo;
    private  JTextField jtDescripcion;
    private  JTextField jtTotalUnidades;
    private  JTextField jtTotalVentas;
    private  JTextField jtGanancias;
   
    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
   
    //** Se declaran los JTable
    private  JTable tablaVentasProducto;
    
   
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
    
   //JButton
   private JButton jbCerrar;
  
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Nro Venta","Fecha","Cantidad","Precio Venta","Total","Precio Compra","Ganancia"};
    private Object [][] datos = new Object[19][6];
    
    //Vectores
    private Vector columnas;
    
    //Modelo de datos 
    private SortableTableModel dm;
   
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
     
    //** Constructor General 
    public UtilidadProducto(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Utilidad Por Producto","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;

      //** Se agregan los JPanel

      jpCriterios = getJPanel("Criterios De Busqueda",55, 45, 700, 65,14);

      jpVentas = getJPanel("Ventas Realizadas",5, 120, 790, 420,14);
      
      
       //Se agregan los JLabel
      JLabel jlCodigo = getJLabel("Código:",220, 20, 50, 20);
      jpCriterios.add(jlCodigo);
      
      JLabel jlDescripcion = getJLabel("Descripción:",400, 20, 80, 20);
      jpCriterios.add(jlDescripcion);
      
      JLabel jlTotalUnidades  = getJLabel("Unidades:",95, 380, 80, 20);
      jpVentas.add(jlTotalUnidades);
      
      JLabel jlTotalVentas  = getJLabel("Total:",320, 380, 80, 20);
      jpVentas.add(jlTotalVentas);
      
      JLabel jlGanancia  = getJLabel("Ganancia:",585, 380, 80, 20);
      jpVentas.add(jlGanancia);
      
      
      
      jtCodigo = getJTextField("",270, 20, 105, 20,"Digíte el Número de Identificación del Cliente","12");
      jtCodigo.setHorizontalAlignment(JTextField.RIGHT);
      jtCodigo.addKeyListener(getValidarEntradaNumeroJTextField());
      jtCodigo.addFocusListener(this);
      jpCriterios.add(jtCodigo);
      
      jtTotalUnidades  = getJTextField("Total Unidades Vendidas",160, 380, 90, 20,"",false);
      jtTotalUnidades.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalUnidades);
      
      jtTotalVentas  = getJTextField("Total Vendidas",360, 380, 150, 20,"",false);
      jtTotalVentas.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalVentas);
     
      jtGanancias = getJTextField("Total Ganancias",650, 380, 125, 20,"",false);
      jtGanancias.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtGanancias);
   
      jtDescripcion = getJTextField("",490, 20, 200, 20,"Digite el Nombre del Cliente o Presione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtDescripcion,conectarMySQL);
      
      
      //jtRazonSocial.addKeyListener(listaValor);
      jtDescripcion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtDescripcion.addKeyListener(listaValor);
      jtDescripcion.addFocusListener(this);
      jtDescripcion.addActionListener(this);
      jpCriterios.add(jtDescripcion);
      
      jcPeriodos =  getJComboBox(50,20,120,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Dia");
      jcPeriodos.addItem("Quincena");
      jcPeriodos.addItem("Mes");
      jcPeriodos.addItem("Trimestre");
      jcPeriodos.addActionListener(this);
      jpCriterios.add(jcPeriodos);
     
    
    
      //** Se agregan los JTable

      // se configura el modelo para la tabla
      dm = new SortableTableModel() {

          //Se especifica el serial para la serializacion
          static final long serialVersionUID = 19781212;

          public Class getColumnClass(int col) {

              return String.class;

          }

         public boolean isCellEditable(int row, int col) {
           
               return true;  

         }

      };

     dm.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla

     tablaVentasProducto = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaVentasProducto.addFocusListener(this);
  
    
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaVentasProducto);
      scrollPane.setBounds(5, 40, 775, 324);
      jpVentas.add(scrollPane);
       
       
     
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
      
      
      jtCodigo.grabFocus();
      
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

		
		tablaVentasProducto.getColumnModel().getColumn(0).setPreferredWidth(43);
	    tablaVentasProducto.getColumnModel().getColumn(1).setPreferredWidth(53);
		tablaVentasProducto.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
		tablaVentasProducto.getColumnModel().getColumn(2).setPreferredWidth(64);
		tablaVentasProducto.getColumnModel().getColumn(2).setCellRenderer(getAlinearDerecha());
		tablaVentasProducto.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
		tablaVentasProducto.getColumnModel().getColumn(4).setPreferredWidth(121);
		tablaVentasProducto.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(5).setPreferredWidth(116);
	    tablaVentasProducto.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
		tablaVentasProducto.getColumnModel().getColumn(6).setPreferredWidth(95);
		tablaVentasProducto.getColumnModel().getColumn(6).setCellRenderer(getAlinearDerecha());
				

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

      dm.setDataVector(datos,nombresColumnas);
      
      jtCodigo.setText("");
	  jtDescripcion.setText("");
	  
      
    }
    
   
    
     
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "";
       
            
               
               if (jcPeriodos.getSelectedIndex() == 0)
                
	                 sentenciaSQL    =   "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
		                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
		                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
		                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
		                                 "                       and VE.Fecha between  P1.FechaDesde and P1.FechaHasta and "+
		                                 "                        V.IdProducto = '"+ jtCodigo.getText() +"'";
		                                 
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                 
                
                     sentenciaSQL    =   "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
		                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
		                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
		                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
		                                 "                       and VE.Fecha between  P1.FechaDesde and P1.FechaHasta and "+
		                                 "                        V.IdProducto = '"+ jtCodigo.getText() +"'"+
		                                 "                       and Substr(VE.Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'";
		        
		                                 
	                
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
			        	                  
					             sentenciaSQL    =   "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
					                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
					                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
					                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
					                                 "                       and VE.Fecha between  P1.FechaDesde and P1.FechaHasta and "+
					                                 "                        V.IdProducto = '"+ jtCodigo.getText() +"'" +
					                                 "                       and substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
										             "                       and Substr(VE.Fecha,1,10) " + condicion;
					          
				    } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                             
		                              
					                 sentenciaSQL    =   "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
						                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
						                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
						                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
						                                 "                       and VE.Fecha between  P1.FechaDesde and P1.FechaHasta and "+
						                                 "                        V.IdProducto = '"+ jtCodigo.getText() +"'"+
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
					        	                      
			        	                                
			        	                                    sentenciaSQL    =   "Select  V.NumeroVenta,substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.Precio,V.subtotal,P1.PrecioCompra,V.Subtotal - (P1.PrecioCompra * V.Cantidad) "+
												                                 "From VentasEncabezado VE,VentasDetalle V, Productos P, Precios P1 "+ 
												                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and "+
												                                 "                       V.IdProducto = P.IdProducto and P1.IdProducto = P.IdProducto " +
												                                 "                       and VE.Fecha between  P1.FechaDesde and P1.FechaHasta and "+
												                                 "                        V.IdProducto = '"+ jtCodigo.getText() +"'"+
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
              long gananciaTotal = 0;
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
			    	
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	
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
			 	 
		 	 	 
			 	 jtTotalUnidades.setText(String.valueOf(cantidades));
			 	 jtTotalVentas.setText(getFormatoNumero(totales));
			 	 jtGanancias.setText(getFormatoNumero(gananciaTotal));
		 	 
			 	 resultadoBoolean = true;
		 	 
			 
			 } else {
			 	     
			 	    jtCodigo.setText("");
			 	    jtDescripcion.setText("");
			 	  	Mensaje(" Este Productos no tiene facturas asociadas ","Información",2);
			 	  	jtCodigo.grabFocus();
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

    for (int i = 0; i < 6; i++)
          
             System.out.println(tablaVentasProducto.getColumnModel().getColumn(i).getPreferredWidth());

     
            limpiar();

         } else 

            if (fuente == Bbuscar) { 

               traerInformacion();

            } else 

               if (fuente == Bsalir) {

                 ocultarJFrame(); 

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
		   
				   if (f.getComponent() == tablaVentasProducto)// && !jtDescripcion.getText().isEmpty())
				       
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