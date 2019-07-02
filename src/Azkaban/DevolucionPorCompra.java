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

final public class DevolucionPorCompra extends CrearJFrame implements ActionListener, FocusListener , ListSelectionListener{

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpComprasEncabezado;
    private  JPanel jpComprasDetalle;
    private  JPanel jpNumeroCompra;
    private  JPanel jpProveedores;
    private  JPanel jpPeriodos;
    
    
    //Se delcaran los JTextField
    private  JTextField jtNumeroDevolucion;
    private  JTextField jtIdProveedor;
    private  JTextField jtRazonSocial;
    
    //JFormatedField
    private  JFormattedTextField jtFechaInicial;
    private  JFormattedTextField jtFechaFinal;
   
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
    private JLabel jlNumeroDevolucion;
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
    private Object [] nombresColumnasEncabezado = {"Numero Devolución","Proveedor","Fecha","Total"};
    private Object [][] datosEncabezado = new Object[7][3];
    
    private Object [] nombresColumnas = {"Item","Producto","Devueltas"};
    private Object [][] datos = new Object[9][6];
    
    
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
    
         
   //** Int
   private int filaSeleccionada = 0;
   
    //** Constructor General 
    public DevolucionPorCompra(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Devolucion Por Compra","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(10,40,780,500);
      
      jpNumeroCompra = getJPanel(0,0,100,100);
      pestaña.addTab("Número Devolución",jpNumeroCompra);
      
      jpProveedores = getJPanel(0,0,100,100);
      pestaña.addTab("Proveedores",jpProveedores);
      
      jpPeriodos = getJPanel(0,0,100,100);
      pestaña.addTab("Periodos",jpPeriodos);

      pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
            detenerEditar();
  			
      		if (pestaña.getSelectedIndex() == 0) {
      			
      			jtNumeroDevolucion.setText("");
      			jtNumeroDevolucion.grabFocus();
      			
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
      
      jpComprasEncabezado = getJPanel("Devoluciones Encabezado",40, 50, 700, 180,14);
      jpNumeroCompra.add(jpComprasEncabezado);
      
      jpComprasDetalle = getJPanel("Detalle Devoluciones ",40, 240, 700, 220,14);
      jpNumeroCompra.add(jpComprasDetalle);
      
      
      
      //** Se declaran los JLabel
     
      jlNumeroDevolucion = getJLabel("Número Devolución: ",280, 20, 130, 20);
      jpNumeroCompra.add(jlNumeroDevolucion);
      
       
      //** Se instancian los JTextField
      jtNumeroDevolucion = getJTextField("",410, 20, 70, 20,"Número de Factura de Venta","9");
      jtNumeroDevolucion.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroDevolucion.addFocusListener(this);
      jtNumeroDevolucion.addKeyListener(getValidarEntradaNumeroJTextField());
      jpNumeroCompra.add(jtNumeroDevolucion);

    
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
     
    
     tablaCompras = getJTable(dm); //Se instancia el JTable con el modelo de datos
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
             
             return false;  
               

         }
         
         public void setValueAt(Object obj, int row, int col) {
	       	
	          super.setValueAt(obj, row, col); 
	          
	     } 
	     
      };

     dm1.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla
     
     
     tablaComprasDetalle = getJTable(dm1,true); //Se instancia el JTable con el modelo de datos
     tablaComprasDetalle.addFocusListener(this);
     
      configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaComprasDetalle);
      scrollPane.setBounds(23, 30, 655, 164);
      jpComprasDetalle.add(scrollPane);
       
     
        
      //** Se configura el icono del frame
      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/ComprasDevolucion.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 

      jtNumeroDevolucion.grabFocus();
       
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
      columnas.add("Item");
      columnas.add("Producto");
      columnas.add("Cantidad");
      columnas.add("");
      
      
    
    }
    
     
  //**************************** Metodo configurarColumnasEncabezado() ************************

    private void configurarColumnasEncabezado() {
       
       	tablaCompras.getColumnModel().getColumn(0).setPreferredWidth(85);
       	tablaCompras.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaCompras.getColumnModel().getColumn(1).setPreferredWidth(325);
	    tablaCompras.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
	    tablaCompras.getColumnModel().getColumn(2).setPreferredWidth(60);
	    tablaCompras.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
	    tablaCompras.getColumnModel().getColumn(3).setPreferredWidth(60);
	    tablaCompras.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
    
    }
    
    
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaComprasDetalle.getColumnModel().getColumn(0).setPreferredWidth(15);
		tablaComprasDetalle.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaComprasDetalle.getColumnModel().getColumn(1).setPreferredWidth(511);
		tablaComprasDetalle.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		tablaComprasDetalle.getColumnModel().getColumn(2).setPreferredWidth(44);
		tablaComprasDetalle.getColumnModel().getColumn(2).setCellRenderer(getAlinearDerecha());
	       	
      
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
    
    
 	//******************************** detenerEditar ********************************
 	
 	private void detenerEditar() {
 
 	   if (tablaComprasDetalle.getEditingRow() > - 1 && tablaComprasDetalle.getEditingColumn() > -1)	
 		
 		 tablaComprasDetalle.getCellEditor(tablaComprasDetalle.getEditingRow(),tablaComprasDetalle.getEditingColumn()).stopCellEditing();
 	
 	}
 	
     //*****************************Metodo traerInformación() ******************************************
    
    final private boolean traerInformación() { 
          
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "Select D.NumeroDevolucion,P.RazonSocial,Substr(C.Fecha,1,10),C.Total "+
                              "From  ComprasEncabezado C, Proveedores P, DevolucionesCompraEncabezado D "+
                              "Where C.NumeroProveedor = P.NumeroProveedor and  C.NumeroCompra = D.NumeroCompra ";
                 
                             
          if (pestaña.getSelectedIndex() == 0) 
        	
        
         	sentenciaSQL += "and D.NumeroDevolucion = '" + jtNumeroDevolucion.getText() + "'"; 
           
         
         else                     
                              
	        if (pestaña.getSelectedIndex() == 1) 
         
	            sentenciaSQL += "and  P.Nit = '" + jtIdProveedor.getText() + "'"; 
	         
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
						 	  	Mensaje(" Este Proveedor no tiene devoluciones asociadas ","Información",2);
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

        
        String sentenciaSQL = "Select  C1.Item,P.Descripcion,C1.CantidadDevuelta "+
                              "From   DevolucionesCompraDetalle C1, Productos P "+
                              "Where   C1.idProducto = P.idProducto  and  C1.NumeroDevolucion = " + numeroFactura; 
      
      
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
				   
				       
        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
        
        if (f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField") || f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JFormattedTextField"))
			          
			  ((JTextField)f.getComponent()).selectAll();
	    
	  
	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
    
        if (f.getComponent() == jtNumeroDevolucion && !jtNumeroDevolucion.getText().isEmpty()) {
        	
        	if (!traerInformación()) {
        		
        		Mensaje("Numero de devolución no registrado","Información",2);
        		jtNumeroDevolucion.setText("");
        		jtNumeroDevolucion.grabFocus();
        	
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
        	
          } 
        

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());
        
        mostrarListaAutomatica = true;

      }

    
     
}