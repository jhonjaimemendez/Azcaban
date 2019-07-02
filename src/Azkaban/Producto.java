package com.JASoft.azkaban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.KeyboardFocusManager;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;



import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JFileChooser;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;

import java.sql.PreparedStatement; 

import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import java.sql.Blob;
import java.sql.ResultSet;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;
import com.JASoft.componentes.ImageFilterImagen;
import com.JASoft.componentes.ImageFileView;
import com.JASoft.componentes.ImagePreview;
import com.JASoft.componentes.GeneraCodigoBarra;

final public class Producto extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Array para campos obligatorios
    private JTextField[] camposObligatorios;

    //** Se declaran los JPanel
    private  JPanel panelIdentificacion;
    private  JPanel panelApellidosNombres;
    private  JPanel panelDatosContacto;
    private  JPanel panelReferencias;

    //** Se declaran los JTextField
    private  JTextField jtDescripcion;
    private  JTextField jtStock;
    private  JTextField jtPrecioVenta;
    private  JTextField jtPorcentajeVentaMinimo;
    private  JTextField jtPorcentajeIva;
    private  JTextField jtCodigo;
    private  JTextField jtPorcentajeVenta;
    private  JTextField jtPrecioMinimo;
    private  JTextField jtCategoria;
    private  JTextField jtMarca;
    private  JTextField jtCodigoCategoria;
    private  JTextField jtCodigoMarca;
    private  JTextField jtStockMinimo;

    
    //** Se declaran los JTextArea
    private JTextArea jaReferenciasApuntes;
    
    //** Se declaran los JLabel
    private JLabel jlFoto;

    //** Se declaran los JButton
    private  JButton jbCambiarImagen;
   
    //** Se declaran los JCheckBox
    private  JCheckBox jhIvaIncluido;

    //** Clase para mostrar una lista de valores
    private ListaValor listaValorCategoria;
    private ListaValor listaValorMarcas;
    
    //** Se declara un JFileChooser
    private JFileChooser fileChooser;
    
    //** Se declara un objeto de tipo PreparedStatement para insertar la foto
    private PreparedStatement preparedStatementInsert;
    private PreparedStatement preparedStatementUpdate;
    
    //** Booleanos
    private boolean insertarFoto = false;
    private boolean mostrarListaAutomatica = true;
    private boolean mostrarCategoria = false; // Utilizada como comodin cuando se devuelve un valor de la forma categoria
    
    //** File
    private File file;

    //**String
    private String rutaImagenAlmacenada;
    private String valorDefectoStock;
    private String precioLista;
    private String precioMinimo;
    private String precioPorcentajeMinimo;
    private String precioPorcentajeLista;
    
    
    //**Long
    private float precioCompra = 0;
    
    //Color
    private Color color = new Color(238,238,238);
    
    
    

   //** Constructor General 
    public Producto(ConectarMySQL p_conectarMySQL,JFrame p_frame,String IVA,String valorDefectoStock) {

      super( "Producto" ,"Toolbar",p_frame,"dummy");

      conectarMySQL = p_conectarMySQL;

      this.valorDefectoStock  = valorDefectoStock;
      
      iniciarGUI(IVA);
     
    }
    
     public Producto(ConectarMySQL p_conectarMySQL,JFrame p_frame,String idProducto) {

      super( "Producto" ,"Toolbar",p_frame,"dummy");

      conectarMySQL = p_conectarMySQL;
      
      iniciarGUI("");
      
      jtCodigo.setText(idProducto);
      
      buscarRegistro();
      
      String categoria = traerInformacion("Categorias","idCategoria = '"+ jtCodigoCategoria.getText() +"'"); 
      jtCategoria.setText(categoria);
      
      String marca = traerInformacion("Marcas", "idMarca = '"+ jtCodigoMarca.getText() +"'"); //Se busca la marca
      jtMarca.setText(marca);
		          	 
    
    
    }
    
    
    public void iniciarGUI(String IVA) {
      
      getJFrame().setName("Producto"); //Comodin utilizado cuando se llama desde aqui la forma Cliente

      // Se instancias colores comunes por eficiencia
      final Color color0 = new Color(132, 132, 132);
      //** Se agregan los JPanel

      panelIdentificacion = getJPanel("Identificación",60, 75, 420, 150,14);

      panelApellidosNombres = getJPanel("Imagen",505, 75, 225, 325,14);

      panelDatosContacto = getJPanel("Existencias y Precios",60, 240, 420, 160,14);

      panelReferencias = getJPanel("Referencias Afines / Anotaciones",60, 420, 670, 90,14);

      //** Se declaran los JLabel

      JLabel jlCodigo = getJLabel("Código:",45, 28, 60, 15);
      jlCodigo.setHorizontalAlignment(SwingConstants.RIGHT);
      panelIdentificacion.add(jlCodigo);

      JLabel jlDescripcion = getJLabel("Descripción:",30, 58, 75, 15);
      jlDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
      panelIdentificacion.add(jlDescripcion);

      JLabel jlStock = getJLabel("Stock's:",40, 23, 85, 15);
      jlStock.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlStock);

      JLabel jlPrecioMinimo = getJLabel("Precio Minimo:",20, 90, 105, 15);
      jlPrecioMinimo.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlPrecioMinimo);

      JLabel jlValorIva = getJLabel("Iva:",90, 133, 35, 15);
      jlValorIva.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlValorIva);

      JLabel jlPorciento1 = getJLabel("% de Ganancia",275, 53, 95, 20);
      jlPorciento1.setHorizontalAlignment(SwingConstants.LEFT);
      jlPorciento1.setForeground(color0);
      panelDatosContacto.add(jlPorciento1);

      JLabel jlPorciento3 = getJLabel("%",170, 130, 15, 20);
      jlPorciento3.setHorizontalAlignment(SwingConstants.LEFT);
      jlPorciento3.setForeground(color0);
      panelDatosContacto.add(jlPorciento3);

      JLabel jlCategoria = getJLabel("Categoría:",30, 88, 75, 15);
      jlCategoria.setHorizontalAlignment(SwingConstants.RIGHT);
      panelIdentificacion.add(jlCategoria);

      JLabel jlMarca = getJLabel("Marca:",30, 118, 75, 15);
      jlMarca.setHorizontalAlignment(SwingConstants.RIGHT);
      panelIdentificacion.add(jlMarca);

      JLabel jlPrecioLista = getJLabel("Precio Lista:",20, 56, 105, 15);
      jlPrecioLista.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlPrecioLista);

      JLabel jlPorciento2 = getJLabel("% de Ganancia",275, 87, 95, 20);
      jlPorciento2.setHorizontalAlignment(SwingConstants.LEFT);
      jlPorciento2.setForeground(color0);
      panelDatosContacto.add(jlPorciento2);
      
      
      JLabel jlStockMinimo = getJLabel("Mínimo:",270, 23, 60, 15);
      jlStockMinimo.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlStockMinimo);
      
      JLabel jlStockActual = getJLabel("Actual:",140, 23, 50, 15);
      jlStockActual.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlStockActual);
      
      JLabel jlUnidades = getJLabel("Und.",235, 20, 30, 20);
      jlUnidades.setHorizontalAlignment(SwingConstants.RIGHT);
      jlUnidades.setForeground(color0);
      panelDatosContacto.add(jlUnidades);
      
      JLabel jlUnidades1 = getJLabel("Und.",375, 20, 30, 20);
      jlUnidades1.setHorizontalAlignment(SwingConstants.RIGHT);
      jlUnidades1.setForeground(color0);
      panelDatosContacto.add(jlUnidades1);
      
      jlFoto =  getJLabel("",27, 55, 170, 170);
      jlFoto.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
      panelApellidosNombres.add(jlFoto);
      
  

      //** Se instancian los JSeparator

      JSeparator jSeparator1 = new JSeparator();
      jSeparator1.setBounds(30, 120, 360, 2);
      panelDatosContacto.add(jSeparator1);

      //** Se agregan los JTextField

      jtDescripcion = getJTextField("",125, 55, 270, 20,"Digite la Descripción del Producto","100");
      jtDescripcion.setHorizontalAlignment(JTextField.LEFT);
      jtDescripcion.addFocusListener(this);
      jtDescripcion.addKeyListener(getConvertirMayuscula());
      panelIdentificacion.add(jtDescripcion);

      jtStock = getJTextField("Cantidad del Producto en Stock",195, 20, 35, 20,"",false);
      jtStock.setHorizontalAlignment(JTextField.RIGHT);
      jtStock.addFocusListener(this);
      jtStock.addKeyListener(getValidarEntradaNumeroJTextField());
      panelDatosContacto.add(jtStock);

      jtPrecioVenta = getJTextField("Precio de Venta del Producto",135, 53, 95, 20,"8",false);
      jtPrecioVenta.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioVenta.addFocusListener(this);
      jtPrecioVenta.addKeyListener(getValidarEntradaNumeroJTextField());
      panelDatosContacto.add(jtPrecioVenta);

      jtPorcentajeVentaMinimo = getJTextField("Porcentaje de Ganancia",235, 87, 35, 20,"2",false);
      jtPorcentajeVentaMinimo.setHorizontalAlignment(JTextField.RIGHT);
      jtPorcentajeVentaMinimo.addFocusListener(this);
      panelDatosContacto.add(jtPorcentajeVentaMinimo);

      jtPorcentajeIva = getJTextField("Valor del Iva aplicado al Producto",135, 130, 30, 20,"",false);
      jtPorcentajeIva.setText(IVA);
      jtPorcentajeIva.setHorizontalAlignment(JTextField.RIGHT);
      jtPorcentajeIva.addFocusListener(this);
      panelDatosContacto.add(jtPorcentajeIva);

      jtCodigo = getJTextField("",125, 25, 105, 20,"Digíte el Código del Producto o Escanéelo con Lector de Barras","20");
      jtCodigo.setHorizontalAlignment(JTextField.LEFT);
      jtCodigo.addFocusListener(this);
      jtCodigo.addKeyListener(getValidarEntradaNumeroJTextField());
      panelIdentificacion.add(jtCodigo);

      jtPorcentajeVenta = getJTextField("Porcentaje de Ganancia",235, 53, 35, 20,"2",false);
      jtPorcentajeVenta.setHorizontalAlignment(JTextField.RIGHT);
      jtPorcentajeVenta.addFocusListener(this);
      jtPorcentajeVenta.addKeyListener(getValidarEntradaNumeroJTextField());
      panelDatosContacto.add(jtPorcentajeVenta);

      jtPrecioMinimo = getJTextField("Precio Mínimo de Venta del Producto",135, 87, 95, 20,"8",false);
      jtPrecioMinimo.setHorizontalAlignment(JTextField.RIGHT);
      jtPrecioMinimo.addKeyListener(getValidarEntradaNumeroJTextField());
      jtPrecioMinimo.addFocusListener(this);
      panelDatosContacto.add(jtPrecioMinimo);
      
      jtCodigoMarca = getJTextField("",125, 115, 50, 20,"Digíte el Código de la Marca","4");
      jtCodigoMarca.addFocusListener(this);
      jtCodigoMarca.addKeyListener(getValidarEntradaNumeroJTextField());
      panelIdentificacion.add(jtCodigoMarca);
      
      jtCodigoCategoria = getJTextField("",125, 85, 50, 20,"Digíte el Código de la Marca","4");
      jtCodigoCategoria.addFocusListener(this);
      jtCodigoCategoria.addKeyListener(getValidarEntradaNumeroJTextField());
      panelIdentificacion.add(jtCodigoCategoria);

     
      jtCategoria = getJTextField("",180, 85, 215, 20,"Digíte la Categoría o Presione F9","20");
      jtCategoria.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtCategoria.setHorizontalAlignment(JTextField.LEFT);
      jtCategoria.selectAll();

      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValorCategoria = getListaValores(getSentencia1(),getComponentesRetorno(),this,jtCategoria,conectarMySQL,0,2,null);

      jtCategoria.addKeyListener(listaValorCategoria);
      jtCategoria.addActionListener(this);
      jtCategoria.addFocusListener(this);
       panelIdentificacion.add(jtCategoria);

      jtMarca = getJTextField("",180, 115, 215, 20,"Digíte la Marca o Presione F9","20");
      jtMarca.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      jtMarca.setHorizontalAlignment(JTextField.LEFT);
      jtMarca.selectAll();

      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValorMarcas = getListaValores(getSentencia(),getComponentesRetorno1(),this,jtMarca,conectarMySQL,1,2,null);

      jtMarca.addKeyListener(listaValorMarcas);
      jtMarca.addActionListener(this);
      jtMarca.addFocusListener(this);
      panelIdentificacion.add(jtMarca);

      jtStockMinimo = getJTextField("",335, 20, 35, 20,"Cantidad del Producto en Stock","4");
      jtStockMinimo.setHorizontalAlignment(JTextField.RIGHT);
      jtStockMinimo.addKeyListener(getValidarEntradaNumeroJTextField());
      jtStockMinimo.selectAll();
      jtStockMinimo.addFocusListener(this);
      panelDatosContacto.add(jtStockMinimo);
       
      //Se agregan los JTextArea
      jaReferenciasApuntes = new  JTextArea();
      jaReferenciasApuntes.addKeyListener(new KeyAdapter() {
      	
      	public void  keyTyped (KeyEvent k) {
   	    
   	       k.setKeyChar(String.valueOf(k.getKeyChar()).toUpperCase().charAt(0));
   	       
   	    }   
      	
      });
      jaReferenciasApuntes.addFocusListener(this);
      jaReferenciasApuntes.setLineWrap(true);
      
      JScrollPane scroll = new JScrollPane(jaReferenciasApuntes);
      scroll.setBounds(28, 30, 615, 40);
      panelReferencias.add(scroll);
      
       
      //** Se agregan los JButton

      jbCambiarImagen = getJButton("Cambiar Imagen",27, 260, 170, 25,"","");
      jbCambiarImagen.setFocusable(false);
      jbCambiarImagen.addActionListener(this);
      panelApellidosNombres.add(jbCambiarImagen);

      //** Se agregan los JCheckBox

      jhIvaIncluido = getJCheckBox("Incluido en los Precios",190, 130, 155, 20,"");
      jhIvaIncluido.setFocusable(false);
      jhIvaIncluido.setSelected(true);
      panelDatosContacto.add(jhIvaIncluido);
     
     

      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/InventarioProductos.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 

      jtCodigo.grabFocus();    //Se coloca el foco en el primer componente Focusable

      // Se adicionan eventos a los botones de la Toolbar
      Blimpiar.addActionListener(this);
      Bguardar.addActionListener(this);
      Beliminar.addActionListener(this);
      Bbuscar.addActionListener(this);
      Bsalir.addActionListener(this);
      Bimprimir.setEnabled(false);
      
      Blimpiar.setName("limpiar");
      
	  //Se crea el array de campos obligatorios 
	  crearArrayCamposObligatorios();
	  
      fileChooser = new JFileChooser();
      fileChooser.setDialogTitle("Azcaban");
      fileChooser.setApproveButtonToolTipText("Escoja la imagen que desea abrir");
      fileChooser.addChoosableFileFilter(new ImageFilterImagen());
      fileChooser.setAcceptAllFileFilterUsed(false);
      fileChooser.setFileView(new ImageFileView());
      fileChooser.setAccessory(new ImagePreview(fileChooser));
     
      //Se agrega un evento de ventana  al frame para que muestre a su padre
  	   getJFrame().addWindowListener(new WindowAdapter() {
  	      
  	      public void windowClosing(WindowEvent w) {
  	
  	      	   ocultarJFrame();
  	      	   
  	      	   if (file != null)
  	      	   
  	      	      file.delete(); //Se borra el archivo imagen creado 
  	      	   
  	      	   
	           
         }
  	   });
  	   
  	   getJFrame().addComponentListener(new ComponentAdapter() {
  	   
  	    	public void componentShown(ComponentEvent e) {
  	
  	           if (!jtCodigoCategoria.getText().isEmpty() && mostrarCategoria) {
  	               
  	               
	          	   String categoria = traerInformacion("Categorias","idCategoria = '"+ jtCodigoCategoria.getText() +"'"); //Se busca la caregoria
	          	   
	          	   if (categoria != null) {
	          	  	   
	          	  	   mostrarListaAutomatica = false;
	          	  	   jtCategoria.setText(categoria);
	          	  	   jtCodigoMarca.grabFocus();
	          	  	   
	          	  	   
	          	  } else {
	          	  	  
          	  	        mostrarListaAutomatica = true;
          	  	        jtCategoria.setText("");
          	  	        jtCodigoCategoria.setText("");
          	  
  	              }    
  	           
  	           } else 
  	          
  	               if (!jtCodigoMarca.getText().isEmpty()) {
  	                      
  	                  String marca = traerInformacion("Marcas", "idMarca = '"+ jtCodigoMarca.getText() +"'"); //Se busca la marca
		          	  
		          	  if (marca != null) {
		          	  	  
		          	  	  mostrarListaAutomatica = false;
		          	  	  jtMarca.setText(marca);
		          	  	  jtStockMinimo.grabFocus();
		          	  	 
		          	  	 
		          	  } else {
	          	  	  
	          	  	     mostrarListaAutomatica = false;
	       			      
	          	  	     //Se verifica si se desean hacer tramites diferentes a matriculas o radicado
					      int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Código marca  " +   jtCodigoMarca.getText() + " no registrado \nDesea registrar esta marca?","Notificación ",JOptionPane.YES_NO_OPTION);
						    
						   if ( opcion == 0 ) {
						       
						       new Marca(conectarMySQL,getJFrame(),jtCodigoMarca.getText());
			    	           getJFrame().setVisible(false);
				
		          	  	   } else {
		          	  	        
		          	  	        mostrarListaAutomatica = true;
		          	  	        jtMarca.setText("");
		          	  	        jtCodigoMarca.setText("");
		          	  	 	
		          	  	   } 
			          }  
  	
  	
  	               }
  	               
  	               
  	               mostrarCategoria = false;                		
  	    	}	
  	  });
	
  
    }

     //******************************** Metodo crearArrayCamposObligatorios *******************

     public void crearArrayCamposObligatorios() { 

           //Se instancia un array de JTextField 
           camposObligatorios = new JTextField[11];

           camposObligatorios[0] = jtDescripcion;
           camposObligatorios[1] = jtStock;
           camposObligatorios[2] = jtPrecioVenta;
           camposObligatorios[3] = jtCodigo;
           camposObligatorios[4] = jtPrecioMinimo;
           camposObligatorios[5] = jtCodigoCategoria;
           camposObligatorios[6] = jtCodigoMarca;
           camposObligatorios[7] = jtPrecioVenta;
           camposObligatorios[8] = jtPorcentajeVenta;
           camposObligatorios[9] = jtPrecioMinimo;
           camposObligatorios[10] = jtPorcentajeVentaMinimo;
           

     }

	//******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		String sentencia = "Select idMarca Codigo,Marca "+
		                   "From Marcas "+
		                   "Where Marca like '";

		return sentencia;

	}
	
	//******************************** Metodo getSentencia1()  ***************************************

	final private String getSentencia1() {

		String sentencia = "Select IdCategoria Codigo,Categoria "+
		                   "From Categorias "+
		                   "Where Categoria like '";

		return sentencia;

	}

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[2][5];
		
		objetosRetorno[0][0] = jtCategoria;
        objetosRetorno[0][1] = jtCodigoMarca;
       	objetosRetorno[0][2] = "1";
       	objetosRetorno[0][3] = jtCodigoCategoria;
       	objetosRetorno[0][4] = "0";

		return objetosRetorno;

	}
	
	
	//******************************** Metodo getComponentesRetorno1()  ***************************************

	final private Object[][] getComponentesRetorno1() {

		Object[][] objetosRetorno = new Object[1][5];
		
		objetosRetorno[0][0] = jtMarca;
        objetosRetorno[0][1] = jtStockMinimo;
       	objetosRetorno[0][2] = "1";
       	objetosRetorno[0][3] = jtCodigoMarca;
       	objetosRetorno[0][4] = "0";

		return objetosRetorno;

	}

	
    //*********************** Metodo limpiar ************************

    private void limpiar(boolean dummy) { 

      jtDescripcion.setText("");
      jtStock.setText("");
      jtPrecioVenta.setText("");
      jtPorcentajeVentaMinimo.setText("");
      jtPorcentajeVenta.setText("");
      jtPrecioMinimo.setText("");
      jtCategoria.setText("");
      jtMarca.setText("");
      jtCodigoCategoria.setText("");
      jtCodigoMarca.setText("");
      jaReferenciasApuntes.setText("");
      jtStockMinimo.setText(valorDefectoStock);
      jtStockMinimo.selectAll();
      jlFoto.setIcon(null);
      insertarFoto = false;
      
      jtPorcentajeVenta.setEditable(false);
      jtPorcentajeVenta.setFocusable(false);
      jtPorcentajeVenta.setBackground(color);
      
      jtPorcentajeVentaMinimo.setEditable(false);
      jtPorcentajeVentaMinimo.setFocusable(false);
      jtPorcentajeVentaMinimo.setBackground(color);
      
      jtPrecioMinimo.setEditable(false);
      jtPrecioMinimo.setFocusable(false);
      jtPrecioMinimo.setBackground(color);
      
      jtPrecioVenta.setEditable(false);
      jtPrecioVenta.setFocusable(false);
      jtPrecioVenta.setBackground(color);
      
      precioCompra = 0;
                 
      precioLista = null;
      precioMinimo = null;
      precioPorcentajeMinimo = null;
      precioPorcentajeLista = null;

      if (dummy) {
        
        jtCodigo.setText("");
        jtCodigo.grabFocus();    //Se coloca el foco en el primer componente Focusable
      
      } else {
      	 
      	 jtDescripcion.grabFocus();
      	
      }

    }

   //*********************** Metodo buscarRegistro ************************

    private boolean buscarRegistro() { 

        boolean resultadoBoolean = false;

        final String sentenciaSQL = "Select P.*,C.Categoria,M.Marca,(Select IfNull(H.PorcentajeGanaciaLista,0) From HistoricoPorcentajesGanancia H  where H.idProducto = P.idProducto and H.FechaHasta Is Null),(Select IfNull(H.PorcentajeGanaciaMinimo,0) From HistoricoPorcentajesGanancia H  where H.idProducto = P.idProducto and H.FechaHasta Is Null) "+
                                    "From   Productos P,Categorias C,Marcas M "+
                                    "Where P.IdProducto = '" + jtCodigo.getText() + "' and P.idMarca = M.idMarca and " +
                                    " P.iDCategoria = C.idCategoria ";


        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 

             if (resultado.next()) { 

				 
				 
				 
				 jtDescripcion.setText(resultado.getString(2));
				 jtPrecioMinimo.setText(resultado.getString(3));
				 jtPrecioVenta.setText(resultado.getString(4));
				 jhIvaIncluido.setSelected(resultado.getBoolean(5));
				 jtStockMinimo.setText(resultado.getString(6));
				 jtStock.setText(resultado.getString(7));
				 jtCodigoMarca.setText(resultado.getString(8));
				 jtCodigoCategoria.setText(resultado.getString(9));
				 jaReferenciasApuntes.setText(resultado.getString(10));
				
				
				  //Se crea un objeto de tipo Blob y se utiliza el Resultset
                  Blob img  = resultado.getBlob(11);
                  
                  if (img != null) {
	                  
	                  byte[] imgData = img.getBytes(1,(int)img.length());
	                
	                  // Se crea un objeto de tipo file para crear el archivo de imagen
	                  file = new File("Imagen.jpg");
	                  FileOutputStream canalSalida = new FileOutputStream(file);
	                
	                  canalSalida.write(imgData);
	                  canalSalida.close();
	               
	                  rutaImagenAlmacenada = file.getPath();
	                  
	                 // Se cambia la imagen
	                  jlFoto.setIcon(getImagenEscalada(170,170,rutaImagenAlmacenada));
	                
	                  // Se cierra el canal
	                  canalSalida.close();
	             }     
                  
                  
                  jtCategoria.setText(resultado.getString(13));
                  jtMarca.setText(resultado.getString(14));
                  jtPorcentajeVenta.setText(resultado.getString(15) != null ? resultado.getString(15) : "0");
                  jtPorcentajeVentaMinimo.setText(resultado.getString(16) != null ? resultado.getString(16) : "0");
                
                
                  if (!jtPrecioVenta.getText().equals("0")) {
                  
	                  jtPorcentajeVenta.setEditable(true);
	                  jtPorcentajeVenta.setFocusable(true);
	                  
	                  jtPorcentajeVentaMinimo.setEditable(true);
	                  jtPorcentajeVentaMinimo.setFocusable(true);
	                 
	                  jtPrecioMinimo.setEditable(true);
	                  jtPrecioMinimo.setFocusable(true);
	                  
	                  jtPrecioVenta.setEditable(true);
	                  jtPrecioVenta.setFocusable(true);
                  }
                  
                  precioLista = jtPrecioVenta.getText();
			      precioMinimo = jtPrecioMinimo.getText();
			      precioPorcentajeMinimo = jtPorcentajeVentaMinimo.getText();
			      precioPorcentajeLista = jtPorcentajeVenta.getText();
			                  
                  
                  resultadoBoolean = true;
                

             } else {
             	 
             	 limpiar(false);
             	 
             }
             
             
             
           }

        } catch (Exception e) {
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultadoBoolean;
    }

     //*********************** Metodo guardar ************************

     private void guardar() throws Exception { 
     
        File imagenCodigoBarras = GeneraCodigoBarra.outputtingBarcodeAsPNG(jtCodigo.getText()); //Se obtiene la imagen del codigo de barras
        FileInputStream canalEntradaImagenCodigoBarras = new FileInputStream( imagenCodigoBarras );

        if (insertarFoto) { //Se verifica que se haya escogido una foto a insertar
         
          preparedStatementInsert = conectarMySQL.getConexion().prepareStatement("INSERT INTO PRODUCTOS VALUES(?,?,?,?,?,?,?,?,?,?,?,? )" );
          
            // Para insertar la imagen se extraeran los byte del archivO que se desea insertar
           File imagen = new File( fileChooser.getSelectedFile().getPath() );
		   FileInputStream canalEntrada = new FileInputStream( imagen );
		   preparedStatementInsert.setBinaryStream(11, canalEntrada, (int)imagen.length() );
		   
		   preparedStatementInsert.setBinaryStream(12, canalEntradaImagenCodigoBarras, (int)imagenCodigoBarras.length() );
		   
		   imagenCodigoBarras.delete();
	
		} else {
		
		
		     preparedStatementInsert = conectarMySQL.getConexion().prepareStatement("INSERT INTO PRODUCTOS (IdProducto,Descripcion,PrecioMininoVenta,PrecioListaVenta,IvaIncluido,StockMinimo,Stock,IdMarca,IdCategoria,Referencia,CodigoBarra) VALUES(?,?,?,?,?,?,?,?,?,?,? )" );
             
             preparedStatementInsert.setBinaryStream(11, canalEntradaImagenCodigoBarras, (int)imagenCodigoBarras.length() );
             
             imagenCodigoBarras.delete();
		}
		
			   	 
        preparedStatementInsert.setString(1,  jtCodigo.getText()); 
        preparedStatementInsert.setString(2,  jtDescripcion.getText()); 
        preparedStatementInsert.setString(3,  jtPrecioMinimo.getText()); 
        preparedStatementInsert.setString(4,  jtPrecioVenta.getText()); 
        preparedStatementInsert.setBoolean(5, jhIvaIncluido.isSelected()); 
        preparedStatementInsert.setString(6,  jtStockMinimo.getText()); 
        preparedStatementInsert.setString(7,  "0"); 
        preparedStatementInsert.setString(8,  jtCodigoMarca.getText()); 
        preparedStatementInsert.setString(9,  jtCodigoCategoria.getText());
        preparedStatementInsert.setString(10,  jaReferenciasApuntes.getText()); 
        
        
        
        
		// Se ejecuta el insert
		preparedStatementInsert.executeUpdate();
		
	             
               
        
        
     }



      //**************************** Metodo actualizar ************************

      private void actualizar() throws Exception { 
         
          // Para insertar la imagen se extraeran los byte del archivO que se desea insertar
         if (rutaImagenAlmacenada != null) {
             
             preparedStatementUpdate = conectarMySQL.getConexion().prepareStatement("UPDATE PRODUCTOS Set Descripcion = ?, IdMarca = ?, IvaIncluido = ?, IdCategoria = ?, Referencia = ?, StockMinimo = ?,  PrecioMininoVenta = ?, PrecioListaVenta = ? , Foto = ? Where IdProducto = ?");
			   	 
	         File imagen = new File(rutaImagenAlmacenada);
		     FileInputStream canalEntrada = new FileInputStream( imagen );
		     preparedStatementUpdate.setBinaryStream(9, canalEntrada, (int)imagen.length());
		     preparedStatementUpdate.setString(10,  jtCodigo.getText());
		  
		  } else {

              preparedStatementUpdate = conectarMySQL.getConexion().prepareStatement("UPDATE PRODUCTOS Set Descripcion = ?, IdMarca = ?, IvaIncluido = ?, IdCategoria = ?, Referencia = ?, StockMinimo = ?,PrecioMininoVenta = ?, PrecioListaVenta = ? Where IdProducto = ?");
			  preparedStatementUpdate.setString(9,  jtCodigo.getText()); 
		  

		  }
		     

         preparedStatementUpdate.setString(1,  jtDescripcion.getText()); 
         preparedStatementUpdate.setString(2,  jtCodigoMarca.getText()); 
         preparedStatementUpdate.setBoolean(3,  jhIvaIncluido.isSelected()); 
         preparedStatementUpdate.setString(4,  jtCodigoCategoria.getText()); 
         preparedStatementUpdate.setString(5,  jaReferenciasApuntes.getText());
         preparedStatementUpdate.setString(6,  jtStockMinimo.getText());
         preparedStatementUpdate.setString(7,  jtPrecioMinimo.getText()); 
         preparedStatementUpdate.setString(8,  jtPrecioVenta.getText()); 
        
	        
    	 // Se ejecuta el insert
		 preparedStatementUpdate.executeUpdate();
		 
		 
		 insertarPrecioHistoricoGanancia();
	
      }
      
      
      
      //**************************** Metodo actualizar ************************

      private void insertarPrecioHistoricoGanancia() throws Exception { 
      
          String condicion = "";
          String[] datos;
          
      
          if (!precioPorcentajeLista.equals(jtPorcentajeVenta.getText()) ||  !precioPorcentajeMinimo.equals(jtPorcentajeVentaMinimo.getText())) {
          	 
	                
			        if (!jtPrecioVenta.getText().equals("0")) {
			        
				        //Se actualiza la fecha hasta del producto del precio que compro
				        condicion = "idProducto = '" + jtCodigo.getText() +"' and FechaHasta is null";
				        
	          	        datos = new String[] { "FechaHasta = '" +  getObtenerFechaCompletaServidor(conectarMySQL) +"'"};
	          	        	
	          	        actualizar("HistoricoPorcentajesGanancia",datos,condicion,conectarMySQL,false);
	               }
			       
			       
			        datos = new String[5];
	          	 	
	          	    datos[0] = "'" + jtCodigo.getText() + "'";
	          	    datos[1] = "'" + getObtenerFechaCompletaServidor(conectarMySQL) + "'";
	          	    datos[2] = "null";
	          	    datos[3] =   jtPorcentajeVenta.getText();
	          	    datos[4] =   jtPorcentajeVentaMinimo.getText();
	          	    
	          	     
			        //Se inserta en la base de datos
			        guardar("HistoricoPorcentajesGanancia",datos,conectarMySQL,false);  
 
          	 	
          } 
          
	      
          

      }
      

      //**************************** Metodo eliminar ************************

      private void eliminar() throws Exception  { 
	
			int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar esta Producto?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

		    if (opcion == 0) {

				 // Se especifica la condición de borrado 

				 String condicion = "idProducto = '" + jtCodigo.getText()  +"'";

				 eliminarRegistro("Productos",condicion,conectarMySQL,false); 

			}

	  }
	  
	  //**************************** Metodo traerInformacion ************************

	  private String traerInformacion(String tabla,String condicion){
 	         
 	         String resultadoString = null;
 	         
 	         final String sentenciaSQL = "Select * "+
 	                                     "From " + tabla + 
 	                                     " Where " + condicion;
 	                                     
 	         
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     if (resultado.next()) 
                         
                          // Se asigna el valor
                          resultadoString = resultado.getString(2);
	 	         
	 	     }  catch (Exception e) {
	 	                          
	 	          Mensaje("No existe ningun registro en la tabla " + tabla,"Reporte de Error", 0);        
	 	     
	 	     }
	 	                 
	         return resultadoString;
     
 	  }
      
     //**************************** Metodo traerInformacion ************************

	  private Long buscarPrecioCompra(){
 	         
 	         Long resultadoLong = 0L;
 	         
 	         final String sentenciaSQL = "Select PrecioCompra "+
 	                                     "From Precios "+
 	                                     "Where idProducto = '" + jtCodigo.getText() + "' and FechaHasta is null";
 	                                     
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     if (resultado.next()) 
                         
                          // Se asigna el valor
                          resultadoLong = resultado.getLong(1);
	 	         
	 	     }  catch (Exception e) {
	 	                          
	 	          Mensaje("No existe ningun registro en la tabla precios ","Reporte de Error", 0);        
	 	     
	 	     }
	 	                 
	         return resultadoLong;
     
 	  }
         

      //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

         // Se verifica el componente que genero la acccion

         if (fuente == Blimpiar) {

            limpiar(true);

         } else 
            if (fuente == Bguardar) {
                  
                  boolean regitroBien = false;
                
                  //Se valida la información antes de guardar 
                  if (validarRegistro(camposObligatorios)) {

                      try {

                          String mensaje = "";

                          if (conectarMySQL.validarRegistro ("Productos",
                                                             "IdProducto = '" + jtCodigo.getText() +"'")) {

                            
                            actualizar(); 

                            mensaje = "Producto actualizado correctamente";

                          }  else {
                          	
                          	
                             guardar(); 

                             mensaje = "Información de Producto registrada correctamente";

                          } 


                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 

                          Mensaje(mensaje,"Información",1);

                          limpiar(true);   //Se limpia la forma 

                       } catch (Exception e) {

                           conectarMySQL.rollback();

                           Mensaje("Error al Insertar Producto" +e,"Error",0); 

				      }

                   }

            } else 

                if (fuente == Beliminar) { 

                   try {
                        
                       if (!jtCodigo.getText().isEmpty()) {
                         
                          if (buscarRegistro()) {
                          
		                       eliminar(); 
		
		                       conectarMySQL.commit();   //Se registra los cambios en la base de dato 
		
		                       Mensaje("Producto Eliminado","Información",JOptionPane.INFORMATION_MESSAGE);
		
		                       limpiar(true);  // Se limpia la forma 
		                   
		                  } else {
		                  	
		                  	   Mensaje("Codigo de producto "+  jtCodigo.getText() +" no registrado","Información",2);
		                  	   jtCodigo.grabFocus();
		                  	
		                  }	
	                           
	                   } else {
	                    	
	                    	 Mensaje("Se debe especificar el codigo del producto a eliminar","Información",2);
	                    	  jtCodigo.grabFocus();
	                   }   

                 } catch (Exception e) {

                       conectarMySQL.rollback();

                       Mensaje("No se puede eliminar Producto" + e,"Error",JOptionPane.ERROR_MESSAGE); 

                  }

                }  else 

                    if (fuente == Bbuscar) { 

                       buscarRegistro();

                    } else 

                       if (fuente == Bsalir) {

                         ocultarJFrame();
                         
                         if (file != null)
                         
                               file.delete(); //Se borra el archivo imagen creado 

                       } else
                          if (fuente == jbCambiarImagen) {
                             
                              
                              int opcion = fileChooser.showOpenDialog(getJFrame());
    		
    		                   if (opcion == JFileChooser.APPROVE_OPTION) {
    		                   	
	    		                   	rutaImagenAlmacenada = fileChooser.getSelectedFile().getPath();
	    		                   		
	    			                jlFoto.setIcon(getImagenEscalada(170,170,rutaImagenAlmacenada));  
	    			                
	    			                insertarFoto = true;
                              
                               }
                        
                          } else
					
							if (fuente == jtCategoria)  {
					
									if (getJTableArrayListaValores()[0].isVisible()) {
					
										if (getJTableArrayListaValores()[0].getSelectedRow() > -1)
					
											getJTableArrayListaValores()[0].setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
					
											getJTableArrayListaValores()[0].setRowSelectionInterval(0,0);
					
											getJTableArrayListaValores()[0].grabFocus();
					
								     } else
					
					  			     	jtCodigoMarca.grabFocus();
					
							}  else
					
								if (fuente == jtMarca)  {
						
										if (getJTableArrayListaValores()[1].isVisible()) {
						
											if (getJTableArrayListaValores()[1].getSelectedRow() > -1)
						
												getJTableArrayListaValores()[1].setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
						
												getJTableArrayListaValores()[1].setRowSelectionInterval(0,0);
						
												getJTableArrayListaValores()[1].grabFocus();
						
									}  else
						
										jhIvaIncluido.grabFocus();
	
		                        	} 
	  }                      	

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 


		 if (f.getComponent() == jtCategoria && (getDialogoArrayListaValores() == null || getDialogoArrayListaValores()[0] == null || (getDialogoArrayListaValores()[0]!= null && !getDialogoArrayListaValores()[0].isVisible())) && jtCategoria.isFocusable() && mostrarListaAutomatica) {
		
			  listaValorCategoria.mostrarListaValores();
			
			  
			          
		 } else

		 	 if (f.getComponent() == jtMarca && (getDialogoArrayListaValores() == null || getDialogoArrayListaValores()[1] == null || (getDialogoArrayListaValores()[1]!= null && !getDialogoArrayListaValores()[1].isVisible())) && jtMarca.isFocusable() && mostrarListaAutomatica) {

	              listaValorMarcas.mostrarListaValores(); 
	      
	         } else {
	         
	              
	                if (f.getComponent() != jtCategoria && getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[0]!= null && getDialogoArrayListaValores()[0].isVisible() && jtCategoria.isFocusable()) {
		                
		               if (f.getComponent() != jtCodigoCategoria) {
		                
		                 if (getJTableArrayListaValores()[0].getRowCount() > 0) {
		                 
								//Se oculta el scroll de la lista de valores
								jtCodigoCategoria.setText(getJTableArrayListaValores()[0].getValueAt(0,0).toString()); 
								jtCategoria.setText(getJTableArrayListaValores()[0].getValueAt(0,1).toString());
				
								getDialogoArrayListaValores()[0].setVisible(false); //Se oculta automaticamente la lista de valor es
			            
			             } else {
			             	
			             	jtCategoria.setText("");
			             	jtCategoria.grabFocus();
			             	listaValorCategoria.mostrarListaValores();
			             	
			             } 			
					  
					  } else
					  
				           getDialogoArrayListaValores()[0].setVisible(false); //Se oculta automaticamente la lista de valor es
			   
					
					} else
					
					    if (f.getComponent() != jtMarca && getDialogoArrayListaValores() != null && getDialogoArrayListaValores()[1]!= null && getDialogoArrayListaValores()[1].isVisible() && jtMarca.isFocusable()) {
					    	
			    	      if (f.getComponent() != jtCodigoMarca) {
			    	      
			    	        if (getJTableArrayListaValores()[1].getRowCount() > 0) {
			    	        	
			    			   //Se oculta el scroll de la lista de valores
							   jtCodigoMarca.setText(getJTableArrayListaValores()[1].getValueAt(0,0).toString()); 
							   jtMarca.setText(getJTableArrayListaValores()[1].getValueAt(0,1).toString());
							 
							   getDialogoArrayListaValores()[1].setVisible(false);
							
							} else {
								
								jtMarca.setText("");
			             	    jtMarca.grabFocus();
			             	    listaValorMarcas.mostrarListaValores();
			             	
								
							} 
                         
                         } else
                             
                              getDialogoArrayListaValores()[1].setVisible(false);
							
					    	
					    }
	         	
                    }  
			
					
					
			       // se coloca el atributo visual por defecto
			       f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
			       
			       if (f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField"))
			          
			          ((JTextField)f.getComponent()).selectAll();
			       
			       
			       mostrarListaAutomatica = true;
	

	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 

          if (f.getComponent() == jtCodigo && !jtCodigo.getText().isEmpty()) {
          	
          	 if (!buscarRegistro()) {
          	 	
          	 	jtStock.setText("0");
          	 	jtPrecioMinimo.setText("0");
          	 	jtPrecioVenta.setText("0");
          	 	jtPorcentajeVentaMinimo.setText("0");
          	 	jtPorcentajeVenta.setText("0");
          	 	jtStockMinimo.setText(valorDefectoStock);
          	 	jtStockMinimo.selectAll();
          	 	
          	 }
          	
          } else
              
	          if (f.getComponent() == jtCodigoCategoria && !jtCodigoCategoria.getText().isEmpty()) {
	          	
	          	  String categoria = traerInformacion("Categorias","idCategoria = '"+ jtCodigoCategoria.getText() +"'"); //Se busca la categoria
	          	  
	          	  if (categoria != null) {
	          	  	   
	          	  	   mostrarListaAutomatica = false;
	          	  	   jtCategoria.setText(categoria);
	          	  	   jtCodigoMarca.grabFocus();
	          	  	   
	          	  	   
	          	  } else {
	          	  	  
	          	  	   mostrarListaAutomatica = false;
	       			      
	          	  	  //Se verifica si se desean hacer tramites diferentes a matriculas o radicado
					   int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Código categoria  " +   jtCodigoCategoria.getText() + " no registrado \nDesea registrar esta categoria?","Notificación ",JOptionPane.YES_NO_OPTION);
						    
					   if ( opcion == 0 ) {
					       
					       mostrarCategoria = true;
					       new Categoria(conectarMySQL,getJFrame(),jtCodigoCategoria.getText());
		    	           getJFrame().setVisible(false);
			
	          	  	   } else {
	          	  	        
	          	  	        mostrarListaAutomatica = true;
	          	  	        jtCategoria.setText("");
	          	  	        jtCodigoCategoria.setText("");
	          	  	 	
	          	  	   }
	          	  	   
	          	  	  
	          	  }
	          	  
	          	  
	          	 
	          	  	   
	          	
	          }else
	          
		          if (f.getComponent() == jtCodigoMarca  && !jtCodigoMarca.getText().isEmpty()) {
		          	
		          	  String marca = traerInformacion("Marcas", "idMarca = '"+ jtCodigoMarca.getText() +"'"); //Se busca la marca
		          	  
		          	  if (marca != null) {
		          	  	  
		          	  	  mostrarListaAutomatica = false;
		          	  	  jtMarca.setText(marca);
		          	  	  jtStockMinimo.grabFocus();
		          	  	 
		          	  	 
		          	  } else {
	          	  	  
	          	  	     mostrarListaAutomatica = false;
	       			      
	          	  	     //Se verifica si se desean hacer tramites diferentes a matriculas o radicado
					      int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Código marca  " +   jtCodigoMarca.getText() + " no registrado \nDesea registrar esta marca?","Notificación ",JOptionPane.YES_NO_OPTION);
						    
						   if ( opcion == 0 ) {
						       
						       new Marca(conectarMySQL,getJFrame(),jtCodigoMarca.getText());
			    	           getJFrame().setVisible(false);
				
		          	  	   } else {
		          	  	        
		          	  	        mostrarListaAutomatica = true;
		          	  	        jtMarca.setText("");
		          	  	        jtCodigoMarca.setText("");
		          	  	 	
		          	  	   } 
			          }	  
		          	  
		          }  else
		          
		          
		               if (f.getComponent() == jtPrecioVenta  && !jtPrecioVenta.getText().isEmpty()) {
		               	    
		               	    if (!precioLista.equals(jtPrecioVenta.getText()))  {
		               	    
		               	        if (precioCompra == 0)  //Se busca en la BD el precio de Compra 
		               	          
		               	           precioCompra = buscarPrecioCompra();
		                        
		                        if (precioCompra < Long.parseLong(jtPrecioVenta.getText())) {
		                          
			               	        Float porcentaje = redondearNumero((Float.parseFloat(jtPrecioVenta.getText()) - precioCompra)/precioCompra * 100,2);  
			          
			                        jtPorcentajeVenta.setText(String.valueOf(porcentaje.longValue()));
			                        
			                    } else {
			                    	
			                    	Mensaje("El precio de venta " + jtPrecioVenta.getText() + " debe ser mayor al precio de compra: " + precioCompra,"Información",2);
			                    	jtPrecioVenta.setText("");
			                    	jtPrecioVenta.grabFocus();
			                    }    
		                                    
		                  }  
                       } else
                       
                          if (f.getComponent() == jtPorcentajeVenta  && !jtPorcentajeVenta.getText().isEmpty()) {
		               	         
		               	        if (precioCompra == 0)  //Se busca en la BD el precio de Compra 
		               	          
		               	           precioCompra = buscarPrecioCompra();
		                     
		                     if (Long.parseLong(jtPorcentajeVenta.getText()) > 0) {
		                          
		               	        Float precio = redondearNumero(precioCompra * (1 + Float.parseFloat(jtPorcentajeVenta.getText()) / 100),0);  
		          
		                        jtPrecioVenta.setText(String.valueOf(precio.longValue()));
		                   
		                    } else {
			                    	
			                    	Mensaje("El porcentaje de ganacia debe ser mayor a 0 " + precioCompra,"Información",2);
			                    	jtPorcentajeVenta.setText("");
			                    	jtPorcentajeVenta.grabFocus();
			                    	
			                    }    
		          
                         }	else	          
				              
				               if (f.getComponent() == jtPrecioMinimo  && !jtPrecioMinimo.getText().isEmpty()) {
				               	         
				               	        if (precioCompra == 0)  //Se busca en la BD el precio de Compra 
				               	          
				               	           precioCompra = buscarPrecioCompra();
				   
				               	        if (precioCompra < Long.parseLong(jtPrecioMinimo.getText())) {
			                         
					               	        Float porcentaje = redondearNumero((Float.parseFloat(jtPrecioMinimo.getText()) - precioCompra)/precioCompra * 100,2);  
					          
					                        jtPorcentajeVentaMinimo.setText(String.valueOf(porcentaje.longValue()));
					                   
					                   } else {
			                    	
					                    	Mensaje("El precio de minimo " + jtPrecioMinimo.getText() + " debe ser mayor al precio de compra: " + precioCompra,"Información",2);
					                    	jtPrecioMinimo.setText("");
					                    	jtPrecioMinimo.grabFocus();
					                    }        
					                    
				          
		                       } else
		                       
		                          if (f.getComponent() == jtPorcentajeVentaMinimo  && !jtPorcentajeVentaMinimo.getText().isEmpty()) {
				               	         
				               	        if (precioCompra == 0)  //Se busca en la BD el precio de Compra 
				               	          
				               	           precioCompra = buscarPrecioCompra();
				   
				               	        if (Long.parseLong(jtPorcentajeVentaMinimo.getText()) > 0) {
				               	        	  
					               	        Float precio = redondearNumero(precioCompra * (1 + Float.parseFloat(jtPorcentajeVentaMinimo.getText()) / 100),2);  
					          
					                        jtPrecioMinimo.setText(String.valueOf(precio.longValue()));
					                    
					                    } else {
					                    	
					                    	Mensaje("El porcentaje de ganacia debe ser mayor a 0 " + precioCompra,"Información",2);
					                    	jtPorcentajeVentaMinimo.setText("");
					                    	jtPorcentajeVentaMinimo.grabFocus();
					                    	
					                    }    
				          
		                         }		          
		          
          // se coloca el atributo visual por defecto
          f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

   
}