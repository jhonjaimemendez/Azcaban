/**
 * Clase: Calendario
 * 
 * @version  1.0
 * 
 * @since 16-04-2007
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
 
package com.JASoft.componentes;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Clase que configura un calendario gregoniano para mostrar un InternalFrame con el calendario
 */

final public class Calendario  {
  
  Calendar cal;
  int dayOfWeek; // numero de dias de un mes
  int days;      // nombre del dia segun la fecha
  	 
  public Calendario() {
  	
  	 cal = new GregorianCalendar();
  
  }
  
  public void mostar(int ano,int mes ){
  	
  	 cal.set(ano,mes,1);  //2006    año   4 es el mes menos uno y 11 el dia
     dayOfWeek = cal.get( Calendar.DAY_OF_WEEK ); 
     days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
    
  }
  
  public int  getDayOfWeek(){
    return dayOfWeek;
  }
  
  public int getDays(){
  	return days;
  }
  	 
   	 
}	