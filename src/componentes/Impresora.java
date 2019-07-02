/**
 * Clase: Impresora
 * 
 * @version  1.0
 * 
 * @since 16-09-2006
 * 
 * @autor JASoft
 *
 * Copyrigth: JASoft
 */
 
 
package com.JASoft.componentes;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.awt.Toolkit;

import java.util.Vector;


/**
 * Clase que permite imprimir un informe configurado por el usuario
 */
	
final public class Impresora{
 		
private Font fuente1;
 	
 	private Font fuente2;
	private PrintJob pj;	
	private Graphics pagina;
	double pos_hoja_X;
	double pos_hoja_Y;
	int valor_X;
	int valor_Y;
	
	double pos_hoja_EtX;
	double pos_hoja_EtY;
	int valor_EtX;
	int valor_EtY;
	double pos_hoja_ValX;
	double pos_hoja_ValY;
	int valor_ValX;
	int valor_ValY;
  
 
public void ImprimirFormulario(String Datos[][]) {
		
double escala=9.5/270.0;
	    
	
		pj = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "Licencia", null);
    
    
		try {
				pagina = pj.getGraphics();
				
		    
		    	
				fuente2= new Font("Times new Roman", Font.BOLD, 8);
				pagina.setFont(fuente2);
				
				for(int i=0;i<Datos.length;i++)
				{
					double pos_hoja_X=Double.parseDouble(Datos[i][1]);
					double pos_hoja_Y=Double.parseDouble(Datos[i][2]);
					int valor_X=(int)(pos_hoja_X/escala);
					int valor_Y=(int)(pos_hoja_Y/escala);
				    
				    pagina.drawString(Datos[i][0],valor_X,valor_Y);
					
				}
	
			
			pagina.dispose();
			pj.end();
			
		}catch(Exception e) {
			System.out.println("LA IMPRESION HA SIDO CANCELADA..."+e);
		}
}

 
public void ImprimirNormal(String Datos[][],String DatosTramites[][],String DatosFinal[][]) {

double escala=9.5/270.0;

	
		pj = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "Licenciap", null);
    
    
		try {
				pagina = pj.getGraphics();
				
		        fuente1= new Font("Courier New", Font.BOLD, 14);
				fuente2= new Font("Courier New", Font.BOLD, 8);
				
				for(int i=0;i<Datos.length;i++)
				{
    				double pos_hoja_EtX=Double.parseDouble(Datos[i][1]);
					double pos_hoja_EtY=Double.parseDouble(Datos[i][2]);
					int valor_EtX=(int)(pos_hoja_EtX/escala);
					int valor_EtY=(int)(pos_hoja_EtY/escala);
					
					double pos_hoja_ValX=Double.parseDouble(Datos[i][4]);
					double pos_hoja_ValY=Double.parseDouble(Datos[i][5]);
					int valor_ValX=(int)(pos_hoja_ValX/escala);
					int valor_ValY=(int)(pos_hoja_ValY/escala);
					
					if(i==0||i==1)
					pagina.setFont(fuente1);
					else
					pagina.setFont(fuente2);
					
					pagina.drawString(Datos[i][0],valor_EtX,valor_EtY);
					pagina.drawString(Datos[i][3],valor_ValX,valor_ValY);
				}
				
				pagina.setFont(fuente2);
				for(int i=0;i<DatosTramites.length;i++)
				{
    				if(DatosTramites[i][1]!=null&&DatosTramites[i][2]!=null&&DatosTramites[i][4]!=null&&DatosTramites[i][5]!=null)
    				{
    				double pos_hoja_EtX=Double.parseDouble(DatosTramites[i][1]);
					double pos_hoja_EtY=Double.parseDouble(DatosTramites[i][2]);
					int valor_EtX=(int)(pos_hoja_EtX/escala);
					int valor_EtY=(int)(pos_hoja_EtY/escala);
					
					double pos_hoja_ValX=Double.parseDouble(DatosTramites[i][4]);
					double pos_hoja_ValY=Double.parseDouble(DatosTramites[i][5]);
					int valor_ValX=(int)(pos_hoja_ValX/escala);
					int valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(DatosTramites[i][0],valor_EtX,valor_EtY);
					pagina.drawString(DatosTramites[i][3],valor_ValX,valor_ValY);
					}
				}
				
				pagina.setFont(fuente2);
				for(int i=0;i<DatosFinal.length;i++)
				{
    		
					double pos_hoja_EtX=Double.parseDouble(DatosFinal[i][1]);
					double pos_hoja_EtY=Double.parseDouble(DatosFinal[i][2]);
					int valor_EtX=(int)(pos_hoja_EtX/escala);
					int valor_EtY=(int)(pos_hoja_EtY/escala);
					
					double pos_hoja_ValX=Double.parseDouble(DatosFinal[i][4]);
					double pos_hoja_ValY=Double.parseDouble(DatosFinal[i][5]);
					int valor_ValX=(int)(pos_hoja_ValX/escala);
					int valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(DatosFinal[i][0],valor_EtX,valor_EtY);
					pagina.drawString(DatosFinal[i][3],valor_ValX,valor_ValY);
				}
			
			pagina.dispose();
			pj.end();
			
		}catch(Exception e) {
			System.out.println("LA IMPRESION HA SIDO CANCELADA..."+e);
		}
		
}

public void ImprimirNormal(String Datos[][],String DatosPropietarios[][],String EncTramites[][],String DatosTramites[][],String DatosFinal[][]) {

double escala=9.5/270.0;

	
		pj = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "Licencia", null);
    
    
		try {
				pagina = pj.getGraphics();
			
				fuente1= new Font("Courier New", Font.BOLD, 14);
				fuente2= new Font("Courier New", Font.BOLD, 8);
				
				for(int i=0;i<Datos.length;i++)
				{
    		
					pos_hoja_EtX=Double.parseDouble(Datos[i][1]);
					pos_hoja_EtY=Double.parseDouble(Datos[i][2]);
					valor_EtX=(int)(pos_hoja_EtX/escala);
					valor_EtY=(int)(pos_hoja_EtY/escala);
					
					pos_hoja_ValX=Double.parseDouble(Datos[i][4]);
					pos_hoja_ValY=Double.parseDouble(Datos[i][5]);
					valor_ValX=(int)(pos_hoja_ValX/escala);
					valor_ValY=(int)(pos_hoja_ValY/escala);
					
					if(i==0||i==1)
					pagina.setFont(fuente1);
					else
					pagina.setFont(fuente2);
					
					pagina.drawString(Datos[i][0],valor_EtX,valor_EtY);
					pagina.drawString(Datos[i][3],valor_ValX,valor_ValY);
				}
				
				pagina.setFont(fuente2);
				for(int i=0;i<DatosPropietarios.length;i++)
				{
    				if(DatosPropietarios[i][1]!=null&&DatosPropietarios[i][2]!=null&&DatosPropietarios[i][4]!=null&&DatosPropietarios[i][5]!=null)
    				{
    				pos_hoja_EtX=Double.parseDouble(DatosPropietarios[i][1]);
					pos_hoja_EtY=Double.parseDouble(DatosPropietarios[i][2]);
					valor_EtX=(int)(pos_hoja_EtX/escala);
					valor_EtY=(int)(pos_hoja_EtY/escala);
					
					pos_hoja_ValX=Double.parseDouble(DatosPropietarios[i][4]);
					pos_hoja_ValY=Double.parseDouble(DatosPropietarios[i][5]);
					valor_ValX=(int)(pos_hoja_ValX/escala);
					valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(DatosPropietarios[i][0],valor_EtX,valor_EtY);
					pagina.drawString(DatosPropietarios[i][3],valor_ValX,valor_ValY);
					}
				}
				
				pagina.setFont(fuente2);
				for(int i=0;i<EncTramites.length;i++)
				{
    				if(EncTramites[i][1]!=null&&EncTramites[i][2]!=null&&EncTramites[i][4]!=null&&EncTramites[i][5]!=null)
    				{
    				pos_hoja_EtX=Double.parseDouble(EncTramites[i][1]);
					pos_hoja_EtY=Double.parseDouble(EncTramites[i][2]);
					valor_EtX=(int)(pos_hoja_EtX/escala);
					valor_EtY=(int)(pos_hoja_EtY/escala);
					
					pos_hoja_ValX=Double.parseDouble(EncTramites[i][4]);
					pos_hoja_ValY=Double.parseDouble(EncTramites[i][5]);
					valor_ValX=(int)(pos_hoja_ValX/escala);
					valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(EncTramites[i][0],valor_EtX,valor_EtY);
					pagina.drawString(EncTramites[i][3],valor_ValX,valor_ValY);
					}
				}
				
			
				pagina.setFont(fuente2);
				for(int i=0;i<DatosTramites.length;i++)
				{
    				if(DatosTramites[i][1]!=null&&DatosTramites[i][2]!=null&&DatosTramites[i][4]!=null&&DatosTramites[i][5]!=null)
    				{
    				pos_hoja_EtX=Double.parseDouble(DatosTramites[i][1]);
					pos_hoja_EtY=Double.parseDouble(DatosTramites[i][2]);
					valor_EtX=(int)(pos_hoja_EtX/escala);
					valor_EtY=(int)(pos_hoja_EtY/escala);
					
					pos_hoja_ValX=Double.parseDouble(DatosTramites[i][4]);
					pos_hoja_ValY=Double.parseDouble(DatosTramites[i][5]);
					valor_ValX=(int)(pos_hoja_ValX/escala);
					valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(DatosTramites[i][0],valor_EtX,valor_EtY);
					pagina.drawString(DatosTramites[i][3],valor_ValX,valor_ValY);
					}
				}
				
				pagina.setFont(fuente2);
				for(int i=0;i<DatosFinal.length;i++)
				{
    		
					pos_hoja_EtX=Double.parseDouble(DatosFinal[i][1]);
					pos_hoja_EtY=Double.parseDouble(DatosFinal[i][2]);
					valor_EtX=(int)(pos_hoja_EtX/escala);
					valor_EtY=(int)(pos_hoja_EtY/escala);
					
					pos_hoja_ValX=Double.parseDouble(DatosFinal[i][4]);
					pos_hoja_ValY=Double.parseDouble(DatosFinal[i][5]);
					valor_ValX=(int)(pos_hoja_ValX/escala);
					valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(DatosFinal[i][0],valor_EtX,valor_EtY);
					pagina.drawString(DatosFinal[i][3],valor_ValX,valor_ValY);
				}
				
			
			pagina.dispose();
			pj.end();
			
		}catch(Exception e) {
			System.out.println("LA IMPRESION HA SIDO CANCELADA..."+e);
		}
		
}



public void ImprimirNormal(Vector VDatos,String DatosTramites[][],String DatosFinal[][]) {

double escala=9.5/270.0;

	
		pj = Toolkit.getDefaultToolkit().getPrintJob(new Frame(), "Licenciap", null);
        
        String Datos[][];
    
		try {
				pagina = pj.getGraphics();
				
		        fuente1= new Font("Courier New", Font.BOLD, 14);
				fuente2= new Font("Courier New", Font.BOLD, 8);
				
				for(int i=0;i<VDatos.size();i++)
				{
					Datos = (String[][]) VDatos.elementAt(i);
    				
    				double pos_hoja_EtX=Double.parseDouble(Datos[0][1]);
					double pos_hoja_EtY=Double.parseDouble(Datos[0][2]);
					int valor_EtX=(int)(pos_hoja_EtX/escala);
					int valor_EtY=(int)(pos_hoja_EtY/escala);
					
					double pos_hoja_ValX=Double.parseDouble(Datos[0][4]);
					double pos_hoja_ValY=Double.parseDouble(Datos[0][5]);
					int valor_ValX=(int)(pos_hoja_ValX/escala);
					int valor_ValY=(int)(pos_hoja_ValY/escala);
					
					if(i==0||i==1)
					pagina.setFont(fuente1);
					else
					pagina.setFont(fuente2);
					
					pagina.drawString(Datos[0][0],valor_EtX,valor_EtY);
					pagina.drawString(Datos[0][3],valor_ValX,valor_ValY);
				}
				
				pagina.setFont(fuente2);
				for(int i=0;i<DatosTramites.length;i++)
				{
    				if(DatosTramites[i][1]!=null&&DatosTramites[i][2]!=null&&DatosTramites[i][4]!=null&&DatosTramites[i][5]!=null)
    				{
    				double pos_hoja_EtX=Double.parseDouble(DatosTramites[i][1]);
					double pos_hoja_EtY=Double.parseDouble(DatosTramites[i][2]);
					int valor_EtX=(int)(pos_hoja_EtX/escala);
					int valor_EtY=(int)(pos_hoja_EtY/escala);
					
					double pos_hoja_ValX=Double.parseDouble(DatosTramites[i][4]);
					double pos_hoja_ValY=Double.parseDouble(DatosTramites[i][5]);
					int valor_ValX=(int)(pos_hoja_ValX/escala);
					int valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(DatosTramites[i][0],valor_EtX,valor_EtY);
					pagina.drawString(DatosTramites[i][3],valor_ValX,valor_ValY);
					}
				}
				
				pagina.setFont(fuente2);
				for(int i=0;i<DatosFinal.length;i++)
				{
    		
					double pos_hoja_EtX=Double.parseDouble(DatosFinal[i][1]);
					double pos_hoja_EtY=Double.parseDouble(DatosFinal[i][2]);
					int valor_EtX=(int)(pos_hoja_EtX/escala);
					int valor_EtY=(int)(pos_hoja_EtY/escala);
					
					double pos_hoja_ValX=Double.parseDouble(DatosFinal[i][4]);
					double pos_hoja_ValY=Double.parseDouble(DatosFinal[i][5]);
					int valor_ValX=(int)(pos_hoja_ValX/escala);
					int valor_ValY=(int)(pos_hoja_ValY/escala);
					
			
					pagina.drawString(DatosFinal[i][0],valor_EtX,valor_EtY);
					pagina.drawString(DatosFinal[i][3],valor_ValX,valor_ValY);
				}
			
			pagina.dispose();
			pj.end();
			
		}catch(Exception e) {
			System.out.println("LA IMPRESION HA SIDO CANCELADA..."+e);
		}
		
}



}