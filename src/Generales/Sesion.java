/**
 * Clase: Sesion
 *
 * @version  1.0
 *
 * @since 18-07-2005
 *
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */


import java.awt.AWTKeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

import java.util.HashSet;

import java.util.Set;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;




/*
 * Esta clase permite capturar el userID y password
 */


final class Sesion extends JDialog implements ActionListener {
	
	  
   //Se especifica el serial para la serializacion
   static final long serialVersionUID = 19781220;
  
   //Casillas de texto
   protected JTextField	jtUsuario;
   
   //Casillas de texto para capturar password
   protected JPasswordField jtPassword;
   
   //Botones
   private JButton jbAceptar;
   
   //Referencia al MenuPrincipal 
//   private MenuPrincipal menuPrincipal;
  
   //Constructor
   
   public Sesion() {
	  
	  super("Sesión",true);
	  
	  menuPrincipal = p_menuPrincipal;
	  
      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
     
	  // Se instancia y configura el marco
  	  this.setBounds((size.width - 300)/2,(size.height - 200)/2,300,200);
  	  this.setResizable(false);
  	  this.getContentPane().setLayout(null);
  	  
	  // Se instancia un panel
  	  JPanel panel = new JPanel();
 	  panel.setLayout(null);
 	  panel.setBorder(new EtchedBorder());
 	  panel.setBounds(30,22,230,90); 
 	  this.add(panel);  
 	  
 	  // Se configuran las etiquetas
 	  JLabel LUsuario = new JLabel("Usuario : ");
 	  LUsuario.setBounds(30,20,80,20);
 	  panel.add(LUsuario);
 		
 	  JLabel LPassword = new JLabel("Password : ");
 	  LPassword.setBounds(30,50,80,20);
 	  panel.add(LPassword);
 	
 	  Set < AWTKeyStroke > upKeys = new HashSet < AWTKeyStroke >(1);
      upKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_ENTER,0));
      upKeys.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB,0));
     
 	
  	  // Se configura el JTexField
 	  jtUsuario = new JTextField();
	  jtUsuario.setBounds(105,20,90,20);
	  jtUsuario.setToolTipText("Digite el nombre de usuario");
	  jtUsuario.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,upKeys);
  	
	  panel.add(jtUsuario);
     
  	  ActionListener showSwingWorkerDialog = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             
                jbAceptar.doClick();
                
            }
      };
        
      
  	  jtPassword = new JPasswordField();
  	  jtPassword.selectAll();
	  jtPassword.setBounds(105,50,90,20);
	  
	  // Se configura para que se pueda navegar con Enter o Tab
      Set < AWTKeyStroke > upKeys1 = new HashSet < AWTKeyStroke >(1);
      upKeys1.add(AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_TAB,0));
      jtPassword.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,upKeys1);
  	  jtPassword.addActionListener(showSwingWorkerDialog);
	  panel.add(jtPassword);
      
      // Se configura los botones
  	  jbAceptar = new JButton("Aceptar",new ImageIcon(getClass().getResource("/Imagenes/OK.gif")));
  	  jbAceptar.setBounds(30,127,110,22);
	  jbAceptar.setToolTipText("Aceptar");
	  jbAceptar.setMnemonic('A');
	  jbAceptar.addActionListener(this);
	  this.add(jbAceptar);
  	  
  	  // Se configura los botones
  	  JButton jbCancelar = new JButton("Cancelar",new ImageIcon(getClass().getResource("/Imagenes/NO.gif")));
  	  jbCancelar.setBounds(150,127,110,22);
	  jbCancelar.setToolTipText("Cancelar");
	  jbCancelar.setMnemonic('C');
	  jbCancelar.addActionListener(this);
	  this.add(jbCancelar);
  	  
  	   // Se configura el icono del JInternal
	  this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/llave16.gif")).getImage());
 	 
  	
	}
	
	//*****************************************************************************
	public void limpiar() {
		
		jtUsuario.setText("");
        jtPassword.setText("");
        jtUsuario.grabFocus();
	}
	
	//*****************************************************************************
	
	public void conectarBS() {
	  
	    if (!menuPrincipal.validarUsuario(jtUsuario.getText(),jtPassword.getPassword())) {
	       
	       JOptionPane.showMessageDialog(this,"Verificar Password y/o usuario ","Error",JOptionPane.ERROR_MESSAGE);
	  	   limpiar();
	  	
	  	}  
	   	 
	    	
	}
	
	//*****************************************************************************
	
	public void actionPerformed(ActionEvent a) {
		
		if (a.getActionCommand().equals("Cancelar")) 
			 System.exit(0);
		else
		 if (a.getActionCommand().equals("Aceptar")) 
			  conectarBS();
			
			    
	}
	
}	