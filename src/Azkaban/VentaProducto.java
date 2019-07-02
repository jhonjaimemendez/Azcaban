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

final public class VentaProducto extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpVentas;
    private  JPanel jpTodos;
    private  JPanel jpProductos;
    
    
    //Se delcaran los JTextField
    private  JTextField jtCodigo;
    private  JTextField jtDescripcion;
    private  JTextField jtTotalUnidades;
    private  JTextField jtTotalUnidadesDevueltas;
    private  JTextField jtTotalUnidadesPagadas;
    
    private  JTextField jtTotalVentas;
    private  JTextField jtTotalDevuelto;
    private  JTextField jtTotalPagado;
   
    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
    //** Se declaran los JTable
    private  JTable tablaVentasProducto;
    private  JTable tablaVentasDetalle;
    
    
    //** Se declaran los JLabel
    private JLabel jlCodigo;
    private JLabel jlDescripcion;
    private JLabel jlTotalUnidades;
    private JLabel jlTotalUnidadesDevueltas;
    private JLabel jlTotalUnidadesPagadas;
    private JLabel jlTotalVentas;
    
    
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
    
     
   //JDialog
   private JDialog dialogVentasDetalle;
   
   
   //JButton
   private JButton jbCerrar;
  
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Numero Factura","Fecha","Código","Producto","Cantidad","Total"};
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
    
    //JTabbedPane
    private JTabbedPane pestaña;
    
    //** Constructor General 
    public VentaProducto(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Ventas Por Producto","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
       
       //** Se agregan los JPanel

      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(5,45,775,490);
      
      jpProductos = getJPanel(0,0,100,100);
      pestaña.addTab("Productos  ",jpProductos);

      jpTodos = getJPanel(0,0,100,100);
      pestaña.addTab("Consolidado   ",jpTodos);
       
      jpVentas = getJPanel("Ventas Realizadas",35, 60, 700, 395,14);
      jpProductos.add(jpVentas);
      
       pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 1) {
      		  	
      		  
      		   jpTodos.add(jpVentas); 
      		   jlTotalUnidades.setVisible(false);
      		   jlTotalUnidadesDevueltas.setVisible(false);
               jlTotalUnidadesPagadas.setVisible(false);
              
               jtTotalUnidades.setVisible(false);
               jtTotalUnidadesDevueltas.setVisible(false);
               jtTotalUnidadesPagadas.setVisible(false);
               
               jtTotalVentas.setText("");
	           jtTotalDevuelto.setText("");	
               jtTotalPagado.setText("");
      		   
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
	           jtTotalDevuelto.setText("");	
               jtTotalPagado.setText("");
               jtTotalUnidadesDevueltas.setText("");
               jtTotalUnidadesPagadas.setText("");
      		   
	           jlTotalUnidades.setVisible(true);
	           jlTotalUnidadesDevueltas.setVisible(true);
               jlTotalUnidadesPagadas.setVisible(true);
               
               jtTotalUnidades.setVisible(true);
               jtTotalUnidadesDevueltas.setVisible(true);
               jtTotalUnidadesPagadas.setVisible(true);
              
               
               
               jtCodigo.setText("");
               jtDescripcion.setText("");
               jcPeriodos.setSelectedIndex(0);
	           
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
     tablaVentasProducto.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		
     		if (m.getClickCount() == 2) {
     		
     		 if (pestaña.getSelectedIndex() == 0)
     		 
     		   configurarJDialogVentasDetalle();
     		   
     		}   
     	}
     	
     });
 	  
     
     ocultarColumnas(tablaVentasProducto,2);
     ocultarColumnas(tablaVentasProducto,3);
	 
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaVentasProducto);
      scrollPane.setBounds(25, 25, 655, 276);
      jpVentas.add(scrollPane);
       
       
     
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
    
      columnas = new Vector();
      columnas.add("NumeroVenta");
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
      jlCodigo = getJLabel("Código:",220, 20, 50, 20);
      jpProductos.add(jlCodigo);
      
      jlDescripcion = getJLabel("Descripción:",400, 20, 80, 20);
      jpProductos.add(jlDescripcion);
      
      jlTotalUnidades  = getJLabel("Unidades Vendidas:",5, 305, 140, 20);
      jlTotalUnidades.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalUnidades);
      
      jlTotalUnidadesDevueltas = getJLabel("Unidades Devueltas:",5, 335, 140, 20);
      jlTotalUnidadesDevueltas.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalUnidadesDevueltas);
    
      jlTotalUnidadesPagadas = getJLabel("Unidades Pagadas:",5, 365, 140, 20);
      jlTotalUnidadesPagadas.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalUnidadesPagadas);
      
      jlTotalVentas  = getJLabel("Total Vendidas:",470, 305, 100, 20);
      jlTotalVentas.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalVentas);
      
      JLabel jlTotalDevuelto  = getJLabel("Total Devuelto:",470, 335, 100, 20);
      jlTotalDevuelto.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalDevuelto);
      
      JLabel jlTotalPagado  = getJLabel("Total Pagado:",470, 365, 100, 20);
      jlTotalPagado.setHorizontalAlignment(JLabel.RIGHT);
      jpVentas.add(jlTotalPagado);
      
      
      
      
      
      jtCodigo = getJTextField("",270, 20, 105, 20,"Digíte el Número de Identificación del Producto","12");
      jtCodigo.setHorizontalAlignment(JTextField.RIGHT);
      jtCodigo.addKeyListener(getValidarEntradaNumeroJTextField());
      jtCodigo.addFocusListener(this);
      jpProductos.add(jtCodigo);
      
      jtTotalUnidades  = getJTextField("Total Unidades Vendidas",160, 305, 80, 20,"",false);
      jtTotalUnidades.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalUnidades);
      
      jtTotalUnidadesDevueltas = getJTextField("Total Unidades Devueltas",160, 335, 80, 20,"",false);
      jtTotalUnidadesDevueltas.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalUnidadesDevueltas);
      
      jtTotalUnidadesPagadas = getJTextField("Total Unidades Pagadas",160, 365, 80, 20,"",false);
      jtTotalUnidadesPagadas.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalUnidadesPagadas);     
      
      jtTotalVentas  = getJTextField("Total Vendidas",578, 305, 100, 20,"",false);
      jtTotalVentas.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalVentas);
      
      jtTotalDevuelto  = getJTextField("Total Devuelto",578, 335, 100, 20,"",false);
      jtTotalDevuelto.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalDevuelto);
      
      jtTotalPagado  = getJTextField("Total Pagado",578, 365, 100, 20,"",false);
      jtTotalPagado.setHorizontalAlignment(JTextField.RIGHT);
      jpVentas.add(jtTotalPagado);  
     
      
      jtDescripcion = getJTextField("",490, 20, 240, 20,"Digite el Nombre del Cliente o Presione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtDescripcion,conectarMySQL);
      
      
      //jtRazonSocial.addKeyListener(listaValor);
      jtDescripcion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtDescripcion.addKeyListener(listaValor);
      jtDescripcion.addFocusListener(this);
      jtDescripcion.addActionListener(this);
      jpProductos.add(jtDescripcion);
      
      jcPeriodos =  getJComboBox(50,20,130,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Dia");
      jcPeriodos.addItem("Quincena");
      jcPeriodos.addItem("Mes");
      jcPeriodos.addItem("Trimestre");
      jpProductos.add(jcPeriodos);
    
    
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
		
	    traerInformacionDetalle(tablaVentasProducto.getValueAt(tablaVentasProducto.getSelectedRow(),2).toString());
		dialogVentasDetalle.setVisible(true);
	
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
      
     
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        String sentenciaSQL = "";
       
           if (pestaña.getSelectedIndex() == 1)
         
         
         
	            sentenciaSQL    =   "Select  P.IdProducto,P.Descripcion,Sum(V.Cantidad),Sum(V.subtotal),Sum(ifnull((Select D.Total " +
							                                                                                         "From DevolucionesVentaencabezado D "+
							                                                                                         "Where  D.NumeroVenta = V.NumeroVenta),'0')) "+
	                                "From VentasEncabezado VE,VentasDetalle V, Productos P "+ 
	                                "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and V.IdProducto = P.IdProducto "+
	                                "Group by P.IdProducto, P.Descripcion"; 
	                                
	                                
                              
         	else
               
               if (jcPeriodos.getSelectedIndex() == 0)
                
	                 sentenciaSQL    =   "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal,   ifnull((Select D1.CantidadDevuelta " +
													                                                                                      "From DevolucionesVentaencabezado D, DevolucionesVentadetalle D1 "+
													                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroVenta = VE.NumeroVenta and "+
													                                                                                      "      D1.IdProducto = P.IdProducto ),'0') Devueltas "+
		                                 "From VentasEncabezado VE,VentasDetalle V, Productos P "+ 
		                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and"+
		                                 "                        V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'";
		                                 
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                
	                 sentenciaSQL    =   "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal, ifnull((Select D1.CantidadDevuelta " +
													                                                                                      "From DevolucionesVentaencabezado D, DevolucionesVentadetalle D1 "+
													                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroVenta = VE.NumeroVenta and "+
													                                                                                      "      D1.IdProducto = P.IdProducto ),'0') Devueltas "+
		                                 "From VentasEncabezado VE,VentasDetalle V, Productos P "+ 
		                                 "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and"+
		                                 "                       V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'"+
		                                 "                       and Substr(VE.Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'";
		                                 
	                
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
        	                  
		                       
				                 sentenciaSQL    =  "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal,  ifnull((Select D1.CantidadDevuelta " +
																	                                                                                      "From DevolucionesVentaencabezado D, DevolucionesVentadetalle D1 "+
																	                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroVenta = VE.NumeroVenta and "+
																	                                                                                      "      D1.IdProducto = P.IdProducto ),'0') Devueltas "+
					                                "From VentasEncabezado VE,VentasDetalle V, Productos P "+ 
					                                "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and"+
					                                "                       V.IdProducto = P.IdProducto and V.IdProducto = '"+ jtCodigo.getText() +"'"+
					                                "                       and substr(VE.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
							                        "                       and Substr(VE.Fecha,1,10) " + condicion;
				     } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                
					                 sentenciaSQL    =  "Select  V.NumeroVenta,Substr(VE.Fecha,1,10),P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal, ifnull((Select D1.CantidadDevuelta " +
																                                                                                      "From DevolucionesVentaencabezado D, DevolucionesVentadetalle D1 "+
																                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroVenta = VE.NumeroVenta and "+
																                                                                                      "      D1.IdProducto = P.IdProducto ),'0') Devueltas "+
						                                "From VentasEncabezado VE,VentasDetalle V, Productos P "+ 
						                                "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and"+
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
					        	                      
			        	                      
						                                 sentenciaSQL    =  	"Select  V.NumeroVenta,VE.Fecha,P.IdProducto,P.Descripcion,V.Cantidad,V.subtotal, ifnull((Select D1.CantidadDevuelta " +
																					                                                                                      "From DevolucionesVentaencabezado D, DevolucionesVentadetalle D1 "+
																					                                                                                      "Where D.NumeroDevolucion = D1.NumeroDevolucion and D.NumeroVenta = VE.NumeroVenta and "+
																					                                                                                      "      D1.IdProducto = P.IdProducto ),'0') Devueltas "+
												                                "From VentasEncabezado VE,VentasDetalle V, Productos P "+ 
												                                "Where VE.NumeroVenta = V.NumeroVenta and VE.estado ='G' and"+
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
              long totalesDevueltos = 0;
              long unidadesDevueltas = 0;
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
			       if (pestaña.getSelectedIndex() == 1) {
			     
			        columnas.add("");
			        columnas.add("");
			        columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	
			    	Long subTotal = resultado.getLong(4);
			    	
	                columnas.add(getFormatoNumero(subTotal));
	                
	                Long totalDevuelto = resultado.getLong(5);
	                
	                totalesDevueltos += totalDevuelto;
                  	                
	                totales += subTotal;
			    
			     } else {
			    	
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	
			    	Long cantidad = resultado.getLong(5);
			    	Long subTotal = resultado.getLong(6);
			    	Long unidades = resultado.getLong(7);
			    	
			    	
	                columnas.add(getFormatoNumero(cantidad));
	                columnas.add(getFormatoNumero(subTotal));
                    
                    cantidades += cantidad;
                    totales += subTotal;
                    unidadesDevueltas += unidades;
			    	
			    }
			    
			    	
			     filas.add(columnas);	
		    				
			  }
	           
	           
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 
			 	 jtTotalVentas.setText(getFormatoNumero(totales));
			 	 jtTotalDevuelto.setText(getFormatoNumero(totalesDevueltos));
			 	 jtTotalPagado.setText(getFormatoNumero(totales - totalesDevueltos));
			 
			 	 if (pestaña.getSelectedIndex() == 0) {
				 	 
				 	 jtTotalUnidades.setText(String.valueOf(cantidades));
				 	 jtTotalUnidadesDevueltas.setText(getFormatoNumero(unidadesDevueltas));
				 	 jtTotalUnidadesPagadas.setText(getFormatoNumero(cantidades - unidadesDevueltas));
				 	 
			 	 }
			 	 
			 	 
			 	 
			 	 resultadoBoolean = true;
		 	 
			 
			 } else {
			 	
			    if (pestaña.getSelectedIndex() == 1)
			 	
			 	 
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

               } else 
			 		   	if (fuente == jbCerrar) {
                         
                               dialogVentasDetalle.setVisible(false);
                             
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
				 		   	     	 jtTotalUnidades.setText("");
							         jtTotalVentas.setText("");
							         jtTotalDevuelto.setText("");	
						             jtTotalPagado.setText("");
						             jtTotalUnidadesDevueltas.setText("");
						             jtTotalUnidadesPagadas.setText("");
						             traerInformacion();
				 		   	     	 
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