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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.util.Vector;
import java.util.Calendar;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.Calendario;

final public class AnularCompra extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpCompras;
    private  JPanel jpNumeroCompra;
    private  JPanel jpProveedores;
    private  JPanel jpPeriodos;
   
    
    //Se delcaran los JTextField
    private  JTextField jtNumeroFactura;
    private  JTextField jtIdProveedor;
    private  JTextField jtRazonSocial;
    
    //JFormatedField
    private  JFormattedTextField jtFechaInicial;
    private  JFormattedTextField jtFechaFinal;
   
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
    //Objeto calendario de Java
    private Calendar calendar = Calendar.getInstance();
   
    //** Se declaran los JTable
    private  JTable tablaCompras;
    private  JTable tablaComprasDetalle;
    
    //** Se declaran los JLabel
    private JLabel jlNumeroFactura;
    private JLabel jlNumero;
    private JLabel jlNombre;
    private JLabel jlFechaInicial;
    private JLabel jlFechaFinal;
    
    //Se declaran los JButton
    private JButton jbFechaInicial;
    private JButton jbFechaFinal;
    
   //JDialog
   private JDialog dialogVentasDetalle;
   
   //JButton
   private JButton jbCerrar;
   
   //Long
   private Long totalCaja;
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Anular","Numero Compra","Proveedor","Fecha","Total"};
    private Object [][] datos = new Object[16][5];
    
    private Object [] nombresColumnasDetalle = {"Item","Código","Producto","Cantidad","Precio Venta","Subtotal"};
    private Object [][] datosDetalle = new Object[16][6];
    
    
    //Vectores
    private Vector columnas;

    //Modelo de datos 
    private SortableTableModel dm;
    private SortableTableModel dm1;

    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
      //Calendario
    private Calendario calendarioInicial;
    private Calendario calendarioFinal;
    
    //String
    private String fechaInicial;
    private String nombreUsuario;
    
     //JTabbedPane
    private JTabbedPane pestaña;
    
   
    //** Constructor General 
    public AnularCompra(ConectarMySQL p_conectarMySQL,JFrame p_frame,String nombreUsuario,Long totalCaja ) {

      super("Anular Compra","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
      this.totalCaja = totalCaja;
      
      this.nombreUsuario = nombreUsuario;
      
      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(10,40,780,500);
      
      jpNumeroCompra = getJPanel(0,0,100,100);
      pestaña.addTab("Número Compra",jpNumeroCompra);
      
      jpProveedores = getJPanel(0,0,100,100);
      pestaña.addTab("Proveedores",jpProveedores);
      
      jpPeriodos = getJPanel(0,0,100,100);
      pestaña.addTab("Periodos",jpPeriodos);

      pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 0) {
      			
      			jtNumeroFactura.setText("");
      			jtNumeroFactura.grabFocus();
      			
      			jpNumeroCompra.add(jpCompras);
      			
                tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(75);
		        tablaCompras.getColumnModel().getColumn(3).setMinWidth(15);
		        tablaCompras.getColumnModel().getColumn(3).setMaxWidth(2147483647);
		        
		        configurarColumnas();
      			
     
      			
      		} else
      		
      		  if (pestaña.getSelectedIndex() == 1) {
      			
      			jtIdProveedor.setText("");
                jtRazonSocial.setText("");
                jtIdProveedor.grabFocus();
      		
                jpProveedores.add(jpCompras);
                
                ocultarColumnas(tablaCompras,2);
                
      				
      		} else {
      			
      			jtFechaInicial.setText(fechaInicial);
                jtFechaFinal.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
                jtFechaInicial.grabFocus();
      			
      			jpPeriodos.add(jpCompras);
                
                tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(75);
		        tablaCompras.getColumnModel().getColumn(3).setMinWidth(15);
		        tablaCompras.getColumnModel().getColumn(3).setMaxWidth(2147483647);
		        
		        configurarColumnas();
      			
                
      			
      		}
      		
  			dm.setDataVector(datos,nombresColumnas);
            
      			
      	}
      	
      	
      });
      
      getJFrame().add(pestaña);


      //** Se agregan los JPanel

      jpCompras = getJPanel("Compras Realizadas",40, 60, 700, 350,14);
      jpNumeroCompra.add(jpCompras);
      
      //** Se declaran los JLabel

      jlNumeroFactura = getJLabel("Número Factura: ",300, 20, 130, 20);
      jpNumeroCompra.add(jlNumeroFactura);
      
      //** Se instancian los JTextField
      jtNumeroFactura = getJTextField("",410, 20, 70, 20,"Número de Factura de Venta","9");
      jtNumeroFactura.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroFactura.addFocusListener(this);
      jtNumeroFactura.addKeyListener(getValidarEntradaNumeroJTextField());
      jpNumeroCompra.add(jtNumeroFactura);

    
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
     tablaCompras.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());
     tablaCompras.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
     
     tablaCompras.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		
     		if (m.getClickCount() == 2) {
     		
     		   configurarJDialogVentasDetalle();
     		   
     		}   
     	}
     	
     });
 	  
      configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaCompras);
      scrollPane.setBounds(25, 40, 655, 276);
      jpCompras.add(scrollPane);
       
    

      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/ComprasAnuladas.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 

      jtNumeroFactura.grabFocus();
       
      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
    
      //Se agregan los JLabel
      jlNumero = getJLabel("Nit:",180, 20, 30, 20);
      jpProveedores.add(jlNumero);
      
      
      jlNombre = getJLabel("Razón Social:",340, 20, 90, 20);
      jpProveedores.add(jlNombre);
      
      
      jtIdProveedor = getJTextField("",210, 20, 105, 20,"Digíte el Número de Identificación del Proveedor","12");
      jtIdProveedor.setHorizontalAlignment(JTextField.RIGHT);
      jtIdProveedor.addKeyListener(getValidarEntradaNumeroJTextField());
      jtIdProveedor.addFocusListener(this);
      jpProveedores.add(jtIdProveedor);
      

      jtRazonSocial = getJTextField("",430, 20, 200, 20,"Digite el Nombre del Cliente o Preione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtRazonSocial,conectarMySQL);
      
      
      //jtRazonSocial.addKeyListener(listaValor);
      jtRazonSocial.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtRazonSocial.addFocusListener(this);
      jtRazonSocial.addActionListener(this);
      jpProveedores.add(jtRazonSocial);

      
       //** Elementos busqueda por fechas
      jlFechaInicial = getJLabel("Fecha Inicial:",180, 20, 80, 20);
      jpPeriodos.add(jlFechaInicial);
      
      
      jlFechaFinal = getJLabel("Fecha Final::",405, 20, 80, 20);
      jpPeriodos.add(jlFechaFinal);
      
      fechaInicial = getObtenerFecha(conectarMySQL);
      calendar.set(Integer.parseInt(fechaInicial.substring(0,4)),Integer.parseInt(fechaInicial.substring(5,7))-1,Integer.parseInt(fechaInicial.substring(8,10)));
	  calendar.add(Calendar.DAY_OF_MONTH, -15);
	  fechaInicial = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
 	   
      
      jtFechaInicial = getJFormattedTextField(fechaInicial,270, 20, 70, 20,"Digíte el Número de Identificación del Cliente","12");
      jtFechaInicial.setHorizontalAlignment(JTextField.RIGHT);
      jtFechaInicial.addKeyListener(getValidarEntradaNumeroJTextField());
      jtFechaInicial.addFocusListener(this);
      jpPeriodos.add(jtFechaInicial);
      
      jtFechaFinal = getJFormattedTextField(getObtenerFecha(conectarMySQL),490, 20, 70, 20,"Digíte el Número de Identificación del Cliente","12");
      jtFechaFinal.setHorizontalAlignment(JTextField.RIGHT);
      jtFechaFinal.addKeyListener(getValidarEntradaNumeroJTextField());
      jtFechaFinal.addFocusListener(this);
      jpPeriodos.add(jtFechaFinal);
      
      jbFechaInicial = getJButton("",345, 20, 20, 20,"Imagenes/Calendario.gif","Calendario");
      jbFechaInicial.setFocusable(false);
      jpPeriodos.add(jbFechaInicial);

      
      jbFechaFinal = getJButton("",565, 20, 20, 20,"Imagenes/Calendario.gif","Calendario");
      jbFechaFinal.setFocusable(false);
      jbFechaFinal.addActionListener(this);
      jbFechaFinal.setVisible(false);
      jpPeriodos.add(jbFechaFinal);
      
      columnas = new Vector();
      columnas.add("Anular");
      columnas.add("Numero Venta");
      columnas.add("Cliente");
      columnas.add("Fecha");
      columnas.add("FormaPago");
      columnas.add("Total");
    
    
    }
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(5);
	    tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(60);
		tablaCompras.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
		tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(210);
		tablaCompras.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(30);
		tablaCompras.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
		tablaCompras.getColumnModel().getColumn(4).setPreferredWidth(60);
		tablaCompras.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
			

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


      //**************************** Metodo actualizarEncabezado ************************

      private void actualizarEncabezado() throws Exception { 
      
          String condicion = "";
          String datos[];
          
          //Se procede a guardar los detalles
          int numFilas = obtenerFilas(tablaCompras,4);
        
          for (int i = 0; i < numFilas; i++) {
          	
          	if (tablaCompras.getValueAt(i,0).toString().equals("true")) {
          	
          	
	          	 condicion = "NumeroCompra = '" + tablaCompras.getValueAt(i,1) +"'";
	          	 
	          	 datos = new String[2];
	          	 
	          	 datos[0] = "Estado = 'A'";
	          	 datos[1] = "FechaAnulacion = '" + getObtenerFechaCompletaServidor(conectarMySQL) +"'";
	          	 
	          	 actualizar("ComprasEncabezado",datos,condicion,conectarMySQL,false);
	          	 
	          	 
	          	Long totalFactura = Long.parseLong(tablaCompras.getValueAt(i,4).toString());
                
		        totalCaja += totalFactura; //Se incrementa la caja
		              
		        //Se almacena en caja
		        datos = new String[7];
		        datos[0] = "null";
		        datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
		        datos[2] = "'" + nombreUsuario + "'";
		        datos[3] = "8";
		        datos[4] = "" + totalFactura;
		        datos[5] = "" + totalCaja;
		        datos[6] = "'COMPRA ANULADA'";
		        
		        guardar("MovimientosCaja",datos,conectarMySQL,false); 
		      
	      
	        }    
          	
          } 
          
          actualizarProductos(); //Se actualiza el Stock del producto	
          

      }
      
      //********************* configurarJDialogVentasDetalle()****************************************
     
      private void configurarJDialogVentasDetalle() {
    	
    	
    	if (dialogVentasDetalle == null) {
    		
    		  dialogVentasDetalle = new JDialog(getJFrame(),"Detalles",true);
		  	  dialogVentasDetalle.setLayout(null);
		  	  dialogVentasDetalle.setSize(695, 427);
		  	  dialogVentasDetalle.setLocationRelativeTo(null);
		  	  
		  	  //Se agrega un panel para un JTable
		  	  JPanel jpDetalleCompra = getJPanel("Detalles de la Compra",20, 10, 640, 265,14);
		  	  
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
		     
		      //** Se configura un scroll para el JTable 
		
		      JScrollPane scrollPane = new JScrollPane(tablaComprasDetalle);
		      scrollPane.setBounds(20, 20, 610, 240);
		      jpDetalleCompra.add(scrollPane);
		       
		  	  dialogVentasDetalle.add(jpDetalleCompra);
		  	  
		  	  
		      //** Se agregan los JButton
		
		      jbCerrar = getJButton("Cerrar",280, 360, 125, 25,"","");
		      jbCerrar.addActionListener(this);
		      dialogVentasDetalle.add(jbCerrar);
		      
		}
		
	    traerInformacionDetalle(tablaCompras.getValueAt(tablaCompras.getSelectedRow(),1).toString());
		dialogVentasDetalle.setVisible(true);
	
	}	  	  
      
      //**************************** Metodo actualizarProductos ************************

      private void actualizarProductos() throws Exception { 
     
             //Se procede a guardar los detalles
             int numFilas = obtenerFilas(tablaCompras,4);
        
             for (int i = 0; i < numFilas; i++) {
               
               	if (tablaCompras.getValueAt(i,0).toString().equals("true")) {
               		
				       final String sentenciaSQL = "Select idProducto,Cantidad "+
				                                   "From   ComprasDetalle "+
				                                   "Where NumeroCompra = '" + tablaCompras.getValueAt(i,1) +"'";
				                                  
				                                   
				
				        try {
				
				           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
				           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
				
				           // Se verifica si tiene datos 
				           if (resultado!=null)	{ 
				
				             if (resultado.next()) { 
				                    
				                 String codigoProducto = resultado.getString(1);
				                 String cantidad = resultado.getString(2);
				                 
								                 
					             String condicion = "IdProducto = '"+ codigoProducto +"'";
					             String datos[] = new String[] {"Stock = Stock -" + cantidad};
					             
					             actualizar("Productos",datos,condicion,conectarMySQL,false); 
				                
				
				             }
				           }
				
				        } catch (Exception e) {
				        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
				        }
			   }   
		 }	        
			          

      }

      //**************************** Metodo eliminar ************************

      private void eliminar() throws Exception  { 

			int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar esta Anular venta?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

		    if (opcion == 0) {

				 // Se especifica la condición de borrado 

				 String condicion = "";

				 eliminarRegistro("Anularventa",condicion,conectarMySQL,false); 

			}

	  }
	  
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select C.NumeroCompra,P.RazonSocial,SubString(C.Fecha,1,10),C.Total "+
                              "From   ComprasEncabezado C, Proveedores P, LotesPorProducto L "+
                              "Where  C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' and " +
                              "       C.NumeroCompra = L.NumeroCompra and L.StockInicial = L.StockDisponible ";
                              
                              
                              
         if (pestaña.getSelectedIndex() == 0) 
        	
        
         	sentenciaSQL += "and  C.NumeroCompra = '" + jtNumeroFactura.getText() + "'"; 
           
         
         else                     
                              
	       if (pestaña.getSelectedIndex() == 1) 
             
	            sentenciaSQL += "and  P.Nit = '" + jtIdProveedor.getText() + "'"; 
	         
	         else {
	             
	             String fechaFinal = jtFechaFinal.getText(); //Se le suma un dia a la fecha
	              
	             calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)));
                 calendar.add(Calendar.DAY_OF_MONTH, 1);
                 fechaFinal = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
                
	            
	             sentenciaSQL += " and  C.Fecha between '" + jtFechaInicial.getText() + "' and '" + fechaFinal + "'";
	         }       
	         
	         
        try {
               
              sentenciaSQL += " Group by C.NumeroCompra"; 
               
              // Se llama el metodo buscarRegistro de la clase ConectarMySQL
              ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

	           // Se verifica si tiene datos 
	           if (resultado != null)	{ 
	
	              
		              Boolean booleanDato = new Boolean(false);	
		              Vector <Vector > filas = new Vector <Vector>();
				
				      while (resultado.next()) {
					     
					       Vector columnas = new Vector();
					    
					    	columnas.add(booleanDato);
					    	columnas.add(resultado.getString(1));
					    	columnas.add(resultado.getString(2));
					    	columnas.add(resultado.getString(3));
					    	columnas.add(resultado.getString(4));
					    	
					    	filas.add(columnas);	
				    				
					  }
			          
			           if (filas.size() > 0) {
			                       
					 	 dm.setDataVector(filas,columnas);
				 	     resultadoBoolean = true;
				 	 
					 
					 } else 
					 	
					      if (pestaña.getSelectedIndex() == 1) {
					      
						 	    jtIdProveedor.setText("");
						 	    jtRazonSocial.setText("");
						 	  	Mensaje(" Este Proveedor no tiene facturas asociadas ","Información",2);
						 	  	jtIdProveedor.grabFocus();
						    } else
			             
				               if (pestaña.getSelectedIndex() == 2) {
			
				             	    jtFechaInicial.setText(fechaInicial);
					             	jtFechaFinal.setText(getObtenerFecha(conectarMySQL));
					             	jtFechaInicial.grabFocus();
					             	Mensaje("No existen ventas asociadas en este periodo","Información",2);
					             	
				             	
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
        
        String sentenciaSQL = "Select C.Item,C.idProducto, P.Descripcion,C.Cantidad,C.Precio,C.Subtotal "+
                              "From   ComprasDetalle C, Productos P "+
                              "Where  C.IdProducto = P.IdProducto and C.NumeroCompra = '"+ numeroFactura+"'";
       	
	  
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
	            
		 	 dm1.setDataVector(filas,columnas);
		 	 tablaComprasDetalle.setModel(dm1);
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
            if (fuente == Bguardar) {

                     try {

                          String mensaje = "Compra Anulada";

                          actualizarEncabezado(); 

                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 

                          Mensaje(mensaje,"Información",1);

                          limpiar();   //Se limpia la forma 

                       } catch (Exception e) {

                           conectarMySQL.rollback();

                           Mensaje("Error al Insertar Anularventa" +e,"Error",0); 

				      }
            } else 

                if (fuente == Beliminar) { 

                   try {

                       eliminar(); 

                       conectarMySQL.commit();   //Se registra los cambios en la base de dato 

                       Mensaje("Anularventa Eliminado","Información",JOptionPane.INFORMATION_MESSAGE);

                       limpiar();  // Se limpia la forma 

                 } catch (Exception e) {

                       conectarMySQL.rollback();

                       Mensaje("No se puede eliminar Anularventa" + e,"Error",JOptionPane.ERROR_MESSAGE); 

                  }

                }  else 

                    if (fuente == Bbuscar) { 

                       traerInformacion();

                    } else 

                       if (fuente == Bsalir) {

                         ocultarJFrame(); 

                       } else
                           
	                            if (fuente == jtRazonSocial)  {
						 	             	     				
			 	     				 if (getJTableListaValores().isVisible()) {
																 		   	  	     
						 		   	  	      if (getJTableListaValores().getSelectedRow() > -1)
						 		   	  	  	     
						 		   	  	  	      getJTableListaValores().setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
						 		   	  	  	  
						 		   	  	  	  else
						 		   	  	  	  
						 		   	  	  	     getJTableListaValores().setRowSelectionInterval(0,0);
						 		   	  	  	     
						 		   	  	  	     
						 		   	  	  	 getJTableListaValores().grabFocus();
						 		   	  	  
						 		   	  	  }
						 		   	  }	else
						 		   	    
			                              	 if (fuente == jbFechaInicial) {
			                              	    
			                              	    if (calendarioInicial == null)
			                              	    	
			                              	 	    calendarioInicial = new Calendario(jtFechaInicial,getJFrame(),360,140);
			                              	 	    
			                              	 	else
			                              	 	     
			                              	 	     calendarioInicial.mostrarCalendario(true);    
			                              	 	
			                              	 } else
			                              	
					                              	 if (fuente == jbFechaFinal) {
					                              	    
					                              	    if (calendarioFinal == null)
					                              	    	
					                              	 	    calendarioFinal = new Calendario(jtFechaFinal,getJFrame(),360,140);
					                              	 	    
					                              	 	else
					                              	 	     
					                              	 	     calendarioFinal.mostrarCalendario(true);    
					                              	 	
					                              	 }   else
								                             
								                              if (fuente == jbCerrar) {
								                             
								                                   dialogVentasDetalle.setVisible(false);
								                                 
								                              } 
      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
        
         if (f.getComponent() == jtRazonSocial && (getDialogoListaValores() == null || !getDialogoListaValores().isVisible()) && jtRazonSocial.isFocusable() &&   mostrarListaAutomatica) {
            	
             listaValor.mostrarListaValores();
	
		}  else
          
              if (f.getComponent() != jtRazonSocial &&  getDialogoListaValores() != null && getDialogoListaValores().isVisible()) {
            
                	//Se oculta el scroll de la lista de valores
					jtIdProveedor.setText(getJTableListaValores().getValueAt(0,0).toString()); 
					jtRazonSocial.setText(getJTableListaValores().getValueAt(0,1).toString());
				
					getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
					jtRazonSocial.selectAll();
				
				} else

				   if (f.getComponent() == tablaCompras && !jtRazonSocial.getText().isEmpty())
				      
				        traerInformacion();
							       
        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
	    
        if (f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField") || f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JFormattedTextField"))
			          
			  ((JTextField)f.getComponent()).selectAll();
	    

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
        if (f.getComponent() == jtNumeroFactura && !jtNumeroFactura.getText().isEmpty()) {
        	
        	if (!traerInformacion()) {
        		
        		Mensaje("Numero de factura no registrado","Información",2);
        		jtNumeroFactura.setText("");
        		jtNumeroFactura.grabFocus();
        	
        	} else {
        		
        		tablaCompras.grabFocus();
        	    tablaCompras.setRowSelectionInterval(0,0);
        	}
        	
        } else
           
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
        	
        } else
           
          if (f.getComponent() == jtFechaFinal && !jtFechaFinal.getText().isEmpty()) {
        	
        	    
        	    traerInformacion();
        	
          } 
        
        

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());
        
        mostrarListaAutomatica = true;

      }

    
      public class MyJCheckBoxRenderer extends JCheckBox implements TableCellRenderer {
    
        public MyJCheckBoxRenderer() {
            super();
            setHorizontalAlignment(JCheckBox.CENTER);
        }
    
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
           
                setForeground(table.getSelectionForeground());
                super.setBackground(table.getSelectionBackground());
           
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
    
            // Select the current value
            
            
            setSelected((Boolean)(value == null ? false : value));
            
            return this;
        }
    }
    
    
    
    public class MyJCheckBoxEditor extends DefaultCellEditor {
       
        public MyJCheckBoxEditor() {
            
            super(new JCheckBox());
            
        }
        
    }
}