
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

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;

import java.util.Vector;
import java.awt.Toolkit;

import java.awt.event.*; 

import com.JASoft.componentes.ConectarMySQL;

import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;

final public class CajaRetirar extends JDialog implements ActionListener, FocusListener {
  
  //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;
    
    private CrearJFrame cJf = new CrearJFrame();
    
    //** Se declaran los JTextField
    private  JTextField jtValorInyectar;
    private  JTextField jtDescripcion;
    
    //Se declaran los JButton
    private JButton jbAceptar;
    private JButton jbCancelar;
    
    //Se declaran los JPanel
    private JPanel panelCajaInyectar;
    
    //Long
    private long totalCaja;
    
    //JSeparator
    private JSeparator jSeparator1 = new JSeparator();
       
     //String 
    private String nombreUsuario;   
    
    private  Caja caja;
   
   
      //** Constructor General 
    public CajaRetirar(MenuPrincipal p_menuPrincipal, ConectarMySQL conectarMySQL,Long totalCaja,String nombreUsuario,Caja caja) {
      
       super(p_menuPrincipal.crearJFrameParaMenu.getJFrame(),"Retirar Capital",true);
       
       this.totalCaja = totalCaja;
       
       this.nombreUsuario = nombreUsuario;
       
       this.caja = caja;
       
       this.conectarMySQL = conectarMySQL;
       
       Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	
	    // Se instancia y configura el marco
  	   this.setBounds((size.width - 400)/2,(size.height - 225)/2,400,225);
  	   this.setResizable(false);
  	   this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
  	   this.setLayout(null);
	   
	   
	   panelCajaInyectar = cJf.getJPanel("Saldo Actual en Caja",50, 30, 295, 80,14);
       this.add(panelCajaInyectar);
        
       
       //Se agregan los JLabel
       JLabel jlCodigo = cJf.getJLabel("Valor",35, 23, 45, 15);
       jlCodigo.setForeground(new Color(148, 0, 0));
       panelCajaInyectar.add(jlCodigo);

       JLabel jlDescripci�n = cJf.getJLabel("Descripci�n",5, 48, 75, 15);
       jlDescripci�n.setForeground(new Color(148, 0, 0));
       panelCajaInyectar.add(jlDescripci�n);
   
       
        //** Se agregan los JButton
       jbAceptar = cJf.getJButton("Aceptar",50, 145, 125, 25,"","");
       jbAceptar.addActionListener(this);
       this.add(jbAceptar);
      
      
       jbCancelar = cJf.getJButton("Cancelar",220, 145, 125, 25,"","");
       jbCancelar.addActionListener(this);
       this.add(jbCancelar);
       
       jtDescripcion = cJf.getJTextField("" ,90, 45, 195, 20,"Digite el N�mero de Identificaci�n del Vendedor","30");
       jtDescripcion.setForeground(new Color(148, 0, 0));
      jtDescripcion.addFocusListener(this);
       jtDescripcion.addKeyListener(cJf.getConvertirMayuscula());
       panelCajaInyectar.add(jtDescripcion);
       
       
       jtValorInyectar = cJf.getJTextField("" ,90, 20, 75, 20,"Digite el N�mero de Identificaci�n del Vendedor","12");
       jtValorInyectar.setForeground(new Color(148, 0, 0));
       jtValorInyectar.setHorizontalAlignment(JTextField.RIGHT);
       jtValorInyectar.addFocusListener(this);
       jtValorInyectar.addKeyListener(cJf.getValidarEntradaNumeroJTextField());
       panelCajaInyectar.add(jtValorInyectar);
       
       jSeparator1.setBounds(40, 125, 310, 2);
       jSeparator1.setForeground(Color.lightGray);
       add(jSeparator1);
       
       
       this.setVisible(true);
       
       this.addWindowListener(new WindowAdapter() {
 	    
 	    	public void windowClosing(WindowEvent awt) {
 	    		
 	    	  	jbCancelar.doClick();
 	    	}
 	    });
       
    }
    
    public void actionPerformed(ActionEvent a) {
     	
     	if (a.getSource() == jbAceptar) {
     	
     		
     		guardar();
     		this.dispose();
     		caja.buscarCaja();
     	    caja.setVisible(true);
     
     	} else
     	
           	if (a.getSource() == jbCancelar) {
     		
     		    this.dispose();
     		    caja.buscarCaja();
     		    caja.setVisible(true);
     
     	     }  
     	
    	
    }
    
     public void guardar() {
    
    
       try {
       	
	    	Long totalRetirar = Long.parseLong(jtValorInyectar.getText());
	    	
	    	if (totalRetirar <= totalCaja) {
	    
			    	totalCaja -= totalRetirar; //Se incrementa la caja
			              
			        //Se almacena en caja
			        String[] datos = new String[7];
			        datos[0] = "null";
			        datos[1] = "'" + cJf.getObtenerFechaCompletaServidor(conectarMySQL) + "'";
			        datos[2] = "'" + nombreUsuario + "'";
			        datos[3] = "4";
			        datos[4] = "" + totalRetirar;
			        datos[5] = "" + totalCaja;
			        datos[6] = "'" + jtDescripcion.getText() + "'";
	        
	       
			        
			        
			        cJf.guardar("MovimientosCaja",datos,conectarMySQL,false); 
			        
		            conectarMySQL.commit();      //Se registra los cambios en la base de datos 
		 
		  } else{
		     
		     cJf.Mensaje("El valor a retirar debe ser menor o igual al de caja ","Informac��n",2);
		     jtValorInyectar.setText("");
		     jtValorInyectar.grabFocus();
		      	
		  	
		  }
			      
	   } catch (Exception e) {
	   	
	   	  cJf.Mensaje("Error al insertar movmiento "+ e,"Informaci�n",2);
	   }    
    	
    	
    }
    
    
  
     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 

       // se coloca el atributo visual por defecto
       f.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());

     }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
         // se coloca el atributo visual por defecto
        f.getComponent().setBackground(cJf.getVisualAtributoPierdeFocoComponentes());
      
      }
} 
    
   