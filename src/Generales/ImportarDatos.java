/**
 * Clase: ImportarDatos
 *
 * @version  1.0
 *
 * @since 02-12-2005
 *
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;

import com.JASoft.componentes.EditableCellTableModel;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.awt.event.KeyEvent;

import java.io.File;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Statement;

import java.util.HashSet;
import java.util.Set;

import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileFilter;


final  class ImportarDatos extends CrearJFrame implements FocusListener,ActionListener{
 	
 	private ConectarMySQL conMySQL;
 	
 	private JTextField TUsuario;
 	private JTextField tRutaArchivo;
 	private JTextField tNumeroFilas;
 	
 	private JPasswordField tPassword;
 	
 	private JButton bCSiguiente;
 	private JButton bSiguiente;
 	private JButton bExaminar;
 	private JButton bImportar;
 	private JButton bAtras;
 	
 	private JPanel panelSesion;
 	private JPanel panelConfiguracion;
 	private JPanel panelImportar;
 	private JPanel principal;
 	
 	private JFileChooser fileChooser;
 	
 	private DatabaseMetaData  dbmd;
 	
 	private JList listaColumnas;
 	
 	private JComboBox CTablas;
 	
 	private JProgressBar barra;
 	
 	private ResultSet resultado;
 	private ResultSetMetaData rsmd;
 	
 	private JTable tabla;
 	
 	private JLabel LImportar;
    private JLabel LTabla;
    private JLabel LArchivo;
    
    private ParametrosTabla parametros;
    
    private int numColumnas;
    
    private EditableCellTableModel editable;
 	
 	public ImportarDatos(ConectarMySQL p_conMySQL,JFrame p_frame) {
 		super("Importar Datos","Toolbar",p_frame);
 		
 		
 		conMySQL = p_conMySQL;
 		
 		// Se instancia el objeto de tipo MetaData
 		try {
 		
 		   dbmd = conMySQL.getConexion().getMetaData();
 		  
 		} catch (Exception e) {
 			System.out.println("Error de conexion");
 		}  
 		
 	    principal = getJPanel(10,50,770,500);
 	   
 	    principal.setBorder(new BevelBorder(BevelBorder.LOWERED));
 	    
 	    getJFrame().add(principal);
 		
 		// Se configura el panel inicial
 		panelSesionInicial();
 		
 		 // Se configura el icono del frame
 		getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/ImportarArchivo.gif")).getImage());
 
 	
  		mostrarJFrame();
 		
 		TUsuario.grabFocus();
 		
 		//Se agregan los eventos al JToolbar
 		Bsalir.addActionListener(this);
 		Blimpiar.setEnabled(false);
 		Beliminar.setEnabled(false);
 		Bimprimir.setEnabled(false);
 		Bguardar.setEnabled(false);
 		
 		
 	}
 	
 	
 	public void panelSesionInicial() {
 		
 		panelSesion = new JPanel();
 		
 		panelSesion.setLayout(null);
 		
 		panelSesion.setBounds(10,10,700,450);
 		
 		// Se configuran las etiquetas
 		JLabel Lnit = getJLabel("Usuario : ",400,100,80,20);
 		panelSesion.add(Lnit);
 		
 		JLabel Lnombre = getJLabel("Password : ",400,140,80,20);
 		panelSesion.add(Lnombre);
 		
 		//Se instancian los JTextField
 		TUsuario = getJTextField("Digite el nombre de usuario",500,100,90,20,"");
 		TUsuario.addFocusListener(this);
 		panelSesion.add(TUsuario);
 		
 		ActionListener showSwingWorkerDialog = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
         
                confirmarUsuarioClave();
            }
         };
 		
 		tPassword = getJPasswordField("Digite el password de usuario",500,140,90,20,"20");
 		tPassword.addFocusListener(this);
 		tPassword.addActionListener(showSwingWorkerDialog);
 		
 		// Se configura para que se pueda navegar con Enter o Tab
        tPassword.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
  	 	panelSesion.add(tPassword);
 		
 		// se agregan JButton al panel
 		bSiguiente = new JButton("Siguiente >");
 		bSiguiente.setMnemonic('S');
 		bSiguiente.setBounds(480,420,140,24);
 		bSiguiente.addActionListener(this);
 		panelSesion.add(bSiguiente);
 		
 		principal.add(panelSesion);
 		
 		
 	}
 	
 	public void panelConfiguracion() {
 		
 		fileChooser = new JFileChooser();
 		fileChooser.addChoosableFileFilter(new MyFilter());
 		
 		panelConfiguracion = new JPanel();
 		
 		panelConfiguracion.setLayout(null);
 		
 		panelConfiguracion.setBounds(100,100,600,400);
 		
 		JLabel LTipoArchivos = getJLabel("Tipo De Archivos",20,20,120,20);
 		panelConfiguracion.add(LTipoArchivos);
 		
 		JLabel LArchivos = getJLabel("Archivo",20,80,120,20);
 		panelConfiguracion.add(LArchivos);
 		
 		JLabel LTablas = getJLabel("Tabla",20,120,120,20);
 		panelConfiguracion.add(LTablas);
 		
        LImportar = getJLabel("Importando Datos",200,190,220,20);
 		panelConfiguracion.add(LImportar);
 		LImportar.setVisible(false);
 		
       	tRutaArchivo = getJTextField("ruta de archivo",80,80,340,20,"40");
 		tRutaArchivo.setBackground(Color.white);
 		panelConfiguracion.add(tRutaArchivo);
 		
 		JRadioButton RXls = new JRadioButton("xls",true);
 		RXls.setBounds(150,20,60,20);
 		panelConfiguracion.add(RXls);
 		
 		JRadioButton RTxt = new JRadioButton("txt");
 		RTxt.setBounds(250,20,60,20);
 		RTxt.setEnabled(false);
 		panelConfiguracion.add(RTxt);
 		
 		JRadioButton RDat = new JRadioButton("dat");
 		RDat.setBounds(350,20,60,20);
 		RDat.setEnabled(false);
 		panelConfiguracion.add(RDat);
 		
 		CTablas = new JComboBox();
 		CTablas.setBounds(80,120,340,20);
 		panelConfiguracion.add(CTablas);
 		
 		try {
 		   
 		    String[] tipo = {"TABLE"};
           
            dbmd = conMySQL.getConexion().getMetaData();
	 	   
	 	    ResultSet resultado = dbmd.getTables(null, null, null, tipo);
	 	      
	 	    while (resultado.next()) 
	 	    
	 	       if (!resultado.getString(3).equalsIgnoreCase("Usuarios"))
	 	         CTablas.addItem(resultado.getString(3));
	 	      
	 	} catch (Exception e) {
	 		System.out.println("Error"+e);
	 	}
 		
 		
 		bExaminar = new JButton("...");
 		bExaminar.setMnemonic('E');
 		bExaminar.setBounds(430,80,20,20);
 		bExaminar.addActionListener(this);
 		panelConfiguracion.add(bExaminar);
 		
 		bCSiguiente = new JButton("Siguiente >");
 		bCSiguiente.setMnemonic('S');
 		bCSiguiente.setBounds(340,280,120,20);
  		bCSiguiente.addActionListener(this);
 		panelConfiguracion.add(bCSiguiente);
 		principal.add(panelConfiguracion);
 		
 		panelConfiguracion.setBorder(new BevelBorder(BevelBorder.RAISED));
 		
	 		
 	}
 	
 	public void panelInforImport() {
 		
 		panelImportar = new JPanel();
 		
 		panelImportar.setLayout(null);
 		
 		panelImportar.setBounds(10,10,800,480);
 		
 		LTabla = new JLabel("Tabla : "+String.valueOf(CTablas.getSelectedItem()).toUpperCase());
 		LTabla.setBounds(20,10,200,20);
 		panelImportar.add(LTabla);
 		
 		
 		LArchivo= new JLabel("Archivo : "+traeNombreArchivo(tRutaArchivo.getText()));
 		LArchivo.setBounds(520,10,200,20);
 		panelImportar.add(LArchivo);
 		
 		
 		JLabel LColumnas = new JLabel("Columnas");
 		LColumnas.setBounds(60,35,100,20);
 		panelImportar.add(LColumnas);
 		
 		JLabel LNumFilas = new JLabel("Numero De Filas Importadas :");
 		LNumFilas.setBounds(490,420,200,20);
 		panelImportar.add(LNumFilas);
 		
 		listaColumnas = new JList();
 		listaColumnas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		listaColumnas.setBorder(new EtchedBorder());
 		
 		
 		JScrollPane scroll = new JScrollPane(listaColumnas);
 		scroll.setBounds(20,50,220,130);
 		panelImportar.add(scroll);
 		
 	 
 	    //Se crea una tabla
 		tabla = new JTable();
 		editable = new EditableCellTableModel(parametros.filas,parametros.columnas);
 		tabla.setModel(editable);
 		
 		JScrollPane scrollTabla = new JScrollPane(tabla);
 		scrollTabla.setBounds(20,190,720,220);
 	    panelImportar.add(scrollTabla);
 	    
 	    bImportar = new JButton("Importar Datos");
 	    bImportar.setBounds(580,455,120,22);
 	    bImportar.setMnemonic('i');
 	    bImportar.addActionListener(this);
 	    panelImportar.add(bImportar);
 	    
 	    bAtras = new JButton("Atras");
 	    bAtras.setBounds(420,455,120,22);
 	    bAtras.setMnemonic('i');
 	    bAtras.addActionListener(this);
 	    panelImportar.add(bAtras);
 	    
 	    tNumeroFilas = new JTextField();
 	    tNumeroFilas.setBounds(660,420,60,20);
 	    tNumeroFilas.setHorizontalAlignment(4);
 		tNumeroFilas.setEditable(false);
 		tNumeroFilas.setBackground(Color.white);
 		panelImportar.add(tNumeroFilas);
 	    
 	    principal.add(panelImportar);
 		
 		principal.repaint();
 		
   }
   
   public String traeNombreArchivo(String ruta) {
   	
   	 try {
   	 	
   	 	ruta = ruta.substring(tRutaArchivo.getText().lastIndexOf('\\')+1,ruta.length());
   	 	
   	 	return ruta.toUpperCase();
   	 	
   	 }catch (Exception e) {
   	 	System.out.println("eer"+e);
   	 	return null;
   	 }
   	
   }
   
   public void traerColumnas() {
	   	
	 	Vector columnas = null;
	 	
	 	try{    
	 	            
 	        resultado = dbmd.getColumns(null,null,""+CTablas.getSelectedItem(),null);
 	        
 	        resultado.last();
	 	    
	 	    Object datos[] = new Object[resultado.getRow()];
	 	    
	 	    columnas = new Vector();
	 	    
	 	    resultado.beforeFirst();
	 	    
	 	    int i =0;  
	 	    
	 	    while (resultado.next()) {
	 	      
	 	        datos[i] = resultado.getString(4);
	 	        i++;
	 	      }  
	 	      
	 	      numColumnas = i--;
	 	      
	 	      listaColumnas.setListData(datos);
	 	      
      } catch (Exception exc) {
      	
       	
 		System.out.println("Error"+exc);
 	  }
 	

   }
   
   
   public void excelInformation() {
   	    
   	    try {
   	     
   	        parametros = new ParametrosTabla();
   	  
	 	    HiloBarra hilo = new HiloBarra();
		    hilo.start();
		    	 	   
	 	} catch (Exception e) {
	 		System.out.println("Error"+e);
	 	} 
   	
   }
 	
 
 	
 	public void actionPerformed(ActionEvent act) {
 		
 		Object fuente = act.getSource();
 		
 		if (fuente == bSiguiente) {
 			
 			confirmarUsuarioClave();
 			    
 	    }else
 	      if (fuente == bExaminar) {
 	      	 
 	      	 int opcion = fileChooser.showOpenDialog(getJFrame());
 	      	 
 	      	 if (opcion == JFileChooser.APPROVE_OPTION) 
 
  	      	     tRutaArchivo.setText(fileChooser.getSelectedFile().getPath());
 
 	      } else
 	         if (fuente == bCSiguiente) {
 	          
 	           if (tRutaArchivo.getText().length()>0) {
 	           	
				     excelInformation();
				     
 	           } else
 	             Mensaje("Se debe especificar una ruta","Error",JOptionPane.ERROR_MESSAGE);
 	          
 	         }else 
 	            
 	            if (fuente == bImportar) {
 	            	
 	             if (numColumnas == tabla.getColumnCount())
 	               insertarDatos();
 	             else {
 	               Mensaje("El numero de columnas de la tabla debe coincidir con el numero de columnas de excel","Información",JOptionPane.WARNING_MESSAGE);
 	             }
 	              
                }  
 	            else
	 	            if (fuente == Bsalir) {
	 	            	 ocultarJFrame();
	 	            } else
	 	               if (fuente == bAtras) {
	 	               	 
	 	               	 panelConfiguracion.setVisible(true);
	 	               	 panelImportar.setVisible(false);
	 	               	 
	 	               }	
 	}    
 	
 	
 	public void barraProgreso(int maximo) {
 		
 		
 		if (barra == null)
 		  barra = new JProgressBar();
 		    
 		barra.setBounds(20,220,400,20);
 		barra.setVisible(true);
 		panelConfiguracion.add(barra);
 		barra.setValue(0);
 		barra.setMinimum(0);
 		barra.setMaximum(maximo);
 		
 	}
 	
 	
 	public void confirmarUsuarioClave() {
 	  
 	  if (TUsuario.getText().equals("")) {
 			  Mensaje(TUsuario.getToolTipText(),"Error",JOptionPane.WARNING_MESSAGE);
 			  TUsuario.grabFocus();
 			} else
 			  if (String.valueOf(tPassword.getPassword()).equals("")) {
 			  	  Mensaje(tPassword.getToolTipText(),"Error",JOptionPane.WARNING_MESSAGE);
 			      tPassword.grabFocus();
 			  }else 
 			  	buscarUsuario();
 	}
  
 	
    public void insertarDatos() {
       
       try {	
    	String datos[][] = new String[tabla.getRowCount()][rsmd.getColumnCount()];
    	
    	
    	for (int i = 0;i<tabla.getRowCount();i++) {
    	 
    	 for (int j = 0; j<tabla.getColumnCount() ; j++ )	

	    	  if (rsmd.getColumnTypeName(j+1).equals("INTEGER"))
	    	     datos[i][j] =""+ tabla.getValueAt(i,j);
	    	  else
	    	  	 if (tabla.getValueAt(i,j)!=null && !tabla.getValueAt(i,j).toString().equalsIgnoreCase("null"))
	    	  	     datos[i][j] ="'" + tabla.getValueAt(i,j) +"'";
	    	     else  
	               datos[i][j] ="null";
    	}
    	
    	String datosComodin[][] = new String[1][tabla.getColumnCount()];
        int k = 0;
        
        for (int i = 0; i < datos.length; i++) {
        
	           for (k = 0; k < tabla.getColumnCount(); k++)
	    	      datosComodin[0][k] = datos[i][k];
	    	    
	    	    try {
	    	    
			    	  guardar(""+CTablas.getSelectedItem(),datosComodin,conMySQL,tabla.getColumnCount(),false);
			    	      
				 } catch (Exception e ) {
				 	    
				 		String error = "" + e;
      	
      
                   if (error.indexOf("Duplicate entry") < 0)
	    		     System.out.println(""+ datosComodin[0][0] + datosComodin[0][1] + e);
	    	     }   
				      
         }        
             
	     
	     conMySQL.commit();
	     
	     Mensaje("Registros insertados correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
	           
    	} catch (Exception e ) {
    		  
    		  Mensaje("Error al insertar" + e,"Error",JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    	
 	public void buscarUsuario() {
 	
 		// Se crea la sentencia para buscar el valor del codigo dane
  	             	String sentenciaSQL = "Select ID "+
			  		                      "From Usuarios " +
			  		                      "Where ID = '"+TUsuario.getText()+"' and "+
			  		                      "Password ='"+String.valueOf(tPassword.getPassword())+"'";
  		                      
			  		                                
			 		// Se valida que se halla digitado un nit
			 		 try {
			 		 
			 		   // Se realiza la consulta en la base de datos para buscar la placa
			 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
			 		    
			 		   if (resultado!=null) 
				 		  if (resultado.next()) {
				 		  
				 		  	panelSesion.setVisible(false);
				 		  	
				 		  	if (panelConfiguracion == null)
				 		  	  panelConfiguracion();
				 		  	else
				 		  	   panelConfiguracion.setVisible(true);
				 		  	
				 		  }else {
				 		    Mensaje("Usuario o Password Errado","Error",JOptionPane.ERROR_MESSAGE);	 
				 		    TUsuario.grabFocus();
				 		  }  
	 		   	  	    	   
	 		   	   	   	
					     } catch (Exception e) {
				 		 
				 		    Mensaje("Usuario o Password Errado","Error",JOptionPane.ERROR_MESSAGE);	 
				 		    TUsuario.grabFocus();
				 	     }     
  	
 	}
 	
 	public void focusGained(FocusEvent F) {
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
 	}
 	
 	public void focusLost(FocusEvent F) {
     
        // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());
 	}
 	
    class MyFilter extends FileFilter {
        public boolean accept(File file) {
            String filename = file.getName();
            
            return filename.endsWith(".xls");
        }
        public String getDescription() {
            return "*.xls";
        }
    }
    
    class ParametrosTabla {
    
    	Vector < Vector > filas = new Vector < Vector >();
    	Vector < String > columnas = new Vector < String >();;
    }
    
    class HiloBarra extends Thread {
    
	     public void run() {
	 		
	 		int numFilas;
	 		
	 		try {
   	
		   		   	Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
				
				    Connection conexion = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+tRutaArchivo.getText()+";DriverID=22;READONLY=false","","");
				    
				    Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				    
				    String[] tipo = {"TABLE","SYSTEM TABLE"};
		           
		            DatabaseMetaData dbmd = conexion.getMetaData();
			 	   
			 	    resultado = dbmd.getTables(null, null, null, tipo);
			 	    
			 	    rsmd = resultado.getMetaData();
			 	     
			 	    int i = 0;
			 	    
			 	    String nombreHojas[]= new String[rsmd.getColumnCount()];
				    
				    while (resultado.next()) {
			 	    
			 	      nombreHojas[i] = resultado.getString(3);
			 	      
			 	      i++;
			 	    }
			 	    
			 	    
			 	    resultado = sentencia.executeQuery("Select * From ["+ nombreHojas[0] +"]");
			 	    
			 	    resultado.last();
				    
				    numFilas = resultado.getRow();
				    
				    barraProgreso(numFilas);
				    
				    conexion.close();
				    
				    conexion = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+fileChooser.getSelectedFile().getPath()+";DriverID=22;READONLY=false","","");
				    
				    sentencia = conexion.createStatement();
				    
				    // Se obtiene la informacion de la hoja de calculo
			 	    resultado = sentencia.executeQuery("Select * From ["+ nombreHojas[0] +"]");//+ nombreHojas[0]);
			 	    
			 	    rsmd = resultado.getMetaData();
			 	    
			 	    int j = 0; 
			 	    	    
			 	    for (j = 1; j<=rsmd.getColumnCount();j++)
				       
				       parametros.columnas.addElement("Columna "+j);
				    
				    LImportar.setVisible(true);
				    		
				 	while (resultado.next()) {
					 	   
					 	       Vector < String > columnas = new Vector < String >();
					 	       
					 	       for (j = 1; j<=rsmd.getColumnCount();j++) {
					 	          
					 	          String datoColumna = resultado.getString(j);
					 	          
					 	          if (datoColumna!=null && datoColumna.endsWith(".0"))
					 	            datoColumna = datoColumna.substring(0,datoColumna.indexOf("."));
					 	          else
					 	          	 if (datoColumna!=null && datoColumna.endsWith(">A"))
					 	          	 	 datoColumna = datoColumna.replaceAll(">A","");
					 	          
					 	          columnas.addElement(datoColumna);
					 	          
					 	          
					 	       }  
					 	       	
					 	       parametros.filas.addElement(columnas); 
					 	       
					 	       barra.setValue(i);
					 	   
					 	       i++;
					 }
					 
					 LImportar.setVisible(false);
					 
					 barra.setVisible(false);
					 
					 panelConfiguracion.setVisible(false) ;
					 
					 if (panelImportar == null)
				 		  panelInforImport();
				     else {
				     
				 	    panelImportar.setVisible(true);
				 	    editable = new EditableCellTableModel(parametros.filas,parametros.columnas);
 		                tabla.setModel(editable);
				 	 }
				 	 traerColumnas();  
				 	 
				 	 LTabla.setText("Tabla : "+String.valueOf(CTablas.getSelectedItem()).toUpperCase());
				 	 LArchivo.setText("Archivo : "+traeNombreArchivo(tRutaArchivo.getText()));
				 	 
				 	 tNumeroFilas.setText(""+numFilas);
				 	   
			        } catch (Exception e) {
			        	Mensaje(""+e,"Error",JOptionPane.ERROR_MESSAGE);
			        }	 		
 	   }
 	}   
 	
} 	