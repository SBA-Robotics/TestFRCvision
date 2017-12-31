# Test FRC vision
Test project to read a Web Cam attached to USB 0 , and using OpenCV , process the frames.
Two  or three "VisionPipeline" subclasses aee given under vision package.

FilterColourVisionPipeline.java
Takes frame from UBM Camera and calculates OpenCV Countours around objects in the frame, for a given color range, and size or area range. Saves the countours to LocalTable.

BoundingRectVisionPipeline.java
Takes frame from previous vision Pipeline , and also gets filtered countours, and draws Bounding Rectangles for each of those countours.

MyVisionRunner.java
The WPILIB provided VisionRunner had issues for me, so I implemented a Runnable and Thread myself.
