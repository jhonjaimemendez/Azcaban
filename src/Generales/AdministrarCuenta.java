/*
 * Clase: AdministrarCuenta
 *
 * Version : 1.0
 *
 * Fecha: 27-10-2006
 *
 * Copyright: JASoft
 */


import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.KeyboardFocusManager;


import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

 /*
  * Esta clase se utiliza para hacer Administrar Cuentas de usuario
  */
  
  final class AdministrarCuenta extends JInternalFrame implements ActionListener,FocusListener{
  	
  	//Referencia de conexion a la base de datos
 	private ConectarMySQL conMySQL;
    
    //Referencia al componente CrearJFrame
  	private CrearJFrame cJf;
 
  	//Casillas de Texto
	private JTextField TNombre;
  	private JTextField campos[]; //Almacena  los campos obligatorios
  	
    //Casillas de texto para password
  	private JPasswordField TNuevoPassword;
  	private JPasswordField TConfirmarNuevoPassword;
  	private JPasswordField TPasswordAnterior;
  	
  	//Botones
 	private JButton Bguardar;
    private JButton Bsalir;
  	
  	//Referencia al MenuPrincipal
 	private MenuPrincipal menu;
 
    //Constructor para el menu principal 
   	public AdministrarCuenta(ConectarMySQL p_conMySQL,MenuPrincipal p_menu,String usuario){ 
  	    
  	    super("Administrar Cuenta",true, true, false, false);
    	
    	setBounds(100,150,600,205);
    	
    	setLayout(null);
    	
    	menu = p_menu;
    	
    	conMySQL = p_conMySQL;
    	
        cJf = new CrearJFrame(); 
		
		
  	    // Se instancia un panel
  	    JPanel panel = cJf.getJPanel(20,20,550,95);
 		         	  
  	    // Se configuran las etiquetas
  	    JLabel LNombre =cJf.getJLabel("Nombre : ",20,25,200,20);
 		panel.add(LNombre); 				 		
 		
 		JLabel LAnteriorPassword = cJf.getJLabel("Anterior Password : ",245,25,200,20);
 		panel.add(LAnteriorPassword);
 	   
 		
        JLabel LCPassword =cJf.getJLabel("Confirmar Password : ",280,60,200,20);
 		panel.add(LCPassword);
 		
 		JLabel LNuevoPassword = cJf.getJLabel("Nuevo Password : ",20,60,200,20);
 		panel.add(LNuevoPassword);
 	    
  	    
  	    // Se crea el array de JTextField
  	    TNombre = cJf.getJTextField(usuario,80,25,120,20,"Nombre de usuario","40");
 		TNombre.setEditable(false);
 	    TNombre.setFocusable(false);
 	    panel.add(TNombre);
 		
 		
 		TPasswordAnterior = cJf.getJPasswordField("Digite el password anterior",365,25,120,20,"20");
 	    TPasswordAnterior.addFocusListener(this);
 	    panel.add(TPasswordAnterior);
 		
  	    TNuevoPassword = cJf.getJPasswordField("Digite el nuevo password ",125,60,120,20,"20");
 	    TNuevoPassword.addFocusListener(this);
 	    panel.add(TNuevoPassword);
  	    
  	    TConfirmarNuevoPassword = cJf.getJPasswordField("Digite la confirmacion del password de usuario",410,60,120,20,"20");
 		TConfirmarNuevoPassword.addFocusListener(this);
 		TConfirmarNuevoPassword.addActionListener(this);
 	    panel.add(TConfirmarNuevoPassword);
 
		
		Bguardar = new JButton(" Guardar",new ImageIcon(getClass().getResource("/Imagenes/Guardar.gif")));
  		Bguardar.setToolTipText("Guardar Cambios");
  		Bguardar.setMnemonic('G');
  		Bguardar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/GuardarS.gif")));
  		Bguardar.setBounds(120,125,120,35);
  		Bguardar.addActionListener(this);
  		this.add(Bguardar);
 
  		
  		Bsalir = new JButton(" Salir",new ImageIcon(getClass().getResource("/Imagenes/Salir.gif")));
  		Bsalir.setToolTipText("Salir");
  		Bsalir.setMnemonic('S');
  		Bsalir.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/SalirS.gif")));
  		Bsalir.setBounds(320,125,120,35);
  		Bsalir.addActionListener(this);
  		this.add(Bsalir);
		
  	    this.add(panel);
  	    
  	   	this.setFrameIcon(new ImageIcon(getClass().getResource("/Imagenes/Admin.gif")));

  	    this.setVisible(true);
  	    
  	    TPasswordAnterior.grabFocus();
  	    
  	    // Se crea el array de los JTextField obligatorios
 		CreaArrayJT();
 		
 		 //Se especifica para que se pueda navegar con enter y con Tan
  	    this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,cJf.getUpKeysFrame());
 	
	   // Se agrega un evento de ventana
 	   this.addInternalFrameListener( new InternalFrameAdapter() {
		    public void internalFrameClosing(InternalFrameEvent ife) {

			   menu.habilitarMenu(true);

		    }
	   });

  	   
  	
  	}
   	
   	//***********************************************************************************
   
 	private boolean confirmarPasswordAnterior() {
 	  
 	     boolean resultadoBoolean = false;
 	     
  	     String sentenciaSQL = "Select  'x' "+
 		                       "From  Usuarios "+
 		                       "Where ID = '"+ TNombre.getText() +"' and "+
 		                              "Password = '"+ new String(TPasswordAnterior.getPassword())  +"'";
 		                       
 	    
 		// Se valida que se halla digitado un nombre
 		 try {
 		 
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado =  conMySQL.buscarRegistro(sentenciaSQL);
 		  
 		   if (resultado!=null) 
 		       
 		       if (resultado.next())  
 		       	   
 		       	   resultadoBoolean = true;
 		       	 
 		       
 		       	    
 		  
 	    } catch (Exception e) {
 	    	
 		   cJf.Mensaje("Error en la busqueda de usuario","Error",JOptionPane.ERROR_MESSAGE);
 	    
 	    }     	
  		
  		
  		return resultadoBoolean;
    }	
    
    //*********************************************************************************
  	
  	private void CreaArrayJT() {
  		
  		// Se crea un array con los JTextField obligatorios
  		campos = new JTextField[4];
 		campos[0] = TNombre;
 		campos[1] = TPasswordAnterior;
 		campos[2] = TNuevoPassword;
 		campos[3] = TConfirmarNuevoPassword;
 	
 	}
  	
  	
  	//*********************************************************************************
   private void limpiar() {
 	
 	  for (int i = 1; i < 4; i++)  
 	    campos[i].setText("");
 	  
 	   TPasswordAnterior.grabFocus();
 	
 	  
 	 
    }
    
    
    //*********************************************************************************
    private void actualizar() {
 	   
 	   	try {
 	   	
	 		  // Se actualiza la tabla propietarios
	 		  String condicion = "ID = '"+ TNombre.getText() +"'";
	 		  	
	 		  String datos[] = new String[1];
	 		  	
	 		  datos[0] = "password = '" + new String(TNuevoPassword.getPassword())+"'";
	 		  
	 		  cJf.actualizar("Usuarios",datos,condicion,conMySQL,false);
	 		 
 		      //Se hacen permanentes los cambios
 	          conMySQL.commit();
 	          
 	          cJf.Mensaje("Cambio De Clave Exitoso","Información",JOptionPane.INFORMATION_MESSAGE);
 	        
			  limpiar();
 		  
		  	  
	 	 } catch (Exception e) {
	 	 	
	 	 	conMySQL.rollback();
	 	 	
	 	 	cJf.Mensaje("Error al actualizar cuenta de usuario"+e,"Error",JOptionPane.ERROR_MESSAGE);
	 	 	
	 	 }  	  	 
 		  
 	   
 	}
 	
 	//***********************************************************************************
 	
 	public void actionPerformed(ActionEvent act) {
 		
 		Object fuente = act.getSource();
 		
 		  	if (fuente==Bguardar) {
 		  		
 		  		     if (confirmarPasswordAnterior()) {
 		  		     
	 		  		         int opcion = JOptionPane.showConfirmDialog(this,"Desea realizar el cambio de clave?","Confirmación",
		 	                                              JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
		 	                                              
		 	                 if (opcion==0) {  
		 	                
	 		  	                   if (cJf.validarRegistro(campos)) {
	 		  	   	  
						 		  	   	  String password = new String(TNuevoPassword.getPassword());
						 		  	   	  String cpassword = new String(TConfirmarNuevoPassword.getPassword());
						 		  	   	  
						 		  	   	  if (password.equals(cpassword)) 
						 		  	   	        actualizar();
							 			  else
						 		  	   	        
						 		  	   	    cJf.Mensaje("Confirmación de clave erronea","Error",JOptionPane.ERROR_MESSAGE);
						 		  	   	     
						 		  	   }
						 	   }	  	   
 		  	          
 		  	          } else {
 		  	          	
 		  	          	  cJf.Mensaje("Password anterior invalido","Error",JOptionPane.ERROR_MESSAGE);
 		  	          	  TPasswordAnterior.grabFocus();
 		  	          	  
 		  	          }
 		  	     
 		  	  
	 		 } else
	 		       if (fuente==Bsalir) {
 			           
 			           try{
 			           
 			               menu.habilitarMenu(true);
 			               
 			               dispose();
 			               
 			          }catch(Exception e){}
 			          
 		           } else
	 		          if (fuente == TConfirmarNuevoPassword) {
	 		          	 //Se ejecuta el evento del boton guardar
	 		          	 Bguardar.doClick();
	 		          	
	 		          }	
 		
 		
 	
 	} 
 	
 	//***********************************************************************************
 	
 	public void focusGained(FocusEvent F) {
 	    
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());
 	}
 	
 
 	//***********************************************************************************
 	
 	public void focusLost(FocusEvent F) {
 		
 	    // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(cJf.getVisualAtributoPierdeFocoComponentes());
 	}
 	
 	

   
}