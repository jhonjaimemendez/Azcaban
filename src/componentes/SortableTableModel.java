/* (swing1.1) */
package com.JASoft.componentes;

import javax.swing.table.DefaultTableModel;


/**
 * Clase que permite configurar las columnas por tipos de datos de las columnas de un JTable
 */
public class SortableTableModel extends DefaultTableModel {
  
  public int[] indexes;
  public TableSorter sorter;

  public SortableTableModel() { }
  
    
  public Object getValueAt(int row, int col) {
    
    int rowIndex = row;
     
       if (indexes != null) {
    	if (super.getValueAt(0,0) != null) {
    	   rowIndex = indexes[row];
    	}   
      }
      
        return super.getValueAt(rowIndex, col);
      
    
  }
    
  public void setValueAt(Object value, int row, int col) {    
    int rowIndex = row;
    if (indexes != null) {
      rowIndex = indexes[row];
    }
    
    super.setValueAt(value, rowIndex, col);
  }
  

  final public void sortByColumn(int column, boolean isAscent) {
  	
    if (sorter == null) {
      sorter = new TableSorter(this);
    }   
    
    
    sorter.sort(column, isAscent);   
    fireTableDataChanged();
  }
  
  final public int[] getIndexes() {
  	
    int n = getRowCount();
    if (indexes != null) {
      if (indexes.length == n) {
        return indexes;
      }
    }
    indexes = new int[n];
    for (int i=0; i<n; i++) {
      indexes[i] = i;
    }
    return indexes;
  }
}
