package org.usfirst.frc4252.TestFRC.vision;

import java.util.List;

//import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import org.opencv.core.Mat;
//import org.opencv.core.CvType;

public class MyVisionRunner implements Runnable {
	
	private VideoSource vSource;
	//private CvSource cvSource;
	private CvSource   customSource;
	private List<VisionPipeline> listPipelines;
	Mat image = new Mat();
	//Mat blank = Mat.zeros(Robot.IMG_HEIGHT, Robot.IMG_WIDTH, 0);
	
	

	public MyVisionRunner(VideoSource vSource,  List<VisionPipeline> listPipelines, CvSource customSource) {
		//super();
		this.vSource = vSource;
		this.customSource = customSource;
		this.listPipelines = listPipelines;
		
	}


	@Override
	public void run() {
		if( vSource.isConnected() ){
			customSource.setConnected(true);
			while (!Thread.interrupted()) {
				long timetograb = CameraServer.getInstance().getVideo(vSource).grabFrameNoTimeout(image);
				if(timetograb > 0){
					
					for(VisionPipeline pipe : listPipelines){
						pipe.process(image);
					}
					customSource.putFrame(image);
				}
			}
		}

	}

}
