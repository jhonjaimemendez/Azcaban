package com.JASoft.componentes;

import java.awt.Color;
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
  * Tema OfficeXPTheme 
  */


 final public class OfficeXPTheme extends DefaultMetalTheme {

    private ColorUIResource background = new ColorUIResource(new Color(216, 231, 252));
    
    public String getName() {
        return "OfficeXPTheme";
    }
            
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getMenuBackground()
     */
    public ColorUIResource getMenuBackground() {
        return background;
    }
    
    public ColorUIResource getBackground(){
        return background;
    }
    
    /* (non-Javadoc)
     * @see com.jgoodies.plaf.plastic.theme.ExperienceBlue#getSecondary2()
     */
    protected ColorUIResource getSecondary3() throws IllegalArgumentException {
        return background;
    }
    
    
    /* (non-Javadoc)
     * @see javax.swing.plaf.metal.MetalTheme#getWindowTitleBackground()
     */
    public ColorUIResource getWindowTitleBackground(){
        return background;
    }
    
    public FontUIResource getUserTextFont() { return new FontUIResource("Arial",Font.BOLD,12); }; 

}


