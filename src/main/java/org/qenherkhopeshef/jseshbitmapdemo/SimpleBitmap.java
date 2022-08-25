/*
 * Copyright ou © ou Copr. Serge Rosmorduc (2004-2022) 
 * serge.rosmorduc@cnam.fr
 * 
 * The project "jseshbitmapdemo" is distributed according to the CC0 1.0 Universal LICENCE.
 */

package org.qenherkhopeshef.jseshbitmapdemo;

/**
 * How to use JSesh to create bitmaps in Java.
 * 
 * Look at the README.md for technical information about how to use the project.
 */

import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.*;

import jsesh.mdc.constants.TextDirection;
import jsesh.mdc.constants.TextOrientation;
import jsesh.mdcDisplayer.preferences.*;
import jsesh.mdcDisplayer.draw.*;
import jsesh.mdc.*;

public class SimpleBitmap {

	public static BufferedImage buildImage(String mdcText)
			throws MDCSyntaxError {
		// Create the drawing system:
		MDCDrawingFacade drawing = new MDCDrawingFacade();
		// Change the scale, choosing the cadrat height in pixels.
		drawing.setCadratHeight(60);
		// Change a number of parameters
		DrawingSpecification drawingSpecifications = new DrawingSpecificationsImplementation();
		drawingSpecifications.setMaxCadratWidth(60);
		drawingSpecifications.setMaxCadratWidth(60);

		PageLayout pageLayout = new PageLayout();
		pageLayout.setLeftMargin(5);
		pageLayout.setTopMargin(5);
		drawingSpecifications.setTextDirection(TextDirection.RIGHT_TO_LEFT);
		// Draw in columns :
		// drawingSpecifications.setTextOrientation(TextOrientation.VERTICAL);	
		drawingSpecifications.setSmallSignsCentered(true);
		drawing.setDrawingSpecifications(drawingSpecifications);
		// Create the picture
		BufferedImage result = drawing.createImage(mdcText);
		return result;
	}

	public static void main(String args[]) throws MDCSyntaxError, IOException {
		// Create the picture
		BufferedImage img = buildImage("i-w-r:a-ra-m-p*t:pt");
		File f = new File("example.png");
		// save it in png (better than jpeg in this case)
		ImageIO.write(img, "png", f);
	}
}
