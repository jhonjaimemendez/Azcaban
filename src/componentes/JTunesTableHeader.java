package com.JASoft.componentes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


/**
 * 
 * Esta clase permite renderizar los encabezados de un JTable, permitiendo realizar ordenamientos
 * por los encabezados de cada columna
 */

final public class JTunesTableHeader extends JTableHeader {
	
	private static final long serialVersionUID = -2399755444752381373L;

	private int selectedColumn = -1;

	private int lastClickedColumn = -1;

	private boolean columnReordering = true;

	private boolean ascending = true;

	private boolean columnSelectionStyle = true;

	/**
	 * Default constructor
	 */
	public JTunesTableHeader(boolean columnSelectionStyle) {
		super();
	
		this.addMouseListener(new HeaderSelectionListener());
		this.setReorderingAllowed(false);
		this.columnSelectionStyle = columnSelectionStyle;
		
		
	}

	/**
	 * Whether the next column selected will be ascending
	 * @return boolean
	 */
	protected boolean isAscending() {
		return ascending;
	}

	/**
	 * Set whether the next column sorted will be ascending
	 * @param ascending
	 */
	protected void setAscending(boolean ascending) {
		this.ascending = ascending;
	}

	/**
	 * Get the currently selected column
	 * @return int
	 */
	public int getSelectedColumn() {
		return selectedColumn;
	}

	/**
	 * Set the currently selected column
	 * @param selectedColumn
	 */
	public void setSelectedColumn(int selectedColumn) {
		this.selectedColumn = selectedColumn;
		this.repaint();
	}

	// to allow the header to be selected we must detect mouse clicks
	private class HeaderSelectionListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
				JTableHeader header = table.getTableHeader();
				int column = header.columnAtPoint(e.getPoint());
				if (column != -1) {
					setSelectedColumn(column);
					if (columnReordering) {
						// clicking on the last column selected will be resorted
						// in the opposing order
						if (lastClickedColumn == column) {
							ascending = !ascending;
						} else {
							ascending = true;
						}
						lastClickedColumn = column;
						sortAllRowsBy((DefaultTableModel) table.getModel(),
								column, ascending);
					}
				}
		}

		// these methods are not needed, apart from to fufil the interface
		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mousePressed(MouseEvent arg0) {
		}

		public void mouseReleased(MouseEvent arg0) {
		}
	}

	/**
	 * Sort all rows in the model, by the colIndex in either ascending or
	 * descending order
	 * 
	 * @param model
	 * @param colIndex
	 * @param ascending
	 */
	@SuppressWarnings("unchecked")
	public void sortAllRowsBy(DefaultTableModel model, int colIndex,
			boolean ascending) {
				
		Vector data = model.getDataVector();
		ColumnSorter columnSorter = new ColumnSorter(colIndex, ascending);
		Collections.sort(data, columnSorter);
		model.fireTableStructureChanged();
	}

	private class ColumnSorter implements Comparator {
		// this is needed to sort the rows
		int colIndex;

		ColumnSorter(int colIndex, boolean ascending) {
			this.colIndex = colIndex;
		}

		@SuppressWarnings("unchecked")
		public int compare(Object a, Object b) {
			Vector v1 = (Vector) a;
			Vector v2 = (Vector) b;
			Object o1 = v1.get(colIndex);
			Object o2 = v2.get(colIndex);

			if (o1 instanceof String && ((String) o1).length() == 0) {
				o1 = null;
			}
			if (o2 instanceof String && ((String) o2).length() == 0) {
				o2 = null;
			}

			// Sort nulls so they appear last, regardless
			// of sort order
			if (o1 == null && o2 == null) {
				return 0;
			} else if (o1 == null) {
				return 1;
			} else if (o2 == null) {
				return -1;
			} else if (o1 instanceof Comparable) {
				if (ascending) {
					return ((Comparable) o1).compareTo(o2);
				} else {
					return ((Comparable) o2).compareTo(o1);
				}
			} else {
				if (ascending) {
					return o1.toString().compareTo(o2.toString());
				} else {
					return o2.toString().compareTo(o1.toString());
				}
			}
		}
	}

	/**
	 * returns columnSelectionStyle
	 * @return boolean
	 */
	public boolean isColumnSelectionStyle() {
		return columnSelectionStyle;
	}

	/**
	 * set the style of background on a column when selected
	 * @param columnSelectionStyle
	 */
	public void setColumnSelectionStyle(boolean columnSelectionStyle) {
		this.columnSelectionStyle = columnSelectionStyle;
	}
	
	
	public void borrarIconoSeleccion() {

			
		selectedColumn = -1;
		

	}
}
