package com.kibolynx.test;

// zxing
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
// opencv
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.opencv.core.*;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class main {

    static{ System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    // Testing code
    class imagePoint {
        float x, y;
        imagePoint(float x, float y) {
            this.x = x;
            this.y = y;
        }

        String dump() {
            return ("[" + x + ", " + y + "]");
        }
    }

    static  final int NAV_MAX_WIDTH = 1280;
    static final int NAV_MAX_HEIGHT = 960;

    static final double[] CAM_MATSIM = {
            567.229305, 0.0, 659.077221,
            0.0, 574.192915, 517.007571,
            0.0, 0.0, 1.0
    };

    static final double[] DIST_COEFFSIM = {
            -0.216247, 0.03875, -0.010157, 0.001969, 0.0
    };

    public  static void main(String[] args)
            throws WriterException, IOException, NotFoundException
    {
        System.out.println("KIBO_LYNX_TEST ------------------------------------");

        System.out.println("Input filename (without path)");
//
        Scanner in = new Scanner(System.in);
//
        String filename = in.nextLine();
        Mat img = Imgcodecs.imread(filename, Imgcodecs.IMREAD_GRAYSCALE);
//
        final int width = img.cols();
        final int height = img.rows();
//
        System.out.println("Image Cv Type: " + CvType.typeToString(img.type()));
        System.out.println("Image width: " + width);
        System.out.println("Image height: " + height + "\n");

//        Mat res = undistort(img);
//        System.out.println("Input number of points");
//        int n = Integer.parseInt(in.nextLine());

        System.out.println("Input points");

        while(true){
            String line = in.nextLine();
            if(line.equals("")){
                break;
            }
            Mat point = new Mat(1, 8, CvType.CV_32FC1);
            float data[] = new float[8];

            String[] num = line.split(", ");
            for (int i = 0; i < 8; i++) {
                data[i] = Float.parseFloat(num[i]);
            }
            point.put(0, 0, data);
//            System.out.println(point.dump());
            drawPointsOnImg(img, point);
        }

        String path = "result.png";

        Imgcodecs.imwrite("drawPoint.png",img);

        Mat pointMat = new Mat(1, 1, CvType.CV_32FC2);
//        float[] point = {639.187f, 884.750f};
//        float[] point = {640f, 480f};
//        pointMat.put(0, 0, point);
//        Mat undistortCenter = undistortPoints(pointMat);
//
//        System.out.println("Undistorted center pos:" + undistortCenter.dump());

//        Mat qr = Imgcodecs.imread("qrUndistort.png", Imgcodecs.IMREAD_GRAYSCALE);
//        Mat outPoints = new Mat();
//        Mat qrCode = new Mat();
//

        // Crop result
//        BufferedImage image = ImageIO.read(new FileInputStream(filename));
//        final int width = image.getWidth();
//        final int height = image.getHeight();
//        BufferedImage cropedImage = image.getSubimage(300, 200, 700, 500);
//        ImageIO.write(cropedImage, "png", new File("crop.png"));
//        System.out.println("Crop succeed");
//
//        Mat img = Imgcodecs.imread("crop.png", Imgcodecs.IMREAD_GRAYSCALE);
//        Mat res = undistort(img);
//
//        Imgcodecs.imwrite("crop-undistort.png",res);

        /// FOR READING QR CODE
        // Path where the QR code is saved

        // Encoding charset
//        String charset = "UTF-8";
//
//        Map<EncodeHintType, ErrorCorrectionLevel> hintmap
//                = new HashMap<EncodeHintType,
//                                ErrorCorrectionLevel>();
//
//        hintmap.put(EncodeHintType.ERROR_CORRECTION,
//                ErrorCorrectionLevel.L);
//
//        String decodedQR = readQR(cropedImage, charset, hintmap);
//
//        System.out.println("Decoded message: " + decodedQR);

//        Imgcodecs.imwrite("points.png",outPoints);
//        Imgcodecs.imwrite("qr.png",qrCode);

        //Interpret QR string
//        String qrContents = "{\"p\":18,\"x\":-9.80,\"y\":-9.80,\"z\":11.09}";
//        System.out.println(qrContents);
//        System.out.println(Arrays.toString(interpretQRString(qrContents)));
    }

    /// Algorithm. functions with minimal changes, fixes
    public static Mat undistort(Mat in) {

        final int width = in.width();
        final int height = in.cols();

        Mat cam_Mat = new Mat(3, 3, CvType.CV_32FC1);
        Mat dist_coeff = new Mat(1, 5, CvType.CV_32FC1);

        cam_Mat.put(0, 0, CAM_MATSIM);
        dist_coeff.put(0, 0, DIST_COEFFSIM);

        Mat out = new Mat(width, height, CvType.CV_8UC1);
        Imgproc.undistort(in, out, cam_Mat, dist_coeff);
        System.out.println("Finish undistorting");
        return out;
    }
//
//    public static String readQR(BufferedImage cropedImage, String charset, Map hashMap)
//            throws FileNotFoundException, IOException, NotFoundException {
//
//        BinaryBitmap binaryBitmap
//                = new BinaryBitmap(new HybridBinarizer(
//                new BufferedImageLuminanceSource(cropedImage)));
//
//        Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
//        decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//        Result result = new MultiFormatReader().decode(binaryBitmap, decodeHints);;
//
//        return result.getText();
//    }
//
//    private static float[] interpretQRString(String qrContents) {
//        String[] multi_contents = qrContents.split(",");
//
//        int pattern = Integer.parseInt(multi_contents[0].substring(5));
//
//        float final_x = Float.parseFloat(multi_contents[1].substring(4));
//        float final_y = Float.parseFloat(multi_contents[2].substring(4));
//        float final_z = Float.parseFloat(multi_contents[3].substring(4, multi_contents[3].length()-1));
//        return new float[] {pattern, final_x, final_y, final_z};
//    }

//    private static Mat undistortPoints(Mat points) {
//        final String TAG = "undistortCorner";
//
//        // in -> rows:1, cols:4
//        // in -> 1xN 2 Channel
//        System.out.println(TAG + "Start");
//
//        Mat cameraMat = new Mat(3, 3, CvType.CV_32FC1);
//        Mat distCoeffs = new Mat(1, 5, CvType.CV_32FC1);
//
//        cameraMat.put(0, 0, CAM_MATSIM);
//        distCoeffs.put(0, 0, DIST_COEFFSIM);
//
//        Mat out = new Mat(points.rows(), points.cols(), points.type());
//
//        Imgproc.undistortPoints(points, out, cameraMat, distCoeffs, new Mat(), cameraMat);
//
//        System.out.println(TAG + "undistort=" + out.dump());
//        // out -> 1xN 2 Channel
//        return out;
//    }
//
//    private static  double[] pixelDistanceToAngle(double[] target, double[] ref) {
//        final String TAG = "pixelDistanceToAngle";
//
//        double xDistance = ref[0] - target[0];
//        double yDistance = ref[1] - target[1];
//        final double anglePerPixel = 130 / Math.sqrt(Math.pow(NAV_MAX_WIDTH, 2) + Math.pow(NAV_MAX_HEIGHT, 2));
//        System.out.println(TAG + "xDistance=" + xDistance);
//        System.out.println(TAG + "yDistance=" + yDistance);
//        System.out.println(TAG + "anglePerPixel=" + anglePerPixel);
//
//        double xAngle = xDistance * anglePerPixel;
//        double yAngle = yDistance * anglePerPixel;
//        System.out.println(TAG + "xAngle=" + xAngle);
//        System.out.println(TAG + "yAngle=" + yAngle);
//
//        double[] out = {xAngle, yAngle};
//        return out;
//    }

//    private void ar_read() {
//        final String TAG = "ar_read";
//
//        Mat pic = api.getMatNavCam();
//        Dictionary dict = Aruco.getPredefinedDictionary(Aruco.DICT_5X5_250);
//        List<Mat> corners = new ArrayList<>();
//        Mat ids = new Mat();
//
//        try {
//            System.out.println(TAG, "Reading AR tags");
//            Aruco.detectMarkers(pic, dict, corners, ids);
//
//            for (int i = 0; i < corners.size(); i++) {
//                System.out.println(TAG, "corners[" + i + "]=" + corners.get(i).dump());
//            }
//            System.out.println(TAG, "ids= " + ids.dump());
//        }
//        catch (Exception e) {
//            System.out.println(TAG, "--Fail: " + e.toString());
//            return;
//        }
//    }

//    private imagePoint findCenter(float x1, float y1, float x2, float y2) {
//        float xCenter = (x1+x2)/2;
//        float yCenter = (y1+y2)/2;
//        return new imagePoint(xCenter, yCenter);
//    }

    private static void drawPointsOnImg(Mat img, Mat points) {
//        System.out.println(points.get(0, 8)[0]);
//        if(points.get(0, 0)[0]) return;
        for(int j = 1; j < 8; j+=2) {
            Imgproc.circle(img, new Point(points.get(0, j)[0], points.get(0, j - 1)[0]), 5, new Scalar(255, 255, 255), -1);
        }
    }
}