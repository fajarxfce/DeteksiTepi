package com.fajar.deteksitepi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
/**
 *
 * @author Fajar
 */
public class DeteksiTepi {

    public static void main(String[] args) {
        Mat originalImage = Imgcodecs.imread("C:\\Users\\Fajar\\Pictures\\Screenshots\\rendy.png");

        // Perform edge detection
        Mat edgeImage = detectEdges(originalImage);

        // Display the original and edge-detected images
        displayImages(matToBufferedImage(originalImage), matToBufferedImage(edgeImage));
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
    }
    
    private static Mat detectEdges(Mat originalImage) {
        // Convert the image to grayscale
        Mat grayImage = new Mat();
        Imgproc.cvtColor(originalImage, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Apply GaussianBlur to reduce noise and improve edge detection
        Imgproc.GaussianBlur(grayImage, grayImage, new Size(5, 5), 0);

        // Apply Canny edge detector
        Mat edges = new Mat();
        Imgproc.Canny(grayImage, edges, 50, 150);

        return edges;
    }
    
    private static BufferedImage matToBufferedImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();

        BufferedImage bufImage = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }
    
    private static void displayImages(BufferedImage originalImage, BufferedImage edgeImage) {
        JFrame frame = new JFrame("Deteksi Tepi");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.add(new JLabel(new ImageIcon(originalImage)));
        panel.add(new JLabel(new ImageIcon(edgeImage)));

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
