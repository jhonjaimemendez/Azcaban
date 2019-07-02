/**
 * Clase: AtributoVisual
 * 
 * @version  1.0
 * 
 * @since 16-04-2007
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
 
package com.JASoft.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;



/**
 * Esta clase permite definir parametros visuales comunes a todas las interfaces
 * del SIT, tales como: Color de fondo de los componentes que obtienen el foco, 
 * Justificacion de los elementos de una tabla, renderizacion y bordes.
 */

 
 public abstract class AtributoVisual extends Cursores{
 	 
 	 //Declaracion de variables
        
      /**
       *Esta variable permite configurar la presentacion de los encabezados del jtable
       */
 	  private   static JTunesTableHeader jTunesTableHeader;
 	  
      
      /**
       *Esta variable permite renderizar componentes en un tabla
       */
      private  static JTunesHeaderRenderer jTunesHeaderRenderer;		

	 	
 	  /**
 	   *Esta variable permite configurar la justificacion del texto al centro
 	   */
 	  private  static HeaderRenderer getAlinearCentro;
    
      
      /**
       *Esta variable permite configurar la justificacion del texto a la izquierda
       */
      private  static HeaderRenderer getAlinearIzquierda;
    
      
      /**
       *Esta variable permite configurar la justificacion del texto a la izquierda
       */
 	  private static HeaderRenderer getAlinearDerecha;
 	  
 	  
      /**
       *Esta variable permite configurar los bordes de los botones de la JToobar
       */
 	  private static Border dobleBorde;
 	  
    	
      /**
       * Color de un componente cuando obtiene el foco
       */
      private  static Color visualAtributoGanaFocoComponente;
  	    
  	   
  	   /**
       * Color de un componente cuando pierde el foco
       */
  	   private  final static Color visualAtributoPierdeFocoComponente = Color.white;
  	   
  	   /**
  	    * Color casilla de texto con lista de valores
  	    */
  	    private  static Color visualAtributoComponenteConLista;
  	   
  	    
  	   /**
       * Color del Combobox cuando se desabilita
       */
  	   private  static Color colorComboBoxDesabilidato;
     
       /**
       * Color de la letra de un JTextField
       */
       private  static Color colorLetraJTextField ;
       
       
       /**
       * Letra para lista de valores
       */
       private  static Font letraListaDeValores = new Font("Arial",Font.BOLD,12);
       
       
      /**
       * Tipo de borde undido
       */
       private static BevelBorder bordeUndido ;
  
      /**
       * Tipo de letra para los JTextField
       */
       private static Font tipoLetra ;
      
      
       /*
        * Tipo de borde EtchedBorde
        */
        
        private static EtchedBorder etchedBorder;
       
      
  	   //Metodos analizadores
  	   
  	   /**
	  	 * Obtiene el valor para justificar el texto de una columna de una tabla hacia
	  	 * lal centro
	  	 *
	  	 * @return HeaderRenderer
	  	 */
  	   final public HeaderRenderer getAlinearCentro() {
  	   
  	     if (getAlinearCentro == null)
  	      
  	          getAlinearCentro = new HeaderRenderer(SwingConstants.CENTER);
  	   	
  	   	  return  getAlinearCentro;
  	   	
  	   }
  	   
	   /**
	  	 * Obtiene el valor para justificar el texto de una columna de una tabla hacia
	  	 * la izquierda
	  	 *
	  	 * @return HeaderRenderer
	  	 */
  	   final public HeaderRenderer getAlinearIzquierda() {
  	   	
  	   	 if (getAlinearIzquierda == null)
  	    
  	   	    getAlinearIzquierda = new HeaderRenderer(SwingConstants.LEFT);
  	   	 
  	   	 return  getAlinearIzquierda;
  	   	
  	   }
  	   
  	   /**
	  	 * Obtiene el valor para justificar el texto de una columna de una tabla hacia
	  	 * la derecha
	  	 *
	  	 * @return HeaderRenderer
	  	 */
  	   final public HeaderRenderer getAlinearDerecha() {
  	   	  
  	   	  if (getAlinearDerecha == null)
  	     
  	   	      getAlinearDerecha = new HeaderRenderer(SwingConstants.RIGHT);
  	   	  
  	   	  return  getAlinearDerecha;
  	   	
  	   }
  	   
  	   
  	   
  	   final public static void configuracionesInicialesPorDefecto() {
  	   	   
  	   	       // Configuraciones generales para el ComboBox y la letra de los TextField
	      UIManager.put("ComboBox.disabledForeground",getColorJComboBoxDesabilidato());   	    
	  	  UIManager.put("TextField.font",  getTipoLetra());
	  	  UIManager.put("TextField.border", getBordeUndido());
	  	  UIManager.put("FormattedTextField.border", getBordeUndido());
	  	  UIManager.put("TextArea.font",  getTipoLetra());
	  	  //UIManager.put("Panel.border",  etchedBorder);
	  	  //UIManager.put("Label.border",  etchedBorder);
	  	  UIManager.put("TextField.foreground",getColorLetraJTextField());
	  	  UIManager.put("TextArea.foreground",getColorLetraJTextField());
	  	  UIManager.put("PasswordField.foreground",getColorLetraJTextField());
	  	  UIManager.put("PasswordField.border", getBordeUndido());
	  
  	   	
  	   }
  	   
  	   
  	   /**
	  	 * Obtiene el color para configurar el fondo de un componente
	  	 * cuando obtiene el foco
	  	 *
	  	 * @return Color
	  	 */
  	   final public Color getVisualAtributoGanaFocoComponentes() {
  	   	
  	   	
  	   	  if (visualAtributoGanaFocoComponente == null)
  	   	  
  	   	     visualAtributoGanaFocoComponente = new Color(255,255,200);
  	   	
  	   	  return  visualAtributoGanaFocoComponente;
  	   	
  	   }
  	   
  	   /**
	  	 * Obtiene el color para configurar el fondo de un componente
	  	 * cuando pierde el foco
	  	 *
	  	 * @return Color
	  	 */
  	   final public Color getVisualAtributoPierdeFocoComponentes() {
  	   	
  	   	  return  visualAtributoPierdeFocoComponente;
  	   	
  	   }
  	   
	    /**
	  	 * Obtiene el color para configurar el fondo de un componente que
	  	 * posee una lista de valores
	  	 *
	  	 * @return Color
	  	 */
  	   final public Color getVisualAtributoComponenteConLista() {
  	   	
  	   	 if (visualAtributoComponenteConLista == null)
  	   	  
  	   	    visualAtributoComponenteConLista = new Color(240,240,255);
  	   	
  	   	  return visualAtributoComponenteConLista;
  	   	
  	   }
  	   
  	   
  	   /**
	  	 * Obtiene  el tipo de letra para los JPasswordField
	  	 * 
	  	 *
	  	 * @return Font
	  	 */
  	   final public Font getLetraJPassword() {
  	      
  	      return null;	
  	   	
  	   }
  	   
  	   
  	   /**
	  	 * Obtiene color para configurar  fondo de un JComboBox
	  	 * desahabilitado
	  	 *
	  	 * @return Color
	  	 */
  	   final protected  static Color getColorJComboBoxDesabilidato() {
  	   	
  	   	 if (colorComboBoxDesabilidato == null)

            colorComboBoxDesabilidato =  new Color(102,102,102);

  	   	  return  colorComboBoxDesabilidato;
  	   	
  	   }
  	   
  	    /**
	  	 * Obtiene color para configurar el color de la letras de un JTextField
	  	 *
	  	 * @return Color
	  	 */
  	   final protected static Color getColorLetraJTextField() {
  	   	
  	   	 if (colorLetraJTextField == null)
  	     
  	        colorLetraJTextField =  new Color(55,55,18);
       
  	   	  return  colorLetraJTextField;
  	   	
  	   }
  	   
  	   /**
	    * Obtiene un borde de tipo undido 
	  	*
	  	* @return BevelBorder
	  	*/
  	   final public static BevelBorder getBordeUndido() {
  	   	
  	   	 if (bordeUndido == null)
  	   	     
  	   	     bordeUndido = new BevelBorder(BevelBorder.LOWERED);
  	   	  
  	   	  return bordeUndido;
  	   	
  	   }
  	   
  	   
  	   /**
	    * Obtiene un tipo de letra utilizado en el JTextField y JTextArea
	  	*
	  	* @return Font
	  	*/
  	   final protected static Font getTipoLetra() {
  	   	
  	   	 if (tipoLetra == null)
  	   	     
  	   	     tipoLetra = new Font("Arial",Font.PLAIN,12);
  	   	  
  	   	  return tipoLetra;
  	   	
  	   }
  	   
  	    
  	   /**
	  	* Obtiene una referencia para configurar un estilo de  encabezado 
	  	* de tabla llamado JTunesTable
	  	*
	  	* @return JTunesTableHeader
	  	*/
  	   final protected JTunesTableHeader getJTunesTableHeader() {
  	       
  	       if (jTunesTableHeader == null)
  	       
  	          jTunesTableHeader = new JTunesTableHeader(true); 
             
             
            return jTunesTableHeader;
  	   }
  	   
  	   
  	   /**
	  	* Obtiene una referencia para configurar un estilo de tabla  
	  	* llamado JTunesTable
	  	*
	  	* @return jTunesHeaderRenderer
	  	*/
  	   final protected JTunesHeaderRenderer getJTunesHeaderRenderer() {
  	       
  	     if (jTunesHeaderRenderer == null)
  	    
  	        jTunesHeaderRenderer = new JTunesHeaderRenderer();
  	                
  	   	  return jTunesHeaderRenderer;
  	   
  	   }
  	   
  	   
  	    /**
	  	* Obtiene una un tipo de letra para los componentes que posean 
	  	* una lista de valores
	  	*
	  	* @return jTunesHeaderRenderer
	  	*/
  	   final protected Font getLetraListaDeValores() {
  	   	  
  	   	  return letraListaDeValores;
  	   	  
  	   }
  	   
  	   
  	   final protected Border getEtchedBorder() {
  	   	    
  	   	    if (etchedBorder == null)
  	   	    
  	   	     etchedBorder = new EtchedBorder();
	         
	         return etchedBorder;

  	   	
  	   }
  	   
  	   final protected Border getBordeBotonesJToolbar() {
  	   	
	  	   	 if (dobleBorde == null) {
	  	   	 
	  	   	 
		  	   	 Border border = BorderFactory.createRaisedBevelBorder();
		         
		         dobleBorde = new CompoundBorder(border,border);
		     
		     }    
	         
	         return dobleBorde;

  	   	
  	   }
  	   
 }