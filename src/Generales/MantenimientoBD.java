/*
 * Clase:MantenimientoBD
 *
 * Version : 01
 *
 * Fecha: 27-10-2006
 *
 * Copyright: JASoft
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/*
  * Esta clase se utiliza para hacer mantenimiento a la base de datos
  */
  
  final class MantenimientoBD extends JInternalFrame implements ActionListener{
  	
  	//Boton
	private JButton BEjecutorias;
	
	//JCheckBox
	private JCheckBox CkOptimizar;
	private JCheckBox CkAnalizar;
	
	//Referencia del MenuPrincipal
 	private MenuPrincipal menu;
    
    //Constructor utilizado deste el menu principal	    
   	public MantenimientoBD(MenuPrincipal p_menu){ 
  	    
  	    super("Utilidad para hacer Mantenimiento a la BD",true, true, false, false);
    	
    	setBounds(250,200,350,150);
    	
    	setLayout(null);
    	
  	
    	menu = p_menu;
    	
    	CkOptimizar = new JCheckBox("Optimizar",true);
		CkOptimizar.setBounds(80,20,110,20);
		CkOptimizar.addActionListener(this);
		this.add(CkOptimizar);
		
		CkAnalizar = new JCheckBox("Analizar",true);
		CkAnalizar.setBounds(190,20,110,20);
		CkAnalizar.addActionListener(this);
		this.add(CkAnalizar);
		
		
		
		BEjecutorias = new JButton("Mantenimiento BD",new ImageIcon(getClass().getResource("/Imagenes/OK.gif")));
		BEjecutorias.setBounds(70,80,200,20);
		BEjecutorias.addActionListener(this);
		this.add(BEjecutorias);

  	    
  	   	this.setFrameIcon(new ImageIcon(getClass().getResource("/Imagenes/MantenenimientoDB.gif")));

  	    this.setVisible(true);
  	    
 
	   // Se agrega un evento de ventana
 	   this.addInternalFrameListener( new InternalFrameAdapter() {
		    public void internalFrameClosing(InternalFrameEvent ife) {

			   menu.habilitarMenu(true);

		    }
	   });

  	   
  	
  	}
   	
   //**********************************************************************************
   	
   public void actionPerformed(ActionEvent act) {
 		
 		Object fuente = act.getSource();
 		
 		if(fuente==BEjecutorias)
 		{
 			String Opcion=" -c";
 			
 			if(CkAnalizar.isSelected())
 			  Opcion=Opcion+" -a";
 			
 			if(CkOptimizar.isSelected())
 			  Opcion=Opcion+" -o";
 			
 			int opcion = JOptionPane.showConfirmDialog(this,"Se recomienda hacer una copia de seguridad antes de hacer un mantenimiento a la BD\n                                               Desea Continuar?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);                                       
			if(opcion==0)
			{
 			 try {
	    		    Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL " + "C:/Archivos de programa/MySQL/MySQL Server 5.0/bin/mysqlcheck "+Opcion+" transito -u T_rans_ito -pT_rans_ito");
	    		 }  catch (Exception e) {
	    		 	e.printStackTrace();
	    		 }
	    	}	
 		}

 	} 

   
}