// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4252.TestFRC;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc4252.TestFRC.commands.*;
import org.usfirst.frc4252.TestFRC.subsystems.*;
import org.usfirst.frc4252.TestFRC.vision.*;
//network table
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
//import edu.wpi.first.wpilibj.tables.*;
//vision - start
import edu.wpi.first.wpilibj.CameraServer;
//import edu.wpi.first.wpilibj.vision.VisionThread;
import edu.wpi.first.wpilibj.vision.VisionPipeline;
import edu.wpi.cscore.UsbCamera;
//import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
//import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.VideoMode;

//import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import org.opencv.core.Core;
import org.opencv.core.Scalar;

/**
 * Sample Robot for vision processing using USB camara 0 on RoboRio.
 * Finds blue objects and draw a bounding box around them.
 * More processing can be done by adding more custom *VisionPipeline classes.
 * See robotInit() method for implementation.
 * The wpilib provided VisionRunner had issues, so using a custom thread in  org.usfirst.frc4252.TestFRC.vision package.
 */
public class Robot extends IterativeRobot {
	//vars for vision - start
	public static final int IMG_WIDTH = 320;
	public static final int IMG_HEIGHT = 240;
	public static Scalar thresholdMin = new Scalar(92,0,0, 0);
	public static Scalar thresholdMax = new Scalar(124,256,256, 0);
	public static CvSource processedVideo;
	//private final Object imgLock = new Object();
	private UsbCamera camera1;
	//private UsbCamera camera2;
	//vars for vision - end
	

    Command autonomousCommand;

    public static OI oi;
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public static Subsystem1 subsystem1;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    RobotMap.init();
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        subsystem1 = new Subsystem1();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // OI must be constructed after subsystems. If the OI creates Commands
        //(which it very likely will), subsystems are not guaranteed to be
        // constructed yet. Thus, their requires() statements may grab null
        // pointers. Bad news. Don't move it.
        oi = new OI();

        // instantiate the command used for the autonomous period
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

        autonomousCommand = new AutonomousCommand();

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS
        
        //vision - start
        // Loads our OpenCV library. This MUST be included
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        camera1 = CameraServer.getInstance().startAutomaticCapture(0);
        camera1.setResolution(IMG_WIDTH, IMG_HEIGHT);
        camera1.setFPS(15);
        //camera2 = CameraServer.getInstance().startAutomaticCapture(1);
        //camera2.setResolution(IMG_WIDTH, IMG_HEIGHT);
        //camera2.setFPS(20);
        
        
        processedVideo = new CvSource("Processed_Video2", VideoMode.PixelFormat.kBGR, IMG_WIDTH, IMG_HEIGHT, 15);
        processedVideo = CameraServer.getInstance().putVideo("ProcessedVideo", IMG_WIDTH, IMG_HEIGHT);
        processedVideo.setVideoMode(camera1.getVideoMode());           
        ArrayList<VisionPipeline> listPipes = new ArrayList<VisionPipeline>();
        listPipes.add(new FilterColourVisionPipeline());
        listPipes.add(new BoundingRectVisionPipeline());
        //and more VisionPipeline
        MyVisionRunner visionRunner = new MyVisionRunner(camera1,listPipes,processedVideo);
        Thread myVisionTread = new Thread(visionRunner);
        myVisionTread.setDaemon(true);
        myVisionTread.start();
        
        //vision - end
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
        
}
