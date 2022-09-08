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

import org.qenherkhopeshef.graphics.svg.SVGGraphics2D;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;

import jsesh.mdc.constants.TextDirection;
import jsesh.mdc.constants.TextOrientation;
import jsesh.mdcDisplayer.preferences.*;
import jsesh.utils.DoubleDimensions;
import jsesh.mdcDisplayer.draw.*;
import jsesh.mdc.*;

public class SimpleBitmap {

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

	/**
	 * Build a SVG picture of the Manuel de codage text.
	 * 
	 * @param mdcText
	 * @return
	 * @throws MDCSyntaxError
	 * @throws IOException
	 */
	public static String buildSVGImage(String mdcText)
			throws MDCSyntaxError {
		try {
			// Create the drawing system:
			MDCDrawingFacade facade = new MDCDrawingFacade();

			// Change the scale, choosing the cadrat height in pixels.
			facade.setCadratHeight(60);
			// Change a number of parameters
			DrawingSpecification drawingSpecifications = new DrawingSpecificationsImplementation();
			drawingSpecifications.setMaxCadratWidth(60); // same as Cadrat Height		

			drawingSpecifications.setTextDirection(TextDirection.RIGHT_TO_LEFT);
			// Draw in columns :
			// drawingSpecifications.setTextOrientation(TextOrientation.VERTICAL);
			drawingSpecifications.setSmallSignsCentered(true);
			facade.setDrawingSpecifications(drawingSpecifications);

			// Let's "write" the SVG code into this string (we could also use an existing
			// Writer instead).
			StringWriter writer = new StringWriter();
			// So, this
			Rectangle2D dims = facade.getBounds(mdcText, 0, 0);
			SVGGraphics2D graphics2d = new SVGGraphics2D(writer, new DoubleDimensions(dims.getWidth(),
					dims.getHeight()));
			// Draw the picture, which will end up written in the writer
			facade.draw(mdcText, graphics2d, 0, 0);
			graphics2d.dispose(); // closes the graphics 2D (IMPORTANT !)
			// Returns the SVG which has just being copied in the StringWriter.
			return writer.toString();
		} catch (IOException e) {
			// should not happen !
			throw new RuntimeException(e);
		}
	}

	public static void main(String args[]) throws MDCSyntaxError, IOException {
		String mdcText = "i-w-r:a-ra-m-p*t:pt";
		// Create the picture
		BufferedImage img = buildImage(mdcText);
		File f = new File("example.png");
		// save it in png (better than jpeg in this case)
		ImageIO.write(img, "png", f);

		// Same with SVG:
		String svgCode = buildSVGImage(mdcText);
		Files.writeString(Path.of("example.svg"), svgCode, StandardCharsets.UTF_8);
	}
}
