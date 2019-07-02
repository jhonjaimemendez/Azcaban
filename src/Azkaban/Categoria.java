package com.JASoft.azkaban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.KeyboardFocusManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;

import java.util.Vector;

import com.JASoft.componentes.ConectarMySQL;

import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;

final public class Categoria extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Se declaran los JPanel
    private  JPanel panelDatosContacto;

   //** Se declaran los JTextField
    private  JTextField jtCodigo;
    private  JTextField jtDescripcion;

    //** Se declaran los JButton
    private  JButton jbAceptar;
    private  JButton jbCancelar;
    private  JButton jbAgregar;
    private  JButton jbEditar;
    private  JButton jbEliminar;

    //** Se declaran los JTable
    private  JTable tablaCategorias;

   //Columnas y filas estaticas 
    private Object [] nombresColumnas  = {"Codigo","Categoria"};
    private Object [][] datos = new Object[18][2];

    //Modelo de datos 
    private SortableTableModel dm;
  
     //JDialog
    private JDialog dialogCategorias;
    
    //Vectores
    private Vector datosTablaCategorias;
    
    //**Se declaran los JFrame
    private JFrame framePadre; // Utilizado para el frame padre en la tabla Producto
  
     //** int
    private int numeroFilasJTableInicial;
  
    
     //** Constructor General 
    public Categoria(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Listado de Categorías","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
      iniciarGUI();

    }
    
    
    public Categoria(ConectarMySQL p_conectarMySQL,JFrame p_frame,String codigo) {

      super("Listado de Categorías","Toolbar",p_frame,"dummy");

      conectarMySQL = p_conectarMySQL;
      
      framePadre = p_frame;
      
      iniciarGUI();
      
      configurarJDialogCategorias();  
      
      jtCodigo.setText(codigo); //Se coloca el foco en el primer componente Focusable
      
      dialogCategorias.setVisible(true);
      
      jtDescripcion.grabFocus();
      

    }
    
    
    
    
    private void iniciarGUI() {
    
      //** Se agregan los JPanel

      panelDatosContacto = getJPanel("Categorías",60, 80, 670, 435,14);

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

     tablaCategorias = getJTable(dm); //Se instancia el JTable con el modelo de datos
     
     tablaCategorias.addMouseListener(new MouseAdapter() {   //Se agrega un evento a la tabla para que puede editar la fila con doble click
 		   
 		   public void mouseClicked(MouseEvent m){
 		    
 		     if (m.getClickCount() ==2 ) {
 		     	
 		     	  jbEditar.doClick();
 		     	  
	 	     }
	 	   }
	 	}); 
	 	

     //** Se configura un scroll para el JTable 

      JScrollPane scrollPane = new JScrollPane(tablaCategorias);
      scrollPane.setBounds(25, 35, 620, 308);
      panelDatosContacto.add(scrollPane);
      
      configurarColumnas();

      //** Se agregan los JButton

      jbAgregar = getJButton("Agregar",110, 370, 125, 25,"Imagenes/Adicionar.gif","");
      jbAgregar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/AdicionarS.gif")));
      jbAgregar.addActionListener(this);
      panelDatosContacto.add(jbAgregar);

      jbEditar = getJButton("Editar",275, 370, 125, 25,"Imagenes/Editar.gif","");
      jbEditar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/EditarS.gif")));
      jbEditar.addActionListener(this);
      panelDatosContacto.add(jbEditar);

      jbEliminar = getJButton("Eliminar",440, 370, 125, 25,"Imagenes/Eliminar.gif","");
      jbEliminar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/EliminarS.gif")));
      jbEliminar.addActionListener(this);
      panelDatosContacto.add(jbEliminar);

      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/InventarioCategorias.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame();
      
      buscarRegistro(); //se buscan las categorias almcenadas 
      
      numeroFilasJTableInicial = obtenerFilas(tablaCategorias,0); //Se obtiene el numero de filas de las marcas existente hasta el momento

      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);

    }
     
    //********************* configurarJdialogCategorias()****************************************
    
    private void configurarJDialogCategorias() {
    	
    	
    	if (dialogCategorias == null) {
    		
    		  dialogCategorias = new JDialog(getJFrame(),"Categorias",true);
		  	  dialogCategorias.setLayout(null);
		  	  dialogCategorias.setSize(400,170);
		  	  dialogCategorias.setLocationRelativeTo(null);
		  	  dialogCategorias.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeysFrame());
	  	  
		  	  
		  	  
		      
		       //** Se declaran los JLabel

		      JLabel jlCodigo = getJLabel("Código",25, 20, 65, 15);
		      dialogCategorias.add(jlCodigo);
		
		      JLabel jlDescripción = getJLabel("Descripción",85, 20, 95, 15);
		      dialogCategorias.add(jlDescripción);
		
		
		      //** Se instancian los JSeparator
		
		      JSeparator jSeparator1 = new JSeparator();
		      jSeparator1.setBounds(40, 75, 310, 2);
		      jSeparator1.setForeground(Color.lightGray);
		
		      //** Se agregan los JTextField
		
		      jtCodigo = getJTextField("",25, 35, 50, 20,"Digíte el Código de la Marca","4");
		      jtCodigo.addKeyListener(getConvertirMayuscula());
		      jtCodigo.addFocusListener(this);
		      dialogCategorias.add(jtCodigo);
		
		      jtDescripcion = getJTextField("",85, 35, 280, 20,"Digíte la Descripción de Marca","40");
		      jtDescripcion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
  	 	      jtDescripcion.addKeyListener(getConvertirMayuscula());
		      jtDescripcion.addActionListener(this);
		      jtDescripcion.addFocusListener(this);
		      dialogCategorias.add(jtDescripcion);
		       
		      //** Se agregan los JButton
		
		      jbAceptar = getJButton("Aceptar",60, 90, 125, 25,"","");
		      jbAceptar.addActionListener(this);
		      dialogCategorias.add(jbAceptar);
		
		      jbCancelar = getJButton("Cancelar",210, 90, 125, 23,"","");
		      jbCancelar.addActionListener(this);
		      dialogCategorias.add(jbCancelar);
		
		  	  
    	} else {
    		
    		  jtCodigo.setText("");
		      jtDescripcion.setText("");
			  
    		
    	}
    	
    	
    	
    	
    }
    
    //********************* agregarFilaJTable()****************************************
     
     private void agregarFilaJTable() {
         
         
         int fila = obtenerFilas(tablaCategorias,0);
         
         if (fila < tablaCategorias.getRowCount()) {
          
           tablaCategorias.setValueAt(jtCodigo.getText(),fila,0);
           tablaCategorias.setValueAt(jtDescripcion.getText(),fila,1); 
           
        } else
          
             dm.addRow(new Object[]{jtCodigo.getText(),jtDescripcion.getText()});

       
      	
     }
     
	//**************************** Metodo configurarColumnas() ************************

	private void configurarColumnas() {

		tablaCategorias.getColumnModel().getColumn(1).setPreferredWidth(330);

	}

    //*********************** Metodo limpiar ************************

    private void limpiar() { 
        
        dm.setDataVector(datos,nombresColumnas);
        buscarRegistro();

    }

   //*********************** Metodo buscarRegistro ************************

    private void buscarRegistro() { 


        final String sentenciaSQL = "Select * "+
                                    "From   Categorias "+
                                    "Order by 1";

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 

              int i = 0;
              
              while (resultado.next()) { 
                 
                 if (obtenerFilas(tablaCategorias,0) >= tablaCategorias.getRowCount()) 
          
                    dm.addRow(new Object[1][2]);
      
      
 	   	     	 tablaCategorias.setValueAt(resultado.getString(1),i,0);
 	   	     	 tablaCategorias.setValueAt(resultado.getString(2),i,1);
                 
                 i++;
              } 
             
           }
           
           datosTablaCategorias = new Vector(); //Se agregan los elementos en un nuevo vector
                                               //para guardar los datos de los Vectores ,
                                               // mas no las refencias
                                               
          
          for (int i = 0; i < dm.getDataVector().size(); i++) {
          	
          	  Vector columna = (Vector)dm.getDataVector().elementAt(i);
          	
          	  datosTablaCategorias.add(columna.clone());
          	
          }                                      
           
          

        } catch (Exception e) {
        	
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //*********************** Metodo guardar ************************

    private void guardar(String datos[]) throws Exception { 
         
        guardar("Categorias",datos,conectarMySQL,false); 
       

    }

    
      //**************************** Metodo actualizar ************************

      private void actualizar(String codigo,String categoria) throws Exception { 
        
            String condicion = "idCategoria = " + codigo;
            
            String[] datos = { "Categoria = "+  categoria };

            actualizar("Categorias",datos,condicion,conectarMySQL,false);


      }

      //**************************** Metodo eliminar ************************

      private void eliminar() throws Exception  { 


		 // Se especifica la condición de borrado 

		 String condicion = "idCategoria = '" + tablaCategorias.getValueAt(tablaCategorias.getSelectedRow(),0) + "'";

		 eliminarRegistro("Categorias",condicion,conectarMySQL,false); 


	  }

      
      //**************************** Metodo esActualizable ************************

      private Boolean esActualizable(String idCategoria, String categoria)  { 

           Boolean sw = new Boolean(false);
           
           int i = 0;
           
           
           while (sw != null && !sw && (i < datosTablaCategorias.size())) {
           
           	     Vector columna = (Vector) datosTablaCategorias.elementAt(i);
           
           	     if (columna.elementAt(0) != null) {
           	    	
           	     	if (columna.elementAt(0).equals(idCategoria) && columna.elementAt(1).equals(categoria)) 
           	     	
           	     	  sw = null;
           	     	  
           	     	else   
           	     	  
           	     	  if (columna.elementAt(0).equals(idCategoria) && !columna.elementAt(1).equals(categoria))
           	     	
           	     	    sw = true;
           	     } 
           	
           	    i++;     
           	
           } 
           
           
           return sw;               

	  }
	  
	  
	  //**************************** Metodo esBorrable ************************

      private boolean esBorrable(String idCategoria)  { 

           boolean sw = false;
           
           int i = 0;
           
           
           while (!sw && (i < datosTablaCategorias.size())) {
           
           	     Vector columna = (Vector) datosTablaCategorias.elementAt(i);
           
           	     if (columna.elementAt(0) != null && columna.elementAt(0) != null) {
           	    	
           	     	if (columna.elementAt(0).equals(idCategoria)) 
           	     	
           	     	  sw = true;
           	     
           	     } else
           	       
           	        i =  datosTablaCategorias.size();
           	
           	    
           	     i++;     
           	
           } 
           
           
           return sw;               

	  }

     //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

         // Se verifica el componente que genero la acccion

         if (fuente == Blimpiar) {

            limpiar();

         } else 
            if (fuente == Bguardar) {

                     try {

                          String mensaje = "Información de Categoria registrada correctamente";
                          
                          int numeroFilas = obtenerFilas(tablaCategorias,0);


                          for (int i = 0; i < numeroFilas; i++) {
                          
                            Boolean actualizable = esActualizable(tablaCategorias.getValueAt(i,0).toString(),tablaCategorias.getValueAt(i,1).toString());
                          
                            if (actualizable != null) {
                            
                          
	                           if ( actualizable )
	                             
	                              actualizar("'" + tablaCategorias.getValueAt(i,0) + "'", "'" + tablaCategorias.getValueAt(i,1) + "'");
	                            
	                            else   
	
	                               guardar(new String[]{"'" + tablaCategorias.getValueAt(i,0) + "'", "'" + tablaCategorias.getValueAt(i,1) + "'"});
	                        
	                        }        
                          
                          }


                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 
                          

                          Mensaje(mensaje,"Información",1);

                          // Se verifica quien hizo el llamado a la forma de Producto
				           if (framePadre != null && framePadre.getName().equals("Producto")) {
				           	
				           	    ocultarJFrame();
				           	
				           } else
				               
			                 limpiar(); //Se limpia la forma 
                         
 

                       } catch (Exception e) {

                           conectarMySQL.rollback();

                           Mensaje("Error al Insertar Categoria" +e,"Error",0); 

				      }
                  

            } else 

                if (fuente == Beliminar) { 

                   try {
              
                         int fila = tablaCategorias.getSelectedRow();
                         	       	   
             	         if (fila != -1 && tablaCategorias.getValueAt(fila,0) != null) {
             	       	   
                 	       	   int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar esta Categoria?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                                if (opcion == 0) {
                                       
                                       if (esBorrable(tablaCategorias.getValueAt(tablaCategorias.getSelectedRow(),0).toString())) {
         
					                       eliminar(); 
					
					                       conectarMySQL.commit();   //Se registra los cambios en la base de dato
					                       
								        }
								        
								        dm.removeRow(fila); // Se borra cuando el numero de filas es mayor a las que se muestra inicialmente
                             	       	   
                             	       	if (tablaCategorias.getRowCount() < 18) 
                             	       	    
                             	       	      dm.addRow(new Object[]{"",""}); //Se agrega una fila si existen menos de las que salen por defecto
                             	       	      
                             	       	    
                         	            }
                         	            
                         	       	
                           } else
                                      
                               Mensaje("Debe seleccionar la fila a eliminar","Información",2);
              
                     
                   } catch (Exception e) {

                       conectarMySQL.rollback();

                       Mensaje("No se puede elimina la categoria por que existe un producto que la refenrencia" + e,"Error",JOptionPane.ERROR_MESSAGE); 

                   }

                }  else 

                    if (fuente == Bbuscar) { 

                       buscarRegistro();

                    } else 

                       if (fuente == Bsalir) {
                                 
                         int numeroFilasJTableFinal = obtenerFilas(tablaCategorias,0);
                         
                         if (numeroFilasJTableFinal != numeroFilasJTableInicial) {
                         	
                         	    int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Existen cambios que no ha registrado \n Desea alamcenarlos ?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                                if (opcion == 0) {
                                  
                                   Bguardar.doClick();
                                        
                                }
                         	
                         }
                         
                         
                         ocultarJFrame(); 

                       }  else
                          
                          if (fuente == jbAgregar) {

                              configurarJDialogCategorias();  
                              
                              jtCodigo.grabFocus();    //Se coloca el foco en el primer componente Focusable
                              
                              dialogCategorias.setVisible(true);

                          } else
                           
                            if (fuente == jbCancelar) {
                                
                               jtCodigo.setFocusable(true);
                               jtCodigo.setEditable(true);
                               dialogCategorias.setVisible(false); 

                            } else
                           
                               if (fuente == jbAceptar) {
                                  
                                  if (!jtCodigo.getText().isEmpty()) {
                                  
	                                     if  (!jtDescripcion.getText().isEmpty()) {
	                                     
	                                       if (jtCodigo.isEditable()) 
	                                       
		                                     agregarFilaJTable();
		                                   
		                                    else {
		                                      
		                                      int fila = tablaCategorias.getSelectedRow();
		                                      tablaCategorias.setValueAt(jtDescripcion.getText(),fila,1);
		                                      jtCodigo.setFocusable(true);
                                 	   	      jtCodigo.setEditable(true);
                                 	
		                                     
		                                    
		                                    }   
		                                   
		                                    dialogCategorias.setVisible(false); 
		                                    jbAceptar.grabFocus();
		                                    
		                                 } else {
		                                 	
		                                 	Mensaje("Se debe digitar la descripción de la categoria","Información",2);
		                                 	jtCodigo.grabFocus();
		                                 
		                                 }
		                         
		                         } else {
	                                 	
	                                 	Mensaje("Se debe digitar el codigo de la categoria","Información",2);
	                                 	jtCodigo.grabFocus();
	                             }
	                                         

                              } else
                              
                                 if (fuente == jbEditar) { 
                                 
                                 	int fila = tablaCategorias.getSelectedRow();
                                 
                                 	if (fila != -1 && tablaCategorias.getValueAt(fila,0) != null) {
                                 		
                                 		if ( jtCodigo == null )
                                 		
                                 		  configurarJDialogCategorias();  //Se instancia el JDialog y se configura
                                 		  
                                 		
                                 		jtCodigo.setText(tablaCategorias.getValueAt(fila,0).toString());
                                 		
                                 		jtCodigo.setFocusable(false);
                                 		jtCodigo.setEditable(false);
                                 		
                                 		
                                 		jtDescripcion.setText(tablaCategorias.getValueAt(fila,1).toString());
                                 	    jtDescripcion.selectAll();
                                 		jtDescripcion.grabFocus();
                                 		
                                 		dialogCategorias.setVisible(true); 
                                 		
                                 	} else {
                                 	
                                 	    Mensaje("Debe seleccionar la fila a editar","Información",2);
                                 	    jbAgregar.grabFocus();
                                 	    
                                 	}
                                 	
                                 } else
                                    
                                    if (fuente == jtDescripcion) {
                                    	
                                    	jbAceptar.doClick();
                                    	
                                    } else
                                    
	                                    if (fuente == jbEliminar) {
	                                
	                                       Beliminar.doClick();
	                                    
                                 	    }

      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 

       // se coloca el atributo visual por defecto
       f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());

     }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
        
        if (f.getComponent() == jtCodigo) {
        	
        	     int fila = obtenerFilas(tablaCategorias,0);
         
		         boolean sw = false;
		         
		         int i = 0;
		         
		         //Se verifica que el codigo de la categoria no se encuentre
		         
		         while (!sw && i < fila) {
		           
		           Vector columna = (Vector) dm.getDataVector().elementAt(i);
		           
		           if (columna.elementAt(0).equals(jtCodigo.getText()))
		           
		             sw = true;
		             
		           else
		             
		             i++;  
		         
		         }  
		          
		          
		         if (sw) {
		        
                    Mensaje("Codigo de categoria "+ jtCodigo.getText() +" ya registrado","Información",2);
                    jtCodigo.setText("");
                    jtCodigo.grabFocus();
                    
                    
                 }   
        		         	  
		         
         
         }
         
         

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

      
}