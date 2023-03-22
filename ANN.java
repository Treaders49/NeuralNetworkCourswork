import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import javax.swing.JFrame;


public class ANN {

	public static void main(String[] args) {
		final double e = 2.71828; //variable to store the constant value "e"
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of inputs:"); //allows the user to adjust the amount of predictors that are included in the model
		int inputs = scan.nextInt();
		System.out.println("Enter number of hidden nodes:");//allows the adjustment of the amount of hidden nodes in the model
		int hiddenNodes = scan.nextInt();
		System.out.println("Enter the learning parameter");//allows the adjustment of the learning parameter
		double learningParameter = scan.nextDouble();
		double startParameter = learningParameter;//used in the modification annealing when the learning parameter is changed, formula shown later in the code
		double bias = randomVal(); //generating a random bias for the output node
		outputNode output = new outputNode(0.0, bias); //initialising the output node object with an inital placeholder output 0.0 and our random bias
		network neuralNetwork = new network(output, "annealing", "validation"); //initialising a new network object with the output node as an attribute as well as specifying the modification used (left blank if none used) and also the mode the model is in (creating results for running on validation data or test data)
		int indxInputs = 0; //the index to iterate through the amount of inputs specified
		
		int indxHidden = 0; //index to iterate through the specified amount of hidden nodes
		while (indxHidden < hiddenNodes) {
			double nodeWeight = randomVal(); //assigning random node weights and biases for a hidden node
			double nodeBias = randomVal();
			hiddenNode hNode = new hiddenNode(nodeWeight, nodeBias, hiddenNodes);//initialising new hidden node with random weight, bias and the total amount of hidden nodes
			neuralNetwork.addHiddenNode(hNode); //add the hidden node to an arrayList of hidden nodes which is stored as an attribute of the neuralNetwork object
			indxHidden++;
		}
		while (indxInputs < inputs) {//iterating through the amount of inputs specified to create an input node for each one
			inputNode inode = new inputNode(0.0);//initialising a new hidden node with a placeholder input value
			for (hiddenNode hNode: neuralNetwork.getHiddenNodes()) { //iterating through each hidden node to assign weights that are associated with them (weights going from the input node to the specific hidden node)
				hNode.addLinkedNode(inode);//adding the input node to an array stored as an attribute for each hidden node, allows the node to associate each specific node with a weight
				double n = randomVal();//assigning a random weight for each link to a hidden node and adding it to an array
				hNode.addLinkedWeight(n);
			}
			neuralNetwork.addInputNode(inode);//adding the input node to an array of input nodes which is an attribute in the neuralNetwork object
			indxInputs++;
		}
		System.out.println("Enter number of runs");//specifies how many times to run through the data
		int epochs = scan.nextInt();
		int epochsIndx = epochs;//tracks the index of the epochs ran
		ArrayList<Double> outputs = new ArrayList<Double>();//initialising arrayLists which record the output value of the model(standardised) and also one for the error for the train data set over 500 epochs and the validation data set if this is being tested
		ArrayList<String> msErrorTrain = new ArrayList<String>();
		ArrayList<String> msErrorValidation = new ArrayList<String>();
		ArrayList<ArrayList<Double>> trainData = load("standardisedTrainData.txt");//loads data from a text file rather than excel, easier to do
		while (epochsIndx > 0) {
			double error = 0;
			int rowIndx = 0;
			while (rowIndx < trainData.size()) {
			
			indxInputs = 0;
			while (indxInputs <= inputs-1) {
				//process of assigning each input node the corresponding value from the trainData arrayList
				neuralNetwork.getInputNodes().get(indxInputs).setVal(trainData.get(rowIndx).get(indxInputs+1));
				
				
				indxInputs++;
			}
			
			
			//specifying the real output to compare against the one predicted by the model
			output.setRealOutput(trainData.get(rowIndx).get(indxInputs+1));
			neuralNetwork.forwardPass();//calls the forward pass method for the network, specified more in that object
			outputs.add((output.getEstimatedOutput()));//the method generates an estimated output which is used in the back pass, explained in network.java
			neuralNetwork.backwardPass(learningParameter);
			error += Math.pow((neuralNetwork.getOutput().getError()), 2); //cumulative value being calculated for the mean squared error
			
			rowIndx++;
			}
			if (neuralNetwork.getImprovement() == "annealing") { //adjusts the learning parameter at the end of each epoch if this modification is active
				learningParameter = 0.01 + (startParameter - 0.01) * (1 - (1/(1 + Math.pow(e, 10-((20*(epochs -epochsIndx))/epochs )) )));
			}
			msErrorTrain.add(BigDecimal.valueOf((error/(double) trainData.size())).toPlainString()); //divides the cumulative error by the amount of inputs to get the mean squared error and adding it to the array
			epochsIndx--;
		}
		
		System.out.println(msErrorTrain);
		
		
		if(neuralNetwork.getMode() == "validation") { //if the mode is selected that wants to check the validation data set in the case of overfitting, the same process occurs on this data as the train data with the model generate by the train data
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
				outputs.add((output.getEstimatedOutput()));
				neuralNetwork.backwardPass(learningParameter);
				error += Math.pow((neuralNetwork.getOutput().getError()), 2);
				
				rowIndx++;
				}
				msErrorValidation.add(BigDecimal.valueOf((error/(double) trainData.size())).toPlainString());
				epochs--;
			} 
		}else {//if the test data mode is selected, this code will run generating data for a scatter plot
			ArrayList<ArrayList<Double>> standardisedTestData = load("standardisedTestData.txt");
			ArrayList<ArrayList<Double>> testData = load("testData.txt");
			ArrayList<ArrayList<Double>> scatterGraphResults = neuralNetwork.generateTestResults(standardisedTestData, testData, inputs);
			System.out.println(scatterGraphResults);
		}
		scan.close();
		
		//process for plotting graphs, not part of the MLP code
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
        frame.setTitle("LP: " + Double.toString(learningParameter) + "  " + "hidden nodes: " + Integer.toString(hiddenNodes));
        frame.add(graph);
        frame.setSize(1300, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}

	private static double randomVal() {//function to generate a random value between 0.01 and 0.91
		return (Math.random() * 0.9) + 0.01;
	}

	//function for loading a file and putting it into an arrayList in an accepted form
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

}
