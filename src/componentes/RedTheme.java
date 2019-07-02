
package com.JASoft.componentes;


 
import java.awt.Font;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * Tema RedTheme
 */


final public class RedTheme extends DefaultMetalTheme {

  public String getName() { return "Mars"; }

  private final ColorUIResource primary1 = new ColorUIResource(153, 102, 102);
  private final ColorUIResource primary2 = new ColorUIResource(204, 153, 153);
  private final ColorUIResource primary3 = new ColorUIResource(255, 204, 204);

 private final ColorUIResource secondary3 = new ColorUIResource(204, 153, 153); 

  protected ColorUIResource getPrimary1() { return primary1; }
  protected ColorUIResource getPrimary2() { return primary2; }
  protected ColorUIResource getPrimary3() { return primary3; }
  
   protected ColorUIResource getSecondary3() { return secondary3; }
   
   public FontUIResource getUserTextFont() { return new FontUIResource("Arial",Font.BOLD,12); }; 
  
}
