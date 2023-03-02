import java.util.*;
public class ANN {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter number of inputs:");
		int inputs = scan.nextInt();
		System.out.println("Enter number of hidden nodes:");
		int hiddenNodes = scan.nextInt();
		System.out.println("Enter the output value");
		double outputValue = scan.nextDouble();
		System.out.println("Enter the learning parameter");
		double learningParameter = scan.nextDouble();
		outputNode output = new outputNode(outputValue);
		network neuralNetwork = new network(output);
		int indxInputs = 0;
		while (indxInputs < inputs) {
			System.out.println("Enter value for input node");
			double inputNodeVal = scan.nextDouble();
			inputNode inode = new inputNode(inputNodeVal);
			neuralNetwork.addInputNode(inode);
			indxInputs++;
		}
		int indxHidden = 0;
		while (indxHidden < hiddenNodes) {
			hiddenNode hNode = new hiddenNode();
			for (inputNode inptN : neuralNetwork.getInputNodes()) {
				hNode.addLinkedNode(inptN);
				hNode.addLinkedWeight((Math.round(Math.random() * 100)) / 100.0);
			}
			
			neuralNetwork.addHiddenNode(hNode);
			indxHidden++;
		}
		System.out.println("Enter number of runs");
		int epochs = scan.nextInt();
		ArrayList<Double> outputs = new ArrayList<Double>();
		while (epochs > 0) {
			neuralNetwork.forwardPass();
			System.out.print("estimated output:");
			System.out.println(output.getEstimatedOutput());
			outputs.add((output.getEstimatedOutput()));
			neuralNetwork.backwardPass(learningParameter);
			epochs-=1;
		}
		System.out.println(outputs.get(0));
		System.out.println(outputs.get(outputs.size()/4));
		System.out.println(outputs.get(outputs.size() - 1));
		scan.close();
	}

	private static double to2Dp(double valueToRound) {
		return Math.round(valueToRound * 100)/ 100.0;
	}
}
