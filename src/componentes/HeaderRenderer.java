/*
 * Clase: HeaderRenderer
 * 
 * Version : 1.0
 * 
 * Fecha: 17-02-2006
 * 
 * Copyright: Ing.  Jhon Mendez
 */

package com.JASoft.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;


/*
 * Esta clase configura la posicion y el borde de las columnas de un objeto de tipo JTable 
 * basados en el formato iTunes
 * 
 */
 
final public class HeaderRenderer
    extends DefaultTableCellRenderer
    
{
	
	
	private static final long serialVersionUID = 9172672736370429058L;

	private final Color selectedColor = Color.BLACK;

	// the colors needed to paint the background of cells that are not selected
	// it allows alternating rows to be differing colors
	private final Color unSelectedColorBlue = new Color(237, 243, 254);

	private final Color unSelectedColorWhite = Color.WHITE;

	// use system color
	private boolean systemColor = true;

	// color used for highlighting a selected row
	private  static Color systemHighlightColor;

	// color used for text in a selected row
	private static Color systemHighlightTextColor;

    private static FontUIResource font = new FontUIResource("ARIAL",Font.PLAIN,12);
   
	// is this row seleected
	private boolean selected = false;

	// gradient selected row
	private static GradientBackground gradientBackground = new GradientBackground();

	private static GradientPaint gradientPaint;
	
	private static GradientPaint gradientPaintAlerta;
	
	private static GradientPaint gradientPaintPrecaucion;
	
	//Table que tiene el gradiente en un instante
	private JTable table;

    	
    public  HeaderRenderer(int alineacion)
    {
    
        setHorizontalAlignment(alineacion);
        
        setFont(font);
		
		setOpaque(true);
        
        systemColor = false;
	    
        
    }
    
   

    public void updateUI()
    {
        super.updateUI();
        //setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                       boolean selected, boolean focused, int row, int column)
    {
                
                this.table = table;
                
            	if (selected) {
					setBackground(selectedColor);
					setForeground(unSelectedColorWhite);
				} else {
					if (row % 2 == 0) {
						
						if (MetalLookAndFeel.getCurrentTheme().getName().equals("Ocean"))
						   setBackground(unSelectedColorBlue);
						else
						   setBackground(UIManager.getColor("TableHeader.background"));   
						   
					} else {
						setBackground(unSelectedColorWhite);
					}
					setForeground(selectedColor);
				}
				
				if (table.getValueAt(row, column) != null)
				   setText(" " + table.getValueAt(row, column).toString());
				
				else
				   setText(" ");
		
				this.selected = selected;
				         
		        setValue(value);
		 
		        return this;
   }
    
    
   	public static Color getTableHeaderColor() {
		return new Color(200,200,200);
	}
    
    

    
   public GradientPaint getGradientPaint() {
	    
	   	systemColor = false;
	   	
	   	
		// only creat the GradientPaint once
		if (gradientPaint == null) {
			Color light = gradientBackground.getLightColor();
			Color dark = gradientBackground.getDarkColor();

			setForeground(gradientBackground.getTextGradientColor());

			if (gradientPaint == null) {
				gradientPaint = new GradientPaint(0, 0, (gradientBackground
						.isLightToDark()) ? light : dark, 0,
						(int) (getHeight() * gradientBackground
								.getGradientHeightFactor()),
						(!gradientBackground.isLightToDark()) ? light : dark,
						true);
			}
		}
		return gradientPaint;
	}
	
	
	public GradientPaint getGradientPaintAlerta() {
	    
	   	systemColor = false;
	   	
	   	
		// only creat the GradientPaint once
		if (gradientPaintAlerta == null) {
		
			setForeground(gradientBackground.getTextGradientColor());

			if (gradientPaintAlerta == null) {
				gradientPaintAlerta = new GradientPaint(0, 0, Color.red, 0,
						(int) (getHeight() * gradientBackground
								.getGradientHeightFactor()),Color.red,true);
			}
		}
		return gradientPaintAlerta;
	}
	
	public GradientPaint getGradientPaintPrecaucion() {
	    
	   	systemColor = false;
	   	
	   	
		// only creat the GradientPaint once
		if (gradientPaintPrecaucion == null) {
		    
		    Color yellow = new Color (255,230,0);
			
			setForeground(gradientBackground.getTextGradientColor());

			if (gradientPaintPrecaucion == null) {
				gradientPaintPrecaucion = new GradientPaint(0, 0, yellow, 0,
						(int) (getHeight() * gradientBackground
								.getGradientHeightFactor()),yellow,true);
			}
		}
		return gradientPaintPrecaucion;
	}
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		// Determine background color.
		Color bgColor = getBackground();
		Rectangle2D drawRect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

		if (systemColor) {
			if (selected) {
				
				setForeground(systemHighlightTextColor);
				setBackground(systemHighlightColor);

			}
			super.paint(g);
			return;
		} else {
			if (selected) {
					
		         if (table.getName() != null && table.getName().equals("Rangos")) {
		         
			        	if (table.getValueAt(table.getSelectedRow(),5).equals("0"))
			               g2.setPaint(getGradientPaintAlerta());
			            
			            else {
			            
			             	
			             	 float total = Integer.parseInt(table.getValueAt(table.getSelectedRow(),4).toString());
			             	 float restantes = Integer.parseInt(table.getValueAt(table.getSelectedRow(),5).toString());
			             	 
			             	 if ((restantes/total) <= 0.2)
			             	    g2.setPaint(getGradientPaintPrecaucion());
			             	 
			             	 else
			             	   g2.setPaint(getGradientPaint()); 
			            }  	   
		             	   
		         }else 
		         	
		         	 if (table.getName() != null && table.getName().equals("RangosPlaca")) {
		         
			        	if (table.getValueAt(table.getSelectedRow(),6).equals("0"))
			               g2.setPaint(getGradientPaintAlerta());
			            
			            else {
			            
			             	
			             	 float total = Integer.parseInt(table.getValueAt(table.getSelectedRow(),5).toString());
			             	 float restantes = Integer.parseInt(table.getValueAt(table.getSelectedRow(),6).toString());
			             	 
			             	 if ((restantes/total) <= 0.2)
			             	    g2.setPaint(getGradientPaintPrecaucion());
			             	 
			             	 else
			             	   g2.setPaint(getGradientPaint()); 
			            }  	   
		             	   
		         }else 
		         	
		             g2.setPaint(getGradientPaint()); 
		             

				g2.fill(drawRect);
			} else {
				
				g2.setColor(bgColor);
				g2.fill(drawRect);
				setForeground(selectedColor);
			}

			setBackground(new Color(100, 100, 100, 0));
		}
		super.paintComponent(g);
	}
}

