/**
 * Clase: GeneraCodigoBarra 
 * 
 * Version : 1.0
 * 
 * Fecha: 22-10-2008
 *
 * Copyright: JASoft
 */
 
package com.JASoft.componentes;

import java.awt.print.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;

import net.sourceforge.barbecue.*; 
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

/*
 * Esta clase permite imprimir un codigo de Barra o obetner la imagen del mismo
 */
public class GeneraCodigoBarra {


   public static void drawingBarcodeDirectToGraphics(String codigo) throws BarcodeException, OutputException {
		
		// Always get a Barcode from the BarcodeFactory
		Barcode barcode = BarcodeFactory.createCode128B(codigo);

		// We are created an image from scratch here, but for printing in Java, your
		// print renderer should have a Graphics internally anyway
		BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_BYTE_GRAY);
		// We need to cast the Graphics from the Image to a Graphics2D - this is OK
		Graphics2D g = (Graphics2D) image.getGraphics();

		// Barcode supports a direct draw method to Graphics2D that lets you position the
		// barcode on the canvas
		barcode.draw(g, 10, 56);
	}

    public static File outputtingBarcodeAsPNG(String codigo) throws BarcodeException {
    	
        // get a Barcode from the BarcodeFactory
		Barcode barcode = BarcodeFactory.createCode128B(codigo);
		File file = null;

        try {
            
            file = new File(codigo + ".png");
            file.deleteOnExit();

            // Let the barcode image handler do the hard work
            BarcodeImageHandler.savePNG(barcode, file);
            
        } catch (Exception e) {
            
            System.out.println("Error al generar código de barra " + e);
        }
        
        
        return file;
    }

}
