package com.JASoft.azkaban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing. JFormattedTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;
import javax.swing.KeyStroke;


import javax.swing.JTextField;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.filechooser.FileFilter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing. JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultCellEditor;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.sql.ResultSet;

import java.text.NumberFormat;
import java.text.DecimalFormat;

import javax.swing.JDialog;

import java.util.Vector;
import java.util.HashMap;

import javax.swing.SwingUtilities;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.ConvertirNumColumn;
import com.JASoft.componentes.Calendario;

final public class Compra extends CrearJFrame implements ActionListener, FocusListener, ListSelectionListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Array para campos obligatorios
    private JTextField[] camposObligatorios;

    //** Se declaran los JPanel
    private  JPanel jpClientes;
    private  JPanel jpFecha;
    private  JPanel jpObservaciones;
   
  
    //** Se declaran los JTextField
    private  JTextField jtNumeroCompra;
    private  JTextField jtNumeroProveedor; //Dummy para almacenar el numero de proveedor
    private  JTextField jtNit;
    private  JTextField jtNombre;
    private  JTextField jtTelefono;
    private  JTextField jtNumeroFactura;
    private  JTextField jtTotal;
    private  JTextField jtCodigoProductoJTable;
    private  JTextField jtProductoJTable;
    private  JTextField jtCantidadProductoJTable;
    private  JTextField jtPrecioCompraProductoJTable;
    private  JTextField jtPrecioListaProductoJTable;
    private  JTextField jtPrecioPorcentajeListaProductoJTable;
    private  JTextField jtPrecioMinimoProductoJTable;
    private  JTextField jtPrecioPorcentajeMinimoProductoJTable;
    private  JTextField jtDireccion;
    private  JTextField jtNitDialog;
    private  JTextField jtNombreDialog;
    private  JTextField jtTelefonoDialog;
    private  JTextField jtPaginaWeb;
    private  JTextField jtEmail;
    private  JTextField jtCiudadMunicipio;
    private  JTextField jtFax;
    
    //** Se declaran los JFormattedTextField
    private  JFormattedTextField jtFechaFactura;
    
    //** Se declaran los JTextArea 
    private JTextArea jaObservaciones;
    
    //** Se declaran los JButton
    private  JButton jbCalendario;
    private  JButton jbInformacionProveedor;
    private  JButton jbCerrar;
    
     //** Se declaran los JComboBox
    private JComboBox jcDepartamento;
    
    jtFechaNacimiento
    
    //** Se declaran los JTable
    private  JTable tablaCompras;
    private  JTable tablaContactos;

   //Columnas y filas estaticas 
    private Object [] nombresColumnasEstaticas = {"Item","Código","Producto","Cantidad","P. Compra","P. Lista","%","P. Minimo","%","Subtotal","SubTotal Sin Formato",""};
    private Object [][] datosEstaticos = new Object[40][12];
    
    //** Clase para mostrar una lista de valores
    private ListaValor listaValorProveedores;
    private ListaValor listaValorProductos;
    
    //** NumberFormat
    private NumberFormat formatter;
    
    //Modelo de datos 
    private SortableTableModel dm;
    
    //JDialog
    private JDialog dialogProveedor;
    
    //int
    private int filaSelecionada;
    private int columnaSelecionada;
    
    //lONG
    private Long totalFactura = 0L;
    private Long totalCaja = 0L;
    
    
    //Calendario
    private Calendario calendario;
    
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    private boolean comodinFocusCellEditor = true;
    
    

   //** DefaultCellEditor
   private DefaultCellEditor editorCodigoProductoJTable;
   private DefaultCellEditor editorProductoJTable;
   private DefaultCellEditor editorCantidadProductoJTable;
   private DefaultCellEditor editorPrecioCompraProductoJTable;
   private DefaultCellEditor editorPrecioListaProductoJTable;
   private DefaultCellEditor editorPorcentajePrecioListaProductoJTable; 
   private DefaultCellEditor editorPrecioMinimoProductoJTable;
   private DefaultCellEditor editorPorcentajePrecioMinimoProductoJTable;
   
    //** ButtonRenderer
    private ButtonRenderer buttonRenderer = new ButtonRenderer();
    private ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox());
    
    //** HasTable
    private HashMap parametrosAzcaban;
     
    //** JScrollPane 
    private JScrollPane scroll; 
    
    //String
    private String nombreUsuario;

    //** Constructor General 
    public Compra(ConectarMySQL p_conectarMySQL,JFrame p_frame,HashMap parametrosAzcaban,String nombreUsuario,Long totalCaja) {

      super("Compra","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
       
      this.totalCaja = totalCaja;
      
      this.parametrosAzcaban = parametrosAzcaban;
      
      this.nombreUsuario = nombreUsuario;
    
      
      getJFrame().setName("Compra"); //Comodin utilizado cuando se llama desde aqui la forma Cliente


      //** Se agregan los JPanel

      jpClientes = getJPanel("Proveedor",60, 75, 475, 95,14);

      jpFecha = getJPanel("",550, 82, 180, 85,14);
     
      jpObservaciones = getJPanel("Observaciones / Anotaciones",10, 450, 620, 80,14);

      //** Se declaran los JLabel

      JLabel jlNumeroCompra = getJLabel("Nro. de Compra:",15, 8, 120, 15);
      jlNumeroCompra.setHorizontalAlignment(SwingConstants.LEFT);
      jpFecha.add(jlNumeroCompra);

      JLabel jlNit = getJLabel("Nit",20, 30, 75, 15);
      jpClientes.add(jlNit);

      JLabel jlNombre = getJLabel("Nombre",130, 30, 65, 15);
      jpClientes.add(jlNombre);

      JLabel jlTelefono = getJLabel("Teléfono",335, 30, 65, 15);
      jlTelefono.setHorizontalAlignment(SwingConstants.LEFT);
      jpClientes.add(jlTelefono);

      JLabel jlNumeroCompra1 = getJLabel("Factura Nro:",15, 36, 85, 15);
      jlNumeroCompra1.setHorizontalAlignment(SwingConstants.LEFT);
      jpFecha.add(jlNumeroCompra1);

      JLabel jlDetalle = getJLabel("Detalle: Factura de Compra",315,185,160,15);
      jlDetalle.setHorizontalAlignment(SwingConstants.CENTER);
      getJFrame().add(jlDetalle);
      
      JLabel jlFecha = getJLabel("Fecha:",15, 63, 55, 15);
      jlFecha.setHorizontalAlignment(SwingConstants.LEFT);
      jpFecha.add(jlFecha);


      JLabel jlTotal = getJLabel("Total :",650,473, 45, 15);
      jlTotal.setHorizontalAlignment(SwingConstants.LEFT);
      getJFrame().add(jlTotal);


      //** Se instancian los JSeparator

      JSeparator jSeparator1 = new JSeparator();
      jSeparator1.setBounds(670, 460, 115, 2);
      getJFrame().add(jSeparator1);


	   // se configura el modelo para la tabla
	   dm = new SortableTableModel() {
	
	        //Se especifica el serial para la serializacion
           static final long serialVersionUID = 19781212;
	
	        public Class getColumnClass(int col) {
	
	            return String.class;
	
	        }
	
	       public boolean isCellEditable(int row, int col) {
	        
	           filaSelecionada = row;
	      
	           boolean resultadoBoolean = true;
	           
	           if (col == 0 ||  col == 6 || col == 8 || col == 9)
                   
                   resultadoBoolean = false;
                   
               
               if (row > (obtenerFilas(tablaCompras,5)))  
               
                  resultadoBoolean = false;  
                      	           
	              
	           return resultadoBoolean;
	
	       }
	       
	       
	       
	       public void setValueAt(Object obj, int row, int col) {
	       	
	          super.setValueAt(obj, row, col); 
	          
	          if (col == 10  && tablaCompras.getValueAt(filaSelecionada,col) != null && !tablaCompras.getValueAt(filaSelecionada,col).toString().isEmpty()) {
	          
	          
	                  //Se calcula el total
			          int filas = obtenerFilas(tablaCompras,9);
			          
			          long suma = 0;
			          
			          for (int i = 0; i < filas; i++) {
			          
			          	suma += Long.parseLong(tablaCompras.getValueAt(i,10).toString());
			          	
				      }   	    
				      
				      jtTotal.setText(formatter.format(suma));
				      totalFactura = suma;
				      
			  }
			  	      
		      
	       
	       }
	       
	     
	     };
	     
	     
	     
	
	  dm.setDataVector(datosEstaticos,nombresColumnasEstaticas); //Se agrega las columnas y filas al JTable
	
	  MouseAdapter mouseAdapter = new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     
     		if (m.getClickCount() == 1) {
     		   
     		    int i = 0;
     		    
     		    boolean sw = true;
     		    
     		    while (i < 11 && sw) {
     		    	
     		    	if (tablaCompras.getValueAt(filaSelecionada,i) == null || tablaCompras.getValueAt(filaSelecionada,i).toString().isEmpty())
     		    	
     		    	  sw = false;
     		    	  
     		    	i++;  
     		    }
     		   
     		    if (sw)
     		    
     		       comodinFocusCellEditor = false;
     		}   
     	}
     	
      };
      
      
      //Se instancia el JTable
      tablaCompras = new JTable(dm); 
      tablaCompras.addMouseListener(mouseAdapter);
      
      tablaCompras.getSelectionModel().addListSelectionListener(this);
      tablaCompras.setColumnSelectionAllowed(true);
      tablaCompras.setFocusTraversalKeysEnabled(true);
      tablaCompras.setRowHeight(17);
      
       
      KeyAdapter keyAdapter = new KeyAdapter() { //Se define el codigo para un evento de tipo Key
      	
          	public void keyPressed(KeyEvent k) {
	      		
	      		
	      		if (k.getKeyCode() == 38 || k.getKeyCode() == 40) 
	      
	      		     k.consume();
	      		  
	      		 else {
	      		  	
	      		  	if (k.getKeyCode() == 37) {
	      		  		
			     	      if (tablaCompras.getEditingColumn() == 3) {
			     	          
			     	          comodinFocusCellEditor = false;
			     	          tablaCompras.editCellAt(filaSelecionada,2);
			     	          editorProductoJTable.getComponent().requestFocus();
			     	          ((JTextField)editorProductoJTable.getComponent()).selectAll();
			     	         
			     	          
			     	      } else
			     	     
			     	         if (tablaCompras.getEditingColumn() == 4) {
			     	              
			     	              comodinFocusCellEditor = false;
			     	              tablaCompras.editCellAt(filaSelecionada,3);
			     	              editorCantidadProductoJTable.getComponent().requestFocus();
			     	              ((JTextField)editorCantidadProductoJTable.getComponent()).selectAll();
			     	         
	      		  		     } else
			     	     
				     	         if (tablaCompras.getEditingColumn() == 5) {
				     	              
				     	              comodinFocusCellEditor = false;
				     	              tablaCompras.editCellAt(filaSelecionada,4);
				     	              editorPrecioCompraProductoJTable.getComponent().requestFocus();
				     	              ((JTextField)editorPrecioCompraProductoJTable.getComponent()).selectAll();
			     	        
				     	         
		      		  		     } else
			     	     
					     	         if (tablaCompras.getEditingColumn() == 6) {
					     	              
					     	              comodinFocusCellEditor = false;
					     	              tablaCompras.editCellAt(filaSelecionada,5);
					     	              editorPrecioListaProductoJTable.getComponent().requestFocus();
					     	              ((JTextField)editorPrecioListaProductoJTable.getComponent()).selectAll();
			     	        
					     	         
			      		  		     } else
			     	     
						     	         if (tablaCompras.getEditingColumn() == 7) {
						     	              
						     	              comodinFocusCellEditor = false;
						     	              tablaCompras.editCellAt(filaSelecionada,6);
						     	              editorPorcentajePrecioListaProductoJTable.getComponent().requestFocus();
						     	              ((JTextField)editorPorcentajePrecioListaProductoJTable.getComponent()).selectAll();
			     	        
						     	         
				      		  		     } else
			     	     
							     	         if (tablaCompras.getEditingColumn() == 8) {
							     	              
							     	              comodinFocusCellEditor = false;
							     	              tablaCompras.editCellAt(filaSelecionada,7);
							     	              editorPrecioMinimoProductoJTable.getComponent().requestFocus();
							     	              ((JTextField)editorPrecioMinimoProductoJTable.getComponent()).selectAll();
			     	        
							     	             }
	      		  		
	      		  	}
	      		  	
	      		     
	      		  } 
	      	}	  
	      	
	      	public void  keyTyped (KeyEvent k) {
	      		
		      	if (((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) ||  ((JTextField)k.getComponent()).getText().length() >= Integer.parseInt(k.getComponent().getName())))
			      
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
	                 
	               if (getJTableArrayListaValores()[1].getRowCount() > 0) {
	               
				     detenerEditar();
            	     tablaCompras.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,0).toString(),0,1);
            	     tablaCompras.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,1).toString(),0,2);
            	     tablaCompras.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,2).toString(),0,6);
            	     tablaCompras.setValueAt(getJTableArrayListaValores()[1].getValueAt(0,3).toString(),0,8);
            	     
				     tablaCompras.editCellAt(filaSelecionada,3);
		             editorCantidadProductoJTable.getComponent().requestFocus();
		             
					 getDialogoArrayListaValores()[1].setVisible(false);
					 
				   } else {
				   	  
				   	  detenerEditar(); 
				   	  tablaCompras.editCellAt(filaSelecionada,2);
		              editorProductoJTable.getComponent().requestFocus();
				   	
				   	
				   }	 
				  		
			     }
		       	
          } else
             
             if (k.getKeyCode() == 38 || k.getKeyCode() == 40) 
                 
                 k.consume();
             
             else
                
                if (k.getKeyCode() == 37) {
                	
	     	          comodinFocusCellEditor = false;
	     	          tablaCompras.editCellAt(filaSelecionada,1);
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
      jtCodigoProductoJTable.setName("20");
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
      jtCantidadProductoJTable.addMouseListener(mouseAdapter);
      jtCantidadProductoJTable.addFocusListener(this);
       
      jtPrecioCompraProductoJTable = new JTextField();
      jtPrecioCompraProductoJTable.setName("7");
      jtPrecioCompraProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioCompraProductoJTable.addKeyListener(keyAdapter);
      jtPrecioCompraProductoJTable.addMouseListener(mouseAdapter);
      jtPrecioCompraProductoJTable.addFocusListener(this);
      
      jtPrecioListaProductoJTable = new JTextField();
      jtPrecioListaProductoJTable.setName("7");
      jtPrecioListaProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioListaProductoJTable.addKeyListener(keyAdapter);
      jtPrecioListaProductoJTable.addMouseListener(mouseAdapter);
      jtPrecioListaProductoJTable.addFocusListener(this);
      
      
      jtPrecioPorcentajeListaProductoJTable = new JTextField();
      jtPrecioPorcentajeListaProductoJTable.setName("3");
      jtPrecioPorcentajeListaProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioPorcentajeListaProductoJTable.addKeyListener(keyAdapter);
      jtPrecioPorcentajeListaProductoJTable.addMouseListener(mouseAdapter);
      
      jtPrecioPorcentajeListaProductoJTable.addFocusListener(this);
      
      jtPrecioMinimoProductoJTable = new JTextField();
      jtPrecioMinimoProductoJTable.setName("7");
      jtPrecioMinimoProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioMinimoProductoJTable.addKeyListener(keyAdapter);
      jtPrecioMinimoProductoJTable.addFocusListener(this);
      
      jtPrecioPorcentajeMinimoProductoJTable = new JTextField();
      jtPrecioPorcentajeMinimoProductoJTable.setName("3");
      jtPrecioPorcentajeMinimoProductoJTable.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioPorcentajeMinimoProductoJTable.addKeyListener(keyAdapter);
      jtPrecioPorcentajeMinimoProductoJTable.addMouseListener(mouseAdapter);
      jtPrecioPorcentajeMinimoProductoJTable.addFocusListener(this);
      
      //Se declaran los DefaultCellEditor
      editorCodigoProductoJTable = getEditor(jtCodigoProductoJTable);
      editorProductoJTable = getEditor(jtProductoJTable);
      editorCantidadProductoJTable = getEditor(jtCantidadProductoJTable);
      editorPrecioCompraProductoJTable = getEditor(jtPrecioCompraProductoJTable);
      
      editorPrecioListaProductoJTable = getEditor(jtPrecioListaProductoJTable);
      editorPorcentajePrecioListaProductoJTable =  getEditor(jtPrecioPorcentajeListaProductoJTable);
      editorPrecioMinimoProductoJTable = getEditor(jtPrecioMinimoProductoJTable);
      editorPorcentajePrecioMinimoProductoJTable = getEditor(jtPrecioPorcentajeMinimoProductoJTable);
      
      
      tablaCompras.getColumnModel().getColumn(1).setCellEditor(editorCodigoProductoJTable);
      tablaCompras.getColumnModel().getColumn(2).setCellEditor(editorProductoJTable);
      tablaCompras.getColumnModel().getColumn(3).setCellEditor(editorCantidadProductoJTable);
      tablaCompras.getColumnModel().getColumn(4).setCellEditor(editorPrecioCompraProductoJTable);     
      tablaCompras.getColumnModel().getColumn(5).setCellEditor(editorPrecioListaProductoJTable);
      tablaCompras.getColumnModel().getColumn(6).setCellEditor(editorPorcentajePrecioListaProductoJTable);
      tablaCompras.getColumnModel().getColumn(7).setCellEditor(editorPrecioMinimoProductoJTable);
      tablaCompras.getColumnModel().getColumn(8).setCellEditor(editorPorcentajePrecioMinimoProductoJTable);
      tablaCompras.getColumnModel().getColumn(11).setCellRenderer(buttonRenderer);
      tablaCompras.getColumnModel().getColumn(11).setCellEditor(buttonEditor);
      
      
   
   
	  configurarColumnas(); //Se configuran las columnas
	 	
	  //Se crea un JScrollPane
      scroll = new JScrollPane(tablaCompras);
      scroll.setBounds(10, 220, 775, 223);
      getJFrame().add(scroll);
      
      JSeparator jSeparator2 = new JSeparator();
      jSeparator2.setBounds(17,205,760,2);
      getJFrame().add(jSeparator2);
      
      
      //** Se agregan los JTextField

      jtNumeroCompra = getJTextField("Digíte el Precio de Compra o Adquicición del Producto",130, 5, 35, 20,"",false);
      jtNumeroCompra.setText(buscarNumeroCompra());
      jtNumeroCompra.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroCompra.addFocusListener(this);
      jpFecha.add(jtNumeroCompra);

      jtFechaFactura = getJFormattedTextField(getObtenerFecha(conectarMySQL),70, 60, 70, 20,"Digíte la Fecha de la Factura","7");
      jtFechaFactura.addFocusListener(this);
      jpFecha.add(jtFechaFactura);

      jtNit = getJTextField("",20, 45, 105, 20,"Digíte el Nit o Número de Identificación de la Empresa","20");
      jtNit.addFocusListener(this);
      jtNit.addKeyListener(new KeyAdapter() {
	      	
	      	     public void  keyTyped (KeyEvent k) {
	
		             if ((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) && k.getKeyChar() != '.' &&  k.getKeyChar() != '-')
		         
		                  k.consume(); // Se consume o borra el caracter digitado
		         }           
	   });
      jpClientes.add(jtNit);
      
      
      jtTelefono = getJTextField("Digite el Número de Teléfono Fijo de la Empresa",335, 45, 95, 20,"",false);
      jtTelefono.setHorizontalAlignment(JTextField.CENTER);
      jtTelefono.addFocusListener(this);
      jtTelefono.addKeyListener(getValidarEntradaNumeroJTextField());
      jpClientes.add(jtTelefono);
     
     
      jtNumeroFactura = getJTextField("",95, 33, 70, 20,"Digíte el Número de Factura","8");
      jtNumeroFactura.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroFactura.addKeyListener(getValidarEntradaNumeroJTextField());
      jtNumeroFactura.addFocusListener(this);
      jpFecha.add(jtNumeroFactura);
      
      jtNumeroProveedor = new JTextField(); //Se instancia el JTextFiel donde se devolvera el nunero del proveedor
       
       
      jtNombre = getJTextField("",130, 45, 200, 20,"Digite el Nombre de la Empresa","40");
      jtNombre.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValorProveedores = getListaValores(getSentencia(),getComponentesRetorno(),this,jtNombre,conectarMySQL,0,2,getOcultarColumnas());

      jtNombre.addKeyListener(listaValorProveedores);
      jtNombre.addFocusListener(this);
      jtNombre.addActionListener(this);
      jpClientes.add(jtNombre);

     
      jtTotal = getJTextField("Valor Total de la Factura",695, 470, 75, 20,"",false);
      jtTotal.setHorizontalAlignment(JTextField.RIGHT);
      jtTotal.addFocusListener(this);
      getJFrame().add(jtTotal);
      
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
      scrollObservaciones.setBounds(20, 25, 580, 40);
      jpObservaciones.add(scrollObservaciones);
      
       

      //** Se agregan los JButton

      jbCalendario = getJButton("",145, 60, 20, 20,"Imagenes/Calendario.gif","Calendario");
      jbCalendario.addActionListener(this);
      jbCalendario.setFocusable(false);
      jpFecha.add(jbCalendario);

      jbInformacionProveedor = getJButton("jButton1",435, 45, 20, 20,"","");
      jbInformacionProveedor.addActionListener(this);
      jbInformacionProveedor.setFocusable(false);
      jpClientes.add(jbInformacionProveedor);

      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/ComprasRegistrar.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 

      jtNit.grabFocus();    //Se coloca el foco en el primer componente Focusable

      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
      //Se configura es setName de los Botones como comodines para sobreescribir el focusLost
      Blimpiar.setName("JButton");
      Bguardar.setName("JButton");
      Beliminar.setName("JButton");
      Bbuscar.setName("JButton");
      Bsalir.setName("JButton");
      Bimprimir.setName("JButton");
      

	  //Se crea el array de campos obligatorios 
	  crearArrayCamposObligatorios();
	  
	  formatter = new DecimalFormat("#,###,###");
	  
	  getJFrame().addComponentListener(new ComponentAdapter() {
  	   
  	    	public void componentShown(ComponentEvent e) {
  	
  	           if (!jtNit.getText().isEmpty()) {
  	               
  	               traerInformacion();
  	               jtNumeroFactura.grabFocus();
  	               
  	           }    
  	             
  	              		
  	    	}	
  	  });
	

    }

     //******************************** Metodo crearArrayCamposObligatorios *******************

     public void crearArrayCamposObligatorios() { 

           //Se instancia un array de JTextField 
           camposObligatorios = new JTextField[5];

           camposObligatorios[0] = jtNumeroCompra;
           camposObligatorios[1] = jtFechaFactura;
           camposObligatorios[2] = jtNit;
           camposObligatorios[3] = jtNumeroFactura;
           camposObligatorios[4] = jtTotal;

     }
     
     


    //*********************** Metodo limpiar ************************

    private void limpiar() { 

      jtNumeroCompra.setText(buscarNumeroCompra());
      jtFechaFactura.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
      jtNit.setText("");
      jtNombre.setText("");
      jtTelefono.setText("");
      jtNumeroFactura.setText("");
      jtTotal.setText("");

      totalFactura = 0L;
      
      detenerEditar();
      
      //Se limpia la tabla
      dm.setDataVector(datosEstaticos,nombresColumnasEstaticas); //Se agrega las columnas y filas al JTable
	
      
      tablaCompras.setModel(dm);
      
      tablaCompras.getColumnModel().getColumn(1).setCellEditor(editorCodigoProductoJTable);
      tablaCompras.getColumnModel().getColumn(2).setCellEditor(editorProductoJTable);
      tablaCompras.getColumnModel().getColumn(3).setCellEditor(editorCantidadProductoJTable);
      tablaCompras.getColumnModel().getColumn(4).setCellEditor(editorPrecioCompraProductoJTable);
      tablaCompras.getColumnModel().getColumn(5).setCellEditor(editorPrecioListaProductoJTable);
      tablaCompras.getColumnModel().getColumn(6).setCellEditor(editorPorcentajePrecioListaProductoJTable);
      tablaCompras.getColumnModel().getColumn(7).setCellEditor(editorPrecioMinimoProductoJTable);
      tablaCompras.getColumnModel().getColumn(8).setCellEditor(editorPorcentajePrecioMinimoProductoJTable);
      tablaCompras.getColumnModel().getColumn(11).setCellRenderer(buttonRenderer);
      tablaCompras.getColumnModel().getColumn(11).setCellEditor(buttonEditor);  
     
   
	  configurarColumnas(); //Se configuran las columnas
	 
      
      jtNit.grabFocus();    //Se coloca el foco en el primer componente Focusable

    }

   //*********************** Metodo buscarRegistro() ************************

    private void buscarRegistro() { 

        final String sentenciaSQL = "Select * "+
                                    "From   Compra ";

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 

             if (resultado.next()) { 


				 jtNumeroCompra.setText(resultado.getString(1));
				 jtFechaFactura.setText(resultado.getString(2));
				 jtNit.setText(resultado.getString(3));
				 jtNombre.setText(resultado.getString(4));
				 jtTelefono.setText(resultado.getString(5));
				 jtNumeroFactura.setText(resultado.getString(6));
				 jtTotal.setText(resultado.getString(7));

             }
           }

        } catch (Exception e) {
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    //*****************************Metodo buscarNumeroCompra() **************************************
    
      private String buscarNumeroCompra() { 

        final String  sentenciaSQL = "Select ifNull(max(numerocompra),0) "+
                                     "From  ComprasEncabezado ";
                                    
                                    
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
    
    //*****************************Metodo buscarNumeroFactura() **************************************
    
      private boolean buscarNumeroFactura() { 

        final String  sentenciaSQL = "Select 'X' "+
                                     "From  ComprasEncabezado "+
                                     "Where NumeroFactura = " + jtNumeroFactura.getText(); 
                                  
                                    
        boolean resultadoBoolean = false;                          

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	 

             if (resultado.next()) 
 
                resultadoBoolean = true;
				

            

        } catch (Exception e) {
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return  resultadoBoolean;
    }
    
    
    
    //*****************************Metodo buscarRegistro() ******************************************
    
    private void buscarRegistroProveedores() { 

        String sentenciaSQL = "Select NumeroProveedor,Nit,RazonSocial,Direccion,Telefono,Fax,PaginaWeb,Email,DivisionPolitica "+
                              "From   Proveedores "+
                              "Where Nit ='" + jtNit.getText() + "' or RazonSocial = '" + jtNombre.getText() + "'" ;
       
       
      
        
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 

             if (resultado.next()) { 

                 jtNumeroProveedor.setText(resultado.getString(1));
                 jtNitDialog.setText(resultado.getString(2));
                 jtNombreDialog.setText(resultado.getString(3));
				 jtDireccion.setText(resultado.getString(4));
				 jtTelefonoDialog.setText(resultado.getString(5));
				 jtFax.setText(resultado.getString(6));
				 jtPaginaWeb.setText(resultado.getString(7));
				 jtEmail.setText(resultado.getString(8));
				 String codigoDivision = resultado.getString(9);
		
	                 
	             Vector municipios =   buscarMunicipio(conectarMySQL,codigoDivision); //Se busca el nombre del departamento y municipio o corregimiento
	             jcDepartamento.removeAllItems();
	 	   	     jcDepartamento.addItem(municipios.elementAt(0));
	 	   	     jtCiudadMunicipio.setText(municipios.elementAt(1).toString());
	 	   	     
	 	   	     
	 	   	     //Se traen los contactos
	 	   	     sentenciaSQL = "Select Nombre,Apellido,Telefono,Celular,Cargos,NumeroContacto "+
                                "From  Contactos " +
                                "Where NumeroProveedor = " + jtNumeroProveedor.getText();
	 	   	     
	 	   	     resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	   	     
	 	   	     
	 	   	     Vector datos = new Vector();
	 	   	     
	 	   	     while (resultado.next()) {
	 	   	     	
	 	   	     	Vector columnas = new Vector();
	 	   	     	
	 	   	     	for (int i = 1; i <= 6; i++)
	 	   	     	
	 	   	     	    columnas.add(resultado.getString(i));
	 	   	     	
	 	   	     	
	 	   	     	datos.add(columnas);
	 	   	     		
	 	   	     }
	 	   	     
	 	   	     
	 	   	     Vector nombresColumnas = new Vector();
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	    
	 	   	     
	 	   	     dm.setDataVector(datos,nombresColumnas);
	 	   	}   
          } 

        } catch (Exception e) {
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }
   

    //*********************** Metodo buscarRegistroProducto ************************

     private boolean buscarRegistroProducto(int filaSeleccionada) {
 	         
 	         boolean resultadoBoolean = false;
 	         
 	         final String sentenciaSQL = "Select Descripcion "+
 	                                     "From Productos "+
 	                                     "Where IdProducto = '" + tablaCompras.getValueAt(filaSeleccionada,1)  +"'";
 	                                     
 	         
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     if (resultado.next()) {
                      
                       tablaCompras.setValueAt(resultado.getString(1),filaSeleccionada,2);
                    
                       if (parametrosAzcaban.get("APLICARHISTORICO").toString().equals("false")) {
                       	
                       	  tablaCompras.setValueAt(parametrosAzcaban.get("PORCSTOCKLISTA"),filaSeleccionada,6);
                          tablaCompras.setValueAt(parametrosAzcaban.get("PORCSTOCKMINIMO"),filaSeleccionada,8);
                       
                       } else {
                       	 
                       	 
                       	  String[] porcentajes = buscarHistoricoPorcentajeProducto(tablaCompras.getValueAt(filaSeleccionada,1).toString());
                       	  
                       	  if (porcentajes != null) {
                       	  
	                       	  tablaCompras.setValueAt(porcentajes[0],filaSeleccionada,6);
	                          tablaCompras.setValueAt(porcentajes[1],filaSeleccionada,8);
	                     
	                      } else {
	                      	
	                      	 tablaCompras.setValueAt(parametrosAzcaban.get("PORCSTOCKLISTA"),filaSeleccionada,6);
                             tablaCompras.setValueAt(parametrosAzcaban.get("PORCSTOCKMINIMO"),filaSeleccionada,8);
                 
	                      }
	                       	
                       } 	         
                     
                       resultadoBoolean = true;
                       
                     }
                     
                     
	 	     }  catch (Exception e) {
	 	                          
	 	          Mensaje("Error en la busqueda " +e,"Reporte de Error", 0);        
	 	     
	 	     }
	 	                 
	         return resultadoBoolean;
     
 	  }

   //******************************** Metodo traerInformacion()  ***************************************

    private boolean traerInformacion() {
 	         
 	         boolean resultadoBoolean = false;
 	         
 	         final String sentenciaSQL = "Select RazonSocial,Telefono,numeroProveedor "+
 	                                     "From Proveedores "+
 	                                     "Where Nit = '" + jtNit.getText() +"'";
 	                                     
 	         
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     if (resultado.next()) {
                      
                      
                        jtNombre.setText(resultado.getString(1));
                        jtTelefono.setText(resultado.getString(2));
                        jtNumeroProveedor.setText(resultado.getString(3));
                        resultadoBoolean = true;
                     
                     }
                     
                     
	 	     }  catch (Exception e) {
	 	                          
	 	          Mensaje("No existe ningun registro en la tabla Proveedores","Reporte de Error", 0);        
	 	     
	 	     }
	 	                 
	         return resultadoBoolean;
     
 	  }

    
      //**************************** Metodo configurarColumnas() ************************
 
      private void configurarColumnas() {
 	   
 	 
 	    // Se configuran los tamaños de las columnas
 		tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(27);
 		tablaCompras.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
 		tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(136);
 		tablaCompras.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
 		tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(197);
 		tablaCompras.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
 		tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(61);
 		tablaCompras.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
 		tablaCompras.getColumnModel().getColumn(4).setPreferredWidth(77);
 		tablaCompras.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
 		tablaCompras.getColumnModel().getColumn(5).setPreferredWidth(78);
 		tablaCompras.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
 		tablaCompras.getColumnModel().getColumn(6).setPreferredWidth(27);
 		tablaCompras.getColumnModel().getColumn(6).setCellRenderer(getAlinearDerecha());
 		tablaCompras.getColumnModel().getColumn(7).setPreferredWidth(71);
 		tablaCompras.getColumnModel().getColumn(7).setCellRenderer(getAlinearDerecha());
 		tablaCompras.getColumnModel().getColumn(8).setPreferredWidth(27);
 		tablaCompras.getColumnModel().getColumn(8).setCellRenderer(getAlinearDerecha());
 		tablaCompras.getColumnModel().getColumn(9).setPreferredWidth(75);
 		tablaCompras.getColumnModel().getColumn(9).setCellRenderer(getAlinearDerecha());
 		tablaCompras.getColumnModel().getColumn(11).setPreferredWidth(15);
 	
 		
 	   ocultarColumnas(tablaCompras,10);
 		
 	   
 	 }   
    
    
    private void configurarColumnasContactos() {
 	   
 	    // Se configuran los tamaños de las columnas
 		tablaContactos.getColumnModel().getColumn(0).setPreferredWidth(115);
 		tablaContactos.getColumnModel().getColumn(1).setPreferredWidth(115);
 		tablaContactos.getColumnModel().getColumn(2).setPreferredWidth(30);
 	    tablaContactos.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
 		tablaContactos.getColumnModel().getColumn(3).setPreferredWidth(30);
 		tablaContactos.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
 		tablaContactos.getColumnModel().getColumn(4).setPreferredWidth(70);
 	
 		
 		
 	   
 	 }   
     
    
     //********************* configurarJDialogProveedores()****************************************
     
    private void configurarJDialogProveedores() {
    	
    	
    	if (dialogProveedor == null) {
    		
    		  dialogProveedor = new JDialog(getJFrame(),"Contactos",true);
		  	  dialogProveedor.setLayout(null);
		  	  dialogProveedor.setSize(695, 427);
		  	  dialogProveedor.setLocationRelativeTo(null);
		  	  
		  	  //** Se agregan los JPanel

		      JPanel panelIdentificacion = getJPanel("Identificación",165, 5, 355, 60,14);
		
		      JPanel panelApellidosNombres = getJPanel("Datos Generales",5, 65, 670, 130,14);
		
		      JPanel panelDatosContacto = getJPanel("Contactos",5, 195, 670, 160,14);
		
		      //** Se declaran los JLabel
		
		      JLabel jlNit = getJLabel("Nit",25, 15, 75, 15);
		      panelIdentificacion.add(jlNit);
		
		      JLabel jlNombre = getJLabel("Nombre",135, 15, 65, 15);
		      panelIdentificacion.add(jlNombre);
		
		      JLabel jlDireccion = getJLabel("Dirección:",0, 28, 120, 15);
		      jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelApellidosNombres.add(jlDireccion);
		
		      JLabel jlTelefono = getJLabel("Teléfono:",375, 53, 65, 15);
		      jlTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelApellidosNombres.add(jlTelefono);
		
		      JLabel jlPaginaweb = getJLabel("Pág. Web:",55, 103, 65, 15);
		      jlPaginaweb.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelApellidosNombres.add(jlPaginaweb);
		
		      JLabel jlEmail = getJLabel("e-Mail:",375, 103, 65, 15);
		      jlEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelApellidosNombres.add(jlEmail);
		
		      JLabel jlCiudadMunicipio = getJLabel("Ciudad / Municipio:",0, 78, 120, 15);
		      jlCiudadMunicipio.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelApellidosNombres.add(jlCiudadMunicipio);
		
		      JLabel jlDepartamento = getJLabel("Departamento:",0, 53, 120, 15);
		      jlDepartamento.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelApellidosNombres.add(jlDepartamento);
		
		      JLabel jlFax = getJLabel("Fax:",375, 78, 65, 15);
		      jlFax.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelApellidosNombres.add(jlFax);
		
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
		      
		      //Columnas y filas estaticas 
             Object [] nombresColumnas = {"Nombre","Apellido","Teléfono","Celular","Cargo"};
             Object [][] datos = new Object[5][5];
		
		     dm.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla
		
		     tablaContactos = getJTable(dm,true); //Se instancia el JTable con el modelo de datos
		
		     //** Se configura un scroll para el JTable 
		
		      JScrollPane scrollPane = new JScrollPane(tablaContactos);
		      scrollPane.setBounds(25, 35, 625, 100);
		      panelDatosContacto.add(scrollPane);
		      
		      configurarColumnasContactos();
		
		      //** Se agregan los JTextField
		
		      jtNitDialog = getJTextField("Digíte el Nit o Número de Identificación de la Empresa",25, 30, 105, 20,"",false);
		      jtNitDialog.setHorizontalAlignment(JTextField.RIGHT);
		      jtNitDialog.addFocusListener(this);
		      panelIdentificacion.add(jtNitDialog);
		
		      jtDireccion = getJTextField("Digite la Dirección de la Empresa",130, 25, 415, 20,"",false);
		      jtDireccion.addFocusListener(this);
		      panelApellidosNombres.add(jtDireccion);
		
		      jtNombreDialog = getJTextField("Digite el Nombre de la Empresa",135, 30, 200, 20,"",false);
		      jtNombreDialog.addFocusListener(this);
		      panelIdentificacion.add(jtNombreDialog);
		
		      jtTelefonoDialog = getJTextField("Digite el Número de Teléfono Fijo de la Empresa",450, 50, 95, 20,"",false);
		      jtTelefonoDialog.setHorizontalAlignment(JTextField.RIGHT);
		      jtTelefonoDialog.addFocusListener(this);
		      panelApellidosNombres.add(jtTelefonoDialog);
		
		      jtPaginaWeb = getJTextField("Digite la dirección de la Página Web de la Empresa",130, 100, 215, 20,"",false);
		      jtPaginaWeb.setHorizontalAlignment(JTextField.RIGHT);
		      jtPaginaWeb.addFocusListener(this);
		      panelApellidosNombres.add(jtPaginaWeb);
		
		      jtEmail = getJTextField("Digíte el Correo Electrónico de la Empresa",450, 100, 200, 20,"",false);
		      jtEmail.addFocusListener(this);
		      panelApellidosNombres.add(jtEmail);
		
		      jtCiudadMunicipio = getJTextField("Digite la Ciudad / Municipio o Presione F9",130, 75, 215, 20,"",false);
		      jtCiudadMunicipio.addFocusListener(this);
		      panelApellidosNombres.add(jtCiudadMunicipio);
		
		      jtFax = getJTextField("Digite el Número del Fax de la Empresa",450, 75, 95, 20,"",false);
		      jtFax.setHorizontalAlignment(JTextField.RIGHT);
		      jtFax.addFocusListener(this);
		      panelApellidosNombres.add(jtFax);
		
		      //** Se agregan los JButton
		
		      jbCerrar = getJButton("Cerrar",280, 360, 125, 25,"","");
		      jbCerrar.addActionListener(this);
		
		      //** Se agregan los JComboBox
		
		      jcDepartamento = getJComboBox(130, 50, 215, 20,"Seleccione el Departamento");
		      jcDepartamento.setEnabled(false);
		      panelApellidosNombres.add(jcDepartamento);
		      
		      
		      dialogProveedor.add(panelIdentificacion);
		      dialogProveedor.add(panelApellidosNombres);
		      dialogProveedor.add(panelDatosContacto);
		      dialogProveedor.add(jbCerrar);
		      
				  	  
				  	  
		}
		
		buscarRegistroProveedores();
		
		dialogProveedor.setVisible(true);
		
	
	}	  	  
    
    //******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia()  {

		String sentencia = "Select Nit,RazonSocial 'Razon Social o Nombre',Telefono, NumeroProveedor "+
		                   "From Proveedores "+
		                   "Where RazonSocial like '";

		return sentencia;

	}
	
	//******************************** Metodo getOcultarColumnas()  ***************************************

	final private int[] getOcultarColumnas() {

		int[] columnasOcultar = new int[2];
        
        columnasOcultar[0] = 2;
       	columnasOcultar[1] = 3;
       	
		return columnasOcultar;

	}
	
	//******************************** Metodo getSentencia1()  ***************************************

	final private String getSentencia1() {
         
          String porcentajeLista = "";
          String porcentajeMinimo = "";
          
           if (parametrosAzcaban.get("APLICARHISTORICO").equals("false"))  {
           	  
           	      porcentajeLista  =   parametrosAzcaban.get("PORCSTOCKLISTA").toString();
                  porcentajeMinimo =   parametrosAzcaban.get("PORCSTOCKMINIMO").toString();
           	   
           	
           } else {
           	  
           	    try {
           	     
	           	     String[] porcentajes = buscarHistoricoPorcentajeProducto(tablaCompras.getValueAt(filaSelecionada,1).toString());
	           	     
	           	     if (porcentajes != null) {
	               	  
	                   	  porcentajeLista  =  porcentajes[0];
	                      porcentajeMinimo = porcentajes[1];
	                 
	                  } else {
	                  	
	                  	  porcentajeLista  =   parametrosAzcaban.get("PORCSTOCKLISTA").toString();
	                      porcentajeMinimo =   parametrosAzcaban.get("PORCSTOCKMINIMO").toString();
	           	   
	         
	                  }
             		   	     
	   	         }  catch (Exception e) {
	 	                          
	 	              Mensaje("Error en la busqueda " +e,"Reporte de Error", 0);        
	 	     
	 	         }
	 	            
           	
           }
          
           
                       	  
                       	  
	                       	
          		
		 

		   String sentencia  = "Select IdProducto Codigo,Descripcion,'" + porcentajeLista + "','"+ porcentajeMinimo + "'"+
		                       "From Productos P "+
		                       "Where P.Descripcion like '"+ jtProductoJTable.getText() +"%'";
	                   
          
		   return sentencia;

	}

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[3][5];
		
		objetosRetorno[0][0] = jtNombre;
        objetosRetorno[0][1] =  jtNumeroFactura; 
       	objetosRetorno[0][2] = "1";
       	objetosRetorno[0][3] = jtNit;
       	objetosRetorno[0][4] = "0";


        objetosRetorno[1][3] = jtTelefono;
       	objetosRetorno[1][4] = "2";
        
        objetosRetorno[2][3] = jtNumeroProveedor;
       	objetosRetorno[2][4] = "3";
       	
		return objetosRetorno;

	}
	
	
	//******************************** Metodo validarFilas()  ***************************************

	final boolean validarFilas() {
		
		boolean resultado = true;
		boolean sw = true;
		
		int i = 0;
		int numeroFilas = tablaCompras.getSelectedRow();
		
		
		while (i < numeroFilas &&  sw) {
			
			int j = 1;
			
			while (j < 9 && sw) {
				
				if (tablaCompras.getValueAt(i,j) == null || tablaCompras.getValueAt(i,j).toString().isEmpty()) {
				
				  sw = false;
				  
				  
				}  
				
				j++;
				
			}
			
			i++;
			
		}
		
		return sw;		
		
	}
	
	
	//******************************** Metodo getComponentesRetorno1()  ***************************************

	final private Object[][] getComponentesRetorno1() {

		Object[][] objetosRetorno = new Object[3][7];
		
		objetosRetorno[0][0] = tablaCompras;
		objetosRetorno[0][1] = "2";
        objetosRetorno[0][2] = "3";
       	objetosRetorno[0][3] = "1";
      
       	objetosRetorno[0][4] = "0";
       	objetosRetorno[0][5] = "1";
       	
       	objetosRetorno[1][4] = "2";
        objetosRetorno[1][5] = "6";
    
        objetosRetorno[2][4] = "3";
        objetosRetorno[2][5] = "8";
    
    
		return objetosRetorno;

	}

	

    //*********************** Metodo guardar ************************

    private void guardar() throws Exception { 


        String[] datos = new String[7];

        datos[0] =  jtNumeroCompra.getText();
        datos[1] =  jtNumeroProveedor.getText() ;
        datos[2] =  jtNumeroFactura.getText();
        datos[3] = "'" + jtFechaFactura.getText() + " " + getObtenerFechaCompletaServidor(conectarMySQL).substring(11) + "'";
        datos[4] =  String.valueOf(totalFactura);
        datos[5] = "'G'"; 
        datos[6] = "null";       

        //Se inserta en la base de datos
        guardar("ComprasEncabezado",datos,conectarMySQL,false); 
        
        
        //Se procede a guardar los detalles
        int numFilas = obtenerFilas(tablaCompras,9);
     
        String[][] datosDetalles = new String[numFilas][6];
        
        
        for (int i = 0; i < numFilas; i++) {
       
        	datosDetalles[i][0] =  jtNumeroCompra.getText();
        	datosDetalles[i][1] = tablaCompras.getValueAt(i,0).toString();
        	datosDetalles[i][2] = "'" + tablaCompras.getValueAt(i,1) + "'";
        	datosDetalles[i][3] =  tablaCompras.getValueAt(i,3).toString();
        	datosDetalles[i][4] =  tablaCompras.getValueAt(i,4).toString() ;
        	datosDetalles[i][5] =  tablaCompras.getValueAt(i,10).toString();
        	
        	
        	
        }
        
        guardar("ComprasDetalle",datosDetalles,conectarMySQL,6,false);
        
        
    
        totalCaja -= totalFactura; //Se incrementa la caja
              
        //Se almacena en caja
        datos = new String[7];
        datos[0] = "null";
        datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
        datos[2] = "'" + nombreUsuario + "'";
        datos[3] = "5";
        datos[4] = "" + totalFactura;
        datos[5] = "" + totalCaja;
        datos[6] = "'COMPRA'";
	        
	       
        
        
        
        guardar("MovimientosCaja",datos,conectarMySQL,false); 
       

     }

 

      //**************************** Metodo actualizar ************************

      private void actualizar() throws Exception { 
      
          String condicion = "";
          String datos[];
          
          //Se procede a guardar los detalles
          int numFilas = obtenerFilas(tablaCompras,9);
        
          for (int i = 0; i < numFilas; i++) {
          	
          	 condicion = "idProducto = '" + tablaCompras.getValueAt(i,1) +"'";
          	 
          	 datos = new String[3];
          	 
          	 datos[0] = "PrecioMininoVenta = " + tablaCompras.getValueAt(i,7);
          	 datos[1] = "PrecioListaVenta = " +  tablaCompras.getValueAt(i,5);
          	 datos[2] = "Stock = Stock + " +  tablaCompras.getValueAt(i,3);
          	 
          	 actualizar("Productos",datos,condicion,conectarMySQL,false);
          	 
          	 //Se guarda en la tabla Precios si el ultimo precio es diferente al que
          	 // que se desea ingresar
          	 
          	 String[] resultadoPrecios = buscarPrecioProducto(tablaCompras.getValueAt(i,1).toString());
          	 
          	 if (resultadoPrecios == null || !tablaCompras.getValueAt(i,4).equals(resultadoPrecios[0])) {
          	 	
	          	 	datos = new String[4];
	          	 	
	          	    datos[0] = "'" + tablaCompras.getValueAt(i,1) + "'";
	          	    datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
	          	    datos[2] = "null";
	          	    datos[3] =   tablaCompras.getValueAt(i,4).toString();
	          	    
	          	     
			        //Se inserta en la base de datos
			        guardar("Precios",datos,conectarMySQL,false); 
			        
			        if (resultadoPrecios != null) {
			        
				        //Se actualiza la fecha hasta del producto del precio que compro
				        condicion = "idProducto = '" + tablaCompras.getValueAt(i,1) +"' and FechaDesde = '" + resultadoPrecios[1] + "'";
				        
	          	        datos = new String[] { "FechaHasta = '" +  getObtenerFechaCompletaServidor(conectarMySQL) +"'"};
	          	        	
	          	        actualizar("Precios",datos,condicion,conectarMySQL,false);
	               }
			        
 
          	 	
          	 }
          	 
          	 
          	 
          	 //Se guardan en el historico las configuraciones de los precios de ganancia
          	  String[] resultadoPorcentajePrecios = buscarHistoricoPorcentajeProducto(tablaCompras.getValueAt(i,1).toString());
          	  
	          	   
	          if (resultadoPorcentajePrecios == null || !tablaCompras.getValueAt(i,6).equals(resultadoPorcentajePrecios[0]) || !tablaCompras.getValueAt(i,8).equals(resultadoPorcentajePrecios[1])) {
	          	 	
		          	 	datos = new String[5];
		          	 	
		          	    datos[0] = "'" + tablaCompras.getValueAt(i,1) + "'";
		          	    datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
		          	    datos[2] = "null";
		          	    datos[3] =   tablaCompras.getValueAt(i,6).toString();
		          	    datos[4] =   tablaCompras.getValueAt(i,8).toString();
		          	    
		          	     
				        //Se inserta en la base de datos
				        guardar("HistoricoPorcentajesGanancia",datos,conectarMySQL,false); 
				        
				        if (resultadoPorcentajePrecios != null) {
				        
					        //Se actualiza la fecha hasta del producto del precio que compro
					        condicion = "idProducto = '" + tablaCompras.getValueAt(i,1) +"' and FechaDesde = '" + resultadoPorcentajePrecios[2] + "'";
					        
		          	        datos = new String[] { "FechaHasta = '" +  getObtenerFechaCompletaServidor(conectarMySQL) +"'"};
		          	        	
		          	        actualizar("HistoricoPorcentajesGanancia",datos,condicion,conectarMySQL,false);
		               }
				        
	 
	          	 	
	          } 
	          
	          
	          //Se almacena en la tabla lotes
	          datos = new String[10];
	          datos[0] =  "null";
	          datos[1] =  "'" + tablaCompras.getValueAt(i,1) + "'";
	  	      datos[2] =  jtNumeroCompra.getText();
	  	      datos[3] =  tablaCompras.getValueAt(i,3).toString();
	  	      datos[4] =  tablaCompras.getValueAt(i,3).toString();
	  	      datos[5] =  "0";
	  	      datos[6] =  "0";
	  	      datos[7] =  "0";
	  	      datos[8] =  "'" + jtFechaFactura.getText() + " " + getObtenerFechaCompletaServidor(conectarMySQL).substring(11) + "'";
	  	      datos[9] =  "null";
	  	      
	  	      //Se inserta en la base de datos
			 guardar("LotesPorProducto",datos,conectarMySQL,false); 
				        
	  	   
          	 
          } 
          
          
          

      }
      
      //**************************** Metodo buscarPrecioProducto ************************

      final private String[] buscarPrecioProducto(String idProducto) throws Exception  { 
      
          String[] resultadoPrecio = null;
          
          final String sentenciaSQL = "Select PrecioCompra,FechaDesde "+
                                      "From   Precios "+
                                      "Where IdProducto = '" + idProducto +"' and "+
                                      "      FechaDesde = (Select Max(FechaDesde) "+
                                      "                    From  Precios "+
                                      "                    Where IdProducto = '" + idProducto +"')";
                                      
                                      
                                      

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	

             if (resultado.next()) {
             
                  resultadoPrecio = new String[2];
                  resultadoPrecio[0] = resultado.getString(1);
                  resultadoPrecio[1] = resultado.getString(2);
                
             }   
           

        } catch (Exception e) {
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
         
         
        return  resultadoPrecio;
      
      }
      
      
        //**************************** Metodo buscarHistoricoPorcentajeProducto ************************

      final private String[] buscarHistoricoPorcentajeProducto(String idProducto) throws Exception  { 
      
          String[] resultadoPrecio = null;
          
          final String sentenciaSQL = "Select PorcentajeGanaciaLista,PorcentajeGanaciaMinimo,FechaDesde "+
                                      "From   HistoricoPorcentajesGanancia "+
                                      "Where IdProducto = '" + idProducto +"' and "+
                                      "      FechaDesde = (Select Max(FechaDesde) "+
                                      "                    From  HistoricoPorcentajesGanancia "+
                                      "                    Where IdProducto = '" + idProducto +"')";
                                      
                                      
                                      

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	

             if (resultado.next()) {
             
                  resultadoPrecio = new String[3];
                  resultadoPrecio[0] = resultado.getString(1);
                  resultadoPrecio[1] = resultado.getString(2);
                  resultadoPrecio[2] = resultado.getString(3);
                
             }   
           

        } catch (Exception e) {
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        	
        }
         
         
        return  resultadoPrecio;
      
      }


      //**************************** Metodo eliminar ************************

      private void eliminar() throws Exception  { 

	
	  }
      
 	
 	
 	//******************************** detenerEditar ********************************
 	
 	private void detenerEditar() {
 		
 	   if (filaSelecionada > - 1 && tablaCompras.getEditingColumn() > -1)	
 		
 		 tablaCompras.getCellEditor(filaSelecionada,tablaCompras.getEditingColumn()).stopCellEditing();
 	
 	}
 	
 	
	//***********************************Metodo validarDatosJTable() ******************************
	final private boolean  validarDatosJTable() {
		
		int numFilas = obtenerFilas(tablaCompras,10);
	
		boolean sw = true;
		
		int i = 0;
		
		String valorValidar = tablaCompras.getValueAt(filaSelecionada,1).toString();
		
		if (numFilas >= 1)

			while ( i < numFilas && sw ) {
			
			    if (tablaCompras.getValueAt(i,1) != null && tablaCompras.getValueAt(i,1).equals(valorValidar) && i != filaSelecionada)
			       
			        sw = false;
			    
			    
			    i++;
			        
			}
			
		return sw;
		
	}
 	
 	//**************************** Metodo mostrarListaValores ************************

	  public void mostrarListaValores() {
		
		 int posicionY = 189 + 17 * filaSelecionada ;
		
         listarValores(getSentencia1(),conectarMySQL,getComponentesRetorno1(),108,posicionY,editorProductoJTable,editorCantidadProductoJTable,1,2,filaSelecionada,getOcultarColumnas());
        
         if (getJTableArrayListaValores()[1].getColumnModel().getColumn(0).getPreferredWidth() != 110) {
			     
	        getJTableArrayListaValores()[1].getColumnModel().getColumn(0).setPreferredWidth(110);
	        getJTableArrayListaValores()[1].getColumnModel().getColumn(1).setPreferredWidth(270);
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
              
              if (totalCaja >= totalFactura) {
	              
	                  //Se valida la información antes de guardar 
	                  if (validarRegistro(camposObligatorios)) {
	                  	
		                  	if (obtenerFilas(tablaCompras,9) > 0) {
		
			                      try {
			
		
			                          guardar(); //Se guarda la compra
		                        
			                          actualizar(); //Se actualizan los productos
			
			                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 
			
			                          Mensaje("Información de Compra registrada correctamente","Información",1);
			
			                          limpiar();   //Se limpia la forma 
			
			                       } catch (Exception e) {
			
			                           conectarMySQL.rollback();
			
			                           Mensaje("Error al Insertar Compra" +e,"Error",0); 
			
							      }
		
		                    } else {
						 	
								 	Mensaje("Se debe especificar el detalle de la factura","Información",2);
								 	jtFechaFactura.grabFocus();
								 	
						   }  
		            
		              }
		           
		            } else {
		            	
		            		   int opcion = JOptionPane.showConfirmDialog(getJFrame(),"No existe saldo suficiente en caja,\n Desea inyectar capital para poder realizar esta compra?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                                if (opcion == 0) {
                                  
                                  
                                    new CajaInyectar(this,conectarMySQL,totalCaja,nombreUsuario);
    
                                    
                                }
		            	
		            }
		            
	          
            }  else 

                    if (fuente == Bbuscar) { 

                       buscarRegistro();

                    } else 

                       if (fuente == Bsalir) {

                         ocultarJFrame(); 

                       } else
                       
                          if (fuente == jtNombre)  {
					
								if (getJTableArrayListaValores()[0].isVisible()) {
				
									if (getJTableArrayListaValores()[0].getSelectedRow() > -1)
				
										getJTableArrayListaValores()[0].setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
				
										getJTableArrayListaValores()[0].setRowSelectionInterval(0,0);
				
										getJTableArrayListaValores()[0].grabFocus();
				
							     } else 
				  			     
				  			     	jtNumeroFactura.grabFocus();
					  			     
					  	 } else
					  	  
					  	          if (fuente == jbInformacionProveedor) {
		                             
		                              if (!jtNit.getText().isEmpty())   
		                                 
		                                 configurarJDialogProveedores();
		                                 
		                              else {
		                              	  
		                              	  Mensaje("Se debe especificar el Nit del Proveedor","Información",2);
		                              	  jtNit.grabFocus();
		                              	
		                              }
		                                
		                                   
		                            
		                          } else
		                             
		                              if (fuente == jbCerrar) {
		                             
		                                   dialogProveedor.setVisible(false);
		                                   jtNumeroFactura.grabFocus();
		                            
		                              } else 
		                              	
		                              	 if (fuente == jbCalendario) {
		                              	    
		                              	    if (calendario == null)
		                              	    	
		                              	 	    calendario = new Calendario(jtFechaFactura,getJFrame(),490,195);
		                              	 	    
		                              	 	else
		                              	 	     
		                              	 	     calendario.mostrarCalendario(true);    
		                              	 	
		                              	 } else
		                              	    
		                              	    if (fuente == jtProductoJTable)  {
					
													if (getJTableArrayListaValores()[1].isVisible()) {
									
														if (getJTableArrayListaValores()[1].getSelectedRow() > -1)
									
															getJTableArrayListaValores()[1].setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
									
															getJTableArrayListaValores()[1].setRowSelectionInterval(0,0);
									
															getJTableArrayListaValores()[1].grabFocus();
									
												     } 
										  			     
										  	 }
										  	 
		
		
			  	 
										  	 
					  	          

      } 
       //******************************************************************************************************************

      public void buscarCaja() {
   	  
	   	    try {
	   	    
	        	final String sentenciaSQL = "Select ValorCaja "+
	        	                            "From MovimientosCaja "+
	        	                            "Order by NumeroTransaccion desc " +
	        	                            "Limit 1  ";
	                                                             
	           	ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	        	
	        	if (resultado !=null) {
	             
	              if (resultado.next()) {
	              	
	                  totalCaja = resultado.getLong(1); 	
	              	
	              }
	            
	            }  
	        
	       
	      }  catch(Exception e) {
	          
	            Mensaje("Error al buscar valor de caja"+e,"Error",JOptionPane.ERROR_MESSAGE);
	      }    
			 		
   	
     }
   
    
     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
      
      	/*if (f.getOppositeComponent() != null && f.getOppositeComponent().getName() != null) {  //Se validan las filas para borrar la informacion que no se ha digitado
           
           if (!f.getOppositeComponent().getName().equals("21")) {
           
			 detenerEditar();
			
			 int numFilaBorrarElementos = obtenerFilas(tablaCompras,5);
	
			 for (int i = 0; i < 7; i++)  //se borra la fila que no se termino de digitar
			  
			   tablaCompras.setValueAt("",numFilaBorrarElementos,i);
		  }	 
			
		}*/	 	       	
				
        
        if (f.
        Component() == jtProductoJTable && (getDialogoArrayListaValores() == null || getDialogoArrayListaValores()[1] == null || (getDialogoArrayListaValores()[1]!= null && !getDialogoArrayListaValores()[1].isVisible())) && jtProductoJTable.isFocusable() && mostrarListaAutomatica) {
	              
	             if (tablaCompras.getValueAt(filaSelecionada,0) != null) {
	                      
	                      mostrarListaValores();
			             
	             }  
		      
		     } else 
		           
		          
		           
			       if (f.getComponent() == jtNombre && (getDialogoArrayListaValores() == null || getDialogoArrayListaValores()[0] == null || (getDialogoArrayListaValores()[0]!= null && !getDialogoArrayListaValores()[0].isVisible())) && jtNombre.isFocusable() && mostrarListaAutomatica) {
		            
		                  detenerEditar(); 
						  
						  listaValorProveedores.mostrarListaValores(78);
						    
					     if (getJTableArrayListaValores()[0].getColumnModel().getColumn(0).getPreferredWidth() != 90) {
					     
						     getJTableArrayListaValores()[0].getColumnModel().getColumn(0).setPreferredWidth(90);
				             getJTableArrayListaValores()[0].getColumnModel().getColumn(1).setPreferredWidth(290);
				            
				             
			             }	       
						 
						          
					 } else
		              
			              if (f.getComponent() != jtNombre && getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[0]!= null && getDialogoArrayListaValores()[0].isVisible() && jtNombre.isFocusable()) {
			            
			                    detenerEditar(); 
			                    
			                    if (getJTableArrayListaValores()[0].getRowCount() > 0) {
			                    
						    
				                	//Se oculta el scroll de la lista de valores
									jtNit.setText(getJTableArrayListaValores()[0].getValueAt(0,0).toString()); 
									jtNombre.setText(getJTableArrayListaValores()[0].getValueAt(0,1).toString());
									jtTelefono.setText(getJTableArrayListaValores()[0].getValueAt(0,2).toString());
									jtNumeroProveedor.setText(getJTableArrayListaValores()[0].getValueAt(0,3).toString());
					
									getDialogoArrayListaValores()[0].setVisible(false); //Se oculta automaticamente la lista de valor es
							    
							    } else {
							    	
							    	jtNombre.setText("");
							    	jtNombre.grabFocus();
							    	listaValorProveedores.mostrarListaValores();
							    	
							    }		
							
							} else
							
						  	    if (f.getComponent() != jtProductoJTable && getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[1]!= null && getDialogoArrayListaValores()[1].isVisible() && jtNombre.isFocusable()) {
			            
			                	     detenerEditar();
									 
									 getDialogoArrayListaValores()[1].setVisible(false);
								
							    	
							    } else
							       
						           if (f.getComponent()  == jtCodigoProductoJTable) {
							          
							          ((JTextField)editorCodigoProductoJTable.getComponent()).selectAll();
							      	  
							      	  
						  	       } else
							           
							           if (f.getComponent()  == jtProductoJTable) {
								      
								      	  ((JTextField)editorProductoJTable.getComponent()).selectAll();
								       
							  	       } else
							  	         
							  	           if (f.getComponent()  == jtCantidadProductoJTable) {
								      
								        	  ((JTextField)editorCantidadProductoJTable.getComponent()).selectAll();
							  	       
							  	           } else
						  	       	 
										       if (f.getComponent()  == jtPrecioCompraProductoJTable) {
									
										      	  ((JTextField)editorPrecioCompraProductoJTable.getComponent()).selectAll();
									  	       
									  	       } else
									  	          
									  	          if (f.getComponent()  == jtPrecioListaProductoJTable) {
									
										      	  	  ((JTextField)editorPrecioListaProductoJTable.getComponent()).selectAll();
									  	       
									  	       		} else
									  	       		
										  	       		if (f.getComponent()  == jtPrecioPorcentajeListaProductoJTable) {
										
											      	  	  ((JTextField)editorPorcentajePrecioListaProductoJTable.getComponent()).selectAll();
										  	       
										  	       		} else
										  	       		
										  	       		  if (f.getComponent()  == jtPrecioMinimoProductoJTable) {
										
											      	  	     ((JTextField)editorPrecioMinimoProductoJTable.getComponent()).selectAll();
										  	       
										  	       		  } else
									  	            
									  	                      if (f.getComponent()  == jtPrecioPorcentajeMinimoProductoJTable) {
										
											      	  	           ((JTextField)editorPorcentajePrecioMinimoProductoJTable.getComponent()).selectAll();
										  	       
											  	       		  } else
										  	            
										  	            
								                       		         detenerEditar();
								                
							      
							    


		  	               
	                    
					
		 
       // se coloca el atributo visual por defecto
       f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
      
       if (f.getComponent() != jtNombre && f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField") || f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JFormattedTextField"))
			          
			   ((JTextField)f.getComponent()).selectAll();
       
       mostrarListaAutomatica = true;
    
       
   
		
	 }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
       
   
        if (!buttonEditor.isPushed)	{
        	
	        if (f.getComponent() == jtNit && !jtNit.getText().isEmpty()) {
	        
	            if (!traerInformacion()) {
	            	
	            	
	            		    mostrarListaAutomatica = false;
			            	
			            	//Se verifica si se desean hacer tramites diferentes a matriculas o radicado
					   	    int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Nit  " +   jtNit.getText() + " no registrado \nDesea registrar este Nit?","Notificación ",JOptionPane.YES_NO_OPTION);
						    
						    if ( opcion == 0 ) {
						    	
						    	mostrarListaAutomatica = false;
						    	new Proveedor(conectarMySQL,getJFrame(),jtNit.getText());
						    	getJFrame().setVisible(false);
						    	
						    	
						    } else {
						    	
						    	jtNit.setText("");
						    	
						    }	
	            
	            } else {
	            	    
	            	    
	            	    mostrarListaAutomatica = false;
	            	    jtNumeroFactura.grabFocus();
	            	    
	            	    
	            }
	        
	        } else
	           
	            if (f.getComponent() == jtNumeroFactura && !jtNumeroFactura.getText().isEmpty()) {
	        
	   
	                  if (buscarNumeroFactura()) {
	                  	  
	                  	  jtNumeroFactura.setText("");
	                  	  jtNumeroFactura.grabFocus();
	                  	  Mensaje("Numero de factura ya se encuentra registrado","Información",2);
	                  	  
	                  	
	                  }
	            
	            
	            
	            } else
	            
		            if (f.getComponent() ==  jtFechaFactura) {
		   
		              if (!esFecha(jtFechaFactura.getText())) {
		              	  
		              	   Mensaje("Fecha " + jtFechaFactura.getText()  +" Invalida " ,"Reporte de error",0);
		            	   jtFechaFactura.setText(getObtenerFecha(conectarMySQL).replace('-','/'));
		            	   jtFechaFactura.selectAll();
		            	   jtFechaFactura.grabFocus();
		              	
		              	
		              } else {
		                        	
		                     tablaCompras.setValueAt("1",0,0);
		                     tablaCompras.editCellAt(0,1);
		                     editorCodigoProductoJTable.getComponent().requestFocus();
		                     
		                  
		          	}
		          	
		          
		          	
		          } else
		          	
		          	 if (f.getComponent() ==  jtCodigoProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
		   
		          	 	if (tablaCompras.getValueAt(filaSelecionada,1) != null && !tablaCompras.getValueAt(filaSelecionada,1).toString().isEmpty()) {
		          	 
		          	         if (validarDatosJTable())	 {//Se valida que al valor digitado no se encuentre repetido
				    
		          	 
				          	 	   if (!buscarRegistroProducto(filaSelecionada)) {
					         	        
					         	         mostrarListaAutomatica = false;
					         	       	 Mensaje("Producto " +  tablaCompras.getValueAt(filaSelecionada,1) +" no registrado","Información",2);
					         	     	 tablaCompras.setValueAt("",filaSelecionada,1);
					         	         tablaCompras.editCellAt(filaSelecionada,2);
				                         editorProductoJTable.getComponent().requestFocus();
					         	     	 mostrarListaAutomatica = true;
					         	     	
					         	     
					         	      } else {
					         	      	
					         	        tablaCompras.editCellAt(filaSelecionada,3);
				                        editorCantidadProductoJTable.getComponent().requestFocus();
				                          
				                        
				                      }  
				                            
				           } else {
				           	 
				           		mostrarListaAutomatica = false;
				           		tablaCompras.setValueAt("",filaSelecionada,1);
				           		tablaCompras.editCellAt(filaSelecionada,1);
				                editorCodigoProductoJTable.getComponent().requestFocus();
				                Mensaje("Este codigo ya ha sido digitado","Información",2);
				                          
			      	
				           }           
				                     
			         	 } else {
			         	 	
			         	 	    tablaCompras.editCellAt(filaSelecionada,2);
		                        editorProductoJTable.getComponent().requestFocus();
		                        
			         	 }
		          	 	     	
			         	     
		          	 } else
		          	 
		          	 	if (f.getComponent() ==  jtCantidadProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
		               
		              	      if (tablaCompras.getValueAt(filaSelecionada,3) != null && !tablaCompras.getValueAt(filaSelecionada,3).toString().isEmpty()) { //Se calcula el valor
			         	          
				         	         long cantidad = Long.parseLong(tablaCompras.getValueAt(filaSelecionada,3).toString());
				         	         
				         	         if (cantidad > 0) {
				         	          
				         	           if (tablaCompras.getValueAt(filaSelecionada,4) != null && !tablaCompras.getValueAt(filaSelecionada,4).toString().isEmpty()) {
			         	            
						         	         long precioCompra = Long.parseLong(tablaCompras.getValueAt(filaSelecionada,4).toString()); 
						         	         
						         	         long subTotal = cantidad * precioCompra;
						         	    
						         	         tablaCompras.setValueAt(formatter.format(subTotal),filaSelecionada,9);
						         	         tablaCompras.setValueAt(subTotal,filaSelecionada,10);
						         	         
						         	   } 
						         	   
						               tablaCompras.editCellAt(filaSelecionada,4);
			                           editorPrecioCompraProductoJTable.getComponent().requestFocus();
			                         
						         	
					         	    } else {
					         	    	 
					         	    	 mostrarListaAutomatica = false;
					         	         tablaCompras.setValueAt("",filaSelecionada,3);
				                    	 tablaCompras.editCellAt(filaSelecionada,3);
							  	         editorCantidadProductoJTable.getComponent().requestFocus();
							  	         Mensaje("La cantidad comprada debe ser mayor cero ","Información",2);
				                    	 
					         	    	
					         	    	
					         	    }    
				         	   } else  {
				         	   	           mostrarListaAutomatica = false;
				         	   	           tablaCompras.setValueAt("",filaSelecionada,3);
					         	           tablaCompras.editCellAt(filaSelecionada,3);
									  	   editorCantidadProductoJTable.getComponent().requestFocus();
									  	   
									    
									  	   if  (getDialogoArrayListaValores() == null || (getDialogoArrayListaValores()[1] != null && !getDialogoArrayListaValores()[1].getName().equals("dummy")))
									  	   
									  	      Mensaje("Se debe especificar la cantidad de producto a comprar","Información",2);
									  	      
									  	   else
									  	   
									  	      if (getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[1] != null)
									  	         
									  	           getDialogoArrayListaValores()[1].setName("");
									  	          
									  	      
									  	   
						        } 	
				         	  
		          	 	} else
		          	 	   
		          	 	   	if (f.getComponent() ==  jtPrecioCompraProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
		          
		          	               if (tablaCompras.getValueAt(filaSelecionada,4) != null && !tablaCompras.getValueAt(filaSelecionada,4).toString().isEmpty()) {
				                        
				                        long cantidad = Long.parseLong(tablaCompras.getValueAt(filaSelecionada,3).toString());
				         	            long precioCompra = Long.parseLong(tablaCompras.getValueAt(filaSelecionada,4).toString());
				         	            
				         	            if (precioCompra > 0) {
				         	            
								         	    long subTotal = cantidad * precioCompra;
							         	        Float precioLista = ( 1 + Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,6).toString())/100) * precioCompra ;
							         	        Float precioMinimo = ( 1 + Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,8).toString())/100) * precioCompra;
							         	        
							         	        tablaCompras.setValueAt(formatter.format(subTotal),filaSelecionada,9);
							         	        tablaCompras.setValueAt(subTotal,filaSelecionada,10);
							         	        tablaCompras.setValueAt(precioLista.longValue(),filaSelecionada,5);
							         	        tablaCompras.setValueAt(precioMinimo.longValue(),filaSelecionada,7);
							         	        
							         	        
							         	       	
				                                tablaCompras.editCellAt(filaSelecionada,5);
				                                editorPrecioListaProductoJTable.getComponent().requestFocus();
				                        
				                        } else {
				                        	
				                        		
						         	         tablaCompras.setValueAt("",filaSelecionada,4);
					                    	 tablaCompras.editCellAt(filaSelecionada,4);
								  	         editorPrecioCompraProductoJTable.getComponent().requestFocus();
								  	         Mensaje("El precio de compra debe ser mayor cero ","Información",2);
				                        
				                        	
				                        }
				                                
						         	
					          	        
					          	   } else {
					          	   	
					          	   	    mostrarListaAutomatica = false;
					          	   	    tablaCompras.setValueAt("",filaSelecionada,4);
					         	        tablaCompras.editCellAt(filaSelecionada,4);
									  	editorPrecioCompraProductoJTable.getComponent().requestFocus();
									  	Mensaje("Se debe especificar el precio  del producto a comprar","Información",2);
									  	 
					          	   }     
						         		
		          	 	   	} else
		          	 	   	    
		          	 	   	    if (f.getComponent() ==  jtPrecioListaProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
		                                
		                                //se calcula el porcentaje teniendo en cuenta el precio
		                 
		                               if (tablaCompras.getValueAt(filaSelecionada,5) != null && !tablaCompras.getValueAt(filaSelecionada,5).toString().isEmpty()) {
				                  
		                                	  
		                                	  float precioCompra = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,4).toString());
				                              float precioLista = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,5).toString());
				                   
				                              if (precioLista > precioCompra) {
				                              
					                              
					                              float valor =   (precioLista -  precioCompra) / precioCompra ;
			                                	  Float porcentaje = redondearNumero(valor,2) * 100;
			                                	   
			                                	  tablaCompras.setValueAt(porcentaje.intValue(),filaSelecionada,6);
			                                	  
			                                      tablaCompras.editCellAt(filaSelecionada,7);
		                                          editorPrecioMinimoProductoJTable.getComponent().requestFocus();
			                                	  
			                                  } else {
			                                  	    
			                                  	    mostrarListaAutomatica = false;
			                                  	    tablaCompras.setValueAt("",filaSelecionada,5);
		                                            tablaCompras.editCellAt(filaSelecionada,5);
		                                            editorPrecioListaProductoJTable.getComponent().requestFocus();
		                                            Mensaje("El precio de lista debe ser mayor al precio de compra","Información",2);
			                                  	
			                                  	
			                                  }	     
			                                	  
		                                	  
		                                	  
		                                } else {
		                                	  
		                                	  mostrarListaAutomatica = false;
		                                	  tablaCompras.setValueAt("",filaSelecionada,5);
		                                      tablaCompras.editCellAt(filaSelecionada,5);
		                                      editorPrecioListaProductoJTable.getComponent().requestFocus();
		                                      Mensaje("Se debe especificar el precio de Lista","Información",2);
		                                	
		                                }
					         	   
					          	       
		        		         		
		          	 	   	    } else
		          	 	   	    
			          	 	   	    if (f.getComponent() ==  jtPrecioPorcentajeListaProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
				          
							         	         //Se calcula el precio a partir del porcentaje
							         	       
							         	         if (tablaCompras.getValueAt(filaSelecionada,6) != null && !tablaCompras.getValueAt(filaSelecionada,6).toString().isEmpty()) {
				                   	
				                                	 float precioCompra = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,4).toString());
				                                	 float porcentaje = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,6).toString());
				                                	 
				                                	 if (porcentaje > 0) {
				                                	 
				                                	    Float precio =   redondearNumero(precioCompra * (1 + porcentaje/100),2);	
				                                	    tablaCompras.setValueAt(precio.longValue(),filaSelecionada,5);
				                                	    
							                            tablaCompras.editCellAt(filaSelecionada,7);
							                            editorPrecioMinimoProductoJTable.getComponent().requestFocus();
						        		         		
				                                	   
				                                	 
				                                	 }  else {
			                                  	    
			                                  	    
		                                	                mostrarListaAutomatica = false;
					                                  	    tablaCompras.setValueAt("",filaSelecionada,6);
				                                            tablaCompras.editCellAt(filaSelecionada,6);
				                                            editorPorcentajePrecioListaProductoJTable.getComponent().requestFocus();
				                                            Mensaje("El porcentaje de ganancia debe ser mayor a cero","Información",2);
			                                  	
			                                           }	     
				                                	  
				                                	  
				                                } else {
				                                	
		                                	           mostrarListaAutomatica = false;
				                                	   tablaCompras.setValueAt("",filaSelecionada,6);
								         	           tablaCompras.editCellAt(filaSelecionada,6);
												  	   editorPorcentajePrecioListaProductoJTable.getComponent().requestFocus();
												  	   Mensaje("Se debe especificar el porcentaje precio de lista producto","Información",2);
				                                }
				                                
				          	 	   	 } else
		          	 	   	             
		          	 	   	             if (f.getComponent() ==  jtPrecioMinimoProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
				       
				                               
				                               //se calcula el porcentaje teniendo en cuenta el precio
		                 
				                                if (tablaCompras.getValueAt(filaSelecionada,7) != null && !tablaCompras.getValueAt(filaSelecionada,7).toString().isEmpty()) {
				                   	 	
				                                	 float precioCompra = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,4).toString());
				                                	 float precioLista = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,5).toString());
				                                	 float precioMinimo = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,7).toString());
				                                	 
				                                	 if  (precioLista >= precioMinimo && precioMinimo > precioCompra) {
			                                	  
			                                  	 
					                                	 float valor = (precioMinimo -  precioCompra) / precioCompra;
					                                	 Float porcentaje = redondearNumero(valor,2) * 100;        
							                             tablaCompras.setValueAt(porcentaje.longValue(),filaSelecionada,8);
							                             
							                             if (filaSelecionada > 10) {
				                                	      	
 				                                	           scroll.getVerticalScrollBar().setValue(17 * (filaSelecionada - 10));
                            	                         }    
				                                	      
							                             tablaCompras.setValueAt(filaSelecionada + 2, filaSelecionada + 1, 0);
										         	     tablaCompras.editCellAt(filaSelecionada + 1,1);
									         	         editorCodigoProductoJTable.getComponent().requestFocus();
							                             
							                         }  else {
							                         	 
							                         	  tablaCompras.setValueAt("",filaSelecionada,7);
			                                              tablaCompras.editCellAt(filaSelecionada,7);
			                                              editorPrecioMinimoProductoJTable.getComponent().requestFocus();
			                                              Mensaje("El precio  minimo debe ser menor o igual al precio de lista y mayor al precio compra","Información",2);
						                         	
							                         	
							                         }    
			                                	  
				                                } else {
		 		                                	
				                                 	  tablaCompras.setValueAt("",filaSelecionada,7);
		                                              tablaCompras.editCellAt(filaSelecionada,7);
		                                              editorPrecioMinimoProductoJTable.getComponent().requestFocus();
		                                              Mensaje("Se debe especificar el precio minimo producto","Información",2);
				                                }
							         	   
					                            
				        		         		
					          	 	   	 } else
					          	 	   	 
					          	 	   	    if (f.getComponent() ==  jtPrecioPorcentajeMinimoProductoJTable && comodinFocusCellEditor &&  f.getOppositeComponent() != null &&  f.getOppositeComponent().getName() == null) {
						                        
						                         //Se calcula el precio a partir del porcentaje
							         	       
							         	         if (tablaCompras.getValueAt(filaSelecionada,8) != null && !tablaCompras.getValueAt(filaSelecionada,8).toString().isEmpty()) {
				                   	
				                                	 float precioCompra = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,4).toString());
				                                	 float porcentajeLista = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,6).toString());
				                                	 float porcentaje = Float.parseFloat(tablaCompras.getValueAt(filaSelecionada,8).toString());
				                                	 
				                                	 if (porcentaje > 0 && porcentaje <= porcentajeLista ) {
				                                	 	    
				                                	     Float precio =   redondearNumero(precioCompra * (1 + porcentaje/100),2);	
				                                	     tablaCompras.setValueAt(precio.longValue(),filaSelecionada,7);
				                                	     
				                                	    
				                                	     
				                                	    if (filaSelecionada > 10) {
				                                	      	
				                                	      	Object nuevaFila[] = {"","","","","","","","","","","",""};
							 		                        dm.addRow(nuevaFila); 
				                                	    }    
				                                	      
							                             tablaCompras.setValueAt(filaSelecionada + 2, filaSelecionada + 1, 0);
										         	     tablaCompras.editCellAt(filaSelecionada + 1,1);
									         	         editorCodigoProductoJTable.getComponent().requestFocus();
									         	            
				                                	   
				                                	 
				                                	 }  else {
			                                  	    
			                                  	    
					                                  	    tablaCompras.setValueAt("",filaSelecionada,8);
				                                            tablaCompras.editCellAt(filaSelecionada,8);
				                                            editorPorcentajePrecioMinimoProductoJTable.getComponent().requestFocus();
				                                            Mensaje("El porcentaje minimo debe ser mayor a cero y menor o igual al porcentaje de lista","Información",2);
			                                  	
			                                           }	     
				                                	  
				                                	  
				                                } else {
				                                	
				                                	   tablaCompras.setValueAt("",filaSelecionada,8);
								         	           tablaCompras.editCellAt(filaSelecionada,8);
												  	   editorPorcentajePrecioMinimoProductoJTable.getComponent().requestFocus();
												  	   Mensaje("Se debe especificar el porcentaje precio de lista producto","Información",2);
				                                }
				                                
						                        
						                      
			          	 	   	      } 
			          	 	   	      
		    } else {
      	  
      	       buttonEditor.isPushed = false; //Se borran los datos de la fila que se borro
      	       
      	       int numFilas = obtenerFilas(tablaCompras,9);  //Se mueven las filas
     
      	 
      	       for (int i = filaSelecionada ; i < numFilas ; i++) {
      	       	 
      	       	 for (int j = 0; j < 11; j++) 	  
      	       	   
      	       	   tablaCompras.setValueAt(tablaCompras.getValueAt(i+1,j), i, j);
      	       }
      	        
      	       
      	       for (int i = 1 ; i < 11; i++)   //Cuando Se selecciona se borra la fila
	                        	
	              tablaCompras.setValueAt(null,numFilas,i);
	           
	            numFilas = obtenerFilas(tablaCompras,9);
	           
      	       tablaCompras.setValueAt(filaSelecionada + 1, filaSelecionada, 0);
      	       tablaCompras.setValueAt(null,filaSelecionada + 1,0);
      	       tablaCompras.setValueAt(null,filaSelecionada + 2,0);
      	       tablaCompras.editCellAt(numFilas,1);
               editorCodigoProductoJTable.getComponent().requestFocus();
               
              
      	 }
              	 	   	      
         comodinFocusCellEditor = true; 
           	
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }
      
      //************************ Metodo valueChanged() ****************************************
      
      public void valueChanged(ListSelectionEvent e) { 
      
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            
            int fila = tablaCompras.getSelectedRow();
            
            if (fila > -1) {
            
		           if (validarFilas()) {
		           
		           
		              if (tablaCompras.getValueAt(fila,0) == null || tablaCompras.getValueAt(fila,0).toString().isEmpty())
		            
		                  tablaCompras.setValueAt( fila + 1,fila,0);
				   
				   }
		      }
		      
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