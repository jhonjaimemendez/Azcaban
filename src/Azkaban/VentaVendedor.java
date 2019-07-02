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

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


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

final public class VentaVendedor extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpTodos;
    private  JPanel jpVendedor;
    private  JPanel jpVentas;

    //Se delcaran los JTextField
    private  JTextField jtIdVendedor;
    private  JTextField jtNombreVendedor;
    private  JTextField jtTotalVentas;
    private  JTextField jtTotalDevuelto;
    private  JTextField jtTotalPagado;
    
   
    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
    
    //** Se declaran los JTable
    private  JTable tablaVentasVendedor;
    private  JTable tablaVentasDetalle;
   
    
    //** Se declaran los JLabel
    private JLabel jlTipo;
    private JLabel jlNumero;
    private JLabel jlNombre;
    private JLabel jlTotalVentas;
    
    
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
    private  JComboBox jcTipoId;
    
     //JTabbedPane
    private JTabbedPane pestaña;
   
     
   //JDialog
   private JDialog dialogVentasDetalle;
   
   //JButton
   private JButton jbCerrar;
  
   //Columnas y filas estaticas 
    private Object [] nombresColumnas =  {"Tipo ","Identificacion","Nombres","Numero De Compras","Fecha","Total"};
    private Object [][] datos = new Object[16][6];
    
    private Object [] nombresColumnasDetalle = {"Item","Código","Producto","Cantidad","Devueltas","Precio","Subtotal"};
    private Object [][] datosDetalle = new Object[16][6];
    
    //Vectores
    private Vector columnas;
     private Vector columnasDetalle;

      //Modelo de datos 
    private SortableTableModel dm;
    private SortableTableModel dm1;
   
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
     
    //** Constructor General 
    public VentaVendedor(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Ventas Por Vendedor","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;

      //** Se agregan los JPanel

      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(5,45,775,490);
      
      jpVendedor = getJPanel(0,0,100,100);
      pestaña.addTab("Vendedor  ",jpVendedor);
      
      jpTodos = getJPanel(0,0,100,100);
      pestaña.addTab("Consolidado   ",jpTodos);
      
      jpVentas = getJPanel("Ventas Por Vendedor",35, 60, 700, 395,14);
      jpVendedor.add(jpVentas);
      
       pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 1) {
      		  	
      		   jtTotalVentas.setText("");
      	       jtTotalDevuelto.setText("");	
               jtTotalPagado.setText("");
      		 
      		  
      		   jpTodos.add(jpVentas);      			
      		   
      		   traerInformacion();
      			
                tablaVentasVendedor.getColumnModel().getColumn(0).setPreferredWidth(75);
	            tablaVentasVendedor.getColumnModel().getColumn(0).setMinWidth(15);
	            tablaVentasVendedor.getColumnModel().getColumn(0).setMaxWidth(2147483647);
	        
	            tablaVentasVendedor.getColumnModel().getColumn(1).setPreferredWidth(75);
	            tablaVentasVendedor.getColumnModel().getColumn(1).setMinWidth(15);
	            tablaVentasVendedor.getColumnModel().getColumn(1).setMaxWidth(2147483647);
	        
	            tablaVentasVendedor.getColumnModel().getColumn(2).setPreferredWidth(75);
	            tablaVentasVendedor.getColumnModel().getColumn(2).setMinWidth(15);
	            tablaVentasVendedor.getColumnModel().getColumn(2).setMaxWidth(2147483647);
	         
		        
		        ocultarColumnas(tablaVentasVendedor,4);
		        
		        configurarColumnas();
      			
     
      			
      		} else {
      		  
      		   jtTotalVentas.setText("");
      	       jtTotalDevuelto.setText("");	
               jtTotalPagado.setText("");
      		   jtIdVendedor.setText("");
	           jtNombreVendedor.setText("");
	           jcTipoId.setSelectedIndex(0);
	           jtNombreVendedor.setText("");
	           jtIdVendedor.grabFocus();
	         
              jpVendedor.add(jpVentas);
              
              tablaVentasVendedor.getColumnModel().getColumn(4).setPreferredWidth(75);
		      tablaVentasVendedor.getColumnModel().getColumn(4).setMinWidth(15);
		      tablaVentasVendedor.getColumnModel().getColumn(4).setMaxWidth(2147483647);
		        
		        
              ocultarColumnas(tablaVentasVendedor,0);
              ocultarColumnas(tablaVentasVendedor,1);
              ocultarColumnas(tablaVentasVendedor,2);
              
              
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

     tablaVentasVendedor = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaVentasVendedor.addFocusListener(this);
     tablaVentasVendedor.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		
     		if (m.getClickCount() == 2) {
     		
     		 if (pestaña.getSelectedIndex() == 0)
     		 
     		   configurarJDialogVentasDetalle();
     		   
     		}   
     	}
     	
     });
  
    
     configurarColumnas();
     
     ocultarColumnas(tablaVentasVendedor,0);
     ocultarColumnas(tablaVentasVendedor,1);
     ocultarColumnas(tablaVentasVendedor,2);
     
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaVentasVendedor);
      scrollPane.setBounds(25, 25, 655, 276);
      jpVentas.add(scrollPane);
       
       
     
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Vendedor.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
    
      columnas = new Vector();
      columnas.add("Tipo Id.");
      columnas.add("Id");
      columnas.add("Nombres");
      columnas.add("Fecha");
      columnas.add("Numero De Compras");
      columnas.add("Total");
      
     
      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
         
      
   
      //Se agregan los JLabel
      jlTipo = getJLabel("Tipo:",170, 20, 50, 20);
      jpVendedor.add(jlTipo);
      
      
      jlNumero = getJLabel("Número:",317, 20, 50, 20);
      jpVendedor.add(jlNumero);
      
      
      jlNombre = getJLabel("Nombre:",487, 20, 50, 20);
      jpVendedor.add(jlNombre);
       
      
       //Se agregan los JLabel
      JLabel jlTotalVentas  = getJLabel("Total Vendido:",490, 305, 100, 20);
      jpVentas.add(jlTotalVentas);
      
      JLabel jlTotalDevuelto  = getJLabel("Total Devuelto:",470, 335, 100, 20);
      jlTotalDevuelto.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalDevuelto);
      
      JLabel jlTotalPagado  = getJLabel("Total Pagado:",470, 365, 100, 20);
      jlTotalPagado.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalPagado);
      
      //Se agregan los JComboBox 
      jcTipoId = getJComboBox(210, 20, 95, 20,"Selecione el Tipo de Identificación del Cliente");
      jcTipoId.addItem("Cedula");
      jcTipoId.addItem("Tarjeta Id.");
      jcTipoId.addItem("Pasaporte");
      jpVendedor.add(jcTipoId);
      
      //Se agregan los JTextField
      jtIdVendedor = getJTextField("",375, 20, 100, 20,"Digíte el Número de Identificación del Cliente","12");
      jtIdVendedor.setHorizontalAlignment(JTextField.RIGHT);
      jtIdVendedor.addKeyListener(getValidarEntradaNumeroJTextField());
      jtIdVendedor.addFocusListener(this);
      jpVendedor.add(jtIdVendedor);
      

      jtNombreVendedor = getJTextField("",540, 20, 200, 20,"Digite el Nombre del Cliente o Preione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtNombreVendedor,conectarMySQL);
      
      
      jtNombreVendedor.addKeyListener(listaValor);
      jtNombreVendedor.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtNombreVendedor.addFocusListener(this);
      jtNombreVendedor.addActionListener(this);
      jpVendedor.add(jtNombreVendedor);
      
       
      jtTotalVentas  = getJTextField("Total Vendidas",578, 305, 100, 20,"",false);
      jtTotalVentas.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalVentas);
      
      
      jtTotalDevuelto  = getJTextField("Total Devuelto",578, 335, 100, 20,"",false);
      jtTotalDevuelto.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalDevuelto);
      
      jtTotalPagado  = getJTextField("Total Pagado",578, 365, 100, 20,"",false);
      jtTotalPagado.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalPagado);  

     
      
      jcPeriodos =  getJComboBox(40,20,120,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Dia");
      jcPeriodos.addItem("Quincena");
      jcPeriodos.addItem("Mes");
      jcPeriodos.addItem("Trimestre");
      jpVendedor.add(jcPeriodos);
     
     
    
    }
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {
        
        tablaVentasVendedor.getColumnModel().getColumn(0).setPreferredWidth(5);
	    tablaVentasVendedor.getColumnModel().getColumn(1).setPreferredWidth(60);
		tablaVentasVendedor.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		tablaVentasVendedor.getColumnModel().getColumn(2).setPreferredWidth(210);
		tablaVentasVendedor.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaVentasVendedor.getColumnModel().getColumn(3).setPreferredWidth(80);
	    tablaVentasVendedor.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
	    tablaVentasVendedor.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
	
         if (pestaña.getSelectedIndex() == 1)
	    
	       	tablaVentasVendedor.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
	    
	    else   	
	       
	       tablaVentasVendedor.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
	    
		
		

	}
	
	
	
	
   //**************************** Metodo configurarColumnas() ************************

	private void configurarColumnasDetalle() {
        
      
        tablaVentasDetalle.getColumnModel().getColumn(0).setPreferredWidth(31);
		tablaVentasDetalle.getColumnModel().getColumn(1).setPreferredWidth(133);
		tablaVentasDetalle.getColumnModel().getColumn(2).setPreferredWidth(175);
	    tablaVentasDetalle.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
		tablaVentasDetalle.getColumnModel().getColumn(3).setPreferredWidth(53);
		tablaVentasDetalle.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
		tablaVentasDetalle.getColumnModel().getColumn(4).setPreferredWidth(63);
		tablaVentasDetalle.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
		tablaVentasDetalle.getColumnModel().getColumn(5).setPreferredWidth(70);
		tablaVentasDetalle.getColumnModel().getColumn(6).setCellRenderer(getAlinearDerecha());
		tablaVentasDetalle.getColumnModel().getColumn(6).setPreferredWidth(69);
    } 
    
    //******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		String sentencia = "Select Case TipoId WHEN 'C' THEN 'Cedula'"+ 
	                   "                   WHEN 'T' THEN 'Tarjeta Id.'"+
	                   "                   Else 'Pasaporte'"+
	                   "                   End Tipo, idVendedor,Concat(Nombre,Concat(' ',Apellido)) Nombres "+
	                   "From   Vendedores "+
	                   "Where Nombre like '";

		return sentencia;


	}
	
	

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[2][5];
		
		objetosRetorno[0][0] = jtNombreVendedor;
        objetosRetorno[0][1] = tablaVentasVendedor; 
       	objetosRetorno[0][2] = "2";
       	objetosRetorno[0][3] = jtIdVendedor;
       	objetosRetorno[0][4] = "1";
        
        
       	objetosRetorno[1][3] = jcTipoId;
       	objetosRetorno[1][4] = "0";

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
       
             
            if (pestaña.getSelectedIndex() == 1) 
         
         
         
                sentenciaSQL    =   "Select  Case V.TipoId WHEN 'C' THEN 'Cedula'"+ 
				                    "                   WHEN 'T' THEN 'Tarjeta Id.'"+
				                    "                   Else 'Pasaporte'"+
				                    "                   End,V.idVendedor, Concat(C.Nombre,Concat(' ',C.Apellido)),count(1),sum(total),Sum(ifnull((Select D.Total " +
												                                                                                                            "From DevolucionesVentaencabezado D "+
												                                                                                                            "Where  D.NumeroVenta = V.NumeroVenta),'0')) "+
		                            "From Ventasencabezado V, Vendedores C "+
	                                "Where V.idVendedor = C.idVendedor "+
	                                "Group by V.TipoId,V.idVendedor, Concat(C.Nombre,Concat(' ',C.Apellido))";
              
         	else
               
               if (jcPeriodos.getSelectedIndex() == 0)
                
	                 sentenciaSQL    =   "Select NumeroVenta,Substr(Fecha,1,10),Total,ifnull((Select D.Total " +
	                                                                                         "From DevolucionesVentaencabezado D  "+
	                                                                                          "Where D.NumeroVenta = V.NumeroVenta),'0') "+
                                         "From VentasEncabezado V "+
                                         "Where idVendedor = '" + jtIdVendedor.getText() +"'";
		                                 
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                
	                 sentenciaSQL    =    "Select NumeroVenta,Substr(Fecha,1,10),Total,ifnull((Select D.Total " +
	                                                                                         "From DevolucionesVentaencabezado D  "+
	                                                                                          "Where D.NumeroVenta = V.NumeroVenta),'0') "+
                                         "From VentasEncabezado V "+
                                         "Where idVendedor = '" + jtIdVendedor.getText() +"'" +
		                                 "                       and Substr(VE.Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'";
		                                 
	                
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
        	                  
		                       
				                 sentenciaSQL    =   "Select NumeroVenta,Substr(Fecha,1,10),Total,ifnull((Select D.Total " +
	                                                                                         "From DevolucionesVentaencabezado D  "+
	                                                                                          "Where D.NumeroVenta = V.NumeroVenta),'0') "+
			                                         "From VentasEncabezado V "+
			                                         "Where idVendedor = '" + jtIdVendedor.getText() +"'"+
			                                         "                       and substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
										             "                       and Substr(VE.Fecha,1,10) " + condicion;
				     } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                
						                 sentenciaSQL    =   "Select NumeroVenta,Substr(Fecha,1,10),Total,ifnull((Select D.Total " +
	                                                                                                             "From DevolucionesVentaencabezado D  "+
	                                                                                                             "Where D.NumeroVenta = V.NumeroVenta),'0') "+
					                                         "From VentasEncabezado V "+
					                                         "Where idVendedor = '" + jtIdVendedor.getText() +"'"+
										                     "      and substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'";
										                     
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
					        	                      
			        	                     
						                                 sentenciaSQL    =  	"Select NumeroVenta,Substr(Fecha,1,10),Total,ifnull((Select D.Total " +
	                                                                                                                                "From DevolucionesVentaencabezado D  "+
	                                                                                                                                "Where D.NumeroVenta = V.NumeroVenta),'0') "+
										                                        "From VentasEncabezado V "+
										                                        "Where idVendedor = '" + jtIdVendedor.getText() +"'"+
																		        "      and substr(VE.Fecha,1,7) between " + condicion;
													          
		                             }
		                             
		                         
	   
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	{ 

              
              Vector <Vector > filas = new Vector <Vector>();
              long totales = 0;
              long totalesDevueltos = 0;
		
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
                  if (pestaña.getSelectedIndex() == 1)  {
			     
			        columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	columnas.add("");
				    		
				    Long total = resultado.getLong(5);
				    
		            columnas.add(getFormatoNumero(total));
		            
		            Long totalDevuelto = resultado.getLong(6);
			     
		            totalesDevueltos += totalDevuelto;
                  
	                  
	                totales += total;
			    
			    
			    } else {
			    	
			    	
			    	columnas.add("");
			    	columnas.add("");
			    	columnas.add("");
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	
			    	Long total = resultado.getLong(3);
			    	
			    	Long totalDevuelto = resultado.getLong(4);
			     
		            totalesDevueltos += totalDevuelto;
                  
			    	
	                 columnas.add(getFormatoNumero(total));
                  
                    totales += total;

			    	
			    }
			    
			    	
			     filas.add(columnas);	
		    				
			  }
	           
	           
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 	 
				 jtTotalVentas.setText(getFormatoNumero(totales));
			 	 jtTotalDevuelto.setText(getFormatoNumero(totalesDevueltos));
			 	 jtTotalPagado.setText(getFormatoNumero(totales - totalesDevueltos));
			 
			 
			 	 
			 	 resultadoBoolean = true;
		 	 
			 
			 } else {
			 	
			    if (pestaña.getSelectedIndex() == 1) 
     
			 	 
			 	 	Mensaje(" No existen facturas asociadas ","Información",2);
			 
			 
			 	else {
			 	    
			 	    jcTipoId.setSelectedIndex(0); 
			 	    jtIdVendedor.setText("");
			 	    jtNombreVendedor.setText("");
			 	  	Mensaje(" Este Cliente no tiene facturas asociadas ","Información",2);
			 	  	jtIdVendedor.grabFocus();
			    }  
			 	
			 } 
		 	      
		 	   	
          } 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultadoBoolean;
    }
	
	
	 
    //*****************************Metodo traerInformacionDetalle() ******************************************
  
	final private boolean traerInformacionDetalle(String numeroFactura) { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select C.Item,C.idProducto, P.Descripcion,C.Cantidad, ifnull((Select D1.CantidadDevuelta " +
                                                                                      "From DevolucionesVentaencabezado D, DevolucionesVentadetalle D1 "+
                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroVenta = C.NumeroVenta and "+
                                                                                      "      D1.IdProducto = C.IdProducto ),'0') Devueltas, C.Precio,C.Subtotal "+
                              "From   VentasDetalle C, Productos P "+
                              "Where  C.IdProducto = P.IdProducto and C.NumeroVenta = '"+ numeroFactura+"'";
       	

	    if (columnasDetalle == null) {
	    	
	    	  columnasDetalle = new Vector();
		      columnasDetalle.add("Item");
		      columnasDetalle.add("idProducto");
		      columnasDetalle.add("Descripcion");
		      columnasDetalle.add("Cantidad");
		      columnasDetalle.add("Devueltas");
		      columnasDetalle.add("Precio");
		      columnasDetalle.add("Subtotal");
		      
		      
	    }
	    
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	{ 
 
              Vector <Vector > filas = new Vector <Vector>();
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	columnas.add(getFormatoNumero(resultado.getString(5)));
			    	columnas.add(getFormatoNumero(resultado.getString(6)));
			    	columnas.add(getFormatoNumero(resultado.getString(7)));
			    	
			    	filas.add(columnas);	
		    				
			  }
	                      
		 	 dm1.setDataVector(filas,columnasDetalle);
		 	 resultadoBoolean = true;
		 
			 	      
		 	   	
          } 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultadoBoolean;
        
    }  
      
	   //********************* configurarJDialogVentasDetalle()****************************************
     
      private void configurarJDialogVentasDetalle() {
    	
    	
    	if (dialogVentasDetalle == null) {
    		
    		  dialogVentasDetalle = new JDialog(getJFrame(),"Detalles",true);
		  	  dialogVentasDetalle.setLayout(null);
		  	  dialogVentasDetalle.setSize(695, 390);
		  	  dialogVentasDetalle.setLocationRelativeTo(null);
		  	  
		  	  //Se agrega un panel para un JTable
		  	  JPanel jpDetalleCompra = getJPanel("Detalles de la Venta",20,10, 640, 285,14);
		  	  
		  	  // se configura el modelo para la tabla
		      dm1 = new SortableTableModel() {
		
		          //Se especifica el serial para la serializacion
		          static final long serialVersionUID = 19781212;
		
		          public Class getColumnClass(int col) {
		
		              return String.class;
		
		          }
		
		         public boolean isCellEditable(int row, int col) {
		
		             if (col == 0)
		               
		               return true;
		               
		             else
		               
		               return false;  
		
		         }
		
		      };
		
		     dm1.setDataVector(datosDetalle,nombresColumnasDetalle);  //Se agrega las columnas y filas al modelo de tabla
		
		     tablaVentasDetalle = getJTable(dm1,true); //Se instancia el JTable con el modelo de datos
		     
		     configurarColumnasDetalle();
		     
		      //** Se configura un scroll para el JTable 
		
		      JScrollPane scrollPane = new JScrollPane(tablaVentasDetalle);
		      scrollPane.setBounds(20, 30, 600, 240);
		      jpDetalleCompra.add(scrollPane);
		       
		  	  dialogVentasDetalle.add(jpDetalleCompra);
		  	  
		  	  
		      //** Se agregan los JButton
		
		      jbCerrar = getJButton("Cerrar",280, 310, 125, 25,"","");
		      jbCerrar.addActionListener(this);
		      dialogVentasDetalle.add(jbCerrar);
		      
		}
		
	    traerInformacionDetalle(tablaVentasVendedor.getValueAt(tablaVentasVendedor.getSelectedRow(),3).toString());
		dialogVentasDetalle.setVisible(true);
	
	}	  	  
	
	//*****************************Metodo traerInformacion() ******************************************
    
    private boolean traerInformacionVendedor() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select Nombre,Apellido "+
                              "From   Vendedores "+
                              "Where  TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) + "' and idVendedor ='" + jtIdVendedor.getText() + "'"; 
       
       
        
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 


	             if (resultado.next()) { 
	
	                 jtNombreVendedor.setText(resultado.getString(1) + " " + resultado.getString(2));
	                 
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
                         
                               dialogVentasDetalle.setVisible(false);
                             
                          } else 
                            
                            if (fuente == jtNombreVendedor)  {
					 	             	     				
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
				 		   	     	 jcTipoId.setSelectedIndex(0);
				 		   	     	 jtIdVendedor.setText("");
				 		   	     	 jtNombreVendedor.setText("");
				 		   	     	 jtTotalVentas.setText("");
      		                         jtTotalDevuelto.setText("");	
                                     jtTotalPagado.setText("");
                                     traerInformacion();
				 		   	     }
      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
        
         
         if (f.getComponent() == jtNombreVendedor && (getDialogoListaValores() == null || !getDialogoListaValores().isVisible()) && jtNombreVendedor.isFocusable() && mostrarListaAutomatica ) {
            	
             listaValor.mostrarListaValores();
	
		} else
			
				 if (f.getComponent() != jtNombreVendedor  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
	                     
	                  jcTipoId.setSelectedItem(getJTableListaValores().getValueAt(0,0).toString());
	                  jtIdVendedor.setText(getJTableListaValores().getValueAt(0,1).toString());
		              jtNombreVendedor.setText(getJTableListaValores().getValueAt(0,2).toString());

		             
		              getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
	                  jtNombreVendedor.selectAll();
	                  
	                  
				      traerInformacion();
			
				 } else
		   
				   if (f.getComponent() == tablaVentasVendedor && !jtNombreVendedor.getText().isEmpty())
				      
				        traerInformacion();
				       
		        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
         if (f.getComponent() == jtIdVendedor && !jtIdVendedor.getText().isEmpty()) {
        	
        	if (!traerInformacionVendedor()) {
        		
        		mostrarListaAutomatica = false;
        		Mensaje("Identificacion " +  jtIdVendedor.getText( ) + " no registrada","Información",2);
        		jtIdVendedor.setText("");
        		
        	} else
        	    
        	    traerInformacion();
         	
        }
        
        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

}