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
import java.util.Calendar;

import java.text.SimpleDateFormat;

import java.sql.ResultSet;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.Calendario;

final public class AnularVenta extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpVentas;
    private  JPanel jpNumeroVenta;
    private  JPanel jpCliente;
    private  JPanel jpPeriodos;
   
    //Se delcaran los JTextField
    private  JTextField jtNumeroFactura;
    private  JTextField jtIdCliente;
    private  JTextField jtNombreCliente;
    
     //Objeto calendario de Java
    private Calendar calendar = Calendar.getInstance();
   
    //JFormattedTextField
    private  JFormattedTextField jtFechaInicial;
    private  JFormattedTextField jtFechaFinal;
   
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
    
     //JTabbedPane
    private JTabbedPane pestaña;
     
    //** Se declaran los JComboBox
    private  JComboBox jcTipoId;

    //** Se declaran los JTable
    private  JTable tablaVentas;
    private  JTable tablaVentasDetalle;
    
    
    //** Se declaran los JLabel
    private JLabel jlNumeroFactura;
    private JLabel jlTipo;
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
   
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Anular","Numero Venta","Cliente","Fecha","Total"};
    private Object [][] datos = new Object[16][5];
    
    private Object [] nombresColumnasDetalle = {"Item","Código","Producto","Cantidad","Precio Venta","Subtotal"};
    private Object [][] datosDetalle = new Object[16][6];
    
    
    //Vectores
    private Vector columnas;

    //Modelo de datos 
    private SortableTableModel dm;
    private SortableTableModel dm1;

    
      //Calendario
    private Calendario calendarioInicial;
    private Calendario calendarioFinal;
    
     //String
    private String fechaInicial;
    private String nombreUsuario;
  
    //Long
    private Long totalCaja;
   
   
    //** Constructor General 
    public AnularVenta(ConectarMySQL p_conectarMySQL,JFrame p_frame,String nombreUsuario,Long totalCaja ) {

      super("Anular Venta","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
      this.totalCaja = totalCaja;
      
      this.nombreUsuario = nombreUsuario;
    
       
      pestaña = new JTabbedPane();
      pestaña.setBounds(10,40,780,500);
      
      jpNumeroVenta = getJPanel(0,0,100,100);
      pestaña.addTab("Número Compra",jpNumeroVenta);
      
      jpCliente = getJPanel(0,0,100,100);
      pestaña.addTab("Clientes",jpCliente);
      
      jpPeriodos = getJPanel(0,0,100,100);
      pestaña.addTab("Periodos",jpPeriodos);
      
      pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 0) {
      			
      			jtNumeroFactura.setText("");
      			jtNumeroFactura.grabFocus();
      			
      			jpNumeroVenta.add(jpVentas);
                
                tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(75);
		        tablaVentas.getColumnModel().getColumn(2).setMinWidth(15);
		        tablaVentas.getColumnModel().getColumn(2).setMaxWidth(2147483647);
		        
		        configurarColumnas();
      			
     
      			
      		} else
      		
      		  if (pestaña.getSelectedIndex() == 1) {
      			
      			jtIdCliente.setText("");
                jtNombreCliente.setText("");
                jcTipoId.setSelectedIndex(0);
                jcTipoId.grabFocus();
                
                jpCliente.add(jpVentas);
                
                ocultarColumnas(tablaVentas,2);
	
      		
      				
      		} else {
      			
      			jtFechaInicial.setText(fechaInicial);
                jtFechaFinal.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
                jtFechaInicial.grabFocus();
      			
      			jpPeriodos.add(jpVentas);
                
                tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(75);
		        tablaVentas.getColumnModel().getColumn(2).setMinWidth(15);
		        tablaVentas.getColumnModel().getColumn(2).setMaxWidth(2147483647);
                
                configurarColumnas();
      			
      		}
      		
      		
            dm.setDataVector(datos,nombresColumnas);
  		
      			
      	}
      	
      	
      });
      
      getJFrame().add(pestaña);


      //** Se agregan los JPanel

      jpVentas = getJPanel("Ventas Realizadas",40, 60, 700, 350,14);

      jpNumeroVenta.add(jpVentas);
                
      
      //** Se declaran los JLabel
      jlNumeroFactura = getJLabel("Número Factura: ",300, 20, 130, 20);
      jpNumeroVenta.add(jlNumeroFactura);
      
      //** Se instancian los JTextField
      jtNumeroFactura = getJTextField("",410, 20, 70, 20,"Número de Factura de Venta","9");
      jtNumeroFactura.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroFactura.addFocusListener(this);
      jtNumeroFactura.addKeyListener(getValidarEntradaNumeroJTextField());
      jpNumeroVenta.add(jtNumeroFactura);

    
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

     tablaVentas = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaVentas.addFocusListener(this);
     tablaVentas.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());
     tablaVentas.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
     
     tablaVentas.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		
     		if (m.getClickCount() == 2) {
     		
     		   configurarJDialogVentasDetalle();
     		   
     		}   
     	}
     	
     });
 	  
      configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaVentas);
      scrollPane.setBounds(25, 40, 655, 276);
      jpVentas.add(scrollPane);
       
       
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/VentasAnuladas.gif")).getImage());

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
      
         
      
      //** Elementos para el cliente
      jlTipo = getJLabel("Tipo:",65, 20, 50, 20);
      jpCliente.add(jlTipo);
      
      
      jlNumero = getJLabel("Número:",247, 20, 50, 20);
      jpCliente.add(jlNumero);
      
      
      jlNombre = getJLabel("Nombre:",440, 20, 50, 20);
      jpCliente.add(jlNombre);
      
      jcTipoId = getJComboBox(110, 20, 105, 20,"Selecione el Tipo de Identificación del Cliente");
      jcTipoId.addItem("Cedula");
      jcTipoId.addItem("Tarjeta Id.");
      jcTipoId.addItem("Pasaporte");
      jpCliente.add(jcTipoId);
      
      
      jtIdCliente = getJTextField("",305, 20, 100, 20,"Digíte el Número de Identificación del Cliente","12");
      jtIdCliente.setHorizontalAlignment(JTextField.RIGHT);
      jtIdCliente.addKeyListener(getValidarEntradaNumeroJTextField());
      jtIdCliente.addFocusListener(this);
      jpCliente.add(jtIdCliente);
      

      jtNombreCliente = getJTextField("",500, 20, 200, 20,"Digite el Nombre del Cliente o Preione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtNombreCliente,conectarMySQL);
      
      
      jtNombreCliente.addKeyListener(listaValor);
      jtNombreCliente.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtNombreCliente.addFocusListener(this);
      jtNombreCliente.addActionListener(this);
      jpCliente.add(jtNombreCliente);

      
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
      jbFechaInicial.addActionListener(this);
      jpPeriodos.add(jbFechaInicial);

      
      jbFechaFinal = getJButton("",565, 20, 20, 20,"Imagenes/Calendario.gif","Calendario");
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

		
		tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(5);
	    tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(60);
		tablaVentas.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
		tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(210);
		tablaVentas.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(30);
		tablaVentas.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
		tablaVentas.getColumnModel().getColumn(4).setPreferredWidth(60);
		tablaVentas.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
		
		

	}
    
    //******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		String sentencia = "Select Case TipoId WHEN 'C' THEN 'Cedula'"+ 
		                   "                   WHEN 'T' THEN 'Tarjeta Id.'"+
		                   "                   Else 'Pasaporte'"+
		                   "                   End Tipo, idCliente,Concat(Nombre,Concat(' ',Apellido)) Nombres "+
		                   "From   Clientes "+
		                   "Where Nombre like '";

		return sentencia;

	}
	
	

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[2][5];
		
		objetosRetorno[0][0] = jtNombreCliente;
        objetosRetorno[0][1] = tablaVentas; 
       	objetosRetorno[0][2] = "2";
       	objetosRetorno[0][3] = jtIdCliente;
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

 

      //**************************** Metodo actualizarEncabezado ************************

      private void actualizarEncabezado() throws Exception { 
      
          String condicion = "";
          String datos[];
          
          //Se procede a guardar los detalles
          int numFilas = obtenerFilas(tablaVentas,4);
        
          for (int i = 0; i < numFilas; i++) {
          	
          	if (tablaVentas.getValueAt(i,0).toString().equals("true")) {
          	
          	
	          	 condicion = "NumeroVenta = '" + tablaVentas.getValueAt(i,1) +"'";
	          	 
	          	 datos = new String[1];
	          	 
	          	 datos[0] = "Estado = 'A'";
	          	 
	          	 actualizar("VentasEncabezado",datos,condicion,conectarMySQL,false);
	          	 
	          	 
		       	Long totalFactura = Long.parseLong(tablaVentas.getValueAt(i,4).toString());
                
		        totalCaja -= totalFactura; //Se incrementa la caja
		              
		        //Se almacena en caja
		        datos = new String[7];
		        datos[0] = "null";
		        datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
		        datos[2] = "'" + nombreUsuario + "'";
		        datos[3] = "6";
		        datos[4] = "" + totalFactura;
		        datos[5] = "" + totalCaja;
		        datos[6] = "'VENTA ANULADA'";
		        
		        
		        
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
		
		     tablaVentasDetalle = getJTable(dm1,true); //Se instancia el JTable con el modelo de datos
		     
		      //** Se configura un scroll para el JTable 
		
		      JScrollPane scrollPane = new JScrollPane(tablaVentasDetalle);
		      scrollPane.setBounds(20, 20, 610, 240);
		      jpDetalleCompra.add(scrollPane);
		       
		  	  dialogVentasDetalle.add(jpDetalleCompra);
		  	  
		  	  
		      //** Se agregan los JButton
		
		      jbCerrar = getJButton("Cerrar",280, 360, 125, 25,"","");
		      jbCerrar.addActionListener(this);
		      dialogVentasDetalle.add(jbCerrar);
		      
		}
		
	    traerInformacionDetalle(tablaVentas.getValueAt(tablaVentas.getSelectedRow(),1).toString());
		dialogVentasDetalle.setVisible(true);
	
	}	  	  
      
      //**************************** Metodo actualizarProductos ************************

      private void actualizarProductos() throws Exception { 
     
              //Se procede a guardar los detalles
              int numFilas = obtenerFilas(tablaVentas,4);
        
             for (int i = 0; i < numFilas; i++) {
               
               	if (tablaVentas.getValueAt(i,0).toString().equals("true")) {
               		
				       final String sentenciaSQL = "Select idProducto,Cantidad "+
				                                   "From   VentasDetalle "+
				                                   "Where NumeroVenta = '" + tablaVentas.getValueAt(i,1) +"'";
				                                  
				                                   
				
				        try {
				
				           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
				           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
				
				           // Se verifica si tiene datos 
				           if (resultado!=null)	{ 
				
				             if (resultado.next()) { 
				                    
				                 String codigoProducto = resultado.getString(1);
				                 String cantidad = resultado.getString(2);
				                 
								                 
					             String condicion = "IdProducto = '"+ codigoProducto +"'";
					             String datos[] = new String[] {"Stock = Stock +" + cantidad};
					             
					             actualizar("Productos",datos,condicion,conectarMySQL,false); 
				                
				
				             }
				           }
				
				        } catch (Exception e) {
				        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
				        }
			   }   
		 }	        
			          

      }

    //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select V.NumeroVenta,Concat(C.Nombre,Concat(' ',C.Apellido)),Substr(V.Fecha,1,10),V.Total "+
                              "From   VentasEncabezado V, Clientes C "+
                              "Where  V.TipoId = C.TipoId and V.IdCliente = C.IdCliente and V.Estado = 'G' ";
                              
                 
         if (pestaña.getSelectedIndex() == 0) 
         	         	
        
         	sentenciaSQL += "and  NumeroVenta = '" + jtNumeroFactura.getText() + "'"; 
           
         
         else                     
                              
	                         
          if (pestaña.getSelectedIndex() == 1) 
         	
	            sentenciaSQL += "and V.TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +
	                           "' and V.idCliente ='" + jtIdCliente.getText() + "'"; 
	         
	         else {
	                      
	             
		             String fechaFinal = jtFechaFinal.getText(); //Se le suma un dia a la fecha
		              
		             calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)));
	                 calendar.add(Calendar.DAY_OF_MONTH, 1);
	                 fechaFinal = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
	                
		            
		             sentenciaSQL += " and  Fecha between '" + jtFechaInicial.getText() + "' and '" + fechaFinal + "'";
	         }         
	   
        try {

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
                 	
	             	jcTipoId.setSelectedIndex(0);
	             	jtIdCliente.setText("");
	             	jtNombreCliente.setText("");
	             	Mensaje("El cliente no tiene asociados ventas","Información",2);
	             	jtIdCliente.grabFocus();
	             	
	             } else
	                
                     if (pestaña.getSelectedIndex() == 2)  {
		             	
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
        
        String sentenciaSQL = "Select V.Item,V.idProducto, P.Descripcion,V.Cantidad,V.Precio,V.Subtotal "+
                              "From   VentasDetalle V, Productos P "+
                              "Where  V.IdProducto = P.IdProducto and V.NumeroVenta = '"+ numeroFactura+"'";
       	
	  
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
		 	 tablaVentasDetalle.setModel(dm1);
		 	 resultadoBoolean = true;
		 	      
		 	   	
          } 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultadoBoolean;
    }
	
	//*****************************Metodo traerInformacion() ******************************************
    
    private boolean traerInformacionCliente() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select Nombre,Apellido,Direccion,Telefono,Celular,Correo,Cupo,Saldo,DivisionPolitica "+
                              "From   Clientes "+
                              "Where  TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) + "' and idCliente ='" + jtIdCliente.getText() + "'"; 
       
       
        
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 


	             if (resultado.next()) { 
	
	                 jtNombreCliente.setText(resultado.getString(1) + " " + resultado.getString(2));
	                 
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

                          String mensaje = "Venta Anulada";

                          actualizarEncabezado(); 

                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 

                          Mensaje(mensaje,"Información",1);

                          limpiar();   //Se limpia la forma 

                       } catch (Exception e) {

                           conectarMySQL.rollback();

                           Mensaje("Error al Insertar Anularventa" +e,"Error",0); 

				      }

              
            }  else 

                    if (fuente == Bbuscar) { 

                       traerInformacion();

                    } else 

                       if (fuente == Bsalir) {

                         ocultarJFrame(); 

                       }  else
                           
                            if (fuente == jtNombreCliente)  {
					 	             	     				
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
        
         if (f.getComponent() == jtNombreCliente && (getDialogoListaValores() == null || !getDialogoListaValores().isVisible()) && jtNombreCliente.isFocusable() && mostrarListaAutomatica ) {
            	
             listaValor.mostrarListaValores();
	
		} else
			
				 if (f.getComponent() != jtNombreCliente  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
	                     
	                  jcTipoId.setSelectedItem(getJTableListaValores().getValueAt(0,0).toString());
	                  jtIdCliente.setText(getJTableListaValores().getValueAt(0,1).toString());
		              jtNombreCliente.setText(getJTableListaValores().getValueAt(0,2).toString());

		             
		              getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
	                  jtNombreCliente.selectAll();
	                  
	                  
				      traerInformacion();
			
				 } else
		   
				   if (f.getComponent() == tablaVentas && !jtNombreCliente.getText().isEmpty())
				      
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
        	}
        	
        } else
           
          if (f.getComponent() == jtIdCliente && !jtIdCliente.getText().isEmpty()) {
        	
        	if (!traerInformacionCliente()) {
        		
        		mostrarListaAutomatica = false;
        		Mensaje("Identificacion " +  jtIdCliente.getText( ) + " no registrada","Información",2);
        		jtIdCliente.setText("");
        		
        	} else
        	    
        	    traerInformacion();
        	
        } else
           
          if (f.getComponent() == jtFechaFinal && !jtFechaFinal.getText().isEmpty()) {
        	    
        	    traerInformacion();
        	
        }
        
        
        
        
        mostrarListaAutomatica = true;
        		
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

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