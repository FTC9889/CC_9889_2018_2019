package com.team9889.lib.detectors;

import com.disnodeteam.dogecv.detectors.DogeCVDetector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by MannoMation on 1/5/2019.
 */
public class HopperDetector extends DogeCVDetector {
    //Outputs
    private Mat cvResizeOutput = new Mat();
    private Point newPoint0Output = new Point();
    private Point newPoint1Output = new Point();
    private Mat cvRectangle0Output = new Mat();
    private Point newPoint2Output = new Point();
    private Point newPoint3Output = new Point();
    private Mat cvRectangle1Output = new Mat();
    private Mat hsvThreshold0Output = new Mat();
    private Mat hsvThreshold1Output = new Mat();
    private Mat cvAddOutput = new Mat();
    private Mat mask0Output = new Mat();
    private Mat mask1Output = new Mat();

    public HopperDetector() {
        super();
        detectorName = "Hopper Detector"; // Set the detector name
    }

    @Override
    public Mat process(Mat input) {
        // Process frame
        // Step CV_resize0:

/*
        Mat cvResizeSrc = input;
        Size cvResizeDsize = new Size(0, 0);
        double cvResizeFx = 0.05;
        double cvResizeFy = 0.05;
        int cvResizeInterpolation = Imgproc.INTER_NEAREST;
        cvResize(cvResizeSrc, cvResizeDsize, cvResizeFx, cvResizeFy, cvResizeInterpolation, cvResizeOutput);
        */
/*
        // Step New_Point0:
        double newPoint0X = 0.0;
        double newPoint0Y = 0.0;
        newPoint(newPoint0X, newPoint0Y, newPoint0Output);

        // Step New_Point1:
        double newPoint1X = 200.0;
        double newPoint1Y = 52.0;
        newPoint(newPoint1X, newPoint1Y, newPoint1Output);

        // Step CV_rectangle0:
        Mat cvRectangle0Src = cvResizeOutput;
        Point cvRectangle0Pt1 = newPoint0Output;
        Point cvRectangle0Pt2 = newPoint1Output;
        Scalar cvRectangle0Color = new Scalar(0.0, 0.0, 0.0, 0.0);
        double cvRectangle0Thickness = 42.0;
        int cvRectangle0Linetype = Core.FILLED;
        double cvRectangle0Shift = 1.0;
        cvRectangle(cvRectangle0Src, cvRectangle0Pt1, cvRectangle0Pt2, cvRectangle0Color, cvRectangle0Thickness, cvRectangle0Linetype, cvRectangle0Shift, cvRectangle0Output);

        // Step New_Point2:
        double newPoint2X = 0.0;
        double newPoint2Y = 140.0;
        newPoint(newPoint2X, newPoint2Y, newPoint2Output);

        // Step New_Point3:
        double newPoint3X = 92.0;
        double newPoint3Y = 163.0;
        newPoint(newPoint3X, newPoint3Y, newPoint3Output);

        // Step CV_rectangle1:
        Mat cvRectangle1Src = cvRectangle0Output;
        Point cvRectangle1Pt1 = newPoint2Output;
        Point cvRectangle1Pt2 = newPoint3Output;
        Scalar cvRectangle1Color = new Scalar(0.0, 0.0, 0.0, 0.0);
        double cvRectangle1Thickness = 27.0;
        int cvRectangle1Linetype = Core.FILLED;
        double cvRectangle1Shift = 0.0;
        cvRectangle(cvRectangle1Src, cvRectangle1Pt1, cvRectangle1Pt2, cvRectangle1Color, cvRectangle1Thickness, cvRectangle1Linetype, cvRectangle1Shift, cvRectangle1Output);
*/
        // Step HSV_Threshold0:
        Mat hsvThreshold0Input = input;
        double[] hsvThreshold0Hue = {3.3898305084745752, 180.0};
        double[] hsvThreshold0Saturation = {0.0, 30.000000000000007};
        double[] hsvThreshold0Value = {108.05084745762711, 255.0};
        hsvThreshold(hsvThreshold0Input, hsvThreshold0Hue, hsvThreshold0Saturation, hsvThreshold0Value, hsvThreshold0Output);

        // Step HSV_Threshold1:
        Mat hsvThreshold1Input = input;
        double[] hsvThreshold1Hue = {1.694915254237288, 27.59358288770054};
        double[] hsvThreshold1Saturation = {127.25988700564972, 255.0};
        double[] hsvThreshold1Value = {0.0, 255.0};
        hsvThreshold(hsvThreshold1Input, hsvThreshold1Hue, hsvThreshold1Saturation, hsvThreshold1Value, hsvThreshold1Output);

        // Step Mask0:
        Mat goldHsvMask = input;
        Mat mask0Mask = hsvThreshold1Output;
        mask(goldHsvMask, mask0Mask, mask0Output);

        // Step Mask1:
        Mat silverHsvMask = input;
        Mat mask1Mask = hsvThreshold0Output;
        mask(silverHsvMask, mask1Mask, mask1Output);

        // Step CV_add0:
        Mat cvAddSrc1 = mask0Output;
        Mat cvAddSrc2 = mask1Output;
        cvAdd(cvAddSrc1, cvAddSrc2, cvAddOutput);

        return mask0Output;
    }

    @Override
    public void useDefaults() {
        // Add in your scorers here.
    }

    /**
     * This method is a generated getter for the output of a CV_resize.
     * @return Mat output from CV_resize.
     */
    public Mat cvResizeOutput() {
        return cvResizeOutput;
    }

    /**
     * This method is a generated getter for the output of a New_Point.
     * @return Point output from New_Point.
     */
    public Point newPoint0Output() {
        return newPoint0Output;
    }

    /**
     * This method is a generated getter for the output of a New_Point.
     * @return Point output from New_Point.
     */
    public Point newPoint1Output() {
        return newPoint1Output;
    }

    /**
     * This method is a generated getter for the output of a CV_rectangle.
     * @return Mat output from CV_rectangle.
     */
    public Mat cvRectangle0Output() {
        return cvRectangle0Output;
    }

    /**
     * This method is a generated getter for the output of a New_Point.
     * @return Point output from New_Point.
     */
    public Point newPoint2Output() {
        return newPoint2Output;
    }

    /**
     * This method is a generated getter for the output of a New_Point.
     * @return Point output from New_Point.
     */
    public Point newPoint3Output() {
        return newPoint3Output;
    }

    /**
     * This method is a generated getter for the output of a CV_rectangle.
     * @return Mat output from CV_rectangle.
     */
    public Mat cvRectangle1Output() {
        return cvRectangle1Output;
    }

    /**
     * This method is a generated getter for the output of a HSV_Threshold.
     * @return Mat output from HSV_Threshold.
     */
    public Mat hsvThreshold0Output() {
        return hsvThreshold0Output;
    }

    /**
     * This method is a generated getter for the output of a HSV_Threshold.
     * @return Mat output from HSV_Threshold.
     */
    public Mat hsvThreshold1Output() {
        return hsvThreshold1Output;
    }

    /**
     * This method is a generated getter for the output of a Mask.
     * @return Mat output from Mask.
     */
    public Mat mask0Output() {
        return mask0Output;
    }

    /**
     * This method is a generated getter for the output of a Mask.
     * @return Mat output from Mask.
     */
    public Mat mask1Output() {
        return mask1Output;
    }


    /**
     * Resizes an image.
     * @param src The image to resize.
     * @param dSize size to set the image.
     * @param fx scale factor along X axis.
     * @param fy scale factor along Y axis.
     * @param interpolation type of interpolation to use.
     * @param dst output image.
     */
    private void cvResize(Mat src, Size dSize, double fx, double fy, int interpolation,
                          Mat dst) {
        if (dSize==null) {
            dSize = new Size(0,0);
        }
        Imgproc.resize(src, dst, dSize, fx, fy, interpolation);
    }

    /**
     * Fills a point with given x and y values.
     * @param x the x value to put in the point
     * @param y the y value to put in the point
     * @param point the point to fill
     */
    private void newPoint(double x, double y, Point point) {
        point.x = x;
        point.y = y;
    }

    /**
     * Draws a rectangle on an image.
     * @param src Image to draw rectangle on.
     * @param pt1 one corner of the rectangle.
     * @param pt2 opposite corner of the rectangle.
     * @param color Scalar indicating color to make the rectangle.
     * @param thickness Thickness of the lines of the rectangle.
     * @param lineType Type of line for the rectangle.
     * @param shift Number of decimal places in the points.
     * @param dst output image.
     */
    private void cvRectangle(Mat src, Point pt1, Point pt2, Scalar color,
                             double thickness, int lineType, double shift, Mat dst) {
        src.copyTo(dst);
        if (color == null) {
            color = Scalar.all(1.0);
        }
        Imgproc.rectangle(dst, pt1, pt2, color, (int)thickness, lineType, (int)shift);
    }

    /**
     * Segment an image based on hue, saturation, and value ranges.
     *
     * @param input The image on which to perform the HSL threshold.
     * @param hue The min and max hue
     * @param sat The min and max saturation
     * @param val The min and max value
     */
    private void hsvThreshold(Mat input, double[] hue, double[] sat, double[] val,
                              Mat out) {
        Imgproc.cvtColor(input, out, Imgproc.COLOR_BGR2HSV);
        Core.inRange(out, new Scalar(hue[0], sat[0], val[0]),
                new Scalar(hue[1], sat[1], val[1]), out);
    }

    /**
     * Filter out an area of an image using a binary mask.
     * @param input The image on which the mask filters.
     * @param mask The binary image that is used to filter.
     * @param output The image in which to store the output.
     */
    private void mask(Mat input, Mat mask, Mat output) {
        mask.convertTo(mask, CvType.CV_8UC1);
        Core.bitwise_xor(output, output, output);
        input.copyTo(output, mask);
    }

    /**
     * Calculates the sum of two Mats.
     * @param src1 the first Mat
     * @param src2 the second Mat
     * @param out the Mat that is the sum of the two Mats
     */
    private void cvAdd(Mat src1, Mat src2, Mat out) {
        Core.add(src1, src2, out);
    }



}
