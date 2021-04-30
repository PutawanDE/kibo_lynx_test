package com.kibolynx.test;

// zxing
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
// opencv
import org.opencv.core.Core;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Scanner;

public class main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    // Testing code
    public  static void main(String[] args) {
        System.out.println("KIBO_LYNX_TEST ------------------------------------");

        System.out.println("Input filename (without path)");

        Scanner in = new Scanner(System.in);

        String filename = in.nextLine();
        Mat img = Imgcodecs.imread(filename, Imgcodecs.IMREAD_GRAYSCALE);

        final int width = img.cols();
        final int height = img.rows();

        System.out.println("Image Cv Type: " + CvType.typeToString(img.type()));
        System.out.println("Image width: " + width);
        System.out.println("Image height: " + height + "\n");

        Mat res = undistort(img);

        Imgcodecs.imwrite("result.png",res);
//        Mat qr = Imgcodecs.imread("qrUndistort.png", Imgcodecs.IMREAD_GRAYSCALE);
        Mat outPoints = new Mat();
        Mat qrCode = new Mat();
        String decodedQR = new QRCodeDetector().detectAndDecode(res, outPoints, qrCode);

        System.out.println("Decoded message: " + decodedQR);
        Imgcodecs.imwrite("points.png",outPoints);
        Imgcodecs.imwrite("qr.png",qrCode);
    }

    /// Algorithm. functions with minimal changes, fixes
    public static Mat undistort(Mat in) {

        final int width = in.cols();
        final int height = in.rows();

        Mat cam_Mat = new Mat(3, 3, CvType.CV_32FC1);
        Mat dist_coeff = new Mat(1, 5, CvType.CV_32FC1);

        final double cam_Mat_sim[] = {
                567.229305, 0.0, 659.077221,
                0.0, 574.192915, 517.007571,
                0.0, 0.0, 1.0
        };

        final double dist_coeff_sim[] = {
                -0.216247, 0.03875, -0.010157, 0.001969, 0.0
        };

        cam_Mat.put(0, 0, cam_Mat_sim);
        dist_coeff.put(0, 0, dist_coeff_sim);

        Mat out = new Mat(width, height, CvType.CV_8UC1);
        Imgproc.undistort(in, out, cam_Mat, dist_coeff);
        System.out.println("Finish undistorting");
        return out;
    }

//    public void  readQRCode(String filename) {
//        com.google.zxing.Result result = new QRCodeReader().decode(bitmap);
//        String data = result.getText();
//    }
}