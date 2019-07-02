package com.JASoft.azkaban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.KeyboardFocusManager;

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
import javax.swing.JScrollPane;
import javax.swing.JDialog;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import com.JASoft.componentes.ConectarMySQL;

import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;


final public class Proveedor extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Array para campos obligatorios
    private JTextField[] camposObligatorios;

    //** Se declaran los JPanel
    private  JPanel panelIdentificacion;
    private  JPanel panelApellidosNombres;
    private  JPanel panelDatosContacto;
    private  JPanel panelContactos;
    

    //** Se declaran los JTextField
    private  JTextField jtNit;
    private  JTextField jtDireccion;
    private  JTextField jtNombre;
    private  JTextField jtTelefono;
    private  JTextField jtPaginaWeb;
    private  JTextField jtEmail;
    private  JTextField jtCiudadMunicipio;
    private  JTextField jtFax;
    private  JTextField jtNombresContacto;
    private  JTextField jtApellidosContacto;
    private  JTextField jtTelefonoContacto;
    private  JTextField jtCelularContacto;
    private  JTextField jtEmailContacto;
    private  JTextField jtCargoContacto;


    //** Se declaran los JButton
    private  JButton jbAgregar;
    private  JButton jbEditar;
    private  JButton jbEliminar;
    private  JButton jbAceptarContacto;
    private  JButton jbCancelarContacto;

    //** Se declaran los JComboBox
    private  JComboBox jcSexoContacto;
    private  JComboBox jcDepartamento;
     
    //** Se declaran los JLabel 
  	private JLabel codigoDivisionPolitica  = new JLabel(getCodigoDivisionPolitica(conectarMySQL)); //Componente que se envia como parametro a
        
    //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 

    //** Se declaran los JTable
    private  JTable tablaContactos;

    //** Se declaran los String
    private  String numeroProveedor = "";

   //Columnas y filas estaticas 
    private Object [] nombresColumnas = {"Nombre","Apellido","Teléfono","Celular","Correo","Cargo","NumeroContacto"};
    private Object [][] datos = new Object[5][7];
    
    //Modelo de datos 
    private SortableTableModel dm;
    
    //JDialog
    private JDialog dialogContacto;
    
    //Vectores
    private Vector datosTablaContactos;
    
       
    //**Se declaran los JFrame
    private JFrame framePadre; // Utilizado para el frame padre en la tabla Ventas
  

    //** Constructor General 
    public Proveedor(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Proveedor","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
      this.framePadre = p_frame;
      
       iniciarGUI();

    }
    
    //** Constructor General 
    public Proveedor(ConectarMySQL p_conectarMySQL,JFrame p_frame,String nit) {

      super("Provedor","Toolbar",p_frame,"dummy");

      conectarMySQL = p_conectarMySQL;
      
      this.framePadre = p_frame;
      
      iniciarGUI();
      
      jtNit.setText(nit);
      
      jtNombre.grabFocus();

    }
    
    
    
    public void iniciarGUI() {
	    
	      //** Se agregan los JPanel
	      panelIdentificacion = getJPanel("Identificación",218, 75, 355, 75,14);
	
	      panelApellidosNombres = getJPanel("Datos Generales",60, 160, 670, 165,14);
	
	      panelDatosContacto = getJPanel("Contactos",60, 340, 670, 190,14);
	      
	      //** Se declaran los JLabel
	
	      JLabel jlNit = getJLabel("Nit",25, 25, 75, 15);
	      panelIdentificacion.add(jlNit);
	
	      JLabel jlNombre = getJLabel("Nombre",140, 25, 65, 15);
	      panelIdentificacion.add(jlNombre);
	
	      JLabel jlDireccion = getJLabel("Dirección:",0, 28, 120, 15);
	      jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
	       panelApellidosNombres.add(jlDireccion);
	
	      JLabel jlTelefono = getJLabel("Teléfono:",375, 61, 65, 15);
	      jlTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
	      panelApellidosNombres.add(jlTelefono);
	
	      JLabel jlPaginaWeb = getJLabel("Pág. Web:",55, 128, 65, 15);
	      jlPaginaWeb.setHorizontalAlignment(SwingConstants.RIGHT);
	      panelApellidosNombres.add(jlPaginaWeb);
	
	      JLabel jlEmail = getJLabel("e-Mail:",375, 128, 65, 15);
	      jlEmail.setHorizontalAlignment(SwingConstants.RIGHT);
	      panelApellidosNombres.add(jlEmail);
	
	      JLabel jlCiudadMunicipio = getJLabel("Ciudad / Municipio:",0, 95, 120, 15);
	      jlCiudadMunicipio.setHorizontalAlignment(SwingConstants.RIGHT);
	      panelApellidosNombres.add(jlCiudadMunicipio);
	
	      JLabel jlDepartamento = getJLabel("Departamento:",0, 61, 120, 15);
	      jlDepartamento.setHorizontalAlignment(SwingConstants.RIGHT);
	      panelApellidosNombres.add(jlDepartamento);
	
	      JLabel jlFax = getJLabel("Fax:",375, 95, 65, 15);
	      jlFax.setHorizontalAlignment(SwingConstants.RIGHT);
	      panelApellidosNombres.add(jlFax);
	
	      //** Se agregan los JTable
	
	     // se configura el modelo para la tabla
	     dm = new SortableTableModel() {
	
	        //Se especifica el serial para la serializacion
	        static final long serialVersionUID = 19781212;
	
	        public Class getColumnClass(int col) {
	
	            return String.class;
	
	        }
	
	       public boolean isCellEditable(int row, int col) {
	
	           return false;
	
	       }
	
	     };
	
	     dm.setDataVector(datos,nombresColumnas); //Se agrega las columnas y filas al JTable
	
	
	      //Se instancia el JTable
	      tablaContactos = getJTable(dm);
	      
	      tablaContactos.addMouseListener(new MouseAdapter() {   //Se agrega un evento a la tabla para que puede editar la fila con doble click
	 		   
	 		   public void mouseClicked(MouseEvent m){
	 		    
	 		     if (m.getClickCount() ==2 ) {
	 		     	
	 		     	  jbEditar.doClick();
	 		     	  
		 	     }
		 	   }
		 	}); 
	      
	      //Se crea un JScrollPane
	      JScrollPane scroll = new JScrollPane(tablaContactos);
	      scroll.setBounds(25, 35, 625, 100);
	      panelDatosContacto.add(scroll);
	      
	      
	      configurarColumnas(); //Se configuran las columnas de la tabla
	      
	      //** Se agregan los JComboBox
	
	      jcDepartamento = getJComboBox(130, 58, 215, 20,"Seleccione el Departamento");
	      jcDepartamento.setMaximumRowCount(15);
		  traerDepatamentos(jcDepartamento,conectarMySQL); // Se traen los departamentos
		  jcDepartamento.setSelectedItem(getDptoDivisionPolitica(conectarMySQL));
		  jcDepartamento.addActionListener(this);
		  jcDepartamento.addFocusListener(this);
	      panelApellidosNombres.add(jcDepartamento);
	      
	      //** Se agregan los JTextField
	
	      jtNit = getJTextField("",25, 40, 105, 20,"Digíte el Nit o Número de Identificación de la Empresa","12");
	      jtNit.setHorizontalAlignment(JTextField.RIGHT);
	      jtNit.addKeyListener(new KeyAdapter() {
	      	
	      	     public void  keyTyped (KeyEvent k) {
	
		             if ((k.getKeyChar()!=KeyEvent.VK_BACK_SPACE) && (!Character.isDigit(k.getKeyChar())) && k.getKeyChar() != '.' &&  k.getKeyChar() != '-')
		         
		                  k.consume(); // Se consume o borra el caracter digitado
		         }           
		     }	
	      	
	      );
	      jtNit.addFocusListener(this);
	      panelIdentificacion.add(jtNit);
	
	      jtDireccion = getJTextField("",130, 25, 415, 20,"Digite la Dirección de la Empresa","40");
	      jtDireccion.addFocusListener(this);
	      jtDireccion.addKeyListener(getConvertirMayuscula());
	      panelApellidosNombres.add(jtDireccion);
	
	      jtNombre = getJTextField("",140, 40, 195, 20,"Digite el Nombre de la Empresa","60");
	      jtNombre.addFocusListener(this);
	      jtNombre.addKeyListener(getConvertirMayuscula());
	      panelIdentificacion.add(jtNombre);
	
	      jtTelefono = getJTextField("",450, 58, 95, 20,"Digite el Número de Teléfono Fijo de la Empresa","12");
	      jtTelefono.setHorizontalAlignment(JTextField.RIGHT);
	      jtTelefono.addFocusListener(this);
	      jtTelefono.addKeyListener(getValidarEntradaNumeroJTextField());
	      panelApellidosNombres.add(jtTelefono);
	
	      jtPaginaWeb = getJTextField("",130, 125, 215, 20,"Digite la dirección de la Página Web de la Empresa","40");
	      jtPaginaWeb.setHorizontalAlignment(JTextField.LEFT);
	      jtPaginaWeb.addFocusListener(this);
	      panelApellidosNombres.add(jtPaginaWeb);
	
	      jtEmail = getJTextField("",450, 125, 200, 20,"Digíte el Correo Electrónico de la Empresa","40");
	      jtEmail.addFocusListener(this);
	      panelApellidosNombres.add(jtEmail);
	      
	      jtFax = getJTextField("",450, 92, 95, 20,"Digite el Número del Fax de la Empresa","12");
	      jtFax.setHorizontalAlignment(JTextField.RIGHT);
	      jtFax.addFocusListener(this);
	      jtFax.addKeyListener(getValidarEntradaNumeroJTextField());
	      panelApellidosNombres.add(jtFax);
	      
	      
	      jtCiudadMunicipio = getJTextField(getMunicipioDivisionPolitica(conectarMySQL),130, 92, 215, 20,"Digite la Ciudad / Municipio o Presione F9","30");
	      jtCiudadMunicipio.selectAll();
	      jtCiudadMunicipio.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
	     
	      jtCiudadMunicipio.selectAll();
	      
	      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
	      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtCiudadMunicipio,jcDepartamento,conectarMySQL,getOcultarColumnas(),480);
	 
	      jtCiudadMunicipio.addFocusListener(this);
	      jtCiudadMunicipio.addKeyListener(getListaValores(getSentencia(),getComponentesRetorno(),this,jtCiudadMunicipio,jcDepartamento,conectarMySQL,getOcultarColumnas(),480));
	      jtCiudadMunicipio.addActionListener(this);
	      panelApellidosNombres.add(jtCiudadMunicipio);
	
	     
	
	      //** Se agregan los JButton
	
	      jbAgregar = getJButton("Agregar",110, 150, 125, 25,"Imagenes/Adicionar.gif","");
	      jbAgregar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/AdicionarS.gif")));
	      jbAgregar.addActionListener(this);
	      panelDatosContacto.add(jbAgregar);
	
	      jbEditar = getJButton("Editar",275, 150, 125, 25,"Imagenes/Editar.gif","");
          jbEditar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/EditarS.gif")));
	      jbEditar.addActionListener(this);
	      panelDatosContacto.add(jbEditar);
	
	      jbEliminar = getJButton("Eliminar",440, 150, 125, 25,"Imagenes/Eliminar.gif","");
          jbEliminar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/EliminarS.gif")));
	      jbEliminar.addActionListener(this);
	      panelDatosContacto.add(jbEliminar);

          getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Proveedores.gif")).getImage());
    

	      //** Se muestra el JFrame
	      mostrarJFrame(); 
	      
	      jtNit.grabFocus();    //Se coloca el foco en el primer componente Focusable
	      
	      // Se adicionan eventos a los botones de la Toolbar
	      Blimpiar.addActionListener(this);
	      Bguardar.addActionListener(this);
	      Beliminar.addActionListener(this);
	      Bbuscar.addActionListener(this);
	      Bsalir.addActionListener(this);
	      Bimprimir.setEnabled(false);
	
	      
		  //Se crea el array de campos obligatorios 
		  crearArrayCamposObligatorios();

    }
    
    
    //********************* configurarJDialogContacto()****************************************
    private void configurarJDialogContacto() {
    	
    	
    	if (dialogContacto == null) {
    		
    		  dialogContacto = new JDialog(getJFrame(),"Contactos",true);
    		  dialogContacto.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeysFrame());
	  	  	  dialogContacto.setLayout(null);
		  	  dialogContacto.setSize(670,320);
		  	  dialogContacto.setLocationRelativeTo(null);
		  	  
		  	  
		      JLabel jlNombres = getJLabel("Nombres",30, 20, 65, 15);
		      dialogContacto.add(jlNombres);
		
		      JLabel jlApellidos = getJLabel("Apellidos",235, 20, 95, 15);
		      dialogContacto.add(jlApellidos);
		
		      JLabel jlSexo = getJLabel("Sexo",530, 20, 75, 15);
		      dialogContacto.add(jlSexo);
		
		      JLabel jlTelefono = getJLabel("Teléfono:",210, 97, 55, 16);
		      dialogContacto.add(jlTelefono);
		
		      JLabel jlCelular = getJLabel("Celular:",210, 130, 55, 15);
		      dialogContacto.add(jlCelular);
		
		      JLabel jlEmail = getJLabel("e-Mail:",210, 161, 55, 15);
		      dialogContacto.add(jlEmail);
		      
		      JLabel jlCargo = getJLabel("Cargo:",210, 190, 55, 20);
		      dialogContacto.add(jlCargo);
		      
		      
		      //** Se instancian los JSeparator
		
		      JSeparator jSeparator1 = new JSeparator();
		      jSeparator1.setBounds(40, 75, 595, 2);
		      jSeparator1.setForeground(Color.lightGray);
		      dialogContacto.add(jSeparator1);
		
		      JSeparator jSeparator2 = new JSeparator();
		      jSeparator2.setBounds(40, 225, 595, 2);
		      jSeparator2.setForeground(Color.lightGray);
		      dialogContacto.add(jSeparator2);
		      
		      //** Se agregan los JTextField
		
		      jtNombresContacto = getJTextField("",30, 35, 195, 20,"Digite Nombres del Contacto","40");
		      jtNombresContacto.addFocusListener(this);
		      jtNombresContacto.addKeyListener(getConvertirMayuscula());
		      dialogContacto.add(jtNombresContacto);
		      
		      
		      jtApellidosContacto = getJTextField("",235, 35, 280, 20,"Digite Apellidos del Contacto","40");
		      jtApellidosContacto.addFocusListener(this);
		      jtApellidosContacto.addKeyListener(getConvertirMayuscula());
		      dialogContacto.add(jtApellidosContacto);
		      
		      
		      jtTelefonoContacto = getJTextField("",280, 95, 95, 20,"Digite el Número de Teléfono Fijo del Contacto","12");
		      jtTelefonoContacto.setHorizontalAlignment(JTextField.RIGHT);
		      jtTelefonoContacto.addFocusListener(this);
		      jtTelefonoContacto.addKeyListener(getValidarEntradaNumeroJTextField());
		      dialogContacto.add(jtTelefonoContacto);
		      
		      
		      jtCelularContacto = getJTextField("",280, 127, 95, 20,"Digite el Número de Teléfono Celular del Contacto","12");
		      jtCelularContacto.setHorizontalAlignment(JTextField.RIGHT);
		      jtCelularContacto.addFocusListener(this);
		      jtCelularContacto.addKeyListener(getValidarEntradaNumeroJTextField());
		      dialogContacto.add(jtCelularContacto);
		      
		      
		      jtEmailContacto = getJTextField("",280, 158, 200, 20,"Digite el Correo Electrónico del Contacto","40");
		      jtEmailContacto.addFocusListener(this);
		      dialogContacto.add(jtEmailContacto);
		      
		      jtCargoContacto = getJTextField("",280, 190, 200, 20,"Digite el Cargo que Ocupa en la Empresa el Contacto","40");
		      jtCargoContacto.addKeyListener(getConvertirMayuscula());
    	      dialogContacto.add(jtCargoContacto);
		      
		      
		      //** Se agregan los JButton
		      jbAceptarContacto = getJButton("Aceptar",215, 245, 125, 25,"","");
		      jbAceptarContacto.addActionListener(this);
		      dialogContacto.add(jbAceptarContacto);
		     
		      jbCancelarContacto = getJButton("Cancelar",365, 245, 125, 23,"","");
		      jbCancelarContacto.addActionListener(this);
		      dialogContacto.add(jbCancelarContacto);
		     
		     
		      //** Se agregan los JComboBox
		
		      jcSexoContacto = getJComboBox(530, 35, 115, 20,"");
		      jcSexoContacto.addItem("Masculino");
              jcSexoContacto.addItem("Femenino");
              jcSexoContacto.addFocusListener(this);
		      dialogContacto.add(jcSexoContacto);
		     
		  
		     

		  	  
    	} else {
    		
    		
    		  jtNombresContacto.setText("");
		      jtApellidosContacto.setText("");
		      jtTelefonoContacto.setText("");
		      jtCelularContacto.setText("");
		      jtEmailContacto.setText("");
		      jtCargoContacto.setText("");
    		  jcSexoContacto.setSelectedIndex(0);
    		  
    		  
    		  
    		
    	}
    	
    	
    }
    
     //********************* agregarFilaJTable()****************************************
     
     private void agregarFilaJTable() {
         
         int fila = obtenerFilas(tablaContactos,0);
         
         if (fila < tablaContactos.getRowCount()) {
          
           tablaContactos.setValueAt(jtNombresContacto.getText(),fila,0);
           tablaContactos.setValueAt(jtApellidosContacto.getText(),fila,1); 
           tablaContactos.setValueAt(jtTelefonoContacto.getText(),fila,2); 
           tablaContactos.setValueAt(jtCelularContacto.getText(),fila,3);
           tablaContactos.setValueAt(jtEmailContacto.getText(),fila,4);
           tablaContactos.setValueAt(jtCargoContacto.getText(),fila,5);
           
        } else
          
             dm.addRow(new Object[]{jtNombresContacto.getText(),jtApellidosContacto.getText(),jtTelefonoContacto.getText(),jtCelularContacto.getText(),jtEmailContacto.getText(),jtCargoContacto.getText()});
         	
     	
     }
     
     //******************************** Metodo crearArrayCamposObligatorios *******************

     public void crearArrayCamposObligatorios() { 

           //Se instancia un array de JTextField 
           camposObligatorios = new JTextField[3];

           camposObligatorios[0] = jtDireccion;
           camposObligatorios[1] = jtNombre;
           camposObligatorios[2] = jtCiudadMunicipio;

     }
     
     
	//******************************** Metodo getSentencia()  ***************************************

	final private String getSentencia() {

		 String sentencia = "Select M.DivisionPolitica 'Codigo',T1.Municipio Municipio,IF(M.DivisionPolitica = M.DivisionPoliticaMnpo,'',M.Municipio) 'Corregimiento',IF(M.DivisionPolitica = M.DivisionPoliticaMnpo,T1.Municipio ,M.Municipio) "+
                        "From Municipioscorregimientos M, Municipioscorregimientos T1 "+
                        "Where M.CodigoDpto = T1.CodigoDpto and T1.DivisionPolitica = M.DivisionPoliticaMnpo and "; 	
                                 
         return sentencia;

	 }

	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private Object[][] getComponentesRetorno() {

		Object[][] objetosRetorno = new Object[1][5];
		
		objetosRetorno[0][0] = jtCiudadMunicipio;
        objetosRetorno[0][1] = jtFax;
       	objetosRetorno[0][2] = "3";
       	objetosRetorno[0][3] = codigoDivisionPolitica;
       	objetosRetorno[0][4] = "0";
       	

		return objetosRetorno;

	}

	//******************************** Metodo getOcultarColumnas()()  ***************************************

	final private int[] getOcultarColumnas() {

		int[] columnasOcultar = new int[1];
		
		columnasOcultar[0] = 3;
       	
		return columnasOcultar;

	}

    //*********************** Metodo limpiar ************************

    private void limpiar(boolean dummy) { 
     
      if (dummy) {
     
	       jtNit.setText("");
	       jtNombre.setText("");
	       jtNit.grabFocus();    //Se coloca el foco en el primer componente Focusable
	       
     
     
      }
      
        
      
      
      jtDireccion.setText("");
      
      jtTelefono.setText("");
      jtPaginaWeb.setText("");
      jtEmail.setText("");
      jtFax.setText("");
      
      jcDepartamento.setSelectedItem("SUCRE");
      jtCiudadMunicipio.setText(getMunicipioDivisionPolitica(conectarMySQL));
      
      codigoDivisionPolitica.setText(getCodigoDivisionPolitica(conectarMySQL)); //Se configura la division politica
 	  
 	  dm.setDataVector(datos,nombresColumnas);

      
      

    }

   //*********************** Metodo buscarRegistro ************************

    private void buscarRegistro(boolean dummy) { 

        String sentenciaSQL = "Select * "+
                              "From   Proveedores ";
                              
        if (dummy) {
        
            
            sentenciaSQL += "Where Nit ='" + jtNit.getText() + "'";
            jtNombre.setText("");
            
        } else {
        
            sentenciaSQL += "Where RazonSocial = '" + jtNombre.getText() + "'" ;
            
        }    
       
        numeroProveedor = "";
        
        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 

             if (resultado.next()) { 

                 numeroProveedor = resultado.getString(1);
				 jtNit.setText(resultado.getString(2));
				 jtNombre.setText(resultado.getString(3));
				 jtDireccion.setText(resultado.getString(4));
				 jtTelefono.setText(resultado.getString(6));
				 jtFax.setText(resultado.getString(7));
				 jtPaginaWeb.setText(resultado.getString(8));
				 jtEmail.setText(resultado.getString(9));
				 
				 codigoDivisionPolitica.setText(resultado.getString(5)); //Se asigna la division politica
 	
	                 
	             Vector municipios =   buscarMunicipio(conectarMySQL,codigoDivisionPolitica.getText()); //Se busca el nombre del departamento y municipio o corregimiento
	 	   	     jcDepartamento.setSelectedItem(municipios.elementAt(0));
	 	   	     jtCiudadMunicipio.setText(municipios.elementAt(1).toString());
	 	   	     
	 	   	     
	 	   	     //Se traen los contactos
	 	   	     sentenciaSQL = "Select Nombre,Apellido,Telefono,Celular,Correo,Cargos,NumeroContacto "+
                                "From  Contactos " +
                                "Where NumeroProveedor = " + numeroProveedor;
	 	   	     
	 	   	     resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	   	     
	 	   	     
	 	   	     Vector datos = new Vector();
	 	   	     
	 	   	     while (resultado.next()) {
	 	   	     	
	 	   	     	Vector columnas = new Vector();
	 	   	     	
	 	   	     	for (int i = 1; i <= 7; i++)
	 	   	     	
	 	   	     	    columnas.add(resultado.getString(i));
	 	   	     	
	 	   	     	
	 	   	     	datos.add(columnas);
	 	   	     		
	 	   	     }
	 	   	     
	 	   	     
	 	   	     Vector nombresColumnas = new Vector();
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     nombresColumnas.add("");
	 	   	     
	 	   	     dm.setDataVector(datos,nombresColumnas);
	 	   	     
	 	   	     
 	   	         datosTablaContactos = new Vector(); //Se agregan los elementos en un nuevo vector
                                                     //para guardar los datos de los Vectores ,
                                                     // mas no las refenrencias
                                           
      
	             for (int i = 0; i < dm.getDataVector().size(); i++) {
	          	
	            	  Vector columna = (Vector)dm.getDataVector().elementAt(i);
	          	
	          	      datosTablaContactos.add(columna.clone());
	          	
	             }
	             
	             
	             if (tablaContactos.getRowCount() < 4) 
     	       	    
     	       	    for (int i = obtenerFilas(tablaContactos,0); i < 5; i++)
     	       	
     	       	      dm.addRow(new Object[]{"","","","","","","",""}); //Se agrega una fila si existen menos de las que salen por defecto
     	       	                  
	 	   	     
	 	   	     
             } else
                
                limpiar(false);
           }

        } catch (Exception e) {
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }
   

    //*********************** Metodo guardar ************************

    private void guardar() throws Exception { 

  
        final String[] datos = new String[9];
        datos[0] = "null";
        datos[1] = "'" + jtNit.getText() + "'";
        datos[2] = "'" + jtNombre.getText() + "'";
        datos[3] = "'" + jtDireccion.getText() + "'";
        datos[4] = codigoDivisionPolitica.getText();
        datos[5] = "'" + jtTelefono.getText() + "'";
        datos[6] = "'" + jtFax.getText() + "'";
        datos[7] = "'" + jtPaginaWeb.getText() + "'";
        datos[8] = "'" + jtEmail.getText() + "'";
  
        //Se inserta en la base de datos
        guardar("Proveedores",datos,conectarMySQL,false); 
        
        
     }
     
     //**************************** Metodo guardarContactos ************************
     
     private void guardarContactos() throws Exception {
     	
     	//Se almacenan los contactos si existen
        int numeroFilas = obtenerFilas(tablaContactos,0);
        
    
     
       for (int i = 0; i < numeroFilas ; i++) {
         
                if (tablaContactos.getValueAt(i,6) != null && !tablaContactos.getValueAt(i,6).toString().isEmpty()) { //Se actuzaliza
                
                       String datosContactos1[] = new String[6]; //Se crea el array de los elementos a actualizar
                       
                        datosContactos1[0] =  "Nombre  = " + (tablaContactos.getValueAt(i,0) == null ? "''" : "'" + tablaContactos.getValueAt(i,0).toString() + "'"); 
                        datosContactos1[1] =  "Apellido  = " + (tablaContactos.getValueAt(i,1) == null ? "''" : "'" + tablaContactos.getValueAt(i,1).toString() + "'"); 
                        datosContactos1[2] =  "Telefono  = " + (tablaContactos.getValueAt(i,2) == null ? "''" : "'" + tablaContactos.getValueAt(i,2).toString() + "'"); 
                        datosContactos1[3] =  "Celular  = " + (tablaContactos.getValueAt(i,3) == null ? "''" : "'" + tablaContactos.getValueAt(i,3).toString() + "'"); 
                        datosContactos1[4] =  "Correo  = " + (tablaContactos.getValueAt(i,4) == null ? "''" : "'" + tablaContactos.getValueAt(i,4).toString() + "'"); 
                        datosContactos1[5] =  "Cargos  = " + (tablaContactos.getValueAt(i,5) == null ? "''" : "'" + tablaContactos.getValueAt(i,5).toString() + "'"); 
                       
                
                        actualizarContactos(tablaContactos.getValueAt(i,6).toString(),datosContactos1);
                
                   
                   } else   {
                   
                          String datosContactos[] = new String[8]; //Se crea el array de ls elementos a insertar
                          
                          
                          datosContactos[0] = "null"; //Se especifica para que se cree el numero con el autonumerico
                          datosContactos[1] = numeroProveedor.isEmpty() ? "(Select Max(NumeroProveedor) From Proveedores)" : numeroProveedor; //Se especifica la consulta

                          for (int j = 0; j < 6; j++) 
                          
                              datosContactos[j + 2] = tablaContactos.getValueAt(i,j) == null ? "''" : "'" + tablaContactos.getValueAt(i,j).toString() + "'";
                   
                          insertarContactos(datosContactos);
                   
                    }
                       
              
              }

        
        	
        }
	    
	 
     //**************************** Metodo insertarContactos ************************
     
     private void insertarContactos(String datosContactos[]) throws Exception {
     	
     	//Se inserta en la base de datos
        guardar("Contactos",datosContactos,conectarMySQL,false); 
        
        
     }
      
      
       //**************************** Metodo actualizar ************************

      private void actualizarContactos(String numerocontacto,String datosContactos[]) throws Exception { 
        
            String condicion = "NumeroContacto = '" + numerocontacto + "'";
            
            actualizar("Contactos",datosContactos,condicion,conectarMySQL,false);

      }

      //**************************** Metodo actualizar ************************

      private void actualizar() throws Exception { 


         final String[] datos = new String[8];

         datos[0] = "Nit = '" + jtNit.getText() + "'";
         datos[1] = "Direccion = '" + jtDireccion.getText() + "'";
         datos[2] = "RazonSocial = '" + jtNombre.getText() + "'";
         datos[3] = "Telefono = '" + jtTelefono.getText() + "'";
         datos[4] = "PaginaWeb = '" + jtPaginaWeb.getText() + "'";
         datos[5] = "Email = '" + jtEmail.getText() + "'";
         datos[6] = "DivisionPolitica = '" + codigoDivisionPolitica.getText() + "'";
         datos[7] = "Fax = '" + jtFax.getText() + "'";

        //Se especifica la condicion para actualizar
         String condicion = "NumeroProveedor = '"+ numeroProveedor  +"'";

         //Se actualiza en la base de datos
         actualizar("Proveedores",datos,condicion,conectarMySQL,false);

      }

      //**************************** Metodo eliminar ************************

      private void eliminar() throws Exception  { 

			int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar este Proveedor?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

		    if (opcion == 0) {

				 // Se especifica la condición de borrado 

				 String condicion = " NumeroProveedor = '" + numeroProveedor +"'";
				 
				 eliminarRegistro("Contactos",condicion,conectarMySQL,false); 
				 
				 eliminarRegistro("Proveedores",condicion,conectarMySQL,false); 
				 
				  

			}

	  }
	  
	  
	   //**************************** Metodo eliminarContacto ************************

	  private void eliminarContacto() throws Exception  { 

			int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar este contacto?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

		    if (opcion == 0) {
		    	
		    	int fila = tablaContactos.getSelectedRow();

				 // Se especifica la condición de borrado 

				 String condicion = "NumeroContacto = '" + tablaContactos.getValueAt(fila,6) +"'";

				 eliminarRegistro("Contactos",condicion,conectarMySQL,false); 
				 
				 
				 dm.removeRow(fila); // Se borra cuando el numero de filas es mayor a las que se muestra inicialmente
     	       	   
     	       	if (tablaContactos.getRowCount() < 4) 
     	       	    
     	       	    for (int i = obtenerFilas(tablaContactos,0); i < 5; i++)
     	       	
     	       	      dm.addRow(new Object[]{"","","","","","","",""}); //Se agrega una fila si existen menos de las que salen por defecto
     	       	      
     	      
			}

	  }
	 
	  //**************************** Metodo esActualizable ************************

      private Boolean esActualizable(String idMarca, String Marca)  { 

           Boolean sw = new Boolean(false);
           
           int i = 0;
           
           
           while (sw != null && !sw && (i < datosTablaContactos.size())) {
           
           	     Vector columna = (Vector) datosTablaContactos.elementAt(i);
           
           	     if (columna.elementAt(0) != null) {
           	    	
           	     	if (columna.elementAt(0).equals(idMarca) && columna.elementAt(1).equals(Marca)) 
           	     	
           	     	  sw = null;
           	     	  
           	     	else   
           	     	  
           	     	  if (columna.elementAt(0).equals(idMarca) && !columna.elementAt(1).equals(Marca))
           	     	
           	     	    sw = true;
           	     } 
           	
           	    i++;     
           	
           } 
           
           
           return sw;               

	  }
	  
	  
	  
	  //**************************** Metodo esBorrable ************************

      private boolean esBorrable(String idMarca)  { 

           boolean sw = false;
           
           int i = 0;
           
           
           while (!sw && (i < datosTablaContactos.size())) {
           
           	     Vector columna = (Vector) datosTablaContactos.elementAt(i);
           
           	     if (columna.elementAt(0) != null && columna.elementAt(0) != null) {
           	    	
           	     	if (columna.elementAt(0).equals(idMarca)) 
           	     	
           	     	  sw = true;
           	     
           	     } else
           	       
           	        i =  datosTablaContactos.size();
           	
           	    
           	     i++;     
           	
           } 
           
           
           return sw;               

	  }

      
      //**************************** Metodo configurarColumnas() ************************
 
      private void configurarColumnas() {
 	   
 	    // Se configuran los tamaños de las columnas
 		tablaContactos.getColumnModel().getColumn(0).setPreferredWidth(105);
 		tablaContactos.getColumnModel().getColumn(1).setPreferredWidth(105);
 		tablaContactos.getColumnModel().getColumn(2).setPreferredWidth(30);
 	    tablaContactos.getColumnModel().getColumn(2).setCellRenderer(getAlinearCentro());
 		tablaContactos.getColumnModel().getColumn(3).setPreferredWidth(30);
 		tablaContactos.getColumnModel().getColumn(3).setCellRenderer(getAlinearCentro());
 		tablaContactos.getColumnModel().getColumn(4).setPreferredWidth(70);
 		
 		ocultarColumnas(tablaContactos,4);
 		ocultarColumnas(tablaContactos,6);
 		
 	   
 	 }   
     
     //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

         // Se verifica el componente que genero la acccion

         if (fuente==Blimpiar) {

            limpiar(true);

         } else 
            if (fuente == Bguardar) {

                  //Se valida la información antes de guardar 
                  if (validarRegistro(camposObligatorios)) {
                    
                    
                      try {

                          String mensaje = "";

                          if (conectarMySQL.validarRegistro ("Proveedores",
                                                             "Nit ='" + jtNit.getText() + "' or RazonSocial = '" + jtNombre.getText() + "'" )) {

                             actualizar(); 

                             mensaje = "Proveedor actualizado correctamente";

                          }  else {

                             guardar(); 

                             mensaje = "Información de Proveedor registrada correctamente";

                          } 

                          guardarContactos(); //Se guardan los contactos
                          
                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 

                          Mensaje(mensaje,"Información",1);

	                       // Se verifica quien hizo el llamado a la forma de personas
				           if (framePadre != null && framePadre.getName().equals("Compra")) {
				           	
				           	    ocultarJFrame();
				           	
				           } else
				               
			                 limpiar(true); //Se limpia la forma 
                         

                       } catch (Exception e) {

                           conectarMySQL.rollback();

                           Mensaje("Error al Insertar Proveedor" +e,"Error",0); 

				      }

                   }

            } else 

                if (fuente==Beliminar) { 

                   try {
                   	
                   	 if (!jtNit.getText().isEmpty() || !jtNombre.getText().isEmpty()) {
                   	 
                       eliminar(); 

                       conectarMySQL.commit();   //Se registra los cambios en la base de dato 

                       Mensaje("Proveedor Eliminado","Información",JOptionPane.INFORMATION_MESSAGE);

                       limpiar(true);  // Se limpia la forma 
                       
                   } else {
                   	   
                   	   Mensaje("Se debe especificar el Nit o Razon social de la empresa","Información",1);
                   	   jtNit.grabFocus();
                   	
                   }    

                 } catch (Exception e) {

                       conectarMySQL.rollback();

                       Mensaje("No se puede eliminar Proveedor porque se encuentra refenciado" + e,"Error",JOptionPane.ERROR_MESSAGE); 

                  }

                }  else 

                    if (fuente == Bbuscar) { 

                       buscarRegistro(false);

                    } else 

                       if (fuente == Bsalir) {

                         ocultarJFrame(); 

                       } else
                          
                          if (fuente == jbAgregar) {

                              configurarJDialogContacto();
                              jtNombresContacto.grabFocus();    //Se coloca el foco en el primer componente Focusable
    	                      dialogContacto.setVisible(true);
    	  

                          } else
                           
                            if (fuente == jbCancelarContacto) {
                                
                                
                                jbEditar.setFocusable(true);
                                 		
                                dialogContacto.setVisible(false); 

                            } else
                           
                               if (fuente == jbAceptarContacto) {
                                 
                                    if (jbEditar.isFocusable())
                                     
                                      agregarFilaJTable();
                                    
                                    else {
                                    	
                                    	int fila = tablaContactos.getSelectedRow();
		                                tablaContactos.setValueAt(jtNombresContacto.getText(),fila,0);
		                                tablaContactos.setValueAt(jtApellidosContacto.getText(),fila,1);
		                                tablaContactos.setValueAt(jtTelefonoContacto.getText(),fila,2);
		                                tablaContactos.setValueAt(jtCelularContacto.getText(),fila,3);
		                                tablaContactos.setValueAt(jtEmailContacto.getText(),fila,4);
		                                tablaContactos.setValueAt(jtCargoContacto.getText(),fila,5);
		                                      
                                    } 
                                     
                                     
                                     dialogContacto.setVisible(false); 

                              } else
                                
                                if (fuente == jbEliminar) {
                                	
	                                  int fila = tablaContactos.getSelectedRow();
	                                  
	                                  if (fila != -1 && tablaContactos.getValueAt(fila,0) != null)
	                                  
	                                      try {
	                                  
	                                  	    eliminarContacto();
	                                  	    
	                                  	    conectarMySQL.commit();      //Se registra los cambios en la base de datos 
	                                  	   
	                                  	  } catch (Exception e) {

						                       conectarMySQL.rollback();
						
						                       Mensaje("No se puede elimina el contacto" + e,"Error",JOptionPane.ERROR_MESSAGE); 
						
						                   }  
	                                  	    
	                                  else
	                                  
	                                     Mensaje("Se debe escoger una fila","Información",2);
                                  	    
                                } else
                                
                                   if (fuente == jbEditar) {
                                	
                                	  if (jtNombresContacto == null)
                                 		
                                 		  configurarJDialogContacto();  //Se instancia el JDialog y se configura
                                 		  
                                 		  
                                 		int fila = tablaContactos.getSelectedRow();  
                                 		
                                 		jtNombresContacto.setText(tablaContactos.getValueAt(fila,0).toString());
                                 		jtApellidosContacto.setText(tablaContactos.getValueAt(fila,1).toString());
                                 		jtTelefonoContacto.setText(tablaContactos.getValueAt(fila,2).toString());
                                 		jtCelularContacto.setText(tablaContactos.getValueAt(fila,3).toString());
                                 		jtEmailContacto.setText(tablaContactos.getValueAt(fila,4).toString());
                                        jtCargoContacto.setText(tablaContactos.getValueAt(fila,5).toString());
                                 		
                                 		
                                 	
                                 		jtNombresContacto.selectAll();
                                 	    jtApellidosContacto.selectAll();
                                 		jtTelefonoContacto.selectAll();
                                 		jtCelularContacto.selectAll();
                                 		jtEmailContacto.selectAll();
                                        jtCargoContacto.selectAll();
                                 		jtNombresContacto.grabFocus();
                                 		
                                 		jbEditar.setFocusable(false);
                                 		
                                 		dialogContacto.setVisible(true); 
                                	
                                   } else
                 	    
				                 	      if (fuente == jcDepartamento) {
				                     
				                              jtCiudadMunicipio.setText(departamentos [jcDepartamento.getSelectedIndex()][3]);
				                              codigoDivisionPolitica.setText(departamentos [jcDepartamento.getSelectedIndex()][2]);
				                              jtCiudadMunicipio.selectAll();
				      
				                          } else
                             
				                             if (fuente == jtCiudadMunicipio)  {
									 	             	     				
						 	     				 if (getJTableListaValores().isVisible()) {
																			 		   	  	     
									 		   	  	      if (getJTableListaValores().getSelectedRow() > -1)
									 		   	  	  	     
									 		   	  	  	      getJTableListaValores().setRowSelectionInterval(getJTableListaValores().getSelectedRow(),getJTableListaValores().getSelectedRow());
									 		   	  	  	  
									 		   	  	  	  else
									 		   	  	  	  
									 		   	  	  	     getJTableListaValores().setRowSelectionInterval(0,0);
									 		   	  	  	     
									 		   	  	  	     
									 		   	  	  	 getJTableListaValores().grabFocus();
									 		   	  	  
									 		   	  	  }	else
									 		   	  	  	 
									 		   	  	  	 jtTelefono.grabFocus();
									 		}   	  	 
				                       

      }

     //**************************** Metodo focusGained ************************

     public void focusGained(FocusEvent f) { 

		if (f.getComponent() == jtCiudadMunicipio && (getDialogoListaValores() == null || !getDialogoListaValores().isVisible()) && jtCiudadMunicipio.isFocusable()) {
	            	
	        	 listaValor.mostrarListaValores();
	        	 
	        	 if (getJTableListaValores().getColumnModel().getColumn(0).getPreferredWidth() != 60) {
		     
				     getJTableListaValores().getColumnModel().getColumn(0).setPreferredWidth(60);
		             getJTableListaValores().getColumnModel().getColumn(1).setPreferredWidth(90);
		             getJTableListaValores().getColumnModel().getColumn(2).setPreferredWidth(230);
		             
                }	      
             
		
			} else {
			
				 if (f.getComponent() != jtCiudadMunicipio  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
	                
	               if (getJTableListaValores().getRowCount() > 0) {
	               
	                 codigoDivisionPolitica.setText(getJTableListaValores().getValueAt(0,0).toString());
	                 jtCiudadMunicipio.setText(getJTableListaValores().getValueAt(0,3).toString());
	             
	                 getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
	                 jtCiudadMunicipio.selectAll();
	                
	                
	                } else {
	            	    
	            	    jtCiudadMunicipio.setText("SINCELEJO");
		            	listaValor.mostrarListaValores();
		            	jtCiudadMunicipio.grabFocus();
		            
		             }      
				 }
	
			      // se coloca el atributo visual por defecto
			      f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
			  }  
			  
			  
	      if (f.getComponent() != jtCiudadMunicipio && f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField"))
			          
			   ((JTextField)f.getComponent()).selectAll();		      


     }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 


          if (f.getSource() == jtEmailContacto && !jtEmailContacto.getText().isEmpty()) {
          	
          	  if (!esEmail(jtEmailContacto.getText())) {
          	  	  
          	  	  Mensaje("Correo "+ jtEmailContacto.getText() +" invalido","Información",JOptionPane.ERROR_MESSAGE);
          	  	  jtEmailContacto.setText("");
          	  	  jtEmailContacto.grabFocus();
          	  	
          	  }
         } else
         
            if (f.getSource() == jtEmail && !jtEmail.getText().isEmpty()) {
          	
          	  if (!esEmail(jtEmail.getText())) {
          	  	  
          	  	  Mensaje("Correo "+ jtEmail.getText() +" invalido","Información",JOptionPane.ERROR_MESSAGE);
          	  	  jtEmail.setText("");
          	  	  jtEmail.grabFocus();
          	  	
          	  }
          } else
             
               if (f.getSource() == jtNit && !jtNit.getText().isEmpty()) {
          
                   buscarRegistro(true);
                    	   	   
               } else
               
                 if (f.getSource() == jtNombre)
                 
                  buscarRegistro(false);
                  
                 else
                 
                 if (f.getSource() == jtPaginaWeb & !jtPaginaWeb.getText().isEmpty()) {
                 	
                 	if (!esPaginaWeb(jtPaginaWeb.getText())) {
          	  	  
		          	  	  Mensaje("Pagina Web "+ jtPaginaWeb.getText() +" invalida","Información",JOptionPane.ERROR_MESSAGE);
		          	  	  jtPaginaWeb.setText("");
		          	  	  jtPaginaWeb.grabFocus();
		          	  	
		          	  }
                 }
                 
                   
				   

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

     
}