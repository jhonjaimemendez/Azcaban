/*
 * 
 * Version : 1.0
 * 
 * Fecha: 02-10-2006
 * 
 * Copyright: JASoft
 */

package com.JASoft.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JTable;


/**
 * Esta clase se utiliza para Imprimir un JTable
 */

final public class Reportes implements Printable{
  
  
  private JTable tableView; //tabla a imprimir
  public JButton printButton;
  private String NumCom;//numero de comparendos se concatena con Pie1
  private String Pie1;//pie de pagina 1
  private String Pie2;//pie de pagina 2
  private String FechaElaboracion;//Encabezado 2
  private String Encabezado1; //Encabezado 1
  
  
  public Reportes(JTable tableView1,String Encabezado11,String Pie11,String NumCom1,String Pie21,String FechaElaboracion1) 
  {
  	
  	
  	Encabezado1=Encabezado11;//Encabezado 1
  	
  	Pie1=Pie11;//pie de pagina 1
  	
  	NumCom=NumCom1;
  	
  	Pie2=Pie21;//pie de pagina 2
  	
  	FechaElaboracion=FechaElaboracion1;//Encabezado 2
  	
    tableView=tableView1;//tabla a imprimir
    
    printButton=new JButton();
    
    
	//botton de impresión    
     printButton.addActionListener( new ActionListener(){
       
        public void actionPerformed(ActionEvent evt) {
          
          PrinterJob pj=PrinterJob.getPrinterJob();
          
          pj.setPrintable(Reportes.this);
          
          if(pj.printDialog())
          {          
          	
          	try{ 
            pj.print();
          	}catch (Exception PrintException) {}
          	
          }
          
          }
          
     });

      
 }

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException 
  {
     	Graphics2D  g2 = (Graphics2D) g;
     	g2.setFont(new Font("Times new Roman", Font.BOLD, 12));
     	g2.setColor(Color.black);
     	
          	
     	int fontHeight=g2.getFontMetrics().getHeight();
     	
     	int fontDesent=g2.getFontMetrics().getDescent();

     	//leave room for page number
     	double pageHeight = pageFormat.getImageableHeight()-fontHeight;
     	
     	double pageWidth =  pageFormat.getImageableWidth();
     	
     	double tableWidth = (double) tableView.getColumnModel().getTotalColumnWidth();
     	
     	double scale = 1; 
     	
     	if (tableWidth >= pageWidth) {
			scale =  pageWidth / tableWidth;
		}

     	double headerHeightOnPage=tableView.getTableHeader().getHeight()*scale;
     	
     	double tableWidthOnPage=tableWidth*scale;
     	
     	double oneRowHeight=(tableView.getRowHeight()+tableView.getRowMargin())*scale;
     	
     	int numRowsOnAPage=(int)((pageHeight-headerHeightOnPage)/oneRowHeight);
     	
     	double pageHeightForTable=oneRowHeight*numRowsOnAPage;
     	
     	int totalNumPages= (int)Math.ceil(((double)tableView.getRowCount())/numRowsOnAPage);
     	
     	if(pageIndex>=totalNumPages) {
            return NO_SUCH_PAGE;
     	}
		
    	g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY()+10);
        
		//Encabezado 1
		g2.setFont(new Font("Times new Roman", Font.BOLD, 8));
		g2.drawString(Encabezado1,2,-2); 
		
     	//Encabezado 2
     	g2.setFont(new Font("Times new Roman", Font.BOLD, 7));
        g2.drawString(FechaElaboracion,350,-2); 
        
     	//pie de pagina 1
     	g2.setFont(new Font("Times new Roman", Font.BOLD, 10));
     	g2.drawString(Pie1+NumCom,0, (int)(pageHeight+fontHeight-fontDesent-6));
		
		//pie de pagina 2
		g2.drawString(Pie2,260, (int)(pageHeight+fontHeight-fontDesent-6));
     	        	
		g2.translate(0f,headerHeightOnPage);
     	g2.translate(0f,-pageIndex*pageHeightForTable);

     	
     	if (pageIndex + 1 == totalNumPages) 
     	{
           int lastRowPrinted = numRowsOnAPage * pageIndex;
           int numRowsLeft = tableView.getRowCount() - lastRowPrinted;
           g2.setClip(0, (int)(pageHeightForTable * pageIndex),(int) Math.ceil(tableWidthOnPage),(int) Math.ceil(oneRowHeight * numRowsLeft));
     	}
     	else
     	{    
             g2.setClip(0, (int)(pageHeightForTable*pageIndex), (int) Math.ceil(tableWidthOnPage), (int) Math.ceil(pageHeightForTable));        
         
        }
        
        g2.scale(scale,scale);
        
     	//Se imprime el cuerpo de la tabla
     	tableView.setFont(new Font("Times new Roman", Font.BOLD, 6));
     	tableView.paint(g2);
     	
     	tableView.setFont(new Font("Times new Roman", Font.BOLD, 6));
     	g2.scale(1/scale,1/scale);
     	
     	g2.translate(0f,pageIndex*pageHeightForTable);
     	
     	g2.translate(0f, -headerHeightOnPage);
     	
     	g2.setClip(0, 0,(int) Math.ceil(tableWidthOnPage), (int)Math.ceil(headerHeightOnPage));
        
     	g2.scale(scale,scale);
     	
     	//Se imprime la cabecera de la tabla
     	tableView.getTableHeader().paint(g2);
     	
       	return Printable.PAGE_EXISTS;
   }


}



