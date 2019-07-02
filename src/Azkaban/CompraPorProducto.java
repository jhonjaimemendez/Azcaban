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
import javax.swing.JRadioButton;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.util.Vector;

import java.sql.ResultSet;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.Calendario;

final public class CompraPorProducto extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpTodos;
    private  JPanel jpProductos;
    private  JPanel jpCompras;
    
    //Se delcaran los JTextField
    private  JTextField jtIdProducto;
    private  JTextField jtNombreProducto;
    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
  
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
     
    
    //** Se declaran los JTable
    private  JTable tablaCompras;
    private  JTable tablaComprasDetalle;
    
    //** JTabbedPane
    private JTabbedPane pestaña;
    
    //** Se declaran los JLabel
    private JLabel jlCodigo;
    private JLabel jlNombre;
    
     
   //JDialog
   private JDialog dialogVentasDetalle;
   
   //JButton
   private JButton jbCerrar;
   
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Código","Producto","Proveedor","Numero Compra","Fecha","Unidades"};
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
    
      //Calendario
    private Calendario calendarioInicial;
    private Calendario calendarioFinal;
    
   
    //** Constructor General 
    public CompraPorProducto(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Compras Por Producto","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;


      //** Se agregan los JPanel
      
      pestaña = new JTabbedPane();
      
      pestaña.setName("pestaña");
      pestaña.setBounds(10,45,775,490);
      
      jpProductos = getJPanel(0,0,100,100);
      pestaña.addTab("Productos  ",jpProductos);
      
      
      jpTodos = getJPanel(0,0,100,100);
      pestaña.addTab("Consolidado   ",jpTodos);
      
     
      jpCompras = getJPanel("Compras Realizadas",40, 60, 700, 350,14);
      jpProductos.add(jpCompras);

      
       pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 1) {
      		  	
      		   
      		   jpTodos.add(jpCompras);      			
      		   
      		   traerInformacion();
      			
                tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(75);
	            tablaCompras.getColumnModel().getColumn(0).setMinWidth(15);
	            tablaCompras.getColumnModel().getColumn(0).setMaxWidth(2147483647);
	        
	            tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(75);
	            tablaCompras.getColumnModel().getColumn(1).setMinWidth(15);
	            tablaCompras.getColumnModel().getColumn(1).setMaxWidth(2147483647);
	           
	            
		        ocultarColumnas(tablaCompras,2);
		        
		        configurarColumnas();
      			
     
      			
      		} else {
      			
	      		  jtIdProducto.setText("");
		          jtNombreProducto.setText("");
		          jtIdProducto.grabFocus();
	             
	              jpProductos.add(jpCompras);
	              
	              tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(75);
			      tablaCompras.getColumnModel().getColumn(2).setMinWidth(15);
			      tablaCompras.getColumnModel().getColumn(2).setMaxWidth(2147483647);
			        
			        
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
     		       
     		       configurarJDialogVentasDetalle();
     		   
     		}   
     	}
     	
     });
 	  
      configurarColumnas();
      
       
      ocultarColumnas(tablaCompras,0);
      ocultarColumnas(tablaCompras,1);

      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaCompras);
      scrollPane.setBounds(25, 40, 655, 276);
      jpCompras.add(scrollPane);
       
      
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
      
      columnas = new Vector();
      columnas.add("Codigo");
      columnas.add("Proveedor");
      columnas.add("Numero Venta");
      columnas.add("Fecha");
      columnas.add("Total");
      columnas.add("Unidades");
      
      
      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
      
       //Se agregan los JLabel
      jlCodigo = getJLabel("Código:",220, 20, 50, 20);
      jpProductos.add(jlCodigo);
      
      
      jlNombre = getJLabel("Descripcion:",400, 20, 90, 20);
      jpProductos.add(jlNombre);
      
      
      jtIdProducto = getJTextField("",270, 20, 105, 20,"Digíte el Número de Identificación del Proveedor","12");
      jtIdProducto.setHorizontalAlignment(JTextField.RIGHT);
      jtIdProducto.addKeyListener(getValidarEntradaNumeroJTextField());
      jtIdProducto.addFocusListener(this);
      jpProductos.add(jtIdProducto);
      

      jtNombreProducto = getJTextField("",490, 20, 240, 20,"Digite el Nombre del Cliente o Preione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtNombreProducto,conectarMySQL);
      
      
      //jtNombreProducto.addKeyListener(listaValor);
      jtNombreProducto.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtNombreProducto.addKeyListener(listaValor);
      jtNombreProducto.addFocusListener(this);
      jtNombreProducto.addActionListener(this);
      jpProductos.add(jtNombreProducto);

      jcPeriodos =  getJComboBox(50,20,130,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Hoy");
      jcPeriodos.addItem("Ultima Quincena");
      jcPeriodos.addItem("Ultimo Mes");
      jcPeriodos.addItem("Ultimo Trimestre");
      jcPeriodos.addActionListener(this);
      jpProductos.add(jcPeriodos);
     
    
    
    }
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(20);
		tablaCompras.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		
	    tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(120);
		tablaCompras.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
		tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(120);
		tablaCompras.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(30);
		tablaCompras.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
		
		tablaCompras.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
		
		
		tablaCompras.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
			
			
		

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

		String sentencia = "Select IdProducto,Descripcion "+
		                   "From   Productos "+
		                   "Where Descripcion like '";

		return sentencia;


	}
	
	

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[1][5];
		
		objetosRetorno[0][0] = jtNombreProducto;
        objetosRetorno[0][1] = tablaCompras; 
       	objetosRetorno[0][2] = "1";
       	objetosRetorno[0][3] = jtIdProducto;
       	objetosRetorno[0][4] = "0";




		return objetosRetorno;

	}
	
	
    //*********************** Metodo limpiar ************************

    private void limpiar() { 

      
        if (pestaña.getSelectedIndex() == 0) //Comodin para que se dispare el stateChange
	       
	         pestaña.setSelectedIndex(1);
	       
	         pestaña.setSelectedIndex(0);	

    }
    
      
      //********************* configurarJDialogVentasDetalle()****************************************
     
      private void configurarJDialogVentasDetalle() {
    	
    	
    	if (dialogVentasDetalle == null) {
    		
    		  dialogVentasDetalle = new JDialog(getJFrame(),"Detalles",true);
		  	  dialogVentasDetalle.setLayout(null);
		  	  dialogVentasDetalle.setSize(695, 390);
		  	  dialogVentasDetalle.setLocationRelativeTo(null);
		  	  
		  	  //Se agrega un panel para un JTable
		  	  JPanel jpDetalleCompra = getJPanel("Detalles de la Compra",20, 10, 640, 285,14);
		  	  
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
		       
		  	  dialogVentasDetalle.add(jpDetalleCompra);
		  	  
		  	  
		      //** Se agregan los JButton
		
		      jbCerrar = getJButton("Cerrar",280, 310, 125, 25,"","");
		      jbCerrar.addActionListener(this);
		      dialogVentasDetalle.add(jbCerrar);
		      
		}
		
	    traerInformacionDetalle(tablaCompras.getValueAt(tablaCompras.getSelectedRow(),3).toString());
		dialogVentasDetalle.setVisible(true);
	
	}	  	  
      
    
      
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = null;
       
        if (pestaña.getSelectedIndex() == 1)
            
            sentenciaSQL   =  "Select   P1.idProducto,P1.Descripcion,P.RazonSocial,Count(1),Substr(C.Fecha,1,10),Sum(C1.Cantidad) "+
                              "From    ComprasEncabezado C,ComprasDetalle C1, Proveedores P,Productos P1 "+
                              "Where   C.NumeroCompra = C1.NumeroCompra and  "+
                              "        C.NumeroProveedor = P.NumeroProveedor and "+
                              "        C1.idProducto = P1.idProducto and "+
                              "        C.Estado = 'G' "+
                              "Group by P1.IdProducto "; 
                              
       else                                            
           
           
           if (jcPeriodos.getSelectedIndex() == 0)
                
	                   
            sentenciaSQL   =  "Select   P1.idProducto,P1.Descripcion,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C1.Cantidad "+
                              "From    ComprasEncabezado C,ComprasDetalle C1, Proveedores P,Productos P1 "+
                              "Where   C.NumeroCompra = C1.NumeroCompra and  "+
                              "        C.NumeroProveedor = P.NumeroProveedor and "+
                              "        C1.idProducto = P1.idProducto and "+
                              "        C.Estado = 'G' "+
                              "and  C1.idProducto = '" + jtIdProducto.getText() + "'";  
           
	                
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                
	                  sentenciaSQL   =    "Select   P1.idProducto,P1.Descripcion,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C1.Cantidad "+
			                              "From    ComprasEncabezado C,ComprasDetalle C1, Proveedores P,Productos P1 "+
			                              "Where   C.NumeroCompra = C1.NumeroCompra and  "+
			                              "        C.NumeroProveedor = P.NumeroProveedor and "+
			                              "        C1.idProducto = P1.idProducto and "+
			                              "        C.Estado = 'G' "+
			                              "and  C1.idProducto = '" + jtIdProducto.getText() + "' " +  
			                              "and Substr(Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'"; 
                    
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
        	                  
		                       
				                  sentenciaSQL   =    "Select   P1.idProducto,P1.Descripcion,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C1.Cantidad "+
						                              "From    ComprasEncabezado C,ComprasDetalle C1, Proveedores P,Productos P1 "+
						                              "Where   C.NumeroCompra = C1.NumeroCompra and  "+
						                              "        C.NumeroProveedor = P.NumeroProveedor and "+
						                              "        C1.idProducto = P1.idProducto and "+
						                              "        C.Estado = 'G'  and  C1.idProducto = '" + jtIdProducto.getText() + "' " +
						                              "                       and substr(Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
										              "                       and Substr(Fecha,1,10) " + condicion;
				     } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                
						                  sentenciaSQL   =    "Select   P1.idProducto,P1.Descripcion,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C1.Cantidad "+
								                              "From    ComprasEncabezado C,ComprasDetalle C1, Proveedores P,Productos P1 "+
								                              "Where   C.NumeroCompra = C1.NumeroCompra and  "+
								                              "        C.NumeroProveedor = P.NumeroProveedor and "+
								                              "        C1.idProducto = P1.idProducto and "+
								                              "        C.Estado = 'G' and  C1.idProducto = '" + jtIdProducto.getText() + "' " +
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
					        	                      
			        	                     
						                                 sentenciaSQL    =  	  "Select   P1.idProducto,P1.Descripcion,P.RazonSocial,C.NumeroCompra,Substr(C.Fecha,1,10),C1.Cantidad "+
													                              "From    ComprasEncabezado C,ComprasDetalle C1, Proveedores P,Productos P1 "+
													                              "Where   C.NumeroCompra = C1.NumeroCompra and  "+
													                              "        C.NumeroProveedor = P.NumeroProveedor and "+
													                              "        C1.idProducto = P1.idProducto and "+
													                              "        C.Estado = 'G' and  C1.idProducto = '" + jtIdProducto.getText() + "' " +
																		          "      and substr(Fecha,1,7) between " + condicion;
													          
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
			    	columnas.add(resultado.getString(5));
			        columnas.add(resultado.getString(6));
			    	
			    	filas.add(columnas);	
		    				
			  }
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
		 	     resultadoBoolean = true;
		 	 
			 
			 } else {
			 	
			     if (pestaña.getSelectedIndex() == 1)
			 	
			 	 	Mensaje(" No existen facturas asociadas a ningun producto","Información",2);
			 
			 
			 	else {
			 	     
			 	    jtIdProducto.setText("");
			 	    jtNombreProducto.setText("");
			 	  	Mensaje(" Este Producto no tiene facturas asociadas ","Información",2);
			 	  	jtIdProducto.grabFocus();
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
        
        String sentenciaSQL = "Select C.Item,C.idProducto, P.Descripcion,C.Cantidad,ifnull((Select D1.CantidadDevuelta " +
                                                                                      "From Devolucionescompraencabezado D, Devolucionescompradetalle D1 "+
                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroCompra = C.NumeroCompra and "+
                                                                                      "      D1.IdProducto = C.IdProducto ),'0') Devueltas,C.Precio,C.Subtotal "+
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
                              "Where  Nit = '" + jtIdProducto.getText() + "'";
       
        	   
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 


	             if (resultado.next()) { 
	
	                 jtNombreProducto.setText(resultado.getString(1));
	                
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
                         
                               dialogVentasDetalle.setVisible(false);
                             
                          } else
                          
                             if (fuente == jtNombreProducto)  {
				 	             	     			
	 	     			    	 if (getJTableListaValores().isVisible()) {
	 	     			    	 						 		   	  	     
				 		   	  	      if (getJTableListaValores().getSelectedRow() > -1)
				 		   	  	  	     
				 		   	  	  	      getJTableListaValores().setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
				 		   	  	  	  
				 		   	  	  	  else
				 		   	  	  	  
				 		   	  	  	     getJTableListaValores().setRowSelectionInterval(0,0);
				 		   	  	  	     
				 		   	  	  	     
				 		   	  	  	 getJTableListaValores().grabFocus();
				 		   	  	  
				 		   	  	  }
				 		   	  }
      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
        
         if (f.getComponent() == jtNombreProducto && (getDialogoListaValores() == null || !getDialogoListaValores().isVisible()) && jtNombreProducto.isFocusable() &&   mostrarListaAutomatica) {
            	
             listaValor.mostrarListaValores();
	
		} else
		
			 if (f.getComponent() != jtNombreProducto  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
                     
                  jtIdProducto.setText(getJTableListaValores().getValueAt(0,0).toString());
	              jtNombreProducto.setText(getJTableListaValores().getValueAt(0,1).toString());
	             
	              getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
                  jtNombreProducto.selectAll();
		
			 } else
		   
				   if (f.getComponent() == tablaCompras && !jtIdProducto.getText().isEmpty())
				      
				       traerInformacion();
				       
		        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
        if (f.getComponent() == jtIdProducto && !jtIdProducto.getText().isEmpty()) {
        	
        	if (!traerInformacionProveedor()) {
        		
        		mostrarListaAutomatica = false;
        	    Mensaje("Nit " +  jtIdProducto.getText( ) + " no registrado","Información",2);
        	    mostrarListaAutomatica = true;
        		jtIdProducto.setText("");
        		
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