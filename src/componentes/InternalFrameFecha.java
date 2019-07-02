/**
 * Clase: InternalFrameFecha
 * 
 * @version  1.0
 * 
 * @since 17-06-2006
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
 
package com.JASoft.componentes;

import com.JASoft.componentes.ConectarMySQL;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.ResultSet;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;


/**
 * Esta clase configura un internal Frame para mostrar un calendario 
 */

final public class InternalFrameFecha extends JInternalFrame implements ActionListener{

    
	
    //Se especifica el serial para la serializacion
    static final long serialVersionUID = 19781216;
    
    
	private JButton Barr;            // boton para aumentar el año
    private JButton Baba;			// boton para disminuir el año
    
    private JTable table;	
    
    private JComboBox Cmes;          // da los meses del años
    
    private JList JLano;             // para el año
    
    private JTextField Tfecha;       // la fecha de ecojamos
    
    private int f,c;
    private int fila = 0;
    private int colum = 0; 
    private int cambiarano;
    private int dia; // es el dia de la decha del sistema por primera vez 
    private int columnaEmpieza;
    
    private boolean mostrar = true;
    private String elementos[]={"2004"};   // se inicia por primera vez con el 2006 para el JList 
    
    private Vector < String > VMesfestivo = new Vector < String >();
    private Vector < String > VDiafestivo = new Vector < String >();
    
    public Calendario calen;
    
    
   public InternalFrameFecha(int posX,int  posY,ConectarMySQL p_conMySQL,String año) {
	  
	  configurarFrame(posX,posY,p_conMySQL,año);
	  
   }
   
   
   public InternalFrameFecha(int posX,int  posY,ConectarMySQL p_conMySQL,String año,boolean p_mostrar) {
	  
	  configurarFrame(posX,posY,p_conMySQL,año);
	  mostrar = p_mostrar;
	  
   }
  
  //*******************************************************************************************************************
  
  public void configurarFrame(int posX,int  posY,ConectarMySQL p_conMySQL,String año) {
  	
      // se configura el JInternalFrame    
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
	  this.setLayout(null);
	  // Se especifica el tamaño y posicion
	  this.setSize(220,206);
	  this.setLocation(posX, posY);
	  
	  //Se buscan los feriados
	  buscarFestivos(p_conMySQL,año);
  }
  
  //*******************************************************************************************************************
  
  public void iniciarCalendario(JTextField p_Tfecha) {
  	  
  	  Tfecha = p_Tfecha;
  	  
  	 //  Ejecutar el constructor de Calendario
  	  calen=new Calendario();
  	
  	  // para llamara a la tabla del calemdario	
  	  llamarCalendario();
  	  
  	
  }
  
  //*******************************************************************************************************************  
    
  public void llamarCalendario() {	 	
  	 
  
      //Border etched = BorderFactory.createEtchedBorder(); // Es para el border de TitledBorder
      //TitledBorder titled=new TitledBorder(etched,"Fecha") ;    
    
	  // Lpanel de las fechas
	  JLabel Lpanel = new JLabel();
	  Lpanel.setBounds(8,8,200,180);
	  Lpanel.setBorder( BorderFactory.createTitledBorder("Fecha") ); 
	  this.add(Lpanel); 
   	 	
  	  // *********** Combobox de mes	
  	  Cmes=new JComboBox();
      Cmes.setBounds(20,25,100,20);
  	  Cmes.addItem("Enero");
   	  Cmes.addItem("Febrero");
   	  Cmes.addItem("Marzo");
   	  Cmes.addItem("Abril");
   	  Cmes.addItem("Mayo");
   	  Cmes.addItem("Junio");
   	  Cmes.addItem("Julio");
   	  Cmes.addItem("Agosto");
   	  Cmes.addItem("Septiembre");
   	  Cmes.addItem("Octubre");
   	  Cmes.addItem("Noviembre");
   	  Cmes.addItem("Diciembre");
   	  Lpanel.add(Cmes);
   	  Cmes.addActionListener(this);   	
 
  	  // Jlist del año  	
      JLano = new JList(elementos);
  	  JLano.setBounds(125,25,40,20);
  	  JLano.setBorder(new EtchedBorder());
  	  JLano.setSelectionBackground(Color.white);
  	  Lpanel.add(JLano); 
  	  	
  	  // boton aumenta el año	
  	  Barr = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Arriba.gif")));
  	  Barr.setBounds(165,25,18,10);
      Barr.setCursor(new Cursor(Cursor.HAND_CURSOR));
      Barr.addActionListener(this);
      Lpanel.add(Barr);
    
       // boton disminuye el año
	   Baba = new JButton(new ImageIcon(getClass().getResource("/Imagenes/Abajo.gif")));
	   Baba.setBounds(165,33,18,10);
	   Baba.setCursor(new Cursor(Cursor.HAND_CURSOR));
	   Baba.addActionListener(this);
	   Lpanel.add(Baba);
	   
       
	  Baba.setEnabled(mostrar);
	  Barr.setEnabled(mostrar);
    
  	
  	// se crea los objeto y la tabla para el calendario
  	Object num[][] = new Object[6][7];
 	
 	Object columnNames[] = {"L", "M", "M", "J", " V ", " S ", " D "};
     
    TableModel model = new DefaultTableModel(num, columnNames) {
    	
        //Se especifica el serial para la serializacion
        static final long serialVersionUID = 19781216;
    
         public boolean isCellEditable(int x, int y) {
           return false;
         }
     };
 	
 	
 	table = new JTable(model);
 	JTableHeader header = table.getTableHeader();
    table.setFont(new Font("Arial",Font.BOLD,12));
    table.setGridColor(Color.white); 
    table.setCellSelectionEnabled( true);  // selecciona celda no filas
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setBorder(new BevelBorder(BevelBorder.LOWERED));
    header.setReorderingAllowed(false);
    header.setResizingAllowed(false);
    final TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
	table.getTableHeader().setDefaultRenderer( new TableCellRenderer() {
			public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
				Component comp = headerRenderer.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, column );
				if ( column == 6 )
				   comp.setBackground(Color.red);
				else
				   comp.setBackground(new Color(0,57,106));
				 
				  comp.setForeground(Color.white);
				
				return comp;
				
			}
		});
    
    
    JScrollPane scroll = new JScrollPane(table);
    scroll.setBounds(20,50,165,115); 
    Lpanel.add(scroll);
    
    table.addMouseListener(new MouseAdapter() {
	
	 public void mouseReleased(MouseEvent evt) {
	 
	 	fila = table.getSelectedRow();
	    colum=table.getSelectedColumn();
	 
  	    if(table.getValueAt(fila,colum).toString().equals("")) {
  	      
	       table.setRowSelectionInterval(f,f);
	       table.setColumnSelectionInterval(c,c);
	     
  	    } else {
  	       f=fila;
  	       c=colum;   
  	       mostrarFecha();
  	       
  	       if (mostrar)
  	         setVisible(false);
 
  	   } 
  	      	    
	 }
    }); 
    
   configurarColumnas();
   
   saberFecha(Tfecha.getText());
  	  	
   cambiarano=Integer.parseInt(elementos[0]);
   calen.mostar(cambiarano,Cmes.getSelectedIndex());
   borraTable();
   buscarCalendario(calen.getDayOfWeek(),calen.getDays());
  	 
  }
  
  
  //******************************************************************************************************************
  
  public void buscarFestivos(ConectarMySQL conMySQL, String año){
  	
  	  	try {
 			
 			String sentencia = "Select FechaFes From Festivos Where substr(FechaFes,1,4) ='"+ año +"'";
 			
 			//Se buscan los festivos en la base de datos
 			ResultSet resultado = conMySQL.buscarRegistro(sentencia);
 			
 			if (resultado != null)
 			  while (resultado.next()) {
 			  	 
 			  	 String fecha = resultado.getString(1);
 			  	
 			     VMesfestivo.addElement(fecha.substring(5,7));
 			     VDiafestivo.addElement(fecha.substring(8,10));
 			  }   
 			
 		} catch (Exception e) {
 			System.out.println("Error"+e);
 		}
 	
  	
  }
  
  
  //******************************************************************************************************************
    
  public void actionPerformed(ActionEvent a){
  	   
  	   Object fuente = a.getSource();
  	   
  	  // seleccionar el mese en el comboBox
  	  if(fuente ==Cmes){
  	   	 cambiarano=Integer.parseInt(elementos[0]);
  	   	 calen.mostar(cambiarano,Cmes.getSelectedIndex());
  	   	 borraTable();
  	     buscarCalendario(calen.getDayOfWeek(),calen.getDays());
  	     //mostrarFecha();
  	  } 
  	  
  	  // los botones para aumentar y disminuir el año
  	  if(fuente==Barr || fuente==Baba){
  	  
	  	    if(fuente==Barr)
	  	       modificar(0);
	  	    else   
	  	       if(fuente==Baba) 
	  	          modificar(1);	
  	    
  	    
	  	    cambiarano=Integer.parseInt(elementos[0]);
	  	    calen.mostar(cambiarano,Cmes.getSelectedIndex());
	  	    borraTable();
	  	    buscarCalendario(calen.getDayOfWeek(),calen.getDays()); 
	  	    mostrarFecha();  
  	   } 
   } 	 
   
   // muestra los numero en la table
  public void buscarCalendario(int dw,int nd ){

  	if(dw==1)
  	  dw=6;
  	else
  	  dw=dw-2;
  	
  	
  	f=1;
  	c=dw;
  	columnaEmpieza = dw;  
  	 
  	int cogerfila=0; // para saber seleccionar el dia por primera vez
  	int cogercolumnas=dw; // para saber seleccionar el dia por primera vez
  	  
  	int cont=1;    
  	
  	
  	for(int i=0;i<6;i++){
       for(int j=dw;j<7;j++){
  	    table.setValueAt(""+cont,i,j);
  	    
  	    if(cont==dia){ // saber las filas y columnas para seleccionarla
  	      cogerfila=i;
  	      cogercolumnas=j;
  	    }
  	  	 
  	  	if(cont==nd){
  	  	  j=8;
  	  	  i=9;	
  	  	 }
  	  	 cont++;
  	  	 dw=0;
  	  	
  	   }	
  	}
  	table.setRowSelectionInterval(cogerfila,cogerfila);
  	table.setColumnSelectionInterval(cogercolumnas,cogercolumnas);
  	fila=cogerfila;
  	colum=cogercolumnas;
  	f= fila;
  	c = colum;	
  }
  
  
  public void configurarColumnas() {
  	   
  	   MyRenderer myRenderer = new MyRenderer();
  	
       for (int i = 0; i<=6 ; i++)
 	  	 table.getColumnModel().getColumn(i).setCellRenderer(myRenderer);
  }
    
  
  // muestra  la fecha en el texfield cada vez que le debes a la tabla
  public void mostrarFecha(){
  
  	String dia,mes; 
  	  int d=Integer.parseInt(table.getValueAt(fila,colum).toString());
  	  if(d<10)
  	   dia='0'+table.getValueAt(fila,colum).toString();
  	  else
  	   dia=table.getValueAt(fila,colum).toString();
  	  
  	  int m=Cmes.getSelectedIndex()+1;
  	  if(m<10)
  	   mes='0'+String.valueOf(m);
  	  else
  	   mes=String.valueOf(m);
  	     
  	   
  	  Tfecha.setText(elementos[0]+'-'+mes+'-'+dia);
  	  
  
  	  
  }
  
  // modifica el año si es mayor o menor de 2006 dandole a los botones
  public void modificar(int a){
  	int ano=Integer.parseInt(elementos[0]);
  	
  	if(ano==2099 && a==0)
  	 ano=1979;
  	 
  	if(ano==1980 && a==1)
  	 ano=2100;
  	
  	if(a==0)
  	    elementos[0]=String.valueOf(ano+1);	
  	 else
  	    elementos[0]=String.valueOf(ano-1);
  	    
  	 JLano.setListData(elementos);   	
  	
  }
  
   // saber la fecha para  colocarla en la tabla por primera vez
  public void saberFecha(String verificar){
    
  	dia=Integer.parseInt(verificar.substring(8,10));
  	elementos[0]=verificar.substring(0,4);
   	Cmes.setSelectedIndex(Integer.parseInt(verificar.substring(5,7))-1);
  } 
   
   // borra table
  
  public void borraTable(){
  	for(int i=0;i<6;i++)
       for(int j=0;j<7;j++)
  	  	 table.setValueAt("",i,j);
  }	
   
 


	//********************************************************************************************************************
	//Se busca si debe aparecer un dia feriado en la tabla
	public int[][] aparecerFeriado() {
		
		 String mes;
		 int celdas[][] = new int[4][2];  
		 int fila;
		 int columna;
		 int indice = 0;
		 int pos = -1;
		 
		 
		 if (Cmes.getSelectedIndex() < 9)
		   mes = "0"+(Cmes.getSelectedIndex()+1);
		 else
		   mes ="" + (Cmes.getSelectedIndex()+1);
		  
		   
		  
		  do {
			  
			  pos = VMesfestivo.indexOf(mes, pos+1);
			    
			  if (pos > -1) {
			  
			  	 //Se busca la fecha y la celda que debe salir el festivo
			     fila = new Double(Math.ceil((Integer.parseInt(""+VDiafestivo.elementAt(pos)) + columnaEmpieza - 1)/7)).intValue();
			     columna = (Integer.parseInt(""+VDiafestivo.elementAt(pos)) + columnaEmpieza - 1)%7;
			     celdas[indice][0] = fila;
			     celdas[indice][1] = columna;
			     
			  }   
			  
			  
			  indice++;
			  
		  } while (pos != -1);	  
		  
		  //Se termina de llenar el vector con -1
		  for (int i = indice; i < 4; i++) {
		  	 celdas[indice][0] = -1;
			 celdas[indice][1] = -1;
		  	
		  }
		
		  return celdas;  
	     	
		
	}

	//********************************************************************************************************************
	final class MyRenderer extends JLabel implements TableCellRenderer {
		
		
	   //Se especifica el serial para la serializacion
       static final long serialVersionUID = 19781217;
    
	
	   public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus,int row, int column) {
	   	  
	   	   if (value.equals(""))  
	         setEnabled(false);
	       else
	         setEnabled(true);
	         
	       if (isSelected && !value.equals("")) 
	          setBorder(new LineBorder(Color.red));
	        else 
	          setBorder(null);
	       
	       int celdas[][] = aparecerFeriado();
	       
	       if (celdas[0][0] != -1) {
	        
	        
		     if (row == celdas[0][0] && column == celdas[0][1])  
		             setForeground(Color.red);
		        else
		          if (row == celdas[1][0] && column == celdas[1][1])  
		             setForeground(Color.red);
		          else
		           if (row == celdas[2][0] && column == celdas[2][1])  
		               setForeground(Color.red);
		            else
		              if (row == celdas[3][0] && column == celdas[3][1])  
		                   setForeground(Color.red);
		              else
		                    setForeground(Color.black);    
	            
	       } else
	         setForeground(Color.black);
	        
	         
           	                    
	        setHorizontalAlignment(SwingConstants.CENTER);
	    
	        setText((String)value);
	        
	        return this;
	    }
	}


  
}
