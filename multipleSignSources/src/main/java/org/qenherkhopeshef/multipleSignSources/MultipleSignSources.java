/*
 * Copyright ou © ou Copr. Serge Rosmorduc (2004-2022) 
 * serge.rosmorduc@cnam.fr
 * 
 * The project "multipleSignSources" is distributed according to the CC0 1.0 Universal LICENCE.
 */

package org.qenherkhopeshef.multipleSignSources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * How to use JSesh to create bitmaps in Java.
 * 
 * Look at the README.md for technical information about how to use the project.
 */

import javax.imageio.ImageIO;

import jsesh.hieroglyphs.DefaultHieroglyphicFontManager;
import jsesh.hieroglyphs.DirectoryHieroglyphicFontManager;
import jsesh.mdc.MDCSyntaxError;
import jsesh.mdc.constants.TextDirection;
import jsesh.mdcDisplayer.draw.MDCDrawingFacade;
import jsesh.mdcDisplayer.preferences.DrawingSpecification;
import jsesh.mdcDisplayer.preferences.DrawingSpecificationsImplementation;
import jsesh.mdcDisplayer.preferences.PageLayout;

public class MultipleSignSources {

	public static void prepareFonts() {
			DefaultHieroglyphicFontManager manager = DefaultHieroglyphicFontManager.getInstance();
			manager.addHieroglyphicFontManager(new DirectoryHieroglyphicFontManager(new File("signs2")));
			manager.addHieroglyphicFontManager(new DirectoryHieroglyphicFontManager(new File("signs1")));
	}

	/**
	 * Produces a bitmap picture, suitable for JPEG or PNG production.
	 * 
	 * @param mdcText : the manuel de codage text to use.
	 * @return a bitmap picture.
	 * @throws MDCSyntaxError
	 */
	public static BufferedImage buildImage(String mdcText)
			throws MDCSyntaxError {
		
		// Create the drawing system:
		MDCDrawingFacade facade = new MDCDrawingFacade();
		
		prepareFonts();
		// Change the global scale, choosing the cadrat height in pixels.
		facade.setCadratHeight(60);

		// Change a number of parameters, using the DrawingSpecificationsImplementation
		// class.
		// The modifications below are examples, and depending on the use case, you
		// would probably want to set them elsewhere.

		DrawingSpecification drawingSpecifications = new DrawingSpecificationsImplementation();
		drawingSpecifications.setTextDirection(TextDirection.RIGHT_TO_LEFT);
		
		// Draw in columns (uncomment to use)
		// drawingSpecifications.setTextOrientation(TextOrientation.VERTICAL);
		drawingSpecifications.setSmallSignsCentered(true);

		drawingSpecifications.setMaxCadratWidth(60); // same as Cadrat Height

		// add a margin
		PageLayout pageLayout = new PageLayout();
		pageLayout.setLeftMargin(10);
		pageLayout.setTopMargin(10);
		drawingSpecifications.setPageLayout(pageLayout);

		// actual drawing.
		facade.setDrawingSpecifications(drawingSpecifications);

		// Create the picture
		return facade.createImage(mdcText);
	}

		public static void main(String args[]) throws MDCSyntaxError, IOException {
		String mdcText = "N35:G1-T22A-N35:W24&G43-A1-Z3-G1VARA-G1VARB";
		// Create the picture
		BufferedImage img = buildImage(mdcText);
		File f = new File("example.png");
		// save it in png (better than jpeg in this case)
		ImageIO.write(img, "png", f);
	}
}
