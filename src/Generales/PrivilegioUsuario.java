/*
 * Clase:PrivilegioUsuario
 *
 * Version : 01
 *
 * Fecha: 27-10-2006
 *
 * Copyright: JASoft e Ing. Jhon Jaime Mendez
 */
 
import com.JASoft.componentes.CheckNode;
import com.JASoft.componentes.CheckRenderer;
import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.KeyboardFocusManager;

import java.sql.ResultSet;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JComboBox;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;



/*
  * Esta clase se utiliza para crear usuarios y configurar sus perfiles
  */
  
  final class PrivilegioUsuario extends JInternalFrame implements ActionListener {
  
  	//Se especifica el serial para la serializacion
    static final long serialVersionUID = 19781220;
    
    //Referencia de conexion a la base de datos
    private ConectarMySQL conectarMySQL;
 	
 	//Referencia al componente CrearJFrame
 	private CrearJFrame cJf;
 	
    
    //JComboBox
    private JComboBox jcUsuarios; 	
 	
 	//Botones
 	private JButton Bguardar;
    private JButton Bsalir;
    
    //Tree para mostrar los diferenres permisos que un usuario puede tener
  	private JTree tree;
  	
  	//CheckNode para el arbol
  	private CheckNode raiz;
  	private CheckNode nodoParametro;
  	private CheckNode nododatosGeneralesRoot;
  	private CheckNode nodoVentasRoot;
  	private CheckNode nodoComprasRoot;
  	private CheckNode nodoInventarioRoot;
  	private CheckNode nodoUtilidad;
    
    //Array CheckNode para los diferentes permisos
  	private CheckNode[] nodosParametros;
  	private CheckNode[] nodosdatosGenerales;
  	private CheckNode[] nodoVentas;
  	private CheckNode[] nodosCompras;
  	private CheckNode[] nodosAuditorias;
  	private CheckNode[] nodosUtilidades;
    private CheckNode[] nodosInventario;

	//Referencia al MenuPrincipal
 	private MenuPrincipal menu;
 		
	//Nombres de opciones del menu parametros
    String [][] parametros =   { {"Parametros Generales", "0"},
    	                         {"Festivos", "1"},
    	                         {"Privilegios de usuario", "2"}
    	                       }; 
						     
						     
	
	//Nombres de opciones del menu Datos Generales
    String [][] datosGenerales =   { {"Clientes","10"},     
								     {"Proveedores","11"},     
								     {"Vendedores","12"}     
								   };  					        
		     
		     
     //Nombres de opciones del menu ventas
    String [][] ventas   =       { {"Facturación","20"},     
						           {"Devolución","21"},     
						           {"Cotización","22"},
						           {"Anular","23"}
						         };  
						 
						 
						 
	//Nombres de opciones del menu compras
    String [][] compras =   { {"Registrar" ,"30"},    
							  {"DevolucionCompras" ,"31"},
							  {"AnularCompras" ,"32"},
		  			        } ; 
		  			               
	//Nombres de opciones del menu inventario
    String [][] inventario = { {"Productos" ,"40"},    
							   {"Categorias" ,"41"},
							   {"Marcas" ,"42"},
							   {"Listado Productos" ,"43"}
		  			         } ; 
	
	     
     //Nombres de opciones del menu utilidades
    String [][] utilidades =  {{"Configurar Copias De Seguridad (Backup)","50"},
                               {"Restaurar Copia De Seguridad (Backup)","51"},
                               {"Mantenimiento Base De Datos","52"},
                               {"Auditorias","53"}
						    };  
						 
 	 
 	 
     

 	//Constructor utilizado deste el menu principal	     
   	public PrivilegioUsuario(ConectarMySQL p_conMySQL,MenuPrincipal p_menu){ 
  	    
  	    super("Privilegios de Usuarios",true, true, false, false);
    	
    	setBounds((p_menu.getWidth() - 420)/2,(p_menu.getHeight() - 460)/2,420,460);
    	
    	setLayout(null);
    	
    	menu = p_menu;
    	
    	conectarMySQL = p_conMySQL;
    	
        cJf =new CrearJFrame(); 
		
		
		// Se instancia un panel
  	    JPanel panel = cJf.getJPanel("Perfiles",40,120,320,240);
  		this.add(panel);
  	
 		         	  
  	    // Se configuran las etiquetas
  	    JLabel LNombre = cJf.getJLabel("Usuario : ",110,60,200,20);
 		this.add(LNombre); 				 		
 		
 	
  	    
  	    // Se crea el array de JTextField
  	    jcUsuarios = cJf.getJComboBox(180,60,120,20,"Usuarios"); 
  	    traerInformacion(jcUsuarios);
  	    jcUsuarios.addActionListener(this);
 	    this.add(jcUsuarios);
 		
 	
 	    
 	    Bguardar = cJf.getJButton(" Guardar",25,380,120,35,"Imagenes/Guardar.gif","Guardar Cambios");
 	    Bguardar.setMnemonic('G');
 	    Bguardar.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/GuardarS.gif")));
  		Bguardar.addActionListener(this);
  		this.add(Bguardar);
 
  		Bsalir = cJf.getJButton(" Salir",280,380,120,35,"Imagenes/Salir.gif","Salir");
  		Bsalir.setMnemonic('S');
  		Bsalir.setRolloverIcon(new ImageIcon(getClass().getResource("/Imagenes/SalirS.gif")));
  		Bsalir.addActionListener(this);
  		this.add(Bsalir);
  	
							 	
         
        //se crea el nodo raiz
        raiz = new CheckNode("Azkaban"); 
        
        //se crea el nodo raiz para el menu parametros
        nodoParametro = new CheckNode("Parámetros Generales"); 
    
        //Se crea un array de nodos                                    
        nodosParametros = new CheckNode[parametros.length];
        
        
        //se agregan los nodos del menu parametros
	    for (int i = 0; i< parametros.length; i++) {
	    	
	        nodosParametros[i] = new CheckNode(parametros[i][0]); 
	        nodoParametro.add(nodosParametros[i]);
	
	    }
        
        
        //se crea el nodo raiz para el menu modulo
        nododatosGeneralesRoot = new CheckNode("Datos Generales"); 
    
        //Se crea un array de nodos                                    
        nodosdatosGenerales = new CheckNode[datosGenerales.length];
        
        
        //se agregan los nodos del menu parametros
	    for (int i = 0; i< datosGenerales.length; i++) {
	    	
	        nodosdatosGenerales[i] = new CheckNode(datosGenerales[i][0]); 
	        nododatosGeneralesRoot.add(nodosdatosGenerales[i]);
	
	    }
	    
	    
	    
	    //se crea el nodo raiz para el menu ventas
        nodoVentasRoot = new CheckNode("Ventas"); 
        
        //Se crea un array de nodos                                    
        nodoVentas = new CheckNode[ventas.length];
        
        
        //se agregan los nodos del menu parametros
	    for (int i = 0; i< ventas.length; i++) {
	    	
	        nodoVentas[i] = new CheckNode(ventas[i][0]); 
	        nodoVentasRoot.add(nodoVentas[i]);
	
	    }
	    
	    
	    //se crea el nodo raiz para el menu Docentes y Personal
        nodoComprasRoot = new CheckNode("Compras"); 
        
        //Se crea un array de nodos                                    
        nodosCompras = new CheckNode[compras.length];
        
        
        //se agregan los nodos del menu parametros
	    for (int i = 0; i< compras.length; i++) {
	    	
	        nodosCompras[i] = new CheckNode(compras[i][0]); 
	        nodoComprasRoot.add(nodosCompras[i]);
	
	    }
	    
	    
	     //se crea el nodo raiz para el menu Docentes y Personal
        nodoInventarioRoot = new CheckNode("Inventario"); 
        
        //Se crea un array de nodos                                    
        nodosInventario = new CheckNode[inventario.length];
        
        
        //se agregan los nodos del menu parametros
	    for (int i = 0; i< compras.length; i++) {
	    	
	        nodosInventario[i] = new CheckNode(inventario[i][0]); 
	        nodoInventarioRoot.add(nodosInventario[i]);
	
	    }
	    
	     //se crea el nodo raiz para el menu auditorias
        nodoUtilidad = new CheckNode("Utilidades"); 
    
        //Se crea un array de nodos                                    
         nodosUtilidades = new CheckNode[utilidades.length];
        
        
        //se agregan los nodos del menu parametros
	    for (int i = 0; i< utilidades.length; i++) {
	    	
	        nodosUtilidades[i] = new CheckNode(utilidades[i][0]); 
	        nodoUtilidad.add(nodosUtilidades[i]);
	
	    }
    
    
        //se agrega los nodos al nodo raiz
        raiz.add(nodoParametro);
        raiz.add(nododatosGeneralesRoot);
        raiz.add(nodoVentasRoot);
        raiz.add(nodoComprasRoot);
        raiz.add(nodoInventarioRoot);
        raiz.add(nodoUtilidad);
   
 
    	//Se configura y renderiza el JTree
        tree = new JTree( raiz );
        tree.setCellRenderer(new CheckRenderer());
        tree.addMouseListener(new NodeSelectionListener(tree));
	    
	    JScrollPane scrollTree = new JScrollPane(tree);
	    scrollTree.setBounds(20,25,280,200);
		panel.add(scrollTree);
		
		
		jcUsuarios.grabFocus();
		
		buscarPrivilegiosUsuario();
  	    
  	  	this.setFrameIcon(new ImageIcon(getClass().getResource("/Imagenes/Usuario.gif")));

  	    this.setVisible(true);
  	    
  	    //Se especifica para que se pueda navegar con enter y con Tan
  	    this.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,cJf.getUpKeysFrame());
 		
 		
	   // Se agrega un evento de ventana
 	   this.addInternalFrameListener( new InternalFrameAdapter() {
		    public void internalFrameClosing(InternalFrameEvent ife) {

			   menu.habilitarMenu(true);

		    }
	   });

  	   
  	
  	}
  	
  	
  	//********************************************************************************
  
  	private void limpiar() {
 	 
		   
		   desSeleccionarElementosArbol();
		   
		   //se contrae todo el arbol
		   expandirArbol(tree,false);
		   
		   //se contrae todo el arbol
		   expandirArbol(tree,true);
		   
		   
	 	   jcUsuarios.setSelectedIndex(0);
		 
    }
    
    
   	//********************************************************************************
  
    public void traerInformacion(JComboBox comboBox) {
      
      
      	String sentenciaSQL = "Select ID "+
      	                      "From Usuarios "+
      	                      "Where ID <> 'Admin'";
 	    
 	    
 	     try {
 		 
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado =conectarMySQL.buscarRegistro(sentenciaSQL);
 		  
 		   if (resultado!=null) 
 		       
 		       while (resultado.next())  {
 		       	
 		       	comboBox.addItem(resultado.getString(1));
 		       	
 		       }
 		   
 		 } catch (Exception e) {
 		 
 		   cJf.Mensaje("Error en la busqueda"+e,"Error",JOptionPane.ERROR_MESSAGE);
 	    
 	    }     	
  		       	   
    	
    }
    
   	
   	//********************************************************************************
  
   	private void desSeleccionarElementosArbol () {
   		  
	     //Se desseleccionan los CheckNodes
	     raiz.setSelected(false);
	  	 nodoParametro.setSelected(false);
	  	 nododatosGeneralesRoot.setSelected(false);
	  	 nodoVentasRoot.setSelected(false);
	     nodoUtilidad.setSelected(false);
	     
	     for (int i = 0; i < nodosParametros.length; i++)
	  	     nodosParametros[0].setSelected(false);
	  	     
	  	     
	  	 for (int i = 0; i < nodosdatosGenerales.length; i++)
	  	     nodosdatosGenerales[0].setSelected(false);
	       
	   	     
	  	 for (int i = 0; i < nodoVentas.length; i++)
	  	     nodoVentas[0].setSelected(false);
	       	     
	         
	  	  for (int i = 0; i < nodosUtilidades.length; i++)
	  	     nodosUtilidades[0].setSelected(false);
	  	  
	  	  for (int i = 0; i < nodosInventario.length; i++)
	  	     nodosInventario[0].setSelected(false);
	  	  
	  	     
	  	  tree.updateUI();   
   	}
   	
   	
    //********************************************************************************
  
   	private void expandirArbol (JTree tree, boolean expand) {
        
        TreeNode root = (TreeNode)tree.getModel().getRoot();
    
        // Traverse tree from root
        expandirTodo(tree, new TreePath(root), expand);
    }
      
    //********************************************************************************
  
    private void expandirTodo(JTree tree, TreePath parent, boolean expand) {
       
        // Traverse children
        TreeNode node = (TreeNode)parent.getLastPathComponent();
        
        if (node.getChildCount() >= 0) {
            for (Enumeration e=node.children(); e.hasMoreElements(); ) {
                TreeNode n = (TreeNode)e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandirTodo(tree, path, expand);
            }
        }
    
        // Expansion or collapse must be done bottom-up
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }
   
    
   	//********************************************************************************
  
    private void guardarPerfiles() throws Exception {
          
          Vector < String > datosPerfiles = new Vector < String >();
          
          //raiz.setSelected(false);
           
           //Se buscan los elementos seleccioonados
           Enumeration enumeracion  = raiz.breadthFirstEnumeration();
           
           CheckNode nodo;
                 
           if (raiz.isSelected())
                 nodo = (CheckNode)enumeracion.nextElement();
                 
                    
          while (enumeracion.hasMoreElements()) {
            
                  nodo = (CheckNode)enumeracion.nextElement();
                 
                 
                    if (nodo.isSelected()) {
                    	
                    	TreeNode[] nodos = nodo.getPath();
                    	
                    	String comodin[][] = null;
                    	
                    	if (nodos[1].toString().equalsIgnoreCase(("Parámetros Generales")))  {
                    	
                    	   comodin = parametros;
                     	
                    	 }else
	                    	if (nodos[1].toString().equalsIgnoreCase(("Datos Generales"))) {
	                    	  
	                    	      comodin = datosGenerales;
	                     	      
	                    	 }else
	                    	    
	                    	    if (nodos[1].toString().equalsIgnoreCase(("ventas"))) {
	                    	    
	                    	      comodin = ventas;
	                     	    
	                    	    } else 
	                    	    
	                    	       if (nodos[1].toString().equalsIgnoreCase(("compras"))) {
	                    	    
	                    	           comodin = compras;
	                     	    
	                    	       } else 
	                    	          
	                    	          if (nodos[1].toString().equalsIgnoreCase(("inventario"))) {
	                    	    
	                    	              comodin = inventario;
	                     	    
	                    	          } else
	                    	         
			                    	        if (nodos[1].toString().equalsIgnoreCase(("Utilidades"))) 
			                    	       
			                    	           comodin = utilidades;
			                    	       
                                   
                          
                          for (int i = 2; i < nodos.length; i++) {
                          
                    	        datosPerfiles.addElement(buscarPosicionElementoEnJTree(comodin,nodos[i].toString()));
  	   	                   }
  	   	            }
  	   	   }
  	   	   
  	   	   
  	   	   String datosPerfilesInsertar[][] = new String[datosPerfiles.size()][2]; 
  	   	   
  	   	   
  	   	   for (int i = 0; i < datosPerfilesInsertar.length; i++ ) {
  	   	   	     
  	   	   	     String codigoPrivilegio =  datosPerfiles.elementAt(i);
  	   	   	     
  	   	   	     datosPerfilesInsertar[i][0] =  codigoPrivilegio;
  	   	   	     datosPerfilesInsertar[i][1] = "'" + jcUsuarios.getSelectedItem() +"'";
  	   	   } 
  	   	   
  	   	   
  	   	   cJf.guardar("Perfiles",datosPerfilesInsertar,conectarMySQL,2,false);
	  	
    
    }
    
   	//********************************************************************************
   
    private void eliminarPerfiles() throws Exception {

         String condicion = "IDUsuario = '" +  jcUsuarios.getSelectedItem() + "'";
         
         cJf.eliminarRegistro("Perfiles",condicion,conectarMySQL,false); 	   
	   
 	}
 	
 
   
  	//********************************************************************************
    
    private void buscarPrivilegiosUsuario() {
 	  
 	     
  	     String sentenciaSQL = "Select P.IdPrivilegio,PR.nombreMenu,PR.Descripcion "+
  	                           "From Perfiles P, Privilegios Pr "+
                               "Where P.IdPrivilegio = Pr.IdPrivilegio and P.IdUsuario='"+ jcUsuarios.getSelectedItem()+"'";
 	    
 	    
 	     try {
 		 
 		   // Se realiza la consulta en la base de datos
 		   ResultSet resultado =conectarMySQL.buscarRegistro(sentenciaSQL);
 		  
 		   if (resultado!=null) 
 		       
 		       while (resultado.next())  {
 		       	   
 		       	    if (resultado.getString(2).equalsIgnoreCase("Parametros")) {
 		       	    	
 		       	    	activarCheckNode(nodosParametros,resultado.getString(3));
 		       	    	
 		       	    } else
 		       	       if (resultado.getString(2).equalsIgnoreCase("Datos Generales")) {
 		       	    	
 		       	        	activarCheckNode(nodosdatosGenerales,resultado.getString(3));
 		       	    	
 		       	    }else
 		       	      
 		       	         if (resultado.getString(2).equalsIgnoreCase("Compras")) {
 		       	    	
 		       	         	activarCheckNode(nodosCompras,resultado.getString(3));
 		       	    	
 		       	       }else
 		       	         if (resultado.getString(2).equalsIgnoreCase("Utilidades")) {
 		       	    	
 		       	         	activarCheckNode(nodosUtilidades,resultado.getString(3));
 		       	         
 		       	         } else
 		       	           
 		       	            if (resultado.getString(2).equalsIgnoreCase("ventas")) {
 		       	    	
 		       	         	  activarCheckNode(nodoVentas,resultado.getString(3));
 		       	            } else
	 		       	           
	 		       	            if (resultado.getString(2).equalsIgnoreCase("inventario")) {
	 		       	    	
	 		       	         	  activarCheckNode(nodosInventario,resultado.getString(3));
	 		       	            }
			 		       
			  }		       
 		       	    
 		  
 	    } catch (Exception e) {
 		   cJf.Mensaje("Error en la busqueda"+e,"Error",JOptionPane.ERROR_MESSAGE);
 	    }     	
  		
    }	
   
    
    //********************************************************************************
  
    private static void activarCheckNode(CheckNode [] nodos,String elementoComparacion) {
    	
    	boolean sw = true;
    	
    	for (int i = 0; i < nodos.length && sw; i++) {
 		       	 
 		       	 //Se quitan las tildes
 		       	 String valorNodo = nodos[i].toString().replace('ó','o').replace('á','a').replace('é','e');
 		       	    	
    		if (valorNodo.equalsIgnoreCase(elementoComparacion)) {
    			
    		     nodos[i].setSelected(true);
    		     
    		     sw = false;
    		     
    		 }    
 		 }
    }
    
   	//********************************************************************************
  
 	private static String buscarPosicionElementoEnJTree(String[][] array,String elementoABuscar) {
    
       boolean sw = true;
       
       int i = 0;
       
       for ( i = 0; i < array.length && sw; i ++) {
       	  
       	   if (array[i][0].equals(elementoABuscar)) 
       	   
       	      sw = false;
       	
       }
      
       return array[i - 1][1];
    
    }
 	
 	
   
   	//********************************************************************************
  
    public void actionPerformed(ActionEvent act) {
 		
 		Object fuente = act.getSource();
 		
 		// Se verifica el componente que genero la acccion
 		
 		if (fuente==Bguardar) {
 		  	  
 		  	   if (jcUsuarios.getItemCount() > 0) {
 		  	   	   
			 		          try {
			 		          	
			 		          	    String mensaje = ""; 
			 		          	     
				 		            if (!conectarMySQL.validarRegistro ("Usuarios ",
	 			                                                  "ID ='"+ jcUsuarios.getSelectedItem() +"'")) {
				 		            
					 		            guardarPerfiles(); 
					 		           
					 		            mensaje = "Perfil almacenado";
					 		           
	 	 
					 		        
					 		        } else {
					 		        	
					 		        	eliminarPerfiles(); //Se eliminan para volverlos a insertar
					 		        	
					 		        	guardarPerfiles(); 
					 		           
					 		            mensaje = "Perfil actualizado";
					 		        	
					 		        	
					 		        } 
					 		        	
					 		         conectarMySQL.commit(); //Se hacen los cambios permanentes
					 		            
					 		         cJf.Mensaje(mensaje,"Información",JOptionPane.INFORMATION_MESSAGE);
					 		            
					 		         //Se limpia la forma     
	 	                             limpiar();
	 		                        
				 		  
			 		          } catch (Exception e) {
			 		          	  
			 		          	  conectarMySQL.rollback(); 
			 		          	  cJf.Mensaje("Error al insertar" + e,"Error",JOptionPane.ERROR_MESSAGE);
			 		          	  
			 		          }
 		  	   	
 		  	   	 }   
 		  	   
 		  		  
	 		 } else
	 		       if (fuente==Bsalir) {
	 		       	 
	 		       	 try{
 			           
	 			          menu.habilitarMenu(true);
	 			          dispose();
 			          }catch(Exception e){}
	 		       	
 		           } else
 		               
 		               // Se verifica el componente que genero la acccion

			         if (fuente == jcUsuarios) {
			            
				 		 desSeleccionarElementosArbol();
				 		 buscarPrivilegiosUsuario();
			         }   
 	} 
 	
 	
 	
 	//********************************************************************************
  
 	public void focusGained(FocusEvent F) {
 	    
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());
 	}
 	
 	
 	
 	//********************************************************************************
  
    final static class NodeSelectionListener extends MouseAdapter {
    
	    JTree tree;
	    
	    NodeSelectionListener(JTree tree) {
	      this.tree = tree;
	    }
	    
	    public void mouseClicked(MouseEvent e) {
	      int x = e.getX();
	      int y = e.getY();
	      int row = tree.getRowForLocation(x, y);
	      TreePath  path = tree.getPathForRow(row);
	      //TreePath  path = tree.getSelectionPath();
	      if (path != null) {
	        CheckNode node = (CheckNode)path.getLastPathComponent();
	        boolean isSelected = ! (node.isSelected());
	        node.setSelected(isSelected);
	        if (node.getSelectionMode() == CheckNode.DIG_IN_SELECTION) {
	          if ( isSelected ) {
	            tree.expandPath(path);
	          } else {
	            tree.collapsePath(path);
	          }
	        }
	        ((DefaultTreeModel)tree.getModel()).nodeChanged(node);
	        // I need revalidate if node is root.  but why?
	        if (row == 0) {
	          tree.revalidate();
	          tree.repaint();
	        }
	      }
	    }
	  }
   
}