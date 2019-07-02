package com.JASoft.azkaban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.KeyboardFocusManager;
import javax.swing.KeyStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.JASoft.componentes.ImprimirReporteJasper;
 
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JFormattedTextField;
import javax.swing.table.TableCellRenderer;
import javax.swing.JDialog;


import java.text.NumberFormat;
import java.text.DecimalFormat;


import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.DefaultCellEditor;
import javax.swing.UIManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.HashMap;
import java.util.Map;

import java.util.Vector;

import java.sql.ResultSet;
import java.sql.CallableStatement;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.Calendario;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


final public class Venta extends CrearJFrame implements ActionListener, ListSelectionListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Array para campos obligatorios
    private JTextField[] camposObligatorios;

    //** Se declaran los JPanel
    private  JPanel jpClientes;
    private  JPanel jpFecha;
    private  JPanel jpObservaciones;
    private  JPanel jpVendedor;

    //** Se declaran los JTextField
    private  JTextField jtNumeroFactura;
    private  JTextField jtIdCliente;
    private  JTextField jtNombreCliente;
    private  JTextField jtTotal;
    private  JTextField jtCodigoProductoJTable;
    private  JTextField jtProductoJTable;
    private  JTextField jtCantidadProductoJTable;
    private  JTextField jtPrecioVentaProductoJTable;
    private  JTextField jtTotalEfectivo;
    private  JTextField jtTotalTarjetas;
    private  JTextField jtTotalBonos;
    private  JTextField jtTotalVueltas;
    private  JTextField jtPrecioVenta;
    private  JTextField jtMinimo;
    private  JTextField jtPrecioLista;
    private  JTextField jtPrecioCompra;
    private  JTextField jtMaximoDescuento;
    private  JTextField jtPorcentajeDescuento;
    
    private JTextField jtDireccion;
    private JTextField jtNombre;
    private JTextField jtSaldo;
    private JTextField jtTelefono;
    private JTextField jtBono;
    private JTextField jtCiudadMunicipio;
    private JTextField jtCupo;
    private JTextField jtCelular;
    private JTextField jtNit;
    
    private  JButton jbAceptar;
    private  JButton jbCancelar;
    
    //** Se declaran los JScrollPane 
    private JScrollPane scrollPane;    
    
      
    //JDialog
    private JDialog dialogClientes;
    
    //JButton 
    private JButton jbCerrar;
    
    //JComboBox
    private JComboBox jcTipoId;
    private JComboBox jcDepartamento; 
    
    
     //** Se declaran los JTextArea 
    private JTextArea jaObservaciones;
    
    //** Se declaran los JFormattedTextField
    private JFormattedTextField jtFechaVenta;
    
    //** Se declaran los JButton
    private  JButton jbCalendario;
    private  JButton jbEditarCliente;

    //** Se declaran los JComboBox
    private  JComboBox jcTipoIdCombo;
    private  JComboBox jcDepartamentoCombo;
   
    //** Se declaran los JTable
    private  JTable tablaVentas;

   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Item","Código","Producto","Stock","P. Minimo","P. Lista","Cantidad","P. Venta","Subtotal","Subtotal sin formato","","PrecioCompra"};
    private Object [][] datos = new Object[40][10];

    //Modelo de datos 
    private SortableTableModel dm;

    //** int
    private int filaSelecionada;
    
    //** Clase para mostrar una lista de valores
    private ListaValor listaValor;

    //** Se declaran los JLabel
    private JLabel codigoRetorno  = new JLabel();
    
     //Calendario
    private Calendario calendario;
    
     //** Booleanos
    private boolean mostrarListaAutomatica = true;
    private boolean comodinFocusCellEditor = true;
         
     //lONG
    private Long totalFactura = 0L;
    private Long totalCaja = 0L;
    
    
    //** DefaultCellEditor
   private DefaultCellEditor editorCodigoProductoJTable;
   private DefaultCellEditor editorProductoJTable;
   private DefaultCellEditor editorCantidadProductoJTable;
   private DefaultCellEditor editorPrecioVentaProductoJTable;
   
   //** NumberFormat
    private NumberFormat formatter;
    private NumberFormat formatter1;
    
    //** ButtonRenderer
    private ButtonRenderer buttonRenderer = new ButtonRenderer();
    private ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox());
    
    //Booleanos
    private boolean imprimirFacturaAutomatica;
    
    //** CallableStatement
    private CallableStatement callStmt;
    
    //**String
    private String[] informacionVendedor;
    private String nombreUsuario;
    
     //Color
    private Color color = new Color(238,238,238);
    
    //JDialog
    private JDialog dialogPrecio;
    
  
    //** Constructor General 
    public Venta(ConectarMySQL p_conectarMySQL,JFrame p_frame,String nombreUsuario,boolean imprimirFacturaAutomatica,Long totalCaja) {

      super("Facturación","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
       
      this.totalCaja = totalCaja;
      
      this.nombreUsuario = nombreUsuario;
      
      this.imprimirFacturaAutomatica = imprimirFacturaAutomatica; 
      
      getJFrame().setName("Venta"); //Comodin utilizado cuando se llama desde aqui la forma Cliente


      //** Se agregan los JPanel
      
      JPanel jpComodin = new JPanel();
      jpComodin.setLayout(null);
      jpComodin.setBounds(60,55,475,85);
      
      jpVendedor = getJPanel("",0, 0, 475, 25,14);
      jpComodin.add(jpVendedor);
      
      jpClientes = getJPanel("Cliente",0, 28, 475, 60,14);
      jpComodin.add(jpClientes);

      jpFecha = getJPanel("",550, 55, 180, 85,14);

      jpObservaciones = getJPanel("Observaciones / Anotaciones",10, 370, 570,150,14);

      
      getJFrame().add(jpComodin);
      

      //** Se declaran los JLabel

      JLabel jlNit = getJLabel("Nro. Id.",125, 15, 115, 15);
      jpClientes.add(jlNit);

      JLabel jlNombreCliente = getJLabel("Nombre",235, 15, 65, 15);
      jpClientes.add(jlNombreCliente);

      JLabel jlTelefono = getJLabel("Tipo Id.",15, 15, 65, 15);
      jlTelefono.setHorizontalAlignment(SwingConstants.LEFT);
      jpClientes.add(jlTelefono);

      JLabel jlNumeroCompra1 = getJLabel("Factura Nro:",15, 20, 85, 15);
      jlNumeroCompra1.setHorizontalAlignment(SwingConstants.LEFT);
      jpFecha.add(jlNumeroCompra1);

      JLabel jlNumeroCompra2 = getJLabel("Fecha:",15, 55, 55, 15);
      jlNumeroCompra2.setHorizontalAlignment(SwingConstants.LEFT);
      jpFecha.add(jlNumeroCompra2);

      JLabel jlTotal = getJLabel("Total:",620,378, 45, 15);
      jlTotal.setHorizontalAlignment(SwingConstants.LEFT);
      getJFrame().add(jlTotal);

      JLabel jlNombreVendedor = getJLabel("Vendedor:",30, 5, 65, 15);
      jlNombreVendedor.setHorizontalAlignment(SwingConstants.RIGHT);
      jlNombreVendedor.setForeground(new Color(148, 148, 148));
      jpVendedor.add(jlNombreVendedor);
      
      informacionVendedor = buscarVendedor(nombreUsuario);
      
      JLabel jlNombreVendedor1 = getJLabel(informacionVendedor[2],105, 5, 360, 15);
      jlNombreVendedor1.setHorizontalAlignment(SwingConstants.LEFT);
      jpVendedor.add(jlNombreVendedor1);
      
      JLabel jlDetalle = getJLabel("Detalle: Factura de Venta",315,155,160,15);
      jlDetalle.setHorizontalAlignment(SwingConstants.CENTER);
      getJFrame().add(jlDetalle);
      
      JLabel jlEfectivo = getJLabel("Efectivo : ",550 ,412,120,15);
      jlEfectivo.setHorizontalAlignment(SwingConstants.RIGHT);
      
      JLabel jlTarjetas = getJLabel("Tarjeta : ",550 ,437,120,15);
      jlTarjetas.setHorizontalAlignment(SwingConstants.RIGHT);
      
      JLabel jlBonos = getJLabel("Bonos : ",550 ,462,120,15);
      jlBonos.setHorizontalAlignment(SwingConstants.RIGHT);
      
      JLabel jlVueltas = getJLabel("Cambio : ",550 ,497,120,15);
      jlVueltas.setHorizontalAlignment(SwingConstants.RIGHT);
       
       
      


      //** Se instancian los JSeparator

      JSeparator jSeparator1 = new JSeparator();
      jSeparator1.setBounds(650, 370, 115, 2);
      getJFrame().add(jSeparator1);

      //** Se agregan los JTable

      // se configura el modelo para la tabla
      dm = new SortableTableModel() {

          //Se especifica el serial para la serializacion
          static final long serialVersionUID = 19781212;

          public Class getColumnClass(int col) {
              
                return JButton.class;

          }
          
          
         public boolean isCellEditable(int row, int col) {
	        
	           filaSelecionada = row;
	           
	           boolean resultadoBoolean = true;
	           
	           if (col == 0 || col == 3 || col == 4 || col == 5 || col == 9)
                   
                   resultadoBoolean = false;
                   
               
               if (row > (obtenerFilas(tablaVentas,0)))  
               
                  resultadoBoolean = false;  
                      	           
	              
	           return resultadoBoolean;
	
	     }
	     
	    
	     public void setValueAt(Object obj, int row, int col) {
	       	
	          super.setValueAt(obj, row, col); 
	          
	          if (col == 9  && tablaVentas.getValueAt(filaSelecionada,col) != null && !tablaVentas.getValueAt(filaSelecionada,col).toString().isEmpty()) {
	          
	          
	                  //Se calcula el total
			          int filas = obtenerFilas(tablaVentas,9);
			          
			          long suma = 0;
			          
			          for (int i = 0; i < filas; i++) {
			          
			          	suma += Long.parseLong(tablaVentas.getValueAt(i,9).toString());
			          	
				      }   	    
				      
				      jtTotal.setText(formatter.format(suma));
				      totalFactura = suma;
				      
			  }
			  	      
		}    
	       
       

      };

     dm.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla

    
     
     MouseAdapter mouseAdapter = new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     
     		if (m.getClickCount() == 1) {
     		   
     		    int i = 0;
     		    
     		    boolean sw = true;
     		    
     		    while (i < 8 && sw) {
     		    	
     		    	if (tablaVentas.getValueAt(filaSelecionada,i) == null || tablaVentas.getValueAt(filaSelecionada,i).toString().isEmpty())
     		    	
     		    	  sw = false;
     		    	  
     		    	i++;  
     		    }
     		   
     		    if (sw)
     		    
     		       comodinFocusCellEditor = false;
     		}
     		   
     	}
     	
      };
      
     tablaVentas = new JTable(dm);
     
	  //Se instancia el JTable con el modelo de datos
     tablaVentas.getSelectionModel().addListSelectionListener(this);
     tablaVentas.setColumnSelectionAllowed(true);
     tablaVentas.setFocusTraversalKeysEnabled(true);
     tablaVentas.addMouseListener(mouseAdapter);
     tablaVentas.setRowHeight(17);
        
     KeyAdapter keyAdapter = new KeyAdapter() { //Se define el codigo para un evento de tipo Key
      	
          	public void keyPressed(KeyEvent k) {
	      		
	      		if (k.getKeyCode() == 38 || k.getKeyCode() == 40) 
	               
	               k.consume();
	               
	             else 
	      		  	
	      		  	if (k.getKeyCode() == 37) {
	      		  		
			     	      if (tablaVentas.getEditingColumn() == 6) {
			     	          
			     	          comodinFocusCellEditor = false;
			     	          tablaVentas.editCellAt(filaSelecionada,2);
			     	          editorProductoJTable.getComponent().requestFocus();
			     	          ((JTextField)editorProductoJTable.getComponent()).selectAll();
			     	        
			     	         
			     	          
			     	      } 
	      		  	}
	      		  	
	      	}	        
	      		
	      	  
	      	
	      	public void  keyTyped (KeyEvent k) {
	      		
		      	if (((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) ||  jtCodigoProductoJTable.getText().length() >= 16))
			      
			           k.consume(); // Se consume o borra el caracter digitado
			      
		    }  
      };
      
     //Se crean unos JTextField comodines para colocarlos como edicion en el JTable
      jtProductoJTable = new JTextField();
      jtProductoJTable.setName("21");     
      
      jtProductoJTable.addKeyListener(new KeyAdapter() {
      	
      	
        public void keyPressed(KeyEvent k) {
        
          if (k.getKeyCode() == 9 && k.getComponent().getName().equals("21")) {
          	
	       	    if (getJTableArrayListaValores()!= null && getJTableArrayListaValores()[1] != null && getJTableArrayListaValores()[1].isVisible()) {
	
				         detenerEditar();
	            	     tablaVentas.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,0).toString(),0,1);
	            	     tablaVentas.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,1).toString(),0,2);
	            	     tablaVentas.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,2).toString(),0,3);
	            	     tablaVentas.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,3).toString(),0,4);
	            	     tablaVentas.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,4).toString(),0,5);
	            	     tablaVentas.editCellAt(filaSelecionada,6);
					     editorCantidadProductoJTable.getComponent().requestFocus();
					                  
	            	      
						 
						 getDialogoArrayListaValores()[1].setVisible(false);
					     
						
						
			     }
		       	
          } else
            
             if (k.getKeyCode() == 38 || k.getKeyCode() == 40) 
	               
	              k.consume();
	              
	              
	         else 
	      		  
      		  	if (k.getKeyCode() == 37) {  
      		   
    
     	              comodinFocusCellEditor = false;
     	              tablaVentas.editCellAt(filaSelecionada,4);
     	              editorCodigoProductoJTable.getComponent().requestFocus();


  	            }     
          
        
        }
        
        
        // Se valida que solo se digite mayuscula
    	 public void  keyTyped (KeyEvent k) {
    	 	 
    	 	  k.setKeyChar(String.valueOf(k.getKeyChar()).toUpperCase().charAt(0));
          
          }
          
          
           //Se utiliza para una lista incremental
          public void keyReleased(KeyEvent k) {
    
         	 if (k.getKeyChar() != k.VK_ESCAPE && k.getKeyChar() != k.VK_ENTER && k.getKeyCode() != 9) 
         	     
         	     mostrarListaValores();
         	     
          }	     
         	   
         
      	
      });
      jtProductoJTable.addFocusListener(this);
      jtProductoJTable.addActionListener(this);
      
      
      jtCodigoProductoJTable = new JTextField();
      jtCodigoProductoJTable.setName("16");
      jtCodigoProductoJTable.addKeyListener(new KeyAdapter() { //Se define el codigo para un evento de tipo Key
      	
          	public void keyPressed(KeyEvent k) {
	      		
	      		if (k.getKeyCode() == 38 || k.getKeyCode() == 40) 
	      
	      		  k.consume();
	      	}	  
	      	
	      	// Se valida que solo se digite mayuscula
    	    public void  keyTyped (KeyEvent k) {
    	 
    	 	   k.setKeyChar(String.valueOf(k.getKeyChar()).toUpperCase().charAt(0));
          
            }
      	
      });

      jtCodigoProductoJTable.addFocusListener(this);
      jtCodigoProductoJTable.addMouseListener(mouseAdapter);
      
      jtCantidadProductoJTable = new JTextField();
      jtCantidadProductoJTable.setName("3");
      jtCantidadProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
      jtCantidadProductoJTable.addKeyListener(keyAdapter);
      jtCantidadProductoJTable.addFocusListener(this);
      jtCantidadProductoJTable.addMouseListener(mouseAdapter);
      
       
      jtPrecioVentaProductoJTable = new JTextField();
      jtPrecioVentaProductoJTable.setName("7");
      jtPrecioVentaProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioVentaProductoJTable.addKeyListener(new KeyAdapter () {
      	
      	public void keyPressed(KeyEvent k) {
	      		
	      		if (k.getKeyCode() == 38 || k.getKeyCode() == 40) 
	      		  
	      		   k.consume();
	      		  
	      		else
	      		  
	      		  if (k.getKeyCode() == KeyEvent.VK_F1) {
	      		  	  
		                              
	      		  	  configurarJDialogPrecios();
	      		  	  jtMinimo.setText(tablaVentas.getValueAt(filaSelecionada,4).toString());
	      		  	  jtPrecioLista.setText(tablaVentas.getValueAt(filaSelecionada,5).toString());
	      		  	  jtPrecioVenta.setText(jtPrecioLista.getText());
	      		  	  jtPrecioCompra.setText(tablaVentas.getValueAt(filaSelecionada,11).toString());	      		  	  
	      		  	  jtPorcentajeDescuento.setText("0");
	      		  	  
	      		  	  //se obtiene el maximo de descuento
	      		  	  float precioMinimo = Float.parseFloat(jtMinimo.getText());
				      float precioLista = Float.parseFloat(jtPrecioLista.getText());
				      
				      if (precioMinimo == precioLista )
				        
				        jtMaximoDescuento.setText("0");
				      
				      else  
				      
				         jtMaximoDescuento.setText(String.valueOf(redondearNumero(precioMinimo/precioLista,2) * 100));             
	      		  	  
                      jtPrecioVenta.grabFocus();    //Se coloca el foco en el primer componente Focusable
    	              dialogPrecio.setVisible(true);
        
	      		  } else
			     	     
     	        	if (k.getKeyCode() == 37)  {
     	              
     	                  comodinFocusCellEditor = false;
	     	              tablaVentas.editCellAt(filaSelecionada,6);
	     	              editorCantidadProductoJTable.getComponent().requestFocus();
	     	              ((JTextField)editorCantidadProductoJTable.getComponent()).selectAll();
			     	        
	     	         
  		  		     } 
  		    
	      	}	  
      });
      jtPrecioVentaProductoJTable.addFocusListener(this);
      jtPrecioVentaProductoJTable.addMouseListener(mouseAdapter);
      
      
      //JButton agregado para eliminar la filas
      JButton jbEliminarFila = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Eliminar.gif")));
      jbEliminarFila.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/EliminarS.gif")));
      
       //Se declaran los DefaultCellEditor
      editorCodigoProductoJTable = getEditor(jtCodigoProductoJTable);
      editorProductoJTable = getEditor(jtProductoJTable);
      editorCantidadProductoJTable = getEditor(jtCantidadProductoJTable);
      editorPrecioVentaProductoJTable = getEditor(jtPrecioVentaProductoJTable);
      
      
      tablaVentas.getColumnModel().getColumn(1).setCellEditor(editorCodigoProductoJTable);
      tablaVentas.getColumnModel().getColumn(2).setCellEditor(editorProductoJTable);
      tablaVentas.getColumnModel().getColumn(6).setCellEditor(editorCantidadProductoJTable);
      tablaVentas.getColumnModel().getColumn(7).setCellEditor(editorPrecioVentaProductoJTable);  
      tablaVentas.getColumnModel().getColumn(10).setCellRenderer(buttonRenderer);
      tablaVentas.getColumnModel().getColumn(10).setCellEditor(buttonEditor);
      
      configurarColumnas();

     //** Se configura un scroll para el JTable 

      scrollPane = new JScrollPane(tablaVentas);
      scrollPane.setBounds(10, 190, 775, 172);
      getJFrame().add(scrollPane);

      //** Se agregan los JTextField

      jtNumeroFactura = getJTextField("Número de Factura de Venta",95, 15, 70, 20,"",false);
      jtNumeroFactura.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroFactura.addFocusListener(this);
      jtNumeroFactura.setText(buscarNumeroVenta());
      jpFecha.add(jtNumeroFactura);

      jtFechaVenta = getJFormattedTextField(getObtenerFecha(conectarMySQL),70, 50, 70, 20,"Dígite la Fecha de la Factura","10");
      jtFechaVenta.setHorizontalAlignment(JTextField.RIGHT);
      jtFechaVenta.addFocusListener(this);
      jtFechaVenta.addFocusListener(this);
      jpFecha.add(jtFechaVenta);

      jtIdCliente = getJTextField("",125, 30, 105, 20,"Digíte el Número de Identificación del Cliente","12");
      jtIdCliente.setHorizontalAlignment(JTextField.RIGHT);
      jtIdCliente.addKeyListener(getValidarEntradaNumeroJTextField());
      jtIdCliente.addFocusListener(this);
      jpClientes.add(jtIdCliente);
      
      jtTotalBonos = getJTextField("",675, 460, 70, 20,"Digíte el total a pagar en Bonos","7");
      jtTotalBonos.setHorizontalAlignment(JTextField.RIGHT);
      jtTotalBonos.addKeyListener(getValidarEntradaNumeroJTextField());
      jtTotalBonos.addFocusListener(this);
      getJFrame().add(jtTotalBonos);

      jtNombreCliente = getJTextField("",235, 30, 200, 20,"Digite el Nombre del Cliente o Preione F9","40");
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtNombreCliente,conectarMySQL,0,2,getOcultarColumnas());
      
      jtNombreCliente.addKeyListener(listaValor);
      jtNombreCliente.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtNombreCliente.addFocusListener(this);
      jtNombreCliente.addActionListener(this);
      jpClientes.add(jtNombreCliente);

      jtTotal = getJTextField("Total Venta",675, 375, 70, 20,"",false);
      jtTotal.setHorizontalAlignment(JTextField.RIGHT);
      jtTotal.addKeyListener(getValidarEntradaNumeroJTextField());
      jtTotal.addFocusListener(this);
      getJFrame().add(jtTotal);
      
      jtTotalEfectivo = getJTextField("",675, 410, 70, 20,"Digíte el total a pagar en efectivo","7");
      jtTotalEfectivo.setHorizontalAlignment(JTextField.RIGHT);
      jtTotalEfectivo.addKeyListener(getValidarEntradaNumeroJTextField());
      jtTotalEfectivo.addFocusListener(this);
      getJFrame().add(jtTotalEfectivo);
      
      jtTotalTarjetas = getJTextField("",675, 435, 70, 20,"Digíte el total a pagar en Tarjetas","7");
      jtTotalTarjetas.setHorizontalAlignment(JTextField.RIGHT);
      jtTotalTarjetas.addKeyListener(getValidarEntradaNumeroJTextField());
      jtTotalTarjetas.addFocusListener(this);
      getJFrame().add(jtTotalTarjetas);
      
      jtTotalVueltas = getJTextField("Digíte el total a pagar en Bonos",675, 495, 70, 20,"7",false);
      jtTotalVueltas.setHorizontalAlignment(JTextField.RIGHT);
      jtTotalVueltas.addFocusListener(this);
      getJFrame().add(jtTotalVueltas);

      //** Se agregan los JButton
      
      jbCalendario = getJButton("",145, 50, 20, 20,"Imagenes/Calendario.gif","Calendario");
      jbCalendario.setFocusable(false);
      jbCalendario.addActionListener(this);
      jpFecha.add(jbCalendario);

      jbEditarCliente = getJButton("...",440, 30, 20, 20,"","");
      jbEditarCliente.setFocusable(false);
      jbEditarCliente.addActionListener(this);
      jpClientes.add(jbEditarCliente);

      //** Se agregan los JComboBox

      jcTipoId = getJComboBox(15, 30, 105, 20,"Selecione el Tipo de Identificación del Cliente");
      jcTipoId.addItem("Cedula");
      jcTipoId.addItem("Tarjeta Id.");
      jcTipoId.addItem("Pasaporte");
      jpClientes.add(jcTipoId);
      
      
      //Se agregan los JTextArea
      jaObservaciones = new  JTextArea();
      jaObservaciones.addKeyListener(new KeyAdapter() {
      	
      	public void  keyTyped (KeyEvent k) {
   	    
   	       k.setKeyChar(String.valueOf(k.getKeyChar()).toUpperCase().charAt(0));
   	       
   	    }   
      	
      });
      jaObservaciones.addFocusListener(this);
      jaObservaciones.setLineWrap(true);
      
      JScrollPane scrollObservaciones = new JScrollPane(jaObservaciones);
      scrollObservaciones.setBounds(30, 33, 510, 90);
      jpObservaciones.add(scrollObservaciones);
      
      JSeparator jSeparator2 = new JSeparator();
      jSeparator2.setBounds(17,175,760,2);
      getJFrame().add(jSeparator2);
      
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/VentasFacturacion.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 

      jtIdCliente.grabFocus();    //Se coloca el foco en el primer componente Focusable

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

	  //Se crea el array de campos obligatorios 
	  crearArrayCamposObligatorios();
	  
	  formatter = new DecimalFormat("#,###,###");
	  
	    
	  getJFrame().addComponentListener(new ComponentAdapter() {
  	   
  	    	public void componentShown(ComponentEvent e) {
  	
  	           if (!jtIdCliente.getText().isEmpty()) {
  	               
  	               traerInformacion();
  	               jtFechaVenta.grabFocus();
  	           }    
  	             
  	              		
  	    	}	
  	  });
  	  
  	  try {
		 		      
	      callStmt = conectarMySQL.getConexion().prepareCall("{call actualizarLotes(?,?,?)}"); //Se especifica la sentencia que actualiza los productos
	    
      } catch (Exception e) {
      	
      	 e.printStackTrace();
      }			

    }
    
    
    
    
     //******************************** Metodo crearArrayCamposObligatorios *******************

     public void crearArrayCamposObligatorios() { 

           //Se instancia un array de JTextField 
           camposObligatorios = new JTextField[3];

           camposObligatorios[0] = jtNumeroFactura;
           camposObligatorios[1] = jtFechaVenta;
           camposObligatorios[2] = jtIdCliente;
           

     }
     
     
     //******************************** Metodo crearArrayCamposObligatorios *******************

      public void imprimirFactura() { 
       
         Map parametros = new HashMap();
         
         parametros.put("NumFactura",new Integer(jtNumeroFactura.getText()));
         parametros.put("Fecha",jtFechaVenta.getText());
         parametros.put("Efectivo",jtTotalEfectivo.getText().isEmpty() ? "0" : jtTotalEfectivo.getText());
         parametros.put("Tarjetas",jtTotalTarjetas.getText());
         parametros.put("Bonos",jtTotalBonos.getText());
         parametros.put("Total",jtTotal.getText());
         parametros.put("Cambio",jtTotalVueltas.getText());
         
       
         ImprimirReporteJasper.runReport("Azkaban",conectarMySQL.getConexion(),parametros,getClass().getClassLoader().getResourceAsStream("FacturaVenta.jrxml"));
     
     }
     
	//**************************** Metodo configurarColumnas() ************************

	final private void configurarColumnas() {

		ocultarColumnas(tablaVentas,4);
	    ocultarColumnas(tablaVentas,9);
	    ocultarColumnas(tablaVentas,11);
		tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(26);
		tablaVentas.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
		tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(129);
		tablaVentas.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(305);
		tablaVentas.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(40);
		tablaVentas.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
		tablaVentas.getColumnModel().getColumn(5).setPreferredWidth(68);
		tablaVentas.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
		tablaVentas.getColumnModel().getColumn(6).setPreferredWidth(66);
		tablaVentas.getColumnModel().getColumn(6).setCellRenderer(getAlinearDerecha());
		tablaVentas.getColumnModel().getColumn(7).setPreferredWidth(90);
		tablaVentas.getColumnModel().getColumn(7).setCellRenderer(getAlinearDerecha());
		tablaVentas.getColumnModel().getColumn(8).setPreferredWidth(91);
		tablaVentas.getColumnModel().getColumn(8).setCellRenderer(getAlinearDerecha());
	    tablaVentas.getColumnModel().getColumn(10).setPreferredWidth(15);
	
		

	}
	
	//***********************************Metodo validarDatosJTable() ******************************
	final private boolean  validarDatosJTable() {
		
		int numFilas = obtenerFilas(tablaVentas,9);
		
		boolean sw = true;
		
		int i = 0;
		
		String valorValidar = tablaVentas.getValueAt(filaSelecionada,1).toString();
		
		if (numFilas >= 1)

			while ( i < numFilas && sw ) {
			
			    if (tablaVentas.getValueAt(i,1) != null && tablaVentas.getValueAt(i,1).equals(valorValidar) && i != filaSelecionada)
			        sw = false;
			    
			    
			    i++;
			        
			}
			
		return sw;
		
	}

	//******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		String sentencia = "Select Case TipoId WHEN 'C' THEN 'Cedula'"+ 
		                   "                   WHEN 'T' THEN 'Tarjeta Id.'"+
		                   "                   Else 'Pasaporte'"+
		                   "                   End Tipo, idCliente Identificacion,Concat(Nombre,Concat(' ',Apellido)) Nombres,Bono "+
		                   "From   Clientes "+
		                   "Where Nombre like '";

		return sentencia;

	}
	
	
	//******************************** Metodo getOcultarColumnas()  ***************************************

	final private int[] getOcultarColumnas() {

		int[] columnasOcultar = new int[1];
        
        columnasOcultar[0] = 3;
       	
		return columnasOcultar;

	}
    
    	//******************************** Metodo getOcultarColumnas()  ***************************************

	final private int[] getOcultarColumnas1() {

		int[] columnasOcultar = new int[4];
        
        columnasOcultar[0] = 2;
        columnasOcultar[1] = 3;
        columnasOcultar[2] = 4;
        columnasOcultar[3] = 5;
       	
		return columnasOcultar;

	}
	
	
	//******************************** Metodo getSentencia1()  ***************************************

	final private String getSentencia1() {

		String sentencia = "Select P.idProducto Codigo,P.Descripcion,P.Stock,P.PrecioMininoVenta,P.PrecioListaVenta,P1.PrecioCompra "+
		                   "From Productos P, Precios P1 "+
		                   "Where P.IdProducto = P1.IdProducto and P1.FechaHasta is Null and P.Stock > 0 and P.Descripcion like '"+ jtProductoJTable.getText() +"%'";

		return sentencia;

	}

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[3][5];
		
		objetosRetorno[0][0] = jtNombreCliente;
        objetosRetorno[0][1] = jtFechaVenta; 
       	objetosRetorno[0][2] = "2";
       	objetosRetorno[0][3] = jtIdCliente;
       	objetosRetorno[0][4] = "1";
        	
       	objetosRetorno[1][3] = jtTotalBonos;
       	objetosRetorno[1][4] = "3";
        
        objetosRetorno[2][3] = jcTipoId;
       	objetosRetorno[2][4] = "0";
       
       	
		return objetosRetorno;

	}
	
   //******************************** Metodo getComponentesRetorno1()  ***************************************
	final private Object[][] getComponentesRetorno1() {

		Object[][] objetosRetorno = new Object[5][7];
		
		objetosRetorno[0][0] = tablaVentas;
		
		objetosRetorno[0][1] = "2";
       
        objetosRetorno[0][2] = "6";
       	objetosRetorno[0][3] = "1";
      
       	objetosRetorno[0][4] = "0";
       	objetosRetorno[0][5] = "1";
       	

       	objetosRetorno[1][4] = "2";
        objetosRetorno[1][5] = "3";
        
        objetosRetorno[2][4] = "3";
        objetosRetorno[2][5] = "4";
       
        objetosRetorno[3][4] = "4";
        objetosRetorno[3][5] = "5";
        
        objetosRetorno[4][4] = "5";
        objetosRetorno[4][5] = "11";
         
		return objetosRetorno;

	}

    //*********************** Metodo limpiar ************************

    private void limpiar() { 

      jtNumeroFactura.setText(buscarNumeroVenta());
      jtFechaVenta.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
      jtIdCliente.setText("");
      jtNombreCliente.setText("");
      jtTotal.setText("");
      jtTotalBonos.setText("");
      jtTotalEfectivo.setText("");
      jtTotalTarjetas.setText("");
      jtTotalVueltas.setText("");
      
        //Se limpia la tabla
      dm.setDataVector(datos,nombresColumnas); //Se agrega las columnas y filas al JTable
      
      //Se declaran los DefaultCellEditor
      tablaVentas.getColumnModel().getColumn(1).setCellEditor(editorCodigoProductoJTable);
      tablaVentas.getColumnModel().getColumn(2).setCellEditor(editorProductoJTable);
      tablaVentas.getColumnModel().getColumn(6).setCellEditor(editorCantidadProductoJTable);
      tablaVentas.getColumnModel().getColumn(7).setCellEditor(editorPrecioVentaProductoJTable); 
      tablaVentas.getColumnModel().getColumn(10).setCellRenderer(buttonRenderer);
      tablaVentas.getColumnModel().getColumn(10).setCellEditor(buttonEditor);  
         
     
      
      configurarColumnas();
      
      totalFactura = 0L;
	
      tablaVentas.setModel(dm);

      jtIdCliente.grabFocus();    //Se coloca el foco en el primer componente Focusable


    }

 
    
    //*****************************Metodo buscarNumeroVenta() **************************************
    
    private String buscarNumeroVenta() { 

        final String  sentenciaSQL = "Select IfNull(max(NumeroVenta),0) "+
                                     "From  VentasEncabezado ";
                                    
                                    
        Long numeroFactura = 0L;                            

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	 

             if (resultado.next()) 
 
                numeroFactura = resultado.getLong(1) + 1;
				

            

        } catch (Exception e) {
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return  String.valueOf(numeroFactura);
    }
    
        //*****************************Metodo buscarVendedor() **************************************
    
    private String[] buscarVendedor(String ID) { 
 
        final String  sentenciaSQL = "Select Case TipoId WHEN 'C' THEN 'Cedula'"+ 
	                                 "                   WHEN 'T' THEN 'Tarjeta Id.'"+
	                                 "                   Else 'Pasaporte'"+
	                                 "                   End Tipo,idVendedor,Concat(Nombre,Concat(' ',Apellido)) "+
                                     "From Vendedores "+
                                     "Where ID = '"+ ID +"'";
                        
                               
        String resultadoString[] = new String[3];                                                 

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	 

             if (resultado.next()) {
             
                resultadoString[0] = resultado.getString(1);
                resultadoString[1] = resultado.getString(2);
                resultadoString[2] = resultado.getString(3);
                
			}	

            

        } catch (Exception e) {
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return  resultadoString;
    }
    //*********************** Metodo guardar ************************

    private void guardar() throws Exception { 


        String[] datos = new String[12];

        datos[0] = "'" + jtNumeroFactura.getText() + "'";
        datos[1] = "'" + jcTipoId.getSelectedItem().toString().charAt(0) + "'";
        datos[2] = "'" + jtIdCliente.getText() + "'";
        datos[3] = "'" + informacionVendedor[0].charAt(0) + "'";
        datos[4] = "'" + informacionVendedor[1] + "'";
        datos[5] = "'" + jtFechaVenta.getText() + " " + getObtenerFechaCompletaServidor(conectarMySQL).substring(11) + "'";
        datos[6] = String.valueOf(totalFactura);
        datos[7] = "'G'";
        datos[8] = "null";
        datos[9] =  jtTotalEfectivo.getText().isEmpty() ? "0" : convertirNumeroSinFormato(jtTotalEfectivo.getText());
        datos[10] = jtTotalTarjetas.getText().isEmpty() ? "0" : convertirNumeroSinFormato(jtTotalTarjetas.getText());
        datos[11] = jtTotalBonos.getText().isEmpty() ? "0" : convertirNumeroSinFormato(jtTotalBonos.getText());
        
        //Se inserta en la base de datos
        guardar("VentasEncabezado",datos,conectarMySQL,false); 
        
         //Se procede a guardar los detalles
        int numFilas = obtenerFilas(tablaVentas,9);
        
        String[][] datosDetalles = new String[numFilas][6];
        
        
        for (int i = 0; i < numFilas; i++) {
        	
        	datosDetalles[i][0] = jtNumeroFactura.getText();
        	datosDetalles[i][1] = tablaVentas.getValueAt(i,0).toString();
        	datosDetalles[i][2] = "'" + tablaVentas.getValueAt(i,1) + "'";
        	datosDetalles[i][3] =  tablaVentas.getValueAt(i,6).toString();
        	datosDetalles[i][4] =  tablaVentas.getValueAt(i,7).toString() ;
        	datosDetalles[i][5] =  tablaVentas.getValueAt(i,9).toString();
        	
        	
        	
        }
        
        guardar("VentasDetalle",datosDetalles,conectarMySQL,6,false);
        
    
        totalCaja += totalFactura; //Se incrementa la caja
              
        //Se almacena en caja
        datos = new String[7];
        datos[0] = "null";
        datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
        datos[2] = "'" + nombreUsuario + "'";
        datos[3] = "5";
        datos[4] = "" + totalFactura;
        datos[5] = "" + totalCaja;
        datos[6] = "'VENTA'";
	        
	       
        
        guardar("MovimientosCaja",datos,conectarMySQL,false); 
       

     }
     
     //**************************** Metodo sumarPagos ************************
     private void sumarPagos() {
     	
     	long totalEfectivo = 0;
     	long totalTarjetas = 0;
     	long totalBonos = 0;
     	long vueltas = 0;
     	
     	if (!jtTotalEfectivo.getText().isEmpty()) 
     		
     		totalEfectivo = Long.parseLong(convertirNumeroSinFormato(jtTotalEfectivo.getText()));
     		
       	if (!jtTotalTarjetas.getText().isEmpty()) 
     		
     		totalTarjetas = Long.parseLong(convertirNumeroSinFormato(jtTotalTarjetas.getText()));
     		
        if (!jtTotalBonos.getText().isEmpty()) 
     		
     		totalBonos = Long.parseLong(convertirNumeroSinFormato(jtTotalBonos.getText()));
     		
     	vueltas = (totalEfectivo + totalTarjetas + totalBonos) - totalFactura;	
     	
     	if (vueltas >= 0) 
     		
     	  	jtTotalVueltas.setForeground(Color.black);
     	
        else 
     		
     		jtTotalVueltas.setForeground(Color.red);
     		
     	
     	jtTotalVueltas.setText(formatter.format(vueltas));	
     }
     
     
     //********************* configurarJdialogClientees()****************************************
     
    private void configurarJDialogClientes() {
    	
    	
    	if (dialogClientes == null) {
    		
    		  dialogClientes = new JDialog(getJFrame(),"Cliente",true);
		  	  dialogClientes.setLayout(null);
		  	  dialogClientes.setSize(695, 277);
		  	  dialogClientes.setLocationRelativeTo(null);
		  	  
		  	  //Se configuran los JPanel
		  	  JPanel panelIdentificacion   =   getJPanel("Identificación",115, 5, 460, 60,14);
		  	   
              JPanel panelApellidosNombres =  getJPanel("Datos Generales",30, 65, 630, 135,14);
   
		  	  //Se configuran los JLabel
		  	  JLabel jlNit = getJLabel("Número",130, 15, 75, 15);
		  	  panelIdentificacion.add(jlNit);
		  	  
		  	  JLabel jlNombre = getJLabel("Nombre",240, 15, 65, 15);
		  	  panelIdentificacion.add(jlNombre);
		  	  
		  	  JLabel jlTipoId =  getJLabel("Tipo",20, 15, 60, 15);
		  	  panelIdentificacion.add(jlTipoId);
		  	  
		  	  
		  	  JLabel jlSaldo =  getJLabel("Saldo:",375, 53, 65, 15);
		  	  jlSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jlSaldo);
		  	  
		  	  JLabel jlTelCelular =  getJLabel("Teléfono / Celular:",15, 105, 105, 15);
		  	  jlTelCelular.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jlTelCelular);
		  	   
		      JLabel jlBono =  getJLabel("Bono:",375, 103, 65, 15);
		      jlBono.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jlBono);
		  	   
              JLabel jlCiudadMunicipio =  getJLabel("Ciudad / Municipio:",0, 78, 120, 15);
              jlCiudadMunicipio.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jlCiudadMunicipio);
		  	  
		  	  
		  	  JLabel jlDepartamento =  getJLabel("Departamento:",0, 53, 120, 15);
		  	  jlDepartamento.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jlDepartamento);
		  	  
		  	  
		  	  JLabel jlCupo =  getJLabel("Cupo:",375, 78, 65, 15);
		  	  jlCupo.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jlCupo);
		  	  
		  	  JLabel jlDireccion  =  getJLabel("Dirección:",0, 28, 120, 15);
		  	  jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jlDireccion);
		  	  
		  	  
		  	  //Se configuran los JTextField
		  	  jtDireccion = getJTextField("Dirección de residencia",130, 25, 415, 20,"",false);
		  	  panelApellidosNombres.add(jtDireccion);
		  	  
		  	  jtTelefono = getJTextField("Teléfono",130, 100, 95, 20,"",false);
		  	  panelApellidosNombres.add(jtTelefono);
		  	  
		  	  
		  	  jtSaldo = getJTextField("Saldo en deudas del Cliente",450, 50, 95, 20,"",false);
		  	  jtSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jtSaldo);
		  	  
		  	  
		  	  jtBono = getJTextField("Saldo en Bonos del Cliente",450, 100, 95, 20,"",false);
		  	  jtBono.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jtBono);
		  	  
		  	  jtCiudadMunicipio = getJTextField("Ciudad / Municipio",130, 75, 215, 20,"",false);
		  	  panelApellidosNombres.add(jtCiudadMunicipio);
		  	  
		  	  jtCupo = getJTextField("Cupo",450, 75, 95, 20,"",false);
		  	  jtCupo.setHorizontalAlignment(SwingConstants.RIGHT);
		  	  panelApellidosNombres.add(jtCupo);
		  	  
		  	  jtCelular = getJTextField("Celular",250, 100, 95, 20,"",false);
		  	  panelApellidosNombres.add(jtCelular);
		  	  
		  	  jtNombre = getJTextField("Nombre",240, 30, 200, 20,"",false);
		  	  panelIdentificacion.add(jtNombre);
		  	  
		  	  
		  	  jtNit = getJTextField("Nit",130, 30, 105, 20,"",false);
		  	  panelIdentificacion.add(jtNit);
		  	  
		  	  
		  	  jcDepartamentoCombo = getJComboBox(130, 50, 215, 20,"Seleccione el Departamento");
		      jcDepartamentoCombo.setEnabled(false);
		      panelApellidosNombres.add(jcDepartamentoCombo);
		      
		      
		      jcTipoIdCombo = getJComboBox(20, 30, 105, 20,"Seleccione el JComboBox");
		      jcTipoIdCombo.addItem("Cédula");
              jcTipoIdCombo.addItem("Tarjeta Id.");
              jcTipoIdCombo.addItem("Pasaporte");
		      jcTipoIdCombo.setEnabled(false);
		      panelIdentificacion.add(jcTipoIdCombo);
		  	  
		  	  //JButton
		  	  jbCerrar = getJButton("Cerrar",283, 210, 125, 25,"","Cerrar");
		      jbCerrar.addActionListener(this);
		      dialogClientes.add(jbCerrar);
				  	  
		  	  
		  	  dialogClientes.add(panelIdentificacion);
		  	  dialogClientes.add(panelApellidosNombres);
		  	  
        }
        
        buscarRegistroClientes();
        
        dialogClientes.setVisible(true);
    }    
    
    //**************************** Metodo buscarRegistroProveedores ************************

    private void buscarRegistroClientes() { 

        String sentenciaSQL = "Select *"+
                              "From   Clientes "+
                              "Where TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +"' and idCliente = '" + jtIdCliente.getText()  +"'";

       
       
      
        
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
           
           
           if (resultado.next()) {
           	   
           	      jcTipoId.setSelectedIndex(resultado.getString(1).equals("C") ? 0 : resultado.getString(1).equals("T") ? 1 : 2 );
                  jtNit.setText(resultado.getString(2));
			      jtNombre.setText(resultado.getString(3) + " " +resultado.getString(4));
			      jtDireccion.setText(resultado.getString(5));
			      jtTelefono.setText(resultado.getString(6));
			      jtCelular.setText(resultado.getString(7));
			      jtCupo.setText(resultado.getString(9));
			      jtSaldo.setText(resultado.getString(10));
	              String codigoDivisionPolitica = resultado.getString(11); //Se asigna la division politica
 	              jtBono.setText(resultado.getString(13));
 	              
 	              Vector municipios =   buscarMunicipio(conectarMySQL,codigoDivisionPolitica); //Se busca el nombre del departamento y municipio o corregimiento
	 	   	      jcDepartamentoCombo.removeAll();
	 	   	      jcDepartamentoCombo.addItem(municipios.elementAt(0));
	 	   	      jtCiudadMunicipio.setText(municipios.elementAt(1).toString());
 	              
           }  
           
        } catch (Exception e) {
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        
        
     }   
      //**************************** Metodo actualizar ************************

      private void actualizar() throws Exception { 
      
          String condicion = "";
          String datos[];
          
          //Se procede a guardar los detalles
          int numFilas = obtenerFilas(tablaVentas,9);
        
          for (int i = 0; i < numFilas; i++) {
          	
          	 condicion = "idProducto = '" + tablaVentas.getValueAt(i,1) +"'";
          	 
          	 datos = new String[1];
          	 
          	 int cantidadVendida = Integer.parseInt(tablaVentas.getValueAt(i,6).toString());
          	 
          	 datos[0] = "Stock = Stock - " + cantidadVendida;
          	 
          	 actualizar("Productos",datos,condicion,conectarMySQL,false); //Se actualiza tabla Productos
          	 
          	 //se actualiza el Lote sacando productos de lote mas viejo a traves de un procedimiento
          	 callStmt.setInt(1,Integer.parseInt(jtNumeroFactura.getText()));
			 callStmt.setString(2,tablaVentas.getValueAt(i,1).toString());
			 callStmt.setInt(3,cantidadVendida); 
			 
			 //** Se ejecuta el procedimiento
             callStmt.execute();
                        
          	
          } 	

      }
    
    
    //*****************************Metodo traerInformacion() ******************************************
    
    private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select Nombre,Apellido,Direccion,Telefono,Celular,Correo,Cupo,Saldo,DivisionPolitica,Bono "+
                              "From   Clientes "+
                              "Where  TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) + "' and idCliente ='" + jtIdCliente.getText() + "'"; 
       
       
        
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 


	             if (resultado.next()) { 
	
	                 jtNombreCliente.setText(resultado.getString(1) + " " + resultado.getString(2));
	                 jtTotalBonos.setText(resultado.getString(10));
	                 
	                 if (jtTotalBonos.getText().equals("0")) {
	                 
	                 
	                    jtTotalBonos.setFocusable(false);
	                    jtTotalBonos.setEditable(false);
	                    jtTotalBonos.setBackground(color);
	                 
	                 } else {
	                 	
	                 	jtTotalBonos.setFocusable(true);
	                    jtTotalBonos.setEditable(true);
	                 }    
	                 
		 	   	     resultadoBoolean = true;
		 	   	}   
		 	   	
          } 

        } catch (Exception e) {
        	
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultadoBoolean;
    }

    //*********************** Metodo buscarRegistroProducto ************************

     private boolean buscarRegistroProducto(int filaSeleccionada) {
 	         
 	         boolean resultadoBoolean = false;
 	         
 	         final String sentenciaSQL = "Select P.Descripcion,P.Stock,P.PrecioMininoVenta,P.PrecioListaVenta,P1.PrecioCompra "+
 	                                     "From Productos P, Precios P1 "+
 	                                     "Where P.IdProducto = P1.IdProducto and P.IdProducto = '" + tablaVentas.getValueAt(filaSeleccionada,1)  +"' "+
 	                                     "      and P1.FechaHasta is null";
 	                                     
 	         
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     if (resultado.next()) {
                      
                       tablaVentas.setValueAt(resultado.getString(1),filaSeleccionada,2);
                       tablaVentas.setValueAt(resultado.getString(2),filaSeleccionada,3);
                       tablaVentas.setValueAt(resultado.getString(3),filaSeleccionada,4);
                       tablaVentas.setValueAt(resultado.getString(4),filaSeleccionada,5);
                       tablaVentas.setValueAt(resultado.getString(5),filaSeleccionada,11);
                       
                       
                       resultadoBoolean = true;
                       
                     }
                     
                     
	 	     }  catch (Exception e) {
	 	                          
	 	          Mensaje("Error en la busqueda " +e,"Reporte de Error", 0);        
	 	     
	 	     }
	 	     
	 	     return resultadoBoolean;
     
 	  }

     

      //**************************** Metodo eliminar ************************

      private void eliminar() throws Exception  { 

			int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar esta Venta?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

		    if (opcion == 0) {

				 // Se especifica la condición de borrado 

				 String condicion = "";

				 eliminarRegistro("Venta",condicion,conectarMySQL,false); 

			}

	  }
      
      //**************************** Metodo mostrarListaValores ************************

	  private void mostrarListaValores() {
		
		int posicionY = 161 + 17 * filaSelecionada ;
        
        
        listarValores(getSentencia1(),conectarMySQL,getComponentesRetorno1(),94,posicionY,editorProductoJTable,editorCantidadProductoJTable,1,2,filaSelecionada,getOcultarColumnas1());
        
        if (getJTableArrayListaValores()[1].getColumnModel().getColumn(0).getPreferredWidth() != 110) {
			     
	        getJTableArrayListaValores()[1].getColumnModel().getColumn(0).setPreferredWidth(110);
	        getJTableArrayListaValores()[1].getColumnModel().getColumn(1).setPreferredWidth(270);
	    }        
     	         	                                      
	 }

     //********************* configurarJDialogContacto()****************************************
    private void configurarJDialogPrecios() {
    	
    	
    	if ( dialogPrecio ==null ) {
    		
    		  dialogPrecio = new JDialog(getJFrame(),"Descuento",true);
		  	  dialogPrecio.setLayout(null);
		  	  dialogPrecio.setSize(400, 240);
		  	  dialogPrecio.setLocationRelativeTo(null);
		  	  
		  	  
		       //** Se declaran los JLabel
		      JLabel jlPrecioVenta = getJLabel("P. Venta",90, 110, 70, 15);
		      jlPrecioVenta.setHorizontalAlignment(SwingConstants.CENTER);
		      dialogPrecio.add(jlPrecioVenta);
		
		      JLabel jlPrecioMinimo = getJLabel("P. Minimo",135, 60, 70, 15);
		      jlPrecioMinimo.setHorizontalAlignment(SwingConstants.CENTER);
		      dialogPrecio.add(jlPrecioMinimo);
		
		      JLabel jlPrecioLista = getJLabel("P. Lista",35, 60, 70, 15);
		      jlPrecioLista.setHorizontalAlignment(SwingConstants.CENTER);
		      dialogPrecio.add(jlPrecioLista);
		
		      JLabel jlPorcentajeMax = getJLabel("% Máximo de Descuento",235, 60, 145, 15);
		      dialogPrecio.add(jlPorcentajeMax);
		
		      JLabel jlPorcentaje1 = getJLabel("%",320, 78, 15, 15);
		      dialogPrecio.add(jlPorcentaje1);
		
		      JLabel jlPorcentaje2 = getJLabel("%",320, 128, 15, 15);
		      dialogPrecio.add(jlPorcentaje2);
		
		      JLabel jlPorcentajeDescuento = getJLabel("% de Descuento",260, 110, 95, 15);
		      dialogPrecio.add(jlPorcentajeDescuento);
		      
		      JLabel jlPrecioCompra  = getJLabel("P. Compra",167, 10, 95, 15);
		      dialogPrecio.add(jlPrecioCompra);
		      
		      //** Se instancian los JSeparator
		
		      JSeparator jSeparator1 = new JSeparator();
		      jSeparator1.setBounds(42, 155, 310, 2);
		      jSeparator1.setForeground(Color.lightGray);
		      dialogPrecio.add(jSeparator1);
		
		      //** Se agregan los JTextField
		
		      jtPrecioVenta = getJTextField("",90, 125, 70, 20,"Digíte el Código de la Categoría","");
		      jtPrecioVenta.setHorizontalAlignment(SwingConstants.RIGHT);
		      jtPrecioVenta.addFocusListener(this);
		      jtPrecioVenta.addActionListener(this);
		      dialogPrecio.add(jtPrecioVenta);
		
		      jtMinimo = getJTextField("Precio Minimo",135, 75, 70, 20,"",false);
		      jtMinimo.setHorizontalAlignment(SwingConstants.RIGHT);
		      jtMinimo.addFocusListener(this);
		      dialogPrecio.add(jtMinimo);
		
		      jtPrecioLista = getJTextField("",35, 75, 70, 20,"",false);
		      jtPrecioLista.setHorizontalAlignment(SwingConstants.RIGHT);
		      dialogPrecio.add(jtPrecioLista);
		      
		      jtPrecioCompra = getJTextField("",160, 25, 70, 20,"",false);
		      jtPrecioCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		      dialogPrecio.add(jtPrecioCompra);
		      
		      jtMaximoDescuento = getJTextField("",290, 75, 25, 20,"",false);
		      jtMaximoDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
		      dialogPrecio.add(jtMaximoDescuento);
		
		      jtPorcentajeDescuento = getJTextField("",290, 125, 25, 20,"","");
		      jtPorcentajeDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
		      jtPorcentajeDescuento.addFocusListener(this);
		      jtPorcentajeDescuento.addActionListener(this);
		      dialogPrecio.add(jtPorcentajeDescuento);
		
		      //** Se agregan los JButton
		
		      jbAceptar = getJButton("Aceptar",60, 170, 125, 25,"","");
		      jbAceptar.addActionListener(this);
		      dialogPrecio.add(jbAceptar);
		
		      jbCancelar = getJButton("Cancelar",210, 170, 125, 23,"","");
		      jbCancelar.addActionListener(this);
		      dialogPrecio.add(jbCancelar);
		      
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

                  //Se valida la información antes de guardar 
                  if (validarRegistro(camposObligatorios)) {
                  	
                  	if (obtenerFilas(tablaVentas,9) > 0) {
                  		
                  		sumarPagos();
                  		
                  		if (Long.parseLong(convertirNumeroSinFormato(jtTotalVueltas.getText())) >= 0) {
                  		
                  		      try {
		
		                          guardar(); 
		                  
		                          actualizar(); //Se actualiza el inventario
		                  
		                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 
		
		                          Mensaje("Información de Venta registrada correctamente","Información",1);
		                          
		                         if (!imprimirFacturaAutomatica) {
		                          	
		                          	imprimirFactura();
		                        
		                         } else {
		                        	
	                                //Se verifica si se desea imprimir la factura
					             	int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea imprimir Factura?","Confirmación",
						 	                                              JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
						 	                                              
						 	        if (opcion == 0) 
						 	        
						 	            //Se imprime la factura	
						 	            imprimirFactura();
				 	     	
		                        	
		                        	
		                         }
		
		                          limpiar();   //Se limpia la forma 
		
		                       } catch (Exception e) {
		
		                           conectarMySQL.rollback();
		
		                           Mensaje("Error al Insertar Venta" +e,"Error",0); 
		
						      }
						 
						 } else {
						 	
						 	Mensaje("El valor de pago es inferior al total","Información",2);
						   jtTotalEfectivo.grabFocus();
						 	
						 }      
					      
					 }  else {
					 	
					 	Mensaje("Se debe especificar el detalle de la factura","Información",2);
					 	jtFechaVenta.grabFocus();
					 	
					 }  

                   }

            } else 

                if (fuente == Beliminar) { 

                   try {

                       eliminar(); 

                       conectarMySQL.commit();   //Se registra los cambios en la base de dato 

                       Mensaje("Venta Eliminado","Información",JOptionPane.INFORMATION_MESSAGE);

                       limpiar();  // Se limpia la forma 

                 } catch (Exception e) {

                       conectarMySQL.rollback();

                       Mensaje("No se puede eliminar Venta" + e,"Error",JOptionPane.ERROR_MESSAGE); 

                  }

                }  else 


                       if (fuente == Bsalir) {

                         ocultarJFrame(); 

                       } else
					
							if (fuente == jtNombreCliente)  {
							
								if (getJTableArrayListaValores()[0].isVisible()) {
								
											if (getJTableArrayListaValores()[0].getSelectedRow() > -1) {
											
												getJTableArrayListaValores()[0].setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
						                    
						                    } else {
						                    
												getJTableArrayListaValores()[0].setRowSelectionInterval(0,0);
						
												getJTableArrayListaValores()[0].grabFocus();
										    }
				
							       } 
						       
						       
						     } else 
		                              	
                              	 if (fuente == jbCalendario) {
                              	    
                              	    if (calendario == null)
                              	    	
                              	 	    calendario = new Calendario(jtFechaVenta,getJFrame(),490,170);
                              	 	    
                              	 	else
                              	 	     
                              	 	     calendario.mostrarCalendario(true);    
                              	 	
                              	 } else
		                              	    
                              	    if (fuente == jbAceptar) {
                              	    	  
                              	    	  tablaVentas.getCellEditor(filaSelecionada,7).stopCellEditing();
                                          tablaVentas.setValueAt(jtPrecioVenta.getText(),filaSelecionada,7);
							              tablaVentas.editCellAt(filaSelecionada,7);
				                          editorPrecioVentaProductoJTable.getComponent().requestFocus();
				                          dialogPrecio.setVisible(false);
                              	    	
                              	    } else
                              	       
                              	       if (fuente == jbCancelar) {
                              	    	
                              	          tablaVentas.editCellAt(filaSelecionada,7);
				                          editorPrecioVentaProductoJTable.getComponent().requestFocus();
				                          dialogPrecio.setVisible(false);
                              	    	
                              	       } else
		                              	    
		                              	    if (fuente == jtProductoJTable)  {
					
													if (getJTableArrayListaValores()[1].isVisible()) {
									
														if (getJTableArrayListaValores()[1].getSelectedRow() > -1)
									
															getJTableArrayListaValores()[1].setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
									
															getJTableArrayListaValores()[1].setRowSelectionInterval(0,0);
									
															getJTableArrayListaValores()[1].grabFocus();
									
												     } 
										  			     
										  	 } else
										  	 
					  		          	       if (fuente == jtPrecioVenta || fuente == jtPorcentajeDescuento)  {
					                               
					                               jbAceptar.doClick();
					                               
								  	           } else
								  	           
								  	              if (fuente == jbEditarCliente) {
		                             
						                              if (!jtIdCliente.getText().isEmpty())   
						                                 
						                                 configurarJDialogClientes();
						                                 
						                              else {
						                              	  
						                              	  Mensaje("Se debe especificar la cedula del cliente","Información",2);
						                              	  jtIdCliente.grabFocus();
						                              	
						                              }
					                              } else
					                                 
					                   
						                              if (fuente == jbCerrar) {
						                             
						                                   dialogClientes.setVisible(false);
						                                   jtFechaVenta.grabFocus();
						                            
						                              }
	 } 
     
     //******************************** detenerEditar ********************************
 	
 	 public void detenerEditar() {
 		
 	   if (filaSelecionada > - 1 && tablaVentas.getEditingColumn() > -1)	
 		
 		 tablaVentas.getCellEditor(filaSelecionada,tablaVentas.getEditingColumn()).stopCellEditing();
 	
 	 }
 	 
 	 //******************************** Metodo validarFilas()  ***************************************

	final boolean validarFilas() {
		
		boolean resultado = true;
		boolean sw = true;
		
		int i = 0; 
		int numeroFilas = tablaVentas.getSelectedRow();
	    
	    
	    while (i < numeroFilas &&  sw) {
			
			int j = 1;
			
			while (j < 9 && sw) {
				
				if (tablaVentas.getValueAt(i,j) == null || tablaVentas.getValueAt(i,j).toString().isEmpty()) {
				
				  sw = false;
				  
				  
				}  
				
				j++;
				
			}
			
			i++;
			
		}
		
		return sw;		
		
	}
 	
 	 //******************************** valueChanged ********************************
 	
     public void valueChanged(ListSelectionEvent e) { 
      
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            
            int fila = tablaVentas.getSelectedRow();
            
            if (fila > -1) {
            
		           if (validarFilas()) {
		           
		           
		              if (tablaVentas.getValueAt(fila,0) == null || tablaVentas.getValueAt(fila,0).toString().isEmpty())
		            
		                  tablaVentas.setValueAt( fila + 1,fila,0);
				   
				   }
		      }
		      
     }      
      
     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
	     
		  if (f.getComponent() == jtNombreCliente && (getDialogoArrayListaValores() == null || getDialogoArrayListaValores()[0] == null || (getDialogoArrayListaValores()[0]!= null && !getDialogoArrayListaValores()[0].isVisible())) && jtNombreCliente.isFocusable() && mostrarListaAutomatica) {
		  	
		     listaValor.mostrarListaValores(76);
		     
		     if (getJTableArrayListaValores()[0].getColumnModel().getColumn(0).getPreferredWidth() != 60) {
		     
			     getJTableArrayListaValores()[0].getColumnModel().getColumn(0).setPreferredWidth(60);
	             getJTableArrayListaValores()[0].getColumnModel().getColumn(1).setPreferredWidth(90);
	             getJTableArrayListaValores()[0].getColumnModel().getColumn(2).setPreferredWidth(230);
	             
             }	       

		}  else 
		

			if (f.getComponent() != jtNombreCliente  && getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[0]!= null && getDialogoArrayListaValores()[0].isVisible()) {
              
               if (getJTableArrayListaValores()[0].getRowCount() > 0) {
			                    
					//Se oculta el scroll de la lista de valores
					jcTipoId.setSelectedItem(getJTableArrayListaValores()[0].getValueAt(0,0).toString());
					jtIdCliente.setText(getJTableArrayListaValores()[0].getValueAt(0,1).toString()); 
					jtNombreCliente.setText(getJTableArrayListaValores()[0].getValueAt(0,2).toString());
					jtTotalBonos.setText(getJTableArrayListaValores()[0].getValueAt(0,3).toString());
	                
					getDialogoArrayListaValores()[0].setVisible(false); //Se oculta automaticamente la lista de valores
               
               } else {
               	   
               	    jcTipoId.setSelectedIndex(0);
					jtIdCliente.setText(""); 
					jtNombreCliente.setText("");
					jtTotalBonos.setText("");
					jtIdCliente.grabFocus();
               	
               }
		      
			}  else
			    
			    if (f.getComponent() == jtProductoJTable  && (getDialogoArrayListaValores() == null || getDialogoArrayListaValores()[1] == null || (getDialogoArrayListaValores()[1]!= null && !getDialogoArrayListaValores()[1].isVisible())) && jtProductoJTable.isFocusable() && mostrarListaAutomatica) {
	             
		              if (tablaVentas.getValueAt(filaSelecionada,0) != null) {
		              
				              mostrarListaValores();
				        
		             }   
		      
		     }  else
			    
			       if (f.getComponent() == jtFechaVenta) {
			       
					     if (jtTotalBonos.getText().equals("0")) {
			                 
			                 
			                    jtTotalBonos.setFocusable(false);
			                    jtTotalBonos.setEditable(false);
			                    jtTotalBonos.setBackground(color);
			                 
			             } 
			       } else
			       
				  	    if (f.getComponent() != jtProductoJTable && getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[1]!= null && getDialogoArrayListaValores()[1].isVisible() && jtNombreCliente.isFocusable()) {
	            
	                	     detenerEditar();
							 
							 getDialogoArrayListaValores()[1].setVisible(false);
						
					    	
					    }  else
			         
					         if (f.getComponent() == jtTotalEfectivo && !jtTotalEfectivo.getText().isEmpty()) {
					         	 
					          	 jtTotalEfectivo.setText(convertirNumeroSinFormato(jtTotalEfectivo.getText()));
					         	
					         } else
					            
					             if (f.getComponent() == jtTotalTarjetas && !jtTotalTarjetas.getText().isEmpty()) {
					         	 
					          	   jtTotalTarjetas.setText(convertirNumeroSinFormato(jtTotalTarjetas.getText()));
					         	
					             } else
					            
					               if (f.getComponent() == jtTotalBonos && !jtTotalBonos.getText().isEmpty()) {
					           	 
					            	   jtTotalBonos.setText(convertirNumeroSinFormato(jtTotalBonos.getText()));
					         	
					               } else
									           
									           if (f.getComponent()  == jtProductoJTable) {
										      
										      	  ((JTextField)editorProductoJTable.getComponent()).selectAll();
										       
									  	       } else
									  	         
									  	           if (f.getComponent()  == jtCantidadProductoJTable) {
										      
										        	  ((JTextField)editorCantidadProductoJTable.getComponent()).selectAll();
									  	       
									  	           }
			
			
			
			
			
			
          if (f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField") || f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JFormattedTextField") || f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextArea"))
			          
			   ((JTextField)f.getComponent()).selectAll();
			   
		
		 // se coloca el atributo visual por defecto
	       f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());


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

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) {
      	
        
          if (!buttonEditor.isPushed)	{
     
		     	if (f.getComponent() == jtIdCliente && !jtIdCliente.getText().isEmpty() &&  (f.getComponent() != null && f.getOppositeComponent().getName() == null || !f.getOppositeComponent().getName().equals("ejecutar"))) {
		      	  
		      	  		if (!traerInformacion()) {
			      			
			      			
			      		    mostrarListaAutomatica = false;
			            	
			            	//Se verifica si se desean hacer tramites diferentes a matriculas o radicado
					   	    int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Identificacion " +  jtIdCliente.getText( )+ " no registrada \nDesea registrar esta identificacion?","Notificación ",JOptionPane.YES_NO_OPTION);
						    
						    if ( opcion == 0 ) {
						    	
						    	mostrarListaAutomatica = false;
						    	new Cliente(conectarMySQL,getJFrame(),jcTipoId.getSelectedIndex(),jtIdCliente.getText());
						    	getJFrame().setVisible(false);
						    	
						    	
						    } else {
						    	
						    	jtIdCliente.setText("");
						    	
						    }	
						 
			            	
			            	  	  
			            } else {
			            	
			            	mostrarListaAutomatica = false;
			            	jtFechaVenta.grabFocus();
			            	
			            }
			     
		      		
		      	} else
		      	
		          if (f.getComponent() ==  jtFechaVenta &&  (f.getOppositeComponent() != null && f.getOppositeComponent().getName() == null || ( f.getOppositeComponent().getName() != null && !f.getOppositeComponent().getName().equals("ejecutar")))) {
		   
		              if (!esFecha(jtFechaVenta.getText())) {
		              	  
		              	   Mensaje("Fecha " + jtFechaVenta.getText()  +" Invalida " ,"Reporte de error",0);
		            	   jtFechaVenta.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
		            	   jtFechaVenta.grabFocus();
		              	
		              	
		              } else {
		                       	
		                     tablaVentas.setValueAt("1",0,0);
		                     tablaVentas.editCellAt(0,1);
		                     editorCodigoProductoJTable.getComponent().requestFocus();
		                     
		                     
		                  
		          	}
		      	  
		      	  } else 
		      
		          	 if (f.getComponent() ==  jtCodigoProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
		   
		          	 	if (tablaVentas.getValueAt(filaSelecionada,1) != null && !tablaVentas.getValueAt(filaSelecionada,1).toString().isEmpty()) {
		          	      
		          	      
				      	  if (validarDatosJTable())	 {//Se valida que al valor digitado no se encuentre repetido
				      		
			      
				          	 	   if (!buscarRegistroProducto(filaSelecionada)) {
					         	        
					         	         mostrarListaAutomatica = false;
					         	       	 Mensaje("Producto " +  tablaVentas.getValueAt(filaSelecionada,1) +" no registrado","Información",2);
					         	     	 tablaVentas.setValueAt("",filaSelecionada,1);
					         	         tablaVentas.editCellAt(filaSelecionada,2);
				                         editorProductoJTable.getComponent().requestFocus();
					         	     	 mostrarListaAutomatica = true;
					         	     	
					         	     
					         	      } else {
					         	      	
					         	      	tablaVentas.editCellAt(filaSelecionada,6);
				                        editorCantidadProductoJTable.getComponent().requestFocus();
				                          
				                        
				                      }  
				           } else {
				           	 
				           		mostrarListaAutomatica = false;
				           		tablaVentas.setValueAt("",filaSelecionada,1);
				           		tablaVentas.editCellAt(filaSelecionada,1);
				                editorCodigoProductoJTable.getComponent().requestFocus();
				                Mensaje("Este codigo ya ha sido digitado","Información",2);
				                          
			      	
				           }           
		                         
			         	 } else {
			         	 
			         	 	
			         	 	    tablaVentas.editCellAt(filaSelecionada,2);
		                        editorProductoJTable.getComponent().requestFocus();
		                        
			         	 }
		          	 	     	
			         	     
		          	 } else 
		      
		          	 
		          	 	if (f.getComponent() ==  jtCantidadProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null && f.getOppositeComponent().getName() == null) {
		         
		              	     if (tablaVentas.getValueAt(filaSelecionada,6) != null && !tablaVentas.getValueAt(filaSelecionada,6).toString().isEmpty()) {
		              	     
				              	    long cantidad = Long.parseLong(tablaVentas.getValueAt(filaSelecionada,6).toString());
				              	    long disponible = Long.parseLong(tablaVentas.getValueAt(filaSelecionada,3).toString());
				              	    
				              	    if (cantidad <= disponible && cantidad > 0) { //Se valida que la cantida sea menoo igual al disponible
				              	     
					              	      if (tablaVentas.getValueAt(filaSelecionada,7) != null && !tablaVentas.getValueAt(filaSelecionada,7).toString().isEmpty()) {
						         	         
							         	     
							         	         long subTotal = cantidad * Long.parseLong(tablaVentas.getValueAt(filaSelecionada,7).toString());
							         	    
							         	         tablaVentas.setValueAt(formatter.format(subTotal),filaSelecionada,8);
							         	         tablaVentas.setValueAt(subTotal,filaSelecionada,9);
								         	     
							         	         
							         	       
									       }
								       
								          tablaVentas.setValueAt(tablaVentas.getValueAt(filaSelecionada,5),filaSelecionada,7);
							              tablaVentas.editCellAt(filaSelecionada,7);
							              editorPrecioVentaProductoJTable.getComponent().requestFocus();
				                          ((JTextField)(editorPrecioVentaProductoJTable.getComponent())).selectAll();
				                    } else {
				                    	 
				                    	 mostrarListaAutomatica = false;
				                    	 tablaVentas.setValueAt("",filaSelecionada,6);
				                    	 tablaVentas.editCellAt(filaSelecionada,6);
							  	         editorCantidadProductoJTable.getComponent().requestFocus();
							  	         Mensaje("La cantidad debe ser mayor cero y al Stock disponible","Información",2);
				                    	 
				                    	 
				                    }      
		                     		
							  } else {
							  	   
							  	   mostrarListaAutomatica = false;
							  	   tablaVentas.setValueAt("",filaSelecionada,6);
			         	           tablaVentas.editCellAt(filaSelecionada,6);
							  	   editorCantidadProductoJTable.getComponent().requestFocus();
							  	   
							  	   
							  	   if  (getDialogoArrayListaValores() == null || (getDialogoArrayListaValores()[1] != null && !getDialogoArrayListaValores()[1].getName().equals("dummy")))
							  	   
							  	      Mensaje("Se debe especificar la cantidad de producto a comprar","Información",2);
							  	      
							  	   else
							  	   
							  	      if (getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[1] != null)
							  	         
							  	           getDialogoArrayListaValores()[1].setName("");
							  	          
							  	      
							  	   
							  } 	
							  	
						
		          	 	} else 
		      
		          	 	   	if (f.getComponent() ==  jtPrecioVentaProductoJTable && comodinFocusCellEditor  &&  f.getComponent() != null && f.getOppositeComponent().getName() == null) {
		         
		          	 	   		
		          	 	   		 if (tablaVentas.getValueAt(filaSelecionada,7) != null && !tablaVentas.getValueAt(filaSelecionada,7).toString().isEmpty()) {
		                              
		                              long precioVenta = Long.parseLong(tablaVentas.getValueAt(filaSelecionada,7).toString());
		                              long precioLista = Long.parseLong(tablaVentas.getValueAt(filaSelecionada,5).toString());
		                              long precioMinimo = Long.parseLong(tablaVentas.getValueAt(filaSelecionada,4).toString());
		                              
		                              
		                               if (precioVenta >= precioMinimo && precioVenta <= precioLista) {
		                               
		                                           long subTotal = Long.parseLong(tablaVentas.getValueAt(filaSelecionada,6).toString()) * precioVenta;
								         	      
								         	        tablaVentas.setValueAt(formatter.format(subTotal),filaSelecionada,8);
								         	        tablaVentas.setValueAt(subTotal,filaSelecionada,9);
								         	     
								         	       	
						         	               if (tablaVentas.getValueAt(filaSelecionada,3) != null && tablaVentas.getValueAt(filaSelecionada,4) != null) {
						          	       
									          	            if (f.getOppositeComponent().getName() != null)
									          	            
									          	                 detenerEditar();
									          	            
														     if (f.getOppositeComponent().getName() == null) {
												         	   
												         	    if (filaSelecionada > 7) {
				                                	      	
						          	 		                        scrollPane.getVerticalScrollBar().setValue(17 * (filaSelecionada - 7));

									 		                         
						                                	    }         
												         	          
										         	          tablaVentas.setValueAt(filaSelecionada + 2, filaSelecionada + 1, 0);
											         	      tablaVentas.editCellAt(filaSelecionada + 1,1);
										         	          editorCodigoProductoJTable.getComponent().requestFocus();
										         	          
										         	            
														    }
					
							          	 	   	        }
							          	 	   	        
							          	} else {
							          		   
							          		   mostrarListaAutomatica = false;
							          		   tablaVentas.setValueAt("",filaSelecionada,7);
						         	           tablaVentas.editCellAt(filaSelecionada,7);
										  	   editorPrecioVentaProductoJTable.getComponent().requestFocus();
										  	   Mensaje("El precio de venta de ser mayor o igual al precio minimo y menor o igual al precio de lista","Información",2);
							          		
							          		
							          	}
							          		
							          	
							          	
							          	 	   	        
					          	 }	else {
							  	       
							  	       mostrarListaAutomatica = false;
								  	   tablaVentas.setValueAt("",filaSelecionada,7);
				         	           tablaVentas.editCellAt(filaSelecionada,7);
								  	   editorPrecioVentaProductoJTable.getComponent().requestFocus();
								  	   Mensaje("Se debe especificar el precio de venta producto","Información",2);
								  	   
								  }    	     
								         		
		          	 	   	} else
		          	 	   	   
		          	 	   	   if (f.getComponent() == jtTotalEfectivo && !jtTotalEfectivo.getText().isEmpty() ) {
		          	 	   	   	
		          	 	   	   	    jtTotalEfectivo.setText(formatter.format(Long.parseLong(jtTotalEfectivo.getText())));
		          	 	   	   	    
		          	 	   	   	    
		          	 	   	   	    sumarPagos();
		          	 	   	   	    
		          	 	   	   	
		          	 	   	   }  else
			          	 	   	   
			          	 	   	   if (f.getComponent() == jtTotalTarjetas && !jtTotalTarjetas.getText().isEmpty()) {
			          	 	   	   	 
			          	 	   	   	 jtTotalTarjetas.setText(formatter.format(Long.parseLong(jtTotalTarjetas.getText())));
			          	 	   	   	    	 
			          	 	   	   	 sumarPagos();
			          	 	   	
		          	 	   	   	
			          	 	   	   	
			          	 	   	   } else
			          	 	   	   
				          	 	   	   if (f.getComponent() == jtTotalBonos  && !jtTotalBonos.getText().isEmpty()) {
				          	 	   	   	
				          	 	   	   	 jtTotalBonos.setText(formatter.format(Long.parseLong(jtTotalBonos.getText())));
				          	 	   	   	 
				          	 	   	   	 sumarPagos();
				          	 	
		          	 	   	   	
				          	 	   	   	
				          	 	   	   }  else 
				          	 	   	      
				          	 	   	       if (f.getComponent() == jtPrecioVenta && !jtPrecioVenta.getText().isEmpty()) {
				          	 	   	 
				          	 	   	       	 long precioVenta = Long.parseLong(jtPrecioVenta.getText());
		                                     long precioLista = Long.parseLong(jtPrecioLista.getText());
		                                     long precioMinimo = Long.parseLong(jtMinimo.getText());
		                             
		                                     if (precioVenta >= precioMinimo && precioVenta <= precioLista) {
		                                     	
		                                     	   if (precioMinimo == precioLista )
				        
												        jtPorcentajeDescuento.setText("0");
												      
												      else  
												      
												         jtPorcentajeDescuento.setText(String.valueOf(redondearNumero(precioMinimo/precioLista,2) * 100));             
									      		  	
		                                        
		                                     } else {
		                                     	
		                                     	 mostrarListaAutomatica = false;
		                                     	 Mensaje("El precio de venta de ser mayor o igual al precio minimo y menor o igual al precio de lista","Información",2);
							                 	 jtPrecioVenta.setText("");
							                 	 jtPrecioVenta.grabFocus();
		                                     	
		                                     }
				          	 	   	       	
				          	 	   	       } else {
				          	 	   	       	   
				          	 	   	       	    if (f.getComponent() == jtPorcentajeDescuento && !jtPorcentajeDescuento.getText().isEmpty()) {
				          	 	   	       	      
				          	 	   	       	      long porcentajeMaximo = Long.parseLong(jtMaximoDescuento.getText());
				          	 	   	       	      long porcentaje = Long.parseLong(jtPorcentajeDescuento.getText());
				          	 	   	       	      
				          	 	   	       	      if (porcentaje <= porcentajeMaximo) {
				          	 	   	       	      
				          	 	   	       	    	  long precioMinimo = Long.parseLong(jtMinimo.getText());
				          	 	   	       	    	  Float valor = redondearNumero(precioMinimo * (1 + porcentajeMaximo),0); 
				          	 	   	       	    	  jtPrecioVenta.setText(String.valueOf(valor.intValue())); 
		                              
				          	 	   	       	      }	else {
		                                     	         
		                                     	         mostrarListaAutomatica = false; 
				                                     	 Mensaje("El porcentaje debe ser menor al maximo","Información",2);
									                 	 jtPorcentajeDescuento.setText("");
									                 	 jtPorcentajeDescuento.grabFocus();
				                                     	
				                                     }
				          	 	   	       	    	
				          	 	   	       	    }	
				          	 	   	       	   
				          	 	   	       	
				          	 	   	       }       
          	 	   	       
          	 	   	   
      	  } else {
      	  
      	       buttonEditor.isPushed = false; //Se borran los datos de la fila que se borro
      	       
      	       int numFilas = obtenerFilas(tablaVentas,9);  //Se mueven las filas
     
      	 
      	       for (int i = filaSelecionada ; i < numFilas ; i++) {
      	       	 
      	       	 for (int j = 0; j < 11; j++) 	  
      	       	   
      	       	   tablaVentas.setValueAt(tablaVentas.getValueAt(i+1,j), i, j);
      	       }
      	        
      	       
      	       for (int i = 1 ; i < 11; i++)   //Cuando Se selecciona se borra la fila
	                        	
	              tablaVentas.setValueAt(null,numFilas,i);
	           
	            numFilas = obtenerFilas(tablaVentas,9);
	           
      	       tablaVentas.setValueAt(filaSelecionada + 1, filaSelecionada, 0);
      	       tablaVentas.setValueAt(null,filaSelecionada + 1,0);
      	       tablaVentas.setValueAt(null,filaSelecionada + 2,0);
      	       tablaVentas.editCellAt(numFilas,1);
               editorCodigoProductoJTable.getComponent().requestFocus();
       	      
      	 }
        
        
        
        mostrarListaAutomatica = true;
        comodinFocusCellEditor = true;
        
       			  	   
     
         
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());
        
        if (f.getComponent() != jtProductoJTable && f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField") || f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JFormattedTextField"))
			          
			   ((JTextField)f.getComponent()).selectAll();
      

      }
      
      
      //Clase que renderiza la celda con un JButton
      class ButtonRenderer extends JButton implements TableCellRenderer {   
      
          public ButtonRenderer() {   
              
     
              
           }
           
               
           public Component getTableCellRendererComponent(JTable table, Object value,                   
                                                          boolean isSelected, boolean hasFocus, 
                                                          int row, int column) {    
                                                          
            
			  
			  if (isSelected) {  
			     
			            setForeground(table.getSelectionForeground()); 
			            setBackground(table.getSelectionBackground());

			          
	                        
			            
			       } else {
			       	   
			          setForeground(table.getForeground());      
			          setBackground(UIManager.getColor("Button.background")); 
			       }    
			       
			      
			       return this;  
			
			}
	    
	    }
	    
	    //Clase que crea la edicion del JButton
	     class ButtonEditor extends DefaultCellEditor { 
	
			 protected JButton button;
			 public boolean isPushed;  
		   
             public ButtonEditor(JCheckBox checkBox) {  
              
	               super(checkBox);    
	               button = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Eliminar.gif")));
	               button.setOpaque(true);
	              
             }   
             
             
             public Component getTableCellEditorComponent(JTable table, Object value,                   
                                                         boolean isSelected, int row, int column) {  
                                                         
                                                         
                  isPushed = true;  
                  return button;
                   
             }   
             
             public Object getCellEditorValue() {    
                  
                   
                    return button ; 
              }
             
            
	    }
	    

   
}