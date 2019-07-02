/**
 * Clase: MenuPrincipal 
 * 
 * Version : 1.0
 * 
 * Fecha: 18-03-2008
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
 import com.JASoft.componentes.CrearJFrameParaMenu;
 import com.JASoft.componentes.RelojDigital;
 import com.JASoft.componentes.ConectarMySQL;
 import com.JASoft.componentes.Calendario;
 import com.JASoft.componentes.RubyTheme;
 import com.JASoft.componentes.ContrastTheme;
 import com.JASoft.componentes.SortableTableModel;
 import com.JASoft.componentes.ImprimirReporteJasper;
  
 
 
 import com.JASoft.azkaban.Cliente;
 import com.JASoft.azkaban.Proveedor;
 import com.JASoft.azkaban.Marca;
 import com.JASoft.azkaban.Categoria;
 import com.JASoft.azkaban.Producto;
 import com.JASoft.azkaban.Compra;
 import com.JASoft.azkaban.Venta;
 import com.JASoft.azkaban.AnularVenta;
 import com.JASoft.azkaban.ListadoClientes;
 import com.JASoft.azkaban.ListadoProductos;
 import com.JASoft.azkaban.AnularCompra;
 import com.JASoft.azkaban.Cotizacion;
 import com.JASoft.azkaban.CompraPorProveedor;
 import com.JASoft.azkaban.CompraPorProducto;
 import com.JASoft.azkaban.CompraAnulada;
 import com.JASoft.azkaban.VentaProducto;
 import com.JASoft.azkaban.VentaCliente;
 import com.JASoft.azkaban.VentaAnulada;
 import com.JASoft.azkaban.DevolucionCompra;
 import com.JASoft.azkaban.UtilidadProducto;
 import com.JASoft.azkaban.UtilidadPeriodo;
 import com.JASoft.azkaban.CotizacionCliente;
 import com.JASoft.azkaban.CotizacionPorPeriodos;
 import com.JASoft.azkaban.DevolucionPorCompra;
 import com.JASoft.azkaban.DevolucionVenta;
 import com.JASoft.azkaban.DevolucionPorVenta;
 import com.JASoft.azkaban.RotacionProductos;
 import com.JASoft.azkaban.RotacionMarcas;
 import com.JASoft.azkaban.RotacionCategorias;
 import com.JASoft.azkaban.RotacionClientes;
 import com.JASoft.azkaban.RotacionProveedor;
 import com.JASoft.azkaban.VentaVendedor;
 import com.JASoft.azkaban.CompraPorPeriodo;
 import com.JASoft.azkaban.VentaPorPeriodo;
 import com.JASoft.azkaban.ConsultaCaja;

 
 import javax.swing.*;
 import javax.swing.border.EtchedBorder;
 import javax.swing.plaf.metal.*;
 import javax.swing.table.*;
 import javax.swing.event.*;
 
 
 import java.awt.*;
 import java.awt.event.*; 
 
 import java.net.DatagramPacket;
 import java.net.DatagramSocket;
 import java.net.InetAddress;
  
 import java.sql.ResultSet;
 import java.sql.PreparedStatement;
 
 import java.util.*;
 
 import java.text.*;
 
 import java.io.*;
 
 
 /**
  * Esta clase permite configurar el menu principal que enlaza y configura los demas menus
  */
  
 final class MenuPrincipal extends JDesktopPane implements ActionListener{
 	
 	//Referencia de conexion a la base de datos
 	private ConectarMySQL conectarMySQL;
 	
 	  
    //Se especifica el serial para la serializacion
    static final long serialVersionUID = 19781203;
    
    
    //Menus
    private JMenu parametros;
    private JMenu ventas;
    private JMenu consultas;
    private JMenu reportes;
    private JMenu ayudas;
    private JMenu salir;
 	private JMenu cambioUsuario;
 	private JMenu utilidades;
    private JMenu compras;
    private JMenu inventario;
    private JMenu datosGenerales; 	
    private JMenu comprasConsulta; 	
    private JMenu ventasConsulta; 
    private JMenu utilidad; 
    private JMenu cotizaciones; 
    private JMenu devoluciones; 	
 	private JMenu estadisticas; 
 	private JMenu contabilidad;	
 	
 	//MenuItem's
 	private JMenuItem mIclientes;
 	private JMenuItem mIproveedores;
 	private JMenuItem mICategorias;
 	private JMenuItem mIMarcas;
 	private JMenuItem mIproductos;
 	private JMenuItem mIVendedores;
 	private JMenuItem mIVentas;
 	private JMenuItem parametrosGenerales;
 	private JMenuItem festivos;
 	private JMenuItem mICalendario;
 	private JMenuItem configurarBackup;
 	private JMenuItem cargarBackup;
 	private JMenuItem administrarCuenta;
 	private JMenuItem mantenimientoBD;
 	private JMenuItem mIDevolucionVentas;
	private JMenuItem mIAnular;
    private JMenuItem mICotizacion;
    private JMenuItem mIListadoClientes;
    private JMenuItem mIListadoProductos;
    private JMenuItem mIPorProveedor;
    private JMenuItem mIPorProducto;
    private JMenuItem mIRegistrar;
    private JMenuItem mIDevolucionCompras;
    private JMenuItem mIAnularCompras;
    private JMenuItem mIComprasAnuladas;
    private JMenuItem mIVentasPorCliente; 	
 	private JMenuItem mIVentasPorProducto; 	
    private JMenuItem mIVentasAnuladas;
    private JMenuItem mIUtilidadPorProductos;
 	private JMenuItem mIUtilidadPorPeriodos;
 	private JMenuItem mIListaPrecios;
 	private JMenuItem mICotizacionPorCliente; 	
 	private JMenuItem mICotizacionPorProducto;
 	private JMenuItem mIDevolucionPorCompra; 	
 	private JMenuItem mIDevolucionPorVenta; 	
 	private JMenuItem mIRotacionProductos; 	
 	private JMenuItem mICompararVentas; 	
 	private JMenuItem mIMarcasEstadisticas; 	
 	private JMenuItem mICategoriasEstadisticas;
 	private JMenuItem mIVentasClienteEstadistica; 	
 	private JMenuItem mIVentasClienteProveedor;
 	private JMenuItem mIListadoPrecios;
 	private JMenuItem mICatalogoProductos; 	
 	private JMenuItem mIPrivilegiosUsuario;
 	private JMenuItem mIVentasPorVendedor;
 	private JMenuItem mIKardex;
 	private JMenuItem mICompraPorPeriodo;
 	private JMenuItem mIVentaPorPeriodo;
 	private JMenuItem miAuditorias;
 	private JMenuItem miCaja;
    private JMenuItem miConsultaCaja;
 	
 	
 	//JLabel
 	private JLabel jlFacturacion;
 	private JLabel jlDevolucion;
 	private JLabel jlCotizacion;
 	private JLabel jlAnular;
 	private JLabel jlRegistrar;
 	private JLabel jlDevolucionCompra;
 	private JLabel jlAnularCompra;
 	private JLabel jlProductos;
 	private JLabel jlCategorias;
 	private JLabel jlListado;
 	private JLabel jlMarcas;
 	private JLabel jlListadoPrecios;
 	private JLabel jlCatalogoProductos;
 	private JLabel jlUsuario;
 	private JLabel jlComodin;
 	private JLabel jlValorCaja;
 	
 	//JButton
 	private JButton jbVentas;
 	private JButton jbCompras;
 	private JButton jbProductos;
 	private JButton jbReportes;
 	
 	
 	private String dirIPServidor;
 	
 	private Sesion sesion;
 	
 	private RelojDigital relojDigital;	
 	
 	private Color colorLetraDesHabilitada;
 	
 	private Vector<JMenuItem> arrayComponent;
 
 	public String nombreUsuario;
 	
 	public CrearJFrameParaMenu crearJFrameParaMenu;
 	
 	private HashMap parametrosAzcaban;
 	
 	public int tema = 0;
 	   
      
 	/**
 	 * Constructor
 	 */
 	public MenuPrincipal() {
	   
	   
 	   try {
 	        
 	        System.out.println("1");   
 	            // Se crea la conexion a la BD
		    	conectarMySQL("root","root");
		 		
		 		System.out.println("2");
		 		buscarParametrosAzkaban();
		 		
		 		System.out.println("3");
		 		boolean decorarMarco = parametrosAzcaban.get("DECORARMARCO").equals("false") ? false : true;
		 	       
		 	    crearJFrameParaMenu  = new CrearJFrameParaMenu("Sistema De Información De Inventario Y Facturación Azkaban",decorarMarco);
		 	    
		 	   
		 	    // Se configura el panel
		 	    this.setBounds(0,0,800,570);
		 	    this.setLayout(null);
		 	    crearJFrameParaMenu.getJFrame().getContentPane().add(this);
		 	     
		 	    JLabel jlMoto = new JLabel(new ImageIcon(getClass().getResource("/Imagenes/Moto.gif")));
		 	    jlMoto.setBounds(25,0,340,500);
		 	    add(jlMoto);
		 	     
		 	    jlUsuario = new JLabel();
		 	    jlUsuario.setFont(new Font("Arial",Font.ITALIC,12));
    	        jlUsuario.setBounds(65,493,200,20);
		 	    add(jlUsuario);
		 	    
		 	    jlComodin = new JLabel("Usuario:");
		 	    jlComodin.setBounds(10,493,200,20);
		 	    jlComodin.setFont(new Font("Arial",Font.ITALIC + Font.BOLD,12));
    	        jlComodin.setVisible(false);
		 	    add(jlComodin);
		 	    
		 	    
		 	    //Se define un borde para los paneles
		 	    EtchedBorder borde = new EtchedBorder();
		 	     
		 	    jbVentas = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Ventas.gif")));
		 	    jbVentas.setBounds(390,10,94,111);
		 	    jbVentas.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/VentasS.gif")));
		 	    jbVentas.setBorderPainted(false);
		 	    jbVentas.setContentAreaFilled(false);
		 	    jbVentas.addActionListener(this);
		 	    add(jbVentas);
		 	    
		 	    jbCompras = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Compras.gif")));
		 	    jbCompras.setBounds(390,130,94,111);
		 	    jbCompras.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/ComprasS.gif")));
		 	    jbCompras.setBorderPainted(false);
		 	    jbCompras.setContentAreaFilled(false);
		 	    jbCompras.addActionListener(this);
		 	    add(jbCompras);
		 	   
		 	    jbProductos = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Productos.gif")));
		 	    jbProductos.setBounds(390,250,94,111);
		 	    jbProductos.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/ProductosS.gif")));
		 	    jbProductos.setBorderPainted(false);
		 	    jbProductos.setContentAreaFilled(false);
		 	    jbProductos.addActionListener(this);
		 	    add(jbProductos);
		 	    
		 	    jbReportes = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Reportes.gif")));
		 	    jbReportes.setBounds(390,370,94,111);
		 	    jbReportes.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/ReportesS.gif")));
		 	    jbReportes.setBorderPainted(false);
		 	    jbReportes.setContentAreaFilled(false);
		 	    jbReportes.addActionListener(this);
		 	    add(jbReportes);


		 	    
		 	    // Se configura una etiqueta para el fondo
		 	  	JPanel panelVentas = new JPanel();
		 	  	panelVentas.setBackground (new Color(107,170,57));
		 	  	panelVentas.setLayout(null);
		 	    panelVentas.setBounds(500,10,250,111);
		 	    panelVentas.setBorder(borde);
		 	    add(panelVentas);
		 	    
		 	    final Font tipoletraN = new Font ("Copperplate Gothic Light",Font.PLAIN,18);
		 	    final Font tipoletraS = new Font ("Copperplate Gothic Light",Font.BOLD,18);
		 	    Color colorB = Color.white;
		 	    
		 	    jlFacturacion = new JLabel("<html> <u> F</u>acturación");
		 	    jlFacturacion.setEnabled(false);
		 	    jlFacturacion.setFont(tipoletraN);
		 	    jlFacturacion.setForeground(colorB);
		 	    jlFacturacion.setBounds(20,0,150,40);
		 	    jlFacturacion.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlFacturacion.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	   
		 	    	   if (salir.isEnabled() && salir.isVisible() && jlFacturacion.isEnabled())
		 	    	   
		 	    	     mIVentas.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlFacturacion.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlFacturacion.setFont(tipoletraN);
		 	    	}	
		 	    	
		 	    	
		 	    });
		 	    panelVentas.add(jlFacturacion);
		 	    
		 	   
		 	    jlDevolucion = new JLabel("<html> <u> D</u>evolución");
		 	    jlDevolucion.setEnabled(false);
		 	    jlDevolucion.setFont(tipoletraN);
		 	    jlDevolucion.setForeground(colorB);
		 	    jlDevolucion.setBounds(20,25,150,40);
		 	    jlDevolucion.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlDevolucion.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	 
		 	    	  if (salir.isEnabled() && salir.isVisible() && jlDevolucion.isEnabled())
		 	    	 
		 	    	    mIDevolucionVentas.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlDevolucion.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlDevolucion.setFont(tipoletraN);
		 	    	}	
		 	    	
		 	    });
		 	    panelVentas.add(jlDevolucion);
		 	    
		 	    jlCotizacion = new JLabel("<html> <u> C</u>otización");
		 	    jlCotizacion.setEnabled(false);
		 	    jlCotizacion.setFont(tipoletraN);
		 	    jlCotizacion.setForeground(colorB);
		 	    jlCotizacion.setBounds(20,49,150,40);
		 	    jlCotizacion.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlCotizacion.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	 
		 	    	  if (salir.isEnabled() && salir.isVisible() && jlCotizacion.isEnabled())
		 	    	   
		 	    	     mICotizacion.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlCotizacion.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlCotizacion.setFont(tipoletraN);
		 	    	}	
		 	    	
		 	    });
		 	    panelVentas.add(jlCotizacion);
		 	    
		 	    jlAnular = new JLabel("<html> <u> A</u>nular");
		 	    jlAnular.setEnabled(false);
		 	    jlAnular.setFont(tipoletraN);
		 	    jlAnular.setForeground(colorB);
		 	    jlAnular.setBounds(20,73,150,40);
		 	    jlAnular.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlAnular.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	  
		 	    	  if (salir.isEnabled() && salir.isVisible() && jlAnular.isEnabled())
		 	    	  
		 	    	     mIAnular.doClick();
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlAnular.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlAnular.setFont(tipoletraN);
		 	    	}	
		 	    	
		 	    });
		 	    panelVentas.add(jlAnular); 
		 	     
		 	    JPanel panelCompras = new JPanel();
		 	    panelCompras.setLayout(null);
		 	  	panelCompras.setBackground (new Color(245,145,45));
		 	    panelCompras.setBounds(500,130,250,111);
		 	    panelCompras.setBorder(borde);
		 	    add(panelCompras);
		 	   
		 	    jlRegistrar = new JLabel("<html> <u>R</u>egistrar");
		 	    jlRegistrar.setEnabled(false);
		 	    jlRegistrar.setFont(tipoletraN);
		 	    jlRegistrar.setForeground(colorB);
		 	    jlRegistrar.setBounds(20,10,150,40);
		 	    jlRegistrar.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlRegistrar.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	   
		 	    	   if (salir.isEnabled() && salir.isVisible() && jlAnular.isEnabled())
		 	    	   
		 	    	       mIRegistrar.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlRegistrar.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlRegistrar.setFont(tipoletraN);
		 	    	}	
		 	    	
		 	    });
		 	    panelCompras.add(jlRegistrar);
		 	    
		 	   
		 	    jlDevolucionCompra = new JLabel("<html> D<u>e</u>volución");
		 	    jlDevolucionCompra.setEnabled(false);
		 	    jlDevolucionCompra.setFont(tipoletraN);
		 	    jlDevolucionCompra.setForeground(colorB);
		 	    jlDevolucionCompra.setBounds(20,35,150,40);
		 	    jlDevolucionCompra.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlDevolucionCompra.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	  
		 	    	   if (salir.isEnabled() && salir.isVisible()  && jlDevolucionCompra.isEnabled())
		 	    	  
		 	    	      mIDevolucionCompras.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlDevolucionCompra.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlDevolucionCompra.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelCompras.add(jlDevolucionCompra);
		 	  
		 	  
		 	    jlAnularCompra = new JLabel("<html> A<u>n</u>ular");
		 	    jlAnularCompra.setEnabled(false);
		 	    jlAnularCompra.setFont(tipoletraN);
		 	    jlAnularCompra.setForeground(colorB);
		 	    jlAnularCompra.setBounds(20,58,150,40);
		 	    jlAnularCompra.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlAnularCompra.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	   
		 	    	   if (salir.isEnabled() && salir.isVisible() && jlAnularCompra.isEnabled())
		 	    	   
		 	    	      mIAnularCompras.doClick();
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlAnularCompra.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlAnularCompra.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelCompras.add(jlAnularCompra); 
		 	    
		 	    
		 	      
		 	    JPanel panelProductos = new JPanel();
		 	  	panelProductos.setLayout(null);
		 	  	panelProductos.setBackground (new Color(55,105,175));
		 	    panelProductos.setBounds(500,250,250,111);
		 	    panelProductos.setBorder(borde);
		 	    add(panelProductos);
		 	  	
		 	  	 	    
		 	    jlProductos = new JLabel("<html> <u> P</u>roductos");
		 	    jlProductos.setEnabled(false);
		 	    jlProductos.setFont(tipoletraN);
		 	    jlProductos.setForeground(colorB);
		 	    jlProductos.setBounds(20,0,150,40);
		 	    jlProductos.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlProductos.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	  
		 	    	  if (salir.isEnabled() && salir.isVisible() && jlProductos.isEnabled())
		 	    	   
		 	    	    mIproductos.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlProductos.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlProductos.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelProductos.add(jlProductos);
		 	    
		 	   
		 	    jlMarcas = new JLabel("<html> <u> M</u>arcas");
		 	    jlMarcas.setEnabled(false);
		 	    jlMarcas.setFont(tipoletraN);
		 	    jlMarcas.setForeground(colorB);
		 	    jlMarcas.setBounds(20,24,150,40);
		 	    jlMarcas.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlMarcas.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	 
		 	    	  if (salir.isEnabled() && salir.isVisible() && jlMarcas.isEnabled())  
		 	    	  
		 	    	     mIMarcas.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlMarcas.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlMarcas.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelProductos.add(jlMarcas);
		 	    
		 	    jlCategorias= new JLabel("<html> <u> C</u>ategorías");
		 	    jlCategorias.setEnabled(false);
		 	    jlCategorias.setFont(tipoletraN);
		 	    jlCategorias.setForeground(colorB);
		 	    jlCategorias.setBounds(20,49,150,40);
		 	    jlCategorias.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlCategorias.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	    	  
		 	    	  if (salir.isEnabled() && salir.isVisible() && jlCategorias.isEnabled())     
		 	    	   
		 	    	     mICategorias.doClick();	
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlCategorias.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlCategorias.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelProductos.add(jlCategorias);
		 	    
		 	    jlListado = new JLabel("<html> <u> L</u>istado Productos");
		 	    jlListado.setEnabled(false);
		 	    jlListado.setFont(tipoletraN);
		 	    jlListado.setForeground(colorB);
		 	    jlListado.setBounds(20,73,220,40);
		 	    jlListado.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlListado.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	      	  
		 	      	  if (salir.isEnabled() && salir.isVisible() && jlListado.isEnabled())     
		 	    	  
		 	    	  
		 	    	     mIListadoProductos.doClick();
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlListado.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		jlListado.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelProductos.add(jlListado); 
		 	   
		 	  	JPanel panelReportes = new JPanel();
		 	  	panelReportes.setLayout(null);
		 	  	panelReportes.setBackground (new Color(100,80,160));
		 	    panelReportes.setBounds(500,370,250,111);
		 	    panelReportes.setBorder(borde);
		 	    add(panelReportes);
		 	    
		 	    
		 	    jlListadoPrecios = new JLabel("<html>  L<u>i</u>stado Precios");
		 	  //  jlListadoPrecios.setEnabled(false);
		 	    jlListadoPrecios.setFont(tipoletraN);
		 	    jlListadoPrecios.setForeground(colorB);
		 	    jlListadoPrecios.setBounds(20,0,180,40);
		 	    jlListadoPrecios.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlListadoPrecios.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	      	  
		 	      	  if (salir.isEnabled() && salir.isVisible() && jlListadoPrecios.isEnabled())     
		 	    	  
		 	    	  
		 	    	     mIListadoPrecios.doClick();
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlListadoPrecios.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		
		 	    		jlListadoPrecios.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelReportes.add(jlListadoPrecios); 
		 	  	
		 	  	jlCatalogoProductos = new JLabel("<html>  Ca<u>t</u>alogo ");
		 	  //  jlListadoPrecios.setEnabled(false);
		 	    jlCatalogoProductos.setFont(tipoletraN);
		 	    jlCatalogoProductos.setForeground(colorB);
		 	    jlCatalogoProductos.setBounds(20,24,180,40);
		 	    jlCatalogoProductos.setCursor(crearJFrameParaMenu.getCursorMano());
		 	    jlCatalogoProductos.addMouseListener(new MouseAdapter() {
		 	    	
		 	    	public void mouseClicked(MouseEvent m) {
		 	      	  
		 	      	  if (salir.isEnabled() && salir.isVisible() && jlCatalogoProductos.isEnabled())     
		 	    	  
		 	    	  
		 	    	     mICatalogoProductos.doClick();
		 	    	 
		 	    	}
		 	    	public void mouseEntered(MouseEvent m) {
		 	    		jlCatalogoProductos.setFont(tipoletraS);
		 	    	}
		 	    	public void mouseExited(MouseEvent m) {
		 	    		
		 	    		jlCatalogoProductos.setFont(tipoletraN);
		 	    	}
		 	    	
		 	    });
		 	    panelReportes.add(jlCatalogoProductos); 
		 	    
		 	    //Se configura el JLabel para el valor de Caja
		 	    jlValorCaja = new JLabel("Valor Caja: 0");
		 	    jlValorCaja.setBounds(380,485,180,40);
		 	    add(jlValorCaja);
		 	   	
		 	  	
		 	  	crearJFrameParaMenu.getJFrame().setVisible(true);
		 	  	
		 	    // Se configura el icono del crearJFrameParaMenu.getJFrame()
		 		//crearJFrameParaMenu.getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/IconoMenuPrincipal.gif")).getImage());
		 	
		 	    // Se Intancia el JInternalcrearJFrameParaMenu.getJFrame()
		 	    sesion = new Sesion(this);
		 	
   	            sesion.setVisible(true);
   	                      
		 	    crearJFrameParaMenu.getJFrame().addWindowListener(new WindowAdapter() {
		 	    	public void windowClosing(WindowEvent awt) {
		 	    		
		 	    	  	crearCopiaSeguridad();
		 	    	}
		 	    });
		 	    
		 	    
		 	    crearJFrameParaMenu.getJFrame().addComponentListener(new ComponentAdapter() {
  	   
  	    	        public void componentShown(ComponentEvent e) {
  	 
  	                      buscarCaja();
  	                          
		 	        }
		 	    });
		 	      
 		  
		 	      
		 	
		 	
 	   } catch (Exception e) {
 	   	   
 	   	   crearJFrameParaMenu.Mensaje("Intento de conexión fallido"+e,"Error",JOptionPane.ERROR_MESSAGE);
 	   	   
 	
 	   	   
 	   }
 	   
 	   crearJFrameParaMenu.getJFrame().addComponentListener(new ComponentAdapter() {
  	    	public void componentShown(ComponentEvent e) {
  	    	  
  	    	       if (relojDigital != null && crearJFrameParaMenu.getJFrame().getName().equals("Menu"))	{
  	    	   
  	    	             add(relojDigital);
 		                 
 		                 crearJFrameParaMenu.getJFrame().setName("Menu Principal");
 		
  	    	       }
  	    	  }
  	   }); 		
 
 	
 	    
       //Se configuran la presentacion de lo JDialog
 	   JDialog.setDefaultLookAndFeelDecorated(true);
 	    
 	}
 	
 	//***********************************************************************************************************************************

 	 private void actualizarTema(int tema) {
   	 
   
   	 	try {   
   	 	
   	 	        DefaultMetalTheme temaUsuario = null;
   	 	
   	 	        switch (tema) {
							      	    
							      	    case 0:
							      	      
							      	      temaUsuario = new OceanTheme();
							      	      
							      	    break;
							      	    
							      	    case 1:
							      	       
							      	       temaUsuario =  new DefaultMetalTheme();
							      	    
							      	    break;
							      	    
							      	    case 2:
							      	      
							      	       temaUsuario = new OfficeXPTheme();
							      	       
							      	    break;
							      	    
							      	    case 3:
							      	      
							      	       temaUsuario = new GreenMetalTheme();
							      	       
							      	    break;
							      	    
							      	    
							      	    case 4:
							      	      
							      	       temaUsuario = new AquaMetalTheme();
							      	       
							      	    break;
							      	    
							      	    
							      	    
							      	    case 5:
							      	      
							      	       temaUsuario = new KhakiMetalTheme();
							      	       
							      	    break;
							      	    
							      	    
							      	    case 6:
							      	      
							      	       temaUsuario = new RedTheme();
							      	       
							      	    break;
							      	    
							      	    
							      	    case 7:
							      	      
							      	       temaUsuario = new CharcoalTheme();
							      	       
							      	    break;
							      	    
							      	    
							      	    case 8:
							      	      
							      	       temaUsuario = new MoodyBlueTheme();
							      	       
							      	    break; 
							      	    
							      	    case 9:
							      	      
							      	      temaUsuario = new ContrastTheme();
							      	      
							      	    break;
							      	      
							      	    case 10:
							      	      
							      	      temaUsuario = new RubyTheme();
							      	      
							      	    break;      
							      	  
							      	  }
							      	  
   	 		
        		MetalLookAndFeel.setCurrentTheme(temaUsuario);
        		UIManager.setLookAndFeel(new MetalLookAndFeel());
                SwingUtilities.updateComponentTreeUI(crearJFrameParaMenu.getJFrame());
                
                 if (tema == 0) 
		             
		               this.setBackground(Color.white);
                else
                    if (tema == 1)
 	                
 	                 this.setBackground(Color.white);
 	                 
 	                else    
                     
                       this.setBackground(temaUsuario.getMenuBackground());
           
                
                
            } catch (Exception e)  {
            	
            	System.out.println("Error"+e);
            }
            
    }
 	
 	
	//***********************************************************************************************************************************

 	protected boolean validarUsuario(String usuario,char[] clave) {
 	
 	    boolean resultadoBoolean = false;
 	   
 	    // Se valida que el usuario exista en la bd
 	    	
 		String password = new String(clave);
 		
 		String sentenciaSQL = "Select password,Tema "+
 		                      "From Usuarios " +
 		                      "Where Id='"+usuario+"' And password='"+password+"'";
 		  
 		try {
   		    
   		    ResultSet resultado = conectarMySQL.buscarRegistro (sentenciaSQL);
   	
   		    if (resultado != null)
   		    	
   		    	if (resultado.next()) {
   		  
   		    		
   		    		if (password.equals(resultado.getString(1))) {
   	                      
   	                      sesion.setVisible(false);
   	                      
	    	              nombreUsuario = usuario;
	    	              
	    	              jlComodin.setVisible(true);
	    	              
	    	              jlUsuario.setText(nombreUsuario);
          
   	                      tema = resultado.getInt(2);
   	                    
   	                      //Se configura el Tema
   		    			  actualizarTema(tema);
   		    			
   		    			  //Se envia por parametro el nombre de usuario
	   		    		  conectarMySQL.setNombreUsuario(usuario);
	   		    		
	   		    		  iniciarMenu();
		        	
		        	      crearArrayComponent();
    		        
    		              configurarMenu(usuario);
    		        
    		              // Se inicia el reloj
		 	              iniciarReloj();   
		 	              
		 	              
		 	   	          crearJFrameParaMenu.iniciarConfiguracionesInicialesPorDefecto(); //Se adicionan las cnfiguraciones iniciales
		 	   	          
		 	   	          buscarCaja();
		 	   	
		 	           	  abrirCaja(); //Se abre la caja
		 	   

	   		    		  resultadoBoolean = true;
	   		    		  
   		    		}   
   		    		  
   		    
   		 }
   		} catch (Exception e) {
   			
   			System.out.println(""+e);
   	
   		}    
 	     
 	     
 	    return resultadoBoolean;
   		          
 	}
 	
 	
 	//************************************************************************************************************************
 	
 	private void conectarMySQL( String usuario, String password ) throws Exception {
 		System.out.println("1");
 	
 		conectarMySQL = new ConectarMySQL(/*obtenerIPServidor()*/"127.0.0.1","Azkaban",usuario,password);
 		
 		System.out.println("2");
 	}
 
 	
 	//************************************************************************************************************************
 	
 	private void iniciarReloj() {
 		
 		// Se configura el reloj Digital
  		relojDigital = new RelojDigital();
 		this.add(relojDigital);
 		
 		this.repaint();
 		this.updateUI();
 		
 		crearJFrameParaMenu.getJFrame().repaint();
// 		crearJFrameParaMenu.getJFrame().getContentPane().updateUI();
 	
 	}
 	
  	
  	//******************************************************************************************************************  
    
    private final String obtenerIPServidor() {
		
		if (dirIPServidor == null) {
		
				try {
				        //** Se cre un objeto de tipo File
				    	File archivo=new File("configuracion/DirIP.dat");
				    			 
				    	//** Se crea un canal de salida al archivo
				        FileInputStream entrada=new FileInputStream(archivo);
				    	
				    	// Se crea un array de Bytes
				    	byte[] bt = new byte[(int)archivo.length()];
				    	
				    	// Se escribe en el archivo
				    	 entrada.read(bt);
				    	
				    	// Se retorna la Direccion IP		
				    	dirIPServidor =  new String(bt);
				    	
				    			
		    } catch (IOException e)	{
		    
		       System.out.println("Error al crear el archivo"+e);
		    }
		   
		 } 
		 
		 
		 return dirIPServidor;
	    			
   }
   
   //******************************************************************************************************************

   public void buscarParametrosAzkaban() {
   	  
   	  
   	  parametrosAzcaban = conectarMySQL.buscarParametros(); //Se buscan los parametros configurados
		 		
   	
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
              	
              	jlValorCaja.setText("Valor Caja: " + resultado.getString(1));
                
              	
              }
            
            }  
        
       
      }  catch(Exception e) {
          
            crearJFrameParaMenu.Mensaje("Error al buscar valor de caja"+e,"Error",JOptionPane.ERROR_MESSAGE);
      }    
		 		
   	
   }
   
   //******************************************************************************************************************

    private void mostrarAdministrarCuenta() {
  		
  		  AdministrarCuenta AdministrarCuentas = new AdministrarCuenta(conectarMySQL,this,nombreUsuario);
		  this.add(AdministrarCuentas);
		
		  crearJFrameParaMenu.getJFrame().setContentPane(this);
	
		  try {
		  
		     AdministrarCuentas.setSelected(true);
		   } catch (java.beans.PropertyVetoException e) {}
	 
	 	  habilitarMenu(false);

  		
  	}
  	
  	//******************************************************************************************************************

  	private void cambiarUsuario() {
  		
	    ocultarMenu();
	    
 	    sesion.limpiar();
 	    
 	    sesion.jtUsuario.grabFocus();
 	    
 	    sesion.setVisible(true);
	    
 			    
 			    
  	}
  	
  	//******************************************************************************************************************
  
    private void mostrarUsuario() {
  		
  		
	    PrivilegioUsuario mIVendedores = new PrivilegioUsuario(conectarMySQL,this);
		this.add(mIVendedores);
		
		crearJFrameParaMenu.getJFrame().setContentPane(this);
	
		try {

		     mIVendedores.setSelected(true);

		} catch (java.beans.PropertyVetoException e) {}
	 
        habilitarMenu(false);
  	}
  	
  	
  	//*********************************************************************************************************************
 	
 	final private void mostrarCalendario() {
 		   
 		   	 
		 		   Calendario  calendario = new Calendario(crearJFrameParaMenu.getJFrame(),630,290);
		           this.add(calendario);
		           
		           crearJFrameParaMenu.getJFrame().setContentPane(this);
		           
		           
		           habilitarMenu(false);

 	}
 	
 	//*********************************************************************************************************************
 	
 	final private void mostrarAyudaTeclado() {
 		
 	   AyudaTeclado ayudaTeclado = new AyudaTeclado(this);
       this.add(ayudaTeclado);
       crearJFrameParaMenu.getJFrame().setContentPane(this);
       
       try {
    
       	   ayudaTeclado.setSelected(true);
    
       } catch (java.beans.PropertyVetoException e) {}
       
       habilitarMenu(false);
 		
 		
 	}
 	
 	
 	//*********************************************************************************************************************
 	
 	 final private void mostrarAcercaDe() {
	
		
		AcercaDe acercaDe = new AcercaDe(this);
 	    add(acercaDe);
 	    
 	    try {
	       acercaDe.setSelected(true);
	    } catch (java.beans.PropertyVetoException e) {}
	    
	    habilitarMenu(false);
 			         
	
 	}
 	
 	//*********************************************************************************************************************
 	
 	final private void mostrarParametrosGenerales() {
 		   
 		
	 	   ParametrosGenerales parametrosGenerales = new ParametrosGenerales(conectarMySQL,this,nombreUsuario,tema);
	 	   this.add(parametrosGenerales,BorderLayout.CENTER);
	       crearJFrameParaMenu.getJFrame().setContentPane(this);
	       
	       try {
	    
	       	   parametrosGenerales.setSelected(true);
	    
	       } catch (java.beans.PropertyVetoException e) {}
	       
	       habilitarMenu(false);
 		
 	} 
    //************************************************************************************************************************

    
    private void crearArrayComponent() {
   	
   	    arrayComponent = new Vector<JMenuItem>();
   	    
   	    arrayComponent.addElement(parametrosGenerales);
   	    arrayComponent.addElement(festivos);
   	    arrayComponent.addElement(mIPrivilegiosUsuario);
   	    
   	    arrayComponent.addElement(mIclientes);
   	    arrayComponent.addElement(mIproveedores);
   	    arrayComponent.addElement(mIVendedores);
   	    
   	    
   	    arrayComponent.addElement(mIVentas);
   	    arrayComponent.addElement(mIDevolucionVentas);
   	    arrayComponent.addElement(mICotizacion);
   	    arrayComponent.addElement(mIAnular);
   	    
   	    arrayComponent.addElement(mIRegistrar);
   	    arrayComponent.addElement(mIDevolucionCompras);
   	    arrayComponent.addElement(mIAnularCompras);
   	    
   	    arrayComponent.addElement(mIproductos);
   	    arrayComponent.addElement(mIMarcas);
   	    arrayComponent.addElement(mICategorias);
   	    arrayComponent.addElement(mIListadoProductos);
   	    
   	    arrayComponent.addElement(configurarBackup);
   	    arrayComponent.addElement(cargarBackup);
   	    arrayComponent.addElement(miAuditorias);

   	    
   	
    }

 	//************************************************************************************************************************
 	
 	private void ocultarMenu() {
 	 	
 	 	//Se ocultan los menus
 	 	parametros.setVisible(false);
 	    consultas.setVisible(false);
        reportes.setVisible(false);
        ayudas.setVisible(false);
        cambioUsuario.setVisible(false);
        utilidades.setVisible(false);
        salir.setVisible(false);
        datosGenerales.setVisible(false);
        ventas.setVisible(false);
        compras.setVisible(false);
        inventario.setVisible(false);
     
	    
	    //Se remueven los item
	    parametros.removeAll();
	    parametros.add(administrarCuenta);
	    utilidades.removeAll();
        utilidades.add(mICalendario);
        
 	   
 	 }
     
     //*********************************************************************************************************************
 	
 	final private void crearCopiaSeguridad() {
 	 
        try {
        	
        	//Se almacena un registro para Cerrar Caja
        	cerrarCaja();
        
        	final String sentenciaSQL = "Select Valor "+
        	                            "From Parametros "+
        	                            "Where CODParam in (21,22) " +
                                        "Order by CODParam ";
                                        
                                        
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            
            String fecha = formato.format(new Date());
                                                            
           	ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
        	
        	if (resultado !=null) {
             
              if (resultado.next()) {
              	
              	    String rutaBD = resultado.getString(1);
              	    
              	    if (rutaBD.endsWith("/"))
              	      rutaBD = rutaBD.substring(0,rutaBD.length() - 1);
              	
	              	String ruta = rutaBD +"/Backup-" + fecha;
	              	
	              	resultado.next();
	              	String dirHost1 = resultado.getString(1);
	              	
	              	resultado.next();
	              	String dirHost2 = resultado.getString(1);
	              	
	              	resultado.next();
	              	String dirHost3 = resultado.getString(1);
	              	
	              
	              	
	              	
	    			//copia de seguridad
	        		char chars[] = {'"'};
	              	
	              	String host1 = chars[0] + "C:/Archivos de programa/MySQL/MySQL Server 5.1/bin/mysqldump" + chars[0] + " --opt --password=A_Z.kA.BAN --user=AZkABANPrionero --routines -h " + dirHost1  +  "  Azkaban > "+chars[0]+ ruta +"/BD_Azkaban_Host1.sql"+chars[0];;
	    		
	    			String host2 = chars[0] + "C:/Archivos de programa/MySQL/MySQL Server 5.1/bin/mysqldump" + chars[0] + " --opt --password=A_Z.kA.BAN --user=AZkABANPrionero --routines -h "+ dirHost2 +" Azkaban > "+chars[0]+ ruta +"/BD_Azkaban_Host2.sql"+chars[0];
	    			
	    			String host3 = chars[0] +"C:/Archivos de programa/MySQL/MySQL Server 5.1/bin/mysqldump"+ chars[0] +" --opt --password=A_Z.kA.BAN --user=AZkABANPrionero --routines -h "+ dirHost3 +" Azkaban > "+chars[0]+ ruta +"/BD_Azkaban_Servidor3.sql"+chars[0];;
	    	
	    	        String sentenciaMySQLDump = host1 +"\n\n\n \n"+ (dirHost2.length() > 0 ? host2 : "") +"\n"+ (dirHost3.length() > 0 ? host3 : "") +" \n exit";
       		
                    DatagramPacket paqueteSalida = new DatagramPacket(sentenciaMySQLDump.getBytes(),sentenciaMySQLDump.length(),InetAddress.getByName(obtenerIPServidor()),60000); 
       				
       			    DatagramSocket socketUDP = new DatagramSocket();

       		        socketUDP.send(paqueteSalida);
       				
		            conectarMySQL.getConexion().close();
		      
		            
		       } else 
		       	  crearJFrameParaMenu.Mensaje("Se deben los parametros para los backup","Error",JOptionPane.ERROR_MESSAGE);	 
		       	  
            }
        } catch(Exception e) {
            crearJFrameParaMenu.Mensaje("Error al crear backup"+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    //********************************************************************************************************************************************
    
    private void cerrarCaja() {
    	
    	try {
    		
    		
    		conectarMySQL.getSentencia().execute("Insert Into  MovimientosCaja Values(null,(Select Sysdate()),'" + nombreUsuario + "',2,0,"+ jlValorCaja.getText().substring(12) + ",'CIERRE CAJA')"); 
    		
    		conectarMySQL.commit();
    		
    	} catch (Exception e) {
    		
    		System.out.println("Error :" +e);
    	}
    	
    }
 	
    //********************************************************************************************************************************************
    
    private void abrirCaja() {
    	
    	try {
    		
    		System.out.println("Insert Into  MovimientosCaja Values(null,(Select Sysdate()),'" + nombreUsuario + "',1,0,"+ jlValorCaja.getText().substring(12) + ",'Apertura Caja')"); 
    		conectarMySQL.getSentencia().execute("Insert Into  MovimientosCaja Values(null,(Select Sysdate()),'" + nombreUsuario + "',1,0,"+ jlValorCaja.getText().substring(12) + ",'APERTURA CAJA')"); 
    		
    		conectarMySQL.commit();
    		
    	} catch (Exception e) {
    		
    		System.out.println("Error :" +e);
    	}
    	
    }
 	
    //*********************************************************************************************************************
    
    //**** Este metodo inicia e instancia los compornetes del JMenu, se coloca aqui para mejorar tiempos de respuesta
    
    private void iniciarMenu() {
    	
    		    // Se configuran los JMenu
		 		parametros = new JMenu("Parámetros");
		 		parametros.setMnemonic('P');
		 		parametros.setActionCommand("Parametros");
		 		parametros.setVisible(false);
			 	
			 	
			 	datosGenerales = new JMenu("Datos Generales");
			 	datosGenerales.setMnemonic('v');
		 		datosGenerales.setActionCommand("datosGenerales");
			 	
			 	
			 	ventas = new JMenu("Ventas");
		 		ventas.setMnemonic('v');
		 		ventas.setActionCommand("ventas");
		 		ventas.setVisible(false);
			 	
			 	compras = new JMenu("Compras");
		 		compras.setMnemonic('C');
		 		compras.setActionCommand("compras");
		 		compras.setVisible(false);
		 		
		 		
		 		inventario = new JMenu("Inventario");
		 		inventario.setMnemonic('I');
		 		inventario.setActionCommand("inventario");
		 	    inventario.setVisible(false);
			 	
		 	    consultas = new JMenu("Consultas");
		 		consultas.setMnemonic('C');   	 
		 	//	consultas.setVisible(false);		
               
                reportes = new JMenu("Reportes");
		 		reportes.setMnemonic('R');   	 		
		 	//	reportes.setVisible(false);		
		 		
		 	    utilidades = new JMenu("Utilidades");
		 		utilidades.setMnemonic('U');
		 	
		 		ayudas = new JMenu("Ayudas");
		 		ayudas.setMnemonic('Y');
		 		ayudas.setVisible(false);
		        
		 		
		 		cambioUsuario = new JMenu("<html>Cam<u>b</u>iar  De Usuario");
		 		cambioUsuario.setVisible(false);
		 		cambioUsuario.addMouseListener(new MouseAdapter() {
		 			public void mouseClicked(MouseEvent m) {
		 				
		 				if (cambioUsuario.isEnabled()) 
		 				
		 			      cambiarUsuario();
		 			  
		 			  }	
		 			
		 		});
				 		

		 		salir = new JMenu("<html> <u> S</u>alir");
		 		//salir.setVisible(false);
		 		salir.setToolTipText("Salir");
		 		salir.addMouseListener(new MouseAdapter() {
		 			public void mouseClicked(MouseEvent m) {
		 			    if (salir.isEnabled()) {
		 			    	
		 			    	 crearCopiaSeguridad();
		 				     System.exit(0);
		 				     
		 				}     
		 			  }	
		 			
		 		});
		
		 	  
		 		parametrosGenerales = new JMenuItem("Parametros Generales",new ImageIcon(getClass().getResource("/Imagenes/Admin.gif")));
		 		parametrosGenerales.setMnemonic('R');
		 		parametrosGenerales.setToolTipText("Permite configurar parametros generales al SIICE");
		 		parametrosGenerales.addActionListener(this);
		 		
		 		festivos = new JMenuItem("Festivos",new ImageIcon(getClass().getResource("/Imagenes/Festivos.gif")));
		 		festivos.setMnemonic('F');
		 		festivos.setToolTipText("Permite Configurar los Festivos del Año Vigente");
		 		festivos.addActionListener(this);
		 	
		 	    administrarCuenta = new JMenuItem("Administrar Cuenta",new ImageIcon(getClass().getResource("/Imagenes/Admin.gif")));
		 		administrarCuenta.setMnemonic('A');
		 		administrarCuenta.setToolTipText("Permite Cambiar la Contraseña al Usuario");
		 		administrarCuenta.addActionListener(this);
		 		parametros.add(administrarCuenta);
	
		 	    mIPrivilegiosUsuario = new JMenuItem("Privilegios de usuario",new ImageIcon(getClass().getResource("/Imagenes/Admin.gif")));
		 		mIPrivilegiosUsuario.setMnemonic('A');
		 		mIPrivilegiosUsuario.setToolTipText("Permite Cambiar la Contraseña al Usuario");
		 		mIPrivilegiosUsuario.addActionListener(this);
		 		
		 	   
		 		//Se congigura los  JMenuItem Para Facturacion
		 		
			    mIVentas = new JMenuItem("Facturación",new ImageIcon(getClass().getResource("/Imagenes/VentasFacturacion.gif")));
			    mIVentas.setMnemonic('F');
			    mIVentas.setToolTipText("Permite crear compras ");
			    mIVentas.addActionListener(this);
			    mIVentas.setActionCommand("Facturacion");
			    mIVentas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,0));
			    
			    mIDevolucionVentas = new JMenuItem("Devolución",new ImageIcon(getClass().getResource("/Imagenes/VentasDevolucion.gif")));
			    mIDevolucionVentas.setMnemonic('D');
			    mIDevolucionVentas.setToolTipText("Permite crear devoluciones ");
			    mIDevolucionVentas.addActionListener(this);
			    mIDevolucionVentas.setActionCommand("Devolucion");
			    mIDevolucionVentas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,0));
			  	
		 	   
			    mICotizacion = new JMenuItem("Cotización",new ImageIcon(getClass().getResource("/Imagenes/VentasCotizacion.gif")));
			    mICotizacion.setMnemonic('C');
			    mICotizacion.setToolTipText("Permite crear devoluciones ");
			    mICotizacion.addActionListener(this);
			    mICotizacion.setActionCommand("Cotizacion");
			    mICotizacion.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,0));
		
			    mIAnular = new JMenuItem("Anular",new ImageIcon(getClass().getResource("/Imagenes/VentasAnuladas.gif")));
			    mIAnular.setMnemonic('A');
			    mIAnular.setToolTipText("Permite crear devoluciones ");
			    mIAnular.addActionListener(this);
			    mIAnular.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,0));
			  	 
			    //Se configura el menu datos Generales

			    mIclientes = new JMenuItem("Clientes",new ImageIcon(getClass().getResource("/Imagenes/Clientes.gif")));
			    mIclientes.setMnemonic('C');
			    mIclientes.setToolTipText("Permite crear mIclientes ");
			    mIclientes.addActionListener(this);
			   
		 	   
			    mIproveedores = new JMenuItem("Proveedores",new ImageIcon(getClass().getResource("/Imagenes/Proveedores.gif")));
			    mIproveedores.setMnemonic('C');
			    mIproveedores.setToolTipText("Permite crear mIproveedores ");
			    mIproveedores.addActionListener(this);
			   
			   
		 		mIVendedores = new JMenuItem("Vendedores",new ImageIcon(getClass().getResource("/Imagenes/Vendedor.gif")));
			    mIVendedores.setMnemonic('U');
			    mIVendedores.setToolTipText("Permite crear Vendedores y configurar privilegios");
			    mIVendedores.addActionListener(this);
			  	
		 		//Se congigura los  JMenuItem Para Compras
		 		
			    mIRegistrar = new JMenuItem("Registrar",new ImageIcon(getClass().getResource("/Imagenes/ComprasRegistrar.gif")));
			    mIRegistrar.setMnemonic('R');
			    mIRegistrar.setToolTipText("Permite crear devoluciones ");
			    mIRegistrar.addActionListener(this);
			    mIRegistrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,0));
			  	
			    
			    mIDevolucionCompras = new JMenuItem("Devolución",new ImageIcon(getClass().getResource("/Imagenes/ComprasDevolucion.gif")));
			    mIDevolucionCompras.setActionCommand("DevolucionCompras");
			    mIDevolucionCompras.setMnemonic('e');
			    mIDevolucionCompras.setToolTipText("Permite crear devoluciones ");
			    mIDevolucionCompras.addActionListener(this);
			    mIDevolucionCompras.setActionCommand("DevolucionCompras");
			    mIDevolucionCompras.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,0));
			  	 
			    mIAnularCompras = new JMenuItem("Anular",new ImageIcon(getClass().getResource("/Imagenes/ComprasAnuladas.gif")));
			    mIAnularCompras.setMnemonic('n');
			    mIAnularCompras.setActionCommand("AnularCompras");
			    mIAnularCompras.setToolTipText("Permite crear devoluciones ");
			    mIAnularCompras.addActionListener(this);
			    mIAnularCompras.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,0));
			  	
			    //Se configura los item's JMenu de Inventario
			    mIproductos = new JMenuItem("Productos",new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")));
			    mIproductos.setMnemonic('C');
			    mIproductos.setToolTipText("Permite crear mICategorias ");
			    mIproductos.addActionListener(this);
			    mIproductos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,0));
			    
		 		mICategorias = new JMenuItem("Categorias",new ImageIcon(getClass().getResource("/Imagenes/InventarioCategorias.gif")));
			    mICategorias.setMnemonic('C');
			    mICategorias.setToolTipText("Permite crear mICategorias ");
			    mICategorias.addActionListener(this);
			    mICategorias.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,0));
			    
			    mIMarcas = new JMenuItem("Marcas",new ImageIcon(getClass().getResource("/Imagenes/InventarioMarcas.gif")));
			    mIMarcas.setMnemonic('C');
			    mIMarcas.setToolTipText("Permite crear mICategorias ");
			    mIMarcas.addActionListener(this);
			    mIMarcas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,0));
			    
			    mIListadoProductos = new JMenuItem("Listado Productos",new ImageIcon(getClass().getResource("/Imagenes/ListadoProductos.gif")));
			    mIListadoProductos.setMnemonic('L');
			    mIListadoProductos.setToolTipText("Permite crear mICategorias ");
			    mIListadoProductos.addActionListener(this);
			    mIListadoProductos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,0));
			    
			    
			    mIKardex = new JMenuItem("Kardex",new ImageIcon(getClass().getResource("/Imagenes/InventarioMarcas.gif")));
			    mIKardex.setMnemonic('K');
			    mIKardex.setToolTipText("Permite crear mICategorias ");
			    mIKardex.addActionListener(this);
			    mIKardex.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,0));
			    
			    //Se configura el menu consultas
			    contabilidad = new JMenu("Contabilidad");
			    contabilidad.setMnemonic('C');
			    contabilidad.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Contabilidad.gif")));
			    consultas.add(contabilidad);
			    consultas.addSeparator();
			    
			    
			    miCaja =  new JMenuItem("Caja ",new ImageIcon(getClass().getResource("/Imagenes/Caja.gif")));
			    miCaja.setMnemonic('A');
			    miCaja.setToolTipText("Permite crear mICategorias ");
			    miCaja.addActionListener(this);
			    contabilidad.add(miCaja);
			    contabilidad.addSeparator();
		     
			    
			    miConsultaCaja =  new JMenuItem("Consulta Caja ",new ImageIcon(getClass().getResource("/Imagenes/ConsultaCaja.gif")));
			    miConsultaCaja.setMnemonic('A');
			    miConsultaCaja.setToolTipText("Permite crear mICategorias ");
			    miConsultaCaja.addActionListener(this);
			    contabilidad.add(miConsultaCaja);
			  
			     
			    mIListadoClientes = new JMenuItem("Listado Clientes",new ImageIcon(getClass().getResource("/Imagenes/Listadoclientes.gif")));
			    mIListadoClientes.setMnemonic('C');
			    mIListadoClientes.setToolTipText("Permite crear mICategorias ");
			    mIListadoClientes.addActionListener(this);
			    consultas.add(mIListadoClientes);
			    consultas.addSeparator();
			    
 		 		//Se configura el menu de las consultas de ventas
		 		ventasConsulta = new JMenu("Ventas");
		 		ventasConsulta.setIcon(new ImageIcon(getClass().getResource("/Imagenes/VentasFacturacion.gif")));
			    ventasConsulta.setMnemonic('P');
			    ventasConsulta.setToolTipText("Permite consultar las compras ");
			    consultas.add(ventasConsulta);
			    consultas.addSeparator();
			    
			    
			    mIVentasPorCliente = new JMenuItem("Por Clientes",new ImageIcon(getClass().getResource("/Imagenes/Clientes.gif")));
			    mIVentasPorCliente.setMnemonic('C');
			    mIVentasPorCliente.setToolTipText("Permite crear mICategorias ");
			    mIVentasPorCliente.addActionListener(this);
			    ventasConsulta.add(mIVentasPorCliente);
			    ventasConsulta.addSeparator();
			    
	
			    mIVentasPorProducto = new JMenuItem("Por Productos",new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")));
			    mIVentasPorProducto.setMnemonic('C');
			    mIVentasPorProducto.setToolTipText("Permite crear mICategorias ");
			    mIVentasPorProducto.addActionListener(this);
			    ventasConsulta.add(mIVentasPorProducto);
			    ventasConsulta.addSeparator();
			    
		
			    mIVentasPorVendedor  = new JMenuItem("Por Vendedor",new ImageIcon(getClass().getResource("/Imagenes/Vendedor.gif")));
			    mIVentasPorVendedor.setMnemonic('V');
			    mIVentasPorVendedor.setToolTipText("Permite crear mICategorias ");
			    mIVentasPorVendedor.addActionListener(this);
			    ventasConsulta.add(mIVentasPorVendedor);
			    ventasConsulta.addSeparator();
			    
			    
			    mIVentaPorPeriodo = new JMenuItem("Por Periodos",new ImageIcon(getClass().getResource("/Imagenes/Calendario.gif")));
			    mIVentaPorPeriodo.setMnemonic('P');
			    mIVentaPorPeriodo.setToolTipText("Permite crear mICategorias ");
			    mIVentaPorPeriodo.addActionListener(this);
			    ventasConsulta.add(mIVentaPorPeriodo);
			    ventasConsulta.addSeparator();
			    
			    
			    mIVentasAnuladas = new JMenuItem("Anuladas",new ImageIcon(getClass().getResource("/Imagenes/VentasAnuladas.gif")));
			    mIVentasAnuladas.setMnemonic('A');
			    mIVentasAnuladas.setToolTipText("Permite crear mICategorias ");
			    mIVentasAnuladas.addActionListener(this);
			    ventasConsulta.add(mIVentasAnuladas);
			    
			    
		        
			    comprasConsulta = new JMenu("Compras");
			    comprasConsulta.setMnemonic('P');
			    comprasConsulta.setIcon(new ImageIcon(getClass().getResource("/Imagenes/ComprasRegistrar.gif")));
			    comprasConsulta.setToolTipText("Permite consultar las compras ");
			    consultas.add(comprasConsulta);
			    consultas.addSeparator();
			    
			    mIPorProveedor = new JMenuItem("Por Proveedor",new ImageIcon(getClass().getResource("/Imagenes/Proveedores.gif")));
			    mIPorProveedor.setMnemonic('C');
			    mIPorProveedor.setToolTipText("Permite crear mICategorias ");
			    mIPorProveedor.addActionListener(this);
			    comprasConsulta.add(mIPorProveedor);
			    comprasConsulta.addSeparator();
			   
			    mIPorProducto = new JMenuItem("Por Productos",new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")));
			    mIPorProducto.setMnemonic('C');
			    mIPorProducto.setToolTipText("Permite crear mICategorias ");
			    mIPorProducto.addActionListener(this);
			    comprasConsulta.add(mIPorProducto);
			    comprasConsulta.addSeparator();
			    
			    mICompraPorPeriodo = new JMenuItem("Por Periodos",new ImageIcon(getClass().getResource("/Imagenes/Calendario.gif")));
			    mICompraPorPeriodo.setMnemonic('C');
			    mICompraPorPeriodo.setToolTipText("Permite crear mICategorias ");
			    mICompraPorPeriodo.addActionListener(this);
			    comprasConsulta.add(mICompraPorPeriodo);
			    comprasConsulta.addSeparator();
			    
			    mIComprasAnuladas = new JMenuItem("Anuladas",new ImageIcon(getClass().getResource("/Imagenes/ComprasAnuladas.gif")));
			    mIComprasAnuladas.setMnemonic('A');
			    mIComprasAnuladas.setToolTipText("Permite crear mICategorias ");
			    mIComprasAnuladas.addActionListener(this);
			    comprasConsulta.add(mIComprasAnuladas);
		 		
	
		        cotizaciones  = new JMenu("Cotización");
		        cotizaciones.setIcon(new ImageIcon(getClass().getResource("/Imagenes/VentasCotizacion.gif")));
			    cotizaciones.setMnemonic('C');
			    cotizaciones.setToolTipText("Permite consultar las compras ");
			    consultas.add(cotizaciones);
			    consultas.addSeparator();
			    
			    mICotizacionPorCliente  = new JMenuItem("Por Clientes",new ImageIcon(getClass().getResource("/Imagenes/Clientes.gif")));
			    mICotizacionPorCliente.setMnemonic('C');
			    mICotizacionPorCliente.setToolTipText("Permite crear mICategorias ");
			    mICotizacionPorCliente.addActionListener(this);
			    cotizaciones.add(mICotizacionPorCliente);
			    cotizaciones.addSeparator();
	
			    mICotizacionPorProducto = new JMenuItem("Por Periodos",new ImageIcon(getClass().getResource("/Imagenes/Calendario.gif")));
			    mICotizacionPorProducto.setMnemonic('C');
			    mICotizacionPorProducto.setToolTipText("Permite crear mICategorias ");
			    mICotizacionPorProducto.addActionListener(this);
			    cotizaciones.add(mICotizacionPorProducto);
			 
			    
			    devoluciones  = new JMenu("Devoluciones");
			    devoluciones.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Devolucion.gif")));
			    devoluciones.setMnemonic('D');
			    devoluciones.setToolTipText("Permite consultar las compras ");
			    consultas.add(devoluciones);
			    consultas.addSeparator();
			    
			    mIDevolucionPorCompra  = new JMenuItem("Devoluciones Compra",new ImageIcon(getClass().getResource("/Imagenes/ComprasDevolucion.gif")));
			    mIDevolucionPorCompra.setMnemonic('C');
			    mIDevolucionPorCompra.setToolTipText("Permite crear mICategorias ");
			    mIDevolucionPorCompra.addActionListener(this);
			    devoluciones.add(mIDevolucionPorCompra);
		        devoluciones.addSeparator();
		        
			    mIDevolucionPorVenta = new JMenuItem("Devoluciones Venta",new ImageIcon(getClass().getResource("/Imagenes/VentasDevolucion.gif")));
			    mIDevolucionPorVenta.setMnemonic('C');
			    mIDevolucionPorVenta.setToolTipText("Permite crear mICategorias ");
			    mIDevolucionPorVenta.addActionListener(this);
			    devoluciones.add(mIDevolucionPorVenta);
			    
			    
		        utilidad = new JMenu("Utilidades");
		        utilidad.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Utilidades.gif")));
			    utilidad.setMnemonic('U');
			    utilidad.setToolTipText("Permite consultar las compras ");
			    consultas.add(utilidad);
		 		consultas.addSeparator();
		 		
		 		mIUtilidadPorProductos = new JMenuItem("Por Productos",new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")));
			    mIUtilidadPorProductos.setMnemonic('A');
			    mIUtilidadPorProductos.setToolTipText("Permite crear mICategorias ");
			    mIUtilidadPorProductos.addActionListener(this);
			    utilidad.add(mIUtilidadPorProductos);
			    utilidad.addSeparator();
			    
			    mIUtilidadPorPeriodos = new JMenuItem("Por Períodos",new ImageIcon(getClass().getResource("/Imagenes/UtilidadesPeriodo.gif")));
			    mIUtilidadPorPeriodos.setMnemonic('A');
			    mIUtilidadPorPeriodos.setToolTipText("Permite crear mICategorias ");
			    mIUtilidadPorPeriodos.addActionListener(this);
			    utilidad.add(mIUtilidadPorPeriodos);
			    
			    
			    estadisticas = new JMenu("Estadisticas");
			    estadisticas.setIcon(new ImageIcon(getClass().getResource("/Imagenes/Estadisticas.gif")));
			    estadisticas.setMnemonic('U');
			    estadisticas.setToolTipText("Permite consultar las compras ");
		        consultas.add(estadisticas);
		    
			    
		 
		 		mIRotacionProductos = new JMenuItem("Por Rotación de Productos",new ImageIcon(getClass().getResource("/Imagenes/InventarioProductosActivos.gif")));
			    mIRotacionProductos.setMnemonic('A');
			    mIRotacionProductos.setToolTipText("Permite crear mICategorias ");
			    mIRotacionProductos.addActionListener(this);
			    estadisticas.add(mIRotacionProductos);
			    estadisticas.addSeparator();
			    
			 /*   mICompararVentas = new JMenuItem("Comparar Ventas Por Períodos",new ImageIcon(getClass().getResource("/Imagenes/UtilidadesPeriodo.gif")));
			    mICompararVentas.setMnemonic('A');
			    mICompararVentas.setToolTipText("Permite crear mICategorias ");
			    mICompararVentas.addActionListener(this);
			    estadisticas.add(mICompararVentas);*/
			    
			    mIMarcasEstadisticas = new JMenuItem("Por Marcas",new ImageIcon(getClass().getResource("/Imagenes/InventarioMarcas.gif")));
			    mIMarcasEstadisticas.setMnemonic('A');
			    mIMarcasEstadisticas.setToolTipText("Permite crear mICategorias ");
			    mIMarcasEstadisticas.addActionListener(this);
			    estadisticas.add(mIMarcasEstadisticas);
			    estadisticas.addSeparator();
			    	
 	            mICategoriasEstadisticas = new JMenuItem("Por Categorias",new ImageIcon(getClass().getResource("/Imagenes/InventarioCategorias.gif")));
			    mICategoriasEstadisticas.setMnemonic('A');
			    mICategoriasEstadisticas.setToolTipText("Permite crear mICategorias ");
			    mICategoriasEstadisticas.addActionListener(this);
			    estadisticas.add(mICategoriasEstadisticas);
			    estadisticas.addSeparator();
			    
			    mIVentasClienteEstadistica = new JMenuItem("Por Clientes",new ImageIcon(getClass().getResource("/Imagenes/Clientes.gif")));
			    mIVentasClienteEstadistica.setMnemonic('A');
			    mIVentasClienteEstadistica.setToolTipText("Permite crear mICategorias ");
			    mIVentasClienteEstadistica.addActionListener(this);
			    estadisticas.add(mIVentasClienteEstadistica);
		        estadisticas.addSeparator();
			     	
 	            mIVentasClienteProveedor = new JMenuItem("Por Proveedor",new ImageIcon(getClass().getResource("/Imagenes/Proveedores.gif")));
			    mIVentasClienteProveedor.setMnemonic('A');
			    mIVentasClienteProveedor.setToolTipText("Permite crear mICategorias ");
			    mIVentasClienteProveedor.addActionListener(this);
			    estadisticas.add(mIVentasClienteProveedor);
			    
			    
                mIListadoPrecios  = new JMenuItem("Listado Precios",new ImageIcon(getClass().getResource("/Imagenes/ListadoPrecios.gif")));
			    mIListadoPrecios.setMnemonic('A');
			    mIListadoPrecios.setToolTipText("Permite crear mICategorias ");
			    mIListadoPrecios.addActionListener(this);
			    reportes.add(mIListadoPrecios);
		        reportes.addSeparator();
		        
		        mICatalogoProductos  = new JMenuItem("Catalogo Productos",new ImageIcon(getClass().getResource("/Imagenes/CatalogoProductos.gif")));
			    mICatalogoProductos.setMnemonic('A');
			    mICatalogoProductos.setToolTipText("Permite crear mICategorias ");
			    mICatalogoProductos.addActionListener(this);
			    reportes.add(mICatalogoProductos);
		 		 
		 		 //Se configuran los Item del Menu Ayuda
		 	    JMenuItem ayudaTeclado = new JMenuItem("Teclado",new ImageIcon(getClass().getResource("/Imagenes/Teclado.gif")));
			    ayudaTeclado.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,0));
			    ayudaTeclado.setToolTipText("Muestra la funcionalidad del teclado");
			    ayudaTeclado.addActionListener(this);
			    ayudas.add(ayudaTeclado);
			    
			    JMenuItem acercaDe = new JMenuItem("Acerca De",new ImageIcon(getClass().getResource("/Imagenes/AcercaDe.gif")));
			    acercaDe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,0));
			    acercaDe.setToolTipText("Muestra la funcionalidad del teclado");
			    acercaDe.addActionListener(this);
			    ayudas.add(acercaDe);
                parametros.add(ayudas);
			     
			    //Se configuran los Item del Menu Utilidades
			    configurarBackup = new JMenuItem("Configurar Copias De Seguridad (Backup)",new ImageIcon(getClass().getResource("/Imagenes/configBackup.gif")));
		 		configurarBackup.setMnemonic('C');
		 		configurarBackup.setToolTipText("Permite configurar la ubicación y el número de copias de seguridad");
		 		configurarBackup.addActionListener(this);
		 		
		 		cargarBackup = new JMenuItem("Restaurar copia de seguridad (Backup)",new ImageIcon(getClass().getResource("/Imagenes/cargarBackup.gif")));
		 		cargarBackup.setMnemonic('r');
		 		cargarBackup.setToolTipText("Permite restaurar una copia de seguridad");
		 		cargarBackup.addActionListener(this);
		 		
		 		mantenimientoBD = new JMenuItem("Mantenimiento Base De Datos",new ImageIcon(getClass().getResource("/Imagenes/MantenenimientoDB.gif")));
			    mantenimientoBD.setMnemonic('M');
			    mantenimientoBD.setToolTipText("Permite hacerle mantenimiento a la base de datos");
			    mantenimientoBD.setActionCommand("Mantenimiento");
			    mantenimientoBD.addActionListener(this);
			    
			    	
			    mICalendario = new JMenuItem("Calendario",new ImageIcon(getClass().getResource("/Imagenes/Calendario.gif")));
			    mICalendario.setMnemonic('A');
			    mICalendario.setToolTipText("Permite visualizar un calendario");
			    mICalendario.setActionCommand("Calendario");
			    mICalendario.addActionListener(this);
			    utilidades.add(mICalendario);
			    
			    
		        miAuditorias = new JMenuItem("Auditorias",new ImageIcon(getClass().getResource("/Imagenes/Auditorias.gif")));
			    miAuditorias.setMnemonic('R');
			    miAuditorias.setToolTipText("Permite conocer auditorias");
			    miAuditorias.addActionListener(this);
					
			
		 	
		 		// Se crea la barra de menu
		 		JMenuBar menuBar = new JMenuBar();
		 		menuBar.add(parametros);
		 		menuBar.add(datosGenerales);
		 		menuBar.add(ventas);
		 		menuBar.add(compras);
		 		menuBar.add(inventario);
		 	    menuBar.add(consultas);   	 		
                menuBar.add(reportes);
		 		menuBar.add(utilidades);
		 		menuBar.add(cambioUsuario);
		 		menuBar.add(ayudas);
		 		menuBar.add(salir);
		 		
		 		
		 		
		 		crearJFrameParaMenu.getJFrame().setJMenuBar(menuBar);
    	
    	
    }
    //**********************************************  Metodo  imprimirReportePrecios **********************************************************
    
    public void imprimirReporte(InputStream nombreReporte) {
    	
        ImprimirReporteJasper.runReport("Azkaban",conectarMySQL.getConexion(),nombreReporte);
		 		
    	
    }
    
    //**********************************************  Metodo  buscarProductosDebajoStockMinimo**********************************************************
   
    public void buscarProductosDebajoStockMinimo() {
   	    
   	     
      final JDialog dialogStock = new JDialog(crearJFrameParaMenu.getJFrame(),"Productos por Debajo Del Stock Minimo",true);
  	  dialogStock.setLayout(null);
  	  dialogStock.setSize(560, 330);
  	  dialogStock.setLocationRelativeTo(null);
  	  
  	  JButton jbAceptar = new JButton("Aceptar");
  	  jbAceptar.setMnemonic('A');
  	  jbAceptar.setBounds(210,260,80,25);
  	  jbAceptar.addActionListener(new ActionListener() {
  	  	
  	  	public void actionPerformed(ActionEvent a) {
  	  		
  	  		dialogStock.setVisible(false);
  	  		
  	  	}
  	  	
  	  });
  	  dialogStock.add(jbAceptar);
  	  
  	   try {
        
        	final String sentenciaSQL = "Select idProducto Codigo,descripcion Descripcion,Stock,StockMinimo "+
        	                            "From Productos "+
        	                            "Where Stock < StockMinimo " +
                                        "Order by 2 ";
                                        
            
                                        
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
           
           SortableTableModel dm = new SortableTableModel() {

		          //Se especifica el serial para la serializacion
		          static final long serialVersionUID = 19781212;
		
		          public Class getColumnClass(int col) {
		
		              return String.class;
		
		          }

			         public boolean isCellEditable(int row, int col) {
			               
			               return false;  
			
			         }

           };

           
           Vector nombresColumnas = new Vector();
           nombresColumnas.add("Código");
           nombresColumnas.add("Descripcion");
           nombresColumnas.add("Stock");
           nombresColumnas.add("StockMinimo");
           
           Vector filas = new Vector();
           
           while (resultado.next()) {
           	   
       	        Vector columnas = new Vector();
		    
		    	columnas.add(resultado.getString(1));
		    	columnas.add(resultado.getString(2));
		    	columnas.add(resultado.getString(3));
		    	columnas.add(resultado.getString(4));
			  	
			  	filas.add(columnas);	
		    				
			  }
	           
	          if (filas.size() > 0) {
	             
	             final JTable tabla = new JTable(dm);
	             tabla.addMouseListener(new MouseAdapter() {
     	
			     	public void mouseClicked(MouseEvent m) {
			     		
			     		if (m.getClickCount() == 2) {
			     		
			     		   new Producto(conectarMySQL,crearJFrameParaMenu.getJFrame(),tabla.getValueAt(tabla.getSelectedRow(),0).toString());
			     		   crearJFrameParaMenu.getJFrame().setVisible(false);
			     		   
			     		}   
			     	}
			     	
			     });
	            
			     JScrollPane scrollPane = new JScrollPane(tabla);
                 scrollPane.setBounds(10, 20, 530, 227);
                 dialogStock.add(scrollPane);
                       
			 	 dm.setDataVector(filas,nombresColumnas);
			 	 
			 	 tabla.getColumnModel().getColumn(0).setPreferredWidth(95);
			 	 tabla.getColumnModel().getColumn(0).setCellRenderer(crearJFrameParaMenu.getAlinearIzquierda());
				 tabla.getColumnModel().getColumn(1).setPreferredWidth(245);
				 tabla.getColumnModel().getColumn(1).setCellRenderer(crearJFrameParaMenu.getAlinearIzquierda());
				 tabla.getColumnModel().getColumn(2).setPreferredWidth(40);
				 tabla.getColumnModel().getColumn(2).setCellRenderer(crearJFrameParaMenu.getAlinearCentro());
				 tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
				 tabla.getColumnModel().getColumn(3).setCellRenderer(crearJFrameParaMenu.getAlinearCentro());
					
			 	 dialogStock.setVisible(true);
		 	    
			 }
			 
			 jbAceptar.grabFocus();
             
             
      
            
        } catch(Exception e) {
          
            crearJFrameParaMenu.Mensaje("Error al crear backup"+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
  	  	
  	  	
  	  
  	  
    
   	    
   	
   	
   }

 	//*********************************************************************************************************************
 	
 	private void configurarMenu(String nombreUsuario) {
 		     
 		     
 		
 		//Se configura el menu dependiendo del perfil
 			
	 	try {
	 			
	 			//busca los parametros
    	        String sentenciaSQL = "Select Descripcion,nombreMenu "+
 		                              "From  Privilegios P, Perfiles Pe,Usuarios U "+
 		                              "Where  P.IdPrivilegio = Pe.IdPrivilegio and U.ID = Pe.IDUsuario and "+
 		                              "U.ID = '"+nombreUsuario + "' " +
 		                              "Order by Pe.idprivilegio";
 		        
 		                        
 		          // Se realiza la consulta en la base de datos para buscar los perfiles
 		           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
 	
            	   if (resultado!=null) {
	                  
	                   while (resultado.next()) {
	 		 
	 		    	       boolean sw = true;
	 		    	       
	 		    	       int numeroItems = 0;
	 		  
         
	 		    	       for (int i = 0; i < arrayComponent.size() && (sw); i++) {
	 		                  
	 		                 String privilegio = resultado.getString(1).replace('ó','o');
	 		               
	 		                 if (privilegio.equalsIgnoreCase("Facturacion"))
	 		                 	  
	 		                 	   jlFacturacion.setEnabled(true);
	 		                 	   
	 		                 else
	 		                 	 
	 		                 	 if (privilegio.equalsIgnoreCase("Devolucion"))
	 		                 	 
	 		                 	    jlDevolucion.setEnabled(true);  
	 		                 	    
	 		                 	   else
	 		                 	 
	 		                 	    if (privilegio.equalsIgnoreCase("Anular"))
	 		                 	 
	 		                 	       jlAnular.setEnabled(true);   
	 		                 	    
	 		                 	      
	 		                 	   else
	 		                 	 
	 		                 	      if (privilegio.equalsIgnoreCase("Cotizacion"))
	 		                 	 
	 		                 	         jlCotizacion.setEnabled(true);
	 		                 	      
	 		                 	       else
	 		                 	 
		 		                 	    if (privilegio.equalsIgnoreCase("Registrar"))
		 		                 	 
		 		                 	       jlRegistrar.setEnabled(true);   
		 		                 	    
		 		                 	      
		 		                 	   else
		 		                 	 
		 		                 	       if (privilegio.equalsIgnoreCase("DevolucionCompras"))
		 		                 	 
		 		                 	         jlDevolucionCompra.setEnabled(true);
		 		                 	         
		 		                 	       else  
		 		                 	       
		 		                 	        if (privilegio.equalsIgnoreCase("AnularCompras"))
		 		                 	 
		 		                 	           jlAnularCompra.setEnabled(true);
		 		                 	           
		 		                 	         else
			 		                 	 
				 		                 	    if (privilegio.equalsIgnoreCase("Productos"))
				 		                 	 
				 		                 	       jlProductos.setEnabled(true);   
				 		                 	    
				 		                 	      
				 		                 	   else
				 		                 	 
				 		                 	       if (privilegio.equalsIgnoreCase("Marcas"))
				 		                 	 
				 		                 	         jlMarcas.setEnabled(true);
				 		                 	         
				 		                 	       else  
				 		                 	       
				 		                 	        if (privilegio.equalsIgnoreCase("Categorias"))
				 		                 	 
				 		                 	           jlCategorias.setEnabled(true);
				 		                 	           
				 		                 	          else  
				 		                 	       
					 		                 	        if (privilegio.equalsIgnoreCase("Listado De Productos"))
					 		                 	 
					 		                 	           jlListado.setEnabled(true);   
				 		                 	         
			 		                 	    
	 		                  
	 		                 if (((AbstractButton)arrayComponent.elementAt(i)).getActionCommand().equalsIgnoreCase(privilegio)) {
	 		                 	
	 		                 	   
	 		
	 		               	     JComponent jComponent = ((JComponent) arrayComponent.elementAt(i));
	 		               	   
	 		               	     if (resultado.getString(2).equals("Parametros")) {
	 		               	     
	 		               	     	  numeroItems = parametros.getItemCount() - 1;
	 		               	     	  parametros.insert((JMenuItem)jComponent,numeroItems);
	 		               	     	  
	 		               	     } else
	 		               	     
	 		               	          if (resultado.getString(2).equals("Ventas")) {
	 		               	     	      
	 		               	     	     JMenuItem menuItem = (JMenuItem)jComponent;
	 		               	     	     ventas.add(menuItem);
	 		               	     	   
	 		               	          } else
	 		               	           
		 		               	          if (resultado.getString(2).equals("Datos Generales")) {
		 		               	     	  
		 		               	     	    JMenuItem menuItem = (JMenuItem)jComponent;
		 		               	     	    datosGenerales.add(menuItem);

 		 		               	     	  } else
	 		 		               	     	  if (resultado.getString(2).equals("Compras")) {
			 		               	     	  
			 		               	     	     JMenuItem menuItem = (JMenuItem)jComponent;
			 		               	     	     compras.add(menuItem);
			 		               	     	      		
	 		 		               	     	  } else
	 		 		               	     	     
	 		 		               	     	     if (resultado.getString(2).equals("Inventario")) {
			 		               	     	   
			 		               	     	        JMenuItem menuItem = (JMenuItem)jComponent;
			 		               	     	        inventario.add(menuItem);
			 		               	     	      		
	 		 		               	     	      } else
			 		               	      
				 		               	            if (resultado.getString(2).equals("Utilidades")) {
				 		               	     	    	       
				 		               	     	      numeroItems = utilidades.getItemCount() - 1;
				 		               	     	      utilidades.insert((JMenuItem)jComponent,numeroItems);
				 		               	     	    }
			 		               	     	    
	 		               	        sw = false;
	 		               	     
	 		               	 }
	 		               }
		 			
		 		       }
		 
		 		       //Se agregan los separadores al menu parametros
		 		       for (int i = 1; i <parametros.getItemCount(); i = i+2) {
		 		       	
		 		       	   parametros.insertSeparator(i);
		 		       }
		 	
		 		       
		 		       //Se agregan los separadores al menu ventas
		 		       if (ventas.getItemCount() > 0) {
		 		       
			 		       //Se visualiza el menu
			 		       ventas.setVisible(true) ;
			 		        
			 		       for (int i = 1; i < ventas.getItemCount(); i = i+2) {
			 		       	
			 		       	   ventas.insertSeparator(i);
			 		       }
			 		  
			 		  } else    
		 		         
		 		          ventas.setVisible(false);
		 		     
		 		     
		 		       //Se agregan los separadores al menu ventas
		 		       if (datosGenerales.getItemCount() > 0) {
		 		       
			 		       //Se visualiza el menu
			 		       datosGenerales.setVisible(true) ;
			 		        
			 		       for (int i = 1; i < datosGenerales.getItemCount(); i = i+2) {
			 		       	
			 		       	   datosGenerales.insertSeparator(i);
			 		       }
			 		  
			 		  } else    
		 		         
		 		          datosGenerales.setVisible(false);
		 		     
		 		     
		 		     
		 		       //Se agregan los separadores al menu compras
		 		       if (compras.getItemCount() > 0) {
		 		       
			 		       //Se visualiza el menu
			 		       compras.setVisible(true) ;
			 		        
			 		       for (int i = 1; i < datosGenerales.getItemCount(); i = i+2) {
			 		       	
			 		       	   compras.insertSeparator(i);
			 		       }
			 		  
			 		  } else    
		 		         
		 		          compras.setVisible(false);
		 		     
		 		     
		 		      
		 		        //Se agregan los separadores al menu inventario
		 		       if (inventario.getItemCount() > 0) {
		 		       
			 		       //Se visualiza el menu
			 		       inventario.setVisible(true) ;
			 		        
			 		       for (int i = 1; i < inventario.getItemCount(); i = i+2) {
			 		       	
			 		       	   inventario.insertSeparator(i);
			 		       }
			 		  
			 		  } else    
		 		         
		 		          inventario.setVisible(false);
		 		     
		 		     
		 		     
		 		     
		 		       //Se agregan los separadores al menu utilidades
		 		       for (int i = 1; i < utilidades.getItemCount(); i = i+2) {
		 		       	
		 		       	   utilidades.insertSeparator(i);
		 		       }
		 		       
		 		    }  
		 		    
		 		      
		 		     //se visualizan los menus por defecto para cualquier tipo de usuario
	                parametros.setVisible(true);
	                utilidades.setVisible(true);  
	                cambioUsuario.setVisible(true);
	                ayudas.setVisible(true);
		 		    salir.setVisible(true);
		 		    
		 		     if (parametrosAzcaban.get("ALERTASTOCK").equals("true"))
		               
		                buscarProductosDebajoStockMinimo();
		 		    		 		     
        
		 		     
	 		} catch (Exception e) {
	 			
   			           System.out.println(""+e);
   		    }    
	 		
		
 	}
 	
 	//***********************************************************************************************************************
 	
 	protected void habilitarMenu(boolean habilitar) {

 		parametros.setEnabled(habilitar);
 		datosGenerales.setEnabled(habilitar);
 		reportes.setEnabled(habilitar);
 		ventas.setEnabled(habilitar);
 		compras.setEnabled(habilitar);
 		consultas.setEnabled(habilitar);
 		inventario.setEnabled(habilitar);
 		utilidades.setEnabled(habilitar);
 	    cambioUsuario.setEnabled(habilitar);
 	    ayudas.setEnabled(habilitar);
 	    salir.setEnabled(habilitar);
 	    
 	     //Se cambia el color de la letra para que aparezca como deshabilito
 	    // debido al efecto del subrayado
 	    if (colorLetraDesHabilitada == null)
 	    
 	        colorLetraDesHabilitada =  new Color(153,153,153);
 	    
 	    if (!habilitar) {
 	      
 	      cambioUsuario.setForeground(colorLetraDesHabilitada);
 	      salir.setForeground(colorLetraDesHabilitada);
 	      
 	    }else {
 	    	
 	      cambioUsuario.setForeground(Color.black);
 	      salir.setForeground(Color.black);
 	    }
 	   
 	  
 	}
 	

 	
   //******************************************************************************************************************
    
   protected int buscarRegistro(String usuario) {
  		
  		
  		int resultadoEntero = 0;
  		
  		// Se crea la sentencia para buscar el valor del Código dane
  		String sentenciaSQL = "Select Tema "+
  		                      "From Usuarios " +
  		                      "Where ID = '" + usuario +"'";
  		                      
  		                                   
 		// Se valida que se halla digitado un nit
 		 try {
 		 
 		   // Se realiza la consulta en la base de datos para buscar la placa
 		   ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
 		    
 		   if (resultado!=null) 
	 		 
	 		  if (resultado.next()) 
	 		  	 
	 		  	 resultadoEntero = resultado.getInt(1);
	 		  
	   } catch (Exception e) {
	     
	        e.printStackTrace();
	     	
	   }     
  	   
  	   return resultadoEntero; 
    }   
    

    //******************************************************************************************************************
    
    public void actionPerformed(ActionEvent evt) {
 		
 		// Se almacena la fuente que produjo el evento
 		Object evento = evt.getSource(); 

        if (evento == jbVentas) {

	         	 ventas.doClick();
	         	  
	     } else   
	            	            
	      if (evento == jbCompras) {
	
		         	 compras.doClick();
		         	  
		  } else
		  
	  	   if (evento == jbProductos) {
	
		         inventario.doClick();
		         	  
		   } else   
             
             if (evento == jbProductos) {
	
		         inventario.doClick();
		         	  
		     } else
                
                 if (evento == jbReportes) {
	
	 	           reportes.doClick();
		         	  
		         } else
		         
		           if (evt.getActionCommand().equals("Salir")) {
		
			         	      
			         	  if (!sesion.isVisible()) {
			         	  	
			         	  	if (salir.isEnabled()) {
			         	  	
				         	     crearCopiaSeguridad();
				         	     System.exit(0);
				         	}     
			         	  
			         	  }  
			     
		            } else 
			         
				         if (evt.getActionCommand().equals("Administrar Cuenta")) {
				         	 
				         	  if (!sesion.isVisible()) 
				         	  	
				         	  	if (salir.isEnabled())  {
				         	  		
				         	      mostrarAdministrarCuenta();
				         	  	
				         	  	}  
			 		     
				         } else 
		
					         if (evt.getActionCommand().equals("CambiarUsuario")) {
					         	  
					         	   if (!sesion.isVisible()) 
					         	   	 
					         	   	 	if (salir.isEnabled()) 
					         	      
					         	           cambiarUsuario();
				 		     
					         } else
			                  
			                    if (evento == cargarBackup) {
			                        
			                        //Se verifica que la maquina sea el servidor
			                      
			                        if (obtenerIPServidor().equals("127.0.0.1")) {
					              
					                        CargarBackup cB = new CargarBackup(conectarMySQL,this);
					                        this.add(cB);
					                        crearJFrameParaMenu.getJFrame().setContentPane(this);
					                        
					                        try {
					                   	         cB.setSelected(true);
					                        
					                        } catch (java.beans.PropertyVetoException e) {}
				 			                
				 			                habilitarMenu(false);
				 			      
				 			       } else 
		                     	       
		                     	       
		                     	        crearJFrameParaMenu.Mensaje("Esta opción debe ser ejecutada desde el servidor","Notificación",JOptionPane.WARNING_MESSAGE);
		                            
			                    } else
			                    
			                         if (evento == configurarBackup) {
							                
							                  //Se verifica que la maquina sea el servidor
							                 if (obtenerIPServidor().equals("127.0.0.1")) {
							                 
								                   ConfigurarBackup cB = new ConfigurarBackup(conectarMySQL,this);
								                   this.add(cB);
								                   crearJFrameParaMenu.getJFrame().setContentPane(this);
								                   
								                   try {
								                   
								                     	 cB.setSelected(true);
								                    
								                    } catch (java.beans.PropertyVetoException e) {}
					                                
					                                habilitarMenu(false);
					                                
				                             } else 
				                             	     crearJFrameParaMenu.Mensaje("Esta opción debe ser ejecutada desde el servidor","Notificación",JOptionPane.WARNING_MESSAGE);
				                             	
			                                
						               } else    
				  	
					                      	if (evento == mantenimientoBD) {
				                              
				                                  //Se verifica que la maquina sea el servidor
						                          if (obtenerIPServidor().equals("127.0.0.1")) {
						                          	
														MantenimientoBD mantBD = new MantenimientoBD(this);
														this.add(mantBD);
														crearJFrameParaMenu.getJFrame().setContentPane(this);
														
													try {
													   
													   mantBD.setSelected(true);
													
													} catch (java.beans.PropertyVetoException e) {}
													
													habilitarMenu(false);
											  	  
											  	  } else 
				                         	          
				                         	          crearJFrameParaMenu.Mensaje("Esta opción debe ser ejecutada desde el servidor","Notificación",JOptionPane.WARNING_MESSAGE);
				                       			
				                        	} else
					 			          	        
				                                if (evt.getActionCommand().equals("Teclado"))
				                                   
				                                   mostrarAyudaTeclado();
				                                   
				                                else
				                                 
				                                 if (evt.getActionCommand().equals("Acerca De"))
				                                   
				                                   mostrarAcercaDe();
				                                
				                                else
					 			              	  
					 			              	  if (evento == mICalendario) 
							 			          
							 			              mostrarCalendario();
							 			              
							 			          else
								 			     
						 			          	        if (evento == parametrosGenerales)
						 			          	       
						 			          	           mostrarParametrosGenerales();
						 			          	           
						 			          	        else
						 			          	          
						 			          	           if (evento == mIPrivilegiosUsuario)
						 			          	       
						 			          	               mostrarUsuario();
						 			          	               
						 			          	           else
				                                            
				                                              if (evento == mIListadoPrecios)  
				                                                  
				                                                  imprimirReporte(getClass().getClassLoader().getResourceAsStream("ListadoPrecios.jrxml")); 
				                                                 
				                                               else
				                                              
				                                                   if (evento == mICatalogoProductos)  
				                                                  
				                                                     imprimirReporte(getClass().getClassLoader().getResourceAsStream("CatalogoProductos.jrxml")); 
				                                                   
				                                                   else
											                            
									                                  if (evento ==   miCaja) 
									                           
									                                     new  Caja(this,conectarMySQL,Long.parseLong(jlValorCaja.getText().substring(12)),nombreUsuario); 
									                             

				                                                          
						 			          	           
						 			          	      
						 			          	        else {
		  	       				                           
		  	       				                           if (evento == festivos) 
										                           
										                           new Festivos(conectarMySQL,crearJFrameParaMenu.getJFrame());  
								                       
											                else
											                             
										                               if (evento == mIclientes) 
										                           
										                                  new Cliente(conectarMySQL,crearJFrameParaMenu.getJFrame());  
										                             
										                                else
										                               
										                                  if (evento == mIproveedores) 
										                        
										                                       new Proveedor(conectarMySQL,crearJFrameParaMenu.getJFrame());  
										                                      
										                                   else
												                               
												                               if (evento == mICategorias) 
												                           
												                                  new Categoria(conectarMySQL,crearJFrameParaMenu.getJFrame());  
												                             
												                                else
												                               
												                                  if (evento == mIMarcas) 
												                           
												                                      new Marca(conectarMySQL,crearJFrameParaMenu.getJFrame());  
												                                    
												                                   
												                                else
												                               
												                                  if (evento == mIproductos) 
												                           
												                                      new Producto(conectarMySQL,crearJFrameParaMenu.getJFrame(),parametrosAzcaban.get("IVA").toString(),parametrosAzcaban.get("VALORSTOCKMINIMO").toString());
												                                      
												                                  else
												                                         
													                                  if (evento == mIRegistrar) 
													                           
													                                      new Compra(conectarMySQL,crearJFrameParaMenu.getJFrame(),parametrosAzcaban,nombreUsuario,Long.parseLong(jlValorCaja.getText().substring(12)));
													                                      
													                                  else
													                                    
													                                    if (evento == mIVentas) 
													                           
													                                         new Venta(conectarMySQL,crearJFrameParaMenu.getJFrame(),nombreUsuario,(parametrosAzcaban.get("IMPRIMIRFACTURA").equals("false") ? false : true),Long.parseLong(jlValorCaja.getText().substring(12)));
													                                    
													                                   else
													                                    
													                                     if (evento == mIAnular) 
													                            
													                                          new AnularVenta(conectarMySQL,crearJFrameParaMenu.getJFrame(),nombreUsuario,Long.parseLong(jlValorCaja.getText().substring(12)));
													                                        
													                                   else
													                                    
													                                     if (evento == mIListadoClientes) 
													                            
													                                          new ListadoClientes(conectarMySQL,crearJFrameParaMenu.getJFrame());
													                                      
													                                       else
													                                    
													                                         if (evento == mIListadoProductos) 
													                            
													                                             new ListadoProductos(conectarMySQL,crearJFrameParaMenu.getJFrame());
													                                         
													                                         else
													                                           
													                                            if (evento == mIAnularCompras) 
													                            
													                                                 new AnularCompra(conectarMySQL,crearJFrameParaMenu.getJFrame(),nombreUsuario,Long.parseLong(jlValorCaja.getText().substring(12)));
													                                                 
													                                                 
													                                               else
													                                           
														                                            if (evento == mICotizacion) 
														                            
														                                                 new Cotizacion(conectarMySQL,crearJFrameParaMenu.getJFrame(),nombreUsuario);     
														                                                 
														                                             else
														                                             
														                                               if (evento == mIPorProveedor)
														                                               
														                                                     
														                                                  new CompraPorProveedor(conectarMySQL,crearJFrameParaMenu.getJFrame());  
														                                                 
														                                              
														                                                else      
														                                         
														                                                   if (evento == mIPorProducto)
														                                                      
														                                                         new CompraPorProducto(conectarMySQL,crearJFrameParaMenu.getJFrame()); 
														                                                         
														                                                   else      
														                                         
														                                                     if (evento == mIComprasAnuladas)
														                                                      
														                                                         new CompraAnulada(conectarMySQL,crearJFrameParaMenu.getJFrame());  
														                                                     
														                                                     else
														                                                     
														                                                     if (evento == mIVentasPorProducto)
														                                                      
														                                                         new VentaProducto(conectarMySQL,crearJFrameParaMenu.getJFrame());  
														                                                     
														                                                     else
														                                                     
															                                                     if (evento == mIVentasPorCliente)
															                                                      
															                                                         new VentaCliente(conectarMySQL,crearJFrameParaMenu.getJFrame());  
															                                                     
														                                                           else
														                                                     
																                                                     if (evento == mIVentasAnuladas)
																                                                      
																                                                         new VentaAnulada(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																                                                     
																                                                     else
																                                                       
																                                                        if (evento == mIDevolucionCompras)
																                                                      
																                                                           new DevolucionCompra(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																                                                           
																                                                        else
																                                                           
																                                                           if (evento == mIUtilidadPorProductos)
																                                                      
																                                                              new UtilidadProducto(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																                                                           
																                                                           else
																                                                           
																	                                                           if (evento == mIUtilidadPorPeriodos)
																	                                                      
																	                                                              new UtilidadPeriodo(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																	                                                        
																	                                                            else
																	                                                              
																	                                                                if (evento == mICotizacionPorCliente)
																	                                                      
																	                                                                      new CotizacionCliente(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																	                                                                      
																	                                                                 else     
																	                                                                   
																	                                                                     if (evento == mICotizacionPorProducto)
																	                                                        
																	                                                                        new CotizacionPorPeriodos(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																	                                                                        
																	                                                                      
																	                                                                      
																	                                                                    else     
																	                                                                   
																	                                                                       if (evento == mIDevolucionPorCompra)
																	                                                        
																	                                                                          new DevolucionPorCompra(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																	                                                                
																	                                                                        else     
																	                                                                   
																		                                                                       if (evento == mIDevolucionVentas)
																		                                                        
																		                                                                          new DevolucionVenta(conectarMySQL,crearJFrameParaMenu.getJFrame()); 
																		                                                                       
																		                                                                       else     
																			                                                                   
																			                                                                       if (evento == mIDevolucionPorVenta)
																			                                                        
																			                                                                           new DevolucionPorVenta(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																			                                                                           
																			                                                                        else
																			                                                                          
																			                                                                          if (evento == mIRotacionProductos)
																			                                                        
																			                                                                            new RotacionProductos(conectarMySQL,crearJFrameParaMenu.getJFrame());  
																			                                                                            
																			                                                                          else
																			                                                                          
																			                                                                            if (evento == mIVendedores)
																			                                                       
																			                                                                              new Vendedor(conectarMySQL,crearJFrameParaMenu.getJFrame());
																			                                                                              
																			                                                                             else
																			                                                                              
																			                                                                               if (evento == mIMarcasEstadisticas)   
																			                                                                            
																			                                                                                     new RotacionMarcas(conectarMySQL,crearJFrameParaMenu.getJFrame());
																			                                                                          
																			                                                                               else
																			                                                                              
																			                                                                                 if (evento == mICategoriasEstadisticas)   
																			                                                                            
																			                                                                                      new RotacionCategorias(conectarMySQL,crearJFrameParaMenu.getJFrame());
																			                                                                                 
																			                                                                                 else
																			                                                                                   
																			                                                                                   if (evento == mIVentasClienteEstadistica)   
																			                                                                            
																			                                                                                      new RotacionClientes(conectarMySQL,crearJFrameParaMenu.getJFrame());
																			                                                                                    
																			                                                                                   else
																			                                                                                   
																			                                                                                     if (evento == mIVentasClienteProveedor)   
																			                                                                            
																			                                                                                         new RotacionProveedor(conectarMySQL,crearJFrameParaMenu.getJFrame());
																			                                                                                    
																			                                                                                     else
																			                                                                                   
																			                                                                                       if (evento == mIVentasPorVendedor)   
																			                                                                            
																			                                                                                           new VentaVendedor(conectarMySQL,crearJFrameParaMenu.getJFrame());
																			                                                                                           
																			                                                                                        else
																			                                                                                   
																				                                                                                       if (evento == mICompraPorPeriodo)   
																				                                                                            
																				                                                                                           new CompraPorPeriodo(conectarMySQL,crearJFrameParaMenu.getJFrame());
																				                                                                                       
																				                                                                                       else        
																		                                                                                                 
																		                                                                                                  if (evento == mIVentaPorPeriodo)   
																				                                                                            
																				                                                                                                new VentaPorPeriodo(conectarMySQL,crearJFrameParaMenu.getJFrame());
																				                                                                                            
																	                                                               				                            else
																																						                            
																																				                              if (evento ==   miAuditorias) 
																																				                           
																																				                                 new  Auditorias(conectarMySQL,crearJFrameParaMenu.getJFrame()); 
																																				                                 
																																				                              
																																				                              else 
																																				                                
																																				                               if (evento ==   miConsultaCaja)
																																				                           
																																				                                   new  ConsultaCaja(conectarMySQL,crearJFrameParaMenu.getJFrame(),Long.parseLong(jlValorCaja.getText().substring(12))); 
																																				                                 
																																				                          
																																				                                     
																																				                                 
																																				                              
																																				                               
											                            
											                          
											                           
									                  
		 			          	                                             
												                  crearJFrameParaMenu.getJFrame().setVisible(false);    //Se oculta el menu principal              
								                      
									 			         } 
									 			             
							 			         
							 			         
							 			         
												  	
							 			              
							                 
								                                       

 	}
 	
 		
    //********************************* Metodo Principal  ***********************************************************
    
    public static void main(String arg[]) {
 	
 		new MenuPrincipal();
 	}
 	
 	
 	
 	
 }