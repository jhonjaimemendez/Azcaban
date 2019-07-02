/**
 * Clase: Auditorias
 * 
 * @version  1.0
 * 
 * @since 19-10-2005
 * 
 * @autor Ing. Ing. Jhon Mendez
 *
 * Copyrigth: JASoft
 */

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.Calendario;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.sql.DatabaseMetaData;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


final class Auditorias extends CrearJFrame implements ActionListener,FocusListener {
  	
  	//Referencia de conexion a la base de datos
    private ConectarMySQL conectarMySQL;
    
    //Casillas de texto
    private JFormattedTextField jtFechaInicial;
  	private JFormattedTextField jtFechaFinal;
  	private JTextField jtNumeroTramites;
  	
    //ComboBox para buscar informacion de las tablas y el tipo de transaccion
    private JComboBox CTablas;
    private JComboBox CTipoTransaccion;
    
    //JCheckBox
    private JCheckBox CkTabla;
    private JCheckBox CkTipo;
    private JCheckBox CkRango;
    
    //Botones
    private JButton BMostrarCal;
    private JButton BMostrarCal1;
    
    
    //Columnas y filas estaticas
  	private Object nomColumnas1[] = {"Usuario","Módulo","Fecha","Hora","Terminal","Tabla","Tipo Sentencia","Sentencia"};
  	private Object datos1[][] = new Object[17][8]; 
  	
  	
    //Vectores para el nombre de columnas y datos
  	private Vector < String > nombresColumnas = new Vector < String >();
  	private Vector < Vector > datos = new Vector < Vector >();
  	
  	//Modelo de datos
  	private SortableTableModel modelo;
  	
    //JTable que muestra informacion de las auditorias
    private JTable TAuditorias;
    	
    //String para especificar una fecha inicial y la actual
    private String fechaActual;
    private String fechaInicial;
    
     //Calendario
    private Calendario calendarioInicial;
    private Calendario calendarioFinal;
    
    
    //Objeto calendario de Java
    private Calendar calendar = Calendar.getInstance();
    
    //Formato de fecha
    private SimpleDateFormat formato ;
    
  
    //Constructor utilizado deste el menu principal	 
    public Auditorias(ConectarMySQL p_conMySQL,JFrame p_frame){ 
    
  	    super ("Auditoria De Transacciones En La Base De Datos","Toolbar",p_frame);
  	    
 		conectarMySQL = p_conMySQL;
  	    
  	    //Se agregan los datos a las columnas
  	    nombresColumnas.addElement("Usuario");
  	    nombresColumnas.addElement("Módulo");
  	    nombresColumnas.addElement("Fecha");
  	    nombresColumnas.addElement("Hora");
  	    nombresColumnas.addElement("Terminal");
  	    nombresColumnas.addElement("Tabla");
  	    nombresColumnas.addElement("Tipo Sen.");
  	    nombresColumnas.addElement("Sentencia");
  	    
  	    // Se crean los objetos para obtener la fecha del sistema
  	    fechaInicial = getObtenerFecha(conectarMySQL);
  	    
  	    calendar.set(Integer.parseInt(fechaInicial.substring(0,4)),Integer.parseInt(fechaInicial.substring(5,7))-1,Integer.parseInt(fechaInicial.substring(8,10)));
	    calendar.add(Calendar.DAY_OF_MONTH, -15);
 	                           
 	    formato = new SimpleDateFormat("yyyy/MM/dd");
 	    fechaActual = fechaInicial;
 	    fechaInicial = formato.format(calendar.getTime());
  	    
  	   
  	       //Se configuran las etiquetas
  	    JLabel LCriterio1 = getJLabel("Fecha Inicial:",300,120,100,20);
  	    
  	    JLabel LCriterio2 = getJLabel("Fecha Final:",500,120,100,20);
  	     		
 		JLabel LNumTramites = getJLabel ("Número De Transacciones Generadas :",440,340,380,20);
  	    
  	    
  	    // se configuran los JCheckBox
  	    CkTabla = new JCheckBox(" Tablas :",true);
  	    CkTabla.setBounds(150,60,100,20);
  	    CkTabla.addActionListener(this);
  	    getJFrame().add(CkTabla);
  	    
  	    CkTipo = new JCheckBox(" Tipo de Trámite:");
  	    CkTipo.setBounds(150,90,150,20);
  	    CkTipo.addActionListener(this);
  	    getJFrame().add(CkTipo);
  	    
  	    CkRango = new JCheckBox(" Rango de Fechas:");
  	    CkRango.setBounds(150,120,150,20);
  	    CkRango.addActionListener(this);
  	    getJFrame().add(CkRango);
  	    
  	    // Se configuran los combobox
  	  	CTablas = new JComboBox();
 		CTablas.setBounds(300,60,190,20);
 		CTablas.addFocusListener(this);
 	   	CTablas.addActionListener(this);
 	    CTablas.setMaximumRowCount(17);
 		getJFrame().add(CTablas);  
 		 
 		//Se buscan los nombres de tablas
  	    buscarNombresTablas();
  	     	
  	     
  	    CTipoTransaccion = new JComboBox();
 		CTipoTransaccion.setBounds(300,90,190,20);
 		CTipoTransaccion.addFocusListener(this);
 	  	CTipoTransaccion.addActionListener(this);
 	    CTipoTransaccion.setEnabled(false);
 		CTipoTransaccion.setMaximumRowCount(17);
 		
 		//Se agregan los items
 		CTipoTransaccion.addItem("INSERCION");
 		CTipoTransaccion.addItem("ACTUALIZACION");
 		CTipoTransaccion.addItem("ELIMINACION");
 		
 		getJFrame().add(CTipoTransaccion); 
  	    
  	   	
 		 //Se crea un JTextField
  	    jtFechaInicial = getJFormattedTextField(fechaInicial,380,120,70,20,"Digite la Fecha Inicial de la Consulta (aaaa-mm-dd)","10");
  	   	jtFechaInicial.addFocusListener(this);
 		jtFechaInicial.setHorizontalAlignment(0);
// 		jtFechaInicial.addKeyListener(getValidarEntradaFechaJTextField());
 		jtFechaInicial.setEditable(false);
 	    jtFechaInicial.setFocusable(false);
 	    
        
        jtFechaFinal = getJFormattedTextField(fechaActual,573,120,70,20,"Digite la Fecha Final de la Consulta (aaaa-mm-dd)","10");
      	jtFechaFinal.setEditable(false);
 	    jtFechaFinal.setFocusable(false);
// 	    jtFechaFinal.addKeyListener(getValidarEntradaFechaJTextField());
 	   	jtFechaFinal.setHorizontalAlignment(0);
 		
 		 
  	    jtNumeroTramites = getJTextField("Número De Trámites",670,340,40,20,"20",false);
 		jtNumeroTramites.setHorizontalAlignment(4);
 		
 		BMostrarCal = getJButton("",453,120,20,20,"Imagenes/Calendario.gif",false);
  		BMostrarCal.addActionListener(this);
  		getJFrame().add(BMostrarCal);

 		BMostrarCal1 = getJButton("",645,120,20,20,"Imagenes/Calendario.gif",false);
  		BMostrarCal1.addActionListener(this);
  		getJFrame().add(BMostrarCal1);
        
        
        // Se crea y configura un panel para agregar la tabla
 		JPanel panel = getJPanel(" Trámites Generados ",40,160,730,380);
 		
  	     modelo = new SortableTableModel() {

          //Se especifica el serial para la serializacion
          static final long serialVersionUID = 19781212;

          public Class getColumnClass(int col) {

              return String.class;

          }

         public boolean isCellEditable(int row, int col) {

             return false;

         }

       };

       modelo.setDataVector(datos,nombresColumnas);  //Se agrega las columnas y filas al modelo de tabla

  	    
 		// Se configura el JTable
 		TAuditorias = getJTable(modelo);
 	
 	    //Se configuran el tamao y justificacin de las  columnas
        configurarColumnas();
        
    	
 		// Se crea un JScrollPane
 		JScrollPane scroll = new JScrollPane(TAuditorias);
 		scroll.setBounds(20,30,690,292);
 		panel.add(scroll);
 		
 		// se agrega una etiqueta y el JTextField al panel
 		panel.add(LNumTramites);
 		panel.add(jtNumeroTramites);
 		
 		getJFrame().add(panel);
  	    
  	    // Se configura el icono del getJFrame
  	    getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Auditorias.gif")).getImage());
 
 		 
 	    mostrarJFrame();
  	    
  	    // Se obtiene el foco
  	    CTablas.grabFocus();
  	    
  	    // Se adicionan eventos a los botones de la Toolbar
 		Blimpiar.addActionListener(this);
 		Bbuscar.addActionListener(this);
 		Bimprimir.addActionListener(this);
 		Beliminar.setEnabled(false);
 		Bsalir.addActionListener(this);
 		Bguardar.setEnabled(false);
 		
 		// Este evento permite ocultar el internalFrame
  	    getJFrame().addMouseListener(new MouseAdapter() {
  	   	
  	   	 public void mouseClicked(MouseEvent e) {
  	   	
  	   	    ocultarInternalFrame();
  	   	
  	   	} 	 
  	   	
  	   });

  }
  
  //********************************************************************
  
  private void ocultarInternalFrame() {
/*  		if (internalFrameFecha != null && internalFrameFecha.isVisible())
  	   	 	 internalFrameFecha.setVisible(false);
  	   	 	 
  	   	if (internalFrameFecha1 != null && internalFrameFecha1.isVisible())
  	   	 	 internalFrameFecha1.setVisible(false);*/
  	
  }
  
  //******************************************************************************
  
  private void traerInformacion() {
  	 
  	 //Se limpia la tabla
  	 limpiarTabla();
     
      
      if (CkTipo.isSelected() || CkTabla.isSelected() || CkRango.isSelected())	{
      
        if (compararFechas(jtFechaInicial.getText(),jtFechaFinal.getText(),true)) {
    		
    		String fechaInicial = "'" + jtFechaInicial.getText() + "'";
	        String fechaFinal = jtFechaFinal.getText();
				 		   	
	        calendar.set(Integer.parseInt(fechaFinal.substring(0,4)),Integer.parseInt(fechaFinal.substring(5,7))-1,Integer.parseInt(fechaFinal.substring(8,10)));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            fechaFinal ="'" +formato.format(calendar.getTime())+"'";
            
            String sentenciaSQL = "Select Usuario,Modulo,Fecha,Fecha,Terminal,Tabla,TipoSentencia,Sentencia "+
                                  "From AuditoriasTransacciones "+
                                  "Where  ";
                                  
             String condicion = "";
             
              
              if (CkTabla.isSelected()) 
                   condicion = "Tabla = '"+ CTablas.getSelectedItem() +"'";
             	 
              	
             	
              if (CkTipo.isSelected() && condicion != null) {
             
                   if (!condicion.equals(""))
                   	 condicion += " and ";
                   	 
                   condicion += "TipoSentencia ='"+ CTipoTransaccion.getSelectedItem() +"'";	 
                               
              }                     
                                  
             
              if (CkRango.isSelected() && condicion!=null) {
             
                   if (!condicion.equals(""))
                   	 condicion += " and ";
                   	 
                   condicion += " Fecha between "+fechaInicial+" and "+ fechaFinal;
                               
              }                     
             
              
              sentenciaSQL += condicion + " Order by 3";                     
              
              
             
            if (condicion != null) {
	            
	            try {                     
	            
	               ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL) ;                  
	               
	               int j = 0;
	               int i = 0;
	              
	               while (resultado.next()) { 
	                     
	                     Vector < String > columnas = new Vector < String >();
	                     
	                     for (j=0; j<8 ;j++) {
		 		           	 if (j == 2)
		 		           	   columnas.addElement(resultado.getString(j+1).substring(0,10));
		 		           	 else
		 		           	  if (j==3)
		 		                columnas.addElement(resultado.getString(j+1).substring(11,16));
		 		                else
		 		           	       columnas.addElement(resultado.getString(j+1));
		 		           	       
		 		          }
		 	   	       
		 	   	       i++;
		 	   	       
		 	   	       
		 	   	       datos.addElement(columnas);  
		 	   	       
	               }
	               
	                   
		 	   	       
	               
	               if (datos.size() > 0) {
		               
		               modelo.setDataVector(datos,nombresColumnas);
		                    
			           TAuditorias.setRowSelectionInterval(0,0);
			           
		               jtNumeroTramites.setText(""+i);
		               
		          } else {
		          	  
		          	  Mensaje("No existe informacion de operaciones sobre esta tabla","Información",JOptionPane.ERROR_MESSAGE);
		              jtNumeroTramites.setText("0");
	               
	               }
	                
	            } catch (Exception e)  {
	            	
	               Mensaje("No se han registrado transacciones"+e,"Informacion",JOptionPane.WARNING_MESSAGE);	
	            }	
	       } else
	                Mensaje("Se debe especifica un numero de placa","Error",JOptionPane.WARNING_MESSAGE);	    
       }  
     } else
            Mensaje("Debe seleccionar por lo menos un parametro de busqueda","Error",JOptionPane.WARNING_MESSAGE);
    
  }
  //***************************************************************************
    private void configurarColumnas() {
		
	    // Se configuran los tamaos de las columnas
	    TAuditorias.getColumnModel().getColumn(0).setPreferredWidth(60);
		TAuditorias.getColumnModel().getColumn(1).setPreferredWidth(60);
		TAuditorias.getColumnModel().getColumn(2).setPreferredWidth(50);
		TAuditorias.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
		TAuditorias.getColumnModel().getColumn(3).setPreferredWidth(15);
		TAuditorias.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
		TAuditorias.getColumnModel().getColumn(4).setPreferredWidth(70);
		TAuditorias.getColumnModel().getColumn(5).setPreferredWidth(75);
		
	}  	
  
  //***************************************************************************

  private void buscarNombresTablas() {
  	
  		try {
 		   
 		    String[] tipo = {"TABLE"};
           
            DatabaseMetaData dbmd = conectarMySQL.getConexion().getMetaData();
	 	   
	 	    ResultSet resultado = dbmd.getTables(null, null, null, tipo);
	 	      
	 	    while (resultado.next()) 
	 	    
	 	       if (!resultado.getString(3).equalsIgnoreCase("Usuarios"))
	 	         CTablas.addItem(resultado.getString(3).toUpperCase());
	 	      
	 	} catch (Exception e) {
	 		System.out.println("Error"+e);
	 	}
 		
  }
  
  //*********************************************************************
  
  private void limpiar() {
 	  
 	  limpiarTabla();
 	  
      // Se limpia la tabla y los JTextField
 	  jtFechaInicial.setText(fechaInicial);
 	  jtFechaInicial.selectAll();
 	  jtFechaFinal.setText(fechaActual);
 	  jtFechaFinal.selectAll();
 	  jtNumeroTramites.setText("");
 	  
 	  jtFechaInicial.setEditable(false);
 	  jtFechaInicial.setFocusable(false);
 	  jtFechaFinal.setEditable(false);
 	  jtFechaFinal.setFocusable(false);
 	  BMostrarCal.setEnabled(false);
 	  BMostrarCal1.setEnabled(false);
 	  
 	  CTipoTransaccion.setSelectedIndex(0);
 	  CTipoTransaccion.setEnabled(false);
 	  
 	  CTablas.setSelectedIndex(0);
 	  CTablas.setEnabled(true);
 	  
 	  CkTabla.setSelected(true);
 	  CkTipo.setSelected(false);
 	  CkRango.setSelected(false);
 	 
	}   
    
    //*****************************************************************************
    	
	private void limpiarTabla() {
	  
	  
	   datos = new Vector < Vector >();

       modelo.setDataVector(datos1,nomColumnas1);
 
	}
    
  //*****************************************************************************
  
  public void actionPerformed(ActionEvent act) {
 		
 	 Object fuente = act.getSource();
 	 
 	 // Se verifica el componente que genero la acccion
 	  if (fuente==Blimpiar) 
 		
 			limpiar();
 	   
 	   else
 		
 		   if (fuente==Bbuscar) 
 		
 		        traerInformacion();
 		
 			 else 
 		
 			    if (fuente==Bsalir) {
               
                   ocultarJFrame();
 			   
 			    } else
 			   
 			       if (fuente==BMostrarCal) {
		 			       if (calendarioInicial == null)
			                              	    	
			                              	 	    calendarioInicial = new Calendario(jtFechaInicial,getJFrame(),630,290);
			                              	 	    
			                              	 	else
			                              	 	     
			                              	 	     calendarioInicial.mostrarCalendario(true);   
		 			          
		 			      } else
		 		
		 			         if (fuente==BMostrarCal1) {
	             
                          	    if (calendarioFinal == null)
                          	    	
                          	 	    calendarioFinal = new Calendario(jtFechaFinal,getJFrame(),630,290);
                          	 	    
                          	 	else
                          	 	     
                          	 	     calendarioFinal.mostrarCalendario(true);  
		                   
		 			        } else
		 			         
		 			          if (fuente==CkTabla) {
		 			           	
		 			              if (CkTabla.isSelected()) {
		 			            
		 			              	 CTablas.setEnabled(true);
		 			              	 CTablas.grabFocus();
		 			            
		 			              }else 
		 			            
		 			              	 CTablas.setEnabled(false);
		 			             
		 			           }else
			 			        
			 			           if (fuente==CkTipo) {
			 			           	
			 			              if (CkTipo.isSelected()) {
			 			              	
			 			              	CTipoTransaccion.setEnabled(true);
		 			              	    CTipoTransaccion.grabFocus();
		 			              	    
			 			              }else 
			 			        
			 			                 CTipoTransaccion.setEnabled(false);
			 			           }else
			 			        
			 			             if (fuente==CkRango) {
			 			           	
			 			                if (CkRango.isSelected()) {
			 			        
			 			                	jtFechaInicial.setEditable(true);
 	                                        jtFechaInicial.setFocusable(true);
			 			                	jtFechaFinal.setEditable(true);
 	                                        jtFechaFinal.setFocusable(true);
			 			                	BMostrarCal.setEnabled(true);
			 			                	BMostrarCal1.setEnabled(true);
			 			        
			 			                }else {
			 			                	
			 			                	jtFechaInicial.setEditable(false);
 	                                        jtFechaInicial.setFocusable(false);
			 			                	jtFechaFinal.setEditable(false);
 	                                        jtFechaFinal.setFocusable(false);
			 			                	BMostrarCal.setEnabled(false);
			 			                	BMostrarCal1.setEnabled(false);
			 			                	
			 			                }	
			 			              }
			 			                          
	 			              	 
  }	 
 		
  //************************************************************************
  public void focusGained(FocusEvent F) {
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
  }
 	
  //************************************************************************ 
  public void focusLost(FocusEvent F) {
 	  	
 	  // se coloca el atributo visual por defecto
 		  F.getComponent().setBackground(Color.yellow);
 		
 	}
}  	 