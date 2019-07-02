/**
 * Clase: Festivos
 *
 * @version  1.0
 *
 * @since 13-09-2006
 *
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.EditableCellTableModel;
import com.JASoft.componentes.Calendario;


import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;


import java.awt.event.MouseEvent;

import java.sql.ResultSet;

import java.util.HashSet;

import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;


/*
  * Esta clase permite configurar los dias festivos de un año
  */
  
final class Festivos extends CrearJFrame implements FocusListener,ActionListener {
  	
  	//Referencia de conexion a la base de datos
	private ConectarMySQL conMySQL;
  	
  	// Casillas De Texto
  	private JTextField tAno;
  	private JTextField tDescripcion;
  	
  	//** Se declaran los JFormattedTextField
    private JFormattedTextField tFechaFestivo;
    
    
    // Tabla para mostrar los dias festivos
  	private JTable tbFestivos;
    
    //Boton para agregar y eliminar fechas
  	private JButton BagregarFec;
  	private JButton BeliminarFec;
  	private JButton jbCalendario;
    
    //Columnas y filas estaticas
 	private Object nomColumnas1[] = {"Fechas","Descripción"};
 	private Object datos1[][] = new Object[18][2];
      
     //Calendario
    private Calendario calendario;
   
    //Modelo de datos
 	private EditableCellTableModel modelo;
 	
 	//Constructor utilizado deste el menu principal
 	public Festivos()
 	{
 		super ("Festivos","Toolbar");
 		
 		JPanel panel = getJPanel("Festivos",40,60,730,280);
 	   
  	    // Se crea un JScrollPane
 		JScrollPane scroll = new JScrollPane(tbFestivos);
 		scroll.setBounds(20,30,690,200);
 		panel.add(scroll);
 		
 		
 		// Se agrega el panel al JFrame
 		getJFrame().add(panel);		
 	 		
 	} 
	
	
  	public Festivos(ConectarMySQL p_conMySQL,JFrame p_frame){ 
  	    super ("Festivos","Toolbar",p_frame);
  	    
 		
 		conMySQL = p_conMySQL;
  	    
  	    //Se configuran las etiquetas
  	    JLabel lAno = getJLabel("Año : ",40,60,40,20);
  	    
 		
 		JLabel lFecha = getJLabel("Fecha : ",40,100,80,20);
 		
 		JLabel lDescripcion = getJLabel("Descripción : ",240,100,80,20);
 		
 		String fechaActual = getObtenerFecha(conMySQL);
 		
 		//Se adiciona un JTextField
  	    tAno = getJTextField("Año",100,60,80,20,"2",false);
  	    tAno.setHorizontalAlignment(JLabel.CENTER);
 		tAno.setText(getObtenerAño(conMySQL));
  	    
  	    tDescripcion = getJTextField("",330,100,430,20,"Descripción del festivo","40");
  	    tDescripcion.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
  	    tDescripcion.addActionListener(this);
  	    tDescripcion.addFocusListener(this);
  	    tDescripcion.addKeyListener(getConvertirMayuscula());
  	    
  	    tFechaFestivo = getJFormattedTextField("",100,100,80,20,"Dígite la Fecha de la Factura","10");
 		tFechaFestivo.setHorizontalAlignment(0);
 	    tFechaFestivo.addFocusListener(this);
 	    tFechaFestivo.addActionListener(this);
 	    tFechaFestivo.selectAll();
 	    
 	    JPanel panel = getJPanel(" Festivos ",40,140,730,380);
 		
 		// Se configura el modelo de datos
 		modelo=new EditableCellTableModel(datos1,nomColumnas1);
 		
 	    // Se crea un JTable
 		tbFestivos = getJTable(modelo,true);
 		
  	    // Se crea un JScrollPane
 		JScrollPane scroll = new JScrollPane(tbFestivos);
 		scroll.setBounds(20,30,690,308);
 		panel.add(scroll);
 		
 		
 		// Se agrega el panel al JFrame
 		getJFrame().add(panel);
 		
			
 		//** Se agregan los JButton
        jbCalendario = getJButton("",185, 100, 20, 20,"Imagenes/Calendario.gif","Calendario");
        jbCalendario.setFocusable(false);
        jbCalendario.addActionListener(this);
        
 		BagregarFec = getJButton("Agregar Fecha",140,345,160,24,"Imagenes/Adicionar.gif","Agrega un feriado al sistema");
 		BagregarFec.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/AdicionarS.gif")));
 		BagregarFec.addActionListener(this);
 		panel.add(BagregarFec);
 		
 		
 		BeliminarFec = getJButton("Eliminar Fecha",420,345,160,24,"Imagenes/EliminarX.gif","Elimina un feriado del sistema");
 //		BeliminarFec.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/EliminarXS.gif")));
 		BeliminarFec.addActionListener(this);
 	 	panel.add(BeliminarFec);
 		
 		
 		getJFrame().add(panel);
 		
 		// Se configura el icono del frame
 		getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Festivos.gif")).getImage());
 
 	    // Se visualiza el frame
  	    mostrarJFrame();
  	    
  	    //Se obtiene el foco en el TextField festivos
  	    tFechaFestivo.grabFocus();
  	    
  	    configurarColumnas();
  	    
  	    // Se buscan los festivos adicionados
  	    buscarRegistro();
  	   
  	   
  	    // Se adicionan eventos a los botones de la Toolbar
 		Bguardar.addActionListener(this);
 		Bsalir.addActionListener(this);
 		Beliminar.addActionListener(this);
 		Blimpiar.setEnabled(false);
 		Bbuscar.setEnabled(false);
 		Bimprimir.setEnabled(false);
 		
  	    // Se configura para que se pueda navegar solo con Tab
        Set upKeys = new HashSet(1);
        upKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB,0));
        tFechaFestivo.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,upKeys);
        
         // oculta el internalFrame
 	    getJFrame().getContentPane().addMouseListener(new MouseAdapter() {
  	   	
  	   	public void mousePressed(MouseEvent e) {
  	   	    	
//  	   	    	internalFrameFechaFes.setVisible(false);
  	   	    	
  	   	} 	 
  	   	
  	   });
  	    
  	    
  	}
  	
 // *********************************************************************************************************
    
    private void guardar(String mensaje) {
 		
 		try {
 			
	 		 String condicion = "substr(FechaFes,1,4) = '"+tAno.getText()+"'"; 
	 		  
	 		 eliminarRegistro("Festivos",condicion,conMySQL,false);
	 		 
	 		 int numeroFilas = obtenerFilas(tbFestivos,0);
	 		 
	 		 String datos[] = new String[2];
	 		 
	 		 // Se guardan los valores de la tabla 		 
		  	 for (int i = 0; i < numeroFilas; i++) {
		  	     
		  	     datos[0] = "'" + tbFestivos.getValueAt(i,0) +"'";   
		  	     datos[1] = "'" + tbFestivos.getValueAt(i,1) +"'"; 
		  	     
		  	       guardar("Festivos",datos,conMySQL,false);
		  	      
		  	       if (i == numeroFilas - 1)
		  	           
		  	           Mensaje(mensaje,"Información",JOptionPane.INFORMATION_MESSAGE);
		  	       
		  	      
		  	 }  
 	    
 	    	//Se hacen permanentes los cambios
 	        conMySQL.commit();
 	         
	 	 } catch (Exception e) {
	 	 	
	 	 	conMySQL.rollback();
	 	 	
	 	 	Mensaje("Error al insertar Festivos" + e,"Error",JOptionPane.ERROR_MESSAGE);
	 	 	
	 	 }  	  	 	 
	  	 
 	}
 	
    
  
   //****************************************************************************************************************
    
    private void eliminarFila() {
 	  
 	  
		int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar este registro?","Confirmacin",
			 	                                   JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
			 	                                             
	    if (opcion==0) {                                       
				 	 	
	 		// Se elimina la fila seleccionada 
	 		modelo.removeRow(tbFestivos.getSelectedRow());
	 		guardar("Registro eliminado");
	 	}	  
	 		   
 	}
    
    //****************************************************************************************************************
    
    private boolean validarAgregarFila() {
   	
	   	 int fila = 0;
	   	 
	   	 boolean agregar = true;
	   	 
	   	   while ((fila <= tbFestivos.getRowCount() - 1) &&   tbFestivos.getValueAt(fila,0)!=null && agregar) {
	   	      
	   	      if (tbFestivos.getValueAt(fila,0).equals(tFechaFestivo.getText()))
	   	      
	   	        agregar = false;
	   	       
	   	      fila++;   
	   	   }     
	   	 
	   	 
	   	       
	   	return agregar;      
	   	  
   	
   }
   
    //****************************************************************************************************************
    private void agregarFila() {
        
        int numeroFila = obtenerFilas(tbFestivos,0);
        
        if (numeroFila >= tbFestivos.getRowCount()) {
        	      	
	      	Object nuevaFila[] = {"",""};
            modelo.addRow(nuevaFila); 
		                                
        	
        }
            
            
        
        
	        tbFestivos.setValueAt(tFechaFestivo.getText(),numeroFila,0);
	        tbFestivos.setValueAt(tDescripcion.getText(),numeroFila,1);
        
        	        
        limpiar();
         	
    	
    }
   
    //***************************************************************************************************************** 	
   	private void buscarRegistro() {
  		
      String sentenciaSQL = "Select * "+
	                        "From Festivos "+
	                        "Where substr(FechaFes,1,4) ='"+ tAno.getText() +"'";
	  
	  try {
	   
	   // Se llama el metodo buscarRegistro de la clase padre
	   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
	   
	  
	                                       
 	    // Se verifica si tiene datos
 	    if (resultado!=null)	{
 	   
		     if (resultado.next()) {
		         	
		          resultado.last();
				           
				  String datos[][] = new String[resultado.getRow()][2];
				           
				  resultado.beforeFirst();
		 	   	  
		 	   	  int i = 0;
		 	   	   
		 	   	  while (resultado.next()) {
			 	        
			 	     datos[i][0] = resultado.getString(1).replace('-','/');
			 	     
			 	     if(resultado.getString(2)==null || resultado.getString(2).equals("null"))
			 	     {
			 	     
			 	        datos[i][1]="";
			 	       
			 	     }
			 	     else
			 	     {
			 	        datos[i][1] = resultado.getString(2);
			 	        
			 	     }
			 	       
			 	     i++;	     
			      }  
			      
			      modelo.setDataVector(datos,nomColumnas1);
			       
			  }
	   }
	   
	 } catch (Exception e) {
	 	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
	 }
	     	      	     
  	}
  	
  //*********************************************************************************************************************

  	protected void aplicar(EditableCellTableModel modelo2) {
 	 		
 	 	modelo=modelo2;

 	}
  	
  
 	//*********************************************************************************************************************
 	private void limpiar() {
 		 
 		 tFechaFestivo.setText("");
 		 tDescripcion.setText("");
 		 tFechaFestivo.grabFocus();
 	}
 	
 	//*******************************************************************************************************************
   	
    private void configurarColumnas() {

 	    // Se configuran los tamaos de las columnas
 	    tbFestivos.getColumnModel().getColumn(0).setPreferredWidth(20);
 	    tbFestivos.getColumnModel().getColumn(0).setCellRenderer(getAlinearCentro());
 		tbFestivos.getColumnModel().getColumn(1).setPreferredWidth(440);

 	
 	}
 	//***************************************************************************************************************** 	
   public void actionPerformed(ActionEvent act) {
 		
 		Object fuente = act.getSource();
 		
 		// Se verifica el componente que genero la acccion
 	
  	     if (fuente==Bsalir) {
  	     
  	     	 ocultarJFrame();
          
          } else 
            
            if (fuente==BagregarFec) {
              
              if (validarAgregarFila())   
                
                agregarFila();
                
              else {
                
                limpiar();
                Mensaje("Fecha ya ha sido agregada","Información",2);  
                
              }  
      	   }else 	
      	    
      	     if (fuente==Beliminar) {
      	   
      	       eliminarFila();
      	   
      	     } else
      	   
      	        if (fuente == Bguardar)
      	   
      	          guardar("Registro Insertado");
      	   
      	        else
      	   
      	             if (fuente==Beliminar) 
      	   
      	                eliminarFila();
      	   
      	             else
      	   
      	                if(fuente==BeliminarFec)
      	                 
      	                   eliminarFila();
      	                 
      	                else
      	                     	
	                      	 if (fuente == jbCalendario) {
	                      	    
	                      	    if (calendario == null)
	                      	    	
	                      	 	    calendario = new Calendario(tFechaFestivo,getJFrame(),490,170);
	                      	 	    
	                      	 	else
	                      	 	     
	                      	 	     calendario.mostrarCalendario(true);    
	                      	 	
	                      	 } else
	                      	    
	                      	    if (fuente == tDescripcion) {
	                      	    	
	                      	    	if (!tAno.getText().isEmpty()) {
	                      	    		
	                      	    		BagregarFec.doClick();
	                      	    		
	                      	    	}
	                      	    	
	                      	    	
	                      	    }  
	     	
         
	 	         
 	}  
 	

  	
  	//*********************************************************************************************************************
  	
  	public void focusGained(FocusEvent F) {
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
 	}
 	
 	//*********************************************************************************************************************
  	
 	public void focusLost(FocusEvent F) {
     
       if (F.getSource() == tFechaFestivo && !tFechaFestivo.getText().equals("    /  /  ")) {
       	
       	   if (!esFecha(tFechaFestivo.getText())) {
		              	  
              	   Mensaje("Fecha " + tFechaFestivo.getText()  +" Invalida " ,"Reporte de error",0);
            	   tFechaFestivo.setText("");
            	   tFechaFestivo.grabFocus();
              	
              	
            } 
                
 	   
 	   }	    		
 	
 	    // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());
 	}
 	
 	 
}