/*
 * Clase: CargarBackup
 *
 * Version : 1.0
 *
 * Fecha: 30-11-2005
 *
 * Copyright: Ing.  Jhon Mendez
 */

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.ImageFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import java.io.File;
import java.io.FileOutputStream;

import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


 /*
  * Esta clase permite configurar los backup que se van a generar
  */
  
final class CargarBackup extends JInternalFrame implements ActionListener,FocusListener{
  	
  	//Referencia de conexion a la base de datos
    private ConectarMySQL conectarMySQL;
  	
  	//Referencia al componebte CrearJFrame
    private CrearJFrame cJf = new CrearJFrame();
    
    //Casillas de texto
 	private JTextField TRuta;
   	
   	//Botones
   	private JButton BRuta;
   	private JButton BCargar;
   	private JButton BSalir;
    
    //JFileChooser para buscar el archivo que contiene backup
    private JFileChooser examinador;
    
    //Referencia al MenuPrincipal
    private MenuPrincipal menu;
	
	//Filtro para tipos de archivos solo SQL
	private ImageFilter filtro = new ImageFilter(new String[] {"sql"},"SQL Script SQL");
    
    //Constructor utilizado deste el menu principal	
  	public CargarBackup(ConectarMySQL p_conectarMySQL,MenuPrincipal p_menu){ 
  	    
 		//super ("Archivos Planos Por Mes","Toolbar");
  	    super("Restaurar Copia de Seguridad (BackUp)",true, true, false, false);
    	
    	setBounds(100,100,600,280);
    	
    	setLayout(null);
    	
    	menu = p_menu;
    	
 		conectarMySQL = p_conectarMySQL;
 		
 		// Se instancia un panel
  	    JPanel panel = cJf.getJPanel(20,20,550,130);
 		
 		JLabel LRuta = cJf.getJLabel("Copia a Restaurar : ",10,50,150,20);
 		panel.add(LRuta);
 		
 		TRuta = cJf.getJTextField("Seleccione la Copia de Seguridad (BackUp) a Restaurar",128,50,388,20,"20",false);
 		TRuta.addFocusListener(this);
 		panel.add(TRuta);

		BRuta = cJf.getJButton("",520,50,20,20,"Imagenes/Ruta.gif","Ruta");
		BRuta.setMnemonic('R');
  		BRuta.addActionListener(this);
  		panel.add(BRuta);
        
        BCargar = cJf.getJButton("Restaurar",160,180,120,35,"Imagenes/cargarBackup.gif","Cargar Backup");
        BCargar.setMnemonic('e');
        BCargar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/cargarBackupS.gif")));
  		BCargar.addActionListener(this);
  		this.add(BCargar);
        
        BSalir = cJf.getJButton(" Salir",290,180,120,35,"Imagenes/Salir.gif","Salir");
        BSalir.setMnemonic('S');
        BSalir.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/SalirS.gif")));
  	    BSalir.addActionListener(this);
  		this.add(BSalir);
        
        buscarRuta();
         
		// Se instancia el JFileChooser
 		examinador = new JFileChooser(TRuta.getText());
 	
 	      // Se configura el icono del this
 		this.setFrameIcon(new ImageIcon(getClass().getResource("/Imagenes/cargarBackup.gif")));
 	 
  	    this.add(panel);
  	    
  	    this.setVisible(true);
  	    
  	      // Se agrega un evento de ventana
 	   this.addInternalFrameListener( new InternalFrameAdapter() {
		    public void internalFrameClosing(InternalFrameEvent ife) {
			   cerrarInternal();
		    }
	   });
     
  	    
  	   
  	}
    
   //********************************************************************************
  	
  	private void buscarRuta() {
  		
  		// Se crea la sentencia para buscar el valor del codigo dane
  		final String sentenciaSQL = "Select Valor "+
  		                            "From Parametros " +
  		                            "Where Codparam = 21";
  		                      
  		                      
  		                                   
 		// Se valida que se halla digitado un nit
 		 try {
 		 
 		   // Se realiza la consulta en la base de datos para buscar la placa
 		   ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
 		    
 		   if (resultado!=null) 
	 		  if (resultado.next()) 
	 		  	
	 		  	TRuta.setText(resultado.getString(1));
	 		  	
	 		  else {
	 		    cJf.Mensaje("Se deben los parametros para los backup","Error",JOptionPane.ERROR_MESSAGE);	 
	 		    TRuta.setText(examinador.getCurrentDirectory().getPath());
	 		  }  
	 		   	  	    	   
	 		   	   	   	
	     } catch (Exception e) {
 		 
 		    cJf.Mensaje("Se deben los parametros para los backup","Error",JOptionPane.ERROR_MESSAGE);	 
 		    TRuta.setText(examinador.getCurrentDirectory().getPath());
 	     }     
  	
                   
  		
  	}
  	
  	//********************************************************************************
  	
    private void CargarCopiaSeguridad() {
    
        try {
	  
			    //Recuperacion
			    char chars[] = {'"'};

        		String bat=chars[0]+"C:/Archivos de programa/MySQL/MySQL Server 5.0/bin/mysql"+chars[0]+" --password=A_Z.kA.BAN --user=AZkABANPrionero  azkaban < "+chars[0]+cambiarCaracter(TRuta.getText())+chars[0]+" \n  exit";
        		
        		System.out.println(bat);
        		
        		File outputFile = new File("Restauracion_BDAzkaban.bat");
        		
        		FileOutputStream fos = new FileOutputStream(outputFile);
        		
        		fos.write(bat.getBytes());
        		
        		fos.close();
        		
        		System.out.println(Runtime.getRuntime().exec("Bat_To_Exe_Converter Restauracion_BDAzkaban.bat Restauracion_BDAzkaban.exe \"\" 0").waitFor());
				        	   
        		outputFile.delete();
        		
        		Runtime.getRuntime().exec("Restauracion_BDAzkaban.exe");
        	    
        	    File executeFile = new File("Restauracion_BDAzkaban.exe");
        	     
        	    executeFile.deleteOnExit();  //Se borra el archivo
        	    
        	    cJf.Mensaje("Restaruracion Exitosa","Información",1);
        			   
	        }catch (Exception e) {
	    	 	 
	    	 	 	e.printStackTrace();
	    	 
	    	 }
        
        
        
    }   
    	
    //********************************************************************************	

    private static String cambiarCaracter(String ruta) {
    
      	String rutaSalida = "";
  		
  		for (int i=0;i<ruta.length();i++) {
  			if (ruta.charAt(i)=='\\')
  			  rutaSalida += "/";
  			else
  			  rutaSalida += ""+ ruta.charAt(i);
  	   }
  		
  		return rutaSalida;
  		
  	} 
  	
  	//********************************************************************************
  	
  	private void cerrarInternal() {
	
	 	menu.habilitarMenu(true);
	 }

    //******************************************************************************** 
     
     public void actionPerformed(ActionEvent act) {
 		
 		Object fuente = act.getSource();
 		
 		if (fuente == BRuta) {
 			
		 	//** Se instancia el examinador de archivos
	    	  examinador=new JFileChooser(TRuta.getText());
    
    		//se coloca el filtro
        	  examinador.addChoosableFileFilter(filtro);
	    		
	    		//** Se muestra el examinador
	    		int opcion=examinador.showDialog(this,"Aceptar");
	    		
	    		if (opcion==JFileChooser.APPROVE_OPTION) 
		    		TRuta.setText(examinador.getSelectedFile().getPath());
	    		
                 
 		} else
 	         if (fuente==BCargar) {
 	            if (TRuta.getText().indexOf(".sql")>0) {
 	              CargarCopiaSeguridad();
 	            }else
 	                  cJf.Mensaje("Debe escoger una copia de seguridad","Error",JOptionPane.ERROR_MESSAGE);
 	         
 	         }else
 		       if (fuente == BSalir) {
 		           cerrarInternal();
 		           dispose();	
 		       }    
 	}
 
 
    //********************************************************************************

  	public void focusGained(FocusEvent F) {
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());
 	}
 	
 	//********************************************************************************
 	
 	public void focusLost(FocusEvent F) {
 	    // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(cJf.getVisualAtributoPierdeFocoComponentes());
 		
 	}
 	
    
}
