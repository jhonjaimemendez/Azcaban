/**
 * Clase: AyudaTeclado
 *
 * @version  1.0
 *
 * @since 29-10-2005
 *
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
 
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;


 /*
  * Esta clase permite mostrar la configuracion del teclado
  */
  
final class AyudaTeclado extends JInternalFrame {
  	
    //Referencia al MenuPrincipal
    private MenuPrincipal menu;
   
    
    //Constructor utilizado deste el menu principal	
  	public AyudaTeclado(MenuPrincipal p_menu){ 
  	    
  	    super("ayuda Teclado",true, true, false, false);
    	
    	menu = p_menu;
    	
    	setBounds((p_menu.getWidth() - 600)/2,(p_menu.getHeight() - 440)/2,600,440);
    	
    	setLayout(null);
    	
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	
    	JLabel LF1 =    getJLabel("F1        :   Muestra ayuda ",20,20,200,20);
    	JLabel LF9 =    getJLabel("F9        :   Muestra lista valores asociada a una casilla de texto ",20,45,380,20);
    	JLabel LTab =   getJLabel("Tab      :   Pasa el foco al siguiente componentes activado",20,70,340,20);
    	JLabel LEnter = getJLabel("Enter   :   Pasa el foco al siguiente componente activado si no hay ninguna lista de valores visibles," ,20,95,580,20);
    	JLabel LEnter1 =getJLabel("                en caso contrario pasa el foco a la lista de valores",20,120,400,20);
    	JLabel LEsc =   getJLabel("Esc      :  Cierra ayuda u oculta una lista de valores" ,20,145,400,20);
    	JLabel LAltS =   getJLabel("Alt + S :  Cierra la interfaz grafica" ,20,170,400,20);
    	JLabel LAltU =   getJLabel("Alt + U :  Cambia de Usuario" ,20,195,400,20);
    	JLabel LAltG =   getJLabel("Alt + G :  Inserta o actualiza valores en la Base De Datos " ,20,220,400,20);
    	JLabel LAltB =   getJLabel("Alt + B :  Busca información en la Base De Datos " ,20,245,400,20);
    	JLabel LAltL =   getJLabel("Alt + L :  Limpia los datos digitados en un formulario " ,20,270,400,20);
    	JLabel LAltE =   getJLabel("Alt + E :  Elimina un registro de la Base de Datos " ,20,295,400,20);
    	JLabel LAltI =   getJLabel("Alt + I  :  Imprime documentos.. " ,20,320,400,20);
    	
    	 //Se adiciona un boton
	  	 JButton BAceptar = new JButton("Aceptar");
         BAceptar.setBounds(260,365,100,20);
	  	 BAceptar.setMnemonic('A');
	  	 BAceptar.addActionListener( new ActionListener() {
	  	  	
	  	  	public void actionPerformed(ActionEvent a) {
	  	  		
	  	  		cerrarInternal();
		        dispose();
	  	  		
	  	  	}
  	    });
  	    this.add(BAceptar);
  	  
  	  
  	  	// Se configura el icono del this
		this.setFrameIcon(new ImageIcon(getClass().getResource("/Imagenes/Admin.gif")));
 		
 		this.setVisible(true);
 		
 			
 		 // Se agrega un evento de ventana
 	   this.addInternalFrameListener( new InternalFrameAdapter() {
		    public void internalFrameClosing(InternalFrameEvent ife) {
			   cerrarInternal();
		    }
	   });
    	
  	}
  	
  	
  	private void cerrarInternal() {
	    
	     menu.habilitarMenu(true);
	
 	 }
  
	
	 private JLabel getJLabel(String texto,int x, int y, int ancho, int largo) {
  		
  		JLabel label = new JLabel(texto);
 		label.setBounds(x,y,ancho,largo);
 		this.add(label);
 		  
 	    return label;
 	    
  	}
  	
 	
   
}