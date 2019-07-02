
/**
 * Clase: MoodyBlueTheme
 * 
 * Copyrigth: Free
 */


package com.JASoft.componentes;

import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * Tema MoodyBlueTheme para las interfaces
 */
 
 final public class MoodyBlueTheme extends DefaultMetalTheme {
		
	 public String getName() {
	 	return "Moody Blues";
	 }
	 
	private final ColorUIResource primary1     = 
          new ColorUIResource  (0x0, 0x33, 0x99);
    private final ColorUIResource primary2     = 
           new ColorUIResource(0xD0, 0xDD, 0xFF);
    
    private final ColorUIResource primary3     = 
        new ColorUIResource(0x0, 0x99, 0xFF); 

    private final ColorUIResource secondary1   = 
           new ColorUIResource(0x6F, 0x6F, 0x6F);
    private final ColorUIResource secondary2   = 
            new ColorUIResource(0x9F, 0x9F, 0x9F);
    private final ColorUIResource secondary3   = 
           new ColorUIResource(0x1f, 0x7f, 0xDC);
           
    private FontUIResource MenuTextFont=new FontUIResource("Times new Roman",Font.BOLD,14);
    
    private FontUIResource WindowTitleFont=new FontUIResource("Times new Roman",Font.BOLD,16);


	protected ColorUIResource getPrimary1() { return primary1; }  
    protected ColorUIResource getPrimary2() { return primary2; } 
    protected ColorUIResource getPrimary3() { return primary3; } 
    
    protected ColorUIResource getSecondary1() { return secondary1; }
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
    
    public FontUIResource getMenuTextFont() { return MenuTextFont;}
	 
	public  FontUIResource getWindowTitleFont() {return WindowTitleFont;}
	 
	}
