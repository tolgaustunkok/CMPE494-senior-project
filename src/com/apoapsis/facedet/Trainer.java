package com.apoapsis.facedet;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.face.BasicFaceRecognizer;
import org.opencv.face.Face;
import org.opencv.imgcodecs.Imgcodecs;

public class Trainer {
	
	private final static int TRAINING_DATA = 0;
	private final static int TRAINING_LABEL = 1;
	private final static int PREDICTION_DATA = 2;
	private final static int PREDICTION_LABEL = 3;
	
	private String[] emotions = { "happy", "contempt", "neutral", "surprise" };
	private BasicFaceRecognizer fishFace;
	
	public Trainer() {
		fishFace = Face.createFisherFaceRecognizer();
	}
	
	private List<List<String>> getFiles(String emotion) {
		List<List<String>> result = new ArrayList<>(2);
		List<String> filesStr = new ArrayList<>();
		PathMatcher files = FileSystems.getDefault().getPathMatcher("glob:resources/dataset/" + emotion + "/*");
		File pwd = new File("resources/");
		
		Collection<File> allFiles = FileUtils.listFiles(pwd, null, true);
		
		for (File f : allFiles) {
			if (files.matches(f.toPath())) {
				filesStr.add(f.toString());
			}
		}
		
		Collections.shuffle(filesStr);
		
		List<String> training = filesStr.subList(0, (int)(filesStr.size() * 0.8));
		List<String> prediction = filesStr.subList((int)(filesStr.size() - filesStr.size() * 0.2), filesStr.size());
		
		result.add(training);
		result.add(prediction);
		
		return result;
	}
	
	private List<Object> makeSets() {
		List<Object> result = new ArrayList<>();
		
		List<Mat> trainingData = new ArrayList<>();
		List<Integer> trainingLabels = new ArrayList<>();
		List<Mat> predictionData = new ArrayList<>();
		List<Integer> predictionLabels = new ArrayList<>();
		
		for (int i = 0; i < emotions.length; i++) {
			List<List<String>> traPre = getFiles(emotions[i]);
			
			for (String item : traPre.get(0)) {
				Mat image = Imgcodecs.imread(item, Imgcodecs.IMREAD_GRAYSCALE);
				//Mat gray = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
				//Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
				//trainingData.add(gray);
				trainingData.add(image);
				trainingLabels.add(i);
			}
			
			for (String item : traPre.get(1)) {
				Mat image = Imgcodecs.imread(item, Imgcodecs.IMREAD_GRAYSCALE);
				//Mat gray = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
				//Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
				//predictionData.add(gray);
				predictionData.add(image);
				predictionLabels.add(i);
			}
		}
		
		result.add(trainingData);
		result.add(trainingLabels);
		result.add(predictionData);
		result.add(predictionLabels);
		
		return result;
	}
	
	private int[] convert2IntArray(List<Integer> arr) {
		int[] result = new int[arr.size()];
		
		for (int i = 0; i < arr.size(); i++) {
			result[i] = arr.get(i);
		}
		
		return result;
	}
	
	public double runRecognizer() {
		List<Object> traDTraLpreDpreL = makeSets();
		
		System.out.println("training fisher face classifier");
		System.out.println("size of training set is: " + ((List<Integer>)traDTraLpreDpreL.get(TRAINING_LABEL)).size() + " images");
		
		Mat trainingLables = new Mat(1, ((List<Integer>) traDTraLpreDpreL.get(TRAINING_LABEL)).size(), CvType.CV_32SC1);
		int[] traL = convert2IntArray(((List<Integer>) traDTraLpreDpreL.get(TRAINING_LABEL)));
		trainingLables.put(0, 0, traL);
		fishFace.train((List<Mat>) traDTraLpreDpreL.get(TRAINING_DATA), trainingLables);
		
		System.out.println("predicting classification set");
		
		int correct = 0, incorrect = 0;
		
		for (int i= 0; i < ((List<Mat>)traDTraLpreDpreL.get(PREDICTION_DATA)).size(); i++) {
			int[] pred = new int[1];
			double[] conf = new double[1];
			fishFace.predict(((List<Mat>)traDTraLpreDpreL.get(PREDICTION_DATA)).get(i), pred, conf);
			
			if (pred[0] == ((List<Integer>)traDTraLpreDpreL.get(PREDICTION_LABEL)).get(i)) {
				correct++;
			} else {
				incorrect++;
			}
		}
		
		return ((100 * correct) / (correct + incorrect));
	}
	
//	private int search(String s, String[] arr) {
//		for (int i = 0; i < arr.length; i++) {
//			if (s.equalsIgnoreCase(arr[i])) {
//				return i;
//			}
//		}
//		
//		return -1;
//	}
	
	public BasicFaceRecognizer getFisherFace() {
		return fishFace;
	}
}
