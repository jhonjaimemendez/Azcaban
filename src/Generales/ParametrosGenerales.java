/*
 * Clase: ParametrosGenerales
 *
 * Version : 1.0
 *
 * Fecha: 27-10-2006
 *
 * Copyright: JASoft
 */


import com.JASoft.componentes.OfficeXPTheme;
import com.JASoft.componentes.GreenMetalTheme;
import com.JASoft.componentes.AquaMetalTheme;
import com.JASoft.componentes.KhakiMetalTheme;
import com.JASoft.componentes.RedTheme;
import com.JASoft.componentes.CharcoalTheme;
import com.JASoft.componentes.MoodyBlueTheme;
import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.RubyTheme;
import com.JASoft.componentes.ContrastTheme;

import java.awt.Font;
import java.awt.Color;


import com.JASoft.componentes.JTitlePanel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Toolkit;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;




import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.JDesktopPane;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import javax.swing.plaf.metal.*;


 /**
  * Esta clase permite realizar configuraciones generales al SIICE
  */
  
  final class ParametrosGenerales extends JInternalFrame implements ActionListener,FocusListener,TreeSelectionListener {
  	
  	//Referencia de conexion a la base de datos
 	private ConectarMySQL conectarMySQL;
    
    //Referencia al componente CrearJFrame
  	private CrearJFrame cJf;
  	
  	//Referencia al MenuPrincipal
 	private MenuPrincipal menu;
 	
 	//Casillas de Texto
 	//** Se declaran los JTextField
    private JTextField jtValor;
    private  JTextField jtUnidadesStock;
    private  JTextField jtRuta;
    private  JTextField jtHost1; //Direccion IP de los servidores de backup
    private  JTextField jtHost2;
    private  JTextField jtHost3;
    private  JTextField jtPrecioLista ;
    private  JTextField jtPrecioMinimo;
    
    
    //JFileChooser para escoger la ruta donde se almacenaran los backup
    private JFileChooser fileChooser;
 
    
	//** Se declaran los JRadioButton
    private  JRadioButton jrHistorico;
    private  JRadioButton jrEstatico;
    private  JRadioButton jrPrecioManual;
  	
  	//** Se declaran los JCheckBox
    private  JCheckBox jhAuditorias;
    private  JCheckBox jhDecorarMarco;
  	private  JCheckBox jhAlertaPrecio;
  	private  JCheckBox jhAlertaProducto;
    private  JCheckBox jhAlertaStock;
    private  JCheckBox jhImprimirFacturas;
    private  JCheckBox jhHost1; //JCheckBox que configuran el numero mde servidores de backup
    private  JCheckBox jhHost2;
    private  JCheckBox jhHost3;
    
  	
  	//JComboBox
  	private JComboBox jcTemas;
	
	//Botones
 	private JButton Bguardar;
    private JButton Bsalir;
   	private JButton jbRuta;
   	private JButton BCopia;
   	private JButton BSalir;
    
 	//Paneles
 	private JTitlePanel panelIVA;
 	private JTitlePanel panelAlertas;
 	private JTitlePanel panelConfiguracionesGenerales;
 	private JTitlePanel panelPrecio;
 	private JTitlePanel panelStock;
 	private JTitlePanel panelCopiasSeguridad;
 	private JTitlePanel panelTemas;
 	
 	//JFrame
 	private JFrame jframe;
  
    //String
    private String nombreUsuario;
    private String IVAAnterior;
  
    //int
    private int tema; // Tema escogido por el usuario
    
    //JTree
    private JTree tree;
    
    
    
    //Constructor para el menu principal 
   	public ParametrosGenerales(ConectarMySQL p_conMySQL,MenuPrincipal p_menu,String nombreUsuario,int tema){ 
  	    
  	    super("Parametros Generales",true, true, false, false);
        
        this.setLayout(null);
        
        this.setBounds((p_menu.getWidth() - 600)/2,(p_menu.getHeight() - 400)/2,600,400);
	       
        	
        menu = p_menu;
    	
    	this.nombreUsuario = nombreUsuario;
    	
    	this.jframe = jframe;
    	
    	this.tema = tema;
    	
    	
    	conectarMySQL = p_conMySQL;
    	
        cJf =new CrearJFrame(); 
		
		
  	    // Se instancia un panel para agregar el JTree
  	    JPanel panel = cJf.getJPanel(10,10,198,350);
  	    
  	    //Se configura la raiz del arbol
  	    DefaultMutableTreeNode root = new DefaultMutableTreeNode( "Parametros Generales" );
  	    
  	    
  	    //Se crean las hojas y se agregan a la raiz
        DefaultMutableTreeNode configuraciones = new  DefaultMutableTreeNode("Configuraciones Generales");
        root.add(configuraciones);
        
        DefaultMutableTreeNode iva = new  DefaultMutableTreeNode("IVA");
        root.add(iva);
        
        DefaultMutableTreeNode alertas = new  DefaultMutableTreeNode("Alertas");
        root.add(alertas);
        
        DefaultMutableTreeNode precios = new  DefaultMutableTreeNode("Precios");
        root.add(precios);
        
        DefaultMutableTreeNode stock = new  DefaultMutableTreeNode("Stock");
        root.add(stock);
        
        DefaultMutableTreeNode copias = new  DefaultMutableTreeNode("Copias De Seguridad");
        root.add(copias);
        
        DefaultMutableTreeNode temas = new  DefaultMutableTreeNode("Temas");
        root.add(temas);
        
        tree = new JTree(root);
       
        //Evento para escuchar los cambios
        tree.addTreeSelectionListener(this);
        
        JScrollPane scroll = new JScrollPane(tree);
        scroll.setBounds(0,0,198,350);
        
        //Se agrega el scroll al panel
        panel.add(scroll);
        
        //Se configuran los JButton
        Bguardar = cJf.getJButton(" Guardar",250,325,120,35,"Imagenes/Guardar.gif","Guardar Cambios");
 	    Bguardar.setMnemonic('G');
 	    Bguardar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/GuardarS.gif")));
  		Bguardar.addActionListener(this);
  		this.add(Bguardar);
 	
  		Bsalir = cJf.getJButton(" Salir",420,325,120,35,"Imagenes/Salir.gif","Salir");
  		Bsalir.setMnemonic('S');
  		Bsalir.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/SalirS.gif")));
  		Bsalir.addActionListener(this);
  		this.add(Bsalir);

        
  	    this.add(panel);
  	    
  	   	this.setFrameIcon(new ImageIcon(getClass().getResource("/Imagenes/Admin.gif")));


        // Se instancia el JFileChooser
 		fileChooser = new JFileChooser();
 		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
 	
        configurarPanelIVA(); //Se instancian los paneles
        
  	    setVisible(true);
  	    
  	    configurarPanelConfiguracionesGenerelas(); 
  	    configurarPanelPrecios();
        configurarPanelAlertas();
        configurarPanelStock();
        configurarCopiasSeguridad();
        configurarPanelTemas();
      
  	    buscarRegistro();
 		
	
	   // Se agrega un evento de ventana
 	   this.addInternalFrameListener( new InternalFrameAdapter() {
 	   	
		    public void internalFrameClosing(InternalFrameEvent ife) {

			   menu.habilitarMenu(true);

		    }
	   });

  	   
  	
  	}
   	
    
   //*********************************************************************************
  	
   private void configurarPanelIVA() {
 	
	 	  if (panelIVA == null) {
		 	  	  
		 	  	  JPanel panelIVAComodin = cJf.getJPanel(215,10,370,305);
	 	  	      panelIVAComodin.setLayout(null);
	 	  	
		 	      //** Se agregan los JPanel

			      panelIVA =  new JTitlePanel("IVA", null, panelIVAComodin, BorderFactory
			                                                            .createEmptyBorder(4, 4, 0, 4),370);
			      panelIVA.setVisible(false);                                                      
			      panelIVA.setBounds(215,10,370,305);
			      
			       //** Se instancian los JSeparator
	              JSeparator jSeparator = new JSeparator();
	              jSeparator.setBounds(10, 40, 350, 2);
	              panelIVA.add(jSeparator);
	
			      JSeparator jSeparator1 = new JSeparator();
			      jSeparator1.setBounds(133, 110, 105, 2);
			      panelIVA.add(jSeparator1);
			      
			      
			      JSeparator jSeparator2 = new JSeparator();
			      jSeparator2.setBounds(158, 95, 55, 2);
			      panelIVA.add(jSeparator2);
			      
			      JSeparator jSeparator3 = new JSeparator();
			      jSeparator3.setBounds(133, 195, 105, 2);
			      panelIVA.add(jSeparator3);
			      
			      JSeparator jSeparator4 = new JSeparator();
                  jSeparator4.setBounds(158, 210, 55, 2);
                  panelIVA.add(jSeparator4);
  
			      //** Se declaran los JLabel
			
			      JLabel jlValor =  cJf.getJLabel("Valor Porcentual del Iva",90, 130, 155, 15);
			      jlValor.setHorizontalAlignment(SwingConstants.RIGHT);
			      panelIVA.add(jlValor);
			
			      JLabel jlPorcentaje =  cJf.getJLabel("%",200, 155, 15, 20);
			      panelIVA.add(jlPorcentaje);
			   
			      jtValor = cJf.getJTextField("",165, 155, 30, 20,"Valor IVA","2");
			      jtValor.setHorizontalAlignment(SwingConstants.RIGHT);
			      jtValor.addFocusListener(this);
			      jtValor.addKeyListener(cJf.getValidarEntradaNumeroJTextField());
			      panelIVA.add(jtValor);

		          add(panelIVA);
		 	  	   
		 	  	  this.add(panelIVA);
		 	  	   
		 	  	
		 	  	
	 	  } else {
	 	  	
	 	  	  panelIVA.setVisible(true);
	 	  	  panelIVA.updateUI();
	 	  	
	 	  }
	 
    }
    
    
   //*********************************************************************************
   
      private void configurarPanelConfiguracionesGenerelas() {
 	
	 	  if (panelConfiguracionesGenerales == null) {
	 	  	
	 	  	
	 	  	JPanel panelConfiguracionesGeneralesComodin = cJf.getJPanel(215,10,370,305);
	 	  	panelConfiguracionesGeneralesComodin.setLayout(null);
	 	  	
	 	  	panelConfiguracionesGenerales = new JTitlePanel("Configuraciones Generales", null, panelConfiguracionesGeneralesComodin, BorderFactory
			                                                            .createEmptyBorder(4, 4, 0, 4),370);
			                                                            
	 	    //panelConfiguracionesGenerales.setLayout(null);	
	 	  	panelConfiguracionesGenerales.setBounds(215,10,370,305);
 	  	 
 	  	    //** Se instancian los JSeparator
	        JSeparator jSeparator1 = new JSeparator();
	        jSeparator1.setBounds(10, 40, 350, 2);
	        panelConfiguracionesGenerales.add(jSeparator1);
		
	 	  	 
	 	  	jhAuditorias = cJf.getJCheckBox("Activar Auditorías Transaccionales",30, 75, 325, 20,"Auditorias");
	 	 	jhAuditorias.setMnemonic('A');
	 		jhAuditorias.setName("20");
	 		panelConfiguracionesGenerales.add(jhAuditorias);
	 	
	 	  	jhDecorarMarco = cJf.getJCheckBox("Marco de Aplicación definido por Sistema Operativo",30, 130, 325, 20,"");
	 	 	jhDecorarMarco.setMnemonic('M');
	 		jhDecorarMarco.setName("20");
	 		panelConfiguracionesGenerales.add(jhDecorarMarco);
	 		
	 		jhImprimirFacturas = cJf.getJCheckBox("Preguntar Anter de Imprimir Facturas",30, 185, 325, 20,"");
	 	 	jhImprimirFacturas.setMnemonic('P');
	 		jhImprimirFacturas.setName("20");
	 		panelConfiguracionesGenerales.add(jhImprimirFacturas);
	 	
	 	  	 	 
	 	  	add(panelConfiguracionesGenerales);
	 	  	
	 	  } else {
	 	  	
	 	  	  panelConfiguracionesGenerales.setVisible(true);
	 	  	
	 	  }
	 	  
	 	  
	 	   panelConfiguracionesGenerales.updateUI();
	 
    }
    
    //*********************************************************************************
   
      private void configurarPanelPrecios() {
 
 
	 	  if (panelPrecio == null) {
	 	  	 //** Se agregan los JPanel
	 	  	  
	 	  	  JPanel panelPrecioComodin = cJf.getJPanel(20, 50, 320, 180);
	 	  	  
	 	  	  
	 	  	  
	 	  	  panelPrecio = new JTitlePanel("Porcentaje de Ganancia", null, panelPrecioComodin, BorderFactory
			                                                            .createEmptyBorder(4, 4, 0, 4),370);
	 	  	  
	 	  	  panelPrecio.setBounds(215,10,370,305);
	 	  	  panelPrecio.setVisible(false);
		 	
              //** Se instancian los JSeparator
	          JSeparator jSeparator1 = new JSeparator();
	          jSeparator1.setBounds(10, 40, 350, 2);
	          panelPrecio.add(jSeparator1);
		      
		      JSeparator jSeparator2 = new JSeparator();
	          jSeparator2.setBounds(83, 80, 205, 2);
	          panelPrecio.add(jSeparator2);
		
		      JSeparator jSeparator3 = new JSeparator();
	          jSeparator3.setBounds(83, 200, 205, 2);
	          panelPrecio.add(jSeparator3);
	          
	          
		      //** Se declaran los JLabel
		
		      JLabel jlPrecioVenta =  cJf.getJLabel("Porcentaje de Ganancia Estandar",78, 60, 215, 15);
		      jlPrecioVenta.setHorizontalAlignment(SwingConstants.LEFT);
		      panelPrecio.add(jlPrecioVenta);
		      
		      
		      JLabel jlPrecioLista = cJf.getJLabel("Precio Lista:",10, 98, 85, 15);
		      jlPrecioLista.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelPrecio.add(jlPrecioLista);
		      
		      JLabel jlPrecioMinimo = cJf.getJLabel("Precio Minimo:",10, 128, 85, 15);
		      jlPrecioMinimo.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelPrecio.add(jlPrecioMinimo);
		      
		      JLabel jlObservacionPrecioLista = cJf.getJLabel("% de Ganancia sobre Valor de Compra",140, 95, 220, 20);
		      jlObservacionPrecioLista.setHorizontalAlignment(SwingConstants.LEFT);
		      jlObservacionPrecioLista.setForeground(new Color(132, 132, 132));
              panelPrecio.add(jlObservacionPrecioLista);
		      
		      JLabel jlObservacionPrecioMinimo = cJf.getJLabel("% de Ganancia sobre Valor de Compra",140, 125, 355, 25);
		      jlObservacionPrecioMinimo.setHorizontalAlignment(SwingConstants.LEFT);
		      jlObservacionPrecioMinimo.setForeground(new Color(132, 132, 132));
              panelPrecio.add(jlObservacionPrecioMinimo);
		      
		      
		      JLabel jlPrecioVentaConfiguracion = cJf.getJLabel("Precio Venta",78, 180, 215, 15);
		      jlPrecioVentaConfiguracion.setHorizontalAlignment(SwingConstants.CENTER);
		      panelPrecio.add(jlPrecioVentaConfiguracion);
              
              //Se instancia un evento
              KeyAdapter keyAdapter = new KeyAdapter() {
              	public void  keyTyped (KeyEvent k) {
	
					  JTextField jtextField = (JTextField) k.getComponent();
					  
					  if ((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (k.getKeyChar()!=KeyEvent.VK_PERIOD) && (!Character.isDigit(k.getKeyChar())) || 
					     (k.getComponent().getName()!= null && jtextField.getText().length() >= Integer.parseInt(k.getComponent().getName())))
					           // Se consume o borra el caracter digitado
					           k.consume();
					  }	
              };
              
              
              jtPrecioLista = cJf.getJTextField("",100, 95, 35, 20,"Digite el Porcentaje Precio Lista","5");
		      jtPrecioLista.setHorizontalAlignment(SwingConstants.RIGHT);
		      jtPrecioLista.addFocusListener(this);
		      jtPrecioLista.addKeyListener(keyAdapter);
		      panelPrecio.add(jtPrecioLista);
		      
		      jtPrecioMinimo = cJf.getJTextField("",100, 125, 35, 20,"Digite el Porcentaje Precio Minimo","5");
		      jtPrecioMinimo.setHorizontalAlignment(SwingConstants.RIGHT);
		      jtPrecioMinimo.addFocusListener(this);
		      jtPrecioMinimo.addKeyListener(keyAdapter);
		      panelPrecio.add(jtPrecioMinimo);
          
		      //Se instancia un objeto de tipo ButtonGroup para agrupar los JRadioButton
		      ButtonGroup grupo = new ButtonGroup();
		
		      //** Se agregan los JRadioButton
		
		      jrHistorico =  cJf.getJRadioButton("Aplicar % de Ganancia Historico del Producto",25, 215, 330, 20,grupo);
		      panelPrecio.add(jrHistorico);
		
		      jrEstatico =  cJf.getJRadioButton("Aplicar % de Ganancia Estandar a Todos los Productos",25, 250, 340, 20,grupo);
		      panelPrecio.add(jrEstatico);
		
		      	 
	 	  	 add(panelPrecio);
	 	  	 
	 	  	
	 	  } else {
	 	  	
	 	  	  panelPrecio.setVisible(true);
	 	  	  panelPrecio.updateUI();
	 	  	
	 	  }
	 	  
	 	    
	 	   
    }
     
   //***********************************************************************************
  	
   private void configurarPanelAlertas() {
 	
	 	  if (panelAlertas == null) {
	 	  	
	 	  	 JPanel panelConfiguracionesComodin = cJf.getJPanel(20, 50, 315, 150);
	 	  	
	 	  	 panelAlertas = new JTitlePanel("Alertas", null, panelConfiguracionesComodin, BorderFactory
			                                                          .createEmptyBorder(4, 4, 0, 4),370);
			                                                            
	 	  	 
	 	  	 panelAlertas.setBounds(215,10,370,305);
	 	  	 panelAlertas.setVisible(false);
		 	
		      
		      //** Se instancian los JSeparator
		
		      JSeparator jSeparator1 = new JSeparator();
		      jSeparator1.setBounds(10, 40, 350, 2);
		      panelAlertas.add(jSeparator1);
		
		      //** Se agregan los JCheckBox
		
		      jhAlertaPrecio = cJf.getJCheckBox("Alertas para cambio de Precios de Ventas",30, 75, 325, 20,"Precios");
		      panelAlertas.add(jhAlertaPrecio);
		
		      jhAlertaStock = cJf.getJCheckBox("Alertas para Stock Minímo",30, 130, 325, 20,"Minimo");
		      panelAlertas.add(jhAlertaStock);
			 
	 	  	  add(panelAlertas);
	 	  	 
	 	  	
	 	  } else {
	 	  	
	 	  	  panelAlertas.setVisible(true);
	 	  	  panelAlertas.updateUI();
	 	  	
	 	  }
	 	  
	 	  
	 
    }
    
    
    //***********************************************************************************
  	
    private void configurarPanelStock() {
 	
	 	  if (panelStock == null) {
	 	  	 
	 	  	 JPanel panelStockComodin = cJf.getJPanel(20, 50, 315, 150);
	 	  	
	 	  	
	 	  	 panelStock = new JTitlePanel("Stock Minimo", null, panelStockComodin, BorderFactory
			                                                          .createEmptyBorder(4, 4, 0, 4),370);
			 panelStock.setBounds(215,10,370,305);                                                         
	 	  	 panelStock.setVisible(false);
		 	
		      //** Se instancian los JSeparator
		      JSeparator jSeparator1 = new JSeparator();
		      jSeparator1.setBounds(10, 40, 350, 2);
		      panelStock.add(jSeparator1);
		
	          JLabel jlUnidades =  cJf.getJLabel("Minimo Unidades : ",40, 132, 155, 15);
		      jlUnidades.setHorizontalAlignment(SwingConstants.RIGHT);
		      panelStock.add(jlUnidades);
		      
			      
		      jtUnidadesStock = cJf.getJTextField("Unidades Stock Minimo",200, 130, 50, 20,"2",true);
		      jtUnidadesStock.setHorizontalAlignment(SwingConstants.RIGHT);
		      jtUnidadesStock.addFocusListener(this);
		      jtUnidadesStock.addKeyListener(cJf.getValidarEntradaNumeroJTextField());
		      panelStock.add(jtUnidadesStock);
		     
	 	  	  add(panelStock);
	 	  	 
	 	  	
	 	  } else {
	 	  	
	 	  	  panelStock.setVisible(true);
	 	  	  panelStock.updateUI();
	 	  	
	 	  }
	 	  
	 
    }
    
    
 
    //************************ configurarPanelIVA() ******************************************************************
     
    private void configurarPanelTemas() {
 	
	 	  if (panelTemas == null) {
		 	  	  
		 	  	  
	 	  	      JPanel paneltemasComodin = cJf.getJPanel(20,20,20,20);
	 	  	
	  	  	      panelTemas = new JTitlePanel("Temas", null, paneltemasComodin, BorderFactory
			                                                                         .createEmptyBorder(4, 4, 0, 4),370);
			                
			      panelTemas.setBounds(215,10,370,305);                                                         
	 	  	      panelTemas.setVisible(false);
		 	
		   	      
			       //** Se instancian los JSeparator
	              JSeparator jSeparator = new JSeparator();
	              jSeparator.setBounds(10, 40, 350, 2);
	              panelTemas.add(jSeparator);
	
			      JSeparator jSeparator1 = new JSeparator();
			      jSeparator1.setBounds(133, 110, 105, 2);
			      panelTemas.add(jSeparator1);
			      
			      
			      JSeparator jSeparator2 = new JSeparator();
			      jSeparator2.setBounds(158, 95, 55, 2);
			      panelTemas.add(jSeparator2);
			      
			      JSeparator jSeparator3 = new JSeparator();
			      jSeparator3.setBounds(133, 195, 105, 2);
			      panelTemas.add(jSeparator3);
			      
			      JSeparator jSeparator4 = new JSeparator();
                  jSeparator4.setBounds(158, 210, 55, 2);
                  panelTemas.add(jSeparator4);
  
			      //** Se declaran los JLabel
			
			      JLabel jlValor =  cJf.getJLabel("Temas : ",35, 145, 155, 15);
			      jlValor.setHorizontalAlignment(SwingConstants.RIGHT);
			      jlValor.setHorizontalAlignment(SwingConstants.CENTER);
			      panelTemas.add(jlValor);
			      
			      jcTemas = cJf.getJComboBox(150,143,120,20,"Temas");
			      jcTemas.setMaximumRowCount(10);
			      jcTemas.addItem("Ocean");
			      jcTemas.addItem("Steel");
			      jcTemas.addItem("OfficeXPTheme");
			      jcTemas.addItem("Esmeralda");
			      jcTemas.addItem("Oxido");
			      jcTemas.addItem("Arena");
			      jcTemas.addItem("Mars");
			      jcTemas.addItem("Charcoal");
			      jcTemas.addItem("MoodyBlues");
			      jcTemas.addItem("Contraste");
			      jcTemas.addItem("Ruby");
			      jcTemas.addActionListener(this);
			      jcTemas.setSelectedIndex(tema);
			      panelTemas.add(jcTemas);
			      
			      jcTemas.setSelectedIndex(tema);
			 
		 	  	  add(panelTemas);
		 	  	   
		 	  	
		 	  	
	 	  } else {
	 	  	
	 	  	  panelTemas.setVisible(true);
	 	  	  panelTemas.updateUI();
	 	  	
	 	  }
	 
    }
    
  	
    
    
    //***********************************************************************************
  	
    private void configurarCopiasSeguridad() {
 	
	 	  if (panelCopiasSeguridad == null) {
	 	  	
	 	  	
	 	  	 JPanel panelCopiasComodin = cJf.getJPanel(20,20,20,20);
	 	  	
	 	  	 panelCopiasSeguridad = new JTitlePanel("Copias de Seguridad (BackUp's)", null, panelCopiasComodin, BorderFactory
			                                                                         .createEmptyBorder(4, 4, 0, 4),370);
			                
	 	  	
	 	  	
	 	  	 panelCopiasSeguridad.setBounds(215,10,370,305);
	 	  	 panelCopiasSeguridad.setVisible(false);
		 	
	 	  	 
	 	  	 //** Se instancian los JSeparator
		
		      JSeparator jSeparator1 = new JSeparator();
		      jSeparator1.setBounds(10, 40, 350, 2);
		      panelCopiasSeguridad.add(jSeparator1);
		      
	 	  	 
		      JSeparator jSeparator2 = new JSeparator();
		      jSeparator2.setBounds(83, 165, 205, 2);
		      panelCopiasSeguridad.add(jSeparator2);
	 	  	
		      //** Se declaran los JLabel
		      
		      JLabel jlServidores = cJf.getJLabel("Servidores",148, 145, 75, 15);
		      panelCopiasSeguridad.add(jlServidores);
		      
		      JLabel LRuta = cJf.getJLabel("Guardar en : ",30, 88, 75, 15);
 		      panelCopiasSeguridad.add(LRuta);
 		
 		
		      //** Se instancian los JSeparator
		
		      
		      jhHost1 = new JCheckBox("Host Nº 1",true);
		 	  jhHost1.setBounds(115, 190, 80, 20);
		 	  jhHost1.setEnabled(false);
		 	  jhHost1.setFocusable(false);
		 	  panelCopiasSeguridad.add(jhHost1);
		 		
		 		
		 	  jtHost1 = cJf.getJTextField("",200, 190, 85, 20,"Dirección del Host Nº 1, este es el servidor local","20");
		 	  jtHost1.addFocusListener(this);
		 	  panelCopiasSeguridad.add(jtHost1);
		  	    
		  	    
	  	      jhHost2 = new JCheckBox("Host Nº 2",false);
	 		  jhHost2.setBounds(115, 225, 80, 20);
	 		  jhHost2.addActionListener(this);
	 		  panelCopiasSeguridad.add(jhHost2);
	 		
	 		  jtHost2 = cJf.getJTextField("Dirección del Host Nº 2",200, 225, 85, 20,"15",false);
	 	      jtHost2.setEditable(false);
	 	      jtHost2.setFocusable(false);
	 	      jtHost2.addKeyListener(new KeyAdapter() {	 
	 	    	 // Se valida que solo se digite mayuscula
	 	    	 public void  keyTyped (KeyEvent k) {
	 	    	 	
	 	    	 	if(((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) && (k.getKeyChar()!='.'))|| (jtHost2.getText().length() >= Integer.parseInt(k.getComponent().getName())))
	 	    	 		k.consume();
	 	    	 	
	              }
	              
	   
	           });
	           jtHost2.addFocusListener(this);
		 	   panelCopiasSeguridad.add(jtHost2);
		 		
		 	   jhHost3 = new JCheckBox("Host Nº 3",false);
		 	   jhHost3.setBounds(115, 260, 80, 20);
		 	   jhHost3.addActionListener(this);
		 	   panelCopiasSeguridad.add(jhHost3);
	 		
	 		   jtHost3 = cJf.getJTextField("Dirección del Host Nº 3",200, 260, 85, 20,"15",false);
	 		   jtHost3.addFocusListener(this);
	 		   jtHost3.addKeyListener(new KeyAdapter() {	 
	 	    	   // Se valida que solo se digite mayuscula
	 	    	    public void  keyTyped (KeyEvent k) {
	 	    	 	
	 	    	 	   if(((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) && (k.getKeyChar()!='.'))|| (jtHost3.getText().length() >= Integer.parseInt(k.getComponent().getName())))
	 	    	 		   k.consume();
	 	    	 	
	                }
	              
	   
	          });
	 	    
	 	   	  panelCopiasSeguridad.add(jtHost3);
	 	   	
	 	   	  jtRuta = cJf.getJTextField("Digite la ruta donde se guardarán los Archivos Planos",105, 85, 205, 20,"15",false);
	 	   	  jtRuta.setText(fileChooser.getCurrentDirectory().getPath() );
	 		  jtRuta.addFocusListener(this);
	 		  panelCopiasSeguridad.add(jtRuta);
	
			  jbRuta = cJf.getJButton("",310, 85, 20, 20,"Imagenes/Ruta.gif","ruta");
			  jbRuta.setMnemonic('R');
	  		  jbRuta.addActionListener(this);
	  		  panelCopiasSeguridad.add(jbRuta);
			  	    
			  
	 	  	 add(panelCopiasSeguridad);
	 	  	 
	 	  	
	 	  } else {
	 	  	
	 	  	  panelCopiasSeguridad.setVisible(true);
	 	  	  panelCopiasSeguridad.updateUI();
	 	  	
	 	  }
	 	  
	 
    }
    
    
    //***********************************************************************************
 	
 	private void buscarRegistro() {
 		
 		  final String sentenciaSQL = "Select valor "+
			                          "From Parametros "+
									  "Where CODPARAM IN ('1','2','3','4','5','21','22') "+
									  "Order by CODPARAM" ;
		
		
		
		 try {
	     
	     	  ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 		  
	   	      // Se extrae el primer registro
	     	  resultado.next(); 
	     	  jhAuditorias.setSelected(resultado.getBoolean(1));
	     	  
	     	  resultado.next(); 
	     	  jhDecorarMarco.setSelected(resultado.getBoolean(1));
	     	 
	     	  resultado.next(); 
	     	  jhImprimirFacturas.setSelected(resultado.getBoolean(1));
	     	   
	 		  resultado.next(); 
	     	  jtValor.setText(resultado.getString(1));
	     	  IVAAnterior = jtValor.getText();
	     	  
	     	  
	     	  resultado.next(); 
	     	  jhAlertaPrecio.setSelected(resultado.getBoolean(1));
	     	  
	     	  resultado.next(); 
	     	  jhAlertaStock.setSelected(resultado.getBoolean(1));
	     	  
	     	  resultado.next(); 
	     	  jtUnidadesStock.setText(resultado.getString(1));
	     	  
	     	  resultado.next();   
	     	  boolean aplicarHistorico =  resultado.getBoolean(1);
	     	  
	     	  
	     	  if (aplicarHistorico)
	     	  
	     	     jrHistorico.setSelected(true);
	     	     
	     	  else
	     	     
	     	     jrEstatico.setSelected(true);    
	     	    
			  resultado.next(); 
	     	  jtPrecioLista.setText(resultado.getString(1));
	     	  
	     	  resultado.next(); 
	     	  jtPrecioMinimo.setText(resultado.getString(1));
	     	 
	     	  resultado.next(); 
	     	  jtRuta.setText(resultado.getString(1));
	     	  
	     	  resultado.next(); 
	     	  jtHost1.setText(resultado.getString(1));
	     	  
	     	  resultado.next(); 
	     	  jtHost2.setText(resultado.getString(1));
	     	  
	     	  resultado.next(); 
	     	  jtHost3.setText(resultado.getString(1));
	     	  
	     	   if (!jtHost2.getText().isEmpty()) {
 		   	   
 		   	     jhHost2.setSelected(true);
 		   	     jtHost2.setEditable(true);
 		   	     jtHost2.setFocusable(true);
 		   	     
 		   	   }  
 		   	     
 		   	   if (!jtHost3.getText().isEmpty()) {
 		   	   
 		   	     jhHost3.setSelected(true);
 		   	     jtHost3.setEditable(true);
 		   	     jtHost3.setFocusable(true);
 		   	     
 		   	   }     
 		   	

	     	  	   		 
	 	    }catch (Exception e){
	 			
	 			cJf.Mensaje("Error " +e,"Reporte de Error",0);
	 		}
	 		
	 		
	 		
 		
 	}
 	//*********************************************************************************
 	
    private void actualizar() {
       
	       
		       try {
		 	   	
	 		         // Se actualiza la tabla Parametros
			 		  String condicion = "CODPARAM = '1' AND NOMPARAM = 'AUDITORIAS'";
			 		  	
			 		  String datos[] = new String[1];
			 		  	
			 		  datos[0] = "Valor = '" + jhAuditorias.isSelected() +"'";
			 		  
			 		  cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		  
			 		  
			 		  condicion = "CODPARAM = '1' AND NOMPARAM = 'DECORARMARCO'";
			 		  	
			 		  datos[0] = "Valor = '" + jhDecorarMarco.isSelected() +"'";
			 		  
			 		  cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		  
			 		  
			 		  condicion = "CODPARAM = '1' AND NOMPARAM = 'IMPRIMIRFACTURA'";
			 		  	
			 		  datos[0] = "Valor = '" + jhImprimirFacturas.isSelected() +"'";
			 		  
			 		  cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		  
			 		  
			 		  condicion = "CODPARAM = '2' AND NOMPARAM = 'IVA'";
			 		  	
			 		  datos[0] = "Valor = '" + jtValor.getText() +"'";
			 		  
			 		  cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 	 	  
			 	 	  if (!jtValor.getText().equals(IVAAnterior)) { //Se actualiza en Historico de IVA
			 		    
			 		    String fechaInicioVigencia = null;
			 		    
			 		    //Se busca la ultima Fecha de HistoricoIVA
			 		    try {
			 		    	
			 		    	ResultSet resultado = conectarMySQL.buscarRegistro("Select Max(FechaInicioVigencia) From HistoricoIVA");
			 		    	
			 		    	if (resultado.next())
			 		    	
			 		    	 fechaInicioVigencia =  resultado.getString(1) ; 
			 		    	
			 		    } catch (Exception e) {
			 		    	
			 		    	e.printStackTrace();
			 		    }
			 		    
			 		    
			 		    if (fechaInicioVigencia != null) {
				 		    
				 		    condicion = "ValorIVA = "+ IVAAnterior + " and FechaInicioVigencia = '" + fechaInicioVigencia + "'";
				 		                                              
				 		    datos[0] = "FechaTerminoVigencia ='" + cJf.getObtenerFecha(conectarMySQL) + "'"; 
				 		    
				 		    cJf.actualizar("HistoricoIVA",datos,condicion,conectarMySQL,false);
				 		    
				 		    IVAAnterior = jtValor.getText();
				 		}    
				 		
				 		    
			 		    //Se inserta el nuevo registro
			 		    String[] datosHistorico = new String[3];
			 		    datosHistorico[0] = jtValor.getText();
			 		    datosHistorico[1] = "'" + cJf.getObtenerFecha(conectarMySQL) + "'";
			 		    datosHistorico[2] = "null";
			 		                                                
			 		    //Se inserta en la base de datos
                        cJf.guardar("HistoricoIVA",datosHistorico,conectarMySQL,false);    
		              	 		      
			 		  }
			 		  
			 		  condicion = "CODPARAM = '3' AND NOMPARAM = 'ALERTACAMBIOPRECIO'";
			 		  	
			 		  datos[0] = "Valor = '" + jhAlertaPrecio.isSelected() +"'";
			 		  
			 		  cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		 
			 		 
			 		 
			 		 condicion = "CODPARAM = '3' AND NOMPARAM = 'ALERTASTOCK'";
			 		  	
			 		 datos[0] = "Valor = '" + jhAlertaStock.isSelected() +"'";
			 		  
			 		 cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		 
			 		 
			 		 
			 		 condicion = "CODPARAM = '4' AND NOMPARAM = 'VALORSTOCKMINIMO'";
			 		  	System.out.println(jtUnidadesStock.getText());
			 		 datos[0] = "Valor = '" + jtUnidadesStock.getText() +"'";
			 		  
			 		 cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		 
			 		 
			 		 
			 		 condicion = "CODPARAM = '5' AND NOMPARAM = 'PORCSTOCKMINIMO'";
			 		  	
			 		 datos[0] = "Valor = '" + jtPrecioMinimo.getText() +"'";
			 		  
			 		 cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		 
			 		 
			 		 
			 		 condicion = "CODPARAM = '5' AND NOMPARAM = 'PORCSTOCKLISTA'";
			 		  	
			 		 datos[0] = "Valor = '" + jtPrecioLista.getText() +"'";
			 		  
			 		 cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		 
			 		 
			 		 condicion = "CODPARAM = '5' AND NOMPARAM = 'APLICARHISTORICO'";
			 		  	
			 		 datos[0] = "Valor = '" + jrHistorico.isSelected() +"'";
			 		  
			 		 cJf.actualizar("Parametros",datos,condicion,conectarMySQL,false);
			 		 
			 		 
			 		 
			  		  datos[0] = "Valor = '"+adicionaCaracter(jtRuta.getText())+"'";
			  		  
			  		  condicion = " CODparam = 21";
			  		  
			  		  cJf.actualizar("PARAMETROS",datos,condicion,conectarMySQL,false);
			  		  
			  		  actualizarHostBackup("22","HOST1",jtHost1.getText());
			  		  
			  		  actualizarHostBackup("22","HOST2",jtHost2.getText());
			  		  
			  		  actualizarHostBackup("22","HOST3",jtHost3.getText());
			  		  
			 		 
			 		  datos[0] = "Tema = " + jcTemas.getSelectedIndex();
			  		  
			  		  condicion = "ID = '" + nombreUsuario + "'";
			  		  
			  		  cJf.actualizar("Usuarios",datos,condicion,conectarMySQL,false);
			  		 
			 		  menu.tema = jcTemas.getSelectedIndex();
			  		  
			 		 
		 		     //Se hacen permanentes los cambios
		 	         conectarMySQL.commit();
		 	          
		 	         cJf.Mensaje("Configuracion Exitosa \n\n Debe reiniciar Azcaban para que los cambios surtan efecto","Información",JOptionPane.INFORMATION_MESSAGE);
		 	         
		 	         IVAAnterior = jtValor.getText();
	     	  
				  	  
			 	 } catch (Exception e) {
			 	 	
			 	 	conectarMySQL.rollback();
			 	 	
			 	 	cJf.Mensaje("Error al actualizar cuenta de usuario"+e,"Error",JOptionPane.ERROR_MESSAGE);
			 	 	
			 	 } 
			 
			 	
			 
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
 	
 	
 	//********************************************************************************
 	
  	private void actualizarHostBackup(String codigoParametro,String nombreParametro, String host) throws Exception{
  		
  			
  		  // Se crea el array para actualizar
  		  String datos[] = new String[1];
  		  
  		  datos[0] = "Valor = '"+ host +"'";
  		  
  		  final String condicion = " CODparam = " + codigoParametro + " and NOMPARAM = '" + nombreParametro +"'" ;
  		  
  		  cJf.actualizar("PARAMETROS",datos,condicion,conectarMySQL,false);
  	
  		
  	}
  	
 	//***********************************************************************************
 	
 	public void actionPerformed(ActionEvent act) {
 		
 		Object evento = act.getSource();
 		   	
 		  	if (evento == Bguardar) {
 		  		
            
	  		       if (!jtValor.getText().isEmpty()) {
	  		       	
	  		       	   if (!jtPrecioLista.getText().isEmpty()) {
	  		       	   
			  		       	   
			  		       	    if (!jtPrecioMinimo.getText().isEmpty()) {
			  		       	    
			  		       		   	  actualizar();
			  		       		   	  
			  		       		   	  menu.buscarParametrosAzkaban(); //Se actualizan los parametros
			  		       		   	  
				 		  	    } else {
				 		  	    	
				 		  	    	cJf.Mensaje(jtPrecioMinimo.getToolTipText(),"Reporte de Error",0);
				 		  		    jtPrecioMinimo.grabFocus();
				 		  	    	
				 		  	    }
		 		  	   } else {
				 		  	    	
		 		  	    	cJf.Mensaje(jtPrecioLista.getToolTipText(),"Reporte de Error",0);
		 		  		    jtPrecioLista.grabFocus();
		 		  	    	
				 	  }	     
		 		  	} else {
		 		  		
		 		  		  cJf.Mensaje(jtValor.getToolTipText(),"Reporte de Error",0);
		 		  		  jtValor.grabFocus();
		 		    }		
      
 		     } else
	 		 
	 		    if (evento == Bsalir) {
		           
		           try{
		           
		               menu.habilitarMenu(true);
		               dispose();
		               
		          }catch(Exception e){
		          
		               System.out.println("Error " + e);
		          	
		          }
		          
	           } else
		             
				 		if (evento == jbRuta) {
				 			
				 			int opcion = fileChooser.showDialog(this,"Aceptar");
				 			
				 			 if (opcion == JFileChooser.APPROVE_OPTION) 
				                 jtRuta.setText(fileChooser.getSelectedFile().getPath());
				 		} else
				 		   
				 		   	  if (evento == jhHost2) 
						 	  {	
									if (jhHost2.isSelected()) 
									{
										jtHost2.setEditable(true);
				 	          			jtHost2.setFocusable(true);
				 	          			jtHost2.grabFocus();
							 		}
							 		else
							 		{
							 			jtHost2.setEditable(false);
				 	         			jtHost2.setFocusable(false);
				 	         			jtHost2.setText("");
							 		}
							 }
							 else
							   if (evento == jhHost3) 
						 	   {	
									if (jhHost3.isSelected()) 
									{
										jtHost3.setEditable(true);
				 	          			jtHost3.setFocusable(true);
				 	            		jtHost3.grabFocus();
							 		}
							 		else
							 		{
							 			jtHost3.setEditable(false);
				 	         			jtHost3.setFocusable(false);
				 	         			jtHost3.setText("");
							 		}
							  } else
							  
							      if (evento == jcTemas) {
							      	  
							      	  switch (jcTemas.getSelectedIndex()) {
							      	    
							      	    case 0:
							      	      
							      	      actualizarTema(new OceanTheme());
							      	      
							      	    break;
							      	    
							      	    case 1:
							      	       
							      	       actualizarTema(new DefaultMetalTheme());
							      	    break;
							      	    
							      	    case 2:
							      	      
							      	       actualizarTema(new OfficeXPTheme());
							      	       
							      	    break;
							      	    
							      	    case 3:
							      	      
							      	       actualizarTema(new GreenMetalTheme());
							      	       
							      	    break;
							      	    
							      	    
							      	    case 4:
							      	      
							      	       actualizarTema(new AquaMetalTheme());
							      	       
							      	    break;
							      	    
							      	    
							      	    
							      	    case 5:
							      	      
							      	       actualizarTema(new KhakiMetalTheme());
							      	       
							      	    break;
							      	    
							      	    
							      	    case 6:
							      	      
							      	       actualizarTema(new RedTheme());
							      	       
							      	    break;
							      	    
							      	    
							      	    case 7:
							      	      
							      	       actualizarTema(new CharcoalTheme());
							      	       
							      	    break;
							      	    
							      	    
							      	    case 8:
							      	      
							      	       actualizarTema(new MoodyBlueTheme());
							      	       
							      	    break; 
							      	    
							      	    case 9:
							      	      
							      	      actualizarTema(new ContrastTheme());
							      	      
							      	    break;
							      	      
							      	    case 10:
							      	      
							      	      actualizarTema(new RubyTheme());
							      	      
							      	    break;   
							      	  
							      	  }
							      	       
							      	
							      } 
				 		   	
	             

 		
 		
 	
 	} 
 	
 	 private void actualizarTema(DefaultMetalTheme tema) {
   	 
   	 
   	 	try {
   	 		
        		MetalLookAndFeel.setCurrentTheme(tema);
        		UIManager.setLookAndFeel(new MetalLookAndFeel());
                SwingUtilities.updateComponentTreeUI(menu.crearJFrameParaMenu.getJFrame());
                
                 if (jcTemas.getSelectedIndex() == 0) 
		             
		               menu.setBackground(Color.white);
                else
                    if (jcTemas.getSelectedIndex() == 1)
 	                
 	                 menu.setBackground(Color.white);
 	                 
 	                else    
                     
                       menu.setBackground(tema.getMenuBackground());
           
                
                
            } catch (Exception e)  {
            	
            	System.out.println("Error"+e);
            }
            
   }
 	
 	//***********************************************************************************
 	
 	public void focusGained(FocusEvent F) {
 	    
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());
 	}
 	
 
 	//***********************************************************************************
 	
 	public void focusLost(FocusEvent F) {
 		
 		
 	  if (F.getComponent() == jtPrecioMinimo) {
 	  	
 	  	 
 	  	 if ((!jtPrecioLista.getText().isEmpty() && !jtPrecioMinimo.getText().isEmpty()) && Integer.parseInt(jtPrecioLista.getText())  < Integer.parseInt(jtPrecioMinimo.getText())) {
 	  	 	
 	  	 	 cJf.Mensaje("El porcentaje para el precio minimo debe ser menor al precio de lista","Reporte de Error",0);
 	  	 	 jtPrecioMinimo.setText("");
 	  	 	 jtPrecioMinimo.grabFocus();
 	  	 	
 	  	 }
 	  	
 	  }
			            
 		
 	    // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(cJf.getVisualAtributoPierdeFocoComponentes());
 	}
    
    
    //***********************************************************************************
 	public void valueChanged(TreeSelectionEvent e) {
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();

        Object nodeInfo = node.getUserObject();
        
        if (nodeInfo.equals("IVA")) {
           
           //Se ocultan los Paneles 
           
            panelAlertas.setVisible(false);
            panelConfiguracionesGenerales.setVisible(false);
       	    panelPrecio.setVisible(false);
       	    panelStock.setVisible(false);
       	    panelCopiasSeguridad.setVisible(false);
       	    panelTemas.setVisible(false);
 	        configurarPanelIVA();
 
            
 	    	
        } else
         
            if (nodeInfo.equals("Alertas")) {
                    
                   //Se ocultan los Paneles 
	 	           
	 	            panelIVA.setVisible(false);
	 	            panelConfiguracionesGenerales.setVisible(false);
	 	            panelPrecio.setVisible(false);
	 	            panelStock.setVisible(false);
	 	            panelCopiasSeguridad.setVisible(false);
	 	            panelTemas.setVisible(false);
	 	            configurarPanelAlertas();  
	        	
            } else
	         
	            if (nodeInfo.equals("Configuraciones Generales")) {
	               
	                    //Se ocultan los Paneles 
		 	            
		 	            panelIVA.setVisible(false);
		 	            panelAlertas.setVisible(false);
		 	            panelPrecio.setVisible(false);
		 	            panelStock.setVisible(false);
		 	            panelCopiasSeguridad.setVisible(false);
		 	            panelTemas.setVisible(false);
	 	        
	 	        
	                   configurarPanelConfiguracionesGenerelas();
	        	
	            } else
	         
		            if (nodeInfo.equals("Precios")) {
		               
		                   //Se ocultan los Paneles 
			 	            panelIVA.setVisible(false);
			 	            panelAlertas.setVisible(false);
			 	            panelConfiguracionesGenerales.setVisible(false);
			 	            panelStock.setVisible(false);
			 	            panelCopiasSeguridad.setVisible(false);
			 	            panelTemas.setVisible(false);
		                    configurarPanelPrecios();
		        	
		            
		            } else
	         
			            if (nodeInfo.equals("Stock")) {
			               
			                   //Se ocultan los Paneles 
				 	            panelIVA.setVisible(false);
			 	                panelAlertas.setVisible(false);
			 	                panelConfiguracionesGenerales.setVisible(false);
			 	                panelPrecio.setVisible(false);
			 	                panelCopiasSeguridad.setVisible(false);
			 	                panelTemas.setVisible(false);
			                    configurarPanelStock();
			        	
			            
			            } else
			            
				            if (nodeInfo.equals("Copias De Seguridad")) {
				               
				                   //Se ocultan los Paneles 
					 	            panelIVA.setVisible(false);
				 	                panelAlertas.setVisible(false);
				 	                panelConfiguracionesGenerales.setVisible(false);
				 	                panelPrecio.setVisible(false);
				 	                panelStock.setVisible(false);
				 	                panelTemas.setVisible(false);
			 	                    configurarCopiasSeguridad();
				        	
				            
				            } else
				            
					            if (nodeInfo.equals("Temas")) {
					               
					                   //Se ocultan los Paneles 
						 	            panelIVA.setVisible(false);
					 	                panelAlertas.setVisible(false);
					 	                panelConfiguracionesGenerales.setVisible(false);
					 	                panelPrecio.setVisible(false);
					 	                panelStock.setVisible(false);
					 	                panelCopiasSeguridad.setVisible(false);
			 	                        configurarPanelTemas();
					        	
					            
					            } 
	        
      
    }
	

   
}