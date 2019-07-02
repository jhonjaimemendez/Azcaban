
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
import java.awt.event.*;


import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;

import javax.swing.event.*;
 
import java.util.Vector;
import java.awt.Toolkit;

import com.JASoft.componentes.ConectarMySQL;

import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;

final public class Caja extends JDialog implements ActionListener, FocusListener {
  
  //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;
    
    private CrearJFrame cJf = new CrearJFrame();
    
    private MenuPrincipal menuPrincipal;
    
    //** Se declaran los JTextField
    private  JTextField jtSaldoCaja;
    
    
    //Se declaran los JButton
    private JButton jbInyectarCapital;
    private JButton jbRetirarCapital;
    
    //Se declaran los JPanel
    private JPanel panelCaja;
    
    //String
    private String nombreUsuario;
    
    //Long
    private long totalCaja;
    
     //** Constructor General 
    public Caja(MenuPrincipal p_menuPrincipal, ConectarMySQL conectarMySQL,Long totalCaja,String nombreUsuario) {
    
       super(p_menuPrincipal.crearJFrameParaMenu.getJFrame(),"Caja",true);
       
       this.totalCaja = totalCaja;
       
       this.menuPrincipal = p_menuPrincipal;
       
       this.conectarMySQL = conectarMySQL;
       
       this.nombreUsuario = nombreUsuario;
       
       Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
     
	  // Se instancia y configura el marco
  	   this.setBounds((size.width - 400)/2,(size.height - 225)/2,400,225);
  	   this.setResizable(false);
  	   this.setLayout(null);
	    
       panelCaja = cJf.getJPanel("Saldo Actual en Caja",50, 30, 295, 75,14);
       this.add(panelCaja);
        
       Font font =  new Font("Dialog", 0, 12);
         
       //Se agregan los JLabel
       JLabel jlFecha = cJf.getJLabel(cJf.getObtenerFecha(conectarMySQL),275, 10, 70, 15);
       jlFecha.setFont(font);
       this.add(jlFecha);

       JLabel jlDireccion = cJf.getJLabel("Fecha :",225, 10, 50, 15);
       jlDireccion.setFont(font);
       this.add(jlDireccion);

       //** Se agregan los JButton
       jbInyectarCapital = cJf.getJButton("Inyectar Capital",50, 145, 125, 25,"","");
       jbInyectarCapital.addActionListener(this);
       this.add(jbInyectarCapital);
      
      
       jbRetirarCapital = cJf.getJButton("Retirar Capital",220, 145, 125, 25,"","");
       jbRetirarCapital.addActionListener(this);
       this.add(jbRetirarCapital);
       
       jtSaldoCaja = cJf.getJTextField("" + totalCaja,72, 30, 150, 30,"Digite el Número de Identificación del Vendedor","12");
       jtSaldoCaja.setHorizontalAlignment(JTextField.RIGHT);
       jtSaldoCaja.addFocusListener(this);
       jtSaldoCaja.setFocusable(false);
       jtSaldoCaja.setEditable(false);
       jtSaldoCaja.addKeyListener(cJf.getValidarEntradaNumeroJTextField());
       jtSaldoCaja.setFont(new Font("Dialog", 0, 18));
       panelCaja.add(jtSaldoCaja);
       
       
       addWindowListener(new WindowAdapter() {
 	    
 	    	public void windowClosing(WindowEvent awt) {
 	    	
 	    	  	menuPrincipal.buscarCaja();
 	    	}
 	    });
       
       this.setVisible(true);
       
                     
 	   
       

    } 
    
    
   //******************************************************************************************************************

   public void buscarCaja() {
   	  
   	    try {
   	    
        	final String sentenciaSQL = "Select ValorCaja "+
        	                            "From MovimientosCaja "+
        	                            "Order by NumeroTransaccion desc " +
        	                            "Limit 1  ";
                                                             
           	ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
        	
        	if (resultado !=null) {
             
              if (resultado.next()) {
              	
              	totalCaja = resultado.getLong(1);
              	System.out.println(totalCaja);
              	jtSaldoCaja.setText("" + totalCaja);
              	
              	
              }
            
            }  
        
       
      }  catch(Exception e) {
          
            cJf.Mensaje("Error al buscar valor de caja"+e,"Error",JOptionPane.ERROR_MESSAGE);
      }    
		 		
   	
   }
   
   //************************************************************************************************************************
    
    public void actionPerformed(ActionEvent a) {
    	
    	if (a.getSource() == jbInyectarCapital) {
    	  
    	   new CajaInyectar(menuPrincipal,conectarMySQL,totalCaja,nombreUsuario,this);
    	   
    	} else
    	   
    	   if (a.getSource() == jbRetirarCapital) {
    	  
    	      new CajaRetirar(menuPrincipal,conectarMySQL,totalCaja,nombreUsuario,this);
    	     
    	  }
    }

    
     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 

       // se coloca el atributo visual por defecto
       f.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());

     }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      }
      
      } 