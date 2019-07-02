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

final public class VentaAnulada extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpCriterios;
    private  JPanel jpCompras;
    
    //Se delcaran los JTextField
    private  JTextField jtIdProducto;
    private  JTextField  jtNombreProducto;
    
     //** Se declaran los JTable
    private  JTable tablaVentas;
    private  JTable tablaVentasDetalle;
    
  
   //JDialog
   private JDialog dialogVentasDetalle;
   
   //JButton
   private JButton jbCerrar;
   
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Numero Venta","Cliente","Fecha","Fecha Anulación","Total"};
    private Object [][] datos = new Object[23][5];
    
    private Object [] nombresColumnasDetalle = {"Item","Código","Producto","Cantidad","Precio Venta","Subtotal"};
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
    public VentaAnulada(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Ventas Anuladas","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;


      jpCompras = getJPanel("Ventas Anuladas",60, 60, 700, 450,14);

    
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
      scrollPane.setBounds(25, 40, 655, 388);
      jpCompras.add(scrollPane);
       
      
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/VentasAnuladas.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
      
      columnas = new Vector();
      columnas.add("Numero Venta");
      columnas.add("Cliente");
      columnas.add("Fecha");
      columnas.add("Fecha Anulacion");
      columnas.add("Total");
      
      traerInformacion();

      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
      
    
    
    }
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(80);
	    tablaVentas.getColumnModel().getColumn(1).setPreferredWidth(258);
		tablaVentas.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
		tablaVentas.getColumnModel().getColumn(2).setPreferredWidth(49);
		tablaVentas.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaVentas.getColumnModel().getColumn(3).setPreferredWidth(80);
		tablaVentas.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
		tablaVentas.getColumnModel().getColumn(4).setPreferredWidth(80);
		tablaVentas.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
			
		

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
		
	    traerInformacionDetalle(tablaVentas.getValueAt(tablaVentas.getSelectedRow(),2).toString());
		dialogVentasDetalle.setVisible(true);
	
	}	  	  
    
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
       String sentenciaSQL =  "Select   V.NumeroVenta,Concat(C.Nombre,Concat(' ',C.Apellido)),V.Fecha,V.FechaAnulacion,V.Total "+
                              "From   VentasEncabezado V, Clientes C "+
                              "Where  V.idCliente = C.idCliente and V.Estado = 'A' ";
                              
                             
        
        	
	   
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
			    	
			    	filas.add(columnas);	
		    				
			  }
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
		 	     resultadoBoolean = true;
		 	 
			 
			 } else 
			 	
			 	 
			 	 	Mensaje(" No existen facturas asociadas a ningun producto","Información",2);
			 
			
		 	   	
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
       	
	    
	    if (columnasDetalle == null) {
	    	
	    	  columnasDetalle = new Vector();
		      columnasDetalle.add("Item");
		      columnasDetalle.add("idProducto");
		      columnasDetalle.add("Descripcion");
		      columnasDetalle.add("Cantidad");
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
			    	columnas.add(resultado.getString(5));
			    	columnas.add(resultado.getString(6));
			    	
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

       
            if (fuente == Bbuscar) { 

               traerInformacion();

            } else 

               if (fuente == Bsalir) {

                 ocultarJFrame(); 

               }  else 
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
        
        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
       
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

}