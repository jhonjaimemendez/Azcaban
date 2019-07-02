/**
 * Clase: InternalFrameFechaFestivos
 * 
 * @version  1.0
 * 
 * @since 17-06-2006
 * 
 * @autor Ing.  Jhon Mendez
 *
 * Copyrigth: JASoft
 */
 
import com.JASoft.componentes.EditableCellTableModel;
import com.JASoft.componentes.HeaderRenderer;
import com.JASoft.componentes.ConectarMySQL;
import com.JASoft.componentes.CrearJFrame;
import com.JASoft.componentes.Calendario;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


import java.util.Vector;
import java.util.Date;
import java.util.Calendar;

import java.sql.*;

import java.text.*;
 



final public class InternalFrameFechaFestivos extends JInternalFrame implements ActionListener,FocusListener{

    
	//Botones
	private JButton bArriba;            // boton para aumentar el año
    private JButton bAbajo;
    private JButton bAceptar;
  	private JButton bCancelar;
  
    //Label
    private JLabel lDescripcionFecha;
    
    //Modelo de datos de la tabla
	private EditableCellTableModel modelo;

    //Tablas
    private JTable tbDiasMes;	
    private JTable tbFestivos;
    
    //ComboBox
    private JComboBox cbMes;          // da los meses del años
  
    //JList
    private JList liano;             // para el año
    
    //JTextField
    private JTextField tFecha;       // la fecha de ecojamos
    private JTextField tano;
    private JTextField tDescripcionFestivos;
    public JTextField tFechaFestivos;
    
    //Enteros
    private int f,c;
    private int fila = 0,colum = 0; 
    private int dia; // es el dia de la decha del sistema por primera vez 
    private int columnaEmpieza;
  
    //String
    private String elementos[] = {"2004"};   // se inicia por primera vez con el 2006 para el JList 
    private String ano = "";
    
    //Boolean
  	private boolean mostrar = true;
    
    //Vectores
    private Vector VMesfestivo = new Vector();
    private Vector VDiafestivo = new Vector();
    
    //Color
    private final static Color color = new Color(0,57,106);
    
    //Objeto tipo caendario
    public Calendario calen;
    
    //Objeto de tipo CrearJFrame
    private CrearJFrame cJf;
 	
    
    /**
     * Constructor
     */
    public InternalFrameFechaFestivos(int posX,int  posY,ConectarMySQL p_conMySQL,String año,String Fechaactual) {
	  
	  configurarFrame(posX,posY,p_conMySQL,año);
	  
   }
   
    /**
     * Constructor
     */
   
   public InternalFrameFechaFestivos(int posX,int  posY,ConectarMySQL p_conMySQL,String fechaActual,boolean p_mostrar,JTable tabla,EditableCellTableModel modelo1) {
	  
	   ano = fechaActual.substring(0,4);
	   
	   tbFestivos = tabla;
	   
	   configurarFrame(posX,posY,p_conMySQL,fechaActual);
	   
	   cJf = new CrearJFrame(); 
	   
	   mostrar = p_mostrar;
	  
	   tFechaFestivos = new JTextField(fechaActual);
	   tFechaFestivos.setBounds(100,40,80,20);
	   tFechaFestivos.setBorder(cJf.getBordeUndido());
	   tFechaFestivos.setToolTipText("Digite la fecha del festivo");
	   tFechaFestivos.setHorizontalAlignment(0);
	   tFechaFestivos.addKeyListener(cJf.getValidarEntradaFechaJTextField());
	   tFechaFestivos.addFocusListener(this);
	   tFechaFestivos.addActionListener(this);
	   tFechaFestivos.selectAll();
	   this.add(tFechaFestivos);
	  
	
   }
  
  //*******************************************************************************************************************
  
  public void configurarFrame(int posX,int  posY,ConectarMySQL p_conMySQL,String año) {
  	
      // se configura el JInternalFrame    
	  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  this.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE);
	  this.setLayout(null);
	 
	  // Se especifica el tamaño y posicion
	  this.setSize(220,350);
	  this.setLocation(posX, posY);
	  
	  //Se buscan los feriados
	  buscarFestivos(p_conMySQL,año);
  }
  
  //*******************************************************************************************************************
  
  public void iniciarCalendario() {
  	  
  	  tFecha = tFechaFestivos;
  	  
  	 //  Ejecutar el constructor de Calendario
  	  calen = new Calendario();
  	
  	  // para llamara a la tabla del calemdario	
  	  llamarCalendario();
  	  
  	
  }
  
  //*******************************************************************************************************************  
    
  public void llamarCalendario() {	 	
  	 
  	
  		//Se configuran las etiquetas
  	    JLabel lAno = new JLabel("Año : ");
 		lAno.setBounds(40,10,40,20);
 		this.add(lAno);
 		
 		JLabel lFecha = new JLabel("Fecha : ");
 		lFecha.setBounds(40,40,80,20);
 		this.add(lFecha);
 		
 		lDescripcionFecha = new JLabel("");
 		lDescripcionFecha.setBounds(40,70,150,20);
 		this.add(lDescripcionFecha);
		
 		
 		//Se adiciona un JTextField
  	    tano = new JTextField();
 		tano.setBounds(100,10,80,20);
 		tano.setText(ano); 		
 		tano.setBorder(cJf.getBordeUndido());
 		tano.setToolTipText("Año");
  	    tano.setHorizontalAlignment(0);
  	    tano.setEditable(false);
  	    tano.setFocusable(false);
  	    tano.setText(ano);
 	    tano.addFocusListener(this);
 	    this.add(tano);
 	    
 	    JLabel LDescFest = new JLabel("Descripción Festivo");
 		LDescFest.setBounds(50,90,150,20);
 		this.add(LDescFest);
 		
 		tDescripcionFestivos = new JTextField();
 		tDescripcionFestivos.addKeyListener(cJf.getConvertirMayuscula());
 		tDescripcionFestivos.setName("20");
 		tDescripcionFestivos.setBounds(20,110,180,20);
 		tDescripcionFestivos.setBorder(cJf.getBordeUndido());
 		tDescripcionFestivos.setToolTipText("Digite las Descripción del Festivo");
  	    tDescripcionFestivos.addFocusListener(this);
 	    this.add(tDescripcionFestivos);
 		

  	    
  	    bAceptar =new JButton("Aceptar");
  	    bAceptar.addActionListener(this);
  	    bAceptar.setBounds(20,310,80,20);
    	this.add(bAceptar);
  		
  		
  		bCancelar = new JButton("Cancelar");
  		bCancelar.setBounds(110,310,90,20);
    	bCancelar.addActionListener(this);
    	this.add(bCancelar);
  	    
  	    
  	    // Lpanel de las fechas
	    JLabel Lpanel = new JLabel();
	    Lpanel.setBounds(8,130,200,175);
	    Lpanel.setBorder( BorderFactory.createTitledBorder("Fecha") ); 
	    this.add(Lpanel); 
   	 	
	  	 // *********** Combobox de mes	
	  	 cbMes=new JComboBox();
	     cbMes.setBounds(20,25,100,20);
	  	 cbMes.addItem("Enero");
	   	 cbMes.addItem("Febrero");
	   	 cbMes.addItem("Marzo");
	   	 cbMes.addItem("Abril");
	   	 cbMes.addItem("Mayo");
	   	 cbMes.addItem("Junio");
	   	 cbMes.addItem("Julio");
	   	 cbMes.addItem("Agosto");
	   	 cbMes.addItem("Septiembre");
	   	 cbMes.addItem("Octubre");
	   	 cbMes.addItem("Noviembre");
	   	 cbMes.addItem("Diciembre");
	   	 Lpanel.add(cbMes);
	   	 cbMes.addActionListener(this);   	
	 
	  	  // Jlist del año  	
	      liano = new JList(elementos);
	  	  liano.setBounds(125,25,40,20);
	  	  liano.setBorder(new EtchedBorder());
	  	  liano.setSelectionBackground(Color.white);
	  	  Lpanel.add(liano); 
		  	  	
	  	  // boton aumenta el año	
	  	  bArriba = new JButton(new ImageIcon("imagenes/Arriba.gif"));
	  	  bArriba.setBounds(165,25,18,10);
	      bArriba.setCursor(new Cursor(Cursor.HAND_CURSOR));
	      bArriba.addActionListener(this);
	      Lpanel.add(bArriba);
    
	       // boton disminuye el año
	       bAbajo = new JButton(new ImageIcon("imagenes/Abajo.gif"));
	       bAbajo.setBounds(165,33,18,10);
	       bAbajo.setCursor(new Cursor(Cursor.HAND_CURSOR));
	       bAbajo.addActionListener(this);
	       Lpanel.add(bAbajo);
       
	       
		  bAbajo.setEnabled(mostrar);
		  bArriba.setEnabled(mostrar);
	    
	  	
	  	  // se crea los objeto y la tabla para el calendario
	  	  Object num[][] = new Object[6][7];
	 	
	 	  Object columnNames[] = {"L", "M", "M", "J", " V ", " S ", " D "};
	     
	      TableModel model = new DefaultTableModel(num, columnNames) {
	         public boolean isCellEditbDiasMes(int x, int y) {
	           return false;
	         }
	      };
	 	
	 	
	 	 tbDiasMes = new JTable(model);
	 	 JTableHeader header = tbDiasMes.getTableHeader();
	     tbDiasMes.setFont(new Font("Arial",Font.BOLD,12));
	     tbDiasMes.setGridColor(Color.white); 
	     tbDiasMes.setCellSelectionEnabled( true);  // selecciona celda no filas
	     tbDiasMes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     tbDiasMes.setBorder(new BevelBorder(BevelBorder.LOWERED));
	     header.setReorderingAllowed(false);
	     header.setResizingAllowed(false);
	     
	     final TableCellRenderer headerRenderer = tbDiasMes.getTableHeader().getDefaultRenderer();
		 tbDiasMes.getTableHeader().setDefaultRenderer( new TableCellRenderer() {
				public Component getTableCellRendererComponent( JTable tbDiasMes, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {
					Component comp = headerRenderer.getTableCellRendererComponent( tbDiasMes, value, isSelected, hasFocus, row, column );
					if ( column == 6 )
					   comp.setBackground(Color.red);
					else
					   comp.setBackground(color);
					 
					  comp.setForeground(Color.white);
					
					return comp;
					
				}
			});
	    
	    
	    JScrollPane scroll = new JScrollPane(tbDiasMes);
	    scroll.setBounds(20,50,165,115); 
	    Lpanel.add(scroll);
	    
	    tbDiasMes.addMouseListener(new MouseAdapter() {
		
		 public void mouseReleased(MouseEvent evt) {
		 
		 	fila = tbDiasMes.getSelectedRow();
		    colum=tbDiasMes.getSelectedColumn();
		 
	  	    if(tbDiasMes.getValueAt(fila,colum).toString().equals("")) {
	  	      
		       tbDiasMes.setRowSelectionInterval(f,f);
		       tbDiasMes.setColumnSelectionInterval(c,c);
		     
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
	   
	   saberFecha(tFecha.getText());
	  	  	
	   int cambiarano=Integer.parseInt(elementos[0]);
	   calen.mostar(cambiarano,cbMes.getSelectedIndex());
	   borratbDiasMes();
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
  	   System.out.println("1");
  	  // seleccionar el mese en el comboBox
  	  if(fuente ==cbMes){
  	   	 int cambiarano = Integer.parseInt(elementos[0]);
  	   	 calen.mostar(cambiarano,cbMes.getSelectedIndex());
  	   	 borratbDiasMes();
  	     buscarCalendario(calen.getDayOfWeek(),calen.getDays());
  	     //mostrarFecha();
  	  }

  	  
  	  if (fuente == bAceptar)
  	  {
  	  	System.out.println("1");
  	  	agregarFecha();
 		 //this.setVisible(false);
 	  }
 	  if (fuente == bCancelar)
  	  {
 			 this.setVisible(false);
 	  }
  	  
  	  // los botones para aumentar y disminuir el año
  	  if(fuente==bArriba || fuente==bAbajo){
  	  
	  	    if(fuente==bArriba)
	  	       modificar(0);
	  	    else   
	  	       if(fuente==bAbajo) 
	  	          modificar(1);	
  	    
  	    
	  	    int cambiarano = Integer.parseInt(elementos[0]);
	  	    calen.mostar(cambiarano,cbMes.getSelectedIndex());
	  	    borratbDiasMes();
	  	    buscarCalendario(calen.getDayOfWeek(),calen.getDays()); 
	  	    mostrarFecha();  
  	   } 
   } 
   
   // muestra los numero en la tbDiasMes
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
  	    tbDiasMes.setValueAt(""+cont,i,j);
  	    
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
  	tbDiasMes.setRowSelectionInterval(cogerfila,cogerfila);
  	tbDiasMes.setColumnSelectionInterval(cogercolumnas,cogercolumnas);
  	fila=cogerfila;
  	colum=cogercolumnas;
  	f= fila;
  	c = colum;	
  }
  
  
  public void configurarColumnas() {
  	   
  	   MyRenderer myRenderer = new MyRenderer();
  	
       for (int i = 0; i<=6 ; i++)
 	  	 tbDiasMes.getColumnModel().getColumn(i).setCellRenderer(myRenderer);
  }
    
  
  // muestra  la fecha en el texfield cada vez que le debes a la tabla
  public void mostrarFecha(){
  
  	String dia,mes; 
  	  int d=Integer.parseInt(tbDiasMes.getValueAt(fila,colum).toString());
  	  if(d<10)
  	   dia='0'+tbDiasMes.getValueAt(fila,colum).toString();
  	  else
  	   dia=tbDiasMes.getValueAt(fila,colum).toString();
  	  
  	  int m=cbMes.getSelectedIndex()+1;
  	  if(m<10)
  	   mes='0'+String.valueOf(m);
  	  else
  	   mes=String.valueOf(m);
  	     
  	   
  	  tFecha.setText(elementos[0]+'-'+mes+'-'+dia);
  	  
  	  mostrarfechalarga();
  	  
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
  	    
  	 liano.setListData(elementos);   	
  	
  }
  
   // saber la fecha para  colocarla en la tabla por primera vez
  public void saberFecha(String verificar){
    
  	dia=Integer.parseInt(verificar.substring(8,10));
  	elementos[0]=verificar.substring(0,4);
   	cbMes.setSelectedIndex(Integer.parseInt(verificar.substring(5,7))-1);
  } 
   
   // borra tbDiasMes
  
  public void borratbDiasMes(){
  	for(int i=0;i<6;i++)
       for(int j=0;j<7;j++)
  	  	 tbDiasMes.setValueAt("",i,j);
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
		 
		 
		 if (cbMes.getSelectedIndex() < 9)
		   mes = "0"+(cbMes.getSelectedIndex()+1);
		 else
		   mes ="" + (cbMes.getSelectedIndex()+1);
		  
		   
		  
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
	
	   public Component getTableCellRendererComponent(JTable tbDiasMes, Object value,boolean isSelected, boolean hasFocus,int row, int column) {
	   	  
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

	public void agregarFecha() {
System.out.println("1");
 	     if (Integer.parseInt(tFechaFestivos.getText().substring(0,4)) == Integer.parseInt(tano.getText())) {
 	     System.out.println("2");
	 	     if (cJf.getValidarFecha(tFechaFestivos.getText(),false)) {
	 	      System.out.println("3");
	 	        // Se crea un objeto de tipo object para agregar la fila
	 	        if(agregarFila()) {
	 	        	 	        	System.out.println("4");
	 	        	if (tbFestivos.getRowCount() == 0 || String.valueOf(tbFestivos.getValueAt(0,0)).length()>0) {
	            System.out.println("5");
		 	             Object nuevaFila[] = {tFechaFestivos.getText(),tDescripcionFestivos.getText()};
						    modelo.addRow(nuevaFila);

						 this.setVisible(false);
					
			 		} else
			 		
			 		     tbFestivos.setValueAt(tFechaFestivos.getText(),0,0);
			 
			  }
			  else
			  cJf.Mensaje("Día ya se encuentra","Error",JOptionPane.ERROR_MESSAGE);
			  
		    } else {
		    	cJf.Mensaje("Fecha Invalida","Error",JOptionPane.ERROR_MESSAGE);
		    	tFechaFestivos.grabFocus();
		    }
		  } else {
		    	cJf.Mensaje("Ao Invalido","Error",JOptionPane.ERROR_MESSAGE);
		    	tFechaFestivos.grabFocus();
		    } 
		      
	     
	 		
    }
    
    //****************************************************************************************************************
      //****************************************************************************************************************
    
    public boolean agregarFila() {
   	
	   	 int fila = 0;
	   	 
	   	 boolean agregar = true;
	   	 
	   	   while ((fila <=tbFestivos.getRowCount() - 1) &&   tbFestivos.getValueAt(fila,0)!=null && agregar) {
	   	      if (tbFestivos.getValueAt(fila,0).equals(tFechaFestivos.getText()))
	   	        agregar = false;
	   	       
	   	      fila++;   
	   	   }     
	   	 
	   	 
	   	       
	   	return agregar;      
	   	  
   	
   }
   
   public void mostrarfechalarga()
   {
   	   	Calendar calendar = Calendar.getInstance();
     	SimpleDateFormat formato ;
   		String FechaCompa;
   		
   		calendar.set(Integer.parseInt(tFechaFestivos.getText().substring(0,4)),Integer.parseInt(tFechaFestivos.getText().substring(5,7))-1,Integer.parseInt(tFechaFestivos.getText().substring(8,10)));
		formato = new SimpleDateFormat("EEEEE, dd MMMMM yyyy");
    	FechaCompa =""+formato.format(calendar.getTime());
    			
    	lDescripcionFecha.setText(FechaCompa);
   }
   
   //*********************************************************************************************************************
  	
  	public void focusGained(FocusEvent F) {
 	    // se agrega un atributo visual
 		F.getComponent().setBackground(cJf.getVisualAtributoGanaFocoComponentes());
 	}
 	//***************************************************************************
   
   public void focusLost(FocusEvent F) {
   	
   	String FechaCompa;
    Calendar calendar = Calendar.getInstance();
     SimpleDateFormat formato ;
     
   	try{
   	
     
       if (F.getSource() == tFechaFestivos) {
 		     
 		     if (tFechaFestivos.getText().length() > 0  && tFechaFestivos.getText().indexOf('-') < 0) {
 		     
 		         String fecha = tFechaFestivos.getText(); 		         
				 tFechaFestivos.setText(fecha.substring(0,4) + "-" + fecha.substring(4,6) + "-" + fecha.substring(6,8));
 		     }
			 
			 if (cJf.getValidarFecha(tFechaFestivos.getText(),false)) {
				
				mostrarfechalarga();
    		}
    		else
 		 	lDescripcionFecha.setText("");
 		       		         
 	  }
 	  
 	  
 	  
 	  }catch(Exception e){cJf.Mensaje(""+e,"Error",JOptionPane.ERROR_MESSAGE);
 	  }

 	    // se coloca el atributo visual por defecto
 		F.getComponent().setBackground(cJf.getVisualAtributoPierdeFocoComponentes());
 	}	 
   
     
}
