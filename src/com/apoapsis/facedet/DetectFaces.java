package com.apoapsis.facedet;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.face.BasicFaceRecognizer;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class DetectFaces {
	private String[] emotions = { "happy", "surprise"};
	private Trainer trainer;
	private CascadeClassifier faceDet, faceDet2, faceDet3, faceDet4;

	public DetectFaces() {
		faceDet = new CascadeClassifier("resources/haarcascade/haarcascade_frontalface_default.xml");
		faceDet2 = new CascadeClassifier("resources/haarcascade/haarcascade_frontalface_alt2.xml");
		faceDet3 = new CascadeClassifier("resources/haarcascade/haarcascade_frontalface_alt.xml");
		faceDet4 = new CascadeClassifier("resources/haarcascade/haarcascade_frontalface_alt_tree.xml");
		trainer = new Trainer();
	}

	public void train() {
		List<Double> metaScore = new ArrayList<>();

		for (int i = 0; i < 1; i++) {
			double correct = trainer.runRecognizer();
			System.out.println("got " + correct + " percent correct!");
			metaScore.add(correct);
		}

		System.out.println("\n\nend score: " + mean(metaScore) + " percent correct!");
	}

	public String detectFaces() {
		VideoCapture cam = new VideoCapture(0);
		Mat image = new Mat((int) cam.get(Videoio.CV_CAP_PROP_FRAME_HEIGHT),
				(int) cam.get(Videoio.CV_CAP_PROP_FRAME_WIDTH), CvType.CV_8UC3);
		cam.read(image);
		cam.release();
		Mat gray = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
		Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

		MatOfRect face = new MatOfRect();
		MatOfRect face2 = new MatOfRect();
		MatOfRect face3 = new MatOfRect();
		MatOfRect face4 = new MatOfRect();
		MatOfRect faceFeatures = null;

		faceDet.detectMultiScale(gray, face, 1.1, 10, Objdetect.CASCADE_SCALE_IMAGE, new Size(5, 5),
				new Size(800, 800));
		faceDet2.detectMultiScale(gray, face2, 1.1, 10, Objdetect.CASCADE_SCALE_IMAGE, new Size(5, 5),
				new Size(800, 800));
		faceDet3.detectMultiScale(gray, face3, 1.1, 10, Objdetect.CASCADE_SCALE_IMAGE, new Size(5, 5),
				new Size(800, 800));
		faceDet4.detectMultiScale(gray, face4, 1.1, 10, Objdetect.CASCADE_SCALE_IMAGE, new Size(5, 5),
				new Size(800, 800));

		if (!face.empty()) {
			faceFeatures = face;
		} else if (!face2.empty()) {
			faceFeatures = face2;
		} else if (!face3.empty()) {
			faceFeatures = face3;
		} else if (!face4.empty()) {
			faceFeatures = face4;
		}

		if (faceFeatures != null) {
			Rect[] facesArray = faceFeatures.toArray();

			for (Rect r : facesArray) {
				gray = new Mat(gray, new Range(r.y, r.y + r.height), new Range(r.x, r.x + r.width));

				Mat out = new Mat(350, 350, CvType.CV_8UC1);
				Imgproc.resize(gray, out, new Size(350, 350));

				int[] pred = new int[1];
				double[] conf = new double[1];

				BasicFaceRecognizer fishFace = trainer.getFisherFace();
				fishFace.predict(out, pred, conf);
				System.out.println("Confidence: " + conf[0]);
				System.out.println("Prediction: " + emotions[pred[0]]);

				if (conf[0] > 200) {
					return emotions[pred[0]];
				} else {
					return "nothing";
				}
			}
		}

		return null;
	}

	private double mean(List<Double> scores) {
		double sum = 0;
		for (double d : scores) {
			sum += d;
		}

		return sum / scores.size();
	}
}
