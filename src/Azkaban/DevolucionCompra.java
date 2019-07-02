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
import javax.swing.JTabbedPane;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import javax.swing.ListSelectionModel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.Vector;
import java.util.Calendar;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.Calendario;

final public class DevolucionCompra extends CrearJFrame implements ActionListener, FocusListener , ListSelectionListener{

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpComprasEncabezado;
    private  JPanel jpComprasDetalle;
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
   
    //JTextField
    private  JTextField jtCantidadDevolverProductoJTable;
    private  JTextField jtTotal;
   
   
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
    //Objeto calendario de Java
    private Calendar calendar = Calendar.getInstance();
   
     //JTabbedPane
    private JTabbedPane pestaña;
   
    
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
   
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnasEncabezado = {"Numero Compra","Proveedor","Fecha","Total"};
    private Object [][] datosEncabezado = new Object[7][3];
    
    private Object [] nombresColumnas = {"Devolver","Item","Producto","Precio","Compradas","Devueltas","Vendidas","Devolver","Codigo"};
    private Object [][] datos = new Object[8][9];
    
    
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
    private String numeroCompra;
    
    //** DefaultCellEditor
    private DefaultCellEditor editorCantidadDevolverProductoJTable;
       
   //** Int
   private int filaSeleccionada = 0;
   
    //** Constructor General 
    public DevolucionCompra(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Devolucion Compra","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
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
      		
            detenerEditar();
  			
      		if (pestaña.getSelectedIndex() == 0) {
      			
      			jtNumeroFactura.setText("");
      			jtNumeroFactura.grabFocus();
      			
      			jpNumeroCompra.add(jpComprasEncabezado);
                jpNumeroCompra.add(jpComprasDetalle);
                
                tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(75);
		        tablaCompras.getColumnModel().getColumn(1).setMinWidth(15);
		        tablaCompras.getColumnModel().getColumn(1).setMaxWidth(2147483647);
		        
		        configurarColumnasEncabezado();
      			
     
      			
      		} else
      		
      		  if (pestaña.getSelectedIndex() == 1) {
      			
      			jtIdProveedor.setText("");
                jtRazonSocial.setText("");
                jtIdProveedor.grabFocus();
      		
                jpProveedores.add(jpComprasEncabezado);
                jpProveedores.add(jpComprasDetalle);
                
                ocultarColumnas(tablaCompras,1);
      				
      		} else {
      			
      			jtFechaInicial.setText(fechaInicial);
                jtFechaFinal.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
                jtFechaInicial.grabFocus();
      			
      			jpPeriodos.add(jpComprasEncabezado);
                jpPeriodos.add(jpComprasDetalle);
                
                tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(75);
		        tablaCompras.getColumnModel().getColumn(1).setMinWidth(15);
		        tablaCompras.getColumnModel().getColumn(1).setMaxWidth(2147483647);
		        
		        configurarColumnasEncabezado();
      			
                
      			
      		}
      		
      		
  			dm1.setDataVector(datos,nombresColumnas);
            dm.setDataVector(datosEncabezado,nombresColumnasEncabezado);
            
      			
      	}
      	
      	
      });
      
      getJFrame().add(pestaña);


      
      //** Se agregan los JPanel
      
      jpComprasEncabezado = getJPanel("Compras Encabezado",40, 50, 700, 180,14);
      jpNumeroCompra.add(jpComprasEncabezado);
      
      jpComprasDetalle = getJPanel("Detalle Compra ",40, 240, 700, 220,14);
      jpNumeroCompra.add(jpComprasDetalle);
      
      
      
      //** Se declaran los JLabel
     
      jlNumeroFactura = getJLabel("Número Factura: ",300, 20, 130, 20);
      jpNumeroCompra.add(jlNumeroFactura);
      
      JLabel jlTotal = getJLabel("Total Devoluciones: ",472, 185, 130, 20);
      jpComprasDetalle.add(jlTotal);
      
      
      
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
               
            return false;  

         }

      };
      
     dm.setDataVector(datosEncabezado,nombresColumnasEncabezado);
     
    
     tablaCompras = getJTable(dm,true); //Se instancia el JTable con el modelo de datos
     tablaCompras.getSelectionModel().addListSelectionListener(this);
     tablaCompras.addFocusListener(this);
     
     
     configurarColumnasEncabezado();
     
     
     //** Se configura un scroll para el JTable 

     JScrollPane scrollPaneEncabezado = new JScrollPane(tablaCompras);
     scrollPaneEncabezado.setBounds(24, 30, 655, 132);
     jpComprasEncabezado.add(scrollPaneEncabezado);
       


      // se configura el modelo para la tabla
      dm1 = new SortableTableModel() {

          //Se especifica el serial para la serializacion
          static final long serialVersionUID = 19781212;

          public Class getColumnClass(int col) {

              return String.class;

          }

         public boolean isCellEditable(int row, int col) {
             
             filaSeleccionada = row;
             
             if (col == 0 || col == 7)
               
               return true;
               
             else
               
               return false;  
               

         }
         
         public void setValueAt(Object obj, int row, int col) {
	       	
	          super.setValueAt(obj, row, col); 
	          
	          if (col == 0)
	            
	             sumarValoresDevueltos();  
	          
	     } 
	     
      };

     dm1.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla
     
     
     tablaComprasDetalle = getJTable(dm1,true); //Se instancia el JTable con el modelo de datos
     tablaComprasDetalle.addFocusListener(this);
     tablaComprasDetalle.getColumnModel().getColumn(0).setCellEditor(new MyJCheckBoxEditor());
     tablaComprasDetalle.getColumnModel().getColumn(0).setCellRenderer(new MyJCheckBoxRenderer());
     
     jtCantidadDevolverProductoJTable = new JTextField();
     jtCantidadDevolverProductoJTable.setName("4");
     jtCantidadDevolverProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
     jtCantidadDevolverProductoJTable.addKeyListener(new KeyAdapter() { //Se define el codigo para un evento de tipo Key
    
	      	
	      	public void  keyTyped (KeyEvent k) {
	      		
		      	if ((k.getKeyChar() != KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) ||  (jtCantidadDevolverProductoJTable.getText().length() >= 4))
			      
			           k.consume(); // Se consume o borra el caracter digitado
		}	           
			      
      });
      jtCantidadDevolverProductoJTable.addFocusListener(this);
      
         
      //Se declaran los DefaultCellEditor
      editorCantidadDevolverProductoJTable = getEditor(jtCantidadDevolverProductoJTable);
      
        
      tablaComprasDetalle.getColumnModel().getColumn(7).setCellEditor(editorCantidadDevolverProductoJTable);
     
  
 	  
      configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaComprasDetalle);
      scrollPane.setBounds(23, 30, 655, 148);
      jpComprasDetalle.add(scrollPane);
       
      jtTotal  = getJTextField("Total Vendidas",595, 185, 80, 20,"",false);
      jtTotal.setHorizontalAlignment(JTextField.RIGHT);
      jpComprasDetalle.add(jtTotal);
     
        
      //** Se configura el icono del frame
      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/ComprasDevolucion.gif")).getImage());

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
      
       //Se configura es setName de los Botones como comodines para sobreescribir el focusLost
      Blimpiar.setName("ejecutar");
      Bguardar.setName("ejecutar");
      Beliminar.setName("JButton");
      Bbuscar.setName("JButton");
      Bsalir.setName("ejecutar");
      Bimprimir.setName("JButton");
      
      
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
      columnas.add("Precio");
      columnas.add("Producto");
      columnas.add("Cantidad");
      columnas.add("Cantidad antes Devuelta");
      columnas.add("Cantidad Compradas");
      columnas.add("Cantidad a Devolver");
      columnas.add("Codigo");
      
      
    
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
    
  //**************************** Metodo configurarColumnasEncabezado() ************************

    private void configurarColumnasEncabezado() {
       
       	tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(70);
       	tablaCompras.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(340);
	    tablaCompras.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
	    tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(60);
	    tablaCompras.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
	    tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(60);
	    tablaCompras.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
    
    }
    
    
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaComprasDetalle.getColumnModel().getColumn(0).setPreferredWidth(49);
	    tablaComprasDetalle.getColumnModel().getColumn(1).setPreferredWidth(28);
		tablaComprasDetalle.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		tablaComprasDetalle.getColumnModel().getColumn(2).setPreferredWidth(190);
		tablaComprasDetalle.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaComprasDetalle.getColumnModel().getColumn(3).setPreferredWidth(71);
		tablaComprasDetalle.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
		tablaComprasDetalle.getColumnModel().getColumn(4).setPreferredWidth(66);
		tablaComprasDetalle.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
	    tablaComprasDetalle.getColumnModel().getColumn(5).setPreferredWidth(63);
		tablaComprasDetalle.getColumnModel().getColumn(5).setCellRenderer(getAlinearCentro());
	    tablaComprasDetalle.getColumnModel().getColumn(6).setPreferredWidth(67);
		tablaComprasDetalle.getColumnModel().getColumn(6).setCellRenderer(getAlinearCentro());
		tablaComprasDetalle.getColumnModel().getColumn(7).setPreferredWidth(66);
		tablaComprasDetalle.getColumnModel().getColumn(7).setCellRenderer(getAlinearCentro());
			
        ocultarColumnas(tablaComprasDetalle,8);
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
        objetosRetorno[0][1] = tablaComprasDetalle; 
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
         
         
         //Se declaran los DefaultCellEditor
         editorCantidadDevolverProductoJTable = getEditor(jtCantidadDevolverProductoJTable);
      

    }
    
    
    //*********************** Metodo sumarValoresDevueltos ************************

    public void sumarValoresDevueltos() {

    	int numFilas = obtenerFilas(tablaComprasDetalle,1);
    	
    	for (int i = 0; i < numFilas; i++) {
    		
    		if (tablaComprasDetalle.getValueAt(i,0).toString().equals("true")) {
    			
    			long suma =+ Long.parseLong(convertirNumeroSinFormato(tablaComprasDetalle.getValueAt(i,3).toString())) * Long.parseLong(convertirNumeroSinFormato(tablaComprasDetalle.getValueAt(i,7).toString()));
    			jtTotal.setText(getFormatoNumero(suma));
    			
    		}
    		
    	
    	}
    	
    }
    
    //*********************** Metodo guardar ************************

    private void guardar() throws Exception { 


        final String[] datos = new String[4];

        datos[0] = "null";
        datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
        datos[2] = numeroCompra;
        datos[3] = convertirNumeroSinFormato(jtTotal.getText());
        
        //Se inserta en la base de datos
        guardar("DevolucionesCompraEncabezado",datos,conectarMySQL,false); 
        
        
     }

    //**************************** Metodo actualizarEncabezado ************************

    private void actualizarDetalles() throws Exception { 
      
          String condicion = "";
          
          //Se procede a guardar los detalles
          int numFilas = obtenerFilas(tablaComprasDetalle,4);
          int item = 1;
          
          
          for (int i = 0; i < numFilas; i++) {
          	
          	if (tablaComprasDetalle.getValueAt(i,0).toString().equals("true")) {
          	     
          	     String datosDetalle[] = new String[4];
          	     datosDetalle[0] =  "(Select Max( NumeroDevolucion) From  devolucionesCompraEncabezado)";
          	     datosDetalle[1] = String.valueOf(item);
          	     datosDetalle[2] = "'" + tablaComprasDetalle.getValueAt(i,8) +"'";
          	     datosDetalle[3] = tablaComprasDetalle.getValueAt(i,7).toString();
          	     
                 guardar("DevolucionesCompraDetalle",datosDetalle,conectarMySQL,false);

          	     
          	
	          	 condicion = "idProducto = '" + tablaComprasDetalle.getValueAt(i,2) +"'";
	          	 String cantidadDevolver =  tablaComprasDetalle.getValueAt(i,7).toString();
								                 
				 String datos[] = new String[] {"Stock = Stock -" + cantidadDevolver};
	             
	             actualizar("Productos",datos,condicion,conectarMySQL,false); 
	             
	             //Se actualiza la tabla de Lotes
	             condicion = "idProducto = '" + tablaComprasDetalle.getValueAt(i,8) +"' and NumeroCompra = " + numeroCompra;
	             datos = new String[3];
	             
	             datos[0] = "StockDisponible = StockDisponible -" + cantidadDevolver;
	             datos[1] = "StockInicial = StockInicial -" + cantidadDevolver;
	             datos[2] = "StockDevueltas = StockDevueltas +" + cantidadDevolver;
	             
	             actualizar("LotesPorproducto",datos,condicion,conectarMySQL,false); 
	             
	             
	             item++;
                
	        }    
          	
          } 
          

      }
      
      
      
 	//******************************** detenerEditar ********************************
 	
 	private void detenerEditar() {
 
 	   if (tablaComprasDetalle.getEditingRow() > - 1 && tablaComprasDetalle.getEditingColumn() > -1)	
 		
 		 tablaComprasDetalle.getCellEditor(tablaComprasDetalle.getEditingRow(),tablaComprasDetalle.getEditingColumn()).stopCellEditing();
 	
 	}
 	
     //*****************************Metodo traerInformación() ******************************************
    
    final private boolean traerInformación() { 
          
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select C.NumeroCompra,P.RazonSocial,Substr(C.Fecha,1,10),C.Total "+
                              "From   ComprasEncabezado C, Proveedores P "+
                              "Where C.NumeroProveedor = P.NumeroProveedor and C.Estado = 'G' ";
                 
                             
          if (pestaña.getSelectedIndex() == 0) 
        	
        
         	sentenciaSQL += "and NumeroCompra = '" + jtNumeroFactura.getText() + "'"; 
           
         
         else                     
                              
	        if (pestaña.getSelectedIndex() == 1) 
         
	            sentenciaSQL += "and  P.Nit = '" + jtIdProveedor.getText() + "'"; 
	         
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
	
	              
		             Vector <Vector > filas = new Vector <Vector>();
				
				      while (resultado.next()) {
					     
					       Vector columnas = new Vector();
					    
					    	columnas.add(resultado.getString(1));
					    	columnas.add(resultado.getString(2));
					    	columnas.add(resultado.getString(3));
					    	columnas.add(getFormatoNumero(resultado.getString(4)));
					    	
					    	filas.add(columnas);	
				    				
					  }
			          
			           if (filas.size() > 0) {
			                       
					 	 dm.setDataVector(filas,columnas);
					 	 tablaCompras.grabFocus();
					 	 tablaCompras.setRowSelectionInterval(0,0);
				 	     resultadoBoolean = true;
				 	 
					 
					 } else 
					 	
					    if (pestaña.getSelectedIndex() == 1) {
					    
						 	    jtIdProveedor.setText("");
						 	    jtRazonSocial.setText("");
						 	  	Mensaje(" Este Proveedor no tiene facturas asociadas ","Información",2);
						 	  	jtIdProveedor.grabFocus();
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
	  
    //*****************************Metodo traerInformaciónDetalle() ******************************************
    
    final private void traerInformaciónDetalle(String numeroFactura) { 

        
        String sentenciaSQL = "Select  C1.Item,P.Descripcion,C1.Precio,C1.Cantidad, L.StockDevueltas,L.StockVendidas,L.StockDisponible,P.IdProducto "+
                              "From   ComprasDetalle C1, LotesPorProducto L,Productos P "+
                              "Where  C1.NumeroCompra = L.NumeroCompra and C1.idProducto = P.idProducto and    L.idProducto = P.idProducto  and " +
                              "       C1.NumeroCompra = " + numeroFactura; 
      
      
      
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
					    	columnas.add(getFormatoNumero(resultado.getString(3)));
					      
      				    	
					    	Long cantidadesComprada = resultado.getLong(4);
					    	Long cantidadDisponible = resultado.getLong(5);
					    	
					        if (cantidadesComprada > cantidadDisponible) {
					        	
					          
					          columnas.add(String.valueOf(cantidadesComprada));
					    	  columnas.add(String.valueOf(cantidadDisponible));
					    		  
    				        	
					        } else {
					        	
					        	
						          columnas.add(String.valueOf(cantidadesComprada));
						    	  columnas.add(String.valueOf(cantidadesComprada));
					    		
					        }
	
					    	columnas.add(resultado.getString(6));
					        columnas.add(resultado.getString(7));
					        columnas.add(resultado.getString(8));
					    	filas.add(columnas);
					    	
				    				
					  }
			          
			           if (filas.size() > 0) {
	
					 	 dm1.setDataVector(filas,columnas);
					 	
					 } else
					     
					     dm1.setDataVector(datos,nombresColumnas); 
					 
				 
				   editorCantidadDevolverProductoJTable = getEditor(jtCantidadDevolverProductoJTable);
         
					 
					     
					 	
			} 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
      
    }
	
	
	//*****************************Metodo traerInformación() ******************************************
    
    private boolean traerInformaciónProveedor() { 
        
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
    
    //**************************** Metodo validarElementoChequeado() ************************
  
    
    private boolean validarElementoChequeado() {
    	
    	int numFilas = tablaComprasDetalle.getRowCount();
    	
    	boolean sw = false;
    	
    	int i = 0;
    	
    	while (i < numFilas && !sw) {
    		
    		if (tablaComprasDetalle.getValueAt(i,0).toString().equals("true")) 
    		
    		  sw = true;
    		  
    		else
    		  
    		  i++;  
    		
    	}
    	
    	
    	return sw;
    	
    	
     }
  
      
      //************************ Metodo valueChanged() ****************************************
      
       public void valueChanged(ListSelectionEvent e) { 
      
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            
             detenerEditar();
             
             
             if (!e.getValueIsAdjusting() && tablaCompras.getSelectedRow() > -1) {
              
               numeroCompra  = tablaCompras.getValueAt(tablaCompras.getSelectedRow(),0).toString();
      
               traerInformaciónDetalle(numeroCompra);
           
             
           }  
       }     
      
      //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

         // Se verifica el componente que genero la acccion

         if (fuente == Blimpiar) {

            limpiar();

         } else 
            if (fuente == Bguardar) {
            	
            	if (validarElementoChequeado()) {
	            	
	                     try {
	                        
	                          String mensaje = "Compra Devuelta";
	                          
	                          guardar();
	
	                          actualizarDetalles(); 
	
	                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 
	
	                          Mensaje(mensaje,"Información",1);
	
	                          limpiar();   //Se limpia la forma 
	
	                       } catch (Exception e) {
	
	                           conectarMySQL.rollback();
	
	                           Mensaje("Error al Insertar Anularventa" +e,"Error",0); 
	
					      }
					      
				 } else   
				 
				    Mensaje("Debe seleccionar el/los produto(s) a devolver" ,"Información",2);     
            }  else 

                    if (fuente == Bbuscar) { 

                       traerInformación();

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
              
             listaValor.mostrarListaValores(340,-50);
             
		}  else
          
              if (f.getComponent() != jtRazonSocial &&  getDialogoListaValores() != null && getDialogoListaValores().isVisible()) {
            
                  if (getJTableListaValores().getRowCount() > 0) {
                
                  	 //Se oculta el scroll de la lista de valores
					 jtIdProveedor.setText(getJTableListaValores().getValueAt(0,0).toString()); 
					 jtRazonSocial.setText(getJTableListaValores().getValueAt(0,1).toString());
				
					 getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
					 jtRazonSocial.selectAll();
				 
				 } else {
				 	 
				 	 jtRazonSocial.setText("");
				 	 jtRazonSocial.grabFocus();
				 	 listaValor.mostrarListaValores(340,-50);
				 	
				 }
				 	 
				
				} else

				   if (f.getComponent() == tablaComprasDetalle && !jtRazonSocial.getText().isEmpty())
				      
				        traerInformación();
				    else
			           
			           if (f.getComponent()  == jtCantidadDevolverProductoJTable) {
				          
				          ((JTextField)editorCantidadDevolverProductoJTable.getComponent()).selectAll();
				      	  
				      	  
			  	       }      
				       
        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
        
        if (f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField") || f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JFormattedTextField"))
			          
			  ((JTextField)f.getComponent()).selectAll();
	    
	  
	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
    
        if (f.getComponent() == jtNumeroFactura && !jtNumeroFactura.getText().isEmpty()) {
        	
        	if (!traerInformación()) {
        		
        		Mensaje("Numero de factura no registrado","Información",2);
        		jtNumeroFactura.setText("");
        		jtNumeroFactura.grabFocus();
        	
        	} 
        	
        } else
           
          if (f.getComponent() == jtIdProveedor && !jtIdProveedor.getText().isEmpty()) {
        	
        	if (!traerInformaciónProveedor()) {
        		
        		mostrarListaAutomatica = false;
        	    Mensaje("Nit " +  jtIdProveedor.getText( ) + " no registrado","Información",2);
        	    mostrarListaAutomatica = true;
        		jtIdProveedor.setText("");
        		
        	} else {
                
                mostrarListaAutomatica = false;
        	    tablaComprasDetalle.grabFocus();
        	    tablaComprasDetalle.setRowSelectionInterval(0,0);
        	    traerInformación();
        		
        	    
        	}    
        	
        } else
           
          if (f.getComponent() == jtFechaFinal && !jtFechaFinal.getText().isEmpty()) {
        	
			
        	    traerInformación();
        	
          } else
             
             if (f.getComponent() == jtCantidadDevolverProductoJTable && tablaComprasDetalle.getValueAt(filaSeleccionada,0).toString().equals("true")  &&  (f.getOppositeComponent() != null && f.getOppositeComponent().getName() == null || ( f.getOppositeComponent().getName() != null && !f.getOppositeComponent().getName().equals("ejecutar")))) {
             	
             	if (tablaComprasDetalle.getValueAt(filaSeleccionada,4) != null) {
             	
		             
		              Long cantidadDevolver = Long.parseLong(jtCantidadDevolverProductoJTable.getText());
		              Long cantidadComprada = Long.parseLong(tablaComprasDetalle.getValueAt(filaSeleccionada,4).toString());
		             
		             
			              if (cantidadDevolver <= 0) {
			              	
			             	jtCantidadDevolverProductoJTable.setText("");
			             	tablaComprasDetalle.editCellAt(filaSeleccionada,7);
			             	editorCantidadDevolverProductoJTable.getComponent().requestFocus();
			             	Mensaje("Cantidad debe ser Mayor a cero","Información",2);
			              
			             } else
			                
			                 if (cantidadDevolver > cantidadComprada) {
			                 	
			                 	jtCantidadDevolverProductoJTable.setText("");
			                 	tablaComprasDetalle.editCellAt(filaSeleccionada,7);
				             	editorCantidadDevolverProductoJTable.getComponent().requestFocus();
				             	Mensaje("Cantidad a devolver debe ser menor o igual  a la comprada","Información",2);
				                 	
			                 } else {
			                 	
			                 	 tablaComprasDetalle.editCellAt(filaSeleccionada + 1,7);
			             	     editorCantidadDevolverProductoJTable.getComponent().requestFocus();
			             	     sumarValoresDevueltos();
			             	
			                 	
			                 }	
			             
			    } 	
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