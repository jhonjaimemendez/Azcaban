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

final public class RotacionProductos extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpCriterios;
    private  JPanel jpVentas;
    
    //Se declaran los JTextField
    private  JTextField jtNumeroMasVendidos;
    private  JTextField jtNumeroMenosVendidos;

    
     //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
   
     //** Se declaran los JCheckBox
    private JRadioButton jrMasVendidos;
    private JRadioButton jrMenosVendidos;
   
    //** Se declaran los JTable
    private  JTable tablaVentasProducto;
    
    
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
   
   
    //JButton
   private JButton jbCerrar;
  
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Código","Descripcion","Cantidad","Total"};
    private Object [][] datos = new Object[16][3];
    
    //Vectores
    private Vector columnas;
    
    //Modelo de datos 
    private SortableTableModel dm;
   
    //** Booleanos
    private boolean mostrarListaAutomatica = true;
    
     
    //** Constructor General 
    public RotacionProductos(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Rotación de Productos","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;

      //** Se agregan los JPanel

      jpCriterios = getJPanel("Criterios De Busqueda",55, 45, 700, 65,14);

      jpVentas = getJPanel("Ventas Realizadas",60, 130, 700, 380,14);
      
      
       //Se instancia un objeto de tipo ButtonGroup para agrupar los JRadioButton
      ButtonGroup grupo = new ButtonGroup();
     
      jrMasVendidos = getJRadioButton("",30, 25, 20, 20,grupo);
   	  jrMasVendidos.setMnemonic('M');
   	  jrMasVendidos.setSelected(true);
   	  jrMasVendidos.addActionListener(this);
	  jpCriterios.add(jrMasVendidos);
	  
	  jrMenosVendidos = getJRadioButton("",280, 25, 20, 20,grupo);
   	  jrMenosVendidos.setMnemonic('e');
   	  jrMenosVendidos.addActionListener(this);
	  jpCriterios.add(jrMenosVendidos);
	  
	  //Se configuran los Jlabel
	  JLabel jlMas = getJLabel("Mas vendidos",80, 25, 80, 20);
      jpCriterios.add(jlMas);
      
	  JLabel jlMenos = getJLabel("Menos vendidos:",330, 25, 100, 20);
      jpCriterios.add(jlMenos);
      
	  
	  jtNumeroMasVendidos = getJTextField("10",50, 25, 25, 20,"Digíte el Número de productos","2");
      jtNumeroMasVendidos.setHorizontalAlignment(JTextField.CENTER);
      jtNumeroMasVendidos.addKeyListener(getValidarEntradaNumeroJTextField());
      jtNumeroMasVendidos.addFocusListener(this);
      jpCriterios.add(jtNumeroMasVendidos);
      
      
      jtNumeroMenosVendidos = getJTextField("10",300, 25, 25, 20,"Digíte el Número de productos","2");
      jtNumeroMenosVendidos.setHorizontalAlignment(JTextField.CENTER);
      jtNumeroMenosVendidos.addKeyListener(getValidarEntradaNumeroJTextField());
      jtNumeroMenosVendidos.addFocusListener(this);
      jpCriterios.add(jtNumeroMenosVendidos);
      
      jcPeriodos =  getJComboBox(450,25,120,20,"Periodos"); //Se crea un JComboBox
      jcPeriodos.addItem("Todos");
      jcPeriodos.addItem("Dia");
      jcPeriodos.addItem("Quincena");
      jcPeriodos.addItem("Mes");
      jcPeriodos.addItem("Trimestre");
      jcPeriodos.addActionListener(this);
      jpCriterios.add(jcPeriodos);
      
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
  
    
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaVentasProducto);
      scrollPane.setBounds(25, 40, 655, 306);
      jpVentas.add(scrollPane);
       
       
     
      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/InventarioProductosActivos.gif")).getImage());
    
     
      //** Se muestra el JFrame
      mostrarJFrame(); 
    
      columnas = new Vector();
      columnas.add("IdProducto");
      columnas.add("Descripcion");
      columnas.add("Cantidad");
      columnas.add("Total");
      
     
      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
      traerInformacion();
      
      jtNumeroMasVendidos.grabFocus();


    
    
    }
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		
		tablaVentasProducto.getColumnModel().getColumn(0).setPreferredWidth(79);
		tablaVentasProducto.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaVentasProducto.getColumnModel().getColumn(1).setPreferredWidth(343);
		tablaVentasProducto.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
		tablaVentasProducto.getColumnModel().getColumn(2).setPreferredWidth(32);
		tablaVentasProducto.getColumnModel().getColumn(2).setCellRenderer(getAlinearDerecha());
		tablaVentasProducto.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
	    tablaVentasProducto.getColumnModel().getColumn(3).setPreferredWidth(50);
	
			
		

	}
    

	
    //*********************** Metodo limpiar ************************

    private void limpiar() { 

      dm.setDataVector(datos,nombresColumnas);
      
	  jrMasVendidos.setSelected(true);
	  
    }
    
   
    
     
  //*****************************Metodo traerInformacion() ******************************************
    
    final private boolean traerInformacion() { 
        
        boolean resultadoBoolean = false;
        
        String sentenciaSQL = "";
        String orden = "";
        String numeroRegistro = "";
       
        if (jrMasVendidos.isSelected()) {
        
           orden = "desc ";  
           numeroRegistro = jtNumeroMasVendidos.getText();
         
        } else
        
           numeroRegistro = jtNumeroMenosVendidos.getText();
          
        
        if (jcPeriodos.getSelectedIndex() == 0)
                    
            sentenciaSQL    =   "Select  P.IdProducto,P.Descripcion,Sum(V1.Cantidad),Sum(V1.subtotal) "+
                                "From  VentasEncabezado V,VentasDetalle V1, Productos P "+ 
                                "Where V.NumeroVenta = V1.NumeroVenta and V.estado ='G' and V1.IdProducto = P.IdProducto "+
                                "Group by P.IdProducto, P.Descripcion "+
                                "Order by 3 "+ orden +
                                "Limit " + numeroRegistro ; 
        else                       
          
          if (jcPeriodos.getSelectedIndex() == 1)
                   
	           sentenciaSQL    =   "Select  P.IdProducto,P.Descripcion,Sum(V1.Cantidad),Sum(V1.subtotal) "+
	                                "From  VentasEncabezado V,VentasDetalle V1, Productos P "+ 
	                                "Where V.NumeroVenta = V1.NumeroVenta and V.estado ='G' and V1.IdProducto = P.IdProducto "+
	                                "        and Substr(V.Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "' "+
	                                "Group by P.IdProducto, P.Descripcion "+
	                                "Order by 3 "+ orden +
	                                "Limit " + numeroRegistro ; 
	                                
	          else                       
		          
		          if (jcPeriodos.getSelectedIndex() == 2) {
		              
		              String condicion = ""; 
        	                    
	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
	                    
	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
	                   
	                   else
	                        
	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
	                    
                   
			           sentenciaSQL    =    "Select  P.IdProducto,P.Descripcion,Sum(V1.Cantidad),Sum(V1.subtotal) "+
			                                "From  VentasEncabezado V,VentasDetalle V1, Productos P "+ 
			                                "Where V.NumeroVenta = V1.NumeroVenta and V.estado ='G' and V1.IdProducto = P.IdProducto "+
			                                "      and substr(V.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "' " +
							                "      and Substr(V.Fecha,1,10) " + condicion +
			                                "Group by P.IdProducto, P.Descripcion "+
			                                "Order by 3 "+ orden +
			                                "Limit " + numeroRegistro ;                                
                   
		                                 
		          } else                       
			          
			          if (jcPeriodos.getSelectedIndex() == 3) 
			              
			                
	                   
				           sentenciaSQL    =    "Select  P.IdProducto,P.Descripcion,Sum(V1.Cantidad),Sum(V1.subtotal) "+
				                                "From  VentasEncabezado V,VentasDetalle V1, Productos P "+ 
				                                "Where V.NumeroVenta = V1.NumeroVenta and V.estado ='G' and V1.IdProducto = P.IdProducto "+
				                                "                       and substr(V.Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "' " +
				                                "Group by P.IdProducto, P.Descripcion "+
				                                "Order by 3 "+ orden +
				                                "Limit " + numeroRegistro ;                                
	                   
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
		        	                      
		                   
					           sentenciaSQL    =    "Select  P.IdProducto,P.Descripcion,Sum(V1.Cantidad),Sum(V1.subtotal) "+
					                                "From  VentasEncabezado V,VentasDetalle V1, Productos P "+ 
					                                "Where V.NumeroVenta = V1.NumeroVenta and V.estado ='G' and V1.IdProducto = P.IdProducto "+
					                                "                       and substr(V.Fecha,1,7) between " + condicion +
													" Group by P.IdProducto, P.Descripcion "+
					                                "Order by 3 "+ orden +
					                                "Limit " + numeroRegistro ;                        
			                         
		                 }
	   
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado != null)	{ 

              
              Vector <Vector > filas = new Vector <Vector>();
              long cantidades = 0;
              long totales = 0;
		
		      while (resultado.next()) {
			     
			       Vector columnas = new Vector();
			    
			        columnas.add(resultado.getString(1));
			    	columnas.add(resultado.getString(2));
			    	columnas.add(getFormatoNumero(resultado.getString(3)));
			    	columnas.add(getFormatoNumero(resultado.getString(4)));
			    
			    	
			        filas.add(columnas);	
		    				
			  }
	           
	           
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 
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
                   
                   if (fuente == jrMasVendidos || fuente == jrMenosVendidos) {
                   	     
                   	   traerInformacion();
                   	
                   	
                   } else 
			 		   	if (fuente == jcPeriodos) {
			 		   		   
			 		   		   dm.setDataVector(datos,nombresColumnas);
                         
                               traerInformacion();
                             
                          } 
      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 
				       
		        
        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
        
         
        if (f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField"))
			          
			   ((JTextField)f.getComponent()).selectAll();
     
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

}