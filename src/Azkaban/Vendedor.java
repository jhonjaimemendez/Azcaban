import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.KeyboardFocusManager;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.sql.ResultSet;

import java.util.Vector;

import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.SortableTableModel;
import com.JASoft.componentes.ListaValor;


final public class Vendedor extends CrearJFrame implements ActionListener, FocusListener {

    //** Referencia a la Base De Datos
    private ConectarMySQL conectarMySQL;

    //** Array para campos obligatorios
    private JTextField[] camposObligatorios;

    //** Se declaran los JPanel
    private  JPanel panelIdentificacion;
    private  JPanel panelApellidosNombres;
    private  JPanel panelDatosContacto;
    private  JPanel panelCredito;

    //** Se declaran los JTextField
    private  JTextField jtNumeroId;
    private  JTextField jtDireccion;
    private  JTextField jtNombres;
    private  JTextField jtTelefono;
    private  JTextField jtCelular;
    private  JTextField jtEmail;
    private  JTextField jtUsuario;
    private  JTextField jtApellidos;
    private  JTextField jtCiudadMunicipio;
    
    //Casillas de texto para password
    private JPasswordField jtPassword;
  	private JPasswordField jtConfirmarPasword;
 	

    //** Se declaran los JComboBox
    private  JComboBox jcTipoId;
    private  JComboBox jcSexo;
    private  JComboBox jcDepartamento;
    
    //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
    
    //** Se declaran los JLabel 
  	private JLabel codigoDivisionPolitica  = new JLabel(getCodigoDivisionPolitica(conectarMySQL)); //Componente que se envia como parametro a
  	
    //** Constructor General 
    public Vendedor(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super("Vendedores","Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;


      // Se instancias colores comunes por eficiencia
      final Color color0 = new Color(198, 0, 0);
      //** Se agregan los JPanel

      panelIdentificacion = getJPanel("Identificación",265, 75, 260, 75,14);

      panelApellidosNombres = getJPanel("Datos Personales",60, 165, 670, 210,14);

      panelDatosContacto = getJPanel("Datos de Contacto",60, 390, 315, 125,14);

      panelCredito = getJPanel("Identificación y Contraseña",415, 390, 315, 125,14);

      //** Se declaran los JLabel

      JLabel jlTipoId = getJLabel("Tipo",25, 25, 60, 15);
      panelIdentificacion.add(jlTipoId);

      JLabel jlNumeroId = getJLabel("Número",135, 25, 75, 15);
      panelIdentificacion.add(jlNumeroId);

      JLabel jlNombres = getJLabel("Nombres",35, 25, 65, 15);
      panelApellidosNombres.add(jlNombres);

      JLabel jlDireccion = getJLabel("Dirección:",155, 108, 120, 15);
      jlDireccion.setHorizontalAlignment(SwingConstants.RIGHT);
      panelApellidosNombres.add(jlDireccion);

      JLabel jlApellidos = getJLabel("Apellidos",240, 25, 95, 15);
      panelApellidosNombres.add(jlApellidos);

      JLabel jlTelefono = getJLabel("Teléfono:",20, 35, 55, 15);
      jlTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlTelefono);

      JLabel jlCelular = getJLabel("Celular:",20, 63, 55, 15);
      jlCelular.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlCelular);

      JLabel jlEmail = getJLabel("e-Mail:",20, 90, 55, 15);
      jlEmail.setHorizontalAlignment(SwingConstants.RIGHT);
      panelDatosContacto.add(jlEmail);

      JLabel jlCupo = getJLabel("Usuario:",10, 33, 145, 15);
      jlCupo.setHorizontalTextPosition(SwingConstants.LEFT);
      jlCupo.setHorizontalAlignment(SwingConstants.RIGHT);
      panelCredito.add(jlCupo);

      JLabel jlSaldo = getJLabel("Contraseña:",10, 60, 145, 15);
      jlSaldo.setHorizontalAlignment(SwingConstants.RIGHT);
      panelCredito.add(jlSaldo);

      JLabel jlSexo = getJLabel("Sexo",530, 25, 75, 15);
      panelApellidosNombres.add(jlSexo);

      JLabel jlCiudadMunicipio = getJLabel("Ciudad / Municipio:",155, 173, 120, 15);
      jlCiudadMunicipio.setHorizontalAlignment(SwingConstants.RIGHT);
      panelApellidosNombres.add(jlCiudadMunicipio);

      JLabel jlDepartamento = getJLabel("Departamento:",155, 141, 120, 15);
      jlDepartamento.setHorizontalAlignment(SwingConstants.RIGHT);
      panelApellidosNombres.add(jlDepartamento);

      JLabel jlSaldo1 = getJLabel("Verificar Contraseña:",10, 88, 145, 15);
      jlSaldo1.setHorizontalAlignment(SwingConstants.RIGHT);
      panelCredito.add(jlSaldo1);

      //** Se instancian los JSeparator

      JSeparator jSeparator1 = new JSeparator();
      jSeparator1.setBounds(105, 85, 470, 2);
      jSeparator1.setForeground(Color.lightGray);
      panelApellidosNombres.add(jSeparator1);

      //** Se agregan los JTextField

      jtNumeroId = getJTextField("",135, 40, 105, 20,"Digite el Número de Identificación del Vendedor","12");
      jtNumeroId.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroId.addFocusListener(this);
      jtNumeroId.addKeyListener(getValidarEntradaNumeroJTextField());
      panelIdentificacion.add(jtNumeroId);

      jtDireccion = getJTextField("",295, 105, 215, 20,"Digite la Dirección del Vendedor","40");
      jtDireccion.addFocusListener(this);
      jtDireccion.addKeyListener(getConvertirMayuscula());
      panelApellidosNombres.add(jtDireccion);

      jtNombres = getJTextField("",35, 40, 195, 20,"Digite Nombres del Vendedor","40");
      jtNombres.addFocusListener(this);
      jtNombres.addKeyListener(getConvertirMayuscula());
      panelApellidosNombres.add(jtNombres);

      jtTelefono = getJTextField("",90, 32, 95, 20,"Digite el Número de Teléfono Fijo del Vendedor","12");
      jtTelefono.setHorizontalAlignment(JTextField.RIGHT);
      jtTelefono.addFocusListener(this);
      jtTelefono.addKeyListener(getValidarEntradaNumeroJTextField());
      panelDatosContacto.add(jtTelefono);

      jtCelular = getJTextField("",90, 60, 95, 20,"Digite el Número de Teléfono Celular del Vendedor","12");
      jtCelular.setHorizontalAlignment(JTextField.RIGHT);
      jtCelular.addFocusListener(this);
      jtCelular.addKeyListener(getValidarEntradaNumeroJTextField());
      panelDatosContacto.add(jtCelular);

      jtEmail = getJTextField("",90, 87, 200, 20,"Digite el Correo Electrónico del Vendedor","40");
      jtEmail.addFocusListener(this);
      panelDatosContacto.add(jtEmail);

      jtUsuario = getJTextField("",175, 30, 100, 20,"Digite la Identificacion del Vendedor en el Sistema","40");
      jtUsuario.setForeground(new Color(0, 99, 0));
      jtUsuario.setHorizontalAlignment(JTextField.RIGHT);
      jtUsuario.addFocusListener(this);
      panelCredito.add(jtUsuario);

      jtPassword = getJPasswordField("Digite la Contraseña",175, 57, 100, 21,"40");
      jtPassword.setForeground(color0);
      jtPassword.setHorizontalAlignment(JTextField.RIGHT);
      jtPassword.setDragEnabled(true);
      jtPassword.addFocusListener(this);
      panelCredito.add(jtPassword);

      jtApellidos = getJTextField("",240, 40, 280, 20,"Digite Apellidos del Vendedor","40");
      jtApellidos.addKeyListener(getConvertirMayuscula());
      jtApellidos.addFocusListener(this);
      panelApellidosNombres.add(jtApellidos);


      jtConfirmarPasword = getJPasswordField("Repita la Contraseña",175, 85, 100, 20,"");
      jtConfirmarPasword.setForeground(color0);
      jtConfirmarPasword.setHorizontalAlignment(JTextField.RIGHT);
      jtConfirmarPasword.setDragEnabled(true);
      jtConfirmarPasword.addFocusListener(this);
      panelCredito.add(jtConfirmarPasword);

      //** Se agregan los JComboBox

      jcTipoId = getJComboBox(25, 40, 105, 20,"Selecione el Tipo de Identificación del Vendedor");
      jcTipoId.addItem("Cédula");
      jcTipoId.addItem("Tarjeta Id.");
      jcTipoId.addItem("Pasaporte");
      jcTipoId.addFocusListener(this);
      panelIdentificacion.add(jcTipoId);

      panelIdentificacion.add(jcTipoId);

      jcSexo = getJComboBox(530, 40, 115, 20,"Seleccione el Sexo del Vendedor");
      jcSexo.addItem("Masculino");
      jcSexo.addItem("Femenino");
      panelApellidosNombres.add(jcSexo);

      jcDepartamento = getJComboBox(295, 138, 215, 20,"Seleccione el Departamento");
      jcDepartamento.setMaximumRowCount(15);
	  traerDepatamentos(jcDepartamento,conectarMySQL); // Se traen los departamentos
	  jcDepartamento.setSelectedItem(getDptoDivisionPolitica(conectarMySQL));
	  jcDepartamento.addActionListener(this);
	  jcDepartamento.addFocusListener(this);
      panelApellidosNombres.add(jcDepartamento);
      
      jtCiudadMunicipio = getJTextField(getMunicipioDivisionPolitica(conectarMySQL),295, 170, 215, 20,"Digite la Ciudad / Municipio o Presione F9","");
      jtCiudadMunicipio.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtCiudadMunicipio,jcDepartamento,conectarMySQL,getOcultarColumnas(),480);
 
      jtCiudadMunicipio.addFocusListener(this);
      jtCiudadMunicipio.addActionListener(this);
      jtCiudadMunicipio.addKeyListener(listaValor);
      
      jtCiudadMunicipio.addFocusListener(this);
      panelApellidosNombres.add(jtCiudadMunicipio);


      //** Se configura el icono del frame

      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Vendedor.gif")).getImage());

      //** Se muestra el JFrame
      mostrarJFrame(); 

      jtNumeroId.grabFocus();    //Se coloca el foco en el primer componente Focusable

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

     //******************************** Metodo crearArrayCamposObligatorios *******************

     public void crearArrayCamposObligatorios() { 

           //Se instancia un array de JTextField 
           camposObligatorios = new JTextField[4];

           camposObligatorios[0] = jtNumeroId;
           camposObligatorios[1] = jtDireccion;
           camposObligatorios[2] = jtNombres;
           camposObligatorios[3] = jtUsuario;

     }

    //*********************** Metodo limpiar ************************

    private void limpiar(boolean dummy) { 
      
      if (dummy) {
      
	      jcTipoId.setSelectedIndex(0);
	      jtNumeroId.setText("");
	      
	      jtNumeroId.grabFocus();    //Se coloca el foco en el primer componente Focusable
	      
      }
      jtDireccion.setText("");
      jtNombres.setText("");
      jtTelefono.setText("");
      jtCelular.setText("");
      jtEmail.setText("");
      jtUsuario.setText("");
      jtPassword.setText("");
      jtApellidos.setText("");
      jtCiudadMunicipio.setText("");
      jtConfirmarPasword.setText("");
      
      jcDepartamento.setSelectedItem("SUCRE");
      jtCiudadMunicipio.setText(getMunicipioDivisionPolitica(conectarMySQL));
      
      
      codigoDivisionPolitica.setText(getCodigoDivisionPolitica(conectarMySQL)); //Se configura la division politica
 	  
      

     

    }

   //*********************** Metodo buscarRegistro ************************

    private void buscarRegistro() { 

        final String sentenciaSQL = "Select V.*,S.password "+
                                    "From   Vendedores V, Usuarios S "+
                                    "Where V.ID = S.ID and TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +"' and idVendedor = '" + jtNumeroId.getText()  +"'";

        try {

           // Se llama el metodo buscarRegistro de la clase ConectarMySQL
           ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);

           // Se verifica si tiene datos 
           if (resultado!=null)	{ 

             if (resultado.next()) { 

                 jcTipoId.setSelectedIndex(resultado.getString(1).equals("C") ? 0 : resultado.getString(1).equals("T") ? 1 : 2 );
                 jtNumeroId.setText(resultado.getString(2));
				 jtNombres.setText(resultado.getString(3));
				 jtApellidos.setText(resultado.getString(4));
				 jtDireccion.setText(resultado.getString(5));
				 jtTelefono.setText(resultado.getString(6));
				 jtCelular.setText(resultado.getString(7));
				 jtEmail.setText(resultado.getString(8));
				 jtUsuario.setText(resultado.getString(9));
				 jcSexo.setSelectedIndex(resultado.getString(10).equals("M") ? 0 : 1 );
                 codigoDivisionPolitica.setText(resultado.getString(11)); //Se asigna la division politica
	             jtPassword.setText(resultado.getString(12));
				 jtConfirmarPasword.setText(resultado.getString(12));
				 
				 Vector municipios =   buscarMunicipio(conectarMySQL,codigoDivisionPolitica.getText()); //Se busca el nombre del departamento y municipio o corregimiento
	 	   	     jcDepartamento.setSelectedItem(municipios.elementAt(0));
	 	   	     jtCiudadMunicipio.setText(municipios.elementAt(1).toString());
                 

             } else
                
                limpiar(false);
             
           }

        } catch (Exception e) {
        	Mensaje("Error "+e,"Información",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
     //**************************** Metodo buscarRegistroCliente ************************

	 private boolean buscarRegistroCliente(){
 	         
 	         boolean resultadoBoolean = false;
 	         
 	         final String sentenciaSQL = "Select 'x' "+
 	                                     "From Usuarios " +
 	                                     "Where ID = '" +  jtUsuario.getText()+ "'";
 	                                     
 	         
 	         try {
	 	     	
	 	     	     ResultSet resultado = conectarMySQL.buscarRegistro(sentenciaSQL);
	 	                      
                     if (resultado.next()) 
                         
                          resultadoBoolean = true;
                          
	 	     }  catch (Exception e) {
	 	                          
	 	          Mensaje("No existe ningun registro en la usuarios ","Reporte de Error", 0);        
	 	     
	 	     }
	 	                 
	         return resultadoBoolean;
     
 	  }


    //*********************** Metodo guardar ************************

    private void guardar() throws Exception { 
        

        String[] datos = new String[11];
        
        datos[0] = "'" + jcTipoId.getSelectedItem().toString().charAt(0) + "'";
        datos[1] = "'" + jtNumeroId.getText() + "'";
        datos[2] = "'" + jtNombres.getText() + "'";
        datos[3] = "'" + jtApellidos.getText() + "'";
        datos[4] = "'" + jtDireccion.getText() + "'";
        datos[5] = "'" + jtTelefono.getText() + "'";
        datos[6] = "'" + jtCelular.getText() + "'";
        datos[7] = "'" + jtEmail.getText() + "'";
        datos[8] = "'" + jtUsuario.getText() + "'";
        datos[9] =  "'" + jcSexo.getSelectedItem().toString().charAt(0) + "'";
        datos[10] =  codigoDivisionPolitica.getText();
       
        

        //Se inserta en la base de datos
        guardar("Vendedores",datos,conectarMySQL,false); 
         
        datos = new String[3];
        
        datos[0] = "'" + jtUsuario.getText() + "'";
        datos[1] = "'" + jtPassword.getText() + "'";
        datos[2] = "0";
        
        //Se inserta en la base de datos
        guardar("Usuarios",datos,conectarMySQL,false);

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
        objetosRetorno[0][1] = jtTelefono;
       	objetosRetorno[0][2] = "3";
       	objetosRetorno[0][3] = codigoDivisionPolitica;
       	objetosRetorno[0][4] = "0";
       	
		return objetosRetorno;

	}
	
	//******************************** Metodo getComponentesRetorno()  ***************************************

	final private int[] getOcultarColumnas() {

		int[] columnasOcultar = new int[1];
        
       	columnasOcultar[0] = 3;
       	
		return columnasOcultar;

	}

      //**************************** Metodo actualizar ************************

      private void actualizar() throws Exception { 
         
         String[] datos = new String[1];
         datos[0] = "Password = '" + jtPassword.getText() + "'";
         
         //Se especifica la condicion para actualizar
         String condicion = "ID = '" +  jtUsuario.getText() +"'";
         
         //Se actualiza en la base de datos
         actualizar("Usuarios",datos,condicion,conectarMySQL,false);


         datos = new String[8];

         datos[0] = "direccion = '" + jtDireccion.getText() + "'";
         datos[1] = "Nombre = '" + jtNombres.getText() + "'";
         datos[2] = "Telefono = '" + jtTelefono.getText() + "'";
         datos[3] = "Celular = '" + jtCelular.getText() + "'";
         datos[4] = "Correo = '" + jtEmail.getText() + "'";
         datos[5] = "ID = '" + jtUsuario.getText() + "'";
         datos[6] = "DivisionPolitica = " + codigoDivisionPolitica.getText();
         datos[7] = "Apellido = '" + jtApellidos.getText() + "'";


         //Se especifica la condicion para actualizar
         condicion = "TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +"' and  idVendedor = '" + jtNumeroId.getText()  +"'";

         //Se actualiza en la base de datos
         actualizar("Vendedores",datos,condicion,conectarMySQL,false);

      }

      //**************************** Metodo eliminar ************************

      private void eliminar() throws Exception  { 

			int opcion = JOptionPane.showConfirmDialog(getJFrame(),"Desea Eliminar este Vendedor?","Confirmación",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

		    if (opcion == 0) {

				 // Se especifica la condición de borrado 

				 String condicion = "IdVendedor = '" + jtNumeroId.getText() + "'";

				 eliminarRegistro("Vendedores",condicion,conectarMySQL,false); 

			}

	  }

      //**************************** Metodo actionPerfomed ************************

     public void actionPerformed(ActionEvent a) { 

          Object fuente = a.getSource(); 

         // Se verifica el componente que genero la acccion

         if (fuente == Blimpiar) {

            limpiar(true);

         } else 
            if (fuente == Bguardar) {

                  //Se valida la información antes de guardar 
                  if (validarRegistro(camposObligatorios)) {
                  	
	                  String password = new String(jtPassword.getPassword());
				 	  String cpassword = new String(jtConfirmarPasword.getPassword());
				 	  System.out.println(password + "  "+cpassword);
					 		      
 	 		  	   	  if (password.equals(cpassword)) {  
 	 		  	   	 
		
		                      try {
		
		                          String mensaje = "";
		
		                          if (conectarMySQL.validarRegistro ("Vendedores",
		                                                             "TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +"' and  idVendedor = '" + jtNumeroId.getText()  +"'")) {
                                                        
		
		                             actualizar(); 
		
		                             mensaje = "Vendedor actualizado correctamente";
		
		                          }  else {
		
		                             guardar(); 
		
		                             mensaje = "Información de Vendedor registrada correctamente";
		
		                          } 
		
		
		                          conectarMySQL.commit();      //Se registra los cambios en la base de datos 
		
		                          Mensaje(mensaje,"Información",1);
		
		                          limpiar(true);   //Se limpia la forma 
		
		                       } catch (Exception e) {
		
		                           conectarMySQL.rollback();
		
		                           Mensaje("Error al Insertar Usuario" +e,"Error",0); 
		
						      }
						      
						} else
 		  	   	             
 		  	   	             Mensaje("Confirmación de clave erronea","Error",0);
 		  	      

                   }

            } else 

                if (fuente == Beliminar) { 

                   try {

                       eliminar(); 

                       conectarMySQL.commit();   //Se registra los cambios en la base de dato 

                       Mensaje("Vendedor Eliminado","Información",JOptionPane.INFORMATION_MESSAGE);

                       limpiar(true);  // Se limpia la forma 

                 } catch (Exception e) {

                       conectarMySQL.rollback();

                       Mensaje("No se puede eliminar Vendedor" + e,"Error",JOptionPane.ERROR_MESSAGE); 

                  }

                }  else 

                    if (fuente == Bbuscar) { 

                       buscarRegistro();

                    } else 

                       if (fuente == Bsalir) {

                         ocultarJFrame(); 

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
	
		} else 
		
			 if (f.getComponent() != jtCiudadMunicipio  && getDialogoListaValores() != null && getDialogoListaValores().isVisible()) { //Se oculta el scroll de la lista de valores
                     
                  codigoDivisionPolitica.setText(getJTableListaValores().getValueAt(0,0).toString());
	              jtCiudadMunicipio.setText(getJTableListaValores().getValueAt(0,3).toString());
	             
	              getDialogoListaValores().setVisible(false); //Se oculta automaticamente la lista de valores
                  jtCiudadMunicipio.selectAll();
		
			 }


       // se coloca el atributo visual por defecto
       f.getComponent().setBackground(getVisualAtributoGanaFocoComponentes());
       
       if (f.getComponent() != jtCiudadMunicipio && f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField"))
			          
			   ((JTextField)f.getComponent()).selectAll();
       


	  }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
        
        if (f.getSource() == jtNumeroId && !jtNumeroId.getText().isEmpty())
            
            if (!jtNumeroId.getText().equals("1"))
            
              buscarRegistro();
              
            else {
              
              Mensaje("Numero de identificación protegido por el sistema","Información",1);  
              limpiar(true);
              
            }
              
        else      
          
          if (f.getSource() == jtEmail && !jtEmail.getText().isEmpty()) {
          	
          	  if (!esEmail(jtEmail.getText())) {
          	  	  
          	  	  Mensaje("Correo "+ jtEmail.getText() +" invalido","Información",JOptionPane.ERROR_MESSAGE);
          	  	  jtEmail.setText("");
          	  	  jtEmail.grabFocus();
          	  	  
          	  	
          	  }
          	
          	
          } else
             
             if (f.getSource() == jtUsuario && !jtUsuario.getText().isEmpty()) {
                 
                 if (buscarRegistroCliente()) {
                 	
                 	Mensaje("Nombre de Usuario ya ha sido registrado","Información",1);
                 	jtUsuario.setText("");
                 	jtUsuario.grabFocus();
                 }
             
             }
              
      

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }
}