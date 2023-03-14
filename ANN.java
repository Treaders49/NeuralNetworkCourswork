import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import javax.swing.JFrame;


public class ANN {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of inputs:");
		int inputs = scan.nextInt();
		System.out.println("Enter number of hidden nodes:");
		int hiddenNodes = scan.nextInt();
		System.out.println("Enter the output value");
		System.out.println("Enter the learning parameter");
		double learningParameter = scan.nextDouble();
		System.out.println("Enter bias for output node");
		double bias = scan.nextDouble();
		outputNode output = new outputNode(0.0, bias);
		network neuralNetwork = new network(output);
		int indxInputs = 0;
		
		int indxHidden = 0;
		while (indxHidden < hiddenNodes) {
			System.out.println("enter weight going out of hidden node");
			double nodeWeight = scan.nextDouble();
			System.out.println("enter bias of hidden node");
			double nodeBias = scan.nextDouble();
			hiddenNode hNode = new hiddenNode(nodeWeight, nodeBias);
			neuralNetwork.addHiddenNode(hNode);
			indxHidden++;
		}
		while (indxInputs < inputs) {
			inputNode inode = new inputNode(0.0);
			for (hiddenNode hNode: neuralNetwork.getHiddenNodes()) {
				hNode.addLinkedNode(inode);
				System.out.println("enter weight coming from input node");
				double n = scan.nextDouble();
				hNode.addLinkedWeight(n);
				hNode.addInitialLinkedWeight(n);
			}
			neuralNetwork.addInputNode(inode);
			indxInputs++;
		}
		System.out.println("Enter number of runs");
		int epochs = scan.nextInt();
		int epochsIndx = epochs;
		ArrayList<Double> outputs = new ArrayList<Double>();
		ArrayList<String> msErrorTrain = new ArrayList<String>();
		ArrayList<String> msErrorValidation = new ArrayList<String>();
		ArrayList<ArrayList<Double>> trainData = load("standardisedTrainData.txt");
		while (epochsIndx > 0) {
			double error = 0;
			int rowIndx = 0;
			while (rowIndx < trainData.size()) {
			
			indxInputs = 0;
			while (indxInputs <= inputs-1) {
				
				neuralNetwork.getInputNodes().get(indxInputs).setVal(trainData.get(rowIndx).get(indxInputs+1));
				
				
				indxInputs++;
			}
			
			
			
			output.setRealOutput(trainData.get(rowIndx).get(indxInputs+1));
			neuralNetwork.forwardPass();
			System.out.print("estimated output:");
			outputs.add((output.getEstimatedOutput()));
			neuralNetwork.backwardPass(learningParameter);
			error += Math.pow((neuralNetwork.getOutput().getError()), 2);
			
			rowIndx++;
			}
			msErrorTrain.add(BigDecimal.valueOf((error/(double) trainData.size())).toPlainString());
			epochsIndx--;
		}
		
		System.out.println(msErrorTrain);
		
		resetMLP(neuralNetwork);
		trainData = load("standardisedValidationData.txt");
		while (epochs > 0) {
			double error = 0;
			int rowIndx = 0;
			while (rowIndx < trainData.size()) {
			
			indxInputs = 0;
			while (indxInputs <= inputs-1) {
				
				neuralNetwork.getInputNodes().get(indxInputs).setVal(trainData.get(rowIndx).get(indxInputs+1));
				
				
				indxInputs++;
			}
			
			
			
			output.setRealOutput(trainData.get(rowIndx).get(indxInputs+1));
			neuralNetwork.forwardPass();
			System.out.print("estimated output:");
			outputs.add((output.getEstimatedOutput()));
			neuralNetwork.backwardPass(learningParameter);
			error += Math.pow((neuralNetwork.getOutput().getError()), 2);
			
			rowIndx++;
			}
			msErrorValidation.add(BigDecimal.valueOf((error/(double) trainData.size())).toPlainString());
			epochs--;
		}
		scan.close();
		
		
		double[] trainErrorData = new double[msErrorTrain.size()];
		int indx = 0;
		for(String error : msErrorTrain) {
		    double errorDouble = Double.parseDouble(error);
		    trainErrorData[indx] = errorDouble;
		    indx++;
		}
		
		double[] validationErrorData = new double[msErrorValidation.size()];
		indx = 0;
		for(String error : msErrorValidation) {
		    double errorDouble = Double.parseDouble(error);
		    validationErrorData[indx] = errorDouble;
		    indx++;
		}
		
        LineGraph graph = new LineGraph(trainErrorData, validationErrorData, "epochs", "error");
        JFrame frame = new JFrame("Line Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(graph);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

	private static void resetMLP(network n) {
		outputNode oNode = n.getOutput();
		oNode.setBias(oNode.getInitialBias());
		ArrayList<hiddenNode> hiddenNodes = n.getHiddenNodes();
		for(hiddenNode hNode: hiddenNodes) {
			hNode.setBias(hNode.getInitialBias());
			hNode.setWeight(hNode.getInitialWeight());
			hNode.setLinkedWeights(hNode.getInitialLinkedWeights());
		}
	}

	private static ArrayList<ArrayList<Double>> load(String fileName) {
		ArrayList<ArrayList<Double>> dataList =  new ArrayList<ArrayList<Double>>();
		try {
			File f = new File(fileName);
			Scanner reader = new Scanner(f);
			while (reader.hasNextLine()) {
				ArrayList<Double> line = new ArrayList<Double>();
				String[] list = reader.nextLine().split(" ");
				for (String cell: list) {
					line.add(Double.parseDouble(cell));
				}
				dataList.add(line);
			}
			reader.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		
		return dataList;
		
		
	}

	private static double round(double valueToRound) {
		return Math.round(valueToRound * 100000)/ 1000000.0;
	}
}
