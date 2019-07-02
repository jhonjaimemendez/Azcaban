package  com.JASoft.componentes;

import java.awt.Font;


/**
 * 
 * Esta clase permite definir los tipos de letras para los encabezados y
 * los datos de las tablas
 */

public class UiUtils {
	
	/**
	 * Changes the style of the font to be Font.BOLD, Font.ITALICS etc
	 * @param f
	 * @param fontStyles
	 * @return Font
	 */
	public static Font changeFontStyle(Font f, int ... fontStyles){
		int style = f.getStyle();
		for (int fontStyle : fontStyles) {
			style+=fontStyle;
		}
		return f.deriveFont(style);
	}
	
	/**
	 * returns the font with the new size
	 * @param f
	 * @param size
	 * @return Font
	 */
	public static Font changeFontSize(Font f, float size) {
		return f.deriveFont(size);
	}
}
