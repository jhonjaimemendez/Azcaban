package com.JASoft.componentes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import java.io.IOException;

import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * 
 * Esta clase permite renderizar un objeto de tipo JTable, para que tenga una presentacion
 * estilo iTunes
 */

final public class JTunesHeaderRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 8012402500450473741L;

	/**
	 * The color of the header by default when unselected
	 */
	public static final int GRAY = 0;
	
	/**
	 * The color of the header by default if selected
	 */
	public static final int BLUE = 10;

	private int selectedColumn = -1;

	private int currentColumn = -1;

	private BufferedImage imageAscending;

	private BufferedImage imageDescending;

	private JTunesTableHeader header;
	
	/**
	 * Default Constructor
	 */
	public JTunesHeaderRenderer() {
	
		setFont(new Font("Arial", Font.BOLD,12));
		setHorizontalAlignment(0);
		loadArrowImages();
		
		
	}

	/**
	 * Set font of header text
	 * @param f if null uses default font
	 * @param size point size the Font should be
	 * @param style style of Font, Font.BOLD, Font.ITALICS etc
	 */
	public void setFont(Font f, int size, int... style) {
		Font font = UiUtils.changeFontStyle((f != null) ? f : getFont(), style);
		setFont(UiUtils.changeFontSize(font, size));
	}
	
	/**
	 * getTableHeaderColor, only shown if not gradient background
	 * @return Color
	 */
	public static Color getTableHeaderColor() {
		return new Color(200,200,200);
	}
	
	/**
	 * creates the GradientPaint for a table header
	 * @param height
	 * @param color
	 * @return GradientPaint
	 */
	public static GradientPaint createGradientTableHeader(int height, int color) {
		return new GradientPaint(0, 0, getLighterTableHeaderColor(color), 0,
				height, getDarkerTableHeaderColor(color), true);
	}

	/**
	 * The lighter table header used in its gradient background
	 * @param color
	 * @return Color
	 */
	private static Color getLighterTableHeaderColor(int color) {
		if (color == GRAY) {
			return new Color(241, 245, 250);
		} else {
			return new Color(194, 207, 221);
		}
	}
	
	/**
	 * The darker table header used in its gradient background
	 * @param color
	 * @return Color
	 */
	private static Color getDarkerTableHeaderColor(int color) {
		if (color == GRAY) {
			return new Color(188, 188, 188);
		} else {
			return new Color(127, 149, 179);
		}
	}
	
	/**
	 * Returns the component used for drawing the header cell.
	 * @param table the JTable that is asking the renderer to draw; can be null
     * @param value the value of the cell to be rendered. It is up to the specific renderer to interpret and draw the value. For example, if value is the string "true", it could be rendered as a string or it could be rendered as a check box that is checked. null is a valid value
	 * @param isSelected true if the cell is to be rendered with the selection highlighted; otherwise false
     * @param hasFocus if true, render cell appropriately. For example, put a special border on the cell, if the cell can be edited, render in the color used to indicate editing
     * @param row the row index of the cell being drawn. When drawing the header, the value of row is -1
     * @param column the column index of the cell being drawn
	 */
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

		setText(" " + value.toString());
		setToolTipText((String) value);

		header = (JTunesTableHeader) table.getTableHeader();
		header.setBackground(getTableHeaderColor());

		createHeaderBorder(column);

		int selectedValue = ((JTunesTableHeader) table.getTableHeader())
				.getSelectedColumn();
		selectedColumn = selectedValue;
		currentColumn = column;

		return this;
	}

	/**
	 * Invoked by Swing to draw components. Applications should not invoke paint directly, but should instead use the repaint method to schedule the component for redrawing.
	 * @param g the graphics context this component will draw in
	 */
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		Rectangle2D drawRect = new Rectangle2D.Double(0, 0, 
				getWidth(), getHeight());

		int columnColor = (selectedColumn == currentColumn && header.isColumnSelectionStyle()) ? BLUE
				: GRAY;

		g2.setPaint(JTunesHeaderRenderer.createGradientTableHeader(getHeight(), columnColor));

		g2.fill(drawRect);


		// if we are currently painting a selected column and we have a selection style
		if (selectedColumn == currentColumn && header.isColumnSelectionStyle()) {
			
			BufferedImage image = null;
			if (header.isAscending()) {
				
				image = imageAscending;
				
			} else {
				image = imageDescending;
			}
			int y = (getHeight() / 2) - 3;
			int x = g.getClipBounds().width - 10;
			
			g2
					.drawImage(image, x, y, image.getWidth(),
							image.getHeight(), null);
		} 
			
		setForeground(Color.BLACK);
		setBackground(new Color(100, 100, 100, 0));
		
		super.paintComponent(g);
	}

	private void createHeaderBorder(int column) {
		// this method draws a border around the header cell
		int top = 1;

		// only draws border on left when it is the first column
		// otherwise we get a double thickness line
		int left = (column == 0) ? 1 : 0;
		int bottom = 1;
		int right = 1;

		setBorder(BorderFactory.createMatteBorder(top, left, bottom, right,
				Color.GRAY));
	}

	private void loadArrowImages() {
		// the two images needed for the selection of columns, to show sorting order
		imageAscending = this.createImage("resources/up_arrow.png");
		imageDescending = this.createImage("resources/down_arrow.png");
	}

	private BufferedImage createImage(String source) {
		
		// creates an image from the input file
		Iterator readers = ImageIO.getImageReadersByFormatName("png");
		ImageReader reader = (ImageReader) readers.next();
		ImageInputStream iis = null;
		try {
			iis = ImageIO.createImageInputStream(UiUtils.class
					.getResourceAsStream(source));
			reader.setInput(iis, true);
			return reader.read(0);
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return null;
	}
	
	
}
