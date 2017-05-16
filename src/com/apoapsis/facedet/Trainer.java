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
import org.opencv.imgproc.Imgproc;

public class Trainer {
	
	private String[] emotions = { "happy", "surprise", "neutral" };
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
		
		// Collections.shuffle(filesStr);
		
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
		
		for (String emotion : emotions) {
			List<List<String>> traPre = getFiles(emotion);
			
			for (String item : traPre.get(0)) {
				Mat image = Imgcodecs.imread(item);
				Mat gray = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
				Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
				trainingData.add(gray);
				trainingLabels.add(search(emotion, emotions));
			}
			
			for (String item : traPre.get(1)) {
				Mat image = Imgcodecs.imread(item);
				Mat gray = new Mat(image.rows(), image.cols(), CvType.CV_8UC1);
				Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);
				predictionData.add(gray);
				predictionLabels.add(search(emotion, emotions));
			}
		}
		
		result.add(trainingData);
		result.add(trainingLabels);
		result.add(predictionData);
		result.add(predictionLabels);
		
		return result;
	}
	
	private int[] changeIntArray(List<Integer> arr) {
		int[] result = new int[arr.size()];
		
		for (int i = 0; i < arr.size(); i++) {
			result[i] = arr.get(i);
		}
		
		return result;
	}
	
	public double runRecognizer() {
		List<Object> traDTralpreDpreL = makeSets();
		
		System.out.println("training fisher face classifier");
		System.out.println("size of training set is: " + ((List<Integer>)traDTralpreDpreL.get(1)).size() + " images");
		
		Mat trainingLables = new Mat(1, ((List<Integer>) traDTralpreDpreL.get(1)).size(), CvType.CV_32SC1);
		int[] traL = changeIntArray(((List<Integer>) traDTralpreDpreL.get(1)));
		trainingLables.put(0, 0, traL);
		fishFace.train((List<Mat>) traDTralpreDpreL.get(0), trainingLables);
		
		System.out.println("predicting classification set");
		
		int cnt = 0, correct = 0, incorrect = 0;
		
		for (Mat image : (List<Mat>)traDTralpreDpreL.get(2)) {
			int[] pred = new int[1];
			double[] conf = new double[1];
			fishFace.predict(image, pred, conf);
			
			if (pred[0] == ((List<Integer>)traDTralpreDpreL.get(3)).get(cnt)) {
				correct++;
				cnt++;
			} else {
				incorrect++;
				cnt++;
			}
		}
		
		return ((100 * correct) / (correct + incorrect));
	}
	
	private int search(String s, String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (s.equalsIgnoreCase(arr[i])) {
				return i;
			}
		}
		
		return -1;
	}
	
	public BasicFaceRecognizer getFisherFace() {
		return fishFace;
	}
}
