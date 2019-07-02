package com.JASoft.azkaban;

import java.awt.Dimension;

import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.sql.CallableStatement;



import java.util.Vector;
import java.util.Calendar;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.Calendario;

final public class DevolucionPorVenta extends CrearJFrame implements ActionListener, FocusListener , ListSelectionListener{

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpVentasEncabezado;
    private  JPanel jpVentasDetalle;
    private  JPanel jpNumeroVenta;
    private  JPanel jpCliente;
    private  JPanel jpPeriodos;
    
    
    //Se delcaran los JTextField
    private  JTextField jtNumeroFactura;
    private  JTextField jtIdCliente;
    private  JTextField jtNombreCliente;
   
    
    //JFormatedField
    private  JFormattedTextField jtFechaInicial;
    private  JFormattedTextField jtFechaFinal;
    
    //JComboBox
    private  JComboBox jcTipoId;
   
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
    //Objeto calendario de Java
    private Calendar calendar = Calendar.getInstance();
    
    
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
    
    //JTabbedPane
    private JTabbedPane pestaña;
    
    
   //JDialog
   private JDialog dialogVentasDetalle;
   
   //JButton
   private JButton jbCerrar;
   
   //** CallableStatement
   private CallableStatement callStmt;
   
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnasEncabezado = {"Numero Devolución","Cliente","Fecha","Total","TipoId","Id"};
    private Object [][] datosEncabezado = new Object[7][3];
    
    private Object [] nombresColumnas = {"Item","Producto","Devueltas"};
    private Object [][] datos = new Object[9][3];
    
    
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
    private String numeroVenta;
    private String tipoId;
    private String Id;
    
   
   //** Int
   private int filaSeleccionada = 0;
   
   
    //** Constructor General 
    public DevolucionPorVenta(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Devolución Por Venta","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;

      pestaña = new JTabbedPane();
      pestaña.setBounds(10,40,780,500);
      
      jpNumeroVenta = getJPanel(0,0,100,100);
      pestaña.addTab("Número Devolución",jpNumeroVenta);
      
      jpCliente = getJPanel(0,0,100,100);
      pestaña.addTab("Clientes",jpCliente);
      
      jpPeriodos = getJPanel(0,0,100,100);
      pestaña.addTab("Periodos",jpPeriodos);
      
      pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 0) {
      			
      			jtNumeroFactura.setText("");
      			jtNumeroFactura.grabFocus();
      			
      			jpNumeroVenta.add(jpVentasEncabezado);
                jpNumeroVenta.add(jpVentasDetalle);
                
                tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(75);
		        tablaVentas.getColumnModel().getColumn(1).setMinWidth(15);
		        tablaVentas.getColumnModel().getColumn(1).setMaxWidth(2147483647);
		        
		        configurarColumnasEncabezado();
		        
		        tipoId = null;
                Id = null;

      			
     
      			
      		} else
      		
      		  if (pestaña.getSelectedIndex() == 1) {
      			
      			jtIdCliente.setText("");
                jtNombreCliente.setText("");
                jcTipoId.setSelectedIndex(0);
                jcTipoId.grabFocus();
                
                jpCliente.add(jpVentasEncabezado);
                jpCliente.add(jpVentasDetalle);
                
                ocultarColumnas(tablaVentas,1);
	
      		
      				
      		} else {
      			
      			jtFechaInicial.setText(fechaInicial);
                jtFechaFinal.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
                jtFechaInicial.grabFocus();
      			
      			jpPeriodos.add(jpVentasEncabezado);
                jpPeriodos.add(jpVentasDetalle);
                
                tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(75);
		        tablaVentas.getColumnModel().getColumn(1).setMinWidth(15);
		        tablaVentas.getColumnModel().getColumn(1).setMaxWidth(2147483647);
                
                configurarColumnasEncabezado();
      			
      		}
      		
      		
  			dm1.setDataVector(datos,nombresColumnas);
            dm.setDataVector(datosEncabezado,nombresColumnasEncabezado);
  		
      			
      	}
      	
      	
      });
      
      getJFrame().add(pestaña);
      
      //** Se agregan los JPanel

      jpVentasEncabezado = getJPanel("Devoluciones Encabezado",40, 50, 700, 180,14);
      jpNumeroVenta.add(jpVentasEncabezado);
      
      jpVentasDetalle = getJPanel("Detalle Devoluciones ",40, 240, 700, 220,14);
      jpNumeroVenta.add(jpVentasDetalle);
      
      
      //** Se declaran los JLabel
     
      jlNumeroFactura = getJLabel("Número Devolución: ",280, 20, 130, 20);
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
               
            return true;  

         }

      };
      
     dm.setDataVector(datosEncabezado,nombresColumnasEncabezado);
     
    
     tablaVentas = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaVentas.getSelectionModel().addListSelectionListener(this);
     tablaVentas.addFocusListener(this);
     
     configurarColumnasEncabezado();
     
	 //Se ocultan las columnas      	
	 ocultarColumnas(tablaVentas,3);
	 ocultarColumnas(tablaVentas,4);
     ocultarColumnas(tablaVentas,5);
     
     //** Se configura un scroll para el JTable 

     JScrollPane scrollPaneEncabezado = new JScrollPane(tablaVentas);
     scrollPaneEncabezado.setBounds(24, 30, 655, 132);
     jpVentasEncabezado.add(scrollPaneEncabezado);
       


      // se configura el modelo para la tabla
      dm1 = new SortableTableModel() {

          //Se especifica el serial para la serializacion
          static final long serialVersionUID = 19781212;

          public Class getColumnClass(int col) {

              return String.class;

          }

         public boolean isCellEditable(int row, int col) {
             
             filaSeleccionada = row;
               
               return false;  

         }
         
         
         public void setValueAt(Object obj, int row, int col) {
	       	
	          super.setValueAt(obj, row, col); 
	        
	          
	       }

      };

     dm1.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla
     
     
     tablaVentasDetalle = getJTable(dm1,true); //Se instancia el JTable con el modelo de datos
     tablaVentasDetalle.addFocusListener(this);
      
      configurarColumnas();
      
      
      //** Se configura un scroll para el JTable 
      JScrollPane scrollPane = new JScrollPane(tablaVentasDetalle);
      scrollPane.setBounds(23, 30, 655, 165);
      jpVentasDetalle.add(scrollPane);
       
    

      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/VentasDevolucion.gif")).getImage());

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
      
      
      jlFechaFinal = getJLabel("Fecha Final:",405, 20, 80, 20);
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
      jbFechaFinal.setFocusable(false);
      jbFechaFinal.addActionListener(this);
      jpPeriodos.add(jbFechaFinal);
      
      columnas = new Vector();
      columnas.add("Devolver");
      columnas.add("Item");
      columnas.add("Producto");
      columnas.add("Cantidad");
       columnas.add("Cantidad");
      
      
      try {
		 		      
	      callStmt = conectarMySQL.getConexion().prepareCall("{call ActualizarLotesDevolucion(?,?,?)}"); //Se especifica la sentencia que actualiza los productos
	    
      } catch (Exception e) {
      	
      	 e.printStackTrace();
      }		
     
   
    }
    
    
  //**************************** Metodo configurarColumnasEncabezado() ************************

    private void configurarColumnasEncabezado() {
       
       	tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(60);
       	tablaVentas.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(260);
	    tablaVentas.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
	    tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(100);
	    tablaVentas.getColumnModel().getColumn(2).setCellRenderer(getAlinearDerecha());
		    
    
    }
    
    
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaVentasDetalle.getColumnModel().getColumn(0).setPreferredWidth(15);
		tablaVentasDetalle.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaVentasDetalle.getColumnModel().getColumn(1).setPreferredWidth(511);
	    tablaVentasDetalle.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		tablaVentasDetalle.getColumnModel().getColumn(2).setPreferredWidth(44);
		tablaVentasDetalle.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
	
	}
	
	
      //******************* ConvertirNumeroSinFormato() *********************
	  
	  
	  public String convertirNumeroSinFormato(String numeroConFormato) {
	  	  
	  	  int posicionPuntoDecimal = numeroConFormato.indexOf('.');
	  	  
	  	  String resultado = numeroConFormato;
	  	  
	  	  if (posicionPuntoDecimal >= 0) {
	  	  	
		  	  int numeroDecimas = numeroConFormato.length() - ( posicionPuntoDecimal + 1);
		  	  
		  	  Double valor = new Double(numeroConFormato);
		  	  
		  	  valor = valor * Math.pow(10,numeroDecimas);
		  	
		  	  resultado = String.valueOf(valor.intValue());
		  	  
		 } 	 
		 
		 
		 return resultado; 
	  }
    
	


    
    //******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		String sentencia = "Select Case TipoId WHEN 'C' THEN 'Cedula'"+ 
		                   "                   WHEN 'T' THEN 'Tarjeta Id.'"+
		                   "                   Else 'Pasaporte'"+
		                   "                   End Tipo, idCliente Identificacion,Concat(Nombre,Concat(' ',Apellido)) Nombres "+
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
    
   

     //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
          
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select D.NumeroDevolucion,Concat(C.Nombre,Concat(' ',C.Apellido)),Substr(V.Fecha,1,10),V.Total,V.TipoId,V.IdCliente "+
                              "From   DevolucionesVentaEncabezado D, VentasEncabezado V, Clientes C "+
                              "Where  D.NumeroVenta = V.NumeroVenta and V.IdCliente = C.IdCliente  ";
                 
                             
         if (pestaña.getSelectedIndex() == 0) 
         	
        
         	sentenciaSQL += "and D.NumeroDevolucion = '" + jtNumeroFactura.getText() + "'"; 
           
         
         else                     
              
               if (pestaña.getSelectedIndex() == 1) 
            
	             sentenciaSQL += "and V.TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +
	                           "' and V.idCliente ='" + jtIdCliente.getText() + "'"; 
	          
	         
	         else {
	             
	             String fechaFinal = jtFechaFinal.getText(); //Se le suma un dia a la fecha
	              
	             calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)));
                 calendar.add(Calendar.DAY_OF_MONTH, 1);
                 fechaFinal = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
                
	            
	             sentenciaSQL += " and  D.Fecha between '" + jtFechaInicial.getText() + "' and '" + fechaFinal + "'";
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
					    	columnas.add(getFormatoNumero(resultado.getString(4)));
					    	columnas.add(resultado.getString(5));
					    	columnas.add(resultado.getString(6));
					    	
					    	filas.add(columnas);	
				    				
					  }
			          
			           if (filas.size() > 0) {
			                       
					 	 dm.setDataVector(filas,columnas);
					 	 tablaVentas.grabFocus();
					 	 tablaVentas.setRowSelectionInterval(0,0);
				 	     resultadoBoolean = true;
				 	 
					 
					 } else 
					 
					 	 if (pestaña.getSelectedIndex() == 1)  {
					             
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
    
    final private void traerInformacionDetalle(String numeroFactura) { 

        
        String sentenciaSQL = "Select  V.Item,P.Descripcion,V.CantidadDevuelta "+
                              "From    DevolucionesVentaDetalle V, Productos P "+
                              "Where  V.idProducto = P.idProducto  and V.NumeroDevolucion = " + numeroFactura; 
                              
      
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
					    	columnas.add(getFormatoNumero(resultado.getString(3)));
					    	
					
					    	filas.add(columnas);	
				    				
					  }
			          
			           if (filas.size() > 0) {
			                       
					 	 dm1.setDataVector(filas,columnas);
				 	 
					 } else
					     
					     dm1.setDataVector(datos,nombresColumnas); 
					 	
			} 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
      
    }
	
	
   //*****************************Metodo traerInformacionCiente() ******************************************
    
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
  
    //**************************** Metodo validarElementoChequeado() ************************
  
    
    private boolean validarElementoChequeado() {
    	
    	int numFilas = obtenerFilas(tablaVentas,1);
    	
    	boolean sw = false;
    	
    	int i = 0;
    	
    	while (i < numFilas && !sw) {
    		
    		if (tablaVentasDetalle.getValueAt(i,0).toString().equals("true")) 
    		
    		  sw = true;
    		  
    		else
    		  
    		  i++;  
    		
    	}
    	
    	
    	return sw;
    	
    	
    }
  
 
      //************************ Metodo valueChanged() ****************************************
      
       public void valueChanged(ListSelectionEvent e) { 
      
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
          
           if (!e.getValueIsAdjusting() && tablaVentas.getSelectedRow() > -1) {
             
	             if (tablaVentas.getValueAt(0,0) != null) {
		             
		             numeroVenta  = tablaVentas.getValueAt(tablaVentas.getSelectedRow(),0).toString();
		             tipoId = tablaVentas.getValueAt(tablaVentas.getSelectedRow(),3).toString();
		             Id = tablaVentas.getValueAt(tablaVentas.getSelectedRow(),4).toString();
		          
		             traerInformacionDetalle(numeroVenta);
		         }    
             
           }  
       }     
      
      //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

         // Se verifica el componente que genero la acccion

         if (fuente == Blimpiar) {

            limpiar();

         }  else 

                    if (fuente == Bbuscar) { 

                       traerInformacion();

                    } else 

                       if (fuente == Bsalir) {

                         ocultarJFrame(); 

                       } else
                           
	                            
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
            	
             listaValor.mostrarListaValores(340,-50);
	
		} else
			
			 if (f.getComponent() != jtNombreCliente  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
                     
                  jcTipoId.setSelectedItem(getJTableListaValores().getValueAt(0,0).toString());
                  jtIdCliente.setText(getJTableListaValores().getValueAt(0,1).toString());
	              jtNombreCliente.setText(getJTableListaValores().getValueAt(0,2).toString());

	             
	              getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
                  jtNombreCliente.selectAll();
                  
                  
			      traerInformacion();
		
			 }else

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
          

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());
        
        mostrarListaAutomatica = true;

      }

    
     
}