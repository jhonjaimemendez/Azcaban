/*
 * Clase: ConfigurarBackup
 *
 * Version : 1.0
 *
 * Fecha: 30-11-2005
 *
 * Copyright: Ing.  Jhon Mendez
 */

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.FileOutputStream;

import java.net.InetAddress;

import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
  
final  class ConfigurarBackup extends JInternalFrame implements ActionListener,FocusListener{
	
	
    //Se especifica el serial para la serializacion
    static final long serialVersionUID = 19781213;
  	
  	//Referencia de conexion a la base de datos
	private ConectarMySQL conMySQL;
    
    //Referencia al  componete CrearJFrame
    private CrearJFrame cJf = new CrearJFrame();
    
    // Casillas De Texto
  	private JTextField TRuta;
    private JTextField THost1; //Direccion IP de los servidores de backup
    private JTextField THost2;
    private JTextField THost3;
    
    //Botones
   	private JButton BRuta;
   	private JButton BCopia;
   	private JButton BSalir;
    
    //JFileChooser para escoger la ruta donde se almacenaran los backup
    private JFileChooser fileChooser;
 
    //JCheckBox que configuran el numero mde servidores de backup
    private JCheckBox CkHost1;
    private JCheckBox CkHost2;
    private JCheckBox CkHost3;
    
    //Referencia al MenuPrincipal
    private MenuPrincipal menu;
    
    //Constructor utilizado deste el menu principal
  	public ConfigurarBackup(ConectarMySQL p_conMySQL,MenuPrincipal p_menu){ 
  	    
  	    super("Configurar Copias De Seguridad (Backup)",true, true, false, false);
    	
    	setBounds(100,100,600,280);
    	
    	setLayout(null);
    	
    	menu = p_menu;
    	
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
 		conMySQL = p_conMySQL;
 		
 		// Se instancia un panel
  	    JPanel panel = cJf.getJPanel(20,40,550,130);
 		
 		JLabel LRuta = cJf.getJLabel("Guardar en : ",30,90,150,20);
 		panel.add(LRuta);
 		
 		
 			// Se crea y configura un panel para agregar la tabla
 		JPanel panelServidores = cJf.getJPanel(" Servidores ",150,5,385,75);
 		
  	   	CkHost1 = new JCheckBox("Host Nº 1",true);
 		CkHost1.setBounds(20,20,100,20);
 		CkHost1.setEnabled(false);
 		CkHost1.setFocusable(false);
 		panelServidores.add(CkHost1);
 		
 		
 		THost1 = cJf.getJTextField("",22,45,100,20,"Dirección del Host Nº 1, este es el servidor local","20");
 		THost1.addFocusListener(this);
 		panelServidores.add(THost1);
  	    
  	    
  	    CkHost2 = new JCheckBox("Host Nº 2",false);
 		CkHost2.setBounds(140,20,100,20);
 		CkHost2.addActionListener(this);
 		panelServidores.add(CkHost2);
 		
 		THost2 = cJf.getJTextField("Dirección del Host Nº 2",142,45,100,20,"15",false);
 	    THost2.setEditable(false);
 	    THost2.setFocusable(false);
 	    THost2.addKeyListener(new KeyAdapter() {	 
 	    	 // Se valida que solo se digite mayuscula
 	    	 public void  keyTyped (KeyEvent k) {
 	    	 	
 	    	 	if(((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) && (k.getKeyChar()!='.'))|| (THost2.getText().length() >= Integer.parseInt(k.getComponent().getName())))
 	    	 		k.consume();
 	    	 	
              }
              
   
        });
        THost2.addFocusListener(this);
 	   	panelServidores.add(THost2);
 		
 		CkHost3 = new JCheckBox("Host Nº 3",false);
 		CkHost3.setBounds(260,20,100,20);
 		CkHost3.addActionListener(this);
 		panelServidores.add(CkHost3);
 		
 		THost3 = cJf.getJTextField("Dirección del Host Nº 3",262,45,100,20,"15",false);
 		THost3.addFocusListener(this);
 		THost3.addKeyListener(new KeyAdapter() {	 
 	    	 // Se valida que solo se digite mayuscula
 	    	 public void  keyTyped (KeyEvent k) {
 	    	 	
 	    	 	if(((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) && (k.getKeyChar()!='.'))|| (THost3.getText().length() >= Integer.parseInt(k.getComponent().getName())))
 	    	 		k.consume();
 	    	 	
              }
              
   
        });
 	    
 	   	panelServidores.add(THost3);
  	    
  	    
 	
  	    TRuta = cJf.getJTextField("Digite la ruta donde se guardarán los Archivos Planos",110,90,200,20,"15",false);
 		TRuta.addFocusListener(this);
 		panel.add(TRuta);

		BRuta = cJf.getJButton("",515,90,20,20,"Imagenes/Ruta.gif","ruta");
		BRuta.setMnemonic('R');
  		BRuta.addActionListener(this);
  		panel.add(BRuta);
        
        	
  		
  		BCopia = cJf.getJButton("Hacer Backup",140,190,140,35,"Imagenes/configBackup.gif","Hacer Backup");
  		BCopia.setMnemonic('H');
  		BCopia.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/configBackupS.gif")));
  		BCopia.addActionListener(this);
  		this.add(BCopia);
  		
  		
  		BSalir = cJf.getJButton(" Salir",340,190,140,35,"Imagenes/Salir.gif","Salir");
  		BSalir.setMnemonic('S');
  		BSalir.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/SalirS.gif")));
  	    BSalir.addActionListener(this);
  		this.add(BSalir);
        
		// Se instancia el JFileChooser
 		fileChooser = new JFileChooser();
 		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 	    
 	     // Se configura el icono del this
 		this.setFrameIcon(new ImageIcon(getClass().getResource("/Imagenes/configBackup.gif")));
 	
        this.add(panel);
        panel.add(panelServidores);
  	    
  	    this.setVisible(true);
  	    
	    //Se buscan las rutas configuradas en la BD
	    buscarRuta();
	    
	    //Se buscan las direcciones IP
	    buscarHostBackup();
	    
	       // Se agrega un evento de ventana
 	   this.addInternalFrameListener( new InternalFrameAdapter() {
		    public void internalFrameClosing(InternalFrameEvent ife) {
			   cerrarInternal();
		    }
	   });
    
	   
	   
  	}
    
   //********************************************************************************
  	
  	private final void buscarRuta() {
  		
  		// Se crea la sentencia para buscar el valor del codigo dane
  		final String sentenciaSQL = "Select Valor "+
  		                            "From Parametros " +
  		                            "Where Codparam = 21";
  		                      
  		                                   
 		// Se valida que se halla digitado un nit
 		 try {
 		 
 		   // Se realiza la consulta en la base de datos para buscar la placa
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		    
 		   if (resultado!=null) 
	 		  
	 		  if (resultado.next()) {
	 		  
	 		  	TRuta.setText(resultado.getString(1));
	 		  	
	 		  }else {
	 		  
	 		    cJf.Mensaje("Se deben configurar los parametros para los backup","Error",JOptionPane.ERROR_MESSAGE);	 
	 		    TRuta.setText(fileChooser.getCurrentDirectory().getPath());
	 		  
	 		  }  
	 		   	  	    	   
	 		   	   	   	
	     } catch (Exception e) {
 		 
 		    cJf.Mensaje("Se deben los parametros para los backup","Error",JOptionPane.ERROR_MESSAGE);	 
 		    TRuta.setText(fileChooser.getCurrentDirectory().getPath());
 	     }     
  	
                   
  		
  	}
    
    //********************************************************************************
    
    private void cerrarInternal() {
	 	
	 	
	 	 
		        if (TRuta.getText().length()>0) {
		       	 
		       	          actualizar();
		 			
		 			
	 			 } else {
	 		
	 			    cJf.Mensaje("Especifique la ruta","Error",JOptionPane.ERROR_MESSAGE);              
	 		
	 			    TRuta.grabFocus();
		   	     }
		   	     
		 	    menu.habilitarMenu(true);
        
        
	        try {
		       	
		  		File outputFile = new File("GenerarBackupTransito.bat");
		    		
		    	outputFile.delete();
		    	
		   } catch (Exception e) {
		   	    
		   	    System.out.println("Error " +e);
		   	
		   }
	    	
	    	
	 }
	 
	 
  	//********************************************************************************
  	
  	private void actualizar() {
	  		
	  	try{
	  		  // Se crea el array para actualizar
	  		  String datos[] = new String[1];
	  		  
	  		  datos[0] = "Valor = '"+adicionaCaracter(TRuta.getText())+"'";
	  		  
	  		  final String condicion = " CODparam = 21";
	  		  
	  		  cJf.actualizar("PARAMETROS",datos,condicion,conMySQL,false);
	  		  
	  		  actualizarHostBackup("22","HOST1",THost1.getText());
	  		  
	  		  actualizarHostBackup("22","HOST2",THost2.getText());
	  		  
	  		  actualizarHostBackup("22","HOST3",THost3.getText());
	  		  
	  		    
	  		  conMySQL.commit();
	  		  
	  		  TRuta.setText(adicionaCaracter(TRuta.getText()));
		 
	  		 }catch (Exception e) {
		 	 	
		 	 	conMySQL.rollback();
		 	 	
		 	 	cJf.Mensaje("No se puede Actualizar en parametros","Error",JOptionPane.ERROR_MESSAGE);
		 	 }
  		 
  	}
  	
  	//********************************************************************************
  	
  	private void actualizarHostBackup(String codigoParametro,String nombreParametro, String host) throws Exception{
  		
  			
  		  // Se crea el array para actualizar
  		  String datos[] = new String[1];
  		  
  		  datos[0] = "Valor = '"+ host +"'";
  		  
  		  final String condicion = " CODparam = " + codigoParametro + " and NOMPARAM = '" + nombreParametro +"'" ;
  		  
  		  cJf.actualizar("PARAMETROS",datos,condicion,conMySQL,false);
  	
  		
  	}
  	 
  	//********************************************************************************
  	 
  	static private String adicionaCaracter(String ruta) {
  		
  		String rutaSalida = "";
  		
  		for (int i=0;i<ruta.length();i++) {
  			if (ruta.charAt(i)=='\\')
  			  rutaSalida += "/";
  			else
  			  rutaSalida += ""+ ruta.charAt(i);
  	   }
  		
  		return rutaSalida;
  		
  	}
  	
   //*********************************************************************************************************************
 	
 	private void crearCopiaSeguridad() {
 	 
        try {
 
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                    String fecha = formato.format(new Date());
                    
                    
                    String ruta = TRuta.getText();
                    
                    
                    if (ruta.endsWith("/"))
              	       ruta = ruta.substring(0,ruta.length() - 1);
              	
                    String ruta1 = ruta +"/Backup-"+fecha;
                    
                    File file = new File(ruta1);
                    
                    
                    if (file.exists())
                      deleteDir(file);
                    
                     file.mkdir();

		            ejecutaMysqlDump(ruta1);
		            
		            //Se configura la copia de seguridad
		            File dir = new File(ruta);
		            deleteDirs(dir);
		           
            
        } catch(Exception e) {
           cJf.Mensaje("Error al crear backup"+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    //*******************************************************************************************************************
    
    private boolean deleteDir(File dir) {
    
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
  
        return dir.delete();
    }
    
    
    //******************************************************************************************************************
    private  boolean deleteDirs(File dir) {
        if (dir.isDirectory()) {
            
            // Se obtienen los directorios y archivos
            String[] children = dir.list();
            
            // Se crea un obejto de tipo vector para alamacenar
            // los directorios de backaup
            Vector < String  > vector = new Vector < String  > ();
            
            for (int i=0; i<children.length; i++) 
              
              if (children[i].length() >=6)
              
               if (children[i].substring(0,6).equals("Backup"))
                  
                   vector.addElement(children[i]);
            
            
            
            if (vector.size() > 3 )
              for (int i=0; i<vector.size() - 3; i++) {
                   
                    boolean success = deleteDir(new File(dir, vector.elementAt(i)));
	               
	                if (!success) {
	                    return false;
	                }
	                
	          }     
            
        }
  
        return dir.delete();
    }
    
    //********************************************************************************
    
    private boolean ejecutaMysqlDump(String ruta)
    {
    	
    	boolean resultadoBoolean = false;
    	
    	try{
    		
    			String Servidor1="";
    			String Servidor2="";
    			String Servidor3="";
    			
    		    
    			//copia de seguridad
        		char chars[] = {'"'};

       			Servidor1=chars[0]+"C:/Archivos de programa/MySQL/MySQL Server 5.0/bin/mysqldump"+chars[0]+" --opt --password=sof.sit? --user=root --routines -h  "+ THost1.getText() +" transitoOpt > "+chars[0]+cambiarCaracter(ruta)+"/BD_Transito_Host1.sql"+chars[0];
       			
       			if (CkHost2.isSelected()) 
       				Servidor2 = chars[0]+"C:/Archivos de programa/MySQL/MySQL Server 5.0/bin/mysqldump"+chars[0]+" --opt --password=sof.sit? --user=root --routines -h "+THost2.getText()+" transitoOpt > "+chars[0]+cambiarCaracter(ruta)+"/BD_Transito_Host2.sql"+chars[0];
       			

       			if (CkHost3.isSelected()) 
       				Servidor3=chars[0]+"C:/Archivos de programa/MySQL/MySQL Server 5.0/bin/mysqldump"+chars[0]+" --opt --password=sof.sit? --user=root --routines -h "+THost3.getText()+" transitoOpt > "+chars[0]+cambiarCaracter(ruta)+"/BD_Transito_Servidor3.sql"+chars[0];
       		
       			
       			String Sentencia=Servidor1+"\n\n\n \n"+Servidor2+"\n"+Servidor3+" \n exit";
       			
       			File outputFile = new File("GenerarBackupTransito.bat");
       			
       			outputFile.deleteOnExit();
        		
        		FileOutputStream fos = new FileOutputStream(outputFile);
        	
        		fos.write(Sentencia.getBytes());
        		
        		fos.close();
        		
        		Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL GenerarBackupTransito.bat").waitFor();
        		
        		resultadoBoolean = true;
        		
        		
        		
        
         }catch (Exception e) {
	    	e.printStackTrace();
	    	
	    }
	    
	    
	    
	    return resultadoBoolean;
		
    }
    
    //********************************************************************************
    
    static  private String cambiarCaracter(String ruta) {
    
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
    	
    final private void buscarHostBackup() {
  		
  		// Se crea la sentencia para buscar el valor del Código dane
  		final String sentenciaSQL = "Select Valor "+
  		                            "From Parametros " +
  		                            "Where Codparam = 22 "+
  		                            "Order by NomParam";
  		                      
  		                                   
 		
 		 try {
 		 
 		   // Se realiza la consulta en la base de datos para buscar la placa
 		   ResultSet resultado = conMySQL.buscarRegistro(sentenciaSQL);
 		    
 		   if (resultado.next()) {
 		   	 
 		   	   THost1.setText(resultado.getString(1));
 		   	   resultado.next();
 		   	   THost2.setText(resultado.getString(1));
 		   	   resultado.next();
 		   	   THost3.setText(resultado.getString(1));
 		   	   
 		   	   
 		   	   if (THost2.getText().length() > 0) {
 		   	   
 		   	     CkHost2.setSelected(true);
 		   	     THost2.setEditable(true);
 		   	     THost2.setFocusable(true);
 		   	     
 		   	   }  
 		   	     
 		   	   if (THost3.getText().length() > 0) {
 		   	   
 		   	     CkHost3.setSelected(true);
 		   	     THost3.setEditable(true);
 		   	     THost3.setFocusable(true);
 		   	     
 		   	   }     
 		   	
 		   }
	 		   	  	    	   
	 		   	   	   	
	     } catch (Exception e) {
 		 
 		    cJf.Mensaje("Se deben configurar los host para lso Backup","Error",JOptionPane.ERROR_MESSAGE);	 
 		    TRuta.setText(fileChooser.getCurrentDirectory().getPath());
 	     }     
  		
  	}
  	
  	//********************************************************************************
    
    public void actionPerformed(ActionEvent act) {
 		
 		Object fuente = act.getSource();
 		
 		if (fuente == BCopia) {
 			
 			if (CkHost2.isSelected()) 
 			{
 				if(THost2.getText().length()>0)
 				{
 					if (CkHost3.isSelected()) 
 					{
 						if(THost3.getText().length()>0)
 						{
 							crearCopiaSeguridad();
 						}
 						else
 						{
 							cJf.Mensaje("Digite el Servidor Nº 3","Error",JOptionPane.ERROR_MESSAGE);
 							THost3.grabFocus();
 						}
 						 						
 					}
 					else
 					crearCopiaSeguridad();
 					
 				}
 				else
 				{
 					cJf.Mensaje("Digite el Servidor Nº 2","Error",JOptionPane.ERROR_MESSAGE);
 					THost2.grabFocus();
 				}
 			}
 			else
 			  if(CkHost3.isSelected()) 
 				{
 					if(THost3.getText().length()>0)
 					{
 						crearCopiaSeguridad();
 					}
 					else
 					{
 						cJf.Mensaje("Digite el Servidor Nº 3","Error",JOptionPane.ERROR_MESSAGE);
 						THost3.grabFocus();
 					}
 						 						
 				}
 				else
 				crearCopiaSeguridad();
 				
 			
 		
 		}
 		else
 		if (fuente == BRuta) {
 			
 			int opcion = fileChooser.showDialog(this,"Aceptar");
 			
 			 if (opcion == JFileChooser.APPROVE_OPTION) 
                 TRuta.setText(fileChooser.getSelectedFile().getPath());
 		} else
 		   if (fuente == BSalir) {
 		   	  
 		      cerrarInternal();
 		      dispose();
 		      
 		   }else
 		   	  if (fuente==CkHost2) 
		 	  {	
					if (CkHost2.isSelected()) 
					{
						THost2.setEditable(true);
 	          			THost2.setFocusable(true);
 	          			THost2.grabFocus();
			 		}
			 		else
			 		{
			 			THost2.setEditable(false);
 	         			THost2.setFocusable(false);
 	         			THost2.setText("");
			 		}
			 }
			 else
			   if (fuente==CkHost3) 
		 	   {	
					if (CkHost3.isSelected()) 
					{
						THost3.setEditable(true);
 	          			THost3.setFocusable(true);
 	            		THost3.grabFocus();
			 		}
			 		else
			 		{
			 			THost3.setEditable(false);
 	         			THost3.setFocusable(false);
 	         			THost3.setText("");
			 		}
			  }
 		   	
 		   
 		   
 		   
 		      
 	} 
 		
 	//********************************************************************************
    
  	public void focusGained(FocusEvent F) {
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());
 	}
 	
 	
 	//********************************************************************************
    
 	public void focusLost(FocusEvent F) {
 		
 			
 	   if (F.getComponent() == THost1 )	{
 	   	
 	   	   try {
 	   	   	   
 	   	   	   InetAddress.getByName(THost1.getText());
 	   	   	
 	   	   } catch (Exception e) {
 	   	   	
 	   	   	   cJf.Mensaje("Direccion Invalida","Error",JOptionPane.ERROR_MESSAGE);
 	   	   	   
 	   	   	   THost1.setText("");
 	   	   	   
 	   	   	   THost1.grabFocus();
 	   	   	
 	   	   }
 	   	
 	   	
 	   }
 	   
 	   
 	    if (F.getComponent() == THost2 )	{
 	   	
 	   	   try {
 	   	   	   
 	   	   	   InetAddress.getByName(THost2.getText());
 	   	   	
 	   	   } catch (Exception e) {
 	   	   	
 	   	   	   cJf.Mensaje("Direccion Invalida","Error",JOptionPane.ERROR_MESSAGE);
 	   	   	   
 	   	   	   THost2.setText("");
 	   	   	   
 	   	   	   THost2.grabFocus();
 	   	   	
 	   	   }
 	   	
 	   }	
 	   
 	   
 	   
 	    if (F.getComponent() == THost3 )	{
 	   	
 	   	   try {
 	   	   	   
 	   	   	   InetAddress.getByName(THost3.getText());
 	   	   	
 	   	   } catch (Exception e) {
 	   	   	
 	   	   	   cJf.Mensaje("Direccion Invalida","Error",JOptionPane.ERROR_MESSAGE);
 	   	   	   
 	   	   	   THost3.setText("");
 	   	   	   
 	   	   	   THost3.grabFocus();
 	   	   	
 	   	   }
 	   	
 	   	
 	   }	
 		
 	    // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(cJf.getVisualAtributoPierdeFocoComponentes());
 		
 	
 		
 	}
 	

    
 	
 
}