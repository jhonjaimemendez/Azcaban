/*
 * Clase: CrearJFrame
 * 
 * Version : 1.0
 * 
 * Fecha: 18-10-2005
 * 
 * Copyright: Ing.  Jhon Mendez
 */
 
 package com.JASoft.componentes;
 
 import java.awt.event.KeyAdapter;
 import java.awt.event.KeyEvent;
 
 import javax.swing.JDialog;
 import javax.swing.JTextField;
 import javax.swing.JComboBox;
 import javax.swing.DefaultCellEditor;
 
 import com.JASoft.componentes.CrearJFrame;
 import com.JASoft.componentes.ConectarMySQL;
 
 /**
   * Esta clase configura los eventos para un JTextField que posea
   * Lista de valores
   */
 
 final public class ListaValor extends KeyAdapter {
          	
        
        private JDialog dialogoListaValores;
        
        private Object [][] camposRetornos;
        
        private CrearJFrame crearJFrame;
        
        private ConectarMySQL conectarMySQL;
        
        private String sentencia; 
        
        private JTextField campoLista;
        
        private JComboBox jComboDepartamentos; //Se espefica solo para la consulta de ciudades
        
        private int posicionX;
        private int posicionY;
        private int ancho;
        private int[] ocultarColumnas;
        private int indiceArrayDialogo;
        private int tamañoArrayDialogo;
        
        
        /**
          * Constructor General
          */
        public ListaValor(String sentencia,Object [][] camposRetornos,CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL,int ancho) {
        	
        	this.camposRetornos = camposRetornos;
        	this.crearJFrame = crearJFrame;
        	this.sentencia = sentencia;
        	this.campoLista = campoLista;
        	this.posicionX = campoLista.getX();
        	this.posicionY = campoLista.getY() + 30;
        	this.conectarMySQL = conectarMySQL;
            this.ancho = ancho;
         
        }  
        
        
         /**
          * Constructor General
          */
        public ListaValor(String sentencia,Object [][] camposRetornos,CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL,int[] ocultarColumnas,int ancho) {
        	
        	this.camposRetornos = camposRetornos;
        	this.crearJFrame = crearJFrame;
        	this.sentencia = sentencia;
        	this.campoLista = campoLista;
        	this.posicionX = campoLista.getX();
        	this.posicionY = campoLista.getY() + 30;
        	this.conectarMySQL = conectarMySQL;
        	this.ocultarColumnas = ocultarColumnas;
        	this.ancho = ancho;
        	
        
        	
        }  
        
        public ListaValor(String sentencia,Object [][] camposRetornos,CrearJFrame crearJFrame,JTextField campoLista, int posicionX, int posicionY, int ancho  ) {
        	
        	this.camposRetornos = camposRetornos;
        	this.crearJFrame = crearJFrame;
        	this.sentencia = sentencia;
        	this.campoLista = campoLista;
        	this.posicionX = posicionX;
        	this.posicionY = posicionY;
        	this.ancho = ancho;
        	
        	
        }  
        
       public ListaValor(String sentencia,JComboBox jComboDepartamentos,Object [][] camposRetornos,CrearJFrame crearJFrame,JTextField campoLista, ConectarMySQL conectarMySQL,int[] ocultarColumnas,int ancho) {
        	
        	
        	this.camposRetornos = camposRetornos;
        	this.ocultarColumnas = ocultarColumnas;
        	this.conectarMySQL = conectarMySQL;
        	this.jComboDepartamentos = jComboDepartamentos;
        	this.crearJFrame = crearJFrame;
        	this.sentencia = sentencia;
        	this.campoLista = campoLista;
            this.posicionX = campoLista.getX();
        	this.posicionY = campoLista.getY() + 30;
        	this.ancho = ancho;
        	
        	
        	
        }
        
        
        public ListaValor(String sentencia,Object [][] camposRetornos,CrearJFrame crearJFrame,JTextField campoLista,ConectarMySQL conectarMySQL,int ancho,int indiceArrayDialogo,int tamañoArrayDialogo,int[] ocultarColumnas) {
     
         	this.camposRetornos = camposRetornos;
        	this.ocultarColumnas = ocultarColumnas;
        	this.conectarMySQL = conectarMySQL;
        	this.jComboDepartamentos = jComboDepartamentos;
        	this.crearJFrame = crearJFrame;
        	this.sentencia = sentencia;
        	this.campoLista = campoLista;
            this.posicionX = campoLista.getX();
        	this.posicionY = campoLista.getY() + 30;
        	this.ancho = ancho;
        	this.indiceArrayDialogo = indiceArrayDialogo;
        	this.tamañoArrayDialogo = tamañoArrayDialogo;
        	this.ocultarColumnas = ocultarColumnas;
 	
        	 
        }
        
        public ListaValor() {
        	
        }
        
        
        
        
        // Se valida que solo se digite mayuscula
    	 public void  keyTyped (KeyEvent k) {
    	 	 
    	 	  k.setKeyChar(String.valueOf(k.getKeyChar()).toUpperCase().charAt(0));
          
          }
          
          
           //Se utiliza para una lista incremental
          public void keyReleased(KeyEvent k) {
    
         	 if (k.getKeyChar() != k.VK_ESCAPE && k.getKeyChar() != k.VK_ENTER && k.getKeyCode() != 9) 
         	     
         	     mostrarListaValores();
         	     
          }	     
         	   
         
         
         public void mostrarListaValores() {
         
         	 
		      if (jComboDepartamentos == null) {
		     
		         	 if (ocultarColumnas == null) {
		         	 
		     
		         	     if (ancho == 0) {
		         	  
		         	        if (tamañoArrayDialogo == 0)
		         	            
		         	            crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
		         	                                      conectarMySQL,camposRetornos,posicionX,posicionY,campoLista);
		         	                                      
		         	         else {
		         	         
		         	                  
		         	            crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
		         	                                      conectarMySQL,camposRetornos,posicionX,posicionY,campoLista,indiceArrayDialogo,tamañoArrayDialogo,null);
		         	          
		         	         } 
		         	                               
		         	     }else                              
		         	        
		     	              crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
		     	                                   conectarMySQL,camposRetornos,posicionX,posicionY,campoLista,ancho);
		     	                     
		     	    
		         	 }  else
		         	      
		         	       if (tamañoArrayDialogo == 0) {
		         	       
		         	         if (ancho == 0) 
		         	     
		         	             crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
		         	                                      conectarMySQL,camposRetornos,posicionX,posicionY,campoLista,ocultarColumnas);
		       	 	         
		       	 	         else                          
		       	 	          
			   	 	               crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
			     	                                      conectarMySQL,camposRetornos,posicionX,posicionY,campoLista,ocultarColumnas,ancho);
   	 	     
   	 	                  } else
                  
                                 crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
         	                                      conectarMySQL,camposRetornos,posicionX,posicionY,campoLista,indiceArrayDialogo,tamañoArrayDialogo,ocultarColumnas);
         	
   	 	     
   	 	      } else {
   	 	      	
   	 	      	
	  	                   crearJFrame.listarValores(sentencia + "M.CodigoDpto = " + crearJFrame.departamentos[jComboDepartamentos.getSelectedIndex()][0] +
		     	      	                             " and M.municipio like '" + (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'"
		     	      	                             ,conectarMySQL,camposRetornos,posicionX,posicionY,campoLista,ocultarColumnas,ancho);
		         	
	 
	  	
   	 	      	
   	 	      	
   	 	      }
   	 	         	 
   	 	         
         }
         
         
         public void mostrarListaValores(int posicionX, int posicionY,Object [][] camposRetornos,DefaultCellEditor editor,DefaultCellEditor editorRetorno,int filaSeleccionada) {
     
             crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
		         	                                  conectarMySQL,camposRetornos,posicionX,posicionY,editor,editorRetorno,indiceArrayDialogo,tamañoArrayDialogo,filaSeleccionada,ocultarColumnas);
		         	                                      
		         	                                      
		         	                                      
		}
		
		public void mostrarListaValores(int posicionX, int posicionY,String sentencia,Object [][] camposRetornos,DefaultCellEditor editor,DefaultCellEditor editorRetorno,int filaSeleccionada) {

             crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
		         	                                  conectarMySQL,camposRetornos,posicionX,posicionY,editor,editorRetorno,indiceArrayDialogo,tamañoArrayDialogo,filaSeleccionada,ocultarColumnas);
		         	                                      
		         	                                      
		         	                                      
		}
		
		 public void mostrarListaValores(int posicionY) {
		 	
		 	
		 	    crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
		         	                                      conectarMySQL,camposRetornos,posicionX,posicionY,campoLista,indiceArrayDialogo,tamañoArrayDialogo,ocultarColumnas);
		 	
		 }
		 
		 
		 
		 public void mostrarListaValores(int posicionX,int posicionY) {
		 	
 	       crearJFrame.listarValores(sentencia +  (campoLista.getText().isEmpty() ? "" : campoLista.getText())  +"%'",
         	                                      conectarMySQL,camposRetornos,posicionX,posicionY,campoLista);
         	   
		 	
		 }
          	 	  
 }
 