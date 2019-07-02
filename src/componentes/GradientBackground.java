 package com.JASoft.componentes;

import java.awt.Color;


/**
 * Esta clase especifica el color de cada fila de un objeto de tipo JTable  
 *
 */

final  public class GradientBackground {

	private Color darkGradientColor = null;

	private Color lightGradientColor = null;

	private Color textGradientColor = null;

	private double gradientHeightFactor = 1;

	private boolean lightToDark;

	/**
	 * Set the Gradient style of selected rows
	 */
	public GradientBackground() {
		
			setGradientSelectedColor(new Color(0, 154, 233), new Color(0, 92,
					211), Color.white);
			setGradientHeightFactor(1);
			setLightToDark(true);
		
	}

	/**
	 * sets whether this Gradient goes from light to dark, or dark to light
	 * @param b
	 */
	public void setLightToDark(boolean b) {
		lightToDark = b;
	}

	/**
	 * get the gradientHeightFactor, this is the height of the gradient without repeats,
	 * e.g. if 0.5 will go from light to dark to light.
	 * @return double
	 */
	public double getGradientHeightFactor() {
		return gradientHeightFactor;
	}

	/**
	 * set the gradientHeightFactor, this is the height of the gradient without repeats,
	 * e.g. if 0.5 will go from light to dark to light.
	 * @param gradientHeightFactor
	 */
	public void setGradientHeightFactor(double gradientHeightFactor) {
		this.gradientHeightFactor = gradientHeightFactor;
	}

	/**
	 * set All three colors needed to compose a gradient
	 * @param lightColor
	 * @param darkColor
	 * @param textColor
	 */
	public void setGradientSelectedColor(Color lightColor, Color darkColor,
			Color textColor) {
		darkGradientColor = darkColor;
		lightGradientColor = lightColor;
		textGradientColor = textColor;
	}

	/**
	 * Get the light color used in this gradient
	 * @return Color
	 */
	public Color getLightColor() {
		return lightGradientColor;
	}

	/**
	 * Get the dark color used in this gradient
	 * @return Color
	 */
	public Color getDarkColor() {
		return darkGradientColor;
	}
	
	/**
	 * True if the gradient goes from light to dark, or dark to light
	 * @return boolean
	 */
	public boolean isLightToDark() {
		return lightToDark;
	}

	/**
	 * get the Color of text that will be shown on top of this gradient
	 * @return Color
	 */
	public Color getTextGradientColor() {
		return textGradientColor;
	}

}
