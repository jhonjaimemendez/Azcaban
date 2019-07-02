package com.JASoft.azkaban;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.KeyboardFocusManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
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
import javax.swing.ImageIcon;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;


import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;

import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.ListaValor;


import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

import java.util.Vector;


import java.sql.ResultSet;

final public class Cliente extends CrearJFrame implements ActionListener, FocusListener {

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
    private  JTextField jtCupo;
    private  JTextField jtSaldo;
    private  JTextField jtApellidos;
    private  JTextField jtCiudadMunicipio;
    private  JTextField jtBono;

    //** Se declaran los JComboBox
    private  JComboBox jcTipoId;
    private  JComboBox jcSexo;
    private  JComboBox jcDepartamento;
    
    
    //** Se declaran los JLabel 
  	private JLabel codigoDivisionPolitica  = new JLabel(getCodigoDivisionPolitica(conectarMySQL)); //Componente que se envia como parametro a
  	 
    //** Clase para mostrar una lista de valores
    private ListaValor listaValor; 
    
    //**Se declaran los JFrame
    private JFrame framePadre; // Utilizado para el frame padre en la tabla Ventas
     
    //** Constructor General 
    public Cliente(ConectarMySQL p_conectarMySQL,JFrame p_frame) {

      super( "Cliente" ,"Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
      iniciarGUI();
    
    }   
    
    
    //** Constructor utiliza en Ventas 
    public Cliente(ConectarMySQL p_conectarMySQL,JFrame p_frame,String tipoIdentificacion, String identificacion) {

      super( "Cliente" ,"Toolbar",p_frame,"dummy");

      conectarMySQL = p_conectarMySQL;
      
      framePadre = p_frame;
      
      iniciarGUI();
      
      jcTipoId.setSelectedItem(tipoIdentificacion);
      
      jtNumeroId.setText(identificacion);
      
      jtNumeroId.grabFocus();
      
      buscarRegistro();
      
      
      
    
    }
    
    
    //** Constructor utiliza en Ventas 
    public Cliente(ConectarMySQL p_conectarMySQL,JFrame p_frame,int tipoIdentificacion, String identificacion) {

      super( "Cliente" ,"Toolbar",p_frame);

      conectarMySQL = p_conectarMySQL;
      
      framePadre = p_frame;
      
      iniciarGUI();
      
      jcTipoId.setSelectedIndex(tipoIdentificacion);
      
      jtNumeroId.setText(identificacion);
     
      jtNombres.grabFocus();
      
    
    }
   
   
   
    public void iniciarGUI(){
     
      //** Se agregan los JPanel

      panelIdentificacion = getJPanel("Identificación",265, 75, 260, 75,14);

      panelApellidosNombres = getJPanel("Datos Personales",60, 165, 670, 210,14);

      panelDatosContacto = getJPanel("Datos de Contacto",60, 390, 315, 125,14);

      panelCredito = getJPanel("Crédito",415, 390, 315, 125,14);

      //** Se declaran los JLabel

      JLabel jlTipoId = getJLabel("Tipo:",25, 25, 60, 15);
      panelIdentificacion.add(jlTipoId);

      JLabel jlNumeroId = getJLabel("Número:",135, 25, 75, 15);
      panelIdentificacion.add(jlNumeroId);

      JLabel jlNombres = getJLabel("Nombres",35, 25, 65, 15);
      panelApellidosNombres.add(jlNombres);

      JLabel jlDireccion = getJLabel("Dirección:",155, 108, 120, 15);
      panelApellidosNombres.add(jlDireccion);

      JLabel jlApellidos = getJLabel("Apellidos",240, 25, 95, 15);
      panelApellidosNombres.add(jlApellidos);

      JLabel jlTelefono = getJLabel("Teléfono:",20, 35, 55, 15);
      panelDatosContacto.add(jlTelefono);

      JLabel jlCelular = getJLabel("Celular:",20, 63, 55, 15);
      panelDatosContacto.add(jlCelular);

      JLabel jlEmail = getJLabel("e-Mail:",20, 90, 55, 15);
      panelDatosContacto.add(jlEmail);

      JLabel jlCupo = getJLabel("Cupo:",90, 35, 34, 15);
      panelCredito.add(jlCupo);

      JLabel jlSaldo = getJLabel("Saldo:",90, 63, 35, 15);
      panelCredito.add(jlSaldo);
      
      JLabel jlBono = getJLabel("Bono:",90, 90, 35, 15);
      panelCredito.add(jlBono);

      JLabel jlSexo = getJLabel("Sexo",530, 25, 75, 15);
      panelApellidosNombres.add(jlSexo);

      JLabel jlCiudadMunicipio = getJLabel("Ciudad / Municipio:",155, 173, 120, 15);
      panelApellidosNombres.add(jlCiudadMunicipio);

      JLabel jlDepartamento = getJLabel("Departamento:",155, 141, 120, 15);
      panelApellidosNombres.add(jlDepartamento);

      //** Se instancian los JSeparator

      JSeparator jSeparator1 = new JSeparator();
      jSeparator1.setBounds(105, 85, 470, 2);
      jSeparator1.setForeground(Color.lightGray);
      panelApellidosNombres.add(jSeparator1);
     
      //** Se agregan los JTextField

      jtNumeroId = getJTextField("",135, 40, 105, 20,"Digite el Número de Identificación del Cliente","12");
      jtNumeroId.setHorizontalAlignment(JTextField.RIGHT);
      jtNumeroId.addFocusListener(this);
      jtNumeroId.addKeyListener(getValidarEntradaNumeroJTextField());
      panelIdentificacion.add(jtNumeroId);

      jtDireccion = getJTextField("",295, 105, 215, 20,"Digite la Dirección del Cliente","80");
      jtDireccion.addFocusListener(this);
      jtDireccion.addKeyListener(getConvertirMayuscula());
      panelApellidosNombres.add(jtDireccion);

      jtNombres = getJTextField("",35, 40, 195, 20,"Digite Nombres del Cliente","40");
      jtNombres.addFocusListener(this);
      jtNombres.addKeyListener(getConvertirMayuscula());
      panelApellidosNombres.add(jtNombres);

      jtTelefono = getJTextField("",90, 32, 95, 20,"Digite el Número de Teléfono Fijo del Cliente","10");
      jtTelefono.setHorizontalAlignment(JTextField.RIGHT);
      jtTelefono.addFocusListener(this);
      jtTelefono.addKeyListener(getValidarEntradaNumeroJTextField());
      panelDatosContacto.add(jtTelefono);

      jtCelular = getJTextField("",90, 60, 95, 20,"Digite el Número de Teléfono Celular del Cliente","12");
      jtCelular.setHorizontalAlignment(JTextField.RIGHT);
      jtCelular.addFocusListener(this);
      jtCelular.addKeyListener(getValidarEntradaNumeroJTextField());
      panelDatosContacto.add(jtCelular);

      jtEmail = getJTextField("",90, 87, 200, 20,"Digite el Correo Electrónico del Cliente","40");
      jtEmail.addFocusListener(this);
      panelDatosContacto.add(jtEmail);

      jtCupo = getJTextField("0",140, 32, 100, 20,"Digite el Cupo para Crédito Maximo del Cliente","7");
      jtCupo.setForeground(new Color(0, 99, 0));
      jtCupo.setHorizontalAlignment(JTextField.RIGHT);
      jtCupo.selectAll();
      jtCupo.addFocusListener(this);
      jtCupo.addKeyListener(getValidarEntradaNumeroJTextField());
      panelCredito.add(jtCupo);

      jtSaldo = getJTextField("0",140, 60, 100, 21,"Saldo Actual del Cliente","7");
      jtSaldo.setForeground(new Color(198, 0, 0));
      jtSaldo.setBackground(new Color(231, 231, 231));
      jtSaldo.setHorizontalAlignment(JTextField.RIGHT);
      jtSaldo.addKeyListener(getValidarEntradaNumeroJTextField());
      panelCredito.add(jtSaldo);
      
      jtBono = getJTextField("0",140, 87, 100, 21,"Bono Actual del Cliente","7");
      jtBono.setForeground(new Color(198, 0, 0));
      jtBono.setBackground(new Color(231, 231, 231));
      jtBono.setHorizontalAlignment(JTextField.RIGHT);
      jtBono.addKeyListener(getValidarEntradaNumeroJTextField());
      panelCredito.add(jtBono);

      jtApellidos = getJTextField("",240, 40, 280, 20,"Digite Apellidos del Cliente","40");
      jtApellidos.addFocusListener(this);
      jtApellidos.addKeyListener(getConvertirMayuscula());
      panelApellidosNombres.add(jtApellidos);

      
      jcDepartamento = getJComboBox(295, 138, 215, 20,"Seleccione el Departamento");
      jcDepartamento.setMaximumRowCount(15);
	  traerDepatamentos(jcDepartamento,conectarMySQL); // Se traen los departamentos
	  jcDepartamento.setSelectedItem(getDptoDivisionPolitica(conectarMySQL));
	  jcDepartamento.addActionListener(this);
	  jcDepartamento.addFocusListener(this);
      panelApellidosNombres.add(jcDepartamento);
      
      
      jtCiudadMunicipio = getJTextField(getMunicipioDivisionPolitica(conectarMySQL),295, 170, 215, 20,"Digite la Ciudad / Municipio o Presione F9","30");
      jtCiudadMunicipio.selectAll();
      jtCiudadMunicipio.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,getUpKeys());
      
      //Se instancia la clase, que se adiciona como evento de tipo KeyAdapter
      listaValor = getListaValores(getSentencia(),getComponentesRetorno(),this,jtCiudadMunicipio,jcDepartamento,conectarMySQL,getOcultarColumnas(),480);
 
      jtCiudadMunicipio.addFocusListener(this);
      jtCiudadMunicipio.addKeyListener(listaValor);
      jtCiudadMunicipio.addActionListener(this);
      jtCiudadMunicipio.addMouseListener(new MouseAdapter() { //Se utiliza para que muestre la lista de valores con doble Click	
     
 		   public void mouseClicked(MouseEvent m){
 		   	
 		   	  if (m.getClickCount()==2) 
 		   	  
 		   	    listaValor.mostrarListaValores();
 		   }
 		});   	
 	 
      panelApellidosNombres.add(jtCiudadMunicipio);

      //** Se agregan los JComboBox

      jcTipoId = getJComboBox(25, 40, 105, 20,"Selecione el Tipo de Identificación del Cliente");
      jcTipoId.addItem("Cédula");
      jcTipoId.addItem("Tarjeta Id.");
      jcTipoId.addItem("Pasaporte");
      jcTipoId.addFocusListener(this);
      panelIdentificacion.add(jcTipoId);

      jcSexo = getJComboBox(530, 40, 115, 20,"Seleccione el Sexo del Cliente");
      jcSexo.addItem("Masculino");
      jcSexo.addItem("Femenino");
      jcSexo.addFocusListener(this);
      panelApellidosNombres.add(jcSexo);
       
      getJFrame().setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Clientes.gif")).getImage()); 
       
       
      //** Se muestra el JFrame
      mostrarJFrame(); 
      
      jtNumeroId.grabFocus();
      
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
           camposObligatorios[3] = jtCupo;

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
	
	//******************************** Metodo getOcultarColumnas()  ***************************************

	final private int[] getOcultarColumnas() {

		int[] columnasOcultar = new int[1];
        
       	columnasOcultar[0] = 3;
       	
		return columnasOcultar;

	}
    //*********************** Metodo limpiar ************************

    private void limpiar(boolean dummy) { 
      
      if (dummy) {
      
	      jcTipoId.setSelectedIndex(0);
	      jtNumeroId.setText("");
	      
          jtNumeroId.grabFocus(); //se le da el foco al primer componente
      }
      
      jtDireccion.setText("");
      jtNombres.setText("");
      jtTelefono.setText("");
      jtCelular.setText("");
      jtEmail.setText("");
      jtCupo.setText("0");
      jtSaldo.setText("0");
      jtApellidos.setText("");
      jtSaldo.setText("0");
      jtBono.setText("0");
      jcDepartamento.setSelectedItem("SUCRE");
      codigoDivisionPolitica.setText(getCodigoDivisionPolitica(conectarMySQL)); //Se configura la division politica
 	  jtCiudadMunicipio.setText(getMunicipioDivisionPolitica(conectarMySQL));
      

    }

   //*********************** Metodo limpiar ************************

    private void buscarRegistro() { 

        final String sentenciaSQL = "Select * "+
                                    "From  Clientes "+
                                    "Where TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +"' and idCliente = '" + jtNumeroId.getText()  +"'";

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
			      jtCupo.setText(resultado.getString(9));
			      jtSaldo.setText(resultado.getString(10));
	              codigoDivisionPolitica.setText(resultado.getString(11)); //Se asigna la division politica
	              jcSexo.setSelectedIndex(resultado.getString(12).equals("M") ? 0 : 1); 
 	              jtBono.setText(resultado.getString(13));
	                 
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
    
    
    //*********************** Metodo guardar ************************

    private void guardar() throws Exception { 


        final String[] datos = new String[13];
        
        datos[0] = "'" + jcTipoId.getSelectedItem().toString().charAt(0) + "'";
        datos[1] = "'" + jtNumeroId.getText() + "'";
        datos[2] = "'" + jtNombres.getText() + "'";
        datos[3] = "'" + jtApellidos.getText() + "'";
        datos[4] = "'" + jtDireccion.getText() + "'";
        datos[5] = "'" + jtTelefono.getText() + "'";
        datos[6] = "'" + jtCelular.getText() + "'";
        datos[7] = "'" + jtEmail.getText() + "'";
        datos[8] = jtCupo.getText().isEmpty() ? "0" : jtCupo.getText() ;
        datos[9] = "0";
        datos[10] =  codigoDivisionPolitica.getText();
        datos[11] =  "'" + jcSexo.getSelectedItem().toString().charAt(0) + "'";
        datos[12] = "0";

        guardar("Clientes",datos,conectarMySQL,false); 

         
     }



      //**************************** Metodo actualizar ************************

      private void actualizar()  throws Exception  { 


        final String[] datos = new String[9];

        datos[0] = "IdCliente = '" + jtNumeroId.getText() + "'";
        datos[1] = "Direccion = '" + jtDireccion.getText() + "'";
        datos[2] = "Nombre = '" + jtNombres.getText() + "'";
        datos[3] = "Apellido = '" + jtApellidos.getText() + "'";
        datos[4] = "Telefono = '" + jtTelefono.getText() + "'";
        datos[5] = "Celular = '" + jtCelular.getText() + "'";
        datos[6] = "Correo = '" + jtEmail.getText() + "'";
        datos[7] = "Cupo = " + jtCupo.getText();
        datos[8] = "DivisionPolitica = " + codigoDivisionPolitica.getText();

        
        String condicion = "TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +"' and  idCliente = '" + jtNumeroId.getText()  +"'";

        actualizar("Clientes",datos,condicion,conectarMySQL,false);
            

      }

      //**************************** Metodo eliminar ************************

      private void eliminar() { 

       try {

            conectarMySQL.commit();

            Mensaje("Cliente Eliminada","Información",JOptionPane.INFORMATION_MESSAGE);

           // Se limpia la forma 
           limpiar(true); 

          } catch (Exception e) {

             conectarMySQL.rollback();

             Mensaje("No se puede eliminar Cliente" + e,"Error",JOptionPane.ERROR_MESSAGE); 

          }

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
					 	         	
                  
                      if (conectarMySQL.validarRegistro ("Clientes ",
                                                        "TipoId = '" + jcTipoId.getSelectedItem().toString().charAt(0) +"' and  idCliente = '" + jtNumeroId.getText()  +"'")) {
                                                        
                   
                          actualizar(); 
                          
                          mensaje = "Cliente actualizado correctamente";

                     } else {
                     
                        guardar();
                        
                        mensaje = "Cliente insertado correctamente";
                       
                    
			        }    
                     
                     
                     conectarMySQL.commit();      //Se registra en la base de datos
		 	         
		 	         Mensaje(mensaje,"Información",1);
		 	         
		 	         
		 	         
		 	          // Se verifica quien hizo el llamado a la forma de personas
			           if (framePadre != null && framePadre.getName().equals("Venta")) {
			           	
			           	    ocultarJFrame();
			           	
			           } else
			               
			               limpiar(true);
			           
			           
		 	         
		 	        } catch (Exception e) {
					 	         	   
		 	         	   conectarMySQL.rollback();
		 	         	   
		 	         	   Mensaje("Error al Insertar Cliente " +e,"Error",0);
		 	         	   
		 	         }  
       
              }         

            } else 

                if (fuente==Beliminar) { 

                   eliminar(); 

                }  else 

                    if (fuente==Bbuscar) { 

                       buscarRegistro();

                    } else 

                       if (fuente==Bsalir) {

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
		      
		      if (f.getComponent() != jtCiudadMunicipio && f.getComponent().getClass().getSuperclass().getName().equals("javax.swing.JTextField"))
			          
			   ((JTextField)f.getComponent()).selectAll();
       
		  }      

       }

      //**************************** Metodo focusLost ************************

      public void focusLost(FocusEvent f) { 
      
      
        if (f.getSource() == jtNumeroId && !jtNumeroId.getText().isEmpty())
        
              buscarRegistro();
              
        else      
          
          if (f.getSource() == jtEmail && !jtEmail.getText().isEmpty()) {
          	
          	  if (!esEmail(jtEmail.getText())) {
          	  	  
          	  	  Mensaje("Correo "+ jtEmail.getText() +" invalido","Información",JOptionPane.ERROR_MESSAGE);
          	  	  jtEmail.setText("");
          	  	  jtEmail.grabFocus();
          	  	  
          	  	
          	  }
          	
          	
          }
              
      

        // se coloca el atributo visual por defecto
        f.getComponent().setBackground(getVisualAtributoPierdeFocoComponentes());

      }

}