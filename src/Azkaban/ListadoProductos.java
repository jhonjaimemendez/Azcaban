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
import javax.swing.ImageIcon;

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

final public class ListadoProductos extends CrearJFrame implements ActionListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel jpProductos;
    
    //Se delcaran los JTextField
    private  JTextField jtNumeroRegistros;
    
    //** Se declaran los JTable
    private  JTable tablaProductos;
    
    //** Se declaran los JLabel
    private JLabel jlNumeroRegistros;
    
   //Columnas y filas estaticas 
    private Object [] nombresColumnas ={"Código","Descripción"," Precio Minino","Precio Lista","Marca","Categoría"};
    private Object [][] datos = new Object[16][6];
    
  
    //Modelo de datos 
    private SortableTableModel dm;
    
    //Vectores
    private Vector columnas;
  
    //** Constructor General 
    public ListadoProductos(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Listado de Productos","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;


      //** Se agregan los JPanel

      jpProductos = getJPanel("Productos Registrados ",10, 60, 770, 450,14);

    
       //** Se declaran los JLabel

      JLabel jlNumeroRegistros = getJLabel("Número De Productos Registrados :",500, 420, 220, 20);
      jpProductos.add(jlNumeroRegistros);
      
      
      //** Se instancian los JTextField
      jtNumeroRegistros = getJTextField("Número de Factura de Venta",720, 420, 40, 20,"",false);
      jtNumeroRegistros.setHorizontalAlignment(JTextField.RIGHT);
      jpProductos.add(jtNumeroRegistros);

    
    
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

     tablaProductos = getJTable(dm); //Se instancia el JTable con el modelo de datos
     tablaProductos.addMouseListener(new MouseAdapter() {
     	
     	public void mouseClicked(MouseEvent m) {
     		
     		
     		if (m.getClickCount() == 2) {
     		
     		   new Producto(conectarMySQL,getJFrame(),tablaProductos.getValueAt(tablaProductos.getSelectedRow(),0).toString());
     		   
     		}   
     	}
     	
     });
     
     configurarColumnas();
      
      
     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaProductos);
      scrollPane.setBounds(10, 40, 750, 356);
      jpProductos.add(scrollPane);
       
      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/ListadoProductos.gif")).getImage());
    
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
 	         
 	           
 	        final String sentenciaSQL = "Select IdProducto,P.Descripcion,PrecioMininoVenta,PrecioListaVenta,C.Categoria,M.Marca "+
                                         "From Productos P, Categorias C, Marcas M "+
                                         "Where  P.IdMarca = M.IdMarca and P.IdCategoria = C.IdCategoria "+
                                         "Order by 2";
 	                                    
 	        
 	        
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     
			           // Se verifica si tiene datos 
			           if (resultado != null)	{ 
			 
			              Vector <Vector > filas = new Vector <Vector>();
					
					      while (resultado.next()) {
						     
						       Vector columnasTabla = new Vector();
						    
						    	columnasTabla.add(resultado.getString(1));
						    	columnasTabla.add(resultado.getString(2));
						    	columnasTabla.add(getFormatoNumero(resultado.getString(3)));
						    	columnasTabla.add(getFormatoNumero(resultado.getString(4)));
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
        
        tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(76);
        tablaProductos.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
        tablaProductos.getColumnModel().getColumn(1).setPreferredWidth(190);
        tablaProductos.getColumnModel().getColumn(2).setPreferredWidth(60);
	    tablaProductos.getColumnModel().getColumn(2).setCellRenderer(getAlinearDerecha());
	    tablaProductos.getColumnModel().getColumn(3).setPreferredWidth(47);
	    tablaProductos.getColumnModel().getColumn(3).setCellRenderer(getAlinearDerecha());
	    tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(89);
	    tablaProductos.getColumnModel().getColumn(4).setCellRenderer(getAlinearCentro());
	    tablaProductos.getColumnModel().getColumn(4).setPreferredWidth(87);
	    tablaProductos.getColumnModel().getColumn(5).setCellRenderer(getAlinearCentro());
	
	}
    
    
	
  
      
      //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

            
           if (fuente == Bsalir) {
             ocultarJFrame(); 

           } 
      }

    
    
     
}