/**
 * Clase: EditableCellTableModel
 * 
 * @version  1.0
 * 
 * @since 01-07-2004
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */

package com.JASoft.componentes;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;


/**
 * Esta clase permite configurar parametros para los JTable,entre ellos estan: edición de celdas en false y configuracion de 
 * datos
 */
 
final public class EditableCellTableModel extends DefaultTableModel {
	
	private boolean habilitar = false;
	
	public EditableCellTableModel() {
	}
	
	public  EditableCellTableModel(Object[][] datosColumnas,Object[] Columnas) {
		
		this.setDataVector(datosColumnas,Columnas);
	}
	
	public  EditableCellTableModel(Object[][] datosColumnas,Object[] Columnas,boolean p_habilitar) {
		
		this.setDataVector(datosColumnas,Columnas);
		habilitar = p_habilitar;
		
	}
	
	public  EditableCellTableModel(Vector datosColumnas,Vector Columnas) {
		
		this.setDataVector(datosColumnas,Columnas);
	}
	
	
	public void setDatos(Vector datosColumna,Vector Columnas) {
		this.setDataVector(datosColumna,Columnas);
	}
	
	public void setDatos(Object[][] datosColumnas,Object[] Columnas) {
		this.setDataVector(datosColumnas,Columnas);
	}
	

	
	public boolean isCellEditable (int row, int column) {
	
	 	return false;
	
	}
}
