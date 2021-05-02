package com.kibolynx.test;

// zxing
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
// opencv
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.opencv.core.Core;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
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
    public  static void main(String[] args)
            throws WriterException, IOException, NotFoundException
    {
        System.out.println("KIBO_LYNX_TEST ------------------------------------");

//        System.out.println("Input filename (without path)");
//
//        Scanner in = new Scanner(System.in);
//
//        String filename = in.nextLine();
//        Mat img = Imgcodecs.imread(filename, Imgcodecs.IMREAD_GRAYSCALE);
//
//        final int width = img.cols();
//        final int height = img.rows();
//
//        System.out.println("Image Cv Type: " + CvType.typeToString(img.type()));
//        System.out.println("Image width: " + width);
//        System.out.println("Image height: " + height + "\n");

//        Mat res = undistort(img);

//        String path = "result.png";

//        Imgcodecs.imwrite(path,res);
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
        String qrContents = "{\"p\":18,\"x\":-9.80,\"y\":-9.80,\"z\":11.09}";
        System.out.println(qrContents);
        System.out.println(Arrays.toString(interpretQRString(qrContents)));
    }

    /// Algorithm. functions with minimal changes, fixes
    public static Mat undistort(Mat in) {

        final int width = in.width();
        final int height = in.cols();

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

    public static String readQR(BufferedImage cropedImage, String charset, Map hashMap)
            throws FileNotFoundException, IOException, NotFoundException {

        BinaryBitmap binaryBitmap
                = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(cropedImage)));

        Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
        decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        Result result = new MultiFormatReader().decode(binaryBitmap, decodeHints);;

        return result.getText();
    }

    private static float[] interpretQRString(String qrContents) {
        String[] multi_contents = qrContents.split(",");

        int pattern = Integer.parseInt(multi_contents[0].substring(5));

        float final_x = Float.parseFloat(multi_contents[1].substring(4));
        float final_y = Float.parseFloat(multi_contents[2].substring(4));
        float final_z = Float.parseFloat(multi_contents[3].substring(4, multi_contents[3].length()-1));
        return new float[] {pattern, final_x, final_y, final_z};
    }
}