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


final public class CotizacionPorPeriodos extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpFechas;
    private  JPanel jpPeriodos;
    private  JPanel jpCotizaciones;
    
    //Se delcaran los JTextField
    private  JTextField jtTotalCotizaciones;
    
     //** JTabbedPane
    private JTabbedPane pestaña;
    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
    
     //JFormatedField
    private  JFormattedTextField jtFechaInicial;
    private  JFormattedTextField jtFechaFinal;
    
    
    //Se declaran los JButton
    private JButton jbFechaInicial;
    private JButton jbFechaFinal;
    
    
    //** Se declaran los JTable
    private  JTable tablaCotizaciones;
    private  JTable tablaCotizacionesDetalle;
    
    
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
     
    //JDialog
    private JDialog dialogVentasDetalle;
   
   //JButton
   private JButton jbCerrar;
   
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Tipo","Identificación","Nombres","Nro Cotizaciones","Fecha","Total"};
    private Object [][] datos = new Object[16][6];
    
    private Object [] nombresColumnasDetalle = {"Item","Código","Producto","Cantidad","Precio","Subtotal"};
    private Object [][] datosDetalle = new Object[16][5];
    
    
    //Vectores
    private Vector columnas;
    private Vector columnasDetalle;

    //Modelo de datos 
    private SortableTableModel dm;
    private SortableTableModel dm1;
    
    //Objeto calendario de Java
    private Calendar calendar = Calendar.getInstance();
   
   
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
      //Calendario
    private Calendario calendarioInicial;
    private Calendario calendarioFinal;
    
     //String
    private String fechaInicial;
  
   
    //** Constructor General 
    public CotizacionPorPeriodos(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Cotizaciones Por Periodo","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;


      //** Se agregan los JPanel
      
      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(10,45,780,500);
      
       
      jpPeriodos = getJPanel(0,0,100,100);
      pestaña.addTab("Periodo  ",jpPeriodos);
      
      jpFechas = getJPanel(0,0,100,100);
      pestaña.addTab("Fechas   ",jpFechas);
     
      jpCotizaciones = getJPanel("Cotizaciones Realizadas",40, 60, 700, 350,14);
      jpPeriodos.add(jpCotizaciones);
      
     
       pestaña.addChangeListener(new ChangeListener() {
      	
      	public void stateChanged(ChangeEvent a) {
      		
      		if (pestaña.getSelectedIndex() == 1) {
      		  	
      		   jtTotalCotizaciones.setText("");
      		   
      		   jpFechas.add(jpCotizaciones);   
      		   
      		   dm.setDataVector(datos,nombresColumnas);
             	
      		   configurarColumnas();
      			
     
      			
      		} else {
      		
	      		  jtTotalCotizaciones.setText("");
	             
	              jpPeriodos.add(jpCotizaciones);
	              
	              dm.setDataVector(datos,nombresColumnas);
	              
	              configurarColumnas();
      			
	              traerInformacion();
      				
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

     tablaCotizaciones = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaCotizaciones.addFocusListener(this);
  
     tablaCotizaciones.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		     	
     		   configurarJDialogVentasDetalle();
     	}
     	
     });
 	  
    
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaCotizaciones);
      scrollPane.setBounds(25, 40, 655, 276);
      jpCotizaciones.add(scrollPane);
       
       //** Elementos busqueda por fechas
      JLabel jlFechaInicial = getJLabel("Fecha Inicial:",180, 20, 80, 20);
      jpFechas.add(jlFechaInicial);
      
      
      JLabel jlFechaFinal = getJLabel("Fecha Final:",405, 20, 80, 20);
      jpFechas.add(jlFechaFinal);
      
      fechaInicial = getObtenerFecha(conectarMySQL);
      calendar.set(Integer.parseInt(fechaInicial.substring(0,4)),Integer.parseInt(fechaInicial.substring(5,7))-1,Integer.parseInt(fechaInicial.substring(8,10)));
	  calendar.add(Calendar.DAY_OF_MONTH, -15);
	  fechaInicial = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
 	   
      
      jtFechaInicial = getJFormattedTextField(fechaInicial,270, 20, 70, 20,"Digíte el Número de Identificación del Cliente","12");
      jtFechaInicial.setHorizontalAlignment(JTextField.RIGHT);
      jtFechaInicial.addKeyListener(getValidarEntradaNumeroJTextField());
      jtFechaInicial.addFocusListener(this);
      jpFechas.add(jtFechaInicial);
      
      jtFechaFinal = getJFormattedTextField(getObtenerFecha(conectarMySQL),490, 20, 70, 20,"Digíte el Número de Identificación del Cliente","12");
      jtFechaFinal.setHorizontalAlignment(JTextField.RIGHT);
      jtFechaFinal.addKeyListener(getValidarEntradaNumeroJTextField());
      jtFechaFinal.addFocusListener(this);
      jpFechas.add(jtFechaFinal);
      
      jbFechaInicial = getJButton("",345, 20, 20, 20,"Imagenes/Calendario.gif","Calendario");
      jbFechaInicial.setFocusable(false);
      jbFechaInicial.addActionListener(this);
      jpFechas.add(jbFechaInicial);

      
      jbFechaFinal = getJButton("",565, 20, 20, 20,"Imagenes/Calendario.gif","Calendario");
      jbFechaFinal.setFocusable(false);
      jbFechaFinal.addActionListener(this);
      jpFechas.add(jbFechaFinal);
      
      jcPeriodos =  getJComboBox(50,20,130,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Hoy");
      jcPeriodos.addItem("Ultima Quincena");
      jcPeriodos.addItem("Ultimo Mes");
      jcPeriodos.addItem("Ultimo Trimestre");
      jcPeriodos.addActionListener(this);
      jpPeriodos.add(jcPeriodos); 
     
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Calendario.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 
      
      columnas = new Vector();
      columnas.add("Nit");
      columnas.add("Proveedor");
      columnas.add("Numero Venta");
      columnas.add("");
      columnas.add("Fecha");
      columnas.add("Total");
      columnas.add("");
      
      
      jtTotalCotizaciones  = getJTextField("Total Vendidas",578, 320, 100, 20,"",false);
      jtTotalCotizaciones.setHorizontalAlignment(JTextField.RIGHT);
      jpCotizaciones.add(jtTotalCotizaciones);
      
      traerInformacion();

      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
      
       //Se agregan los JLabel
      JLabel jlTotalVentas  = getJLabel("Total:",530, 320, 80, 20);
      jpCotizaciones.add(jlTotalVentas);
      
      
    
    }

	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaCotizaciones.getColumnModel().getColumn(0).setPreferredWidth(27);
	    tablaCotizaciones.getColumnModel().getColumn(1).setPreferredWidth(60);
		tablaCotizaciones.getColumnModel().getColumn(1).setCellRenderer(getAlinearCentro());
		tablaCotizaciones.getColumnModel().getColumn(2).setPreferredWidth(208);
		tablaCotizaciones.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaCotizaciones.getColumnModel().getColumn(3).setPreferredWidth(109);
		tablaCotizaciones.getColumnModel().getColumn(4).setPreferredWidth(58);
		tablaCotizaciones.getColumnModel().getColumn(5).setPreferredWidth(58);
		
		tablaCotizaciones.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
	    tablaCotizaciones.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
	
	
	    if (pestaña.getSelectedIndex() == 1)
	    
	       	tablaCotizaciones.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
	    
	    else   	
	       
	        tablaCotizaciones.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
	    
		
	    

	}
	
   //**************************** Metodo configurarColumnas() ************************

	private void configurarColumnasDetalle() {
        
      
        tablaCotizacionesDetalle.getColumnModel().getColumn(0).setPreferredWidth(29);
		tablaCotizacionesDetalle.getColumnModel().getColumn(1).setPreferredWidth(133);
		tablaCotizacionesDetalle.getColumnModel().getColumn(2).setPreferredWidth(175);
		
	    tablaCotizacionesDetalle.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
		tablaCotizacionesDetalle.getColumnModel().getColumn(3).setPreferredWidth(53);
		tablaCotizacionesDetalle.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
		tablaCotizacionesDetalle.getColumnModel().getColumn(4).setPreferredWidth(63);
		tablaCotizacionesDetalle.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
		tablaCotizacionesDetalle.getColumnModel().getColumn(5).setPreferredWidth(70);
	
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
		
		     tablaCotizacionesDetalle = getJTable(dm1,true); //Se instancia el JTable con el modelo de datos
		     
		     configurarColumnasDetalle();
		     
		      //** Se configura un scroll para el JTable 
		
		      JScrollPane scrollPane = new JScrollPane(tablaCotizacionesDetalle);
		      scrollPane.setBounds(20, 30, 600, 240);
		      jpDetalleCompra.add(scrollPane);
		       
		  	  dialogVentasDetalle.add(jpDetalleCompra);
		  	  
		  	  
		      //** Se agregan los JButton
		
		      jbCerrar = getJButton("Cerrar",280, 310, 125, 25,"","");
		      jbCerrar.addActionListener(this);
		      dialogVentasDetalle.add(jbCerrar);
		      
		}
		
	    traerInformacionDetalle(tablaCotizaciones.getValueAt(tablaCotizaciones.getSelectedRow(),3).toString());
		dialogVentasDetalle.setVisible(true);
	
	}	  	  
      
      
     
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = null;
        
        
        if (pestaña.getSelectedIndex() == 1) {
            
             String fechaFinal = jtFechaFinal.getText(); //Se le suma un dia a la fecha
	              
             calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)));
             calendar.add(Calendar.DAY_OF_MONTH, 1);
             fechaFinal = new SimpleDateFormat("yyyy/MM/dd").format(calendar.getTime());
            
          
            sentenciaSQL =          "Select  Case V.TipoId WHEN 'C' THEN 'Cedula'"+ 
				                    "                   WHEN 'T' THEN 'Tarjeta Id.'"+
				                    "                   Else 'Pasaporte'"+
				                    "                   End,V.idCliente, Concat(C.Nombre,Concat(' ',C.Apellido)),NumeroCotizacion,Substr(Fecha,1,10),Total "+
		                            "From Cotizacionesencabezado V, Clientes C "+
	                                "Where V.idCliente = C.idCliente  and  Fecha between '" + jtFechaInicial.getText() + "' and '" + fechaFinal + "' ";
                              
                              
        } else
          
           if (jcPeriodos.getSelectedIndex() == 0)
                
	                 sentenciaSQL    =  "Select  Case V.TipoId WHEN 'C' THEN 'Cedula'"+ 
					                    "                   WHEN 'T' THEN 'Tarjeta Id.'"+
					                    "                   Else 'Pasaporte'"+
					                    "                   End,V.idCliente, Concat(C.Nombre,Concat(' ',C.Apellido)),NumeroCotizacion,Substr(Fecha,1,10),Total "+
			                            "From Cotizacionesencabezado V, Clientes C "+
		                                "Where V.idCliente = C.idCliente ";
		                
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                
	                 sentenciaSQL    =   "Select  Case V.TipoId WHEN 'C' THEN 'Cedula'"+ 
					                    "                   WHEN 'T' THEN 'Tarjeta Id.'"+
					                    "                   Else 'Pasaporte'"+
					                    "                   End,V.idCliente, Concat(C.Nombre,Concat(' ',C.Apellido)),NumeroCotizacion,Substr(Fecha,1,10),Total "+
			                            "From Cotizacionesencabezado V, Clientes C "+
		                                "Where V.idCliente = C.idCliente and Substr(Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "'"; 
                    
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
        	                  
		                       
				                 sentenciaSQL    =   "Select  Case V.TipoId WHEN 'C' THEN 'Cedula'"+ 
					                                 "                   WHEN 'T' THEN 'Tarjeta Id.'"+
					                                 "                   Else 'Pasaporte'"+
					                                 "                   End,V.idCliente, Concat(C.Nombre,Concat(' ',C.Apellido)),NumeroCotizacion,Substr(Fecha,1,10),Total "+
			                                         "From Cotizacionesencabezado V, Clientes C "+
		                                             "Where V.idCliente = C.idCliente "+
		                                             "                       and substr(Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
										             "                       and Substr(Fecha,1,10) " + condicion;
				     } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                
						                 sentenciaSQL    =   "Select  Case V.TipoId WHEN 'C' THEN 'Cedula'"+ 
					                                         "                   WHEN 'T' THEN 'Tarjeta Id.'"+
					                                         "                   Else 'Pasaporte'"+
					                                         "                   End,V.idCliente, Concat(C.Nombre,Concat(' ',C.Apellido)),NumeroCotizacion,Substr(Fecha,1,10),Total "+
			                                                 "From Cotizacionesencabezado V, Clientes C "+
		                                                     "Where V.idCliente = C.idCliente "+
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
					        	                      
			        	                     
						                                 sentenciaSQL    =  	"Select  Case V.TipoId WHEN 'C' THEN 'Cedula'"+ 
															                    "                   WHEN 'T' THEN 'Tarjeta Id.'"+
															                    "                   Else 'Pasaporte'"+
															                    "                   End,V.idCliente, Concat(C.Nombre,Concat(' ',C.Apellido)),NumeroCotizacion,Substr(Fecha,1,10),Total "+
													                            "From Cotizacionesencabezado V, Clientes C "+
												                                "Where V.idCliente = C.idCliente "+
																		        "      and substr(Fecha,1,7) between " + condicion;
													          
		                             }
		                             
         
         
         
         
	   
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	{ 

              
              Vector <Vector > filas = new Vector <Vector>();
              long totales = 0;
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
			    	columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(resultado.getString(3));
			    	columnas.add(resultado.getString(4));
			    	columnas.add(resultado.getString(5));
			    	
			    	
		    		Long total = resultado.getLong(6);
			        columnas.add(getFormatoNumero(total));
                   
                    totales += total;
                    
			    	filas.add(columnas);	
		    				
			  }
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 
			 	 jtTotalCotizaciones.setText(getFormatoNumero(totales));
			 	 
		 	     resultadoBoolean = true;
		 	 
			 
			 } else {
			 				 	 
			 	 	Mensaje(" No existen facturas asociadas ","Información",2);
			 	
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
        
        String sentenciaSQL = "Select C.Item,C.idProducto, P.Descripcion,C.Cantidad, C.Precio,C.Subtotal "+
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
			    	columnas.add(getFormatoNumero(resultado.getString(5)));
			    	columnas.add(getFormatoNumero(resultado.getString(6)));
			    	
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
				 		   	     
				 		   	     if (fuente == jcPeriodos) {
				 		   	     	
				 		   	     	 dm.setDataVector(datos,nombresColumnas);
				 		   	     	 
				 		   	     	 jtTotalCotizaciones.setText("");
				 		   	     	
				 		   	     	 traerInformacion();
				 		   	     	 
				 		   	     	 
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