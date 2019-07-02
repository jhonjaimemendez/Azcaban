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


final public class ConsultaCaja extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpPeriodos;
    private  JPanel jpCajas;
    
    //Se delcaran los JTextField
    private  JTextField jtTotalCaja;
    
     //** JTabbedPane
    private JTabbedPane pestaña;
    
    
    //Se declaran los JButton
    private JButton jbFechaInicial;
    private JButton jbFechaFinal;
    
    
    //** Se declaran los JTable
    private  JTable tablaMovimientoCaja;
    
    
    //** Se declaran los JComboBox
    private JComboBox jcPeriodos;
     
   
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Tipo Operación","Usuario","Fecha","Hora","Valor","ValorCaja","Concepto"};
    private Object [][] datos = new Object[16][6];
    
    
    //Vectores
    private Vector columnas;
    private Vector columnasDetalle;

    //Modelo de datos 
    private SortableTableModel dm;
   
    //Objeto calendario de Java
    private Calendar calendar = Calendar.getInstance();
   
   
    
      //Calendario
    private Calendario calendarioInicial;
    private Calendario calendarioFinal;
    
     //String
    private String fechaInicial;
    
    //Long
    private Long totalCaja; 
  
   
    //** Constructor General 
    public ConsultaCaja(ConectarMySQL p_conectarMySQL,JFrame p_frame,Long totalCaja) {

      super("Movimientos Caja","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;

      this.totalCaja = totalCaja;  
       
      //** Se agregan los JPanel
      
      pestaña = new JTabbedPane();
      pestaña.setName("pestaña");
      pestaña.setBounds(5,45,775,490);
      
       
      jpPeriodos = getJPanel(0,0,100,100);
      pestaña.addTab("Periodo  ",jpPeriodos);
      
      jpCajas = getJPanel("Movimientos De Caja",10, 60, 755, 395,14);
      jpPeriodos.add(jpCajas);
      
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

     tablaMovimientoCaja = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaMovimientoCaja.addFocusListener(this);
  
    
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaMovimientoCaja);
      scrollPane.setBounds(10, 25, 735, 276);
      jpCajas.add(scrollPane);
       
      
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
      columnas.add("Tipo Operación");
      columnas.add("Usuario");
      columnas.add("Fecha");
      columnas.add("Hora");
      columnas.add("Valor");
      columnas.add("ValorCaja");
      
      columnas.add("Concepto");
      
       
       //Se agregan los JLabel
      JLabel jlTotalCaja  = getJLabel("Total Caja:",470, 340, 100, 20);
      jlTotalCaja.setHorizontalAlignment(JLabel.RIGHT);
      jpCajas.add(jlTotalCaja);
       
      jtTotalCaja  = getJTextField("Total Compradas",578, 340, 100, 20,"",false);
      jtTotalCaja.setHorizontalAlignment(JTextField.RIGHT);
      jpCajas.add(jtTotalCaja);
    
      
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

		
		tablaMovimientoCaja.getColumnModel().getColumn(0).setPreferredWidth(83);
		tablaMovimientoCaja.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
	    tablaMovimientoCaja.getColumnModel().getColumn(1).setPreferredWidth(91);
		tablaMovimientoCaja.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
	
		tablaMovimientoCaja.getColumnModel().getColumn(2).setPreferredWidth(39);
		tablaMovimientoCaja.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
	    
	    
	    tablaMovimientoCaja.getColumnModel().getColumn(3).setPreferredWidth(26);
	    tablaMovimientoCaja.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
	    
	    tablaMovimientoCaja.getColumnModel().getColumn(4).setPreferredWidth(35);
	    tablaMovimientoCaja.getColumnModel().getColumn(4).setCellRenderer(getAlinearDerecha());
		
		tablaMovimientoCaja.getColumnModel().getColumn(5).setPreferredWidth(56);
	    tablaMovimientoCaja.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
		
	    tablaMovimientoCaja.getColumnModel().getColumn(6).setPreferredWidth(185);
	    

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
        
        String sentenciaSQL = null;
        
        
           if (jcPeriodos.getSelectedIndex() == 0)
                
	                 sentenciaSQL    =    "Select Descripcion, IdUsuario,substr(Fecha,1,10),substr(Fecha,11),ValorMovimiento,ValorCaja,Concepto "+
                                          "From   Movimientoscaja M, Tipomovimientos P "+
                                          "Where  M.CodigoMovimiento = P.CodigoMovimiento "+
                                          "Order by NumeroTransaccion";
	                
		      
		      else                         
                 
                 if (jcPeriodos.getSelectedIndex() == 1)
                
	                 sentenciaSQL    =    "Select Descripcion, IdUsuario,substr(Fecha,1,10),substr(Fecha,11),ValorMovimiento,ValorCaja,Concepto "+
                                          "From   Movimientoscaja M, Tipomovimientos P "+
                                          "Where  M.CodigoMovimiento = P.CodigoMovimiento and Substr(Fecha,1,10) = '" + getObtenerFecha(conectarMySQL) + "' "+ 
                                          "Order by NumeroTransaccion";
	                
        	     else
        	     
        	         if (jcPeriodos.getSelectedIndex() == 2) {
        	                    
        	                   String condicion = ""; 
        	                    
        	                   if (Integer.parseInt(getObtenerDia(conectarMySQL)) > 15)
        	                    
        	                        condicion = " >= '"+ getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                   
        	                   else
        	                        
        	                        condicion = " < '"  + getObtenerFecha(conectarMySQL).substring(0,8) + "16'"; //se obtiene la fecha hasta el valor de la quincena
        	                     
        	                  
		                       
				                 sentenciaSQL    =    "Select Descripcion, IdUsuario,substr(Fecha,1,10),substr(Fecha,11),ValorMovimiento,ValorCaja,Concepto "+
                                                      "From   Movimientoscaja M, Tipomovimientos P "+
                                                      "Where  M.CodigoMovimiento = P.CodigoMovimiento "+
                                                      "                        and substr(Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'" +
										              "                       and Substr(Fecha,1,10) " + condicion +
										              "       Order by NumeroTransaccion";
	                
				     } else    
        	     
		        	         if (jcPeriodos.getSelectedIndex() == 3)
		                
						                 sentenciaSQL    =   "Select Descripcion, IdUsuario,substr(Fecha,1,10),substr(Fecha,11),ValorMovimiento,ValorCaja,Concepto "+
                                                             "From   Movimientoscaja M, Tipomovimientos P "+
                                                             "Where  M.CodigoMovimiento = P.CodigoMovimiento "+
                                                              "      and substr(Fecha,1,7) = '" + getObtenerFecha(conectarMySQL).substring(0,7) + "'"+
                                                              "       Order by NumeroTransaccion";
										                     
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
					        	                      
			        	                     
						                                 sentenciaSQL    =  "Select Descripcion, IdUsuario,substr(Fecha,1,10),substr(Fecha,11),ValorMovimiento,ValorCaja,Concepto "+
                                                                            "From   Movimientoscaja M, Tipomovimientos P "+
                                                                            "Where  M.CodigoMovimiento = P.CodigoMovimiento "+
                                                                            "      and substr(Fecha,1,7) between " + condicion +
										                                    "       Order by NumeroTransaccion";
													          
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
			    	columnas.add(getFormatoNumero(resultado.getString(5)));
			        columnas.add(getFormatoNumero(resultado.getLong(6)));
			        columnas.add(resultado.getString(7));
			        
			    	filas.add(columnas);	
		    				
			  }
	           
	          if (filas.size() > 0) {
	                       
			 	 dm.setDataVector(filas,columnas);
			 	 
			 	 jtTotalCaja.setText(getFormatoNumero(totalCaja));
			 	
			 	 
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
				 		   	     
	 		   	     if (fuente == jcPeriodos) {
	 		   	     	
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