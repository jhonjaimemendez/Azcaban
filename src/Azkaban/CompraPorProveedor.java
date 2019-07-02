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


final public class CompraPorProveedor extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpTodos;
    private  JPanel jpProveedor;
    private  JPanel jpCompras;
    
    //Se delcaran los JTextField
    private  JTextField jtIdProveedor;
    private  JTextField jtRazonSocial;
    private  JTextField jtTotalCompras;
    private  JTextField jtTotalDevuelto;
    private  JTextField jtTotalPagado;
    
    
     //** JTabbedPane
    private JTabbedPane pestaña;
    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
    
    
    //** Se declaran los JTable
    private  JTable tablaCompras;
    private  JTable tablaComprasDetalle;
    
    
    //** Se declaran los JLabel
    private JLabel jlNumero;
    private JLabel jlNombre;
    
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
     
    //JDialog
    private JDialog dialogComprasDetalle;
   
   //JButton
   private JButton jbCerrar;
   
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Nit","Proveedor","Numero Compra","Fecha","Total"};
    private Object [][] datos = new Object[16][5];
    
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
    public CompraPorProveedor(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Compras Por Proveedor","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;


      //** Se agregan los JPanel
      
      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(5,45,775,490);
      
       
      jpProveedor = getJPanel(0,0,100,100);
      pestaña.addTab("Provedores  ",jpProveedor);
      
      jpTodos = getJPanel(0,0,100,100);
      pestaña.addTab("Consolidado   ",jpTodos);
     
      jpCompras = getJPanel("Compras Realizadas",35, 60, 700, 395,14);
      jpProveedor.add(jpCompras);
      
     
       pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		 jtTotalCompras.setText("");
	         jtTotalDevuelto.setText("");	
             jtTotalPagado.setText("");
               
      		if (pestaña.getSelectedIndex() == 1) {
      		 	  
      		   jpTodos.add(jpCompras);      			
      		   
      		   traerInformacion();
      			
                tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(75);
	            tablaCompras.getColumnModel().getColumn(0).setMinWidth(15);
	            tablaCompras.getColumnModel().getColumn(0).setMaxWidth(2147483647);
	        
	            tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(75);
	            tablaCompras.getColumnModel().getColumn(1).setMinWidth(15);
	            tablaCompras.getColumnModel().getColumn(1).setMaxWidth(2147483647);
	        
	            
		        ocultarColumnas(tablaCompras,3);
		        
		        configurarColumnas();
      			
     
      			
      		} else {
      		      
      		 	  jtIdProveedor.setText("");
		          jtRazonSocial.setText("");
	             
	              jpProveedor.add(jpCompras);
	              
	              tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(75);
			      tablaCompras.getColumnModel().getColumn(3).setMinWidth(15);
			      tablaCompras.getColumnModel().getColumn(3).setMaxWidth(2147483647);
			        
			        
	              ocultarColumnas(tablaCompras,0);
	              ocultarColumnas(tablaCompras,1);
	              
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

             if (col == 0)
               
               return true;
               
             else
               
               return false;  

         }

      };

     dm.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla

     tablaCompras = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaCompras.addFocusListener(this);
  
     tablaCompras.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		
     		if (m.getClickCount() == 2) {
     		
     		 if (pestaña.getSelectedIndex() == 0)
     		 
     		   configurarJDialogComprasDetalle();
     		   
     		}   
     	}
     	
     });
 	  
 	 ocultarColumnas(tablaCompras,0);
	 ocultarColumnas(tablaCompras,1);
      
      
      configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaCompras);
      scrollPane.setBounds(25, 25, 655, 276);
      jpCompras.add(scrollPane);
       
       
     
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Proveedores.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
      
      columnas = new Vector();
      columnas.add("Nit");
      columnas.add("Proveedor");
      columnas.add("Numero Venta");
      columnas.add("Fecha");
      columnas.add("Total");
      
      
      jtTotalCompras  = getJTextField("Total Compradas",578, 305, 100, 20,"",false);
      jtTotalCompras.setHorizontalAlignment(JTextField.RIGHT);
      jpCompras.add(jtTotalCompras);
    
    
      jtTotalDevuelto  = getJTextField("Total Devuelto",578, 335, 100, 20,"",false);
      jtTotalDevuelto.setHorizontalAlignment(JTextField.RIGHT);
      jpCompras.add(jtTotalDevuelto);
      
      jtTotalPagado  = getJTextField("Total Pagado",578, 365, 100, 20,"",false);
      jtTotalPagado.setHorizontalAlignment(JTextField.RIGHT);
      jpCompras.add(jtTotalPagado);  

      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
      
       //Se agregan los JLabel
      jlNumero = getJLabel("Nit:",220, 20, 30, 20);
      jpProveedor.add(jlNumero);
       
      JLabel jlTotalCompras  = getJLabel("Total Comprado:",470, 305, 100, 20);
      jlTotalCompras.setHorizontalAlignment(JLabel.RIGHT);
      jpCompras.add(jlTotalCompras);
      
      JLabel jlTotalDevuelto  = getJLabel("Total Devuelto:",470, 335, 100, 20);
      jlTotalDevuelto.setHorizontalAlignment(JLabel.RIGHT);
      jpCompras.add(jlTotalDevuelto);
      
      JLabel jlTotalPagado  = getJLabel("Total Pagado:",470, 365, 100, 20);
      jlTotalPagado.setHorizontalAlignment(JLabel.RIGHT);
      jpCompras.add(jlTotalPagado);
      
      jlNombre = getJLabel("Razón Social:",380, 20, 90, 20);
      jpProveedor.add(jlNombre);
      
      
      jtIdProveedor = getJTextField("",250, 20, 105, 20,"Digíte el Número de Identificación del Proveedor","12");
      jtIdProveedor.setHorizontalAlignment(JTextField.RIGHT);
      jtIdProveedor.addKeyListener(getValidarEntradaNumeroJTextField());
      jtIdProveedor.addFocusListener(this);
      jpProveedor.add(jtIdProveedor);
      
         
      

      jtRazonSocial = getJTextField("",470, 20, 260, 20,"Digite el Nombre del Cliente o Preione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtRazonSocial,conectarMySQL);
      
      
      //jtRazonSocial.addKeyListener(listaValor);
      jtRazonSocial.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtRazonSocial.addKeyListener(listaValor);
      jtRazonSocial.addFocusListener(this);
      jtRazonSocial.addActionListener(this);
      jpProveedor.add(jtRazonSocial);
      
      jcPeriodos =  getJComboBox(50,20,130,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Hoy");
      jcPeriodos.addItem("Ultima Quincena");
      jcPeriodos.addItem("Ultimo Mes");
      jcPeriodos.addItem("Ultimo Trimestre");
      jcPeriodos.addActionListener(this);
      jpProveedor.add(jcPeriodos);
     
     
    
    
    }

	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(20);
		tablaCompras.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(200);
		tablaCompras.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
	
		tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(40);
	    
	    tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(30);
	    tablaCompras.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
	    
	    tablaCompras.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
		
		
	    if (pestaña.getSelectedIndex() == 1)
	    
	       	tablaCompras.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
	    
	    else   	
	       
	       tablaCompras.getColumnModel().getColumn(2).setCellRenderer(getAlinearDerecha());	
		

	}
	
   //**************************** Metodo configurarColumnas() ************************

	private void configurarColumnasDetalle() {
        
      
        tablaComprasDetalle.getColumnModel().getColumn(0).setPreferredWidth(31);
		tablaComprasDetalle.getColumnModel().getColumn(1).setPreferredWidth(133);
		tablaComprasDetalle.getColumnModel().getColumn(2).setPreferredWidth(175);
	    tablaComprasDetalle.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
		tablaComprasDetalle.getColumnModel().getColumn(3).setPreferredWidth(53);
		tablaComprasDetalle.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
		tablaComprasDetalle.getColumnModel().getColumn(4).setPreferredWidth(63);
		tablaComprasDetalle.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
		tablaComprasDetalle.getColumnModel().getColumn(5).setPreferredWidth(70);
		tablaComprasDetalle.getColumnModel().getColumn(6).setCellRenderer(getAlinearDerecha());
		tablaComprasDetalle.getColumnModel().getColumn(6).setPreferredWidth(69);
    } 
	
	



    
    //******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		String sentencia = "Select Nit,RazonSocial "+
		                   "From   Proveedores "+
		                   "Where RazonSocial like '";

		return sentencia;


	}
	
	

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[1][5];
		
		objetosRetorno[0][0] = jtRazonSocial;
        objetosRetorno[0][1] = tablaCompras; 
       	objetosRetorno[0][2] = "1";
       	objetosRetorno[0][3] = jtIdProveedor;
       	objetosRetorno[0][4] = "0";




		return objetosRetorno;

	}
	
	
    //*********************** Metodo limpiar ************************

    private void limpiar() { 

        if (pestaña.getSelectedIndex() == 0) //Comodin para que se dispare el stateChange
	       
	         pestaña.setSelectedIndex(1);
	       
	         pestaña.setSelectedIndex(0);


    }
    
   
      //********************* configurarJDialogComprasDetalle()****************************************
     
      private void configurarJDialogComprasDetalle() {
    	
    	
    	if (dialogComprasDetalle == null) {
    		
    		  dialogComprasDetalle = new JDialog(getJFrame(),"Detalles",true);
		  	  dialogComprasDetalle.setLayout(null);
		  	  dialogComprasDetalle.setSize(695, 390);
		  	  dialogComprasDetalle.setLocationRelativeTo(null);
		  	  
		  	  //Se agrega un panel para un JTable
		  	  JPanel jpDetalleCompra = getJPanel("Detalles de la Compra",20,10, 640, 285,14);
		  	  
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
		
		     tablaComprasDetalle = getJTable(dm1,true); //Se instancia el JTable con el modelo de datos
		     
		     configurarColumnasDetalle();
		     
		      //** Se configura un scroll para el JTable 
		
		      JScrollPane scrollPane = new JScrollPane(tablaComprasDetalle);
		      scrollPane.setBounds(20, 30, 600, 240);
		      jpDetalleCompra.add(scrollPane);
		       
		  	  dialogComprasDetalle.add(jpDetalleCompra);
		  	  
		  	  
		      //** Se agregan los JButton
		
		      jbCerrar = getJButton("Cerrar",280, 310, 125, 25,"","");
		      jbCerrar.addActionListener(this);
		      dialogComprasDetalle.add(jbCerrar);
		      
		}
		
	    traerInformacionDetalle(tablaCompras.getValueAt(tablaCompras.getSelectedRow(),2).toString());
		dialogComprasDetalle.setVisible(true);
	
	}	  	  
      
      
     
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = null;
        
        
        if (pestaña.getSelectedIndex() == 1)
          
            sentenciaSQL =    "Select   P.Nit,P.RazonSocial,Count(1),Substr(C.Fecha,1,10),Sum(C.Total),Sum(ifnull((Select D.Total " +
						                                                                                           "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
						                                                                                           "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra),'0')) "+
                              "From   ComprasEncabezado C, Proveedores P "+
                              "Where  C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' "+
                              "Group by P.Nit ";
                              
                              
         else
          
           if (jcPeriodos.getSelectedIndex() == 0)
                
	                 sentenciaSQL    =   "Select   P.Nit,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C.Total,ifnull((Select D.Total " +
						                                                                                           "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
						                                                                                           "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra),'0') "+
			                             "From   ComprasEncabezado C, Proveedores P "+
			                             "Where  C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' "+
			                             "and  P.Nit = '" + jtIdProveedor.getText() + "'"; 
           
	                
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                
	                 sentenciaSQL    =   "Select   P.Nit,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C.Total,ifnull((Select D.Total " +
						                                                                                           "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
						                                                                                           "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra),'0') "+
			                             "From   ComprasEncabezado C, Proveedores P "+
			                             "Where  C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' "+
			                             "and  P.Nit = '" + jtIdProveedor.getText() + "' " +
			                             "and Substr(Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'"; 
                    
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
        	                  
		                       
				                 sentenciaSQL    =   "Select   P.Nit,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C.Total,ifnull((Select D.Total " +
						                                                                                                      "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
						                                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra),'0') "+
						                             "From   ComprasEncabezado C, Proveedores P "+
						                             "Where  C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' "+
						                             "and  P.Nit = '" + jtIdProveedor.getText() + "' " +
			                                         "                       and substr(Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
										             "                       and Substr(Fecha,1,10) " + condicion;
				     } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                
						                 sentenciaSQL    =   "Select   P.Nit,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C.Total,ifnull((Select D.Total " +
						                                                                                           "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
						                                                                                           "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra),'0') "+
						                                      "From   ComprasEncabezado C, Proveedores P "+
						                                      "Where  C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' "+
						                                      "       and  P.Nit = '" + jtIdProveedor.getText() + "' " +
										                      "      and substr(Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'";
										                     
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
					        	                      
			        	                     
						                                 sentenciaSQL    =  	"Select   P.Nit,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C.Total,ifnull((Select D.Total " +
						                                                                                                                                  "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
						                                                                                                                                  "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra),'0') "+
													                            "From   ComprasEncabezado C, Proveedores P "+
													                            "Where  C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' "+
													                            "       and  P.Nit = '" + jtIdProveedor.getText() + "' " +
																		        "      and substr(Fecha,1,7) between " + condicion;
													          
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
			    
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	
		    		Long total = resultado.getLong(5);
			        columnas.add(getFormatoNumero(total));
                   
                    Long totalDevuelto = resultado.getLong(6);
			        
                    
                    totales += total;
                    
                    totalesDevueltos += totalDevuelto;
                    
			    	filas.add(columnas);	
		    				
			  }
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 
			 	 jtTotalCompras.setText(getFormatoNumero(totales));
			 	 jtTotalDevuelto.setText(getFormatoNumero(totalesDevueltos));
			 	 jtTotalPagado.setText(getFormatoNumero(totales - totalesDevueltos));
			 	 
			 	 
		 	     resultadoBoolean = true;
		 	 
			 
			 } else {
			 	
			    if (pestaña.getSelectedIndex() == 1)  
			 	
			 	 
			 	 	Mensaje(" No existen facturas asociadas ","Información",2);
			 
			 
			 	else {
			 	     
			 	    jtIdProveedor.setText("");
			 	    jtRazonSocial.setText("");
			 	  	Mensaje(" Este Proveedor no tiene facturas asociadas ","Información",2);
			 	  	jtIdProveedor.grabFocus();
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
                                                                                      "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra and "+
                                                                                      "      D1.IdProducto = C.IdProducto ),'0') Devueltas, C.Precio,C.Subtotal "+
                              "From   ComprasDetalle C, Productos P "+
                              "Where  C.IdProducto = P.IdProducto and C.NumeroCompra = '"+ numeroFactura+"'";
       	
	    
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
	//*****************************Metodo traerInformacion() ******************************************
    
    private boolean traerInformacionProveedor() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select RazonSocial "+
                              "From   Proveedores "+
                              "Where  Nit = '" + jtIdProveedor.getText() + "'";
       
        	   
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 


	             if (resultado.next()) { 
	
	                 jtRazonSocial.setText(resultado.getString(1));
	                
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

               } else 
			 		   	if (fuente == jbCerrar) {
			 		   		    
                               dialogComprasDetalle.setVisible(false);
                             
                          } else
                          
                             if (fuente == jtRazonSocial)  {
				 	             	     			
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
				 		   	     	 jtIdProveedor.setText("");
				 		   	     	 jtRazonSocial.setText("");
				 		   	     	 jtIdProveedor.grabFocus();
				 		   	     	 
				 		   	     }
      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
        
         if (f.getComponent() == jtRazonSocial && (getDialogoListaValores() == null || !getDialogoListaValores().isVisible()) && jtRazonSocial.isFocusable() &&   mostrarListaAutomatica) {
            	
             listaValor.mostrarListaValores();
	
		} else
		
			 if (f.getComponent() != jtRazonSocial  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
                     
                  jtIdProveedor.setText(getJTableListaValores().getValueAt(0,0).toString());
	              jtRazonSocial.setText(getJTableListaValores().getValueAt(0,1).toString());
	             
	              getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
                  jtRazonSocial.selectAll();
		
			 } else
		   
				   if (f.getComponent() == tablaCompras && !jtIdProveedor.getText().isEmpty())
				      
				       traerInformacion();
				       
		        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
        if (f.getComponent() == jtIdProveedor && !jtIdProveedor.getText().isEmpty()) {
        	
        	if (!traerInformacionProveedor()) {
        		
        		mostrarListaAutomatica = false;
        	    Mensaje("Nit " +  jtIdProveedor.getText( ) + " no registrado","Información",2);
        	    mostrarListaAutomatica = true;
        		jtIdProveedor.setText("");
        		
        	} else {
                
                mostrarListaAutomatica = false;
        	    tablaCompras.grabFocus();
        	    tablaCompras.setRowSelectionInterval(0,0);
        	    traerInformacion();
        		
        	    
        	}    
        	
        }
        
        
        

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

}