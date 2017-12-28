package org.usfirst.frc4252.TestFRC.vision;

import edu.wpi.first.wpilibj.vision.VisionPipeline;

import java.util.ArrayList;
import org.opencv.core.*;
import org.opencv.imgproc.*;
//import org.opencv.objdetect.*;

/**
* BoilerPipeline class.
*
* <p>An OpenCV pipeline generated by GRIP.
*
* @author GRIP
*/
public class BoundingRectVisionPipeline implements VisionPipeline {

	//Point topLeft = new Point(30, 30);
	//Point bottRight = new Point(60, 60);
	Scalar color = new Scalar(255,255,0);
	int thickness = 3;
	//private void processImg( Mat image ){
	//	Imgproc.rectangle(image, topLeft, bottRight, color, 3);
	//}
	
	/**
	 * This is the primary method that runs the entire pipeline and updates the outputs.
	 */
	@Override	public void process(Mat sourceImage) {
		ArrayList<MatOfPoint> finalListOfContours = (ArrayList<MatOfPoint>) LocalTable.getInstance().getFromTable("filteredContours" );
		
		for (int i = 0; i < finalListOfContours.size(); i++)
		{
			MatOfPoint thisContour = finalListOfContours.get(i);
			Rect bb = Imgproc.boundingRect(thisContour);
			Point topLeft = new Point(bb.x, bb.y);
			Point bottRight = new Point(bb.x + bb.width, bb.y + bb.height);
			Imgproc.rectangle(sourceImage, topLeft, bottRight, color, 3);
			LocalTable.getInstance().putInTable("targetCount", i+1);
			LocalTable.getInstance().putInTable("target"+i, bb);
			/**
			// Translate contours
			Point topLeft = new Point(bb.x, bb.y);
			Mat matChunk = Mat.zeros(bb.size(), sourceImage.getType());
			MatOfPoint translatedContour = CVUtils.translateMatOfPoints(thisContour, topLeft);

			matChunk = mat.submat(bb).clone();

			// Draw fill mask
			Mat contourMask = new Mat(matChunk.size(), CvType.CV_32SC1);
			contourMask.setTo(new org.opencv.core.Scalar(0));
			LinkedList<MatOfPoint> listOfPoints = new LinkedList<MatOfPoint>();
			listOfPoints.add(translatedContour);
			Core.fillPoly(contourMask, listOfPoints, new org.opencv.core.Scalar(-1));

			// Add to DMImage list
			Imgproc.cvtColor(matChunk, matChunk, Imgproc.COLOR_HSV2BGR);
			BufferedImage bufImage = CVUtils.getAlphaBufferedImageFromMat(matChunk, contourMask);

			// Add positional info
			String name = JSUtils.generateImageName();
			DMImage dmImage = factory.new DMImage(name, topLeft, bufImage);
			dmImageList.add(dmImage);
			*/
		}
		
	}

	



}