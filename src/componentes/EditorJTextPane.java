/**
 * Clase: EditorJTextPane
 * 
 * @version  1.0
 * 
 * @since 18-10-2005
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
 
 package com.JASoft.componentes;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


/**
 * Esta clase permite configurar un JTextPane para mostralos como vista previa para la impresion
 */
 
 
 final  public class EditorJTextPane  {
 	
     static final String titulo1 = "Titulo1";
     
      static final String titulo2 = "Titulo2";
     
     static final String etiquetas = "Etiquetas";
     
     StyleContext sc = new StyleContext();
    
     DefaultStyledDocument doc = new DefaultStyledDocument(sc);
     
     Paragraph[] contenido;
     
     public JTextPane pane;
     
     int indice = 0;
    

 	 public EditorJTextPane(int filas) {
 	 	
 	 	 pane = new JTextPane(doc);
 	 	 
 	     contenido = new Paragraph[filas];
 	
 	     crearEstilos();
 	
 	      	
 	 }
 	 
 	 public void configurarTextPane(int filas) {
 	 	
 	 	    doc = new DefaultStyledDocument(sc);
 	 	   
 	 	    pane.setDocument(doc);
 	 	
 	 	    contenido = new Paragraph[filas];
 	 	   
 	 	    indice = 0;
 	 	   
 	 	
 	 }
 	 
 	 
 	 //*************************************************************************************************************
 	 
 	 public void adicionarLinea(String linea, int espacios,int estilo) {

 	 	// Se configura el estilo
 	 	    String estiloF = "";
	        
	        // Se verifica el estilo que se desea 
	        switch (estilo) {
	         
	          case 0:
	             estiloF = titulo1; 
	          break;
	          
	          case 1:
	             estiloF = titulo2; 
	          break;
	         
	           case 2:
	             estiloF = etiquetas; 
	          break;
	             
	            
	        }
	        
	        
	      contenido[indice] = new Paragraph(estiloF, new Run[] { new Run(null,obtenerLineas(espacios)+linea) });
	      indice++; 
	      
 	 }
 	 
 	 //*************************************************************************************************************
 	 
 	 public void adicionarTextPane() {
 	 	
		try {
		          
		     // Se adiciona el texto
		     addText(sc.getStyle(titulo1), contenido);
		      
		    } catch (Exception e) {
		      System.out.println("Exception when constructing document: " + e);
		      
		    } 	 	
 	 	
 	 }
 	 
 	 //*************************************************************************************************************
 	 
 	 public void crearEstilos() {
 	 	
 	        //Estilo numero 1
 	        
	        // Create and add the heading style
            Style styleTitulo1 = sc.addStyle(titulo1, null);
            StyleConstants.setForeground(styleTitulo1, Color.black);
            StyleConstants.setFontSize(styleTitulo1, 14);
		    StyleConstants.setFontFamily(styleTitulo1, "Courier New");
		    StyleConstants.setBold(styleTitulo1, true);
		    StyleConstants.setAlignment(styleTitulo1,4);
		    

 	 	     //Se configura el estilo 2 
 	   	    Style styleTitulo2 = sc.addStyle(titulo2, null);
            StyleConstants.setForeground(styleTitulo2, Color.black);
            StyleConstants.setFontSize(styleTitulo2, 14);
		    StyleConstants.setFontFamily(styleTitulo2, "Courier New");
		    StyleConstants.setBold(styleTitulo2, false);
		    StyleConstants.setAlignment(styleTitulo2,4);
		   
 	       
	         //Se configura el estilo 2 
 	   	    Style styleTitulo3 = sc.addStyle(etiquetas, null);
            StyleConstants.setForeground(styleTitulo3, Color.black);
            StyleConstants.setFontSize(styleTitulo3, 14);
		    StyleConstants.setFontFamily(styleTitulo3, "Courier New");
		    StyleConstants.setBold(styleTitulo3, false);
		    StyleConstants.setAlignment(styleTitulo3,0);
		   
 	       
	    
 	 }
 	 
 	 //*************************************************************************************************************
 	 
      public  void addText(Style logicalStyle, Paragraph[] content) {
	       
	       // The outer loop adds paragraphs, while the
	       // inner loop adds character runs.
	       //pane.setEditable(true);
	        
		    int paragraphs = content.length;
		    
		    for (int i = 0; i < paragraphs; i++) {
		    	
		      Run[] runs = content[i].content;
		      for (int j = 0; j < runs.length; j++) {
		        pane.setCharacterAttributes(
		            runs[j].styleName == null ? SimpleAttributeSet.EMPTY
		                : sc.getStyle(runs[j].styleName), true);
		        pane.replaceSelection(runs[j].content);
		      }
		
		      // At the end of the paragraph, add the logical style and
		      // any overriding paragraph style and then terminate the
		      // paragraph with a newline.
		      pane.setParagraphAttributes(SimpleAttributeSet.EMPTY, true);
		
		      if (logicalStyle != null) {
		        pane.setLogicalStyle(logicalStyle);
		        
		      }
		
		      if (content[i].styleName != null) {
		        pane.setParagraphAttributes(sc.getStyle(content[i].styleName),
		            false);
		            
		      }
		
		      if (i != paragraphs - 1) {
		        pane.replaceSelection("\n");
		      }
		      
		      
		    }
		    
		    
		     	 	 	  
 
       }

 	 
 	 //*****************************************************************************************************************
 	 
 	 static public String obtenerLineas(int linea) {
 	 	
 	 	String separador = "";
 	 	
 	 	for (int i = 0; i < linea; i++)
 	 	   separador += "\n";
 	 	  
 	 	return separador;
 	 }
 	 
 	 
 	 //*************************************************************************************************************
 	 
 	 public void generarLineaGuion(int espacios) {
 	 	
 	 	final String guiones ="-----------------------------------------------------------------------------------------------";
 	 	
        adicionarLinea(guiones,espacios,0);
 	 	
 	 	
 	 }
 	 
 	 
 	
 }
 
 
/*
 * Esta clase ayuda a configurar los parrafos en un JTabbePane
 */
class Run {

	    public Run(String styleName, String content) {
	      this.styleName = styleName;
	      this.content = content;
	    }
	
	    public String styleName;
	
	    public String content;
  }
  


/*
 * Esta clase configura los parrafos en un JTabbePane
 */
class Paragraph {
   	
    public Paragraph(String styleName, Run[] content) {
	      this.styleName = styleName;
	      this.content = content;
	}

     public String styleName;

     public Run[] content;
     
  }
 
 
 
 
