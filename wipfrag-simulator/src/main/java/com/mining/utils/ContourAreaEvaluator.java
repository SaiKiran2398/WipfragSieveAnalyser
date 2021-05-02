package com.mining.utils;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


public class ContourAreaEvaluator {
    private String fileAddress;
    private List<MatOfPoint> contours;
    private List<Double> contourAreas;
    private static final double pixelChangeRatio = 5.23;
    private static final double imageScale = 0.38;

    ContourAreaEvaluator(String fileAddress){
        this.fileAddress = fileAddress;
        contours = new ArrayList<>();
        contourAreas = new ArrayList<>();
    }
    public void process() throws Exception {
        //Loading the OpenCV core library
        nu.pattern.OpenCV.loadLocally();

        //Reading the contents of the image
        Mat src = Imgcodecs.imread(fileAddress);

        //Converting the source image to binary
        Mat gray = new Mat(src.rows(), src.cols(), src.type());
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_BGR2GRAY);
        Mat binary = new Mat(src.rows(), src.cols(), src.type(), new Scalar(0));
        Imgproc.threshold(gray, binary, 100, 255, Imgproc.THRESH_BINARY_INV);

        //Finding Contours
        List<MatOfPoint> contours = new ArrayList();
        Mat hierarchey = new Mat();
        Imgproc.findContours(binary, contours, hierarchey, Imgproc.RETR_TREE,
                Imgproc.CHAIN_APPROX_SIMPLE);
        Mat draw = Mat.zeros(src.size(), CvType.CV_8UC3);

        System.out.println("Total Rocks detected : " + contours.size());
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(0, 0, 255);

            //Calculating the area in Pixels and converting it to MM.
            double cont_area = Imgproc.contourArea(contours.get(i));
            cont_area = (cont_area * (pixelChangeRatio)) / Math.pow(imageScale,2);

            //Adding contour areas to list.
            contourAreas.add(cont_area);

            if(cont_area>5000.0){
                Imgproc.drawContours(draw, contours, i, color, 2,
                        Imgproc.LINE_8, hierarchey, 2, new Point() ) ;
            } else {
                color = new Scalar(255, 255, 255);
                Imgproc.drawContours(draw, contours, i, color, 2, Imgproc.LINE_8,
                        hierarchey, 2, new Point() ) ;
            }
        }

        Imgcodecs.imwrite("D:\\RockContours.jpg",draw);
        HighGui.imshow("Contours operation", draw);
        HighGui.waitKey();
    }

    public List<Double> getContourAreas() {
        return contourAreas;
    }
}
