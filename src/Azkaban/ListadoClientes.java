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

final public class ListadoClientes extends CrearJFrame implements ActionListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpClientes;
    
    //Se delcaran los JTextField
    private  JTextField jtNumeroRegistros;
    
    //** Se declaran los JTable
    private  JTable tablaClientes;
    
    //** Se declaran los JLabel
    private JLabel jlNumeroRegistros;
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Tipo","Número","Nombres","Dirección","Teléfono","Celular"};
    private Object [][] datos = new Object[16][6];
    
  
    //Modelo de datos 
    private SortableTableModel dm;
    
    //Vectores
    private Vector columnas;
  
    //** Constructor General 
    public ListadoClientes(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Listado de Clientes","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;


      //** Se agregan los JPanel

      jpClientes = getJPanel("Clientes Registrados ",10, 60, 770, 450,14);

    
      //** Se declaran los JLabel

      JLabel jlNumeroRegistros = getJLabel("Numero De Clientes Registrados :",500, 420, 220, 20);
      jpClientes.add(jlNumeroRegistros);
      
      
      //** Se instancian los JTextField
      jtNumeroRegistros = getJTextField("Número de Factura de Venta",720, 420, 40, 20,"",false);
      jtNumeroRegistros.setHorizontalAlignment(JTextField.RIGHT);
      jpClientes.add(jtNumeroRegistros);

    
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

     tablaClientes = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaClientes.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		
     		if (m.getClickCount() == 2) {
     			
     		   new Cliente(conectarMySQL,getJFrame(),tablaClientes.getValueAt(tablaClientes.getSelectedRow(),0).toString(),tablaClientes.getValueAt(tablaClientes.getSelectedRow(),1).toString());
     		   
     		}   
     	}
     	
     });
     
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaClientes);
      scrollPane.setBounds(10, 40, 750, 362);
      jpClientes.add(scrollPane);
       
      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/ListadoClientes.gif")).getImage());
    
      mostrarJFrame();
      
      columnas = new Vector();
      columnas.add("Tipo");
      columnas.add("Número ");
      columnas.add("Nombres");
      columnas.add("Dirección");
      columnas.add("Teléfono");
      columnas.add("Celular");
    
    
      traerInformacion();
     
      
      Bsalir.addActionListener(this);
    }
    
     //******************************** Metodo traerInformacion()  ***************************************

    private void traerInformacion() {
 	         
 	         
 	         final String sentenciaSQL = "Select TipoId,idCliente,Concat(Nombre,Concat(' ',Apellido)),Direccion,Telefono,Celular "+
 	                                     "From Clientes ";
 	                                     
 	       
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     
			           // Se verifica si tiene datos 
			           if (resultado != null)	{ 
			 
			              Vector <Vector > filas = new Vector <Vector>();
					
					      while (resultado.next()) {
						     
						       Vector columnasTabla = new Vector();
						        
						        String tipoId = resultado.getString(1);
						        
						        
						        
						        if (tipoId.equals("C"))
						            
						            tipoId = "Cédula";
						            
						        else
						        
						          if (tipoId.equals("C"))
						            
						             tipoId = "Tarjeta";
						          
						          else
						          
						            tipoId = "Pasaporte";
						             
						            
						            
						    	columnasTabla.add(tipoId);
						    	columnasTabla.add(resultado.getString(2));
						    	columnasTabla.add(resultado.getString(3));
						    	columnasTabla.add(resultado.getString(4));
						    	columnasTabla.add(resultado.getString(5));
						    	columnasTabla.add(resultado.getString(6));
						    	
						    	filas.add(columnasTabla);	
					    				
						  }
				            
				      	 dm.setDataVector(filas,columnas);
				      	 jtNumeroRegistros.setText(String.valueOf(filas.size()));
					 	 
                      }
                     
	 	     }  catch (Exception e) {
	 	                          
	 	          Mensaje("No existe ningun registro en la tabla Productos"+e,"Reporte de Error", 0);        
	 	     
	 	     }
	 	                 

     
 	  }

	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {


		tablaClientes.getColumnModel().getColumn(0).setPreferredWidth(30);
		tablaClientes.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
		tablaClientes.getColumnModel().getColumn(1).setPreferredWidth(62);
		tablaClientes.getColumnModel().getColumn(1).setCellRenderer(getAlinearIzquierda());
		tablaClientes.getColumnModel().getColumn(2).setPreferredWidth(186);
		tablaClientes.getColumnModel().getColumn(2).setCellRenderer(getAlinearIzquierda());
		tablaClientes.getColumnModel().getColumn(3).setPreferredWidth(211);
		tablaClientes.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
		tablaClientes.getColumnModel().getColumn(4).setPreferredWidth(50);
		tablaClientes.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
		tablaClientes.getColumnModel().getColumn(5).setPreferredWidth(49);
		tablaClientes.getColumnModel().getColumn(5).setCellRenderer(getAlinearDerecha());
		

	}
    
    
	
  
      
      //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

            
           if (fuente == Bsalir) {

             ocultarJFrame(); 

           } 
      }

    
    
     
}