package com.JASoft.componentes;

import java.io.File;

import javax.swing.filechooser.FileFilter;


final public class ImageFilter extends FileFilter {
	
	
	
     String gif = "gif";
     String jpg = "jpg";
     
     String exten[];
     String Descripcion;
    
    public ImageFilter(String ext[],String Descripcion1)
    {
    	exten=ext;
    	Descripcion=Descripcion1;
    	
    }
    
    
    //acepta los directorios txt
    public boolean accept(File f) {

	if (f.isDirectory()) {
	    return true;
	}

	String s = f.getName();
	int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1) {
	    String extension = s.substring(i+1).toLowerCase();
	    
	    for(int e=0;e<exten.length;e++)
	    {
	    	if (extension.equals(exten[e]))
			{
		   		 return true;
	    	} 
	 	}
	}

	return false;
    }
    
    //Descripcion del filtro
    public String getDescription() {
	return Descripcion;
    }
    
}
